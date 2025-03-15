package me.cat.skilled.network.packet.in;

import me.cat.skilled.client.gui.ScreenRegistry;
import me.cat.skilled.network.Messenger;
import me.cat.skilled.network.packet.out.SetScreenS2CPacket;
import me.cat.skilled.category.Category;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetCategoryC2S {
    private final String categoryId;

    public SetCategoryC2S(Category category) {
        categoryId = category.getId();
    }

    public SetCategoryC2S(FriendlyByteBuf buf) {
        categoryId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(categoryId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (PlayerSkillManager.getCategoryId(serverPlayer).isEmpty()) {
                PlayerSkillManager.setCategoryId(serverPlayer, categoryId);
                Messenger.sendToPlayer(new SetScreenS2CPacket(ScreenRegistry.SKILL), serverPlayer);
            }
        });
    }
}
