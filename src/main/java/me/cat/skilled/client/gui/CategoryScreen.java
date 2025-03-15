package me.cat.skilled.client.gui;

import me.cat.skilled.Skilled;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.SetCategoryC2S;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.category.Category;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".category_screen");
    private static final ResourceLocation WINDOW_BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/window_background.png");
    private static final ResourceLocation SCREEN_COMPONENTS = new ResourceLocation(Skilled.MODID, "textures/gui/screen_components.png");

    private final List<CategoryButton> categoryButtons = new ArrayList<>();

    private int centerX;
    private int centerY;

    public CategoryScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();

        centerX = width / 2;
        centerY = height / 2;

        addCategoryButtons();
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        renderWindow(pGuiGraphics);
        renderWidgets(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderToolTips(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderWindow(GuiGraphics guiGraphics) {
        int windowWidth = 250;
        int windowHeight = 150;
        int x = centerX - windowWidth / 2;
        int y = centerY - windowHeight / 2;
        int sliceSize = 9;

        guiGraphics.blit(
                WINDOW_BACKGROUND,
                x, y,
                0, 0,
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

    public void renderWidgets(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for (Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    private void addCategoryButtons() {
        var categories = CategoryRegistry.getCategories();
        int count = categories.size();
        int spacing = 20;
        int startX = centerX - spacing * count / 2;
        int i = 0;

        for (Map.Entry<String, Category> entry : categories) {
            Category category = entry.getValue();
            CategoryButton categoryButton = addRenderableWidget(new CategoryButton(
                    startX + spacing * i,
                    centerY + 40,
                    (btn) -> onCategoryButtonClicked(btn, category),
                    category));
            categoryButtons.add(categoryButton);
            i++;
        }
    }

    private void renderToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (CategoryButton categoryButton : categoryButtons) {
            categoryButton.renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void onCategoryButtonClicked(Button button, Category category) {
        setButtonsVisibility(false);
        Messenger.sendToServer(new SetCategoryC2S(category));
    }

    private void setButtonsVisibility(boolean isVisible) {
        for (Button button : categoryButtons) {
            button.visible = isVisible;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
