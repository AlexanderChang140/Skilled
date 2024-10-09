package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.PlayerSkills;
import me.cat.skilled.capability.PlayerSkillsProvider;
import me.cat.skilled.skill.SkillWrapper;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

import static net.minecraft.commands.Commands.literal;

public class SkillCommand {
    private static final SuggestionProvider<CommandSourceStack> sugg = (ctx, builder) -> SharedSuggestionProvider.suggest(SkillUtil.getSkillIds(), builder);

    public SkillCommand(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(literal("skilled")
                .then(literal("skill")
                        .then(literal("get")
                                .executes(command -> getSkills(command.getSource())))
                        .then(literal("add")
                                .then(Commands.argument("skills", StringArgumentType.string())
                                        .suggests(sugg)
                                        .executes(command -> {
                                            String skill = StringArgumentType.getString(command, "skills");
                                            int level = 1;
                                            return addSkill(command.getSource(), skill, level);
                                        })
                                        .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                                .executes(command -> {
                                                    String skill = StringArgumentType.getString(command, "skills");
                                                    int level = IntegerArgumentType.getInteger(command, "level");
                                                    return addSkill(command.getSource(), skill, level);
                                                })
                                        )
                                )
                        )
                        .then(literal("clear")
                                .executes(command -> clearSkill(command.getSource()))
                        )
                )
        );
    }

    private int addSkill(CommandSourceStack source, String skillId, int level) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();

            if (!SkillUtil.getSkillIds().contains(skillId)) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
            } else {
                SkillUtil.updateSkill(serverPlayer, skillId, level);
                source.sendSystemMessage(Component.literal("Skill added"));
                return 1;
            }
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int getSkills(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            var map = SkillUtil.getMap(serverPlayer);

            for (Map.Entry<String, SkillWrapper> entry : map.entrySet()) {
                source.sendSystemMessage(Component.literal(entry.getKey() + " : level " + entry.getValue().getSkillLevel()));
            }
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int clearSkill(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer player = source.getPlayerOrException();
            player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(PlayerSkills::clearSkills);
            source.sendSystemMessage(Component.literal("Skills cleared"));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}
