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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventBinding;
import dev.leg1tka.api.events.implement.EventBlockChange;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPotion;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.util.InputUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BundleContentsComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.ItemCooldownManager.Entry;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
public final class FTHelper extends Module {
   public static final FTHelper c = new FTHelper();
   private static int d;
   private static long e;
   private static long f;
   private static long g;
   private final BooleanSetting h = new BooleanSetting("Кулдауны противников", true);
   private final BooleanSetting i;
   private final ModeSetting j;
   private final BooleanSetting k;
   private final ModeSetting l;
   private final dev.leg1tka.settings.KeybindSetting m;
   private final dev.leg1tka.settings.KeybindSetting n;
   private final dev.leg1tka.settings.KeybindSetting o;
   private final dev.leg1tka.settings.KeybindSetting p;
   private final dev.leg1tka.settings.KeybindSetting q;
   private final dev.leg1tka.settings.KeybindSetting r;
   private final dev.leg1tka.settings.KeybindSetting s;
   private final dev.leg1tka.settings.KeybindSetting t;
   private final dev.leg1tka.settings.KeybindSetting u;
   private final dev.leg1tka.settings.KeybindSetting v;
   private final dev.leg1tka.settings.KeybindSetting w;
   private final dev.leg1tka.settings.KeybindSetting x;
   private final dev.leg1tka.settings.KeybindSetting y;
   private final dev.leg1tka.settings.KeybindSetting z;
   private final dev.leg1tka.settings.KeybindSetting A;
   private final dev.leg1tka.settings.KeybindSetting B;
   private final dev.leg1tka.settings.KeybindSetting C;
   private final dev.leg1tka.settings.KeybindSetting D;
   private final dev.leg1tka.settings.KeybindSetting E;
   private final dev.leg1tka.settings.KeybindSetting F;
   private final dev.leg1tka.settings.KeybindSetting G;
   private final dev.leg1tka.settings.KeybindSetting H;
   private final dev.leg1tka.settings.KeybindSetting I;
   private final ModeSetting J;
   private final ModeSetting K;
   private final Map L;
   private BuffState M;
   private FTBuff N;
   private final dev.leg1tka.api.utils.time.ElapsedTimer O;
   private ChargeState P;
   private int Q;
   private int R;
   private int S;
   private int T;
   private boolean U;
   private final dev.leg1tka.api.utils.time.ElapsedTimer V;

