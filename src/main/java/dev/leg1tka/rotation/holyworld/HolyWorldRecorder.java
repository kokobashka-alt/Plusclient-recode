package dev.leg1tka.rotation.holyworld;

import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventTickPre;
import dev.leg1tka.modules.combat.AttackAura;
import java.util.List;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

/**
 * Records how the player tracks a target with the mouse: at the start of every tick the features describing the
 * situation, and on the next tick the view change the player actually produced. Only manual aiming is recorded;
 * ticks where Aura is on, a screen is open, the player is dead or the view jumped are cut out of the episode.
 */
public final class HolyWorldRecorder implements ClientAccess {
    private static final double MAX_DISTANCE = 7.0;
    private static final double ACQUIRE_ANGLE = 30.0;
    private static final double KEEP_ANGLE = 55.0;
    private static final float MAX_HUMAN_YAW_STEP = 90.0F;
    private static final float MAX_HUMAN_PITCH_STEP = 60.0F;

    private final HolyWorldBrain brain;
    private final double[] pending = new double[Features.N_IN];
    private Dataset.Builder builder = new Dataset.Builder();
    private LivingEntity target;
    private boolean hasPending;
    private boolean haveLast;
    private float lastYaw;
    private float lastPitch;
    private float lastAimYaw;
    private float lastAimPitch;
    private double prevYawDelta;
    private double prevPitchDelta;
    private int savedEpisodes;

    HolyWorldRecorder(HolyWorldBrain brain) {
        this.brain = brain;
    }

    @EventLink
    public void onTickPre(EventTickPre event) {
        if (!brain.isRecording()) {
            return;
        }
        ClientPlayerEntity player = minecraftClient.player;
        if (player == null || minecraftClient.world == null || !canRecord(player)) {
            flush();
            haveLast = false;
            return;
        }

        float yaw = player.getYaw();
        float pitch = player.getPitch();
        if (hasPending && target != null) {
            float yawDelta = MathHelper.wrapDegrees(yaw - lastYaw);
            float pitchDelta = pitch - lastPitch;
            if (Math.abs(yawDelta) > MAX_HUMAN_YAW_STEP || Math.abs(pitchDelta) > MAX_HUMAN_PITCH_STEP) {
                flush();
            } else {
                builder.add(pending, yawDelta, pitchDelta);
                prevYawDelta = yawDelta;
                prevPitchDelta = pitchDelta;
            }
        }
        hasPending = false;

        LivingEntity next = pickTarget(player, yaw, pitch);
        if (next == null) {
            flush();
            lastYaw = yaw;
            lastPitch = pitch;
            return;
        }
        if (next != target) {
            flush();
            target = next;
            haveLast = false;
            prevYawDelta = 0;
            prevPitchDelta = 0;
        }

        HolyWorldAim.Angles aim = HolyWorldAim.angles(player, next);
        double yawVelocity = haveLast ? MathHelper.wrapDegrees(aim.yaw - lastAimYaw) : 0.0;
        double pitchVelocity = haveLast ? aim.pitch - lastAimPitch : 0.0;
        Features.build(MathHelper.wrapDegrees(aim.yaw - yaw), aim.pitch - pitch, prevYawDelta, prevPitchDelta,
                yawVelocity, pitchVelocity, aim.distance, aim.sizeDegrees, player.getAttackCooldownProgress(0.5F), pending);
        hasPending = true;
        haveLast = true;
        lastYaw = yaw;
        lastPitch = pitch;
        lastAimYaw = aim.yaw;
        lastAimPitch = aim.pitch;
    }

    private boolean canRecord(ClientPlayerEntity player) {
        if (minecraftClient.currentScreen != null || !player.isAlive() || player.isSpectator() || player.hasVehicle() || player.isGliding()) {
            return false;
        }
        return AttackAura.c == null || !AttackAura.c.isEnabled();
    }

    private LivingEntity pickTarget(ClientPlayerEntity player, float yaw, float pitch) {
        if (target != null && target.isAlive() && !target.isRemoved() && angleTo(player, target, yaw, pitch) < KEEP_ANGLE
                && player.distanceTo(target) <= MAX_DISTANCE + 2.0) {
            return target;
        }
        List<LivingEntity> candidates = minecraftClient.world.getEntitiesByClass(LivingEntity.class,
                player.getBoundingBox().expand(MAX_DISTANCE), entity -> entity != player && entity.isAlive() && !entity.isSpectator());
        LivingEntity best = null;
        double bestAngle = ACQUIRE_ANGLE;
        for (LivingEntity candidate : candidates) {
            if (player.distanceTo(candidate) > MAX_DISTANCE) {
                continue;
            }
            double angle = angleTo(player, candidate, yaw, pitch);
            if (angle < bestAngle) {
                bestAngle = angle;
                best = candidate;
            }
        }
        return best;
    }

    private static double angleTo(ClientPlayerEntity player, LivingEntity entity, float yaw, float pitch) {
        HolyWorldAim.Angles aim = HolyWorldAim.angles(player, entity);
        double dy = MathHelper.wrapDegrees(aim.yaw - yaw);
        double dp = aim.pitch - pitch;
        return Math.sqrt(dy * dy + dp * dp);
    }

    /** Ends the current episode, keeping it if it is long enough. */
    void flush() {
        if (builder.length() > 0) {
            if (brain.dataset().add(builder.build())) {
                savedEpisodes++;
            }
            builder = new Dataset.Builder();
        }
        hasPending = false;
        target = null;
        haveLast = false;
    }

    int savedEpisodes() {
        return savedEpisodes;
    }
}
