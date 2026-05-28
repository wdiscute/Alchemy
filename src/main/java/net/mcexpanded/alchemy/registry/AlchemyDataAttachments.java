package net.mcexpanded.alchemy.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class AlchemyDataAttachments
{
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, Alchemy.MOD_ID);

    public static final MapCodec<Map<Item, List<String>>> KNOWN_TRAITS_MAP_CODEC =
            Codec.unboundedMap(
                    BuiltInRegistries.ITEM.byNameCodec(),
                    Codec.STRING.listOf()
            ).fieldOf("known_traits_for_item");


    public static final Supplier<AttachmentType<Map<Item, List<String>>>> KNOWN_TRAITS_MAP = ATTACHMENT_TYPES.register(
            "known_traits_map", () -> AttachmentType.builder(Map::<Item, List<String>>of)
                    .sync(ByteBufCodecs.map(HashMap::new, ByteBufCodecs.registry(Registries.ITEM), ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list())))
                    .serialize(KNOWN_TRAITS_MAP_CODEC)
                    .build()
    );

    public static final MapCodec<List<Holder<MobEffect>>> KNOWN_EFFECTS_MAP_CODEC =
            BuiltInRegistries.MOB_EFFECT.holderByNameCodec().listOf().fieldOf("known_effects");

    public static final Supplier<AttachmentType<List<Holder<MobEffect>>>> KNOWN_EFFECTS = ATTACHMENT_TYPES.register(
            "known_effects", () -> AttachmentType.builder(() -> List.<Holder<MobEffect>>of())
                    .sync(MobEffect.STREAM_CODEC.apply(ByteBufCodecs.list()))
                    .serialize(KNOWN_EFFECTS_MAP_CODEC)
                    .build()
    );

    // sets the value to default
    public static <T> void remove(Entity holder, Supplier<AttachmentType<T>> attachmentType)
    {
        if(holder == null) return;
        holder.removeData(attachmentType);
    }

    // sets the value to default
    public static <T> void remove(Entity holder, AttachmentType<T> attachmentType)
    {
        if(holder == null) return;
        holder.removeData(attachmentType);
    }

    public static <T> void set(Entity holder, Supplier<AttachmentType<T>> attachmentType, T data)
    {
        if(holder == null) return;
        holder.setData(attachmentType, data);
    }

    public static <T> void set(Entity holder, AttachmentType<T> attachmentType, T data)
    {
        if(holder == null) return;
        holder.setData(attachmentType, data);
    }

    public static <T> T get(Entity holder, Supplier<AttachmentType<T>> attachmentType)
    {
        if(holder == null) throw new RuntimeException("Called Starcatcher DataAttachments Get() with a null entity");
        return holder.getData(attachmentType);
    }

    public static <T> T get(Entity holder, AttachmentType<T> attachmentType)
    {
        if(holder == null) throw new RuntimeException("Called Starcatcher DataAttachments Get() with a null entity");
        return holder.getData(attachmentType);
    }

    public static void register(IEventBus eventBus)
    {
        ATTACHMENT_TYPES.register(eventBus);
    }

}
