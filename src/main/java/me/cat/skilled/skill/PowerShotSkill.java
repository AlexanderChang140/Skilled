package me.cat.skilled.skill;

import me.cat.skilled.Skilled;
import me.cat.skilled.client.keybinding.KeyBinding;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.AddSkillEffectC2SPacket;
import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.TickTimer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

public class PowerShotSkill {
    private static final TickTimer timer = new TickTimer(200);
    private static boolean isAbilityReady = true;

    @Mod.EventBusSubscriber(modid = Skilled.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.PRIMARY_ABILITY_KEY.consumeClick()) {
                Messenger.sendToServer(new AddSkillEffectC2SPacket(SkillIds.POWER_SHOT, EffectRegistry.POWER_SHOT.get(), -1, 0, false, false));
            }
        }
    }

    @Mod.EventBusSubscriber
    public static class ServerForgeEvents {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            if (event.side == LogicalSide.SERVER && !isAbilityReady && timer.doTick()) {
                isAbilityReady = true;
            }
        }
    }
}
