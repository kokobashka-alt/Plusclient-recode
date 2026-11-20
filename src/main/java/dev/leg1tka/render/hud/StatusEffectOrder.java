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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;

public final class StatusEffectOrder {
   private static volatile boolean a = false;
   private static final List<StatusEffectInstance> b = new CopyOnWriteArrayList<>();

   private StatusEffectOrder() {
   }

   public static boolean a() {
      return a;
   }

   public static void a(boolean flag0) {
      a = flag0;
      if (!flag0) {
         b.clear();
      }
   }

   public static List<StatusEffectInstance> b() {
      return Collections.unmodifiableList(b);
   }

   public static void a(RegistryEntry<StatusEffect> registryEntry0, int intValue1, int intValue2, boolean flag3) {
      if (registryEntry0 != null) {
         a(registryEntry0);
         int intValue4 = flag3 ? -1 : Math.max(1, intValue2);
         b.add(new StatusEffectInstance(registryEntry0, intValue4, Math.max(0, intValue1)));
      }
   }

   public static void a(RegistryEntry<StatusEffect> registryEntry0) {
      if (registryEntry0 != null) {
         b.removeIf(statusEffectInstance1 -> statusEffectInstance1.getEffectType().value() == registryEntry0.value());
      }
   }

   public static void c() {
      b.clear();
   }

   public static void d() {
      if (a) {
         ArrayList arrayList0 = new ArrayList(b.size());
         boolean flag1 = false;

         for (StatusEffectInstance statusEffectInstance3 : b) {
            if (statusEffectInstance3.isInfinite()) {
               arrayList0.add(statusEffectInstance3);
            } else {
               int intValue4 = statusEffectInstance3.getDuration() - 1;
               if (intValue4 <= 0) {
                  flag1 = true;
               } else {
                  arrayList0.add(new StatusEffectInstance(statusEffectInstance3.getEffectType(), intValue4, statusEffectInstance3.getAmplifier()));
                  flag1 = true;
               }
            }
         }

         if (flag1) {
            b.clear();
            b.addAll(arrayList0);
         }
      }
   }
}
