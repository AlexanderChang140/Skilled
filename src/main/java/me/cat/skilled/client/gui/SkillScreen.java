package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.category.Category;
import me.cat.skilled.category.node.Connection;
import me.cat.skilled.category.node.Node;
import me.cat.skilled.category.node.NodeView;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.RequestLevelNodeC2S;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.capability.manager.PlayerLevelManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SkillScreen extends WindowScreen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".skill_screen");
    private static final ResourceLocation WINDOW_BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/window_background.png");

    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 150;

    private static final double MAX_DRAG_X = 100;
    private static final double MAX_DRAG_Y = 100;

    private static final int CONNECTION_SIZE = 1;

    private final Map<String, NodeButton> nodeButtons = new HashMap<>();
    private Category category;

    public SkillScreen() {
        super(TITLE, WINDOW_WIDTH, WINDOW_HEIGHT, MAX_DRAG_X, MAX_DRAG_Y, WINDOW_BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        nodeButtons.clear();
        category = CategoryRegistry.getCategory(NodeManager.getCategoryId(minecraft.player));
        addButtons();
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderButtonToolTips(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderInWindow(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        dragButtons();
        drawConnections();
        renderWidgets(guiGraphics, mouseX, mouseY, partialTick);
        renderPlayerLevel(guiGraphics);
    }

    public void renderWidgets(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    public void addButtons() {
        if (category == null) {
            return;
        }

        for (var node : category.getNodes()) {
            NodeView nodeView = node.getNodeView();
            int offset = nodeView.size() / 2;
            NodeButton nodeButton = addRenderableWidget(new NodeButton(
                    centerX + nodeView.x() - offset,
                    centerY + nodeView.y()- offset,
                    (btn) -> onNodeButtonClick(node),
                    node,
                    this::inWindow
            ));
            nodeButtons.put(node.getNodeId(), nodeButton);
        }
    }

    private void renderButtonToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (NodeButton nodeButton : nodeButtons.values()) {
            nodeButton.renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void onNodeButtonClick(Node node) {
        int nodeLevel = NodeManager.getNodeLevel(minecraft.player, node.getNodeId());
        Messenger.sendToServer(new RequestLevelNodeC2S(node, nodeLevel));
    }

    private void dragButtons() {
        for (NodeButton nodeButton : nodeButtons.values()) {
            nodeButton.setX(nodeButton.getBaseX() + (int) currDragX);
            nodeButton.setY(nodeButton.getBaseY() + (int) currDragY);
        }
    }

    private void drawConnections() {
        if (category == null) {
            return;
        }

        var connectionMap = category.getNodeToConnections();
        HashSet<String> visited = new HashSet<>();
        for (var entry : connectionMap) {
            String nodeId = entry.getKey();
            NodeButton nodeButton = nodeButtons.get(nodeId);
            List<Connection> connections = entry.getValue();
            int skillOffset = 8;
            for (Connection connection : connections) {
                String connectedNodeId = connection.connectedNodeId();
                if (!visited.contains(connectedNodeId)) {
                    NodeButton prereqButton = nodeButtons.get(connectedNodeId);
                    int preqreqOffset = prereqButton.getWidth() / 2;
                    int x1 = nodeButton.getX() + skillOffset;
                    int y1 = nodeButton.getY() + skillOffset;
                    int x2 = prereqButton.getX() + preqreqOffset;
                    int y2 = prereqButton.getY() + preqreqOffset;
                    drawLine(x1, y1, x2, y2, CONNECTION_SIZE);
                }
            }
            visited.add(nodeId);
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2, int width) {
        /*
        PoseStack poseStack = pGuiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(centerX, centerY, 0);
        poseStack.mulPose(new Quaternionf().rotationAxis((float) Math.toRadians(0), 0, 0, 1));
        poseStack.translate(-centerX, -centerY, 0);
        poseStack.popPose();
         */

        Vec2 perp = new Vec2(-y2 + y1, x2 - x1).normalized().scale(width / 2.0f);

        RenderSystem.setShader(GameRenderer::getPositionShader);
        RenderSystem.setShaderColor(1f, 1, 0.8f, 1);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        bufferBuilder.vertex(x1 - perp.x, y1 - perp.y, 0).endVertex();
        bufferBuilder.vertex(x1 + perp.x, y1 + perp.y, 0).endVertex();
        bufferBuilder.vertex(x2 + perp.x, y2 + perp.y, 0).endVertex();
        bufferBuilder.vertex(x2 - perp.x, y2 - perp.y, 0).endVertex();

        tesselator.end();
        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    private void renderPlayerLevel(GuiGraphics guiGraphics) {
        int x = centerX;
        int y = centerY - 40;

        guiGraphics.drawCenteredString(minecraft.font, "Level: " + PlayerLevelManager.getPlayerLevel(minecraft.player), x, y, Color.WHITE.getRGB());
        guiGraphics.drawCenteredString(minecraft.font, "Skill Points: " + PlayerLevelManager.getSkillPoints(minecraft.player), x, y - 15, Color.WHITE.getRGB());
    }
}