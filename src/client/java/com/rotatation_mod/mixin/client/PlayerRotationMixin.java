package com.rotatation_mod.mixin.client;


import com.mojang.datafixers.types.Type;
import net.minecraft.block.Portal;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;

import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

import com.rotatation_mod.logic.RotationManager;

@Mixin(ClientPlayerEntity.class)
public abstract class PlayerRotationMixin
{
    @Shadow public abstract Portal.Effect getCurrentPortalEffect();

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;
        World world = client.world;
        if (player == null) return;
        if (world == null) return;

        int renderDistance = client.options.getSimulationDistance().getValue() * 16;
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        Box box = new Box(
                x - renderDistance, y - renderDistance, z - renderDistance,
                x + renderDistance, y + renderDistance, z + renderDistance
        );

        List<LivingEntity> entitiesInBox = client.world.getEntitiesByClass(LivingEntity.class, box, Entity::hasCustomName);

        for (LivingEntity entity : entitiesInBox)
        {
            if (entity == null) continue;
            if (entity.getCustomName() == null) continue;

            String uid = entity.getUuidAsString();
            RotationManager.setEntityName(uid, entity.getCustomName().getString());
            RotationManager.setEntityMap(uid, entity);

            RotationManager.entityList = entitiesInBox;

            RotationManager.updateEntityRotation(entity);
        }
    }
}
