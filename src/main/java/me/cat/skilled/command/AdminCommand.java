package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.PlayerLevelManager;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.literal;

public class AdminCommand {
    public AdminCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled")
                .requires(command -> command.hasPermission(2))
                .then(literal("reset_level")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetLevel(command.getSource(), EntityArgument.getEntity(command, "player")))
                        )
                )
                .then(literal("reset_skills")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetSkills(command.getSource(), EntityArgument.getEntity(command, "player"))))
                )
                .then(literal("reset_class")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetClass(command.getSource(), EntityArgument.getEntity(command, "player"))))
                )
                .then(literal("add_level")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                        .executes(command -> addLevel(command.getSource(), EntityArgument.getEntity(command, "player"), IntegerArgumentType.getInteger(command, "level")))
                                )
                        )
                )
                .then(literal("add_experience")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("experience", IntegerArgumentType.integer(1))
                                    .executes(command -> addExperience(command.getSource(), EntityArgument.getEntity(command, "player"), IntegerArgumentType.getInteger(command, "experience")))
                                )
                        )
                )
        );
    }

    private int resetLevel(CommandSourceStack source, Entity entity) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            resetSkills(source, serverPlayer);
            PlayerLevelManager.updatePlayerLevel(serverPlayer, 1);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int resetClass(CommandSourceStack source, Entity entity) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            PlayerSkillManager.clearAll(serverPlayer);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int resetSkills(CommandSourceStack source, Entity entity) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            PlayerSkillManager.clearSkills(serverPlayer);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int addLevel(CommandSourceStack source, Entity entity, int level) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            PlayerLevelManager.addPlayerLevel(serverPlayer, level);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int addExperience(CommandSourceStack source, Entity entity, int experience) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            PlayerLevelManager.addPlayerExperience(serverPlayer, experience);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}