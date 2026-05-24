package net.mcexpanded.alchemy;

import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
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




    }
}
