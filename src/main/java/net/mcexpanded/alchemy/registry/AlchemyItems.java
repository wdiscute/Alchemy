package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.potion.DrinkablePotionItem;
import net.mcexpanded.alchemy.potion.SprayPotionItem;
import net.mcexpanded.alchemy.potion.ThrowablePotionItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AlchemyItems
{
    DeferredRegister.Items ITEMS = DeferredRegister.createItems(Alchemy.MOD_ID);

    //flasks
    DeferredItem<Item> ROUND_FLASK = ITEMS.registerItem("round_flask", DrinkablePotionItem::new);
    DeferredItem<Item> CUBIC_FLASK = ITEMS.registerItem("cubic_flask", ThrowablePotionItem::new);
    DeferredItem<Item> CONICAL_FLASK = ITEMS.registerItem("conical_flask", SprayPotionItem::new);

    DeferredItem<Item> FROG_LEG = ITEMS.registerItem("frog_leg", Item::new);
    DeferredItem<Item> LIZARD_TAIL = ITEMS.registerItem("lizard_tail", Item::new);

    static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }
}
