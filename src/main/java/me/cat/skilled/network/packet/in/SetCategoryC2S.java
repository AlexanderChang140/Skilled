package me.cat.skilled.network.packet.in;

import me.cat.skilled.client.gui.ScreenRegistry;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.SetScreenS2CPacket;
import me.cat.skilled.skill.category.Category;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetCategoryC2S {
    String categoryId;

    public SetCategoryC2S(Category category) {
        categoryId = category.getId();
    }

    public SetCategoryC2S(FriendlyByteBuf buf) {
        categoryId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(categoryId);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (SkillUtil.getCategoryId(serverPlayer).isEmpty()) {
                SkillUtil.setCategoryId(serverPlayer, categoryId);
                Messenger.sendToPlayer(new SetScreenS2CPacket(ScreenRegistry.SKILL), serverPlayer);
            }
        });
        return true;
    }
}
