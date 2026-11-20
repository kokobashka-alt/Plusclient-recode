package dev.leg1tka.rotation.neural;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import it.unimi.dsi.fastutil.ints.Int2LongOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.longs.Long2LongOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.vehicle.AbstractBoatEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdatePlayerAbilitiesC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket.Handler;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionSyncS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRotationS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;

public final class RotationTrainingContext {
   public static int a;
   public static int b;
   public static int c;
   public static int d;
   public static int e;
   public static int f;
   private static int h;
   private static int i;
   private static int j;
   private static int k;
   private static int l;
   private static int m;
   private static int n;
   private static int o;
   private static int p;
   private static int q;
   private static int r;
   private static int s;
   private static int t;
   private static int u;
   private static int v;
   private static int w;
   private static int x;
   private static int y;
   private static int z;
   private static int A;
   private static int B;
   private static int C;
   private static int D;
   private static int E;
   private static int F;
   private static final double[] G = new double[]{1.62, 1.27, 0.4};
   private static double H;
   private static double I;
   private static double J;
   private static double K;
   private static float L;
   private static double M;
   private static double N;
   private static double O;
   private static double P;
   private static float Q;
   private static long R;
   private static double S;
   private static double T;
   private static double U;
   private static int V;
   private static int W;
   private static int X;
   private final ConcurrentLinkedQueue Y = new ConcurrentLinkedQueue();
   private final Int2ObjectOpenHashMap Z = new Int2ObjectOpenHashMap();
   private final Int2LongOpenHashMap ModuleStorage = new Int2LongOpenHashMap();
   private final Long2LongOpenHashMap ab = new Long2LongOpenHashMap();
   private final dev.leg1tka.rotation.neural.RotationDeltaHistory ac = new dev.leg1tka.rotation.neural.RotationDeltaHistory();
   public final dev.leg1tka.rotation.neural.RotationFeatureColumns g = new dev.leg1tka.rotation.neural.RotationFeatureColumns();
   private final double[] ad = new double[3];
   private final double[] ae = new double[3];
   private final double[] af = new double[3];
   private final double[] ag = new double[3];
   private final double[] ah = new double[2];
   private int Storage = -1;
   private double ModuleManager;
   private double ModuleRegistry;
   private double al;
   private float am;
   private float an;
   private float ao;
   private float ap;
   private boolean aq;
   private int ar = 1;
   private long as;
   private double at;
   private boolean au;
   private boolean av;
   private long aw;
   private double ax = 1000.0;
   private long ay;
   private long az;
   private boolean aA;
   private boolean aB;
   private boolean aC;
   private boolean aD;
   private boolean aE;
   private boolean aF;
   private boolean aG;
   private boolean aH;
   private boolean aI;
   private int aJ;
   private boolean aK;
   private boolean aL;
   private boolean aM;
   private boolean aN;
   private boolean aO;
   private boolean aP;
   private boolean aQ;
   private boolean aR;
   private int aS = 20;
   private float aT = 20.0F;
   private float aU;
   private int aV = 255;
   private float aW = 0.6F;
   private float aX;
   private int aY;
   private int aZ = -1;
   private int ba = -1;
   private int bb = 255;
   private int bc = 255;
   private float bd = 1.0F;
   private float be = 1.0F;
   private boolean bf;
   private boolean bg;
   private boolean bh;
   private boolean bi;
   private boolean bj;
   private float bk;
   private float bl;
   private float bm;
   private int bn = 255;
   private boolean bo;
   private int bp = 255;
   private float bq;
   private float br;
   private float bs;
   private int bt = 255;
   private int bu;
   private int bv;
   private int bw = -1;
   private int bx;
   private int by;
   private int bz;
   private int bA;
   private float bB = -1.0F;
   private float bC = -1.0F;
   private float bD = -1.0F;
   private boolean bE;
   private boolean bF;
   private int bG = -1;
   private int bH = 0;
   private int bI = -1;
   private int bJ = -1;
   private float bK = 1.0F;
   private float bL = 1.0F;
   private float bM = 1.0F;
   private boolean bN;
   private boolean bO;
   private boolean bP;
   private boolean bQ;
   private boolean bR;
   private boolean bS;
   private boolean bT;
   private float bU;
   private float bV;

   public RotationTrainingContext() {
   }

   private static MinecraftClient g() {
      return MinecraftClient.getInstance();
   }

   public long a() {
      return this.az;
   }

   public boolean b() {
      return this.bR;
   }

   public long c() {
      return this.as;
   }

   public int d() {
      return this.ar;
   }

