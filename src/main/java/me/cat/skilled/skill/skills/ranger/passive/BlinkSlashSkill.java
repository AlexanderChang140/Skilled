package me.cat.skilled.skill.skills.ranger.passive;

import me.cat.skilled.registry.SkillRegistry;
import me.cat.skilled.skill.Skill;
import me.cat.skilled.skill.skills.ranger.active.EnderShotSkill;
import me.cat.skilled.util.Utilities;
import me.cat.skilled.util.event.Subscriber;

public class BlinkSlashSkill extends Skill implements Subscriber<Void, Void> {
    @Override
    public void init() {
        if (skillMap.get(SkillRegistry.ENDER_SHOT.getSkillId()) instanceof EnderShotSkill enderShotSkill) {
            enderShotSkill.getTeleportEvent().subscribe(this);
        }
        else {
            Utilities.failedSubscription(skillData.getSkillId(), SkillRegistry.ENDER_SHOT.getSkillId());
        }
    }

    @Override
    public void onRemove() {
        if (skillMap.get(SkillRegistry.ENDER_SHOT.getSkillId()) instanceof EnderShotSkill enderShotSkill) {
            enderShotSkill.getTeleportEvent().unsubscribe(this);
        }
    }

    @Override
    public Void onNotified(Void v) {
        System.out.println("BlinkSlash triggered");
        return null;
    }
}
