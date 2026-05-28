package net.mcexpanded.alchemy.effects;

import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.*;

public class AuraOfSlowness extends AbstractAura
{
    public AuraOfSlowness()
    {
        super(Attributes.MOVEMENT_SPEED,
                Alchemy.rl("aura_of_slowness"),
                -0.5f,
                Operation.ADD_MULTIPLIED_BASE,
                1,
                10,
                0xff000000
        );
    }

    private static final Map<LivingEntity, List<LivingEntity>> MAP = new HashMap<>();

    @Override
    public Map<LivingEntity, List<LivingEntity>> getMap()
    {
        return MAP;
    }
}
