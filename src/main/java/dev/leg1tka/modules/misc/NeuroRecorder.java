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
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventUpdatePost;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Locale;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.item.SwordItem;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
public class NeuroRecorder extends Module {
   public static NeuroRecorder c;
   private final ModeSetting d = new ModeSetting(
      "Противник",
      "Ходячий бот",
      "Игрок",
      "Ходячий бот",
      "Стоячий бот"
   );
   private final NumberSetting e = new NumberSetting(
      "Конец боя без ударов (с)", 3.0F, 1.0F, 10.0F, 0.5F
   );
   private final NumberSetting f = new NumberSetting("Макс. дистанция", 8.0F, 4.0F, 16.0F, 0.5F);
   private final NumberSetting g = new NumberSetting("Подход до удара (с)", 3.0F, 0.0F, 5.0F, 0.5F);
   private final BooleanSetting h = new BooleanSetting("Сообщения в чат", true);
   private static String[] i;
   private static int j;
   private static int k;
   private BufferedWriter l;
   private File m;
   private PlayerEntity n;
   private int o;
   private int p;
   private int q;
   private int r;
   private int s = 1000;
   private boolean t;
   private boolean u;
   private float v;
   private int w;
   private int x;
   private boolean y;
   private float z;
   private float A;
   private Vec3d B;
   private int C = -1;
   private Vec3d D;
   private int E;
   private Object F;
   private final ArrayDeque<double[]> G = new ArrayDeque<>();
   private int H = -1;
   private final StringBuilder I = new StringBuilder(768);

   public NeuroRecorder() {
      super(
         "NeuroRecorder",
         "Запись PvP-дуэлей для обучения ротации",
         Category.MISC
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g, this.h});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.n = null;
      this.o = 0;
      this.x = 0;
      this.y = false;
      this.C = -1;
      this.B = null;
      this.G.clear();

      try {
         File file1 = new File(minecraftClient.runDirectory, "aspectdlc/neuro");
         file1.mkdirs();
         String string2 = createString173(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
         this.m = new File(file1, string2);
         this.l = new BufferedWriter(new FileWriter(this.m));
         this.l.write(String.join(",", i));
         this.l.newLine();
         this.d(createString174(string2, this.d.g()));
      } catch (IOException iOException3) {
         this.l = null;
         this.d(createString175(iOException3.getMessage()));
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.c("модуль выключен");
      if (this.l != null) {
         try {
            this.l.close();
         } catch (IOException iOException2) {
         }

         this.l = null;
         this.d(createString176(this.m != null ? this.m.getName() : "?", this.o, this.x));
      }
   }

   @EventLink
   public void onAttack(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player == null) return;
      if (eventAttackEntity1.getPlayer() == minecraftClient.player && eventAttackEntity1.getTarget() instanceof PlayerEntity targetPlayer) {
         if (this.n == null) {
            this.a(targetPlayer);
         }
         this.y = true;
         this.q = (int)(this.e.e().floatValue() * 20.0F);
      }
   }

