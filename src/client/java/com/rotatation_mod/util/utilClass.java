package com.rotatation_mod.util;

import com.rotatation_mod.logic.RotationManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;

public abstract class utilClass
{
    public static void sendChatMessage(String message)
    {
        if (MinecraftClient.getInstance().player != null)
        {
            MinecraftClient.getInstance().player.sendMessage(Text.of(message), false);
        }
    }

    public static String reverseString(String s)
    {
        return new StringBuilder(s).reverse().toString();
    }

    /*
    public static EntityRenderState getKeyFromValueState(String displayName)
    {
        for (Map.Entry<EntityRenderState, String> entry : RotationManager.entityNameStateMap.entrySet())
        {
            if (entry.getValue().equals(displayName))
            {
                return entry.getKey();
            }
        }
        return null;
    }*/
}
