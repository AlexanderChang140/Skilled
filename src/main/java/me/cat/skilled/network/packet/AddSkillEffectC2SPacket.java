package me.cat.skilled.network.packet;

import me.cat.skilled.util.SkillUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class AddSkillEffectC2SPacket {
    private final String skillId;
    private final MobEffect effect;
    private final int duration;
    private final int amplifier;
    private final boolean ambient;
    private final boolean visible;

    public AddSkillEffectC2SPacket(String skillId, MobEffect effect, int duration, int amplifier, boolean ambient, boolean visible) {
        this.skillId = skillId;
        this.effect = effect;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.visible = visible;
    }

    public AddSkillEffectC2SPacket(FriendlyByteBuf buf) {
        this.skillId = buf.readUtf();
        this.effect = buf.readRegistryIdSafe(MobEffect.class);
        this.duration = buf.readVarInt();
        this.amplifier = buf.readVarInt();
        this.ambient = buf.readBoolean();
        this.visible = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(skillId);
        buf.writeRegistryId(ForgeRegistries.MOB_EFFECTS, effect);
        buf.writeVarInt(duration);
        buf.writeVarInt(amplifier);
        buf.writeBoolean(ambient);
        buf.writeBoolean(visible);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer serverPlayer = context.getSender();
            if (SkillUtil.hasSkill(serverPlayer, skillId)) {
                serverPlayer.addEffect(new MobEffectInstance(effect, duration, amplifier, ambient, visible));
            }
        });
        return true;
    }
}
