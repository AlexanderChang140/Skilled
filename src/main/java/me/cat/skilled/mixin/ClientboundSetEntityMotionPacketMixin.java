package me.cat.skilled.mixin;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientboundSetEntityMotionPacket.class)
public class ClientboundSetEntityMotionPacketMixin {
    @Redirect(method = "<init>(ILnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D", ordinal = 0))
    public double redirectClamp0(double pValue, double pMin, double pMax) {
        return pValue;
    }

    @Redirect(method = "<init>(ILnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D", ordinal = 1))
    public double redirectClamp1(double pValue, double pMin, double pMax) {
        return pValue;
    }

    @Redirect(method = "<init>(ILnet/minecraft/world/phys/Vec3;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;clamp(DDD)D", ordinal = 2))
    public double redirectClamp2(double pValue, double pMin, double pMax) {
        return pValue;
    }
}
