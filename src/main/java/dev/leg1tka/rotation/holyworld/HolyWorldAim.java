package dev.leg1tka.rotation.holyworld;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

/**
 * Aim point and angles used both when recording the player's own tracking and when the model drives the rotation,
 * so the two always see the same geometry. The point is independent of any module state: horizontal center of the
 * hitbox, 70% of its height.
 */
public final class HolyWorldAim {
    private static final double HEIGHT_FRACTION = 0.7;

    private HolyWorldAim() {
    }

    public static final class Angles {
        public final float yaw;
        public final float pitch;
        public final double distance;
        public final double sizeDegrees;

        Angles(float yaw, float pitch, double distance, double sizeDegrees) {
            this.yaw = yaw;
            this.pitch = pitch;
            this.distance = distance;
            this.sizeDegrees = sizeDegrees;
        }
    }

    public static Vec3d point(LivingEntity entity) {
        Box box = entity.getBoundingBox();
        return new Vec3d((box.minX + box.maxX) * 0.5, box.minY + (box.maxY - box.minY) * HEIGHT_FRACTION, (box.minZ + box.maxZ) * 0.5);
    }

    public static Angles angles(ClientPlayerEntity player, LivingEntity entity) {
        Vec3d eye = player.getEyePos();
        Vec3d point = point(entity);
        double dx = point.x - eye.x;
        double dy = point.y - eye.y;
        double dz = point.z - eye.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float) -Math.toDegrees(Math.atan2(dy, horizontal));
        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        double size = Math.toDegrees(Math.atan2(entity.getWidth() * 0.5, Math.max(distance, 0.3)));
        return new Angles(MathHelper.wrapDegrees(yaw), MathHelper.clamp(pitch, -90.0F, 90.0F), distance, size);
    }
}
