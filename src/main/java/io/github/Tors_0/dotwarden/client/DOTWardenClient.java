package io.github.Tors_0.dotwarden.client;

import io.github.Tors_0.dotwarden.client.render.ScytheItemRenderer;
import io.github.Tors_0.dotwarden.common.networking.DOTWNetworking;
import io.github.Tors_0.dotwarden.common.registry.ModItems;
import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.impl.client.model.loading.ModelLoaderHooks;
import net.fabricmc.fabric.mixin.client.model.loading.ModelLoaderMixin;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;

import java.util.Objects;

public class DOTWardenClient implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     *
     * @param mod the mod which is initialized
     */
    @Override
    public void onInitializeClient(ModContainer mod) {
        DOTWNetworking.init();

        // code from RealRTTV/malum-quilt
        Item item = ModItems.HARMONIC_STAFF;
        Identifier scytheId = Registries.ITEM.getId(item);
        ScytheItemRenderer scytheItemRenderer = new ScytheItemRenderer(scytheId);
        ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(scytheItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, scytheItemRenderer);
        // TODO what is ModelLoadingRegistry called (1.19.2 -> 1.20.1)
//        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
//            out.accept(new ModelIdentifier(Objects.requireNonNull(Identifier.tryValidate(scytheId.getNamespace(), scytheId.getPath() + "_gui")), "inventory"));
//            out.accept(new ModelIdentifier(Objects.requireNonNull(Identifier.tryValidate(scytheId.getNamespace(), scytheId.getPath() + "_handheld")), "inventory"));
//        });

    }
}
