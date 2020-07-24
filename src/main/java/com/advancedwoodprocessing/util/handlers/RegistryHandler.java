package com.advancedwoodprocessing.util.handlers;

import com.advancedwoodprocessing.Main;
import com.advancedwoodprocessing.events.DropHandler;
import com.advancedwoodprocessing.init.ModBlocks;
import com.advancedwoodprocessing.init.ModItems;
import com.advancedwoodprocessing.util.IHasModel;
import com.advancedwoodprocessing.util.handlers.EventHandler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class RegistryHandler {
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
		for (Item plank: ModItems.PLANKS)
			OreDictionary.registerOre("awpPlank", new ItemStack(plank));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for (Item item : ModItems.ITEMS) {
			if (item instanceof IHasModel)  {
				((IHasModel)item).registerModel();
			}
		}
		
		for (Block block : ModBlocks.BLOCKS) {
			if (block instanceof IHasModel)  {
				((IHasModel)block).registerModel();
			}
		}
	}
	
	public static void preInitRegisters() {
		EventHandler.registerEvents();
		CraftingHandler.removeRecipes();
		PacketHandler.registerMessages("awp");
	}

	public static void initRegisters(){
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandlers());
	}

	public static void postInitRegisters() {
		DropHandler.Woods = OreDictionary.getOres("logWood");
	}
}
