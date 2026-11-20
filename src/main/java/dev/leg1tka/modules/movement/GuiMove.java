package dev.leg1tka.modules.movement;


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
import dev.leg1tka.api.events.implement.EventCloseInv;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;

public class GuiMove extends Module {
   public static GuiMove c = new GuiMove();
   public ModeSetting d = new ModeSetting(
      "Обход",
      "Обычный",
      "Обычный",
      "Grim",
      "SpookyTime",
      "Legit"
   );
   public ModeSetting e = new ModeSetting("Версия свапа", "Test", "Test", "New").a(() -> this.d.b("Grim"));
   public int f = 0;
   private final List<ClickSlotC2SPacket> g = new ArrayList<>();
   private CloseHandledScreenC2SPacket h = null;
   private boolean i = false;
   private boolean j = false;
   private int k = -1;
   private boolean l = false;
   private static int m;
   private boolean n = false;
   private boolean o = false;
   private boolean p = false;
   private CloseHandledScreenC2SPacket q = null;
   private int r = 0;
   private static int s;
   private static int t;
   private static int u;
   private static int v;
   private static int w;
   private static int x;
   private static int y;
   private static int z;
   private static int A;
   private int B = 0;
   private boolean C = false;
   private CloseHandledScreenC2SPacket D = null;

