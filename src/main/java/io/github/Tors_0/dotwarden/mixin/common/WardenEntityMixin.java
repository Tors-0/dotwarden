package io.github.Tors_0.dotwarden.mixin.common;

import io.github.Tors_0.dotwarden.common.item.DiscipleArmorItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.IntStream;

@Mixin(WardenEntity.class)
public class WardenEntityMixin {
    @Inject(method = "setAttackTarget", at = @At("HEAD"), cancellable = true)
    public void setAttackTarget(LivingEntity entity, CallbackInfo ci) {
        if (!(entity instanceof PlayerEntity player)) return;
        boolean isSneaky = IntStream.rangeClosed(0, 3).mapToObj(player.getInventory()::getArmorStack)
                .allMatch(itemStack -> itemStack.getItem() instanceof DiscipleArmorItem);
        if (isSneaky) ci.cancel();
    }
}
