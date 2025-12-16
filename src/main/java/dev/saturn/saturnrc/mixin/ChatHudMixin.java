package dev.saturn.saturnrc.mixin;

import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin {

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V",
        at = @At("HEAD"),
        cancellable = true)
    private void onAddMessage(Text message, CallbackInfo ci) {
        RCModule rcModule = Modules.get().get(RCModule.class);

        if (rcModule != null && rcModule.isActive() && rcModule.normalMessages.get()) {
            if (message.getString().contains("»")) {
                ci.cancel();
            }
        }
    }
}