   public GuiMove() {
      super(
         "GuiMove",
         "Ходьба с открытым инвентарём",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         KeyBinding[] keyBindingArray2 = new KeyBinding[]{
            minecraftClient.options.forwardKey, minecraftClient.options.backKey, minecraftClient.options.leftKey, minecraftClient.options.rightKey, minecraftClient.options.jumpKey, minecraftClient.options.sprintKey
         };
         if (this.d.b("SpookyTime")) {
            this.a(keyBindingArray2);
         } else if (this.d.b("Legit")) {
            this.b(keyBindingArray2);
         } else {
            if (this.d.b("Grim") && this.e.b("Test") && this.j && !dev.leg1tka.api.utils.player.PlayerPositionUtils.c()) {
               this.e(true);
               this.j = false;
               this.f = 3;
            }

            if (this.d.b("Grim") && this.e.b("New") && this.k >= 0) {
               if (this.k == 0) {
                  this.e(true);
                  this.k = -1;
                  this.f = 1;
               } else {
                  this.k--;
               }
            }

            if (this.f == 0 && !this.g.isEmpty() && minecraftClient.currentScreen == null && !this.j) {
               this.u();
            }

            if (this.f != 0) {
               this.c(keyBindingArray2);
               this.f--;
               if (this.f == 0 && this.i) {
                  this.i = false;
                  Sprint.p();
               }
            } else if (!(minecraftClient.currentScreen instanceof ChatScreen) && !(minecraftClient.currentScreen instanceof SignEditScreen)) {
               if (!this.d.b("Grim") || !(minecraftClient.currentScreen instanceof HandledScreen) || minecraftClient.currentScreen instanceof InventoryScreen) {
                  if (this.j) {
                     this.c(keyBindingArray2);
                  } else {
                     this.d(keyBindingArray2);
                  }
               }
            }
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.SEND && !this.l) {
         Packet packet2 = eventPacket1.getPacket();
         if (this.d.b("SpookyTime")) {
            this.a(eventPacket1, packet2);
         } else if (this.d.b("Legit")) {
            this.b(eventPacket1, packet2);
         } else if (this.d.b("Grim") && dev.leg1tka.api.utils.player.PlayerPositionUtils.c() && minecraftClient.currentScreen instanceof InventoryScreen) {
            if (packet2 instanceof ClickSlotC2SPacket clickSlotC2SPacket4) {
               this.g.add(clickSlotC2SPacket4);
               eventPacket1.cancel();
            } else {
               if (packet2 instanceof CloseHandledScreenC2SPacket closeHandledScreenC2SPacket3) {
                  this.h = closeHandledScreenC2SPacket3;
                  if (this.e.b("New")) {
                     this.k = 1;
                     this.j = false;
                  } else {
                     this.j = true;
                  }

                  this.t();
                  eventPacket1.cancel();
               }
            }
         }
      }
   }

   @EventLink
   public void onCloseInv(EventCloseInv eventCloseInv1) {
      if (this.d.b("Grim") && this.e.b("New") && dev.leg1tka.api.utils.player.PlayerPositionUtils.c() && minecraftClient.currentScreen instanceof InventoryScreen) {
         this.h = new CloseHandledScreenC2SPacket(eventCloseInv1.windowId);
         this.k = 1;
         this.t();
         this.f = 1;
         eventCloseInv1.cancel();
      } else {
         if (this.d.b("Grim") && !this.j) {
            this.t();
            this.f = 1;
         }
      }
   }

   private void a(EventPacket eventPacket1, Object object2) {
      if (minecraftClient.currentScreen instanceof HandledScreen) {
         if (object2 instanceof ClickSlotC2SPacket clickSlotC2SPacket3) {
            if (!this.o && this.p()) {
               eventPacket1.cancel();
               this.g.add(clickSlotC2SPacket3);
            }
         } else if (object2 instanceof CloseHandledScreenC2SPacket closeHandledScreenC2SPacket4 && !this.g.isEmpty() && this.p()) {
            eventPacket1.cancel();
            this.p = true;
            this.q = closeHandledScreenC2SPacket4;
         }
      }
   }

   private void a(KeyBinding[] keyBindingArray1) {
      boolean flag2 = this.q();
      if (this.n || this.o) {
         this.c(keyBindingArray1);
      } else if (flag2 || minecraftClient.currentScreen == null) {
         this.d(keyBindingArray1);
      }

      boolean flag3 = !this.g.isEmpty() && (!flag2 || !this.p() || this.p);
      if (this.n) {
         if (this.r > 0) {
            this.r--;
            return;
         }

         this.n = false;
         this.o = true;
      } else if (flag3) {
         this.n = true;
         this.r = 5;
         this.c(keyBindingArray1);
         return;
      }

      if (this.o) {
         this.l = true;

         while (!this.g.isEmpty()) {
            minecraftClient.getNetworkHandler().sendPacket((Packet)this.g.remove(0));
         }

         if (this.p && this.q != null) {
            minecraftClient.getNetworkHandler().sendPacket(this.q);
         }

         this.l = false;
         this.r();
         this.d(keyBindingArray1);
      }
   }

   private void b(EventPacket eventPacket1, Object object2) {
      if (object2 instanceof ClickSlotC2SPacket clickSlotC2SPacket3) {
         if (!(minecraftClient.currentScreen instanceof HandledScreen)) {
            return;
         }

         if (!this.l && this.p()) {
            eventPacket1.cancel();
            this.g.add(clickSlotC2SPacket3);
         }
      } else if (object2 instanceof CloseHandledScreenC2SPacket closeHandledScreenC2SPacket4 && !this.l && !this.g.isEmpty()) {
         eventPacket1.cancel();
         this.C = true;
         this.D = closeHandledScreenC2SPacket4;
      }
   }

   private void b(KeyBinding[] keyBindingArray1) {
      boolean flag2 = this.q();
      switch (this.B) {
         case 1:
            this.c(keyBindingArray1);
            this.B = 2;
            return;
         case 2:
            this.B = 3;
            return;
         case 3:
            this.B = 4;
            return;
         case 4:
            this.l = true;

            while (!this.g.isEmpty()) {
               minecraftClient.getNetworkHandler().sendPacket((Packet)this.g.remove(0));
            }

            this.l = false;
            this.B = 5;
            return;
         case 5:
            this.B = 6;
            return;
         case 6:
            if (this.C && this.D != null) {
               this.l = true;
               minecraftClient.getNetworkHandler().sendPacket(this.D);
               this.l = false;
            }

            this.B = 7;
            return;
         case 7:
            this.B = 8;
            return;
         case 8:
            this.d(keyBindingArray1);
            this.s();
            return;
         default:
            if (this.g.isEmpty() && !this.C) {
               if (flag2 || minecraftClient.currentScreen == null) {
                  this.d(keyBindingArray1);
               }
            } else {
               this.B = 1;
               this.c(keyBindingArray1);
            }
      }
   }

   private boolean p() {
      return InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.forwardKey.getDefaultKey().getCode())
         || InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.backKey.getDefaultKey().getCode())
         || InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.leftKey.getDefaultKey().getCode())
         || InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.rightKey.getDefaultKey().getCode());
   }

   private boolean q() {
      return minecraftClient.currentScreen instanceof HandledScreen;
   }

   private void c(KeyBinding[] keyBindingArray1) {
      for (KeyBinding keyBinding5 : keyBindingArray1) {
         keyBinding5.setPressed(false);
      }
   }

   private void d(KeyBinding[] keyBindingArray1) {
      for (KeyBinding keyBinding5 : keyBindingArray1) {
         keyBinding5.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), keyBinding5.getDefaultKey().getCode()));
      }
   }

   private void r() {
      this.g.clear();
      this.n = false;
      this.o = false;
      this.p = false;
      this.q = null;
      this.r = 0;
   }

   private void s() {
      this.g.clear();
      this.B = 0;
      this.C = false;
      this.D = null;
   }

   private void t() {
      if (!this.i) {
         Sprint.b(0);
         this.i = true;
      }
   }

   private void u() {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         this.l = true;

         try {
            for (ClickSlotC2SPacket clickSlotC2SPacket2 : this.g) {
               minecraftClient.getNetworkHandler().sendPacket(clickSlotC2SPacket2);
            }
         } finally {
            this.l = false;
         }

         this.g.clear();
      } else {
         this.g.clear();
      }
   }

   private void e(boolean flag1) {
      if (minecraftClient.player != null && minecraftClient.getNetworkHandler() != null) {
         this.u();
         if (flag1 && this.h != null) {
            this.l = true;

            try {
               minecraftClient.getNetworkHandler().sendPacket(this.h);
            } finally {
               this.l = false;
            }

            this.h = null;
         }
      } else {
         this.g.clear();
         this.h = null;
      }
   }

   public static void b(int intValue0) {
      GuiMove guiMove1 = ModuleManager.V;
      if (guiMove1 != null && guiMove1.isEnabled()) {
         guiMove1.f = Math.max(guiMove1.f, intValue0);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.e(true);
      if (this.i) {
         this.i = false;
         Sprint.p();
      }

      this.j = false;
      this.k = -1;
      this.l = false;
      this.f = 0;
      this.r();
      this.s();
   }
}
