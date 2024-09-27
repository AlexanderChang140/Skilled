package me.cat.skilled.command;

import static net.minecraft.commands.Commands.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.cat.skilled.capability.PlayerSkills;
import me.cat.skilled.capability.PlayerSkillsProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ClearSkillCommand {
    public ClearSkillCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled").then(literal("clear").executes((command) -> ClearSkill(command.getSource()))));
    }

    private int ClearSkill(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(PlayerSkills::clearMap);
        source.sendSystemMessage(Component.literal("Skills cleared"));
        return 1;
    }
}
