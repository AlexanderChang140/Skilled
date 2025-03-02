package me.cat.skilled.mixin;

import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ranger.passive.FleetfootedSkill;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {
    // FleetfootedSkill
    @Redirect(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(FFF)F"))
    public float redirectClamp(float pValue, float pMin, float pMax) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (PlayerSkillManager.getSkillInstance(player, SkillRegistry.FLEETFOOTED.getSkillId()) instanceof FleetfootedSkill skill) {
            return Mth.clamp(FleetfootedSkill.getCrouchMovementSpeed(skill.getLevel()) + EnchantmentHelper.getSneakingSpeedBonus(player), pMin, pMax);
        }
        else {
            return Mth.clamp(pValue, pMin, pMax);
        }
    }

    @Redirect(method = "aiStep()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/Input;leftImpulse:F", opcode = Opcodes.PUTFIELD))
    private void redirectLeftImpulse(Input input, float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!(player.getUseItem().getItem() instanceof ProjectileWeaponItem) || !PlayerSkillManager.hasSkill(player, SkillRegistry.FLEETFOOTED.getSkillId())) {
            input.leftImpulse *= 0.2F;
        }
        player.setSprinting(false);
    }

    @Redirect(method = "aiStep()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/player/Input;forwardImpulse:F", opcode = Opcodes.PUTFIELD))
    private void redirectForwardImpulse(Input input, float value) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        if (!(player.getUseItem().getItem() instanceof ProjectileWeaponItem) || !PlayerSkillManager.hasSkill(player, SkillRegistry.FLEETFOOTED.getSkillId())) {
            input.forwardImpulse *= 0.2F;
        }
        player.setSprinting(false);
    }
}
