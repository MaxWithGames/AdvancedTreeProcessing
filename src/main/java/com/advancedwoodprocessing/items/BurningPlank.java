package com.advancedwoodprocessing.items;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Nullable;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BurningPlank extends ItemBase implements IHasModel{
	public BurningPlank(String name, CreativeTabs tab) {
		super(name, tab);
		
		setMaxDamage(1200);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem) {
		entityItem.setDead();
        return true;
    }
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
		NBTTagCompound nbt = new NBTTagCompound();
		if (stack.hasTagCompound())
            nbt = stack.getTagCompound();
        else
            nbt = new NBTTagCompound();
		
		if (!nbt.hasKey("time_created")) {
			nbt.setLong("time_created", worldIn.getTotalWorldTime());
			stack.setTagCompound(nbt);
		}
				
		if (!worldIn.isRemote) {
			long lifespan = worldIn.getTotalWorldTime() - stack.getTagCompound().getLong("time_created"); 
			
			if (lifespan > stack.getMaxDamage())
				entityIn.replaceItemInInventory(itemSlot, new ItemStack(ModItems.COAL_DUST));
			else
				stack.setItemDamage((int)lifespan);
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("time_created")) {
			long lifespan = worldIn.getTotalWorldTime() - stack.getTagCompound().getLong("time_created");
			
			DecimalFormat decimalFormat = new DecimalFormat("#.00");
	        String timeLeftString = stack.getMaxDamage() > lifespan ?
	        		decimalFormat.format((stack.getMaxDamage() - lifespan) / 20.f):
	        		"0.00";
			
			tooltip.add("Time left: " + timeLeftString + "s");
		}
    }
	
	@Override
	public void registerModel() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
