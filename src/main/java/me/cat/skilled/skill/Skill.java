package me.cat.skilled.skill;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public abstract class Skill {
    protected final int maxLevel;
    protected int level = 1;

    protected Skill(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = Mth.clamp(level, 1, maxLevel);
    }

    public CompoundTag saveNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        return tag;
    }

    public void loadNbt(CompoundTag tag) {
        level = tag.getInt("level");
    }
}
