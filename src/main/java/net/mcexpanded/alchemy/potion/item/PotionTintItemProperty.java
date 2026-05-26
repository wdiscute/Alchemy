package net.mcexpanded.alchemy.potion.item;

import com.mojang.serialization.MapCodec;
import net.mcexpanded.alchemy.potion.PotionData;
import net.mcexpanded.alchemy.registry.AlchemyDataComponents;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public record PotionTintItemProperty() implements ItemTintSource
{
    public static final int DEFAULT_COLOR = 0xffff00ff;

    public static final MapCodec<PotionTintItemProperty> MAP_CODEC = MapCodec.unit(PotionTintItemProperty::new);

    @Override
    public int calculate(ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity owner)
    {
        List<PotionData> potionData = stack.get(AlchemyDataComponents.POTION_DATA);

        if (potionData == null) return DEFAULT_COLOR;

        return averageColor(potionData.stream().mapToInt(o -> o.effect().value().getColor()).toArray());
    }

    public static int averageColor(int... colors)
    {
        if (colors.length == 0)
            throw new IllegalArgumentException("At least one color required");

        int a = Integer.MAX_VALUE;
        int r = 0;
        int g = 0;
        int b = 0;

        for (int color : colors)
        {
            //yes i totally wrote bit shift code
            //a += (color >> 24) & 0xFF;
            r += (color >> 16) & 0xFF;
            g += (color >> 8) & 0xFF;
            b += color & 0xFF;
        }

        int count = colors.length;

        return ((a / count) << 24)
                | ((r / count) << 16)
                | ((g / count) << 8)
                | (b / count);
    }

    @Override
    public MapCodec<PotionTintItemProperty> type()
    {
        return MAP_CODEC;
    }
}
