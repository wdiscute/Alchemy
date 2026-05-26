package net.mcexpanded.alchemy.datagen;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.potion.item.PotionTintItemProperty;
import net.mcexpanded.alchemy.potion.item.PotionTypeItemProperty;
import net.minecraft.client.color.item.Potion;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        generatePotion(ROUND_FLASK.get());
        generatePotion(CONICAL_FLASK.get());
        generatePotion(CUBIC_FLASK.get());

        simpleItem(LIZARD_TAIL);
        simpleItem(FROG_LEG);
    }

    public static final ModelTemplate CROSSBOW = createItem("crossbow", TextureSlot.LAYER0);

    public static ModelTemplate createItem(String id, TextureSlot... slots)
    {
        return new ModelTemplate(Optional.of(ModelLocationUtils.decorateItemModelLocation(id)), Optional.empty(), slots);
    }

    public void generatePotion(Item item)
    {
//        ItemModel.Unbaked smallSimple = ItemModelUtils.tintedModel(itemModels.createFlatItemModel(item, "_small", ModelTemplates.FLAT_HANDHELD_ITEM));
//        ItemModel.Unbaked normalSimple = ItemModelUtils.tintedModel(itemModels.createFlatItemModel(item, "", ModelTemplates.FLAT_HANDHELD_ITEM));
        //ItemModel.Unbaked largeSimple = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, "_large", ModelTemplates.FLAT_HANDHELD_ITEM));


        Identifier emptyId = ModelTemplates.FLAT_HANDHELD_ITEM.create(
                BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_empty"),
                TextureMapping.layer0(
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix(""))
                ),
                itemModels.modelOutput
        );

        ItemModel.Unbaked empty = ItemModelUtils.plainModel(emptyId);


        Identifier smallId = ModelTemplates.TWO_LAYERED_ITEM.create(
                BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_small"),
                TextureMapping.layered(
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_small_overlay")),
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_small"))
                ),
                itemModels.modelOutput
        );

        ItemModel.Unbaked small = ItemModelUtils.tintedModel(smallId, new PotionTintItemProperty());

        Identifier normalId = ModelTemplates.TWO_LAYERED_ITEM.create(
                BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix(""),
                TextureMapping.layered(
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_overlay")),
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix(""))
                ),
                itemModels.modelOutput
        );

        ItemModel.Unbaked normal = ItemModelUtils.tintedModel(normalId, new PotionTintItemProperty());

        Identifier largeId = ModelTemplates.TWO_LAYERED_ITEM.create(
                BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_large"),
                TextureMapping.layered(
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_large_overlay")),
                        new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/").withSuffix("_large"))
                ),
                itemModels.modelOutput
        );

        ItemModel.Unbaked large = ItemModelUtils.tintedModel(largeId, new PotionTintItemProperty());


        this.itemModels.itemModelOutput
                .accept(
                        item,
                        ItemModelUtils.rangeSelect(
                                new PotionTypeItemProperty(), empty,
                                ItemModelUtils.override(small, 1f),
                                ItemModelUtils.override(normal, 2f),
                                ItemModelUtils.override(large, 3F)
                        )
                );


//        ItemModel.Unbaked normal = ItemModelUtils.tintedModel(itemModels.generateLayeredItem(
//                item, new Material(Alchemy.rl("item/potion_overlay")),
//                new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/"))).withSuffix("_normal"), new Potion());
//
//        ItemModel.Unbaked large = ItemModelUtils.tintedModel(itemModels.generateLayeredItem(
//                item, new Material(Alchemy.rl("item/potion_overlay")),
//                new Material(BuiltInRegistries.ITEM.getKey(item).withPrefix("item/"))).withSuffix("_large"), new Potion());





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
