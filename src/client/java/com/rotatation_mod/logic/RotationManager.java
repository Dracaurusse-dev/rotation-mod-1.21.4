package com.rotatation_mod.logic;


import com.rotatation_mod.util.utilClass;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import org.joml.Quaternionf;

import java.util.*;

import static com.rotatation_mod.util.utilClass.sendChatMessage;
import static com.rotatation_mod.util.utilClass.reverseString;

public class RotationManager<S extends EntityRenderState>
{
    public static Map<String, LivingEntity> entityMap = new HashMap<>();
    public static Map<String, LivingEntityRenderState> stateMap = new HashMap<>();
    public static Map<String, float[]> entityAngle = new HashMap<>();
    public static Map<String, int[]> entitySpeed = new HashMap<>();
    public static Map<String, Quaternionf> entityRotation = new HashMap<>();
    public static List<String> rotatedEntities = new ArrayList<>();

    public static Map<String, String> entityName = new HashMap<>();

    public static List<LivingEntity> entityList = new ArrayList<>();

    public static void updateEntityRotation(LivingEntity entity)
    {
        String uid = entity.getUuidAsString();

        if (entity.getCustomName() == null)
        {
            sendChatMessage("Entity has no custom name");
            return;
        }

        String name = entity.getCustomName().getString();
        float[] angleArr = getAngleFromEntity(entity, name);
        int[] speedArr = getSpeedFromEntity(entity, name);

        setEntityAngle(uid, angleArr);
        setEntitySpeed(uid, speedArr);

        Quaternionf rotation = getEntityRotation(uid);

        float deltaZ;
        float deltaY;
        float deltaX;

        if (rotatedEntities.contains(uid))
        {
            deltaX = (float) Math.toRadians(speedArr[0] / 100.0);
            deltaY = (float) Math.toRadians(speedArr[1] / 100.0);
            deltaZ = (float) Math.toRadians(speedArr[2] / 100.0);
        } else
        {
            deltaX = (float) Math.toRadians(angleArr[0]);
            deltaY = (float) Math.toRadians(angleArr[1]);
            deltaZ = (float) Math.toRadians(angleArr[2]);
            rotatedEntities.add(uid);
        }

        rotation.rotateXYZ(deltaX, deltaY, deltaZ);

        entityRotation.put(uid, rotation);
    }

    private static float[] getAngleFromEntity(LivingEntity entity, String customName)
    {
        float[] angleArr = new float[3];
        if (entity == null ) return  angleArr;

        String[] axisStr = customName.split("-");
        String[] stringAngleArr = new String[3];

        String fullName = "Dinnerbone";
        for (int i = 0; i < Math.min(axisStr.length, 3); i++)
        {
            String segment = axisStr[i];
            String[] parts = segment.split("(?<=\\d)(?=\\D)");
            String textPart = "";

            if (parts.length == 1)
            {
                textPart = parts[0];
            } else if (parts.length > 1)
            {
                textPart = parts[1];
            }

            if (!textPart.isEmpty() && !(fullName.contains(textPart) || utilClass.reverseString(fullName).contains(textPart)))
            {
                return new float[3];
            }
            stringAngleArr[i] = textPart;
        }

        for (int i = 0; i < 3; i++)
        {
            if (stringAngleArr[i] == null)
            {
                stringAngleArr[i] = "";
            }
            angleArr[i] = 180.0f * stringAngleArr[i].length() / fullName.length();
        }

        return angleArr;
    }

    private static int[] getSpeedFromEntity(LivingEntity entity, String customName)
    {
        int[] speedArr = new int[3];
        if (entity == null) return speedArr;

        String[] axisStr = customName.split("-");

        for (int i = 0; i < Math.min(axisStr.length, 3); i++)
        {
            String segment = axisStr[i];
            String[] parts = segment.split("(?<=\\d)(?=\\D)"); // Split numbers from letters
            String digitPart = "";

            if (parts.length == 1)
            {
                digitPart = "0";
            } else if (parts.length > 1)
            {
                digitPart = parts[0];
            }

            speedArr[i] = Integer.parseInt(digitPart);
        }
        return speedArr;
    }

    public static void setStateMap(String entityID, LivingEntityRenderState state)
    {
        stateMap.put(entityID, state);
    }

    public static LivingEntityRenderState getStateByID(String entityID)
    {
        return stateMap.getOrDefault(entityID, null);
    }

    public static void setEntityMap(String entityID, LivingEntity entity)
    {
        entityMap.put(entityID, entity);
    }

    public static LivingEntity getEntityByID(String entityID)
    {
        return entityMap.getOrDefault(entityID, null);
    }

    public static void setEntityAngle(String entityID, float[] angleArr)
    {
        entityAngle.put(entityID, angleArr);
    }

    public static float[] getEntityAngle(String entityID)
    {
        return entityAngle.getOrDefault(entityID, new float[3]);
    }

    public static void setEntitySpeed(String entityID, int[] speedArr)
    {
        entitySpeed.put(entityID, speedArr);
    }

    public static int[] getEntitySpeed(String entityID)
    {
        return entitySpeed.getOrDefault(entityID, new int[3]);
    }

    public static void setEntityRotation(String entityID, Quaternionf rotation)
    {
        entityRotation.put(entityID, rotation);
    }

    public static Quaternionf getEntityRotation(String entityID)
    {
        return entityRotation.getOrDefault(entityID, new Quaternionf());
    }

    public static void setEntityName(String entityID, String customName)
    {
        entityName.put(entityID, customName);
    }

    public static String getEntityName(String entityID)
    {
        return entityName.getOrDefault(entityID, "");
    }

    public static String getEntityByName(String customName)
    {
        for (Map.Entry<String, String> entry : entityName.entrySet())
        {
            if (entry.getValue().equals(customName))
            {
                return entry.getKey();
            }
        }
        return "";
    }
}
