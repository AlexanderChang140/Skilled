package me.cat.skilled.skill.instance.ranger.active;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.instance.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class EnderShotSkill extends ActiveSkill {
    public static final int MAX_LEVEL = 1;

    public EnderShotSkill() {
        super(200);
    }

    @Override
    public int getMaxLevel() {
        return MAX_LEVEL;
    }

    @Override
    public boolean onActivateSkill(ServerPlayer serverPlayer) {
        MobEffectInstance mobEffectInstance = new MobEffectInstance(EffectRegistry.ENDER_SHOT.get(), -1, 0, false, false);
        serverPlayer.addEffect(mobEffectInstance);
        return true;
    }
}
