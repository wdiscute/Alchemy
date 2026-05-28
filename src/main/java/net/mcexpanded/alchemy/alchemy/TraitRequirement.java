package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;

public record TraitRequirement
        (
                String group,
                int level,
                boolean higherLevelsAmplifyEffect
        )
{

        public static final Codec<TraitRequirement> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.STRING.fieldOf("group").forGetter(TraitRequirement::group),
                Codec.INT.fieldOf("level").forGetter(TraitRequirement::level),
                Codec.BOOL.fieldOf("higher_levels_amplify_effect").forGetter(TraitRequirement::higherLevelsAmplifyEffect)
        ).apply(instance, TraitRequirement::new));

        public static final Codec<List<TraitRequirement>> LIST_CODEC = CODEC.listOf();
}
