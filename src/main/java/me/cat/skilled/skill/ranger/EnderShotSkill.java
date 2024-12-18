package me.cat.skilled.skill.ranger;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

public class EnderShotSkill extends ActiveSkill {
    public EnderShotSkill() {
        super(1, 1,200);
    }

    @Override
    public void onActivateSkill(ServerPlayer serverPlayer) {
        serverPlayer.addEffect(new MobEffectInstance(EffectRegistry.ENDER_SHOT.get(), -1, 0, false, false));
    }
}
