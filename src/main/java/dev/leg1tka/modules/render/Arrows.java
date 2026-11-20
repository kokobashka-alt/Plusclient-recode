package dev.leg1tka.modules.render;


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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class Arrows extends Module {
   public static Arrows c = new Arrows();
   private static final Identifier d = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/arrows/arr.png");
   private final NumberSetting e = new NumberSetting("Радиус", 58.0F, 30.0F, 120.0F, 1.0F);
   private final NumberSetting f = new NumberSetting("Размер", 13.0F, 8.0F, 28.0F, 0.5F);
   private final BooleanSetting g = new BooleanSetting("Игроки с броней", false);
   private final Map<UUID, Arrows.a> h = new HashMap<>();
   private final Set<UUID> i = new HashSet<>();

   public Arrows() {
      super(
         "Arrows",
         "Красивые стрелочки на энтити",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.e, this.f, this.g});
   }

   @EventLink
   public void onRender(EventRender.Default default1) {
      if (minecraftClient.player != null && minecraftClient.world != null && !minecraftClient.options.hudHidden) {
         if (minecraftClient.options.getPerspective() != Perspective.FIRST_PERSON) {
            this.p();
         } else {
            float floatValue2 = default1.getPartialTicks();
            float floatValue3 = (float)minecraftClient.getWindow().getScaledWidth() * 0.5F;
            float floatValue4 = (float)minecraftClient.getWindow().getScaledHeight() * 0.5F;
            float floatValue5 = this.f.f();
            float floatValue6 = floatValue4 - this.e.f();
            float floatValue7 = this.a(floatValue2);
            Vec3d vec3d8 = this.b(floatValue2);
            this.i.clear();

            for (AbstractClientPlayerEntity abstractClientPlayerEntity10 : minecraftClient.world.getPlayers()) {
               if (abstractClientPlayerEntity10 != minecraftClient.player && abstractClientPlayerEntity10.isAlive() && !abstractClientPlayerEntity10.isSpectator() && !this.b(abstractClientPlayerEntity10)) {
                  UUID uUID11 = abstractClientPlayerEntity10.getUuid();
                  Arrows.a value12 = this.h.computeIfAbsent(uUID11, uUID0 -> new Arrows.a());
                  this.i.add(uUID11);
                  if (this.g.getValue() && !this.c(abstractClientPlayerEntity10)) {
                     value12.a = this.a(value12.a, 0.0F, 0.1F);
                  } else {
                     int intValue13 = this.a(abstractClientPlayerEntity10);
                     float floatValue14 = this.a(abstractClientPlayerEntity10, floatValue2, floatValue7, vec3d8);
                     value12.b = this.b(value12.b, floatValue14, 0.18F);
                     value12.a = this.a(value12.a, 1.0F, 0.12F);
                     float floatValue15 = MathHelper.clamp(value12.a, 0.0F, 1.0F);
                     if (!(floatValue15 <= 0.01F)) {
                        int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue13, floatValue15);
                        int intValue17 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue13, 0.55F), floatValue15 * 0.65F);
                        this.a(default1.getContext().getMatrices(), floatValue3, floatValue4, floatValue6, floatValue5, value12.b, intValue16, intValue17);
                     }
                  }
               }
            }

            this.h.entrySet().removeIf(entry1x -> {
               if (this.i.contains(entry1x.getKey())) {
                  return false;
               } else {
                  Arrows.a value2x = (Arrows.a)entry1x.getValue();
                  value2x.a = this.a(value2x.a, 0.0F, 0.1F);
                  return value2x.a <= 0.02F;
               }
            });
         }
      } else {
         this.h.clear();
      }
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8) {
      matrixStack1.push();
      matrixStack1.translate(floatValue2, floatValue3, 0.0F);
      matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue6));
      matrixStack1.translate(-floatValue2, -floatValue3, 0.0F);
      float floatValue9 = floatValue2 - floatValue5 * 0.5F;
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, d, floatValue9, floatValue4 + floatValue5 * 0.08F, floatValue5, floatValue5, intValue8);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, d, floatValue9, floatValue4, floatValue5, floatValue5, intValue7);
      matrixStack1.pop();
   }

   private void p() {
      this.h.entrySet().removeIf(entry1 -> {
         Arrows.a value2 = (Arrows.a)entry1.getValue();
         value2.a = this.a(value2.a, 0.0F, 0.1F);
         return value2.a <= 0.02F;
      });
   }

   private float a(float floatValue1, float floatValue2, float floatValue3) {
      floatValue3 = MathHelper.clamp(floatValue3, 0.0F, 1.0F);
      return MathHelper.lerp(floatValue3, floatValue1, floatValue2);
   }

   private int a(AbstractClientPlayerEntity abstractClientPlayerEntity1) {
      String string2 = abstractClientPlayerEntity1.getName().getString();
      boolean flag3 = AspectDLC.INSTANCE.friendStorage != null && AspectDLC.INSTANCE.friendStorage.c(string2);
      return flag3 ? dev.leg1tka.api.utils.text.ColorUtils.b(85, 255, 85) : dev.leg1tka.api.utils.text.ColorUtils.a();
   }

   private float a(Entity entity1, float floatValue2, float floatValue3, Vec3d vec3d4) {
      Vec3d vec3d5 = dev.leg1tka.api.utils.math.MathOperations.a(entity1, floatValue2);
      double doubleValue6 = vec3d5.x - vec3d4.x;
      double doubleValue8 = vec3d5.z - vec3d4.z;
      float floatValue10 = (float)(-Math.toDegrees(Math.atan2(doubleValue6, doubleValue8)));
      return MathHelper.wrapDegrees(floatValue10 - floatValue3);
   }

   private float a(float floatValue1) {
      return dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.b() : MathHelper.lerp(floatValue1, minecraftClient.player.prevYaw, minecraftClient.player.getYaw());
   }

   private Vec3d b(float floatValue1) {
      return dev.leg1tka.rotation.FreeLookStorage.a() && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null ? minecraftClient.gameRenderer.getCamera().getPos() : dev.leg1tka.api.utils.math.MathOperations.a(minecraftClient.player, floatValue1);
   }

   private float b(float floatValue1, float floatValue2, float floatValue3) {
      float floatValue4 = MathHelper.wrapDegrees(floatValue2 - floatValue1);
      return floatValue1 + floatValue4 * floatValue3;
   }

   private boolean b(AbstractClientPlayerEntity abstractClientPlayerEntity1) {
      if (abstractClientPlayerEntity1.getCustomName() != null) {
         String string2 = abstractClientPlayerEntity1.getCustomName().getString();
         if (string2 != null && string2.startsWith("Ghost_")) {
            return true;
         }
      }

      return "OtherClientPlayerEntity".equals(abstractClientPlayerEntity1.getClass().getSimpleName()) && abstractClientPlayerEntity1.getPitch() == -30.0F;
   }

   private boolean c(AbstractClientPlayerEntity abstractClientPlayerEntity1) {
      for (ItemStack itemStack3 : abstractClientPlayerEntity1.getArmorItems()) {
         if (!itemStack3.isEmpty()) {
            return true;
         }
      }

      return false;
   }

   static class a {
      float a;
      float b;

      a() {
      }
   }
}
