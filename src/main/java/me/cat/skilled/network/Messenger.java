package me.cat.skilled.network;

import me.cat.skilled.Skilled;
import me.cat.skilled.network.packet.ActivatePrimarySkillC2SPacket;
import me.cat.skilled.network.packet.DashS2CPacket;
import me.cat.skilled.network.packet.SyncCapabilityS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messenger {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Skilled.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE.messageBuilder(SyncCapabilityS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncCapabilityS2CPacket::new)
                .encoder(SyncCapabilityS2CPacket::toBytes)
                .consumerMainThread(SyncCapabilityS2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(ActivatePrimarySkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ActivatePrimarySkillC2SPacket::new)
                .encoder(ActivatePrimarySkillC2SPacket::toBytes)
                .consumerMainThread(ActivatePrimarySkillC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(DashS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(DashS2CPacket::new)
                .encoder(DashS2CPacket::toBytes)
                .consumerMainThread(DashS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
