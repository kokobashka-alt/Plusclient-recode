package dev.leg1tka.modules.movement;


import dev.leg1tka.modules.Module;
import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventMove;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class FreeCamera extends Module {
   public static FreeCamera c = new FreeCamera();
   public Vec3d d;

   public FreeCamera() {
      super(
         "FreeCamera",
         "Обзор местности за фейк игрока",
         Category.MOVEMENT
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraftClient.player != null) {
         this.d = minecraftClient.player.getPos();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      if (minecraftClient.player != null && this.d != null) {
         minecraftClient.player.setPosition(this.d);
      }
   }

   @EventLink
   public void onEvent(EventPacket eventPacket1) {
      Packet packet2 = eventPacket1.getPacket();
      if (packet2 instanceof PlayerMoveC2SPacket) {
         eventPacket1.cancel();
      } else if (packet2 instanceof PlayerRespawnS2CPacket || packet2 instanceof GameJoinS2CPacket) {
         this.toggle();
      }
   }

   @EventLink
   public void onEvent(Event3DRender event3DRender1) {
      if (this.d != null && minecraftClient.player != null) {
         float floatValue2 = minecraftClient.player.getWidth() / 2.0F;
         float floatValue3 = minecraftClient.player.getHeight();
         Box box4 = new Box(
            this.d.x - (double)floatValue2, this.d.y, this.d.z - (double)floatValue2, this.d.x + (double)floatValue2, this.d.y + (double)floatValue3, this.d.z + (double)floatValue2
         );
         this.a(event3DRender1.getMatrices(), box4, event3DRender1.getCamera().getPos());
      }
   }

   private void a(MatrixStack matrixStack1, Box box2, Vec3d vec3d3) {
      double doubleValue4 = box2.minX - vec3d3.x;
      double doubleValue6 = box2.minY - vec3d3.y;
      double doubleValue8 = box2.minZ - vec3d3.z;
      double doubleValue10 = box2.maxX - vec3d3.x;
      double doubleValue12 = box2.maxY - vec3d3.y;
      double doubleValue14 = box2.maxZ - vec3d3.z;
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.disableDepthTest();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderSystem.lineWidth(1.5F);
      dev.leg1tka.render.RenderPrimitives.drawOutlinedBox(matrixStack1, new Box(doubleValue4, doubleValue6, doubleValue8, doubleValue10, doubleValue12, doubleValue14), 1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   @EventLink
   public void onEvent(EventMove eventMove1) {
      if (minecraftClient.player != null) {
         minecraftClient.player.noClip = true;
         double doubleValue2 = 1.0;
         double doubleValue4 = (double)minecraftClient.player.input.movementForward;
         double doubleValue6 = (double)minecraftClient.player.input.movementSideways;
         double doubleValue8 = Math.toRadians((double)minecraftClient.player.getYaw());
         double doubleValue10 = 0.0;
         double doubleValue12 = 0.0;
         if (doubleValue4 != 0.0 || doubleValue6 != 0.0) {
            double doubleValue14 = doubleValue8 + Math.atan2(-doubleValue6, doubleValue4);
            doubleValue10 = -Math.sin(doubleValue14) * doubleValue2;
            doubleValue12 = Math.cos(doubleValue14) * doubleValue2;
         }

         double doubleValue16 = 0.0;
         if (minecraftClient.options.jumpKey.isPressed()) {
            doubleValue16 = doubleValue2;
         } else if (minecraftClient.options.sneakKey.isPressed()) {
            doubleValue16 = -doubleValue2;
         }

         eventMove1.setMovePos(new Vec3d(doubleValue10, doubleValue16, doubleValue12));
      }
   }

   @EventLink
   public void onEvent(EventMoveInput eventMoveInput1) {
      if (minecraftClient.player != null) {
         if (minecraftClient.player.getPose() == EntityPose.CROUCHING || minecraftClient.player.getPose() == EntityPose.SWIMMING) {
            eventMoveInput1.setStrafe(eventMoveInput1.getStrafe() * 5.0F);
         }
      }
   }
}
