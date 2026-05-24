package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

import java.util.List;

public interface AlchemyDataMaps
{
    DataMapType<Item, ReagentProperties> REAGENT_PROPERTIES = DataMapType.builder(
            Alchemy.rl("reagent_properties"), Registries.ITEM, ReagentProperties.CODEC)
            .synced(ReagentProperties.CODEC, true).build();

    DataMapType<MobEffect, List<EffectRequirement>> POTION_EFFECT_REQUIREMENTS = DataMapType.builder(
                    Alchemy.rl("potion_effect_requirements"), Registries.MOB_EFFECT, EffectRequirement.LIST_CODEC)
            .synced(EffectRequirement.LIST_CODEC, true).build();

    static ReagentProperties get(ItemStack stack)
    {
        return stack.typeHolder().getData(REAGENT_PROPERTIES);
    }

}
