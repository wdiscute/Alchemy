package net.mcexpanded.alchemy.effects;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.jspecify.annotations.Nullable;

public class AuraOfDecay extends MobEffect
{
    public AuraOfDecay()
    {
        super(MobEffectCategory.HARMFUL, 0xff29550d);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity mob, int amplification)
    {

        int x = level.getRandom().nextInt(6) - 3;
        int y = level.getRandom().nextInt(6) - 3;
        int z = level.getRandom().nextInt(6) - 3;

        BlockPos playerBp = mob.blockPosition();
        BlockPos.MutableBlockPos bp = playerBp.mutable().move(x, y, z);
        Block block = level.getBlockState(bp).getBlock();

        if (block.defaultBlockState().isAir())
            block = level.getBlockState(bp.move(0, -1, 0)).getBlock();

        if (block.defaultBlockState().isAir())
            block = level.getBlockState(bp.move(0, -1, 0)).getBlock();
        BlockState decayedState = AlchemyDataMaps.get(block);

        level.sendParticles(ParticleTypes.ASH, bp.getX(), bp.getY() + 2, bp.getZ(), 20,  1, 1, 1, 0);

        level.sendParticles(ParticleTypes.WHITE_ASH, playerBp.getX(), playerBp.getY() + 2, bp.getZ(), 20,
                5, 1, 5, 0);

        if (decayedState != null)
            level.setBlockAndUpdate(bp, decayedState);

        return true;
    }


    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplification)
    {
        return tickCount % 6 - Math.min(5, amplification) == 0;
    }
}
