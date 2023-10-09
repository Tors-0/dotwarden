package io.github.Tors_0.dotwarden.mixin.common;

import io.github.Tors_0.dotwarden.common.item.DiscipleArmorItem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.warden.AngerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.stream.IntStream;

@Mixin(AngerManager.class)
public class WardenAngerManagerMixin {
    @Shadow
    @Final
    protected ArrayList<Entity> suspects;

    @Shadow
    private int highestAnger;

    @Shadow
    @Final
    protected Object2IntMap<Entity> angerBySuspect;

    @Inject(method = "sortAndUpdateHighestAnger", at = @At(value = "TAIL"))
    public void sortAndUpdateHighestAnger(CallbackInfo ci) {
        for (int i = 0; i < this.suspects.size(); i++) {
            Entity entity = this.suspects.get(i);
            if (entity instanceof PlayerEntity player) {
                boolean isSneaky = IntStream.rangeClosed(0, 3).mapToObj(player.getInventory()::getArmorStack)
                        .allMatch(itemStack -> itemStack.getItem() instanceof DiscipleArmorItem);
                if (isSneaky) this.suspects.remove(entity);
            }
        }
        if (this.suspects.size() == 1) {
            highestAnger = angerBySuspect.getInt(this.suspects.get(0));
        }
    }
}
