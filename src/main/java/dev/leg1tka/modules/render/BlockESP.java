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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;

public class BlockESP extends Module {
   public static BlockESP c = new BlockESP();
   private static float d;
   private static float e;
   private static float f;
   private static float g;
   private static float h;
   private static long i;
   private static int j;
   private final NumberSetting k = new NumberSetting("Дистанция", 60.0F, 10.0F, 120.0F, 1.0F);
   private final Set<String> l = ConcurrentHashMap.newKeySet();
   private final Map<BlockPos, String> m = new ConcurrentHashMap<>();
   private final Set<ChunkPos> n = ConcurrentHashMap.newKeySet();
   private ChunkPos o;
   private int p = -1;
   private long q;

   public BlockESP() {
      super(
         "BlockESP",
         "Показывает выбранные блоки через стену",
         Category.RENDER
      );
      this.addSettings(new Setting[]{this.k});
   }

   @Override
   public void onEnable() {
      this.s();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.s();
      super.onDisable();
   }

   @EventLink(
      priority = 100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      if (minecraftClient.world != null && minecraftClient.player != null && !this.l.isEmpty()) {
         int intValue2 = this.r();
         ChunkPos chunkPos3 = new ChunkPos(minecraftClient.player.getBlockPos());
         if (intValue2 != this.p) {
            this.s();
            this.p = intValue2;
         }

         if (this.o == null || !this.o.equals(chunkPos3)) {
            this.n.clear();
            this.o = chunkPos3;
         }

         long longValue4 = System.currentTimeMillis();
         if (longValue4 - this.q >= 50L) {
            this.b(intValue2);
            this.q = longValue4;
         }

         this.a(minecraftClient.player.getPos(), intValue2);
         this.a(event3DRender1.getMatrices());
      }
   }

   private void b(int intValue1) {
      if (minecraftClient.world != null && minecraftClient.player != null) {
         BlockPos blockPos2 = minecraftClient.player.getBlockPos();
         int intValue3 = blockPos2.getX() >> 4;
         int intValue4 = blockPos2.getZ() >> 4;
         int intValue5 = (intValue1 >> 4) + 2;
         ArrayList<ChunkPos> arrayList6 = new ArrayList<>();

         for (int intValue7 = -intValue5; intValue7 <= intValue5; intValue7++) {
            for (int intValue8 = -intValue5; intValue8 <= intValue5; intValue8++) {
               ChunkPos chunkPos9 = new ChunkPos(intValue3 + intValue7, intValue4 + intValue8);
               if (!this.n.contains(chunkPos9)) {
                  arrayList6.add(chunkPos9);
               }
            }
         }

         arrayList6.sort((chunkPos3x, chunkPos4x) -> {
            long longValue5x = this.a(chunkPos3x, intValue3, intValue4);
            long longValue7x = this.a(chunkPos4x, intValue3, intValue4);
            return Long.compare(longValue5x, longValue7x);
         });
         int intValue11 = 0;

         for (ChunkPos chunkPos13 : arrayList6) {
            if (intValue11 >= 2) {
               break;
            }

            WorldChunk worldChunk10 = minecraftClient.world.getChunk(chunkPos13.x, chunkPos13.z);
            if (worldChunk10 != null) {
               this.a(worldChunk10, blockPos2, intValue1);
               this.n.add(chunkPos13);
               intValue11++;
            }
         }
      }
   }

   private void a(WorldChunk worldChunk1, BlockPos blockPos2, int intValue3) {
      int intValue4 = worldChunk1.getPos().getStartX();
      int intValue5 = worldChunk1.getPos().getStartZ();
      int intValue6 = intValue4 + 15;
      int intValue7 = intValue5 + 15;
      int intValue8 = Math.max(minecraftClient.world.getBottomY(), blockPos2.getY() - intValue3);
      int intValue9 = Math.min(minecraftClient.world.getTopYInclusive(), blockPos2.getY() + intValue3);
      int intValue10 = intValue3 * intValue3;
      Mutable mutable11 = new Mutable();

      for (int intValue12 = intValue4; intValue12 <= intValue6; intValue12++) {
         for (int intValue13 = intValue5; intValue13 <= intValue7; intValue13++) {
            for (int intValue14 = intValue8; intValue14 <= intValue9; intValue14++) {
               mutable11.set(intValue12, intValue14, intValue13);
               if (!(mutable11.getSquaredDistance(blockPos2) > (double)intValue10)) {
                  BlockState blockState15 = worldChunk1.getBlockState(mutable11);
                  if (!blockState15.isAir()) {
                     String string16 = Registries.BLOCK.getId(blockState15.getBlock()).getPath().toLowerCase();
                     if (this.l.contains(string16)) {
                        this.m.put(mutable11.toImmutable(), string16);
                     }
                  }
               }
            }
         }
      }
   }

