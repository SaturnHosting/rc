package dev.saturn.saturnrc.modules;

import dev.saturn.saturnrc.SaturnRC;
import meteordevelopment.meteorclient.events.render.Render3DEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.render.color.Color;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;

public class RCModule extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();
    private final SettingGroup sgRender = this.settings.createGroup("Render");

    /**
     * Example setting.
     * The {@code name} parameter should be in kebab-case.
     * If you want to access the setting from another class, simply make the setting {@code public}, and use
     * {@link meteordevelopment.meteorclient.systems.modules.Modules#get(Class)} to access the {@link Module} object.
     */
    private final Setting<Double> scale = sgGeneral.add(new DoubleSetting.Builder()
        .name("scale")
        .description("The size of the marker.")
        .defaultValue(2.0d)
        .range(0.5d, 10.0d)
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
        .defaultValue(Color.BLACK)
        .build()
    );

    /**
     * The {@code name} parameter should be in kebab-case.
     */
    public RCModule() {
        super(SaturnRC.CATEGORY, "saturn-rc", "An example module that highlights the center of the world.");
    }

    /**
     * Example event handling method.
     * Requires {@link SaturnRC#getPackage()} to be setup correctly, otherwise the game will crash whenever the module is enabled.
     */
    @EventHandler
    private void onRender3d(Render3DEvent event) {

    }
}
