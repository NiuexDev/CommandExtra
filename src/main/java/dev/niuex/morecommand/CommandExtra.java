package dev.niuex.morecommand;

import dev.niuex.morecommand.command.VelocityCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandExtra extends JavaPlugin {

    public static CommandExtra plugin;

    @Override
    public void onLoad() {
        plugin = this;
        getLogger().info("已加载。");
        VelocityCommand.register(this.getLifecycleManager());
    }

    @Override
    public void onEnable() {
        getLogger().info("已启用。");
    }

    @Override
    public void onDisable() {
        getLogger().info("已禁用。");
    }

//    @Override
//    public void bootstrap(BootstrapContext context) {
//        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();
//        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
//            final Commands commands = event.registrar();
//            commands.register(rootNode);
//        });
//    }
//
//    private static final LiteralCommandNode<CommandSourceStack> rootNode = Commands.literal("vector").build();
}