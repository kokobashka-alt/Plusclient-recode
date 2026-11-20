package dev.leg1tka.modules.misc;


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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.math.Box;
public class PotionTracker extends Module {
   public static PotionTracker c;
   private static double d;
   private static double e;
   private static double f;
   private static int g;
   private static int h;
   private static int i;
   private final Map j = new HashMap();
   private ClientWorld k;

   public PotionTracker() {
      super(
         "PotionTracker",
         "Показывает попадание выкинутых зелий по игрокам",
         Category.MISC
      );
   }

   @Override
   public void onDisable() {
      this.j.clear();
      this.k = null;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.world == null || minecraftClient.player == null) return;
      if (this.k != minecraftClient.world) {
         this.k = minecraftClient.world;
         this.j.clear();
      }

      for (Entity entity : minecraftClient.world.getEntities()) {
         if (entity instanceof PotionEntity potionEntity) {
            CustomPotion cp = this.a(potionEntity);
            if (cp != null) {
               this.j.putIfAbsent(potionEntity.getId(), new PotionTracker.b(cp, potionEntity.getX(), potionEntity.getY(), potionEntity.getZ()));
            }
         }
      }

      java.util.Iterator<Map.Entry<Integer, PotionTracker.b>> it = this.j.entrySet().iterator();
      while (it.hasNext()) {
         Map.Entry<Integer, PotionTracker.b> entry = it.next();
         Entity e = minecraftClient.world.getEntityById(entry.getKey());
         if (e == null || e.isRemoved()) {
            this.a(entry.getValue());
            it.remove();
         } else {
            entry.getValue().b = e.getX();
            entry.getValue().c = e.getY();
            entry.getValue().d = e.getZ();
         }
      }
   }

   private void a(PotionTracker.b value1) {
      Box box2 = new Box(value1.b - 4.0, value1.c - 2.0, value1.d - 4.0, value1.b + 4.0, value1.c + 2.0, value1.d + 4.0);
      ArrayList arrayList3 = new ArrayList();

      for (PlayerEntity playerEntity5 : minecraftClient.world.getPlayers()) {
         if (playerEntity5 != null && playerEntity5.isAlive() && box2.contains(playerEntity5.getPos())) {
            double doubleValue6 = playerEntity5.getX() - value1.b;
            double doubleValue8 = playerEntity5.getZ() - value1.d;
            double doubleValue10 = Math.sqrt(doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8);
            if (!(doubleValue10 > 4.0)) {
               double doubleValue12 = Math.max(0.0, 1.0 - doubleValue10 / 4.0);
               int intValue14 = Math.max(1, Math.min(100, (int)Math.round(doubleValue12 * 100.0)));
               arrayList3.add(new PotionTracker.a(playerEntity5.getName().getString(), intValue14, doubleValue10));
            }
         }
      }

      arrayList3.sort(Comparator.comparingDouble(createToDoubleFunction183()));

      for (int intValue15 = 0; intValue15 < Math.min(4, arrayList3.size()); intValue15++) {
         PotionTracker.a value16 = (PotionTracker.a)arrayList3.get(intValue15);
         this.a(value16.playerName(), value1.a, value16.percent());
      }
   }

   private CustomPotion a(PotionEntity potionEntity1) {
      PotionContentsComponent potionContentsComponent2 = (PotionContentsComponent)potionEntity1.getStack().get(DataComponentTypes.POTION_CONTENTS);
      CustomPotion customPotion3 = this.a(potionContentsComponent2);
      return customPotion3 != null ? customPotion3 : this.c(potionEntity1.getStack().getName().getString());
   }

   private CustomPotion a(PotionContentsComponent potionContentsComponent1) {
      if (potionContentsComponent1 != null && potionContentsComponent1.hasEffects()) {
         boolean flag2 = this.a(potionContentsComponent1, StatusEffects.REGENERATION, 1);
         boolean flag3 = this.a(potionContentsComponent1, StatusEffects.STRENGTH, 4);
         boolean flag4 = this.a(potionContentsComponent1, StatusEffects.HEALTH_BOOST, 2);
         boolean flag5 = this.a(potionContentsComponent1, StatusEffects.STRENGTH, 3);
         boolean flag6 = this.a(potionContentsComponent1, StatusEffects.SPEED, 2);
         if (flag2) {
            return CustomPotion.HOLY_WATER;
         } else if (flag3) {
            return CustomPotion.WRATH;
         } else if (flag4) {
            return CustomPotion.PALADIN;
         } else if (flag5 && flag6) {
            return CustomPotion.ASSASSIN;
         } else {
            return flag5 ? CustomPotion.ASSASSIN : null;
         }
      } else {
         return null;
      }
   }

   private boolean a(PotionContentsComponent potionContentsComponent1, RegistryEntry<StatusEffect> registryEntry2, int intValue3) {
      for (StatusEffectInstance statusEffectInstance5 : potionContentsComponent1.getEffects()) {
         if (statusEffectInstance5.getEffectType().equals(registryEntry2) && statusEffectInstance5.getAmplifier() == intValue3) {
            return true;
         }
      }

      return false;
   }

   private CustomPotion c(String string1) {
      String string2 = this.d(string1);

      for (CustomPotion customPotion6 : CustomPotion.values()) {
         if (string2.contains(this.d(customPotion6.a()))) {
            return customPotion6;
         }
      }

      return null;
   }

   private String d(String string1) {
      return string1.replaceAll("§.", "").replace("[", "").replace("]", "").replace("✦", "").toLowerCase(Locale.ROOT).trim();
   }

   private void a(String string1, CustomPotion customPotion2, int intValue3) {
      if (minecraftClient.player != null) {
         MutableText mutableText4 = Text.literal("");
         mutableText4.append(this.a("AspectDLC", dev.leg1tka.api.utils.text.ColorUtils.k(0), dev.leg1tka.api.utils.text.ColorUtils.k(90), true));
         mutableText4.append(Text.literal(" ⇒ ").setStyle(this.p()));
         mutableText4.append(Text.literal(string1).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(i))));
         mutableText4.append(Text.literal(" получил ").setStyle(this.p()));
         mutableText4.append(this.a(customPotion2.a, customPotion2.b, customPotion2.c, true));
         mutableText4.append(Text.literal(createString184(intValue3)).setStyle(this.p()));
         minecraftClient.player.sendMessage(mutableText4, false);
      }
   }

   private MutableText a(String string1, int intValue2, int intValue3, boolean flag4) {
      MutableText mutableText5 = Text.literal("");

      for (int intValue6 = 0; intValue6 < string1.length(); intValue6++) {
         float floatValue7 = string1.length() <= 1 ? 0.0F : (float)intValue6 / (float)(string1.length() - 1);
         int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.a(intValue2, intValue3, floatValue7);
         mutableText5.append(Text.literal(String.valueOf(string1.charAt(intValue6))).setStyle(Style.EMPTY.withBold(flag4).withColor(TextColor.fromRgb(intValue8))));
      }

      return mutableText5;
   }

   private Style p() {
      return Style.EMPTY.withColor(TextColor.fromRgb(h));
   }



   private static Predicate<Entity> createPredicate182() {
      return (Entity entity0) -> entity0.isAlive();
   }

   private static ToDoubleFunction<PotionTracker.a> createToDoubleFunction183() {
      return (PotionTracker.a value0) -> value0.distance();
   }

   private static String createString184(int intValue0) {
      return " " + intValue0 + "%";
   }

   static record a(String playerName, int percent, double distance)  {


}

   static class b {
      CustomPotion a;
      double b;
      double c;
      double d;

      b(CustomPotion customPotion1, double doubleValue2, double doubleValue4, double doubleValue6) {
         this.a = customPotion1;
         this.b = doubleValue2;
         this.c = doubleValue4;
         this.d = doubleValue6;
      }
   }

   static enum CustomPotion {
      HOLY_WATER("[✦] Святая вода", 16774507, 12123970),
      WRATH("[✦] Зелье Гнева", 12849682, 16757051),
      PALADIN("[✦] Зелье Паладина", 12123970, 16773280),
      ASSASSIN("[✦] Зелье Ассасина", 5592405, 11545130);

      final String a;
      final int b;
      final int c;

      private CustomPotion(String string3, int intValue4, int intValue5) {
         this.a = string3;
         this.b = intValue4;
         this.c = intValue5;
      }

      String a() {
         int intValue1 = this.a.indexOf("] ");
         return intValue1 >= 0 ? this.a.substring(intValue1 + 2) : this.a;
      }
   }
   public static void dev_leg1tka_o_ip_initializer() {}
}
