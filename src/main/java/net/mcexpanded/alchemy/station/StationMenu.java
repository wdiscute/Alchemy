package net.mcexpanded.alchemy.station;

import net.mcexpanded.alchemy.potion.PotionAPI;
import net.mcexpanded.alchemy.registry.AlchemyDataAttachments;
import net.mcexpanded.alchemy.registry.AlchemyMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;

public class StationMenu extends AbstractContainerMenu
{
    private final Container container;
    public final StationBlockEntity be;
    public static final int REAGENT_ONE = 0;
    public static final int REAGENT_TWO = 1;
    public static final int REAGENT_THREE = 2;
    public static final int FLASK = 3;
    public static final int RESULT = 4;
    public static final int CONTAINER_SIZE = 5;

    public StationMenu(int containerId, Inventory playerInventory, FriendlyByteBuf extraData)
    {
        this(containerId, playerInventory, new SimpleContainer(CONTAINER_SIZE), playerInventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public StationMenu(int containerId, Inventory playerInventory, Container container, BlockEntity blockEntity)
    {
        super(AlchemyMenuTypes.STATION.get(), containerId);
        checkContainerSize(container, CONTAINER_SIZE);
        this.container = container;
        this.be = ((StationBlockEntity) blockEntity);
        container.startOpen(playerInventory.player);


        this.addSlot(new Slot(container, REAGENT_ONE, 35, 32));
        this.addSlot(new Slot(container, REAGENT_TWO, 53, 32));
        this.addSlot(new Slot(container, REAGENT_THREE, 71, 32));

        this.addSlot(new Slot(container, FLASK, 89, 32));

        this.addSlot(new Slot(container, RESULT, 125, 32));


        for (int i1 = 0; i1 < 3; ++i1)
            for (int k1 = 0; k1 < 9; ++k1)
                this.addSlot(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));

        for (int j1 = 0; j1 < 9; ++j1)
            this.addSlot(new Slot(playerInventory, j1, 8 + j1 * 18, 142));

    }

    @Override
    public boolean clickMenuButton(Player player, int buttonId)
    {

        if (buttonId == 67)
        {
            Map<Item, List<String>> data = player.getData(AlchemyDataAttachments.KNOWN_TRAITS_MAP);
            ItemStack result = container.getItem(RESULT);
            if(result == null || !result.isEmpty()) return super.clickMenuButton(player, buttonId);

            ItemStack reagent1 = container.getItem(REAGENT_ONE);
            ItemStack reagent2 = container.getItem(REAGENT_TWO);
            ItemStack reagent3 = container.getItem(REAGENT_THREE);
            ItemStack flask = container.getItem(FLASK);

            ItemStack itemStack = PotionAPI.craftPotion(reagent1, reagent2, reagent3, flask);

            if(itemStack != null)
            {
                PotionAPI.awardTraitKnowledge(itemStack, reagent1, reagent2, reagent3, player);
                PotionAPI.awardEffectKnowledge(itemStack, player);

                //Map<Item, List<String>> data = player.getData(AlchemyDataAttachments.KNOWN_TRAITS_MAP);

                reagent1.shrink(1);
                reagent2.shrink(1);
                reagent3.shrink(1);
                flask.shrink(1);

                container.setItem(REAGENT_ONE, reagent1);
                container.setItem(REAGENT_TWO, reagent2);
                container.setItem(REAGENT_THREE, reagent3);
                container.setItem(FLASK, flask);

                container.setItem(RESULT, itemStack);

                return true;
            }
        }

        return super.clickMenuButton(player, buttonId);
    }

    public boolean stillValid(Player player)
    {
        return this.container.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem())
        {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.container.getContainerSize())
            {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
                slot.setByPlayer(ItemStack.EMPTY);
            else
                slot.setChanged();
        }

        return itemstack;
    }

    public void removed(Player player)
    {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
