package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.SyncManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.literal;

public class SyncCommand {
    public SyncCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled_debug")
                .requires(command -> command.hasPermission(2))
                .then(literal("sync")
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(command -> resetLevel(EntityArgument.getEntity(command, "player")))
                        )
                )
        );
    }

    private int resetLevel(Entity entity) {
        try {
            ServerPlayer serverPlayer = (ServerPlayer) entity;
            SyncManager.syncCaps(serverPlayer);
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}
