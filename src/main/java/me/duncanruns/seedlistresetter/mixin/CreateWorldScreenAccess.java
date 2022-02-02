package me.duncanruns.seedlistresetter.mixin;

import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(CreateWorldScreen.class)
public interface CreateWorldScreenAccess {
    @Accessor
    TextFieldWidget getLevelNameField();

    @Invoker("createLevel")
    public void invokeCreateLevel();

    @Accessor
    public TextFieldWidget getSeedField();
}
