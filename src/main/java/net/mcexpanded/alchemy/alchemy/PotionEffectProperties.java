package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record PotionEffectProperties
        (
                List<TraitRequirement> requirements,
                int duration,
                int level
        )
{
    public static final Codec<PotionEffectProperties> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    TraitRequirement.LIST_CODEC.fieldOf("traits_required").forGetter(PotionEffectProperties::requirements),
                    Codec.INT.fieldOf("duration_in_ticks").forGetter(PotionEffectProperties::duration),
                    Codec.INT.fieldOf("effect_level").forGetter(PotionEffectProperties::level)
            ).apply(instance, PotionEffectProperties::new));
}
