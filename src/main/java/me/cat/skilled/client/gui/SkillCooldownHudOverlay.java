package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import me.cat.skilled.Skilled;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.awt.*;

public class SkillCooldownHudOverlay {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/frame.png");

    public static final IGuiOverlay HUD_SKILL_COOLDOWN = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) ->  {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        int size = 12;
        int startX = (int) (screenWidth * 0.9);
        int startY = (int) (screenHeight * 0.9);
        int xOffset = size + 15;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        var skillSlots = SkillSlot.values();
        for (int i = 0; i < skillSlots.length - 1; i++) {
            SkillSlot skillSlot = skillSlots[i];
            String activeSkillId = SkillUtil.getActiveSkillId(localPlayer, skillSlot);
            if (activeSkillId != null) {
                // Skill icon render
                SkillData skillData = SkillRegistry.getSkillData(activeSkillId);
                ActiveSkill activeSkill = SkillUtil.getActiveSkillInstance(localPlayer, skillSlot);
                int x = startX - (skillSlots.length - 1 - i) * xOffset;

                guiGraphics.blit(FRAME, x, startY, 0, 0, size, size, size, size);
                guiGraphics.blit(skillData.getIcon(), x, startY, 0, 0, size, size, size, size);

                // Cooldown render
                float cooldown = (float) activeSkill.getCurrTick() / activeSkill.getMaxTick();
                int maxY = cooldown == 0 ? startY + size: startY + (int) (size * (cooldown));

                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                guiGraphics.setColor(1, 1, 1, 0.5f);
                guiGraphics.fill(x, startY + size, x + size, maxY, Color.BLACK.getRGB());
                guiGraphics.setColor(1, 1, 1, 1f);
                RenderSystem.disableBlend();
            }
        }
    });
}
