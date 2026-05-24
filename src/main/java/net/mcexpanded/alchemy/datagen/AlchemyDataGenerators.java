package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = Alchemy.MOD_ID)
public class AlchemyDataGenerators
{

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event)
    {
        event.createDatapackRegistryObjects(
                new RegistrySetBuilder()
                        .add(Alchemy.TRAIT_REGISTRY_KEY, DGAlchemyTraitPropertiesProvider::bootstrap)
        );

        //trait properties
        DGAlchemyTraitPropertiesProvider provider = event.createProvider(DGAlchemyTraitPropertiesProvider::new);

        //data maps
        event.addProvider(new DGAlchemyDataMapsProvider(event.getGenerator().getPackOutput(), provider.getRegistryProvider()));




        //DataGenerator gen = event.getGenerator();

        //CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        //PackOutput output = gen.getPackOutput();

        //item models
        //event.createProvider(DGSCModelProvider::new);

        //biome modifiers
        //event.createProvider(DGSCBiomeModifierProvider::new);

        //block tags
        //event.createProvider(DGSCBlocksTagsProvider::new);

        //item tags
        //event.createProvider(DGSCItemsTagsProvider::new);

        //fp tags
        //event.createProvider(DGSCFPTagsProvider::new);

        //biome tags
        //event.createProvider(DGSCBiomeTagsProvider::new);

        //advancements
        //gen.addProvider(event.includeServer(), new DGSCAdvancementProvider(output, lookupProvider, existingFileHelper));

        //loot modifiers
        //event.createProvider(DGSCLootModifiers::new);

        //loot table
        //gen.addProvider(true, new LootTableProvider(output, Collections.emptySet(),
        //        List.of(new LootTableProvider.SubProviderEntry(DGSCBlockLootTableProvider::new, LootContextParamSets.BLOCK)), lookupProvider));

        //recipes
        //event.getGenerator().addProvider(true, new DGSCRecipeProvider.Runner(output, lookupProvider));
    }
}
