package net.mcexpanded.alchemy.effects;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.NeoForgeMod;

public class HealthRecovery extends MobEffect
{
    public HealthRecovery()
    {
        super(MobEffectCategory.BENEFICIAL, 0xffdb2828);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification)
    {
        if(level.isClientSide()) return true;

        //non undead
        if(!mob.isInvertedHealAndHarm())
        {
            if (mob.getHealth() < mob.getMaxHealth())
                mob.heal(1.0F);
        }
        //undead
        else
        {
            if (mob.getHealth() < mob.getMaxHealth())
                mob.hurtServer(level, mob.damageSources().source(NeoForgeMod.POISON_DAMAGE), 1.0f);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification)
    {
        return tickCount % 10 == 0;
    }
}
