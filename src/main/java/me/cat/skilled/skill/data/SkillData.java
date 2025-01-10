package me.cat.skilled.skill.data;

import me.cat.skilled.skill.instance.Skill;
import me.cat.skilled.util.SkillUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SkillData {
    protected static final int SIZE_SMALL = 8;
    protected static final int SIZE_LARGE = 16;

    private final String skillId;
    private final String categoryId;
    private final int maxLevel;
    private final Supplier<Skill> skillSupplier;
    private final int requiredPoints;
    private final Set<String> prerequisites = new HashSet<>();

    private final String title;
    private final Function<Integer, String> desc;
    private final ResourceLocation icon;
    private final int x;
    private final int y;
    private final int size;

    @Deprecated
    public SkillData(
            String skillId,
            String categoryId,
            int maxLevel,
            Supplier<Skill> skillSupplier,

            String title,
            Function<Integer, String> desc,
            ResourceLocation icon,
            int x,
            int y
    ) {
        this.skillId = skillId;
        this.categoryId = categoryId;
        this.maxLevel = maxLevel;
        this.skillSupplier = skillSupplier;
        this.requiredPoints = 0;

        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.x = x;
        this.y = y;
        this.size = SIZE_SMALL;
    }

    public SkillData(
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
            int size
    ) {
        this.skillId = skillId;
        this.categoryId = categoryId;
        this.maxLevel = maxLevel;
        this.skillSupplier = skillSupplier;
        this.requiredPoints = requiredPoints;

        this.title = title;
        this.desc = desc;
        this.icon = icon;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public boolean isUnlocked(Player player) {
        return SkillUtil.getUsedSkillPoints(player) >= requiredPoints && SkillUtil.hasPrerequisites(player, getSkillId());
    }

    public void registerPrerequisites() {
    }

    protected void addPrerequisite(SkillData skillData) {
        prerequisites.add(skillData.getSkillId());
    }

    public String getSkillId() {
        return skillId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public Skill getSkillInstance() {
        return skillSupplier.get();
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public int getRequiredPoints() {
        return requiredPoints;
    }

    public Set<String> getPrerequisites() {
        return Collections.unmodifiableSet(prerequisites);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    protected static double toPercent(double percent) {
        return percent * 100;
    }

    protected static double toPercentOffset(double percent) {
        return (percent - 1) * 100;
    }
}