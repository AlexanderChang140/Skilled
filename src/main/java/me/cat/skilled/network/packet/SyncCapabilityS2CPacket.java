package me.cat.skilled.network.packet;

import me.cat.skilled.capability.PlayerSkills;
import me.cat.skilled.capability.PlayerSkillsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCapabilityS2CPacket {
    CompoundTag skillTag;

    public SyncCapabilityS2CPacket(PlayerSkills playerSkills) {
        CompoundTag tag = new CompoundTag();
        playerSkills.saveNBTData(tag);
        skillTag = tag;
    }

    public SyncCapabilityS2CPacket(FriendlyByteBuf buf) {
        this.skillTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(skillTag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getCapability(PlayerSkillsProvider.PLAYER_SKILLS)
                    .ifPresent(skills -> skills.loadNBTData(skillTag));
        });
        return true;
    }
}
