package dev.saturn.saturnrc.modules;

import dev.saturn.saturnrc.SaturnRC;
import dev.saturn.saturnrc.util.SocketClient;
import dev.saturn.saturnrc.util.Utils;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RCModule extends Module {
    public SocketClient socketClient;

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgPrefix = this.settings.createGroup("Prefix");
    private final SettingGroup sgMessage = this.settings.createGroup("Message");
    private final SettingGroup sgExclude = this.settings.createGroup("Exclude");

    public final Setting<String> host = sgGeneral.add(new StringSetting.Builder()
        .name("host")
        .description("Host of the socket server")
        .defaultValue("127.0.0.1")
        .build()
    );

    public final Setting<Integer> port = sgGeneral.add(new IntSetting.Builder()
        .name("port")
        .description("Port of the socket server")
        .min(0)
        .max(65535)
        .defaultValue(3000)
        .build()
    );

    public final Setting<String> token = sgGeneral.add(new StringSetting.Builder()
        .name("token")
        .description("Auth token for the socket server")
        .defaultValue("token")
        .build()
    );

    public final Setting<String> prefix = sgPrefix.add(new StringSetting.Builder()
            .name("prefix")
            .description("Prefix of the messages")
            .defaultValue("[RC]")
            .build()
    );

    public final Setting<SettingColor> prefixColor = sgPrefix.add(new ColorSetting.Builder()
        .name("prefix-color")
        .description("The color of the prefix.")
        .defaultValue(Color.GRAY)
        .build()
    );

    public final Setting<String> username = sgMessage.add(new StringSetting.Builder()
        .name("username")
        .description("Formatting for the username")
        .defaultValue("<%username%>")
        .build()
    );

    public final Setting<SettingColor> usernameColor = sgMessage.add(new ColorSetting.Builder()
        .name("username-color")
        .description("The color of the username.")
        .defaultValue(Color.WHITE)
        .build()
    );

    public final Setting<SettingColor> messageColor = sgMessage.add(new ColorSetting.Builder()
        .name("message-color")
        .description("The color of the message.")
        .defaultValue(Color.WHITE)
        .build()
    );

    public final Setting<Boolean> normalMessages = sgExclude.add(new BoolSetting.Builder()
        .name("normal-messages")
        .description("Exclude normal messages from being shown in the chat. (Only works in 6b6t)that")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> meteorCommands = sgExclude.add(new BoolSetting.Builder()
        .name("meteor-commands")
        .description("Allow meteor commands to be excluded from sending through RC.")
        .defaultValue(true)
        .build()
    );

    public final Setting<List<String>> otherPrefixes = sgExclude.add(new StringListSetting.Builder()
        .name("other-prefixes")
        .description("Allow other prefixes to be excluded from sending through RC.")
        .defaultValue(Arrays.asList("*", ".", ";"))
        .build()
    );

    public RCModule() {
        super(SaturnRC.CATEGORY, "RC", "Module and settings for Saturn RC.");
    }

    @Override
    public void onActivate() {
        try {
            socketClient = new SocketClient(host.get(), port.get(), token.get());
            socketClient.start();
        } catch (IOException e) {
            Utils.chatMessage("Could not connect to socket " + host + ":" + port, Utils.MessageType.ERROR);
            e.printStackTrace();
            this.toggle();
        }

        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        if(socketClient != null) {
            socketClient.close();
            socketClient = null;
        }

        super.onDeactivate();
    }
}
