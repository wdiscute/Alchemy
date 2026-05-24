package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DGAlchemyDataMapsProvider extends DataMapProvider
{
    protected DGAlchemyDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider)
    {
        var reagents = this.builder(AlchemyDataMaps.REAGENT_PROPERTIES);

        HolderLookup.RegistryLookup<TraitProperties> traits =
                provider.lookupOrThrow(Alchemy.TRAIT_REGISTRY_KEY);


        reagents.add(Items.PINK_TULIP.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGAlchemyTraits.FORTIFY
                ), false);

        reagents.add(Items.LILAC.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGAlchemyTraits.MAJOR_VITALITY
                ), false);

        reagents.add(Items.LILY_OF_THE_VALLEY.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGAlchemyTraits.FORTIFY
                ), false);




        var requirements = this.builder(AlchemyDataMaps.POTION_EFFECT_REQUIREMENTS);

        //vitality?
        requirements.add(MobEffects.REGENERATION, List.of(new EffectRequirement("vitality", 1)), false);
        requirements.add(MobEffects.RESISTANCE, List.of(new EffectRequirement("fortify", 1)), false);


    }
}
