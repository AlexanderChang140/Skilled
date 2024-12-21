package me.cat.skilled.skill.ranger.active;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class PowerShotSkill extends ActiveSkill {
    public PowerShotSkill() {
        super(1, 200);
    }

    @Override
    protected void onActivateSkill(ServerPlayer serverPlayer) {
        serverPlayer.addEffect(new MobEffectInstance(EffectRegistry.POWER_SHOT.get(), -1, level - 1, false, false));
    }
}
