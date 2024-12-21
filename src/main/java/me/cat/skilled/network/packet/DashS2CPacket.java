package me.cat.skilled.network.packet;

import me.cat.skilled.skill.warrior.active.DashSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DashS2CPacket {
    public DashS2CPacket() {
    }

    public DashS2CPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            double DASH_SPEED = DashSkill.DASH_SPEED;
            LocalPlayer localPlayer = Minecraft.getInstance().player;
                    Vec3 motion = localPlayer.getDeltaMovement();
            Vec3 lookDirection = localPlayer.getLookAngle();
            Vec3 dashVelocity = new Vec3(
                    lookDirection.x * DASH_SPEED,
                    lookDirection.y * DASH_SPEED,
                    lookDirection.z * DASH_SPEED
            );

            localPlayer.setDeltaMovement(motion.add(dashVelocity));
            localPlayer.fallDistance = 0.0F;
        });
        return true;
    }
}
