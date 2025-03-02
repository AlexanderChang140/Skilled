package me.cat.skilled.network.packet.out;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LoadShaderS2C {
    ResourceLocation resourceLocation;

    public LoadShaderS2C(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public LoadShaderS2C(FriendlyByteBuf buf) {
        resourceLocation = new ResourceLocation(buf.readUtf(), buf.readUtf());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(resourceLocation.getNamespace());
        buf.writeUtf(resourceLocation.getPath());
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            instance.gameRenderer.loadEffect(resourceLocation);
        });
        return true;
    }
}
