package dev.niuex.morecommand.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.FinePositionResolver;
import io.papermc.paper.command.brigadier.argument.resolvers.selector.EntitySelectorArgumentResolver;
import io.papermc.paper.plugin.bootstrap.BootstrapContext;
import io.papermc.paper.plugin.bootstrap.PluginBootstrap;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

public class VelocityCommand implements PluginBootstrap {
//public class Vector {

    @Override
    public void bootstrap(BootstrapContext context) {
        LifecycleEventManager<BootstrapContext> manager = context.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(rootNode);
        });
    }

    public static void register(LifecycleEventManager<Plugin> manager) {
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(rootNode);
        });
    }

    private static final LiteralCommandNode<CommandSourceStack> rootNode = Commands.literal("velocity")
            .then(Commands.argument("速度", ArgumentTypes.finePosition())
                    .executes(context -> {
                        context.getSource().getExecutor().sendPlainMessage(
                                context.getArgument("速度", FinePositionResolver.class).resolve(context.getSource()).x() +
                                ", " +
                                context.getArgument("速度", FinePositionResolver.class).resolve(context.getSource()).y() +
                                ", " +
                                context.getArgument("速度", FinePositionResolver.class).resolve(context.getSource()).z()
                        );
                        context.getSource().getExecutor().setVelocity(
                                context.getArgument("速度", FinePositionResolver.class)
                                        .resolve(context.getSource())
                                        .toVector()
                        );
                        return Command.SINGLE_SUCCESS;
                    })
            )
            .then(Commands.argument("实体", ArgumentTypes.entity())
                    .executes(context -> {
                        context.getSource().getExecutor().sendPlainMessage(context.getArgument("实体", EntitySelectorArgumentResolver.class).resolve(context.getSource()).getFirst().getName());
                        context.getSource().getExecutor().setVelocity(
                                context.getArgument("实体", EntitySelectorArgumentResolver.class)
                                        .resolve(context.getSource())
                                        .getFirst()
                                        .getVelocity()
                        );
                        return Command.SINGLE_SUCCESS;
                    })
            )
            .then(Commands.argument("目标", ArgumentTypes.entities())
                    .then(Commands.argument("速度", ArgumentTypes.finePosition())
                            .executes(context -> {
                                Vector velocity = context.getArgument("速度", FinePositionResolver.class)
                                        .resolve(context.getSource())
                                        .toVector();
                                context.getArgument("目标", EntitySelectorArgumentResolver.class)
                                        .resolve(context.getSource())
                                        .forEach(entity -> entity.setVelocity(velocity));
                                return Command.SINGLE_SUCCESS;
                            })
                    )
                    .then(Commands.argument("实体", ArgumentTypes.entity())
                            .executes(context -> {
                                Vector velocity = context.getArgument("实体", EntitySelectorArgumentResolver.class)
                                        .resolve(context.getSource())
                                        .getFirst()
                                        .getVelocity();
                                context.getArgument("目标", EntitySelectorArgumentResolver.class)
                                        .resolve(context.getSource())
                                        .forEach(entity -> entity.setVelocity(velocity));
                                return Command.SINGLE_SUCCESS;
                            })
                    )
            )
            .build();
}