   private void a(Vec3d vec3d1, int intValue2) {
      if (minecraftClient.world == null) {
         this.m.clear();
      } else {
         int intValue3 = intValue2 * intValue2;
         this.m.entrySet().removeIf(entry3x -> {
            BlockPos blockPos4 = (BlockPos)entry3x.getKey();
            BlockState blockState5 = minecraftClient.world.getBlockState(blockPos4);
            if (blockState5.isAir()) {
               return true;
            } else {
               String string6 = Registries.BLOCK.getId(blockState5.getBlock()).getPath().toLowerCase();
               return !this.l.contains(string6) ? true : blockPos4.getSquaredDistance(vec3d1) > (double)intValue3;
            }
         });
      }
   }

   private void a(MatrixStack matrixStack1) {
      if (!this.m.isEmpty()) {
         Vec3d vec3d2 = minecraftClient.gameRenderer.getCamera().getPos();
         matrixStack1.push();
         matrixStack1.translate(-vec3d2.x, -vec3d2.y, -vec3d2.z);
         Matrix4f matrix4f3 = matrixStack1.peek().getPositionMatrix();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableCull();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
         Tessellator tessellator4 = Tessellator.getInstance();
         BufferBuilder bufferBuilder5 = tessellator4.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);

         for (BlockPos blockPos7 : this.m.keySet()) {
            this.a(bufferBuilder5, matrix4f3, blockPos7, 0.1F, 1.0F, 0.15F, 0.18F);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder5.end());
         RenderSystem.lineWidth(2.0F);
         BufferBuilder bufferBuilder9 = tessellator4.begin(DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);

         for (BlockPos blockPos8 : this.m.keySet()) {
            this.b(bufferBuilder9, matrix4f3, blockPos8, 0.1F, 1.0F, 0.15F, 1.0F);
         }

         BufferRenderer.drawWithGlobalProgram(bufferBuilder9.end());
         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
         RenderSystem.depthMask(true);
         RenderSystem.disableBlend();
         matrixStack1.pop();
      }
   }

   private void a(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, BlockPos blockPos3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = (float)blockPos3.getX();
      float floatValue9 = (float)blockPos3.getY();
      float floatValue10 = (float)blockPos3.getZ();
      dev.leg1tka.render.RenderPrimitives.drawFilledBox(bufferBuilder1, matrix4f2, floatValue8, floatValue9, floatValue10, floatValue8 + 1.0F, floatValue9 + 1.0F, floatValue10 + 1.0F, floatValue4, floatValue5, floatValue6, floatValue7);
   }

   private void b(BufferBuilder bufferBuilder1, Matrix4f matrix4f2, BlockPos blockPos3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = (float)blockPos3.getX();
      float floatValue9 = (float)blockPos3.getY();
      float floatValue10 = (float)blockPos3.getZ();
      dev.leg1tka.render.RenderPrimitives.drawBoxLines(bufferBuilder1, matrix4f2, floatValue8, floatValue9, floatValue10, floatValue8 + 1.0F, floatValue9 + 1.0F, floatValue10 + 1.0F, floatValue4, floatValue5, floatValue6, floatValue7);
   }

   public void c(String string1) {
      this.l.add(string1.toLowerCase());
      this.n.clear();
      this.m.clear();
   }

   public void d(String string1) {
      this.l.remove(string1.toLowerCase());
      this.m.entrySet().removeIf(entry1x -> ((String)entry1x.getValue()).equalsIgnoreCase(string1));
   }

   public void p() {
      this.l.clear();
      this.s();
   }

   public Set<String> q() {
      return new HashSet<>(this.l);
   }

   public boolean e(String string1) {
      return this.l.contains(string1.toLowerCase());
   }

   private int r() {
      return Math.round(this.k.f());
   }

   private long a(ChunkPos chunkPos1, int intValue2, int intValue3) {
      long longValue4 = (long)(chunkPos1.x - intValue2);
      long longValue6 = (long)(chunkPos1.z - intValue3);
      return longValue4 * longValue4 + longValue6 * longValue6;
   }

   private void s() {
      this.m.clear();
      this.n.clear();
      this.o = null;
      this.q = 0L;
      this.p = -1;
   }
}
