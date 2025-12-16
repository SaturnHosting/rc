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
            socketClient = new SocketClient("127.0.0.1", 3000, "token");
            socketClient.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        super.onActivate();
    }

    @Override
    public void onDeactivate() {
        socketClient.close();
        socketClient = null;
        super.onDeactivate();
    }
}
