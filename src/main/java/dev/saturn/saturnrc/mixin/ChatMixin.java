package dev.saturn.saturnrc.mixin;

import dev.saturn.saturnrc.modules.RCModule;
import dev.saturn.saturnrc.util.Utils;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

import static meteordevelopment.meteorclient.MeteorClient.mc;

@Mixin(ClientPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void onSendChatMessage(String message, CallbackInfo ci) throws IOException {
        if (Modules.get().get(RCModule.class).isActive()) {
            assert mc.player != null;
            try {
                Modules.get().get(RCModule.class).socketClient.sendMessage(mc.player.getName().getLiteralString(), message);
            } catch (IOException e) {
                Utils.chatMessage("Failed to send message, is the server online?", Utils.MessageType.ERROR);
                e.printStackTrace();
            }
            ci.cancel();
        }
    }
}
