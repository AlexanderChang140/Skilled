package me.cat.skilled.skill;

import me.cat.skilled.capability.manager.PlayerLevelManager;
import me.cat.skilled.capability.manager.PlayerSkillManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SkillData {
    protected static final int SIZE = 16;

    private final String skillId;
    private final String categoryId;
    private final int maxLevel;
    private final Supplier<Skill> skillSupplier;
    private final int requiredPoints;
    private final Set<String> prerequisites = new HashSet<>();

    private final String title;
    private final Function<Integer, String> desc;
    private final ResourceLocation icon;
    private final int size;
    private int x;
    private int y;

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
        this.size = SIZE;
        this.x = x;
        this.y = y;
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
        this.size = size;
        this.x = x;
        this.y = y;
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
        this.size = size;
        this.x = 0;
        this.y = 0;
    }

    public boolean isUnlocked(Player player) {
        return PlayerLevelManager.getUsedSkillPoints(player) >= requiredPoints
                && PlayerSkillManager.hasPrerequisites(player, getSkillId())
                && PlayerLevelManager.getSkillPoints(player) > 0
                && Objects.equals(PlayerSkillManager.getCategoryId(player), categoryId);
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

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    protected static double toPercent(double percent) {
        return percent * 100;
    }

    protected static double toPercentOffset(double percent) {
        return (percent - 1) * 100;
    }
}