package me.cat.skilled.client.gui;

import me.cat.skilled.Skilled;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.SetCategoryC2S;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.skill.category.Category;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryScreen extends Screen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".category_screen");
    private static final ResourceLocation BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/space.png");

    private final List<Button> categoryButtons = new ArrayList<>();

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
        int backgroundWidth = 250;
        int backgroundHeight = 150;
        int x = centerX - (int) (backgroundWidth * 0.5);
        int y = centerY - (int) (backgroundHeight * 0.5);

        pGuiGraphics.blit(BACKGROUND, x, y, 0, 0, backgroundWidth, backgroundHeight);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public void addCategoryButtons() {
        var categories = CategoryRegistry.getCategories();
        int count = categories.size();
        int spacing = 20;
        int startX = centerX - spacing * count / 2;
        int i = 0;

        for (Map.Entry<String, Category> entry : categories) {
            Category category = entry.getValue();
            Button button = addRenderableWidget(new CategoryButton(
                    startX + spacing * i,
                    centerY + 40,
                    (btn) -> onCategoryButtonClicked(btn, category),
                    category));
            categoryButtons.add(button);
            i++;
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
