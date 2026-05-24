package net.mcexpanded.alchemy;

import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
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
                Alchemy.TRAIT_REGISTRY_KEY, TraitProperties.DIRECT_CODEC, TraitProperties.DIRECT_CODEC,
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
        if(event.getEntity() == null) return;

        ReagentProperties reagentProperties = AlchemyDataMaps.get(stack);
        if (reagentProperties != null)
        {
            List<Component> toolTip = event.getToolTip();


            reagentProperties.traits().forEach(trait ->
                    {
                        Identifier key = event.getEntity().level().registryAccess().lookupOrThrow(Alchemy.TRAIT_REGISTRY_KEY).getKey(trait.value());
                        MutableComponent name = Component.translatable("alchemy.trait." + key.toLanguageKey());
                        toolTip.add(1, Component.literal(" -").append(name).withStyle(ChatFormatting.DARK_GRAY));
                    }
            );

            toolTip.add(1, Component.literal("Alchemy Traits").withStyle(ChatFormatting.GRAY));
        }
    }
}
