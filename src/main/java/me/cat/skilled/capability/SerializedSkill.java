package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;

public interface SerializedSkill {
    public CompoundTag saveNbt();
    public void loadNbt(CompoundTag tag);
}
