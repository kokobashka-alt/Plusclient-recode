package dev.leg1tka.gui.components;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.function.IntPredicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;

public class MenuIconButton {
   private static float a;
   private String b = "";
   private final String c;
   private final int d;
   private final IntPredicate e;
   private String f = null;
   private int g = 0;
   private boolean h = false;
   private boolean i = false;
   private float j = 0.0F;
   private float k = 0.0F;
   private long l = 0L;
   private long m = 0L;
   private float n;
   private float o;
   private float p;
   private float q;

   public MenuIconButton(String string1, int intValue2, IntPredicate intPredicate3) {
      this.c = string1;
      this.d = intValue2;
      this.e = intPredicate3;
   }

   public dev.leg1tka.gui.components.MenuIconButton a(String string1) {
      this.f = string1;
      return this;
   }

   public String a() {
      return this.b;
   }

   public void b(String string1) {
      this.b = string1 == null ? "" : string1;
      if (this.b.length() > this.d) {
         this.b = this.b.substring(0, this.d);
      }

      this.g = this.b.length();
      this.h = false;
   }

   public boolean b() {
      return this.i;
   }

   public void a(boolean flag1) {
      this.i = flag1;
      if (flag1) {
         this.l = System.currentTimeMillis();
         this.g = this.b.length();
      } else {
         this.h = false;
      }
   }

   public void c() {
      this.h = !this.b.isEmpty();
      this.g = this.b.length();
   }

   public boolean a(double doubleValue1, double doubleValue3) {
      return doubleValue1 >= (double)this.n && doubleValue1 <= (double)(this.n + this.p) && doubleValue3 >= (double)this.o && doubleValue3 <= (double)(this.o + this.q);
   }

