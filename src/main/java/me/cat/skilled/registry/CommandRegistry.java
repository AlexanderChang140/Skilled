package me.cat.skilled.registry;

import me.cat.skilled.command.*;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber
public class CommandRegistry {
    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        new SkillCommand(event.getDispatcher());
        new ClassCommand(event.getDispatcher());
        new ExperienceCommand(event.getDispatcher());
        new AdminCommand(event.getDispatcher());
        new SyncCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}
