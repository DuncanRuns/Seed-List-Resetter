package me.duncanruns.seedlistresetter;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;

public class NoSeedsLeftScreen extends Screen {
    private final String string;

    public NoSeedsLeftScreen() {
        super(new LiteralText("No seeds left."));
        string = "The seed list is empty (.minecraft/seeds.txt)";
    }

    @Override
    protected void init() {
        addButton(new ButtonWidget(width / 2 - 40, height / 2 + 20, 80, 20, "Exit", button -> {
            onClose();
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        renderBackground();
        drawCenteredString(minecraft.textRenderer, string, width / 2, height / 2 - 20, 16777215);
        super.render(mouseX, mouseY, delta);
    }
}
