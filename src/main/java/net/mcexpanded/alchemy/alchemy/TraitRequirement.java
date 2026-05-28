package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TraitRequirement
        (
                String group,
                int level
        )
{

        public static final Codec<TraitRequirement> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("group").forGetter(TraitRequirement::group),
                Codec.INT.fieldOf("level").forGetter(TraitRequirement::level)
        ).apply(instance, TraitRequirement::new));

        public static final Codec<List<TraitRequirement>> LIST_CODEC = CODEC.listOf();
}