   public void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, float floatValue8) {
      this.n = floatValue2;
      this.o = floatValue3;
      this.p = floatValue4;
      this.q = floatValue5;
      long longValue9 = System.nanoTime();
      float floatValue11 = this.m == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue9 - this.m) / 1.0E9));
      this.m = longValue9;
      this.j = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.j, this.i ? 1.0F : 0.0F, floatValue11, 14.0F);
      this.k = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.k, this.a((double)intValue6, (double)intValue7) ? 1.0F : 0.0F, floatValue11, 14.0F);
      float floatValue12 = Math.min(floatValue5 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(11.0F));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue12, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.05F + this.k * 0.02F + this.j * 0.03F) * floatValue8, 16777215));
      int intValue13 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.1F + this.j * 0.75F) * floatValue8, this.j > 0.02F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.c() : 16777215);
      int intValue14 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.1F + this.j * 0.75F) * floatValue8, this.j > 0.02F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.d() : 16777215);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, floatValue12, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue13, intValue14, intValue13, intValue14);
      float floatValue15 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      float floatValue16 = floatValue2 + floatValue15;
      float floatValue17 = floatValue3 + floatValue5 * 0.5F;
      if (this.f != null) {
         float floatValue18 = dev.leg1tka.gui.menu.MenuLayout.a(15.0F);
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", this.f, floatValue18, floatValue16 + floatValue18 * 0.5F, floatValue17, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.45F + this.j * 0.4F) * floatValue8, 16777215));
         floatValue16 += floatValue18 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F);
      }

      float floatValue23 = floatValue2 + floatValue4 - floatValue15 - floatValue16;
      if (this.b.isEmpty()) {
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, this.c, 13.5F, floatValue16, floatValue17, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.38F * floatValue8, 16777215));
      }

      float floatValue19 = dev.leg1tka.gui.components.MenuTextRenderer.a(13.5F, this.b.substring(0, Math.min(this.g, this.b.length())));
      float floatValue20 = Math.max(0.0F, floatValue19 - floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(2.0F));
      dev.leg1tka.render.ScissorStack.a();
      dev.leg1tka.render.ScissorStack.a((double)(floatValue16 - dev.leg1tka.gui.menu.MenuLayout.a(1.0F)), (double)floatValue3, (double)(floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F)), (double)floatValue5);
      if (this.h && this.i && !this.b.isEmpty()) {
         float floatValue21 = dev.leg1tka.gui.components.MenuTextRenderer.a(13.5F, this.b);
         float floatValue22 = dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue16 - floatValue20 - dev.leg1tka.gui.menu.MenuLayout.a(2.0F), floatValue17 - floatValue22 * 0.5F, floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F), floatValue22, dev.leg1tka.gui.menu.MenuLayout.a(4.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue8, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()));
      }

      if (!this.b.isEmpty()) {
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, this.b, 13.5F, floatValue16 - floatValue20, floatValue17, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.96F * floatValue8, 16777215));
      }

      if (this.i && !this.h && (System.currentTimeMillis() - this.l) / 530L % 2L == 0L) {
         float floatValue24 = dev.leg1tka.gui.menu.MenuLayout.b(floatValue16 - floatValue20 + floatValue19);
         float floatValue25 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue24, floatValue17 - floatValue25 * 0.5F, Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.4F)), floatValue25, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()));
      }

      dev.leg1tka.render.ScissorStack.c();
      dev.leg1tka.render.ScissorStack.b();
   }

   private void d() {
      if (this.h) {
         this.b = "";
         this.g = 0;
         this.h = false;
      }
   }

   public boolean a(char character1) {
      if (!this.i) {
         return false;
      } else if (character1 >= ' ' && this.e.test(character1)) {
         this.d();
         if (this.b.length() >= this.d) {
            return true;
         } else {
            this.b = this.b.substring(0, this.g) + character1 + this.b.substring(this.g);
            this.g++;
            this.l = System.currentTimeMillis();
            return true;
         }
      } else {
         return true;
      }
   }

   public boolean a(int intValue1, int intValue2) {
      if (!this.i) {
         return false;
      } else {
         boolean flag3 = (intValue2 & 2) != 0;
         MinecraftClient minecraftClient4 = MinecraftClient.getInstance();
         this.l = System.currentTimeMillis();
         switch (intValue1) {
            case 65:
               if (flag3) {
                  this.c();
                  return true;
               }
               break;
            case 67:
               if (flag3) {
                  minecraftClient4.keyboard.setClipboard(this.b);
                  return true;
               }
               break;
            case 86:
               if (flag3) {
                  String string5 = minecraftClient4.keyboard.getClipboard();
                  if (string5 != null) {
                     this.d();

                     for (char character9 : string5.trim().toCharArray()) {
                        if (this.b.length() >= this.d) {
                           break;
                        }

                        if (this.e.test(character9)) {
                           this.b = this.b.substring(0, this.g) + character9 + this.b.substring(this.g);
                           this.g++;
                        }
                     }
                  }

                  return true;
               }
               break;
            case 259:
               if (!this.h && !flag3) {
                  if (this.g > 0) {
                     this.b = this.b.substring(0, this.g - 1) + this.b.substring(this.g);
                     this.g--;
                  }
               } else {
                  this.b = this.h ? "" : this.b.substring(this.g);
                  this.g = 0;
                  this.h = false;
               }

               return true;
            case 261:
               if (this.h) {
                  this.d();
               } else if (this.g < this.b.length()) {
                  this.b = this.b.substring(0, this.g) + this.b.substring(this.g + 1);
               }

               return true;
            case 262:
               this.h = false;
               this.g = flag3 ? this.b.length() : Math.min(this.b.length(), this.g + 1);
               return true;
            case 263:
               this.h = false;
               this.g = flag3 ? 0 : Math.max(0, this.g - 1);
               return true;
            case 268:
               this.h = false;
               this.g = 0;
               return true;
            case 269:
               this.h = false;
               this.g = this.b.length();
               return true;
         }

         return intValue1 != 256 && intValue1 != 257 && intValue1 != 335 && intValue1 != 258 && intValue1 != 265 && intValue1 != 264;
      }
   }
}
