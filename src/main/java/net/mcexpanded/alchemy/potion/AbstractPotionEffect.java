package net.mcexpanded.alchemy.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class AbstractPotionEffect extends MobEffect
{
    protected AbstractPotionEffect(MobEffectCategory category, int color)
    {
        super(category, color);
    }
}
