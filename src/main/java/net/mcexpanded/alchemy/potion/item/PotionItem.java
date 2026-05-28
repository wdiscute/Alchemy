package net.mcexpanded.alchemy.potion.item;

import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;

import java.util.ArrayList;
import java.util.List;

public class PotionItem extends Item
{
    public PotionItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack)
    {
        //potion data display
        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);
        if (potionData == null) return super.getName(stack);

//        potionData = List.of(
//                new PotionData(MobEffects.REGENERATION, 1 ,1),
//                new PotionData(MobEffects.POISON, 1 ,1)
//        );

        List<String> effectsToAdd = new ArrayList<>();

        potionData.forEach(o ->
        {
            String s = I18n.get("effect." + o.effect().getRegisteredName().replace(":", "."));

            effectsToAdd.add(s);
        });

        StringBuilder effects = new StringBuilder();

        for (int i = 0; i < effectsToAdd.size(); i++)
        {
            //if last effect
            if (i == effectsToAdd.size() - 1)
            {
                effects.append(effectsToAdd.getLast());
            }
            else if (i == effectsToAdd.size() - 2)
                effects.append(effectsToAdd.get(i)).append(" and ");
            else
                effects.append(effectsToAdd.get(i)).append(", ");
        }

        return Component.translatable("tooltip.alchemy.potion", effects.toString());
    }
}
