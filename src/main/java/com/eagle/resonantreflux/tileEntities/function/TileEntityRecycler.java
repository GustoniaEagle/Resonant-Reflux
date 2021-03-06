package com.eagle.resonantreflux.tileentities.function;

import com.eagle.resonantreflux.registry.ItemRegistry;
import com.eagle.resonantreflux.tileentities.core.TileEntityRR;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * This class was created by GustoniaEagle.
 * It is distributed under a part of the Resonant Reflux mod.
 * <p>
 * Forge is open source, and available under the
 * GNU General Public License Version 2.
 * <p>
 * File created @ 23/09/2015, 5:33 PM GMT.
 */
public class TileEntityRecycler extends TileEntityRR implements ISidedInventory
{
    private ItemStack[] inv = new ItemStack[2];
    private int progress;

    public TileEntityRecycler()
    {
        super(40000000, 32000);
        progress = 0;
    }

    @Override
    public void updateEntity()
    {
        worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        progress = tagCompound.getInteger("Progress");
        NBTTagList nbttaglist = tagCompound.getTagList("Items", 10);
        this.inv = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound tagCompound1 = nbttaglist.getCompoundTagAt(i);
            int j = tagCompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inv.length)
            {
                this.inv[j] = ItemStack.loadItemStackFromNBT(tagCompound1);
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setInteger("Progress", progress);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inv.length; ++i)
        {
            if (this.inv[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inv[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tagCompound.setTag("Items", nbttaglist);
    }

    public int getProgress()
    {
        return progress;
    }

    public void setProgress(int progress)
    {
        this.progress = progress;
    }

    public int getPowerStored()
    {
        return this.storage.getEnergyStored();
    }

    public void setPowerStored(int powerStored)
    {
        this.storage.setEnergyStored(powerStored);
    }

    public int getMaxPowerStored()
    {
        return this.storage.getMaxEnergyStored();
    }

    public void setMaxPowerStored(int maxPowerStored)
    {
        this.storage.setCapacity(maxPowerStored);
    }

    @Override
    public int getSizeInventory()
    {
        return 64;
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inv[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        if (this.inv[slot] != null)
        {
            ItemStack itemstack;

            if (this.inv[slot].stackSize <= amount)
            {
                itemstack = this.inv[slot];
                this.inv[slot] = null;
                this.markDirty();
                return itemstack;
            }
            else
            {
                itemstack = this.inv[slot].splitStack(amount);

                if (this.inv[slot].stackSize == 0)
                {
                    this.inv[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inv[slot] != null)
        {
            ItemStack itemstack = this.inv[slot];
            this.inv[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack)
    {
        this.inv[slot] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit())
        {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName()
    {
        return "Flux Crystallization Chamber";
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this && player.getDistanceSq(
                (double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory()
    {
        markDirty();
    }

    @Override
    public void closeInventory()
    {
        markDirty();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack)
    {
        return slot == 0 && itemStack != null &&
                (itemStack.getItem() == ItemRegistry.scrap || itemStack.getItem() == ItemRegistry.scrapBag);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        switch (side)
        {
            case 0:
                return new int[]{1};
            case 1:
                return new int[]{1};
            default:
                return new int[]{0};
        }
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side)
    {
        return slot == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side)
    {
        return slot == 1;
    }
}
