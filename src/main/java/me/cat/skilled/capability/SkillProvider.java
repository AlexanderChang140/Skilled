package me.cat.skilled.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkillProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<SkillCap> SKILLS = CapabilityManager.get(new CapabilityToken<>() { });

    private SkillCap skills = null;
    private final LazyOptional<SkillCap> optional = LazyOptional.of(this::createPlayerSkills);

    private SkillCap createPlayerSkills() {
        if (this.skills == null) {
            this.skills = new SkillCap();
        }
        return this.skills;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == SKILLS) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSkills().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSkills().loadNBTData(nbt);
    }
}
