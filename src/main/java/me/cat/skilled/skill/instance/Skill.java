package me.cat.skilled.skill.instance;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public abstract class Skill {
    protected int level = 1;

    protected Skill() {
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = Mth.clamp(level, 1, getMaxLevel());
    }

    public abstract int getMaxLevel();

    public CompoundTag saveNbt() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", level);
        return tag;
    }

    public void loadNbt(CompoundTag tag) {
        level = tag.getInt("level");
    }
}
