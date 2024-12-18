package me.cat.skilled.skill;

import net.minecraft.nbt.CompoundTag;

public abstract class Skill {
    protected int level;
    protected int maxLevel;

    protected Skill(int level, int maxLevel) {
        this.level = level;
        this.maxLevel = level;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = Math.max(0, Math.min(level, this.maxLevel));
    }

    public CompoundTag saveNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        tag.putInt("max_level", maxLevel);
        return tag;
    }

    public void loadNbt(CompoundTag tag) {
        level = tag.getInt("level");
        maxLevel = tag.getInt("max_level");
    }
}
