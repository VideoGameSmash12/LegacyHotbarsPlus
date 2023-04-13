package me.videogamesm12.hotbarsplus.core.mixin;

import me.videogamesm12.hotbarsplus.core.HBPCore;
import me.videogamesm12.hotbarsplus.core.gui.CustomButtons;
import net.minecraft.class_3251;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeInventoryScreen.class)
public class CreativeInventoryScreenMixin
{
    @Redirect(method = "setSelectedTab", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;field_15872:Lnet/minecraft/class_3251;", opcode = Opcodes.GETFIELD))
    public class_3251 redirectHotbarSelection(MinecraftClient instance)
    {
        return HBPCore.UPL.getHotbarPage();
    }

    @Redirect(method = "method_14550", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;field_15872:Lnet/minecraft/class_3251;", opcode = Opcodes.GETFIELD))
    private static class_3251 redirectShit(MinecraftClient instance)
    {
        return HBPCore.UPL.getHotbarPage();
    }

    @Inject(method = "buttonClicked", at = @At("HEAD"), cancellable = true)
    public void injectButtonClicked(ButtonWidget button, CallbackInfo ci)
    {
        if (button instanceof CustomButtons.NextButton)
        {
            HBPCore.UPL.incrementPage();
            ci.cancel();
        }
        else if (button instanceof CustomButtons.BackupButton)
        {
            HBPCore.UBL.backupHotbar();
            ci.cancel();
        }
        else if (button instanceof CustomButtons.PreviousButton)
        {
            HBPCore.UPL.decrementPage();
            ci.cancel();
        }
    }
}
