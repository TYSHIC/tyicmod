package org.tyic.tyicmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.tyic.tyicmod.TyicMod;
import org.tyic.tyicmod.block.ModBlocks;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> TYIC_MOD =
            RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(TyicMod.MOD_ID, "tyic_mod"));

    public static void init() {
        TyicMod.LOGGER.info("Registering mod item groups.");
        Registry.register(Registries.ITEM_GROUP, TYIC_MOD, FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.tyic_mod"))
                .icon(() -> new ItemStack(ModItems.TYIC_LOGO))
                .build());
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.TYIC_MOD)
                .register((itemGroup) -> {
                    itemGroup.add(ModItems.TYIC_LOGO);
                    itemGroup.add(ModItems.KNIFE);
                    itemGroup.add(ModBlocks.TYSH_BLOCK);
                    itemGroup.add(ModBlocks.WATER_FEEDER);
                    itemGroup.add(ModItems.TNT_REMOTE);
                });
    }
}
