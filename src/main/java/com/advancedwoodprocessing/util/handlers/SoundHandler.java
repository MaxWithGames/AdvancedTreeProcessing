package com.advancedwoodprocessing.util.handlers;

import java.util.ArrayList;

import com.advancedwoodprocessing.util.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class SoundHandler {
	public static ArrayList<SoundEvent> FIRE_CLAPS = new ArrayList<SoundEvent>();
	
	@SubscribeEvent
	public static void init(RegistryEvent.Register<SoundEvent> event) {	
		for (int i = 0; i <= 5; i++)
			FIRE_CLAPS.add(register("fire_clap_" + i, event));
		
		System.out.println("\n" + "SOUNDS REGISTRED" + "\n");
	}

	public static SoundEvent register(String name, RegistryEvent.Register<SoundEvent> event) {
		ResourceLocation location = new ResourceLocation(Reference.MOD_ID, name);
		SoundEvent soundEvent = new SoundEvent(location).setRegistryName(name);
		
		event.getRegistry().register(soundEvent);
		
		return soundEvent;
	}
}