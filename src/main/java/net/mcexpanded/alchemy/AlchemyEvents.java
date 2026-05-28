package net.mcexpanded.alchemy;

import net.mcexpanded.alchemy.alchemy.ReagentProperties;
import net.mcexpanded.alchemy.alchemy.TraitProperties;
import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.potion.item.PotionTintItemProperty;
import net.mcexpanded.alchemy.potion.item.PotionTypeItemProperty;
import net.mcexpanded.alchemy.registry.AlchemyDataAttachments;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.mcexpanded.alchemy.registry.AlchemyDataMaps;
import net.mcexpanded.alchemy.registry.AlchemyMenuTypes;
import net.mcexpanded.alchemy.station.StationScreen;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterRangeSelectItemModelPropertyEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

import java.util.List;
import java.util.Map;

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
        event.register(AlchemyDataMaps.POTION_EFFECT_PROPERTIES);
    }

    @SubscribeEvent
    public static void registerItemProperties(RegisterRangeSelectItemModelPropertyEvent event)
    {
        event.register(Alchemy.rl("potion_type"), PotionTypeItemProperty.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerItemTint(RegisterColorHandlersEvent.ItemTintSources event)
    {
        event.register(Alchemy.rl("potion_ting"), PotionTintItemProperty.MAP_CODEC);
    }


    @SubscribeEvent
    public static void tooltipEvent(ItemTooltipEvent event)
    {
        List<Component> toolTip = event.getToolTip();
        ItemStack stack = event.getItemStack();
        if (event.getEntity() == null) return;

        boolean shift = event.getFlags().hasShiftDown();

        //reagent properties display
        ReagentProperties reagentProperties = AlchemyDataMaps.get(stack);
        if (reagentProperties != null)
        {
            reagentProperties.traits().forEach(trait ->
                    {
                        //if known
                        Map<Item, List<String>> data = event.getEntity().getData(AlchemyDataAttachments.KNOWN_TRAITS_MAP);
                        if(!AlchemyConfig.HIDE_REAGENT_TRAITS_UNTIL_FOUND.get() || data.getOrDefault(stack.getItem(), List.of()).contains(trait.value().group()))
                        {
                            Identifier key = event.getEntity().level().registryAccess().lookupOrThrow(Alchemy.TRAIT_REGISTRY_KEY).getKey(trait.value());
                            MutableComponent name = Component.translatable("alchemy.trait." + key.toLanguageKey());

                            MutableComponent comp = Component.literal(" -");
                            comp.append(name);

                            if (shift)
                            {
                                comp.append(Component.literal(" ("));
                                comp.append(Component.translatable("alchemy.group." + trait.value().group()));
                                comp.append(Component.translatable(" lvl. " + trait.value().level() + ")"));
                            }

                            toolTip.add(1, comp.withStyle(ChatFormatting.DARK_GRAY));
                        }
                        else
                        {
                            MutableComponent comp = Component.literal(" - ???");
                            toolTip.add(1, comp.withStyle(ChatFormatting.DARK_GRAY));
                        }
                    }
            );

            toolTip.add(1, Component.literal("Alchemy Traits").withStyle(ChatFormatting.GRAY));
        }

        //potion data display
        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);
        if (potionData == null) return;

        potionData.forEach(o ->
        {
            MutableComponent comp = Component.translatable("effect." + o.effect().getRegisteredName().replace(":", "."));

            if (o.level() == 1)
                comp.append(Component.literal(" (" + o.duration() + ")"));
            else
                comp.append(Component.literal(" " + o.level() + " (" + o.duration() + ")"));

            toolTip.add(comp.withStyle(ChatFormatting.DARK_GRAY));
        });
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event)
    {
        event.register(AlchemyMenuTypes.STATION.get(), StationScreen::new);
    }
}
