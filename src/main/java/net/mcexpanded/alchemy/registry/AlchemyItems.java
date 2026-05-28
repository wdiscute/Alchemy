package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.potion.item.DrinkablePotionItem;
import net.mcexpanded.alchemy.potion.item.SprayPotionItem;
import net.mcexpanded.alchemy.potion.item.ThrowablePotionItem;
import net.mcexpanded.alchemy.station.GuideScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
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

    DeferredItem<Item> GUIDE = ITEMS.registerItem("guide", (p) -> new Item(p)
    {
        @Override
        public InteractionResult use(Level level, Player player, InteractionHand hand)
        {
            Minecraft.getInstance().setScreen(new GuideScreen());
            return super.use(level, player, hand);
        }
    });

    static void register(IEventBus modEventBus)
    {
        ITEMS.register(modEventBus);
    }
}
