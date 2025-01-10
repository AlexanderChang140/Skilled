package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.ISkillCap;
import me.cat.skilled.capability.SkillProvider;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;

import static net.minecraft.commands.Commands.literal;

public class SkillCommand {
    private static final SuggestionProvider<CommandSourceStack> sugg = (ctx, builder) -> SharedSuggestionProvider.suggest(SkillRegistry.getSkillIds(), builder);

    public SkillCommand(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(literal("skilled")
                .then(literal("skill")
                        .then(literal("get")
                                .executes(command -> getSkills(command.getSource())))
                        .then(literal("get_active")
                                .executes(command -> getActive(command.getSource())))
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

            if (!SkillRegistry.getSkillIds().contains(skillId)) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
            } else {
                SkillUtil.updateSkill(serverPlayer, skillId, level);
                SkillUtil.syncSkillCap(serverPlayer);
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
            var entrySet = SkillUtil.getSkillMap(serverPlayer).entrySet();

            if (entrySet.isEmpty()) {
                source.sendSystemMessage(Component.literal("No skills found"));
            }
            else {
                for (Map.Entry<String, Skill> entry : entrySet) {
                    String skillId = entry.getKey();
                    int level = entry.getValue().getLevel();

                    source.sendSystemMessage(Component.literal(skillId + " : level " + level));
                }
            }
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int getActive(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            var entrySet = SkillUtil.getActiveSkillMap(serverPlayer).entrySet();

            if (entrySet.isEmpty()) {
                source.sendSystemMessage(Component.literal("No skills found"));
            }
            else {
                for (Map.Entry<SkillSlot, String> entry : entrySet) {
                    String skillSlot = entry.getKey().toString();
                    String skillId = entry.getValue();

                    source.sendSystemMessage(Component.literal(skillSlot + " : " + skillId));
                }
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
            ServerPlayer serverPlayer = source.getPlayerOrException();
            serverPlayer.getCapability(SkillProvider.SKILLS).ifPresent(ISkillCap::clearSkills);
            SkillUtil.syncSkillCap(serverPlayer);
            source.sendSystemMessage(Component.literal("Skills cleared"));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}
