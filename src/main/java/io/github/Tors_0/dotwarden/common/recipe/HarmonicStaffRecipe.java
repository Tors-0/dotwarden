package io.github.Tors_0.dotwarden.common.recipe;

import io.github.Tors_0.dotwarden.common.DOTWarden;
import io.github.Tors_0.dotwarden.common.item.EchoChamberItem;
import io.github.Tors_0.dotwarden.common.registry.ModItems;
import io.github.Tors_0.dotwarden.common.registry.ModRecipeSerializers;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class HarmonicStaffRecipe extends SpecialCraftingRecipe {
    // TODO: make le recipes use a sculk core instead of custom recipe types
    // TODO opt 2: fix custom recipes
    public static final Identifier HARMONIC_STAFF_RECIPE_ID = new Identifier(DOTWarden.ID, "harmonic_staff");
    private static final Ingredient ECHO_CHAMBER = Ingredient.ofItems(ModItems.ECHO_CHAMBER);
    private static final Ingredient ECHO_SHARD = Ingredient.ofItems(Items.ECHO_SHARD);
    private static final Ingredient AMETHYST_CLUSTER = Ingredient.ofItems(Items.AMETHYST_CLUSTER);

    public HarmonicStaffRecipe(Identifier id, CraftingCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        for (int i = 6; i < 9; ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                if (ECHO_SHARD.test(itemStack)) {
                    return ECHO_CHAMBER.test(inventory.getStack(i - 3)) && AMETHYST_CLUSTER.test(inventory.getStack(i - 6));
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        for (int i = 3; i < 6; ++i) {
            ItemStack echoChamber = inventory.getStack(i);
            if (!echoChamber.isEmpty()
                    && EchoChamberItem.getBundleOccupancy(echoChamber) == 16
            ) {
                if (inventory.getStack(i-3).isOf(Items.AMETHYST_CLUSTER) && inventory.getStack(i+3).isOf(Items.ECHO_SHARD)) {
                    return new ItemStack(ModItems.HARMONIC_STAFF);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return new ItemStack(ModItems.HARMONIC_STAFF);
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 1 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.HARMONIC_STAFF;
    }

    public static class Type implements RecipeType<HarmonicStaffRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();

        public static final String ID = "harmonic_staff";
    }
    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }
}
