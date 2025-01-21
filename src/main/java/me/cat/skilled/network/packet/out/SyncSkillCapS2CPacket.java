package me.cat.skilled.network.packet.out;

import me.cat.skilled.capability.SkillCap;
import me.cat.skilled.capability.SkillProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSkillCapS2CPacket {
    CompoundTag skillTag;

    public SyncSkillCapS2CPacket(SkillCap skillCap) {
        CompoundTag tag = new CompoundTag();
        skillCap.saveNBTData(tag);
        skillTag = tag;
    }

    public SyncSkillCapS2CPacket(FriendlyByteBuf buf) {
        this.skillTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(skillTag);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            localPlayer.getCapability(SkillProvider.SKILLS)
                    .ifPresent(skills -> skills.loadNBTData(skillTag));
        });
        return true;
    }
}
