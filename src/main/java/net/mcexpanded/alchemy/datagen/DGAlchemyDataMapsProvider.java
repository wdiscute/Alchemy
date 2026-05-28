package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.alchemy.PotionEffectProperties;
import net.mcexpanded.alchemy.alchemy.TraitRequirement;
import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.mcexpanded.alchemy.registry.AlchemyMobEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DGAlchemyDataMapsProvider extends DataMapProvider
{
    protected DGAlchemyDataMapsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider)
    {
        super(packOutput, lookupProvider);
    }

    private void decay(HolderLookup.Provider provider)
    {
        var decay = this.builder(AlchemyDataMaps.DECAY_CONVERSION);

        decay.add(Blocks.GRASS_BLOCK.builtInRegistryHolder(), Blocks.DIRT.defaultBlockState(), false);
        decay.add(Blocks.DIRT.builtInRegistryHolder(), Blocks.COARSE_DIRT.defaultBlockState(), false);

        decay.add(BlockTags.FLOWERS, Blocks.DEAD_BUSH.defaultBlockState(), false);

        decay.add(Blocks.FERN.builtInRegistryHolder(), Blocks.SHORT_DRY_GRASS.defaultBlockState(), false);
        decay.add(Blocks.BUSH.builtInRegistryHolder(), Blocks.SHORT_DRY_GRASS.defaultBlockState(), false);
        decay.add(Blocks.SHORT_GRASS.builtInRegistryHolder(), Blocks.SHORT_DRY_GRASS.defaultBlockState(), false);
        decay.add(Blocks.TALL_GRASS.builtInRegistryHolder(), Blocks.TALL_DRY_GRASS.defaultBlockState(), false);

        decay.add(BlockTags.LEAVES, Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.VINE.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);

        decay.add(Blocks.MOSSY_COBBLESTONE.builtInRegistryHolder(), Blocks.COBBLESTONE.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_COBBLESTONE_SLAB.builtInRegistryHolder(), Blocks.COBBLESTONE_SLAB.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_COBBLESTONE_STAIRS.builtInRegistryHolder(), Blocks.COBBLESTONE_STAIRS.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_COBBLESTONE_WALL.builtInRegistryHolder(), Blocks.COBBLESTONE_WALL.defaultBlockState(), false);

        decay.add(Blocks.MOSSY_STONE_BRICK_SLAB.builtInRegistryHolder(), Blocks.STONE_BRICK_SLAB.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_STONE_BRICK_STAIRS.builtInRegistryHolder(), Blocks.STONE_BRICK_STAIRS.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_STONE_BRICK_WALL.builtInRegistryHolder(), Blocks.STONE_BRICK_WALL.defaultBlockState(), false);
        decay.add(Blocks.MOSSY_STONE_BRICKS.builtInRegistryHolder(), Blocks.STONE_BRICKS.defaultBlockState(), false);


        decay.add(Blocks.BAMBOO.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);

        decay.add(Blocks.RED_MUSHROOM.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.BROWN_MUSHROOM.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);

        decay.add(Blocks.WHEAT.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.CARROTS.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.BEETROOTS.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.POTATOES.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);

        decay.add(Blocks.SUGAR_CANE.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);

        decay.add(Blocks.MELON.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.MELON_STEM.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.PUMPKIN.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
        decay.add(Blocks.PUMPKIN_STEM.builtInRegistryHolder(), Blocks.AIR.defaultBlockState(), false);
    }

    public void potions(HolderLookup.Provider provider)
    {
        var requirements = this.builder(AlchemyDataMaps.POTION_EFFECT_PROPERTIES);

        //health recovery aka instant health
        requirements.add(
                AlchemyMobEffects.HEALTH_RECOVERY,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.VITALITY, 1, true)
                        ),
                        100,
                        0
                ), false);

        //sustained health recovery aka regeneration
        requirements.add(
                AlchemyMobEffects.SUSTAINED_HEALTH_RECOVERY,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.VITALITY, 1, true),
                                new TraitRequirement(TraitGroups.SUSTAINABILITY, 1, false)
                        ),
                        900,
                        0
                ), false);

        //max health aka health boost
        requirements.add(
                AlchemyMobEffects.MAX_HEALTH,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.VITALITY, 1, true),
                                new TraitRequirement(TraitGroups.PERSISTENCE, 1, false)
                        ),
                        900,
                        0
                ), false);

        //poison aka poison
        requirements.add(
                AlchemyMobEffects.POISON,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.TOXICITY, 1, true),
                                new TraitRequirement(TraitGroups.SUSTAINABILITY, 1, false)
                        ),
                        900,
                        0
                ), false);

        //disease damage aka instant damage but tagged as poison damage
        requirements.add(
                AlchemyMobEffects.DISEASE_DAMAGE,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.TOXICITY, 1, true)
                        ),
                        900,
                        0
                ), false);

        //aura of decay, transforms blocks around into decayed version
        requirements.add(
                AlchemyMobEffects.AURA_OF_DECAY,
                new PotionEffectProperties(
                        List.of(
                                new TraitRequirement(TraitGroups.DECAY, 1, true),
                                new TraitRequirement(TraitGroups.RADIANCE, 1, false)
                        ),
                        900,
                        0
                ), false);
    }

    public void reagents(HolderLookup.Provider provider)
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

        reagents.add(Items.WITHER_SKELETON_SKULL.builtInRegistryHolder(),
                new ReagentProperties(
                        traits,
                        1, 1,
                        DGTraits.MAJOR_DECAY,
                        DGTraits.MAJOR_HARM,
                        DGTraits.RADIANCE,
                        DGTraits.MAJOR_SCORCH
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
    }


    @Override
    protected void gather(HolderLookup.Provider provider)
    {

        potions(provider);
        reagents(provider);
        decay(provider);


    }
}
