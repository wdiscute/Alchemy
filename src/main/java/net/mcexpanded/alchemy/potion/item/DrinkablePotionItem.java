package net.mcexpanded.alchemy.potion.item;

import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class DrinkablePotionItem extends PotionItem
{
    public DrinkablePotionItem(Properties properties)
    {
        super(properties.component(DataComponents.CONSUMABLE,
                Consumable.builder()
                        .consumeSeconds(1.6F)
                        .animation(ItemUseAnimation.DRINK)
                        .sound(SoundEvents.GENERIC_DRINK)
                        .hasConsumeParticles(false)
                        .build()
        ));
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity user)
    {
        return 30;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand)
    {
        //super.use(level, player, hand);

        ItemStack stack = player.getItemInHand(hand);
        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);

        if(potionData != null)
        {
            player.startUsingItem(hand);
            return InteractionResult.CONSUME;
        }

        //cant call super, or it will attempt to drink since the potion has the consume data component
        return InteractionResult.PASS;
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack)
    {
        return true;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity)
    {
        if (level.isClientSide()) return super.finishUsingItem(stack, level, entity);

        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);

        if(potionData == null) return stack;

        potionData.forEach(o -> entity.addEffect(new MobEffectInstance(
                o.effect(), o.duration(), o.level(), false, false, true, null
        )));

        return super.finishUsingItem(stack, level, entity);
    }
}
