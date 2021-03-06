package com.eagle.resonantreflux.blocks.function;

import cofh.api.block.IDismantleable;
import com.eagle.resonantreflux.Dictionary;
import com.eagle.resonantreflux.ResonantReflux;
import com.eagle.resonantreflux.blocks.core.BlockContainerRR;
import com.eagle.resonantreflux.tileentities.function.TileEntityFluxCrystallizationChamber;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * This class was created by GustoniaEagle.
 * It is distributed under a part of the Resonant Reflux mod.
 * <p>
 * Forge is open source, and available under the
 * GNU General Public License Version 2.
 * <p>
 * File created @ 23/09/2015, 5:32 PM GMT.
 */
public class BlockRecycler extends BlockContainerRR implements IDismantleable
{
    public BlockRecycler()
    {
        super(Dictionary.UNLOCALIZED_RECYCLER, Material.iron, 12.0F, true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityFluxCrystallizationChamber();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sideX, float sideY, float sideZ)
    {
        if (!player.isSneaking())
        {
            player.openGui(ResonantReflux.instance, Dictionary.Gui.RECYCLER.ordinal(), world, x, y, z);
        }
        return true;
    }

    @Override
    public ArrayList<ItemStack> dismantleBlock(EntityPlayer player, World world, int x, int y, int z, boolean returnDrops)
    {
        ArrayList<ItemStack> drops = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
        world.setBlockToAir(x, y, z);
        if (!returnDrops)
        {
            for (ItemStack itemStack : drops)
            {
                EntityItem entityItem = new EntityItem(world,
                        x + world.rand.nextFloat(), y + world.rand.nextFloat(), z + world.rand.nextFloat(), itemStack);
                entityItem.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(entityItem);
            }
        }

        return drops;
    }

    @Override
    public boolean canDismantle(EntityPlayer player, World world, int x, int y, int z)
    {
        return true;
    }

}
