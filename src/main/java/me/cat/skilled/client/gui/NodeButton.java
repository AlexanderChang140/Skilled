package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.category.node.Node;
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
import java.util.function.Function;

public class NodeButton extends Button {
    private static final ResourceLocation FRAME = new ResourceLocation(Skilled.MODID, "textures/gui/skill_button_frame.png");
    private static final int BASE_FRAME_OFFSET = 2;

    private final Node node;
    private final ResourceLocation nodeIcon;
    private final BiPredicate<Double, Double> inWindow;

    private final String nodeTitle;
    private final Function<Integer, String> nodeDesc;

    private final int baseX;
    private final int baseY;
    private final int frameOffset;
    private final long startTime;

    private enum NodeStatus {
        LOCKED,
        UNLOCKED,
        ACQUIRED,
        MAXED
    }

    public NodeButton(int pX, int pY, OnPress pOnPress, Node node, BiPredicate<Double, Double> inWindow) {
        super(pX, pY, node.getNodeView().size() + BASE_FRAME_OFFSET, node.getNodeView().size() + BASE_FRAME_OFFSET, Component.empty(), pOnPress, Button.DEFAULT_NARRATION);
        this.node = node;
        this.nodeIcon = node.getNodeView().icon();
        this.inWindow = inWindow;

        this.nodeTitle = node.getNodeView().title();
        this.nodeDesc = node.getNodeView().desc();

        this.baseX = getX();
        this.baseY = getY();
        this.frameOffset = BASE_FRAME_OFFSET;
        this.startTime = Minecraft.getInstance().level.getGameTime();
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Player player = Minecraft.getInstance().player;
        NodeStatus nodeStatus = getNodeStatus(player, node);
        long currTime = Minecraft.getInstance().level.getGameTime();
        float fade = (float) getAlphaFade(0.7, 0.8, 15, currTime % startTime);

        switch (nodeStatus) {
            case LOCKED -> pGuiGraphics.setColor(0.4f, 0.4f, 0.4f, 1.0f);
            case UNLOCKED -> pGuiGraphics.setColor(fade, fade, fade, 1.0f);
            case ACQUIRED -> pGuiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f);
            case MAXED -> pGuiGraphics.setColor(1.0f, 0.85f, 0f, 1.0f);
        }

        this.active = nodeStatus == NodeStatus.UNLOCKED || nodeStatus == NodeStatus.ACQUIRED;
        RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        RenderSystem.enableBlend();

        pGuiGraphics.blit(
                FRAME, getX(), getY(), 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        pGuiGraphics.blit(
                nodeIcon, getX() + frameOffset / 2, getY() + frameOffset / 2, 0, 0, getWidth() - frameOffset,  getHeight() - frameOffset, getWidth() - frameOffset, getHeight() - frameOffset);
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
        int nodeLevel = NodeManager.getNodeLevel(player, node.getNodeId());
        int nodeMaxLevel = node.getMaxLevel();
        String title = String.format("%s (%d/%d)",
                nodeTitle,
                nodeLevel,
                nodeMaxLevel
                );
        String desc = nodeDesc.apply(nodeLevel);

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

    private NodeStatus getNodeStatus(Player player, Node node) {
        int nodeLevel = NodeManager.getNodeLevel(player, node.getNodeId());

        if (nodeLevel >= node.getMaxLevel()) {
            return NodeStatus.MAXED;
        }
        else if (nodeLevel > 0) {
            return NodeStatus.ACQUIRED;
        }
        else if (NodeManager.canAcquireNode(player, node)) {
            return NodeStatus.UNLOCKED;
        }
        else {
            return NodeStatus.LOCKED;
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
}