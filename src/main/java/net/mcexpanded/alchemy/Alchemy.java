package net.mcexpanded.alchemy;

import com.mojang.datafixers.util.Pair;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.datagen.AlchemyDataGenerators;
import net.mcexpanded.alchemy.registry.*;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(Alchemy.MOD_ID)
public class Alchemy
{
    public static final String MOD_ID = "alchemy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ResourceKey<Registry<TraitProperties>> TRAIT_REGISTRY_KEY =
            ResourceKey.createRegistryKey(Alchemy.rl("traits"));

    public static Identifier rl(String path)
    {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public Alchemy(IEventBus modEventBus, ModContainer modContainer)
    {
        AlchemyBlocks.register(modEventBus);
        AlchemyItems.register(modEventBus);
        AlchemyBlockEntities.register(modEventBus);
        AlchemyMenuTypes.register(modEventBus);
        AlchemyDataComponents.register(modEventBus);
    }
}
