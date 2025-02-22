package org.tyic.tyicmod;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<BlockPos> BLOCK_POS = register("block_pos", builder -> builder.codec(BlockPos.CODEC));

    public static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builder) {
        RegistryKey<ComponentType<?>> registryKey = RegistryKey.of(RegistryKeys.DATA_COMPONENT_TYPE, Identifier.of(TyicMod.MOD_ID, id));
        return Registry.register(Registries.DATA_COMPONENT_TYPE, registryKey, builder.apply(ComponentType.builder()).build());
    }

    public static void init() {
        TyicMod.LOGGER.info("Registering mod data component types.");
    }
}
