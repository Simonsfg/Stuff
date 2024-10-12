package org.simon.stuff.client.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

public class JointArmPose {
    public static void applyArmPose(BipedEntityModel<?> model, LivingEntity entity) {
        // Adjust the arm position for smoking a joint
        model.rightArm.pitch = (float) Math.toRadians(-90); // Raise arm
        model.rightArm.yaw = (float) Math.toRadians(20);   // Angle slightly inward
        model.rightArm.roll = 0;

        // You might want to adjust the left arm as well
        model.leftArm.pitch = (float) Math.toRadians(-10); // Slight downward angle
        model.leftArm.yaw = 0;
        model.leftArm.roll = 0;
    }
}