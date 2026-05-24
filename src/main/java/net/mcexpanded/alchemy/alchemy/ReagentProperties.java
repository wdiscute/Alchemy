package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.Arrays;
import java.util.List;

public record ReagentProperties
        (
                List<Holder<TraitProperties>> traits,
                int temperature,
                int dilute
        )
{

    public ReagentProperties(int temperature, int dilute, TraitProperties... traits)
    {
        this(Arrays.stream(traits).map(Holder::direct).toList(), temperature, dilute);
    }

    public static final Codec<ReagentProperties> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryFileCodec.create(Alchemy.TRAIT_REGISTRY_KEY, TraitProperties.CODEC).listOf()
                            .fieldOf("traits").forGetter(ReagentProperties::traits),
                    Codec.INT.fieldOf("temperature").forGetter(ReagentProperties::temperature),
                    Codec.INT.fieldOf("dilute").forGetter(ReagentProperties::dilute)
            ).apply(instance, ReagentProperties::new));


}
