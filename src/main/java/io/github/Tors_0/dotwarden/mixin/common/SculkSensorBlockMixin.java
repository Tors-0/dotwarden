package io.github.Tors_0.dotwarden.mixin.common;

import io.github.Tors_0.dotwarden.common.item.DiscipleArmorItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.GameEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.stream.IntStream;

@Mixin(SculkSensorBlockEntity.class)
public class SculkSensorBlockMixin extends BlockEntity {
    public SculkSensorBlockMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "accept", at = @At(value = "HEAD"), cancellable = true)
    private void dotwarden$blockSculkSensorWithArmor(ServerWorld world, GameEventListener listener, BlockPos pos, GameEvent event, Entity entity, Entity sourceEntity, float distance, CallbackInfo ci) {
        if (!(sourceEntity instanceof PlayerEntity player)) return;
        boolean isSneaky = IntStream.rangeClosed(0, 3).mapToObj(player.getInventory()::getArmorStack)
                .allMatch(itemStack -> itemStack.getItem() instanceof DiscipleArmorItem);
        if (isSneaky) ci.cancel();
    }
}
