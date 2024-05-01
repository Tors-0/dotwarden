package io.github.Tors_0.dotwarden.common.registry;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {
    public static RegistryKey<DamageType> HEARTSTAB = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("heartstab"));
}
