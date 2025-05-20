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

public class SeismicHornRecipe extends SpecialCraftingRecipe {
    public static final Identifier SEISMIC_HORN_RECIPE_ID = new Identifier(DOTWarden.ID, "seismic_horn");
    private static final Ingredient ECHO_CHAMBER = Ingredient.ofItems(ModItems.ECHO_CHAMBER);
    private static final Ingredient GOAT_HORN = Ingredient.ofItems(Items.GOAT_HORN);
    private static final Ingredient SCULK_SHRIEKER = Ingredient.ofItems(Items.SCULK_SHRIEKER);

    public SeismicHornRecipe(Identifier id, CraftingCategory category) {
        super(id, category);
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    @Override
    public boolean matches(RecipeInputInventory inventory, World world) {
        for (int i = 3; i < 6; ++i) {
            ItemStack itemStack = inventory.getStack(i);
            if (!itemStack.isEmpty()) {
                if (GOAT_HORN.test(itemStack)) {
                    return ECHO_CHAMBER.test(inventory.getStack(i - 3)) && SCULK_SHRIEKER.test(inventory.getStack(i + 3));
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack craft(RecipeInputInventory inventory, DynamicRegistryManager registryManager) {
        for (int i = 0; i < 3; ++i) {
            ItemStack echoChamber = inventory.getStack(i);
            if (!echoChamber.isEmpty()
                    && EchoChamberItem.getBundleOccupancy(echoChamber) == 32
            ) {
                if (inventory.getStack(i+3).isOf(Items.GOAT_HORN) && inventory.getStack(i+6).isOf(Items.SCULK_SHRIEKER)) {
                    return new ItemStack(ModItems.SEISMIC_HORN);
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return new ItemStack(ModItems.SEISMIC_HORN);
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SEISMIC_HORN;
    }


    public static class Type implements RecipeType<HarmonicStaffRecipe> {
        private Type() {}
        public static final SeismicHornRecipe.Type INSTANCE = new SeismicHornRecipe.Type();

        public static final String ID = "seismic_horn";
    }
    @Override
    public RecipeType<?> getType() {
        return HarmonicStaffRecipe.Type.INSTANCE;
    }
}
