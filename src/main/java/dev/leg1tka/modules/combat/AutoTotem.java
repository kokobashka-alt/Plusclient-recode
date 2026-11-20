package dev.leg1tka.modules.combat;


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
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPlayerSync;
import dev.leg1tka.api.events.implement.EventPlayerSyncEnd;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Formatting;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Vec3d;
public class AutoTotem extends Module {
   public static final AutoTotem c = new AutoTotem();
   private static int d;
   private static int e;
   private static int f;
   private static float g;
   private static float h;
   private static long i;
   private static String j;
   private static String k;
   private final BooleanSetting l = new BooleanSetting("Обходить Grim", true);
   private final ModeSetting m = new ModeSetting("Режим", "Grim", "Grim", "Polar");
   private final NumberSetting n = new NumberSetting("Здоровье", 6.0F, 1.0F, 20.0F, 0.5F);
   private final BooleanSetting o = new BooleanSetting("Сохранять зачар", true);
   private final BooleanSetting p = new BooleanSetting("Возвращать тотем", true);
   private final BooleanSetting q = new BooleanSetting("Свапать талисман", false);
   private final dev.leg1tka.settings.StringSetting r;
   private final MultiSetting s;
   private final NumberSetting t;
   private final NumberSetting u;
   private ItemStack v;
   private int w;
   private boolean x;
   private boolean y;
   private boolean z;
   private TotemState A;
   private int B;
   private long C;
   private int D;

   public AutoTotem() {
      super(
         "AutoTotem",
         "Автоматически берёт тотем в опасности",
         Category.COMBAT
      );
      dev.leg1tka.settings.StringSetting value10001 = new dev.leg1tka.settings.StringSetting("Введите название:", "Мрака", 32);
      BooleanSetting booleanSetting10002 = this.q;
      Objects.requireNonNull(this.q);
      this.r = value10001.a(createSupplier93(booleanSetting10002));
      this.s = new MultiSetting(
         "Доп триггеры",
         new BooleanSetting("Кристаллы", true),
         new BooleanSetting("Предикт кристаллов", true),
         new BooleanSetting("Якоря", true),
         new BooleanSetting("Булава", true),
         new BooleanSetting("Элитры", true),
         new BooleanSetting("Падения", true)
      );
      this.t = new NumberSetting("Здоровье на элитрах", 10.0F, 1.0F, 20.0F, 0.5F)
         .a(createSupplier94(this));
      this.u = new NumberSetting("Высота падения", 10.0F, 3.0F, 50.0F, 1.0F).a(createSupplier95(this));
      this.v = ItemStack.EMPTY;
      this.w = -1;
      this.A = TotemState.NONE;
      this.addSettings(new Setting[]{this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u});
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      this.p();
   }

