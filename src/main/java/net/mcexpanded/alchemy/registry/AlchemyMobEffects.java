package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.effects.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface AlchemyMobEffects
{
    DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, Alchemy.MOD_ID);

    DeferredHolder<MobEffect, MobEffect> SUSTAINED_HEALTH_RECOVERY =
            register("sustained_health_recovery", SustainedHealthRecovery::new);

    DeferredHolder<MobEffect, MobEffect> HEALTH_RECOVERY =
            register("health_recovery", HealthRecovery::new);

    DeferredHolder<MobEffect, MobEffect> MAX_HEALTH =
            register("max_health", MaxHealth::new);

    DeferredHolder<MobEffect, MobEffect> DISEASE_DAMAGE =
            register("disease_damage", DiseaseDamage::new);

    DeferredHolder<MobEffect, MobEffect> POISON =
            register("poison", PoisonDamage::new);

    private static <T extends MobEffect> DeferredHolder<MobEffect, MobEffect> register(String name, Supplier<MobEffect> supplier)
    {
        return MOB_EFFECTS.register(name, supplier);
    }

    static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }


}
