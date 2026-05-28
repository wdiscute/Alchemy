package net.mcexpanded.alchemy.effects;

import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractAura extends MobEffect
{

    public final Holder<Attribute> attribute;
    public final Identifier modifier;
    final float value;
    final AttributeModifier.Operation operation;
    final AABB range;
    final int ticksDelay;


    public AbstractAura(Holder<Attribute> attribute, Identifier modifier, float value,
                        AttributeModifier.Operation operation, int ticksDelay, int range, int color)
    {

        this.attribute = attribute;
        this.modifier = modifier;
        this.value = value;
        this.operation = operation;
        this.range = new AABB(-range, -range, -range, range, range, range);
        this.ticksDelay = ticksDelay;

        super(MobEffectCategory.NEUTRAL, color);
    }

    public AbstractAura(Holder<Attribute> attribute, Identifier modifier, float value,
                        AttributeModifier.Operation operation, int ticksDelay, AABB range, int color)
    {

        this.attribute = attribute;
        this.modifier = modifier;
        this.value = value;
        this.operation = operation;
        this.range = range;
        this.ticksDelay = ticksDelay;

        super(MobEffectCategory.NEUTRAL, color);
    }

    public abstract Map<LivingEntity, List<LivingEntity>> getMap();

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification)
    {
        AABB move = new AABB(range.minX, range.minY, range.minZ, range.maxX, range.maxY, range.maxZ).move(mob.position());

        //get entities in range
        List<Entity> entitiesInRange = level.getEntities(mob, move, (d) -> d instanceof Entity);

        //for each entity in range
        entitiesInRange.forEach(o ->
        {
            //if living entity
            if (o instanceof LivingEntity le)
            {
                //if doesn't have modifier already, apply slowness modifier
                if (!le.getAttribute(attribute).hasModifier(modifier))
                {
                    le.getAttribute(attribute).addOrUpdateTransientModifier(new AttributeModifier(modifier, value, operation));

                    List<LivingEntity> listOfAffectedEntities = getMap().getOrDefault(mob, new ArrayList<>());

                    //add entity to list of entites with debuff to remove when potion effect ends/entity goes out of range
                    listOfAffectedEntities.add(le);
                    getMap().put(mob, listOfAffectedEntities);
                }
            }
            else
            {
                //if not a living entity, slow delta movement for projectiles etc
                o.setDeltaMovement(o.getDeltaMovement().scale(0.9f));
            }

            //if any entity in list of affected entities is no longer in range, remove debuff
            List<LivingEntity> listOfAffectedEntities = getMap().getOrDefault(mob, new ArrayList<>());
            listOfAffectedEntities.stream().filter(entity -> !entitiesInRange.contains(entity))
                    .forEach(e -> e.getAttribute(attribute).removeModifier(modifier));

            level.sendParticles(ParticleTypes.ASH, o.getX(), o.getY() + 0.5f, o.getZ(), 2, 0.7f, 0.5f, 0.7f, 0);

            //needs sync so it doesn't rubberband on client
            o.needsSync = true;
        });

        return true;
    }

    @Override
    public void onMobRemoved(ServerLevel level, LivingEntity mob, int amplifier, Entity.RemovalReason reason)
    {
        //remove all modifiers applied from auras when aura effect is removed
        List<LivingEntity> listOfAffectedEntities = getMap().getOrDefault(mob, new ArrayList<>());
        listOfAffectedEntities.stream().filter(Objects::nonNull).forEach(
                e -> e.getAttribute(attribute).removeModifier(modifier));
        getMap().remove(mob);

        super.onMobRemoved(level, mob, amplifier, reason);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification)
    {
        return tickCount % ticksDelay == 0;
    }
}
