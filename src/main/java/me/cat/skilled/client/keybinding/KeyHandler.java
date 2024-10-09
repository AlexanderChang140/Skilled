package me.cat.skilled.client.keybinding;

import me.cat.skilled.Skilled;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.ActivatePrimarySkillC2SPacket;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skilled.MODID, value = Dist.CLIENT)
public class KeyHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.PRIMARY_ABILITY_KEY.consumeClick()) {
            Messenger.sendToServer(new ActivatePrimarySkillC2SPacket());
        }
    }
}
