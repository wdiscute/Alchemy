package net.mcexpanded.alchemy.station;

import net.mcexpanded.alchemy.registry.AlchemyBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class StationBlockEntity extends BlockEntity implements WorldlyContainer, MenuProvider
{
    private NonNullList<ItemStack> itemStacks;
    public int openCount;
    private Component name;
    @Nullable
    private final DyeColor color;


    public int getContainerSize()
    {
        return this.itemStacks.size();
    }

    @Override
    public boolean isEmpty()
    {
        for (ItemStack itemstack : this.getItems())
        {
            if (!itemstack.isEmpty())
            {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int i)
    {
        return this.getItems().get(i);
    }

    @Override
    public ItemStack removeItem(int slot, int amount)
    {
        ItemStack itemstack = ContainerHelper.removeItem(this.getItems(), slot, amount);
        if (!itemstack.isEmpty())
        {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot)
    {
        return ContainerHelper.takeItem(this.getItems(), slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack)
    {
        this.getItems().set(slot, stack);
        stack.limitSize(this.getMaxStackSize(stack));
        this.setChanged();
    }

    @Override
    public boolean stillValid(Player player)
    {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public int[] getSlotsForFace(Direction side)
    {
        return new int[]{5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack itemStack, Direction direction)
    {
        return slot >= 5 && direction != Direction.DOWN;
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack itemStack, Direction direction)
    {
        return direction == Direction.DOWN && slot > 4;
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack stack)
    {
        return WorldlyContainer.super.canPlaceItem(slot, stack);
    }

    public boolean triggerEvent(int id, int type)
    {
        if (id == 1)
        {
            this.openCount = type;
            return true;
        }
        else
        {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(ContainerUser containerUser)
    {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator())
        {
            if (this.openCount < 0)
            {
                this.openCount = 0;
            }

            ++this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1)
            {
                this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_OPEN, this.worldPosition);
                this.level.playSound(null, this.worldPosition, SoundEvents.SHULKER_BOX_OPEN, SoundSource.BLOCKS, 0.2F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
                this.level.playSound(null, this.worldPosition, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 0.2F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
                this.level.playSound(null, this.worldPosition, SoundEvents.CHAIN_BREAK, SoundSource.BLOCKS, 0.2F, this.level.getRandom().nextFloat() * 0.1F + 0.4F);
            }
        }
    }


    @Override
    public void stopOpen(ContainerUser containerUser)
    {
        if (!this.remove && !containerUser.getLivingEntity().isSpectator())
        {
            --this.openCount;
            this.level.blockEvent(this.worldPosition, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0)
            {
                this.level.gameEvent(containerUser.getLivingEntity(), GameEvent.CONTAINER_CLOSE, this.worldPosition);
                this.level.playSound(null, this.worldPosition, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 0.2F, this.level.getRandom().nextFloat() * 0.1F + 0.9F);
                this.level.playSound(null, this.worldPosition, SoundEvents.CHAIN_PLACE, SoundSource.BLOCKS, 0.2F, this.level.getRandom().nextFloat() * 0.1F + 0.4F);
                this.level.playSound(null, this.worldPosition, SoundEvents.SNOW_BREAK, SoundSource.BLOCKS, 1.3F, this.level.getRandom().nextFloat() * 0.1F + 0.4F);
            }
        }
    }

    protected NonNullList<ItemStack> getItems()
    {
        return this.itemStacks;
    }

    @Nullable
    public DyeColor getColor()
    {
        return this.color;
    }

    public StationBlockEntity(@Nullable DyeColor color, BlockPos pos, BlockState blockState)
    {
        super(AlchemyBlockEntities.STATION.get(), pos, blockState);
        this.itemStacks = NonNullList.withSize(StationMenu.CONTAINER_SIZE, ItemStack.EMPTY);
        this.color = color;
    }

    public StationBlockEntity(BlockPos pos, BlockState blockState)
    {
        super(AlchemyBlockEntities.STATION.get(), pos, blockState);
        this.itemStacks = NonNullList.withSize(StationMenu.CONTAINER_SIZE, ItemStack.EMPTY);
        this.color = StationBlock.getColorFromBlock(blockState.getBlock());
    }

    @Override
    public void clearContent()
    {
        this.getItems().clear();
    }

    @Override
    public Component getDisplayName()
    {
        return Component.translatable("block.starcatcher.tackle_box");
    }

    @Override
    public @org.jetbrains.annotations.Nullable AbstractContainerMenu createMenu(int containerId, Inventory
            playerInventory, Player player)
    {
        if (!player.isSpectator())
            return new StationMenu(containerId, playerInventory, this, this);
        else
            return null;
    }
}
