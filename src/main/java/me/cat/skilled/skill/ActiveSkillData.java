package me.cat.skilled.skill;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public class ActiveSkillData extends SkillData {
    private final SkillSlot skillSlot;

    @Deprecated
    public ActiveSkillData(
            String skillId,
            String categoryId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            Function<Integer, String> desc,
            ResourceLocation icon,
            int x,
            int y,
            SkillSlot skillSlot
    ) {
        super(skillId, categoryId, maxLevel, skillSupplier, title, desc, icon, x, y);
        this.skillSlot = skillSlot;
    }

    public ActiveSkillData(
            String skillId,
            String categoryId,
            int maxLevel,
            Supplier<Skill> skillSupplier,
            int requiredPoints,

            String title,
            Function<Integer, String> desc,
            ResourceLocation icon,
            int x,
            int y,
            SkillSlot skillSlot
    ) {
        super(skillId, categoryId, maxLevel, skillSupplier, requiredPoints, title, desc, icon, x, y, SIZE);
        this.skillSlot = skillSlot;
    }

    public SkillSlot getSkillSlot() {
        return skillSlot;
    }

    @Override
    public int getSize() {
        return SIZE;
    }
}
