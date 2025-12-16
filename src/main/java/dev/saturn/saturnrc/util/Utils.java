package dev.saturn.saturnrc.util;

import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

import java.awt.*;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class Utils {
    public enum MessageType {
        MESSAGE,
        ERROR,
        WARNING
    }


    public static void message(String playerMessage, MessageType type) {
        String prefix = Modules.get().get(RCModule.class).prefix.get();
        TextColor prefixColor = Modules.get().get(RCModule.class).prefixColor.get().toTextColor();

        MutableText prefixText = Text.literal(prefix + " ")
            .styled(style -> style.withColor(prefixColor));

        MutableText messageText = Text.literal(playerMessage);

        switch(type) {
            case MESSAGE -> {
                messageText.styled(style -> style.withColor(Color.WHITE.getRGB()));
            } case ERROR -> {
                messageText.styled(style -> style.withColor(Color.RED.getRGB()));
            } case WARNING -> {
                messageText.styled(style -> style.withColor(Color.YELLOW.getRGB()));
            }
        }

        mc.player.sendMessage(prefixText.append(messageText), false);
    }
}
