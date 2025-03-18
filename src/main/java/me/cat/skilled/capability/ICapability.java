package me.cat.skilled.capability;

import net.minecraft.nbt.CompoundTag;

public interface ICapability {
    void saveNBTData(CompoundTag nbt);

    void loadNBTData(CompoundTag nbt);
}
