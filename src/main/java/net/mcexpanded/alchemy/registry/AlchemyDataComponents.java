package net.mcexpanded.alchemy.registry;

import net.mcexpanded.alchemy.Alchemy;
import net.mcexpanded.alchemy.potion.PotionData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface AlchemyDataComponents
{
    DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Alchemy.MOD_ID);

    //bucketed fish
    DeferredHolder<DataComponentType<?>, DataComponentType<List<PotionData>>> POTION_DATA = register(
            "potion_data",
            builder -> builder.persistent(PotionData.LIST_CODEC));



    static <T> void set(ItemStack stack, Supplier<DataComponentType<T>> component, T data)
    {
        stack.set(component, data);
    }

    static <T> boolean has(ItemStack stack, Supplier<DataComponentType<T>> component)
    {
        return stack.has(component);
    }

    static <T> void remove(ItemStack stack, Supplier<DataComponentType<T>> component)
    {
        stack.remove(component);
    }

    @Nonnull
    static <T> T getOrDefault(ItemStack stack, Supplier<DataComponentType<T>> component, T defaultValue)
    {
        return stack.getOrDefault(component, defaultValue);
    }

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator)
    {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    static void register(IEventBus eventBus)
    {
        DATA_COMPONENT_TYPES.register(eventBus);
    }

}
