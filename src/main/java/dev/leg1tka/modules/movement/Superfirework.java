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

import lombok.Generated;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;

public class Superfirework extends Module {
   private static final String[] d = new String[]{"0 - 5", "5 - 10", "10 - 15", "15 - 20", "20 - 25", "25 - 30", "30 - 35", "35 - 40", "40 - 45"};
   private static long e;
   public static Superfirework c = new Superfirework();
   private final NumberSetting[] f = new NumberSetting[9];
   private final NumberSetting[] g = new NumberSetting[9];
   private final ModeSetting h = new ModeSetting("Сервер", "Custom", "Custom", "BravoHVH");
   private final BooleanSetting i = new BooleanSetting("Дебаг", false).visibleWhen(this::p);
   private long j;

   public Superfirework() {
      super("Superfirework", "Ускоряет на элитрах", Category.MOVEMENT);

      for (int intValue1 = 0; intValue1 < this.f.length; intValue1++) {
         this.f[intValue1] = new NumberSetting("yaw " + d[intValue1], 1.5F, 1.5F, 2.5F, 0.01F).a(this::p);
      }

      for (int intValue2 = 0; intValue2 < this.g.length; intValue2++) {
         this.g[intValue2] = new NumberSetting("pitch " + d[intValue2], 1.5F, 1.5F, 2.5F, 0.01F).a(this::p);
      }

      this.addSettings(new Setting[]{this.h, this.i});
      this.addSettings(this.f);
      this.addSettings(this.g);
   }

   public boolean p() {
      return this.h.b("Custom");
   }

   public Vec2f q() {
      float floatValue1 = minecraftClient.player != null ? minecraftClient.player.getYaw() : 0.0F;
      float floatValue2 = minecraftClient.player != null ? minecraftClient.player.getPitch() : 0.0F;
      AttackAura attackAura3 = AttackAura.c;
      if (attackAura3 != null && attackAura3.isEnabled() && attackAura3.E() != null) {
         floatValue1 = attackAura3.H();
         floatValue2 = attackAura3.I();
      }

      float floatValue4 = this.a(MathHelper.wrapDegrees(floatValue1));
      float floatValue5 = this.a(Math.abs(floatValue2));
      int intValue6 = this.a(floatValue4, this.f.length);
      int intValue7 = this.a(floatValue5, this.g.length);
      float floatValue8 = this.f[intValue6].e().floatValue();
      float floatValue9 = this.g[intValue7].e().floatValue();
      if (floatValue9 > floatValue8) {
         floatValue8 = floatValue9;
      }

      this.a(intValue6, floatValue8, intValue7, floatValue9);
      return new Vec2f(floatValue8, floatValue9);
   }

   private void a(int intValue1, float floatValue2, int intValue3, float floatValue4) {
      if (this.i.getValue()) {
         long longValue5 = System.currentTimeMillis();
         if (longValue5 - this.j >= 800L) {
            this.j = longValue5;
            dev.leg1tka.api.utils.text.ClientChat.a("yaw " + d[intValue1] + ": " + floatValue2 + " | pitch " + d[intValue3] + ": " + floatValue4);
         }
      }
   }

   private int a(float floatValue1, int intValue2) {
      return Math.min((int)(floatValue1 / 5.0F), intValue2 - 1);
   }

   private float a(float floatValue1) {
      float floatValue2 = Math.abs(floatValue1);
      if (floatValue2 > 90.0F) {
         floatValue2 = 180.0F - floatValue2;
      }

      if (floatValue2 > 45.0F) {
         floatValue2 = 90.0F - floatValue2;
      }

      return floatValue2;
   }

   @Generated
   public NumberSetting[] r() {
      return this.f;
   }

   @Generated
   public NumberSetting[] s() {
      return this.g;
   }

   @Generated
   public ModeSetting t() {
      return this.h;
   }

   @Generated
   public BooleanSetting u() {
      return this.i;
   }

   @Generated
   public long v() {
      return this.j;
   }

   @Generated
   public void a(long longValue1) {
      this.j = longValue1;
   }
}
