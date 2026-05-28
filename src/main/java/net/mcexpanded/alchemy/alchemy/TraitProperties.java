package net.mcexpanded.alchemy.alchemy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.sounds.SoundEvent;

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

    public static final StreamCodec<ByteBuf, TraitProperties> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, TraitProperties::group,
            ByteBufCodecs.INT, TraitProperties::level,
            ByteBufCodecs.INT, TraitProperties::color,
            TraitProperties::new
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<TraitProperties>> STREAM_CODEC = ByteBufCodecs.holder(
            Alchemy.TRAIT_REGISTRY_KEY, DIRECT_STREAM_CODEC
    );}
