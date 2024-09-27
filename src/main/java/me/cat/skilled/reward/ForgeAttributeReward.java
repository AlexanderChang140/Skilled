package me.cat.skilled.reward;

import me.cat.skilled.util.RewardUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.puffish.skillsmod.SkillsMod;
import net.puffish.skillsmod.api.SkillsAPI;
import net.puffish.skillsmod.api.json.BuiltinJson;
import net.puffish.skillsmod.api.json.JsonElement;
import net.puffish.skillsmod.api.json.JsonObject;
import net.puffish.skillsmod.api.reward.Reward;
import net.puffish.skillsmod.api.reward.RewardConfigContext;
import net.puffish.skillsmod.api.reward.RewardDisposeContext;
import net.puffish.skillsmod.api.reward.RewardUpdateContext;
import net.puffish.skillsmod.api.util.Problem;
import net.puffish.skillsmod.api.util.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ForgeAttributeReward implements Reward {
    public static final ResourceLocation ID = SkillsMod.createIdentifier("forge_attribute");

    private final List<UUID> uuids = new ArrayList<>();

    private final Attribute attribute;
    private final float value;
    private final AttributeModifier.Operation operation;

    private ForgeAttributeReward(Attribute attribute, float value, AttributeModifier.Operation operation) {
        this.attribute = attribute;
        this.value = value;
        this.operation = operation;
    }

    public static void register() {
        SkillsAPI.registerReward(
                ID,
                ForgeAttributeReward::parse
        );
    }

    private static Result<ForgeAttributeReward, Problem> parse(RewardConfigContext context) {
        return context.getData()
                .andThen(JsonElement::getAsObject)
                .andThen(ForgeAttributeReward::parse);
    }

    private static Result<ForgeAttributeReward, Problem> parse(JsonObject rootObject) {
        var problems = new ArrayList<Problem>();

        var optAttribute = rootObject.get("attribute")
                .andThen(attributeElement -> RewardUtil.parseAttribute(attributeElement)
                        .andThen(attribute -> {
                            if (DefaultAttributes.getSupplier(EntityType.PLAYER).hasAttribute(attribute)) {
                                return Result.success(attribute);
                            } else {
                                return Result.failure(attributeElement.getPath().createProblem("Expected a valid player attribute"));
                            }
                        })
                )
                .ifFailure(problems::add)
                .getSuccess();

        var optValue = rootObject.getFloat("value")
                .ifFailure(problems::add)
                .getSuccess();

        var optOperation = rootObject.get("operation")
                .andThen(BuiltinJson::parseAttributeOperation)
                .ifFailure(problems::add)
                .getSuccess();

        if (problems.isEmpty()) {
            return Result.success(new ForgeAttributeReward(
                    optAttribute.orElseThrow(),
                    optValue.orElseThrow(),
                    optOperation.orElseThrow()
            ));
        } else {
            return Result.failure(Problem.combine(problems));
        }
    }

    private void createMissingUUIDs(int count) {
        while (uuids.size() < count) {
            uuids.add(UUID.randomUUID());
        }
    }

    @Override
    public void update(RewardUpdateContext context) {
        var count = context.getCount();
        var instance = Objects.requireNonNull(context.getPlayer().getAttribute(attribute));

        createMissingUUIDs(count);

        for (var i = 0; i < uuids.size(); i++) {
            var uuid = uuids.get(i);
            if (instance.getModifier(uuid) == null) {
                if (i < count) {
                    instance.addTransientModifier(new AttributeModifier(
                            uuid,
                            "",
                            value,
                            operation
                    ));
                }
            } else {
                if (i >= count) {
                    instance.removeModifier(uuid);
                }
            }
        }
    }

    @Override
    public void dispose(RewardDisposeContext context) {
        for (var player : context.getServer().getPlayerList().getPlayers()) {
            var instance = Objects.requireNonNull(player.getAttribute(attribute));
            for (var uuid : uuids) {
                instance.removeModifier(uuid);
            }
        }
        uuids.clear();
    }
}