   public FTHelper() {
      super("FTHelper", "FTHelper", Category.COMBAT);
      BooleanSetting booleanSetting10001 = new BooleanSetting("Оповещение", true);
      BooleanSetting booleanSetting10002 = this.h;
      Objects.requireNonNull(this.h);
      this.i = booleanSetting10001.visibleWhen(createSupplier114(booleanSetting10002));
      this.j = new ModeSetting("Сервер", "FunTime", "FunTime", "SpookyTime", "HolyWorld");
      this.k = new BooleanSetting("Авто питч", false).visibleWhen(createSupplier115(this));
      this.l = new ModeSetting(
         "Режим арбалета",
         "По бинду",
         "По бинду",
         "Умный"
      );
      this.m = new dev.leg1tka.settings.KeybindSetting("Заряд ветра", -1).visibleWhen(createSupplier116(this));
      this.n = new dev.leg1tka.settings.KeybindSetting("Огненный смерч", -1).visibleWhen(createSupplier117(this));
      this.o = new dev.leg1tka.settings.KeybindSetting("Дезориентация", -1).visibleWhen(createSupplier118(this));
      this.p = new dev.leg1tka.settings.KeybindSetting("Трапка", -1).visibleWhen(createSupplier119(this));
      this.q = new dev.leg1tka.settings.KeybindSetting("Пласт", -1).visibleWhen(createSupplier120(this));
      this.r = new dev.leg1tka.settings.KeybindSetting("Явная пыль", -1).visibleWhen(createSupplier121(this));
      this.s = new dev.leg1tka.settings.KeybindSetting("Божья аура", -1).visibleWhen(createSupplier122(this));
      this.t = new dev.leg1tka.settings.KeybindSetting("Снежок", -1).visibleWhen(createSupplier123(this));
      this.u = new dev.leg1tka.settings.KeybindSetting("Взрывная трапка", -1).visibleWhen(createSupplier124(this));
      this.v = new dev.leg1tka.settings.KeybindSetting("Обычная трапка", -1).visibleWhen(createSupplier125(this));
      this.w = new dev.leg1tka.settings.KeybindSetting("Стан", -1).visibleWhen(createSupplier126(this));
      this.x = new dev.leg1tka.settings.KeybindSetting("Взрывная штучка", -1).visibleWhen(createSupplier127(this));
      this.y = new dev.leg1tka.settings.KeybindSetting("Ком снега", -1).visibleWhen(createSupplier128(this));
      this.z = new dev.leg1tka.settings.KeybindSetting("Арбалет", -1);
      this.A = new dev.leg1tka.settings.KeybindSetting("Перезарядка", -1).visibleWhen(createSupplier129(this));
      this.B = new dev.leg1tka.settings.KeybindSetting("Зелье ассасина", -1).visibleWhen(createSupplier130(this));
      this.C = new dev.leg1tka.settings.KeybindSetting("Зелье палладина", -1).visibleWhen(createSupplier131(this));
      this.D = new dev.leg1tka.settings.KeybindSetting("Снотворное", -1).visibleWhen(createSupplier132(this));
      this.E = new dev.leg1tka.settings.KeybindSetting("Зелье гнева", -1).visibleWhen(createSupplier133(this));
      this.F = new dev.leg1tka.settings.KeybindSetting("Хлопушка", -1).visibleWhen(createSupplier134(this));
      this.G = new dev.leg1tka.settings.KeybindSetting("Зелье радиации", -1).visibleWhen(createSupplier135(this));
      this.H = new dev.leg1tka.settings.KeybindSetting("Святая вода", -1).visibleWhen(createSupplier136(this));
      this.I = new dev.leg1tka.settings.KeybindSetting("Разбафф", -1).visibleWhen(createSupplier137(this));
      this.J = new ModeSetting(
            "Дебафф",
            "Снотворное",
            "Снотворное",
            "Радиация"
         )
         .a(createSupplier138(this));
      this.K = new ModeSetting(
            "Бафф",
            "Зелье гнева",
            "Зелье гнева",
            "Зелье ассасина"
         )
         .a(createSupplier139(this));
      this.L = new HashMap();
      this.M = BuffState.IDLE;
      this.O = new dev.leg1tka.api.utils.time.ElapsedTimer();
      this.P = ChargeState.IDLE;
      this.Q = -1;
      this.R = -1;
      this.S = -1;
      this.T = -1;
      this.V = new dev.leg1tka.api.utils.time.ElapsedTimer();
      this.addSettings(
         new Setting[]{
            this.h,
            this.i,
            this.j,
            this.k,
            this.l,
            this.m,
            this.n,
            this.o,
            this.p,
            this.q,
            this.r,
            this.s,
            this.t,
            this.u,
            this.v,
            this.w,
            this.x,
            this.y,
            this.z,
            this.A,
            this.B,
            this.C,
            this.D,
            this.E,
            this.F,
            this.G,
            this.H,
            this.I,
            this.J,
            this.K
         }
      );
      EventInvoker.register(this);
   }

   @EventLink
   public void onKey(EventBinding eventBinding1) {
      this.b(eventBinding1.getKey());
   }

   private void b(int intValue1) {
      if (this.l.b("Умный") && intValue1 == this.A.getKey()) {
         if (this.P == ChargeState.IDLE) {
            if (dev.leg1tka.api.utils.player.InventorySelection.a().b()) {
               return;
            }

            dev.leg1tka.api.utils.text.ClientChat.a(
               "FTHelper", "Начинается перезарядка..."
            );
            this.P = ChargeState.FINDING_ARROW;
         } else {
            dev.leg1tka.api.utils.text.ClientChat.a("FTHelper", "Остановка перезарядки!");
            this.x();
         }
      } else if (intValue1 == this.z.getKey()) {
         if (this.l.b("Умный")) {
            this.a(this.z.getName(), createPredicate140(), false);
         } else {
            this.a(this.z.getName(), Items.CROSSBOW);
         }
      }
   }



