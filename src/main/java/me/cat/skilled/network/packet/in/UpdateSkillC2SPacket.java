package me.cat.skilled.network.packet.in;

import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSkillC2SPacket {
    private final String skillId;
    private final int skillLevel;

    public UpdateSkillC2SPacket(String skillId, int skillLevel) {
        this.skillId = skillId;
        this.skillLevel = skillLevel;
    }

    public UpdateSkillC2SPacket(FriendlyByteBuf buf) {
        skillId = buf.readUtf();
        skillLevel = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(skillId);
        buf.writeInt(skillLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            SkillUtil.updateSkill(serverPlayer, skillId, skillLevel);
        });
        return true;
    }
}
