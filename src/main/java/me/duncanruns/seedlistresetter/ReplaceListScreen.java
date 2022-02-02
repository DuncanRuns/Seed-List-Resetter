package me.duncanruns.seedlistresetter;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import org.apache.commons.io.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ReplaceListScreen extends Screen {
    private final String string;
    private final Path path;

    public ReplaceListScreen(Path path) {
        super(new LiteralText("Replace Seed"));
        this.string = "Replace the contents of seeds.txt with the contents of " + path.getFileName().toString() + "?";
        this.path = path;
    }


    @Override
    protected void init() {
        addButton(new ButtonWidget(width / 2 - 85, height / 2 + 20, 80, 20, "Yes", button -> {
            try {
                FileWriter fileWriter = new FileWriter("seeds.txt");
                fileWriter.write(FileUtils.readFileToString(path.toFile(), "utf-8"));
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            onClose();
        }));
        addButton(new ButtonWidget(width / 2 + 5, height / 2 + 20, 80, 20, "Cancel", button -> {
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
