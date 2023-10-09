package io.github.Tors_0.dotwarden.mixin.common;

import io.github.Tors_0.dotwarden.common.item.DiscipleArmorItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.warden.WardenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.IntStream;

@Mixin(WardenEntity.class)
public abstract class WardenEntityMixin {

    @Inject(method = "isEnemy", cancellable = true, at = @At(value = "RETURN"))
    protected void isEnemy(@Nullable Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if ((entity instanceof PlayerEntity player)) {
            boolean isSneaky = IntStream.rangeClosed(0, 3).mapToObj(player.getInventory()::getArmorStack)
                    .allMatch(itemStack -> itemStack.getItem() instanceof DiscipleArmorItem);
            if (isSneaky) cir.cancel();
        }
    }
}
