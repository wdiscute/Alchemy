package net.mcexpanded.alchemy;

import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;

@EventBusSubscriber(modid = Alchemy.MOD_ID)
public class AlchemyEvents
{
    @SubscribeEvent
    public static void addDatapackRegistry(DataPackRegistryEvent.NewRegistry event)
    {
        event.dataPackRegistry(
                Alchemy.TRAIT_REGISTRY_KEY, TraitProperties.CODEC, TraitProperties.CODEC,
                builder -> builder.maxId(512));

        event.dataPackRegistry(
                Alchemy.TRAIT_REGISTRY_KEY, TraitProperties.CODEC, TraitProperties.CODEC,
                builder -> builder.maxId(512));
    }

    @SubscribeEvent
    public static void registerDataMaps(RegisterDataMapTypesEvent event)
    {
        event.register(AlchemyDataMaps.REAGENT_PROPERTIES);
        event.register(AlchemyDataMaps.POTION_EFFECT_REQUIREMENTS);
    }

    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();

        ReagentProperties reagentProperties = AlchemyDataMaps.get(stack);
        if (reagentProperties != null)
        {
            List<Component> toolTip = event.getToolTip();

            reagentProperties.traits().forEach(o ->
                    toolTip.add(1, Component.literal(" -" + o.value().effect()).withStyle(ChatFormatting.DARK_GRAY)));

            toolTip.add(1 , Component.literal("Alchemy Traits").withStyle(ChatFormatting.GRAY));
        }
    }
}
