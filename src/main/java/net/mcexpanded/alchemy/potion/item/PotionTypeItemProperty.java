package net.mcexpanded.alchemy.potion.item;

import com.mojang.serialization.MapCodec;
import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PotionTypeItemProperty() implements RangeSelectItemModelProperty
{
    public static final MapCodec<PotionTypeItemProperty> MAP_CODEC = MapCodec.unit(PotionTypeItemProperty::new);

    @Override
    public float get(ItemStack stack, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed)
    {
        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);

        return potionData == null ? 0f : potionData.size();
    }

    @Override
    public MapCodec<PotionTypeItemProperty> type()
    {
        return MAP_CODEC;
    }
}