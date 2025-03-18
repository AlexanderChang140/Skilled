package me.cat.skilled.network.packet.in;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.SkillData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AddSkillLevelC2SPacket {
    private final String skillId;

    public AddSkillLevelC2SPacket(SkillData skillData) {
        this.skillId = skillData.getSkillId();
    }

    public AddSkillLevelC2SPacket(FriendlyByteBuf buf) {
        skillId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(skillId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            SkillData skillData = SkillRegistry.getSkillData(skillId);
            int currlevel = PlayerSkillManager.getSkillLevel(serverPlayer, skillId);
            if (skillData.isUnlocked(serverPlayer) && currlevel < skillData.getMaxLevel()) {
                PlayerSkillManager.updateSkillLevel(serverPlayer, skillId, currlevel + 1);
            }
        });
    }
}
