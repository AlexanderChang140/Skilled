package me.cat.skilled.skill.skills.warrior.active;

import me.cat.skilled.skill.ActiveSkill;
import net.minecraft.server.level.ServerPlayer;

public class SecondWindSkill extends ActiveSkill {
    public static final float HEAL_FACTOR = 0.5f;

    public SecondWindSkill() {
        super(100, 1);
    }

    @Override
    protected boolean onActivateSkill(ServerPlayer serverPlayer) {
        serverPlayer.heal((serverPlayer.getMaxHealth() - serverPlayer.getHealth()) * HEAL_FACTOR);
        return true;
    }
}
