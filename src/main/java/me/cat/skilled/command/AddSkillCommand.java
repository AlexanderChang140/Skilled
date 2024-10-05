package me.cat.skilled.command;

import static net.minecraft.commands.Commands.*;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import me.cat.skilled.capability.PlayerSkillsProvider;
import me.cat.skilled.util.SkillIds;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AddSkillCommand {
    private static final SuggestionProvider<CommandSourceStack> sugg = (ctx, builder) -> SharedSuggestionProvider.suggest(getSkillIds(), builder);

    public AddSkillCommand(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(literal("skilled")
            .then(literal("add")
                .then(Commands.argument("skills", StringArgumentType.string())
                    .suggests(sugg)
                    .executes(command -> {
                        String skill = StringArgumentType.getString(command, "skills");
                        int level = 1;
                        return AddSkill(command.getSource(), skill, level);
                    })
                    .then(Commands.argument("level", IntegerArgumentType.integer(1))
                        .executes(command ->  {
                            String skill = StringArgumentType.getString(command, "skills");
                            int level = IntegerArgumentType.getInteger(command, "level");
                            return AddSkill(command.getSource(), skill, level);
                        })
                    )
                )
            )
        );
    }

    private int AddSkill(CommandSourceStack source, String skillId, int level) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        player.getCapability(PlayerSkillsProvider.PLAYER_SKILLS).ifPresent(skills ->
                skills.setSkillLevel(skillId, level));
        source.sendSystemMessage(Component.literal("Skill added"));
        return 1;
    }

    private static List<String> getSkillIds(){
        List<String> stringFields = new ArrayList<>();

        Field[] fields = SkillIds.class.getFields();

        for (Field field : fields) {
            if (field.getType() == String.class) {
                try {
                    stringFields.add((String) field.get(null));
                }
                catch (IllegalAccessException ignored) {
                    System.out.println("Failed to get skill id");
                }
            }
        }
        return stringFields;
    }
}
