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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;

public class CooldownHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   private final dev.leg1tka.api.animation.Animation f = new dev.leg1tka.api.animation.Animation(70.0F, 10.5F, dev.leg1tka.api.animation.Easings.h);
   private final Map<String, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry> g = new ConcurrentHashMap<>();
   private final Map<String, dev.leg1tka.api.animation.Animation> h = new LinkedHashMap<>();
   private boolean i = false;

   public void c(boolean flag1) {
      this.i = flag1;
   }

   public CooldownHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
      EventInvoker.register(this);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer a(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("nunito", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer b(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("icon", intValue1);
   }

   private dev.leg1tka.render.font.MsdfTextRenderer c(int intValue1) {
      return dev.leg1tka.render.font.FontCache.a("iconsminced", intValue1);
   }

   private dev.leg1tka.api.animation.Animation a(String string1) {
      return this.h.computeIfAbsent(string1, string0 -> new dev.leg1tka.api.animation.Animation(0.0F, 10.5F, dev.leg1tka.api.animation.Easings.h));
   }

   private String d(int intValue1) {
      int intValue2 = Math.max(0, intValue1);
      int intValue3 = intValue2 / 60;
      int intValue4 = intValue2 % 60;
      return intValue3 + ":" + (intValue4 < 10 ? "0" + intValue4 : String.valueOf(intValue4));
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE) {
         MinecraftClient minecraftClient2 = MinecraftClient.getInstance();
         if (minecraftClient2 != null && minecraftClient2.world != null) {
            if (eventPacket1.getPacket() instanceof CooldownUpdateS2CPacket cooldownUpdateS2CPacket3) {
               Item item9 = (Item)Registries.ITEM.get(cooldownUpdateS2CPacket3.cooldownGroup());
               if (item9 == Items.BOW
                  || item9 == Items.CROSSBOW
                  || item9 == Items.FIREWORK_ROCKET
                  || item9 == Items.WATER_BUCKET
                  || item9 == Items.HONEY_BLOCK
                  || item9 == Items.SLIME_BLOCK) {
                  return;
               }

               int intValue5 = cooldownUpdateS2CPacket3.cooldown();
               String string6 = "vanilla:" + item9.getTranslationKey();
               if (intValue5 == 0) {
                  this.g.remove(string6);
               } else {
                  long longValue7 = minecraftClient2.world.getTime();
                  this.g.put(string6, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry.a(string6, item9.getName().getString(), item9.getDefaultStack(), longValue7, (long)intValue5));
               }
            } else if (eventPacket1.getPacket() instanceof PlayerRespawnS2CPacket) {
               this.g.clear();
            }
         }
      }
   }

   @EventLink
   public void onUpdateCustom(EventUpdate eventUpdate1) {
      MinecraftClient minecraftClient2 = MinecraftClient.getInstance();
      if (minecraftClient2 != null && minecraftClient2.player != null) {
         ItemStack itemStack3 = minecraftClient2.player.getActiveItem();
         if (itemStack3 != null && !itemStack3.isEmpty() && minecraftClient2.player.getItemUseTime() >= itemStack3.getMaxUseTime(minecraftClient2.player)) {
            PotionContentsComponent potionContentsComponent4 = (PotionContentsComponent)itemStack3.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent4 != null && (potionContentsComponent4.getColor() == 33461 || potionContentsComponent4.getColor() == -515037)) {
               ItemStack itemStack5 = Items.POTION.getDefaultStack();
               itemStack5.set(
                  DataComponentTypes.POTION_CONTENTS,
                  new PotionContentsComponent(Optional.of(Potions.SWIFTNESS), Optional.of(potionContentsComponent4.getColor()), List.of(), Optional.empty())
               );
               long longValue6 = System.nanoTime();
               String string8 = "custom:Исцел";
               this.g.put(string8, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry.b(string8, "Исцел", itemStack5, longValue6, 10000000000L));
            }
         }
      }
   }

   @Override
   public void a(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      int intValue4;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue4 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue4 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      float floatValue5 = 70.0F;
      float floatValue6 = this.c(15).b("v");
      float floatValue7 = floatValue2 + 3.5F + (8.5F - floatValue6) / 2.0F;
      float floatValue8 = floatValue2 + 17.5F;
      float floatValue9 = floatValue8 - floatValue2 + this.a(13).b("Cooldowns") + 6.0F;
      if (floatValue9 > floatValue5) {
         floatValue5 = floatValue9;
      }

      int intValue10 = 0;
      float floatValue11 = 0.0F;
      MinecraftClient minecraftClient12 = MinecraftClient.getInstance();
      long longValue13 = minecraftClient12 != null && minecraftClient12.world != null ? minecraftClient12.world.getTime() : 0L;
      this.g.entrySet().removeIf(entry2x -> ((dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry)entry2x.getValue()).a(longValue13));
      List<Map.Entry<String, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry>> list15 = new ArrayList<>(this.g.entrySet());
      list15.sort((entry2x, entry3x) -> Float.compare(entry2x.getValue().b(longValue13), entry3x.getValue().b(longValue13)));
      LinkedHashMap<String, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry> linkedHashMap16 = new LinkedHashMap<>();

      for (Map.Entry<String, dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry> entry18 : list15) {
         linkedHashMap16.put(entry18.getKey(), entry18.getValue());
      }

      this.g.clear();
      this.g.putAll(linkedHashMap16);
      ArrayList<String> arrayList43 = new ArrayList<>(this.g.keySet());

      for (String string19 : arrayList43) {
         this.a(string19).animateTo(1.0F);
      }

      for (Map.Entry<String, dev.leg1tka.api.animation.Animation> entry48 : this.h.entrySet()) {
         if (!arrayList43.contains(entry48.getKey())) {
            ((dev.leg1tka.api.animation.Animation)entry48.getValue()).animateTo(0.0F);
         }
      }

      for (String string49 : this.h.keySet()) {
         dev.leg1tka.api.animation.Animation value20 = this.a(string49);
         float floatValue21 = value20.getValue();
         dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry cooldownEntry22 = (dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry)this.g.get(string49);
         if (floatValue21 > 0.01F && cooldownEntry22 != null) {
            intValue10++;
            String string23 = cooldownEntry22.b;
            if (string23.equalsIgnoreCase(
                  "Зачарованное золотое яблоко"
               )
               || cooldownEntry22.c.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
               string23 = "Чарка";
            }

            String string24 = this.d(cooldownEntry22.c(longValue13));
            float floatValue25 = this.a(12).b(string23);
            float floatValue26 = this.a(11).b(string24);
            float floatValue27 = 17.5F + floatValue25 + 6.0F + floatValue26 + 5.0F;
            if (floatValue27 > floatValue5) {
               floatValue5 = floatValue27;
            }

            floatValue11 += 12.5F * floatValue21;
         }
      }

      float floatValue47 = intValue10 == 0 ? 11.5F : 11.5F + floatValue11;
      this.f.animateTo(floatValue5);
      float floatValue50 = this.f.getValue();
      float floatValue51 = floatValue47;
      this.a(default1.getContext().getMatrices(), floatValue2, floatValue3, floatValue50, floatValue47, intValue4);
      this.c(15).d(default1.getContext().getMatrices(), "v", floatValue7, floatValue3 + 4.75F, intValue4);
      dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue2 + 14.0F, floatValue3 + 3.0F, 0.8F, 5.5F, 0.4F, dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, 140));
      this.a(13).d(default1.getContext().getMatrices(), "Cooldowns", floatValue8, floatValue3 + 3.5F, -1);
      float floatValue52 = 12.5F;

      for (String string54 : this.h.keySet()) {
         dev.leg1tka.api.animation.Animation value55 = this.a(string54);
         float floatValue56 = value55.getValue();
         dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry cooldownEntry57 = (dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry)this.g.get(string54);
         if (floatValue56 > 0.01F && cooldownEntry57 != null) {
            dev.leg1tka.render.ScissorStack.a();
            dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue50, (double)floatValue51);
            int intValue58 = (int)(255.0F * floatValue56);
            int intValue28 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue58);
            int intValue29 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, intValue58);
            String string30 = cooldownEntry57.b;
            if (string30.equalsIgnoreCase(
                  "Зачарованное золотое яблоко"
               )
               || cooldownEntry57.c.getItem() == Items.ENCHANTED_GOLDEN_APPLE) {
               string30 = "Чарка";
            }

            String string31 = this.d(cooldownEntry57.c(longValue13));
            if (!cooldownEntry57.c.isEmpty()) {
               MatrixStack matrixStack32 = default1.getContext().getMatrices();
               matrixStack32.push();
               matrixStack32.translate(floatValue2 + 3.0F, floatValue3 + floatValue52 + 1.25F, 0.0F);
               matrixStack32.scale(0.5625F, 0.5625F, 1.0F);
               default1.getContext().drawItem(cooldownEntry57.c, 0, 0);
               matrixStack32.pop();
            }

            float floatValue59 = cooldownEntry57.b(longValue13);
            float floatValue33 = floatValue2 + 3.5F;
            float floatValue34 = floatValue50 - 7.0F;
            float floatValue35 = floatValue3 + floatValue52 + 11.0F;
            dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue33, floatValue35, floatValue34, 1.0F, 0.5F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, (int)(80.0F * floatValue56)));
            dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue33, floatValue35, Math.max(floatValue34 * floatValue59, 1.0F), 1.0F, 0.5F, intValue29);
            float floatValue36 = floatValue2 + 14.0F;
            float floatValue37 = floatValue3 + floatValue52 + 3.0F;
            int intValue38 = dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, (int)(140.0F * floatValue56));
            dev.leg1tka.render.RenderPrimitives.b(default1.getContext().getMatrices(), floatValue36, floatValue37, 0.8F, 5.5F, 0.4F, intValue38);
            float floatValue39 = floatValue2 + 17.5F;
            float floatValue40 = floatValue3 + floatValue52 + 3.65F;
            this.a(12).d(default1.getContext().getMatrices(), string30, floatValue39, floatValue40, intValue28);
            float floatValue41 = this.a(11).b(string31);
            float floatValue42 = floatValue2 + floatValue50 - floatValue41 - 5.0F;
            this.a(11).d(default1.getContext().getMatrices(), string31, floatValue42, floatValue40, intValue29);
            floatValue52 += 12.5F * floatValue56;
            dev.leg1tka.render.ScissorStack.b();
            dev.leg1tka.render.ScissorStack.c();
         }
      }

      this.h.entrySet().removeIf(entry1x -> !arrayList43.contains(entry1x.getKey()) && ((dev.leg1tka.api.animation.Animation)entry1x.getValue()).getValue() <= 0.01F);
      this.e.c(floatValue50);
      this.e.d(floatValue51);
      super.a(default1);
   }

   static enum CooldownType {
      TICKS,
      REAL;

      private CooldownType() {
      }
   }

   static class CooldownEntry {
      final String a;
      final String b;
      final ItemStack c;
      final CooldownType d;
      final long e;
      final long f;

      private CooldownEntry(String string1, String string2, ItemStack itemStack3, CooldownType cooldownType4, long longValue5, long longValue7) {
         this.a = string1;
         this.b = string2;
         this.c = itemStack3;
         this.d = cooldownType4;
         this.e = longValue5;
         this.f = longValue7;
      }

      static dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry a(String string0, String string1, ItemStack itemStack2, long longValue3, long longValue5) {
         return new dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry(string0, string1, itemStack2, CooldownType.TICKS, longValue3, longValue5);
      }

      static dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry b(String string0, String string1, ItemStack itemStack2, long longValue3, long longValue5) {
         return new dev.leg1tka.render.hud.CooldownHudRenderer.CooldownEntry(string0, string1, itemStack2, CooldownType.REAL, longValue3, longValue5);
      }

      boolean a(long longValue1) {
         return switch (this.d) {
            case TICKS -> longValue1 >= this.e + this.f;
            case REAL -> System.nanoTime() >= this.e + this.f;
         };
      }

      float b(long longValue1) {
         long longValue3 = switch (this.d) {
            case TICKS -> Math.max(0L, this.e + this.f - longValue1);
            case REAL -> Math.max(0L, this.e + this.f - System.nanoTime());
         };
         return (float)longValue3 / (float)this.f;
      }

      int c(long longValue1) {
         long longValue3 = switch (this.d) {
            case TICKS -> Math.max(0L, this.e + this.f - longValue1);
            case REAL -> Math.max(0L, this.e + this.f - System.nanoTime());
         };
         return this.d == CooldownType.TICKS ? (int)(longValue3 / 20L) : (int)(longValue3 / 1000000000L);
      }
   }
}
