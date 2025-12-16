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

public class RCModule extends Module {
    public static SocketClient socketClient;

    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

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

    public final Setting<String> prefix = sgGeneral.add(new StringSetting.Builder()
            .name("prefix")
            .description("Prefix of the messages")
            .defaultValue("[SATURN]")
            .build()
    );

    public final Setting<SettingColor> prefixColor = sgGeneral.add(new ColorSetting.Builder()
        .name("prefix-color")
        .description("The color of the prefix.")
        .defaultValue(Color.GRAY)
        .build()
    );

    public RCModule() {
        super(SaturnRC.CATEGORY, "saturn-rc", "An example module that highlights the center of the world.");
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
