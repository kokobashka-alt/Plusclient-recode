package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.DisplayEntity.TextDisplayEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.chunk.WorldChunk;

public final class BotMessageParser {
   private static final Pattern a = Pattern.compile("(?:§.|&.)*(\\d{1,2}):(\\d{2})");
   private static int b;
   private static long c;
   private static final Map<String, dev.leg1tka.network.bots.BotMessageParser.c> d = new HashMap<>();

   private BotMessageParser() {
   }

   public static String a(String string0, ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2) {
      String string3 = string0 == null ? "local" : string0.trim().toLowerCase(Locale.ROOT);
      if (string3.endsWith(":25565")) {
         string3 = string3.substring(0, string3.length() - 6);
      }

      String string4 = dev.leg1tka.network.bots.BotController.a(clientWorld1, clientPlayerEntity2);
      return string3 + "|" + (string4 == null ? "" : string4) + "|" + clientWorld1.getRegistryKey().getValue();
   }

   public static Map<BlockPos, dev.leg1tka.network.bots.BotMessageParser.a> a(String string0) {
      dev.leg1tka.network.bots.BotMessageParser.c value1 = string0 == null ? null : (dev.leg1tka.network.bots.BotMessageParser.c)d.get(string0);
      return value1 == null ? Map.of() : value1.a;
   }

   public static dev.leg1tka.network.bots.BotMessageParser.a a(String string0, BlockPos blockPos1) {
      dev.leg1tka.network.bots.BotMessageParser.c value2 = (dev.leg1tka.network.bots.BotMessageParser.c)d.get(string0);
      return value2 == null ? null : (dev.leg1tka.network.bots.BotMessageParser.a)value2.a.get(blockPos1);
   }

   public static void a() {
      d.clear();
   }

   public static void a(String string0, ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2, long longValue3) {
      dev.leg1tka.network.bots.BotMessageParser.c value5 = d.computeIfAbsent(string0, string0x -> new dev.leg1tka.network.bots.BotMessageParser.c());
      value5.b = longValue3;
      Map<BlockPos, dev.leg1tka.network.bots.BotMessageParser.a> map6 = value5.a;
      ChunkPos chunkPos7 = clientPlayerEntity2.getChunkPos();

      for (int intValue8 = chunkPos7.x - 4; intValue8 <= chunkPos7.x + 4; intValue8++) {
         for (int intValue9 = chunkPos7.z - 4; intValue9 <= chunkPos7.z + 4; intValue9++) {
            if (clientWorld1.getChunkManager().isChunkLoaded(intValue8, intValue9)) {
               WorldChunk worldChunk10 = clientWorld1.getChunk(intValue8, intValue9);

               for (BlockEntity blockEntity12 : worldChunk10.getBlockEntities().values()) {
                  if (blockEntity12 instanceof ChestBlockEntity) {
                     map6.computeIfAbsent(blockEntity12.getPos(), dev.leg1tka.network.bots.BotMessageParser.a::new);
                  }
               }
            }
         }
      }

      Iterator iterator25 = map6.values().iterator();

      while (iterator25.hasNext()) {
         dev.leg1tka.network.bots.BotMessageParser.a value26 = (dev.leg1tka.network.bots.BotMessageParser.a)iterator25.next();
         if (clientWorld1.getChunkManager().isChunkLoaded(value26.a.getX() >> 4, value26.a.getZ() >> 4) && !(clientWorld1.getBlockEntity(value26.a) instanceof ChestBlockEntity)) {
            iterator25.remove();
         }
      }

      double doubleValue27 = 80.0;
      Iterator iterator28 = clientWorld1.getEntities().iterator();

      while (true) {
         long longValue15;
         Entity entity30;
         while (true) {
            if (!iterator28.hasNext()) {
               for (dev.leg1tka.network.bots.BotMessageParser.a value31 : map6.values()) {
                  if (value31.b == AnchorState.TIMER && longValue3 >= value31.c) {
                     value31.b = AnchorState.ACTIVE;
                  }
               }

               d.values().removeIf(value2x -> longValue3 - value2x.b > 600000L);
               return;
            }

            entity30 = (Entity)iterator28.next();
            if ((entity30 instanceof ArmorStandEntity || entity30 instanceof TextDisplayEntity) && !(entity30.squaredDistanceTo(clientPlayerEntity2) > doubleValue27 * doubleValue27)) {
               String string13 = entity30.hasCustomName() && entity30.getCustomName() != null ? entity30.getCustomName().getString() : "";
               if (entity30 instanceof TextDisplayEntity textDisplayEntity14 && textDisplayEntity14.getText() != null) {
                  string13 = textDisplayEntity14.getText().getString();
               }

               Matcher matcher32 = a.matcher(string13);
               if (matcher32.find()) {
                  try {
                     longValue15 = ((long)Integer.parseInt(matcher32.group(1)) * 60L + (long)Integer.parseInt(matcher32.group(2))) * 1000L;
                     break;
                  } catch (NumberFormatException numberFormatException24) {
                  }
               }
            }
         }

         dev.leg1tka.network.bots.BotMessageParser.a value17 = null;
         double doubleValue18 = 25.0;

         for (dev.leg1tka.network.bots.BotMessageParser.a value21 : map6.values()) {
            double doubleValue22 = value21.a.toCenterPos().squaredDistanceTo(entity30.getPos());
            if (doubleValue22 < doubleValue18) {
               doubleValue18 = doubleValue22;
               value17 = value21;
            }
         }

         long longValue33 = longValue3 + longValue15;
         if (value17 != null && (value17.b != AnchorState.TIMER || Math.abs(value17.c - longValue33) > 2000L)) {
            value17.b = AnchorState.TIMER;
            value17.c = longValue33;
         }
      }
   }

   public static class a {
      public final BlockPos a;
      public AnchorState b = AnchorState.UNKNOWN;
      public long c;
      public long d;
      public long e;
      public String f;
      public long g;
      int h = -1;
      String i = "";

      a(BlockPos blockPos1) {
         this.a = blockPos1;
      }

      public boolean a(String string1, long longValue2) {
         return this.f != null && longValue2 < this.g && !this.f.equalsIgnoreCase(string1);
      }

      public String a(long longValue1) {
         if (this.b == AnchorState.ACTIVE) {
            return "Активен";
         } else if (this.b != AnchorState.TIMER) {
            return "";
         } else {
            int intValue3 = (int)Math.max(0L, (this.c - longValue1) / 1000L);
            if (intValue3 != this.h) {
               this.h = intValue3;
               int intValue4 = intValue3 / 60;
               int intValue5 = intValue3 % 60;
               this.i = (intValue4 < 10 ? "0" : "") + intValue4 + ":" + (intValue5 < 10 ? "0" : "") + intValue5;
            }

            return this.i;
         }
      }
   }

   public static enum AnchorState {
      UNKNOWN,
      TIMER,
      ACTIVE;

      private AnchorState() {
      }
   }

   static final class c {
      final Map<BlockPos, dev.leg1tka.network.bots.BotMessageParser.a> a = new HashMap<>();
      long b;

      c() {
      }
   }
}
