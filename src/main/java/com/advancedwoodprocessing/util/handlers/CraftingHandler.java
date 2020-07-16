package com.advancedwoodprocessing.util.handlers;

import java.util.ArrayList;

import com.advancedwoodprocessing.util.DummyRecipe;
import com.google.common.collect.Lists;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;

public class CraftingHandler {
	public static void registerRecipes() {}

	public static void removeRecipes() {
		ForgeRegistry<IRecipe> recipeRegistry = (ForgeRegistry<IRecipe>)ForgeRegistries.RECIPES;
	    ArrayList<IRecipe> recipes = Lists.newArrayList(recipeRegistry.getValues());
	    
	    ArrayList<Item> forbiddenCrafts = Lists.newArrayList();
	    forbiddenCrafts.add(Item.getItemFromBlock(Blocks.PLANKS));
	    forbiddenCrafts.add(Items.STICK);
	    
	    for (IRecipe r : recipes) {
	    	ItemStack output = r.getRecipeOutput();
            if (forbiddenCrafts.contains(output.getItem())) {
            	recipeRegistry.remove(r.getRegistryName());
            	recipeRegistry.register(DummyRecipe.from(r));
            }
	    }
	}
}
