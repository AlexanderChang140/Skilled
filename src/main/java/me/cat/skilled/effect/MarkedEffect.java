package me.cat.skilled.effect;

import me.cat.skilled.registry.EffectRegistry;
import me.cat.skilled.skill.instance.ranger.active.MarkSkill;
import net.minecraft.ChatFormatting;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

@Mod.EventBusSubscriber
public class MarkedEffect extends MobEffect {
    public static final float DAMAGE_MULTIPLIER = 0.25f;
    private static final String TEAM_NAME = "marked_team";

    public MarkedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @Override
    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if (!pLivingEntity.level().isClientSide) {
            pLivingEntity.setGlowingTag(true);
            assignTeam(pLivingEntity);
        }
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, @NotNull AttributeMap attributeMap, int amplifier) {
        if (!pLivingEntity.level().isClientSide) {
            removeTeam(pLivingEntity);
            MarkSkill.removeMarkedEntity(pLivingEntity);
        }
        super.removeAttributeModifiers(pLivingEntity, attributeMap, amplifier);
    }

    private static void assignTeam(LivingEntity livingEntity) {
        if (livingEntity.level().isClientSide) {
            return;
        }

        Scoreboard scoreboard = livingEntity.level().getScoreboard();
        PlayerTeam team = scoreboard.getPlayerTeam(TEAM_NAME);
        if (team == null) {
            team = scoreboard.addPlayerTeam(TEAM_NAME);
            team.setColor(ChatFormatting.RED);
        }
        scoreboard.addPlayerToTeam(livingEntity.getStringUUID(), team);
    }

    private static void removeTeam(LivingEntity livingEntity) {
        Scoreboard scoreboard = livingEntity.level().getScoreboard();
        livingEntity.setGlowingTag(false);
        scoreboard.removePlayerFromTeam(TEAM_NAME);
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(EffectRegistry.MARKED.get())) {
            event.setAmount(event.getAmount() * (1 + DAMAGE_MULTIPLIER * livingEntity.getEffect(EffectRegistry.MARKED.get()).getAmplifier()));
        }
    }
}
