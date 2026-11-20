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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.entry.RegistryEntry;

public class StatusEffectHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final Map<StatusEffect, dev.leg1tka.api.animation.Animation> f = new LinkedHashMap<>();
   private final Map<StatusEffect, dev.leg1tka.render.hud.StatusEffectHudRenderer.a> g = new HashMap<>();
   private final Map<StatusEffect, Integer> h = new HashMap<>();
   private final Set<StatusEffect> i = new HashSet<>();
   private final dev.leg1tka.api.animation.Animation j = new dev.leg1tka.api.animation.Animation(70.0F, 10.5F, dev.leg1tka.api.animation.Easings.h);
   private boolean k = false;

   public StatusEffectHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
   }

   public boolean e() {
      return this.k;
   }

   public void c(boolean flag1) {
      this.k = flag1;
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer b(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("icon", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer c(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("iconsminced", intValue1);
   }

   private dev.leg1tka.api.animation.Animation a(StatusEffect statusEffect1) {
      return this.f.computeIfAbsent(statusEffect1, statusEffect0 -> new dev.leg1tka.api.animation.Animation(0.0F, 10.5F, dev.leg1tka.api.animation.Easings.h));
   }

   private static String d(int intValue0) {
      return String.valueOf(Math.max(1, intValue0));
   }

   private static String a(StatusEffectInstance statusEffectInstance0) {
      return a(statusEffectInstance0.getDuration(), statusEffectInstance0.isInfinite());
   }

   private static String a(int intValue0, boolean flag1) {
      if (flag1) {
         return "inf";
      } else {
         int intValue2 = Math.max(0, intValue0 / 20);
         int intValue3 = intValue2 / 60;
         int intValue4 = intValue2 % 60;
         return intValue3 + ":" + (intValue4 < 10 ? "0" + intValue4 : String.valueOf(intValue4));
      }
   }

   private void b(StatusEffectInstance statusEffectInstance1) {
      StatusEffect statusEffect2 = (StatusEffect)statusEffectInstance1.getEffectType().value();
      dev.leg1tka.render.hud.StatusEffectHudRenderer.a value3 = this.g.computeIfAbsent(statusEffect2, statusEffect0 -> new dev.leg1tka.render.hud.StatusEffectHudRenderer.a());
      value3.a = statusEffectInstance1.getEffectType();
      value3.b = I18n.translate(statusEffectInstance1.getTranslationKey(), new Object[0]);
      value3.c = statusEffectInstance1.getAmplifier() + 1;
      value3.d = statusEffectInstance1.getDuration();
      value3.e = statusEffectInstance1.isInfinite();
   }

   private static int a(dev.leg1tka.render.hud.StatusEffectHudRenderer.a value0, int intValue1) {
      StatusEffect statusEffect2 = value0.a != null ? (StatusEffect)value0.a.value() : null;
      if (statusEffect2 != StatusEffects.WEAKNESS.value()
         && statusEffect2 != StatusEffects.MINING_FATIGUE.value()
         && statusEffect2 != StatusEffects.SLOWNESS.value()
         && statusEffect2 != StatusEffects.BLINDNESS.value()) {
         return (statusEffect2 != StatusEffects.STRENGTH.value() || value0.c < 4)
               && statusEffect2 != StatusEffects.HEALTH_BOOST.value()
               && statusEffect2 != StatusEffects.RESISTANCE.value()
            ? dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue1)
            : dev.leg1tka.api.utils.text.ColorUtils.a(150, 255, 90, intValue1);
      } else {
         return dev.leg1tka.api.utils.text.ColorUtils.a(255, 85, 85, intValue1);
      }
   }

   private List<StatusEffect> a(Collection<StatusEffectInstance> collection1, Set<StatusEffect> set2) {
      ArrayList arrayList3 = new ArrayList();
      this.i.clear();

      for (StatusEffectInstance statusEffectInstance5 : collection1) {
         StatusEffect statusEffect6 = (StatusEffect)statusEffectInstance5.getEffectType().value();
         if (this.i.add(statusEffect6)) {
            arrayList3.add(statusEffect6);
         }
      }

      for (StatusEffect statusEffect8 : this.f.keySet()) {
         if (!set2.contains(statusEffect8)) {
            arrayList3.add(statusEffect8);
         }
      }

      return arrayList3;
   }

   private void a(EventRender.Default default1, RegistryEntry<StatusEffect> registryEntry2, float floatValue3, float floatValue4, int intValue5, int intValue6) {
      if (registryEntry2 != null && minecraftClient != null && minecraftClient.getStatusEffectSpriteManager() != null) {
         Sprite sprite7 = minecraftClient.getStatusEffectSpriteManager().getSprite(registryEntry2);
         if (sprite7 != null) {
            int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue6);
            dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), sprite7, floatValue3, floatValue4, (float)intValue5, intValue8);
         }
      }
   }

   private void a(EventRender.Default default1, dev.leg1tka.render.font.MsdfTextRenderer value2, String string3, float floatValue4, float floatValue5, int intValue6) {
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.a(20, 20, 20, 145);
      value2.d(default1.getContext().getMatrices(), string3, floatValue4 + 0.8F, floatValue5 + 0.8F, intValue7);
      value2.d(default1.getContext().getMatrices(), string3, floatValue4, floatValue5, intValue6);
   }

   private String e(int intValue1) {
      String[] stringArray2 = new String[]{"", "M", "MM", "MMM"};
      String[] stringArray3 = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
      String[] stringArray4 = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
      String[] stringArray5 = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
      return intValue1 > 0 && intValue1 < 4000 ? stringArray2[intValue1 / 1000] + stringArray3[intValue1 % 1000 / 100] + stringArray4[intValue1 % 100 / 10] + stringArray5[intValue1 % 10] : String.valueOf(intValue1);
   }

   @Override
   public void a(EventRender.Default default1) {
      if (this.k) {
         this.c(default1);
      } else {
         this.b(default1);
      }

      super.a(default1);
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

      float floatValue5 = 70.0F;
      int intValue6 = 0;
      float floatValue7 = 0.0F;
      Collection<StatusEffectInstance> collection8 = dev.leg1tka.render.hud.StatusEffectOrder.a() ? dev.leg1tka.render.hud.StatusEffectOrder.b() : (minecraftClient != null && minecraftClient.player != null ? minecraftClient.player.getStatusEffects() : List.of());
      HashSet hashSet9 = new HashSet();

      for (StatusEffectInstance statusEffectInstance11 : collection8) {
         StatusEffect statusEffect12 = (StatusEffect)statusEffectInstance11.getEffectType().value();
         hashSet9.add(statusEffect12);
         this.a(statusEffect12).animateTo(1.0F);
         this.b(statusEffectInstance11);
         int intValue13 = statusEffectInstance11.getDuration();
         Integer integer14 = (Integer)this.h.get(statusEffect12);
         if (integer14 == null || intValue13 > integer14) {
            this.h.put(statusEffect12, intValue13);
         }
      }

      for (Entry entry41 : this.f.entrySet()) {
         if (!hashSet9.contains(entry41.getKey())) {
            ((dev.leg1tka.api.animation.Animation)entry41.getValue()).animateTo(0.0F);
         }
      }

      List<StatusEffect> list40 = this.a(collection8, hashSet9);
      float floatValue42 = this.c(15).b("f");
      float floatValue43 = floatValue2 + 3.5F + (8.5F - floatValue42) / 2.0F;
      float floatValue44 = floatValue2 + 17.5F;
      float floatValue45 = floatValue44 - floatValue2 + this.a(13).b("Potions list") + 6.0F;
      if (floatValue45 > floatValue5) {
         floatValue5 = floatValue45;
      }

      for (StatusEffect statusEffect16 : list40) {
         dev.leg1tka.api.animation.Animation value17 = this.a(statusEffect16);
         float floatValue18 = value17.getValue();
         dev.leg1tka.render.hud.StatusEffectHudRenderer.a value19 = (dev.leg1tka.render.hud.StatusEffectHudRenderer.a)this.g.get(statusEffect16);
         if (floatValue18 > 0.01F && value19 != null) {
            intValue6++;
            String string20 = value19.b != null ? value19.b : I18n.translate(statusEffect16.getTranslationKey(), new Object[0]);
            String string21 = " [" + this.e(value19.c) + "]";
            String string22 = a(value19.d, value19.e);
            float floatValue23 = this.a(12).b(string20);
            float floatValue24 = this.a(12).b(string21);
            float floatValue25 = this.a(11).b(string22);
            float floatValue26 = 17.5F + floatValue23 + floatValue24 + 6.0F + floatValue25 + 5.0F;
            if (floatValue26 > floatValue5) {
               floatValue5 = floatValue26;
            }

            floatValue7 += 11.5F * floatValue18;
         }
      }

      float floatValue46 = intValue6 == 0 ? 11.5F : 11.5F + floatValue7;
      this.j.animateTo(floatValue5);
      float floatValue47 = this.j.getValue();
      float floatValue48 = floatValue46;
      this.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue47, floatValue46, intValue4);
      this.c(15).d(default1.getContext().getMatrices(), "f", floatValue43, floatValue3 + 4.75F, intValue4);
      dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue2 + 14.0F, floatValue3 + 3.0F, 0.8F, 5.5F, 0.4F, dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, 140));
      this.a(13).d(default1.getContext().getMatrices(), "Potions list", floatValue44, floatValue3 + 3.5F, -1);
      float floatValue49 = 11.5F;

      for (StatusEffect statusEffect51 : list40) {
         dev.leg1tka.api.animation.Animation value52 = this.a(statusEffect51);
         float floatValue53 = value52.getValue();
         dev.leg1tka.render.hud.StatusEffectHudRenderer.a value54 = (dev.leg1tka.render.hud.StatusEffectHudRenderer.a)this.g.get(statusEffect51);
         if (floatValue53 > 0.01F && value54 != null) {
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue47, (double)floatValue48);
            int intValue55 = (int)(255.0F * floatValue53);
            int intValue56 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue55);
            String string57 = value54.b != null ? value54.b : I18n.translate(statusEffect51.getTranslationKey(), new Object[0]);
            String string27 = " [" + this.e(value54.c) + "]";
            String string28 = a(value54.d, value54.e);
            float floatValue29 = floatValue2 + 3.0F;
            float floatValue30 = floatValue3 + floatValue49 + 1.25F;
            if (value54.a != null) {
               this.a(default1, value54.a, floatValue29, floatValue30, 9, intValue55);
            }

            float floatValue31 = floatValue2 + 14.0F;
            float floatValue32 = floatValue3 + floatValue49 + 3.0F;
            int intValue33 = dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, (int)(140.0F * floatValue53));
            dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue31, floatValue32, 0.8F, 5.5F, 0.4F, intValue33);
            float floatValue34 = floatValue2 + 17.5F;
            float floatValue35 = floatValue3 + floatValue49 + 3.65F;
            this.a(12).d(default1.getContext().getMatrices(), string57, floatValue34, floatValue35, a(value54, intValue55));
            float floatValue36 = this.a(12).b(string57);
            this.a(12).d(default1.getContext().getMatrices(), string27, floatValue34 + floatValue36, floatValue35, intValue56);
            float floatValue37 = this.a(11).b(string28);
            float floatValue38 = floatValue2 + floatValue47 - floatValue37 - 5.0F;
            this.a(11).d(default1.getContext().getMatrices(), string28, floatValue38, floatValue35, intValue56);
            floatValue49 += 11.5F * floatValue53;
            dev.leg1tka.render.ScissorStack.b();
            dev.leg1tka.render.ScissorStack.c();
         }
      }

      this.f.entrySet().removeIf(entry1x -> !hashSet9.contains(entry1x.getKey()) && ((dev.leg1tka.api.animation.Animation)entry1x.getValue()).getValue() <= 0.01F);
      this.g.keySet().removeIf(statusEffect1x -> !this.f.containsKey(statusEffect1x));
      this.h.keySet().removeIf(statusEffect1x -> !this.f.containsKey(statusEffect1x));
      this.e.c(floatValue47);
      this.e.d(floatValue48);
   }

   public void c(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      int intValue4;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue4 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue4 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      float floatValue5 = 70.0F;
      float floatValue6 = 16.0F;
      int intValue7 = 0;
      Collection<StatusEffectInstance> collection8 = dev.leg1tka.render.hud.StatusEffectOrder.a() ? dev.leg1tka.render.hud.StatusEffectOrder.b() : (minecraftClient != null && minecraftClient.player != null ? minecraftClient.player.getStatusEffects() : List.of());
      HashSet hashSet9 = new HashSet();

      for (StatusEffectInstance statusEffectInstance11 : collection8) {
         StatusEffect statusEffect12 = (StatusEffect)statusEffectInstance11.getEffectType().value();
         hashSet9.add(statusEffect12);
         this.a(statusEffect12).animateTo(1.0F);
         this.b(statusEffectInstance11);
         int intValue13 = statusEffectInstance11.getDuration();
         Integer integer14 = (Integer)this.h.get(statusEffect12);
         if (integer14 == null || intValue13 > integer14) {
            this.h.put(statusEffect12, intValue13);
         }
      }

      for (Entry entry33 : this.f.entrySet()) {
         if (!hashSet9.contains(entry33.getKey())) {
            ((dev.leg1tka.api.animation.Animation)entry33.getValue()).animateTo(0.0F);
         }
      }

      List<StatusEffect> list32 = this.a(collection8, hashSet9);

      for (StatusEffect statusEffect36 : list32) {
         dev.leg1tka.api.animation.Animation value38 = this.a(statusEffect36);
         float floatValue40 = value38.getValue();
         dev.leg1tka.render.hud.StatusEffectHudRenderer.a value15 = (dev.leg1tka.render.hud.StatusEffectHudRenderer.a)this.g.get(statusEffect36);
         if (floatValue40 > 0.01F && value15 != null) {
            intValue7++;
            String string16 = value15.b != null ? value15.b : I18n.translate(statusEffect36.getTranslationKey(), new Object[0]);
            String string17 = d(value15.c);
            String string18 = a(value15.d, value15.e);
            float floatValue19 = 16.0F;
            float floatValue20 = this.a(12).b(string16);
            if (!string17.isEmpty()) {
               floatValue20 += this.a(10).b(" " + string17);
            }

            float floatValue21 = this.a(10).b(string18);
            float floatValue22 = floatValue19 + floatValue20 + floatValue21 + 20.0F;
            if (floatValue22 > floatValue5) {
               floatValue5 = floatValue22;
            }

            floatValue6 += 20.0F * floatValue40;
         }
      }

      if (intValue7 > 0) {
         floatValue6 += 2.0F;
      }

      this.j.animateTo(floatValue5);
      float floatValue35 = this.j.getValue();
      float floatValue37 = floatValue6;
      this.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue35, floatValue6, intValue4);
      this.a(14).d(default1.getContext().getMatrices(), "Potions list", floatValue2 + 5.0F, floatValue3 + 6.0F, -1);
      this.b(13).d(default1.getContext().getMatrices(), "d", floatValue2 + floatValue35 - 12.5F, floatValue3 + 7.5F, intValue4);
      float floatValue39 = 18.0F;

      for (StatusEffect statusEffect42 : list32) {
         dev.leg1tka.api.animation.Animation value43 = this.a(statusEffect42);
         float floatValue44 = value43.getValue();
         dev.leg1tka.render.hud.StatusEffectHudRenderer.a value45 = (dev.leg1tka.render.hud.StatusEffectHudRenderer.a)this.g.get(statusEffect42);
         if (floatValue44 > 0.01F && value45 != null) {
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue35, (double)floatValue37);
            int intValue46 = (int)(255.0F * floatValue44);
            int intValue47 = dev.leg1tka.api.utils.text.ColorUtils.a(55, 55, 55, intValue46);
            int intValue48 = dev.leg1tka.api.utils.text.ColorUtils.a(35, 35, 35, intValue46);
            dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2 + 3.0F, floatValue3 + floatValue39 - 0.9F, floatValue35 - 6.0F, 18.0F, 2.2F, dev.leg1tka.api.utils.text.ColorUtils.a(18, 22, 30, Math.max(28, intValue46 - 20)), intValue4);
            float floatValue49 = 16.0F;
            float floatValue23 = floatValue2 + 5.0F;
            float floatValue24 = floatValue3 + floatValue39 + 1.0F;
            if (value45.a != null) {
               this.a(default1, value45.a, floatValue23, floatValue24, (int)floatValue49, intValue46);
            }

            String string25 = value45.b != null ? value45.b : I18n.translate(statusEffect42.getTranslationKey(), new Object[0]);
            String string26 = d(value45.c);
            float floatValue27 = floatValue23 + floatValue49 + 4.0F;
            float floatValue28 = floatValue3 + floatValue39 + 5.0F;
            this.a(12).d(default1.getContext().getMatrices(), string25, floatValue27, floatValue28, a(value45, intValue46));
            if (!string26.isEmpty()) {
               float floatValue29 = this.a(12).b(string25);
               int intValue30 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue46);
               this.a(10).d(default1.getContext().getMatrices(), " " + string26, floatValue27 + floatValue29, floatValue28 + 1.0F, intValue30);
            }

            String string50 = a(value45.d, value45.e);
            float floatValue51 = floatValue2 + floatValue35 - this.a(10).b(string50) - 7.0F;
            this.a(10).d(default1.getContext().getMatrices(), string50, floatValue51, floatValue28 + 1.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue46));
            floatValue39 += 20.0F * floatValue44;
            dev.leg1tka.render.ScissorStack.b();
            dev.leg1tka.render.ScissorStack.c();
         }
      }

      this.f.entrySet().removeIf(entry1x -> !hashSet9.contains(entry1x.getKey()) && ((dev.leg1tka.api.animation.Animation)entry1x.getValue()).getValue() <= 0.01F);
      this.g.keySet().removeIf(statusEffect1x -> !this.f.containsKey(statusEffect1x));
      this.h.keySet().removeIf(statusEffect1x -> !this.f.containsKey(statusEffect1x));
      this.e.c(floatValue35);
      this.e.d(floatValue37);
   }

   public void d(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      int intValue4 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
      int intValue5 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4);
      int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4 + 30);
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4 + 90);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4 + 120);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4 + 180);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue4 + 210);
      Collection<StatusEffectInstance> collection11 = dev.leg1tka.render.hud.StatusEffectOrder.a() ? dev.leg1tka.render.hud.StatusEffectOrder.b() : (minecraftClient != null && minecraftClient.player != null ? minecraftClient.player.getStatusEffects() : List.of());
      HashSet hashSet12 = new HashSet();

      for (StatusEffectInstance statusEffectInstance14 : collection11) {
         StatusEffect statusEffect15 = (StatusEffect)statusEffectInstance14.getEffectType().value();
         hashSet12.add(statusEffect15);
         this.a(statusEffect15).animateTo(1.0F);
      }

      for (Entry entry35 : this.f.entrySet()) {
         if (!hashSet12.contains(entry35.getKey())) {
            ((dev.leg1tka.api.animation.Animation)entry35.getValue()).animateTo(0.0F);
         }
      }

      float floatValue33 = 84.0F;
      float floatValue36 = 18.0F;
      int intValue37 = 0;

      for (StatusEffectInstance statusEffectInstance17 : collection11) {
         dev.leg1tka.api.animation.Animation value18 = this.a((StatusEffect)statusEffectInstance17.getEffectType().value());
         float floatValue19 = value18.getValue();
         if (!(floatValue19 <= 0.01F)) {
            intValue37++;
            String string20 = I18n.translate(statusEffectInstance17.getTranslationKey(), new Object[0]);
            String string21 = d(statusEffectInstance17.getAmplifier() + 1);
            String string22 = string20 + (string21.isEmpty() ? "" : " > " + string21);
            floatValue33 = Math.max(floatValue33, this.a(16).b(string22) + 38.0F);
            floatValue33 = Math.max(floatValue33, this.a(15).b(a(statusEffectInstance17)) + 38.0F);
            floatValue36 += 18.0F * floatValue19;
         }
      }

      if (intValue37 == 0) {
         float floatValue39 = 18.0F;
         dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue33, 15.0F, 0.0F, 10.0F, 10.0F, intValue5, intValue6, intValue7, intValue8, intValue9, intValue10);
         String string41 = "potions";
         float floatValue43 = floatValue2 + (floatValue33 - this.a(16).b(string41)) / 2.0F;
         this.a(default1, this.a(16), string41, floatValue43, floatValue3 + 5.0F, -1);
         this.e.c(floatValue33);
         this.e.d(floatValue39);
      } else {
         dev.leg1tka.render.RenderPrimitives.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue33, floatValue36, dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150), 15.0F, 0.0F, 10.0F, 10.0F, intValue5, intValue6, intValue7, intValue8, intValue9, intValue10);
         String string38 = "potions";
         float floatValue40 = floatValue2 + (floatValue33 - this.a(16).b(string38)) / 1.9F;
         this.a(default1, this.a(16), string38, floatValue40, floatValue3 + 5.0F, -1);
         float floatValue42 = 20.0F;

         for (StatusEffectInstance statusEffectInstance45 : collection11) {
            dev.leg1tka.api.animation.Animation value46 = this.a((StatusEffect)statusEffectInstance45.getEffectType().value());
            float floatValue47 = value46.getValue();
            if (!(floatValue47 <= 0.01F)) {
               dev.leg1tka.render.ScissorStack.a();
               dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue33, (double)floatValue36);
               int intValue23 = (int)(255.0F * floatValue47);
               int intValue24 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue23);
               int intValue25 = dev.leg1tka.api.utils.text.ColorUtils.a(20, 185, 45, intValue23);
               float floatValue26 = floatValue2 + 5.0F;
               float floatValue27 = floatValue3 + floatValue42;
               this.a(default1, statusEffectInstance45.getEffectType(), floatValue26, floatValue27, 11, intValue23);
               String string28 = I18n.translate(statusEffectInstance45.getTranslationKey(), new Object[0]).toLowerCase();
               String string29 = d(statusEffectInstance45.getAmplifier() + 1);
               float floatValue30 = floatValue26 + 14.0F;
               this.a(15).d(default1.getContext().getMatrices(), string28 + " >", floatValue30, floatValue3 + floatValue42 - 1.0F, intValue24);
               if (!string29.isEmpty()) {
                  float floatValue31 = this.a(14).b(string28 + " >");
                  this.a(14).d(default1.getContext().getMatrices(), " " + string29, (double)(floatValue30 + floatValue31 + 2.0F), (double)(floatValue3 + floatValue42) - 0.5, intValue25);
               }

               this.a(14).d(default1.getContext().getMatrices(), a(statusEffectInstance45), (double)floatValue30, (double)(floatValue3 + floatValue42) + 7.5, intValue24);
               floatValue42 += 18.0F * floatValue47;
               dev.leg1tka.render.ScissorStack.b();
               dev.leg1tka.render.ScissorStack.c();
            }
         }

         this.e.c(floatValue33);
         this.e.d(floatValue36);
      }
   }

   static class a {
      RegistryEntry a;
      String b;
      int c;
      int d;
      boolean e;

      a() {
      }
   }
}
