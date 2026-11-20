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
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;

public class Blink extends Module implements dev.leg1tka.api.ClientAccess {
   public static final Blink c = new Blink();
   private final ModeSetting d = new ModeSetting(
      "Режим",
      "Движение",
      "Движение",
      "Полный"
   );
   private final BooleanSetting e = new BooleanSetting("Пакет на удар", true);
   private final BooleanSetting f = new BooleanSetting("Выключать после удара", true);
   private final BooleanSetting g = new BooleanSetting("Защита Grim", true);
   private final ModeSetting h = new ModeSetting(
         "Действие Grim",
         "Сброс",
         "Сброс",
         "Выключение"
      )
      .a(this.g::getValue);
   private final NumberSetting i = new NumberSetting("Макс. тиков", 15.0F, 5.0F, 18.0F, 1.0F).a(this.g::getValue);
   private final BooleanSetting j = new BooleanSetting("Показать хитбокс", true);
   private final ModeSetting k = new ModeSetting(
         "Шейдер",
         "Красивый",
         "Красивый",
         "Обычный"
      )
      .a(this.j::getValue);
   private final Queue l = new ConcurrentLinkedQueue();
   private final dev.leg1tka.render.FramebufferOutline m = new dev.leg1tka.render.FramebufferOutline();
   private Box n;
   private int o;
   private int p = -1;
   private int q = -1;

   public Blink() {
      super(
         "Blink",
         "Задерживает отправку пакетов движения",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k});
   }

   @Override
   public void onEnable() {
      this.o = 0;
      this.l.clear();
      if (minecraftClient.player != null) {
         this.n = minecraftClient.player.getBoundingBox();
      }

      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.p();
      this.n = null;
      this.o = 0;
      this.m.c();
      super.onDisable();
   }

   public void p() {
      if (minecraftClient.getNetworkHandler() == null) {
         this.l.clear();
      } else {
         while (!this.l.isEmpty()) {
            Packet packet1 = (Packet)this.l.poll();
            if (packet1 != null) {
               dev.leg1tka.network.PacketSender.a(packet1);
            }
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player != null && this.isEnabled()) {
         if (eventAttackEntity1.getPlayer() == minecraftClient.player) {
            if (this.e.getValue()) {
               this.p();
               if (minecraftClient.player != null) {
                  this.n = minecraftClient.player.getBoundingBox();
               }
            }
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onPacket(EventPacket eventPacket1) {
      if (minecraftClient.player != null && eventPacket1.getType() == EventPacket.Type.SEND) {
         Packet packet2 = eventPacket1.getPacket();
         if (packet2 instanceof PlayerInteractEntityC2SPacket) {
            if (this.f.getValue()) {
               this.setEnabled(false);
            }
         } else if (packet2 instanceof PlayerMoveC2SPacket) {
            eventPacket1.cancel();
            this.l.add(packet2);
         } else {
            if (this.d.b("Полный") && (packet2 instanceof PlayerInputC2SPacket || packet2 instanceof PlayerActionC2SPacket)) {
               eventPacket1.cancel();
               this.l.add(packet2);
            }
         }
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         this.o++;
         if (this.g.getValue() && this.o >= this.i.e().intValue()) {
            if (this.h.b("Выключение")) {
               this.setEnabled(false);
            } else {
               this.p();
               this.o = 0;
               if (minecraftClient.player != null) {
                  this.n = minecraftClient.player.getBoundingBox();
               }
            }
         }
      }
   }

   @EventLink(
      priority = -100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null && this.j.getValue() && this.n != null) {
         Vec3d vec3d2 = event3DRender1.getCamera().getPos();
         Box box3 = this.n.offset(-vec3d2.x, -vec3d2.y, -vec3d2.z);
         Matrix4f matrix4f4 = event3DRender1.getMatrices().peek().getPositionMatrix();
         if (this.k.b("Красивый")) {
            this.q();
            this.m.a(matrix4f4, box3);
         } else {
            this.m.b();
            this.a(matrix4f4, box3);
         }
      } else {
         this.m.b();
      }
   }

   @EventLink(
      priority = 200
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.m.a() && this.j.getValue() && this.k.b("Красивый")) {
         int intValue2 = dev.leg1tka.api.utils.text.ColorUtils.d(this.p, -1, 0.38F);
         int intValue3 = dev.leg1tka.api.utils.text.ColorUtils.d(this.q, -1, 0.42F);
         this.m.a(dev.leg1tka.render.FramebufferOutline.BloomConfig.c(intValue2, intValue3));
      }
   }

   private void a(Matrix4f matrix4f1, Box box2) {
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableCull();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
      RenderSystem.lineWidth(1.5F);
      int intValue3 = -520093697;
      int intValue4 = intValue3 >> 16 & 0xFF;
      int intValue5 = intValue3 >> 8 & 0xFF;
      int intValue6 = intValue3 & 0xFF;
      int intValue7 = intValue3 >> 24 & 0xFF;
      dev.leg1tka.render.RenderPrimitives.drawOutlinedBox(matrix4f1, box2, intValue4, intValue5, intValue6, intValue7);
      RenderSystem.lineWidth(1.0F);
      RenderSystem.depthMask(true);
      RenderSystem.enableDepthTest();
      RenderSystem.enableCull();
      RenderSystem.disableBlend();
   }

   private void q() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null && AspectDLC.INSTANCE.themeStorage.b() != null) {
         dev.leg1tka.gui.themes.ThemePalette value1 = AspectDLC.INSTANCE.themeStorage.b().a();
         if (value1 == null) {
            this.p = dev.leg1tka.api.utils.text.ColorUtils.k(0);
            this.q = dev.leg1tka.api.utils.text.ColorUtils.k(180);
         } else {
            if (!"Rainbow".equals(value1.b())) {
               int intValue2 = value1.c != null && value1.c.length > 0 ? value1.c[0] : dev.leg1tka.api.utils.text.ColorUtils.k(0);
               this.p = intValue2;
               this.q = intValue2;
            } else {
               this.p = dev.leg1tka.api.utils.text.ColorUtils.a();
               this.q = dev.leg1tka.api.utils.text.ColorUtils.k(180);
            }
         }
      } else {
         this.p = dev.leg1tka.api.utils.text.ColorUtils.k(0);
         this.q = dev.leg1tka.api.utils.text.ColorUtils.k(180);
      }
   }
}
