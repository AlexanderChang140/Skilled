package me.cat.skilled.skill.skills.ranger.active;

import me.cat.skilled.capability.manager.PlayerSkillManager;
import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.DurationSkill;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class VolleySkill extends DurationSkill {
    protected VolleySkill() {
        super(600, 100, true);
    }

    @Mod.EventBusSubscriber
    public static class VolleyEventHandler {
        @SubscribeEvent
        public static void onLivingEntityUseItemStart(LivingEntityUseItemEvent event) {
            if (!(event.getEntity() instanceof Player player)) return;
            if (!(event.getItem().getItem() instanceof ProjectileWeaponItem)) return;
            if (!(PlayerSkillManager.getSkillInstance(player, SkillRegistry.VOLLEY.getSkillId()) instanceof VolleySkill skill)) return;
            if (!skill.isActive) return;
            event.setDuration(event.getDuration() - 1);
        }
    }
}
