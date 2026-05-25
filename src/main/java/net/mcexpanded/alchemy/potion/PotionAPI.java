package net.mcexpanded.alchemy.potion;

import com.mojang.datafixers.util.Pair;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionAPI
{
    public static ItemStack craftPotion(ItemStack reagent, ItemStack reagent2, ItemStack reagent3, ItemStack flask)
    {
        //if flask already has potion data, return
        if(flask.has(AlchemyDataComponents.POTION_DATA)) return null;

        //get list of available traits + level from reagents
        List<Pair<String, Integer>> availableTraits = getAvailableTraits(reagent, reagent2, reagent3);

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


        //if no matching effects, return flask
        if(matchingEffects.isEmpty()) return null;

        //map available effects to potion data
        List<PotionData> potionData = matchingEffects.stream().map(
                e -> new PotionData(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(e), 100, 1)).toList();

        ItemStack toReturn = flask.copyWithCount(1);
        toReturn.set(AlchemyDataComponents.POTION_DATA, potionData);

        return toReturn;
    }

    public static List<Pair<String, Integer>> getAvailableTraits(ItemStack reagent, ItemStack reagent2, ItemStack reagent3)
    {
        //get traits, return if either item doesn't have traits
        ReagentProperties reagentProperties = AlchemyDataMaps.get(reagent);
        if (reagentProperties == null) return List.of();

        ReagentProperties reagentProperties2 = AlchemyDataMaps.get(reagent2);
        if (reagentProperties2 == null) return List.of();

        ReagentProperties reagentProperties3 = AlchemyDataMaps.get(reagent3);
        if (reagentProperties3 == null) return List.of();

        return getAvailableTraits(reagentProperties, reagentProperties2, reagentProperties3);
    }

    public static List<Pair<String, Integer>> getAvailableTraits(ReagentProperties reagent1, ReagentProperties reagent2, ReagentProperties reagent3)
    {
        //convert reagent traits into a map
        Map<String, Integer> traits1 = new HashMap<>();
        reagent1.traits().forEach(o -> traits1.put(o.value().group(), o.value().level()));

        Map<String, Integer> traits2 = new HashMap<>();
        reagent2.traits().forEach(o -> traits2.put(o.value().group(), o.value().level()));

        Map<String, Integer> traits3 = new HashMap<>();
        reagent3.traits().forEach(o -> traits3.put(o.value().group(), o.value().level()));


        Map<String, Integer> availableTraits = new HashMap<>();

        for (Map.Entry<String, Integer> entry : traits1.entrySet())
        {
            String group = entry.getKey();
            //if trait is on all 3 reagents
            if (traits2.containsKey(group) && traits3.containsKey(group))
            {
                //put in map with lowest level + 1
                availableTraits.put(group,
                        Math.min(
                                Math.min(
                                        traits3.get(group),
                                        traits2.get(group)
                                ),
                                entry.getValue()) + 1
                );
                continue;
            }

            //if trait is in second reagent only, put in map with lowest level
            if (traits2.containsKey(group))
                availableTraits.put(group, Math.min(entry.getValue(), traits2.get(group)));

            //if trait is in third reagent only, put in map with lowest level
            if (traits3.containsKey(group))
                availableTraits.put(group, Math.min(entry.getValue(), traits3.get(group)));
        }

        for (Map.Entry<String, Integer> entry : traits2.entrySet())
        {
            String group = entry.getKey();
            //if group already on map, continue as it can't also be on third reagent
            if(availableTraits.containsKey(group)) continue;

            //if third reagent contains group, means that first didn't contain it so we must add it to the map
            if(traits3.containsKey(group))
                availableTraits.put(group, Math.min(entry.getValue(), traits3.get(group)));
        }

        List<Pair<String, Integer>> toReturn = new ArrayList<>();
        availableTraits.forEach((k, v) -> toReturn.add(Pair.of(k, v)));
        return toReturn;
    }
}
