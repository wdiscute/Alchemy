package net.mcexpanded.alchemy.potion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;

import java.util.List;

public record PotionData(
        Holder<MobEffect> effect,
        int duration,
        int level
)
{
        public static final Codec<PotionData> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        MobEffect.CODEC.fieldOf("effect").forGetter(PotionData::effect),
                        Codec.INT.fieldOf("duration").forGetter(PotionData::duration),
                        Codec.INT.fieldOf("level").forGetter(PotionData::level)
                ).apply(instance, PotionData::new));

        public static final Codec<List<PotionData>> LIST_CODEC = CODEC.listOf();
}
