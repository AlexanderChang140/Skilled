package me.cat.skilled.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.registries.ForgeRegistries;
import net.puffish.skillsmod.api.SkillsAPI;
import net.puffish.skillsmod.api.json.JsonElement;
import net.puffish.skillsmod.api.util.Problem;
import net.puffish.skillsmod.api.util.Result;

import java.util.function.Function;
import java.util.function.Supplier;

import static net.puffish.skillsmod.api.json.BuiltinJson.parseIdentifier;

public class RewardUtil {
    public static Result<Attribute, Problem> parseAttribute(JsonElement element) {
        return parseFromResourceLocation(
                element,
                id -> {
                    if (id.getNamespace().equals(SkillsAPI.MOD_ID)) {
                        id = new ResourceLocation("puffish_attributes", id.getPath());
                    }
                    return ForgeRegistries.ATTRIBUTES.getValue(id);
                },
                () -> "Expected attribute",
                id -> "Unknown attribute `" + id + "`"
        );
    }

    private static <T> Result<T, Problem> parseFromResourceLocation(JsonElement element, Function<ResourceLocation, T> parser, Supplier<String> expected, Function<ResourceLocation, String> unknown) {
        return parseIdentifier(element)
                .mapFailure(problem -> element.getPath().createProblem(expected.get()))
                .andThen(id -> {
                    try {
                        return Result.success(parser.apply(id));
                    } catch (Exception ignored) {
                        return Result.failure(element.getPath().createProblem(unknown.apply(id)));
                    }
                });
    }
}
