package me.cat.skilled.client.keybinding;

import me.cat.skilled.Skilled;
import me.cat.skilled.client.gui.CategoryScreen;
import me.cat.skilled.client.gui.SkillScreen;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.ActivateActiveSkillC2SPacket;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skilled.MODID, value = Dist.CLIENT)
public class KeyHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.PRIMARY_ABILITY_KEY.consumeClick()) {
            activateSkill(SkillSlot.PRIMARY);
        }

        if (KeyBinding.SECONDARY_ABILITY_KEY.consumeClick()) {
            activateSkill(SkillSlot.SECONDARY);
        }

        if (KeyBinding.OPEN_SKILLS_KEY.consumeClick()) {
            Minecraft instance = Minecraft.getInstance();
            if (instance.screen instanceof SkillScreen || instance.screen instanceof CategoryScreen) {
                instance.setScreen(null);
            }
            else if (SkillUtil.getCategoryId(instance.player).isEmpty()) {
                instance.setScreen(new CategoryScreen());
            }
            else {
                instance.setScreen(new SkillScreen());
            }
        }
    }

    private static void activateSkill(SkillSlot skillSlot) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        ActiveSkill activeSkill = SkillUtil.getActiveSkillInstance(localPlayer, skillSlot);
        if (activeSkill != null) {
            activeSkill.activateSkill(localPlayer);
        }
        Messenger.sendToServer(new ActivateActiveSkillC2SPacket(skillSlot));
    }
}
