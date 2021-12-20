package me.duncanruns.seedlistresetter.mixin;

import me.duncanruns.seedlistresetter.NoSeedsLeftScreen;
import me.duncanruns.seedlistresetter.ReplaceListScreen;
import me.duncanruns.seedlistresetter.SeedListResetter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.List;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier ICON = new Identifier("textures/item/book.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgetsNormal", at = @At("TAIL"))
    private void initMixin(int y, int spacingY, CallbackInfo ci) {
        addButton(new ButtonWidget(width / 2 - 124, y, 20, 20, new LiteralText(""), button -> {
            check();
        }));

        if (SeedListResetter.isPlaying) {
            check();
        }
    }

    private void check() {
        if (!SeedListResetter.nextSeed(this)) {
            assert client != null;
            SeedListResetter.isPlaying = false;
            client.openScreen(new NoSeedsLeftScreen());
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlayMixin(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        assert this.client != null;
        int y = this.height / 4 + 48;
        this.client.getTextureManager().bindTexture(ICON);
        drawTexture(matrices, (width / 2) - 122, y + 2, 0.0F, 0.0F, 16, 16, 16, 16);

    }

    @Override
    public void method_29638(List<Path> list) {
        assert client != null;
        client.openScreen(new ReplaceListScreen(list.get(0)));
    }
}
