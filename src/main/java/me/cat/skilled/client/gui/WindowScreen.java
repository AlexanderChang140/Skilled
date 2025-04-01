package me.cat.skilled.client.gui;

import me.cat.skilled.Skilled;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class WindowScreen extends Screen {
    private final static ResourceLocation SCREEN_COMPONENTS = new ResourceLocation(Skilled.MODID, "textures/gui/screen_components.png");

    private final ResourceLocation background;
    protected final int windowWidth;
    protected final int windowHeight;
    protected final double maxDragX;
    protected final double maxDragY;

    protected int centerX;
    protected int centerY;

    protected double currDragX = 0;
    protected double currDragY = 0;
    protected boolean canDrag = false;

    protected WindowScreen(Component pTitle, int windowWidth, int windowHeight, double maxDragX, double maxDragY, ResourceLocation background) {
        super(pTitle);
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.maxDragX = maxDragX;
        this.maxDragY = maxDragY;
        this.background = background;
    }

    @Override
    protected void init() {
        super.init();
        centerX = width / 2;
        centerY = height / 2 ;
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        renderWindow(pGuiGraphics);
        pGuiGraphics.enableScissor(centerX - windowWidth / 2, centerY - windowHeight / 2, centerX + windowWidth / 2, centerY + windowHeight / 2);
        renderInWindow(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        pGuiGraphics.disableScissor();
    }

    protected void renderWindow(GuiGraphics guiGraphics) {
        int x = centerX - windowWidth / 2;
        int y = centerY - windowHeight / 2;
        int sliceSize = 9;

        guiGraphics.blit(
                background,
                x, y,
                (int) -currDragX,  (int) -currDragY,
                windowWidth, windowHeight
        );

        guiGraphics.blitNineSliced(
                SCREEN_COMPONENTS,
                x - sliceSize, y - sliceSize,
                windowWidth + sliceSize * 2, windowHeight + sliceSize * 2,
                sliceSize,
                27, 27,
                1, 1
        );
    }

    protected void renderInWindow(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        canDrag = inWindow(pMouseX, pMouseY);
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && canDrag) {
            currDragX = Mth.clamp(currDragX + dragX, -maxDragX, maxDragX);
            currDragY = Mth.clamp(currDragY + dragY, -maxDragY, maxDragY);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    protected boolean inWindow(double x, double y) {
        // Account for rounding error by adding 1
        return x + 1 > centerX - (double) windowWidth / 2 && x < centerX + (double) windowWidth / 2 && y + 1 > centerY - (double) windowHeight / 2 && y < centerY + (double) windowHeight / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
