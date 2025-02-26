package org.tyic.tyicmod.item;

import net.minecraft.block.Blocks;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Colors;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.tyic.tyicmod.ModDataComponentTypes;
import org.tyic.tyicmod.Util;

import java.util.List;

public class TntRemoteItem extends Item {
    public TntRemoteItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) return ActionResult.PASS;
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
        ItemStack stack = user.getStackInHand(hand);
        if (blockHitResult.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (world.getBlockState(blockPos).isOf(Blocks.TNT)) {
                stack.set(ModDataComponentTypes.BLOCK_POS, blockPos);
                user.sendMessage(Text.translatable("tooltip.tyicmod.tnt_remote.binding_tnt",
                        blockPos.getX(), blockPos.getY(), blockPos.getZ()).withColor(Colors.GREEN), true);
                return ActionResult.SUCCESS;
            }
        }
        BlockPos tntBlockPos = stack.get(ModDataComponentTypes.BLOCK_POS);
        if (tntBlockPos == null) return ActionResult.PASS;
        if (!world.getBlockState(tntBlockPos).isOf(Blocks.TNT)) {
            stack.remove(ModDataComponentTypes.BLOCK_POS);
            return ActionResult.PASS;
        }
        user.sendMessage(Text.translatable("tooltip.tyicmod.tnt_remote.priming_tnt")
                .withColor(Colors.RED), true);
        world.removeBlock(tntBlockPos, false);
        TntBlock.primeTnt(world, tntBlockPos);
        stack.remove(ModDataComponentTypes.BLOCK_POS);
        return ActionResult.SUCCESS;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        BlockPos tntBlockPos = stack.get(ModDataComponentTypes.BLOCK_POS);
        if (tntBlockPos != null)
            if (Util.hasShiftDown.get())
                tooltip.add(Text.translatable("tooltip.tyicmod.tnt_remote.binding_tnt",
                        tntBlockPos.getX(), tntBlockPos.getY(), tntBlockPos.getZ()).withColor(Colors.GREEN));
            else tooltip.add(Util.PRESS_SHIFT);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
