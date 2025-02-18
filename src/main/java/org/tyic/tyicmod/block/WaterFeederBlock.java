package org.tyic.tyicmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.Potions;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.Map;

public class WaterFeederBlock extends Block {
    public static final EnumProperty<Direction> FACING = Properties.FACING;

    public WaterFeederBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    private boolean nextToWater(World world, BlockPos pos) {
        for (Direction direction : Direction.values())
            if (world.getBlockState(pos.offset(direction)).isOf(Blocks.WATER))
                return true;
        return false;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient() || hit.getSide() != state.get(FACING) || !nextToWater(world, pos))
            return ActionResult.PASS;
        Map<Item, ItemStack> itemStackMap = Map.<Item, ItemStack>of(Items.DIRT, new ItemStack(Items.GRASS_BLOCK), Items.BUCKET, new ItemStack(Items.WATER_BUCKET), Items.GLASS_BOTTLE, PotionContentsComponent.createStack(Items.POTION, Potions.WATER));
        if (!itemStackMap.containsKey(stack.getItem())) return ActionResult.PASS;
        ItemUsage.exchangeStack(stack, player, itemStackMap.get(stack.getItem()));
        return ActionResult.SUCCESS;
    }
}
