package org.tyic.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.tyic.TyicMod;

import java.util.function.Function;

public class ModItems {
    public static final Item TYIC_LOGO = register("tyic_logo", Item::new, new Item.Settings());
    public static final Item KNIFE = register("knife", KnifeItem::new, new Item.Settings().maxCount(1).useCooldown(1).maxDamage(5));

    public static Item register(String id, Function<Item.Settings, Item> itemFunction, Item.Settings settings) {
        RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(TyicMod.MOD_ID, id));
        return Registry.register(Registries.ITEM, registryKey, itemFunction.apply(settings.registryKey(registryKey)));
    }

    public static void init() {
        TyicMod.LOGGER.info("Registering mod items.");
    }
}
