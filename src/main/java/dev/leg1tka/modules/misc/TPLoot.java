package dev.leg1tka.modules.misc;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.OnGroundOnly;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.util.math.Vec3d;

public final class TPLoot extends Module {
   public static TPLoot c = new TPLoot();
   private final NumberSetting d = new NumberSetting("Дистанция", 10.0F, 3.0F, 50.0F, 1.0F);
   private final NumberSetting e = new NumberSetting("Задержка лута", 500.0F, 100.0F, 5000.0F, 50.0F);
   private final ModeSetting f = new ModeSetting(
      "После лута",
      "Возвращаться",
      "Возвращаться",
      "Тепаться на спавн"
   );
   private final NumberSetting g = new NumberSetting(
      "Задержка действия", 1000.0F, 200.0F, 10000.0F, 100.0F
   );
   private final dev.leg1tka.api.utils.time.Stopwatch h = new dev.leg1tka.api.utils.time.Stopwatch();
   private final dev.leg1tka.api.utils.time.Stopwatch i = new dev.leg1tka.api.utils.time.Stopwatch();
   private Vec3d j = null;
   private boolean k = false;
   private static final List l = List.of(
      Items.NETHERITE_SWORD,
      Items.NETHERITE_HELMET,
      Items.NETHERITE_CHESTPLATE,
      Items.NETHERITE_LEGGINGS,
      Items.NETHERITE_BOOTS,
      Items.PLAYER_HEAD,
      Items.GOLDEN_APPLE,
      Items.ENCHANTED_GOLDEN_APPLE,
      Items.END_CRYSTAL,
      Items.TOTEM_OF_UNDYING,
      Items.ELYTRA
   );

   public TPLoot() {
      super(
         "TPLoot",
         "Телепортирует к ресурсам",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g});
   }

   @EventLink
   public void onTick(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.k) {
            if (this.i.hasElapsed((long)this.g.e().floatValue())) {
               if (this.f.b("Возвращаться") && this.j != null) {
                  this.a(this.j);
                  dev.leg1tka.api.utils.text.ClientChat.a(
                     "TpLoot: возврат на исходную позицию"
                  );
               }

               if (this.f.b("Тепаться на спавн")) {
                  minecraftClient.player.networkHandler.sendChatCommand("spawn");
                  dev.leg1tka.api.utils.text.ClientChat.a("TpLoot: выполнен /spawn");
               }

               this.k = false;
               this.j = null;
               this.h.reset();
            }
         } else if (this.h.hasElapsed((long)this.e.e().floatValue())) {
            ItemEntity itemEntity2 = this.p();
            if (itemEntity2 != null) {
               this.j = minecraftClient.player.getPos();
               Vec3d vec3d3 = itemEntity2.getPos();
               this.a(vec3d3);
               ItemStack itemStack4 = itemEntity2.getStack();
               dev.leg1tka.api.utils.text.ClientChat.a("TpLoot: подобран " + itemStack4.getName().getString());
               this.h.reset();
               this.k = true;
               this.i.reset();
            }
         }
      }
   }

   private ItemEntity p() {
      double doubleValue1 = this.d.e().doubleValue();
      ItemEntity itemEntity3 = null;
      double doubleValue4 = Double.MAX_VALUE;

      for (Entity entity7 : minecraftClient.world.getEntities()) {
         if (entity7 instanceof ItemEntity) {
            ItemEntity itemEntity8 = (ItemEntity)entity7;
            ItemStack itemStack9 = itemEntity8.getStack();
            if (this.a(itemStack9.getItem())) {
               double doubleValue10 = minecraftClient.player.squaredDistanceTo(entity7);
               if (!(doubleValue10 > doubleValue1 * doubleValue1) && doubleValue10 < doubleValue4) {
                  doubleValue4 = doubleValue10;
                  itemEntity3 = itemEntity8;
               }
            }
         }
      }

      return itemEntity3;
   }

   private boolean a(Item item1) {
      return l.contains(item1);
   }

   private void a(Vec3d vec3d1) {
      int intValue2 = (int)Math.ceil(minecraftClient.player.getPos().distanceTo(vec3d1) / 10.0);
      intValue2 = Math.max(intValue2, 3);

      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         minecraftClient.player.networkHandler.sendPacket(new OnGroundOnly(minecraftClient.player.isOnGround(), minecraftClient.player.horizontalCollision));
      }

      minecraftClient.player.networkHandler.sendPacket(new PositionAndOnGround(vec3d1.x, vec3d1.y, vec3d1.z, false, minecraftClient.player.horizontalCollision));
      minecraftClient.player.setPosition(vec3d1.x, vec3d1.y, vec3d1.z);
   }

   @Override
   public void onEnable() {
      this.j = null;
      this.k = false;
      this.h.reset();
      this.i.reset();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.j = null;
      this.k = false;
      super.onDisable();
   }
}
