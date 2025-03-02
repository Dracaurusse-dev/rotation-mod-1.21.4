package com.rotatation_mod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RotationMod implements ModInitializer
{
	public static final String MOD_ID = "rotation-mod";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize()
	{

	}
}