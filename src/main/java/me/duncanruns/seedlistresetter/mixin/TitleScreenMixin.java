package me.duncanruns.seedlistresetter.mixin;

import me.duncanruns.seedlistresetter.NoSeedsLeftScreen;
import me.duncanruns.seedlistresetter.SeedListResetter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private static final Identifier ICON = new Identifier("textures/item/book.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "initWidgetsNormal", at = @At("TAIL"))
    private void initMixin(int y, int spacingY, CallbackInfo ci) {
        addButton(new ButtonWidget(width / 2 - 124, y, 20, 20, "", button -> {
            check();
        }));

        if (SeedListResetter.isPlaying) {
            check();
        }
    }

    private void check() {
        if (!SeedListResetter.loopPrevent) {
            SeedListResetter.loopPrevent = true;
            if (!SeedListResetter.nextSeed(this)) {
                assert minecraft != null;
                SeedListResetter.isPlaying = false;
                minecraft.openScreen(new NoSeedsLeftScreen());
            }
            SeedListResetter.loopPrevent = false;
        }
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void goldBootsOverlayMixin(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        assert this.minecraft != null;
        int y = this.height / 4 + 48;
        minecraft.getTextureManager().bindTexture(ICON);
        blit((width / 2) - 122, y + 2, 0.0F, 0.0F, 16, 16, 16, 16);
    }
}
