package me.cat.skilled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.cat.skilled.Skilled;
import me.cat.skilled.capability.manager.NodeManager;
import me.cat.skilled.registry.CategoryRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;

public class ClassCommand {
    private static final SuggestionProvider<CommandSourceStack> sugg = (ctx, builder) -> SharedSuggestionProvider.suggest(CategoryRegistry.getCategoryIds(), builder);

    public ClassCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("skilled_debug")
                .requires(command -> command.hasPermission(4))
                .then(literal("category")
                        .then(literal("get")
                                .executes(command -> getCategory(command.getSource())))
                        .then(literal("set")
                                .then(Commands.argument("classes", StringArgumentType.string())
                                        .suggests(sugg)
                                        .executes(command -> {
                                            String category = StringArgumentType.getString(command, "classes");
                                            return setCategory(command.getSource(), category);
                                        })
                                )
                        )
                        .then(literal("clear")
                                .executes(command -> clearCategory(command.getSource()))
                        )
                )
        );
    }

    private int setCategory(CommandSourceStack source, String category) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();

            if (!CategoryRegistry.getCategoryIds().contains(category)) {
                throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherUnknownArgument().create();
            }
            else {
                NodeManager.clearAll(serverPlayer);
                NodeManager.setCategoryId(serverPlayer, category);
                source.sendSystemMessage(Component.literal("Class set to " + category));
                return 1;
            }
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int getCategory(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            String categoryId = NodeManager.getCategoryId(serverPlayer);

            if (!categoryId.isEmpty()) {
                source.sendSystemMessage(Component.literal(categoryId));
            }
            else {
                source.sendSystemMessage(Component.literal("No class found"));
            }
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }

    private int clearCategory(CommandSourceStack source) throws CommandSyntaxException {
        try {
            ServerPlayer serverPlayer = source.getPlayerOrException();
            NodeManager.clearAll(serverPlayer);
            source.sendSystemMessage(Component.literal("Class cleared"));
            return 1;
        }
        catch (Exception e) {
            Skilled.LOGGER.error("An unexpected error occurred trying to execute that command", e);
            throw e;
        }
    }
}
