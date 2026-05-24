package net.mcexpanded.alchemy;

import com.mojang.datafixers.util.Pair;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
    }


    public static ItemStack craftPotion(ItemStack reagent, ItemStack reagent2, ItemStack flask)
    {
        //get list of available traits + level from reagents
        List<Pair<String, Integer>> availableTraits = getAvailableTraits(reagent, reagent2);

        List<MobEffect> matchingEffects = new ArrayList<>();

        //for every mob effect
        BuiltInRegistries.MOB_EFFECT.forEach(me ->
        {
            //if mobEffect has a datamap entry
            List<EffectRequirement> effectRequirements = AlchemyDataMaps.get(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(me));
            if (effectRequirements != null)
            {
                //if all requirements from datamap match, add mobEffect to list of matching Effects
                if (effectRequirements.stream().allMatch(
                        o -> availableTraits.stream().anyMatch(
                                t -> t.getFirst().equals(o.group()) && t.getSecond() >= o.level())))
                {
                    matchingEffects.add(me);
                    System.out.println(me.getDescriptionId());
                }
            }
        });


        System.out.println(matchingEffects);
        return flask;
    }

    public static List<Pair<String, Integer>> getAvailableTraits(ItemStack reagent, ItemStack reagent2)
    {
        //get traits, return if either item doesn't have traits
        ReagentProperties reagentProperties = AlchemyDataMaps.get(reagent);
        if (reagentProperties == null) return List.of();
        ReagentProperties reagentProperties2 = AlchemyDataMaps.get(reagent2);
        if (reagentProperties2 == null) return List.of();
        return getAvailableTraits(reagentProperties, reagentProperties2);

    }

    public static List<Pair<String, Integer>> getAvailableTraits(ReagentProperties reagent, ReagentProperties reagent2)
    {
        List<TraitProperties> traits = reagent.traits().stream().map(Holder::value).toList();
        List<TraitProperties> traits2 = reagent2.traits().stream().map(Holder::value).toList();

        List<Pair<String, Integer>> list = new ArrayList<>();


        for (TraitProperties trait1 : traits)
        {
            for (TraitProperties trait2 : traits2)
            {
                //if found matching trait
                if (trait1.group().equals(trait2.group()))
                {
                    //add trait group to list with lowest level of trait
                    list.add(Pair.of(trait1.group(), Math.min(trait1.level(), trait2.level())));
                    break;
                }
            }
        }

        return list;
    }


}
