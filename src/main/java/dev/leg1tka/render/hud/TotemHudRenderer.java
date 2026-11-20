package dev.leg1tka.render.hud;

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
import dev.leg1tka.api.events.implement.EventRender;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;

public class TotemHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final dev.leg1tka.api.animation.Animation f = new dev.leg1tka.api.animation.Animation(0.0F, 9.0F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation g = new dev.leg1tka.api.animation.Animation(0.0F, 4.6F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation h = new dev.leg1tka.api.animation.Animation(1.0F, 9.2F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation i = new dev.leg1tka.api.animation.Animation(1.0F, 7.4F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation j = new dev.leg1tka.api.animation.Animation(20.0F, 7.0F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation k = new dev.leg1tka.api.animation.Animation(0.0F, 7.0F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation l = new dev.leg1tka.api.animation.Animation(0.0F, 9.2F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation m = new dev.leg1tka.api.animation.Animation(0.0F, 7.4F, dev.leg1tka.api.animation.Easings.h);
   private final dev.leg1tka.api.animation.Animation n = new dev.leg1tka.api.animation.Animation(0.0F, 9.0F, dev.leg1tka.api.animation.Easings.h);
   private final List o = new ObjectArrayList();
   private LivingEntity p;
   private float q = 20.0F;
   private boolean r = true;
   private boolean s = false;
   private boolean t = false;
   private boolean u = false;
   private float v = 0.0F;
   private float w = 0.0F;
   private long x = System.nanoTime();
   private LivingEntity y;
   private int z = 0;
   private int A = dev.leg1tka.api.utils.text.ColorUtils.a(124, 91, 242, 255);
   private int B = dev.leg1tka.api.utils.text.ColorUtils.a(93, 67, 175, 255);
   private Framebuffer C;
   private int D = -1;
   private int E = -1;
   private final ItemStack[] F = new ItemStack[6];
   private final ItemStack[] G = new ItemStack[4];
   private boolean H = false;
   private final dev.leg1tka.api.animation.Animation I = new dev.leg1tka.api.animation.Animation(0.0F, 12.0F, dev.leg1tka.api.animation.Easings.F);
   private final dev.leg1tka.api.animation.Animation J = new dev.leg1tka.api.animation.Animation(1.0F, 8.0F, dev.leg1tka.api.animation.Easings.E);
   private boolean K = false;

   private static float i() {
      return 1.0F;
   }

   public TotemHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   public boolean e() {
      return this.r;
   }

   public void c(boolean flag1) {
      this.r = flag1;
      if (!flag1) {
         this.o.clear();
      }
   }

   public boolean f() {
      return this.s;
   }

   public void d(boolean flag1) {
      this.s = flag1;
   }

   public boolean g() {
      return this.t;
   }

   public void e(boolean flag1) {
      this.t = flag1;
   }

   public boolean h() {
      return this.u;
   }

   public void f(boolean flag1) {
      this.u = flag1;
   }

   private int a(int intValue1, int intValue2, float floatValue3) {
      floatValue3 = MathHelper.clamp(floatValue3, 0.0F, 1.0F);
      int intValue4 = intValue1 >> 16 & 0xFF;
      int intValue5 = intValue1 >> 8 & 0xFF;
      int intValue6 = intValue1 & 0xFF;
      int intValue7 = intValue1 >> 24 & 0xFF;
      int intValue8 = intValue2 >> 16 & 0xFF;
      int intValue9 = intValue2 >> 8 & 0xFF;
      int intValue10 = intValue2 & 0xFF;
      int intValue11 = intValue2 >> 24 & 0xFF;
      int intValue12 = (int)((float)intValue4 + (float)(intValue8 - intValue4) * floatValue3);
      int intValue13 = (int)((float)intValue5 + (float)(intValue9 - intValue5) * floatValue3);
      int intValue14 = (int)((float)intValue6 + (float)(intValue10 - intValue6) * floatValue3);
      int intValue15 = (int)((float)intValue7 + (float)(intValue11 - intValue7) * floatValue3);
      return intValue15 << 24 | intValue12 << 16 | intValue13 << 8 | intValue14;
   }

   private void j() {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         int intValue1 = minecraftClient.getWindow().getFramebufferWidth();
         int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
         if (this.C == null || this.D != intValue1 || this.E != intValue2) {
            if (this.C != null) {
               this.C.delete();
            }

            this.C = new SimpleFramebuffer(intValue1, intValue2, true);
            this.D = intValue1;
            this.E = intValue2;
         }
      }
   }

   private int a(LivingEntity livingEntity1) {
      int intValue2 = 0;

      for (ItemStack itemStack4 : livingEntity1.getArmorItems()) {
         if (intValue2 < this.G.length) {
            this.G[intValue2++] = itemStack4;
         }
      }

      int intValue6 = 0;

      for (int intValue7 = intValue2 - 1; intValue7 >= 0; intValue7--) {
         this.F[intValue6++] = this.G[intValue7];
         this.G[intValue7] = ItemStack.EMPTY;
      }

      ItemStack itemStack8 = livingEntity1.getMainHandStack();
      if (!itemStack8.isEmpty()) {
         this.F[intValue6++] = itemStack8;
      }

      ItemStack itemStack5 = livingEntity1.getOffHandStack();
      if (!itemStack5.isEmpty()) {
         this.F[intValue6++] = itemStack5;
      }

      return intValue6;
   }

   private boolean g(boolean flag1) {
      if (!flag1) {
         return false;
      } else {
         this.j();
         if (this.C == null) {
            return false;
         } else {
            this.C.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            this.C.clear();
            this.C.beginWrite(false);
            return true;
         }
      }
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = floatValue2 + floatValue4 * 0.5F;
      float floatValue9 = floatValue3 + floatValue5 * 0.5F;
      matrixStack1.translate(floatValue8, floatValue9, 0.0F);
      matrixStack1.scale(floatValue6, floatValue7, 1.0F);
      matrixStack1.translate(-floatValue8, -floatValue9, 0.0F);
   }

   private void a(MatrixStack matrixStack1, LivingEntity livingEntity2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      if (livingEntity2 != null && !(floatValue6 <= 0.02F)) {
         long longValue8 = System.nanoTime();
         float floatValue10 = MathHelper.clamp((float)(longValue8 - this.x) / 1.0E9F * 60.0F, 0.2F, 3.0F);
         this.x = longValue8;
         if (this.y != livingEntity2) {
            this.o.clear();
            this.y = livingEntity2;
            this.z = Math.max(0, livingEntity2.hurtTime);
         }

         ThreadLocalRandom threadLocalRandom11 = ThreadLocalRandom.current();
         float floatValue12 = floatValue3 + floatValue5 * 0.5F;
         float floatValue13 = floatValue4 + floatValue5 * 0.5F;
         int intValue14 = Math.max(0, livingEntity2.hurtTime);
         boolean flag15 = intValue14 > 0 && (intValue14 > this.z || intValue14 % 3 == 0);
         this.z = intValue14;
         if (flag15) {
            int intValue16 = 1 + threadLocalRandom11.nextInt(2);

            for (int intValue17 = 0; intValue17 < intValue16 && this.o.size() < 14; intValue17++) {
               float floatValue18 = (float)(threadLocalRandom11.nextDouble() * Math.PI * 2.0);
               float floatValue19 = threadLocalRandom11.nextFloat() * floatValue5 * 0.24F;
               float floatValue20 = (float)(threadLocalRandom11.nextDouble() * Math.PI * 2.0);
               float floatValue21 = 0.58F + threadLocalRandom11.nextFloat() * 0.9F;
               dev.leg1tka.render.hud.TotemHudRenderer.a value22 = new dev.leg1tka.render.hud.TotemHudRenderer.a();
               value22.a = floatValue12 + MathHelper.cos(floatValue18) * floatValue19;
               value22.b = floatValue13 + MathHelper.sin(floatValue18) * floatValue19;
               value22.c = MathHelper.cos(floatValue20) * floatValue21 + (value22.a - floatValue12) * 0.025F;
               value22.d = MathHelper.sin(floatValue20) * floatValue21 + (value22.b - floatValue13) * 0.025F;
               value22.e = 3.8F + threadLocalRandom11.nextFloat() * 1.4F;
               value22.f = 0.0F;
               value22.g = 74.0F + threadLocalRandom11.nextFloat() * 42.0F;
               this.o.add(value22);
            }
         }

         for (int intValue24 = this.o.size() - 1; intValue24 >= 0; intValue24--) {
            dev.leg1tka.render.hud.TotemHudRenderer.a value25 = (dev.leg1tka.render.hud.TotemHudRenderer.a)this.o.get(intValue24);
            value25.f += floatValue10;
            if (value25.f >= value25.g) {
               this.o.remove(intValue24);
            } else {
               value25.a = value25.a + value25.c * floatValue10;
               value25.b = value25.b + value25.d * floatValue10;
               value25.c = value25.c * (float)Math.pow(0.975F, (double)floatValue10);
               value25.d = value25.d * (float)Math.pow(0.975F, (double)floatValue10);
               value25.d += 0.0012F * floatValue10;
               float floatValue26 = 1.0F - value25.f / value25.g;
               float floatValue27 = floatValue26 * floatValue26 * (3.0F - 2.0F * floatValue26);
               float floatValue28 = floatValue6 * floatValue27;
               if (!(floatValue28 <= 0.02F)) {
                  float floatValue29 = value25.a - value25.e * 0.5F;
                  float floatValue30 = value25.b - value25.e * 0.5F;
                  int intValue23 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue7, floatValue28 * 0.58F);
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue29, floatValue30, value25.e, value25.e, value25.e * 0.45F, intValue23);
               }
            }
         }
      } else {
         this.o.clear();
         this.y = livingEntity2;
         this.z = 0;
      }
   }

   private void a(EventRender.Default default1, MatrixStack matrixStack2, ItemStack itemStack3, float floatValue4, float floatValue5, float floatValue6) {
      if (!itemStack3.isEmpty()) {
         matrixStack2.push();
         float floatValue7 = 16.0F * floatValue6;
         matrixStack2.translate(floatValue4, floatValue5, 0.0F);
         matrixStack2.scale(floatValue6, floatValue6, 1.0F);
         default1.getContext().drawItem(itemStack3, 0, 0);
         matrixStack2.pop();
      }
   }

   private void a(EventRender.Default default1, MatrixStack matrixStack2, LivingEntity livingEntity3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, int intValue9) {
      if (default1 != null && matrixStack2 != null && livingEntity3 != null && !(floatValue8 <= 0.01F)) {
         FTHelper fTHelper10 = FTHelper.c;
         if (fTHelper10 != null && fTHelper10.isEnabled() && fTHelper10.p().getValue()) {
            if (livingEntity3 instanceof PlayerEntity playerEntity11) {
               boolean flag12 = minecraftClient.currentScreen instanceof ChatScreen && minecraftClient.player != null && playerEntity11.getUuid().equals(minecraftClient.player.getUuid());
               List<dev.leg1tka.network.bots.BotSoundEvents.a> list13 = dev.leg1tka.network.bots.BotSoundEvents.a(playerEntity11);
               if (list13.isEmpty()) {
                  if (!flag12) {
                     return;
                  }

                  long longValue14 = System.currentTimeMillis();
                  list13 = List.of(
                     new dev.leg1tka.network.bots.BotSoundEvents.a("Тотем", new ItemStack(Items.TOTEM_OF_UNDYING), longValue14 - 15000L, longValue14 + 45000L, 16734810, null),
                     new dev.leg1tka.network.bots.BotSoundEvents.a("Яблоко", new ItemStack(Items.GOLDEN_APPLE), longValue14 - 12000L, longValue14 + 18000L, 16734810, null)
                  );
               }

               float floatValue31 = floatValue5 + floatValue7 + 3.0F;
               float floatValue15 = 14.0F;
               float floatValue16 = 14.0F;
               float floatValue17 = floatValue4;
               dev.leg1tka.render.font.MsdfTextRenderer value18 = dev.leg1tka.render.font.FontCache.a("nunito", 9);
               if (value18 == null) {
                  value18 = dev.leg1tka.render.font.FontCache.a("semibold", 9);
               }

               for (dev.leg1tka.network.bots.BotSoundEvents.a value20 : list13) {
                  this.a(matrixStack2, floatValue17, floatValue31, floatValue15, floatValue16, intValue9, floatValue8);
                  float floatValue21 = 0.55F * floatValue8;
                  float floatValue22 = 16.0F * floatValue21;
                  float floatValue23 = floatValue17 + (floatValue15 - floatValue22) * 0.5F;
                  float floatValue24 = floatValue31 + (floatValue16 - floatValue22) * 0.5F - 1.0F;
                  this.a(default1, matrixStack2, value20.c, floatValue23, floatValue24, floatValue21);
                  float floatValue25 = value20.c();
                  if (floatValue25 > 0.01F) {
                     int intValue26 = value20.e ? dev.leg1tka.api.utils.text.ColorUtils.a(value20.d >> 16 & 0xFF, value20.d >> 8 & 0xFF, value20.d & 0xFF, (int)(255.0F * floatValue8)) : intValue9;
                     dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue17 + 1.5F, floatValue31 + floatValue16 - 2.0F, (floatValue15 - 3.0F) * floatValue25, 1.2F, 0.6F, intValue26);
                  }

                  float floatValue32 = value20.a();
                  int intValue27 = (int)floatValue32;
                  String string28 = intValue27 >= 60 ? intValue27 / 60 + (intValue27 % 60 < 10 ? ":0" : ":") + intValue27 % 60 : String.format(Locale.US, "%.1f", floatValue32);
                  if (value18 != null) {
                     float floatValue29 = value18.a(string28);
                     int intValue30 = floatValue32 <= 3.0F ? dev.leg1tka.api.utils.text.ColorUtils.a(255, 90, 90, (int)(255.0F * floatValue8)) : dev.leg1tka.api.utils.text.ColorUtils.a(230, 230, 240, (int)(255.0F * floatValue8));
                     value18.a(matrixStack2, string28, floatValue17 + (floatValue15 - floatValue29) * 0.5F, floatValue31 + floatValue16 + 1.5F, intValue30);
                     floatValue17 += Math.max(floatValue15, floatValue29) + 3.5F;
                  } else {
                     floatValue17 += floatValue15 + 3.5F;
                  }
               }
            }
         }
      }
   }

   private String a(LivingEntity livingEntity1, float floatValue2) {
      this.H = false;
      return String.format("%.1f", floatValue2);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (this.t) {
         this.c(default1);
      } else {
         this.b(default1);
      }

      super.a(default1);
   }

   public void b(EventRender.Default default1) {
      if (minecraftClient.player == null) {
         this.o.clear();
         this.z = 0;
         this.e.c(0.0F);
         this.e.d(0.0F);
      } else {
         AttackAura attackAura2 = ModuleManager.i;
         boolean flag3 = minecraftClient.currentScreen instanceof ChatScreen;
         LivingEntity livingEntity4 = attackAura2 != null ? attackAura2.E() : null;
         boolean flag5 = flag3 || livingEntity4 != null;
         this.f.setSpeed(flag5 ? 9.0F : 5.0F);
         this.f.animateTo(flag5 ? 1.0F : 0.0F);
         float floatValue6 = MathHelper.clamp(this.f.getValue(), 0.0F, 1.0F);
         if (flag5) {
            this.p = (LivingEntity)(flag3 ? minecraftClient.player : livingEntity4);
         }

         LivingEntity livingEntity7 = (LivingEntity)(flag5 ? (flag3 ? minecraftClient.player : livingEntity4) : this.p);
         if (livingEntity7 != null && !(floatValue6 <= 0.01F)) {
            int intValue8;
            if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
               intValue8 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
            } else {
               intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a();
            }

            int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue8, 0.4F);
            if (flag5) {
               this.A = intValue8;
               this.B = intValue9;
            }

            int intValue10 = flag5 ? intValue8 : this.A;
            int intValue11 = flag5 ? intValue9 : this.B;
            float floatValue12 = livingEntity7.getAbsorptionAmount();
            boolean flag13 = floatValue12 > 0.0F;
            this.n.setSpeed(flag13 ? 9.0F : 5.0F);
            this.n.animateTo(flag13 ? 1.0F : 0.0F);
            float floatValue14 = MathHelper.clamp(this.n.getValue(), 0.0F, 1.0F);
            float floatValue15 = Math.max(1.0F, livingEntity7.getMaxHealth());
            float floatValue16 = Math.max(1.0F, this.q);
            float floatValue17 = HPFixer.b((LivingEntity)livingEntity7);
            boolean flag18 = floatValue17 == -1.0F;
            float floatValue19 = flag18 ? 0.0F : floatValue17;
            this.j.animateTo(floatValue19);
            this.k.animateTo(flag5 ? floatValue12 : 0.0F);
            float floatValue20 = MathHelper.clamp(this.j.getValue(), 0.0F, floatValue15);
            float floatValue21 = MathHelper.clamp(this.k.getValue(), 0.0F, floatValue16);
            float floatValue22 = flag18 ? 0.0F : MathHelper.clamp(floatValue17 / floatValue15, 0.0F, 1.0F);
            this.h.animateTo(floatValue22);
            float floatValue23 = MathHelper.clamp(this.h.getValue(), 0.0F, 1.0F);
            if (floatValue23 < this.i.getValue()) {
               this.i.animateTo(floatValue23);
            }

            float floatValue24 = MathHelper.clamp(this.i.getValue(), 0.0F, 1.0F);
            boolean flag25 = !flag5;
            if (flag25) {
               floatValue24 = floatValue23;
            }

            float floatValue26 = this.e.a();
            float floatValue27 = this.e.b();
            float floatValue28 = 32.0F;
            float floatValue29 = 18.0F;
            float floatValue30 = 3.0F;
            float floatValue31 = (floatValue28 - floatValue29) * 0.5F - 3.5F;
            float floatValue32 = 5.0F;
            float floatValue33 = 5.0F;
            float floatValue34 = 5.9F;
            float floatValue35 = floatValue27 + floatValue28 - 7.5F;
            String string36 = livingEntity7.getName().getString();
            String string37 = flag18 ? "HP: ???" : "HP: " + String.format("%.1f", floatValue20);
            dev.leg1tka.render.font.MsdfTextRenderer value38 = this.a(13);
            dev.leg1tka.render.font.MsdfTextRenderer value39 = this.a(12);
            float floatValue40 = floatValue26 + floatValue30 + floatValue29 + floatValue32;
            float floatValue41 = value38.a(string36);
            float floatValue42 = value39.a(string37);
            String string43 = flag13 && floatValue14 > 0.01F ? " (+" + String.format("%.0f", floatValue21) + ")" : "";
            float floatValue44 = floatValue42 + value39.a(string43);
            float floatValue45 = Math.max(floatValue41, floatValue44);
            float floatValue46 = Math.max(90.0F, floatValue30 + floatValue29 + floatValue32 + floatValue45 + floatValue33);
            float floatValue47 = floatValue26 + floatValue30;
            float floatValue48 = floatValue27 + floatValue31;
            int intValue52 = (int)(255.0F * floatValue6);
            MatrixStack matrixStack53 = default1.getContext().getMatrices();
            matrixStack53.push();
            int intValue54 = 0;

            for (ItemStack itemStack56 : livingEntity7.getArmorItems()) {
               if (intValue54 < this.G.length) {
                  this.G[intValue54++] = itemStack56;
               }
            }

            ItemStack itemStack71 = livingEntity7.getMainHandStack();
            ItemStack itemStack74 = livingEntity7.getOffHandStack();
            float floatValue57 = 10.0F;
            float floatValue58 = 0.5F * floatValue6;
            float floatValue59 = 11.0F;
            float floatValue60 = floatValue27 - floatValue59 - 2.5F;
            int intValue61 = 0;

            for (int intValue62 = intValue54 - 1; intValue62 >= 0; intValue62--) {
               if (this.G[intValue62] != null && !this.G[intValue62].isEmpty()) {
                  intValue61++;
               }
            }

            int intValue88 = 0;
            if (!itemStack71.isEmpty()) {
               intValue88++;
            }

            if (!itemStack74.isEmpty()) {
               intValue88++;
            }

            if (intValue61 > 0) {
               float floatValue63 = (float)intValue61 * floatValue57;
               this.a(matrixStack53, floatValue26, floatValue60, floatValue63, floatValue59, intValue10, floatValue6);
               float floatValue64 = 16.0F * floatValue58;
               int intValue65 = intValue54 - 1;

               for (int intValue66 = 0; intValue65 >= 0; intValue65--) {
                  ItemStack itemStack67 = this.G[intValue65];
                  if (itemStack67 != null && !itemStack67.isEmpty()) {
                     float floatValue68 = floatValue26 + (float)intValue66 * floatValue57 + (floatValue57 - floatValue64) * 0.5F;
                     float floatValue69 = floatValue60 + (floatValue59 - floatValue64) * 0.5F;
                     this.a(default1, matrixStack53, itemStack67, floatValue68, floatValue69, floatValue58);
                     intValue66++;
                  }
               }
            }

            if (intValue88 > 0) {
               float floatValue90 = floatValue26 + (float)intValue61 * floatValue57 + (intValue61 > 0 ? 2.5F : 0.0F);
               float floatValue93 = (float)intValue88 * floatValue57;
               this.a(matrixStack53, floatValue90, floatValue60, floatValue93, floatValue59, intValue10, floatValue6);
               float floatValue96 = 16.0F * floatValue58;
               int intValue97 = 0;
               if (!itemStack71.isEmpty()) {
                  float floatValue98 = floatValue90 + (float)intValue97 * floatValue57 + (floatValue57 - floatValue96) * 0.5F;
                  float floatValue100 = floatValue60 + (floatValue59 - floatValue96) * 0.5F;
                  this.a(default1, matrixStack53, itemStack71, floatValue98, floatValue100, floatValue58);
                  intValue97++;
               }

               if (!itemStack74.isEmpty()) {
                  float floatValue99 = floatValue90 + (float)intValue97 * floatValue57 + (floatValue57 - floatValue96) * 0.5F;
                  float floatValue101 = floatValue60 + (floatValue59 - floatValue96) * 0.5F;
                  this.a(default1, matrixStack53, itemStack74, floatValue99, floatValue101, floatValue58);
               }
            }

            for (int intValue91 = 0; intValue91 < intValue54; intValue91++) {
               this.G[intValue91] = ItemStack.EMPTY;
            }

            float floatValue70 = floatValue6 * i();
            if (floatValue70 > 0.001F) {
               this.a(matrixStack53, floatValue26, floatValue27, floatValue46, floatValue28, 3.0F, intValue10, floatValue70);
            }

            if (this.r) {
               this.a(matrixStack53, (LivingEntity)livingEntity7, floatValue47, floatValue48, floatValue29, floatValue6, intValue10);
            } else {
               this.o.clear();
            }

            if (livingEntity7 instanceof PlayerEntity playerEntity72) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack53, playerEntity72.getUuid(), floatValue47, floatValue48, floatValue29, 2.0F, floatValue6, 0.0F);
            } else {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack53, floatValue47, floatValue48, floatValue6);
               this.a(14).b(matrixStack53, "?", floatValue47 + floatValue29 / 2.0F, floatValue48 + 3.0F, dev.leg1tka.api.utils.text.ColorUtils.a(220, 220, 220, intValue52));
            }

            float floatValue73 = Math.max(8.0F, floatValue46 - floatValue30 - floatValue29 - floatValue32 - floatValue33);
            value38.a(matrixStack53, string36, floatValue40, floatValue27 + 5.5F, floatValue73, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue52));
            float floatValue75 = floatValue27 + 14.5F;
            if (flag18) {
               value39.a(matrixStack53, "HP: ", floatValue40, floatValue75, dev.leg1tka.api.utils.text.ColorUtils.a(232, 232, 232, intValue52));
               value39.a(matrixStack53, "???", floatValue40 + value39.a("HP: "), floatValue75, dev.leg1tka.api.utils.text.ColorUtils.a(255, 200, 100, intValue52));
            } else {
               value39.a(matrixStack53, "HP: ", floatValue40, floatValue75, dev.leg1tka.api.utils.text.ColorUtils.a(232, 232, 232, intValue52));
               floatValue57 = floatValue40 + value39.a("HP: ");
               value39.a(matrixStack53, String.format("%.1f", floatValue20), floatValue57, floatValue75, dev.leg1tka.api.utils.text.ColorUtils.a(232, 232, 232, intValue52));
               if (floatValue14 > 0.01F && !string43.isEmpty()) {
                  floatValue58 = floatValue57 + value39.a(String.format("%.1f", floatValue20));
                  int intValue81 = (int)(255.0F * floatValue14 * floatValue6);
                  value39.a(matrixStack53, string43, floatValue58, floatValue75, dev.leg1tka.api.utils.text.ColorUtils.a(236, 183, 39, intValue81), dev.leg1tka.api.utils.text.ColorUtils.a(147, 108, 16, intValue81));
               }
            }

            int intValue77 = dev.leg1tka.api.utils.text.ColorUtils.a(30, 30, 38, (int)(200.0F * floatValue6));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack53, floatValue26 + 2.0F, floatValue35, floatValue46 - 4.0F, floatValue34, 2.0F, intValue77);
            if (!flag18) {
               if (!flag25 && floatValue24 > floatValue23 + 0.01F) {
                  floatValue58 = (floatValue46 - 4.0F) * floatValue24;
                  if (floatValue58 > 1.0F) {
                     int intValue82 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue11, 0.6F), floatValue6 * 0.55F);
                     dev.leg1tka.render.RenderPrimitives.b(matrixStack53, floatValue26 + 2.0F, floatValue35, floatValue58, floatValue34, 2.0F, intValue82);
                  }
               }

               floatValue58 = (floatValue46 - 4.0F) * floatValue23;
               if (floatValue58 > 1.0F) {
                  int intValue83;
                  int intValue85;
                  if (this.s) {
                     intValue61 = dev.leg1tka.api.utils.text.ColorUtils.a(220, 50, 50, 255);
                     intValue88 = dev.leg1tka.api.utils.text.ColorUtils.a(220, 200, 50, 255);
                     int intValue92 = dev.leg1tka.api.utils.text.ColorUtils.a(80, 220, 80, 255);
                     if (floatValue23 < 0.5F) {
                        float floatValue94 = floatValue23 / 0.5F;
                        intValue85 = this.a(intValue61, intValue88, floatValue94);
                     } else {
                        float floatValue95 = (floatValue23 - 0.5F) / 0.5F;
                        intValue85 = this.a(intValue88, intValue92, floatValue95);
                     }

                     intValue83 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue85, 0.4F);
                  } else {
                     intValue83 = intValue11;
                     intValue85 = intValue10;
                  }

                  dev.leg1tka.render.RenderPrimitives.a(matrixStack53, floatValue26 + 2.0F, floatValue35, floatValue58, floatValue34, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(intValue83, floatValue6), dev.leg1tka.api.utils.text.ColorUtils.a(intValue85, floatValue6), true);
               }

               if (floatValue14 > 0.01F) {
                  floatValue59 = MathHelper.clamp(floatValue12 / floatValue16, 0.0F, 1.0F);
                  this.l.animateTo(floatValue59);
                  floatValue60 = (floatValue46 - 4.0F) * MathHelper.clamp(this.l.getValue(), 0.0F, 1.0F);
                  if (floatValue60 > 1.0F) {
                     dev.leg1tka.render.RenderPrimitives.a(
                        matrixStack53,
                        floatValue26 + 2.0F,
                        floatValue35,
                        floatValue60,
                        floatValue34,
                        2.0F,
                        dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(147, 108, 16, 255), floatValue14 * floatValue6),
                        dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(236, 183, 39, 255), floatValue14 * floatValue6),
                        true
                     );
                  }
               }
            } else {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack53, floatValue26 + 2.0F, floatValue35, (floatValue46 - 4.0F) * 0.5F, floatValue34, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(80, 80, 80, 255), floatValue6 * 0.5F));
            }

            this.a(default1, matrixStack53, (LivingEntity)livingEntity7, floatValue26, floatValue27, floatValue46, floatValue28, floatValue6, intValue10);
            matrixStack53.pop();
            this.e.c(floatValue46);
            this.e.d(floatValue28);
         } else {
            this.o.clear();
            this.z = 0;
            this.e.c(0.0F);
            this.e.d(0.0F);
            this.n.resetTo(0.0F);
            this.k.resetTo(0.0F);
            this.l.resetTo(0.0F);
            this.m.resetTo(0.0F);
         }
      }
   }

   public void c(EventRender.Default default1) {
      if (minecraftClient.player == null) {
         this.e.c(0.0F);
         this.e.d(0.0F);
      } else {
         AttackAura attackAura2 = ModuleManager.i;
         boolean flag3 = minecraftClient.currentScreen instanceof ChatScreen;
         LivingEntity livingEntity4 = attackAura2 != null ? attackAura2.E() : null;
         boolean flag5 = flag3 || livingEntity4 != null;
         this.f.setSpeed(flag5 ? 9.0F : 5.0F);
         this.f.animateTo(flag5 ? 1.0F : 0.0F);
         float floatValue6 = MathHelper.clamp(this.f.getValue(), 0.0F, 1.0F);
         if (flag5) {
            this.p = (LivingEntity)(flag3 ? minecraftClient.player : livingEntity4);
         }

         LivingEntity livingEntity7 = (LivingEntity)(flag5 ? (flag3 ? minecraftClient.player : livingEntity4) : this.p);
         if (livingEntity7 != null && !(floatValue6 <= 0.01F)) {
            int intValue8;
            if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
               intValue8 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
            } else {
               intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a();
            }

            int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue8, 0.5F);
            float floatValue10 = this.e.a();
            float floatValue11 = this.e.b();
            float floatValue12 = 112.0F;
            float floatValue13 = 44.0F;
            float floatValue14 = HPFixer.b((LivingEntity)livingEntity7);
            boolean flag15 = floatValue14 == -1.0F;
            float floatValue16 = Math.max(1.0F, livingEntity7.getMaxHealth());
            float floatValue17 = livingEntity7.getAbsorptionAmount();
            boolean flag18 = floatValue17 > 0.0F;
            this.n.setSpeed(flag18 ? 9.0F : 5.0F);
            this.n.animateTo(flag18 ? 1.0F : 0.0F);
            float floatValue19 = MathHelper.clamp(this.n.getValue(), 0.0F, 1.0F);
            float floatValue20 = 68.0F;
            float floatValue21 = 4.0F;
            float floatValue22 = flag15 ? 2.0F : MathHelper.clamp(floatValue14 / floatValue16 * floatValue20, 2.0F, floatValue20);
            this.v = this.v + (floatValue22 - this.v) * 0.1F;
            this.v = MathHelper.clamp(this.v, 2.0F, floatValue20);
            if (this.v < this.w) {
               this.w = this.w + (this.v - this.w) * 0.05F;
            }

            this.w = MathHelper.clamp(this.w, 2.0F, floatValue20);
            float floatValue23 = Math.max(1.0F, this.q);
            float floatValue24 = MathHelper.clamp(floatValue17 / floatValue23, 0.0F, 1.0F);
            this.l.animateTo(floatValue24);
            float floatValue25 = MathHelper.clamp(this.l.getValue(), 0.0F, 1.0F) * floatValue20;
            int intValue26 = (int)(255.0F * floatValue6);
            MatrixStack matrixStack27 = default1.getContext().getMatrices();
            matrixStack27.push();
            float floatValue28 = floatValue10 + floatValue12 / 2.0F;
            float floatValue29 = floatValue11 + floatValue13 / 2.0F;
            matrixStack27.translate(floatValue28, floatValue29, 0.0F);
            matrixStack27.scale(floatValue6, floatValue6, 1.0F);
            matrixStack27.translate(-floatValue28, -floatValue29, 0.0F);
            float floatValue30 = floatValue6 * i();
            if (floatValue30 > 0.001F) {
               this.a(matrixStack27, floatValue10, floatValue11, floatValue12, floatValue13, 3.0F, intValue8, floatValue30);
            }

            float floatValue31 = floatValue10 + 6.0F;
            float floatValue32 = floatValue11 + (floatValue13 - 22.0F) / 2.0F;
            if (livingEntity7 instanceof PlayerEntity) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack27, livingEntity7.getUuid(), floatValue31, floatValue32, 22.0F, 2.0F, floatValue6, 0.0F);
            } else {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack27, floatValue31, floatValue32, floatValue6);
               this.a(20).b(matrixStack27, "?", floatValue31 + 11.0F, floatValue32 + 5.0F, dev.leg1tka.api.utils.text.ColorUtils.a(220, 220, 220, intValue26));
            }

            String string33 = livingEntity7.getName().getString();
            float floatValue34 = 50.0F;
            if (this.a(13).b(string33) > floatValue34) {
               while (this.a(13).b(string33 + "...") > floatValue34 && string33.length() > 0) {
                  string33 = string33.substring(0, string33.length() - 1);
               }

               string33 = string33 + "...";
            }

            this.a(13).a(matrixStack27, string33, floatValue10 + 35.0F, floatValue11 + 7.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue26));
            String string35 = flag15 ? "???" : Math.round(floatValue14) + ".0";
            dev.leg1tka.render.font.MsdfTextRenderer value36 = this.a(12);
            float floatValue37 = value36.a(string35);
            value36.a(matrixStack27, string35, floatValue10 + floatValue12 - 7.0F - floatValue37, floatValue11 + 7.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue26));
            if (floatValue19 > 0.01F && !flag15) {
               String string38 = "+" + Math.round(floatValue17);
               float floatValue39 = floatValue10 + floatValue12 - 7.0F - floatValue37 - value36.a(string38) - 2.0F;
               int intValue40 = (int)(255.0F * floatValue19 * floatValue6);
               value36.a(matrixStack27, string38, floatValue39, floatValue11 + 7.0F, dev.leg1tka.api.utils.text.ColorUtils.a(236, 183, 39, intValue40), dev.leg1tka.api.utils.text.ColorUtils.a(147, 108, 16, intValue40));
            }

            float floatValue53 = floatValue11 + 18.0F;
            dev.leg1tka.render.font.MsdfTextRenderer value54 = dev.leg1tka.render.font.FontCache.a("iconnew", 11);
            if (value54 != null) {
               value54.a(matrixStack27, "m", floatValue10 + floatValue12 - 9.0F, floatValue53, dev.leg1tka.api.utils.text.ColorUtils.a(intValue8, (float)intValue26), dev.leg1tka.api.utils.text.ColorUtils.a(intValue9, (float)intValue26));
            }

            float floatValue55 = 8.0F;
            float floatValue41 = 2.0F;
            float floatValue42 = 0.5F * floatValue6;
            float floatValue43 = floatValue53 - 1.0F;
            int intValue44 = 0;

            for (ItemStack itemStack46 : livingEntity7.getArmorItems()) {
               if (intValue44 < this.G.length) {
                  this.G[intValue44++] = itemStack46;
               }
            }

            for (int intValue56 = 0; intValue56 < 4; intValue56++) {
               ItemStack itemStack58 = intValue44 > 0 ? this.G[3 - intValue56] : ItemStack.EMPTY;
               if (itemStack58 == null) {
                  itemStack58 = ItemStack.EMPTY;
               }

               float floatValue47 = floatValue10 + 35.0F + (float)intValue56 * (floatValue55 + floatValue41);
               if (!itemStack58.isEmpty()) {
                  this.a(default1, matrixStack27, itemStack58, floatValue47, floatValue43, floatValue42);
               }

               if (intValue44 > 0) {
                  this.G[3 - intValue56] = ItemStack.EMPTY;
               }
            }

            float floatValue57 = floatValue11 + floatValue13 - 8.0F;
            dev.leg1tka.render.RenderPrimitives.b(matrixStack27, floatValue10 + 34.0F, floatValue57, floatValue20, floatValue21, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(38, 34, 50, (int)(235.0F * floatValue6)));
            if (this.w > this.v + 1.0F) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack27, floatValue10 + 34.0F, floatValue57, this.w, floatValue21, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue9, 0.5F), floatValue6 * 0.6F), dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue8, 0.5F), floatValue6 * 0.6F), true);
            }

            if (this.v > 1.0F) {
               int intValue59;
               int intValue61;
               if (this.s) {
                  float floatValue48 = flag15 ? 0.0F : MathHelper.clamp(this.v / floatValue20, 0.0F, 1.0F);
                  int intValue49 = dev.leg1tka.api.utils.text.ColorUtils.a(220, 50, 50, 255);
                  int intValue50 = dev.leg1tka.api.utils.text.ColorUtils.a(220, 200, 50, 255);
                  int intValue51 = dev.leg1tka.api.utils.text.ColorUtils.a(80, 220, 80, 255);
                  if (floatValue48 < 0.5F) {
                     float floatValue52 = floatValue48 / 0.5F;
                     intValue61 = this.a(intValue49, intValue50, floatValue52);
                  } else {
                     float floatValue62 = (floatValue48 - 0.5F) / 0.5F;
                     intValue61 = this.a(intValue50, intValue51, floatValue62);
                  }

                  intValue59 = dev.leg1tka.api.utils.text.ColorUtils.b(intValue61, 0.4F);
               } else {
                  intValue59 = intValue9;
                  intValue61 = intValue8;
               }

               dev.leg1tka.render.RenderPrimitives.a(matrixStack27, floatValue10 + 34.0F, floatValue57, this.v, floatValue21, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(intValue59, floatValue6), dev.leg1tka.api.utils.text.ColorUtils.a(intValue61, floatValue6), true);
            }

            if (floatValue19 > 0.01F && floatValue25 > 1.0F) {
               float floatValue60 = floatValue19 * floatValue6;
               dev.leg1tka.render.RenderPrimitives.a(matrixStack27, floatValue10 + 34.0F, floatValue57, floatValue25, floatValue21, 1.5F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(147, 108, 16, 255), floatValue60), dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(236, 183, 39, 255), floatValue60), true);
            }

            this.a(default1, matrixStack27, (LivingEntity)livingEntity7, floatValue10, floatValue11, floatValue12, floatValue13, floatValue6, intValue8);
            matrixStack27.pop();
            this.e.c(floatValue12);
            this.e.d(floatValue13);
         } else {
            this.e.c(0.0F);
            this.e.d(0.0F);
         }
      }
   }

   public void d(EventRender.Default default1) {
      if (minecraftClient.player == null) {
         this.e.c(0.0F);
         this.e.d(0.0F);
      } else {
         AttackAura attackAura2 = ModuleManager.i;
         boolean flag3 = minecraftClient.currentScreen instanceof ChatScreen;
         LivingEntity livingEntity4 = attackAura2 != null ? attackAura2.E() : null;
         boolean flag5 = flag3 || livingEntity4 != null;
         this.f.animateTo(flag5 ? 1.0F : 0.0F);
         if (flag5 && !this.K) {
            this.I.resetTo(0.0F);
            this.J.resetTo(1.0F);
            this.K = true;
         } else if (!flag5 && this.K) {
            this.J.resetTo(1.0F);
            this.K = false;
         }

         if (flag5) {
            this.I.animateTo(1.0F);
            this.p = (LivingEntity)(flag3 ? minecraftClient.player : livingEntity4);
         } else {
            this.J.animateTo(0.0F);
         }

         float floatValue6;
         if (flag5) {
            floatValue6 = MathHelper.clamp(this.I.getValue(), 0.0F, 1.0F);
         } else {
            floatValue6 = MathHelper.clamp(this.J.getValue(), 0.0F, 1.0F);
         }

         LivingEntity livingEntity8 = (LivingEntity)(flag5 ? (flag3 ? minecraftClient.player : livingEntity4) : this.p);
         if (livingEntity8 != null && !(floatValue6 <= 0.01F)) {
            float floatValue9 = this.e.a();
            float floatValue10 = this.e.b();
            float floatValue11 = 3.0F;
            float floatValue12 = 110.0F;
            float floatValue13 = 46.0F;
            float floatValue14 = HPFixer.b((LivingEntity)livingEntity8);
            boolean flag15 = floatValue14 == -1.0F;
            float floatValue16 = Math.max(1.0F, livingEntity8.getMaxHealth());
            float floatValue17 = flag15 ? 0.0F : floatValue14;
            this.j.animateTo(floatValue17);
            float floatValue18 = MathHelper.clamp(this.j.getValue(), 0.0F, floatValue16);
            float floatValue19 = flag15 ? 0.0F : MathHelper.clamp(floatValue14 / floatValue16, 0.0F, 1.0F);
            this.h.animateTo(floatValue19);
            float floatValue20 = MathHelper.clamp(this.h.getValue(), 0.0F, 1.0F);
            if (floatValue20 > this.i.getValue()) {
               this.i.resetTo(floatValue20);
            } else {
               this.i.animateTo(floatValue20);
            }

            MatrixStack matrixStack21 = default1.getContext().getMatrices();
            matrixStack21.push();
            this.a(matrixStack21, floatValue9, floatValue10, floatValue12, floatValue13, floatValue6, floatValue6);
            float floatValue22 = floatValue6;
            int intValue23 = (int)(255.0F * floatValue6);
            float floatValue24 = floatValue13 - floatValue11 * 2.0F - 4.0F;
            int intValue25 = !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
            this.a(matrixStack21, floatValue9, floatValue10, floatValue12, floatValue13, 3.0F, intValue25, floatValue6);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack21, floatValue9 + floatValue11 + 2.0F, floatValue10 + floatValue11 + 2.0F, floatValue24, floatValue24, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(40, 40, 40, 255), floatValue6));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack21, floatValue9 + floatValue11 + 3.0F, floatValue10 + floatValue11 + 3.0F, floatValue24 - 2.0F, floatValue24 - 2.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 255), floatValue6));
            matrixStack21.pop();
            int intValue26 = (int)(floatValue9 + floatValue11 + 3.0F);
            int intValue27 = (int)(floatValue10 + floatValue11 + 3.0F);
            int intValue28 = (int)(floatValue9 + floatValue11 + 3.0F + floatValue24 - 2.0F);
            int intValue29 = (int)(floatValue10 + floatValue11 + 3.0F + floatValue24 - 2.0F);
            int intValue30 = (int)(15.0F * floatValue6);
            float floatValue31 = floatValue9 + floatValue11 + 3.0F + (floatValue24 - 2.0F) / 2.0F;
            float floatValue32 = floatValue10 + floatValue11 + 3.0F + (floatValue24 - 2.0F) / 3.0F;
            float floatValue33 = ((LivingEntity)livingEntity8).bodyYaw;
            float floatValue34 = livingEntity8.getPitch();
            double doubleValue35 = Math.toRadians((double)(floatValue33 + 180.0F));
            float floatValue37 = floatValue31 + (float)(Math.sin(doubleValue35) * 50.0);
            float floatValue38 = floatValue32 - floatValue34;
            if (floatValue6 > 0.5F) {
               InventoryScreen.drawEntity(default1.getContext(), intValue26, intValue27, intValue28, intValue29, intValue30, 0.0F, floatValue37, floatValue38, (LivingEntity)livingEntity8);
            }

            matrixStack21.push();
            this.a(matrixStack21, floatValue9, floatValue10, floatValue12, floatValue13, floatValue6, floatValue6);
            String string39 = livingEntity8.getName().getString();
            float floatValue40 = floatValue9 + floatValue11 + floatValue24 + 6.0F;
            float floatValue41 = Math.max(8.0F, floatValue9 + floatValue12 - floatValue11 - 4.0F - floatValue40);
            this.a(14).a(matrixStack21, string39, floatValue40, floatValue10 + floatValue11 + 5.0F, floatValue41, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue23));
            if (flag15) {
               this.a(14)
                  .d(
                     matrixStack21,
                     "HP: Неизвестно | Dist: " + (int)livingEntity8.distanceTo(minecraftClient.player),
                     floatValue40,
                     floatValue10 + floatValue11 + 20.0F,
                     dev.leg1tka.api.utils.text.ColorUtils.a(255, 200, 100, intValue23)
                  );
            } else {
               this.a(14)
                  .d(
                     matrixStack21,
                     "HP: " + String.format("%.1f", floatValue18) + " | Dist: " + (int)livingEntity8.distanceTo(minecraftClient.player),
                     floatValue40,
                     floatValue10 + floatValue11 + 20.0F,
                     dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue23)
                  );
            }

            float floatValue42 = floatValue40;
            float floatValue43 = floatValue10 + floatValue11 + 15.0F;
            float floatValue44 = 5.0F;
            float floatValue45 = 0.5F;
            byte byteValue46 = 10;
            float floatValue47 = floatValue16 / (float)byteValue46;
            float floatValue48 = flag15 ? 0.0F : floatValue14;
            int intValue49;
            if (flag15) {
               intValue49 = dev.leg1tka.api.utils.text.ColorUtils.a(128, 128, 128, intValue23);
            } else if (floatValue14 <= floatValue16 * 0.25F) {
               intValue49 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 50, 50, intValue23);
            } else if (floatValue14 <= floatValue16 * 0.5F) {
               intValue49 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 220, 0, intValue23);
            } else {
               intValue49 = dev.leg1tka.api.utils.text.ColorUtils.a(0, 255, 0, intValue23);
            }

            int intValue50 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue49, floatValue6 * 0.5F);

            for (int intValue51 = 0; intValue51 < byteValue46; intValue51++) {
               float floatValue52 = floatValue42 + (float)intValue51 * (floatValue44 + floatValue45);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack21, floatValue52, floatValue43 - 3.0F, floatValue22);
               if (floatValue48 > 0.0F && !flag15) {
                  float floatValue54 = MathHelper.clamp(floatValue48 / floatValue47, 0.0F, 1.0F);
                  float floatValue55 = floatValue44 * floatValue54;
                  if (floatValue55 > 0.0F) {
                     dev.leg1tka.render.RenderPrimitives.b(matrixStack21, floatValue52, floatValue43 - 3.0F, floatValue55, intValue49, intValue50);
                  }

                  floatValue48 -= floatValue47;
               }
            }

            float floatValue63 = floatValue40 - 1.0F;
            float floatValue64 = floatValue10 + floatValue11 + 28.0F;
            float floatValue53 = 10.0F;
            intValue25 = !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
            int intValue65 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.a(50, 50, 50, 255), floatValue22);
            int intValue66 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue25, 0.15F), floatValue22);
            int intValue56 = dev.leg1tka.api.utils.text.ColorUtils.a(dev.leg1tka.api.utils.text.ColorUtils.b(intValue25, 0.05F), floatValue22);
            int intValue57 = this.a((LivingEntity)livingEntity8);
            if (intValue57 > 0) {
               float floatValue58 = floatValue63 - 0.85F;
               float floatValue59 = floatValue64 - 0.85F;
               float floatValue60 = (float)(intValue57 - 1) * floatValue53 + 9.8F;
               float floatValue61 = 9.8F;
            }

            float floatValue67 = 0.5F;

            for (int intValue68 = 0; intValue68 < intValue57; intValue68++) {
               ItemStack itemStack69 = this.F[intValue68];
               if (!itemStack69.isEmpty()) {
                  float floatValue70 = floatValue63 + (float)intValue68 * floatValue53;
                  this.a(default1, matrixStack21, itemStack69, floatValue70, floatValue64, floatValue67);
               }

               this.F[intValue68] = ItemStack.EMPTY;
            }

            this.a(default1, matrixStack21, (LivingEntity)livingEntity8, floatValue9, floatValue10, floatValue12, floatValue13, floatValue22, intValue25);
            matrixStack21.pop();
            this.e.c(floatValue12);
            this.e.d(floatValue13);
         } else {
            this.e.c(0.0F);
            this.e.d(0.0F);
         }
      }
   }

   static class a {
      float a;
      float b;
      float c;
      float d;
      float e;
      float f;
      float g;

      a() {
      }
   }
}
