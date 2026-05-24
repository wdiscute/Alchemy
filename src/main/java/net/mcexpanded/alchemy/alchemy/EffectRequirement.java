package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record EffectRequirement
        (
                String group,
                int level
        )
{

        public static final Codec<EffectRequirement> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("group").forGetter(EffectRequirement::group),
                Codec.INT.fieldOf("level").forGetter(EffectRequirement::level)
        ).apply(instance, EffectRequirement::new));

        public static final Codec<List<EffectRequirement>> LIST_CODEC = CODEC.listOf();
}
