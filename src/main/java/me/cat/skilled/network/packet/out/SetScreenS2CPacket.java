package me.cat.skilled.network.packet.out;

import me.cat.skilled.client.gui.ScreenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetScreenS2CPacket {
    private final String screenId;

    public SetScreenS2CPacket(ScreenRegistry.ScreenData screenData) {
        screenId = screenData.getScreenId();
    }

    public SetScreenS2CPacket(FriendlyByteBuf buf) {
        screenId = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(screenId);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            instance.setScreen(ScreenRegistry.getScreen(screenId));
        });
    }
}
