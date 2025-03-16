package me.cat.skilled.network;

import me.cat.skilled.Skilled;
import me.cat.skilled.network.packet.in.*;
import me.cat.skilled.network.packet.out.*;
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

        // IN
        INSTANCE.messageBuilder(ActivateActiveSkillC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ActivateActiveSkillC2SPacket::new)
                .encoder(ActivateActiveSkillC2SPacket::toBytes)
                .consumerMainThread(ActivateActiveSkillC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(AddSkillLevelC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AddSkillLevelC2SPacket::new)
                .encoder(AddSkillLevelC2SPacket::toBytes)
                .consumerMainThread(AddSkillLevelC2SPacket::handle)
                .add();

        INSTANCE.messageBuilder(SetCategoryC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetCategoryC2S::new)
                .encoder(SetCategoryC2S::toBytes)
                .consumerMainThread(SetCategoryC2S::handle)
                .add();

        INSTANCE.messageBuilder(SyncSkillS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncSkillS2CPacket::new)
                .encoder(SyncSkillS2CPacket::toBytes)
                .consumerMainThread(SyncSkillS2CPacket::handle)
                .add();

        // Out
        INSTANCE.messageBuilder(SyncSkillCapS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncSkillCapS2CPacket::new)
                .encoder(SyncSkillCapS2CPacket::toBytes)
                .consumerMainThread(SyncSkillCapS2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(SetScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SetScreenS2CPacket::new)
                .encoder(SetScreenS2CPacket::toBytes)
                .consumerMainThread(SetScreenS2CPacket::handle)
                .add();

        INSTANCE.messageBuilder(LoadShaderS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LoadShaderS2C::new)
                .encoder(LoadShaderS2C::toBytes)
                .consumerMainThread(LoadShaderS2C::handle)
                .add();

        INSTANCE.messageBuilder(ClearShaderS2C.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ClearShaderS2C::new)
                .encoder(ClearShaderS2C::toBytes)
                .consumerMainThread(ClearShaderS2C::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAll(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
