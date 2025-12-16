package dev.saturn.saturnrc;

import dev.saturn.saturnrc.commands.RCCommand;
import dev.saturn.saturnrc.modules.RCModule;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class SaturnRC extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("SaturnRC");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Saturn RC");

        // Modules
        Modules.get().add(new RCModule());

        // Commands
        Commands.add(new RCCommand());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "dev.saturn.saturnrc";
    }
}