   @EventLink(
      priority = -200
   )
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (this.A != TotemState.NONE) {
         eventMoveInput1.setForward(0.0F);
         eventMoveInput1.setStrafe(0.0F);
         eventMoveInput1.setJump(false);
      }
   }

   @EventLink(
      priority = -200
   )
   public void onSync(EventPlayerSync eventPlayerSync1) {
      if (this.A != TotemState.NONE && minecraftClient.player != null) {
         minecraftClient.player.setSprinting(false);
      }
   }

   @EventLink(
      priority = -200
   )
   public void onSyncEnd(EventPlayerSyncEnd eventPlayerSyncEnd1) {
      if (minecraftClient.player != null) {
         if (this.A != TotemState.NONE) {
            if (minecraftClient.player.age - this.B > 6) {
               this.A = TotemState.NONE;
            } else if (!this.y() && !minecraftClient.player.isSprinting() && !minecraftClient.player.hasVehicle()) {
               this.s();
            }
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE && eventPacket1.getPacket() instanceof EntityStatusS2CPacket packet) {
         if (packet.getStatus() == 35 && minecraftClient.player != null && packet.getEntity(minecraftClient.world) == minecraftClient.player) {
            this.p();
         }
      }
   }

   private void a(Vec3d vec3d1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (dev.leg1tka.api.utils.player.InventoryQueries.a(vec3d1) >= this.D()) {
            this.y = false;
            this.q();
            this.r();
         }
      }
   }

   private void p() {
      if (minecraftClient.player != null && minecraftClient.world != null && !this.F()) {
         if (this.z()) {
            this.q();
            this.r();
         }
      }
   }

   private void q() {
      this.C = System.currentTimeMillis();
      this.D = 0;
      this.z = this.y;
   }

   private void r() {
      if (this.A != TotemState.EQUIP && !this.I() && this.t() != -1) {
         this.a(TotemState.EQUIP);
      }
   }

   private void a(TotemState totemState1) {
      this.A = totemState1;
      this.B = minecraftClient.player.age;
      if (!this.l.getValue()) {
         this.s();
      }
   }

   private void s() {
      TotemState totemState1 = this.A;
      this.A = TotemState.NONE;
      if (totemState1 == TotemState.EQUIP) {
         this.v();
      } else if (totemState1 == TotemState.RETURN) {
         this.w();
      }
   }

   private int t() {
      String string1 = this.u();
      if (this.a(minecraftClient.player.getOffHandStack(), string1)) {
         return -1;
      } else {
         int intValue2 = this.c(string1);
         if (intValue2 != -1) {
            return intValue2;
         } else if (!this.F()) {
            return this.G();
         } else {
            return this.o.getValue() && this.b(minecraftClient.player.getOffHandStack()) ? this.H() : -1;
         }
      }
   }

   private String u() {
      return !this.q.getValue() ? "" : this.r.e().trim().toLowerCase(Locale.ROOT);
   }

   private boolean a(ItemStack itemStack1, String string2) {
      if (!string2.isEmpty() && itemStack1.isOf(Items.TOTEM_OF_UNDYING)) {
         String string3 = Formatting.strip(itemStack1.getName().getString());
         return string3 != null && string3.toLowerCase(Locale.ROOT).contains(string2);
      } else {
         return false;
      }
   }

   private int c(String string1) {
      if (string1.isEmpty()) {
         return -1;
      } else {
         for (int intValue2 = 9; intValue2 < 45; intValue2++) {
            if (this.a(minecraftClient.player.playerScreenHandler.getSlot(intValue2).getStack(), string1)) {
               return intValue2;
            }
         }

         return -1;
      }
   }

   private void v() {
      if (!this.I()) {
         int intValue1 = this.t();
         if (intValue1 != -1) {
            this.b(intValue1);
            this.c(intValue1);
         }
      }
   }

   private void b(int intValue1) {
      if (!this.x) {
         ItemStack itemStack2 = minecraftClient.player.getOffHandStack();
         this.v = itemStack2.isEmpty() ? ItemStack.EMPTY : itemStack2.copy();
         this.w = intValue1;
         this.x = true;
      }
   }

   private void w() {
      if (this.x && this.F() && !this.z() && !this.C()) {
         int intValue1 = this.x();
         if (intValue1 != -1) {
            this.c(intValue1);
         }

         this.x = false;
         this.v = ItemStack.EMPTY;
         this.w = -1;
      }
   }

   private int x() {
      if (this.w >= 9 && this.w < 45 && this.a(minecraftClient.player.playerScreenHandler.getSlot(this.w).getStack())) {
         return this.w;
      } else {
         for (int intValue1 = 9; intValue1 < 45; intValue1++) {
            if (this.a(minecraftClient.player.playerScreenHandler.getSlot(intValue1).getStack())) {
               return intValue1;
            }
         }

         return -1;
      }
   }

   private boolean a(ItemStack itemStack1) {
      return this.v.isEmpty() ? itemStack1.isEmpty() : ItemStack.areEqual(itemStack1, this.v);
   }

   private void c(int intValue1) {
      minecraftClient.interactionManager.clickSlot(0, intValue1, 40, SlotActionType.SWAP, minecraftClient.player);
      minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(0));
   }

   private boolean y() {
      PlayerInput playerInput1 = minecraftClient.player.input.playerInput;
      return playerInput1.forward() || playerInput1.backward() || playerInput1.left() || playerInput1.right() || playerInput1.jump();
   }

   private boolean z() {
      this.y = false;
      if (minecraftClient.player != null && minecraftClient.world != null && !minecraftClient.player.isInCreativeMode() && !minecraftClient.player.isSpectator()) {
         boolean flag1 = this.s.a("Булава") && dev.leg1tka.api.utils.player.InventoryQueries.c();
         float floatValue2 = this.A();
         if (floatValue2 <= this.B()) {
            return true;
         } else if (this.s.a("Элитры")
            && minecraftClient.player.isGliding()
            && minecraftClient.player.getEquippedStack(EquipmentSlot.CHEST).isOf(Items.ELYTRA)
            && floatValue2 <= this.t.f()) {
            return true;
         } else if (this.s.a("Падения") && dev.leg1tka.api.utils.player.InventoryQueries.c(this.u.f())) {
            return true;
         } else {
            float floatValue3 = this.D();
            if (this.s.a("Кристаллы") && dev.leg1tka.api.utils.player.InventoryQueries.a() >= floatValue3) {
               return true;
            } else if (this.s.a("Предикт кристаллов")
               && dev.leg1tka.api.utils.player.InventoryQueries.a(floatValue3) >= floatValue3) {
               return true;
            } else if (this.s.a("Якоря") && dev.leg1tka.api.utils.player.InventoryQueries.b(floatValue3) >= floatValue3) {
               return true;
            } else if (this.s.a("Булава") && dev.leg1tka.api.utils.player.InventoryQueries.d() >= floatValue3) {
               return true;
            } else {
               this.y = flag1;
               return flag1;
            }
         }
      } else {
         return false;
      }
   }

   private float A() {
      return minecraftClient.player.getHealth();
   }

   private float B() {
      return this.n.f();
   }

   private boolean C() {
      return this.A() <= this.B() + 1.0F;
   }

   private float D() {
      return Math.max(0.0F, minecraftClient.player.getHealth() + minecraftClient.player.getAbsorptionAmount() - 2.0F);
   }

   private boolean E() {
      return !this.s.a("Предикт кристаллов") || !dev.leg1tka.api.utils.player.InventoryQueries.b();
   }

   private boolean F() {
      return minecraftClient.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING);
   }

   private int G() {
      int intValue1 = -1;
      int intValue2 = -1;

      for (int intValue3 = 9; intValue3 < 45; intValue3++) {
         ItemStack itemStack4 = minecraftClient.player.playerScreenHandler.getSlot(intValue3).getStack();
         if (itemStack4.isOf(Items.TOTEM_OF_UNDYING)) {
            if (this.b(itemStack4)) {
               if (intValue2 == -1) {
                  intValue2 = intValue3;
               }
            } else if (intValue1 == -1) {
               intValue1 = intValue3;
            }
         }
      }

      if (this.o.getValue()) {
         return intValue1 != -1 ? intValue1 : intValue2;
      } else {
         return intValue2 != -1 ? intValue2 : intValue1;
      }
   }

   private int H() {
      for (int intValue1 = 9; intValue1 < 45; intValue1++) {
         ItemStack itemStack2 = minecraftClient.player.playerScreenHandler.getSlot(intValue1).getStack();
         if (itemStack2.isOf(Items.TOTEM_OF_UNDYING) && !this.b(itemStack2)) {
            return intValue1;
         }
      }

      return -1;
   }

   private boolean b(ItemStack itemStack1) {
      if (itemStack1.isEmpty() || !itemStack1.isOf(Items.TOTEM_OF_UNDYING)) {
         return false;
      } else if (itemStack1.hasEnchantments()) {
         return true;
      } else {
         AttributeModifiersComponent attributeModifiersComponent2 = (AttributeModifiersComponent)itemStack1.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
         return attributeModifiersComponent2 != null && !attributeModifiersComponent2.modifiers().isEmpty();
      }
   }

   private boolean I() {
      if (this.m.b("Polar") && minecraftClient.player.isUsingItem()) {
         UseAction useAction1 = minecraftClient.player.getActiveItem().getUseAction();
         return useAction1 == UseAction.EAT || useAction1 == UseAction.DRINK;
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.w = -1;
      this.v = ItemStack.EMPTY;
      this.x = false;
      this.A = TotemState.NONE;
      this.D = 0;
      super.onDisable();
   }



   private static Supplier createSupplier93(BooleanSetting booleanSetting0) {
      return booleanSetting0::getValue;
   }

   private static Supplier createSupplier94(AutoTotem autoTotem0) {
      return () -> autoTotem0.s.a("Элитры");
   }

   private static Supplier createSupplier95(AutoTotem autoTotem0) {
      return () -> autoTotem0.s.a("Падения");
   }

   private static Runnable createRunnable96(AutoTotem autoTotem0, Vec3d vec3d1) {
      return () -> autoTotem0.a(vec3d1);
   }

   private static Runnable createRunnable97(AutoTotem autoTotem0, EntityStatusS2CPacket entityStatusS2CPacket1) {
      return () -> {
         if (minecraftClient.world != null && minecraftClient.player != null && entityStatusS2CPacket1.getEntity(minecraftClient.world) == minecraftClient.player) {
            autoTotem0.x = false;
            autoTotem0.v = ItemStack.EMPTY;
            if (autoTotem0.A == TotemState.RETURN) {
               autoTotem0.A = TotemState.NONE;
            }

            autoTotem0.y = false;
            autoTotem0.q();
            minecraftClient.execute(createRunnable100(autoTotem0));
         }
      };
   }

   private static Runnable createRunnable98(AutoTotem autoTotem0) {
      return () -> minecraftClient.execute(createRunnable99(autoTotem0));
   }

   private static Runnable createRunnable99(AutoTotem autoTotem0) {
      return autoTotem0::p;
   }

   private static Runnable createRunnable100(AutoTotem autoTotem0) {
      return autoTotem0::p;
   }

   static enum TotemState {
      NONE,
      EQUIP,
      RETURN;

      private TotemState() {
      }
   }
   public static void dev_leg1tka_o_gm_initializer() {}
}
