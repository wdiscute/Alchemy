package net.mcexpanded.alchemy.potion;

import com.mojang.datafixers.util.Pair;
import net.mcexpanded.alchemy.alchemy.PotionEffectProperties;
import net.mcexpanded.alchemy.alchemy.TraitRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataAttachments;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PotionAPI
{
    public static ItemStack craftPotion(ItemStack reagent1, ItemStack reagent2, ItemStack reagent3, ItemStack flask)
    {
        //if flask already has potion data, return
        if (flask.has(AlchemyDataComponents.POTION_DATA)) return null;
        if (reagent2.isEmpty()) return null;
        if (reagent3.isEmpty()) return null;
        if (flask.isEmpty()) return null;

        if (reagent1.is(reagent2.getItem())) return null;
        if (reagent1.is(reagent3.getItem())) return null;
        if (reagent2.is(reagent3.getItem())) return null;

        //get list of available traits + level from reagents
        List<Pair<String, Integer>> availableTraits = getAvailableTraits(reagent1, reagent2, reagent3);

        List<PotionData> matchingEffects = new ArrayList<>();

        //for every mob effect
        BuiltInRegistries.MOB_EFFECT.forEach(me ->
        {
            //if mobEffect has a datamap entry
            PotionEffectProperties potionEffectProperties = AlchemyDataMaps.get(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(me));
            if (potionEffectProperties == null) return;
            List<TraitRequirement> effectRequirements = potionEffectProperties.requirements();
            if (effectRequirements != null)
            {
                //if all requirements from datamap match, add mobEffect to list of matching Effects
                if (effectRequirements.stream().allMatch(
                        o -> availableTraits.stream().anyMatch(
                                t -> t.getFirst().equals(o.group()) && t.getSecond() >= o.level())))
                {
                    int amp = 0;
                    int duration = 0;

                    //for each req that amplifies based on level
                    for (TraitRequirement req : effectRequirements.stream().filter(TraitRequirement::higherLevelsAmplifyEffect).toList())
                    {
                        //for each trait that matches the req group (should always only be 1)
                        List<Pair<String, Integer>> list = availableTraits.stream().filter(o -> o.getFirst().equals(req.group())).toList();

                        //add the extra levels to the amp
                        for (Pair<String, Integer> trait : list)
                            amp += trait.getSecond() - req.level();
                    }


                    //for each req that adds ticks to duration based on level
                    for (TraitRequirement req : effectRequirements.stream().filter(TraitRequirement::higherLevelsAmplifyEffect).toList())
                    {
                        //for each trait that matches the req group (should always only be 1)
                        List<Pair<String, Integer>> list = availableTraits.stream().filter(o -> o.getFirst().equals(req.group())).toList();

                        //add the extra levels to the amp
                        for (Pair<String, Integer> trait : list)
                            duration += (trait.getSecond() - req.level()) * req.higherLevelsAddTicksToDuration();
                    }

                    PotionData potionData = new PotionData(
                            BuiltInRegistries.MOB_EFFECT.wrapAsHolder(me),
                            potionEffectProperties.duration() + duration,
                            potionEffectProperties.level() + amp
                            );

                    matchingEffects.add(potionData);
                }
            }
        });

        //if no matching effects, return flask
        if (matchingEffects.isEmpty()) return null;


        //testing
        //potionData.add(new PotionData(MobEffects.HASTE, 1, 1));
        //potionData.add(new PotionData(MobEffects.SLOW_FALLING, 1, 1));


        ItemStack toReturn = flask.copyWithCount(1);
        toReturn.set(AlchemyDataComponents.POTION_DATA, matchingEffects);

        return toReturn;
    }

    public static void awardTraitKnowledge(ItemStack resultPotion, ItemStack r1, ItemStack r2, ItemStack r3, Player player)
    {
        List<PotionData> potionData = AlchemyDataComponents.getOrDefault(resultPotion, AlchemyDataComponents.POTION_DATA, List.of());
        if (potionData.isEmpty()) return;
        Map<Item, List<String>> knownTraitsMap = new HashMap<>(player.getData(AlchemyDataAttachments.KNOWN_TRAITS_MAP));

        List<String> knownTraits1 = new ArrayList<>(knownTraitsMap.getOrDefault(r1.getItem(), new ArrayList<>()));
        List<String> knownTraits2 = new ArrayList<>(knownTraitsMap.getOrDefault(r2.getItem(), new ArrayList<>()));
        List<String> knownTraits3 = new ArrayList<>(knownTraitsMap.getOrDefault(r3.getItem(), new ArrayList<>()));

        for (PotionData potion : potionData)
        {
            List<String> learntTraits = AlchemyDataMaps.get(potion.effect()).requirements().stream().map(TraitRequirement::group).toList();

            List<String> traitsOfReagent1 = AlchemyDataMaps.get(r1).traits().stream().map(o -> o.value().group()).toList();
            List<String> traitsOfReagent2 = AlchemyDataMaps.get(r2).traits().stream().map(o -> o.value().group()).toList();
            List<String> traitsOfReagent3 = AlchemyDataMaps.get(r3).traits().stream().map(o -> o.value().group()).toList();

            //for each required trait
            for (String learntTrait : learntTraits)
            {
                //if reg1 contains learnt trait, and it's not already known
                if (traitsOfReagent1.contains(learntTrait) && !knownTraits1.contains(learntTrait))
                {
                    knownTraits1.add(learntTrait);
                    knownTraitsMap.put(r1.getItem(), knownTraits1);
                }

                if (traitsOfReagent2.contains(learntTrait) && !knownTraits2.contains(learntTrait))
                {
                    knownTraits2.add(learntTrait);
                    knownTraitsMap.put(r2.getItem(), knownTraits2);
                }

                if (traitsOfReagent3.contains(learntTrait) && !knownTraits3.contains(learntTrait))
                {
                    knownTraits3.add(learntTrait);
                    knownTraitsMap.put(r3.getItem(), knownTraits3);
                }

                player.setData(AlchemyDataAttachments.KNOWN_TRAITS_MAP, knownTraitsMap);
            }
        }
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
            if (availableTraits.containsKey(group)) continue;

            //if third reagent contains group, means that first didn't contain it so we must add it to the map
            if (traits3.containsKey(group))
                availableTraits.put(group, Math.min(entry.getValue(), traits3.get(group)));
        }

        List<Pair<String, Integer>> toReturn = new ArrayList<>();
        availableTraits.forEach((k, v) -> toReturn.add(Pair.of(k, v)));
        return toReturn;
    }

    public static void awardEffectKnowledge(ItemStack itemStack, Player player)
    {
        List<Holder<MobEffect>> knownEffects = new ArrayList<>(player.getData(AlchemyDataAttachments.KNOWN_EFFECTS));

        for (PotionData potionData : AlchemyDataComponents.getOrDefault(itemStack, AlchemyDataComponents.POTION_DATA, List.of()))
            if (!knownEffects.contains(potionData.effect()))
                knownEffects.add(potionData.effect());

        if (!knownEffects.equals(player.getData(AlchemyDataAttachments.KNOWN_EFFECTS)))
            player.setData(AlchemyDataAttachments.KNOWN_EFFECTS, knownEffects);
    }
}
