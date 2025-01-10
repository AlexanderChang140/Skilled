package me.cat.skilled.network.packet.in;

import me.cat.skilled.skill.data.SkillSlot;
import me.cat.skilled.skill.instance.ActiveSkill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ActivateActiveSkillC2SPacket {
    private final int index;

    public ActivateActiveSkillC2SPacket(SkillSlot skillSlot) {
        index = skillSlot.getIndex();
    }

    public ActivateActiveSkillC2SPacket(FriendlyByteBuf buf) {
        index = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(index);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            SkillSlot skillSlot = SkillSlot.fromNumber(index);
            ActiveSkill activeSkill = SkillUtil.getActiveSkillInstance(serverPlayer, skillSlot);
            if (activeSkill != null) {
                activeSkill.activateSkill(serverPlayer);
            }
        });
        return true;
    }
}
