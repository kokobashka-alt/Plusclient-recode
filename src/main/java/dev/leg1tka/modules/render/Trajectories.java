package dev.leg1tka.modules.render;


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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Trajectories extends Module {
   public static Trajectories c = new Trajectories();
   private static int d;
   private static int e;
   private static double f;
   private static double g;
   private static double h;
   private static final Identifier i = dev.leg1tka.api.utils.resources.ResourceIdentifiers.a("textures/trajectories/glow.png");
   private static double j;
   private static int k;
   private static int l;
   private static float m;
   private static float n;
   private static float o;
   private static float p;
   private static int q;
   private static int r;
   private static int s;
   private static int t;
   private static int u;
   private static final float[] v = new float[41];
   private static final float[] w = new float[41];
   private static int x;
   private static int y;
   private static int z;
   private static int A;
   private final ModeSetting B = new ModeSetting(
      "Режим", "Новый", "Старый", "Новый"
   );
   private final NumberSetting C = new NumberSetting("Ширина линии", 2.2F, 0.5F, 5.0F, 0.1F)
      .a(() -> this.B.b("Старый"));
   private final NumberSetting D = new NumberSetting("Толщина", 1.0F, 0.4F, 2.5F, 0.05F).a(() -> this.B.b("Новый"));
   private final NumberSetting E = new NumberSetting("Яркость", 0.8F, 0.2F, 1.5F, 0.05F).a(() -> this.B.b("Новый"));
   private final BooleanSetting F = new BooleanSetting("Искры", true).visibleWhen(() -> this.B.b("Новый"));
   private final BooleanSetting G = new BooleanSetting("Падающие предметы", true);
   private final BooleanSetting H = new BooleanSetting("Цвет по редкости", true).visibleWhen(this.G::getValue);
   private final Int2ObjectOpenHashMap<Trajectories.e> I = new Int2ObjectOpenHashMap<>();
   private final List<Trajectories.e> J = new ArrayList<>();
   private final double[] K = new double[51];
   private final double[] L = new double[2304];
   private final float[] M = new float[1152];
   private final int[] N = new int[384];
   private final int[] O = new int[384];
   private final int[] P = new int[384];
   private final float[] Q = new float[384];
   private int R;
   private final double[] S = new double[144];
   private final Direction[] T = new Direction[48];
   private final int[] U = new int[48];
   private final int[] V = new int[48];
   private int W;
   private final Random X = new Random();
   private final Quaternionf Y = new Quaternionf();
   private final Vector3f Z = new Vector3f();
   private final Vector3f ModuleStorage = new Vector3f();
   private double ab;
   private float ac = 1.0F;
   private final List<Trajectories.a> ad = new ArrayList<>();
   private final List<Trajectories.b> ae = new ArrayList<>();
   private final List<Trajectories.a> af = new ArrayList<>();
   private int ag;
   private int ah = -1;
   private final Trajectories.c[] Storage = new Trajectories.c[3];
   private int ModuleManager;
   private int ModuleRegistry = -1;
   private Item al;
   private double am;
   private Vec3d an = Vec3d.ZERO;
   private Vec3d ao = Vec3d.ZERO;
   private final Matrix4f ap = new Matrix4f();
   private final Quaternionf aq = new Quaternionf();
   private Vec3d ar = Vec3d.ZERO;
   private boolean as;
   private double at;
   private double au;
   private double av;

   public Trajectories() {
      super(
         "Trajectories",
         "Показывает траекторию предмета в руке",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.B, this.C, this.D, this.E, this.F, this.G, this.H});
   }

   @Override
   public void onDisable() {
      this.ad.clear();
      this.ae.clear();
      this.r();
      this.ModuleManager = 0;
      this.ModuleRegistry = -1;
      this.ah = -1;
      super.onDisable();
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      this.ag++;
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         boolean flag2 = this.B.b("Новый");
         if (this.ah != this.ag) {
            this.ah = this.ag;
            this.p();
            if (flag2) {
               this.s();
            } else {
               this.r();
            }
         }

         Camera camera3 = event3DRender1.getCamera();
         Vec3d vec3d4 = camera3.getPos();
         this.at = vec3d4.x;
         this.au = vec3d4.y;
         this.av = vec3d4.z;
         MatrixStack matrixStack5 = event3DRender1.getMatrices();
         Matrix4f matrix4f6 = matrixStack5.peek().getPositionMatrix();
         int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.a();
         float floatValue8 = event3DRender1.getTickDelta();
         ItemStack itemStack9 = this.q();
         Trajectories.d value10 = itemStack9.isEmpty() ? null : this.b(itemStack9);
         if (value10 != null) {
            this.a(itemStack9, value10, floatValue8);
         } else {
            this.ModuleManager = 0;
         }

         if (flag2) {
            this.a(matrixStack5, matrix4f6, camera3, floatValue8, intValue7, itemStack9);
         } else if (this.ModuleManager > 0 || !this.ad.isEmpty()) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            RenderSystem.lineWidth(this.C.e().floatValue());
            BufferBuilder bufferBuilder11 = Tessellator.getInstance().begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

            for (int intValue12 = 0; intValue12 < this.ModuleManager; intValue12++) {
               Trajectories.c value13 = this.Storage[intValue12];
               this.a(bufferBuilder11, matrix4f6, value13.points, value13.pointCount, dev.leg1tka.api.utils.text.ColorUtils.d(intValue7, 190));
               if (value13.entityHit != null && value13.entityHit.isAlive()) {
                  this.a(bufferBuilder11, matrix4f6, value13.entityHit, dev.leg1tka.api.utils.text.ColorUtils.a(255, 70, 70, 210));
               } else if (value13.blockHit != null) {
                  this.a(bufferBuilder11, matrix4f6, value13.hitPos, value13.blockHit.getSide(), dev.leg1tka.api.utils.text.ColorUtils.d(intValue7, 230));
               }
            }

            for (Trajectories.a value16 : this.ad) {
               this.a(bufferBuilder11, matrix4f6, value16, intValue7);
            }

            BufferRenderer.drawWithGlobalProgram(bufferBuilder11.end());
            if (itemStack9.isOf(Items.SPLASH_POTION)) {
               for (int intValue15 = 0; intValue15 < this.ModuleManager; intValue15++) {
                  if (this.Storage[intValue15].hitPos != null) {
                     this.a(matrixStack5, this.Storage[intValue15].hitPos, intValue7);
                  }
               }
            }

            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();
         }

         this.ap.set(event3DRender1.getProjectionMatrix());
         this.ar = vec3d4;
         this.aq.set(camera3.getRotation());
         this.as = true;
      }
   }

   private void a(ItemStack itemStack1, Trajectories.d value2, float floatValue3) {
      Vec3d vec3d4 = minecraftClient.player.getCameraPosVec(floatValue3);
      Vec3d vec3d5 = minecraftClient.player.getRotationVec(floatValue3).normalize();
      boolean flag6 = this.ModuleRegistry == this.ag && this.al == itemStack1.getItem() && this.am == value2.velocity && this.an.equals(vec3d4) && this.ao.equals(vec3d5);
      if (!flag6) {
         this.ModuleRegistry = this.ag;
         this.al = itemStack1.getItem();
         this.am = value2.velocity;
         this.an = vec3d4;
         this.ao = vec3d5;
         this.ModuleManager = 0;

         for (Vec3d vec3d10 : this.a(itemStack1, vec3d5)) {
            Trajectories.c value11 = this.a(minecraftClient.player, value2, vec3d4, vec3d10);
            if (value11.pointCount >= 2) {
               this.Storage[this.ModuleManager++] = value11;
            }
         }
      }
   }

   private Trajectories.c a(PlayerEntity playerEntity1, Trajectories.d value2, Vec3d vec3d3, Vec3d vec3d4) {
      Vec3d vec3d5 = vec3d3;
      Vec3d vec3d6 = vec3d4.normalize().multiply(value2.velocity);
      double[] doubleValueArray7 = new double[1323];
      int intValue8 = 0;
      intValue8 = a(doubleValueArray7, intValue8, vec3d3);
      BlockHitResult blockHitResult9 = null;
      double doubleValue10 = (double)minecraftClient.world.getBottomY();
      Mutable mutable12 = new Mutable();

      for (int intValue13 = 0; intValue13 < 440; intValue13++) {
         Vec3d vec3d15 = vec3d5.add(vec3d6.multiply(0.5));
         BlockHitResult blockHitResult16 = minecraftClient.world.raycast(new RaycastContext(vec3d5, vec3d15, ShapeType.COLLIDER, FluidHandling.NONE, playerEntity1));
         if (blockHitResult16.getType() == Type.BLOCK) {
            blockHitResult9 = blockHitResult16;
            intValue8 = a(doubleValueArray7, intValue8, blockHitResult16.getPos());
            break;
         }

         intValue8 = a(doubleValueArray7, intValue8, vec3d15);
         vec3d5 = vec3d15;
         mutable12.set(vec3d15.x, vec3d15.y, vec3d15.z);
         boolean flag17 = minecraftClient.world.getBlockState(mutable12).isOf(Blocks.WATER);
         double doubleValue18 = Math.pow(flag17 ? 0.8 : value2.drag, 0.5);
         vec3d6 = vec3d6.multiply(doubleValue18).subtract(0.0, value2.gravity * 0.5, 0.0);
         if (vec3d15.y <= doubleValue10) {
            break;
         }
      }

      Entity entity28 = null;
      Vec3d vec3d14 = null;
      List<Entity> list29 = minecraftClient.world.getOtherEntities(playerEntity1, b(doubleValueArray7, intValue8).expand(1.0), entity0 -> entity0 != null && entity0.isAlive() && entity0.canHit());
      if (!list29.isEmpty()) {
         for (int intValue30 = 0; intValue30 + 1 < intValue8 && entity28 == null; intValue30++) {
            Vec3d vec3d32 = a(doubleValueArray7, intValue30);
            Vec3d vec3d33 = a(doubleValueArray7, intValue30 + 1);
            double doubleValue19 = Double.MAX_VALUE;

            for (Entity entity22 : list29) {
               Box box23 = entity22.getBoundingBox().expand(0.3);
               if (a(box23, vec3d32, vec3d33)) {
                  Optional optional24 = box23.raycast(vec3d32, vec3d33);
                  if (!optional24.isEmpty()) {
                     double doubleValue25 = vec3d32.squaredDistanceTo((Vec3d)optional24.get());
                     if (doubleValue25 < doubleValue19) {
                        doubleValue19 = doubleValue25;
                        entity28 = entity22;
                        vec3d14 = (Vec3d)optional24.get();
                     }
                  }
               }
            }
         }
      }

      Vec3d vec3d31;
      if (vec3d14 != null) {
         vec3d31 = vec3d14;
      } else if (blockHitResult9 != null) {
         vec3d31 = blockHitResult9.getPos();
      } else {
         vec3d31 = a(doubleValueArray7, intValue8 - 1);
      }

      return new Trajectories.c(doubleValueArray7, intValue8, blockHitResult9, vec3d31, entity28);
   }

   private void p() {
      this.af.addAll(this.ad);
      this.ad.clear();
      this.ae.clear();
      Box box1 = minecraftClient.player.getBoundingBox().expand(128.0);

      for (Entity entity3 : minecraftClient.world.getEntitiesByClass(PersistentProjectileEntity.class, box1, Entity::isAlive)) {
         this.a(entity3);
      }

      for (Entity entity8 : minecraftClient.world.getEntitiesByClass(ThrownItemEntity.class, box1, Entity::isAlive)) {
         this.a(entity8);
      }

      if (this.G.getValue()) {
         Box box7 = minecraftClient.player.getBoundingBox().expand(64.0);
         List<ItemEntity> list9 = minecraftClient.world.getEntitiesByClass(ItemEntity.class, box7, itemEntity0 -> itemEntity0.isAlive() && !itemEntity0.isOnGround() && !itemEntity0.isTouchingWater());
         if (list9.size() > 32) {
            list9.sort(Comparator.comparingDouble((ItemEntity itemEntity0) -> itemEntity0.squaredDistanceTo(minecraftClient.player)));
         }

         int intValue4 = 0;

         for (int intValue5 = Math.min(list9.size(), 32); intValue4 < intValue5; intValue4++) {
            this.a((Entity)list9.get(intValue4));
         }
      }
   }

   private void a(Entity entity1) {
      if (!this.b(entity1)) {
         boolean flag2 = entity1 instanceof ItemEntity;
         Vec3d vec3d3 = entity1.getVelocity();
         Vec3d vec3d4 = entity1.getPos();
         if (flag2) {
            vec3d3 = vec3d3.add(0.0, -entity1.getFinalGravity(), 0.0);
         }

         Trajectories.a value5 = this.af.isEmpty() ? new Trajectories.a() : (Trajectories.a)this.af.remove(this.af.size() - 1);
         double[] doubleValueArray6 = value5.a;
         int intValue7 = a(doubleValueArray6, 0, vec3d4);
         double doubleValue8 = (double)minecraftClient.world.getBottomY();
         int intValue10 = flag2 ? 100 : 300;
         Vec3d vec3d11 = null;
         int intValue12 = 0;
         boolean flag13 = false;
         Direction direction14 = null;

         for (int intValue15 = 0; intValue15 < intValue10; intValue15++) {
            Vec3d vec3d16 = vec3d4;
            vec3d4 = vec3d4.add(vec3d3);
            vec3d3 = this.a(entity1, vec3d16, vec3d3);
            BlockHitResult blockHitResult17 = minecraftClient.world.raycast(new RaycastContext(vec3d16, vec3d4, ShapeType.COLLIDER, FluidHandling.NONE, entity1));
            if (blockHitResult17.getType() != Type.MISS) {
               vec3d4 = blockHitResult17.getPos();
               intValue7 = a(doubleValueArray6, intValue7, vec3d4);
               vec3d11 = vec3d4;
               intValue12 = intValue15;
               flag13 = true;
               direction14 = blockHitResult17.getSide();
               break;
            }

            intValue7 = a(doubleValueArray6, intValue7, vec3d4);
            if (vec3d4.y < doubleValue8) {
               vec3d11 = vec3d4;
               intValue12 = intValue15;
               break;
            }
         }

         int intValue25 = flag13 ? intValue7 - 1 : intValue7;
         Entity entity26 = null;
         if (!flag2 && intValue25 >= 2) {
            List<Entity> list27 = minecraftClient.world
               .getOtherEntities(entity1, b(doubleValueArray6, intValue25).expand(0.3), entity1x -> entity1x != null && entity1x.isAlive() && entity1x != minecraftClient.player && entity1x != entity1);
            if (!list27.isEmpty()) {
               label74:
               for (int intValue18 = 0; intValue18 + 1 < intValue25; intValue18++) {
                  Vec3d vec3d19 = a(doubleValueArray6, intValue18);
                  Vec3d vec3d20 = a(doubleValueArray6, intValue18 + 1);

                  for (Entity entity22 : list27) {
                     Box box23 = entity22.getBoundingBox().expand(0.3);
                     if (a(box23, vec3d19, vec3d20) && box23.raycast(vec3d19, vec3d20).isPresent()) {
                        entity26 = entity22;
                        intValue7 = intValue18 + 2;
                        vec3d11 = vec3d20;
                        intValue12 = intValue18;
                        direction14 = null;
                        break label74;
                     }
                  }
               }
            }
         }

         value5.b = intValue7;
         value5.c = vec3d11;
         value5.d = entity26;
         value5.e = entity1;
         value5.f = direction14;
         value5.g = flag2 ? this.a(((ItemEntity)entity1).getStack()) : 0;
         value5.h = flag2 ? 0.2F : 0.0F;
         value5.i = null;
         this.ad.add(value5);
         if (vec3d11 != null && !flag2) {
            this.ae.add(new Trajectories.b(this.c(entity1), vec3d11, intValue12));
         }
      }
   }

   private int a(ItemStack itemStack1) {
      if (!this.H.getValue()) {
         return 0;
      } else {
         return switch (itemStack1.getRarity()) {
            case UNCOMMON -> -6566;
            case RARE -> -10817281;
            case EPIC -> -2991361;
            case COMMON -> 0;
            default -> throw new MatchException(null, null);
         };
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Trajectories.a value3, int intValue4) {
      if (value3.g != 0) {
         intValue4 = value3.g;
      }

      int intValue5 = intValue4 >> 16 & 0xFF;
      int intValue6 = intValue4 >> 8 & 0xFF;
      int intValue7 = intValue4 & 0xFF;
      double[] doubleValueArray8 = value3.a;

      for (int intValue9 = 0; intValue9 + 1 < value3.b; intValue9++) {
         int intValue10 = (int)(MathHelper.clamp((float)intValue9 / 25.0F, 0.0F, 1.0F) * 190.0F);
         int intValue11 = intValue9 * 3;
         this.a(bufferBuilder1, matrix4f2, doubleValueArray8[intValue11], doubleValueArray8[intValue11 + 1], doubleValueArray8[intValue11 + 2], intValue5, intValue6, intValue7, intValue10);
         this.a(bufferBuilder1, matrix4f2, doubleValueArray8[intValue11 + 3], doubleValueArray8[intValue11 + 4], doubleValueArray8[intValue11 + 5], intValue5, intValue6, intValue7, intValue10);
      }

      if (value3.d != null) {
         if (value3.d.isAlive()) {
            this.a(bufferBuilder1, matrix4f2, value3.d, dev.leg1tka.api.utils.text.ColorUtils.a(255, 70, 70, 210));
         }

         this.a(bufferBuilder1, matrix4f2, value3.c, dev.leg1tka.api.utils.text.ColorUtils.a(255, 70, 70, 230));
      } else if (value3.c != null) {
         this.a(bufferBuilder1, matrix4f2, value3.c, dev.leg1tka.api.utils.text.ColorUtils.d(intValue4, 230));
      }
   }

   private boolean b(Entity entity1) {
      return entity1.getX() == entity1.prevX && entity1.getY() == entity1.prevY && entity1.getZ() == entity1.prevZ;
   }

   private Vec3d a(Entity entity1, Vec3d vec3d2, Vec3d vec3d3) {
      boolean flag4 = minecraftClient.world.getBlockState(BlockPos.ofFloored(vec3d2)).getFluidState().isIn(FluidTags.WATER);
      double doubleValue5;
      if (entity1 instanceof ItemEntity) {
         doubleValue5 = 0.98;
      } else if (entity1 instanceof TridentEntity) {
         doubleValue5 = 0.99;
      } else if (entity1 instanceof PersistentProjectileEntity && flag4) {
         doubleValue5 = 0.6;
      } else {
         doubleValue5 = flag4 ? 0.8 : 0.99;
      }

      return vec3d3.multiply(doubleValue5).add(0.0, -entity1.getFinalGravity(), 0.0);
   }

   private ItemStack c(Entity entity1) {
      if (entity1 instanceof ThrownItemEntity thrownItemEntity3) {
         return thrownItemEntity3.getStack();
      } else {
         return entity1 instanceof PersistentProjectileEntity persistentProjectileEntity2 ? persistentProjectileEntity2.getItemStack() : ItemStack.EMPTY;
      }
   }

   private static int a(double[] doubleValueArray0, int intValue1, Vec3d vec3d2) {
      int intValue3 = intValue1 * 3;
      doubleValueArray0[intValue3] = vec3d2.x;
      doubleValueArray0[intValue3 + 1] = vec3d2.y;
      doubleValueArray0[intValue3 + 2] = vec3d2.z;
      return intValue1 + 1;
   }

   private static Vec3d a(double[] doubleValueArray0, int intValue1) {
      int intValue2 = intValue1 * 3;
      return new Vec3d(doubleValueArray0[intValue2], doubleValueArray0[intValue2 + 1], doubleValueArray0[intValue2 + 2]);
   }

   private static Box b(double[] doubleValueArray0, int intValue1) {
      double doubleValue2 = Double.MAX_VALUE;
      double doubleValue4 = Double.MAX_VALUE;
      double doubleValue6 = Double.MAX_VALUE;
      double doubleValue8 = -Double.MAX_VALUE;
      double doubleValue10 = -Double.MAX_VALUE;
      double doubleValue12 = -Double.MAX_VALUE;

      for (byte byteValue14 = 0; byteValue14 < intValue1 * 3; byteValue14 += 3) {
         doubleValue2 = Math.min(doubleValue2, doubleValueArray0[byteValue14]);
         doubleValue4 = Math.min(doubleValue4, doubleValueArray0[byteValue14 + 1]);
         doubleValue6 = Math.min(doubleValue6, doubleValueArray0[byteValue14 + 2]);
         doubleValue8 = Math.max(doubleValue8, doubleValueArray0[byteValue14]);
         doubleValue10 = Math.max(doubleValue10, doubleValueArray0[byteValue14 + 1]);
         doubleValue12 = Math.max(doubleValue12, doubleValueArray0[byteValue14 + 2]);
      }

      return new Box(doubleValue2, doubleValue4, doubleValue6, doubleValue8, doubleValue10, doubleValue12);
   }

   private static boolean a(Box box0, Vec3d vec3d1, Vec3d vec3d2) {
      return Math.max(vec3d1.x, vec3d2.x) >= box0.minX
         && Math.min(vec3d1.x, vec3d2.x) <= box0.maxX
         && Math.max(vec3d1.y, vec3d2.y) >= box0.minY
         && Math.min(vec3d1.y, vec3d2.y) <= box0.maxY
         && Math.max(vec3d1.z, vec3d2.z) >= box0.minZ
         && Math.min(vec3d1.z, vec3d2.z) <= box0.maxZ;
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (this.as && !this.ae.isEmpty() && minecraftClient.player != null) {
         DrawContext drawContext2 = default1.getContext();
         MatrixStack matrixStack3 = drawContext2.getMatrices();
         dev.leg1tka.render.font.MsdfTextRenderer value4 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         if (value4 != null) {
            for (Trajectories.b value6 : this.ae) {
               Vec3d vec3d7 = this.a(value6.pos);
               if (vec3d7 != null && !(vec3d7.z >= 1.0)) {
                  double doubleValue8 = (double)(value6.ticks * 50) / 1000.0;
                  String string10 = String.format("%.1f", doubleValue8).replace(',', '.') + " сек";
                  float floatValue11 = value4.b(string10);
                  float floatValue12 = (float)vec3d7.x;
                  float floatValue13 = (float)vec3d7.y;
                  float floatValue15 = 5.75F;
                  float floatValue16 = floatValue12 - floatValue11 / 2.0F;
                  float floatValue17 = floatValue13 - floatValue15 / 2.0F;
                  float floatValue19 = floatValue17 + 0.0F;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue16 - 7.0F, floatValue19 - 2.0F, floatValue11 + 14.75F, floatValue15 + 5.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 120));
                  matrixStack3.push();
                  matrixStack3.translate(floatValue16 - 5.0F, floatValue19 - 0.75F, 0.0F);
                  matrixStack3.scale(0.5F, 0.5F, 1.0F);
                  drawContext2.drawItem(value6.stack, 0, 0);
                  matrixStack3.pop();
                  value4.a(matrixStack3, string10, floatValue16 + 4.5F, floatValue19, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
               }
            }
         }
      }
   }

   private Vec3d a(Vec3d vec3d1) {
      if (minecraftClient.player == null) {
         return null;
      } else {
         Vector3f vector3f2 = new Vector3f((float)(vec3d1.x - this.ar.x), (float)(vec3d1.y - this.ar.y), (float)(vec3d1.z - this.ar.z));
         vector3f2.rotate(new Quaternionf(this.aq).conjugate());
         Vector4f vector4f3 = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0F);
         this.ap.transform(vector4f3);
         if (vector4f3.w() <= 1.0E-5F) {
            return null;
         } else {
            float floatValue4 = 1.0F / vector4f3.w();
            float floatValue5 = (vector4f3.x() * floatValue4 + 1.0F) * (float)minecraftClient.getWindow().getScaledWidth() / 2.0F;
            float floatValue6 = (1.0F - vector4f3.y() * floatValue4) * (float)minecraftClient.getWindow().getScaledHeight() / 2.0F;
            float floatValue7 = vector4f3.z() * floatValue4;
            return !Float.isNaN(floatValue5) && !Float.isNaN(floatValue6) && !Float.isInfinite(floatValue5) && !Float.isInfinite(floatValue6)
               ? new Vec3d((double)floatValue5, (double)floatValue6, (double)floatValue7)
               : null;
         }
      }
   }

   private ItemStack q() {
      ItemStack itemStack1 = minecraftClient.player.getMainHandStack();
      if (!itemStack1.isEmpty() && this.b(itemStack1) != null) {
         return itemStack1;
      } else {
         ItemStack itemStack2 = minecraftClient.player.getOffHandStack();
         return !itemStack2.isEmpty() && this.b(itemStack2) != null ? itemStack2 : ItemStack.EMPTY;
      }
   }

   private Trajectories.d b(ItemStack itemStack1) {
      Item item2 = itemStack1.getItem();
      if (item2 == Items.ENDER_PEARL || item2 == Items.SNOWBALL || item2 == Items.EGG) {
         return new Trajectories.d(1.5, 0.03, 0.99);
      } else if (item2 == Items.SPLASH_POTION || item2 == Items.LINGERING_POTION) {
         return new Trajectories.d(0.5, 0.05, 0.99);
      } else if (item2 instanceof BowItem) {
         float floatValue3 = 1.0F;
         if (minecraftClient.player.isUsingItem() && minecraftClient.player.getActiveItem() == itemStack1) {
            float floatValue4 = (float)minecraftClient.player.getItemUseTime();
            float floatValue5 = floatValue4 / 20.0F;
            floatValue5 = (floatValue5 * floatValue5 + floatValue5 * 2.0F) / 3.0F;
            floatValue3 = Math.min(floatValue5, 1.0F);
         }

         double doubleValue6 = 3.0 * (double)floatValue3;
         return doubleValue6 <= 0.01 ? null : new Trajectories.d(doubleValue6, 0.05, 0.99);
      } else if (item2 instanceof CrossbowItem) {
         return !CrossbowItem.isCharged(itemStack1) ? null : new Trajectories.d(3.15, 0.05, 0.99);
      } else {
         return item2 instanceof TridentItem ? new Trajectories.d(2.5, 0.05, 0.99) : null;
      }
   }

   private Vec3d[] a(ItemStack itemStack1, Vec3d vec3d2) {
      if (itemStack1.getItem() instanceof CrossbowItem && dev.leg1tka.api.utils.player.InventorySearch.a(itemStack1, Enchantments.MULTISHOT) > 0) {
         float floatValue3 = (float)(MathHelper.atan2(vec3d2.z, vec3d2.x) * (180.0 / Math.PI)) - 90.0F;
         float floatValue4 = (float)(-(MathHelper.atan2(vec3d2.y, (double)MathHelper.sqrt((float)(vec3d2.x * vec3d2.x + vec3d2.z * vec3d2.z))) * (180.0 / Math.PI)));
         return new Vec3d[]{this.a(floatValue3 - 10.0F, floatValue4), vec3d2, this.a(floatValue3 + 10.0F, floatValue4)};
      } else {
         return new Vec3d[]{vec3d2};
      }
   }

   private Vec3d a(float floatValue1, float floatValue2) {
      float floatValue3 = floatValue1 * (float) (Math.PI / 180.0);
      float floatValue4 = floatValue2 * (float) (Math.PI / 180.0);
      float floatValue5 = MathHelper.sin(-floatValue3 - (float) Math.PI) * -MathHelper.cos(-floatValue4);
      float floatValue6 = MathHelper.sin(-floatValue4);
      float floatValue7 = MathHelper.cos(-floatValue3 - (float) Math.PI) * -MathHelper.cos(-floatValue4);
      return new Vec3d((double)floatValue5, (double)floatValue6, (double)floatValue7).normalize();
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, double doubleValue3, double doubleValue5, double doubleValue7, int intValue9, int intValue10, int intValue11, int intValue12) {
      bufferBuilder1.vertex(matrix4f2, (float)(doubleValue3 - this.at), (float)(doubleValue5 - this.au), (float)(doubleValue7 - this.av)).color(intValue9, intValue10, intValue11, intValue12);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, double[] doubleValueArray3, int intValue4, int intValue5) {
      int intValue6 = intValue5 >> 16 & 0xFF;
      int intValue7 = intValue5 >> 8 & 0xFF;
      int intValue8 = intValue5 & 0xFF;
      int intValue9 = intValue5 >> 24 & 0xFF;

      for (int intValue10 = 0; intValue10 + 1 < intValue4; intValue10++) {
         int intValue11 = intValue10 * 3;
         this.a(bufferBuilder1, matrix4f2, doubleValueArray3[intValue11], doubleValueArray3[intValue11 + 1], doubleValueArray3[intValue11 + 2], intValue6, intValue7, intValue8, intValue9);
         this.a(bufferBuilder1, matrix4f2, doubleValueArray3[intValue11 + 3], doubleValueArray3[intValue11 + 4], doubleValueArray3[intValue11 + 5], intValue6, intValue7, intValue8, intValue9);
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, int intValue4) {
      int intValue5 = intValue4 >> 16 & 0xFF;
      int intValue6 = intValue4 >> 8 & 0xFF;
      int intValue7 = intValue4 & 0xFF;
      int intValue8 = intValue4 >> 24 & 0xFF;
      double doubleValue9 = 0.35;
      this.a(bufferBuilder1, matrix4f2, vec3d3.x - doubleValue9, vec3d3.y, vec3d3.z - doubleValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, vec3d3.x + doubleValue9, vec3d3.y, vec3d3.z + doubleValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, vec3d3.x - doubleValue9, vec3d3.y, vec3d3.z + doubleValue9, intValue5, intValue6, intValue7, intValue8);
      this.a(bufferBuilder1, matrix4f2, vec3d3.x + doubleValue9, vec3d3.y, vec3d3.z - doubleValue9, intValue5, intValue6, intValue7, intValue8);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, Direction direction4, int intValue5) {
      Vec3d vec3d6 = Vec3d.of(direction4.getVector()).normalize();
      Vec3d vec3d7 = direction4 != Direction.UP && direction4 != Direction.DOWN ? vec3d6.crossProduct(new Vec3d(0.0, 1.0, 0.0)).normalize() : new Vec3d(1.0, 0.0, 0.0);
      Vec3d vec3d8 = vec3d6.crossProduct(vec3d7).normalize();
      double doubleValue9 = vec3d3.x + vec3d6.x * 0.004;
      double doubleValue11 = vec3d3.y + vec3d6.y * 0.004;
      double doubleValue13 = vec3d3.z + vec3d6.z * 0.004;
      double doubleValue15 = 0.35;
      int intValue17 = intValue5 >> 16 & 0xFF;
      int intValue18 = intValue5 >> 8 & 0xFF;
      int intValue19 = intValue5 & 0xFF;
      int intValue20 = intValue5 >> 24 & 0xFF;
      byte byteValue21 = 48;
      double doubleValue22 = 0.0;
      double doubleValue24 = 0.0;
      double doubleValue26 = 0.0;

      for (int intValue28 = 0; intValue28 <= byteValue21; intValue28++) {
         double doubleValue29 = (Math.PI * 2) * (double)intValue28 / (double)byteValue21;
         double doubleValue31 = Math.cos(doubleValue29) * doubleValue15;
         double doubleValue33 = Math.sin(doubleValue29) * doubleValue15;
         double doubleValue35 = doubleValue9 + vec3d7.x * doubleValue31 + vec3d8.x * doubleValue33;
         double doubleValue37 = doubleValue11 + vec3d7.y * doubleValue31 + vec3d8.y * doubleValue33;
         double doubleValue39 = doubleValue13 + vec3d7.z * doubleValue31 + vec3d8.z * doubleValue33;
         if (intValue28 > 0) {
            this.a(bufferBuilder1, matrix4f2, doubleValue22, doubleValue24, doubleValue26, intValue17, intValue18, intValue19, intValue20);
            this.a(bufferBuilder1, matrix4f2, doubleValue35, doubleValue37, doubleValue39, intValue17, intValue18, intValue19, intValue20);
         }

         doubleValue22 = doubleValue35;
         doubleValue24 = doubleValue37;
         doubleValue26 = doubleValue39;
      }

      this.a(bufferBuilder1, matrix4f2, doubleValue9 - vec3d7.x * doubleValue15, doubleValue11 - vec3d7.y * doubleValue15, doubleValue13 - vec3d7.z * doubleValue15, intValue17, intValue18, intValue19, intValue20);
      this.a(bufferBuilder1, matrix4f2, doubleValue9 + vec3d7.x * doubleValue15, doubleValue11 + vec3d7.y * doubleValue15, doubleValue13 + vec3d7.z * doubleValue15, intValue17, intValue18, intValue19, intValue20);
      this.a(bufferBuilder1, matrix4f2, doubleValue9 - vec3d8.x * doubleValue15, doubleValue11 - vec3d8.y * doubleValue15, doubleValue13 - vec3d8.z * doubleValue15, intValue17, intValue18, intValue19, intValue20);
      this.a(bufferBuilder1, matrix4f2, doubleValue9 + vec3d8.x * doubleValue15, doubleValue11 + vec3d8.y * doubleValue15, doubleValue13 + vec3d8.z * doubleValue15, intValue17, intValue18, intValue19, intValue20);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Entity entity3, int intValue4) {
      Box box5 = entity3.getBoundingBox();
      int intValue6 = intValue4 >> 16 & 0xFF;
      int intValue7 = intValue4 >> 8 & 0xFF;
      int intValue8 = intValue4 & 0xFF;
      int intValue9 = intValue4 >> 24 & 0xFF;
      double doubleValue10 = box5.minX;
      double doubleValue12 = box5.minY;
      double doubleValue14 = box5.minZ;
      double doubleValue16 = box5.maxX;
      double doubleValue18 = box5.maxY;
      double doubleValue20 = box5.maxZ;
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue12, doubleValue14, doubleValue16, doubleValue12, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue12, doubleValue14, doubleValue16, doubleValue12, doubleValue20, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue12, doubleValue20, doubleValue10, doubleValue12, doubleValue20, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue12, doubleValue20, doubleValue10, doubleValue12, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue18, doubleValue14, doubleValue16, doubleValue18, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue18, doubleValue14, doubleValue16, doubleValue18, doubleValue20, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue18, doubleValue20, doubleValue10, doubleValue18, doubleValue20, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue18, doubleValue20, doubleValue10, doubleValue18, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue12, doubleValue14, doubleValue10, doubleValue18, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue12, doubleValue14, doubleValue16, doubleValue18, doubleValue14, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue16, doubleValue12, doubleValue20, doubleValue16, doubleValue18, doubleValue20, intValue6, intValue7, intValue8, intValue9);
      this.a(bufferBuilder1, matrix4f2, doubleValue10, doubleValue12, doubleValue20, doubleValue10, doubleValue18, doubleValue20, intValue6, intValue7, intValue8, intValue9);
   }

   private void a(
      BufferBuilder bufferBuilder1,
      Matrix4f matrix4f2,
      double doubleValue3,
      double doubleValue5,
      double doubleValue7,
      double doubleValue9,
      double doubleValue11,
      double doubleValue13,
      int intValue15,
      int intValue16,
      int intValue17,
      int intValue18
   ) {
      this.a(bufferBuilder1, matrix4f2, doubleValue3, doubleValue5, doubleValue7, intValue15, intValue16, intValue17, intValue18);
      this.a(bufferBuilder1, matrix4f2, doubleValue9, doubleValue11, doubleValue13, intValue15, intValue16, intValue17, intValue18);
   }

   private void a(MatrixStack matrixStack1, Vec3d vec3d2, int intValue3) {
      int intValue4 = dev.leg1tka.api.utils.text.ColorUtils.d(intValue3, 82);
      int intValue5 = intValue4 >> 16 & 0xFF;
      int intValue6 = intValue4 >> 8 & 0xFF;
      int intValue7 = intValue4 & 0xFF;
      int intValue8 = intValue4 >> 24 & 0xFF;
      float floatValue9 = 4.0F;
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      RenderSystem.setShaderTexture(0, i);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      matrixStack1.push();
      matrixStack1.translate(vec3d2.x - this.at, vec3d2.y + 0.012 - this.au, vec3d2.z - this.av);
      Matrix4f matrix4f10 = matrixStack1.peek().getPositionMatrix();
      BufferBuilder bufferBuilder11 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      bufferBuilder11.vertex(matrix4f10, -floatValue9, 0.0F, -floatValue9).texture(0.0F, 0.0F).color(intValue5, intValue6, intValue7, intValue8);
      bufferBuilder11.vertex(matrix4f10, -floatValue9, 0.0F, floatValue9).texture(0.0F, 1.0F).color(intValue5, intValue6, intValue7, intValue8);
      bufferBuilder11.vertex(matrix4f10, floatValue9, 0.0F, floatValue9).texture(1.0F, 1.0F).color(intValue5, intValue6, intValue7, intValue8);
      bufferBuilder11.vertex(matrix4f10, floatValue9, 0.0F, -floatValue9).texture(1.0F, 0.0F).color(intValue5, intValue6, intValue7, intValue8);
      BufferRenderer.drawWithGlobalProgram(bufferBuilder11.end());
      matrixStack1.pop();
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
   }

   private void r() {
      this.I.clear();
      this.J.clear();
      this.R = 0;
      this.W = 0;
   }

   private void s() {
      int intValue1 = 0;

      for (int intValue2 = this.J.size(); intValue1 < intValue2; intValue1++) {
         ((Trajectories.e)this.J.get(intValue1)).h = false;
      }

      boolean flag16 = this.F.getValue();
      int intValue17 = 0;

      for (int intValue3 = this.ad.size(); intValue17 < intValue3; intValue17++) {
         Trajectories.a value4 = (Trajectories.a)this.ad.get(intValue17);
         Entity entity5 = value4.e;
         Trajectories.e value6 = (Trajectories.e)this.I.get(entity5.getId());
         if (value6 == null) {
            value6 = new Trajectories.e();
            value6.c = entity5;
            this.I.put(entity5.getId(), value6);
            this.J.add(value6);
         }

         value6.h = true;
         value6.i = false;
         value6.g++;
         value4.i = value6;
         value6.d = value4.g;
         value6.e = value4.h;
         if (value4.f != null) {
            value6.f = value4.f;
         }

         double doubleValue7 = entity5.getX();
         double doubleValue9 = entity5.getY() + (double)value4.h;
         double doubleValue11 = entity5.getZ();
         value6.a(doubleValue7, doubleValue9, doubleValue11);
         if (flag16 && value6.g >= 3) {
            Vec3d vec3d13 = entity5.getVelocity();
            int intValue14 = value4.h > 0.0F ? (this.X.nextBoolean() ? 1 : 0) : 2;

            for (int intValue15 = 0; intValue15 < intValue14; intValue15++) {
               this.a(
                  doubleValue7,
                  doubleValue9,
                  doubleValue11,
                  (float)(-vec3d13.x * 0.06 + this.X.nextGaussian() * 0.018),
                  (float)(-vec3d13.y * 0.06 + this.X.nextGaussian() * 0.018 + 0.008),
                  (float)(-vec3d13.z * 0.06 + this.X.nextGaussian() * 0.018),
                  value6.d,
                  10 + this.X.nextInt(10),
                  0.07F + this.X.nextFloat() * 0.06F
               );
            }
         }
      }

      for (int intValue18 = this.J.size() - 1; intValue18 >= 0; intValue18--) {
         Trajectories.e value21 = (Trajectories.e)this.J.get(intValue18);
         if (!value21.h) {
            if (!value21.i) {
               value21.i = true;
               if (value21.b >= 4) {
                  int intValue23 = (value21.b - 1) * 3;
                  this.a(value21.a[intValue23], value21.a[intValue23 + 1] - (double)value21.e, value21.a[intValue23 + 2], value21.f, value21.d, flag16);
               }
            }

            value21.a();
            value21.a();
            if (value21.b == 0) {
               this.I.remove(value21.c.getId());
               int intValue24 = this.J.size() - 1;
               this.J.set(intValue18, (Trajectories.e)this.J.get(intValue24));
               this.J.remove(intValue24);
            }
         }
      }

      for (int intValue19 = 0; intValue19 < this.R; intValue19++) {
         if (++this.N[intValue19] >= this.O[intValue19]) {
            this.b(intValue19--);
         } else {
            int intValue22 = intValue19 * 6;
            int intValue25 = intValue19 * 3;
            this.L[intValue22 + 3] = this.L[intValue22];
            this.L[intValue22 + 4] = this.L[intValue22 + 1];
            this.L[intValue22 + 5] = this.L[intValue22 + 2];
            this.L[intValue22] = this.L[intValue22] + (double)this.M[intValue25];
            this.L[intValue22 + 1] = this.L[intValue22 + 1] + (double)this.M[intValue25 + 1];
            this.L[intValue22 + 2] = this.L[intValue22 + 2] + (double)this.M[intValue25 + 2];
            this.M[intValue25] = this.M[intValue25] * 0.9F;
            this.M[intValue25 + 1] = this.M[intValue25 + 1] * 0.9F + 0.0025F;
            this.M[intValue25 + 2] = this.M[intValue25 + 2] * 0.9F;
         }
      }

      for (int intValue20 = 0; intValue20 < this.W; intValue20++) {
         if (++this.U[intValue20] >= 14) {
            this.c(intValue20--);
         }
      }
   }

   private void a(double doubleValue1, double doubleValue3, double doubleValue5, float floatValue7, float floatValue8, float floatValue9, int intValue10, int intValue11, float floatValue12) {
      if (this.R < 384) {
         int intValue13 = this.R++;
         int intValue14 = intValue13 * 6;
         this.L[intValue14] = this.L[intValue14 + 3] = doubleValue1;
         this.L[intValue14 + 1] = this.L[intValue14 + 4] = doubleValue3;
         this.L[intValue14 + 2] = this.L[intValue14 + 5] = doubleValue5;
         int intValue15 = intValue13 * 3;
         this.M[intValue15] = floatValue7;
         this.M[intValue15 + 1] = floatValue8;
         this.M[intValue15 + 2] = floatValue9;
         this.N[intValue13] = 0;
         this.O[intValue13] = intValue11;
         this.P[intValue13] = intValue10;
         this.Q[intValue13] = floatValue12;
      }
   }

   private void b(int intValue1) {
      int intValue2 = --this.R;
      if (intValue1 != intValue2) {
         System.arraycopy(this.L, intValue2 * 6, this.L, intValue1 * 6, 6);
         System.arraycopy(this.M, intValue2 * 3, this.M, intValue1 * 3, 3);
         this.N[intValue1] = this.N[intValue2];
         this.O[intValue1] = this.O[intValue2];
         this.P[intValue1] = this.P[intValue2];
         this.Q[intValue1] = this.Q[intValue2];
      }
   }

   private void a(double doubleValue1, double doubleValue3, double doubleValue5, Direction direction7, int intValue8, boolean flag9) {
      if (this.W < 48) {
         int intValue10 = this.W++;
         this.S[intValue10 * 3] = doubleValue1;
         this.S[intValue10 * 3 + 1] = doubleValue3;
         this.S[intValue10 * 3 + 2] = doubleValue5;
         this.T[intValue10] = direction7;
         this.U[intValue10] = 0;
         this.V[intValue10] = intValue8;
      }

      if (flag9) {
         for (int intValue15 = 0; intValue15 < 12; intValue15++) {
            double doubleValue11 = this.X.nextDouble() * Math.PI * 2.0;
            double doubleValue13 = 0.06 + this.X.nextDouble() * 0.07;
            this.a(
               doubleValue1,
               doubleValue3 + 0.05,
               doubleValue5,
               (float)(Math.cos(doubleValue11) * doubleValue13),
               0.03F + this.X.nextFloat() * 0.06F,
               (float)(Math.sin(doubleValue11) * doubleValue13),
               intValue8,
               14 + this.X.nextInt(10),
               0.08F + this.X.nextFloat() * 0.06F
            );
         }
      }
   }

   private void c(int intValue1) {
      int intValue2 = --this.W;
      if (intValue1 != intValue2) {
         System.arraycopy(this.S, intValue2 * 3, this.S, intValue1 * 3, 3);
         this.T[intValue1] = this.T[intValue2];
         this.U[intValue1] = this.U[intValue2];
         this.V[intValue1] = this.V[intValue2];
      }
   }

   private void a(MatrixStack matrixStack1, Matrix4f matrix4f2, Camera camera3, float floatValue4, int intValue5, ItemStack itemStack6) {
      boolean flag7 = this.ModuleManager > 0 || !this.ad.isEmpty();
      if (flag7 || !this.J.isEmpty() || this.R != 0 || this.W != 0) {
         this.ab = (double)((float)this.ag + floatValue4) / 20.0;
         float floatValue8 = 0.08F * this.D.f();
         float floatValue9 = this.E.f();
         this.Y.rotationY((float)Math.toRadians((double)(-camera3.getYaw()))).rotateX((float)Math.toRadians((double)camera3.getPitch()));
         this.Y.transform(this.Z.set(1.0F, 0.0F, 0.0F));
         this.Y.transform(this.ModuleStorage.set(0.0F, 1.0F, 0.0F));
         RenderSystem.enableBlend();
         RenderSystem.blendFunc(770, 1);
         RenderSystem.disableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         BufferBuilder bufferBuilder10 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

         for (int intValue11 = 0; intValue11 < this.ModuleManager; intValue11++) {
            Trajectories.c value12 = this.Storage[intValue11];
            boolean flag13 = value12.entityHit != null && value12.entityHit.isAlive();
            int intValue14 = flag13 ? -47546 : intValue5;
            this.ac = floatValue9;
            this.a(bufferBuilder10, matrix4f2, value12.points, value12.pointCount, floatValue8, intValue14, 0);
            if (flag13) {
               this.a(bufferBuilder10, matrix4f2, value12.entityHit, floatValue4, intValue11);
            } else if (value12.blockHit != null) {
               this.a(bufferBuilder10, matrix4f2, value12.hitPos, value12.blockHit.getSide(), intValue14, intValue11);
            }
         }

         int intValue25 = 0;

         for (int intValue34 = this.ad.size(); intValue25 < intValue34; intValue25++) {
            Trajectories.a value42 = (Trajectories.a)this.ad.get(intValue25);
            Entity entity50 = value42.e;
            int intValue15 = value42.g != 0 ? value42.g : intValue5;
            value42.a[0] = MathHelper.lerp((double)floatValue4, entity50.prevX, entity50.getX());
            value42.a[1] = MathHelper.lerp((double)floatValue4, entity50.prevY, entity50.getY()) + (double)value42.h;
            value42.a[2] = MathHelper.lerp((double)floatValue4, entity50.prevZ, entity50.getZ());
            boolean flag16 = value42.h > 0.0F;
            this.ac = floatValue9 * b(value42.i, floatValue4) * (flag16 ? 0.8F : 1.0F);
            if (!(this.ac <= 0.004F)) {
               this.a(bufferBuilder10, matrix4f2, value42.a, value42.b, flag16 ? floatValue8 * 0.7F : floatValue8, intValue15, 1);
               if (!flag16 || value42.c == null || !this.a(value42.c.x, value42.c.y, value42.c.z)) {
                  if (value42.d != null) {
                     if (value42.d.isAlive()) {
                        this.a(bufferBuilder10, matrix4f2, value42.d, floatValue4, entity50.getId());
                     }
                  } else if (value42.c != null) {
                     this.a(bufferBuilder10, matrix4f2, value42.c, value42.f, intValue15, entity50.getId());
                  }
               }
            }
         }

         intValue25 = 0;

         for (int intValue35 = this.J.size(); intValue25 < intValue35; intValue25++) {
            Trajectories.e value43 = (Trajectories.e)this.J.get(intValue25);
            this.ac = floatValue9 * b(value43, floatValue4);
            if (!(this.ac <= 0.004F)) {
               int intValue51 = this.a(value43, floatValue4);
               this.a(bufferBuilder10, matrix4f2, this.K, intValue51, floatValue8 * 1.6F, value43.d != 0 ? value43.d : intValue5, 2);
            }
         }

         this.ac = floatValue9;

         for (int intValue27 = 0; intValue27 < this.W; intValue27++) {
            float floatValue36 = Math.min(1.0F, ((float)this.U[intValue27] + floatValue4) / 14.0F);
            float floatValue44 = 1.0F - floatValue36;
            float floatValue52 = 1.0F - floatValue44 * floatValue44 * floatValue44;
            int intValue57 = this.V[intValue27] != 0 ? this.V[intValue27] : intValue5;
            int intValue60 = intValue27 * 3;
            this.a(
               bufferBuilder10,
               matrix4f2,
               this.S[intValue60],
               this.S[intValue60 + 1],
               this.S[intValue60 + 2],
               this.T[intValue27],
               0.1F + 0.85F * floatValue52,
               0.03F + 0.07F * floatValue44,
               intValue57,
               (int)((double)(230.0F * floatValue44) * Math.sqrt((double)floatValue44))
            );
         }

         a(bufferBuilder10);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
         RenderSystem.setShaderTexture(0, i);
         bufferBuilder10 = Tessellator.getInstance().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
         this.ac = floatValue9;

         for (int intValue28 = 0; intValue28 < this.ModuleManager; intValue28++) {
            Trajectories.c value37 = this.Storage[intValue28];
            boolean flag45 = value37.entityHit != null && value37.entityHit.isAlive();
            if (flag45 || value37.blockHit != null) {
               this.b(bufferBuilder10, matrix4f2, value37.hitPos, flag45 ? null : value37.blockHit.getSide(), flag45 ? -47546 : intValue5, intValue28);
            }
         }

         intValue25 = 0;

         for (int intValue38 = this.ad.size(); intValue25 < intValue38; intValue25++) {
            Trajectories.a value46 = (Trajectories.a)this.ad.get(intValue25);
            if (value46.c != null && (!(value46.h > 0.0F) || !this.a(value46.c.x, value46.c.y, value46.c.z))) {
               this.ac = floatValue9 * b(value46.i, floatValue4);
               int intValue53 = value46.d != null ? -47546 : (value46.g != 0 ? value46.g : intValue5);
               this.b(bufferBuilder10, matrix4f2, value46.c, value46.f, intValue53, value46.e.getId());
            }
         }

         intValue25 = 0;

         for (int intValue39 = this.J.size(); intValue25 < intValue39; intValue25++) {
            Trajectories.e value47 = (Trajectories.e)this.J.get(intValue25);
            if (value47.h) {
               this.ac = floatValue9 * b(value47, floatValue4);
               if (!(this.ac <= 0.004F)) {
                  Entity entity54 = value47.c;
                  int intValue58 = value47.d != 0 ? value47.d : intValue5;
                  double doubleValue61 = MathHelper.lerp((double)floatValue4, entity54.prevX, entity54.getX());
                  double doubleValue18 = MathHelper.lerp((double)floatValue4, entity54.prevY, entity54.getY()) + (double)value47.e;
                  double doubleValue20 = MathHelper.lerp((double)floatValue4, entity54.prevZ, entity54.getZ());
                  float floatValue22 = value47.e > 0.0F ? 0.8F : 1.0F;
                  float floatValue23 = 1.0F + 0.08F * MathHelper.sin((float)(this.ab * 7.0 + (double)entity54.getId()));
                  this.a(bufferBuilder10, matrix4f2, doubleValue61, doubleValue18, doubleValue20, 0.9F * floatValue22 * floatValue23, intValue58, 110);
                  this.a(bufferBuilder10, matrix4f2, doubleValue61, doubleValue18, doubleValue20, 0.3F * floatValue22, a(intValue58, 0.5F), 190);
               }
            }
         }

         this.ac = floatValue9;

         for (int intValue31 = 0; intValue31 < this.R; intValue31++) {
            float floatValue40 = 1.0F - Math.min(1.0F, ((float)this.N[intValue31] + floatValue4) / (float)this.O[intValue31]);
            int intValue48 = intValue31 * 6;
            int intValue55 = this.P[intValue31] != 0 ? this.P[intValue31] : intValue5;
            this.a(
               bufferBuilder10,
               matrix4f2,
               MathHelper.lerp((double)floatValue4, this.L[intValue48 + 3], this.L[intValue48]),
               MathHelper.lerp((double)floatValue4, this.L[intValue48 + 4], this.L[intValue48 + 1]),
               MathHelper.lerp((double)floatValue4, this.L[intValue48 + 5], this.L[intValue48 + 2]),
               this.Q[intValue31] * (0.4F + 0.6F * floatValue40),
               a(intValue55, 0.45F),
               (int)(255.0F * floatValue40)
            );
         }

         for (int intValue32 = 0; intValue32 < this.W; intValue32++) {
            float floatValue41 = Math.min(1.0F, ((float)this.U[intValue32] + floatValue4) / 14.0F);
            float floatValue49 = 1.0F - floatValue41;
            int intValue56 = this.V[intValue32] != 0 ? this.V[intValue32] : intValue5;
            int intValue59 = intValue32 * 3;
            this.a(
               bufferBuilder10,
               matrix4f2,
               this.S[intValue59],
               this.S[intValue59 + 1] + 0.1,
               this.S[intValue59 + 2],
               0.4F + 1.1F * (1.0F - floatValue49 * floatValue49),
               a(intValue56, 0.5F),
               (int)(200.0F * floatValue49 * floatValue49)
            );
         }

         a(bufferBuilder10);
         RenderSystem.setShaderTexture(0, 0);
         if (itemStack6.isOf(Items.SPLASH_POTION)) {
            RenderSystem.defaultBlendFunc();

            for (int intValue33 = 0; intValue33 < this.ModuleManager; intValue33++) {
               if (this.Storage[intValue33].hitPos != null) {
                  this.a(matrixStack1, this.Storage[intValue33].hitPos, intValue5);
               }
            }
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableBlend();
      }
   }

   private static void a(BufferBuilder bufferBuilder0) {
      BuiltBuffer builtBuffer1 = bufferBuilder0.endNullable();
      if (builtBuffer1 != null) {
         BufferRenderer.drawWithGlobalProgram(builtBuffer1);
      }
   }

   private int a(Trajectories.e value1, float floatValue2) {
      int intValue3 = value1.h ? value1.b - 1 : value1.b;
      if (intValue3 < 0) {
         intValue3 = 0;
      }

      System.arraycopy(value1.a, 0, this.K, 0, intValue3 * 3);
      if (!value1.h) {
         return intValue3;
      } else {
         Entity entity4 = value1.c;
         int intValue5 = intValue3 * 3;
         this.K[intValue5] = MathHelper.lerp((double)floatValue2, entity4.prevX, entity4.getX());
         this.K[intValue5 + 1] = MathHelper.lerp((double)floatValue2, entity4.prevY, entity4.getY()) + (double)value1.e;
         this.K[intValue5 + 2] = MathHelper.lerp((double)floatValue2, entity4.prevZ, entity4.getZ());
         return intValue3 + 1;
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, double[] doubleValueArray3, int intValue4, float floatValue5, int intValue6, int intValue7) {
      if (intValue4 >= 2) {
         int intValue8 = intValue6 >> 16 & 0xFF;
         int intValue9 = intValue6 >> 8 & 0xFF;
         int intValue10 = intValue6 & 0xFF;
         int intValue11 = a(intValue6, 0.65F);
         int intValue12 = intValue11 >> 16 & 0xFF;
         int intValue13 = intValue11 >> 8 & 0xFF;
         int intValue14 = intValue11 & 0xFF;
         double doubleValue15 = 0.0;
         if (intValue7 != 2) {
            for (byte byteValue17 = 3; byteValue17 < intValue4 * 3; byteValue17 += 3) {
               doubleValue15 += c(doubleValueArray3, byteValue17);
            }
         }

         float floatValue52 = 0.0F;
         float floatValue18 = 0.0F;
         float floatValue19 = 0.0F;
         float floatValue20 = 0.0F;
         float floatValue21 = 0.0F;
         float floatValue22 = 0.0F;
         float floatValue23 = 1.0F;
         float floatValue24 = 0.0F;
         float floatValue25 = 0.0F;
         float floatValue26 = 0.0F;
         int intValue27 = 0;
         int intValue28 = 0;
         double doubleValue29 = 0.0;

         for (int intValue31 = 0; intValue31 < intValue4; intValue31++) {
            int intValue32 = intValue31 * 3;
            float floatValue33 = (float)(doubleValueArray3[intValue32] - this.at);
            float floatValue34 = (float)(doubleValueArray3[intValue32 + 1] - this.au);
            float floatValue35 = (float)(doubleValueArray3[intValue32 + 2] - this.av);
            if (intValue31 > 0) {
               doubleValue29 += c(doubleValueArray3, intValue32);
            }

            int intValue36 = intValue31 == 0 ? intValue32 : intValue32 - 3;
            int intValue37 = intValue31 == intValue4 - 1 ? intValue32 : intValue32 + 3;
            float floatValue38 = (float)(doubleValueArray3[intValue37] - doubleValueArray3[intValue36]);
            float floatValue39 = (float)(doubleValueArray3[intValue37 + 1] - doubleValueArray3[intValue36 + 1]);
            float floatValue40 = (float)(doubleValueArray3[intValue37 + 2] - doubleValueArray3[intValue36 + 2]);
            float floatValue41 = floatValue39 * floatValue35 - floatValue40 * floatValue34;
            float floatValue42 = floatValue40 * floatValue33 - floatValue38 * floatValue35;
            float floatValue43 = floatValue38 * floatValue34 - floatValue39 * floatValue33;
            float floatValue44 = (float)Math.sqrt((double)(floatValue41 * floatValue41 + floatValue42 * floatValue42 + floatValue43 * floatValue43));
            if (floatValue44 > 1.0E-6F) {
               floatValue21 = floatValue41 / floatValue44;
               floatValue22 = floatValue42 / floatValue44;
               floatValue23 = floatValue43 / floatValue44;
            }

            float floatValue45;
            float floatValue46;
            if (intValue7 == 2) {
               float floatValue47 = (float)intValue31 / (float)(intValue4 - 1);
               floatValue45 = floatValue47 * floatValue47;
               floatValue46 = 0.2F + 0.8F * floatValue47;
            } else {
               float floatValue54 = intValue7 == 0 ? MathHelper.clamp((float)((doubleValue29 - 0.8) / 2.2), 0.0F, 1.0F) : 1.0F;
               float floatValue48 = MathHelper.clamp((float)((doubleValue15 - doubleValue29) / 0.7), 0.0F, 1.0F);
               float floatValue49 = 0.5F + 0.5F * MathHelper.sin((float)(doubleValue29 * 1.4 - this.ab * 6.0));
               floatValue49 *= floatValue49;
               floatValue49 *= floatValue49;
               floatValue45 = floatValue54 * floatValue48 * (0.5F + 0.5F * floatValue49) * (intValue7 == 1 ? 0.75F : 1.0F);
               floatValue46 = 0.85F + 0.3F * floatValue49;
            }

            floatValue45 *= this.ac * a(floatValue33, floatValue34, floatValue35);
            float floatValue55 = floatValue5 * floatValue46;
            int intValue56 = Math.min(255, (int)(floatValue45 * 120.0F));
            int intValue59 = Math.min(255, (int)(floatValue45 * 235.0F));
            if (intValue31 > 0) {
               a(
                  bufferBuilder1,
                  matrix4f2,
                  floatValue52,
                  floatValue18,
                  floatValue19,
                  floatValue24 * floatValue20,
                  floatValue25 * floatValue20,
                  floatValue26 * floatValue20,
                  intValue27,
                  floatValue33,
                  floatValue34,
                  floatValue35,
                  floatValue21 * floatValue55,
                  floatValue22 * floatValue55,
                  floatValue23 * floatValue55,
                  intValue56,
                  intValue8,
                  intValue9,
                  intValue10
               );
               float floatValue50 = floatValue20 * 0.28F;
               float floatValue51 = floatValue55 * 0.28F;
               a(
                  bufferBuilder1,
                  matrix4f2,
                  floatValue52,
                  floatValue18,
                  floatValue19,
                  floatValue24 * floatValue50,
                  floatValue25 * floatValue50,
                  floatValue26 * floatValue50,
                  intValue28,
                  floatValue33,
                  floatValue34,
                  floatValue35,
                  floatValue21 * floatValue51,
                  floatValue22 * floatValue51,
                  floatValue23 * floatValue51,
                  intValue59,
                  intValue12,
                  intValue13,
                  intValue14
               );
            }

            floatValue52 = floatValue33;
            floatValue18 = floatValue34;
            floatValue19 = floatValue35;
            floatValue24 = floatValue21;
            floatValue25 = floatValue22;
            floatValue26 = floatValue23;
            floatValue20 = floatValue55;
            intValue27 = intValue56;
            intValue28 = intValue59;
         }
      }
   }

   private static double c(double[] doubleValueArray0, int intValue1) {
      double doubleValue2 = doubleValueArray0[intValue1] - doubleValueArray0[intValue1 - 3];
      double doubleValue4 = doubleValueArray0[intValue1 + 1] - doubleValueArray0[intValue1 - 2];
      double doubleValue6 = doubleValueArray0[intValue1 + 2] - doubleValueArray0[intValue1 - 1];
      return Math.sqrt(doubleValue2 * doubleValue2 + doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6);
   }

   private static void a(
      BufferBuilder bufferBuilder0,
      Matrix4f matrix4f1,
      float floatValue2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      float floatValue7,
      int intValue8,
      float floatValue9,
      float floatValue10,
      float floatValue11,
      float floatValue12,
      float floatValue13,
      float floatValue14,
      int intValue15,
      int intValue16,
      int intValue17,
      int intValue18
   ) {
      if (intValue8 > 0 || intValue15 > 0) {
         bufferBuilder0.vertex(matrix4f1, floatValue2 - floatValue5, floatValue3 - floatValue6, floatValue4 - floatValue7).color(intValue16, intValue17, intValue18, 0);
         bufferBuilder0.vertex(matrix4f1, floatValue2, floatValue3, floatValue4).color(intValue16, intValue17, intValue18, intValue8);
         bufferBuilder0.vertex(matrix4f1, floatValue9, floatValue10, floatValue11).color(intValue16, intValue17, intValue18, intValue15);
         bufferBuilder0.vertex(matrix4f1, floatValue9 - floatValue12, floatValue10 - floatValue13, floatValue11 - floatValue14).color(intValue16, intValue17, intValue18, 0);
         bufferBuilder0.vertex(matrix4f1, floatValue2, floatValue3, floatValue4).color(intValue16, intValue17, intValue18, intValue8);
         bufferBuilder0.vertex(matrix4f1, floatValue2 + floatValue5, floatValue3 + floatValue6, floatValue4 + floatValue7).color(intValue16, intValue17, intValue18, 0);
         bufferBuilder0.vertex(matrix4f1, floatValue9 + floatValue12, floatValue10 + floatValue13, floatValue11 + floatValue14).color(intValue16, intValue17, intValue18, 0);
         bufferBuilder0.vertex(matrix4f1, floatValue9, floatValue10, floatValue11).color(intValue16, intValue17, intValue18, intValue15);
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, double doubleValue3, double doubleValue5, double doubleValue7, Direction direction9, float floatValue10, float floatValue11, int intValue12, int intValue13) {
      if (intValue13 > 0 && !(floatValue10 <= 0.0F)) {
         float floatValue14 = 1.0F;
         float floatValue15 = 0.0F;
         float floatValue16 = 0.0F;
         float floatValue17 = 0.0F;
         float floatValue18 = 0.0F;
         float floatValue19 = 1.0F;
         float floatValue20 = 0.0F;
         float floatValue21 = 1.0F;
         float floatValue22 = 0.0F;
         if (direction9 != null) {
            floatValue20 = (float)direction9.getOffsetX();
            floatValue21 = (float)direction9.getOffsetY();
            floatValue22 = (float)direction9.getOffsetZ();
            if (direction9.getAxis() == Axis.X) {
               floatValue14 = 0.0F;
               floatValue16 = 1.0F;
               floatValue18 = 1.0F;
               floatValue19 = 0.0F;
            } else if (direction9.getAxis() == Axis.Z) {
               floatValue18 = 1.0F;
               floatValue19 = 0.0F;
            }
         }

         float floatValue23 = (float)(doubleValue3 - this.at) + floatValue20 * 0.015F;
         float floatValue24 = (float)(doubleValue5 - this.au) + floatValue21 * 0.015F;
         float floatValue25 = (float)(doubleValue7 - this.av) + floatValue22 * 0.015F;
         intValue13 = Math.min(255, (int)((float)intValue13 * this.ac * a(floatValue23, floatValue24, floatValue25)));
         if (intValue13 > 0) {
            float floatValue26 = Math.max(0.0F, floatValue10 - floatValue11);
            float floatValue27 = floatValue10 + floatValue11;
            int intValue28 = intValue12 >> 16 & 0xFF;
            int intValue29 = intValue12 >> 8 & 0xFF;
            int intValue30 = intValue12 & 0xFF;
            float floatValue31 = floatValue23 * floatValue23 + floatValue24 * floatValue24 + floatValue25 * floatValue25;
            int intValue32 = floatValue31 > 1024.0F ? 4 : (floatValue31 > 144.0F ? 2 : 1);

            for (int intValue33 = 0; intValue33 < 40; intValue33 += intValue32) {
               float floatValue34 = v[intValue33];
               float floatValue35 = w[intValue33];
               float floatValue36 = v[intValue33 + intValue32];
               float floatValue37 = w[intValue33 + intValue32];
               float floatValue38 = floatValue14 * floatValue34 + floatValue17 * floatValue35;
               float floatValue39 = floatValue15 * floatValue34 + floatValue18 * floatValue35;
               float floatValue40 = floatValue16 * floatValue34 + floatValue19 * floatValue35;
               float floatValue41 = floatValue14 * floatValue36 + floatValue17 * floatValue37;
               float floatValue42 = floatValue15 * floatValue36 + floatValue18 * floatValue37;
               float floatValue43 = floatValue16 * floatValue36 + floatValue19 * floatValue37;
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue38 * floatValue26, floatValue24 + floatValue39 * floatValue26, floatValue25 + floatValue40 * floatValue26).color(intValue28, intValue29, intValue30, 0);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue38 * floatValue10, floatValue24 + floatValue39 * floatValue10, floatValue25 + floatValue40 * floatValue10).color(intValue28, intValue29, intValue30, intValue13);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue41 * floatValue10, floatValue24 + floatValue42 * floatValue10, floatValue25 + floatValue43 * floatValue10).color(intValue28, intValue29, intValue30, intValue13);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue41 * floatValue26, floatValue24 + floatValue42 * floatValue26, floatValue25 + floatValue43 * floatValue26).color(intValue28, intValue29, intValue30, 0);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue38 * floatValue10, floatValue24 + floatValue39 * floatValue10, floatValue25 + floatValue40 * floatValue10).color(intValue28, intValue29, intValue30, intValue13);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue38 * floatValue27, floatValue24 + floatValue39 * floatValue27, floatValue25 + floatValue40 * floatValue27).color(intValue28, intValue29, intValue30, 0);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue41 * floatValue27, floatValue24 + floatValue42 * floatValue27, floatValue25 + floatValue43 * floatValue27).color(intValue28, intValue29, intValue30, 0);
               bufferBuilder1.vertex(matrix4f2, floatValue23 + floatValue41 * floatValue10, floatValue24 + floatValue42 * floatValue10, floatValue25 + floatValue43 * floatValue10).color(intValue28, intValue29, intValue30, intValue13);
            }
         }
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, Direction direction4, int intValue5, int intValue6) {
      float floatValue7 = a(this.ab * 0.9 + (double)intValue6 * 0.37);
      float floatValue8 = 1.0F - floatValue7;
      this.a(bufferBuilder1, matrix4f2, vec3d3.x, vec3d3.y, vec3d3.z, direction4, 0.32F, 0.05F, intValue5, 210);
      this.a(bufferBuilder1, matrix4f2, vec3d3.x, vec3d3.y, vec3d3.z, direction4, 0.1F + 0.6F * floatValue7, 0.015F + 0.06F * floatValue8, intValue5, (int)(180.0F * floatValue8 * floatValue8));
      this.a(bufferBuilder1, matrix4f2, vec3d3.x, vec3d3.y, vec3d3.z, direction4, 0.1F, 0.08F, a(intValue5, 0.6F), 140);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Entity entity3, float floatValue4, int intValue5) {
      double doubleValue6 = MathHelper.lerp((double)floatValue4, entity3.prevX, entity3.getX());
      double doubleValue8 = MathHelper.lerp((double)floatValue4, entity3.prevY, entity3.getY());
      double doubleValue10 = MathHelper.lerp((double)floatValue4, entity3.prevZ, entity3.getZ());
      float floatValue12 = entity3.getWidth() * 0.75F;
      float floatValue13 = a(this.ab * 1.2 + (double)intValue5 * 0.37);
      this.a(bufferBuilder1, matrix4f2, doubleValue6, doubleValue8, doubleValue10, Direction.UP, floatValue12, 0.05F, -47546, 200);
      this.a(
         bufferBuilder1,
         matrix4f2,
         doubleValue6,
         doubleValue8 + (double)(entity3.getHeight() * floatValue13),
         doubleValue10,
         Direction.UP,
         floatValue12 * (1.0F - 0.25F * floatValue13),
         0.04F,
         -47546,
         (int)(170.0F * (1.0F - floatValue13))
      );
   }

   private void b(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, Vec3d vec3d3, Direction direction4, int intValue5, int intValue6) {
      float floatValue7 = 1.0F + 0.1F * MathHelper.sin((float)(this.ab * 5.0 + (double)intValue6));
      double doubleValue8 = direction4 != null ? (double)direction4.getOffsetX() * 0.05 : 0.0;
      double doubleValue10 = direction4 != null ? (double)direction4.getOffsetY() * 0.05 : 0.0;
      double doubleValue12 = direction4 != null ? (double)direction4.getOffsetZ() * 0.05 : 0.0;
      this.a(bufferBuilder1, matrix4f2, vec3d3.x + doubleValue8, vec3d3.y + doubleValue10, vec3d3.z + doubleValue12, 0.75F * floatValue7, intValue5, 110);
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, double doubleValue3, double doubleValue5, double doubleValue7, float floatValue9, int intValue10, int intValue11) {
      float floatValue12 = (float)(doubleValue3 - this.at);
      float floatValue13 = (float)(doubleValue5 - this.au);
      float floatValue14 = (float)(doubleValue7 - this.av);
      intValue11 = Math.min(255, (int)((float)intValue11 * this.ac * a(floatValue12, floatValue13, floatValue14)));
      if (intValue11 > 0) {
         float floatValue15 = floatValue9 * 0.5F;
         float floatValue16 = this.Z.x * floatValue15;
         float floatValue17 = this.Z.y * floatValue15;
         float floatValue18 = this.Z.z * floatValue15;
         float floatValue19 = this.ModuleStorage.x * floatValue15;
         float floatValue20 = this.ModuleStorage.y * floatValue15;
         float floatValue21 = this.ModuleStorage.z * floatValue15;
         int intValue22 = intValue10 >> 16 & 0xFF;
         int intValue23 = intValue10 >> 8 & 0xFF;
         int intValue24 = intValue10 & 0xFF;
         bufferBuilder1.vertex(matrix4f2, floatValue12 - floatValue16 - floatValue19, floatValue13 - floatValue17 - floatValue20, floatValue14 - floatValue18 - floatValue21).texture(0.0F, 1.0F).color(intValue22, intValue23, intValue24, intValue11);
         bufferBuilder1.vertex(matrix4f2, floatValue12 - floatValue16 + floatValue19, floatValue13 - floatValue17 + floatValue20, floatValue14 - floatValue18 + floatValue21).texture(0.0F, 0.0F).color(intValue22, intValue23, intValue24, intValue11);
         bufferBuilder1.vertex(matrix4f2, floatValue12 + floatValue16 + floatValue19, floatValue13 + floatValue17 + floatValue20, floatValue14 + floatValue18 + floatValue21).texture(1.0F, 0.0F).color(intValue22, intValue23, intValue24, intValue11);
         bufferBuilder1.vertex(matrix4f2, floatValue12 + floatValue16 - floatValue19, floatValue13 + floatValue17 - floatValue20, floatValue14 + floatValue18 - floatValue21).texture(1.0F, 1.0F).color(intValue22, intValue23, intValue24, intValue11);
      }
   }

   private static float b(Trajectories.e value0, float floatValue1) {
      if (value0 == null) {
         return 0.0F;
      } else {
         float floatValue2 = MathHelper.clamp(((float)value0.g + floatValue1) / 7.0F, 0.0F, 1.0F);
         return floatValue2 * floatValue2 * (3.0F - 2.0F * floatValue2);
      }
   }

   private static float a(float floatValue0, float floatValue1, float floatValue2) {
      float floatValue3 = floatValue0 * floatValue0 + floatValue1 * floatValue1 + floatValue2 * floatValue2;
      if (floatValue3 >= 12.25F) {
         return 1.0F;
      } else {
         float floatValue4 = MathHelper.clamp(((float)Math.sqrt((double)floatValue3) - 1.0F) / 2.5F, 0.0F, 1.0F);
         return floatValue4 * floatValue4 * (3.0F - 2.0F * floatValue4);
      }
   }

   private boolean a(double doubleValue1, double doubleValue3, double doubleValue5) {
      double doubleValue7 = doubleValue1 - this.at;
      double doubleValue9 = doubleValue3 - this.au;
      double doubleValue11 = doubleValue5 - this.av;
      return doubleValue7 * doubleValue7 + doubleValue9 * doubleValue9 + doubleValue11 * doubleValue11 > 2304.0;
   }

   private static int a(int intValue0, float floatValue1) {
      int intValue2 = intValue0 >> 16 & 0xFF;
      int intValue3 = intValue0 >> 8 & 0xFF;
      int intValue4 = intValue0 & 0xFF;
      intValue2 += (int)((float)(255 - intValue2) * floatValue1);
      intValue3 += (int)((float)(255 - intValue3) * floatValue1);
      intValue4 += (int)((float)(255 - intValue4) * floatValue1);
      return 0xFF000000 | intValue2 << 16 | intValue3 << 8 | intValue4;
   }

   private static float a(double doubleValue0) {
      return (float)(doubleValue0 - Math.floor(doubleValue0));
   }

   static {
      for (int intValue0 = 0; intValue0 <= 40; intValue0++) {
         double doubleValue1 = (Math.PI * 2) * (double)intValue0 / 40.0;
         v[intValue0] = (float)Math.cos(doubleValue1);
         w[intValue0] = (float)Math.sin(doubleValue1);
      }
   }

   static final class a {
      final double[] a = new double[903];
      int b;
      Vec3d c;
      Entity d;
      Entity e;
      Direction f;
      int g;
      float h;
      Trajectories.e i;

      a() {
      }
   }

   static record b(ItemStack stack, Vec3d pos, int ticks)  {


}

   static record c(double[] points, int pointCount, BlockHitResult blockHit, Vec3d hitPos, Entity entityHit)  {




}

   static record d(double velocity, double gravity, double drag)  {


}

   static final class e {
      final double[] a = new double[48];
      int b;
      Entity c;
      int d;
      float e;
      Direction f;
      int g = -1;
      boolean h;
      boolean i;

      e() {
      }

      void a(double doubleValue1, double doubleValue3, double doubleValue5) {
         if (this.b == 16) {
            System.arraycopy(this.a, 3, this.a, 0, 45);
            this.b--;
         }

         int intValue7 = this.b * 3;
         this.a[intValue7] = doubleValue1;
         this.a[intValue7 + 1] = doubleValue3;
         this.a[intValue7 + 2] = doubleValue5;
         this.b++;
      }

      void a() {
         if (this.b != 0) {
            System.arraycopy(this.a, 3, this.a, 0, (this.b - 1) * 3);
            this.b--;
         }
      }
   }
}
