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

import dev.leg1tka.api.events.implement.EventBlockChange;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPotion;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public final class BotSoundEvents {
   private static long a;
   private static long b;
   private static double c;
   private static double d;
   private static double e;
   private static int f;
   private static int g;
   private static int h;
   private static int i;
   private static final Pattern j = Pattern.compile(
      "(.*?)\\s*будет доступн\\S*\\s+через\\s+(\\d[\\d.,]*)\\s*сек",
      2
   );
   private static final Map<java.util.UUID, dev.leg1tka.network.bots.BotSoundEvents.b> k = new HashMap<>();
   private static final ConcurrentLinkedQueue<dev.leg1tka.network.bots.BotSoundEvents.c> l = new ConcurrentLinkedQueue<>();
   private static final List<dev.leg1tka.network.bots.BotSoundEvents.d> m = new ArrayList<>();
   private static final Set<BlockPos> n = new HashSet<>();
   private static final EnumMap<dev.leg1tka.api.utils.player.ItemCustomData.a, Long> o = new EnumMap<>(dev.leg1tka.api.utils.player.ItemCustomData.a.class);
   private static final Map<String, Long> p = new HashMap<>();
   private static final List<dev.leg1tka.network.bots.BotSoundEvents.a> q = new ArrayList<>();
   private static ClientWorld r;
   private static int s;
   private static int t;
   private static int u;
   private static int v;
   private static ItemStack w = ItemStack.EMPTY;
   private static long x;

   private BotSoundEvents() {
   }

   private static MinecraftClient c() {
      return MinecraftClient.getInstance();
   }

   private static boolean d() {
      FTHelper fTHelper0 = FTHelper.c;
      return fTHelper0 != null && fTHelper0.isEnabled() && fTHelper0.p().getValue();
   }

   public static void a() {
      k.clear();
      l.clear();
      m.clear();
      n.clear();
      s = 0;
      v = 0;
      u = 0;
      t = 0;
   }

   public static void a(ItemStack itemStack0) {
      if (itemStack0 != null && !itemStack0.isEmpty()) {
         long longValue1 = System.currentTimeMillis();
         w = itemStack0.copy();
         x = longValue1;

         for (dev.leg1tka.api.utils.player.ItemCustomData.a value6 : dev.leg1tka.api.utils.player.ItemCustomData.a.values()) {
            if (itemStack0.isOf(value6.b)) {
               o.put(value6, longValue1);
            }
         }
      }
   }

   public static long b(ItemStack itemStack0) {
      if (!p.isEmpty() && itemStack0 != null && !itemStack0.isEmpty()) {
         String string1 = dev.leg1tka.api.utils.player.ItemCustomData.b(itemStack0);
         long longValue2 = System.currentTimeMillis();
         long longValue4 = 0L;

         for (Entry<String, Long> entry7 : p.entrySet()) {
            long longValue8 = (Long)entry7.getValue() - longValue2;
            if (longValue8 > longValue4 && !((String)entry7.getKey()).isEmpty() && string1.contains((CharSequence)entry7.getKey())) {
               longValue4 = longValue8;
            }
         }

         return longValue4;
      } else {
         return 0L;
      }
   }

   public static List<dev.leg1tka.network.bots.BotSoundEvents.a> a(PlayerEntity playerEntity0) {
      q.clear();
      if (playerEntity0 == null) {
         return q;
      } else {
         dev.leg1tka.network.bots.BotSoundEvents.b value1 = (dev.leg1tka.network.bots.BotSoundEvents.b)k.get(playerEntity0.getUuid());
         if (value1 != null && value1.a == playerEntity0.getId()) {
            long longValue2 = System.currentTimeMillis();

            for (dev.leg1tka.network.bots.BotSoundEvents.a value5 : value1.c.values()) {
               if (value5.h > longValue2) {
                  q.add(value5);
               }
            }

            return q;
         } else {
            return q;
         }
      }
   }

   public static void a(EventPacket eventPacket0) {
      if (eventPacket0.getType() != EventPacket.Type.SEND) {
         Packet packet1 = eventPacket0.getPacket();
         if (packet1 instanceof GameJoinS2CPacket) {
            l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(packet1, System.currentTimeMillis()));
         } else if (packet1 instanceof CooldownUpdateS2CPacket cooldownUpdateS2CPacket8) {
            l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(cooldownUpdateS2CPacket8, System.currentTimeMillis()));
         } else {
            label75:
            if (!(packet1 instanceof OverlayMessageS2CPacket)) {
               if (packet1 instanceof GameMessageS2CPacket gameMessageS2CPacket2 && gameMessageS2CPacket2.overlay()) {
                  break label75;
               }

               if (!d()) {
                  return;
               }

               if (packet1 instanceof EntityStatusS2CPacket entityStatusS2CPacket7) {
                  if (entityStatusS2CPacket7.getStatus() == 35) {
                     l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(entityStatusS2CPacket7, System.currentTimeMillis()));
                  }
               } else if (packet1 instanceof EntitySpawnS2CPacket entitySpawnS2CPacket3) {
                  EntityType entityType6 = entitySpawnS2CPacket3.getEntityType();
                  if (entityType6 == EntityType.SNOWBALL
                     || entityType6 == EntityType.WIND_CHARGE
                     || entityType6 == EntityType.ENDER_PEARL
                     || entityType6 == EntityType.EYE_OF_ENDER
                     || entityType6 == EntityType.POTION
                     || entityType6 == EntityType.SMALL_FIREBALL
                     || entityType6 == EntityType.FIREBALL) {
                     l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(entitySpawnS2CPacket3, System.currentTimeMillis()));
                  }
               } else if (packet1 instanceof PlaySoundS2CPacket playSoundS2CPacket4) {
                  String string9 = ((SoundEvent)playSoundS2CPacket4.getSound().value()).id().getPath();
                  if (string9.equals("entity.illusioner.mirror_move") || string9.equals("entity.blaze.shoot") || string9.equals("entity.wind_charge.throw")) {
                     l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(playSoundS2CPacket4, System.currentTimeMillis()));
                  }
               } else if (packet1 instanceof EntityStatusEffectS2CPacket entityStatusEffectS2CPacket5) {
                  RegistryEntry registryEntry10 = entityStatusEffectS2CPacket5.getEffectId();
                  if (registryEntry10.equals(StatusEffects.WITHER) || registryEntry10.equals(StatusEffects.SLOWNESS) || registryEntry10.equals(StatusEffects.MINING_FATIGUE)) {
                     l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(entityStatusEffectS2CPacket5, System.currentTimeMillis()));
                  }
               }

               return;
            }

            l.add(new dev.leg1tka.network.bots.BotSoundEvents.c(packet1, System.currentTimeMillis()));
         }
      }
   }

   public static void a(EventBlockChange eventBlockChange0) {
      if (d()) {
         if (TrapESP.a(eventBlockChange0.getNewState().getBlock()) && !TrapESP.a(eventBlockChange0.getOldState().getBlock())) {
            n.add(eventBlockChange0.getPos());
            s = 2;
         }
      }
   }

   public static void a(EventPotion eventPotion0) {
      MinecraftClient minecraftClient1 = c();
      if (d() && minecraftClient1.world != null && minecraftClient1.player != null) {
         BlockPos blockPos2 = eventPotion0.getPos();
         PotionEntity potionEntity3 = dev.leg1tka.api.utils.player.ItemCustomData.a(minecraftClient1.world, blockPos2);
         if (potionEntity3 != null && dev.leg1tka.api.utils.player.ItemCustomData.c(potionEntity3.getStack()) && potionEntity3.getOwner() instanceof PlayerEntity playerEntity4) {
            a(playerEntity4, dev.leg1tka.api.utils.player.ItemCustomData.a.HEALING, System.currentTimeMillis());
         }
      }
   }

   public static void b() {
      MinecraftClient minecraftClient0 = c();
      if (minecraftClient0.world != null && minecraftClient0.player != null) {
         if (minecraftClient0.world != r) {
            r = minecraftClient0.world;
            a();
         }

         a(minecraftClient0);
         if (!d()) {
            if (!k.isEmpty()) {
               a();
            }
         } else {
            c(minecraftClient0);
            d(minecraftClient0);
            e(minecraftClient0);
            e();
            f(minecraftClient0);
         }
      } else {
         a();
         r = null;
      }
   }

   private static void a(MinecraftClient minecraftClient0) {
      dev.leg1tka.network.bots.BotSoundEvents.c value1;
      while ((value1 = (dev.leg1tka.network.bots.BotSoundEvents.c)l.poll()) != null) {
         Object object2 = value1.packet();
         long longValue3 = value1.time();
         if (object2 instanceof GameJoinS2CPacket) {
            a();
         } else if (object2 instanceof CooldownUpdateS2CPacket cooldownUpdateS2CPacket5) {
            dev.leg1tka.api.utils.player.ItemCustomData.a.a(cooldownUpdateS2CPacket5.cooldownGroup(), cooldownUpdateS2CPacket5.cooldown());
         } else if (object2 instanceof OverlayMessageS2CPacket overlayMessageS2CPacket6) {
            a(overlayMessageS2CPacket6.text().getString(), longValue3);
         } else if (object2 instanceof GameMessageS2CPacket gameMessageS2CPacket7) {
            a(gameMessageS2CPacket7.content().getString(), longValue3);
         } else if (d()) {
            if (object2 instanceof EntityStatusS2CPacket) {
               EntityStatusS2CPacket entityStatusS2CPacket8 = (EntityStatusS2CPacket)object2;
               if (entityStatusS2CPacket8.getEntity(minecraftClient0.world) instanceof PlayerEntity playerEntity14) {
                  a(playerEntity14, dev.leg1tka.api.utils.player.ItemCustomData.a.TOTEM, longValue3);
               }
            } else if (object2 instanceof EntitySpawnS2CPacket entitySpawnS2CPacket9) {
               a(minecraftClient0, entitySpawnS2CPacket9, longValue3);
            } else if (object2 instanceof PlaySoundS2CPacket playSoundS2CPacket10) {
               a(minecraftClient0, playSoundS2CPacket10, longValue3);
            } else if (object2 instanceof EntityStatusEffectS2CPacket) {
               EntityStatusEffectS2CPacket entityStatusEffectS2CPacket11 = (EntityStatusEffectS2CPacket)object2;
               if (entityStatusEffectS2CPacket11.getEntityId() == minecraftClient0.player.getId()) {
                  RegistryEntry registryEntry12 = entityStatusEffectS2CPacket11.getEffectId();
                  if (registryEntry12.equals(StatusEffects.WITHER) && entityStatusEffectS2CPacket11.getAmplifier() == 1) {
                     t = 20;
                  } else if (registryEntry12.equals(StatusEffects.SLOWNESS) && entityStatusEffectS2CPacket11.getAmplifier() == 4) {
                     u = 20;
                  } else if (registryEntry12.equals(StatusEffects.MINING_FATIGUE) && entityStatusEffectS2CPacket11.getAmplifier() == 4) {
                     v = 20;
                  }
               }
            }
         }
      }
   }

   private static void a(String string0, long longValue1) {
      String string3 = Formatting.strip(string0);
      if (string3 != null && string3.contains("доступн")) {
         Matcher matcher4 = j.matcher(string3);
         if (matcher4.find()) {
            float floatValue5;
            try {
               floatValue5 = Float.parseFloat(matcher4.group(2).replace(',', '.'));
            } catch (NumberFormatException numberFormatException11) {
               return;
            }

            long longValue6 = longValue1 + (long)(floatValue5 * 1000.0F);
            String string8 = matcher4.group(1).trim().toLowerCase(Locale.ROOT);
            int intValue9 = string8.lastIndexOf(93);
            if (intValue9 >= 0) {
               string8 = string8.substring(intValue9 + 1).trim();
            }

            if (!w.isEmpty() && longValue1 - x < 1500L) {
               String string10 = dev.leg1tka.api.utils.player.ItemCustomData.b(w);
               if (string8.isEmpty() || !string10.contains(string8)) {
                  string8 = string10;
               }
            }

            if (!string8.isEmpty()) {
               p.put(string8, longValue6);
               p.values().removeIf(long2 -> long2 < longValue1);
            }
         }
      }
   }

   private static void a(MinecraftClient minecraftClient0, EntitySpawnS2CPacket entitySpawnS2CPacket1, long longValue2) {
      EntityType entityType4 = entitySpawnS2CPacket1.getEntityType();
      int intValue5 = entitySpawnS2CPacket1.getEntityData();
      if (entityType4 == EntityType.POTION) {
         m.add(new dev.leg1tka.network.bots.BotSoundEvents.d(entitySpawnS2CPacket1.getEntityId(), intValue5, 0));
      } else {
         dev.leg1tka.api.utils.player.ItemCustomData.a value6;
         if (entityType4 == EntityType.SNOWBALL) {
            value6 = dev.leg1tka.api.utils.player.ItemCustomData.a.SNOWBALL;
         } else if (entityType4 == EntityType.WIND_CHARGE) {
            value6 = dev.leg1tka.api.utils.player.ItemCustomData.a.WIND_CHARGE;
         } else if (entityType4 == EntityType.ENDER_PEARL) {
            value6 = dev.leg1tka.api.utils.player.ItemCustomData.a.PEARL;
         } else if (entityType4 == EntityType.EYE_OF_ENDER) {
            value6 = dev.leg1tka.api.utils.player.ItemCustomData.a.DEZORIENTATION;
         } else {
            value6 = dev.leg1tka.api.utils.player.ItemCustomData.a.FIRE_CHARGE;
         }

         PlayerEntity playerEntity7 = null;
         if (entityType4 != EntityType.EYE_OF_ENDER && intValue5 != 0) {
            if (!(minecraftClient0.world.getEntityById(intValue5) instanceof PlayerEntity playerEntity8)) {
               return;
            }

            playerEntity7 = playerEntity8;
         }

         if (playerEntity7 == null) {
            playerEntity7 = a(minecraftClient0, new Vec3d(entitySpawnS2CPacket1.getX(), entitySpawnS2CPacket1.getY(), entitySpawnS2CPacket1.getZ()));
         }

         if (playerEntity7 != null && playerEntity7 != minecraftClient0.player) {
            a(playerEntity7, value6, longValue2);
         }
      }
   }

   private static PlayerEntity a(MinecraftClient minecraftClient0, Vec3d vec3d1) {
      AbstractClientPlayerEntity abstractClientPlayerEntity2 = null;
      double doubleValue3 = 6.25;

      for (AbstractClientPlayerEntity abstractClientPlayerEntity6 : minecraftClient0.world.getPlayers()) {
         if (abstractClientPlayerEntity6.isAlive()) {
            double doubleValue7 = abstractClientPlayerEntity6.squaredDistanceTo(vec3d1);
            if (doubleValue7 < doubleValue3) {
               doubleValue3 = doubleValue7;
               abstractClientPlayerEntity2 = abstractClientPlayerEntity6;
            }
         }
      }

      return abstractClientPlayerEntity2;
   }

   private static void a(MinecraftClient minecraftClient0, PlaySoundS2CPacket playSoundS2CPacket1, long longValue2) {
      String string4 = ((SoundEvent)playSoundS2CPacket1.getSound().value()).id().getPath();
      Vec3d vec3d5 = new Vec3d(playSoundS2CPacket1.getX(), playSoundS2CPacket1.getY(), playSoundS2CPacket1.getZ());
      double doubleValue6 = minecraftClient0.player.squaredDistanceTo(vec3d5);
      if (string4.equals("entity.illusioner.mirror_move")) {
         if (!(doubleValue6 > 36.0) && !a(dev.leg1tka.api.utils.player.ItemCustomData.a.DEZORIENTATION, longValue2)) {
            PlayerEntity playerEntity16 = b(minecraftClient0);
            if (playerEntity16 != null) {
               a(playerEntity16, dev.leg1tka.api.utils.player.ItemCustomData.a.DEZORIENTATION, longValue2);
            }
         }
      } else {
         dev.leg1tka.api.utils.player.ItemCustomData.a value8 = string4.equals("entity.blaze.shoot") ? dev.leg1tka.api.utils.player.ItemCustomData.a.FIRE_CHARGE : dev.leg1tka.api.utils.player.ItemCustomData.a.WIND_CHARGE;
         if (!a(value8, longValue2) || !(doubleValue6 < 36.0)) {
            AbstractClientPlayerEntity abstractClientPlayerEntity9 = null;
            double doubleValue10 = 36.0;

            for (AbstractClientPlayerEntity abstractClientPlayerEntity13 : minecraftClient0.world.getPlayers()) {
               if (abstractClientPlayerEntity13 != minecraftClient0.player && abstractClientPlayerEntity13.isAlive()) {
                  double doubleValue14 = abstractClientPlayerEntity13.squaredDistanceTo(vec3d5);
                  if (doubleValue14 < doubleValue10 && doubleValue14 < doubleValue6) {
                     doubleValue10 = doubleValue14;
                     abstractClientPlayerEntity9 = abstractClientPlayerEntity13;
                  }
               }
            }

            if (abstractClientPlayerEntity9 != null) {
               a(abstractClientPlayerEntity9, value8, longValue2);
            }
         }
      }
   }

   private static PlayerEntity b(MinecraftClient minecraftClient0) {
      AttackAura attackAura1 = ModuleManager.a != null ? ModuleManager.i : null;
      if (attackAura1 != null && attackAura1.isEnabled() && attackAura1.E() instanceof PlayerEntity playerEntity2 && playerEntity2.isAlive() && playerEntity2 != minecraftClient0.player) {
         return playerEntity2;
      } else {
         AbstractClientPlayerEntity abstractClientPlayerEntity9 = null;
         double doubleValue10 = 144.0;

         for (AbstractClientPlayerEntity abstractClientPlayerEntity6 : minecraftClient0.world.getPlayers()) {
            if (abstractClientPlayerEntity6 != minecraftClient0.player && abstractClientPlayerEntity6.isAlive()) {
               double doubleValue7 = minecraftClient0.player.squaredDistanceTo(abstractClientPlayerEntity6);
               if (doubleValue7 < doubleValue10) {
                  doubleValue10 = doubleValue7;
                  abstractClientPlayerEntity9 = abstractClientPlayerEntity6;
               }
            }
         }

         return abstractClientPlayerEntity9;
      }
   }

   private static void e() {
      if (t > 0 && u > 0 && v > 0) {
         v = 0;
         u = 0;
         t = 0;
         long longValue0 = System.currentTimeMillis();
         if (!a(dev.leg1tka.api.utils.player.ItemCustomData.a.DEZORIENTATION, longValue0)) {
            PlayerEntity playerEntity2 = b(c());
            if (playerEntity2 != null) {
               a(playerEntity2, dev.leg1tka.api.utils.player.ItemCustomData.a.DEZORIENTATION, longValue0);
            }
         }
      } else {
         if (t > 0) {
            t--;
         }

         if (u > 0) {
            u--;
         }

         if (v > 0) {
            v--;
         }
      }
   }

   private static void c(MinecraftClient minecraftClient0) {
      if (!m.isEmpty()) {
         long longValue1 = System.currentTimeMillis();

         for (int intValue3 = m.size() - 1; intValue3 >= 0; intValue3--) {
            dev.leg1tka.network.bots.BotSoundEvents.d value4 = (dev.leg1tka.network.bots.BotSoundEvents.d)m.get(intValue3);
            Entity entity5 = minecraftClient0.world.getEntityById(value4.entityId());
            if (entity5 instanceof PotionEntity potionEntity6 && !potionEntity6.getStack().isEmpty() && !potionEntity6.getStack().isOf(Items.AIR)) {
               m.remove(intValue3);
               if (dev.leg1tka.api.utils.player.ItemCustomData.c(potionEntity6.getStack()) && (value4.ownerId() != 0 ? minecraftClient0.world.getEntityById(value4.ownerId()) : null) instanceof PlayerEntity playerEntity8 && playerEntity8 != minecraftClient0.player) {
                  a(playerEntity8, dev.leg1tka.api.utils.player.ItemCustomData.a.HEALING, longValue1);
               }
               continue;
            }

            if (entity5 != null && value4.age() < 3) {
               m.set(intValue3, new dev.leg1tka.network.bots.BotSoundEvents.d(value4.entityId(), value4.ownerId(), value4.age() + 1));
            } else {
               m.remove(intValue3);
            }
         }
      }
   }

   private static void d(MinecraftClient minecraftClient0) {
      if (s > 0 && --s <= 0) {
         if (!n.isEmpty()) {
            int intValue1 = Integer.MAX_VALUE;
            int intValue2 = Integer.MAX_VALUE;
            int intValue3 = Integer.MAX_VALUE;
            int intValue4 = Integer.MIN_VALUE;
            int intValue5 = Integer.MIN_VALUE;
            int intValue6 = Integer.MIN_VALUE;

            for (BlockPos blockPos8 : n) {
               intValue1 = Math.min(intValue1, blockPos8.getX());
               intValue2 = Math.min(intValue2, blockPos8.getY());
               intValue3 = Math.min(intValue3, blockPos8.getZ());
               intValue4 = Math.max(intValue4, blockPos8.getX());
               intValue5 = Math.max(intValue5, blockPos8.getY());
               intValue6 = Math.max(intValue6, blockPos8.getZ());
            }

            BlockBox blockBox23 = new BlockBox(intValue1, intValue2, intValue3, intValue4, intValue5, intValue6);
            TrapESP.TrapType trapType24 = null;

            for (TrapESP.TrapType trapType12 : TrapESP.TrapType.values()) {
               if (trapType12.a(blockBox23, n)) {
                  trapType24 = trapType12;
                  break;
               }
            }

            n.clear();
            if (trapType24 != null) {
               dev.leg1tka.api.utils.player.ItemCustomData.a value25 = switch (trapType24) {
                  case DRAGON_TRAPKA -> dev.leg1tka.api.utils.player.ItemCustomData.a.DRAGON_TRAP;
                  case TRAPKA -> dev.leg1tka.api.utils.player.ItemCustomData.a.TRAP;
                  case DRAGON_PLAST -> dev.leg1tka.api.utils.player.ItemCustomData.a.DRAGON_PLAST;
                  default -> dev.leg1tka.api.utils.player.ItemCustomData.a.PLAST;
               };
               long longValue26 = System.currentTimeMillis();
               dev.leg1tka.api.utils.player.ItemCustomData.a value27 = value25 == dev.leg1tka.api.utils.player.ItemCustomData.a.DRAGON_TRAP ? dev.leg1tka.api.utils.player.ItemCustomData.a.TRAP : (value25 == dev.leg1tka.api.utils.player.ItemCustomData.a.DRAGON_PLAST ? dev.leg1tka.api.utils.player.ItemCustomData.a.PLAST : value25);
               if (!a(value27, longValue26)) {
                  Vec3d vec3d13 = new Vec3d((double)(intValue1 + intValue4 + 1) * 0.5, (double)(intValue2 + intValue5 + 1) * 0.5, (double)(intValue3 + intValue6 + 1) * 0.5);
                  double doubleValue14 = value27 == dev.leg1tka.api.utils.player.ItemCustomData.a.TRAP ? 16.0 : 36.0;
                  AbstractClientPlayerEntity abstractClientPlayerEntity16 = null;
                  double doubleValue17 = doubleValue14;

                  for (AbstractClientPlayerEntity abstractClientPlayerEntity20 : minecraftClient0.world.getPlayers()) {
                     if (abstractClientPlayerEntity20.isAlive()) {
                        double doubleValue21 = abstractClientPlayerEntity20.squaredDistanceTo(vec3d13);
                        if (doubleValue21 < doubleValue17) {
                           doubleValue17 = doubleValue21;
                           abstractClientPlayerEntity16 = abstractClientPlayerEntity20;
                        }
                     }
                  }

                  if (abstractClientPlayerEntity16 != null && abstractClientPlayerEntity16 != minecraftClient0.player) {
                     a(abstractClientPlayerEntity16, value25, longValue26);
                  }
               }
            }
         }
      }
   }

   private static void e(MinecraftClient minecraftClient0) {
      long longValue1 = System.currentTimeMillis();

      for (AbstractClientPlayerEntity abstractClientPlayerEntity4 : minecraftClient0.world.getPlayers()) {
         if (abstractClientPlayerEntity4 != minecraftClient0.player) {
            boolean flag5 = abstractClientPlayerEntity4.isUsingItem() && abstractClientPlayerEntity4.isAlive();
            dev.leg1tka.network.bots.BotSoundEvents.b value6 = (dev.leg1tka.network.bots.BotSoundEvents.b)k.get(abstractClientPlayerEntity4.getUuid());
            if (value6 == null) {
               if (!flag5) {
                  continue;
               }

               value6 = b(abstractClientPlayerEntity4);
            }

            if (flag5 && !value6.d) {
               value6.e = abstractClientPlayerEntity4.getActiveItem().copy();
               value6.f = 0;
            }

            if (flag5) {
               value6.f++;
               value6.g = abstractClientPlayerEntity4.getItemUseTimeLeft();
            } else if (value6.d) {
               if (value6.f >= 10 && value6.g <= 3) {
                  a(abstractClientPlayerEntity4, value6.e, longValue1);
               }

               value6.e = ItemStack.EMPTY;
               value6.f = 0;
            }

            value6.d = flag5;
         }
      }
   }

   private static void a(PlayerEntity playerEntity0, ItemStack itemStack1, long longValue2) {
      if (itemStack1.isOf(Items.ENCHANTED_GOLDEN_APPLE)) {
         a(playerEntity0, dev.leg1tka.api.utils.player.ItemCustomData.a.ENCHANTED_APPLE, longValue2);
      } else if (itemStack1.isOf(Items.GOLDEN_APPLE)) {
         a(playerEntity0, dev.leg1tka.api.utils.player.ItemCustomData.a.GOLDEN_APPLE, longValue2);
      } else if (itemStack1.isOf(Items.CHORUS_FRUIT)) {
         a(playerEntity0, dev.leg1tka.api.utils.player.ItemCustomData.a.CHORUS, longValue2);
      } else if (itemStack1.isOf(Items.POTION) && dev.leg1tka.api.utils.player.ItemCustomData.c(itemStack1)) {
         a(playerEntity0, dev.leg1tka.api.utils.player.ItemCustomData.a.HEALING, longValue2);
      }
   }

   private static boolean a(dev.leg1tka.api.utils.player.ItemCustomData.a value0, long longValue1) {
      Long long3 = (Long)o.get(value0);
      return long3 != null && longValue1 - long3 < 2500L;
   }

   private static dev.leg1tka.network.bots.BotSoundEvents.b b(PlayerEntity playerEntity0) {
      dev.leg1tka.network.bots.BotSoundEvents.b value1 = (dev.leg1tka.network.bots.BotSoundEvents.b)k.get(playerEntity0.getUuid());
      if (value1 == null) {
         value1 = new dev.leg1tka.network.bots.BotSoundEvents.b();
         value1.a = playerEntity0.getId();
         k.put(playerEntity0.getUuid(), value1);
      } else if (value1.a != playerEntity0.getId()) {
         value1.a = playerEntity0.getId();
         value1.c.clear();
         value1.d = false;
      }

      value1.b = playerEntity0.getName().getString();
      return value1;
   }

   private static void a(PlayerEntity playerEntity0, dev.leg1tka.api.utils.player.ItemCustomData.a value1, long longValue2) {
      MinecraftClient minecraftClient4 = c();
      if (playerEntity0 != null && minecraftClient4.player != null && playerEntity0 != minecraftClient4.player && !playerEntity0.getUuid().equals(minecraftClient4.player.getUuid())) {
         dev.leg1tka.network.bots.BotSoundEvents.b value5 = b(playerEntity0);
         dev.leg1tka.network.bots.BotSoundEvents.a value6 = (dev.leg1tka.network.bots.BotSoundEvents.a)value5.c.get(value1);
         if (value6 == null || longValue2 - value6.g >= 2000L) {
            value5.c.put(value1, new dev.leg1tka.network.bots.BotSoundEvents.a(value1.a, value1.a(), longValue2, longValue2 + value1.b(), 16734810, null));
         }
      }
   }

   private static void f(MinecraftClient minecraftClient0) {
      if (!k.isEmpty()) {
         long longValue1 = System.currentTimeMillis();
         boolean flag3 = FTHelper.c.q().getValue();
         Iterator iterator4 = k.entrySet().iterator();

         while (iterator4.hasNext()) {
            dev.leg1tka.network.bots.BotSoundEvents.b value5 = (dev.leg1tka.network.bots.BotSoundEvents.b)((Entry)iterator4.next()).getValue();
            Iterator iterator6 = value5.c.entrySet().iterator();

            while (iterator6.hasNext()) {
               dev.leg1tka.network.bots.BotSoundEvents.a value7 = (dev.leg1tka.network.bots.BotSoundEvents.a)((Entry)iterator6.next()).getValue();
               if (longValue1 >= value7.h) {
                  iterator6.remove();
                  if (flag3) {
                     a(value5.b, value7);
                  }
               }
            }

            if (value5.c.isEmpty() && !value5.d && !(minecraftClient0.world.getEntityById(value5.a) instanceof PlayerEntity)) {
               iterator4.remove();
            }
         }
      }
   }

   private static void a(String string0, dev.leg1tka.network.bots.BotSoundEvents.a value1) {
      dev.leg1tka.api.utils.text.ClientChat.a(
         "FTHelper",
         (Text)Text.literal("У игрока ")
            .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(13158600)))
            .append(Text.literal(string0).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777215))))
            .append(
               Text.literal(" прошло КД на ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(13158600)))
            )
            .append(Text.literal(value1.a).setStyle(Style.EMPTY.withColor(Formatting.GREEN)))
      );
      dev.leg1tka.render.hud.NotificationQueue.a(string0 + ": " + value1.a + " готов", value1.c);
   }

   public static class a {
      public final String a;
      public final String b;
      public final ItemStack c;
      public final int d;
      public final boolean e;
      public final dev.leg1tka.api.utils.player.ItemCustomData.b f;
      public final long g;
      public final long h;

      public a(String string1, ItemStack itemStack2, long longValue3, long longValue5, int intValue7, dev.leg1tka.api.utils.player.ItemCustomData.b value8) {
         this.a = string1;
         this.b = value8 != null ? string1 : "КД " + string1;
         this.c = itemStack2;
         this.g = longValue3;
         this.h = longValue5;
         this.d = intValue7;
         this.f = value8;
         this.e = value8 != null;
      }

      public float a() {
         long longValue1 = this.h - System.currentTimeMillis();
         return longValue1 <= 0L ? 0.0F : (float)longValue1 / 1000.0F;
      }

      public int b() {
         long longValue1 = this.h - System.currentTimeMillis();
         return longValue1 <= 0L ? 0 : (int)(longValue1 / 50L);
      }

      public float c() {
         long longValue1 = this.h - this.g;
         long longValue3 = this.h - System.currentTimeMillis();
         return longValue3 > 0L && longValue1 > 0L ? Math.min(1.0F, (float)longValue3 / (float)longValue1) : 0.0F;
      }
   }

   static class b {
      int a;
      String b = "";
      final EnumMap<dev.leg1tka.api.utils.player.ItemCustomData.a, dev.leg1tka.network.bots.BotSoundEvents.a> c = new EnumMap<>(dev.leg1tka.api.utils.player.ItemCustomData.a.class);
      boolean d;
      ItemStack e = ItemStack.EMPTY;
      int f;
      int g;

      b() {
      }
   }

   static record c(Object packet, long time)  {

}

   static record d(int entityId, int ownerId, int age)  {


}
}
