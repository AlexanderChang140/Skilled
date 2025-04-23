package me.cat.skilled.skill;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ActiveSkillData extends SkillData {
    private final SkillSlot skillSlot;

    public ActiveSkillData(
            String skillId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            Function<Integer, String> desc,
            ResourceLocation icon,
            SkillSlot skillSlot
    ) {
        super(skillId, maxLevel, skillSupplier, title, desc, icon);
        this.skillSlot = skillSlot;
    }

    public ActiveSkillData(
            String skillId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            String desc,
            ResourceLocation icon,
            SkillSlot skillSlot
    ) {
        super(skillId, maxLevel, skillSupplier, title, desc, icon);
        this.skillSlot = skillSlot;
    }

    public SkillSlot getSkillSlot() {
        return skillSlot;
    }
}