   @EventLink
   public void onTickEnd(EventUpdatePost eventUpdatePost1) {
      if (minecraftClient.player == null || minecraftClient.world == null || this.l == null) return;
      if (this.n != null) {
         if (this.n.isRemoved() || !this.n.isAlive()) {
            this.c("цель мертва");
            return;
         }
         if (minecraftClient.player.distanceTo(this.n) > this.f.e().floatValue()) {
            this.c("дистанция превышена");
            return;
         }
         if (--this.q <= 0) {
            this.c("таймаут боя");
            return;
         }
         Vec3d playerVel = minecraftClient.player.getVelocity();
         Vec3d targetVel = this.n.getVelocity();
         double[] row = this.a(this.n, minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), 0.0F, 0.0F, playerVel, targetVel, this.y, false);
         row[0] = (double)this.o;
         row[1] = (double)(this.p++);
         this.a(row);
         this.y = false;
      }
   }

   private double[] a(PlayerEntity playerEntity1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, Vec3d vec3d6, Vec3d vec3d7, boolean flag8, boolean flag9) {
      Vec3d vec3d10 = minecraftClient.player.getEyePos();
      Box box11 = playerEntity1.getBoundingBox();
      Vec3d vec3d12 = box11.getCenter();
      Vec3d vec3d13 = Vec3d.fromPolar(floatValue3, floatValue2);
      Vec3d vec3d14 = a(box11, vec3d10, vec3d13);
      boolean flag15 = box11.contains(vec3d10) || box11.raycast(vec3d10, vec3d10.add(vec3d13.multiply(16.0))).isPresent();
      float[] floatValueArray16 = a(vec3d10, vec3d12);
      float[] floatValueArray17 = a(vec3d10, vec3d14);
      Vec3d vec3d18 = vec3d12.subtract(vec3d10);
      double doubleValue19 = vec3d18.length();
      double doubleValue21 = Math.max(0.05, Math.sqrt(vec3d18.x * vec3d18.x + vec3d18.z * vec3d18.z));
      float floatValue23 = (float)Math.toDegrees(2.0 * Math.atan2(box11.getLengthX() * 0.5, doubleValue21));
      float floatValue24 = (float)Math.toDegrees(2.0 * Math.atan2(box11.getLengthY() * 0.5, Math.max(0.05, doubleValue19)));
      float[] floatValueArray25 = a(playerEntity1.getEyePos(), vec3d10);
      float floatValue26 = MathHelper.wrapDegrees(playerEntity1.getYaw() - floatValueArray25[0]);
      AttackAura attackAura27 = AttackAura.c;
      boolean flag28 = attackAura27 != null && attackAura27.isEnabled();
      boolean flag29 = flag28 && attackAura27.e.b("NoRotate");
      int intValue30 = !flag28 ? 0 : (flag29 ? 1 : 2);
      boolean flag31 = flag28 && (!flag29 || minecraftClient.player.isGliding()) || !flag28 && dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.b();
      Item item32 = minecraftClient.player.getMainHandStack().getItem();
      int intValue33 = item32 instanceof SwordItem ? 1 : (item32 instanceof AxeItem ? 2 : (item32 instanceof MaceItem ? 3 : 0));
      int intValue34 = 0;
      if (minecraftClient.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry35 = minecraftClient.getNetworkHandler().getPlayerListEntry(minecraftClient.player.getUuid());
         if (playerListEntry35 != null) {
            intValue34 = playerListEntry35.getLatency();
         }
      }

      return new double[]{
         0.0,
         0.0,
         this.d.b("Игрок") ? 0.0 : (this.d.b("Ходячий бот") ? 1.0 : 2.0),
         (Double)minecraftClient.options.getMouseSensitivity().getValue(),
         dev.leg1tka.rotation.RotationContext.a(),
         (double)((Integer)minecraftClient.options.getFov().getValue()).intValue(),
         (double)intValue34,
         (double)minecraftClient.getCurrentFps(),
         (double)floatValue2,
         (double)floatValue3,
         (double)floatValue4,
         (double)floatValue5,
         (double)minecraftClient.player.getYaw(),
         (double)minecraftClient.player.getPitch(),
         (double)floatValueArray16[0],
         (double)floatValueArray16[1],
         (double)floatValueArray17[0],
         (double)floatValueArray17[1],
         flag15 ? 1.0 : 0.0,
         vec3d18.x,
         vec3d18.y,
         vec3d18.z,
         doubleValue19,
         (double)floatValue23,
         (double)floatValue24,
         vec3d6.x,
         vec3d6.y,
         vec3d6.z,
         vec3d7.x,
         vec3d7.y,
         vec3d7.z,
         (double)e(minecraftClient.player.isOnGround()),
         (double)e(minecraftClient.player.isSprinting()),
         (double)e(minecraftClient.player.isSneaking()),
         (double)e(minecraftClient.options.forwardKey.isPressed()),
         (double)e(minecraftClient.options.backKey.isPressed()),
         (double)e(minecraftClient.options.leftKey.isPressed()),
         (double)e(minecraftClient.options.rightKey.isPressed()),
         (double)e(minecraftClient.options.jumpKey.isPressed()),
         (double)e(playerEntity1.isOnGround()),
         (double)floatValue26,
         (double)e(playerEntity1.isBlocking()),
         (double)e(minecraftClient.player.canSee(playerEntity1)),
         (double)intValue33,
         (double)minecraftClient.player.getAttackCooldownProgress(0.0F),
         this.t ? (double)this.v : -1.0,
         (double)this.s,
         (double)e(this.t),
         (double)e(this.u),
         (double)e(flag8),
         (double)playerEntity1.hurtTime,
         (double)minecraftClient.player.hurtTime,
         (double)intValue30,
         (double)e(flag31),
         (double)e(flag9),
         (double)e(minecraftClient.currentScreen != null)
      };
   }

   private void a(double[] doubleValueArray1) {
      this.I.setLength(0);

      for (int intValue2 = 0; intValue2 < doubleValueArray1.length; intValue2++) {
         if (intValue2 > 0) {
            this.I.append(',');
         }

         double doubleValue3 = doubleValueArray1[intValue2];
         if (doubleValue3 == Math.rint(doubleValue3) && Math.abs(doubleValue3) < 1.0E9) {
            this.I.append((long)doubleValue3);
         } else {
            this.I.append(String.format(Locale.ROOT, "%.5f", doubleValue3));
         }
      }

      try {
         this.l.write(this.I.toString());
         this.l.newLine();
         this.x++;
      } catch (IOException iOException5) {
         this.d(createString177(iOException5.getMessage()));
      }
   }

   private void a(PlayerEntity playerEntity1) {
      this.n = playerEntity1;
      this.o++;
      this.q = this.r;
      int intValue2 = this.H == playerEntity1.getId() ? this.G.size() : 0;
      this.p = -intValue2;
      if (intValue2 > 0) {
         for (double[] doubleValueArray4 : this.G) {
            doubleValueArray4[0] = (double)this.o;
            doubleValueArray4[1] = (double)(this.p++);
            this.a(doubleValueArray4);
         }
      }

      this.G.clear();
      this.H = -1;
      this.d(createString179(this.o, intValue2 > 0 ? createString178(intValue2) : ""));
   }

   private void c(String string1) {
      if (this.n != null) {
         this.d(createString180(this.o, string1, Math.max(0, this.p)));
         this.n = null;

         try {
            if (this.l != null) {
               this.l.flush();
            }
         } catch (IOException iOException3) {
         }
      }
   }

   private PlayerEntity a(double doubleValue1) {
      PlayerEntity playerEntity3 = null;
      double doubleValue4 = doubleValue1 * doubleValue1;

      for (PlayerEntity playerEntity7 : minecraftClient.world.getPlayers()) {
         if (playerEntity7 != minecraftClient.player && playerEntity7.isAlive()) {
            double doubleValue8 = playerEntity7.squaredDistanceTo(minecraftClient.player);
            if (doubleValue8 < doubleValue4) {
               doubleValue4 = doubleValue8;
               playerEntity3 = playerEntity7;
            }
         }
      }

      return playerEntity3;
   }

   private static int e(boolean flag0) {
      return flag0 ? 1 : 0;
   }

   private static float[] a(Vec3d vec3d0, Vec3d vec3d1) {
      double doubleValue2 = vec3d1.x - vec3d0.x;
      double doubleValue4 = vec3d1.y - vec3d0.y;
      double doubleValue6 = vec3d1.z - vec3d0.z;
      double doubleValue8 = Math.sqrt(doubleValue2 * doubleValue2 + doubleValue6 * doubleValue6);
      float floatValue10 = (float)(Math.toDegrees(Math.atan2(doubleValue6, doubleValue2)) - 90.0);
      float floatValue11 = (float)(-Math.toDegrees(Math.atan2(doubleValue4, doubleValue8)));
      return new float[]{floatValue10, floatValue11};
   }

   private static Vec3d a(Box box0, Vec3d vec3d1, Vec3d vec3d2) {
      Vec3d vec3d3 = box0.getCenter();

      for (int intValue4 = 0; intValue4 < 3; intValue4++) {
         double doubleValue5 = Math.max(0.0, vec3d3.subtract(vec3d1).dotProduct(vec3d2));
         Vec3d vec3d7 = vec3d1.add(vec3d2.multiply(doubleValue5));
         vec3d3 = new Vec3d(
            MathHelper.clamp(vec3d7.x, box0.minX, box0.maxX), MathHelper.clamp(vec3d7.y, box0.minY, box0.maxY), MathHelper.clamp(vec3d7.z, box0.minZ, box0.maxZ)
         );
      }

      return vec3d3;
   }

   private void d(String string1) {
      if (this.h.getValue() && minecraftClient.player != null) {
         minecraftClient.player.sendMessage(Text.literal(createString181(string1)), false);
      }
   }



   private static String createString173(String string0) {
      return "session_" + string0 + ".csv";
   }

   private static String createString174(String string0, String string1) {
      return "§aЗапись включена: §f"
         + string0
         + " §7(противник: "
         + string1
         + ")";
   }

   private static String createString175(String string0) {
      return "§cНе удалось создать файл записи: "
         + string0;
   }

   private static String createString176(String string0, int intValue1, int intValue2) {
      return "§aЗапись сохранена: §f"
         + string0
         + " §7("
         + intValue1
         + " боёв, "
         + intValue2
         + " тиков)";
   }

   private static String createString177(String string0) {
      return "§cОшибка записи: " + string0;
   }

   private static String createString178(int intValue0) {
      return " §8(подход " + intValue0 + " тиков)";
   }

   private static String createString179(int intValue0, String string1) {
      return "§7Бой §f#" + intValue0 + " §7начат" + string1;
   }

   private static String createString180(int intValue0, String string1, int intValue2) {
      return "§7Бой §f#"
         + intValue0
         + " §7закончен ("
         + string1
         + "), тиков: §f"
         + intValue2;
   }

   private static String createString181(String string0) {
      return "§d[Neuro] §r" + string0;
   }
   public static void dev_leg1tka_o_io_initializer() {}
}
