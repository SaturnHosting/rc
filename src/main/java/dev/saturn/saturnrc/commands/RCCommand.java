package dev.saturn.saturnrc.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.saturn.saturnrc.modules.RCModule;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import static com.mojang.brigadier.Command.SINGLE_SUCCESS;

public class RCCommand extends Command {
    public RCCommand() {
        super("rc", "Toggles the RC module on/off.");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
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
