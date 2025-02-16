package org.tyic.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class KnifeItem extends Item {
    public KnifeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        // 邏輯處理只能在伺服端進行，不能在客戶端
        if (world.isClient()) return ActionResult.PASS;
        user.damage((ServerWorld) world, world.getDamageSources().playerAttack(user), 1f);
        user.getStackInHand(hand).damage(1, user, LivingEntity.getSlotForHand(hand));
        return ActionResult.SUCCESS;
    }
}
