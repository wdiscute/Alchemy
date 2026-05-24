package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

public record TraitProperties
        (
                String group,
                int level,
                int color
        )
{

    public static final Codec<TraitProperties> DIRECT_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("effect_group").forGetter(TraitProperties::group),
                    Codec.INT.fieldOf("level").forGetter(TraitProperties::level),
                    Codec.INT.fieldOf("color").forGetter(TraitProperties::color)
            ).apply(instance, TraitProperties::new));

    public static final Codec<Holder<TraitProperties>> CODEC = RegistryFileCodec.create(Alchemy.TRAIT_REGISTRY_KEY, DIRECT_CODEC);

    public static TraitProperties fromId(Identifier identifier)
    {
        return null;
    }
}
