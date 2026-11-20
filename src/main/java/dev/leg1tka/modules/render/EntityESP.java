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

import com.mojang.blaze3d.systems.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.Event3DRender;
import dev.leg1tka.api.events.implement.EventRender;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntArrays;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.font.TextRenderer.TextLayerType;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.Fog;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WaterAnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class EntityESP extends Module {
   public static EntityESP c = new EntityESP();
   private static int d;
   private static int e;
   private static int f;
   private static float g;
   private static float h;
   private static float i;
   private static float j;
   private static float k;
   private static float l;
   private static float m;
   private static float n;
   private static float o;
   private static float p;
   private static float q;
   private static float r;
   private static float s;
   private static String t;
   private static String u;
   private static String v;
   private static float w;
   private static float x;
   private static float y;
   private static float z;
   private static float A;
   private static float B;
   private static float C;
   private static float D;
   private static float E;
   private static float F;
   private static float G;
   private static float H;
   private static float I;
   private static int J;
   private static int K;
   private static int L;
   private static int M;
   private static int N;
   private static int O;
   private static int P;
   private static int Q;
   private static int R;
   private static int S;
   private static int T;
   private static int U;
   private static int V;
   private static int W;
   private static int X;
   private static int Y;
   private static String Z;
   private static int ModuleStorage;
   private static int ab;
   private static float ac;
   private static final String[] ad = new String[46];
   private dev.leg1tka.render.font.MsdfTextRenderer ae;
   private float af;
   private float ag;
   private static int ah;
   private static long Storage;
   private static long ModuleManager;
   private static long ModuleRegistry;
   private static long al;
   private static int am;
   private static float an;
   private static float ao;
   private static int ap;
   private static String[][] aq;
   private final MultiSetting ar = new MultiSetting(
      "Элементы",
      new BooleanSetting("Теги", true),
      new BooleanSetting("Броня", true),
      new BooleanSetting("Зачары", false),
      new BooleanSetting("Вторая рука", false)
   );
   private final BooleanSetting as = new BooleanSetting("Игроки", true);
   private final BooleanSetting at = new BooleanSetting("Мобы", true);
   private final BooleanSetting au = new BooleanSetting("Животные", true);
   private final BooleanSetting av = new BooleanSetting("Предметы", true);
   private final MultiSetting aw = new MultiSetting("Отображать", this.as, this.at, this.au, this.av);
   private final ModeSetting ax = new ModeSetting("Стиль", "Aspect", "Aspect", "Minecraft");
   private final ModeSetting ay = new ModeSetting(
      "Бокс",
      "Нет",
      "Нет",
      "Уголки",
      "Рамка",
      "Призма",
      "Кольцо"
   );
   private final ModeSetting az = new ModeSetting(
         "Цвет бокса",
         "Тема",
         "Тема",
         "Здоровье",
         "Белый"
      )
      .a(() -> !this.ay.b("Нет"));
   private final Matrix4f aA = new Matrix4f();
   private final Quaternionf aB = new Quaternionf();
   private Vec3d aC = Vec3d.ZERO;
   private float aD;
   private int aE;
   private int aF;
   private boolean aG;
   private final Vector3f aH = new Vector3f();
   private final Vector4f aI = new Vector4f();
   private final EntityESP.h aJ = new EntityESP.h();
   private float aK;
   private float aL;
   private float aM;
   private float aN;
   private float aO = 1.0F;
   private final long aP = System.nanoTime();
   private final Map<UUID, EntityESP.a> aQ = new HashMap<>();
   private final Int2ObjectOpenHashMap<EntityESP.g> aR = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectOpenHashMap<EntityESP.f> aS = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectOpenHashMap<EntityESP.d> aT = new Int2ObjectOpenHashMap<>();
   private final String[] aU = new String[600];
   private final String[] aV = new String[600];
   private final Int2ObjectOpenHashMap<EntityESP.k> aW = new Int2ObjectOpenHashMap<>();
   private final Int2ObjectOpenHashMap<EntityESP.j> aX = new Int2ObjectOpenHashMap<>();
   private long aY;
   private long aZ;
   private int ba;
   private final float[] bb = new float[2];
   private final dev.leg1tka.render.AnimatedQuadShader bc = new dev.leg1tka.render.AnimatedQuadShader();
   private final dev.leg1tka.render.AnimatedQuadShader[] bd = new dev.leg1tka.render.AnimatedQuadShader[6];
   private final dev.leg1tka.render.AnimatedQuadShader[] be = new dev.leg1tka.render.AnimatedQuadShader[6];
   private final dev.leg1tka.render.RoundedTextureRenderer[] bf = new dev.leg1tka.render.RoundedTextureRenderer[6];
   private final EntityESP.c[] bg = new EntityESP.c[6];
   private final dev.leg1tka.render.font.GlyphBuffer[] bh = new dev.leg1tka.render.font.GlyphBuffer[6];
   private int bi;
   private final dev.leg1tka.render.OccludedOutlineShader bj = new dev.leg1tka.render.OccludedOutlineShader();
   private Entity[] bk = new Entity[64];
   private float[] bl = new float[256];
   private double[] bm = new double[64];
   private int[] bn = new int[64];
   private int bo;
   private final IntComparator bp = (intValue1x, intValue2) -> Double.compare(this.bm[intValue2], this.bm[intValue1x]);
   private float[] bq = new float[256];
   private int[] br = new int[64];
   private int bs;
   private float bt;
   private float bu;
   private float bv;
   private float bw;
   private final List bx = new ArrayList();
   private final Long2ObjectOpenHashMap by = new Long2ObjectOpenHashMap();
   private final List bz = new ArrayList();
   private int bA;
   private int[] bB = new int[64];
   private int[] bC = new int[64];
   private int[] bD = new int[64];
   private int[] bE = new int[64];
   private int[] bF = new int[64];
   private boolean[] bG = new boolean[64];
   private int[] bH = new int[64];
   private int[] bI = new int[64];
   private int[] bJ = new int[64];
   private double[] bK = new double[256];
   private int bL;
   private final IntArrayList bM = new IntArrayList();
   private final IntComparator bN = (intValue1x, intValue2) -> Integer.compare(this.bF[intValue1x], this.bF[intValue2]);
   private MatrixStack bO;
   private EntityESP.c bP;
   private final Consumer<VertexConsumerProvider> bQ = vertexConsumerProvider1x -> this.a(vertexConsumerProvider1x, false);
   private final Consumer<VertexConsumerProvider> bR = vertexConsumerProvider1x -> this.a(vertexConsumerProvider1x, true);
   private float bS;
   private float bT;
   private float bU;
   private float bV;
   private float bW;
   private float bX;
   private static float bY;
   private static float bZ;
   private static float ca;
   private static float cb;
   private static float cc;
   private static float cd;
   private static float ce;
   private static float cf;
   private static float cg;
   private static float ch;
   private static float ci;
   private static float cj;
   private static int ck;
   private static int cl;
   private static int cm;
   private static int cn;
   private static int co;
   private static int cp;
   private static int cq;
   private static int cr;
   private final Matrix4f cs = new Matrix4f();
   private final Quaternionf ct = new Quaternionf();
   private final Matrix4f cu = new Matrix4f();
   private final FrustumIntersection cv = new FrustumIntersection();
   private final MatrixStack cw = new MatrixStack();
   private final Matrix4f cx = new Matrix4f();
   private final dev.leg1tka.render.EntityIconAtlas cy = new dev.leg1tka.render.EntityIconAtlas();
   private EntityESP.e cz;
   private boolean cA;
   private static int cB;
   private static int cC;
   private static int cD;
   private static int cE;

   public EntityESP() {
      super(
         "EntityESP",
         "Показывает игроков через стену",
         Category.RENDER
      );

      for (int intValue1 = 0; intValue1 < 6; intValue1++) {
         this.bd[intValue1] = new dev.leg1tka.render.AnimatedQuadShader();
         this.be[intValue1] = new dev.leg1tka.render.AnimatedQuadShader();
         this.bf[intValue1] = new dev.leg1tka.render.RoundedTextureRenderer(8.0F, 2.5F);
         this.bg[intValue1] = new EntityESP.c();
         this.bh[intValue1] = new dev.leg1tka.render.font.GlyphBuffer();
      }

      this.addSettings(new Setting[]{this.ax, this.aw, this.ar, this.ay, this.az});
   }

   @Override
   public void onDisable() {
      this.aG = false;
      this.aQ.clear();
      this.aR.clear();
      this.aS.clear();
      this.aT.clear();
      this.aW.clear();
      this.aX.clear();
      this.bx.clear();
      this.by.clear();
      this.bc.b();

      for (int intValue1 = 0; intValue1 < 6; intValue1++) {
         this.bd[intValue1].b();
         this.be[intValue1].b();
         this.bf[intValue1].b();
         this.bg[intValue1].a();
         this.bh[intValue1].b();
      }

      Arrays.fill(this.bk, null);
      this.bo = 0;
      this.cA = false;
      this.cy.b(minecraftClient);
      if (this.cz != null) {
         this.cz.a();
         this.cz = null;
      }

      super.onDisable();
   }

   @EventLink(
      priority = 100
   )
   public void onRender3D(Event3DRender event3DRender1) {
      this.aG = true;
      this.aA.set(event3DRender1.getProjectionMatrix());
      this.cs.set(event3DRender1.getPositionMatrix());
      this.aC = event3DRender1.getCamera().getPos();
      this.ct.set(event3DRender1.getCamera().getRotation());
      this.aB.set(this.ct).conjugate();
      this.aD = event3DRender1.getTickDelta();
      this.aE = minecraftClient.getWindow().getScaledWidth();
      this.aF = minecraftClient.getWindow().getScaledHeight();
      boolean flag2 = this.ay.b("Призма");
      if ((flag2 || this.ay.b("Кольцо")) && minecraftClient.world != null && minecraftClient.player != null) {
         this.a(event3DRender1, flag2);
      }

      this.cA = this.ax.b("Minecraft");
   }

   @EventLink(
      priority = 100
   )
   public void onRender2D(EventRender.Default default1) {
      if (this.aG && minecraftClient.world != null && minecraftClient.player != null) {
         boolean flag2 = this.ar.a("Теги");
         boolean flag3 = this.ar.a("Броня");
         boolean flag4 = this.ar.a("Вторая рука");
         boolean flag5 = this.ay.b("Уголки") || this.ay.b("Рамка");
         if (this.ax.b("Minecraft")) {
            flag2 = false;
            flag3 = false;
            flag4 = false;
         }

         if (flag2 || flag3 || flag4 || flag5) {
            dev.leg1tka.render.font.MsdfTextRenderer value6 = dev.leg1tka.render.font.FontCache.a("sf_regular", 13);
            dev.leg1tka.render.font.MsdfTextRenderer value7 = flag3 && this.ar.a("Зачары") ? dev.leg1tka.render.font.FontCache.a("nunito", 10) : null;
            if (value6 == null) {
               flag2 = false;
               flag4 = false;
            }

            int intValue8 = (flag2 ? 1 : 0) | (flag3 ? 2 : 0) | (flag4 ? 4 : 0) | (value7 != null ? 16 : 0);
            long longValue9 = System.nanoTime();
            float floatValue11 = this.a(longValue9);
            this.aO = (float)minecraftClient.getWindow().getScaleFactor();
            dev.leg1tka.api.utils.player.EquipmentUtils.a();
            this.bi = 0;
            this.bs = 0;
            this.bo = 0;
            this.bx.clear();
            this.a(flag2, flag5);
            if (flag2 && this.av.getValue() && !this.bx.isEmpty()) {
               this.b(value6);
            }

            this.bx.clear();
            IntArrays.quickSort(this.bn, 0, this.bo, this.bp);

            for (int intValue12 = 0; intValue12 < this.bo; intValue12++) {
               int intValue13 = this.bn[intValue12];
               Entity entity14 = this.bk[intValue13];
               this.aK = this.bl[intValue13 * 4];
               this.aL = this.bl[intValue13 * 4 + 1];
               this.aM = this.bl[intValue13 * 4 + 2];
               this.aN = this.bl[intValue13 * 4 + 3];
               if (entity14 instanceof PlayerEntity) {
                  PlayerEntity playerEntity15 = (PlayerEntity)entity14;
                  EntityESP.g value17 = this.a(playerEntity15, intValue8, value6, value7, longValue9);
                  if (flag5) {
                     this.a(value17.i ? -8588646 : this.b(value17.s.i), value17.i ? -8588646 : this.c(value17.s.i));
                  }

                  if (intValue8 != 0) {
                     this.a(value17, intValue8);
                     this.a(value17, intValue8, this.q(), floatValue11, value6, value7);
                  }
               } else if (entity14 instanceof LivingEntity) {
                  LivingEntity livingEntity16 = (LivingEntity)entity14;
                  EntityESP.f value19 = flag2 ? this.a(livingEntity16, value6, longValue9) : null;
                  if (flag5) {
                     int intValue18 = value19 != null ? value19.g.i : a(a(livingEntity16));
                     this.a(this.b(intValue18), this.c(intValue18));
                  }

                  if (value19 != null) {
                     this.a(value19);
                     this.a(value19, this.q(), floatValue11, value6);
                  }
               }
            }

            Arrays.fill(this.bk, 0, this.bo, null);
            this.v();
            this.a(default1.getContext(), floatValue11);
         }
      }
   }

   private void a(boolean flag1, boolean flag2) {
      for (Entity entity4 : minecraftClient.world.getEntities()) {
         if (entity4 instanceof PlayerEntity playerEntity6) {
            if (!this.d(playerEntity6)) {
               continue;
            }
         } else {
            if (entity4 instanceof ItemEntity itemEntity7) {
               if (flag1 && this.av.getValue() && itemEntity7.isAlive()) {
                  this.bx.add(itemEntity7);
               }
               continue;
            }

            if (!(entity4 instanceof LivingEntity livingEntity5) || !flag1 && !flag2 || !this.b(livingEntity5)) {
               continue;
            }
         }

         if (this.d(entity4)) {
            this.b(entity4);
         }
      }
   }

   private void b(Entity entity1) {
      if (this.bo == this.bk.length) {
         int intValue2 = this.bo * 2;
         this.bk = Arrays.copyOf(this.bk, intValue2);
         this.bl = Arrays.copyOf(this.bl, intValue2 * 4);
         this.bm = Arrays.copyOf(this.bm, intValue2);
         this.bn = Arrays.copyOf(this.bn, intValue2);
      }

      int intValue3 = this.bo++;
      this.bk[intValue3] = entity1;
      this.bl[intValue3 * 4] = this.aK;
      this.bl[intValue3 * 4 + 1] = this.aL;
      this.bl[intValue3 * 4 + 2] = this.aM;
      this.bl[intValue3 * 4 + 3] = this.aN;
      this.bm[intValue3] = entity1.squaredDistanceTo(this.aC);
      this.bn[intValue3] = intValue3;
   }

   private int q() {
      int intValue1 = 0;

      for (int intValue2 = 0; intValue2 < this.bs; intValue2++) {
         int intValue3 = intValue2 * 4;
         if (this.bt < this.bq[intValue3 + 2] && this.bv > this.bq[intValue3] && this.bu < this.bq[intValue3 + 3] && this.bw > this.bq[intValue3 + 1]) {
            intValue1 = Math.max(intValue1, this.br[intValue2] + 1);
         }
      }

      intValue1 = Math.min(intValue1, 5);
      if (this.bs == this.br.length) {
         this.bq = Arrays.copyOf(this.bq, this.bq.length * 2);
         this.br = Arrays.copyOf(this.br, this.br.length * 2);
      }

      int intValue5 = this.bs * 4;
      this.bq[intValue5] = this.bt;
      this.bq[intValue5 + 1] = this.bu;
      this.bq[intValue5 + 2] = this.bv;
      this.bq[intValue5 + 3] = this.bw;
      this.br[this.bs++] = intValue1;
      this.bi = Math.max(this.bi, intValue1 + 1);
      return intValue1;
   }

   private void r() {
      this.bt = Float.POSITIVE_INFINITY;
      this.bu = Float.POSITIVE_INFINITY;
      this.bv = Float.NEGATIVE_INFINITY;
      this.bw = Float.NEGATIVE_INFINITY;
   }

   private void a(float floatValue1, float floatValue2, float floatValue3, float floatValue4) {
      this.bt = Math.min(this.bt, floatValue1);
      this.bu = Math.min(this.bu, floatValue2);
      this.bv = Math.max(this.bv, floatValue1 + floatValue3);
      this.bw = Math.max(this.bw, floatValue2 + floatValue4);
   }

   private void a(EntityESP.g value1, int intValue2) {
      this.r();
      float floatValue3 = (this.aK + this.aM) * 0.5F;
      this.bX = floatValue3;
      float floatValue4 = this.b(this.aL) - 2.0F;
      if ((intValue2 & 1) != 0) {
         this.bT = this.b(floatValue3 - value1.t * 0.5F);
         this.bS = floatValue4 - 12.0F;
         this.a(this.bT, this.bS, value1.t, 12.0F);
         floatValue4 = this.bS - 2.0F;
      }

      if ((intValue2 & 2) != 0 && value1.A > 0) {
         this.bU = this.b(floatValue3 - value1.B * 0.5F);
         this.bV = floatValue4 - 12.0F;
         float floatValue5 = this.bV;
         if (value1.G && (intValue2 & 16) != 0 && value1.h != null) {
            floatValue5 -= 1.5F + 0.7F * value1.h.d() * 0.5F;
         }

         this.a(this.bU, floatValue5, value1.B, this.bV + 12.0F - floatValue5);
      }

      if ((intValue2 & 4) != 0 && value1.L > 0) {
         this.bW = this.b(this.aN) + 2.0F;
         float floatValue7 = 0.0F;

         for (int intValue6 = 0; intValue6 < value1.L; intValue6++) {
            floatValue7 = Math.max(floatValue7, value1.I[intValue6] + 8.0F);
         }

         this.a(this.b(floatValue3 - floatValue7 * 0.5F), this.bW, floatValue7, (float)value1.L * 13.5F - 1.5F);
      }

      if (this.bt > this.bv) {
         this.a(this.aK, this.aL, 0.0F, 0.0F);
      }
   }

   private void a(EntityESP.g value1, int intValue2, int intValue3, float floatValue4, dev.leg1tka.render.font.MsdfTextRenderer value5, dev.leg1tka.render.font.MsdfTextRenderer value6) {
      dev.leg1tka.render.AnimatedQuadShader value7 = this.bd[intValue3];
      dev.leg1tka.render.font.GlyphBuffer value8 = this.bh[intValue3];
      int intValue9 = value1.i ? 1796088855 : 1795952148;
      float floatValue10 = value1.d < 0.0F ? 0.0F : MathHelper.clamp(floatValue4 - value1.d, 0.0F, 0.1F);
      value1.d = floatValue4;
      value1.c = value1.c + ((value1.b ? 1.0F : 0.0F) - value1.c) * (1.0F - (float)Math.exp((double)(-12.0F * floatValue10)));
      int intValue11 = intValue9;
      if (value1.c > 0.004F) {
         float floatValue12 = value1.b ? 0.82F + 0.18F * MathHelper.sin(floatValue4 * 5.0F) : 1.0F;
         intValue11 = a(intValue9, -1634986980, value1.c * floatValue12);
      }

      if ((intValue2 & 1) != 0) {
         float floatValue18 = this.bT;
         float floatValue13 = this.bS;
         float floatValue14 = floatValue13 + 6.0F;
         float floatValue15 = this.a(value5, floatValue14);
         value7.a(floatValue18, floatValue13, value1.t, 12.0F, 5.0F, intValue11, 1.0F);
         this.bf[intValue3].a(value1.j, floatValue18 + 2.0F, floatValue13 + 2.0F, c(value1.a));
         float floatValue16 = floatValue18 + 2.0F + 8.0F + 3.5F;
         value7.a(floatValue16, floatValue14 - 3.0F, 0.6F, 6.0F, 1560281087);
         floatValue16 += 4.1F;
         float floatValue17 = floatValue16 - value1.q;
         floatValue17 = this.a(value8, value5, value1.k, value1.m, floatValue17, floatValue15);
         value8.a(value5, value1.o, floatValue17, floatValue15, -1, true);
         floatValue17 += value1.p;
         floatValue17 = this.a(value8, value5, value1.l, value1.n, floatValue17, floatValue15);
         if (value1.i) {
            value8.a(value5, " [F]", floatValue17, floatValue15, -8588646, true);
         }

         floatValue16 += value1.r - value1.q + 3.5F;
         value7.a(floatValue16, floatValue14 - 3.0F, 0.6F, 6.0F, 1560281087);
         floatValue16 += 4.1F;
         this.a(value7, value8, value5, value1.s, floatValue16, floatValue14, floatValue15);
         if (value1.b) {
            this.a(value8, value5, value1.a, this.b(floatValue16 + value1.s.h + 3.0F), floatValue15);
         }
      }

      if ((intValue2 & 2) != 0 && value1.A > 0) {
         this.a(value1, intValue3, value7, value8, value6, (intValue2 & 16) != 0);
      }

      if ((intValue2 & 4) != 0 && value1.L > 0) {
         float floatValue19 = this.bW;

         for (int intValue20 = 0; intValue20 < value1.L; intValue20++) {
            float floatValue21 = value1.I[intValue20] + 8.0F;
            float floatValue22 = this.b(this.bX - floatValue21 * 0.5F);
            value7.a(floatValue22, floatValue19, floatValue21, 12.0F, 5.0F, intValue9, 0.8F);
            value8.a(value5, value1.H[intValue20], floatValue22 + 4.0F - value1.J[intValue20], this.a(value5, floatValue19 + 6.0F), value1.K[intValue20], true);
            floatValue19 += 13.5F;
         }
      }
   }

   private void a(EntityESP.g value1, int intValue2, dev.leg1tka.render.AnimatedQuadShader value3, dev.leg1tka.render.font.GlyphBuffer value4, dev.leg1tka.render.font.MsdfTextRenderer value5, boolean flag6) {
      float floatValue7 = this.bU;
      float floatValue8 = this.bV;

      for (int intValue9 = 0; intValue9 < value1.A; intValue9++) {
         if (intValue9 == value1.z && intValue9 > 0) {
            floatValue7 += 4.0F;
         }

         value3.a(floatValue7, floatValue8, 12.0F, 12.0F, 4.0F, 1795952148, 0.6F);
         float floatValue10 = value1.x[intValue9];
         boolean flag11 = floatValue10 >= 0.0F;
         this.bg[intValue2].a(value1.w[intValue9], floatValue7 + 1.0F, floatValue8 + 1.0F);
         if (flag11) {
            float floatValue12 = 7.0F;
            float floatValue13 = floatValue8 + 12.0F - 1.0F - 1.0F;
            this.be[intValue2].a(floatValue7 + 2.5F, floatValue13, floatValue12, 1.0F, 0.5F, -1509949440);
            this.be[intValue2].a(floatValue7 + 2.5F, floatValue13, Math.max(1.0F, floatValue12 * floatValue10), 1.0F, 0.5F, value1.y[intValue9]);
         }

         if (flag6 && value5 != null && intValue9 < value1.z && value1.E[intValue9] != null) {
            float floatValue14 = this.b(floatValue8 - 1.5F);
            float floatValue15 = floatValue14 - value5.c().g() * value5.d() * 0.5F + 2.5F;
            value4.a(value5, value1.E[intValue9], floatValue7 + 6.0F - value1.F[intValue9], floatValue15, -3618561, true);
         }

         floatValue7 += 13.0F;
      }
   }

   private void a(dev.leg1tka.render.AnimatedQuadShader value1, dev.leg1tka.render.font.GlyphBuffer value2, dev.leg1tka.render.font.MsdfTextRenderer value3, EntityESP.b value4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = this.b(floatValue5) - value4.c;
      value2.a(value3, "[", floatValue8, floatValue7, -1929379841, true);
      value2.a(value3, value4.a, floatValue8 + value4.d, floatValue7, value4.i, true);
      if (value4.b != null) {
         value2.a(value3, "+", floatValue8 + value4.e, floatValue7, -1929379841, true);
         value2.a(value3, value4.b, floatValue8 + value4.f, floatValue7, -20946, true);
      }

      value2.a(value3, "]", floatValue8 + value4.g, floatValue7, -1929379841, true);
   }

   private void a(dev.leg1tka.render.font.GlyphBuffer value1, dev.leg1tka.render.font.MsdfTextRenderer value2, PlayerEntity playerEntity3, float floatValue4, float floatValue5) {
      int intValue6 = (int)Math.min((long)(ad.length - 1), (dev.leg1tka.api.utils.player.EquipmentUtils.b(playerEntity3) + 99L) / 100L);
      value1.a(value2, "[", floatValue4, floatValue5, -1929379841, true);
      float floatValue7 = floatValue4 + this.ag;
      String string8 = ad[intValue6];
      value1.a(value2, string8, floatValue7, floatValue5, -46518, true);
      value1.a(value2, "]", floatValue7 + value2.a(string8), floatValue5, -1929379841, true);
   }

   private void a(dev.leg1tka.render.font.MsdfTextRenderer value1) {
      if (this.ae != value1) {
         this.ae = value1;
         this.ag = value1.a("[");
         float floatValue2 = 0.0F;

         for (String string6 : ad) {
            floatValue2 = Math.max(floatValue2, value1.a(string6));
         }

         this.af = this.ag + floatValue2 + value1.a("]");
      }
   }

   private static int a(int intValue0, int intValue1, float floatValue2) {
      floatValue2 = MathHelper.clamp(floatValue2, 0.0F, 1.0F);
      int intValue3 = intValue0 >>> 24;
      int intValue4 = intValue0 >> 16 & 0xFF;
      int intValue5 = intValue0 >> 8 & 0xFF;
      int intValue6 = intValue0 & 0xFF;
      int intValue7 = intValue1 >>> 24;
      int intValue8 = intValue1 >> 16 & 0xFF;
      int intValue9 = intValue1 >> 8 & 0xFF;
      int intValue10 = intValue1 & 0xFF;
      return Math.round((float)intValue3 + (float)(intValue7 - intValue3) * floatValue2) << 24
         | Math.round((float)intValue4 + (float)(intValue8 - intValue4) * floatValue2) << 16
         | Math.round((float)intValue5 + (float)(intValue9 - intValue5) * floatValue2) << 8
         | Math.round((float)intValue6 + (float)(intValue10 - intValue6) * floatValue2);
   }

   private float a(dev.leg1tka.render.font.GlyphBuffer value1, dev.leg1tka.render.font.MsdfTextRenderer value2, List<EntityESP.i> list3, float[] floatValueArray4, float floatValue5, float floatValue6) {
      int intValue7 = 0;

      for (int intValue8 = list3.size(); intValue7 < intValue8; intValue7++) {
         EntityESP.i value9 = (EntityESP.i)list3.get(intValue7);
         value1.a(value2, value9.text(), floatValue5, floatValue6, value9.color(), true);
         floatValue5 += floatValueArray4[intValue7];
      }

      return floatValue5;
   }

   private void a(EntityESP.f value1) {
      this.r();
      this.bX = (this.aK + this.aM) * 0.5F;
      this.bT = this.b(this.bX - value1.h * 0.5F);
      this.bS = this.b(this.aL) - 2.0F - 12.0F;
      this.a(this.bT, this.bS, value1.h, 12.0F);
   }

   private void a(EntityESP.f value1, int intValue2, float floatValue3, dev.leg1tka.render.font.MsdfTextRenderer value4) {
      dev.leg1tka.render.AnimatedQuadShader value5 = this.bd[intValue2];
      dev.leg1tka.render.font.GlyphBuffer value6 = this.bh[intValue2];
      float floatValue7 = this.bS + 6.0F;
      float floatValue8 = this.a(value4, floatValue7);
      value5.a(this.bT, this.bS, value1.h, 12.0F, 5.0F, 1795952148, 1.0F);
      float floatValue9 = this.bT + 4.0F;
      value6.a(value4, value1.d, floatValue9 - value1.f, floatValue8, -1, true);
      floatValue9 += value1.e + 3.5F;
      value5.a(floatValue9, floatValue7 - 3.0F, 0.6F, 6.0F, 1560281087);
      floatValue9 += 4.1F;
      this.a(value5, value6, value4, value1.g, floatValue9, floatValue7, floatValue8);
   }

   private void a(int intValue1, int intValue2) {
      float floatValue3 = this.b(this.aK);
      float floatValue4 = this.b(this.aL);
      float floatValue5 = this.b(this.aM) - floatValue3;
      float floatValue6 = this.b(this.aN) - floatValue4;
      if (this.ay.b("Уголки")) {
         this.bc.b(floatValue3, floatValue4, floatValue5, floatValue6, intValue1);
      } else {
         this.bc.a(floatValue3, floatValue4, floatValue5, floatValue6, Math.min(6.0F, Math.min(floatValue5, floatValue6) * 0.18F), intValue1, intValue2);
      }
   }

   private int b(int intValue1) {
      if (this.az.b("Здоровье")) {
         return intValue1;
      } else {
         return this.az.b("Белый") ? -1051913 : 0xFF000000 | dev.leg1tka.api.utils.text.ColorUtils.k(0);
      }
   }

   private int c(int intValue1) {
      if (this.az.b("Здоровье")) {
         return intValue1;
      } else {
         return this.az.b("Белый") ? -1051913 : 0xFF000000 | dev.leg1tka.api.utils.text.ColorUtils.k(90);
      }
   }

   private void a(DrawContext drawContext1, float floatValue2) {
      Matrix4f matrix4f3 = drawContext1.getMatrices().peek().getPositionMatrix();
      boolean flag4 = !this.bc.a() || this.bi > 0;
      if (flag4) {
         this.bc.a(matrix4f3, floatValue2);

         for (int intValue5 = 0; intValue5 < this.bi; intValue5++) {
            this.bd[intValue5].a(matrix4f3, floatValue2);
            this.bf[intValue5].a(matrix4f3);
            this.a(drawContext1, this.bg[intValue5]);
            this.be[intValue5].a(matrix4f3, floatValue2);
            RenderSystem.disableDepthTest();
            this.bh[intValue5].a(matrix4f3);
         }

         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
         RenderSystem.disableBlend();
      }
   }

   private void a(DrawContext drawContext1, EntityESP.c value2) {
      if (value2.d != 0) {
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         RenderSystem.disableDepthTest();
         RenderSystem.depthMask(false);
         boolean flag3 = false;
         boolean flag4 = false;

         for (int intValue5 = 0; intValue5 < value2.d; intValue5++) {
            if (value2.a[intValue5].isSideLit()) {
               flag4 = true;
            } else {
               flag3 = true;
            }
         }

         this.bO = drawContext1.getMatrices();
         this.bP = value2;
         drawContext1.draw();
         if (flag3) {
            DiffuseLighting.disableGuiDepthLighting();
            drawContext1.draw(this.bQ);
            DiffuseLighting.enableGuiDepthLighting();
         }

         if (flag4) {
            drawContext1.draw(this.bR);
         }

         this.bO = null;
         this.bP = null;
         value2.a();
         RenderSystem.depthMask(true);
      }
   }

   private void a(VertexConsumerProvider vertexConsumerProvider1, boolean flag2) {
      MatrixStack matrixStack3 = this.bO;
      EntityESP.c value4 = this.bP;

      for (int intValue5 = 0; intValue5 < value4.d; intValue5++) {
         ItemRenderState itemRenderState6 = value4.a[intValue5];
         if (itemRenderState6.isSideLit() == flag2) {
            matrixStack3.push();
            matrixStack3.translate(value4.b[intValue5], value4.c[intValue5], 0.0F);
            matrixStack3.scale(0.625F, 0.625F, 1.0F);
            matrixStack3.translate(8.0F, 8.0F, 150.0F);
            matrixStack3.scale(16.0F, -16.0F, 16.0F);
            itemRenderState6.render(matrixStack3, vertexConsumerProvider1, 15728880, OverlayTexture.DEFAULT_UV);
            matrixStack3.pop();
         }
      }
   }

   private void a(Event3DRender event3DRender1, boolean flag2) {
      this.bj.a(event3DRender1.getProjectionMatrix(), minecraftClient.getWindow().getFramebufferHeight());
      double doubleValue3 = this.aC.x;
      double doubleValue5 = this.aC.y;
      double doubleValue7 = this.aC.z;
      float floatValue9 = this.aD;

      for (Entity entity11 : minecraftClient.world.getEntities()) {
         int intValue12;
         int intValue13;
         if (entity11 instanceof PlayerEntity playerEntity15) {
            if (!this.d(playerEntity15)) {
               continue;
            }

            EntityESP.g value16 = (EntityESP.g)this.aR.get(playerEntity15.getId());
            boolean flag17 = value16 != null && value16.a == playerEntity15 && value16.i;
            int intValue18 = value16 != null && value16.a == playerEntity15 ? value16.s.i : a(a((LivingEntity)playerEntity15));
            intValue12 = flag17 ? -8588646 : this.b(intValue18);
            intValue13 = flag17 ? -8588646 : this.c(intValue18);
         } else {
            if (!(entity11 instanceof LivingEntity livingEntity14) || !this.b(livingEntity14)) {
               continue;
            }

            int intValue30 = a(a(livingEntity14));
            intValue12 = this.b(intValue30);
            intValue13 = this.c(intValue30);
         }

         double doubleValue29 = MathHelper.lerp((double)floatValue9, entity11.lastRenderX, entity11.getX()) - entity11.getX();
         double doubleValue31 = MathHelper.lerp((double)floatValue9, entity11.lastRenderY, entity11.getY()) - entity11.getY();
         double doubleValue19 = MathHelper.lerp((double)floatValue9, entity11.lastRenderZ, entity11.getZ()) - entity11.getZ();
         Box box21 = entity11.getBoundingBox();
         float floatValue22 = (float)(box21.minX + doubleValue29 - doubleValue3);
         float floatValue23 = (float)(box21.minY + doubleValue31 - doubleValue5);
         float floatValue24 = (float)(box21.minZ + doubleValue19 - doubleValue7);
         float floatValue25 = (float)(box21.maxX + doubleValue29 - doubleValue3);
         float floatValue26 = (float)(box21.maxY + doubleValue31 - doubleValue5);
         float floatValue27 = (float)(box21.maxZ + doubleValue19 - doubleValue7);
         if (flag2) {
            this.bj.a(floatValue22 - 0.02F, floatValue23, floatValue24 - 0.02F, floatValue25 + 0.02F, floatValue26 + 0.02F, floatValue27 + 0.02F, intValue12, intValue13);
         } else {
            float floatValue28 = Math.max(0.3F, (floatValue25 - floatValue22) * 0.5F) * 1.25F;
            this.bj.a((floatValue22 + floatValue25) * 0.5F, floatValue23 + 0.02F, (floatValue24 + floatValue27) * 0.5F, floatValue28, (floatValue26 - floatValue23) * 1.15F, intValue12);
         }
      }

      this.bj.a(event3DRender1.getMatrices().peek().getPositionMatrix(), this.a(System.nanoTime()));
   }

   public void p() {
      if (this.cA) {
         this.cA = false;
         if (this.isEnabled() && this.ax.b("Minecraft") && minecraftClient.world != null && minecraftClient.player != null) {
            boolean flag1 = this.ar.a("Теги");
            boolean flag2 = this.ar.a("Броня");
            boolean flag3 = flag2 && this.ar.a("Зачары");
            boolean flag4 = this.ar.a("Вторая рука");
            if (flag1 || flag2 || flag4) {
               boolean flag5 = flag1 && this.av.getValue();
               long longValue6 = System.nanoTime();
               this.cv.set(this.cu.set(this.aA).mul(this.cs), true);
               this.bo = 0;
               this.bx.clear();

               for (Entity entity9 : minecraftClient.world.getEntities()) {
                  if (entity9 instanceof PlayerEntity playerEntity11) {
                     if (!this.d(playerEntity11)) {
                        continue;
                     }
                  } else {
                     if (entity9 instanceof ItemEntity itemEntity12) {
                        if (flag5 && itemEntity12.isAlive()) {
                           this.bx.add(itemEntity12);
                        }
                        continue;
                     }

                     if (!flag1 || !(entity9 instanceof LivingEntity livingEntity10) || !this.b(livingEntity10)) {
                        continue;
                     }
                  }

                  if (this.c(entity9)) {
                     this.b(entity9);
                  }
               }

               if (this.bo == 0 && this.bx.isEmpty()) {
                  this.v();
               } else {
                  this.cy.a();

                  for (int intValue15 = 0; intValue15 < this.bo; intValue15++) {
                     EntityESP.k value17 = this.a(this.bk[intValue15], longValue6, flag2, flag3, flag4);
                     if (flag2) {
                        this.a(value17);
                     }
                  }

                  this.cy.a(minecraftClient);
                  EntityESP.e value16 = this.s();
                  RenderSystem.backupProjectionMatrix();
                  RenderSystem.setProjectionMatrix(this.aA, ProjectionType.PERSPECTIVE);
                  Matrix4fStack matrix4fStack18 = RenderSystem.getModelViewStack();
                  matrix4fStack18.pushMatrix();
                  matrix4fStack18.identity();
                  Fog fog19 = RenderSystem.getShaderFog();
                  RenderSystem.setShaderFog(Fog.DUMMY);
                  if (Atmosphere.u()) {
                     RenderSystem.depthMask(true);
                     RenderSystem.clear(256);
                  }

                  this.cw.loadIdentity();
                  this.cw.multiplyPositionMatrix(this.cs);
                  int intValue20 = Math.round(minecraftClient.options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;
                  if (!this.bx.isEmpty()) {
                     this.a(value16, intValue20);
                  }

                  this.bx.clear();
                  IntArrays.quickSort(this.bn, 0, this.bo, this.bp);

                  for (int intValue21 = 0; intValue21 < this.bo; intValue21++) {
                     Entity entity13 = this.bk[this.bn[intValue21]];
                     EntityESP.k value14 = (EntityESP.k)this.aW.get(entity13.getId());
                     if (value14 != null && value14.a == entity13) {
                        this.a(value16, value14, entity13, flag1, flag2, flag3, flag4, intValue20);
                     }
                  }

                  value16.draw();
                  RenderSystem.setShaderFog(fog19);
                  matrix4fStack18.popMatrix();
                  RenderSystem.restoreProjectionMatrix();
                  Arrays.fill(this.bk, 0, this.bo, null);
                  this.bo = 0;
                  this.v();
               }
            }
         }
      }
   }

   public boolean a(Entity entity1) {
      if (!this.isEnabled() || !this.ar.a("Теги")) {
         return false;
      } else if (entity1 instanceof PlayerEntity playerEntity3) {
         return this.d(playerEntity3);
      } else if (entity1 instanceof ItemEntity) {
         return this.av.getValue();
      } else {
         if (entity1 instanceof LivingEntity livingEntity2 && this.b(livingEntity2)) {
            return true;
         }

         return false;
      }
   }

   private EntityESP.e s() {
      if (this.cz == null) {
         this.cz = new EntityESP.e();
         this.cz.a(RenderLayer.getText(dev.leg1tka.render.EntityIconAtlas.a));
         this.cz.a(RenderLayer.getGlint());
         this.cz.a(RenderLayer.getTextBackground());
      }

      return this.cz;
   }

   private boolean c(Entity entity1) {
      float floatValue2 = entity1.getHeight();
      return this.cv
         .testSphere(
            (float)(entity1.getX() - this.aC.x), (float)(entity1.getY() + (double)floatValue2 * 0.5 - this.aC.y), (float)(entity1.getZ() - this.aC.z), floatValue2 * 0.5F + 2.5F
         );
   }

   private void a(EntityESP.e value1, EntityESP.k value2, Entity entity3, boolean flag4, boolean flag5, boolean flag6, boolean flag7, int intValue8) {
      this.a(entity3, (double)entity3.getHeight() + 0.5);
      Matrix4f matrix4f9 = this.cw.peek().getPositionMatrix();
      float floatValue10 = 9.0F;
      if (flag4) {
         this.a(value2.l, (float)(-value2.m) * 0.5F, 0.0F, -1, matrix4f9, value1, intValue8);
         this.a(value2.f, (float)(-value2.g) * 0.5F, -10.35F, -1, matrix4f9, value1, intValue8);
         floatValue10 = -11.35F;
      }

      if (flag5 && value2.F > 0) {
         this.a(value1, value2, floatValue10 - 2.0F - 16.0F, flag6);
      }

      this.cw.pop();
      if (flag7 && value2.L > 0) {
         this.a(entity3, 0.0);
         Matrix4f matrix4f11 = this.cw.peek().getPositionMatrix();
         float floatValue12 = 3.0F;

         for (int intValue13 = 0; intValue13 < value2.L; intValue13++) {
            this.a(value2.H[intValue13], (float)(-value2.I[intValue13]) * 0.5F, floatValue12, -1, matrix4f11, value1, intValue8);
            floatValue12 += 10.5F;
         }

         this.cw.pop();
      }
   }

   private void a(EntityESP.e value1, EntityESP.k value2, float floatValue3, boolean flag4) {
      Matrix4f matrix4f5 = this.cw.peek().getPositionMatrix();
      this.cx.set(matrix4f5).translate(0.0F, 0.0F, 6.0F);
      boolean flag6 = this.cy.b();
      float floatValue7 = -value2.G * 0.5F;

      for (int intValue8 = 0; intValue8 < value2.F; intValue8++) {
         if (intValue8 == value2.E && intValue8 > 0) {
            floatValue7 += 5.0F;
         }

         int intValue9 = value2.v[intValue8];
         if (flag6 && intValue9 != -1) {
            this.a(value1.getBuffer(RenderLayer.getText(dev.leg1tka.render.EntityIconAtlas.a)), matrix4f5, floatValue7, floatValue3, intValue9);
            if (value2.x[intValue8]) {
               a(value1.getBuffer(RenderLayer.getGlint()), matrix4f5, floatValue7, floatValue3);
            }
         }

         if (value2.y[intValue8] >= 0) {
            VertexConsumer vertexConsumer10 = value1.getBuffer(RenderLayer.getTextBackground());
            a(vertexConsumer10, matrix4f5, floatValue7 + 2.0F, floatValue3 + 13.0F, 13.0F, 2.0F, 2.0F, -16777216);
            if (value2.y[intValue8] > 0) {
               a(vertexConsumer10, matrix4f5, floatValue7 + 2.0F, floatValue3 + 13.0F, (float)value2.y[intValue8], 1.0F, 4.0F, value2.z[intValue8]);
            }
         }

         if (value2.A[intValue8] != null) {
            minecraftClient.textRenderer.draw(value2.A[intValue8], floatValue7 + 17.0F - (float)value2.B[intValue8], floatValue3 + 9.0F, -1, true, this.cx, value1, TextLayerType.NORMAL, 0, 15728880);
         }

         if (flag4 && intValue8 < value2.E && value2.C[intValue8] != null) {
            this.cw.push();
            this.cw.translate(floatValue7 + 8.0F, floatValue3 - 1.0F, 0.0F);
            this.cw.scale(0.5F, 0.5F, 1.0F);
            minecraftClient.textRenderer
               .draw(
                  value2.C[intValue8],
                  (float)(-value2.D[intValue8]) * 0.5F,
                  -9.0F,
                  -3618561,
                  false,
                  this.cw.peek().getPositionMatrix(),
                  value1,
                  TextLayerType.NORMAL,
                  0,
                  15728880
               );
            this.cw.pop();
         }

         floatValue7 += 17.0F;
      }
   }

   private void a(VertexConsumer vertexConsumer1, Matrix4f matrix4f2, float floatValue3, float floatValue4, int intValue5) {
      float floatValue6 = this.cy.b(intValue5);
      float floatValue7 = this.cy.c(intValue5);
      float floatValue8 = this.cy.d(intValue5);
      float floatValue9 = this.cy.e(intValue5);
      vertexConsumer1.vertex(matrix4f2, floatValue3, floatValue4, 0.0F).color(-1).texture(floatValue6, floatValue8).light(15728880);
      vertexConsumer1.vertex(matrix4f2, floatValue3, floatValue4 + 16.0F, 0.0F).color(-1).texture(floatValue6, floatValue9).light(15728880);
      vertexConsumer1.vertex(matrix4f2, floatValue3 + 16.0F, floatValue4 + 16.0F, 0.0F).color(-1).texture(floatValue7, floatValue9).light(15728880);
      vertexConsumer1.vertex(matrix4f2, floatValue3 + 16.0F, floatValue4, 0.0F).color(-1).texture(floatValue7, floatValue8).light(15728880);
   }

   private static void a(VertexConsumer vertexConsumer0, Matrix4f matrix4f1, float floatValue2, float floatValue3) {
      vertexConsumer0.vertex(matrix4f1, floatValue2, floatValue3, 0.0F).texture(0.0F, 0.0F);
      vertexConsumer0.vertex(matrix4f1, floatValue2, floatValue3 + 16.0F, 0.0F).texture(0.0F, 0.015625F);
      vertexConsumer0.vertex(matrix4f1, floatValue2 + 16.0F, floatValue3 + 16.0F, 0.0F).texture(0.015625F, 0.015625F);
      vertexConsumer0.vertex(matrix4f1, floatValue2 + 16.0F, floatValue3, 0.0F).texture(0.015625F, 0.0F);
   }

   private void a(EntityESP.k value1) {
      LivingEntity livingEntity2 = (LivingEntity)value1.a;

      for (int intValue3 = 0; intValue3 < value1.F; intValue3++) {
         if (!this.cy.a(value1.v[intValue3])) {
            ItemStack itemStack4 = value1.r[intValue3];
            value1.v[intValue3] = value1.w[intValue3] ? this.cy.a(itemStack4, livingEntity2, a((Entity) livingEntity2, intValue3)) : this.cy.a(itemStack4, livingEntity2);
         }
      }
   }

   private static long a(Entity entity0, int intValue1) {
      return (long)entity0.getId() << 3 | (long)intValue1;
   }

   private static boolean a(ItemStack itemStack0, LivingEntity livingEntity1) {
      return !itemStack0.isOf(Items.COMPASS) && !itemStack0.isOf(Items.RECOVERY_COMPASS) && !itemStack0.isOf(Items.CLOCK)
         ? livingEntity1.isUsingItem() && livingEntity1.getActiveItem() == itemStack0
         : true;
   }

   private void a(EntityESP.e value1, int intValue2) {
      this.t();
      Vec3d vec3d3 = minecraftClient.player.getPos();
      int intValue4 = 0;

      for (int intValue5 = 0; intValue5 < this.bL && intValue4 < 48; intValue5++) {
         int intValue6 = this.bI[intValue5];
         int intValue7 = this.bJ[intValue5];
         double doubleValue8 = this.bK[intValue5 * 4];
         double doubleValue10 = this.bK[intValue5 * 4 + 1];
         double doubleValue12 = this.bK[intValue5 * 4 + 2];
         double doubleValue14 = vec3d3.x - doubleValue8;
         double doubleValue16 = vec3d3.y - doubleValue10;
         double doubleValue18 = vec3d3.z - doubleValue12;
         boolean flag20 = intValue7 > 1 && doubleValue14 * doubleValue14 + doubleValue16 * doubleValue16 + doubleValue18 * doubleValue18 > 144.0;
         if (flag20) {
            double doubleValue21 = this.bK[intValue5 * 4 + 3] + 0.25;
            float floatValue23 = (float)Math.min(intValue7, 48) * 10.5F * 0.025F;
            if (this.cv
               .testSphere((float)(doubleValue8 - this.aC.x), (float)(doubleValue21 + (double)floatValue23 * 0.5 - this.aC.y), (float)(doubleValue12 - this.aC.z), floatValue23 * 0.5F + 2.5F)) {
               IntArrays.quickSort(this.bH, intValue6, intValue6 + intValue7, this.bN);
               this.cw.push();
               this.cw.translate(doubleValue8 - this.aC.x, doubleValue21 - this.aC.y, doubleValue12 - this.aC.z);
               this.cw.multiply(this.ct);
               this.cw.scale(0.025F, -0.025F, 0.025F);
               Matrix4f matrix4f24 = this.cw.peek().getPositionMatrix();
               float floatValue25 = -9.0F;

               for (int intValue26 = 0; intValue26 < intValue7 && intValue4 < 48; intValue26++) {
                  EntityESP.j value27 = this.a((ItemEntity)this.bx.get(this.bH[intValue6 + intValue26]));
                  this.a(value27.c, (float)(-value27.d) * 0.5F, floatValue25, -1, matrix4f24, value1, intValue2);
                  floatValue25 -= 10.5F;
                  intValue4++;
               }

               this.cw.pop();
            }
         } else {
            for (int intValue28 = 0; intValue28 < intValue7 && intValue4 < 48; intValue28++) {
               ItemEntity itemEntity22 = (ItemEntity)this.bx.get(this.bH[intValue6 + intValue28]);
               if (this.cv.testSphere((float)(itemEntity22.getX() - this.aC.x), (float)(itemEntity22.getY() + 0.5 - this.aC.y), (float)(itemEntity22.getZ() - this.aC.z), 2.5F)) {
                  EntityESP.j value29 = this.a(itemEntity22);
                  this.a(itemEntity22, (double)itemEntity22.getHeight() + 0.25);
                  this.a(value29.c, (float)(-value29.d) * 0.5F, -9.0F, -1, this.cw.peek().getPositionMatrix(), value1, intValue2);
                  this.cw.pop();
                  intValue4++;
               }
            }
         }
      }
   }

   private void a(Entity entity1, double doubleValue2) {
      double doubleValue4 = MathHelper.lerp((double)this.aD, entity1.lastRenderX, entity1.getX());
      double doubleValue6 = MathHelper.lerp((double)this.aD, entity1.lastRenderY, entity1.getY()) + doubleValue2;
      double doubleValue8 = MathHelper.lerp((double)this.aD, entity1.lastRenderZ, entity1.getZ());
      this.cw.push();
      this.cw.translate(doubleValue4 - this.aC.x, doubleValue6 - this.aC.y, doubleValue8 - this.aC.z);
      this.cw.multiply(this.ct);
      this.cw.scale(0.025F, -0.025F, 0.025F);
   }

   private void a(Text text1, float floatValue2, float floatValue3, int intValue4, Matrix4f matrix4f5, VertexConsumerProvider vertexConsumerProvider6, int intValue7) {
      minecraftClient.textRenderer.draw(text1, floatValue2, floatValue3, intValue4, false, matrix4f5, vertexConsumerProvider6, TextLayerType.NORMAL, intValue7, 15728880);
   }

   private static void a(VertexConsumer vertexConsumer0, Matrix4f matrix4f1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7) {
      vertexConsumer0.vertex(matrix4f1, floatValue2, floatValue3, floatValue6).color(intValue7).light(15728880);
      vertexConsumer0.vertex(matrix4f1, floatValue2, floatValue3 + floatValue5, floatValue6).color(intValue7).light(15728880);
      vertexConsumer0.vertex(matrix4f1, floatValue2 + floatValue4, floatValue3 + floatValue5, floatValue6).color(intValue7).light(15728880);
      vertexConsumer0.vertex(matrix4f1, floatValue2 + floatValue4, floatValue3, floatValue6).color(intValue7).light(15728880);
   }

   private EntityESP.k a(Entity entity1, long longValue2, boolean flag4, boolean flag5, boolean flag6) {
      int intValue7 = entity1.getId();
      EntityESP.k value8 = (EntityESP.k)this.aW.get(intValue7);
      if (value8 == null) {
         value8 = new EntityESP.k();
         this.aW.put(intValue7, value8);
      }

      boolean flag9 = value8.a != entity1;
      boolean flag10 = value8.c != flag4 || value8.d != flag5 || value8.e != flag6;
      if (!flag9 && !flag10 && longValue2 < value8.b) {
         return value8;
      } else {
         if (flag9) {
            value8.a();
         }

         value8.b = longValue2 + 50000000L + (flag9 ? (long)(intValue7 & 7) * 6000000L : 0L);
         value8.a = entity1;
         value8.c = flag4;
         value8.d = flag5;
         value8.e = flag6;
         LivingEntity livingEntity11 = (LivingEntity)entity1;
         this.a(value8, livingEntity11);
         if (entity1 instanceof PlayerEntity playerEntity12) {
            this.a(value8, playerEntity12);
            if (!flag4) {
               value8.F = 0;
            } else {
               this.a(value8, playerEntity12, flag5, flag9 || flag10);
            }

            this.a(value8, playerEntity12, flag6);
         } else {
            Text text13 = entity1.getCustomName();
            if (flag9 || text13 != value8.h) {
               value8.h = text13;
               value8.f = entity1.getDisplayName();
               value8.g = minecraftClient.textRenderer.getWidth(value8.f);
            }

            value8.F = 0;
            value8.L = 0;
         }

         return value8;
      }
   }

   private void a(EntityESP.k value1, LivingEntity livingEntity2) {
      float floatValue3 = Math.max(0.0F, livingEntity2.getAbsorptionAmount());
      float floatValue4 = a(livingEntity2, floatValue3);
      float floatValue5 = MathHelper.clamp(floatValue4 / Math.max(1.0F, livingEntity2.getMaxHealth()), 0.0F, 1.0F);
      int intValue6 = floatValue5 > 0.66F ? -11141291 : (floatValue5 >= 0.33F ? -171 : -43691);
      int intValue7 = Math.round(floatValue4);
      int intValue8 = Math.round(floatValue3);
      if (intValue7 != value1.n || intValue8 != value1.o || intValue6 != value1.p) {
         value1.n = intValue7;
         value1.o = intValue8;
         value1.p = intValue6;
         int intValue9 = intValue6 & 16777215;
         int intValue10 = 11184810;
         MutableText mutableText11 = Text.literal("HP: ").withColor(intValue9);
         mutableText11.append(Text.literal("[").withColor(intValue10));
         mutableText11.append(Text.literal(this.e(intValue7)).withColor(intValue9));
         if (intValue8 > 0) {
            mutableText11.append(Text.literal("+").withColor(intValue10));
            mutableText11.append(Text.literal(this.e(intValue8)).withColor(16755200));
         }

         mutableText11.append(Text.literal("]").withColor(intValue10));
         value1.l = mutableText11;
         value1.m = minecraftClient.textRenderer.getWidth(mutableText11);
      }
   }

   private void a(EntityESP.k value1, PlayerEntity playerEntity2) {
      EntityESP.a value3 = this.a(playerEntity2);
      boolean flag4 = AspectDLC.INSTANCE.friendStorage != null && AspectDLC.INSTANCE.friendStorage.c(playerEntity2.getName().getString());
      String string5 = this.c(playerEntity2.getNameForScoreboard());
      if (value3.d != value1.j || flag4 != value1.k || !string5.equals(value1.i)) {
         value1.j = value3.d;
         value1.k = flag4;
         value1.i = string5;
         MutableText mutableText6 = Text.empty();

         for (EntityESP.i value8 : value3.a) {
            mutableText6.append(Text.literal(value8.text()).withColor(value8.color() & 16777215));
         }

         mutableText6.append(Text.literal(string5).withColor(16777215));

         for (EntityESP.i value10 : value3.b) {
            mutableText6.append(Text.literal(value10.text()).withColor(value10.color() & 16777215));
         }

         if (flag4) {
            mutableText6.append(Text.literal(" [F]").withColor(5635925));
         }

         value1.f = mutableText6;
         value1.g = minecraftClient.textRenderer.getWidth(mutableText6);
      }
   }

   private void a(EntityESP.k value1, PlayerEntity playerEntity2, boolean flag3, boolean flag4) {
      int intValue5 = 0;
      ItemStack[] itemStackArray6 = value1.q;

      for (ItemStack itemStack8 : playerEntity2.getArmorItems()) {
         if (intValue5 < itemStackArray6.length) {
            itemStackArray6[intValue5++] = itemStack8;
         }
      }

      for (int intValue19 = intValue5; intValue19 < itemStackArray6.length; intValue19++) {
         itemStackArray6[intValue19] = ItemStack.EMPTY;
      }

      int intValue20 = 0;

      for (int intValue21 = 3; intValue21 >= 0; intValue21--) {
         if (!itemStackArray6[intValue21].isEmpty()) {
            value1.r[intValue20++] = itemStackArray6[intValue21];
         }
      }

      int intValue22 = intValue20;
      ItemStack itemStack9 = playerEntity2.getMainHandStack();
      ItemStack itemStack10 = playerEntity2.getOffHandStack();
      if (!itemStack9.isEmpty()) {
         intValue22 = intValue20 + 1;
         value1.r[intValue20] = itemStack9;
      }

      if (!itemStack10.isEmpty()) {
         value1.r[intValue22++] = itemStack10;
      }

      boolean flag11 = flag4 || intValue20 != value1.E || intValue22 != value1.F;
      value1.E = intValue20;
      value1.F = intValue22;
      value1.G = (float)intValue22 * 16.0F + (float)Math.max(0, intValue22 - 1) * 1.0F + (intValue20 > 0 && intValue22 > intValue20 ? 5.0F : 0.0F);

      for (int intValue12 = 0; intValue12 < intValue22; intValue12++) {
         ItemStack itemStack13 = value1.r[intValue12];
         boolean flag14 = a(itemStack13, playerEntity2);
         ItemStack itemStack15 = value1.s[intValue12];
         boolean flag16 = !flag11
            && itemStack15 != null
            && (itemStack15 == itemStack13 ? itemStack13.getCount() == value1.t[intValue12] && itemStack13.getDamage() == value1.u[intValue12] : ItemStack.areEqual(itemStack15, itemStack13));
         if (flag14) {
            value1.v[intValue12] = this.cy.a(itemStack13, playerEntity2, a((Entity) playerEntity2, intValue12));
         } else if (!flag16 || value1.w[intValue12] || !this.cy.a(value1.v[intValue12])) {
            value1.v[intValue12] = this.cy.a(itemStack13, playerEntity2);
         }

         value1.w[intValue12] = flag14;
         value1.s[intValue12] = itemStack13;
         value1.t[intValue12] = itemStack13.getCount();
         value1.u[intValue12] = itemStack13.getDamage();
         if (!flag16) {
            value1.x[intValue12] = itemStack13.hasGlint();
            value1.y[intValue12] = itemStack13.isItemBarVisible() ? itemStack13.getItemBarStep() : -1;
            value1.z[intValue12] = 0xFF000000 | itemStack13.getItemBarColor();
            int intValue17 = itemStack13.getCount();
            value1.A[intValue12] = intValue17 > 1 ? this.e(intValue17) : null;
            value1.B[intValue12] = intValue17 > 1 ? minecraftClient.textRenderer.getWidth(value1.A[intValue12]) : 0;
            if (intValue12 < 4) {
               String string18 = flag3 && intValue12 < intValue20 ? this.a(itemStack13) : null;
               value1.C[intValue12] = string18 != null && !string18.isEmpty() ? string18 : null;
               value1.D[intValue12] = value1.C[intValue12] != null ? minecraftClient.textRenderer.getWidth(value1.C[intValue12]) : 0;
            }
         }
      }

      for (int intValue23 = intValue22; intValue23 < value1.r.length; intValue23++) {
         value1.r[intValue23] = null;
         value1.s[intValue23] = null;
         value1.v[intValue23] = -1;
         value1.w[intValue23] = false;
         if (intValue23 < 4) {
            value1.C[intValue23] = null;
         }
      }
   }

   private void a(EntityESP.k value1, PlayerEntity playerEntity2, boolean flag3) {
      int intValue4 = 0;
      if (flag3) {
         intValue4 = this.a(value1, intValue4, playerEntity2.getMainHandStack());
         intValue4 = this.a(value1, intValue4, playerEntity2.getOffHandStack());
      }

      for (int intValue5 = intValue4; intValue5 < value1.J.length; intValue5++) {
         value1.J[intValue5] = null;
      }

      value1.L = intValue4;
   }

   private int a(EntityESP.k value1, int intValue2, ItemStack itemStack3) {
      if (itemStack3.isEmpty()) {
         return intValue2;
      } else if (value1.J[intValue2] == itemStack3 && value1.K[intValue2] == itemStack3.getCount() && value1.H[intValue2] != null) {
         return intValue2 + 1;
      } else {
         MutableText mutableText4 = Text.empty().append(itemStack3.getName()).withColor(this.b(itemStack3) & 16777215);
         if (itemStack3.getCount() > 1) {
            mutableText4.append(Text.literal(" " + this.f(itemStack3.getCount())).withColor(11184810));
         }

         value1.H[intValue2] = mutableText4;
         value1.I[intValue2] = minecraftClient.textRenderer.getWidth(mutableText4);
         value1.J[intValue2] = itemStack3;
         value1.K[intValue2] = itemStack3.getCount();
         return intValue2 + 1;
      }
   }

   private EntityESP.j a(ItemEntity itemEntity1) {
      ItemStack itemStack2 = itemEntity1.getStack();
      int intValue3 = itemEntity1.getId();
      EntityESP.j value4 = (EntityESP.j)this.aX.get(intValue3);
      if (value4 != null && value4.a == itemStack2 && value4.b == itemStack2.getCount()) {
         return value4;
      } else {
         if (value4 == null) {
            value4 = new EntityESP.j();
            this.aX.put(intValue3, value4);
         }

         value4.a = itemStack2;
         value4.b = itemStack2.getCount();
         value4.c = Text.empty().append(itemStack2.getName()).append(Text.literal(" " + this.f(itemStack2.getCount())).withColor(11184810));
         value4.d = minecraftClient.textRenderer.getWidth(value4.c);
         return value4;
      }
   }

   private EntityESP.g a(PlayerEntity playerEntity1, int intValue2, dev.leg1tka.render.font.MsdfTextRenderer value3, dev.leg1tka.render.font.MsdfTextRenderer value4, long longValue5) {
      int intValue7 = playerEntity1.getId();
      EntityESP.g value8 = (EntityESP.g)this.aR.get(intValue7);
      if (value8 == null) {
         value8 = new EntityESP.g();
         value8.e = 0L;
         this.aR.put(intValue7, value8);
      }

      if (value8.a == playerEntity1 && longValue5 < value8.e && (intValue2 & ~value8.f) == 0 && value8.g == value3 && value8.h == value4) {
         return value8;
      } else {
         boolean flag9 = value8.a != playerEntity1;
         value8.e = longValue5 + 50000000L + (flag9 ? (long)(intValue7 & 7) * 6000000L : 0L);
         value8.a = playerEntity1;
         value8.f = intValue2;
         value8.g = value3;
         value8.h = value4;
         value8.i = AspectDLC.INSTANCE.friendStorage != null && AspectDLC.INSTANCE.friendStorage.c(playerEntity1.getName().getString());
         value8.j = playerEntity1 instanceof AbstractClientPlayerEntity abstractClientPlayerEntity10 ? abstractClientPlayerEntity10.getSkinTextures().texture() : null;
         value8.b = dev.leg1tka.api.utils.player.EquipmentUtils.a(playerEntity1);
         this.a(value8, playerEntity1);
         if (value3 != null && (intValue2 & 1) != 0) {
            this.a(value8, playerEntity1, value3);
         }

         if ((intValue2 & 6) != 0) {
            this.a(value8, playerEntity1, intValue2, value4);
         }

         if (value3 != null && (intValue2 & 4) != 0) {
            this.a(value8, value3);
         } else {
            value8.L = 0;
         }

         return value8;
      }
   }

   private void a(EntityESP.g value1, PlayerEntity playerEntity2) {
      this.a(value1.s, playerEntity2);
   }

   private void a(EntityESP.b value1, LivingEntity livingEntity2) {
      float floatValue3 = Math.max(0.0F, livingEntity2.getAbsorptionAmount());
      float floatValue4 = a(livingEntity2, floatValue3);
      value1.j = MathHelper.clamp(floatValue4 / Math.max(1.0F, livingEntity2.getMaxHealth()), 0.0F, 1.0F);
      value1.i = a(value1.j);
      value1.a = this.e(Math.round(floatValue4));
      int intValue5 = Math.round(floatValue3);
      value1.b = intValue5 > 0 ? this.e(intValue5) : null;
   }

   private static float a(LivingEntity livingEntity0, float floatValue1) {
      float floatValue2 = Math.max(0.0F, livingEntity0.getHealth());
      float floatValue3 = HPFixer.c(livingEntity0);
      if (Math.abs(floatValue3 - (floatValue2 + floatValue1)) < 0.01F) {
         return floatValue2;
      } else {
         return floatValue1 > 0.0F && floatValue3 > livingEntity0.getMaxHealth() + 0.5F ? Math.max(0.0F, floatValue3 - floatValue1) : floatValue3;
      }
   }

   private void a(EntityESP.b value1, dev.leg1tka.render.font.MsdfTextRenderer value2) {
      this.a(value2, "[");
      value1.c = this.bb[0];
      float floatValue3 = value2.a("[");
      value1.d = floatValue3;
      floatValue3 += value2.a(value1.a);
      if (value1.b != null) {
         value1.e = floatValue3;
         floatValue3 += value2.a("+");
         value1.f = floatValue3;
         floatValue3 += value2.a(value1.b);
      }

      value1.g = floatValue3;
      this.a(value2, "]");
      value1.h = floatValue3 + this.bb[1] - value1.c;
   }

   private void a(EntityESP.g value1, PlayerEntity playerEntity2, dev.leg1tka.render.font.MsdfTextRenderer value3) {
      EntityESP.a value4 = this.a(playerEntity2);
      value1.k = value4.a;
      value1.l = value4.b;
      value1.m = this.a(value3, value1.k, value1.m);
      value1.n = this.a(value3, value1.l, value1.n);
      value1.o = this.c(playerEntity2.getNameForScoreboard());
      value1.p = value3.a(value1.o);
      StringBuilder stringBuilder5 = new StringBuilder();

      for (EntityESP.i value7 : value1.k) {
         stringBuilder5.append(value7.text());
      }

      stringBuilder5.append(value1.o);

      for (EntityESP.i value9 : value1.l) {
         stringBuilder5.append(value9.text());
      }

      if (value1.i) {
         stringBuilder5.append(" [F]");
      }

      this.a(value3, stringBuilder5.toString());
      value1.q = this.bb[0];
      value1.r = this.bb[1];
      this.a(value1.s, value3);
      value1.t = 17.6F + (value1.r - value1.q) + 3.5F + 0.6F + 3.5F + value1.s.h + 4.0F;
      if (value1.b) {
         this.a(value3);
         value1.t = value1.t + 3.0F + this.af;
      }
   }

   private float[] a(dev.leg1tka.render.font.MsdfTextRenderer value1, List<EntityESP.i> list2, float[] floatValueArray3) {
      int intValue4 = list2.size();
      if (floatValueArray3.length < intValue4) {
         floatValueArray3 = new float[intValue4];
      }

      for (int intValue5 = 0; intValue5 < intValue4; intValue5++) {
         floatValueArray3[intValue5] = value1.a(((EntityESP.i)list2.get(intValue5)).text());
      }

      return floatValueArray3;
   }

   private void a(dev.leg1tka.render.font.MsdfTextRenderer value1, String string2) {
      value1.a(string2, this.bb);
   }

   private void a(EntityESP.g value1, PlayerEntity playerEntity2, int intValue3, dev.leg1tka.render.font.MsdfTextRenderer value4) {
      ItemStack[] itemStackArray5 = value1.u;
      int intValue6 = 0;

      for (ItemStack itemStack8 : playerEntity2.getArmorItems()) {
         if (intValue6 < itemStackArray5.length) {
            itemStackArray5[intValue6++] = itemStack8;
         }
      }

      for (int intValue15 = intValue6; intValue15 < itemStackArray5.length; intValue15++) {
         itemStackArray5[intValue15] = ItemStack.EMPTY;
      }

      int intValue16 = 0;

      for (int intValue17 = 3; intValue17 >= 0; intValue17--) {
         if (!itemStackArray5[intValue17].isEmpty()) {
            value1.v[intValue16++] = itemStackArray5[intValue17];
         }
      }

      value1.z = intValue16;
      ItemStack itemStack18 = playerEntity2.getMainHandStack();
      ItemStack itemStack9 = playerEntity2.getOffHandStack();
      value1.C = itemStack18;
      value1.D = itemStack9;
      int intValue10 = intValue16;
      if (!itemStack18.isEmpty()) {
         intValue10 = intValue16 + 1;
         value1.v[intValue16] = itemStack18;
      }

      if (!itemStack9.isEmpty()) {
         value1.v[intValue10++] = itemStack9;
      }

      value1.A = intValue10;

      for (int intValue11 = intValue10; intValue11 < value1.v.length; intValue11++) {
         value1.v[intValue11] = null;
      }

      int intValue19 = intValue10 - intValue16;
      value1.B = (float)intValue10 * 12.0F + (float)Math.max(0, intValue10 - 1) * 1.0F + (intValue16 > 0 && intValue19 > 0 ? 4.0F : 0.0F);
      value1.G = false;
      if ((intValue3 & 2) != 0) {
         for (int intValue12 = 0; intValue12 < intValue10; intValue12++) {
            ItemStack itemStack13 = value1.v[intValue12];
            if (itemStack13.isDamageable() && itemStack13.getDamage() > 0) {
               float floatValue14 = 1.0F - (float)itemStack13.getDamage() / (float)itemStack13.getMaxDamage();
               value1.x[intValue12] = floatValue14;
               value1.y[intValue12] = dev.leg1tka.api.utils.text.ColorUtils.d(-38037, -8588646, floatValue14);
            } else {
               value1.x[intValue12] = -1.0F;
            }

            ItemRenderState itemRenderState22 = value1.w[intValue12];
            if (itemRenderState22 == null) {
               itemRenderState22 = new ItemRenderState();
               value1.w[intValue12] = itemRenderState22;
            }

            minecraftClient.getItemModelManager().update(itemRenderState22, itemStack13, ModelTransformationMode.GUI, false, minecraftClient.world, minecraftClient.player, 0);
         }

         if (value4 != null) {
            for (int intValue20 = 0; intValue20 < intValue16; intValue20++) {
               String string21 = this.a(value1.v[intValue20]);
               boolean flag23 = string21 != null && !string21.isEmpty();
               value1.E[intValue20] = flag23 ? string21 : null;
               if (flag23) {
                  this.a(value4, string21);
               }

               value1.F[intValue20] = flag23 ? (this.bb[0] + this.bb[1]) * 0.5F : 0.0F;
               value1.G |= flag23;
            }
         }
      }
   }

   private void a(EntityESP.g value1, dev.leg1tka.render.font.MsdfTextRenderer value2) {
      int intValue3 = 0;
      if (!value1.C.isEmpty()) {
         intValue3 = this.a(value1, intValue3, value1.C, value2);
      }

      if (!value1.D.isEmpty()) {
         intValue3 = this.a(value1, intValue3, value1.D, value2);
      }

      value1.L = intValue3;
   }

   private int a(EntityESP.g value1, int intValue2, ItemStack itemStack3, dev.leg1tka.render.font.MsdfTextRenderer value4) {
      int intValue5 = itemStack3.getCount();
      String string6 = itemStack3.getName().getString();
      String string7 = intValue5 > 1 ? string6 + " ×" + intValue5 : string6;
      value1.H[intValue2] = string7;
      this.a(value4, string7);
      value1.J[intValue2] = this.bb[0];
      value1.I[intValue2] = this.bb[1] - this.bb[0];
      value1.K[intValue2] = this.b(itemStack3);
      return intValue2 + 1;
   }

   private EntityESP.f a(LivingEntity livingEntity1, dev.leg1tka.render.font.MsdfTextRenderer value2, long longValue3) {
      int intValue5 = livingEntity1.getId();
      EntityESP.f value6 = (EntityESP.f)this.aS.get(intValue5);
      if (value6 == null) {
         value6 = new EntityESP.f();
         this.aS.put(intValue5, value6);
      }

      if (value6.a == livingEntity1 && value6.c == value2 && longValue3 < value6.b) {
         return value6;
      } else {
         boolean flag7 = value6.a != livingEntity1;
         value6.b = longValue3 + 50000000L + (flag7 ? (long)(intValue5 & 7) * 6000000L : 0L);
         value6.a = livingEntity1;
         value6.c = value2;
         value6.d = livingEntity1.getDisplayName().getString();
         this.a(value2, value6.d);
         value6.f = this.bb[0];
         value6.e = this.bb[1] - this.bb[0];
         this.a(value6.g, livingEntity1);
         this.a(value6.g, value2);
         value6.h = 4.0F + value6.e + 3.5F + 0.6F + 3.5F + value6.g.h + 4.0F;
         return value6;
      }
   }

   private void b(dev.leg1tka.render.font.MsdfTextRenderer value1) {
      this.t();
      Vec3d vec3d2 = minecraftClient.player.getPos();
      int intValue3 = 0;

      for (int intValue4 = 0; intValue4 < this.bL && intValue3 < 48; intValue4++) {
         int intValue5 = this.bI[intValue4];
         int intValue6 = this.bJ[intValue4];
         double doubleValue7 = this.bK[intValue4 * 4];
         double doubleValue9 = this.bK[intValue4 * 4 + 1];
         double doubleValue11 = this.bK[intValue4 * 4 + 2];
         double doubleValue13 = this.bK[intValue4 * 4 + 3];
         double doubleValue15 = vec3d2.x - doubleValue7;
         double doubleValue17 = vec3d2.y - doubleValue9;
         double doubleValue19 = vec3d2.z - doubleValue11;
         boolean flag21 = intValue6 > 1 && doubleValue15 * doubleValue15 + doubleValue17 * doubleValue17 + doubleValue19 * doubleValue19 > 144.0;
         if (flag21) {
            if (this.a(doubleValue7, doubleValue13 + 0.25, doubleValue11, this.aJ)) {
               IntArrays.quickSort(this.bH, intValue5, intValue5 + intValue6, this.bN);
               float floatValue22 = this.aJ.b - 2.0F;
               float floatValue23 = this.aJ.a;

               for (int intValue24 = 0; intValue24 < intValue6 && intValue3 < 48; intValue24++) {
                  ItemEntity itemEntity25 = (ItemEntity)this.bx.get(this.bH[intValue5 + intValue24]);
                  EntityESP.d value26 = this.a(itemEntity25, value1);
                  this.a(value26);
                  this.a(value26, value1, floatValue23, floatValue22 - 12.0F, true);
                  floatValue22 -= 13.5F;
                  intValue3++;
               }
            }
         } else {
            for (int intValue27 = 0; intValue27 < intValue6 && intValue3 < 48; intValue27++) {
               ItemEntity itemEntity28 = (ItemEntity)this.bx.get(this.bH[intValue5 + intValue27]);
               if (this.a(itemEntity28, (double)itemEntity28.getHeight() + 0.25, this.aJ)) {
                  this.a(this.a(itemEntity28, value1), value1, this.aJ.a, this.aJ.b - 2.0F - 12.0F, false);
                  intValue3++;
               }
            }
         }
      }
   }

   private void a(EntityESP.d value1, dev.leg1tka.render.font.MsdfTextRenderer value2, float floatValue3, float floatValue4, boolean flag5) {
      float floatValue6 = flag5 ? value1.n : value1.h;
      float floatValue7 = flag5 ? value1.o : value1.i;
      float floatValue8 = 4.0F + (floatValue7 - floatValue6) + 3.0F + (value1.k - value1.j) + 4.0F;
      float floatValue9 = this.b(floatValue3 - floatValue8 * 0.5F);
      floatValue4 = this.b(floatValue4);
      this.r();
      this.a(floatValue9, floatValue4, floatValue8, 12.0F);
      int intValue10 = this.q();
      this.bd[intValue10].a(floatValue9, floatValue4, floatValue8, 12.0F, 5.0F, 1795952148, 0.8F);
      float floatValue11 = this.a(value2, floatValue4 + 6.0F);
      float floatValue12 = floatValue9 + 4.0F - floatValue6;
      if (flag5) {
         this.a(this.bh[intValue10], value2, value1.l, value1.m, floatValue12, floatValue11);
      } else {
         this.bh[intValue10].a(value2, value1.e, floatValue12, floatValue11, value1.f, true);
      }

      float floatValue13 = floatValue9 + 4.0F + (floatValue7 - floatValue6) + 3.0F;
      this.bh[intValue10].a(value2, value1.g, floatValue13 - value1.j, floatValue11, -6579301, true);
   }

   private EntityESP.d a(ItemEntity itemEntity1, dev.leg1tka.render.font.MsdfTextRenderer value2) {
      ItemStack itemStack3 = itemEntity1.getStack();
      Language language4 = Language.getInstance();
      int intValue5 = itemEntity1.getId();
      EntityESP.d value6 = (EntityESP.d)this.aT.get(intValue5);
      if (value6 != null && value6.a == itemStack3 && value6.b == itemStack3.getCount() && value6.c == language4 && value6.d == value2) {
         return value6;
      } else {
         if (value6 == null) {
            value6 = new EntityESP.d();
            this.aT.put(intValue5, value6);
         }

         value6.a = itemStack3;
         value6.b = itemStack3.getCount();
         value6.c = language4;
         value6.d = value2;
         value6.e = itemStack3.getName().getString();
         value6.f = this.b(itemStack3);
         value6.g = this.f(itemStack3.getCount());
         this.a(value2, value6.e);
         value6.h = this.bb[0];
         value6.i = this.bb[1];
         this.a(value2, value6.g);
         value6.j = this.bb[0];
         value6.k = this.bb[1];
         value6.l = null;
         return value6;
      }
   }

   private void a(EntityESP.d value1) {
      if (value1.l == null) {
         ArrayList arrayList2 = new ArrayList();
         this.a(arrayList2, value1.a.getName());
         if (arrayList2.isEmpty()) {
            arrayList2.add(new EntityESP.i(value1.e, value1.f));
         }

         value1.m = this.a(value1.d, arrayList2, new float[arrayList2.size()]);
         this.a(value1.d, c(arrayList2));
         value1.n = this.bb[0];
         value1.o = this.bb[1];
         value1.l = arrayList2;
      }
   }

   private void t() {
      int intValue1 = this.bx.size();
      this.bL = 0;
      if (intValue1 != 0) {
         this.d(intValue1);
         this.by.clear();
         this.bA = 0;
         float floatValue2 = 3.5F;
         double doubleValue3 = (double)floatValue2 * (double)floatValue2;

         for (int intValue5 = 0; intValue5 < intValue1; intValue5++) {
            ItemEntity itemEntity6 = (ItemEntity)this.bx.get(intValue5);
            this.bB[intValue5] = MathHelper.floor(itemEntity6.getX() / (double)floatValue2);
            this.bC[intValue5] = MathHelper.floor(itemEntity6.getY() / (double)floatValue2);
            this.bD[intValue5] = MathHelper.floor(itemEntity6.getZ() / (double)floatValue2);
            this.bE[intValue5] = 0;
            this.bG[intValue5] = false;
            this.bF[intValue5] = Registries.ITEM.getRawId(itemEntity6.getStack().getItem());
            long longValue7 = a(this.bB[intValue5], this.bC[intValue5], this.bD[intValue5]);
            IntArrayList intArrayList9 = (IntArrayList)this.by.get(longValue7);
            if (intArrayList9 == null) {
               intArrayList9 = this.u();
               this.by.put(longValue7, intArrayList9);
            }

            intArrayList9.add(intValue5);
         }

         int intValue26 = 0;

         for (int intValue27 = 0; intValue27 < intValue1; intValue27++) {
            if (!this.bG[intValue27]) {
               this.bG[intValue27] = true;
               ItemEntity itemEntity28 = (ItemEntity)this.bx.get(intValue27);
               double doubleValue8 = itemEntity28.getX();
               double doubleValue10 = itemEntity28.getY();
               double doubleValue12 = itemEntity28.getZ();
               int intValue14 = intValue27 + 1;
               this.bM.clear();

               for (int intValue15 = -1; intValue15 <= 1; intValue15++) {
                  for (int intValue16 = -1; intValue16 <= 1; intValue16++) {
                     for (int intValue17 = -1; intValue17 <= 1; intValue17++) {
                        IntArrayList intArrayList18 = (IntArrayList)this.by.get(a(this.bB[intValue27] + intValue15, this.bC[intValue27] + intValue16, this.bD[intValue27] + intValue17));
                        if (intArrayList18 != null) {
                           int intValue19 = 0;

                           for (int intValue20 = intArrayList18.size(); intValue19 < intValue20; intValue19++) {
                              int intValue21 = intArrayList18.getInt(intValue19);
                              if (!this.bG[intValue21] && this.bE[intValue21] != intValue14) {
                                 this.bE[intValue21] = intValue14;
                                 if (((ItemEntity)this.bx.get(intValue21)).squaredDistanceTo(doubleValue8, doubleValue10, doubleValue12) <= doubleValue3) {
                                    this.bM.add(intValue21);
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               IntArrays.quickSort(this.bM.elements(), 0, this.bM.size());
               int intValue29 = intValue26;
               this.bH[intValue26++] = intValue27;
               int intValue30 = 0;

               for (int intValue32 = this.bM.size(); intValue30 < intValue32; intValue30++) {
                  int intValue33 = this.bM.getInt(intValue30);
                  this.bG[intValue33] = true;
                  this.bH[intValue26++] = intValue33;
               }

               double doubleValue31 = 0.0;
               double doubleValue34 = 0.0;
               double doubleValue35 = 0.0;
               double doubleValue22 = Double.NEGATIVE_INFINITY;

               for (int intValue24 = intValue29; intValue24 < intValue26; intValue24++) {
                  ItemEntity itemEntity25 = (ItemEntity)this.bx.get(this.bH[intValue24]);
                  doubleValue31 += itemEntity25.getX();
                  doubleValue34 += itemEntity25.getY();
                  doubleValue35 += itemEntity25.getZ();
                  doubleValue22 = Math.max(doubleValue22, itemEntity25.getY() + (double)itemEntity25.getHeight());
               }

               int intValue36 = intValue26 - intValue29;
               int intValue37 = this.bL++;
               this.bI[intValue37] = intValue29;
               this.bJ[intValue37] = intValue36;
               this.bK[intValue37 * 4] = doubleValue31 / (double)intValue36;
               this.bK[intValue37 * 4 + 1] = doubleValue34 / (double)intValue36;
               this.bK[intValue37 * 4 + 2] = doubleValue35 / (double)intValue36;
               this.bK[intValue37 * 4 + 3] = doubleValue22;
            }
         }
      }
   }

   private void d(int intValue1) {
      if (this.bB.length < intValue1) {
         int intValue2 = Math.max(intValue1, this.bB.length * 2);
         this.bB = new int[intValue2];
         this.bC = new int[intValue2];
         this.bD = new int[intValue2];
         this.bE = new int[intValue2];
         this.bF = new int[intValue2];
         this.bG = new boolean[intValue2];
         this.bH = new int[intValue2];
         this.bI = new int[intValue2];
         this.bJ = new int[intValue2];
         this.bK = new double[intValue2 * 4];
      }
   }

   private IntArrayList u() {
      if (this.bA == this.bz.size()) {
         this.bz.add(new IntArrayList(4));
      }

      IntArrayList intArrayList1 = (IntArrayList)this.bz.get(this.bA++);
      intArrayList1.clear();
      return intArrayList1;
   }

   private void v() {
      long longValue1 = System.currentTimeMillis();
      if (longValue1 >= this.aY) {
         this.aY = longValue1 + 2000L;
         this.aR.int2ObjectEntrySet().removeIf((Int2ObjectMap.Entry<EntityESP.g> entry0) -> minecraftClient.world.getEntityById(entry0.getIntKey()) != entry0.getValue().a);
         this.aS.int2ObjectEntrySet().removeIf((Int2ObjectMap.Entry<EntityESP.f> entry0) -> minecraftClient.world.getEntityById(entry0.getIntKey()) != entry0.getValue().a);
         this.aT.int2ObjectEntrySet().removeIf((Int2ObjectMap.Entry<EntityESP.d> entry0) -> !(minecraftClient.world.getEntityById(entry0.getIntKey()) instanceof ItemEntity));
         this.aW.int2ObjectEntrySet().removeIf((Int2ObjectMap.Entry<EntityESP.k> entry0) -> minecraftClient.world.getEntityById(entry0.getIntKey()) != entry0.getValue().a);
         this.aX.int2ObjectEntrySet().removeIf((Int2ObjectMap.Entry<EntityESP.j> entry0) -> !(minecraftClient.world.getEntityById(entry0.getIntKey()) instanceof ItemEntity));
         this.aQ.entrySet().removeIf(entry0 -> minecraftClient.world.getPlayerByUuid(entry0.getKey()) == null);
      }
   }

   private EntityESP.a a(PlayerEntity playerEntity1) {
      long longValue2 = System.currentTimeMillis();
      EntityESP.a value4 = this.aQ.computeIfAbsent(playerEntity1.getUuid(), uUID0 -> new EntityESP.a());
      if (longValue2 < value4.c) {
         return value4;
      } else {
         value4.c = longValue2 + 1000L;
         ArrayList arrayList5 = new ArrayList();
         ArrayList arrayList6 = new ArrayList();
         dev.leg1tka.gui.themes.ThemeGroupRegistry.a value7 = dev.leg1tka.gui.themes.ThemeGroupRegistry.a(playerEntity1);
         if (value7 != null) {
            arrayList5.add(new EntityESP.i(value7.text() + " ", value7.col()));
            return this.a(value4, arrayList5, arrayList6);
         } else if (minecraftClient.getNetworkHandler() == null) {
            return this.a(value4, arrayList5, arrayList6);
         } else {
            PlayerListEntry playerListEntry8 = minecraftClient.getNetworkHandler().getPlayerListEntry(playerEntity1.getUuid());
            Text text9 = playerListEntry8 != null ? playerListEntry8.getDisplayName() : null;
            if (text9 == null) {
               text9 = playerEntity1.getDisplayName();
            }

            if (text9 == null) {
               return this.a(value4, arrayList5, arrayList6);
            } else {
               String[] stringArray10 = this.b(playerEntity1);
               boolean[] flagArray11 = new boolean[]{false};
               text9.visit((style5x, string6x) -> {
                  if (string6x != null && !string6x.isEmpty()) {
                     String string7x = string6x.replace('\n', ' ').replace('\r', ' ');
                     int intValue8x = style5x.getColor() != null ? style5x.getColor().getRgb() : 16777215;
                     if (flagArray11[0]) {
                        this.a(arrayList6, string7x, intValue8x);
                        return Optional.empty();
                     } else {
                        int intValue9x = this.a(string7x, stringArray10);
                        if (intValue9x < 0) {
                           this.a(arrayList5, string7x, intValue8x);
                           return Optional.empty();
                        } else {
                           this.a(arrayList5, string7x.substring(0, intValue9x), intValue8x);
                           this.a(arrayList6, string7x.substring(intValue9x + this.ba), intValue8x);
                           flagArray11[0] = true;
                           return Optional.empty();
                        }
                     }
                  } else {
                     return Optional.empty();
                  }
               }, Style.EMPTY);
               if (!flagArray11[0]) {
                  arrayList5.clear();
                  arrayList6.clear();
                  Team team12 = playerEntity1.getScoreboardTeam();
                  if (team12 != null) {
                     if (team12.getPrefix() != null) {
                        this.a(arrayList5, team12.getPrefix());
                     }

                     if (team12.getSuffix() != null) {
                        this.a(arrayList6, team12.getSuffix());
                     }
                  }

                  int intValue13 = this.a(c(arrayList5), stringArray10);
                  if (intValue13 >= 0) {
                     this.a(arrayList5, intValue13);
                  }
               }

               return this.a(value4, arrayList5, arrayList6);
            }
         }
      }
   }

   private EntityESP.a a(EntityESP.a value1, List<EntityESP.i> list2, List<EntityESP.i> list3) {
      this.a(list2);
      this.b(list3);
      if (list2.equals(value1.a) && list3.equals(value1.b) && value1.d != 0L) {
         return value1;
      } else {
         value1.a = list2.isEmpty() ? Collections.emptyList() : List.copyOf(list2);
         value1.b = list3.isEmpty() ? Collections.emptyList() : List.copyOf(list3);
         value1.d = ++this.aZ;
         return value1;
      }
   }

   private void a(List<EntityESP.i> list1) {
      if (c(list1).trim().isEmpty()) {
         list1.clear();
      } else {
         int intValue2 = list1.size() - 1;
         EntityESP.i value3 = (EntityESP.i)list1.get(intValue2);
         if (!value3.text().endsWith(" ")) {
            list1.set(intValue2, new EntityESP.i(value3.text() + " ", value3.color()));
         }
      }
   }

   private void b(List<EntityESP.i> list1) {
      while (!list1.isEmpty()) {
         EntityESP.i value2 = (EntityESP.i)list1.get(0);
         String string3 = value2.text().stripLeading();
         if (!string3.isEmpty()) {
            list1.set(0, new EntityESP.i(string3, value2.color()));
            break;
         }

         list1.remove(0);
      }

      this.a(list1, 16);

      while (!list1.isEmpty()) {
         int intValue5 = list1.size() - 1;
         EntityESP.i value7 = (EntityESP.i)list1.get(intValue5);
         String string4 = value7.text().stripTrailing();
         if (!string4.isEmpty()) {
            list1.set(intValue5, new EntityESP.i(string4, value7.color()));
            break;
         }

         list1.remove(intValue5);
      }

      if (!list1.isEmpty()) {
         EntityESP.i value6 = (EntityESP.i)list1.get(0);
         list1.set(0, new EntityESP.i(" " + value6.text(), value6.color()));
      }
   }

   private static String c(List<EntityESP.i> list0) {
      StringBuilder stringBuilder1 = new StringBuilder();

      for (EntityESP.i value3 : list0) {
         stringBuilder1.append(value3.text());
      }

      return stringBuilder1.toString();
   }

   private void a(List<EntityESP.i> list1, int intValue2) {
      int intValue3 = Math.max(0, intValue2);

      for (int intValue4 = 0; intValue4 < list1.size(); intValue4++) {
         EntityESP.i value5 = (EntityESP.i)list1.get(intValue4);
         if (intValue3 <= 0) {
            list1.subList(intValue4, list1.size()).clear();
            return;
         }

         int intValue6 = value5.text().length();
         if (intValue6 > intValue3) {
            list1.set(intValue4, new EntityESP.i(value5.text().substring(0, intValue3), value5.color()));
            list1.subList(intValue4 + 1, list1.size()).clear();
            return;
         }

         intValue3 -= intValue6;
      }
   }

   private void a(List<EntityESP.i> list1, Text text2) {
      text2.visit((style2x, string3) -> {
         if (string3 != null && !string3.isEmpty()) {
            int intValue4 = style2x.getColor() != null ? style2x.getColor().getRgb() : 16777215;
            this.a(list1, string3.replace('\n', ' ').replace('\r', ' '), intValue4);
            return Optional.empty();
         } else {
            return Optional.empty();
         }
      }, Style.EMPTY);
   }

   private void a(List<EntityESP.i> list1, String string2, int intValue3) {
      if (string2 != null && !string2.isEmpty()) {
         int intValue4 = intValue3;
         int intValue5 = intValue3;
         StringBuilder stringBuilder6 = new StringBuilder();
         int intValue7 = 0;

         while (intValue7 < string2.length()) {
            int intValue8 = string2.codePointAt(intValue7);
            int intValue9 = Character.charCount(intValue8);
            if (intValue8 == 167 && intValue7 + intValue9 < string2.length()) {
               this.a(list1, stringBuilder6, intValue5);
               char character14 = Character.toLowerCase(string2.charAt(intValue7 + intValue9));
               int intValue15 = a(character14);
               if (intValue15 >= 0) {
                  intValue4 = intValue15;
               } else if (character14 == 'r') {
                  intValue4 = intValue3;
               }

               intValue5 = intValue4;
               intValue7 += intValue9 + 1;
            } else {
               String string10 = dev.leg1tka.gui.themes.ThemeRoleNames.a(intValue8);
               if (string10 == null) {
                  if (stringBuilder6.length() > 0 && intValue5 != intValue4) {
                     this.a(list1, stringBuilder6, intValue5);
                  }

                  intValue5 = intValue4;
                  stringBuilder6.appendCodePoint(intValue8);
                  intValue7 += intValue9;
               } else {
                  this.a(list1, stringBuilder6, intValue5);
                  int intValue11 = Math.max(1, string10.length());

                  for (int intValue12 = 0; intValue12 < string10.length(); intValue12++) {
                     int intValue13 = dev.leg1tka.gui.themes.ThemeRoleNames.a(intValue8, intValue12, intValue11, 1.0F, intValue4);
                     if (stringBuilder6.length() > 0 && intValue5 != intValue13) {
                        this.a(list1, stringBuilder6, intValue5);
                     }

                     intValue5 = intValue13;
                     stringBuilder6.append(string10.charAt(intValue12));
                  }

                  intValue7 += intValue9;
               }
            }
         }

         this.a(list1, stringBuilder6, intValue5);
      }
   }

   private void a(List<EntityESP.i> list1, StringBuilder stringBuilder2, int intValue3) {
      if (!stringBuilder2.isEmpty()) {
         list1.add(new EntityESP.i(stringBuilder2.toString(), 0xFF000000 | intValue3));
         stringBuilder2.setLength(0);
      }
   }

   private static int a(char character0) {
      return switch (character0) {
         case '0' -> 0;
         case '1' -> 170;
         case '2' -> 43520;
         case '3' -> 43690;
         case '4' -> 11141120;
         case '5' -> 11141290;
         case '6' -> 16755200;
         case '7' -> 11184810;
         case '8' -> 5592405;
         case '9' -> 5592575;
         default -> -1;
         case 'a' -> 5635925;
         case 'b' -> 5636095;
         case 'c' -> 16733525;
         case 'd' -> 16733695;
         case 'e' -> 16777045;
         case 'f' -> 16777215;
      };
   }

   private String[] b(PlayerEntity playerEntity1) {
      String string2 = playerEntity1.getGameProfile() != null ? playerEntity1.getGameProfile().getName() : "";
      String string3 = playerEntity1.getNameForScoreboard();
      String string4 = playerEntity1.getName().getString();
      return new String[]{string4, this.c(string4), string3, this.c(string3), string2, this.c(string2)};
   }

   private String c(String string1) {
      NameProtect nameProtect2 = dev.leg1tka.managers.ModuleManager.a != null ? dev.leg1tka.managers.ModuleManager.A : null;
      return nameProtect2 != null && nameProtect2.isEnabled() ? nameProtect2.c(string1) : string1;
   }

   private int a(String string1, String[] stringArray2) {
      this.ba = 0;
      if (string1 != null && !string1.isEmpty()) {
         int intValue3 = -1;

         for (String string7 : stringArray2) {
            if (string7 != null && !string7.isEmpty()) {
               int intValue8 = a(string1, string7);
               if (intValue8 >= 0 && (intValue3 == -1 || intValue8 < intValue3 || intValue8 == intValue3 && string7.length() > this.ba)) {
                  intValue3 = intValue8;
                  this.ba = string7.length();
               }
            }
         }

         return intValue3;
      } else {
         return -1;
      }
   }

   private static int a(String string0, String string1) {
      int intValue2 = string0.length() - string1.length();

      for (int intValue3 = 0; intValue3 <= intValue2; intValue3++) {
         if (string0.regionMatches(true, intValue3, string1, 0, string1.length())) {
            return intValue3;
         }
      }

      return -1;
   }

   private String e(int intValue1) {
      if (intValue1 >= 0 && intValue1 < 600) {
         String string2 = this.aU[intValue1];
         if (string2 == null) {
            string2 = String.valueOf(intValue1);
            this.aU[intValue1] = string2;
         }

         return string2;
      } else {
         return String.valueOf(intValue1);
      }
   }

   private String f(int intValue1) {
      if (intValue1 >= 0 && intValue1 < 600) {
         String string2 = this.aV[intValue1];
         if (string2 == null) {
            string2 = "×" + intValue1;
            this.aV[intValue1] = string2;
         }

         return string2;
      } else {
         return "×" + intValue1;
      }
   }

   private String a(ItemStack itemStack1) {
      if (itemStack1 != null && !itemStack1.isEmpty()) {
         ItemEnchantmentsComponent itemEnchantmentsComponent2 = (ItemEnchantmentsComponent)itemStack1.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
         if (itemEnchantmentsComponent2.isEmpty()) {
            return null;
         } else {
            for (String[] stringArray6 : aq) {
               for (RegistryEntry registryEntry8 : itemEnchantmentsComponent2.getEnchantments()) {
                  Identifier identifier9 = ((Optional<RegistryKey<?>>) (Object) registryEntry8.getKey()).map(RegistryKey::getValue).orElse(null);
                  if (identifier9 != null && identifier9.getPath().equals(stringArray6[0])) {
                     return stringArray6[1] + itemEnchantmentsComponent2.getLevel(registryEntry8);
                  }
               }
            }

            Iterator iterator10 = itemEnchantmentsComponent2.getEnchantments().iterator();
            if (iterator10.hasNext()) {
               RegistryEntry registryEntry11 = (RegistryEntry)iterator10.next();
               Identifier identifier12 = ((Optional<RegistryKey<?>>) (Object) registryEntry11.getKey()).map(RegistryKey::getValue).orElse(null);
               String string13 = identifier12 != null ? identifier12.getPath() : "?";
               return string13.substring(0, Math.min(2, string13.length())).toUpperCase() + itemEnchantmentsComponent2.getLevel(registryEntry11);
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private int b(ItemStack itemStack1) {
      Text text2 = itemStack1.getName();
      if (text2 != null) {
         int[] intValueArray3 = new int[]{0};
         boolean[] flagArray4 = new boolean[]{false};
         text2.visit((style2x, string3x) -> {
            if (!flagArray4[0] && style2x != null && style2x.getColor() != null) {
               intValueArray3[0] = 0xFF000000 | style2x.getColor().getRgb();
               flagArray4[0] = true;
            }

            return flagArray4[0] ? Optional.of(string3x) : Optional.empty();
         }, Style.EMPTY);
         if (flagArray4[0]) {
            return intValueArray3[0];
         }
      }
      return switch (itemStack1.getRarity()) {
         case UNCOMMON -> -171;
         case RARE -> -11141121;
         case EPIC -> -43521;
         case COMMON -> -1;
         default -> throw new MatchException(null, null);
      };
   }

   private static float a(LivingEntity livingEntity0) {
      float floatValue1 = Math.max(0.0F, livingEntity0.getAbsorptionAmount());
      return MathHelper.clamp(a(livingEntity0, floatValue1) / Math.max(1.0F, livingEntity0.getMaxHealth()), 0.0F, 1.0F);
   }

   private static int a(float floatValue0) {
      if (floatValue0 > 0.66F) {
         return -8588646;
      } else {
         return floatValue0 >= 0.33F ? -4516 : -38037;
      }
   }

   private static int c(PlayerEntity playerEntity0) {
      if (playerEntity0.hurtTime <= 0) {
         return -1;
      } else {
         float floatValue1 = MathHelper.clamp((float)playerEntity0.hurtTime / 10.0F, 0.0F, 1.0F);
         int intValue2 = Math.round(255.0F * (1.0F - 0.45F * floatValue1));
         return -65536 | intValue2 << 8 | intValue2;
      }
   }

   private float a(dev.leg1tka.render.font.MsdfTextRenderer value1, float floatValue2) {
      float floatValue3 = value1.d() * 0.5F;
      float floatValue4 = this.b(floatValue2 + 0.7F * floatValue3 * 0.5F);
      return floatValue4 - value1.c().g() * floatValue3 + 2.5F;
   }

   private float b(float floatValue1) {
      return (float)Math.round(floatValue1 * this.aO) / this.aO;
   }

   private float a(long longValue1) {
      return (float)((longValue1 - this.aP) % 3600000000000L) / 1.0E9F;
   }

   private boolean d(Entity entity1) {
      double doubleValue2 = MathHelper.lerp((double)this.aD, entity1.lastRenderX, entity1.getX()) - entity1.getX();
      double doubleValue4 = MathHelper.lerp((double)this.aD, entity1.lastRenderY, entity1.getY()) - entity1.getY();
      double doubleValue6 = MathHelper.lerp((double)this.aD, entity1.lastRenderZ, entity1.getZ()) - entity1.getZ();
      Box box8 = entity1.getBoundingBox();
      double doubleValue9 = box8.minX + doubleValue2 - 0.05;
      double doubleValue11 = box8.minY + doubleValue4 - 0.05;
      double doubleValue13 = box8.minZ + doubleValue6 - 0.05;
      double doubleValue15 = box8.maxX + doubleValue2 + 0.05;
      double doubleValue17 = box8.maxY + doubleValue4 + 0.05;
      double doubleValue19 = box8.maxZ + doubleValue6 + 0.05;
      float floatValue21 = Float.POSITIVE_INFINITY;
      float floatValue22 = Float.POSITIVE_INFINITY;
      float floatValue23 = Float.NEGATIVE_INFINITY;
      float floatValue24 = Float.NEGATIVE_INFINITY;
      boolean flag25 = false;

      for (int intValue26 = 0; intValue26 < 8; intValue26++) {
         if (this.a((intValue26 & 1) == 0 ? doubleValue9 : doubleValue15, (intValue26 & 2) == 0 ? doubleValue11 : doubleValue17, (intValue26 & 4) == 0 ? doubleValue13 : doubleValue19, this.aJ)) {
            flag25 = true;
            floatValue21 = Math.min(floatValue21, this.aJ.a);
            floatValue22 = Math.min(floatValue22, this.aJ.b);
            floatValue23 = Math.max(floatValue23, this.aJ.a);
            floatValue24 = Math.max(floatValue24, this.aJ.b);
         }
      }

      if (!flag25) {
         return false;
      } else if (floatValue21 > (float)(this.aE + 300) || floatValue23 < -300.0F) {
         return false;
      } else if (floatValue22 > (float)(this.aF + 300) || floatValue24 < -300.0F) {
         return false;
      } else if (!(floatValue23 - floatValue21 < 2.0F) && !(floatValue24 - floatValue22 < 2.0F)) {
         this.aK = floatValue21;
         this.aL = floatValue22;
         this.aM = floatValue23;
         this.aN = floatValue24;
         return true;
      } else {
         return false;
      }
   }

   private boolean a(double doubleValue1, double doubleValue3, double doubleValue5, EntityESP.h value7) {
      this.aH.set((float)(doubleValue1 - this.aC.x), (float)(doubleValue3 - this.aC.y), (float)(doubleValue5 - this.aC.z));
      this.aH.rotate(this.aB);
      this.aI.set(this.aH.x, this.aH.y, this.aH.z, 1.0F);
      this.aA.transform(this.aI);
      float floatValue8 = this.aI.w;
      if (floatValue8 <= 1.0E-5F) {
         return false;
      } else {
         float floatValue9 = (this.aI.x / floatValue8 * 0.5F + 0.5F) * (float)this.aE;
         float floatValue10 = (0.5F - this.aI.y / floatValue8 * 0.5F) * (float)this.aF;
         if (Float.isFinite(floatValue9) && Float.isFinite(floatValue10)) {
            value7.a = floatValue9;
            value7.b = floatValue10;
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean a(Entity entity1, double doubleValue2, EntityESP.h value4) {
      double doubleValue5 = MathHelper.lerp((double)this.aD, entity1.lastRenderX, entity1.getX());
      double doubleValue7 = MathHelper.lerp((double)this.aD, entity1.lastRenderY, entity1.getY()) + doubleValue2;
      double doubleValue9 = MathHelper.lerp((double)this.aD, entity1.lastRenderZ, entity1.getZ());
      return this.a(doubleValue5, doubleValue7, doubleValue9, value4);
   }

   private boolean w() {
      return minecraftClient.gameRenderer != null && !minecraftClient.gameRenderer.getCamera().isThirdPerson();
   }

   private boolean b(LivingEntity livingEntity1) {
      if (livingEntity1.isRemoved() || livingEntity1 instanceof ArmorStandEntity || !livingEntity1.isAlive()) {
         return false;
      } else if (e(livingEntity1)) {
         return this.au.getValue();
      } else {
         return livingEntity1 instanceof MobEntity && !(livingEntity1 instanceof PlayerEntity) ? this.at.getValue() : false;
      }
   }

   private boolean d(PlayerEntity playerEntity1) {
      return !this.as.getValue() || playerEntity1 == null || !playerEntity1.isAlive() ? false : playerEntity1 != minecraftClient.player || !this.w();
   }

   private static boolean e(Entity entity0) {
      return entity0 instanceof AnimalEntity || entity0 instanceof WaterAnimalEntity || entity0 instanceof AmbientEntity;
   }

   private static long a(int intValue0, int intValue1, int intValue2) {
      return (long)(intValue0 & 16777215) << 40 | (long)(intValue2 & 16777215) << 16 | (long)intValue1 & 65535L;
   }

   static {
      for (int intValue0 = 0; intValue0 < ad.length; intValue0++) {
         ad[intValue0] = "AIRSTUCK - " + intValue0 / 10 + "." + intValue0 % 10 + "s";
      }

      aq = new String[][]{
         {"protection", "P"},
         {"blast_protection", "B"},
         {"fire_protection", "F"},
         {"projectile_protection", "Pr"},
         {"feather_falling", "FF"},
         {"thorns", "T"},
         {"depth_strider", "D"},
         {"respiration", "R"},
         {"aqua_affinity", "A"},
         {"unbreaking", "U"},
         {"mending", "M"}
      };
   }

   static final class a {
      List<EntityESP.i> a = Collections.emptyList();
      List<EntityESP.i> b = Collections.emptyList();
      long c;
      long d;

      a() {
      }
   }

   static final class b {
      String a = "0";
      String b;
      float c;
      float d;
      float e;
      float f;
      float g;
      float h;
      int i = -8588646;
      float j;

      b() {
      }
   }

   static final class c {
      ItemRenderState[] a = new ItemRenderState[64];
      float[] b = new float[64];
      float[] c = new float[64];
      int d;

      c() {
      }

      void a(ItemRenderState itemRenderState1, float floatValue2, float floatValue3) {
         if (itemRenderState1 != null && !itemRenderState1.isEmpty()) {
            if (this.d == this.a.length) {
               int intValue4 = this.d * 2;
               this.a = Arrays.copyOf(this.a, intValue4);
               this.b = Arrays.copyOf(this.b, intValue4);
               this.c = Arrays.copyOf(this.c, intValue4);
            }

            this.a[this.d] = itemRenderState1;
            this.b[this.d] = floatValue2;
            this.c[this.d] = floatValue3;
            this.d++;
         }
      }

      void a() {
         Arrays.fill(this.a, 0, this.d, null);
         this.d = 0;
      }
   }

   static class d {
      ItemStack a;
      int b;
      Language c;
      dev.leg1tka.render.font.MsdfTextRenderer d;
      String e;
      int f;
      String g;
      float h;
      float i;
      float j;
      float k;
      List l;
      float[] m;
      float n;
      float o;

      d() {
      }
   }

   static class e extends Immediate {
      e() {
         super(new BufferAllocator(1536), new LinkedHashMap());
      }

      void a(RenderLayer renderLayer1) {
         if (!this.layerBuffers.containsKey(renderLayer1)) {
            this.layerBuffers.put(renderLayer1, new BufferAllocator(renderLayer1.getExpectedBufferSize()));
         }
      }

      public VertexConsumer getBuffer(RenderLayer renderLayer1) {
         this.a(renderLayer1);
         return super.getBuffer(renderLayer1);
      }

      void a() {
         this.draw();

         for (BufferAllocator bufferAllocator2 : this.layerBuffers.values()) {
            bufferAllocator2.close();
         }

         this.layerBuffers.clear();
         this.allocator.close();
      }
   }

   static class f {
      LivingEntity a;
      long b;
      dev.leg1tka.render.font.MsdfTextRenderer c;
      String d;
      float e;
      float f;
      final EntityESP.b g = new EntityESP.b();
      float h;

      f() {
      }
   }

   static class g {
      PlayerEntity a;
      boolean b;
      float c;
      float d = -1.0F;
      long e;
      int f;
      dev.leg1tka.render.font.MsdfTextRenderer g;
      dev.leg1tka.render.font.MsdfTextRenderer h;
      boolean i;
      Identifier j;
      List<EntityESP.i> k = Collections.emptyList();
      List<EntityESP.i> l = Collections.emptyList();
      float[] m = new float[8];
      float[] n = new float[8];
      String o;
      float p;
      float q;
      float r;
      final EntityESP.b s = new EntityESP.b();
      float t;
      final ItemStack[] u = new ItemStack[4];
      final ItemStack[] v = new ItemStack[6];
      final ItemRenderState[] w = new ItemRenderState[6];
      final float[] x = new float[6];
      final int[] y = new int[6];
      int z;
      int A;
      float B;
      ItemStack C = ItemStack.EMPTY;
      ItemStack D = ItemStack.EMPTY;
      final String[] E = new String[4];
      final float[] F = new float[4];
      boolean G;
      final String[] H = new String[2];
      final float[] I = new float[2];
      final float[] J = new float[2];
      final int[] K = new int[2];
      int L;

      g() {
      }
   }

   static class h {
      float a;
      float b;

      h() {
      }
   }

   static record i(String text, int color)  {

}

   static class j {
      ItemStack a;
      int b;
      Text c;
      int d;

      j() {
      }
   }

   static class k {
      Entity a;
      long b;
      boolean c;
      boolean d;
      boolean e;
      Text f = Text.empty();
      int g;
      Text h;
      String i;
      long j = -1L;
      boolean k;
      Text l = Text.empty();
      int m;
      int n;
      int o;
      int p;
      final ItemStack[] q = new ItemStack[4];
      final ItemStack[] r = new ItemStack[6];
      final ItemStack[] s = new ItemStack[6];
      final int[] t = new int[6];
      final int[] u = new int[6];
      final int[] v = new int[6];
      final boolean[] w = new boolean[6];
      final boolean[] x = new boolean[6];
      final int[] y = new int[6];
      final int[] z = new int[6];
      final String[] A = new String[6];
      final int[] B = new int[6];
      final String[] C = new String[4];
      final int[] D = new int[4];
      int E;
      int F;
      float G;
      final Text[] H = new Text[2];
      final int[] I = new int[2];
      final ItemStack[] J = new ItemStack[2];
      final int[] K = new int[2];
      int L;

      k() {
      }

      void a() {
         this.h = null;
         this.i = null;
         this.j = -1L;
         this.n = Integer.MIN_VALUE;
         this.o = Integer.MIN_VALUE;
         this.p = 0;
         this.E = 0;
         this.F = 0;
         this.L = 0;
         Arrays.fill(this.s, null);
         Arrays.fill(this.v, -1);
         Arrays.fill(this.w, false);
         Arrays.fill(this.J, null);
         Arrays.fill(this.H, null);
      }
   }
}
