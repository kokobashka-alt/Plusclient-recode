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

import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventPacket;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
public class LayerCooldown extends Module {
   public static LayerCooldown c;
   private static long d;
   private static int e;
   private static int f;
   private static int g;
   private static float h;
   private static float i;
   private static double j;
   private static ItemStack k = ItemStack.EMPTY;
   private final Matrix4f l = new Matrix4f();
   private final Quaternionf m = new Quaternionf();
   private Vec3d n = Vec3d.ZERO;
   private boolean o;
   private final List<LayerCooldown.a> p = new ArrayList<>();
   private final List<LayerCooldown.b> q = new ArrayList<>();

   public LayerCooldown() {
      super(
         "LayerCooldown",
         "Показывает таймер возле поставленного пласта",
         Category.MISC
      );
   }

   @Override
   public void onDisable() {
      this.p.clear();
      this.q.clear();
      this.o = false;
      super.onDisable();
   }

   @EventLink
   public void onPacket(EventPacket eventPacket1) {
      if (eventPacket1.getType() == EventPacket.Type.RECEIVE && eventPacket1.getPacket() instanceof PlaySoundS2CPacket packet) {
         String soundName = this.a(packet);
         if (soundName != null && this.c(soundName)) {
            BlockPos pos = BlockPos.ofFloored(packet.getX(), packet.getY(), packet.getZ());
            this.q.add(new LayerCooldown.b(pos, System.currentTimeMillis() + 150L));
         }
      }
   }

   @EventLink(
      priority = 100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      this.l.set(event3DRender1.getMatrices().peek().getPositionMatrix());
      this.m.set(event3DRender1.getCamera().getRotation());
      this.n = event3DRender1.getCamera().getPos();
      this.p();
   }

