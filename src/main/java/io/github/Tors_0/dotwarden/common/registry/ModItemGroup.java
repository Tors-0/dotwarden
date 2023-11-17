package io.github.Tors_0.dotwarden.common.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;

public class ModItemGroup {
    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addItem(ModItems.DISCIPLE_HOOD);
            entries.addItem(ModItems.DISCIPLE_CLOAK);
            entries.addItem(ModItems.DISCIPLE_GREAVES);
            entries.addItem(ModItems.DISCIPLE_BOOTS);
            entries.addItem(ModItems.SCULKED_KNIFE);
            entries.addItem(ModItems.HARMONIC_STAFF);
            entries.addItem(ModItems.SEISMIC_HORN);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addItem(ModItems.CAPTURED_SOUL);
            entries.addItem(ModItems.ECHO_CHAMBER);
            entries.addItem(ModItems.CORRUPTED_HEART);
            entries.addItem(ModItems.POWER_OF_THE_DISCIPLE);
            entries.addItem(ModItems.SCULK_CORE);
        });
    }
}
