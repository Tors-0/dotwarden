package io.github.Tors_0.dotwarden.common.registry;

import io.github.Tors_0.dotwarden.common.DOTWarden;
import io.github.Tors_0.dotwarden.common.recipe.HarmonicStaffRecipe;
import io.github.Tors_0.dotwarden.common.recipe.SeismicHornRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModRecipeSerializers {
    public static SpecialRecipeSerializer<HarmonicStaffRecipe> HARMONIC_STAFF;
    public static SpecialRecipeSerializer<SeismicHornRecipe> SEISMIC_HORN;

    static {
        HARMONIC_STAFF = register(new Identifier(DOTWarden.ID, "harmonic_staff"), new SpecialRecipeSerializer<>(HarmonicStaffRecipe::new));
        SEISMIC_HORN = register(new Identifier(DOTWarden.ID, "seismic_horn"), new SpecialRecipeSerializer<>(SeismicHornRecipe::new));
    }

    public static void init() {
    }

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(Identifier id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
    }
}
