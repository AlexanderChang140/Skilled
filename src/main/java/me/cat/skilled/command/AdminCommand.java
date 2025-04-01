package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.capability.manager.PlayerLevelManager;
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
                                .executes(command -> resetLevel(EntityArgument.getEntity(command, "player")))
                        )
                )
                .then(literal("reset_skills")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetSkills(EntityArgument.getEntity(command, "player"))))
                )
                .then(literal("reset_class")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetClass(EntityArgument.getEntity(command, "player"))))
                )
                .then(literal("add_level")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("level", IntegerArgumentType.integer(1))
                                        .executes(command -> addLevel(EntityArgument.getEntity(command, "player"), IntegerArgumentType.getInteger(command, "level")))
                                )
                        )
                )
                .then(literal("add_experience")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("experience", IntegerArgumentType.integer(1))
                                    .executes(command -> addExperience(EntityArgument.getEntity(command, "player"), IntegerArgumentType.getInteger(command, "experience")))
                                )
                        )
                )
        );
    }

    private int resetLevel(Entity entity) {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            resetSkills(serverPlayer);
            PlayerLevelManager.updatePlayerLevel(serverPlayer, 1);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int resetClass(Entity entity) {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            NodeManager.clearAll(serverPlayer);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int resetSkills(Entity entity) {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            NodeManager.clearNodes(serverPlayer);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int addLevel(Entity entity, int level) {
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

    private int addExperience(Entity entity, int experience) {
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