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

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class BotLootTracker {
   private static int b;
   private static int c;
   private static int d;
   private static int e;
   private static int f;
   private static int g;
   private static long h;
   private static long i;
   private static double j;
   private static double k;
   private static long l;
   private static int m;
   private static int n;
   private static int o;
   private static int p;
   private static long q;
   private static long r;
   public final String a;
   private final Consumer s;
   private dev.leg1tka.network.bots.BotNavigator t;
   private BotState u = BotState.SEARCH;
   private int v;
   private BlockPos w;
   private boolean x;
   private Vec3d y;
   private ClientWorld z;
   private float A;
   private float B;
   private boolean C;
   private int D;
   private int E;
   private int F;
   private BlockHitResult G;
   private boolean H;
   private int I;
   private long J;
   private int K;
   private int L;
   private int M;
   private int N;
   private String O;
   private BotState P;
   private ClientWorld Q;
   private Vec3d R;
   private int S;
   private boolean T;
   private int U;
   private Vec3d V;
   private final Set W = new HashSet();
   private int X;
   private boolean Y;
   private String Z;
   private long ModuleStorage;
   private BotAction ab = BotAction.NONE;
   private int ac;
   private int ad;
   private int ae;
   private int af;
   private int ag;
   private int ah;
   private boolean Storage;
   private boolean ModuleManager;
   private boolean ModuleRegistry;
   private boolean al;
   private int am;
   private long an;
   private long ao;
   private long ap;
   private long aq;
   private long ar;
   private long as;
   private long at;
   private boolean au;
   private int av = -1;
   private int aw;
   private static int ax;

   public BotLootTracker(String string1, dev.leg1tka.network.bots.BotNavigator value2, Consumer<String> consumer3) {
      this.a = string1;
      this.t = value2;
      this.s = consumer3;
   }

   public dev.leg1tka.network.bots.BotNavigator a() {
      return this.t;
   }

   public boolean b() {
      return this.Y;
   }

   public boolean c() {
      return this.u == BotState.OPEN || this.u == BotState.LOOT || this.u == BotState.DUMP;
   }

   public void a(dev.leg1tka.network.bots.BotNavigator value1) {
      if (this.t != null) {
         this.t.b();
      }

      this.t = value1;
      if (this.w != null && (this.u == BotState.WALK || this.u == BotState.AIM || this.u == BotState.OPEN || this.u == BotState.LOOT || this.u == BotState.DUMP)) {
         this.e();
      }
   }

   public void d() {
      this.h();
      this.j();
      this.ab = BotAction.NONE;
      if (this.t != null) {
         this.t.b();
      }

      this.Y = true;
   }

   public String a(ClientPlayerEntity clientPlayerEntity1) {
      StringBuilder stringBuilder2 = new StringBuilder(this.ab == BotAction.NONE ? this.l() : this.k());
      if (this.w != null && this.ab == BotAction.NONE && this.u != BotState.SEARCH && this.u != BotState.TP) {
         stringBuilder2.append(" ").append(this.w.toShortString());
      }

      if (clientPlayerEntity1 != null) {
         PlayerInventory playerInventory3 = clientPlayerEntity1.getInventory();
         stringBuilder2.append(" §8| §7свободно: ")
            .append(dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory3))
            .append(", лута: ")
            .append(a(playerInventory3))
            .append(", аур: ")
            .append(dev.leg1tka.network.bots.BotInventoryActions.c(playerInventory3, dev.leg1tka.network.bots.BotInventoryActions.b))
            .append('/')
            .append(dev.leg1tka.network.bots.BotInventoryActions.d(playerInventory3, dev.leg1tka.network.bots.BotInventoryActions.b))
            .append(", скорость: ")
            .append(dev.leg1tka.network.bots.BotInventoryActions.c(playerInventory3, dev.leg1tka.network.bots.BotInventoryActions.c))
            .append(", морковь: ")
            .append(dev.leg1tka.network.bots.BotInventoryActions.d(playerInventory3, dev.leg1tka.network.bots.BotInventoryActions.d))
            .append(", невид: ")
            .append(a(clientPlayerEntity1, StatusEffects.INVISIBILITY))
            .append("с")
            .append(", скор: ")
            .append(a(clientPlayerEntity1, StatusEffects.SPEED))
            .append("с");
      }

      return stringBuilder2.toString();
   }

   private static int a(ClientPlayerEntity clientPlayerEntity0, RegistryEntry<StatusEffect> registryEntry1) {
      StatusEffectInstance statusEffectInstance2 = clientPlayerEntity0.getStatusEffect(registryEntry1);
      return statusEffectInstance2 == null ? 0 : (statusEffectInstance2.isInfinite() ? 9999 : statusEffectInstance2.getDuration() / 20);
   }

   public void a(String string1, ClientPlayerEntity clientPlayerEntity2, ClientWorld clientWorld3, ClientPlayerInteractionManager clientPlayerInteractionManager4, long longValue5) {
      if (!this.Y) {
         this.Z = string1;
         this.ModuleStorage = longValue5;
         this.v++;
         if (!clientPlayerEntity2.isDead() && !(clientPlayerEntity2.getHealth() <= 0.0F)) {
            if (this.u == BotState.DEAD) {
               this.a(o(), BotState.SEARCH, clientPlayerEntity2, clientWorld3);
               this.z = clientWorld3;
            } else {
               if (this.z != null && clientWorld3 != this.z && this.u != BotState.TP) {
                  this.h();
                  this.t.a();
                  this.y = null;
                  this.w = null;
                  this.u = BotState.SEARCH;
                  this.v = 0;
               }

               this.z = clientWorld3;
               if (this.ab != BotAction.NONE) {
                  this.d(clientPlayerEntity2, clientPlayerInteractionManager4);
               } else {
                  boolean flag7 = clientPlayerEntity2.currentScreenHandler != clientPlayerEntity2.playerScreenHandler;
                  if (!flag7 && (this.u == BotState.SEARCH || this.u == BotState.WALK || this.u == BotState.STASH)) {
                     BotAction botAction8 = this.e(clientPlayerEntity2);
                     if (botAction8 != BotAction.NONE) {
                        this.a(botAction8);
                        this.d(clientPlayerEntity2, clientPlayerInteractionManager4);
                        return;
                     }
                  }

                  if (!flag7) {
                     dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity2, clientPlayerInteractionManager4, 0);
                  }

                  switch (this.u) {
                     case SEARCH:
                        this.b(clientPlayerEntity2);
                        break;
                     case WALK:
                        this.a(clientPlayerEntity2, clientWorld3);
                        break;
                     case AIM:
                        this.b(clientPlayerEntity2, clientWorld3);
                        break;
                     case OPEN:
                        this.a(clientPlayerEntity2, clientPlayerInteractionManager4);
                        break;
                     case LOOT:
                        this.b(clientPlayerEntity2, clientPlayerInteractionManager4);
                        break;
                     case TP:
                        this.c(clientPlayerEntity2, clientWorld3);
                        break;
                     case STASH:
                        this.d(clientPlayerEntity2, clientWorld3);
                        break;
                     case DUMP:
                        this.c(clientPlayerEntity2, clientPlayerInteractionManager4);
                  }
               }
            }
         } else {
            if (this.ab != BotAction.NONE) {
               this.j();
               this.ab = BotAction.NONE;
            }

            if (this.u != BotState.DEAD) {
               this.h();
               this.t.a();
               this.u = BotState.DEAD;
               this.c(
                  "умер, после возрождения вернусь на /"
                     + o()
               );
            }

            this.z = clientWorld3;
         }
      }
   }

   private void b(ClientPlayerEntity clientPlayerEntity1) {
      if (this.y == null) {
         this.y = clientPlayerEntity1.getPos();
      }

      PlayerInventory playerInventory2 = clientPlayerEntity1.getInventory();
      if (dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory2) == 0) {
         if (a(playerInventory2) == 0) {
            this.b(
               "инвентарь забит не лутом, освободи место"
            );
         } else {
            this.c(
               "инвентарь полон, телепорт /"
                  + n()
            );
            this.a(n(), BotState.STASH, clientPlayerEntity1, this.z);
         }
      } else {
         dev.leg1tka.network.bots.BotMessageParser.a value3 = this.c(clientPlayerEntity1);
         if (value3 == null) {
            this.t.a();
            if (--this.X <= 0) {
               this.X = 400;
               this.c(
                  "нет доступных сундуков в зоне, жду таймеры"
               );
            }
         } else {
            this.X = 0;
            value3.f = this.a;
            value3.g = this.ModuleStorage + 45000L;
            this.w = value3.a;
            this.x = false;
            this.F = 0;
            this.e();
            this.c("иду к сундуку " + this.w.toShortString());
         }
      }
   }

   private dev.leg1tka.network.bots.BotMessageParser.a c(ClientPlayerEntity clientPlayerEntity1) {
      WardenHelper wardenHelper2 = WardenHelper.c;
      boolean flag3 = !wardenHelper2.r();
      double doubleValue4 = wardenHelper2.s();
      dev.leg1tka.network.bots.BotMessageParser.a value6 = null;
      double doubleValue7 = Double.MAX_VALUE;

      for (dev.leg1tka.network.bots.BotMessageParser.a value10 : dev.leg1tka.network.bots.BotMessageParser.a(this.Z).values()) {
         if (value10.b != dev.leg1tka.network.bots.BotMessageParser.AnchorState.TIMER && value10.d <= this.ModuleStorage && value10.e <= this.ModuleStorage && !value10.a(this.a, this.ModuleStorage)) {
            if (!flag3 && this.y != null) {
               double doubleValue11 = (double)value10.a.getX() + 0.5 - this.y.x;
               double doubleValue13 = (double)value10.a.getZ() + 0.5 - this.y.z;
               if (doubleValue11 * doubleValue11 + doubleValue13 * doubleValue13 > doubleValue4 * doubleValue4) {
                  continue;
               }
            }

            double doubleValue15 = clientPlayerEntity1.squaredDistanceTo(value10.a.toCenterPos()) + (value10.b == dev.leg1tka.network.bots.BotMessageParser.AnchorState.ACTIVE ? 0.0 : 1.0E7);
            if (doubleValue15 < doubleValue7) {
               doubleValue7 = doubleValue15;
               value6 = value10;
            }
         }
      }

      return value6;
   }

   private void a(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      if (!this.x) {
         dev.leg1tka.network.bots.BotMessageParser.a value3 = dev.leg1tka.network.bots.BotMessageParser.a(this.Z, this.w);
         if (value3 == null || value3.b == dev.leg1tka.network.bots.BotMessageParser.AnchorState.TIMER || value3.d > this.ModuleStorage || value3.a(this.a, this.ModuleStorage)) {
            this.h();
            this.t.a();
            this.u = BotState.SEARCH;
            return;
         }

         value3.g = this.ModuleStorage + 45000L;
      }

      switch (this.t.a(clientPlayerEntity1, clientWorld2)) {
         case ARRIVED:
            this.t.a();
            this.u = BotState.AIM;
            this.v = 0;
            this.C = false;
            this.D = 0;
            this.E = 0;
            break;
         case FAILED:
            this.a(this.t.c());
      }
   }

   private void b(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      double doubleValue3 = dev.leg1tka.network.bots.BotBlockActions.a(clientPlayerEntity1);
      Vec3d vec3d5 = clientPlayerEntity1.getEyePos();
      BlockHitResult blockHitResult6 = dev.leg1tka.network.bots.BotBlockActions.a(clientWorld2, clientPlayerEntity1, vec3d5, this.w, doubleValue3);
      if (blockHitResult6 == null) {
         if (++this.E > 10) {
            if (++this.F > 2) {
               this.a("не вижу блок");
            } else {
               this.e();
            }
         }
      } else {
         this.E = 0;
         if (!this.C) {
            this.A = clientPlayerEntity1.getYaw();
            this.B = clientPlayerEntity1.getPitch();
            this.C = true;
         }

         float floatValue7 = MathHelper.wrapDegrees(dev.leg1tka.network.bots.BotBlockActions.a(vec3d5, blockHitResult6.getPos()) - this.A);
         float floatValue8 = dev.leg1tka.network.bots.BotBlockActions.b(vec3d5, blockHitResult6.getPos()) - this.B;
         this.A = this.A + MathHelper.clamp(floatValue7, -40.0F, 40.0F);
         this.B = MathHelper.clamp(this.B + MathHelper.clamp(floatValue8, -30.0F, 30.0F), -90.0F, 90.0F);
         this.t.a(clientPlayerEntity1, this.A, this.B);
         this.D = Math.abs(floatValue7) < 1.0F && Math.abs(floatValue8) < 1.0F ? this.D + 1 : 0;
         if (this.D >= 2) {
            BlockHitResult blockHitResult9 = dev.leg1tka.network.bots.BotBlockActions.a(clientWorld2, clientPlayerEntity1, vec3d5, this.A, this.B, this.w, doubleValue3);
            if (blockHitResult9 != null) {
               this.G = blockHitResult9;
               this.u = BotState.OPEN;
               this.v = 0;
               this.H = false;
               return;
            }

            this.D = 0;
         }

         if (this.v > 40) {
            this.a("не смог навестись");
         }
      }
   }

   private void a(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      this.t.a(clientPlayerEntity1, this.A, this.B);
      if (!this.H) {
         if (clientPlayerEntity1.currentScreenHandler != clientPlayerEntity1.playerScreenHandler) {
            clientPlayerEntity1.closeHandledScreen();
         }

         clientPlayerInteractionManager2.interactBlock(clientPlayerEntity1, Hand.MAIN_HAND, this.G);
         clientPlayerEntity1.swingHand(Hand.MAIN_HAND);
         this.H = true;
         this.I = 0;
      } else {
         if (clientPlayerEntity1.currentScreenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler3 && genericContainerScreenHandler3.syncId != 0) {
            if (++this.I >= 5) {
               this.u = this.x ? BotState.DUMP : BotState.LOOT;
               this.v = 0;
               this.J = 0L;
               this.K = -1;
               this.M = 0;
               this.N = 0;
               this.au = false;
               this.av = -1;
               this.aw = 0;
            }

            return;
         }

         if (this.v > 40) {
            this.a("не открылся");
         }
      }
   }

   private void b(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      this.t.a();
      if (!(clientPlayerEntity1.currentScreenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler3)) {
         this.g();
         this.u = BotState.SEARCH;
      } else if (this.ModuleStorage >= this.J) {
         int intValue8 = genericContainerScreenHandler3.getRows() * 9;
         ItemStack itemStack5 = genericContainerScreenHandler3.getCursorStack();
         if (!itemStack5.isEmpty()) {
            int intValue6 = this.au ? -1 : dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler3, intValue8, itemStack5, this.a(clientPlayerEntity1, itemStack5));
            if (intValue6 >= 0 && !this.a(intValue6, itemStack5)) {
               clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue6, 0, SlotActionType.PICKUP, clientPlayerEntity1);
               this.J = this.ModuleStorage + 60L;
               return;
            }

            this.au = true;
            int intValue7 = dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler3, intValue8, itemStack5);
            if (intValue7 >= 0) {
               clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue7, 0, SlotActionType.PICKUP, clientPlayerEntity1);
               this.J = this.ModuleStorage + 60L;
               return;
            }
         }

         if (this.au) {
            clientPlayerEntity1.closeHandledScreen();
            this.h();
            this.c(
               "инвентарь полон, телепорт /"
                  + n()
            );
            this.a(n(), BotState.STASH, clientPlayerEntity1, this.z);
         } else {
            int intValue9 = -1;

            for (int intValue10 = 0; intValue10 < intValue8; intValue10++) {
               if (a(genericContainerScreenHandler3.getSlot(intValue10).getStack())) {
                  intValue9 = intValue10;
                  break;
               }
            }

            if (intValue9 < 0) {
               clientPlayerEntity1.closeHandledScreen();
               this.g();
               if (this.N > 0) {
                  this.c("забрал стаков: " + this.N);
               }

               this.u = BotState.SEARCH;
            } else {
               ItemStack itemStack11 = genericContainerScreenHandler3.getSlot(intValue9).getStack();
               if (dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler3, intValue8, itemStack11, this.a(clientPlayerEntity1, itemStack11)) < 0) {
                  this.au = true;
               } else {
                  this.M = 0;
                  this.K = -1;
                  clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue9, 0, SlotActionType.PICKUP, clientPlayerEntity1);
                  this.N++;
                  this.J = this.ModuleStorage + p();
               }
            }
         }
      }
   }

   private boolean a(ClientPlayerEntity clientPlayerEntity1, ItemStack itemStack2) {
      return dev.leg1tka.network.bots.BotInventoryActions.a(itemStack2) && dev.leg1tka.network.bots.BotInventoryActions.c(clientPlayerEntity1.getInventory(), dev.leg1tka.network.bots.BotInventoryActions.b) < WardenHelper.c.x();
   }

   private void c(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      this.t.a();
      if (clientPlayerEntity1.currentScreenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler3) {
         if (this.ModuleStorage >= this.J) {
            int intValue8 = genericContainerScreenHandler3.getRows() * 9;
            ItemStack itemStack5 = genericContainerScreenHandler3.getCursorStack();
            if (!itemStack5.isEmpty()) {
               if (this.aw > 0 && this.av >= 0) {
                  clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, this.av, 1, SlotActionType.PICKUP, clientPlayerEntity1);
                  this.aw--;
                  this.J = this.ModuleStorage + 60L;
               } else {
                  int intValue9 = dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler3, intValue8, itemStack5);
                  if (intValue9 >= 0 && !this.a(intValue9, itemStack5)) {
                     clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue9, 0, SlotActionType.PICKUP, clientPlayerEntity1);
                     this.J = this.ModuleStorage + 60L;
                  } else {
                     int intValue10 = dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler3, intValue8, itemStack5, true);
                     if (intValue10 >= 0) {
                        clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue10, 0, SlotActionType.PICKUP, clientPlayerEntity1);
                        this.J = this.ModuleStorage + 60L;
                     } else {
                        this.d(clientPlayerEntity1);
                     }
                  }
               }
            } else {
               this.av = -1;
               this.aw = 0;
               int intValue6 = this.a(genericContainerScreenHandler3, intValue8);
               if (intValue6 == -2) {
                  if (!a(genericContainerScreenHandler3, intValue8, genericContainerScreenHandler3.getSlot(this.av).getStack())) {
                     this.d(clientPlayerEntity1);
                  } else {
                     clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, this.av, 0, SlotActionType.PICKUP, clientPlayerEntity1);
                     this.M = 0;
                     this.K = -1;
                     this.J = this.ModuleStorage + 60L;
                  }
               } else if (intValue6 < 0) {
                  dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, 0);
                  clientPlayerEntity1.closeHandledScreen();
                  this.c(
                     "сложил стаков: "
                        + this.N
                        + ", возвращаюсь /"
                        + o()
                  );
                  this.a(o(), BotState.SEARCH, clientPlayerEntity1, this.z);
               } else {
                  ItemStack itemStack7 = genericContainerScreenHandler3.getSlot(intValue6).getStack();
                  if (a(genericContainerScreenHandler3, intValue8, itemStack7) && !this.a(intValue6, itemStack7)) {
                     clientPlayerInteractionManager2.clickSlot(genericContainerScreenHandler3.syncId, intValue6, 0, SlotActionType.QUICK_MOVE, clientPlayerEntity1);
                     this.N++;
                     this.J = this.ModuleStorage + p();
                  } else {
                     this.d(clientPlayerEntity1);
                  }
               }
            }
         }
      } else {
         this.u = BotState.STASH;
      }
   }

   private int a(GenericContainerScreenHandler genericContainerScreenHandler1, int intValue2) {
      for (int intValue3 = 9; intValue3 < 36; intValue3++) {
         int intValue4 = dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValue3);
         if (a(genericContainerScreenHandler1.getSlot(intValue4).getStack(), true)) {
            return intValue4;
         }
      }

      for (int intValue10 = 0; intValue10 < 9; intValue10++) {
         int intValue12 = dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValue10);
         ItemStack itemStack5 = genericContainerScreenHandler1.getSlot(intValue12).getStack();
         if (a(itemStack5, intValue10 == 0)) {
            return intValue12;
         }
      }

      int intValue11 = WardenHelper.c.x();
      int[] intValueArray13 = new int[8];
      int intValue14 = 0;

      for (int intValue6 = 1; intValue6 < 9; intValue6++) {
         if (dev.leg1tka.network.bots.BotInventoryActions.a(genericContainerScreenHandler1.getSlot(dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValue6)).getStack())) {
            intValueArray13[intValue14++] = intValue6;
         }
      }

      for (int intValue15 = 1; intValue15 < intValue14; intValue15++) {
         int intValue7 = intValueArray13[intValue15];
         int intValue8 = genericContainerScreenHandler1.getSlot(dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValue7)).getStack().getCount();

         int intValue9;
         for (intValue9 = intValue15 - 1; intValue9 >= 0 && genericContainerScreenHandler1.getSlot(dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValueArray13[intValue9])).getStack().getCount() < intValue8; intValue9--) {
            intValueArray13[intValue9 + 1] = intValueArray13[intValue9];
         }

         intValueArray13[intValue9 + 1] = intValue7;
      }

      int intValue16 = 0;

      for (int intValue17 = 0; intValue17 < intValue14; intValue17++) {
         int intValue18 = dev.leg1tka.network.bots.BotInventoryActions.a(intValue2, intValueArray13[intValue17]);
         int intValue19 = genericContainerScreenHandler1.getSlot(intValue18).getStack().getCount();
         if (intValue16 >= intValue11) {
            return intValue18;
         }

         if (intValue16 + intValue19 > intValue11) {
            this.av = intValue18;
            this.aw = intValue11 - intValue16;
            return -2;
         }

         intValue16 += intValue19;
      }

      return -1;
   }

   private static boolean a(ItemStack itemStack0, boolean flag1) {
      return !itemStack0.isEmpty() && a(itemStack0) && !dev.leg1tka.network.bots.BotInventoryActions.b(itemStack0) && !dev.leg1tka.network.bots.BotInventoryActions.d.test(itemStack0) ? flag1 || !dev.leg1tka.network.bots.BotInventoryActions.a(itemStack0) : false;
   }

   private void d(ClientPlayerEntity clientPlayerEntity1) {
      clientPlayerEntity1.closeHandledScreen();
      if (m()) {
         this.W.add(this.w);
         this.c(
            "сундук дома полон, ищу следующий"
         );
         this.u = BotState.STASH;
      } else {
         this.b("эндер-сундук полон");
      }
   }

   private boolean a(int intValue1, ItemStack itemStack2) {
      if (intValue1 == this.K && itemStack2.getCount() == this.L) {
         this.M++;
      } else {
         this.M = 0;
      }

      this.K = intValue1;
      this.L = itemStack2.getCount();
      return this.M >= 3;
   }

   private void a(String string1, BotState botState2, ClientPlayerEntity clientPlayerEntity3, ClientWorld clientWorld4) {
      this.t.a();
      this.O = string1;
      this.P = botState2;
      this.Q = clientWorld4;
      this.R = clientPlayerEntity3.getPos();
      this.T = false;
      this.U = 0;
      this.u = BotState.TP;
      this.v = 0;
      this.w = null;
      clientPlayerEntity3.networkHandler.sendChatCommand(string1);
   }

   private void c(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      this.t.a();
      if (this.T) {
         if (++this.U >= 40) {
            this.S = 0;
            this.v = 0;
            this.N = 0;
            if (this.P == BotState.STASH) {
               this.V = clientPlayerEntity1.getPos();
               this.W.clear();
               this.u = BotState.STASH;
            } else {
               this.y = clientPlayerEntity1.getPos();
               this.u = BotState.SEARCH;
            }
         }
      } else {
         if (clientWorld2 != this.Q || clientPlayerEntity1.getPos().squaredDistanceTo(this.R) > 256.0) {
            this.T = true;
            this.U = 0;
         } else if (this.v > 300) {
            if (++this.S > 3) {
               this.b("телепорт /" + this.O + " не сработал");
               return;
            }

            this.v = 0;
            this.R = clientPlayerEntity1.getPos();
            this.Q = clientWorld2;
            clientPlayerEntity1.networkHandler.sendChatCommand(this.O);
         }
      }
   }

   private void d(ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      boolean flag3 = m();
      Vec3d vec3d4 = flag3 ? this.V : clientPlayerEntity1.getPos();
      if (vec3d4 == null) {
         vec3d4 = clientPlayerEntity1.getPos();
      }

      double doubleValue5 = flag3 ? 12.0 : 64.0;
      int intValue7 = MathHelper.ceil(doubleValue5 / 16.0);
      ChunkPos chunkPos8 = new ChunkPos(BlockPos.ofFloored(vec3d4));
      BlockPos blockPos9 = null;
      double doubleValue10 = Double.MAX_VALUE;

      for (int intValue12 = chunkPos8.x - intValue7; intValue12 <= chunkPos8.x + intValue7; intValue12++) {
         for (int intValue13 = chunkPos8.z - intValue7; intValue13 <= chunkPos8.z + intValue7; intValue13++) {
            if (clientWorld2.getChunkManager().isChunkLoaded(intValue12, intValue13)) {
               for (BlockEntity blockEntity15 : clientWorld2.getChunk(intValue12, intValue13).getBlockEntities().values()) {
                  boolean flag16 = flag3 ? blockEntity15 instanceof ChestBlockEntity || blockEntity15 instanceof BarrelBlockEntity : blockEntity15 instanceof EnderChestBlockEntity;
                  if (flag16 && !this.W.contains(blockEntity15.getPos())) {
                     Vec3d vec3d17 = blockEntity15.getPos().toCenterPos();
                     if (!(vec3d17.squaredDistanceTo(vec3d4) > doubleValue5 * doubleValue5)) {
                        double doubleValue18 = clientPlayerEntity1.squaredDistanceTo(vec3d17);
                        if (doubleValue18 < doubleValue10) {
                           doubleValue10 = doubleValue18;
                           blockPos9 = blockEntity15.getPos();
                        }
                     }
                  }
               }
            }
         }
      }

      if (blockPos9 == null) {
         this.b(
            flag3
               ? "у дома нет свободных сундуков"
               : "рядом нет эндер-сундука"
         );
      } else {
         this.w = blockPos9;
         this.x = true;
         this.F = 0;
         this.N = 0;
         this.e();
         this.c(
            flag3
               ? "несу лут в сундук " + this.w.toShortString()
               : "несу лут в эндер-сундук "
                  + this.w.toShortString()
         );
      }
   }

   private void e() {
      this.t.a(this.w);
      this.u = BotState.WALK;
      this.v = 0;
      this.C = false;
   }

   private void a(String string1) {
      this.t.a();
      if (this.u == BotState.OPEN || this.u == BotState.LOOT || this.u == BotState.DUMP) {
         this.f();
      }

      if (this.x) {
         this.W.add(this.w);
         this.c("склад " + this.w.toShortString() + ": " + string1 + ", ищу другой");
         this.u = BotState.STASH;
      } else {
         dev.leg1tka.network.bots.BotMessageParser.a value2 = dev.leg1tka.network.bots.BotMessageParser.a(this.Z, this.w);
         if (value2 != null) {
            value2.e = this.ModuleStorage + 60000L;
            if (this.a.equalsIgnoreCase(value2.f)) {
               value2.f = null;
            }
         }

         this.c(
            "сундук "
               + this.w.toShortString()
               + ": "
               + string1
               + ", пропускаю на 60с"
         );
         this.u = BotState.SEARCH;
      }

      this.v = 0;
   }

   private void b(String string1) {
      this.c("§cостановлен: " + string1);
      this.d();
   }

   private void f() {
      ClientPlayerEntity clientPlayerEntity1 = MinecraftClient.getInstance().player;
      if (clientPlayerEntity1 != null && clientPlayerEntity1.currentScreenHandler != clientPlayerEntity1.playerScreenHandler) {
         clientPlayerEntity1.closeHandledScreen();
      }
   }

   private void g() {
      dev.leg1tka.network.bots.BotMessageParser.a value1 = this.w == null ? null : dev.leg1tka.network.bots.BotMessageParser.a(this.Z, this.w);
      if (value1 != null) {
         value1.d = this.ModuleStorage + WardenHelper.c.u();
         value1.f = null;
      }
   }

   private void h() {
      if (!this.x && this.w != null && this.Z != null) {
         dev.leg1tka.network.bots.BotMessageParser.a value1 = dev.leg1tka.network.bots.BotMessageParser.a(this.Z, this.w);
         if (value1 != null && this.a.equalsIgnoreCase(value1.f)) {
            value1.f = null;
         }
      }
   }

   private BotAction e(ClientPlayerEntity clientPlayerEntity1) {
      PlayerInventory playerInventory2 = clientPlayerEntity1.getInventory();
      if (this.ModuleStorage >= this.aq && !playerInventory2.getStack(0).isEmpty()) {
         return BotAction.CLEAR;
      } else {
         WardenHelper wardenHelper3 = WardenHelper.c;
         if (wardenHelper3.y() && this.ModuleStorage >= this.ap && clientPlayerEntity1.getHungerManager().getFoodLevel() < 10) {
            if (a(playerInventory2, dev.leg1tka.network.bots.BotInventoryActions.d)) {
               return BotAction.EAT;
            }

            if (this.ModuleStorage >= this.as) {
               this.as = this.ModuleStorage + 60000L;
               this.c("нет золотой моркови");
            }
         }

         if (wardenHelper3.v() && this.ModuleStorage >= this.an && !clientPlayerEntity1.hasStatusEffect(StatusEffects.INVISIBILITY)) {
            if (a(playerInventory2, dev.leg1tka.network.bots.BotInventoryActions.b)) {
               return BotAction.AURA;
            }

            if (this.ModuleStorage >= this.ar) {
               this.ar = this.ModuleStorage + 60000L;
               this.c("нет Божьей ауры");
            }
         }

         boolean flag4 = this.al || wardenHelper3.w() && this.ModuleStorage >= this.ao && !clientPlayerEntity1.hasStatusEffect(StatusEffects.SPEED);
         if (flag4) {
            if (dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory2, dev.leg1tka.network.bots.BotInventoryActions.c) >= 0) {
               return BotAction.SPEED;
            }

            this.al = false;
            if (this.ModuleStorage >= this.at) {
               this.at = this.ModuleStorage + 60000L;
               this.c(
                  "нет зелья скорости в хотбаре"
               );
            }
         }

         return BotAction.NONE;
      }
   }

   private static boolean a(PlayerInventory playerInventory0, Predicate<ItemStack> predicate1) {
      return dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory0, predicate1) >= 0 || dev.leg1tka.network.bots.BotInventoryActions.b(playerInventory0, predicate1) >= 0;
   }

   private void a(BotAction botAction1) {
      this.ab = botAction1;
      this.ac = 0;
      this.ad = 0;
      this.ae = 0;
      this.ag = 0;
      this.ah = 0;
      this.ModuleManager = this.u == BotState.WALK;
      this.t.a();
   }

   private void a(int intValue1) {
      this.ac = intValue1;
      this.ae = 0;
   }

   private void d(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      this.ad++;
      this.ae++;
      this.t.a();
      if (this.ModuleRegistry) {
         MinecraftClient.getInstance().itemUseCooldown = 4;
      }

      if (this.ad > 400) {
         dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, 0);
         this.i();
      } else {
         if (this.ab == BotAction.CLEAR) {
            this.e(clientPlayerEntity1, clientPlayerInteractionManager2);
         } else {
            this.f(clientPlayerEntity1, clientPlayerInteractionManager2);
         }
      }
   }

   private void e(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      if (clientPlayerEntity1.getInventory().getStack(0).isEmpty()) {
         this.i();
      } else if (!clientPlayerEntity1.isSprinting() || this.ae >= 10) {
         if (this.ac == 0) {
            dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2);
            this.a(1);
         } else if (this.ac == 1 && this.ae >= 2) {
            dev.leg1tka.network.bots.BotInventoryActions.b(clientPlayerEntity1, clientPlayerInteractionManager2);
            this.a(2);
         } else if (this.ac == 2 && this.ae >= 2) {
            if (!clientPlayerEntity1.getInventory().getStack(0).isEmpty()) {
               this.aq = this.ModuleStorage + 10000L;
            }

            this.i();
         }
      }
   }

   private void f(ClientPlayerEntity clientPlayerEntity1, ClientPlayerInteractionManager clientPlayerInteractionManager2) {
      Predicate predicate3 = this.ab == BotAction.EAT ? dev.leg1tka.network.bots.BotInventoryActions.d : (this.ab == BotAction.AURA ? dev.leg1tka.network.bots.BotInventoryActions.b : dev.leg1tka.network.bots.BotInventoryActions.c);
      PlayerInventory playerInventory4 = clientPlayerEntity1.getInventory();
      switch (this.ac) {
         case 0:
            int intValue9 = dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory4, predicate3);
            if (intValue9 < 0) {
               int intValue6 = this.ab == BotAction.SPEED ? -1 : dev.leg1tka.network.bots.BotInventoryActions.b(playerInventory4, predicate3);
               int intValue7 = dev.leg1tka.network.bots.BotInventoryActions.b(playerInventory4);
               if (intValue6 >= 0 && intValue7 >= 0 && this.ag <= 3) {
                  if (this.ae < 2) {
                     return;
                  }

                  this.ag++;
                  dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, intValue6, intValue7);
                  this.ae = 0;
                  return;
               }

               switch (this.ab) {
                  case EAT:
                     this.ap = this.ModuleStorage + 10000L;
                     break;
                  case AURA:
                     this.an = this.ModuleStorage + 10000L;
                     break;
                  default:
                     this.al = false;
                     this.ao = this.ModuleStorage + 10000L;
               }

               this.a(3);
               return;
            }

            this.af = intValue9;
            dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, intValue9);
            this.a(1);
            break;
         case 1:
            if (playerInventory4.selectedSlot != this.af || !predicate3.test(playerInventory4.getStack(this.af))) {
               this.a(0);
               return;
            }

            clientPlayerInteractionManager2.interactItem(clientPlayerEntity1, Hand.MAIN_HAND);
            if (this.ab != BotAction.AURA && this.t instanceof dev.leg1tka.network.bots.BaritoneBotNavigator) {
               MinecraftClient minecraftClient8 = MinecraftClient.getInstance();
               minecraftClient8.options.useKey.setPressed(true);
               minecraftClient8.itemUseCooldown = 4;
               this.ModuleRegistry = true;
            }

            this.Storage = false;
            this.a(2);
            break;
         case 2:
            if (this.ab == BotAction.AURA) {
               if (clientPlayerEntity1.hasStatusEffect(StatusEffects.INVISIBILITY)) {
                  this.am = 0;
                  this.al = WardenHelper.c.w();
                  this.a(3);
               } else if (this.ae > 60) {
                  this.am++;
                  this.an = this.ModuleStorage + (this.am >= 3 ? 60000L : 10000L);
                  if (this.am == 3) {
                     this.c(
                        "Божья аура не даёт невидимость, повтор через 60с"
                     );
                  }

                  this.a(3);
               }

               return;
            }

            if (clientPlayerEntity1.isUsingItem()) {
               this.Storage = true;
               return;
            }

            if (!this.Storage && this.ae < 10) {
               return;
            }

            this.j();
            if (this.ab == BotAction.EAT) {
               if (this.Storage && clientPlayerEntity1.getHungerManager().getFoodLevel() < 20 && ++this.ah < 12) {
                  int intValue5 = dev.leg1tka.network.bots.BotInventoryActions.a(playerInventory4, dev.leg1tka.network.bots.BotInventoryActions.d);
                  if (intValue5 >= 0) {
                     this.af = intValue5;
                     dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, intValue5);
                     this.a(1);
                     return;
                  }
               }

               this.a(3);
               return;
            }

            this.al = false;
            this.a(4);
            break;
         case 3:
         default:
            dev.leg1tka.network.bots.BotInventoryActions.a(clientPlayerEntity1, clientPlayerInteractionManager2, 0);
            this.i();
            break;
         case 4:
            if (clientPlayerEntity1.hasStatusEffect(StatusEffects.SPEED)) {
               this.ao = this.ModuleStorage + 1000L;
               this.a(3);
            } else if (this.ae > 20) {
               this.ao = this.ModuleStorage + 60000L;
               this.c(
                  "зелье не дало скорость, повтор через 60с"
               );
               this.a(3);
            }
      }
   }

   private void i() {
      this.j();
      this.ab = BotAction.NONE;
      this.ac = 0;
      if (this.ModuleManager && this.u == BotState.WALK && this.w != null) {
         this.t.a(this.w);
      }

      this.ModuleManager = false;
   }

   private void j() {
      if (this.ModuleRegistry) {
         MinecraftClient.getInstance().options.useKey.setPressed(false);
         this.ModuleRegistry = false;
      }
   }

   private String k() {
      return switch (this.ab) {
         case NONE -> this.l();
         case CLEAR -> "освобождаю слот 1";
         case EAT -> "ем";
         case AURA -> "Божья аура";
         case SPEED -> "пью скорость";
      };
   }

   private void c(String string1) {
      this.s.accept("§7[WardenBot §f" + this.a + "§7] §f" + string1);
   }

   private String l() {
      return switch (this.u) {
         case SEARCH -> "поиск";
         case WALK -> this.x
         ? "иду к складу"
         : "иду к сундуку";
         case AIM -> "наводка";
         case OPEN -> "открываю";
         case LOOT -> "лутаю";
         case TP -> "телепорт /" + this.O;
         case STASH -> "ищу склад";
         case DUMP -> "складываю";
         case DEAD -> "мёртв";
      };
   }

   private static boolean m() {
      return WardenHelper.c.p();
   }

   private static String n() {
      return m() ? "home" : "spawn";
   }

   private static String o() {
      return WardenHelper.c.q();
   }

   private static long p() {
      return WardenHelper.c.t() + (long)ThreadLocalRandom.current().nextInt(0, 40);
   }

   public static boolean a(ItemStack itemStack0) {
      if (itemStack0.isEmpty()) {
         return false;
      } else if (!itemStack0.isOf(Items.PHANTOM_MEMBRANE) && !itemStack0.isOf(Items.ELYTRA)) {
         String string1 = itemStack0.getName().getString().toLowerCase(Locale.ROOT);
         return string1.contains("снотворное")
            || string1.contains("ассасина")
            || string1.contains("святая")
            || string1.contains("радиации")
            || string1.contains("драконья")
            || string1.contains("неизбежная")
            || string1.contains("обледенения")
            || string1.contains("световая")
            || string1.contains("палладина");
      } else {
         return true;
      }
   }

   private static int a(PlayerInventory playerInventory0) {
      int intValue1 = 0;

      for (int intValue2 = 0; intValue2 < 36; intValue2++) {
         if (a(playerInventory0.getStack(intValue2))) {
            intValue1++;
         }
      }

      return intValue1;
   }

   private static boolean a(GenericContainerScreenHandler genericContainerScreenHandler0, int intValue1, ItemStack itemStack2) {
      for (int intValue3 = 0; intValue3 < intValue1; intValue3++) {
         ItemStack itemStack4 = genericContainerScreenHandler0.getSlot(intValue3).getStack();
         if (itemStack4.isEmpty()) {
            return true;
         }

         if (itemStack4.getCount() < itemStack4.getMaxCount() && ItemStack.areItemsAndComponentsEqual(itemStack4, itemStack2)) {
            return true;
         }
      }

      return false;
   }

   static enum BotAction {
      NONE,
      CLEAR,
      EAT,
      AURA,
      SPEED;

      private BotAction() {
      }
   }

   public static enum BotState {
      SEARCH,
      WALK,
      AIM,
      OPEN,
      LOOT,
      TP,
      STASH,
      DUMP,
      DEAD;

      private BotState() {
      }
   }
}
