package me.cat.skilled.network.packet.in;

import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.data.SkillData;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LevelSkillC2SPacket {
    private final String skillId;

    public LevelSkillC2SPacket(SkillData skillData) {
        this.skillId = skillData.getSkillId();
    }

    public LevelSkillC2SPacket(FriendlyByteBuf buf) {
        skillId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(skillId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            SkillData skillData = SkillRegistry.getSkillData(skillId);
            int level = Mth.clamp(SkillUtil.getSkillLevel(serverPlayer, skillId) + 1, 1, skillData.getMaxLevel());
            if (skillData.isUnlocked(serverPlayer)) {
                SkillUtil.updateSkill(serverPlayer, skillId, level);
            }
        });
        return true;
    }
}
