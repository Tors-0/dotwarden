package io.github.Tors_0.dotwarden.common.registry;

import io.github.Tors_0.dotwarden.common.DOTWarden;
import io.github.Tors_0.dotwarden.common.item.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

import java.util.LinkedHashMap;
import java.util.Map;

public interface ModItems {
	Map<Item, Identifier> ITEMS = new LinkedHashMap<>();

	// Item NAME = createItem("name", new ModItem(new QuiltItemSettings()));
    // Basic Utility
	Item POWER_OF_THE_DISCIPLE      = createItem("power_of_the_disciple",       new PowerItem(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.EPIC)));
    Item SCULKED_KNIFE              = createItem("sculked_knife",               new SculkedKnifeItem(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE).maxDamage(64)));
    // Crafting Materials
    Item CORRUPTED_HEART            = createItem("corrupted_heart",             new CorruptedHeartItem(
            new QuiltItemSettings().rarity(Rarity.RARE).maxCount(1).fireproof()));
    Item CAPTURED_SOUL              = createItem("captured_soul",               new SoulItem(
            new QuiltItemSettings().maxCount(4).rarity(Rarity.RARE)));
    Item SCULK_CORE                 = createItem("sculk_core",                  new Item(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE)));
    Item ECHO_CHAMBER               = createItem("echo_chamber",                new EchoChamberItem(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE)));
    // Custom Items/Weapons
    Item HARMONIC_STAFF             = createItem("harmonic_staff",              new HarmonicStaffItem(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE)));
    Item SEISMIC_HORN               = createItem("seismic_horn",                new SeismicHornItem(
            new QuiltItemSettings().maxCount(1).rarity(Rarity.RARE)));
/*    Item SCULK_BELL                 = createItem("sculk_bell",                  new Item(
            new QuiltItemSettings()));*/
    // Armor Items
    Item DISCIPLE_HOOD              = createItem("disciple_helmet",             new DiscipleArmorItem(ArmorItem.ArmorSlot.HELMET,
            new QuiltItemSettings().maxCount(1)));
    Item DISCIPLE_CLOAK             = createItem("disciple_chestplate",         new DiscipleArmorItem(ArmorItem.ArmorSlot.CHESTPLATE,
            new QuiltItemSettings()));
    Item DISCIPLE_GREAVES           = createItem("disciple_leggings",           new DiscipleArmorItem(ArmorItem.ArmorSlot.LEGGINGS,
            new QuiltItemSettings().maxCount(1)));
    Item DISCIPLE_BOOTS             = createItem("disciple_boots",              new DiscipleArmorItem(ArmorItem.ArmorSlot.BOOTS,
            new QuiltItemSettings()));

    /**
     * Creates a new item of type T using the constructor from T
     * @param name String name of the item (used in JSON)
     * @param item _Item class that extends net.minecraft.Item
     * @return A newly created _Item object
     * @param <T> _Item class that extends net.minecraft.Item
     */
	private static <T extends Item> T createItem(String name, T item) {
		ITEMS.put(item, new Identifier(DOTWarden.ID, name));
		return item;
	}

    /**
     * Registers all items into the Minecraft Item Registry
     */
	static void register() {
		ITEMS.keySet().forEach(item -> {
            Registry.register(Registries.ITEM, ITEMS.get(item), item);
		});
	}
}
