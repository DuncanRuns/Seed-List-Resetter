package me.duncanruns.seedlistresetter.mixin;

import me.duncanruns.seedlistresetter.SeedListResetter;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void initMixin(CallbackInfo info) {
        if (SeedListResetter.isPlaying) {
            this.addButton(new ButtonWidget(0, this.height - 20, 100, 20, new LiteralText("Stop Seed List"), (buttonWidget) -> {
                SeedListResetter.isPlaying = false;
                buttonWidget.active = false;
                buttonWidget.visible = false;
            }));
        }
    }
}
