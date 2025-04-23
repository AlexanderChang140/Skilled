package me.cat.skilled.skill;

import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SkillData {
    private final String skillId;
    private final int maxLevel;
    private final Supplier<Skill> skillSupplier;

    private final String title;
    private final Function<Integer, String> desc;
    private final ResourceLocation icon;

    public SkillData(
            String skillId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            Function<Integer, String> desc,
            ResourceLocation icon
    ) {
        this.skillId = skillId;
        this.maxLevel = maxLevel;
        this.skillSupplier = skillSupplier;

        this.title = title;
        this.desc = desc;
        this.icon = icon;
    }

    public SkillData(
            String skillId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            String desc,
            ResourceLocation icon
    ) {
        this.skillId = skillId;
        this.maxLevel = maxLevel;
        this.skillSupplier = skillSupplier;

        this.title = title;
        this.desc = (level) -> desc;
        this.icon = icon;
    }

    public String getSkillId() {
        return skillId;
    }

    public Skill getSkillInstance() {
        Skill skill = skillSupplier.get();
        skill.setSkillData(this);
        return skill;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc(int level) {
        return desc.apply(level);
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    protected static double toPercent(double percent) {
        return percent * 100;
    }

    protected static double toPercentOffset(double percent) {
        return (percent - 1) * 100;
    }
}