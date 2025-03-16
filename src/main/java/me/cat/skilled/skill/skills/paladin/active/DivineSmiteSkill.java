package me.cat.skilled.skill.skills.paladin.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.DamageSourceRegistry;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.ToggleableSkill;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class DivineSmiteSkill extends ToggleableSkill {
    private static final float DAMAGE = 6.0f;

    protected DivineSmiteSkill() {
        super(100);
    }

    @Mod.EventBusSubscriber
    public static class DivineSmiteEvents {
        @SubscribeEvent
        public static void onAttackEntity(LivingAttackEvent event) {
            if (event.getSource().getEntity() instanceof Player player
                    && PlayerSkillManager.getSkillInstance(player, SkillRegistry.DIVINE_SMITE.getSkillId()) instanceof DivineSmiteSkill skill
                    && skill.isToggled) {
                skill.isToggled = false;
                skill.isSkillReady = false;
                if (player instanceof ServerPlayer serverPlayer) {
                    event.getEntity().hurt(DamageSourceRegistry.holyDamage(serverPlayer), event.getAmount() + DAMAGE);
                    event.setCanceled(true);
                }
            }
        }
    }
}
