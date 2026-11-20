package dev.leg1tka.api.utils.player;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;

public final class EquipmentUtils implements dev.leg1tka.api.ClientAccess {
   private static int d;
   private static int e;
   private static int f;
   private static double g;
   private static int h;
   public static long c;
   private static final Int2ObjectOpenHashMap i = new Int2ObjectOpenHashMap();
   private static Object j;
   private static long k = Long.MIN_VALUE;

   private EquipmentUtils() {
   }

   public static void a() {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         if (j != minecraftClient.world) {
            i.clear();
            j = minecraftClient.world;
            k = Long.MIN_VALUE;
         }

         long longValue0 = minecraftClient.world.getTime();
         if (longValue0 != k) {
            k = longValue0;
            long longValue2 = System.currentTimeMillis();

            for (PlayerEntity playerEntity5 : minecraftClient.world.getPlayers()) {
               if (playerEntity5 != minecraftClient.player) {
                  dev.leg1tka.api.utils.player.EquipmentUtils.a value6 = (dev.leg1tka.api.utils.player.EquipmentUtils.a)i.get(playerEntity5.getId());
                  if (value6 == null) {
                     value6 = new dev.leg1tka.api.utils.player.EquipmentUtils.a();
                     i.put(playerEntity5.getId(), value6);
                  }

                  value6.i = longValue0;
                  boolean flag7 = !playerEntity5.hasVehicle()
                     && !playerEntity5.isGliding()
                     && !playerEntity5.isCreative()
                     && !playerEntity5.isSpectator()
                     && !playerEntity5.isTouchingWater()
                     && !playerEntity5.isInLava()
                     && !playerEntity5.isClimbing()
                     && minecraftClient.world.isSpaceEmpty(playerEntity5, playerEntity5.getBoundingBox().offset(0.0, -0.0625, 0.0));
                  double doubleValue8 = playerEntity5.getX() - value6.a;
                  double doubleValue10 = playerEntity5.getY() - value6.b;
                  double doubleValue12 = playerEntity5.getZ() - value6.c;
                  boolean flag14 = value6.d && doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12 < 1.0E-4;
                  value6.e = flag7 && value6.d && !flag14 ? value6.e + 1 : 0;
                  value6.f = value6.e >= 25 ? 40 : Math.max(0, value6.f - 1);
                  int intValue15 = flag7 && flag14 && value6.f == 0 ? value6.g + 1 : 0;
                  if (intValue15 >= 20 && value6.g < 20) {
                     value6.h = longValue2;
                  }

                  value6.g = intValue15;
                  value6.a = playerEntity5.getX();
                  value6.b = playerEntity5.getY();
                  value6.c = playerEntity5.getZ();
                  value6.d = true;
               }
            }

            if (longValue0 % 100L == 0L) {
               ObjectIterator objectIterator16 = i.int2ObjectEntrySet().fastIterator();

               while (objectIterator16.hasNext()) {
                  if (((dev.leg1tka.api.utils.player.EquipmentUtils.a)((Entry)objectIterator16.next()).getValue()).i != longValue0) {
                     objectIterator16.remove();
                  }
               }
            }
         }
      }
   }

   public static boolean a(PlayerEntity playerEntity0) {
      dev.leg1tka.api.utils.player.EquipmentUtils.a value1 = (dev.leg1tka.api.utils.player.EquipmentUtils.a)i.get(playerEntity0.getId());
      return value1 != null && value1.g >= 20 && !playerEntity0.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA);
   }

   public static long b(PlayerEntity playerEntity0) {
      dev.leg1tka.api.utils.player.EquipmentUtils.a value1 = (dev.leg1tka.api.utils.player.EquipmentUtils.a)i.get(playerEntity0.getId());
      return value1 == null ? 0L : Math.max(0L, 4500L - (System.currentTimeMillis() - value1.h));
   }

   static class a {
      double a;
      double b;
      double c;
      boolean d;
      int e;
      int f;
      int g;
      long h;
      long i;

      a() {
      }
   }
}
