package org.tyic.tyicmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.tyic.tyicmod.ModDataComponentTypes;
import org.tyic.tyicmod.Util;
import org.tyic.tyicmod.block.entity.ModBlockEntityTypes;
import org.tyic.tyicmod.block.entity.RedstoneHeaterBlockEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class RedstoneHeaterBlock extends BlockWithEntity {
    public static final MapCodec<RedstoneHeaterBlock> CODEC = createCodec(RedstoneHeaterBlock::new);

    public RedstoneHeaterBlock(Settings settings) {
        super(settings);
    }

    private static Text getRedstoneText(int redstone) {
        return Text.translatable("tooltip.tyicmod.redstone_heater.redstone", redstone, RedstoneHeaterBlockEntity.MAX_REDSTONE).withColor(Colors.GREEN);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneHeaterBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient() ? null : validateTicker(type, ModBlockEntityTypes.REDSTONE_HEATER, RedstoneHeaterBlockEntity::tick);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient() || !(world.getBlockEntity(pos) instanceof RedstoneHeaterBlockEntity blockEntity))
            return ActionResult.PASS;
        ItemStack itemStack;
        if (!((itemStack = player.getMainHandStack()).isOf(Items.REDSTONE) || (itemStack = player.getOffHandStack()).isOf(Items.REDSTONE))) {
            player.sendMessage(getRedstoneText(blockEntity.getRedstone()), true);
            return ActionResult.SUCCESS;
        }
        blockEntity.addRedstone(100 * itemStack.getCount());
        itemStack.setCount(0);
        return ActionResult.SUCCESS;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!(world instanceof ServerWorld serverWorld) || !(entity instanceof ItemEntity inputItemEntity)) return;
        ItemStack inputStack = inputItemEntity.getStack();
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(inputStack);
        Optional<RecipeEntry<SmeltingRecipe>> recipeEntry = serverWorld.getRecipeManager()
                .getFirstMatch(RecipeType.SMELTING, singleStackRecipeInput, world);
        if (recipeEntry.isEmpty()) return;
        Vec3d centerPos = pos.toCenterPos();
        inputItemEntity.discard();
        for (int i = 0; i < inputStack.getCount(); i++)
            world.spawnEntity(new ItemEntity(world, centerPos.getX(), pos.getY() + 1, centerPos.getZ(),
                    recipeEntry.get().value().craft(singleStackRecipeInput, world.getRegistryManager())));
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isClient() || !(world.getBlockEntity(pos) instanceof RedstoneHeaterBlockEntity blockEntity) || blockEntity.getRedstone() <= 0)
            return super.onBreak(world, pos, state, player);
        ItemStack itemStack = new ItemStack(this);
        itemStack.applyComponentsFrom(blockEntity.createComponentMap());
        ItemEntity itemEntity = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), itemStack);
        itemEntity.setToDefaultPickupDelay();
        world.spawnEntity(itemEntity);
        return super.onBreak(world, pos, state, player);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        if (Util.hasShiftDown.get())
            tooltip.add(getRedstoneText(Objects.requireNonNullElse(stack.get(ModDataComponentTypes.REDSTONE), 0)));
        else tooltip.add(Util.PRESS_SHIFT);
        super.appendTooltip(stack, context, tooltip, options);
    }
}
