package org.tyic.tyicmod.item;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import org.tyic.tyicmod.TyicMod;

public class ModDataComponentTypes {
    public static final ComponentType<BlockPos> BLOCK_POS = register("block_pos", BlockPos.CODEC);
    public static final ComponentType<Integer> REDSTONE = register("redstone", Codecs.NON_NEGATIVE_INT);

    public static <T> ComponentType<T> register(String id, Codec<T> codec) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(TyicMod.MOD_ID, id),
                ComponentType.<T>builder().codec(codec).build());
    }

    public static void init() {
        TyicMod.LOGGER.info("Registering mod data component types.");
    }
}
