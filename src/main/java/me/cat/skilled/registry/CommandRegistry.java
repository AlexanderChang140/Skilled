package me.cat.skilled.registry;

import me.cat.skilled.command.AdminCommand;
import me.cat.skilled.command.ClassCommand;
import me.cat.skilled.command.ExperienceCommand;
import me.cat.skilled.command.SkillCommand;
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

        ConfigCommand.register(event.getDispatcher());
    }
}
