package me.cat.skilled.network.packet.out;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.skill.Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncSkillS2CPacket {
    private final String skillId;
    private final CompoundTag skillTag;

    public SyncSkillS2CPacket(Skill skill) {
        skillId = skill.getSkillData().getSkillId();
        skillTag = skill.serializeNBT();
    }

    public SyncSkillS2CPacket(FriendlyByteBuf buf) {
        this.skillId = buf.readUtf();
        this.skillTag = buf.readNbt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(skillId);
        buf.writeNbt(skillTag);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            PlayerSkillManager.getSkillInstance(localPlayer, skillId).deserializeNBT(skillTag);
        });
    }
}
