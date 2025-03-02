package me.cat.skilled.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import me.cat.skilled.Skilled;
import me.cat.skilled.skill.category.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;

import java.awt.Color;

public class CategoryButton extends Button {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/skill_button_frame.png");

    private static final int BUTTON_WIDTH = 16;
    private static final int BUTTON_HEIGHT = 16;

    private final Category category;

    public CategoryButton(int pX, int pY, OnPress pOnPress, Category category) {
        super(pX, pY, BUTTON_WIDTH, BUTTON_HEIGHT, Component.empty(), pOnPress, Button.DEFAULT_NARRATION);
        this.category = category;
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(
                FRAME, getX(), getY(), 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        pGuiGraphics.blit(
                category.getIcon(), getX(), getY(), 0, 0, BUTTON_WIDTH, BUTTON_HEIGHT, BUTTON_WIDTH, BUTTON_HEIGHT);
        pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void renderToolTip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        if (!isMouseOver(mouseX, mouseY) || !visible) {
            return;
        }

        PoseStack poseStack = guiGraphics.pose();
        float scale = 1.0f;
        int offsetX = 5;
        int offsetY = 5;
        int x = (int) ((mouseX + offsetX) / scale);
        int y = (int) ((mouseY + offsetY) / scale);
        int width = 100;

        poseStack.pushPose();
        poseStack.scale(scale, scale, 1);
        guiGraphics.drawWordWrap(
                Minecraft.getInstance().font,
                FormattedText.of(category.getTitle()),
                x,
                y,
                width,
                Color.GRAY.hashCode());
        poseStack.popPose();
    }
}
