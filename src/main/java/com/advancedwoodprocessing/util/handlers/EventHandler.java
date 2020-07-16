package com.advancedwoodprocessing.util.handlers;

import com.advancedwoodprocessing.events.DropHandler;

import net.minecraftforge.common.MinecraftForge;

public class EventHandler {
	public static void registerEvents() {
		DropHandler dropHandler = new DropHandler();
		MinecraftForge.EVENT_BUS.register(dropHandler);
	}
}
