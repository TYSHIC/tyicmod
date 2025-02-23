package org.tyic.tyicmod.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ElectricPotBlockEntity extends BlockEntity {
    private int water = 0;

    public ElectricPotBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ELECTRIC_POT, pos, state);
    }

    public void setWater(int water) {
        this.water = Math.max(0, water);
    }

    public void increaseWater(int value) {
        setWater(getWater() + value);
    }

    public int getWater() {
        return water;
    }

    public static void tick(World world, BlockPos pos, BlockState state, ElectricPotBlockEntity blockEntity) {
        if (blockEntity.getWater() <= 0) return;
        blockEntity.increaseWater(-1);
    }
}
