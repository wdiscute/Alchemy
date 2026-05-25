package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static net.mcexpanded.alchemy.registry.AlchemyItems.*;

public class DGAlchemyModelProvider extends ModelProvider
{
    public DGAlchemyModelProvider(PackOutput output)
    {
        super(output, Alchemy.MOD_ID);
    }

    private ItemModelGenerators itemModels = null;
    private BlockModelGenerators blockModels = null;

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks()
    {
        List<Holder<Block>> list = new ArrayList<>();

        return list.stream();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems()
    {
        List<Holder<Item>> list = new ArrayList<>();

        list.addAll(ITEMS.getEntries().stream().toList());

        return list.stream();
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels)
    {
        this.blockModels = blockModels;
        this.itemModels = itemModels;

        //non bucket fishes
        simpleItem(ROUND_FLASK);
        simpleItem(CONICAL_FLASK);
        simpleItem(CUBIC_FLASK);

        simpleItem(LIZARD_TAIL);
        simpleItem(FROG_LEG);


    }

    private void simpleItem(DeferredItem<? extends Item> item)
    {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    private void simpleBlockItem(Holder<Block> block)
    {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block.value(),
                        BlockModelGenerators.plainVariant(Alchemy.rl("block/" + block.getKey().identifier().getPath())))
                .with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }
}
