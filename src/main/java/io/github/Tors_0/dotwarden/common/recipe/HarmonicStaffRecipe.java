package io.github.Tors_0.dotwarden.common.recipe;

import com.google.gson.JsonObject;
import io.github.Tors_0.dotwarden.common.DOTWarden;
import io.github.Tors_0.dotwarden.common.registry.ModItems;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.quiltmc.qsl.recipe.api.serializer.QuiltRecipeSerializer;

public class HarmonicStaffRecipe extends SpecialCraftingRecipe {
    // TODO: make le recipes use a sculk core instead of custom recipe types
    public static final Identifier HARMONIC_STAFF_RECIPE_ID = new Identifier(DOTWarden.ID, "harmonic_staff");
    private static final Ingredient ECHO_CHAMBER = Ingredient.ofItems(ModItems.ECHO_CHAMBER);
    private static final Ingredient ECHO_SHARD = Ingredient.ofItems(Items.ECHO_SHARD);
    private static final Ingredient AMETHYST_CLUSTER = Ingredient.ofItems(Items.AMETHYST_CLUSTER);

    public HarmonicStaffRecipe(Identifier id, CraftingCategory category) {
        super(HARMONIC_STAFF_RECIPE_ID, CraftingCategory.EQUIPMENT);
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return false;
    }

    @Override
    public Identifier getId() {
        return HARMONIC_STAFF_RECIPE_ID;
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
            ItemStack echoStack = inventory.getStack(i);
            if (
                    !echoStack.isEmpty()
                    && echoStack.getNbt() != null
                    && echoStack.getOrCreateNbt().contains("Items")
                    && ItemStack.fromNbt(echoStack.getOrCreateNbt().getList("Items", NbtElement.COMPOUND_TYPE).getCompound(0)).getCount() == 1
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
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return DOTWarden.HARMONIC_STAFF_RECIPE_SERIALIZER;
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
    public static class Serializer implements QuiltRecipeSerializer<HarmonicStaffRecipe> {
        private Serializer() {}

        public static final Serializer INSTANCE = new Serializer();

        @Override
        public JsonObject toJson(HarmonicStaffRecipe recipe) {
            return null;
        }

        @Override
        public HarmonicStaffRecipe read(Identifier id, JsonObject json) {
            return new HarmonicStaffRecipe(id, CraftingCategory.EQUIPMENT);
        }

        @Override
        public HarmonicStaffRecipe read(Identifier id, PacketByteBuf buf) {
            return new HarmonicStaffRecipe(id, CraftingCategory.EQUIPMENT);
        }

        @Override
        public void write(PacketByteBuf buf, HarmonicStaffRecipe recipe) {

        }
    }
}
