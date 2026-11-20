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
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class NotificationQueue {
   public static long a;
   private static long b;
   private static final long c = System.currentTimeMillis();
   private static final List<dev.leg1tka.render.hud.NotificationQueue.a> d = new ArrayList<>();

   public NotificationQueue() {
   }

   private static void a(dev.leg1tka.render.hud.NotificationQueue.a value0) {
      long longValue1 = System.currentTimeMillis();

      for (dev.leg1tka.render.hud.NotificationQueue.a value4 : d) {
         if (longValue1 - value4.f < 1500L) {
            if (value0.d != null && value0.d.equals(value4.d)) {
               value4.f = longValue1;
               return;
            }

            if (value0.a != null && value0.a.equals(value4.a) && value0.c == value4.c && value0.d == null && value4.d == null) {
               value4.f = longValue1;
               return;
            }
         }
      }

      d.add(value0);
      if (d.size() > 3) {
         long longValue7 = 2250L;

         for (int intValue5 = 0; intValue5 < d.size() - 3; intValue5++) {
            dev.leg1tka.render.hud.NotificationQueue.a value6 = (dev.leg1tka.render.hud.NotificationQueue.a)d.get(intValue5);
            if (longValue1 - value6.f < longValue7) {
               value6.f = longValue1 - longValue7;
            }
         }
      }
   }

   public static void a(String string0, String string1, boolean flag2) {
      if (string0 != null && !string0.isEmpty()) {
         a(new dev.leg1tka.render.hud.NotificationQueue.a(string0, string1, flag2, null, null, System.currentTimeMillis()));
         a(flag2);
      }
   }

   public static void a(String string0, String string1) {
      if (string0 != null && !string0.isEmpty()) {
         a(new dev.leg1tka.render.hud.NotificationQueue.a(string0, string1, false, string0, null, System.currentTimeMillis()));
      }
   }

   public static void a(String string0, ItemStack itemStack1) {
      if (string0 != null && !string0.isEmpty()) {
         a(new dev.leg1tka.render.hud.NotificationQueue.a(string0, null, false, string0, itemStack1, System.currentTimeMillis()));
      }
   }

   public static List<dev.leg1tka.render.hud.NotificationQueue.a> a() {
      long longValue0 = System.currentTimeMillis();
      Iterator iterator2 = d.iterator();

      while (iterator2.hasNext()) {
         dev.leg1tka.render.hud.NotificationQueue.a value3 = (dev.leg1tka.render.hud.NotificationQueue.a)iterator2.next();
         if (longValue0 - value3.f > 2500L) {
            iterator2.remove();
         }
      }

      return d;
   }

   private static void a(boolean flag0) {
      if (System.currentTimeMillis() - c >= 4000L) {
         ClientSounds clientSounds1 = ClientSounds.c;
         if (clientSounds1 != null && clientSounds1.isEnabled()) {
            String string2 = clientSounds1.d.g();
            if ("Celestial".equals(string2)) {
               String string3 = flag0 ? "celestial_enable" : "celestial_disable";
               float floatValue4 = 1.0F;
               float floatValue5 = clientSounds1.e.f() / clientSounds1.e.h();

               try {
                  MinecraftClient minecraftClient6 = MinecraftClient.getInstance();
                  if (minecraftClient6 != null && minecraftClient6.getSoundManager() != null) {
                     SoundEvent soundEvent7 = SoundEvent.of(Identifier.of("aspectdlc", string3));
                     minecraftClient6.getSoundManager().play(PositionedSoundInstance.master(soundEvent7, floatValue4, floatValue5));
                  }
               } catch (Exception exception8) {
               }
            }
         }
      }
   }

   public static class a {
      public final String a;
      public final String b;
      public final boolean c;
      public final String d;
      public final ItemStack e;
      public long f;

      public a(String string1, String string2, boolean flag3, String string4, ItemStack itemStack5, long longValue6) {
         this.a = string1;
         this.b = string2;
         this.c = flag3;
         this.d = string4;
         this.e = itemStack5;
         this.f = longValue6;
      }

      public boolean a() {
         return this.d != null && !this.d.isEmpty();
      }

      public boolean b() {
         return this.e != null && !this.e.isEmpty();
      }
   }
}
