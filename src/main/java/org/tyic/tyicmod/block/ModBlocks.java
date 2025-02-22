package org.tyic.tyicmod.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.tyic.tyicmod.TyicMod;
import org.tyic.tyicmod.item.ModItems;

import java.util.function.Function;

public class ModBlocks {
    public static final Block TYSH_BLOCK = register("tysh_block", Block::new, AbstractBlock.Settings.create());
    public static final Block WATER_FEEDER = register("water_feeder", WaterFeederBlock::new,
            AbstractBlock.Settings.create().strength(4f).requiresTool());

    public static Block register(String id, Function<AbstractBlock.Settings, Block> blockFunction, AbstractBlock.Settings settings) {
        RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(TyicMod.MOD_ID, id));
        Block block = Registry.register(Registries.BLOCK, registryKey, blockFunction.apply(settings.registryKey(registryKey)));
        ModItems.register(id, (itemSettings) -> new BlockItem(block, itemSettings), new Item.Settings().useBlockPrefixedTranslationKey());
        return block;
    }

    public static void init() {
        TyicMod.LOGGER.info("Registering mod blocks.");
    }
}
