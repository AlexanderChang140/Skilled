package me.cat.skilled.mixin;

import me.cat.skilled.skill.FleetfootedSkill;
import me.cat.skilled.util.SkillIds;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Redirect(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    public float redirectClamp(float pValue, float pMin, float pMax) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (FleetfootedSkill.hasSkill(player)) {
            return FleetfootedSkill.CROUCH_MOVEMENT_SPEED;
        }
        else {
            return Mth.clamp(pValue, pMin, pMax);
        }
    }

    @Redirect(method = "aiStep()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/Input;leftImpulse:F", opcode = Opcodes.PUTFIELD))
    private void redirectLeftImpulse(Input input, float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!(player.getUseItem().getItem() instanceof ProjectileWeaponItem) || !SkillUtil.hasSkill(player, SkillIds.FLEETFOOTED_SKILL)) {
            input.leftImpulse *= 0.2F;
        }
        player.setSprinting(false);
    }

    @Redirect(method = "aiStep()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/Input;forwardImpulse:F", opcode = Opcodes.PUTFIELD))
    private void redirectForwardImpulse(Input input, float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!(player.getUseItem().getItem() instanceof ProjectileWeaponItem) || !SkillUtil.hasSkill(player, SkillIds.FLEETFOOTED_SKILL)) {
            input.forwardImpulse *= 0.2F;
        }
        player.setSprinting(false);
    }
}
