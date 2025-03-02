package com.rotatation_mod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

@Environment(EnvType.CLIENT)
public class RotationModClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ClientTickEvents.END_CLIENT_TICK.register(client ->
			{
				if (client.player != null && client.world != null)
				{
					//RotationLogic.detectAndRotate();
				}
			}
		);
	}
}