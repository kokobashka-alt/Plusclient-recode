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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class ItemHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final dev.leg1tka.api.animation.Animation f = new dev.leg1tka.api.animation.Animation(80.0F, 10.5F, dev.leg1tka.api.animation.Easings.h);

   public ItemHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   @Override
   public void a(EventRender.Default default1) {
      List list2 = this.e();
      if (list2.isEmpty()) {
         this.f.animateTo(0.0F);
         this.e.c(0.0F);
         this.e.d(0.0F);
      } else {
         this.a(default1, list2);
         super.a(default1);
      }
   }

   private List<ServerHelper.HelperItem> e() {
      ServerHelper serverHelper1 = ServerHelper.c;
      ArrayList arrayList2 = new ArrayList();
      if (serverHelper1 == null) {
         return arrayList2;
      } else {
         for (ServerHelper.HelperItem helperItem5 : serverHelper1.q() ? serverHelper1.r() : serverHelper1.s()) {
            if (helperItem5.bind().getKey() != -1) {
               arrayList2.add(helperItem5);
            }
         }

         return arrayList2;
      }
   }

   private void a(EventRender.Default default1, List<ServerHelper.HelperItem> list2) {
      MatrixStack matrixStack3 = default1.getContext().getMatrices();
      float floatValue4 = this.e.a();
      float floatValue5 = this.e.b();
      int intValue6 = this.f();
      byte byteValue7 = 13;
      dev.leg1tka.render.font.MsdfTextRenderer value8 = this.a(byteValue7);
      float floatValue9 = 19.0F;
      float floatValue10 = 9.8F;
      float floatValue11 = 0.61F;
      float floatValue12 = 2.8F;
      float floatValue13 = 5.0F;
      float floatValue14 = 6.0F;
      float floatValue15 = this.a(list2, value8, floatValue10, floatValue12, floatValue13, floatValue14, 60.0F);
      this.f.animateTo(floatValue15);
      float floatValue16 = this.f.getValue();
      this.b(matrixStack3, floatValue4, floatValue5, floatValue16, floatValue9, intValue6);
      if (list2.isEmpty()) {
         this.a(12).d(matrixStack3, "Helper", floatValue4 + 5.0F, floatValue5 + 6.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 230));
         this.e.c(floatValue16);
         this.e.d(floatValue9);
      } else {
         this.a(default1.getContext(), list2, value8, floatValue4, floatValue5, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, 8.2F);
         this.e.c(floatValue16);
         this.e.d(floatValue9);
      }
   }

   private void b(EventRender.Default default1, List<ServerHelper.HelperItem> list2) {
      MatrixStack matrixStack3 = default1.getContext().getMatrices();
      float floatValue4 = this.e.a();
      float floatValue5 = this.e.b();
      int intValue6 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6 + 30);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6 + 90);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6 + 120);
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6 + 180);
      int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue6 + 210);
      dev.leg1tka.render.font.MsdfTextRenderer value13 = this.a(14);
      float floatValue14 = 22.0F;
      float floatValue15 = 11.0F;
      float floatValue16 = 0.69F;
      float floatValue17 = 3.5F;
      float floatValue18 = 6.0F;
      float floatValue19 = 7.0F;
      float floatValue20 = this.a(list2, value13, floatValue15, floatValue17, floatValue18, floatValue19, 72.0F);
      this.f.animateTo(floatValue20);
      float floatValue21 = this.f.getValue();
      if (list2.isEmpty()) {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack3, floatValue4, floatValue5, floatValue21, 15.0F, 0.0F, 10.0F, 10.0F, intValue7, intValue8, intValue9, intValue10, intValue11, intValue12);
         String string22 = "helper";
         float floatValue23 = floatValue4 + (floatValue21 - this.a(15).b(string22)) / 2.0F;
         this.a(15).f(matrixStack3, string22, floatValue23, floatValue5 + 5.0F, -1);
         this.e.c(floatValue21);
         this.e.d(18.0F);
      } else {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack3, floatValue4, floatValue5, floatValue21, floatValue14, dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150), 3.5F, 0.0F, 10.0F, 10.0F, intValue7, intValue8, intValue9, intValue10, intValue11, intValue12);
         this.a(default1.getContext(), list2, value13, floatValue4, floatValue5, floatValue14, floatValue15, floatValue16, floatValue17, floatValue18, floatValue19, 9.5F);
         this.e.c(floatValue21);
         this.e.d(floatValue14);
      }
   }

   private float a(List<ServerHelper.HelperItem> list1, dev.leg1tka.render.font.MsdfTextRenderer value2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      if (list1.isEmpty()) {
         return floatValue7;
      } else {
         float floatValue8 = floatValue6 * 2.0F;

         for (int intValue9 = 0; intValue9 < list1.size(); intValue9++) {
            String string10 = dev.leg1tka.gui.components.InputBindings.d(((ServerHelper.HelperItem)list1.get(intValue9)).bind().getKey());
            floatValue8 += floatValue3 + floatValue4 + value2.b(string10);
            if (intValue9 < list1.size() - 1) {
               floatValue8 += floatValue5;
            }
         }

         return floatValue8;
      }
   }

   private void a(
      DrawContext drawContext1, List<ServerHelper.HelperItem> list2, dev.leg1tka.render.font.MsdfTextRenderer value3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, float floatValue8, float floatValue9, float floatValue10, float floatValue11, float floatValue12
   ) {
      MatrixStack matrixStack13 = drawContext1.getMatrices();
      float floatValue14 = floatValue4 + floatValue11;
      float floatValue15 = floatValue5 + (floatValue6 - floatValue7) * 0.5F;
      float floatValue16 = floatValue5 + floatValue12;

      for (int intValue17 = 0; intValue17 < list2.size(); intValue17++) {
         ServerHelper.HelperItem helperItem18 = (ServerHelper.HelperItem)list2.get(intValue17);
         String string19 = dev.leg1tka.gui.components.InputBindings.d(helperItem18.bind().getKey());
         this.a(drawContext1, new ItemStack(helperItem18.item()), floatValue14, floatValue15, floatValue8);
         value3.d(matrixStack13, string19, floatValue14 + floatValue7 + floatValue9, floatValue16, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 240));
         floatValue14 += floatValue7 + floatValue9 + value3.b(string19);
         if (intValue17 < list2.size() - 1) {
            floatValue14 += floatValue10;
         }
      }
   }

   private void a(DrawContext drawContext1, ItemStack itemStack2, float floatValue3, float floatValue4, float floatValue5) {
      MatrixStack matrixStack6 = drawContext1.getMatrices();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      matrixStack6.push();
      matrixStack6.translate(floatValue3, floatValue4, 0.0F);
      matrixStack6.scale(floatValue5, floatValue5, 1.0F);
      drawContext1.drawItem(itemStack2, 0, 0);
      matrixStack6.pop();
      RenderSystem.depthMask(false);
      RenderSystem.disableDepthTest();
   }

   private void b(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6) {
      this.a(matrixStack1, floatValue2, floatValue3, floatValue4, floatValue5, intValue6);
   }

   private int f() {
      return !AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow") ? AspectDLC.INSTANCE.themeStorage.b().a().c[0] : dev.leg1tka.api.utils.text.ColorUtils.a();
   }
}
