package org.tyic.tyicmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.ComponentMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tyic.tyicmod.item.ModDataComponentTypes;

public class RedstoneHeaterBlockEntity extends BlockEntity {
    private int redstone = 0;
    public static final int MAX_REDSTONE = 1000000;

    public RedstoneHeaterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.REDSTONE_HEATER, pos, state);
    }

    public void setRedstone(int value) {
        this.redstone = Math.clamp(value, 0, MAX_REDSTONE);
    }

    public void addRedstone(int value) {
        setRedstone(getRedstone() + value);
    }

    public int getRedstone() {
        return this.redstone;
    }

    public static void tick(World world, BlockPos pos, BlockState state,
                            RedstoneHeaterBlockEntity blockEntity) {
        if (blockEntity.getRedstone() <= 0) return;
        blockEntity.addRedstone(-1);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        if (nbt.contains("redstone")) setRedstone(nbt.getInt("redstone"));
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        nbt.putInt("redstone", getRedstone());
    }

    @Override
    protected void readComponents(ComponentsAccess components) {
        super.readComponents(components);
        setRedstone(components.getOrDefault(ModDataComponentTypes.REDSTONE, 0));
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        super.addComponents(builder);
        builder.add(ModDataComponentTypes.REDSTONE, getRedstone());
    }
}
