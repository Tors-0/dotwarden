package io.github.Tors_0.dotwarden.common;

import io.github.Tors_0.dotwarden.common.extensions.PlayerExtensions;
import io.github.Tors_0.dotwarden.common.registry.ModItemGroup;
import io.github.Tors_0.dotwarden.common.registry.ModItems;
import io.github.Tors_0.dotwarden.common.registry.ModRecipeSerializers;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.quiltmc.qsl.entity.event.api.ServerPlayerEntityCopyCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DOTWarden implements ModInitializer {
    public static final String ID = "dotwarden";
    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod name as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    private static final Identifier WARDEN_LOOT_TABLE_ID = EntityType.WARDEN.getLootTableId();

    @Override
    public void onInitialize(ModContainer mod) {
        LOGGER.info("Now initializing {} version {}", mod.metadata().name(), mod.metadata().version());

        // register all items
        ModItems.register();
        ModItemGroup.init();

        ModRecipeSerializers.init();

        // register custom recipe types
//        Registry.register(Registries.RECIPE_TYPE, new Identifier(ID, HarmonicStaffRecipe.Type.ID), HarmonicStaffRecipe.Type.INSTANCE);
//        Registry.register(Registries.RECIPE_TYPE, new Identifier(ID, SeismicHornRecipe.Type.ID), SeismicHornRecipe.Type.INSTANCE);

        // keep power and sacrifice status on death/dimension transfer
        ServerPlayerEntityCopyCallback.EVENT.register((copy, original, wasDeath) -> {
            if (copy instanceof PlayerExtensions playerNew && original instanceof PlayerExtensions playerOld) {
                playerNew.dotwarden$setPowerLevel(playerOld.dotwarden$getPowerLevel());
                playerNew.dotwarden$setSacrifice(playerOld.dotwarden$hasSacrificed());
            }
        });

        // let the warden drop a soul
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin() && WARDEN_LOOT_TABLE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .with(ItemEntry.builder(ModItems.CAPTURED_SOUL));
                tableBuilder.pool(poolBuilder);
            }
        });

        LOGGER.info("Finished initializing {}", mod.metadata().name());
    }
}
