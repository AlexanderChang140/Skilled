package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.cat.skilled.Skilled;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class SkillButton extends Button {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/frame.png");

    private static final int BUTTON_OFFSET = 2;

    private final SkillData skillData;

    public SkillButton(int pX, int pY, int size, OnPress pOnPress, SkillData skillData) {
        super(pX, pY, size + BUTTON_OFFSET, size + BUTTON_OFFSET, Component.empty(), pOnPress, Button.DEFAULT_NARRATION);
        this.skillData = skillData;
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Player player = Minecraft.getInstance().player;
        int skillLevel = SkillUtil.getSkillLevel(player, skillData.getSkillId());

        if (SkillUtil.getSkillLevel(player, skillData.getSkillId()) == 0) {
            pGuiGraphics.setColor(0.5f, 0.5f, 0.5f, 1.0f);
        }

        if (SkillUtil.getSkillLevel(player, skillData.getSkillId()) == skillData.getMaxLevel()) {
            pGuiGraphics.setColor(1.0f, 0.85f, 0f, 1.0f);
        }

        this.active = skillLevel < SkillUtil.getSkillMaxLevel(skillData.getSkillId()) && skillData.isUnlocked(player);
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        pGuiGraphics.blit(
                FRAME, getX() - BUTTON_OFFSET / 2, getY() - BUTTON_OFFSET / 2, 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        pGuiGraphics.blit(
                skillData.getIcon(), getX(), getY(), 0, 0, skillData.getSize(), skillData.getSize(), skillData.getSize(), skillData.getSize());
        pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!isMouseOver(mouseX, mouseY) || !visible) {
            return;
        }

        PoseStack poseStack = guiGraphics.pose();
        float scale = 0.7f;
        int offsetX = 5;
        int offsetY = 5;
        int x = (int) ((mouseX + offsetX) / scale);
        int y = (int) ((mouseY + offsetY) / scale);
        int width = 100;

        Player player = Minecraft.getInstance().player;
        Font font = Minecraft.getInstance().font;
        int skillLevel = SkillUtil.getSkillLevel(player, skillData.getSkillId());
        String title = String.format("%s (%d/%d)",
                skillData.getTitle(),
                skillLevel,
                skillData.getMaxLevel()
                );
        String desc = skillData.getDesc(skillLevel);

        List<Component> list = new ArrayList<>();
        list.add(Component.literal(title));
        list.add(Component.literal(desc));
        List<FormattedCharSequence> wrappedLines = list.stream()
                .flatMap(component -> font.split(component, width).stream())
                .toList();

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);
        guiGraphics.renderTooltip(font, wrappedLines, x, y);
        poseStack.popPose();
    }

    @Override
    public boolean isMouseOver(double x, double y) {
        return x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight();
    }
}