package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerLevelManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;

public class ExperienceCommand {
    public ExperienceCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled_debug")
                .requires(command -> command.hasPermission(4))
                .then(literal("experience")
                        .then(literal("get")
                                .executes(command -> getExperience(command.getSource())))
                        .then(literal("set")
                                .then(Commands.argument("experience", IntegerArgumentType.integer(0))
                                        .executes(command -> {
                                            int experience = IntegerArgumentType.getInteger(command,"experience");
                                            return setExperience(command.getSource(), experience);
                                        })
                                )
                        )
                        .then(literal("add")
                                .then(Commands.argument("experience", IntegerArgumentType.integer(0))
                                        .executes(command -> {
                                            int experience = IntegerArgumentType.getInteger(command,"experience");
                                            return addExperience(command.getSource(), experience);
                                        })
                                )
                        )
                )
        );
    }

    public int getExperience(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            source.sendSystemMessage(Component.literal("experience : " + PlayerLevelManager.getPlayerExperience(serverPlayer)));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    public int setExperience(CommandSourceStack source, int experience) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            PlayerLevelManager.updatePlayerExperience(serverPlayer, experience);
            source.sendSystemMessage(Component.literal("Set experience to " + experience));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    public int addExperience(CommandSourceStack source, int experience) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            PlayerLevelManager.addPlayerExperience(serverPlayer, experience);
            source.sendSystemMessage(Component.literal("Added " + experience + " experience"));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}
