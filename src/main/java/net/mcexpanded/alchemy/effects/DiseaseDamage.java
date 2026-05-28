package net.mcexpanded.alchemy.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jspecify.annotations.Nullable;

public class DiseaseDamage extends MobEffect
{
    public DiseaseDamage()
    {
        super(MobEffectCategory.HARMFUL, 0xff29550d);
    }

    @Override
    public void applyInstantenousEffect(ServerLevel level, @Nullable Entity source, @Nullable Entity owner, LivingEntity mob, int amplification, double scale)
    {
        if (mob.isInvertedHealAndHarm())
            mob.heal(5);
        else if (!level.isClientSide())
            mob.hurtServer(level, mob.damageSources().source(NeoForgeMod.POISON_DAMAGE), 5);

    }
}
