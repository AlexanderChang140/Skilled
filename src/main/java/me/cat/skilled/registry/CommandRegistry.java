package me.cat.skilled.registry;

import me.cat.skilled.command.ClearSkillCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegiser(RegisterCommandsEvent event) {
        new ClearSkillCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
