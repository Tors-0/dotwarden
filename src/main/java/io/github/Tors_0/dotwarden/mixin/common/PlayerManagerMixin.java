package io.github.Tors_0.dotwarden.mixin.common;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemStack;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    // code credit to https://github.com/Grohiik/sticky-elytra for this mixin code
    @Inject(method = "respawnPlayer", at = @At(value = "RETURN"), cancellable = true)
    private void transferInventory(ServerPlayerEntity oldplayer, boolean alive,
                                   CallbackInfoReturnable<ServerPlayerEntity> cir) {
        ServerPlayerEntity newPlayer = cir.getReturnValue();
        List<DefaultedList<ItemStack>> oldInventorylist =
                ImmutableList.of(oldplayer.getInventory().main, oldplayer.getInventory().armor,
                        oldplayer.getInventory().offHand);
        List<DefaultedList<ItemStack>> newInventorylist =
                ImmutableList.of(newPlayer.getInventory().main, newPlayer.getInventory().armor,
                        newPlayer.getInventory().offHand);

        for (int i = 0; i < 3; i++) {
            DefaultedList<ItemStack> oldInventory = oldInventorylist.get(i);
            DefaultedList<ItemStack> newInventory = newInventorylist.get(i);

            for (int j = 0; j < oldInventory.size(); j++) {
                newInventory.set(j, oldInventory.get(j));
            }
        }

        cir.setReturnValue(newPlayer);
    }
}
