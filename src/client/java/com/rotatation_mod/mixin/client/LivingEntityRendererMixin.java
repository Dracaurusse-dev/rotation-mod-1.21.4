package com.rotatation_mod.mixin.client;


import com.rotatation_mod.logic.RotationManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;


@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
{
    @Inject(method = "render*", at = @At("HEAD"))
    private void onRender(LivingEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci)
    {
        // Make sure Dinnerbone entities are not turned twice
        if (state.flipUpsideDown)
        {
            state.flipUpsideDown = false;
        }

        matrixStack.push();

        float offsetY = state.height / 2.0f;

        matrixStack.translate(0, offsetY, 0);

        if (state.customName != null)
        {
            String customName = state.customName.getString();
            String uid = RotationManager.getEntityByName(customName);

            if (!Objects.equals(uid, ""))
            {
                RotationManager.setStateMap(uid, state);

                Quaternionf rotation = RotationManager.getEntityRotation(uid);

                if (rotation != null)
                {
                    matrixStack.multiply(rotation);
                }
            }
        }

        matrixStack.translate(0, -offsetY, 0);
    }

    @Inject(method = "render*", at = @At("TAIL"))
    private void afterRender(LivingEntityRenderState state, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci)
    {
        matrixStack.pop();
    }
}
