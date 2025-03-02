package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.cat.skilled.Skilled;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.capability.manager.PlayerSkillManager;
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
import java.util.function.BiPredicate;

public class SkillButton extends Button {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/skill_button_frame.png");
    private static final int BASE_FRAME_OFFSET = 2;

    private final SkillData skillData;

    private final BiPredicate<Double, Double> inWindow;

    private final int baseX;
    private final int baseY;
    private final int frameOffset;
    private final long startTime;

    private enum SkillStatus {
        LOCKED,
        UNLOCKED,
        ACQUIRED,
        MAXED
    }

    public SkillButton(SkillData skillData, OnPress pOnPress, BiPredicate<Double, Double> inWindow, int pX, int pY, int scale) {
        super(pX, pY, (skillData.getSize() + BASE_FRAME_OFFSET) * scale, (skillData.getSize() + BASE_FRAME_OFFSET) * scale, Component.empty(), pOnPress, Button.DEFAULT_NARRATION);
        this.skillData = skillData;
        this.inWindow = inWindow;
        this.baseX = getX();
        this.baseY = getY();
        this.frameOffset = BASE_FRAME_OFFSET * scale;
        this.startTime = Minecraft.getInstance().level.getGameTime();
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Player player = Minecraft.getInstance().player;
        SkillStatus skillStatus = getSkillStatus(player, skillData);
        long currTime = Minecraft.getInstance().level.getGameTime();
        float fade = (float) getAlphaFade(0.7, 0.8, 15, currTime % startTime);

        switch (skillStatus) {
            case LOCKED -> pGuiGraphics.setColor(0.4f, 0.4f, 0.4f, 1.0f);
            case UNLOCKED -> pGuiGraphics.setColor(fade, fade, fade, 1.0f);
            case ACQUIRED -> pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            case MAXED -> pGuiGraphics.setColor(1.0f, 0.85f, 0f, 1.0f);
        }

        this.active = skillStatus == SkillStatus.UNLOCKED || skillStatus == SkillStatus.ACQUIRED;
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        RenderSystem.enableBlend();

        pGuiGraphics.blit(
                FRAME, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        pGuiGraphics.blit(
                skillData.getIcon(), getX() + frameOffset / 2, getY() + frameOffset / 2, 0, 0, getWidth() - frameOffset,  getHeight() - frameOffset, getWidth() - frameOffset, getHeight() - frameOffset);
        pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);

        RenderSystem.disableBlend();
    }

    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!isMouseOver(mouseX, mouseY) || !visible) {
            return;
        }

        float scale = 1f;
        int offsetX = 5;
        int offsetY = 5;
        int x = (int) ((mouseX + offsetX) / scale);
        int y = (int) ((mouseY + offsetY) / scale);
        int width = 100;

        Player player = Minecraft.getInstance().player;
        Font font = Minecraft.getInstance().font;
        int skillLevel = PlayerSkillManager.getSkillLevel(player, skillData.getSkillId());
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

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);
        guiGraphics.renderTooltip(font, wrappedLines, x, y);
        poseStack.popPose();
    }

    private SkillStatus getSkillStatus(Player player, SkillData skillData) {
        int skillLevel = PlayerSkillManager.getSkillLevel(player, skillData.getSkillId());
        if (skillLevel == skillData.getMaxLevel()) {
            return SkillStatus.MAXED;
        }
        else if (skillLevel > 0) {
            return SkillStatus.ACQUIRED;
        }
        else if (skillData.isUnlocked(player)) {
            return SkillStatus.UNLOCKED;
        }
        else {
            return SkillStatus.LOCKED;
        }
    }

    @Override
    public boolean isMouseOver(double x, double y) {
        return x > getX() && x < getX() + getWidth() && y > getY() && y < getY() + getHeight() && inWindow.test(x, y);
    }

    @Override
    protected boolean clicked(double pMouseX, double pMouseY) {
        return this.active && this.visible && pMouseX >= (double)this.getX() && pMouseY >= (double)this.getY() && pMouseX < (double)(this.getX() + this.width) && pMouseY < (double)(this.getY() + this.height) && inWindow.test(pMouseX, pMouseY);
    }

    public double getAlphaFade(double minVal, double maxVal, double period, double ticks) {
        return Math.abs((maxVal - minVal) * Math.sin(Math.PI / period * ticks) + minVal);
    }

    public int getBaseX() {
        return baseX;
    }

    public int getBaseY() {
        return baseY;
    }

    public SkillData getSkillData() {
        return skillData;
    }
}