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

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.function.Predicate;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public final class InventorySelection implements dev.leg1tka.api.ClientAccess {
   private static dev.leg1tka.api.utils.player.InventorySelection c;
   private final dev.leg1tka.api.utils.player.SelectedSlotState d = new dev.leg1tka.api.utils.player.SelectedSlotState();
   private final dev.leg1tka.api.utils.player.BundleInventoryActions e = new dev.leg1tka.api.utils.player.BundleInventoryActions(this.d);
   private final ArrayList<dev.leg1tka.api.utils.player.InventorySelection.Task> f = new ArrayList<>();
   private boolean g;
   private boolean h;
   private int i = -1;
   private ItemStack j = ItemStack.EMPTY;
   private int k;

   private InventorySelection() {
      EventInvoker.register(this);
   }

   public static dev.leg1tka.api.utils.player.InventorySelection a() {
      if (c == null) {
         c = new dev.leg1tka.api.utils.player.InventorySelection();
      }

      return c;
   }

   public boolean b() {
      return !this.f.isEmpty() || this.e.a();
   }

   public static ItemStack a(int intValue0, ItemStack itemStack1) {
      dev.leg1tka.api.utils.player.InventorySelection value2 = c;
      return value2 != null && value2.i == intValue0 ? value2.j : itemStack1;
   }

   public void a(Predicate<ItemStack> predicate1, ItemStack itemStack2, ServerMode serverMode3, boolean flag4) {
      this.f.add(new Task(predicate1, itemStack2, serverMode3, flag4));
   }

   public void a(boolean flag1) {
      if (flag1) {
         c();
      }

      b(Hand.OFF_HAND, flag1);
   }

   public static void c() {
      if (minecraftClient.player != null && dev.leg1tka.rotation.RotationStorage.c != null) {
         dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(minecraftClient.player.getYaw(), 90.0F), 355.0F, 355.0F, 28.0F, 28.0F, 6, 2, false);
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         if (!this.f.isEmpty()) {
            Task task2 = (Task)this.f.get(0);
            if ((task2.c == ServerMode.SPOOKYTIME || task2.c == ServerMode.SPACETIME) && !task2.f) {
               this.b(task2);
            } else {
               this.a(task2);
            }
         }

         this.e.c();
         this.f();
         this.d.c();
      } else {
         this.f.clear();
         this.e.b();
         this.d.d();
         this.i = -1;
         this.g = false;
         this.h = false;
      }
   }

   @EventLink(
      priority = -1000
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.d.b() || this.g) {
         eventMoveInput1.setForward(0.0F);
         eventMoveInput1.setStrafe(0.0F);
         eventMoveInput1.setJump(false);
      }

      if (this.g) {
         eventMoveInput1.setSneak(false);
      }
   }

   private void a(Task task1) {
      int intValue2 = b(task1.a);
      int intValue3 = c(task1.a);
      if (task1.e != -1 || (intValue2 != -1 || intValue3 != -1) && !a(task1.b)) {
         if (task1.e < 0 || !this.e.a()) {
            task1.e++;
            if (task1.e == 0) {
               task1.i = g();
               if (intValue2 != -1) {
                  task1.j = intValue2;
                  if (intValue2 != task1.i) {
                     a(intValue2, false);
                  }
               } else if (!this.a(task1, intValue3, 1, false)) {
                  this.f.remove(task1);
               }
            } else {
               if (task1.l != -1) {
                  if (task1.a.test(e(task1.l)) && !a(task1.b)) {
                     d(task1.l);
                     if (task1.d) {
                        c();
                     }

                     b(Hand.MAIN_HAND, task1.d);
                     d(task1.i);
                  }

                  this.a(task1, 1, false);
               } else {
                  if (task1.a.test(minecraftClient.player.getMainHandStack()) && !a(task1.b)) {
                     minecraftClient.interactionManager.syncSelectedSlot();
                     if (task1.d) {
                        c();
                     }

                     b(Hand.MAIN_HAND, task1.d);
                  }

                  if (g() != task1.i) {
                     a(task1.i, false);
                  }
               }

               this.f.remove(task1);
            }
         }
      } else {
         this.f.remove(task1);
      }
   }

   private void b(Task task1) {
      boolean flag2 = task1.c == ServerMode.SPACETIME;
      if (task1.e == -1) {
         if (a(task1.b)) {
            this.f.remove(task1);
         } else {
            task1.i = g();
            if (task1.a.test(minecraftClient.player.getMainHandStack())) {
               task1.j = task1.i;
            } else if (task1.a.test(minecraftClient.player.getOffHandStack())) {
               task1.g = true;
            } else {
               int intValue3 = b(task1.a);
               if (intValue3 != -1) {
                  task1.j = intValue3;
               } else {
                  int intValue4 = a(task1.a);
                  if (intValue4 == -1) {
                     task1.f = true;
                     this.a(task1);
                     return;
                  }

                  task1.k = intValue4;
                  task1.l = e();
               }
            }

            this.d();
            task1.e = 0;
         }
      } else if (task1.e == 0) {
         this.d();
         if (task1.g) {
            if (task1.a.test(minecraftClient.player.getOffHandStack()) && !a(task1.b)) {
               if (task1.d) {
                  c();
               }

               a(Hand.OFF_HAND, task1.d);
            }
         } else if (task1.l == -1) {
            if (task1.a.test(e(task1.j)) && !a(task1.b)) {
               b(task1.j);
               if (task1.d) {
                  c();
               }

               a(Hand.MAIN_HAND, task1.d);
               b(task1.i);
            }
         } else if (task1.a.test(e(task1.k)) && !a(task1.b)) {
            a(task1.k, task1.l);
            c(task1);
            if (flag2) {
               task1.h = true;
            } else {
               a(task1.k, task1.l);
            }
         }

         task1.e = 1;
      } else if (task1.h) {
         task1.e++;
         switch (task1.e) {
            case 2:
               this.a(task1.i);
            case 3:
            default:
               break;
            case 4:
               this.d();
               break;
            case 5:
               this.d();
               a(task1.k, task1.l);
               break;
            case 6:
               this.a(task1.i);
               this.f.remove(task1);
         }
      } else {
         this.a(task1.i);
         this.f.remove(task1);
      }
   }

   private static void c(Task task0) {
      b(task0.l);
      if (task0.d) {
         c();
      }

      a(Hand.MAIN_HAND, task0.d);
      b(task0.i);
   }

   private void d() {
      this.g = true;
      minecraftClient.options.sprintKey.setPressed(false);
      minecraftClient.player.setSprinting(false);
      if (!this.h) {
         this.h = true;
         minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         if (Sprint.c.isEnabled()) {
            minecraftClient.options.sprintKey.setPressed(false);
         }
      }
   }

   private void a(int intValue1) {
      this.g = false;
      this.h = false;
      b(intValue1);
      minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
   }

   private static void a(int intValue0, int intValue1) {
      int intValue2 = minecraftClient.player.currentScreenHandler.syncId;
      minecraftClient.interactionManager.clickSlot(intValue2, intValue0, intValue1, SlotActionType.SWAP, minecraftClient.player);
      minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue2));
   }

   private static void b(int intValue0) {
      if (intValue0 >= 0 && intValue0 <= 8) {
         minecraftClient.player.getInventory().selectedSlot = intValue0;
         minecraftClient.interactionManager.syncSelectedSlot();
      }
   }

   private static void a(Hand hand0, boolean flag1) {
      b(hand0, flag1);
   }

   private static int a(Predicate<ItemStack> predicate0) {
      for (int intValue1 = 35; intValue1 >= 9; intValue1--) {
         if (predicate0.test(e(intValue1))) {
            return intValue1;
         }
      }

      return -1;
   }

   private static int e() {
      for (int intValue0 = 0; intValue0 < 9; intValue0++) {
         if (e(intValue0).isEmpty()) {
            return intValue0;
         }
      }

      return 8;
   }

   private boolean a(Task task1, int intValue2, int intValue3, boolean flag4) {
      if (intValue2 == -1) {
         return false;
      } else {
         int intValue5 = a(e(intValue2), task1.a);
         if (intValue5 == -1) {
            task1.k = intValue2;
            task1.l = a(task1.i, intValue2, -1);
            this.c(task1.l);
            this.e.a(intValue2, task1.l, intValue3, flag4);
            return true;
         } else {
            task1.n = b(9, 35);
            if (task1.n == -1) {
               task1.n = b(0, 8);
            }

            if (task1.n == -1) {
               return false;
            } else {
               task1.m = intValue2;
               task1.l = a(task1.i, intValue2, task1.n);
               this.c(task1.l);
               task1.o = e(task1.l).copy();
               this.e.a(intValue2, task1.l, task1.n, intValue5, intValue3, flag4);
               return true;
            }
         }
      }
   }

   private void a(Task task1, int intValue2, boolean flag3) {
      if (task1.m != -1) {
         if (!ItemStack.areItemsAndComponentsEqual(e(task1.l), task1.o)) {
            this.e.b(task1.n, task1.l, intValue2, flag3);
         }
      } else {
         this.e.a(task1.l, task1.k, intValue2, flag3);
      }
   }

   private void f() {
      if (this.i != -1) {
         if (this.f.isEmpty() && !this.e.a()) {
            if (--this.k <= 0) {
               this.i = -1;
            }
         } else {
            this.k = 4;
         }
      }
   }

   private void c(int intValue1) {
      if (intValue1 >= 0 && intValue1 <= 8) {
         this.i = intValue1;
         this.j = e(intValue1).copy();
         this.k = 4;
      }
   }

   private static void b(Hand hand0, boolean flag1) {
      dev.leg1tka.rotation.RotationStorage rotationStorage2 = dev.leg1tka.rotation.RotationStorage.c;
      boolean flag3 = rotationStorage2 != null && rotationStorage2.b() && rotationStorage2.k() != null;
      float floatValue4 = flag3 ? rotationStorage2.k().getYaw() : minecraftClient.player.getYaw();
      float floatValue5 = flag1 ? 90.0F : (flag3 ? rotationStorage2.k().getPitch() : minecraftClient.player.getPitch());
      minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, intValue3x -> new PlayerInteractItemC2SPacket(hand0, intValue3x, floatValue4, floatValue5));
   }

   private static void d(int intValue0) {
      minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue0));
   }

   private static void a(int intValue0, boolean flag1) {
      if (intValue0 >= 0 && intValue0 <= 8) {
         minecraftClient.player.getInventory().selectedSlot = intValue0;
         if (flag1) {
            minecraftClient.interactionManager.syncSelectedSlot();
         }
      }
   }

   private static int g() {
      return minecraftClient.player.getInventory().selectedSlot;
   }

   private static ItemStack e(int intValue0) {
      return minecraftClient.player.getInventory().getStack(intValue0);
   }

   private static boolean a(ItemStack itemStack0) {
      return itemStack0 != null && !itemStack0.isEmpty() && minecraftClient.player.getItemCooldownManager().isCoolingDown(itemStack0);
   }

   private static int b(Predicate<ItemStack> predicate0) {
      for (int intValue1 = 8; intValue1 >= 0; intValue1--) {
         if (predicate0.test(e(intValue1))) {
            return intValue1;
         }
      }

      return -1;
   }

   private static int c(Predicate<ItemStack> predicate0) {
      for (int intValue1 = 35; intValue1 >= 9; intValue1--) {
         if (predicate0.test(e(intValue1))) {
            return intValue1;
         }
      }

      for (int intValue2 = 35; intValue2 >= 0; intValue2--) {
         if (a(e(intValue2), predicate0) != -1) {
            return intValue2;
         }
      }

      return -1;
   }

   public static int a(ItemStack itemStack0, Predicate<ItemStack> predicate1) {
      BundleContentsComponent bundleContentsComponent2 = (BundleContentsComponent)itemStack0.get(DataComponentTypes.BUNDLE_CONTENTS);
      if (bundleContentsComponent2 == null) {
         return -1;
      } else {
         for (int intValue3 = 0; intValue3 < bundleContentsComponent2.size(); intValue3++) {
            if (predicate1.test(bundleContentsComponent2.get(intValue3))) {
               return intValue3;
            }
         }

         return -1;
      }
   }

   private static int b(int intValue0, int intValue1) {
      for (int intValue2 = intValue1; intValue2 >= intValue0; intValue2--) {
         if (e(intValue2).isEmpty()) {
            return intValue2;
         }
      }

      return -1;
   }

   private static int a(int intValue0, int intValue1, int intValue2) {
      for (int intValue3 = 8; intValue3 >= 0; intValue3--) {
         if (intValue3 != intValue0 && intValue3 != intValue1 && intValue3 != intValue2 && e(intValue3).isEmpty()) {
            return intValue3;
         }
      }

      for (int intValue4 = 8; intValue4 >= 0; intValue4--) {
         if (intValue4 != intValue0 && intValue4 != intValue1 && intValue4 != intValue2) {
            return intValue4;
         }
      }

      return -1;
   }

   public static enum ServerMode {
      FUNTIME,
      SPOOKYTIME,
      HOLYWORLD,
      SPACETIME;

      private ServerMode() {
      }
   }

   static class Task {
      final Predicate a;
      final ItemStack b;
      final ServerMode c;
      final boolean d;
      int e = -1;
      boolean f;
      boolean g;
      boolean h;
      int i = -1;
      int j = -1;
      int k = -1;
      int l = -1;
      int m = -1;
      int n = -1;
      ItemStack o = ItemStack.EMPTY;

      Task(Predicate<ItemStack> predicate1, ItemStack itemStack2, ServerMode serverMode3, boolean flag4) {
         this.a = predicate1;
         this.b = itemStack2;
         this.c = serverMode3;
         this.d = flag4;
      }
   }
}
