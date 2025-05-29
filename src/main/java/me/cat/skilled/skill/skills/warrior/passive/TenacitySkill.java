package me.cat.skilled.skill.skills.warrior.passive;

import me.cat.skilled.skill.Skill;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.UUID;

public class TenacitySkill extends Skill {
    private static final double HEALTH_MULTIPLIER = 0.25;
    private static final UUID TENACITY_HEALTH_MULTPLIER_UUID = UUID.nameUUIDFromBytes("tenacity_health_multiplier".getBytes());

    @Override
    public void onUpdate(Player player) {
        AttributeModifier modifier = new AttributeModifier(
                TENACITY_HEALTH_MULTPLIER_UUID,
                TENACITY_HEALTH_MULTPLIER_UUID.toString(),
                HEALTH_MULTIPLIER,
                AttributeModifier.Operation.MULTIPLY_BASE
        );
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(modifier);
    }

    @Override
    public void onRemove(Player player) {
        Objects.requireNonNull(player.getAttribute(Attributes.MAX_HEALTH)).removeModifier(TENACITY_HEALTH_MULTPLIER_UUID);
    }
}
