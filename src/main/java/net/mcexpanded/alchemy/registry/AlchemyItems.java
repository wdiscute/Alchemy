package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AlchemyItems
{
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alchemy.MOD_ID);

    DeferredItem<Item> FROG_LEG = ITEMS.registerItem("frog_leg", Item::new);

    static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }
}
