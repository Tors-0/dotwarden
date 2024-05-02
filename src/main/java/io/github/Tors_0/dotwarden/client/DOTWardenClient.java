package io.github.Tors_0.dotwarden.client;

import io.github.Tors_0.dotwarden.client.render.BigItemRenderer;
import io.github.Tors_0.dotwarden.common.item.EchoChamberItem;
import io.github.Tors_0.dotwarden.common.networking.DOTWNetworking;
import io.github.Tors_0.dotwarden.common.registry.ModItems;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
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

        // register predicate providers for custom item states
        ModelPredicateProviderRegistry.register(
                ModItems.ECHO_CHAMBER,
                new Identifier("filled"),
                (stack, world, entity, seed) -> EchoChamberItem.getAmountFilled(stack));
        ModelPredicateProviderRegistry.register(
                ModItems.SEISMIC_HORN,
                new Identifier("tooting"),
                (stack, world, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == stack ? 1.0F : 0.0F
        );

        // code from RealRTTV/malum-quilt
        Item item = ModItems.HARMONIC_STAFF;
        Identifier staffId = Registries.ITEM.getId(item);
        BigItemRenderer bigItemRenderer = new BigItemRenderer(staffId);
        ResourceLoader.get(ResourceType.CLIENT_RESOURCES).registerReloader(bigItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(item, bigItemRenderer);

        // TODO how to do this in 1.20.1? this code is from 1.19.2
//        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
//            out.accept(new ModelIdentifier(Objects.requireNonNull(Identifier.method_43902(staffId.getNamespace(),
//                    staffId.getPath() + "_gui")), "inventory"));
//            out.accept(new ModelIdentifier(Objects.requireNonNull(Identifier.method_43902(staffId.getNamespace(),
//                    staffId.getPath() + "_handheld")), "inventory"));
//        });
        ModelLoadingPlugin.register((pluginContext -> {
            pluginContext.addModels(new ModelIdentifier(Objects.requireNonNull(Identifier.tryValidate(staffId.getNamespace(),
                    staffId.getPath() + "_gui")), "inventory"));
            pluginContext.addModels(new ModelIdentifier(Objects.requireNonNull(Identifier.tryValidate(staffId.getNamespace(),
                    staffId.getPath() + "_handheld")), "inventory"));
        }));
    }
}
