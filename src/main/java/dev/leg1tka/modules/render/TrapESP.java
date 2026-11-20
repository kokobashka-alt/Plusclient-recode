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

import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventBlockChange;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.api.events.implement.EventUpdate;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.boss.BossBar.Color;
import net.minecraft.entity.boss.BossBar.Style;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class TrapESP extends Module {
   public static final TrapESP c = new TrapESP();
   private static final Set d = Set.of(
      Blocks.QUARTZ_BLOCK,
      Blocks.DEAD_TUBE_CORAL_BLOCK,
      Blocks.INFESTED_MOSSY_STONE_BRICKS,
      Blocks.PURPUR_PILLAR,
      Blocks.END_STONE_BRICKS,
      Blocks.NETHER_BRICKS,
      Blocks.GILDED_BLACKSTONE,
      Blocks.PRISMARINE_BRICKS,
      Blocks.ICE,
      Blocks.NETHER_WART_BLOCK,
      Blocks.RESPAWN_ANCHOR,
      Blocks.NETHERITE_BLOCK,
      Blocks.WHITE_STAINED_GLASS,
      Blocks.BLACK_CONCRETE,
      Blocks.POLISHED_BASALT
   );
   private final List<TrapESP.a> e = new ObjectArrayList<>();
   private final Set<BlockPos> f = new ObjectOpenHashSet<>();
   private int g;
   private final Matrix4f h = new Matrix4f();
   private Vec3d i = Vec3d.ZERO;
   private final Quaternionf j = new Quaternionf();
   private boolean k;

   public static boolean a(Block block0) {
      return d.contains(block0);
   }

   public TrapESP() {
      super(
         "TrapESP",
         "Отображает время до исчезновения структур трапки и пласта",
         Category.RENDER
      );
   }

   @Override
   public void onDisable() {
      for (TrapESP.a value2 : this.e) {
         value2.b();
      }

      this.e.clear();
      this.f.clear();
      this.g = 0;
      super.onDisable();
   }

   @EventLink
   public void onBlockChange(EventBlockChange eventBlockChange1) {
      if (d.contains(eventBlockChange1.getNewState().getBlock()) && !d.contains(eventBlockChange1.getOldState().getBlock())) {
         this.f.add(eventBlockChange1.getPos());
         this.g = 2;
      }
   }

   @EventLink
   public void onUpdate(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null) {
         Iterator iterator2 = this.e.iterator();

         while (iterator2.hasNext()) {
            TrapESP.a value3 = (TrapESP.a)iterator2.next();
            if (value3.a()) {
               value3.b();
               iterator2.remove();
            } else {
               value3.a(minecraftClient.player);
            }
         }

         if (this.g > 0) {
            this.g--;
            if (this.g == 0 && !this.f.isEmpty()) {
               int intValue14 = Integer.MAX_VALUE;
               int intValue4 = Integer.MAX_VALUE;
               int intValue5 = Integer.MAX_VALUE;
               int intValue6 = Integer.MIN_VALUE;
               int intValue7 = Integer.MIN_VALUE;
               int intValue8 = Integer.MIN_VALUE;

               for (BlockPos blockPos10 : this.f) {
                  intValue14 = Math.min(intValue14, blockPos10.getX());
                  intValue4 = Math.min(intValue4, blockPos10.getY());
                  intValue5 = Math.min(intValue5, blockPos10.getZ());
                  intValue6 = Math.max(intValue6, blockPos10.getX());
                  intValue7 = Math.max(intValue7, blockPos10.getY());
                  intValue8 = Math.max(intValue8, blockPos10.getZ());
               }

               BlockBox blockBox15 = new BlockBox(intValue14, intValue4, intValue5, intValue6, intValue7, intValue8);

               for (TrapType trapType13 : TrapType.values()) {
                  if (trapType13.a(blockBox15, this.f)) {
                     this.e.add(new TrapESP.a(trapType13, blockBox15));
                     break;
                  }
               }

               this.f.clear();
            }
         }
      }
   }

   @EventLink
   public void onRender3D(Event3DRender event3DRender1) {
      this.h.set(event3DRender1.getProjectionMatrix());
      this.i = event3DRender1.getCamera().getPos();
      this.j.set(event3DRender1.getCamera().getRotation());
      this.k = true;
   }

   @EventLink
   public void onRender2D(EventRender.Default default1) {
      if (this.k && !this.e.isEmpty() && minecraftClient.player != null) {
         DrawContext drawContext2 = default1.getContext();
         MatrixStack matrixStack3 = drawContext2.getMatrices();
         dev.leg1tka.render.font.MsdfTextRenderer value4 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         if (value4 != null) {
            for (TrapESP.a value6 : this.e) {
               BlockPos blockPos7 = value6.b.getCenter();
               Vec3d vec3d8 = new Vec3d((double)blockPos7.getX() + 0.5, (double)blockPos7.getY() + 0.5, (double)blockPos7.getZ() + 0.5);
               Vec3d vec3d9 = this.a(vec3d8);
               if (vec3d9 != null && !(vec3d9.z >= 1.0)) {
                  float floatValue10 = Math.max(0.0F, (float)(value6.a.d - (System.currentTimeMillis() - value6.c)) / 1000.0F);
                  String string11 = String.format(Locale.US, "%.1f", floatValue10);
                  float floatValue12 = value4.b(string11);
                  float floatValue13 = 14.0F + floatValue12 + 4.0F;
                  float floatValue14 = (float)vec3d9.x - floatValue13 / 2.0F;
                  float floatValue15 = (float)vec3d9.y - 6.0F;
                  dev.leg1tka.render.RenderPrimitives.b(matrixStack3, floatValue14, floatValue15, floatValue13, 12.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 120));
                  matrixStack3.push();
                  matrixStack3.translate(floatValue14 + 1.5F, floatValue15 + 1.5F, 0.0F);
                  matrixStack3.scale(0.55F, 0.55F, 1.0F);
                  drawContext2.drawItem(value6.a.e.getDefaultStack(), 0, 0);
                  matrixStack3.pop();
                  value4.a(matrixStack3, string11, floatValue14 + 13.5F, floatValue15 + 3.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 255));
               }
            }
         }
      }
   }

   private Vec3d a(Vec3d vec3d1) {
      if (minecraftClient.player == null) {
         return null;
      } else {
         Vector3f vector3f2 = new Vector3f((float)(vec3d1.x - this.i.x), (float)(vec3d1.y - this.i.y), (float)(vec3d1.z - this.i.z));
         vector3f2.rotate(new Quaternionf(this.j).conjugate());
         Vector4f vector4f3 = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0F);
         this.h.transform(vector4f3);
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

   public static class a {
      public final TrapType a;
      public final BlockBox b;
      public final long c;
      private ClientBossBar d;

      public a(TrapType trapType1, BlockBox blockBox2) {
         this.a = trapType1;
         this.b = blockBox2;
         this.c = System.currentTimeMillis();
      }

      public boolean a() {
         return System.currentTimeMillis() - this.c >= this.a.d;
      }

      public void a(ClientPlayerEntity clientPlayerEntity1) {
         if (clientPlayerEntity1 != null && dev.leg1tka.api.ClientAccess.minecraftClient.inGameHud != null) {
            if (this.b.contains(clientPlayerEntity1.getBlockPos())) {
               float floatValue2 = Math.max(0.0F, (float)(this.a.d - (System.currentTimeMillis() - this.c)) / 1000.0F);
               float floatValue3 = MathHelper.clamp(floatValue2 / ((float)this.a.d / 1000.0F), 0.0F, 1.0F);
               MutableText mutableText4 = Text.literal(String.format(Locale.US, "%s — %.1f сек", this.a.a, floatValue2));
               if (this.d == null) {
                  this.d = new ClientBossBar(UUID.randomUUID(), mutableText4, floatValue3, Color.WHITE, Style.PROGRESS, false, false, false);
                  dev.leg1tka.api.ClientAccess.minecraftClient.inGameHud.getBossBarHud().bossBars.put(this.d.getUuid(), this.d);
               } else {
                  this.d.setName(mutableText4);
                  this.d.setPercent(floatValue3);
               }
            } else {
               this.b();
            }
         }
      }

      public void b() {
         if (this.d != null && dev.leg1tka.api.ClientAccess.minecraftClient.inGameHud != null) {
            dev.leg1tka.api.ClientAccess.minecraftClient.inGameHud.getBossBarHud().bossBars.remove(this.d.getUuid());
            this.d = null;
         }
      }
   }

   public static enum TrapType {
      DRAGON_TRAPKA(
         "Драконья трапка",
         new int[][]{{7, 7, 7}, {7, 7, 6}},
         true,
         30000L,
         Items.NETHERITE_SCRAP
      ),
      TRAPKA("Трапка", new int[][]{{5, 5, 5}, {5, 5, 4}, {5, 6, 5}, {5, 6, 4}}, true, 15000L, Items.NETHERITE_SCRAP),
      DRAGON_PLAST(
         "Драконий пласт", new int[][]{{7, 7, 2}, {7, 7, 1}}, true, 20000L, Items.DRIED_KELP
      ),
      PLAST("Пласт", new int[][]{{5, 5, 2}, {5, 5, 1}}, true, 20000L, Items.DRIED_KELP),
      GARMOSHKA("Пласт", new int[][]{{5, 5, 5}, {5, 6, 5}}, false, 20000L, Items.DRIED_KELP);

      public final String a;
      public final int[][] b;
      public final boolean c;
      public final long d;
      public final Item e;

      private TrapType(String string3, int[][] intValueArrayArray4, boolean flag5, long longValue6, Item item8) {
         this.a = string3;
         this.b = intValueArrayArray4;
         this.c = flag5;
         this.d = longValue6;
         this.e = item8;
      }

      public boolean a(BlockBox blockBox1, Set<BlockPos> set2) {
         int[] intValueArray3 = new int[]{blockBox1.getBlockCountX(), blockBox1.getBlockCountY(), blockBox1.getBlockCountZ()};
         Arrays.sort(intValueArray3);

         for (int[] intValueArray7 : this.b) {
            int[] intValueArray8 = (int[])intValueArray7.clone();
            Arrays.sort(intValueArray8);
            if (Arrays.equals(intValueArray3, intValueArray8)) {
               return this.c
                  == set2.stream()
                     .noneMatch(
                        blockPos1x -> blockPos1x.getX() > blockBox1.getMinX()
                              && blockPos1x.getX() < blockBox1.getMaxX()
                              && blockPos1x.getY() > blockBox1.getMinY()
                              && blockPos1x.getY() < blockBox1.getMaxY()
                              && blockPos1x.getZ() > blockBox1.getMinZ()
                              && blockPos1x.getZ() < blockBox1.getMaxZ()
                     );
            }
         }

         return false;
      }
   }
}
