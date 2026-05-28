package net.mcexpanded.alchemy.effects;

import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class MaxHealth extends MobEffect
{
    public MaxHealth()
    {
        super(MobEffectCategory.BENEFICIAL, 0xfff67e75);
        addAttributeModifier(Attributes.MAX_HEALTH,
                Identifier.withDefaultNamespace("effect.health_boost"),
                4.0, AttributeModifier.Operation.ADD_VALUE);
    }
}
