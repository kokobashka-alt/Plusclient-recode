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
import java.util.HashSet;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class AutoCart extends Module {
   public static AutoCart c = new AutoCart();
   private static int d;
   private static long e;
   private static double f;
   private static double g;
   private final Set h = new HashSet();
   private final Set i = new HashSet();
   private long j;

   public AutoCart() {
      super(
         "AutoCart",
         "Ставит рельсу + TNT вагонетку в точку падения стрелы",
         Category.MISC
      );
   }

   @Override
   public void onEnable() {
      this.h.clear();
      this.i.clear();
      this.j = 0L;
      this.p();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.h.clear();
      this.i.clear();
      this.j = 0L;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.interactionManager != null) {
         HashSet hashSet2 = new HashSet();

         for (Entity entity4 : minecraftClient.world.getEntities()) {
            if (entity4 instanceof PersistentProjectileEntity) {
               PersistentProjectileEntity persistentProjectileEntity5 = (PersistentProjectileEntity)entity4;
               if (!(persistentProjectileEntity5 instanceof TridentEntity) && persistentProjectileEntity5.getOwner() == minecraftClient.player) {
                  int intValue6 = persistentProjectileEntity5.getId();
                  hashSet2.add(intValue6);
                  this.h.add(intValue6);
                  if (!this.i.contains(intValue6) && this.q() && this.a(persistentProjectileEntity5)) {
                     AutoCart.a value7 = this.b(persistentProjectileEntity5);
                     if (value7 != null) {
                        if (minecraftClient.player.squaredDistanceTo(Vec3d.ofCenter(value7.placePos())) > 25.0) {
                           this.i.add(intValue6);
                        } else if (this.a(value7.placePos()) || persistentProjectileEntity5.isOnGround()) {
                           this.i.add(intValue6);
                        }
                     }
                  }
               }
            }
         }

         this.h.retainAll(hashSet2);
         this.i.retainAll(hashSet2);
      }
   }

   private void p() {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         for (Entity entity2 : minecraftClient.world.getEntities()) {
            if (entity2 instanceof PersistentProjectileEntity) {
               PersistentProjectileEntity persistentProjectileEntity3 = (PersistentProjectileEntity)entity2;
               if (!(persistentProjectileEntity3 instanceof TridentEntity) && persistentProjectileEntity3.getOwner() == minecraftClient.player) {
                  this.h.add(persistentProjectileEntity3.getId());
               }
            }
         }
      }
   }

   private boolean q() {
      return this.a(Items.RAIL) != -1 && this.a(Items.TNT_MINECART) != -1;
   }

   private boolean a(PersistentProjectileEntity persistentProjectileEntity1) {
      return !persistentProjectileEntity1.isOnGround() && persistentProjectileEntity1.getVelocity().lengthSquared() > 1.0E-4;
   }

   private AutoCart.a b(PersistentProjectileEntity persistentProjectileEntity1) {
      if (minecraftClient.world == null) {
         return null;
      } else {
         Vec3d vec3d2 = persistentProjectileEntity1.getPos();
         Vec3d vec3d3 = persistentProjectileEntity1.getVelocity();

         for (int intValue4 = 1; intValue4 <= 80; intValue4++) {
            Vec3d vec3d5 = vec3d2;
            vec3d2 = vec3d2.add(vec3d3);
            vec3d3 = this.a(persistentProjectileEntity1, vec3d5, vec3d3);
            BlockHitResult blockHitResult6 = this.a(vec3d5, vec3d2, persistentProjectileEntity1);
            if (blockHitResult6.getType() != Type.MISS) {
               BlockPos blockPos7 = this.a(blockHitResult6);
               if (blockPos7 != null) {
                  return new AutoCart.a(blockPos7, intValue4);
               }

               return null;
            }

            if (vec3d2.y < (double)(minecraftClient.world.getBottomY() - 2)) {
               break;
            }
         }

         return null;
      }
   }

   private Vec3d a(PersistentProjectileEntity persistentProjectileEntity1, Vec3d vec3d2, Vec3d vec3d3) {
      if (minecraftClient.world == null) {
         return vec3d3;
      } else {
         FluidState fluidState4 = minecraftClient.world.getBlockState(BlockPos.ofFloored(vec3d2)).getFluidState();
         boolean flag5 = fluidState4.isIn(FluidTags.WATER);
         double doubleValue6 = flag5 ? 0.6 : 0.99;
         return vec3d3.multiply(doubleValue6).add(0.0, -persistentProjectileEntity1.getFinalGravity(), 0.0);
      }
   }

   private BlockHitResult a(Vec3d vec3d1, Vec3d vec3d2, Entity entity3) {
      return minecraftClient.world.raycast(new RaycastContext(vec3d1, vec3d2, ShapeType.COLLIDER, FluidHandling.NONE, entity3));
   }

   private BlockPos a(BlockHitResult blockHitResult1) {
      if (minecraftClient.world == null) {
         return null;
      } else {
         BlockPos blockPos2 = blockHitResult1.getBlockPos();
         if (this.b(blockPos2)) {
            return blockPos2;
         } else {
            BlockPos blockPos3 = blockHitResult1.getSide() == Direction.UP ? blockPos2.up() : blockPos2;
            if (this.c(blockPos3)) {
               return blockPos3;
            } else {
               BlockPos blockPos4 = blockPos2.up();
               if (this.c(blockPos4)) {
                  return blockPos4;
               } else {
                  BlockPos blockPos5 = blockPos2.down();
                  return this.c(blockPos5) ? blockPos5 : null;
               }
            }
         }
      }
   }

   private boolean a(BlockPos blockPos1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.interactionManager != null) {
         int intValue2 = this.a(Items.RAIL);
         int intValue3 = this.a(Items.TNT_MINECART);
         if (intValue2 != -1 && intValue3 != -1) {
            int intValue4 = this.r();

            boolean flag6;
            try {
               if (this.b(blockPos1) || this.a(blockPos1, intValue2)) {
                  if (this.d(blockPos1)) {
                     return true;
                  }

                  if (System.currentTimeMillis() >= this.j) {
                     if (this.b(blockPos1, intValue3)) {
                        this.j = System.currentTimeMillis() + 100L;
                        this.b(intValue3);
                     }

                     return this.d(blockPos1);
                  }

                  return false;
               }

               flag6 = false;
            } finally {
               this.c(intValue4);
            }

            return flag6;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean b(BlockPos blockPos1) {
      return minecraftClient.world != null && minecraftClient.world.getBlockState(blockPos1).isIn(BlockTags.RAILS);
   }

   private boolean c(BlockPos blockPos1) {
      if (minecraftClient.world == null) {
         return false;
      } else if (this.b(blockPos1)) {
         return true;
      } else {
         BlockState blockState2 = minecraftClient.world.getBlockState(blockPos1);
         if (!this.a(blockState2)) {
            return false;
         } else {
            BlockPos blockPos3 = blockPos1.down();
            return minecraftClient.world.getBlockState(blockPos3).isSideSolidFullSquare(minecraftClient.world, blockPos3, Direction.UP);
         }
      }
   }

   private boolean a(BlockState blockState1) {
      if (blockState1.isReplaceable()) {
         return true;
      } else if (blockState1.isIn(BlockTags.SNOW)) {
         return true;
      } else {
         return blockState1.isIn(BlockTags.SWORD_EFFICIENT)
            ? true
            : blockState1.isOf(Blocks.SHORT_GRASS)
               || blockState1.isOf(Blocks.TALL_GRASS)
               || blockState1.isOf(Blocks.FERN)
               || blockState1.isOf(Blocks.LARGE_FERN)
               || blockState1.isOf(Blocks.DEAD_BUSH)
               || blockState1.isOf(Blocks.VINE)
               || blockState1.isOf(Blocks.SNOW);
      }
   }

   private boolean a(BlockPos blockPos1, int intValue2) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         this.c(intValue2);
         BlockPos blockPos3 = blockPos1.down();
         BlockHitResult blockHitResult4 = new BlockHitResult(
            new Vec3d((double)blockPos1.getX() + 0.5, (double)blockPos1.getY(), (double)blockPos1.getZ() + 0.5), Direction.UP, blockPos3, false
         );
         ActionResult actionResult5 = minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.MAIN_HAND, blockHitResult4);
         if (actionResult5.isAccepted()) {
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            return true;
         } else {
            return this.b(blockPos1);
         }
      } else {
         return false;
      }
   }

   private boolean b(BlockPos blockPos1, int intValue2) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         this.c(intValue2);
         BlockHitResult blockHitResult3 = new BlockHitResult(
            new Vec3d((double)blockPos1.getX() + 0.5, (double)blockPos1.getY() + 0.15, (double)blockPos1.getZ() + 0.5), Direction.UP, blockPos1, false
         );
         ActionResult actionResult4 = minecraftClient.interactionManager.interactBlock(minecraftClient.player, Hand.MAIN_HAND, blockHitResult3);
         if (actionResult4.isAccepted()) {
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void b(int intValue1) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null) {
         if (intValue1 >= 0 && intValue1 <= 8) {
            if (!minecraftClient.player.getInventory().getStack(intValue1).isOf(Items.TNT_MINECART)) {
               int intValue2 = this.a(Items.TNT_MINECART, 9, 36);
               if (intValue2 != -1) {
                  minecraftClient.interactionManager.clickSlot(minecraftClient.player.currentScreenHandler.syncId, intValue2, intValue1, SlotActionType.SWAP, minecraftClient.player);
               }
            }
         }
      }
   }

   private boolean d(BlockPos blockPos1) {
      if (minecraftClient.world == null) {
         return false;
      } else {
         Box box2 = new Box(blockPos1).expand(0.2, 1.0, 0.2);

         for (Entity entity4 : minecraftClient.world.getOtherEntities(null, box2)) {
            if (entity4 instanceof TntMinecartEntity) {
               return true;
            }
         }

         return false;
      }
   }

   private int a(Item item1) {
      return this.a(item1, 0, 9);
   }

   private int r() {
      return minecraftClient.player.getInventory().selectedSlot;
   }

   private void c(int intValue1) {
      if (minecraftClient.player != null && intValue1 >= 0 && intValue1 <= 8) {
         minecraftClient.player.getInventory().selectedSlot = intValue1;
         if (minecraftClient.getNetworkHandler() != null) {
            minecraftClient.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(intValue1));
         }
      }
   }

   private int a(Item item1, int intValue2, int intValue3) {
      if (minecraftClient.player == null) {
         return -1;
      } else {
         for (int intValue4 = intValue2; intValue4 < intValue3; intValue4++) {
            if (minecraftClient.player.getInventory().getStack(intValue4).isOf(item1)) {
               return intValue4;
            }
         }

         return -1;
      }
   }

   static record a(BlockPos placePos, int ticksToImpact)  {

}
}
