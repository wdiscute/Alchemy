package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.TraitRequirement;
import net.mcexpanded.alchemy.alchemy.PotionEffectProperties;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.minecraft.core.Holder;
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


    DataMapType<MobEffect, PotionEffectProperties> POTION_EFFECT_PROPERTIES = DataMapType.builder(
                    Alchemy.rl("potion_effect_requirements"), Registries.MOB_EFFECT, PotionEffectProperties.CODEC)
            .synced(PotionEffectProperties.CODEC, true).build();

    static ReagentProperties get(ItemStack stack)
    {
        return stack.typeHolder().getData(REAGENT_PROPERTIES);
    }

    static ReagentProperties get(Item stack)
    {
        return stack.builtInRegistryHolder().getData(REAGENT_PROPERTIES);
    }

    static PotionEffectProperties get(Holder<MobEffect> mobEffect)
    {
        return mobEffect.getData(POTION_EFFECT_PROPERTIES);
    }
}
