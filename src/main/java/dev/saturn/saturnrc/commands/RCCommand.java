package dev.saturn.saturnrc.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

public class RCCommand extends Command {
    public RCCommand() {
        super("rc", "RC chat commands");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("toggle").executes(context -> {
            RCModule module = Modules.get().get(RCModule.class);

            if (module == null) {
                error("RC module not found, what the fuck?");
                return SINGLE_SUCCESS;
            }

            module.toggle();

            if (module.isActive()) {
                info("§aenabled§r");
            } else {
                info("§cdisabled§r");
            }

            return SINGLE_SUCCESS;
        }));

        builder.then(literal("online").executes(context -> {
            RCModule module = Modules.get().get(RCModule.class);

            if (module == null || !module.isActive()) {
                error("RC module is not active");
                return SINGLE_SUCCESS;
            }

            try {
                module.socketClient.sendOnline();
            } catch (Exception e) {
                error("Failed to send online request: " + e.getMessage());
            }

            return SINGLE_SUCCESS;
        }));

        builder.executes(context -> {
            RCModule module = Modules.get().get(RCModule.class);

            if (module == null) {
                error("RC module not found, what the fuck?");
                return SINGLE_SUCCESS;
            }

            module.toggle();

            if (module.isActive()) {
                info("§aenabled§r");
            } else {
                info("§cdisabled§r");
            }

            return SINGLE_SUCCESS;
        });
    }
}
