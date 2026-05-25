package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.station.StationBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface AlchemyBlockEntities
{
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Alchemy.MOD_ID);


    Supplier<BlockEntityType<StationBlockEntity>> STATION =
            BLOCK_ENTITIES.register("station",
                    () -> new BlockEntityType<>(StationBlockEntity::new,
                            AlchemyBlocks.STATION.get()
                    ));


    static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
