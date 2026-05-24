package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TraitProperties
        (
                String group,
                int level,
                int color
        )
{

    public static final Codec<TraitProperties> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("effect_group").forGetter(TraitProperties::group),
                    Codec.INT.fieldOf("level").forGetter(TraitProperties::level),
                    Codec.INT.fieldOf("color").forGetter(TraitProperties::color)
            ).apply(instance, TraitProperties::new));





}
