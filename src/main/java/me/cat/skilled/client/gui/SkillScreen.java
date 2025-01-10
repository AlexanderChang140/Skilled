package me.cat.skilled.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import me.cat.skilled.Skilled;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.LevelSkillC2SPacket;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

import java.util.*;
import java.util.stream.Collectors;

public class SkillScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".skill_screen");
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/space.png");

    private final List<SkillButton> skillButtons = new ArrayList<>();

    private int centerX;
    private int centerY;

    public SkillScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();

        centerX = width / 2;
        centerY = height / 2;

        skillButtons.clear();
        addSkillButtons();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        int backgroundWidth = 250;
        int backgroundHeight = 150;
        int x = centerX - (int) (backgroundWidth * 0.5);
        int y = centerY - (int) (backgroundHeight * 0.5);

        pGuiGraphics.blit(BACKGROUND, x, y, 0, 0, backgroundWidth, backgroundHeight);
        drawConnections();
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderToolTips(pGuiGraphics, pMouseX, pMouseY);
        test(pGuiGraphics);
    }

    public void test(GuiGraphics guiGraphics) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.scale(0.5f, 0.5f, 1);
        guiGraphics.blit(new ResourceLocation(Skilled.MODID, "textures/skill/stealth.png"), centerX, centerY, 0, 0, 32, 32, 32, 32);
        poseStack.popPose();
    }

    public void addSkillButtons() {
        Player player = getMinecraft().player;
        String categoryId = SkillUtil.getCategoryId(player);
        var validSkills = SkillRegistry.getSkills().stream()
                .filter(entry -> Objects.equals(entry.getValue().getCategoryId(), categoryId))
                .collect(Collectors.toSet());

        for (Map.Entry<String, SkillData> entry : validSkills) {
            SkillData skillData = entry.getValue();
            int offset = skillData.getSize() / 2;
            SkillButton skillButton = addRenderableWidget(new SkillButton(
                    centerX + skillData.getX() - offset,
                    centerY + skillData.getY() - offset,
                    skillData.getSize(),
                    (btn) -> onSkillButtonClick(btn, skillData),
                    skillData));
            skillButtons.add(skillButton);
        }
    }

    private void drawConnections() {
        int connectionSize = 1;
        Player player = getMinecraft().player;
        String categoryId = SkillUtil.getCategoryId(player);
        var validSkills = SkillRegistry.getSkills().stream()
                .filter(entry -> Objects.equals(entry.getValue().getCategoryId(), categoryId))
                .collect(Collectors.toSet());

        for (Map.Entry<String, SkillData> entry : validSkills) {
            SkillData skillData = entry.getValue();
            for (String prereqId: skillData.getPrerequisites()) {
                SkillData prereq = SkillRegistry.getSkillData(prereqId);
                int x1 = centerX + skillData.getX();
                int y1 = centerY + skillData.getY();
                int x2 = centerX + prereq.getX();
                int y2 = centerY + prereq.getY();

                drawLine(x1, y1, x2, y2, connectionSize);
            }
        }
    }

    private void drawLine(int x1, int y1, int x2, int y2, int width) {
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

    private void renderToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (SkillButton skillButton : skillButtons) {
            skillButton.createToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void onSkillButtonClick(Button button, SkillData skillData) {
        Messenger.sendToServer(new LevelSkillC2SPacket(skillData));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}