   @EventLink(
      priority = 100
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.p.isEmpty() || minecraftClient.world == null) return;
      long now = System.currentTimeMillis();
      this.p.removeIf(createPredicate169(now));
      dev.leg1tka.render.font.MsdfTextRenderer font = dev.leg1tka.render.font.FontCache.a("sf_regular", 12);
      if (font == null) return;
      if (k.isEmpty()) {
         k = new ItemStack(Blocks.ANVIL);
      }
      DrawContext drawContext = default1.getContext();
      for (LayerCooldown.a timer : this.p) {
         Vec3d screenPos = this.a(timer.pos);
         if (screenPos != null) {
            float remaining = (float)(timer.endTime - now) / 1000.0F;
            this.a(drawContext, drawContext.getMatrices(), font, (float)screenPos.x, (float)screenPos.y, remaining);
         }
      }
   }

   private void p() {
      if (!this.q.isEmpty() && minecraftClient.world != null) {
         long longValue1 = System.currentTimeMillis();
         Iterator iterator3 = this.q.iterator();

         while (iterator3.hasNext()) {
            LayerCooldown.b value4 = (LayerCooldown.b)iterator3.next();
            if (value4.runAt <= longValue1) {
               BlockPos blockPos5 = this.a(value4.center);
               Vec3d vec3d6 = blockPos5 == null ? Vec3d.ofCenter(value4.center) : new Vec3d((double)blockPos5.getX() + 0.5, (double)blockPos5.getY() + 0.65, (double)blockPos5.getZ() + 0.5);
               this.a(blockPos5 == null ? value4.center : blockPos5, vec3d6);
               iterator3.remove();
            }
         }
      }
   }

   private BlockPos a(BlockPos blockPos1) {
      BlockPos blockPos2 = null;
      double doubleValue3 = Double.MAX_VALUE;

      for (int intValue5 = -4; intValue5 <= 4; intValue5++) {
         for (int intValue6 = -4; intValue6 <= 4; intValue6++) {
            for (int intValue7 = -4; intValue7 <= 4; intValue7++) {
               BlockPos blockPos8 = blockPos1.add(intValue5, intValue6, intValue7);
               BlockState blockState9 = minecraftClient.world.getBlockState(blockPos8);
               if (this.a(blockState9)) {
                  double doubleValue10 = blockPos8.getSquaredDistance(blockPos1);
                  if (doubleValue10 < doubleValue3) {
                     doubleValue3 = doubleValue10;
                     blockPos2 = blockPos8;
                  }
               }
            }
         }
      }

      return blockPos2;
   }

   private boolean a(BlockState blockState1) {
      if (blockState1 != null && !blockState1.isAir()) {
         Block block2 = blockState1.getBlock();
         return block2 == Blocks.PISTON
            || block2 == Blocks.STICKY_PISTON
            || block2 == Blocks.MOVING_PISTON
            || block2 == Blocks.DRIED_KELP_BLOCK
            || block2 == Blocks.ANVIL
            || block2 == Blocks.CHIPPED_ANVIL
            || block2 == Blocks.DAMAGED_ANVIL;
      } else {
         return false;
      }
   }

   private void a(BlockPos blockPos1, Vec3d vec3d2) {
      long longValue3 = System.currentTimeMillis() + 19500L;

      for (int intValue5 = 0; intValue5 < this.p.size(); intValue5++) {
         LayerCooldown.a value6 = (LayerCooldown.a)this.p.get(intValue5);
         if (value6.blockPos.getSquaredDistance(blockPos1) <= 2.25) {
            this.p.set(intValue5, new LayerCooldown.a(blockPos1, vec3d2.add(0.0, 0.6, 0.0), longValue3));
            return;
         }
      }

      this.p.add(new LayerCooldown.a(blockPos1, vec3d2.add(0.0, 0.6, 0.0), longValue3));
   }

   private boolean c(String string1) {
      return "block.anvil.place".equals(string1) || "entity.zombie_horse.death".equals(string1) || "entity.ender_dragon.growl".equals(string1);
   }

   private String a(PlaySoundS2CPacket playSoundS2CPacket1) {
      try {
         return Registries.SOUND_EVENT.getId((SoundEvent)playSoundS2CPacket1.getSound().value()).getPath();
      } catch (Exception exception3) {
         return null;
      }
   }

   private void a(DrawContext drawContext1, MatrixStack matrixStack2, dev.leg1tka.render.font.MsdfTextRenderer value3, float floatValue4, float floatValue5, float floatValue6) {
      String string7 = createString170(this.a(floatValue6));
      float floatValue8 = value3.a(string7);
      float floatValue9 = 10.0F;
      float floatValue10 = 0.62F;
      float floatValue11 = 3.0F;
      float floatValue12 = floatValue9 + floatValue11 + floatValue8 + 8.0F;
      float floatValue13 = 12.5F;
      float floatValue14 = floatValue4 - floatValue12 * 0.5F;
      float floatValue15 = floatValue5 - floatValue13 * 0.5F;
      int intValue16 = dev.leg1tka.api.utils.text.ColorUtils.a();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      dev.leg1tka.render.RenderPrimitives.c(matrixStack2, floatValue14, floatValue15, floatValue12, floatValue13, 2.0F, 3.0F, intValue16);
      this.a(drawContext1, matrixStack2, floatValue14 + 4.0F, floatValue15 + 1.25F, floatValue10);
      value3.a(matrixStack2, string7, floatValue14 + 4.0F + floatValue9 + floatValue11, floatValue15 + 4.55F, -1);
      RenderSystem.disableBlend();
   }

   private String a(float floatValue1) {
      int intValue2 = Math.round(floatValue1 * 10.0F);
      return createString171(intValue2 / 10, Math.abs(intValue2 % 10));
   }

   private void a(DrawContext drawContext1, MatrixStack matrixStack2, float floatValue3, float floatValue4, float floatValue5) {
      if (drawContext1 != null) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         matrixStack2.push();
         matrixStack2.translate(floatValue3, floatValue4, 0.0F);
         matrixStack2.scale(floatValue5, floatValue5, 1.0F);
         drawContext1.drawItem(k, 0, 0);
         matrixStack2.pop();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }

   private Vec3d a(Vec3d vec3d1) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Vector3f vector3f2 = new Vector3f((float)(vec3d1.x - this.n.x), (float)(vec3d1.y - this.n.y), (float)(vec3d1.z - this.n.z));
         Quaternionf quaternionf3 = new Quaternionf(this.m).conjugate();
         vector3f2.rotate(quaternionf3);
         Vector4f vector4f4 = new Vector4f(vector3f2.x, vector3f2.y, vector3f2.z, 1.0F);
         this.l.transform(vector4f4);
         float floatValue5 = vector4f4.w;
         if (floatValue5 <= 1.0E-5F) {
            return null;
         } else {
            float floatValue6 = vector4f4.x / floatValue5;
            float floatValue7 = vector4f4.y / floatValue5;
            float floatValue8 = vector4f4.z / floatValue5;
            float floatValue9 = (floatValue6 * 0.5F + 0.5F) * (float)minecraftClient.getWindow().getScaledWidth();
            float floatValue10 = (1.0F - (floatValue7 * 0.5F + 0.5F)) * (float)minecraftClient.getWindow().getScaledHeight();
            if (!Float.isNaN(floatValue9) && !Float.isNaN(floatValue10) && !Float.isInfinite(floatValue9) && !Float.isInfinite(floatValue10)) {
               return !(floatValue9 < -400.0F)
                     && !(floatValue10 < -400.0F)
                     && !(floatValue9 > (float)(minecraftClient.getWindow().getScaledWidth() + 400))
                     && !(floatValue10 > (float)(minecraftClient.getWindow().getScaledHeight() + 400))
                  ? new Vec3d((double)floatValue9, (double)floatValue10, (double)floatValue8)
                  : null;
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }



   private static Predicate<LayerCooldown.a> createPredicate169(long longValue0) {
      return (LayerCooldown.a value2) -> value2.endTime <= longValue0;
   }

   private static String createString170(String string0) {
      return string0 + "с";
   }

   private static String createString171(int intValue0, int intValue1) {
      return intValue0 + "." + intValue1;
   }

   static record a(BlockPos blockPos, Vec3d pos, long endTime)  {


}

   static record b(BlockPos center, long runAt)  {

}
   public static void dev_leg1tka_o_il_initializer() {}
}
