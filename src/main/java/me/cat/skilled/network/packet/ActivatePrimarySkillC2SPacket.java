package me.cat.skilled.network.packet;

import me.cat.skilled.skill.ActiveSkill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ActivatePrimarySkillC2SPacket {

    public ActivatePrimarySkillC2SPacket() {
    }

    public ActivatePrimarySkillC2SPacket(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            String primarySkillId = SkillUtil.getPrimarySkillId(serverPlayer);
            if (SkillUtil.getSkillInstance(serverPlayer, primarySkillId) instanceof ActiveSkill activeSkill) {
                activeSkill.activateSkill(serverPlayer);
            }
        });
        return true;
    }
}