   public int a(int[] intValueArray1, double[] doubleValueArray2, double doubleValue3) {
      int intValue5 = 0;
      ObjectIterator objectIterator6 = this.Z.values().iterator();

      while (objectIterator6.hasNext()) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value7 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)objectIterator6.next();
         if (intValue5 < intValueArray1.length && value7.b && value7.a()) {
            double doubleValue8 = value7.h - this.ModuleManager;
            double doubleValue10 = value7.i - this.ModuleRegistry;
            double doubleValue12 = value7.j - this.al;
            if (!(doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10 + doubleValue12 * doubleValue12 > doubleValue3)) {
               intValueArray1[intValue5] = value7.a;
               doubleValueArray2[intValue5 * 3] = value7.h;
               doubleValueArray2[intValue5 * 3 + 1] = value7.i;
               doubleValueArray2[intValue5 * 3 + 2] = value7.j;
               intValue5++;
            }
         }
      }

      return intValue5;
   }

   public void e() {
      ClientPlayerEntity clientPlayerEntity1 = g().player;
      ClientWorld clientWorld2 = g().world;
      this.Z.clear();
      if (clientPlayerEntity1 != null && clientWorld2 != null) {
         this.Storage = clientPlayerEntity1.getId();
         this.ModuleManager = clientPlayerEntity1.getX();
         this.ModuleRegistry = clientPlayerEntity1.getY();
         this.al = clientPlayerEntity1.getZ();
         this.am = this.ao = clientPlayerEntity1.getYaw();
         this.an = this.ap = clientPlayerEntity1.getPitch();
         this.aq = clientPlayerEntity1.isOnGround();
         this.aY = clientPlayerEntity1.getInventory().selectedSlot;
         this.aT = clientPlayerEntity1.getHealth();
         this.aS = clientPlayerEntity1.getHungerManager().getFoodLevel();
         this.aA = clientPlayerEntity1.isSprinting();
         this.aB = clientPlayerEntity1.isSneaking();
         this.aC = clientPlayerEntity1.getAbilities().flying;
         this.au = false;

         for (Entity entity4 : clientWorld2.getEntities()) {
            if (entity4 != clientPlayerEntity1) {
               Vec3d vec3d5 = entity4.getTrackedPosition().getPos();
               this.Z.put(entity4.getId(), this.a(entity4.getId(), entity4.getType(), entity4, vec3d5.x, vec3d5.y, vec3d5.z));
            }
         }
      }
   }

   public void a(Packet<?> packet1) {
      if (packet1 instanceof BundleS2CPacket bundleS2CPacket2) {
         for (Packet packet4 : bundleS2CPacket2.getPackets()) {
            this.a(packet4);
         }
      } else {
         if (packet1 instanceof EntitySpawnS2CPacket
            || packet1 instanceof EntitiesDestroyS2CPacket
            || packet1 instanceof EntityStatusS2CPacket
            || packet1 instanceof EntityS2CPacket
            || packet1 instanceof EntityPositionS2CPacket
            || packet1 instanceof EntityPositionSyncS2CPacket
            || packet1 instanceof EntityVelocityUpdateS2CPacket
            || packet1 instanceof ExplosionS2CPacket
            || packet1 instanceof HealthUpdateS2CPacket
            || packet1 instanceof GameJoinS2CPacket
            || packet1 instanceof PlayerRespawnS2CPacket
            || packet1 instanceof PlayerRotationS2CPacket) {
            this.Y.add(packet1);
         }
      }
   }

   public void f() {
      ObjectIterator objectIterator1 = this.Z.values().iterator();

      while (objectIterator1.hasNext()) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value2 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)objectIterator1.next();
         if (value2.n) {
            value2.c();
         }
      }

      this.h();
   }

   public boolean b(Packet<?> packet1) {
      if (packet1 instanceof PlayerMoveC2SPacket playerMoveC2SPacket10) {
         return this.a(playerMoveC2SPacket10);
      } else {
         if (packet1 instanceof PlayerInteractEntityC2SPacket playerInteractEntityC2SPacket2) {
            final boolean[] flagArray9 = new boolean[1];
            playerInteractEntityC2SPacket2.handle(new Handler() {
               public void interact(Hand hand) {
               }

               public void interactAt(Hand hand, Vec3d pos) {
               }

               public void attack() {
                  flagArray9[0] = true;
               }
            });
            if (flagArray9[0]) {
               this.b(playerInteractEntityC2SPacket2.entityId);
            }
         } else if (packet1 instanceof HandSwingC2SPacket handSwingC2SPacket3) {
            if (handSwingC2SPacket3.getHand() == Hand.MAIN_HAND) {
               this.bf = true;
               this.bc = 0;
            }
         } else if (packet1 instanceof PlayerInteractItemC2SPacket) {
            this.bt = 0;
         } else if (packet1 instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket4) {
            this.a(playerInteractBlockC2SPacket4);
         } else if (packet1 instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket5) {
            this.bc = 0;
            this.aY = updateSelectedSlotC2SPacket5.getSelectedSlot();
            this.bu++;
         } else if (packet1 instanceof ClientCommandC2SPacket clientCommandC2SPacket6) {
            switch (clientCommandC2SPacket6.getMode()) {
               case START_SPRINTING:
                  this.aA = true;
                  break;
               case STOP_SPRINTING:
                  this.aA = false;
                  break;
               case PRESS_SHIFT_KEY:
                  this.aB = true;
                  break;
               case RELEASE_SHIFT_KEY:
                  this.aB = false;
                  break;
               case START_FALL_FLYING:
                  this.aD = true;
            }
         } else if (packet1 instanceof UpdatePlayerAbilitiesC2SPacket updatePlayerAbilitiesC2SPacket7) {
            this.aC = updatePlayerAbilitiesC2SPacket7.isFlying();
         } else if (packet1 instanceof PlayerInputC2SPacket playerInputC2SPacket8) {
            this.aK = playerInputC2SPacket8.input().forward();
            this.aL = playerInputC2SPacket8.input().backward();
            this.aM = playerInputC2SPacket8.input().left();
            this.aN = playerInputC2SPacket8.input().right();
            this.aO = playerInputC2SPacket8.input().jump();
            this.aP = playerInputC2SPacket8.input().sneak();
            this.aQ = playerInputC2SPacket8.input().sprint();
            this.aR = true;
         } else if (packet1 instanceof TeleportConfirmC2SPacket) {
            this.bS = true;
         }

         return false;
      }
   }

   private void h() {
      Packet packet1;
      while ((packet1 = (Packet)this.Y.poll()) != null) {
         this.c(packet1);
      }
   }

   private void c(Packet<?> packet1) {
      ClientWorld clientWorld2 = g().world;
      boolean flag3 = g().player == null || !g().player.hasVehicle();
      if (packet1 instanceof EntitySpawnS2CPacket entitySpawnS2CPacket4) {
         int intValue15 = entitySpawnS2CPacket4.getEntityId();
         if (intValue15 < 0 || intValue15 == this.Storage) {
            return;
         }

         Entity entity16 = clientWorld2 == null ? null : clientWorld2.getEntityById(intValue15);
         this.Z.put(intValue15, this.a(intValue15, entitySpawnS2CPacket4.getEntityType(), entity16, entitySpawnS2CPacket4.getX(), entitySpawnS2CPacket4.getY(), entitySpawnS2CPacket4.getZ()));
         if (entitySpawnS2CPacket4.getEntityType() == EntityType.END_CRYSTAL) {
            if (this.ModuleStorage.size() >= 256) {
               this.ModuleStorage.clear();
            }

            this.ModuleStorage.put(intValue15, this.as);
         }
      } else if (packet1 instanceof EntitiesDestroyS2CPacket entitiesDestroyS2CPacket5) {
         IntListIterator intListIterator17 = entitiesDestroyS2CPacket5.getEntityIds().iterator();

         while (intListIterator17.hasNext()) {
            int intValue24 = (Integer)intListIterator17.next();
            this.ModuleStorage.remove(intValue24);
            this.Z.remove(intValue24);
         }
      } else if (packet1 instanceof EntityStatusS2CPacket entityStatusS2CPacket6) {
         if (entityStatusS2CPacket6.getStatus() != 3 || clientWorld2 == null) {
            return;
         }

         Entity entity18 = entityStatusS2CPacket6.getEntity(clientWorld2);
         if (entity18 == null) {
            return;
         }

         dev.leg1tka.rotation.neural.MotionFeatureExtractor value25 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(entity18.getId());
         if (value25 != null) {
            value25.k = true;
         }
      } else if (packet1 instanceof EntityS2CPacket entityS2CPacket7) {
         if (clientWorld2 == null) {
            return;
         }

         Entity entity19 = entityS2CPacket7.getEntity(clientWorld2);
         if (entity19 == null) {
            return;
         }

         dev.leg1tka.rotation.neural.MotionFeatureExtractor value26 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(entity19.getId());
         if (value26 != null) {
            value26.a(true, entityS2CPacket7.isPositionChanged(), (long)entityS2CPacket7.getDeltaX(), (long)entityS2CPacket7.getDeltaY(), (long)entityS2CPacket7.getDeltaZ(), 0.0, 0.0, 0.0, flag3);
         }
      } else if (packet1 instanceof EntityPositionS2CPacket entityPositionS2CPacket8) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value20 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(entityPositionS2CPacket8.entityId());
         Vec3d vec3d27 = entityPositionS2CPacket8.change().position();
         if (value20 != null) {
            value20.a(false, true, 0L, 0L, 0L, vec3d27.x, vec3d27.y, vec3d27.z, flag3);
         }
      } else if (packet1 instanceof EntityPositionSyncS2CPacket entityPositionSyncS2CPacket9) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value21 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(entityPositionSyncS2CPacket9.id());
         Vec3d vec3d28 = entityPositionSyncS2CPacket9.values().position();
         if (value21 != null) {
            value21.a(false, true, 0L, 0L, 0L, vec3d28.x, vec3d28.y, vec3d28.z, flag3);
         }
      } else if (packet1 instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket10) {
         if (entityVelocityUpdateS2CPacket10.getEntityId() != this.Storage) {
            return;
         }

         this.bj = true;
         this.bk = (float)entityVelocityUpdateS2CPacket10.getVelocityX();
         this.bl = (float)entityVelocityUpdateS2CPacket10.getVelocityY();
         this.bm = (float)entityVelocityUpdateS2CPacket10.getVelocityZ();
         this.bn = 0;
      } else if (packet1 instanceof ExplosionS2CPacket explosionS2CPacket11) {
         if (explosionS2CPacket11.playerKnockback().isEmpty()) {
            return;
         }

         Vec3d vec3d22 = (Vec3d)explosionS2CPacket11.playerKnockback().get();
         if (vec3d22.x == 0.0 && vec3d22.y == 0.0 && vec3d22.z == 0.0) {
            return;
         }

         this.bo = true;
         this.bp = 0;
         this.bq = (float)vec3d22.x;
         this.br = (float)vec3d22.y;
         this.bs = (float)vec3d22.z;
      } else if (packet1 instanceof HealthUpdateS2CPacket healthUpdateS2CPacket12) {
         float floatValue23 = this.aT - healthUpdateS2CPacket12.getHealth();
         if (floatValue23 > 0.0F) {
            this.aU = floatValue23;
            this.aV = 0;
         }

         this.aT = healthUpdateS2CPacket12.getHealth();
         this.aS = healthUpdateS2CPacket12.getFood();
      } else if (packet1 instanceof GameJoinS2CPacket gameJoinS2CPacket13) {
         this.k();
         this.Storage = gameJoinS2CPacket13.playerEntityId();
         this.Z.clear();
         this.ModuleStorage.clear();
      } else if (packet1 instanceof PlayerRespawnS2CPacket) {
         this.k();
         this.Z.clear();
         this.ModuleStorage.clear();
      } else if (packet1 instanceof PlayerRotationS2CPacket playerRotationS2CPacket14) {
         this.bT = true;
         this.bU = playerRotationS2CPacket14.yRot();
         this.bV = playerRotationS2CPacket14.xRot();
      }
   }

   private dev.leg1tka.rotation.neural.MotionFeatureExtractor a(int intValue1) {
      ClientWorld clientWorld2 = g().world;
      Entity entity3 = clientWorld2 == null ? null : clientWorld2.getEntityById(intValue1);
      if (entity3 == null) {
         return null;
      } else {
         Vec3d vec3d4 = entity3.getTrackedPosition().getPos();
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value5 = this.a(intValue1, entity3.getType(), entity3, vec3d4.x, vec3d4.y, vec3d4.z);
         this.Z.put(intValue1, value5);
         return value5;
      }
   }

   private dev.leg1tka.rotation.neural.MotionFeatureExtractor a(int intValue1, EntityType<?> entityType2, Entity entity3, double doubleValue4, double doubleValue6, double doubleValue8) {
      boolean flag10 = entityType2 == EntityType.PLAYER;
      boolean flag11 = entity3 != null ? entity3 instanceof LivingEntity : flag10 || entityType2 == EntityType.ARMOR_STAND || entityType2.getSpawnGroup() != SpawnGroup.MISC;
      boolean flag12 = entity3 instanceof AbstractBoatEntity;
      boolean flag13 = entity3 instanceof AbstractMinecartEntity;
      dev.leg1tka.rotation.neural.MotionFeatureExtractor value14 = new dev.leg1tka.rotation.neural.MotionFeatureExtractor(intValue1, flag10, flag11, flag12, flag13, entityType2 == EntityType.SHULKER, entityType2 == EntityType.END_CRYSTAL, doubleValue4, doubleValue6, doubleValue8);
      if (entity3 != null) {
         this.a(value14, entity3);
      }

      return value14;
   }

   private boolean a(PlayerMoveC2SPacket playerMoveC2SPacket1) {
      this.h();
      this.as++;
      boolean flag2 = playerMoveC2SPacket1.changesPosition();
      boolean flag3 = playerMoveC2SPacket1.changesLook();
      boolean flag4 = this.bS && flag2 && flag3;
      if (flag4) {
         this.bS = false;
      }

      boolean flag5 = !flag4 && this.bT && flag3 && !flag2 && playerMoveC2SPacket1.getYaw(this.am) == this.bU && playerMoveC2SPacket1.getPitch(this.an) == this.bV;
      if (flag5) {
         this.bT = false;
      }

      if (flag4 || flag5) {
         this.au = false;
      }

      if (!flag4 && !flag5) {
         if (flag2) {
            this.ModuleManager = playerMoveC2SPacket1.getX(this.ModuleManager);
            this.ModuleRegistry = playerMoveC2SPacket1.getY(this.ModuleRegistry);
            this.al = playerMoveC2SPacket1.getZ(this.al);
         }

         if (flag3) {
            this.av = true;
            float floatValue6 = playerMoveC2SPacket1.getYaw(this.am);
            float floatValue7 = playerMoveC2SPacket1.getPitch(this.an);
            this.ac.a(this.am, this.an, floatValue6, floatValue7);
            this.ao = this.am;
            this.ap = this.an;
            this.am = floatValue6;
            this.an = floatValue7;
         }
      }

      this.i();
      this.j();
      if (!flag4) {
         ObjectIterator objectIterator8 = this.Z.values().iterator();

         while (objectIterator8.hasNext()) {
            dev.leg1tka.rotation.neural.MotionFeatureExtractor value10 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)objectIterator8.next();
            value10.a(false);
         }

         this.aq = playerMoveC2SPacket1.isOnGround();
      }

      if (flag4 || flag5) {
         if (flag2) {
            this.ModuleManager = playerMoveC2SPacket1.getX(this.ModuleManager);
            this.ModuleRegistry = playerMoveC2SPacket1.getY(this.ModuleRegistry);
            this.al = playerMoveC2SPacket1.getZ(this.al);
         }

         if (flag3) {
            this.am = playerMoveC2SPacket1.getYaw(this.am);
            this.an = playerMoveC2SPacket1.getPitch(this.an);
            this.ao = this.am;
            this.ap = this.an;
         }
      }

      boolean flag9 = this.a(System.nanoTime()) || this.bh;
      if (!flag9) {
         this.az++;
      } else {
         this.m();
      }

      this.l();
      return flag9;
   }

   private void i() {
      ClientPlayerEntity clientPlayerEntity1 = g().player;
      if (clientPlayerEntity1 != null) {
         this.aD = clientPlayerEntity1.isGliding();
         this.aE = clientPlayerEntity1.isSwimming();
         this.aI = clientPlayerEntity1.isUsingRiptide();
         this.aF = clientPlayerEntity1.hasVehicle();
      }
   }

   private void j() {
      ClientWorld clientWorld1 = g().world;
      if (clientWorld1 != null) {
         int intValue2 = MathHelper.floor(this.ModuleManager);
         int intValue3 = MathHelper.floor(this.al);
         int intValue4 = MathHelper.floor(this.ModuleRegistry - 0.5000001);
         int intValue5 = MathHelper.floor(this.ModuleRegistry);
         int intValue6 = MathHelper.floor(this.ModuleRegistry + 0.5);
         int intValue7 = MathHelper.floor(this.ModuleRegistry + 1.6);
         BlockState blockState8 = clientWorld1.getBlockState(new BlockPos(intValue2, intValue4, intValue3));
         this.aW = a(blockState8);
         this.bN = blockState8.isOf(Blocks.SLIME_BLOCK);
         this.bO = blockState8.isOf(Blocks.HONEY_BLOCK);
         this.bP = blockState8.isOf(Blocks.SOUL_SAND);
         this.bQ = blockState8.isOf(Blocks.MUD);
         BlockState blockState9 = clientWorld1.getBlockState(new BlockPos(intValue2, intValue5, intValue3));
         this.aG = b(blockState9);
         this.aH = blockState9.getFluidState().isIn(FluidTags.WATER);
         this.aJ = this.aD
            ? EntityPose.GLIDING.ordinal()
            : (
               this.aE
                  ? EntityPose.SWIMMING.ordinal()
                  : (this.aI ? EntityPose.SPIN_ATTACK.ordinal() : (this.aB ? EntityPose.CROUCHING.ordinal() : EntityPose.STANDING.ordinal()))
            );
         float floatValue10 = 1.0F;
         float floatValue11 = 1.0F;
         float floatValue12 = 1.0F;
         int[] intValueArray13 = new int[]{intValue5, intValue6, intValue7};

         for (int intValue17 : intValueArray13) {
            BlockState blockState18 = clientWorld1.getBlockState(new BlockPos(intValue2, intValue17, intValue3));
            float[] floatValueArray19 = c(blockState18);
            if (floatValueArray19 != null) {
               floatValue10 = a(floatValue10, floatValueArray19[0]);
               floatValue11 = a(floatValue11, floatValueArray19[1]);
               floatValue12 = a(floatValue12, floatValueArray19[2]);
            }
         }

         this.bK = floatValue10;
         this.bL = floatValue11;
         this.bM = floatValue12;
      }
   }

   private static float a(BlockState blockState0) {
      if (blockState0.isOf(Blocks.ICE) || blockState0.isOf(Blocks.PACKED_ICE) || blockState0.isOf(Blocks.FROSTED_ICE)) {
         return 0.98F;
      } else if (blockState0.isOf(Blocks.BLUE_ICE)) {
         return 0.989F;
      } else {
         return !blockState0.isOf(Blocks.SLIME_BLOCK) && !blockState0.isOf(Blocks.HONEY_BLOCK) ? 0.6F : 0.8F;
      }
   }

   private static boolean b(BlockState blockState0) {
      return blockState0.isOf(Blocks.LADDER)
         || blockState0.isOf(Blocks.VINE)
         || blockState0.isOf(Blocks.TWISTING_VINES)
         || blockState0.isOf(Blocks.TWISTING_VINES_PLANT)
         || blockState0.isOf(Blocks.WEEPING_VINES)
         || blockState0.isOf(Blocks.WEEPING_VINES_PLANT)
         || blockState0.isOf(Blocks.CAVE_VINES)
         || blockState0.isOf(Blocks.CAVE_VINES_PLANT)
         || blockState0.isOf(Blocks.SCAFFOLDING);
   }

   private static float[] c(BlockState blockState0) {
      if (blockState0.isOf(Blocks.COBWEB)) {
         return new float[]{0.25F, 0.05F, 0.25F};
      } else if (blockState0.isOf(Blocks.SWEET_BERRY_BUSH)) {
         return new float[]{0.8F, 0.75F, 0.8F};
      } else {
         return blockState0.isOf(Blocks.POWDER_SNOW) ? new float[]{0.9F, 1.5F, 0.9F} : null;
      }
   }

   private static float a(float floatValue0, float floatValue1) {
      return Math.abs(floatValue1 - 1.0F) > Math.abs(floatValue0 - 1.0F) ? floatValue1 : floatValue0;
   }

   private void b(int intValue1) {
      if (intValue1 != this.Storage) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value2 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(intValue1);
         if (value2 == null) {
            value2 = this.a(intValue1);
         }

         this.bG = this.c(intValue1);
         this.bH = a(value2);
         if (value2 != null && value2.b) {
            this.bg = true;
            this.ba = this.aZ;
            this.aZ = intValue1;
            this.bi = this.aA;
            float floatValue3 = 20.0F / this.p();
            this.bd = MathHelper.clamp(((float)this.bc + 0.5F) / floatValue3, 0.0F, 1.0F);
            this.bb = 0;
            this.bc = 0;
            this.bh = true;
         }
      }
   }

   private int c(int intValue1) {
      if (!this.ModuleStorage.containsKey(intValue1)) {
         return -1;
      } else {
         long longValue2 = this.as - this.ModuleStorage.get(intValue1);
         return (int)Math.max(-1024L, Math.min(1024L, longValue2));
      }
   }

   private static int a(dev.leg1tka.rotation.neural.MotionFeatureExtractor value0) {
      if (value0 == null) {
         return 5;
      } else if (value0.b) {
         return 0;
      } else if (value0.g) {
         return 2;
      } else if (value0.c) {
         return 1;
      } else {
         return !value0.d && !value0.e ? 4 : 3;
      }
   }

   private void a(PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket1) {
      this.bt = 0;
      ClientWorld clientWorld2 = g().world;
      BlockHitResult blockHitResult3 = playerInteractBlockC2SPacket1.getBlockHitResult();
      BlockPos blockPos4 = blockHitResult3.getBlockPos();
      BlockState blockState5 = clientWorld2 == null ? null : clientWorld2.getBlockState(blockPos4);
      byte byteValue6;
      if (blockState5 == null || !clientWorld2.isChunkLoaded(blockPos4)) {
         byteValue6 = 5;
      } else if (blockState5.isOf(Blocks.OBSIDIAN) || blockState5.isOf(Blocks.CRYING_OBSIDIAN)) {
         byteValue6 = 1;
      } else if (blockState5.isOf(Blocks.BEDROCK)) {
         byteValue6 = 2;
      } else if (blockState5.isOf(Blocks.RESPAWN_ANCHOR)) {
         byteValue6 = 3;
      } else {
         byteValue6 = 4;
      }

      this.bv++;
      if (playerInteractBlockC2SPacket1.getHand() == Hand.OFF_HAND) {
         this.bF = true;
      }

      this.bw = blockHitResult3.getSide().getId();
      this.bx = byteValue6;
      this.by = blockPos4.getX();
      this.bz = blockPos4.getY();
      this.bA = blockPos4.getZ();
      this.bB = (float)(blockHitResult3.getPos().x - (double)blockPos4.getX());
      this.bC = (float)(blockHitResult3.getPos().y - (double)blockPos4.getY());
      this.bD = (float)(blockHitResult3.getPos().z - (double)blockPos4.getZ());
      this.bE = blockHitResult3.isInsideBlock();
      if (byteValue6 == 3) {
         this.bI = (Integer)blockState5.get(RespawnAnchorBlock.CHARGES);
         this.bJ = this.a(blockPos4);
      }
   }

   private int a(BlockPos blockPos1) {
      long longValue2 = ((long)blockPos1.getX() & 67108863L) << 38 | ((long)blockPos1.getZ() & 67108863L) << 12 | (long)blockPos1.getY() & 4095L;
      long longValue4 = this.ab.containsKey(longValue2) ? this.ab.get(longValue2) : -1L;
      if (this.ab.size() >= 256) {
         this.ab.clear();
      }

      this.ab.put(longValue2, this.as);
      return longValue4 == -1L ? -1 : (int)Math.max(0L, Math.min(1024L, this.as - longValue4));
   }

   private boolean a(long longValue1) {
      if (this.ay != 0L) {
         double doubleValue3 = (double)(longValue1 - this.ay) / 1000000.0;
         this.ax = Math.min(this.ax + doubleValue3, 1000.0);
      }

      this.ay = longValue1;
      if (this.ax < 50.0) {
         return false;
      } else {
         this.ax -= 50.0;
         return true;
      }
   }

   private void k() {
      this.ar++;
      this.aZ = -1;
      this.ba = -1;
      this.au = false;
      this.aw = 0L;
      this.aX = 0.0F;
      this.aA = false;
      this.aB = false;
      this.aD = false;
      this.aE = false;
      this.aF = false;
      this.aG = false;
      this.aH = false;
      this.bb = 255;
      this.bc = 255;
      this.aT = 20.0F;
      this.aV = 255;
      this.aU = 0.0F;
      this.bf = false;
      this.bg = false;
      this.bh = false;
      this.bj = false;
      this.bo = false;
      this.bi = false;
      this.aK = this.aL = this.aM = this.aN = this.aO = this.aP = this.aQ = false;
      this.aR = false;
   }

   private void l() {
      if (this.bb < 255) {
         this.bb++;
      }

      if (this.bc < 255) {
         this.bc++;
      }

      if (this.bn < 255) {
         this.bn++;
      }

      if (this.bp < 255) {
         this.bp++;
      }

      if (this.bt < 255) {
         this.bt++;
      }

      if (this.aV < 255) {
         this.aV++;
      }

      this.bf = false;
      this.bg = false;
      this.bh = false;
      this.aU = 0.0F;
      this.bj = false;
      this.bo = false;
      this.bi = false;
      this.av = false;
      this.bu = 0;
      this.bv = 0;
      this.bw = -1;
      this.bx = 0;
      this.by = this.bz = this.bA = 0;
      this.bB = this.bC = this.bD = -1.0F;
      this.bE = false;
      this.bG = -1;
      this.bF = false;
      this.bH = 0;
      this.bI = -1;
      this.bJ = -1;
   }

   private void m() {
      ClientPlayerEntity clientPlayerEntity1 = g().player;
      double[] doubleValueArray2 = this.g.bH;
      doubleValueArray2[0] = (double)this.ar;
      doubleValueArray2[1] = (double)((int)this.as);
      long longValue3 = System.currentTimeMillis();
      doubleValueArray2[2] = this.aw == 0L ? 0.0 : (double)((float)Math.max(0L, Math.min(10000L, longValue3 - this.aw)));
      this.aw = longValue3;
      doubleValueArray2[3] = (double)this.aZ;
      doubleValueArray2[4] = (double)this.am;
      doubleValueArray2[5] = (double)this.an;
      doubleValueArray2[6] = this.ModuleManager;
      doubleValueArray2[7] = this.ModuleRegistry;
      doubleValueArray2[8] = this.al;
      this.g.a(94, !this.au);
      double doubleValue5;
      if (this.au) {
         doubleValue5 = this.ModuleRegistry - this.at;
      } else {
         doubleValue5 = 0.0;
         this.au = true;
         this.aX = 0.0F;
         this.ac.e();
      }

      this.at = this.ModuleRegistry;
      this.g.a(9, this.aq);
      this.g.a(10, this.aA);
      this.g.a(11, this.aB);
      this.g.a(13, this.aC);
      this.g.a(14, this.aE);
      this.g.a(15, this.aD);
      this.g.a(16, this.aF);
      this.g.a(17, this.aG);
      this.g.a(18, this.aH);
      doubleValueArray2[12] = (double)a((PlayerEntity)clientPlayerEntity1);
      doubleValueArray2[19] = (double)this.aJ;
      this.g.a(20, this.aK);
      this.g.a(21, this.aL);
      this.g.a(22, this.aM);
      this.g.a(23, this.aN);
      this.g.a(24, this.aO);
      this.g.a(25, this.aP);
      this.g.a(26, this.aQ);
      this.g.a(59, this.aR);
      this.g.a(76, this.av);
      doubleValueArray2[27] = g().interactionManager == null ? 0.0 : (double)g().interactionManager.getCurrentGameMode().getId();
      doubleValueArray2[28] = (double)this.aS;
      doubleValueArray2[97] = (double)this.aT;
      doubleValueArray2[98] = (double)this.aU;
      doubleValueArray2[99] = (double)this.aV;
      doubleValueArray2[29] = (double)this.aW;
      float floatValue7 = 0.1F;
      float floatValue8 = 3.0F;
      float floatValue9 = 1.0F;
      float floatValue10 = 0.08F;
      float floatValue11 = 0.42F;
      float floatValue12 = 0.6F;
      float floatValue13 = 0.0F;
      int intValue14 = -1;
      int intValue15 = -1;
      int intValue16 = -1;
      int intValue17 = -1;
      int intValue18 = -1;
      int intValue19 = -1;
      boolean flag20 = false;
      boolean flag21 = false;
      boolean flag22 = false;
      if (clientPlayerEntity1 != null) {
         double doubleValue23 = clientPlayerEntity1.getAttributeValue(EntityAttributes.MOVEMENT_SPEED);
         floatValue7 = (float)(clientPlayerEntity1.isSprinting() ? doubleValue23 / 1.3 : doubleValue23);
         floatValue8 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.ENTITY_INTERACTION_RANGE);
         floatValue9 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.SCALE);
         floatValue10 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.GRAVITY);
         floatValue11 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.JUMP_STRENGTH);
         floatValue12 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.STEP_HEIGHT);
         floatValue13 = (float)clientPlayerEntity1.getAttributeValue(EntityAttributes.WATER_MOVEMENT_EFFICIENCY);
         intValue14 = a(clientPlayerEntity1, StatusEffects.JUMP_BOOST);
         intValue15 = a(clientPlayerEntity1, StatusEffects.SPEED);
         intValue16 = a(clientPlayerEntity1, StatusEffects.SLOWNESS);
         intValue17 = a(clientPlayerEntity1, StatusEffects.HASTE);
         intValue18 = a(clientPlayerEntity1, StatusEffects.MINING_FATIGUE);
         intValue19 = a(clientPlayerEntity1, StatusEffects.LEVITATION);
         flag20 = clientPlayerEntity1.hasStatusEffect(StatusEffects.SLOW_FALLING);
         flag21 = clientPlayerEntity1.hasStatusEffect(StatusEffects.BLINDNESS);
         flag22 = clientPlayerEntity1.hasStatusEffect(StatusEffects.DOLPHINS_GRACE);
      }

      doubleValueArray2[31] = (double)floatValue7;
      this.a(doubleValue5, flag20, intValue19);
      doubleValueArray2[30] = (double)this.aX;
      doubleValueArray2[32] = (double)intValue14;
      this.g.a(33, flag20);
      doubleValueArray2[34] = (double)this.a(clientPlayerEntity1);
      doubleValueArray2[35] = (double)intValue15;
      doubleValueArray2[36] = (double)intValue16;
      doubleValueArray2[37] = (double)intValue17;
      doubleValueArray2[38] = (double)intValue18;
      doubleValueArray2[39] = (double)floatValue8;
      doubleValueArray2[40] = (double)floatValue9;
      doubleValueArray2[41] = (double)floatValue10;
      doubleValueArray2[42] = (double)floatValue11;
      doubleValueArray2[43] = (double)floatValue12;
      float floatValue41 = this.p();
      this.be = this.bg ? this.bd : MathHelper.clamp(((float)this.bc + 0.5F) / (20.0F / floatValue41), 0.0F, 1.0F);
      doubleValueArray2[65] = (double)this.be;
      this.a(floatValue8, flag21);
      this.g.a(51, this.bg);
      doubleValueArray2[63] = (double)this.bb;
      this.g.a(64, this.bf);
      this.g.a(67, this.bi);
      doubleValueArray2[66] = (double)floatValue41;
      this.g.a(68, this.bj);
      doubleValueArray2[69] = this.bj ? (double)this.bk : 0.0;
      doubleValueArray2[70] = this.bj ? (double)this.bl : 0.0;
      doubleValueArray2[71] = this.bj ? (double)this.bm : 0.0;
      doubleValueArray2[72] = (double)this.bn;
      this.g.a(73, this.bo);
      doubleValueArray2[74] = (double)this.bp;
      doubleValueArray2[75] = (double)this.bt;
      boolean flag24 = this.ac.a();
      boolean flag25 = this.ac.b();
      doubleValueArray2[77] = (double)((float)this.ac.a);
      doubleValueArray2[78] = (double)((float)this.ac.b);
      this.g.a(79, flag24);
      this.g.a(80, flag25);
      float floatValue26 = this.ac.c();
      float floatValue27 = this.ac.d();
      doubleValueArray2[81] = flag24 ? (double)floatValue26 : 0.0;
      doubleValueArray2[82] = flag25 ? (double)floatValue27 : 0.0;
      doubleValueArray2[83] = (double)this.b(clientPlayerEntity1);
      doubleValueArray2[84] = (double)this.bK;
      doubleValueArray2[85] = (double)this.bL;
      doubleValueArray2[86] = (double)this.bM;
      this.g.a(87, this.bN);
      this.g.a(88, this.bO);
      this.g.a(89, this.bP);
      this.g.a(90, this.bQ);
      doubleValueArray2[91] = (double)intValue19;
      doubleValueArray2[92] = (double)this.b(floatValue13, flag22);
      this.g.a(93, this.aI);
      doubleValueArray2[100] = 0.0;
      doubleValueArray2[50] = 0.0;
      doubleValueArray2[102] = (double)this.bG;
      boolean flag28 = clientPlayerEntity1 != null && clientPlayerEntity1.isUsingItem() && clientPlayerEntity1.getActiveHand() == Hand.OFF_HAND;
      this.g.a(103, this.bF || flag28);
      doubleValueArray2[105] = (double)this.bI;
      doubleValueArray2[106] = (double)this.bJ;
      doubleValueArray2[107] = (double)this.bw;
      doubleValueArray2[108] = (double)this.bx;
      doubleValueArray2[109] = (double)this.by;
      doubleValueArray2[110] = (double)this.bz;
      doubleValueArray2[111] = (double)this.bA;
      doubleValueArray2[112] = (double)this.bB;
      doubleValueArray2[113] = (double)this.bC;
      doubleValueArray2[114] = (double)this.bD;
      this.g.a(115, this.bE);
      doubleValueArray2[116] = (double)this.bv;
      doubleValueArray2[101] = (double)this.bH;
      doubleValueArray2[117] = clientPlayerEntity1 == null ? 0.0 : (double)a(clientPlayerEntity1.getInventory().getStack(this.aY).getItem());
      doubleValueArray2[118] = clientPlayerEntity1 == null ? 0.0 : (double)a(clientPlayerEntity1.getOffHandStack().getItem());
      doubleValueArray2[119] = (double)this.bu;
      doubleValueArray2[120] = this.bo ? (double)this.bq : 0.0;
      doubleValueArray2[121] = this.bo ? (double)this.br : 0.0;
      doubleValueArray2[122] = this.bo ? (double)this.bs : 0.0;
      int intValue29 = 0;
      int intValue30 = 0;
      ObjectIterator objectIterator31 = this.Z.values().iterator();

      while (objectIterator31.hasNext()) {
         dev.leg1tka.rotation.neural.MotionFeatureExtractor value32 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)objectIterator31.next();
         if (value32.a()) {
            double doubleValue33 = value32.h - this.ModuleManager;
            double doubleValue35 = value32.i - this.ModuleRegistry;
            double doubleValue37 = value32.j - this.al;
            double doubleValue39 = doubleValue33 * doubleValue33 + doubleValue35 * doubleValue35 + doubleValue37 * doubleValue37;
            if (doubleValue39 <= 36.0) {
               intValue30++;
            }

            if (value32.g && doubleValue39 <= 64.0) {
               intValue29++;
            }
         }
      }

      doubleValueArray2[104] = (double)intValue29;
      doubleValueArray2[123] = (double)intValue30;
   }

   private void a(float floatValue1, boolean flag2) {
      double[] doubleValueArray3 = this.g.bH;
      dev.leg1tka.rotation.neural.MotionFeatureExtractor value4 = this.aZ >= 0 ? (dev.leg1tka.rotation.neural.MotionFeatureExtractor)this.Z.get(this.aZ) : null;
      this.bR = false;
      if (value4 != null && value4.a()) {
         this.a(value4, g().world == null ? null : g().world.getEntityById(value4.a));
         this.o();
         this.g.a(44, true);
         this.g.a(53, this.aZ == this.ba);
         dev.leg1tka.rotation.neural.MotionFeatureVector value5 = value4.d();
         doubleValueArray3[45] = (double)((float)this.a(value5));
         doubleValueArray3[46] = (double)((float)this.b(value5));
         doubleValueArray3[47] = (double)((float)(value4.h - this.ModuleManager));
         doubleValueArray3[48] = (double)((float)(value4.i - this.ModuleRegistry));
         doubleValueArray3[49] = (double)((float)(value4.j - this.al));
         dev.leg1tka.rotation.neural.MotionFeatureVector value6 = value4.e();
         this.bR = value6.c(this.ModuleManager, this.ModuleRegistry + 1.62, this.al)
            || this.a(value6, this.ModuleManager, this.ModuleRegistry + 1.62, this.al, this.ModuleManager + this.ad[0] * 6.0, this.ModuleRegistry + 1.62 + this.ae[0] * 6.0, this.al + this.af[0] * 6.0);
         doubleValueArray3[96] = (double)Math.max(0.0F, (float)a(value5, value6));
         doubleValueArray3[52] = (double)((float)this.c(value6));
         if (this.bg) {
            double doubleValue7 = Math.max((double)floatValue1 + 3.0, 6.0);
            this.g.a(56, this.a(value5, doubleValue7));
            boolean flag9 = this.a(value6, doubleValue7);
            double doubleValue10 = this.ag[0];
            double doubleValue12 = this.ag[1];
            double doubleValue14 = this.ag[2];
            this.g.a(127, flag9);
            doubleValueArray3[126] = (double)this.b(value5, doubleValue7);
            this.d(value6);
            doubleValueArray3[124] = (double)((float)this.ah[0]);
            doubleValueArray3[125] = (double)((float)this.ah[1]);
            doubleValueArray3[58] = flag9 ? 0.0 : (double)((float)this.c(value6, doubleValue7));
            if (flag9) {
               doubleValueArray3[60] = (double)((float)(doubleValue10 - value6.b()));
               doubleValueArray3[61] = (double)((float)(doubleValue12 - value6.c()));
               doubleValueArray3[62] = (double)((float)(doubleValue14 - value6.d()));
            } else {
               doubleValueArray3[60] = 0.0;
               doubleValueArray3[61] = 0.0;
               doubleValueArray3[62] = 0.0;
            }

            this.g.a(55, this.a(flag2));
            this.g.a(57, this.a(this.ModuleManager, this.ModuleRegistry + 1.62, this.al, value5.b(), value5.c(), value5.d()));
            doubleValueArray3[95] = (double)this.e(value6);
         } else {
            this.n();
         }

         int intValue16 = 0;
         ObjectIterator objectIterator8 = this.Z.values().iterator();

         while (objectIterator8.hasNext()) {
            dev.leg1tka.rotation.neural.MotionFeatureExtractor value17 = (dev.leg1tka.rotation.neural.MotionFeatureExtractor)objectIterator8.next();
            if (value17.c && value17.a()) {
               double doubleValue18 = value17.h - this.ModuleManager;
               double doubleValue19 = value17.i - this.ModuleRegistry;
               double doubleValue20 = value17.j - this.al;
               if (doubleValue18 * doubleValue18 + doubleValue19 * doubleValue19 + doubleValue20 * doubleValue20 <= 36.0) {
                  intValue16++;
               }
            }
         }

         doubleValueArray3[54] = (double)intValue16;
      } else {
         this.g.a(44, false);
         this.g.a(53, false);
         doubleValueArray3[45] = 0.0;
         doubleValueArray3[46] = 0.0;
         doubleValueArray3[47] = 0.0;
         doubleValueArray3[48] = 0.0;
         doubleValueArray3[49] = 0.0;
         this.n();
         doubleValueArray3[96] = 0.0;
         doubleValueArray3[52] = 0.0;
         doubleValueArray3[54] = 0.0;
      }
   }

   private void n() {
      double[] doubleValueArray1 = this.g.bH;
      this.g.a(56, false);
      doubleValueArray1[58] = 0.0;
      doubleValueArray1[60] = 0.0;
      doubleValueArray1[61] = 0.0;
      doubleValueArray1[62] = 0.0;
      this.g.a(55, false);
      this.g.a(57, false);
      doubleValueArray1[95] = 0.0;
      this.g.a(127, false);
      doubleValueArray1[126] = 0.0;
      doubleValueArray1[124] = 0.0;
      doubleValueArray1[125] = 0.0;
   }

   private void a(dev.leg1tka.rotation.neural.MotionFeatureExtractor value1, Entity entity2) {
      if (entity2 != null) {
         if (value1.b && entity2 instanceof LivingEntity livingEntity3) {
            float floatValue4 = livingEntity3.getScale();
            value1.l = 0.6F * floatValue4;
            value1.m = a(entity2.getPose()) * floatValue4;
         } else {
            value1.l = entity2.getWidth();
            value1.m = entity2.getHeight();
         }
      }
   }

   private static float a(EntityPose entityPose0) {
      return switch (entityPose0) {
         case SLEEPING -> 0.2F;
         case GLIDING, SWIMMING, SPIN_ATTACK -> 0.6F;
         case CROUCHING -> 1.5F;
         default -> 1.8F;
      };
   }

   private void o() {
      this.a(0, this.am, this.an);
      this.a(1, this.ao, this.an);
      this.a(2, this.ao, this.ap);
   }

   private void a(int intValue1, float floatValue2, float floatValue3) {
      float floatValue4 = floatValue3 * (float) (Math.PI / 180.0);
      float floatValue5 = -floatValue2 * (float) (Math.PI / 180.0);
      float floatValue6 = MathHelper.cos(floatValue4);
      double doubleValue7 = (double)(MathHelper.sin(floatValue5) * floatValue6);
      double doubleValue9 = (double)(-MathHelper.sin(floatValue4));
      double doubleValue11 = (double)(MathHelper.cos(floatValue5) * floatValue6);
      double doubleValue13 = Math.sqrt(doubleValue7 * doubleValue7 + doubleValue9 * doubleValue9 + doubleValue11 * doubleValue11);
      if (doubleValue13 != 0.0) {
         doubleValue7 /= doubleValue13;
         doubleValue9 /= doubleValue13;
         doubleValue11 /= doubleValue13;
      }

      this.ad[intValue1] = doubleValue7;
      this.ae[intValue1] = doubleValue9;
      this.af[intValue1] = doubleValue11;
   }

   private double a(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      double doubleValue2 = Double.MAX_VALUE;

      for (double doubleValue7 : G) {
         double doubleValue9 = this.ModuleRegistry + doubleValue7;
         double doubleValue11 = MathHelper.clamp(this.ModuleManager, value1.a, value1.d);
         double doubleValue13 = MathHelper.clamp(doubleValue9, value1.b, value1.e);
         double doubleValue15 = MathHelper.clamp(this.al, value1.c, value1.f);
         double doubleValue17 = doubleValue11 - this.ModuleManager;
         double doubleValue19 = doubleValue13 - doubleValue9;
         double doubleValue21 = doubleValue15 - this.al;
         doubleValue2 = Math.min(doubleValue2, Math.sqrt(doubleValue17 * doubleValue17 + doubleValue19 * doubleValue19 + doubleValue21 * doubleValue21));
      }

      return doubleValue2;
   }

   private double b(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      double doubleValue2 = Double.MAX_VALUE;

      for (int intValue4 = 0; intValue4 < 3; intValue4++) {
         for (double doubleValue8 : G) {
            double doubleValue10 = this.ModuleManager;
            double doubleValue12 = this.ModuleRegistry + doubleValue8;
            double doubleValue14 = this.al;

            for (int intValue16 = 0; intValue16 < 9; intValue16++) {
               double doubleValue17;
               double doubleValue19;
               double doubleValue21;
               if (intValue16 == 0) {
                  doubleValue17 = value1.b();
                  doubleValue19 = value1.c();
                  doubleValue21 = value1.d();
               } else {
                  int intValue23 = intValue16 - 1;
                  doubleValue17 = (intValue23 & 1) == 0 ? value1.a : value1.d;
                  doubleValue19 = (intValue23 & 2) == 0 ? value1.b : value1.e;
                  doubleValue21 = (intValue23 & 4) == 0 ? value1.c : value1.f;
               }

               double doubleValue33 = doubleValue17 - doubleValue10;
               double doubleValue25 = doubleValue19 - doubleValue12;
               double doubleValue27 = doubleValue21 - doubleValue14;
               double doubleValue29 = Math.sqrt(doubleValue33 * doubleValue33 + doubleValue25 * doubleValue25 + doubleValue27 * doubleValue27);
               if (doubleValue29 < 0.001) {
                  return 0.0;
               }

               double doubleValue31 = this.ad[intValue4] * (doubleValue33 / doubleValue29) + this.ae[intValue4] * (doubleValue25 / doubleValue29) + this.af[intValue4] * (doubleValue27 / doubleValue29);
               doubleValue2 = Math.min(doubleValue2, Math.toDegrees(Math.acos(MathHelper.clamp(doubleValue31, -1.0, 1.0))));
            }
         }
      }

      return doubleValue2;
   }

   private double c(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      double doubleValue2 = value1.b();
      double doubleValue4 = value1.c();
      double doubleValue6 = value1.d();
      double doubleValue8 = Double.MAX_VALUE;
      double doubleValue10 = 0.0;

      for (int intValue12 = 0; intValue12 < 3; intValue12++) {
         for (double doubleValue16 : G) {
            double doubleValue18 = doubleValue2 - this.ModuleManager;
            double doubleValue20 = doubleValue4 - (this.ModuleRegistry + doubleValue16);
            double doubleValue22 = doubleValue6 - this.al;
            double doubleValue24 = Math.sqrt(doubleValue18 * doubleValue18 + doubleValue20 * doubleValue20 + doubleValue22 * doubleValue22);
            if (doubleValue24 < 0.001) {
               return 0.0;
            }

            double doubleValue26 = this.ad[intValue12] * (doubleValue18 / doubleValue24) + this.ae[intValue12] * (doubleValue20 / doubleValue24) + this.af[intValue12] * (doubleValue22 / doubleValue24);
            double doubleValue28 = Math.toDegrees(Math.acos(MathHelper.clamp(doubleValue26, -1.0, 1.0)));
            if (doubleValue28 < doubleValue8) {
               doubleValue8 = doubleValue28;
               doubleValue10 = doubleValue24;
            }
         }
      }

      if (doubleValue8 == Double.MAX_VALUE) {
         return 0.0;
      } else {
         double doubleValue30 = Math.max(value1.d - value1.a, Math.max(value1.e - value1.b, value1.f - value1.c)) / 2.0;
         double doubleValue31 = Math.toDegrees(Math.atan2(doubleValue30, doubleValue10));
         return doubleValue31 > 1.0E-6 ? doubleValue8 / doubleValue31 : 0.0;
      }
   }

   private void d(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      this.ah[0] = 0.0;
      this.ah[1] = 0.0;
      double doubleValue2 = !this.aE && !this.aD ? (this.aB ? 1.27 : 1.62) : 0.4;
      double doubleValue4 = value1.b() - this.ModuleManager;
      double doubleValue6 = value1.c() - (this.ModuleRegistry + doubleValue2);
      double doubleValue8 = value1.d() - this.al;
      double doubleValue10 = Math.sqrt(doubleValue4 * doubleValue4 + doubleValue8 * doubleValue8);
      double doubleValue12 = Math.sqrt(doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8);
      if (!(doubleValue12 < 0.001)) {
         double doubleValue14 = (double)this.am - Math.toDegrees(Math.atan2(-doubleValue4, doubleValue8));
         doubleValue14 -= Math.floor(doubleValue14 / 360.0 + 0.5) * 360.0;
         double doubleValue16 = (double)this.an - Math.toDegrees(-Math.atan2(doubleValue6, doubleValue10));
         double doubleValue18 = Math.max(value1.d - value1.a, value1.f - value1.c) / 2.0;
         double doubleValue20 = (value1.e - value1.b) / 2.0;
         double doubleValue22 = Math.toDegrees(Math.atan2(doubleValue18, doubleValue12));
         double doubleValue24 = Math.toDegrees(Math.atan2(doubleValue20, doubleValue12));
         if (doubleValue22 > 1.0E-6) {
            this.ah[0] = doubleValue14 / doubleValue22;
         }

         if (doubleValue24 > 1.0E-6) {
            this.ah[1] = doubleValue16 / doubleValue24;
         }
      }
   }

   private boolean a(dev.leg1tka.rotation.neural.MotionFeatureVector value1, double doubleValue2) {
      for (int intValue4 = 0; intValue4 < 3; intValue4++) {
         for (double doubleValue8 : G) {
            double doubleValue10 = this.ModuleManager;
            double doubleValue12 = this.ModuleRegistry + doubleValue8;
            double doubleValue14 = this.al;
            if (value1.c(doubleValue10, doubleValue12, doubleValue14)) {
               this.ag[0] = doubleValue10;
               this.ag[1] = doubleValue12;
               this.ag[2] = doubleValue14;
               return true;
            }

            if (this.a(value1, doubleValue10, doubleValue12, doubleValue14, doubleValue10 + this.ad[intValue4] * doubleValue2, doubleValue12 + this.ae[intValue4] * doubleValue2, doubleValue14 + this.af[intValue4] * doubleValue2)) {
               return true;
            }
         }
      }

      return false;
   }

   private int b(dev.leg1tka.rotation.neural.MotionFeatureVector value1, double doubleValue2) {
      int intValue4 = 0;

      for (int intValue5 = 0; intValue5 < 3; intValue5++) {
         for (double doubleValue9 : G) {
            double doubleValue11 = this.ModuleManager;
            double doubleValue13 = this.ModuleRegistry + doubleValue9;
            double doubleValue15 = this.al;
            if (value1.c(doubleValue11, doubleValue13, doubleValue15)) {
               intValue4++;
            } else if (this.a(value1, doubleValue11, doubleValue13, doubleValue15, doubleValue11 + this.ad[intValue5] * doubleValue2, doubleValue13 + this.ae[intValue5] * doubleValue2, doubleValue15 + this.af[intValue5] * doubleValue2)) {
               intValue4++;
            }
         }
      }

      return intValue4;
   }

   private double c(dev.leg1tka.rotation.neural.MotionFeatureVector value1, double doubleValue2) {
      double doubleValue4 = Double.MAX_VALUE;

      for (int intValue6 = 0; intValue6 < 3; intValue6++) {
         for (double doubleValue10 : G) {
            double doubleValue12 = this.a(this.ModuleManager, this.ModuleRegistry + doubleValue10, this.al, this.ad[intValue6], this.ae[intValue6], this.af[intValue6], doubleValue2, value1);
            if (doubleValue12 < doubleValue4) {
               doubleValue4 = doubleValue12;
            }

            if (doubleValue4 == 0.0) {
               return 0.0;
            }
         }
      }

      return doubleValue4;
   }

   private double a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11, double doubleValue13, dev.leg1tka.rotation.neural.MotionFeatureVector value15) {
      if (this.a(value15, doubleValue1, doubleValue3, doubleValue5, doubleValue1 + doubleValue7 * doubleValue13, doubleValue3 + doubleValue9 * doubleValue13, doubleValue5 + doubleValue11 * doubleValue13)) {
         return 0.0;
      } else if (value15.c(doubleValue1, doubleValue3, doubleValue5)) {
         return 0.0;
      } else {
         double doubleValue16 = Double.MAX_VALUE;

         for (int intValue18 = 0; intValue18 <= 32; intValue18++) {
            double doubleValue19 = doubleValue13 * (double)intValue18 / 32.0;
            double doubleValue21 = doubleValue1 + doubleValue7 * doubleValue19;
            double doubleValue23 = doubleValue3 + doubleValue9 * doubleValue19;
            double doubleValue25 = doubleValue5 + doubleValue11 * doubleValue19;
            double doubleValue27 = MathHelper.clamp(doubleValue21, value15.a, value15.d);
            double doubleValue29 = MathHelper.clamp(doubleValue23, value15.b, value15.e);
            double doubleValue31 = MathHelper.clamp(doubleValue25, value15.c, value15.f);
            double doubleValue33 = doubleValue21 - doubleValue27;
            double doubleValue35 = doubleValue23 - doubleValue29;
            double doubleValue37 = doubleValue25 - doubleValue31;
            double doubleValue39 = doubleValue33 * doubleValue33 + doubleValue35 * doubleValue35 + doubleValue37 * doubleValue37;
            if (doubleValue39 < doubleValue16) {
               doubleValue16 = doubleValue39;
            }
         }

         return Math.sqrt(doubleValue16);
      }
   }

   private boolean a(dev.leg1tka.rotation.neural.MotionFeatureVector value1, double doubleValue2, double doubleValue4, double doubleValue6, double doubleValue8, double doubleValue10, double doubleValue12) {
      double doubleValue14 = doubleValue8 - doubleValue2;
      double doubleValue16 = doubleValue10 - doubleValue4;
      double doubleValue18 = doubleValue12 - doubleValue6;
      boolean flag20 = false;
      double doubleValue21 = 0.0;
      double doubleValue23 = 0.0;
      double doubleValue25 = 0.0;
      double doubleValue27 = 0.0;

      for (int intValue29 = 0; intValue29 < 6; intValue29++) {
         double doubleValue30;
         if (intValue29 < 2) {
            if (doubleValue14 * doubleValue14 < 1.0E-7F) {
               continue;
            }

            doubleValue30 = ((intValue29 == 0 ? value1.a : value1.d) - doubleValue2) / doubleValue14;
         } else if (intValue29 < 4) {
            if (doubleValue16 * doubleValue16 < 1.0E-7F) {
               continue;
            }

            doubleValue30 = ((intValue29 == 2 ? value1.b : value1.e) - doubleValue4) / doubleValue16;
         } else {
            if (doubleValue18 * doubleValue18 < 1.0E-7F) {
               continue;
            }

            doubleValue30 = ((intValue29 == 4 ? value1.c : value1.f) - doubleValue6) / doubleValue18;
         }

         if (!(doubleValue30 < 0.0) && !(doubleValue30 > 1.0)) {
            double doubleValue32 = doubleValue2 + doubleValue14 * doubleValue30;
            double doubleValue34 = doubleValue4 + doubleValue16 * doubleValue30;
            double doubleValue36 = doubleValue6 + doubleValue18 * doubleValue30;
            boolean flag38 = intValue29 < 2
               ? doubleValue34 >= value1.b && doubleValue34 <= value1.e && doubleValue36 >= value1.c && doubleValue36 <= value1.f
               : (
                  intValue29 < 4
                     ? doubleValue32 >= value1.a && doubleValue32 <= value1.d && doubleValue36 >= value1.c && doubleValue36 <= value1.f
                     : doubleValue32 >= value1.a && doubleValue32 <= value1.d && doubleValue34 >= value1.b && doubleValue34 <= value1.e
               );
            if (flag38) {
               double doubleValue39 = (doubleValue32 - doubleValue2) * (doubleValue32 - doubleValue2) + (doubleValue34 - doubleValue4) * (doubleValue34 - doubleValue4) + (doubleValue36 - doubleValue6) * (doubleValue36 - doubleValue6);
               if (!flag20 || doubleValue39 < doubleValue21) {
                  flag20 = true;
                  doubleValue21 = doubleValue39;
                  doubleValue23 = doubleValue32;
                  doubleValue25 = doubleValue34;
                  doubleValue27 = doubleValue36;
               }
            }
         }
      }

      if (flag20) {
         this.ag[0] = doubleValue23;
         this.ag[1] = doubleValue25;
         this.ag[2] = doubleValue27;
      }

      return flag20;
   }

   private boolean a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7, double doubleValue9, double doubleValue11) {
      ClientWorld clientWorld13 = g().world;
      if (clientWorld13 == null) {
         return false;
      } else {
         double doubleValue14 = doubleValue7 - doubleValue1;
         double doubleValue16 = doubleValue9 - doubleValue3;
         double doubleValue18 = doubleValue11 - doubleValue5;
         double doubleValue20 = Math.sqrt(doubleValue14 * doubleValue14 + doubleValue16 * doubleValue16 + doubleValue18 * doubleValue18);
         if (doubleValue20 < 0.25) {
            return false;
         } else {
            int intValue22 = Math.min((int)(doubleValue20 / 0.25), 24);
            double doubleValue23 = 0.25 / doubleValue20;
            double doubleValue25 = doubleValue14 * doubleValue23;
            double doubleValue27 = doubleValue16 * doubleValue23;
            double doubleValue29 = doubleValue18 * doubleValue23;
            Mutable mutable31 = new Mutable();

            for (int intValue32 = 1; intValue32 < intValue22; intValue32++) {
               mutable31.set(
                  MathHelper.floor(doubleValue1 + doubleValue25 * (double)intValue32),
                  MathHelper.floor(doubleValue3 + doubleValue27 * (double)intValue32),
                  MathHelper.floor(doubleValue5 + doubleValue29 * (double)intValue32)
               );
               BlockState blockState33 = clientWorld13.getBlockState(mutable31);
               if (!blockState33.isAir() && blockState33.isSolid()) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private float e(dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      double doubleValue2 = this.ModuleManager;
      double doubleValue4 = this.ModuleRegistry + 1.62;
      double doubleValue6 = this.al;
      int intValue8 = 0;
      if (!this.a(doubleValue2, doubleValue4, doubleValue6, value1.b(), value1.c(), value1.d())) {
         intValue8++;
      }

      for (int intValue9 = 0; intValue9 < 8; intValue9++) {
         double doubleValue10 = (intValue9 & 1) == 0 ? value1.a : value1.d;
         double doubleValue12 = (intValue9 & 2) == 0 ? value1.b : value1.e;
         double doubleValue14 = (intValue9 & 4) == 0 ? value1.c : value1.f;
         if (!this.a(doubleValue2, doubleValue4, doubleValue6, doubleValue10, doubleValue12, doubleValue14)) {
            intValue8++;
         }
      }

      return (float)intValue8 / 9.0F;
   }

   private static double a(dev.leg1tka.rotation.neural.MotionFeatureVector value0, dev.leg1tka.rotation.neural.MotionFeatureVector value1) {
      return value0.d - value0.a - (value1.d - value1.a) + (value0.e - value0.b - (value1.e - value1.b)) + (value0.f - value0.c - (value1.f - value1.c));
   }

   private boolean a(boolean flag1) {
      if (this.be <= 0.9F) {
         return false;
      } else if (this.aX <= 0.0F) {
         return false;
      } else {
         return this.aq ? false : !this.aG && !this.aH && !this.aF && !this.aA && !flag1;
      }
   }

   private void a(double doubleValue1, boolean flag3, int intValue4) {
      if (this.aq || this.aG || this.aH) {
         this.aX = 0.0F;
      } else if (doubleValue1 < 0.0) {
         this.aX += (float)(-doubleValue1);
      }

      if (this.aD && doubleValue1 > -0.5 && this.aX > 1.0F) {
         this.aX = 1.0F;
      }

      if (flag3 || intValue4 >= 0) {
         this.aX = 0.0F;
      }
   }

   private float b(float floatValue1, boolean flag2) {
      float floatValue3 = this.aA ? 0.9F : 0.8F;
      float floatValue4 = floatValue1;
      if (!this.aq) {
         floatValue4 = floatValue1 * 0.5F;
      }

      if (floatValue4 > 0.0F) {
         floatValue3 += (0.546F - floatValue3) * floatValue4;
      }

      if (flag2) {
         floatValue3 = 0.96F;
      }

      return floatValue3;
   }

   private static int a(ClientPlayerEntity clientPlayerEntity0, RegistryEntry<StatusEffect> registryEntry1) {
      StatusEffectInstance statusEffectInstance2 = clientPlayerEntity0.getStatusEffect(registryEntry1);
      return statusEffectInstance2 == null ? -1 : statusEffectInstance2.getAmplifier();
   }

   private int a(ClientPlayerEntity clientPlayerEntity1) {
      if (clientPlayerEntity1 != null && g().getNetworkHandler() != null) {
         PlayerListEntry playerListEntry2 = g().getNetworkHandler().getPlayerListEntry(clientPlayerEntity1.getUuid());
         return playerListEntry2 == null ? 0 : playerListEntry2.getLatency();
      } else {
         return 0;
      }
   }

   private int b(ClientPlayerEntity clientPlayerEntity1) {
      if (clientPlayerEntity1 != null && g().world != null && this.aD) {
         int intValue2 = 0;

         for (Entity entity4 : g().world.getEntities()) {
            if (entity4 instanceof FireworkRocketEntity && entity4.squaredDistanceTo(clientPlayerEntity1) < 9.0) {
               intValue2++;
            }
         }

         return intValue2;
      } else {
         return 0;
      }
   }

   private float p() {
      ClientPlayerEntity clientPlayerEntity1 = g().player;
      if (clientPlayerEntity1 == null) {
         return 4.0F;
      } else {
         Item item2 = clientPlayerEntity1.getInventory().getStack(this.aY).getItem();
         if (item2 == Items.WOODEN_SWORD
            || item2 == Items.STONE_SWORD
            || item2 == Items.IRON_SWORD
            || item2 == Items.GOLDEN_SWORD
            || item2 == Items.DIAMOND_SWORD
            || item2 == Items.NETHERITE_SWORD) {
            return 1.6F;
         } else if (item2 == Items.WOODEN_AXE || item2 == Items.STONE_AXE) {
            return 0.8F;
         } else if (item2 == Items.IRON_AXE) {
            return 0.9F;
         } else if (item2 == Items.GOLDEN_AXE || item2 == Items.DIAMOND_AXE || item2 == Items.NETHERITE_AXE) {
            return 1.0F;
         } else if (item2 == Items.WOODEN_PICKAXE
            || item2 == Items.STONE_PICKAXE
            || item2 == Items.IRON_PICKAXE
            || item2 == Items.GOLDEN_PICKAXE
            || item2 == Items.DIAMOND_PICKAXE
            || item2 == Items.NETHERITE_PICKAXE) {
            return 1.2F;
         } else if (item2 == Items.WOODEN_SHOVEL
            || item2 == Items.STONE_SHOVEL
            || item2 == Items.IRON_SHOVEL
            || item2 == Items.GOLDEN_SHOVEL
            || item2 == Items.DIAMOND_SHOVEL
            || item2 == Items.NETHERITE_SHOVEL) {
            return 1.0F;
         } else {
            return item2 == Items.TRIDENT ? 1.1F : 4.0F;
         }
      }
   }

   private static int a(PlayerEntity playerEntity0) {
      if (playerEntity0 != null && playerEntity0.isUsingItem()) {
         ItemStack itemStack1 = playerEntity0.getActiveItem();
         if (itemStack1.isEmpty()) {
            return 5;
         } else {
            Item item2 = itemStack1.getItem();
            if (item2 == Items.SHIELD) {
               return 1;
            } else if (item2 == Items.BOW || item2 == Items.CROSSBOW) {
               return 3;
            } else if (item2 == Items.TRIDENT) {
               return 4;
            } else if (item2.getComponents().contains(DataComponentTypes.FOOD)) {
               return 2;
            } else {
               return item2 != Items.POTION && item2 != Items.MILK_BUCKET && item2 != Items.HONEY_BOTTLE ? 5 : 2;
            }
         }
      } else {
         return 0;
      }
   }

   private static int a(Item item0) {
      if (item0 == null || item0 == Items.AIR) {
         return 1;
      } else if (item0 == Items.END_CRYSTAL) {
         return 3;
      } else if (item0 == Items.RESPAWN_ANCHOR) {
         return 5;
      } else if (item0 == Items.GLOWSTONE) {
         return 6;
      } else if (item0 == Items.TOTEM_OF_UNDYING) {
         return 7;
      } else if (item0 == Items.SHIELD) {
         return 12;
      } else if (item0 == Items.OBSIDIAN || item0 == Items.CRYING_OBSIDIAN || item0 == Items.BEDROCK) {
         return 4;
      } else if (item0 == Items.BOW || item0 == Items.CROSSBOW || item0 == Items.TRIDENT) {
         return 9;
      } else if (item0 != Items.ENDER_PEARL && item0 != Items.SNOWBALL && item0 != Items.EGG) {
         Reference reference1 = item0.getRegistryEntry();
         if (!reference1.isIn(ItemTags.SWORDS) && !reference1.isIn(ItemTags.AXES)) {
            if (item0.getComponents().contains(DataComponentTypes.FOOD)) {
               return 11;
            } else {
               return item0 instanceof BlockItem ? 8 : 0;
            }
         } else {
            return 2;
         }
      } else {
         return 10;
      }
   }
}
