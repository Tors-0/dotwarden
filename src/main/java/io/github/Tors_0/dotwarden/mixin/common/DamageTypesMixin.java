package io.github.Tors_0.dotwarden.mixin.common;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.world.gen.BootstrapContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static io.github.Tors_0.dotwarden.common.registry.ModDamageTypes.HEARTSTAB;

@Mixin(DamageTypes.class)
public interface DamageTypesMixin {
    @Inject(method = "bootstrap", at = @At(value = "RETURN"))
    private static void dotwarden$addCustomDamageType(BootstrapContext<DamageType> context, CallbackInfo ci) {
        context.register(HEARTSTAB, new DamageType("heartstab", 0.1f));
    }
}
