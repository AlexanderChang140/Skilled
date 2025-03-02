package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.cat.skilled.Skilled;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.AddSkillLevelC2SPacket;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillData;
import me.cat.skilled.capability.manager.PlayerLevelManager;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class SkillScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".skill_screen");
    private static final ResourceLocation WINDOW_BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/window_background.png");
    private static final ResourceLocation SCREEN_COMPONENTS = new ResourceLocation(Skilled.MODID, "textures/gui/screen_components.png");

    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 150;

    private static final double MAX_DRAG_X = 100;
    private static final double MAX_DRAG_Y = 100;

    private final Map<String, SkillButton> skillButtons = new HashMap<>();

    private final long startTime;

    private int centerX;
    private int centerY;
    private int scale;

    private double currDragX = 0;
    private double currDragY = 0;
    private boolean canDrag = false;

    public SkillScreen() {
        super(TITLE);
        this.startTime = Minecraft.getInstance().level.getGameTime();
    }

    @Override
    protected void init() {
        super.init();
        scale = 1;
        centerX = width / 2;
        centerY = height / 2 ;

        skillButtons.clear();
        addSkillButtons();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        renderWindow(pGuiGraphics);
        renderInside(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderButtonToolTips(pGuiGraphics, pMouseX, pMouseY);
    }



    private void renderWindow(GuiGraphics guiGraphics) {
        int x = centerX - WINDOW_WIDTH / 2;
        int y = centerY - WINDOW_HEIGHT / 2;
        int sliceSize = 9;

        guiGraphics.blit(
                WINDOW_BACKGROUND,
                x, y,
                (int) -currDragX,  (int) -currDragY,
                WINDOW_WIDTH, WINDOW_HEIGHT
        );

        guiGraphics.blitNineSliced(
                SCREEN_COMPONENTS,
                x - sliceSize, y - sliceSize,
                WINDOW_WIDTH + sliceSize * 2, WINDOW_HEIGHT + sliceSize * 2,
                sliceSize,
                27, 27,
                1, 1
        );
    }

    private void renderInside(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.enableScissor(centerX - WINDOW_WIDTH / 2, centerY - WINDOW_HEIGHT / 2, centerX + WINDOW_WIDTH / 2, centerY + WINDOW_HEIGHT / 2);
        dragButtons();
        drawConnections();
        renderWidgets(guiGraphics, mouseX, mouseY, partialTick);
        renderPlayerLevel(guiGraphics);
        guiGraphics.disableScissor();
    }

    public void renderWidgets(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    public void addSkillButtons() {
        Player player = getMinecraft().player;
        String categoryId = PlayerSkillManager.getCategoryId(player);
        var validSkills = SkillRegistry.getSkills().stream()
                .filter(entry -> Objects.equals(entry.getValue().getCategoryId(), categoryId))
                .collect(Collectors.toSet());
        for (Map.Entry<String, SkillData> entry : validSkills) {
            SkillData skillData = entry.getValue();
            int offset = skillData.getSize() / 2;
            SkillButton skillButton = addRenderableWidget(new SkillButton(
                    skillData, (btn) -> onSkillButtonClick(btn, skillData),
                    this::inWindow,
                    centerX + skillData.getX() * scale - offset,
                    centerY + skillData.getY() * scale - offset,
                    scale
            ));
            skillButtons.put(skillData.getSkillId(), skillButton);
        }
    }

    private void renderButtonToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (SkillButton skillButton : skillButtons.values()) {
            skillButton.renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void onSkillButtonClick(Button button, SkillData skillData) {
        Messenger.sendToServer(new AddSkillLevelC2SPacket(skillData));
    }

    private void dragButtons() {
        for (SkillButton skillButton : skillButtons.values()) {
            skillButton.setX(skillButton.getBaseX() + (int) currDragX);
            skillButton.setY(skillButton.getBaseY() + (int) currDragY);
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        canDrag = inWindow(pMouseX, pMouseY);
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (button == 0 && canDrag) {
            currDragX = Mth.clamp(currDragX + dragX, -MAX_DRAG_X, MAX_DRAG_X);
            currDragY = Mth.clamp(currDragY + dragY, -MAX_DRAG_Y, MAX_DRAG_Y);
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    private void drawConnections() {
        int connectionSize = scale;
        for (var skillButton : skillButtons.values()) {
            SkillData skillData = skillButton.getSkillData();
            int skillOffset = skillButton.getWidth() / 2;
            for (String prereqId: skillData.getPrerequisites()) {
                SkillButton prereqButton = skillButtons.get(SkillRegistry.getSkillData(prereqId).getSkillId());
                int preqreqOffset = prereqButton.getWidth() / 2;
                int x1 = skillButton.getX() + skillOffset;
                int y1 = skillButton.getY() + skillOffset;
                int x2 = prereqButton.getX() + preqreqOffset;
                int y2 = prereqButton.getY() + preqreqOffset;
                drawLine(x1, y1, x2, y2, connectionSize);
            }
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
        int y = centerY - 40 * scale;

        guiGraphics.drawCenteredString(minecraft.font, "Level: " + PlayerLevelManager.getPlayerLevel(minecraft.player), x, y, Color.WHITE.getRGB());
        guiGraphics.drawCenteredString(minecraft.font, "Skill Points: " + PlayerLevelManager.getSkillPoints(minecraft.player), x, y - 15, Color.WHITE.getRGB());
    }

    private boolean inWindow(double x, double y) {
        // Account for rounding error by adding 1
        return x + 1 > centerX - (double) WINDOW_WIDTH / 2 && x < centerX + (double) WINDOW_WIDTH / 2 && y + 1 > centerY - (double) WINDOW_HEIGHT / 2 && y < centerY + (double) WINDOW_HEIGHT / 2;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}