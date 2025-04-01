package me.cat.skilled.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class CapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    private final Map<Capability<?>, LazyOptional<?>> capabilities = new HashMap<>();
    private final Map<Capability<?>, INBTSerializable<CompoundTag>> optionals = new HashMap<>();

    public <T extends INBTSerializable<CompoundTag>> void registerCapability(Capability<T> capability, Supplier<T> instanceSupplier) {
        T instance = instanceSupplier.get();
        optionals.put(capability, instance);
        capabilities.put(capability, LazyOptional.of(() -> instance));
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return capabilities.containsKey(cap) ? capabilities.get(cap).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        for (Map.Entry<Capability<?>, INBTSerializable<CompoundTag>> entry : optionals.entrySet()) {
            CompoundTag capNBT = entry.getValue().serializeNBT();
            nbt.put(entry.getKey().getName(), capNBT);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        for (Map.Entry<Capability<?>, INBTSerializable<CompoundTag>> entry : optionals.entrySet()) {
            entry.getValue().deserializeNBT(nbt.getCompound(entry.getKey().getName()));
        }
    }
}
