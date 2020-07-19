package com.advancedwoodprocessing.util.handlers;

import com.advancedwoodprocessing.events.DropHandler;
import com.advancedwoodprocessing.events.PlaceHandler;

import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
	public static void registerEvents() {
		DropHandler dropHandler = new DropHandler();
		MinecraftForge.EVENT_BUS.register(dropHandler);
		
		PlaceHandler placeHandler = new PlaceHandler();
		MinecraftForge.EVENT_BUS.register(placeHandler);
	}
}
