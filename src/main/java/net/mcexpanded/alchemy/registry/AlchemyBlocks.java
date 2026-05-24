package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.station.StationBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public interface AlchemyBlocks
{
    DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Alchemy.MOD_ID);

    //tackle boxes
    DeferredBlock<Block> STATION = register("station", (p) -> new StationBlock(null, MapColor.TERRACOTTA_WHITE, p));

    private static <T extends Block> DeferredBlock<T> register(String name, Function<BlockBehaviour.Properties, ? extends T> block)
    {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, block);
        AlchemyItems.ITEMS.registerItem(name, (p) -> new BlockItem(toReturn.get(), p
                .useBlockDescriptionPrefix()
                .setId(ResourceKey.create(Registries.ITEM, Alchemy.rl(name)))));
        return toReturn;
    }

    static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}