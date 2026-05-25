package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.EffectRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.List;
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

        //
        //,--.                     ,--.             ,---. ,--.
        //|  |-.   ,--,--.  ,---.  `--'  ,---.     /  .-' |  |  ,---.  ,--.   ,--.  ,---.  ,--.--.  ,---.
        //| .-. ' ' ,-.  | (  .-'  ,--. | .--'     |  `-, |  | | .-. | |  |.'.|  | | .-. : |  .--' (  .-'
        //| `-' | \ '-'  | .-'  `) |  | \ `--.     |  .-' |  | ' '-' ' |   .'.   | \   --. |  |    .-'  `)
        // `---'   `--`--' `----'  `--'  `---'     `--'   `--'  `---'  '--'   '--'  `----' `--'    `----'
        //

        reagents.add(Items.WHITE_TULIP.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.PURITY,
                        DGTraits.MINOR_DECAY
                ), false);

        reagents.add(Items.PINK_TULIP.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.SUSTAINABILITY,
                        DGTraits.MINOR_DEFILE
                ), false);

        reagents.add(Items.ORANGE_TULIP.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.MINOR_SWIFTNESS,
                        DGTraits.MINOR_VORACITY
                ), false);

        reagents.add(Items.RED_TULIP.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.MINOR_MIGHT,
                        DGTraits.MINOR_TOXICITY
                ), false);

        reagents.add(Items.LILY_OF_THE_VALLEY.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_TOXICITY,
                        DGTraits.MINOR_STEADY,
                        DGTraits.PERSISTENCE
                ), false);

        reagents.add(Items.GOLDEN_CARROT.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_MIGHT,
                        DGTraits.PERSISTENCE,
                        DGTraits.MAJOR_SATIETY,
                        DGTraits.PERCEPTION
                ), false);

        reagents.add(Items.ALLIUM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_VITALITY,
                        DGTraits.MINOR_HINDRANCE,
                        DGTraits.MINOR_TOXICITY
                ), false);

        reagents.add(Items.OXEYE_DAISY.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.PERCEPTION,
                        DGTraits.SUSTAINABILITY,
                        DGTraits.MINOR_DECAY,
                        DGTraits.MINOR_TOXICITY
                ), false);

        reagents.add(Items.CORNFLOWER.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.PERCEPTION,
                        DGTraits.MINOR_STEADY,
                        DGTraits.MINOR_HARM
                ), false);

        reagents.add(Items.ALLIUM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.SUSTAINABILITY,
                        DGTraits.MINOR_SWIFTNESS,
                        DGTraits.MAJOR_VORACITY
                ), false);

        reagents.add(Items.DANDELION.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_TOXICITY,
                        DGTraits.RADIANCE,
                        DGTraits.MINOR_SCORCH
                ), false);

        reagents.add(Items.SUNFLOWER.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_SCORCH,
                        DGTraits.RADIANCE,
                        DGTraits.MINOR_TOXICITY
                ), false);

        reagents.add(Items.POPPY.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.PERSISTENCE,
                        DGTraits.MINOR_SWIFTNESS
                ), false);

        reagents.add(Items.POPPY.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.PERSISTENCE,
                        DGTraits.MINOR_SWIFTNESS
                ), false);

        reagents.add(Items.BLUE_ORCHID.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VORACITY,
                        DGTraits.MINOR_HINDRANCE,
                        DGTraits.MINOR_SWIFTNESS,
                        DGTraits.MINOR_AQUOSITY
                ), false);

        reagents.add(Items.AZURE_BLUET.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.MINOR_DEFILE,
                        DGTraits.MINOR_HARM
                ), false);

        //
        //                               ,--.          ,--.      ,---. ,--.
        // ,---.   ,---.   ,---.   ,---. `--'  ,--,--. |  |     /  .-' |  |  ,---.  ,--.   ,--.  ,---.  ,--.--.  ,---.
        //(  .-'  | .-. | | .-. : | .--' ,--. ' ,-.  | |  |     |  `-, |  | | .-. | |  |.'.|  | | .-. : |  .--' (  .-'
        //.-'  `) | '-' ' \   --. \ `--. |  | \ '-'  | |  |     |  .-' |  | ' '-' ' |   .'.   | \   --. |  |    .-'  `)
        //`----'  |  |-'   `----'  `---' `--'  `--`--' `--'     `--'   `--'  `---'  '--'   '--'  `----' `--'    `----'
        //        `--'


        reagents.add(Items.CLOSED_EYEBLOSSOM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.HARM,
                        DGTraits.DEFILE,
                        DGTraits.TOXICITY
                ), false);

        reagents.add(Items.OPEN_EYEBLOSSOM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.VITALITY,
                        DGTraits.SATIETY,
                        DGTraits.SCORCH
                ), false);

        reagents.add(Items.WITHER_ROSE.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_DECAY,
                        DGTraits.MAJOR_TOXICITY,
                        DGTraits.MAJOR_HARM
                ), false);

        reagents.add(Items.TORCHFLOWER.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.SCORCH,
                        DGTraits.TOXICITY,
                        DGTraits.LUMINOSITY,
                        DGTraits.VORACITY
                ), false);

        reagents.add(Items.PITCHER_PLANT.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.SATIETY,
                        DGTraits.MAJOR_VITALITY,
                        DGTraits.SATIETY
                ), false);



        //
        //          ,--.   ,--.
        // ,---.  ,-'  '-. |  ,---.   ,---.  ,--.--.  ,---.
        //| .-. | '-.  .-' |  .-.  | | .-. : |  .--' (  .-'
        //' '-' '   |  |   |  | |  | \   --. |  |    .-'  `)
        // `---'    `--'   `--' `--'  `----' `--'    `----'
        //

        reagents.add(Items.GLOW_BERRIES.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.LUMINOSITY,
                        DGTraits.VITALITY,
                        DGTraits.MINOR_DEFILE
                ), false);

        reagents.add(Items.RED_MUSHROOM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_VITALITY,
                        DGTraits.MINOR_HINDRANCE,
                        DGTraits.MINOR_SATIETY
                ), false);

        reagents.add(Items.BROWN_MUSHROOM.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MINOR_TOXICITY,
                        DGTraits.MINOR_AQUOSITY,
                        DGTraits.MINOR_SATIETY
                ), false);

        reagents.add(Items.CRIMSON_FUNGUS.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.SCORCH,
                        DGTraits.LUMINOSITY,
                        DGTraits.STEADY
                ), false);

        reagents.add(Items.WARPED_FUNGUS.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.SCORCH,
                        DGTraits.DEFILE,
                        DGTraits.DECAY
                ), false);

        reagents.add(Items.SCULK.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.OBSCURITY,
                        DGTraits.MAJOR_DECAY
                ), false);

        reagents.add(Items.HONEY_BOTTLE.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.STICKINESS,
                        DGTraits.MAJOR_SATIETY
                ), false);

        reagents.add(Items.SLIME_BALL.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.STICKINESS
                ), false);

        reagents.add(Items.COBWEB.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_HINDRANCE,
                        DGTraits.MINOR_DECAY
                ), false);

        //golden items
        reagents.add(Items.GLISTERING_MELON_SLICE.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_MIGHT,
                        DGTraits.PERSISTENCE
                ), false);

        reagents.add(Items.GOLDEN_CARROT.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_VORACITY,
                        DGTraits.PERSISTENCE,
                        DGTraits.PERCEPTION
                ), false);









        var requirements = this.builder(AlchemyDataMaps.POTION_EFFECT_REQUIREMENTS);

        requirements.add(MobEffects.POISON, List.of(
                new EffectRequirement(TraitGroups.TOXICITY, 1),
                new EffectRequirement(TraitGroups.SUSTAINABILITY, 1)
        ), false);

        requirements.add(MobEffects.INSTANT_HEALTH, List.of(
                new EffectRequirement(TraitGroups.VITALITY, 1)
        ), false);

        requirements.add(MobEffects.REGENERATION, List.of(
                new EffectRequirement(TraitGroups.VITALITY, 1),
                new EffectRequirement(TraitGroups.SUSTAINABILITY, 1)
        ), false);

        requirements.add(MobEffects.HEALTH_BOOST, List.of(
                new EffectRequirement(TraitGroups.VITALITY, 1),
                new EffectRequirement(TraitGroups.PERSISTENCE, 1)
        ), false);




    }
}
