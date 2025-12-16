package dev.saturn.saturnrc.util;

import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

import java.awt.*;

import static meteordevelopment.meteorclient.MeteorClient.mc;

public class Utils {
    public enum MessageType {
        MESSAGE,
        ERROR,
        WARNING
    }

    public static void chatMessage(String playerMessage, MessageType type) {
        if (playerMessage == null || playerMessage.isEmpty()) return;

        String prefix = Modules.get().get(RCModule.class).prefix.get();
        TextColor prefixColor = Modules.get().get(RCModule.class).prefixColor.get().toTextColor();
        TextColor messageColor = Modules.get().get(RCModule.class).messageColor.get().toTextColor();
        TextColor usernameColor = Modules.get().get(RCModule.class).usernameColor.get().toTextColor();

        String usernameFormat = Modules.get().get(RCModule.class).username.get();

        String message = playerMessage;
        String username = "";

        if (type == MessageType.MESSAGE) {
            String[] parts = playerMessage.split(" ", 2);
            username = parts[0];
            message = parts.length > 1 ? parts[1] : "";
        }

        MutableText prefixText = Text.literal(prefix + " ")
            .styled(style -> style.withColor(prefixColor));

        MutableText nameText = Text.literal("");
        if (type == MessageType.MESSAGE) {
            nameText = Text.literal(usernameFormat.replace("%username%", username) + " ")
                .styled(style -> style.withColor(usernameColor));
        }

        MutableText typeText = Text.literal("");
        if (type == MessageType.ERROR) {
            typeText = Text.literal("Error: ")
                .styled(style -> style.withColor(Formatting.RED));
        } else if (type == MessageType.WARNING) {
            typeText = Text.literal("Warning: ")
                .styled(style -> style.withColor(Formatting.YELLOW));
        }

        MutableText messageText = Text.literal(message);

        switch(type) {
            case MESSAGE:
                messageText.styled(style -> style.withColor(messageColor));
                break;
            case ERROR:
                messageText.styled(style -> style.withColor(Formatting.RED));
                break;
            case WARNING:
                messageText.styled(style -> style.withColor(Formatting.YELLOW));
                break;
        }

        MutableText finalMessage = prefixText.append(type == MessageType.MESSAGE ? nameText : typeText)
            .append(messageText);

        mc.player.sendMessage(finalMessage, false);
    }
}
