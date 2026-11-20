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

import com.mojang.authlib.GameProfile;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventPopTotem;
import dev.leg1tka.api.events.implement.EventUpdate;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot.Type;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry.Reference;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class FakePlayer extends Module {
   public static FakePlayer c = new FakePlayer();
   private static String d;
   private static String e;
   private static String f;
   private static String g;
   private static String h;
   private static String i;
   private static String j;
   private static final EquipmentSlot[] k = new EquipmentSlot[]{
      EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET, EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND
   };
   private static int l;
   private static int m;
   private static int n;
   private static int o;
   private static int p;
   private final ModeSetting q = new ModeSetting(
      "Режим",
      "Живой",
      "Живой",
      "Статичный"
   );
   private final BooleanSetting r = new BooleanSetting("Получает урон", true);
   private final NumberSetting s = new NumberSetting("Отбрасывание", 1.0F, 0.0F, 2.0F, 0.1F)
      .a(() -> this.q.b("Живой"));
   private final BooleanSetting t = new BooleanSetting("Бесконечные тотемы", true);
   private final BooleanSetting u = new BooleanSetting("Регенерация", true);
   private final ModeSetting v = new ModeSetting(
      "Броня",
      "Незерит",
      "Незерит",
      "Алмаз",
      "Как у меня",
      "Нет"
   );
   private final NumberSetting w = new NumberSetting("Защита", 4.0F, 0.0F, 4.0F, 1.0F)
      .a(() -> this.v.b("Незерит") || this.v.b("Алмаз"));
   private final BooleanSetting x = new BooleanSetting("Взрывоуст. на штанах", false)
      .visibleWhen(() -> this.v.b("Незерит") || this.v.b("Алмаз"));
   private final BooleanSetting y = new BooleanSetting("Тотем в руке", true);
   private final Map<Integer, FakePlayer.a> z = new HashMap<>();
   private dev.leg1tka.api.utils.player.PreviewPlayerEntity A;
   private ClientWorld B;
   private Vec3d C;
   private float D;
   private float E;
   private String F = "";
   private float G;
   private int H;
   private int I;
   private int J;
   private int K;
   private int L;
   private int M;
   private int N;
   private int O = -1000;

   public FakePlayer() {
      super(
         "FakePlayer",
         "Фейковый игрок для тестов: урон, отбрасывание, тотемы, броня",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.q, this.r, this.s, this.t, this.u, this.v, this.w, this.x, this.y});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      if (minecraftClient.player != null && minecraftClient.world != null) {
         this.C = minecraftClient.player.getPos();
         this.D = minecraftClient.player.getYaw();
         this.E = minecraftClient.player.getPitch();
         this.N = 0;
         this.s();
      } else {
         this.setEnabled(false);
      }
   }

   @Override
   public void onDisable() {
      this.t();
      this.z.clear();
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (this.A != null && this.B == minecraftClient.world && !this.A.isRemoved()) {
            this.r();
            this.A.a = this.q.b("Статичный");
            if (!this.F.equals(this.u())) {
               this.v();
            }

            if (this.M > 0) {
               if (++this.M > 20) {
                  this.A.handleStatus((byte)60);
                  this.t();
                  this.s();
               }
            } else {
               if (this.H > 0) {
                  this.H--;
               }

               if (this.L > 0 && --this.L == 0) {
                  this.A.setAbsorptionAmount(0.0F);
               }

               if (this.J > 0) {
                  this.J--;
                  if (++this.K >= 25) {
                     this.K = 0;
                     this.q();
                  }
               }

               if (this.u.getValue() && ++this.I >= 80) {
                  this.I = 0;
                  this.q();
               }

               if (this.y.getValue() && this.t.getValue() && !this.A.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
                  this.A.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
               }
            }
         } else {
            if (this.B != minecraftClient.world) {
               this.C = minecraftClient.player.getPos();
            }

            this.s();
         }
      }
   }

   @EventLink(
      priority = 200
   )
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (this.A != null && eventAttackEntity1.getTarget() == this.A && eventAttackEntity1.getPlayer() == minecraftClient.player) {
         eventAttackEntity1.cancel();
         float floatValue2 = minecraftClient.player.getAttackCooldownProgress(0.5F);
         minecraftClient.player.resetLastAttackedTicks();
         if (this.M <= 0) {
            float floatValue3 = (float)minecraftClient.player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            int intValue4 = this.a(Enchantments.SHARPNESS, minecraftClient.player.getMainHandStack());
            float floatValue5 = intValue4 > 0 ? 0.5F * (float)intValue4 + 0.5F : 0.0F;
            float floatValue6 = floatValue3 * (0.2F + floatValue2 * floatValue2 * 0.8F) + floatValue5 * floatValue2;
            boolean flag7 = floatValue2 > 0.9F;
            boolean flag8 = flag7 && minecraftClient.player.isSprinting();
            boolean flag9 = flag7
               && minecraftClient.player.fallDistance > 0.0F
               && !minecraftClient.player.isOnGround()
               && !minecraftClient.player.isClimbing()
               && !minecraftClient.player.isTouchingWater()
               && !minecraftClient.player.hasStatusEffect(StatusEffects.BLINDNESS)
               && !minecraftClient.player.hasVehicle()
               && !minecraftClient.player.isSprinting();
            if (flag9) {
               floatValue6 *= 1.5F;
            }

            floatValue6 = dev.leg1tka.api.utils.player.ItemAttributes.a(this.A, floatValue6, false, 0);
            SoundEvent soundEvent10 = flag8
               ? SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK
               : (flag9 ? SoundEvents.ENTITY_PLAYER_ATTACK_CRIT : (flag7 ? SoundEvents.ENTITY_PLAYER_ATTACK_STRONG : SoundEvents.ENTITY_PLAYER_ATTACK_WEAK));
            minecraftClient.world.playSound(this.A.getX(), this.A.getY(), this.A.getZ(), soundEvent10, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
            if (flag9) {
               minecraftClient.particleManager.addEmitter(this.A, ParticleTypes.CRIT);
            }

            if (floatValue5 > 0.0F) {
               minecraftClient.particleManager.addEmitter(this.A, ParticleTypes.ENCHANTED_HIT);
            }

            if (this.a(floatValue6, minecraftClient.player.getYaw()) && this.q.b("Живой")) {
               double doubleValue11 = (double)this.s.f();
               this.A.takeKnockback(0.4 * doubleValue11, minecraftClient.player.getX() - this.A.getX(), minecraftClient.player.getZ() - this.A.getZ());
               if (flag8) {
                  float floatValue13 = minecraftClient.player.getYaw() * (float) (Math.PI / 180.0);
                  this.A.takeKnockback(0.5 * doubleValue11, (double)MathHelper.sin(floatValue13), (double)(-MathHelper.cos(floatValue13)));
               }
            }
         }
      }
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE && eventPacket1.getPacket() instanceof ExplosionS2CPacket explosionS2CPacket2) {
         Vec3d vec3d4 = explosionS2CPacket2.center();
         minecraftClient.execute(() -> this.a(vec3d4));
      }
   }

   private void a(Vec3d vec3d1) {
      if (this.A != null && minecraftClient.world != null && this.M <= 0) {
         float floatValue2 = this.b(vec3d1);
         float floatValue3 = dev.leg1tka.api.utils.player.ItemAttributes.a(vec3d1, this.A, this.A.getBoundingBox(), null, List.of(), floatValue2, true);
         double doubleValue4 = Math.sqrt(this.A.squaredDistanceTo(vec3d1));
         if (!(doubleValue4 > (double)floatValue2 * 2.0)) {
            Vec3d vec3d6 = vec3d1.subtract(this.A.getPos());
            this.a(floatValue3, (float)Math.toDegrees(Math.atan2(vec3d6.z, vec3d6.x)) - 90.0F);
            if (this.q.b("Живой")) {
               double doubleValue7 = (1.0 - doubleValue4 / ((double)floatValue2 * 2.0)) * (double)dev.leg1tka.api.utils.player.ItemAttributes.b(vec3d1, this.A);
               double doubleValue9 = Math.min(1.0, 0.15 * (double)this.w());
               Vec3d vec3d11 = new Vec3d(this.A.getX() - vec3d1.x, this.A.getEyeY() - vec3d1.y, this.A.getZ() - vec3d1.z);
               if (!(vec3d11.lengthSquared() < 1.0E-8)) {
                  this.A.addVelocity(vec3d11.normalize().multiply(doubleValue7 * (1.0 - doubleValue9) * (double)this.s.f()));
               }
            }
         }
      }
   }

   private boolean a(float floatValue1, float floatValue2) {
      boolean flag3;
      float floatValue4;
      if (this.H > 10) {
         if (floatValue1 <= this.G) {
            return false;
         }

         floatValue4 = floatValue1 - this.G;
         this.G = floatValue1;
         flag3 = false;
      } else {
         floatValue4 = floatValue1;
         this.G = floatValue1;
         this.H = 20;
         flag3 = true;
      }

      if (flag3) {
         this.A.animateDamage(floatValue2);
         minecraftClient.world.playSound(this.A.getX(), this.A.getY(), this.A.getZ(), SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
      }

      if (this.r.getValue()) {
         this.a(floatValue4);
      }

      return flag3;
   }

   private void a(float floatValue1) {
      float floatValue2 = this.A.getAbsorptionAmount();
      float floatValue3 = Math.min(floatValue2, floatValue1);
      this.A.setAbsorptionAmount(floatValue2 - floatValue3);
      float floatValue4 = this.A.getHealth() - (floatValue1 - floatValue3);
      if (floatValue4 > 0.0F) {
         this.A.setHealth(floatValue4);
      } else if (this.t.getValue()) {
         this.p();
      } else {
         this.A.setHealth(0.0F);
         this.M = 1;
         minecraftClient.world.playSound(this.A.getX(), this.A.getY(), this.A.getZ(), SoundEvents.ENTITY_PLAYER_DEATH, SoundCategory.PLAYERS, 1.0F, 1.0F, false);
      }
   }

   private void p() {
      this.N++;
      this.A.setHealth(1.0F);
      this.A.setAbsorptionAmount(8.0F);
      this.J = 900;
      this.K = 0;
      this.L = 100;
      minecraftClient.particleManager.addEmitter(this.A, ParticleTypes.TOTEM_OF_UNDYING, 30);
      minecraftClient.world.playSound(this.A.getX(), this.A.getY(), this.A.getZ(), SoundEvents.ITEM_TOTEM_USE, this.A.getSoundCategory(), 1.0F, 1.0F, false);
      new EventPopTotem(this.A).call();
   }

   private void q() {
      if (this.A.getHealth() > 0.0F && this.A.getHealth() < this.A.getMaxHealth()) {
         this.A.heal(1.0F);
      }
   }

   private void a(double doubleValue1) {
      if (this.M <= 0) {
         float floatValue3 = (float)Math.ceil(doubleValue1 - 3.0);
         if (!(floatValue3 <= 0.0F)) {
            int intValue4 = 0;

            for (EquipmentSlot equipmentSlot8 : k) {
               if (equipmentSlot8.getType() == Type.HUMANOID_ARMOR) {
                  intValue4 += this.a(Enchantments.PROTECTION, this.A.getEquippedStack(equipmentSlot8));
               }
            }

            intValue4 += this.a(Enchantments.FEATHER_FALLING, this.A.getEquippedStack(EquipmentSlot.FEET)) * 3;
            this.a(floatValue3 * (1.0F - (float)Math.min(20, intValue4) / 25.0F), this.A.getYaw());
         }
      }
   }

   private float b(Vec3d vec3d1) {
      float floatValue2 = 5.0F;
      double doubleValue3 = 0.36;

      for (FakePlayer.a value6 : this.z.values()) {
         double doubleValue7 = value6.a.squaredDistanceTo(vec3d1);
         if (doubleValue7 < doubleValue3) {
            doubleValue3 = doubleValue7;
            floatValue2 = value6.b;
         }
      }

      if (minecraftClient.world.getBlockState(BlockPos.ofFloored(vec3d1)).getBlock() instanceof RespawnAnchorBlock) {
         floatValue2 = 5.0F;
      }

      return floatValue2;
   }

   private void r() {
      int intValue1 = minecraftClient.player.age;
      this.z.values().removeIf(value1x -> intValue1 - value1x.c > 10);

      for (Entity entity3 : minecraftClient.world.getEntities()) {
         boolean flag4 = entity3 instanceof EndCrystalEntity;
         if ((flag4 || entity3 instanceof TntEntity) && !(entity3.squaredDistanceTo(this.A) > 400.0)) {
            FakePlayer.a value5 = this.z.computeIfAbsent(entity3.getId(), integer0 -> new FakePlayer.a());
            value5.a = entity3.getPos();
            value5.b = flag4 ? 6.0F : 4.0F;
            value5.c = intValue1;
         }
      }
   }

   private void s() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         UUID uUID1 = UUID.nameUUIDFromBytes("AspectFakePlayer:FakePlayer".getBytes(StandardCharsets.UTF_8));
         this.A = new dev.leg1tka.api.utils.player.PreviewPlayerEntity(minecraftClient.world, new GameProfile(uUID1, "FakePlayer"));

         while (minecraftClient.world.getEntityById(this.O) != null) {
            this.O--;
         }

         this.A.setId(this.O--);
         Vec3d vec3d2 = this.C != null ? this.C : minecraftClient.player.getPos();
         this.A.refreshPositionAndAngles(vec3d2.x, vec3d2.y, vec3d2.z, this.D, this.E);
         this.A.setHeadYaw(this.D);
         this.A.setBodyYaw(this.D);
         this.A.b = this::a;
         this.A.a = this.q.b("Статичный");
         this.B = minecraftClient.world;
         this.F = "";
         this.v();
         this.A.setHealth(this.A.getMaxHealth());
         this.A.setAbsorptionAmount(0.0F);
         this.G = 0.0F;
         this.H = this.I = this.J = this.K = this.L = this.M = 0;
         minecraftClient.world.addEntity(this.A);
      }
   }

   private void t() {
      if (this.A != null && minecraftClient.world != null && !this.A.isRemoved()) {
         minecraftClient.world.removeEntity(this.A.getId(), RemovalReason.DISCARDED);
      }

      this.A = null;
   }

   private String u() {
      return this.v.g() + (int)this.w.f() + this.x.getValue() + this.y.getValue() + this.t.getValue();
   }

   private void v() {
      this.F = this.u();

      for (EquipmentSlot equipmentSlot4 : k) {
         this.a(equipmentSlot4, this.A.getEquippedStack(equipmentSlot4), false);
      }

      String string8 = this.v.g();

      Item[] itemArray7 = switch (string8) {
         case "Незерит" -> new Item[]{
         Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS, Items.NETHERITE_SWORD
      };
         case "Алмаз" -> new Item[]{
         Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS, Items.DIAMOND_SWORD
      };
         default -> null;
      };

      for (int intValue9 = 0; intValue9 < 5; intValue9++) {
         EquipmentSlot equipmentSlot12 = k[intValue9];
         ItemStack itemStack14;
         if (this.v.b("Как у меня")) {
            itemStack14 = minecraftClient.player.getEquippedStack(equipmentSlot12).copy();
         } else if (itemArray7 == null) {
            itemStack14 = ItemStack.EMPTY;
         } else {
            itemStack14 = new ItemStack(itemArray7[intValue9]);
            if (intValue9 < 4) {
               this.a(itemStack14, equipmentSlot12 == EquipmentSlot.LEGS && this.x.getValue() ? Enchantments.BLAST_PROTECTION : Enchantments.PROTECTION, (int)this.w.f());
            }
         }

         this.A.equipStack(equipmentSlot12, itemStack14);
      }

      ItemStack itemStack10 = this.y.getValue()
         ? new ItemStack(Items.TOTEM_OF_UNDYING)
         : (this.v.b("Как у меня") ? minecraftClient.player.getOffHandStack().copy() : ItemStack.EMPTY);
      this.A.equipStack(EquipmentSlot.OFFHAND, itemStack10);

      for (EquipmentSlot equipmentSlot6 : k) {
         this.a(equipmentSlot6, this.A.getEquippedStack(equipmentSlot6), true);
      }

      if (this.A.getHealth() > this.A.getMaxHealth()) {
         this.A.setHealth(this.A.getMaxHealth());
      }
   }

   private void a(EquipmentSlot equipmentSlot1, ItemStack itemStack2, boolean flag3) {
      if (!itemStack2.isEmpty()) {
         itemStack2.applyAttributeModifiers(equipmentSlot1, (registryEntry2x, entityAttributeModifier3x) -> {
            EntityAttributeInstance entityAttributeInstance4 = this.A.getAttributes().getCustomInstance(registryEntry2x);
            if (entityAttributeInstance4 != null) {
               entityAttributeInstance4.removeModifier(entityAttributeModifier3x.id());
               if (flag3) {
                  entityAttributeInstance4.addTemporaryModifier(entityAttributeModifier3x);
               }
            }
         });
      }
   }

   private void a(ItemStack itemStack1, RegistryKey<Enchantment> registryKey2, int intValue3) {
      if (intValue3 > 0) {
         Registry registry4 = minecraftClient.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
         itemStack1.addEnchantment(registry4.getOrThrow(registryKey2), intValue3);
      }
   }

   private int a(RegistryKey<Enchantment> registryKey1, ItemStack itemStack2) {
      if (!itemStack2.isEmpty() && minecraftClient.world != null) {
         Reference reference3 = minecraftClient.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(registryKey1);
         return EnchantmentHelper.getLevel(reference3, itemStack2);
      } else {
         return 0;
      }
   }

   private int w() {
      int intValue1 = 0;

      for (EquipmentSlot equipmentSlot5 : k) {
         if (equipmentSlot5.getType() == Type.HUMANOID_ARMOR) {
            intValue1 += this.a(Enchantments.BLAST_PROTECTION, this.A.getEquippedStack(equipmentSlot5));
         }
      }

      return intValue1;
   }

   static class a {
      Vec3d a;
      float b;
      int c;

      a() {
      }
   }
}
