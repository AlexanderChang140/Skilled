package me.cat.skilled.client.gui;

import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.in.SetCategoryC2S;
import me.cat.skilled.registry.CategoryRegistry;
import me.cat.skilled.category.Category;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryScreen extends WindowScreen {
    private static final Component TITLE = Component.translatable("gui." + Skilled.MODID + ".category_screen");
    private static final ResourceLocation WINDOW_BACKGROUND = new ResourceLocation(Skilled.MODID, "textures/gui/window_background.png");

    private final List<CategoryButton> categoryButtons = new ArrayList<>();

    public CategoryScreen() {
        super(TITLE, 250, 150, 0, 0, WINDOW_BACKGROUND);
    }

    @Override
    protected void init() {
        super.init();
        categoryButtons.clear();
        addCategoryButtons();
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderButtonToolTips(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderInWindow(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderWidgets(guiGraphics, mouseX, mouseY, partialTick);
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

    private void renderButtonToolTips(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        for (CategoryButton categoryButton : categoryButtons) {
            categoryButton.renderToolTip(guiGraphics, mouseX, mouseY);
        }
    }

    private void onCategoryButtonClicked(Button button, Category category) {
        setButtonsVisibility(false);
        NodeManager.clientSetCategoryId(minecraft.player, category.getId());
        Messenger.sendToServer(new SetCategoryC2S(category));
    }

    private void setButtonsVisibility(boolean isVisible) {
        for (Button button : categoryButtons) {
            button.visible = isVisible;
        }
    }
}
