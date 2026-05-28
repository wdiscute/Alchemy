package net.mcexpanded.alchemy;

import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
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
        AlchemyMobEffects.register(modEventBus);
        AlchemyDataAttachments.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.CLIENT, AlchemyConfig.SPEC_SERVER);
    }
}
