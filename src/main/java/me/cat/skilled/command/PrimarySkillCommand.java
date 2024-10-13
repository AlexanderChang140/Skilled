package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillWrapper;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static net.minecraft.commands.Commands.literal;

public class PrimarySkillCommand {

    public PrimarySkillCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled")
                .then(literal("primary_skill")
                        .then(literal("get")
                                .executes(command -> getPrimarySkill(command.getSource())))
                        .then(literal("set")
                                .then(Commands.argument("skills", StringArgumentType.string())
                                        .suggests(PrimarySkillCommand::suggestSkills)
                                        .executes(command -> {
                                            String skill = StringArgumentType.getString(command, "skills");
                                            return setPrimarySkill(command.getSource(), skill);
                                        })
                                )
                        )
                )
        );
    }

    private int getPrimarySkill(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            String primarySkillId = SkillUtil.getPrimarySkillId(serverPlayer);

            source.sendSystemMessage(Component.literal(Objects.requireNonNullElse(primarySkillId, "No primary skill set")));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int setPrimarySkill(CommandSourceStack source, String skillId) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();

            if (!SkillUtil.hasSkill(serverPlayer, skillId) || !SkillUtil.isActiveSkill(serverPlayer, skillId)) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
            } else {
                SkillUtil.setPrimarySkillId(serverPlayer, skillId);
                SkillUtil.syncCapability(serverPlayer);
                source.sendSystemMessage(Component.literal("Primary skill set"));
                return 1;
            }
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private static CompletableFuture<Suggestions> suggestSkills(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        try {
            ServerPlayer serverPlayer = context.getSource().getPlayerOrException();
            var map = SkillUtil.getMap(serverPlayer);

            for (Map.Entry<String, SkillWrapper> entry : map.entrySet()) {
                String skillId = entry.getKey();
                if (SkillUtil.isActiveSkill(serverPlayer, skillId)) {
                    builder.suggest(skillId);
                }
            }
            return builder.buildFuture();
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            return builder.buildFuture();
        }
    }
}
