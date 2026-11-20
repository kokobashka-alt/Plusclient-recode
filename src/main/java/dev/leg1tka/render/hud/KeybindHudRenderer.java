package dev.leg1tka.render.hud;


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
import dev.leg1tka.api.events.implement.EventRender;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.util.math.MatrixStack;

public class KeybindHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final Map<Module, dev.leg1tka.api.animation.Animation> f = new HashMap<>();
   private final dev.leg1tka.api.animation.Animation g = new dev.leg1tka.api.animation.Animation(60.0F, 10.5F, dev.leg1tka.api.animation.Easings.h);
   private static final Map<Character, Character> h = new HashMap<>();

   public KeybindHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer b(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("icon1", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer c(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("icon", intValue1);
   }

   private dev.leg1tka.api.animation.Animation a(Module module1) {
      return this.f.computeIfAbsent(module1, module0 -> new dev.leg1tka.api.animation.Animation(0.0F, 10.5F, dev.leg1tka.api.animation.Easings.h));
   }

   private String a(String string1) {
      StringBuilder stringBuilder2 = new StringBuilder();

      for (char character6 : string1.toCharArray()) {
         stringBuilder2.append(h.getOrDefault(character6, character6));
      }

      return stringBuilder2.toString();
   }

   @Override
   public void a(EventRender.Default default1) {
      if (minecraftClient != null && minecraftClient.player != null) {
         this.b(default1);
         super.a(default1);
      }
   }

   public void b(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      int intValue4;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue4 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue4 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      float floatValue5 = 64.0F;
      int intValue6 = 0;
      float floatValue7 = 0.0F;
      ObjectListIterator objectListIterator8 = ModuleManager.a.a().iterator();

      while (objectListIterator8.hasNext()) {
         Module module9 = (Module)objectListIterator8.next();
         if (module9.getKeyBind() != -1 && module9.getKeyBind() != 0) {
            dev.leg1tka.api.animation.Animation value10 = this.a(module9);
            value10.animateTo(module9.isEnabled() ? 1.0F : 0.0F);
         }
      }

      float floatValue35 = this.b(15).b("f");
      float floatValue36 = floatValue2 + 3.5F + (8.5F - floatValue35) / 2.0F;
      float floatValue37 = floatValue2 + 17.5F;
      float floatValue11 = floatValue37 - floatValue2 + this.a(13).b("Keybinds") + 6.0F;
      if (floatValue11 > floatValue5) {
         floatValue5 = floatValue11;
      }

      ObjectListIterator objectListIterator12 = ModuleManager.a.a().iterator();

      while (objectListIterator12.hasNext()) {
         Module module13 = (Module)objectListIterator12.next();
         if (module13.getKeyBind() != -1 && module13.getKeyBind() != 0) {
            dev.leg1tka.api.animation.Animation value14 = this.a(module13);
            float floatValue15 = value14.getValue();
            if (floatValue15 > 0.01F) {
               intValue6++;
               String string16 = this.a(dev.leg1tka.gui.components.InputBindings.a(module13.getKeyBind()));
               float floatValue17 = this.a(11).b(string16);
               float floatValue18 = this.a(12).b(module13.getDisplayName());
               float floatValue19 = 17.5F + floatValue18 + 6.0F + floatValue17 + 5.0F;
               if (floatValue19 > floatValue5) {
                  floatValue5 = floatValue19;
               }

               floatValue7 += 11.5F * floatValue15;
            }
         }
      }

      float floatValue38 = intValue6 == 0 ? 11.5F : 11.5F + floatValue7;
      this.g.animateTo(floatValue5);
      float floatValue39 = this.g.getValue();
      float floatValue40 = floatValue38;
      MatrixStack matrixStack41 = default1.getContext().getMatrices();
      this.a(matrixStack41, floatValue2, floatValue3, floatValue39, floatValue38, intValue4);
      this.b(15).d(matrixStack41, "f", floatValue36, floatValue3 + 4.75F, intValue4);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack41, floatValue2 + 14.0F, floatValue3 + 3.0F, 0.8F, 5.5F, 0.4F, dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, 140));
      this.a(13).d(matrixStack41, "Keybinds", floatValue37, floatValue3 + 3.5F, -1);
      float floatValue42 = 11.5F;
      ObjectListIterator objectListIterator43 = ModuleManager.a.a().iterator();

      while (objectListIterator43.hasNext()) {
         Module module44 = (Module)objectListIterator43.next();
         if (module44.getKeyBind() != -1 && module44.getKeyBind() != 0) {
            dev.leg1tka.api.animation.Animation value45 = this.a(module44);
            float floatValue20 = value45.getValue();
            if (floatValue20 > 0.01F) {
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue39, (double)floatValue40);
               String string21 = this.a(dev.leg1tka.gui.components.InputBindings.a(module44.getKeyBind()));
               int intValue22 = (int)(255.0F * floatValue20);
               int intValue23 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue22);
               int intValue24 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue22);
               String string25 = module44.getCategory() != null ? module44.getCategory().c() : "a";
               dev.leg1tka.render.font.MsdfTextRenderer value26 = dev.leg1tka.render.font.FontCache.a("icon", 13);
               float floatValue27 = value26.b(string25);
               value26.d(matrixStack41, string25, floatValue2 + 3.5F + (8.5F - floatValue27) / 2.0F, floatValue3 + floatValue42 + 5.0F, intValue24);
               float floatValue28 = floatValue2 + 14.0F;
               float floatValue29 = floatValue3 + floatValue42 + 3.0F;
               int intValue30 = dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, (int)(140.0F * floatValue20));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack41, floatValue28, floatValue29, 0.8F, 5.5F, 0.4F, intValue30);
               float floatValue31 = floatValue2 + 17.5F;
               float floatValue32 = floatValue3 + floatValue42 + 3.65F;
               this.a(12).d(matrixStack41, module44.getDisplayName(), floatValue31, floatValue32, intValue23);
               float floatValue33 = this.a(11).b(string21);
               float floatValue34 = floatValue2 + floatValue39 - floatValue33 - 5.0F;
               this.a(11).d(matrixStack41, string21, floatValue34, floatValue32, intValue24);
               floatValue42 += 11.5F * floatValue20;
               dev.leg1tka.render.ScissorStack.b();
               dev.leg1tka.render.ScissorStack.c();
            }
         }
      }

      this.e.c(floatValue39);
      this.e.d(floatValue40);
   }

   public void c(EventRender.Default default1) {
      MatrixStack matrixStack2 = default1.getContext().getMatrices();
      float floatValue3 = this.e.a();
      float floatValue4 = this.e.b();
      int intValue5 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
      int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5);
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 30);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 90);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 120);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 180);
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 210);
      ArrayList<Module> arrayList12 = new ArrayList<>();
      ObjectListIterator objectListIterator13 = ModuleManager.a.a().iterator();

      while (objectListIterator13.hasNext()) {
         Module module14 = (Module)objectListIterator13.next();
         if (module14.getKeyBind() > 0 && module14.getKeyBind() != 0) {
            module14.getToggleAnimation().animateTo(module14.isEnabled() ? 1.0F : 0.0F);
            if (module14.getToggleAnimation().getValue() > 0.01F) {
               arrayList12.add(module14);
            }
         } else {
            module14.getToggleAnimation().animateTo(0.0F);
         }
      }

      float floatValue28 = 84.0F;
      float floatValue29 = 18.0F;
      int intValue15 = 0;

      for (Module module17 : arrayList12) {
         float floatValue18 = module17.getToggleAnimation().getValue();
         if (!(floatValue18 <= 0.01F)) {
            intValue15++;
            String string19 = module17.getDisplayName().toLowerCase() + " >> toggle";
            floatValue28 = Math.max(floatValue28, this.a(14).b(string19) + 7.0F);
            floatValue29 += 12.0F * floatValue18;
         }
      }

      this.g.animateTo(floatValue28);
      float floatValue30 = this.g.getValue();
      if (intValue15 == 0) {
         float floatValue32 = 18.0F;
         dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue3, floatValue4, floatValue30, 15.0F, 0.0F, 10.0F, 10.0F, intValue6, intValue7, intValue8, intValue9, intValue10, intValue11);
         String string34 = "keybinds";
         float floatValue36 = floatValue3 + (floatValue30 - this.a(15).b(string34)) / 2.0F;
         this.a(15).f(default1.getContext().getMatrices(), string34, floatValue36, floatValue4 + 5.0F, -1);
         this.e.c(floatValue30);
         this.e.d(floatValue32);
      } else {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue3, floatValue4, floatValue30, floatValue29, dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150), 15.0F, 0.0F, 10.0F, 10.0F, intValue6, intValue7, intValue8, intValue9, intValue10, intValue11);
         String string31 = "keybinds";
         float floatValue33 = floatValue3 + (floatValue30 - this.a(15).b(string31)) / 2.0F;
         this.a(15).f(default1.getContext().getMatrices(), string31, floatValue33, floatValue4 + 5.0F, -1);
         float floatValue35 = 18.0F;

         for (Module module21 : arrayList12) {
            float floatValue22 = module21.getToggleAnimation().getValue();
            if (!(floatValue22 <= 0.01F)) {
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue3, (double)floatValue4, (double)floatValue30, (double)floatValue29);
               int intValue23 = (int)(255.0F * floatValue22);
               int intValue24 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue23);
               int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.a();
               dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue3 + 5.0F, floatValue4 + floatValue35 + 2.0F, 2.5F, 8.0F, 0.5F, intValue25);
               String string26 = module21.getDisplayName().toLowerCase() + " >> " + (module21.isEnabled() ? "on" : "off");
               float floatValue27 = floatValue3 + 11.0F;
               this.a(14).d(matrixStack2, string26, floatValue27, floatValue4 + floatValue35 + 2.0F, intValue24);
               floatValue35 += 12.0F * floatValue22;
               dev.leg1tka.render.ScissorStack.c();
               dev.leg1tka.render.ScissorStack.b();
            }
         }

         this.e.c(floatValue30);
         this.e.d(floatValue29);
      }
   }

   static {
      String string0 = "йцукенгшщзхъфывапролджэячсмитьбюЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
      String string1 = "qwertyuiop[]asdfghjkl;'zxcvbnm,.QWERTYUIOP[]ASDFGHJKL;'ZXCVBNM,.";
      int intValue2 = Math.min(string0.length(), string1.length());

      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         h.put(string0.charAt(intValue3), string1.charAt(intValue3));
      }
   }
}
