package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.ResourceKey;

import java.util.Arrays;
import java.util.List;

public record ReagentProperties
        (
                List<Holder<TraitProperties>> traits,
                int temperature,
                int dilute
        )
{

    @SafeVarargs
    public ReagentProperties(HolderLookup.RegistryLookup<TraitProperties> registry, int temperature, int dilute, ResourceKey<TraitProperties>... traits)
    {
        this(Arrays.stream(traits).map(o -> (Holder<TraitProperties>) registry.getOrThrow(o)).toList(), temperature, dilute);
    }

    public static final Codec<ReagentProperties> CODEC =
            RecordCodecBuilder.create(instance ->
                    instance.group(
                            RegistryFixedCodec
                                    .create(Alchemy.TRAIT_REGISTRY_KEY)
                                    .listOf()
                                    .fieldOf("traits")
                                    .forGetter(ReagentProperties::traits),

                            Codec.INT.fieldOf("temperature")
                                    .forGetter(ReagentProperties::temperature),

                            Codec.INT.fieldOf("dilute")
                                    .forGetter(ReagentProperties::dilute)
                    ).apply(instance, ReagentProperties::new)
            );
}
