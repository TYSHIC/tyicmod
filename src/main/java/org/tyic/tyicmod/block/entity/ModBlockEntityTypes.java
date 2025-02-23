package org.tyic.tyicmod.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.tyic.tyicmod.TyicMod;
import org.tyic.tyicmod.block.ModBlocks;

public class ModBlockEntityTypes {
    public static final BlockEntityType<ElectricPotBlockEntity> ELECTRIC_POT = register("electric_pot", ElectricPotBlockEntity::new, ModBlocks.ELECTRIC_POT);

    public static <T extends BlockEntity> BlockEntityType<T> register(String id, FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        RegistryKey<BlockEntityType<?>> registryKey = RegistryKey.of(RegistryKeys.BLOCK_ENTITY_TYPE, Identifier.of(TyicMod.MOD_ID, id));
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, registryKey, FabricBlockEntityTypeBuilder.create(factory, blocks).build());
    }
}