   public void onPacket(EventPacket eventPacket1) {
   }

   @EventLink
   public void onBlockChange(EventBlockChange eventBlockChange1) {
      dev.leg1tka.network.bots.BotSoundEvents.a(eventBlockChange1);
   }

   @EventLink
   public void onPotion(EventPotion eventPotion1) {
      dev.leg1tka.network.bots.BotSoundEvents.a(eventPotion1);
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null) return;
      this.v();
      this.w();
   }

   public BooleanSetting p() {
      return this.h;
   }

   public BooleanSetting q() {
      return this.i;
   }

   public dev.leg1tka.settings.KeybindSetting r() {
      return this.m;
   }

   private boolean s() {
      return this.j.b("HolyWorld");
   }

   private dev.leg1tka.api.utils.player.InventorySelection.ServerMode t() {
      if (this.j.b("SpookyTime")) {
         return dev.leg1tka.api.utils.player.InventorySelection.ServerMode.SPOOKYTIME;
      } else {
         return this.j.b("HolyWorld") ? dev.leg1tka.api.utils.player.InventorySelection.ServerMode.HOLYWORLD : dev.leg1tka.api.utils.player.InventorySelection.ServerMode.FUNTIME;
      }
   }

   private boolean a(String string1, Item item2) {
      return this.a(string1, createPredicate141(item2), false);
   }

   private boolean a(String string1, Item item2, String string3) {
      return this.a(string1, createPredicate142(item2, string3), false);
   }

   private boolean a(FTBuff fTBuff1) {
      boolean flag2 = this.k.getValue() && fTBuff1 != FTBuff.SLEEPING && fTBuff1 != FTBuff.RADIATION;
      return this.a(fTBuff1.b, createPredicate143(this, fTBuff1), flag2);
   }

   private boolean a(String string1, Predicate<ItemStack> predicate2, boolean flag3) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         dev.leg1tka.api.utils.player.InventorySelection value4 = dev.leg1tka.api.utils.player.InventorySelection.a();
         if (!value4.b() && this.P == ChargeState.IDLE) {
            ItemStack itemStack5 = minecraftClient.player.getOffHandStack();
            if (predicate2.test(itemStack5)) {
               if (this.a(itemStack5)) {
                  return false;
               } else {
                  dev.leg1tka.network.bots.BotSoundEvents.a(itemStack5);
                  dev.leg1tka.api.utils.player.InventorySelection.ServerMode serverMode11 = this.t();
                  if (serverMode11 == dev.leg1tka.api.utils.player.InventorySelection.ServerMode.SPOOKYTIME) {
                     if (flag3) {
                        dev.leg1tka.api.utils.player.InventorySelection.c();
                     }

                     value4.a(predicate2, itemStack5.copy(), serverMode11, flag3);
                  } else {
                     value4.a(flag3);
                  }

                  return true;
               }
            } else {
               for (int intValue6 = 35; intValue6 >= 0; intValue6--) {
                  ItemStack itemStack7 = minecraftClient.player.getInventory().getStack(intValue6);
                  if (predicate2.test(itemStack7)) {
                     if (this.a(itemStack7)) {
                        return false;
                     }

                     if (flag3) {
                        dev.leg1tka.api.utils.player.InventorySelection.c();
                     }

                     dev.leg1tka.network.bots.BotSoundEvents.a(itemStack7);
                     value4.a(predicate2, itemStack7.copy(), this.t(), flag3);
                     return true;
                  }
               }

               for (int intValue10 = 35; intValue10 >= 0; intValue10--) {
                  ItemStack itemStack12 = minecraftClient.player.getInventory().getStack(intValue10);
                  int intValue8 = dev.leg1tka.api.utils.player.InventorySelection.a(itemStack12, predicate2);
                  if (intValue8 != -1) {
                     ItemStack itemStack9 = ((BundleContentsComponent)itemStack12.get(DataComponentTypes.BUNDLE_CONTENTS)).get(intValue8);
                     if (this.a(itemStack9)) {
                        return false;
                     }

                     if (!this.u()) {
                        dev.leg1tka.render.hud.NotificationQueue.a(
                           "Нет свободного слота под предмет из мешка",
                           itemStack9
                        );
                        return false;
                     }

                     if (flag3) {
                        dev.leg1tka.api.utils.player.InventorySelection.c();
                     }

                     dev.leg1tka.network.bots.BotSoundEvents.a(itemStack9);
                     value4.a(predicate2, itemStack9.copy(), this.t(), flag3);
                     return true;
                  }
               }

               dev.leg1tka.render.hud.NotificationQueue.a(createString144(string1), "!");
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean u() {
      for (int intValue1 = 0; intValue1 <= 35; intValue1++) {
         if (minecraftClient.player.getInventory().getStack(intValue1).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private boolean a(ItemStack itemStack1) {
      ItemCooldownManager itemCooldownManager2 = minecraftClient.player.getItemCooldownManager();
      long longValue3 = dev.leg1tka.network.bots.BotSoundEvents.b(itemStack1);
      if (!itemCooldownManager2.isCoolingDown(itemStack1) && longValue3 <= 0L) {
         return false;
      } else {
         Identifier identifier5 = itemCooldownManager2.getGroup(itemStack1);
         long longValue6 = System.currentTimeMillis();
         Long long8 = (Long)this.L.get(identifier5);
         if (long8 != null && longValue6 - long8 < 500L) {
            return true;
         } else {
            this.L.put(identifier5, longValue6);
            Entry entry9 = (Entry)itemCooldownManager2.entries.get(identifier5);
            int intValue10 = entry9 == null ? 0 : Math.max(0, entry9.endTick() - itemCooldownManager2.tick);
            float floatValue11 = Math.max((float)intValue10 / 20.0F, (float)longValue3 / 1000.0F);
            String string12 = String.format(Locale.forLanguageTag("ru"), "%.1fs", floatValue11);
            String string13 = Formatting.strip(itemStack1.getName().getString());
            dev.leg1tka.api.utils.text.ClientChat.a(
               "FTHelper",
               (Text)Text.literal(string13)
                  .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16777215)))
                  .append(Text.literal(" в КД ещё ").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(13158600))))
                  .append(Text.literal(string12).setStyle(Style.EMPTY.withColor(Formatting.RED)))
            );
            dev.leg1tka.render.hud.NotificationQueue.a(createString145(string13, string12), itemStack1);
            return true;
         }
      }
   }

   private void v() {
      if (this.M != BuffState.IDLE) {
         if (this.M == BuffState.WAITING_DEBUFF) {
            if (!this.C()) {
               this.M = BuffState.WAITING_AURA;
               this.O.a();
            }
         } else if (this.M == BuffState.WAITING_AURA) {
            if (this.O.a(500L)) {
               if (!this.D()) {
                  if (!this.a(this.N)) {
                     this.F();
                  } else {
                     this.M = BuffState.WAITING_BUFF_ACTION;
                  }
               } else if (!this.a(this.s.getName(), Items.PHANTOM_MEMBRANE)) {
                  this.F();
               } else {
                  this.M = BuffState.WAITING_AURA_ACTION;
               }
            }
         } else if (this.M == BuffState.WAITING_AURA_ACTION) {
            if (!this.C()) {
               this.M = BuffState.WAITING_BUFF;
               this.O.a();
            }
         } else if (this.M == BuffState.WAITING_BUFF) {
            if (this.O.a(100L)) {
               if (!this.a(this.N)) {
                  this.F();
               } else {
                  this.M = BuffState.WAITING_BUFF_ACTION;
               }
            }
         } else {
            if (!this.C()) {
               this.F();
            }
         }
      }
   }

   private void w() {
      if (this.P != ChargeState.IDLE && minecraftClient.player != null) {
         if (this.P == ChargeState.FINDING_ARROW) {
            int intValue1 = this.z();
            if (intValue1 == -1) {
               dev.leg1tka.api.utils.text.ClientChat.a(
                  "FTHelper",
                  "Световая стрела не найдена!"
               );
               this.P = ChargeState.IDLE;
               return;
            }

            this.Q = intValue1;
            this.P = ChargeState.SWAPPING_ARROW;
         } else if (this.P == ChargeState.SWAPPING_ARROW) {
            if (this.Q != 44) {
               this.a(this.Q, 8);
            }

            this.P = ChargeState.FINDING_CROSSBOW;
         } else if (this.P == ChargeState.FINDING_CROSSBOW) {
            int intValue3 = this.A();
            if (intValue3 == -1) {
               this.P = ChargeState.RESTORING_ARROW;
               return;
            }

            this.R = intValue3;
            this.S = this.c(8);
            this.P = ChargeState.SWAPPING_CROSSBOW;
         } else if (this.P == ChargeState.SWAPPING_CROSSBOW) {
            this.G();
            this.T = minecraftClient.player.getInventory().selectedSlot;
            this.a(this.R, this.S);
            this.d(this.S);
            this.P = ChargeState.START_CHARGING;
         } else if (this.P == ChargeState.START_CHARGING) {
            minecraftClient.options.useKey.setPressed(true);
            this.a(Hand.MAIN_HAND);
            this.V.a();
            this.P = ChargeState.CHARGING;
         } else if (this.P == ChargeState.CHARGING) {
            ItemStack itemStack4 = minecraftClient.player.getInventory().getStack(this.S);
            if (itemStack4.getItem() instanceof CrossbowItem) {
               float floatValue2 = (float)CrossbowItem.getPullTime(itemStack4, minecraftClient.player) * 50.0F;
               if (this.V.a((long)floatValue2 + 50L)) {
                  minecraftClient.options.useKey.setPressed(false);
                  minecraftClient.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
                  this.V.a();
                  this.P = ChargeState.WAITING_AFTER_CHARGE;
               }
            } else {
               this.x();
            }
         } else if (this.P == ChargeState.WAITING_AFTER_CHARGE) {
            if (this.V.a(500L)) {
               this.P = ChargeState.RESTORING_CROSSBOW;
            }
         } else if (this.P == ChargeState.RESTORING_CROSSBOW) {
            this.I();
            this.a(this.R, this.S);
            this.H();
            this.P = ChargeState.FINDING_CROSSBOW;
         } else if (this.P == ChargeState.RESTORING_ARROW) {
            if (this.Q != -1 && this.Q != 44) {
               this.a(this.Q, 8);
            }

            dev.leg1tka.api.utils.text.ClientChat.a("FTHelper", "Все арбалеты заряжены!");
            this.y();
         }
      }
   }

   private void x() {
      if (minecraftClient.player == null) {
         this.y();
      } else {
         if (this.P == ChargeState.START_CHARGING || this.P == ChargeState.CHARGING || this.P == ChargeState.WAITING_AFTER_CHARGE) {
            minecraftClient.options.useKey.setPressed(false);
            minecraftClient.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
            this.I();
            this.a(this.R, this.S);
            this.H();
         }

         if (this.P != ChargeState.IDLE && this.P != ChargeState.FINDING_ARROW && this.Q != -1 && this.Q != 44) {
            this.a(this.Q, 8);
         }

         this.y();
      }
   }

   private void y() {
      this.P = ChargeState.IDLE;
      this.Q = -1;
      this.R = -1;
      this.S = -1;
      this.T = -1;
      this.V.a();
      if (minecraftClient.player != null) {
         minecraftClient.options.useKey.setPressed(false);
      }
   }

   private int z() {
      if (minecraftClient.player != null && minecraftClient.player.currentScreenHandler != null) {
         for (int intValue1 = 9; intValue1 < 45; intValue1++) {
            ItemStack itemStack2 = minecraftClient.player.currentScreenHandler.getSlot(intValue1).getStack();
            if (itemStack2 != null
               && itemStack2.isOf(Items.TIPPED_ARROW)
               && itemStack2.getName().getString().toLowerCase().contains("световая")) {
               return intValue1;
            }
         }

         ItemStack itemStack3 = minecraftClient.player.currentScreenHandler.getSlot(45).getStack();
         return itemStack3 != null
               && itemStack3.isOf(Items.TIPPED_ARROW)
               && itemStack3.getName().getString().toLowerCase().contains("световая")
            ? 45
            : -1;
      } else {
         return -1;
      }
   }

   private int A() {
      if (minecraftClient.player != null && minecraftClient.player.currentScreenHandler != null) {
         for (int intValue1 = 9; intValue1 < 36; intValue1++) {
            ItemStack itemStack2 = minecraftClient.player.currentScreenHandler.getSlot(intValue1).getStack();
            if (itemStack2 != null && itemStack2.getItem() instanceof CrossbowItem && !CrossbowItem.isCharged(itemStack2)) {
               return intValue1;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private void a(int intValue1, int intValue2) {
      if (minecraftClient.player != null && minecraftClient.player.currentScreenHandler != null) {
         int intValue3 = minecraftClient.player.currentScreenHandler.syncId;
         minecraftClient.interactionManager.clickSlot(intValue3, intValue1, intValue2, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(intValue3));
      }
   }

   private int c(int intValue1) {
      for (int intValue2 = 0; intValue2 < 9; intValue2++) {
         if (intValue2 != intValue1 && minecraftClient.player.getInventory().getStack(intValue2).isEmpty()) {
            return intValue2;
         }
      }

      return intValue1 == 8 ? 7 : 8;
   }

   private void B() {
      if (minecraftClient.player != null && !this.E() && !this.C()) {
         FTBuff fTBuff1 = this.J.b("Радиация") ? FTBuff.RADIATION : FTBuff.SLEEPING;
         FTBuff fTBuff2 = this.K.b("Зелье ассасина") ? FTBuff.ASSASSIN : FTBuff.WRATH;
         if (this.a(fTBuff1)) {
            this.N = fTBuff2;
            this.O.a();
            this.M = BuffState.WAITING_DEBUFF;
         }
      }
   }

   private boolean a(ItemStack itemStack1, FTBuff fTBuff2) {
      if (itemStack1 != null && !itemStack1.isEmpty() && itemStack1.isOf(Items.SPLASH_POTION)) {
         String string3 = this.c(itemStack1.getName().getString());
         if (!string3.isEmpty() && string3.contains(this.c(fTBuff2.b))) {
            return true;
         } else {
            PotionContentsComponent potionContentsComponent4 = (PotionContentsComponent)itemStack1.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent4 == null) {
               return false;
            } else {
               HashMap hashMap5 = new HashMap();

               for (StatusEffectInstance statusEffectInstance7 : potionContentsComponent4.getEffects()) {
                  hashMap5.put(statusEffectInstance7.getEffectType(), statusEffectInstance7.getAmplifier());
               }

               if (hashMap5.size() != fTBuff2.a.size()) {
                  return false;
               } else {
                  for (FTHelper.a value10 : fTBuff2.a) {
                     Integer integer8 = (Integer)hashMap5.get(value10.effect());
                     if (integer8 == null || integer8 != value10.amplifier()) {
                        return false;
                     }
                  }

                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private String c(String string1) {
      return string1 == null ? "" : string1.toLowerCase().replace("[+]", "").replace("[-]", "").replace("[*]", "").replace("[#]", "").replaceAll("\\s+", " ").trim();
   }

   private boolean C() {
      return this.P != ChargeState.IDLE || dev.leg1tka.api.utils.player.InventorySelection.a().b();
   }

   private boolean D() {
      if (minecraftClient.player == null) {
         return false;
      } else {
         StatusEffectInstance statusEffectInstance1 = minecraftClient.player.getStatusEffect(StatusEffects.WEAKNESS);
         return statusEffectInstance1 != null && statusEffectInstance1.getAmplifier() >= 1;
      }
   }

   private boolean E() {
      return this.M != BuffState.IDLE;
   }

   private void F() {
      this.M = BuffState.IDLE;
      this.N = null;
      this.O.a();
   }

   private void a(Hand hand1) {
      dev.leg1tka.rotation.RotationStorage rotationStorage2 = AspectDLC.INSTANCE.rotationStorage;
      float floatValue3 = rotationStorage2 != null && rotationStorage2.b() && rotationStorage2.k() != null ? rotationStorage2.k().getYaw() : minecraftClient.player.getYaw();
      float floatValue4 = rotationStorage2 != null && rotationStorage2.b() && rotationStorage2.k() != null ? rotationStorage2.k().getPitch() : minecraftClient.player.getPitch();
      minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, createSequencedPacketCreator146(hand1, floatValue3, floatValue4));
   }

   private void G() {
      minecraftClient.options.sprintKey.setPressed(false);
      minecraftClient.player.setSprinting(false);
      if (!this.U) {
         this.U = true;
         minecraftClient.getNetworkHandler().sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
         if (Sprint.c.isEnabled()) {
            minecraftClient.options.sprintKey.setPressed(false);
         }
      }
   }

   private void H() {
      this.U = false;
      minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
   }

   private void d(int intValue1) {
      if (minecraftClient.player != null && intValue1 >= 0) {
         minecraftClient.player.getInventory().selectedSlot = intValue1;
         minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue1));
      }
   }

   private void I() {
      if (minecraftClient.player != null && this.T >= 0) {
         this.d(this.T);
      }
   }

   @Override
   public void onDisable() {
      this.x();
      this.F();
      this.L.clear();
      if (minecraftClient.player != null) {
         minecraftClient.options.sprintKey.setPressed(InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), minecraftClient.options.sprintKey.getDefaultKey().getCode()));
      }

      dev.leg1tka.network.bots.BotSoundEvents.a();
      super.onDisable();
   }



   private static Supplier createSupplier114(BooleanSetting booleanSetting0) {
      return booleanSetting0::getValue;
   }

   private static Supplier createSupplier115(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier116(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier117(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier118(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier119(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier120(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier121(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier122(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier123(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier124(FTHelper fTHelper0) {
      return fTHelper0::s;
   }

   private static Supplier createSupplier125(FTHelper fTHelper0) {
      return fTHelper0::s;
   }

   private static Supplier createSupplier126(FTHelper fTHelper0) {
      return fTHelper0::s;
   }

   private static Supplier createSupplier127(FTHelper fTHelper0) {
      return fTHelper0::s;
   }

   private static Supplier createSupplier128(FTHelper fTHelper0) {
      return fTHelper0::s;
   }

   private static Supplier createSupplier129(FTHelper fTHelper0) {
      return () -> fTHelper0.l.b("Умный");
   }

   private static Supplier createSupplier130(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier131(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier132(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier133(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier134(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier135(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier136(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier137(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier138(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Supplier createSupplier139(FTHelper fTHelper0) {
      return () -> !fTHelper0.s();
   }

   private static Predicate<ItemStack> createPredicate140() {
      return (ItemStack itemStack0) -> itemStack0.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack0);
   }

   private static Predicate<ItemStack> createPredicate141(Item item0) {
      return (ItemStack itemStack1) -> itemStack1.isOf(item0);
   }

   private static Predicate<ItemStack> createPredicate142(Item item0, String string1) {
      return (ItemStack itemStack2) -> itemStack2.isOf(item0) && Formatting.strip(itemStack2.getName().getString()).toLowerCase(Locale.ROOT).contains(string1);
   }

   private static Predicate<ItemStack> createPredicate143(FTHelper fTHelper0, FTBuff fTBuff1) {
      return (ItemStack itemStack2) -> fTHelper0.a(itemStack2, fTBuff1);
   }

   private static String createString144(String string0) {
      return string0 + " нет в инвентаре";
   }

   private static String createString145(String string0, String string1) {
      return string0 + " в КД ещё " + string1;
   }

   private static SequencedPacketCreator createSequencedPacketCreator146(Hand hand0, float floatValue1, float floatValue2) {
      return intValue3 -> new PlayerInteractItemC2SPacket(hand0, intValue3, floatValue1, floatValue2);
   }

   static record a(RegistryEntry<StatusEffect> effect, int amplifier)  {

}

   static enum FTBuff {
      ASSASSIN(
         List.of(
            new FTHelper.a(StatusEffects.STRENGTH, 3), new FTHelper.a(StatusEffects.SPEED, 2), new FTHelper.a(StatusEffects.HASTE, 0), new FTHelper.a(StatusEffects.INSTANT_DAMAGE, 1)
         ),
         "Зелье ассасина"
      ),
      PALADIN(
         List.of(
            new FTHelper.a(StatusEffects.RESISTANCE, 0),
            new FTHelper.a(StatusEffects.FIRE_RESISTANCE, 0),
            new FTHelper.a(StatusEffects.HEALTH_BOOST, 2),
            new FTHelper.a(StatusEffects.INVISIBILITY, 2)
         ),
         "Зелье палладина"
      ),
      SLEEPING(
         List.of(
            new FTHelper.a(StatusEffects.WEAKNESS, 1),
            new FTHelper.a(StatusEffects.MINING_FATIGUE, 1),
            new FTHelper.a(StatusEffects.WITHER, 1),
            new FTHelper.a(StatusEffects.BLINDNESS, 0)
         ),
         "Снотворное"
      ),
      WRATH(List.of(new FTHelper.a(StatusEffects.STRENGTH, 4), new FTHelper.a(StatusEffects.SLOWNESS, 3)), "Зелье гнева"),
      CRACKER(
         List.of(
            new FTHelper.a(StatusEffects.SLOWNESS, 8), new FTHelper.a(StatusEffects.SPEED, 4), new FTHelper.a(StatusEffects.BLINDNESS, 9), new FTHelper.a(StatusEffects.GLOWING, 0)
         ),
         "Хлопушка"
      ),
      RADIATION(
         List.of(
            new FTHelper.a(StatusEffects.POISON, 1),
            new FTHelper.a(StatusEffects.WITHER, 1),
            new FTHelper.a(StatusEffects.SLOWNESS, 2),
            new FTHelper.a(StatusEffects.HUNGER, 4),
            new FTHelper.a(StatusEffects.GLOWING, 0)
         ),
         "Зелье радиации"
      ),
      HOLY_WATER(
         List.of(new FTHelper.a(StatusEffects.REGENERATION, 1), new FTHelper.a(StatusEffects.INVISIBILITY, 1), new FTHelper.a(StatusEffects.INSTANT_HEALTH, 1)),
         "Святая вода"
      );

      final List<FTHelper.a> a;
      final String b;

      private FTBuff(List<FTHelper.a> list3, String string4) {
         this.a = list3;
         this.b = string4;
      }
   }

   static enum BuffState {
      IDLE,
      WAITING_DEBUFF,
      WAITING_AURA,
      WAITING_AURA_ACTION,
      WAITING_BUFF,
      WAITING_BUFF_ACTION;

      private BuffState() {
      }
   }

   static enum ChargeState {
      IDLE,
      FINDING_ARROW,
      SWAPPING_ARROW,
      FINDING_CROSSBOW,
      SWAPPING_CROSSBOW,
      START_CHARGING,
      CHARGING,
      WAITING_AFTER_CHARGE,
      RESTORING_CROSSBOW,
      RESTORING_ARROW;

      private ChargeState() {
      }
   }
   public static void dev_leg1tka_o_gr_initializer() {}
}
