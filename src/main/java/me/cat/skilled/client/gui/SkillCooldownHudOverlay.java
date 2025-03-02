package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.cat.skilled.Skilled;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillSlot;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SkillCooldownHudOverlay {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/skill_button_frame.png");

    public static final IGuiOverlay HUD_SKILL_COOLDOWN = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) ->  {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        int size = 12;
        int startX = (int) (screenWidth * 0.9);
        int startY = (int) (screenHeight * 0.9);
        int xOffset = size + 15;

        var skillSlots = SkillSlot.values();
        for (int i = 0; i < skillSlots.length; i++) {
            SkillSlot skillSlot = skillSlots[i];
            String activeSkillId = PlayerSkillManager.getActiveSkillId(localPlayer, skillSlot);
            ActiveSkill activeSkill = PlayerSkillManager.getActiveSkillInstance(localPlayer, skillSlot);
            if (activeSkill != null) {
                // Skill icon render
                SkillData skillData = SkillRegistry.getSkillData(activeSkillId);
                int x = startX - (skillSlots.length - 1 - i) * xOffset;

                guiGraphics.blit(FRAME, x, startY, 0, 0, size, size, size, size);
                guiGraphics.blit(skillData.getIcon(), x, startY, 0, 0, size, size, size, size);

                // Cooldown render
                float curr = activeSkill.getCurrTick() + partialTick;
                float max = activeSkill.getMaxTick();
                float fillPercent = Mth.clamp(curr / max, 0, 1);
                int minY = startY + size;
                int maxY = activeSkill.showCooldown() ? startY + (int) (size * fillPercent) : minY;
                int cooldownColor = activeSkill.getCooldownColorValues().getRGB();

                RenderSystem.enableBlend();
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 0.5f);
                guiGraphics.fill(x, minY, x + size, maxY, cooldownColor);
                guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
            }
        }
    });
}
