package dev.leg1tka.gui;

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
import dev.leg1tka.AspectDLC;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PlayerSkinWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;

public final class AltManagerScreen extends Screen {
   private static int a;
   private static int b;
   private static int c;
   private static final DateTimeFormatter d = DateTimeFormatter.ofPattern("d MMMM", Locale.forLanguageTag("ru"));
   private static String e;
   private static final String[] f = new String[]{
      "Недавние", "По имени", "Новые"
   };
   private static final String[] g = new String[]{"1 ч", "1 д", "7 д", "30 д"};
   private static final int[][] h = new int[][]{{0, 1, 0}, {1, 0, 0}, {7, 0, 0}, {30, 0, 0}};
   private static final int[] i = new int[]{365, 23, 59};
   private static final String[] j = new String[]{"д", "ч", "м"};
   private static boolean k = false;
   private final Screen l;
   private final dev.leg1tka.gui.components.MenuIconButton m = new dev.leg1tka.gui.components.MenuIconButton("Поиск по нику", 16, AltManagerScreen::a).a("l");
   private final dev.leg1tka.gui.components.MenuIconButton n = new dev.leg1tka.gui.components.MenuIconButton("Новый ник", 16, AltManagerScreen::a);
   private final dev.leg1tka.gui.components.MenuIconButton o = new dev.leg1tka.gui.components.MenuIconButton("Новый ник", 16, AltManagerScreen::a);
   private final dev.leg1tka.gui.components.MenuIconButton p = new dev.leg1tka.gui.components.MenuIconButton("mc.server.ru", 64, AltManagerScreen::b);
   private final dev.leg1tka.gui.components.MenuIconButton q = new dev.leg1tka.gui.components.MenuIconButton(
      "Причина, если хочешь", 48, intValue0 -> intValue0 >= 32
   );
   private PlayerSkinWidget r;
   private dev.leg1tka.accounts.AltAccount s = null;
   private boolean t = false;
   private int u = 0;
   private int v = 2;
   private float w = 0.0F;
   private float x = 0.0F;
   private long y = 0L;
   private long z = 0L;
   private long A = 0L;
   private long B = 0L;
   private long C = 0L;
   private dev.leg1tka.accounts.AltAccount D = null;
   private String E = "";
   private long F = 0L;
   private String G = null;
   private long H = 0L;
   private final Random I = new Random();
   private dev.leg1tka.accounts.AltAccount J = null;
   private float K;
   private float L;
   private float M = 0.0F;
   private final List<AltManagerScreen.a> N = new ArrayList<>();
   private dev.leg1tka.accounts.AltAccount O = null;
   private float P = 0.0F;
   private boolean Q = false;
   private final int[] R = new int[]{7, 0, 0};
   private int S = -1;
   private String T = "";
   private final List<String> U = new ArrayList<>();
   private String V = "";
   private final HashMap<String, Float> W = new HashMap<>();
   private final HashMap X = new HashMap();
   private final List<AltManagerScreen.b> Y = new ArrayList<>();
   private List Z = new ArrayList();
   private float ModuleStorage;
   private float ab;
   private float ac;
   private float ad;
   private float ae;
   private float af;
   private float ag;
   private float ah;
   private float Storage;
   private float ModuleManager;
   private float ModuleRegistry;
   private float al;
   private float am;
   private float an;
   private float ao;

   public AltManagerScreen(Screen screen1) {
      super(Text.literal("Менеджер аккаунтов"));
      this.l = screen1;
   }

   private static boolean a(int intValue0) {
      return intValue0 >= 97 && intValue0 <= 122 || intValue0 >= 65 && intValue0 <= 90 || intValue0 >= 48 && intValue0 <= 57 || intValue0 == 95;
   }

   private static boolean b(int intValue0) {
      return a(intValue0) || intValue0 == 46 || intValue0 == 45 || intValue0 == 58;
   }

   private List<dev.leg1tka.accounts.AltAccount> a() {
      return AspectDLC.INSTANCE.altStorage != null ? AspectDLC.INSTANCE.altStorage.c() : Collections.emptyList();
   }

   private void b() {
      if (AspectDLC.INSTANCE.altStorage != null) {
         AspectDLC.INSTANCE.altStorage.a();
      }
   }

   private String c() {
      return this.client != null ? this.client.getSession().getUsername() : "";
   }

   private boolean a(dev.leg1tka.accounts.AltAccount value1) {
      return value1 != null && value1.c().equalsIgnoreCase(this.c());
   }

   private dev.leg1tka.accounts.AltAccount a(String string1) {
      for (dev.leg1tka.accounts.AltAccount value3 : this.a()) {
         if (value3.c().equalsIgnoreCase(string1)) {
            return value3;
         }
      }

      return null;
   }

   private String d() {
      return this.s != null ? this.s.c() : this.c();
   }

   private List<dev.leg1tka.accounts.AltAccount> e() {
      String string1 = this.m.a().trim().toLowerCase(Locale.ROOT);
      List<dev.leg1tka.accounts.AltAccount> list2 = new ArrayList<>();

      for (dev.leg1tka.accounts.AltAccount value4 : this.a()) {
         if ((this.u != 1 || value4.h()) && (this.u != 2 || value4.b()) && (string1.isEmpty() || value4.c().toLowerCase(Locale.ROOT).contains(string1))) {
            list2.add(value4);
         }
      }

      list2.sort((value1x, value2x) -> {
         if (value1x.h() != value2x.h()) {
            return value1x.h() ? -1 : 1;
         } else if (this.v == 1) {
            return value1x.c().compareToIgnoreCase(value2x.c());
         } else {
            return this.v == 2 ? Long.compare(value2x.g(), value1x.g()) : Long.compare(Math.max(value2x.i(), value2x.g()), Math.max(value1x.i(), value1x.g()));
         }
      });
      return list2;
   }

   private static String a(long longValue0) {
      if (longValue0 <= 0L) {
         return "никогда";
      } else {
         long longValue2 = (System.currentTimeMillis() - longValue0) / 1000L;
         if (longValue2 < 60L) {
            return "только что";
         } else if (longValue2 < 3600L) {
            return longValue2 / 60L + " мин назад";
         } else if (longValue2 < 86400L) {
            return longValue2 / 3600L + " ч назад";
         } else {
            long longValue4 = longValue2 / 86400L;
            if (longValue4 == 1L) {
               return "вчера";
            } else {
               return longValue4 < 30L ? longValue4 + " дн назад" : b(longValue0);
            }
         }
      }
   }

   private static String b(long longValue0) {
      return Instant.ofEpochMilli(longValue0).atZone(ZoneId.systemDefault()).format(d);
   }

   private static String b(dev.leg1tka.accounts.AltAccount value0) {
      List list1 = value0.a();
      if (list1.size() > 1) {
         return "бан на " + list1.size() + " серверах";
      } else {
         dev.leg1tka.accounts.AccountBan value2 = (dev.leg1tka.accounts.AccountBan)list1.get(0);
         return "бан " + dev.leg1tka.accounts.ServerReconnectTracker.a(value2.e()) + ", " + (value2.a() ? "навсегда" : dev.leg1tka.accounts.ServerReconnectTracker.a(value2.c()));
      }
   }

   private void a(String string1, int intValue2, Runnable runnable3) {
      this.Y.add(new AltManagerScreen.b(string1, intValue2, runnable3));

      while (this.Y.size() > 3) {
         this.Y.remove(0);
      }
   }

   private void b(String string1) {
      this.E = string1;
      this.F = System.currentTimeMillis();
   }

   private float a(String string1, boolean flag2, float floatValue3) {
      float floatValue4 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.W.getOrDefault(string1, 0.0F), flag2 ? 1.0F : 0.0F, floatValue3, 14.0F);
      this.W.put(string1, floatValue4);
      return floatValue4;
   }

   private boolean a(double doubleValue1, double doubleValue3, float floatValue5, float floatValue6, float floatValue7, float floatValue8) {
      return doubleValue1 >= (double)floatValue5 && doubleValue1 <= (double)(floatValue5 + floatValue7) && doubleValue3 >= (double)floatValue6 && doubleValue3 <= (double)(floatValue6 + floatValue8);
   }

   private boolean a(String string1, double doubleValue2, double doubleValue4) {
      float[] floatValueArray6 = (float[])this.X.get(string1);
      return floatValueArray6 != null && this.a(doubleValue2, doubleValue4, floatValueArray6[0], floatValueArray6[1], floatValueArray6[2], floatValueArray6[3]);
   }

   protected void init() {
      dev.leg1tka.gui.menu.MenuLayout.a();
      this.y = System.currentTimeMillis();
      this.v = 2;
      if (this.client != null) {
         this.r = new PlayerSkinWidget(40, 80, this.client.getLoadedEntityModels(), () -> dev.leg1tka.accounts.AccountProfileResolver.b(this.d()));
         this.addSelectableChild(this.r);
      }

      this.f();
      if (this.s == null || !this.a().contains(this.s)) {
         this.s = this.a(this.c());
         if (this.s == null && !this.a().isEmpty()) {
            this.s = this.e().isEmpty() ? null : this.e().get(0);
         }
      }
   }

   private void f() {
      for (String string2 : dev.leg1tka.accounts.ServerReconnectTracker.c()) {
         this.a("Разбанен " + string2, 4906624, null);
      }

      this.C = System.currentTimeMillis();
   }

   public void close() {
      if (this.client != null) {
         this.client.setScreen(this.l);
      }
   }

   private void a(float floatValue1, float floatValue2, float floatValue3) {
      this.ae = dev.leg1tka.gui.menu.MenuLayout.a(22.0F);
      this.ac = Math.min(floatValue1 - dev.leg1tka.gui.menu.MenuLayout.a(48.0F), dev.leg1tka.gui.menu.MenuLayout.a(900.0F));
      this.ad = Math.min(floatValue2 - dev.leg1tka.gui.menu.MenuLayout.a(56.0F), dev.leg1tka.gui.menu.MenuLayout.a(580.0F));
      this.ModuleStorage = dev.leg1tka.gui.menu.MenuLayout.b((floatValue1 - this.ac) * 0.5F);
      this.ab = dev.leg1tka.gui.menu.MenuLayout.b((floatValue2 - this.ad) * 0.5F + (1.0F - floatValue3) * dev.leg1tka.gui.menu.MenuLayout.a(16.0F));
      this.ModuleManager = this.ab + dev.leg1tka.gui.menu.MenuLayout.a(74.0F);
      float floatValue4 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
      this.af = this.ModuleStorage + this.ae;
      this.ag = (this.ac - this.ae * 2.0F - floatValue4) * 0.58F;
      this.ah = this.af + this.ag + floatValue4;
      this.Storage = this.ModuleStorage + this.ac - this.ae - this.ah;
      this.am = dev.leg1tka.gui.menu.MenuLayout.a(44.0F);
      this.an = dev.leg1tka.gui.menu.MenuLayout.a(4.0F);
      this.ao = this.ab + this.ad - this.ae - dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
      this.ModuleRegistry = this.ModuleManager + dev.leg1tka.gui.menu.MenuLayout.a(36.0F) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F) + dev.leg1tka.gui.menu.MenuLayout.a(26.0F) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      this.al = this.ao - dev.leg1tka.gui.menu.MenuLayout.a(24.0F) - this.ModuleRegistry;
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      dev.leg1tka.gui.menu.MenuLayout.a();
      long longValue5 = System.nanoTime();
      float floatValue7 = this.z == 0L ? 0.016F : Math.min(0.05F, (float)((double)(longValue5 - this.z) / 1.0E9));
      this.z = longValue5;
      this.X.clear();
      if (System.currentTimeMillis() - this.C > 1000L) {
         this.f();
      }

      float floatValue8 = (float)this.width;
      float floatValue9 = (float)this.height;
      MatrixStack matrixStack10 = context.getMatrices();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      dev.leg1tka.gui.menu.MenuScreenRouting.c.a(context, floatValue8, floatValue9);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack10, 0.0F, 0.0F, floatValue8, floatValue9, 0.0F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F, 329226), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F, 329226));
      context.draw();
      dev.leg1tka.gui.menu.MenuBackdropRenderer.e();
      dev.leg1tka.render.RenderPrimitives.e();
      float floatValue11 = dev.leg1tka.gui.menu.MenuNavigation.b();
      float floatValue12 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)(System.currentTimeMillis() - this.y) / 420.0F);
      float floatValue13 = floatValue11 * floatValue12;
      if (!(floatValue13 < 0.02F)) {
         this.a(floatValue8, floatValue9, floatValue12);
         this.Z = this.e();
         this.i();
         this.P = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.P, this.O != null ? 1.0F : 0.0F, floatValue7, 12.0F);
         this.M = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.M, this.J != null ? 1.0F : 0.0F, floatValue7, 16.0F);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack10, this.ModuleStorage, this.ab, this.ac, this.ad, dev.leg1tka.gui.menu.MenuLayout.a(20.0F), floatValue13);
         this.a(matrixStack10, mouseX, mouseY, floatValue13, floatValue7);
         this.b(matrixStack10, mouseX, mouseY, floatValue13, floatValue7);
         boolean flag14 = this.O != null || this.J != null;
         this.a(context, matrixStack10, flag14 ? -9999 : mouseX, flag14 ? -9999 : mouseY, delta, floatValue13 * (1.0F - this.P), floatValue7);
         if (this.P > 0.01F) {
            this.c(matrixStack10, mouseX, mouseY, floatValue13 * this.P, floatValue7);
         }

         if (this.M > 0.01F && this.J != null) {
            this.a(matrixStack10, mouseX, mouseY, floatValue8, floatValue9, floatValue13 * this.M, floatValue7);
         }

         this.b(matrixStack10, mouseX, mouseY, floatValue8, floatValue9, floatValue11, floatValue7);
         context.draw();
         dev.leg1tka.render.RenderPrimitives.e();
      }
   }

   private void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      float floatValue6 = this.ab + this.ae + dev.leg1tka.gui.menu.MenuLayout.a(17.0F);
      float floatValue7 = dev.leg1tka.gui.menu.MenuLayout.a(36.0F);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, this.af, floatValue6 - floatValue7 * 0.5F, floatValue7, floatValue7, floatValue7 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue4, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue4, dev.leg1tka.gui.menu.MenuBackdropRenderer.d()), true);
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", "a", dev.leg1tka.gui.menu.MenuLayout.a(18.0F), this.af + floatValue7 * 0.5F, floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16777215));
      float floatValue8 = this.af + floatValue7 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Аккаунты", 19.0F, floatValue8, floatValue6 - dev.leg1tka.gui.menu.MenuLayout.a(7.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16777215));
      int intValue9 = this.a().size();
      int intValue10 = 0;

      for (dev.leg1tka.accounts.AltAccount value12 : this.a()) {
         if (value12.b()) {
            intValue10++;
         }
      }

      String string28 = intValue9 == 0
         ? "Пока пусто"
         : "Сохранено: " + intValue9 + (intValue10 > 0 ? ",  в бане: " + intValue10 : "");
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string28, 11.5F, floatValue8, floatValue6 + dev.leg1tka.gui.menu.MenuLayout.a(10.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
      float floatValue29 = dev.leg1tka.gui.menu.MenuLayout.a(32.0F);
      float floatValue13 = dev.leg1tka.gui.menu.MenuLayout.a(96.0F);
      float floatValue14 = this.ModuleStorage + this.ac - this.ae - floatValue13;
      float floatValue15 = floatValue6 - floatValue29 * 0.5F;
      this.a(matrixStack1, "back", floatValue14, floatValue15, floatValue13, floatValue29, "Назад", null, 0, intValue2, intValue3, floatValue4, floatValue5);
      float floatValue16 = dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, "Назад");
      float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
      float floatValue18 = floatValue14 + (floatValue13 - (floatValue17 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + floatValue16)) * 0.5F;
      dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack1, floatValue18 + floatValue17 * 0.35F, floatValue6, 180.0F, floatValue17, Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.5F)), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue4, 16777215));
      String string19 = this.c();
      float floatValue20 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      float floatValue21 = dev.leg1tka.gui.menu.MenuLayout.a(7.0F);
      String string22 = "Сейчас: ";
      float floatValue23 = floatValue20 + floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string22) + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string19) + floatValue20;
      float floatValue24 = floatValue14 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F) - floatValue23;
      this.X.put("cur", new float[]{floatValue24, floatValue15, floatValue23, floatValue29});
      float floatValue25 = this.a("cur", this.a((double)intValue2, (double)intValue3, floatValue24, floatValue15, floatValue23, floatValue29), floatValue5);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue24, floatValue15, floatValue23, floatValue29, floatValue29 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.05F + floatValue25 * 0.04F) * floatValue4, 16777215));
      int intValue26 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.09F + floatValue25 * 0.08F) * floatValue4, 16777215);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue24, floatValue15, floatValue23, floatValue29, floatValue29 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue26, intValue26, intValue26, intValue26);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24 + floatValue20, floatValue6 - floatValue21 * 0.5F, floatValue21, floatValue21, floatValue21 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(5.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.7F * floatValue4, 4906624));
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24 + floatValue20 + floatValue21 * 0.5F, floatValue6, floatValue21, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 4906624));
      float floatValue27 = floatValue24 + floatValue20 + floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string22, 12.5F, floatValue27, floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue4, 16777215));
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string19, 12.5F, floatValue27 + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string22), floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16777215));
   }

   private void b(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      float floatValue6 = dev.leg1tka.gui.menu.MenuLayout.a(36.0F);
      float floatValue7 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F)
         + dev.leg1tka.gui.menu.MenuLayout.a(14.0F)
         + dev.leg1tka.gui.menu.MenuLayout.a(8.0F)
         + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, "По добавлению")
         + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      this.m.a(matrixStack1, this.af, this.ModuleManager, this.ag - floatValue7 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue6, intValue2, intValue3, floatValue4);
      float floatValue8 = this.af + this.ag - floatValue7;
      this.X.put("sort", new float[]{floatValue8, this.ModuleManager, floatValue7, floatValue6});
      float floatValue9 = this.a("sort", this.a((double)intValue2, (double)intValue3, floatValue8, this.ModuleManager, floatValue7, floatValue6), floatValue5);
      float floatValue10 = Math.min(floatValue6 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(11.0F));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue8, this.ModuleManager, floatValue7, floatValue6, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.05F + floatValue9 * 0.04F) * floatValue4, 16777215));
      int intValue11 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.1F + floatValue9 * 0.08F) * floatValue4, 16777215);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue8, this.ModuleManager, floatValue7, floatValue6, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue11, intValue11, intValue11, intValue11);
      float floatValue12 = this.ModuleManager + floatValue6 * 0.5F;
      float floatValue13 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", "w", floatValue13, floatValue8 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + floatValue13 * 0.5F, floatValue12, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.5F + floatValue9 * 0.4F) * floatValue4, 16777215));
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, f[this.v], 12.5F, floatValue8 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + floatValue13 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue12, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.75F + floatValue9 * 0.2F) * floatValue4, 16777215));
      float floatValue14 = this.ModuleManager + floatValue6 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      float floatValue15 = dev.leg1tka.gui.menu.MenuLayout.a(26.0F);
      int intValue16 = 0;
      int intValue17 = 0;

      for (dev.leg1tka.accounts.AltAccount value19 : this.a()) {
         if (value19.h()) {
            intValue16++;
         }

         if (value19.b()) {
            intValue17++;
         }
      }

      float floatValue33 = this.af;
      floatValue33 += this.a(matrixStack1, "chip0", floatValue33, floatValue14, floatValue15, "Все", this.a().size(), this.u == 0, false, intValue2, intValue3, floatValue4, floatValue5) + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
      floatValue33 += this.a(
            matrixStack1,
            "chip1",
            floatValue33,
            floatValue14,
            floatValue15,
            "Закреплённые",
            intValue16,
            this.u == 1,
            false,
            intValue2,
            intValue3,
            floatValue4,
            floatValue5
         )
         + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
      this.a(matrixStack1, "chip2", floatValue33, floatValue14, floatValue15, "В бане", intValue17, this.u == 2, true, intValue2, intValue3, floatValue4, floatValue5);
      boolean flag36 = System.currentTimeMillis() - this.A < 3000L;
      String string20 = flag36 ? "Точно? Ещё раз" : "Очистить";
      float floatValue21 = dev.leg1tka.gui.components.MenuTextRenderer.a(12.0F, string20);
      float floatValue22 = this.af + this.ag - floatValue21 - dev.leg1tka.gui.menu.MenuLayout.a(4.0F);
      float floatValue23 = floatValue14 + floatValue15 * 0.5F;
      this.X.put("clear", new float[]{floatValue22 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue14, floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(10.0F), floatValue15});
      float floatValue24 = this.a("clear", this.a((double)intValue2, (double)intValue3, floatValue22 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue14, floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(10.0F), floatValue15), floatValue5);
      if (!this.a().isEmpty()) {
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string20, 12.0F, floatValue22, floatValue23, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag36 ? 1.0F : 0.42F + floatValue24 * 0.4F) * floatValue4, !flag36 && !(floatValue24 > 0.5F) ? 16777215 : 16734826));
      }

      float floatValue25 = (float)this.Z.size() * (this.am + this.an) - this.an;
      float floatValue26 = Math.max(0.0F, floatValue25 - this.al);
      this.x = Math.max(0.0F, Math.min(this.x, floatValue26));
      this.w = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(this.w, this.x, floatValue5, 16.0F);
      dev.leg1tka.render.ScissorStack.a();
      dev.leg1tka.render.ScissorStack.a((double)(this.af - dev.leg1tka.gui.menu.MenuLayout.a(2.0F)), (double)(this.ModuleRegistry - dev.leg1tka.gui.menu.MenuLayout.a(2.0F)), (double)(this.ag + dev.leg1tka.gui.menu.MenuLayout.a(4.0F)), (double)(this.al + dev.leg1tka.gui.menu.MenuLayout.a(4.0F)));
      boolean flag27 = this.a((double)intValue2, (double)intValue3, this.af, this.ModuleRegistry, this.ag, this.al) && this.J == null;

      for (int intValue28 = 0; intValue28 < this.Z.size(); intValue28++) {
         float floatValue29 = this.ModuleRegistry + (float)intValue28 * (this.am + this.an) - this.w;
         if (!(floatValue29 + this.am < this.ModuleRegistry - this.am) && !(floatValue29 > this.ModuleRegistry + this.al + this.am)) {
            float floatValue30 = dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)(System.currentTimeMillis() - this.y - 120L - (long)intValue28 * 30L) / 380.0F);
            this.a(matrixStack1, (dev.leg1tka.accounts.AltAccount)this.Z.get(intValue28), intValue28, this.af, floatValue29 + (1.0F - floatValue30) * dev.leg1tka.gui.menu.MenuLayout.a(10.0F), this.ag, intValue2, intValue3, flag27, floatValue4 * floatValue30, floatValue5);
         }
      }

      dev.leg1tka.render.ScissorStack.c();
      dev.leg1tka.render.ScissorStack.b();
      if (floatValue26 > 0.0F) {
         float floatValue37 = Math.max(dev.leg1tka.gui.menu.MenuLayout.a(24.0F), this.al * this.al / floatValue25);
         float floatValue41 = this.ModuleRegistry + (this.al - floatValue37) * (this.w / floatValue26);
         float floatValue44 = dev.leg1tka.gui.menu.MenuLayout.a(3.0F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, this.af + this.ag + dev.leg1tka.gui.menu.MenuLayout.a(5.0F), floatValue41, floatValue44, floatValue37, floatValue44 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.18F * floatValue4, 16777215));
      }

      if (this.Z.isEmpty()) {
         float floatValue38 = this.ModuleRegistry + this.al * 0.45F;
         boolean flag42 = !this.a().isEmpty();
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", flag42 ? "l" : "a", dev.leg1tka.gui.menu.MenuLayout.a(30.0F), this.af + this.ag * 0.5F, floatValue38 - dev.leg1tka.gui.menu.MenuLayout.a(30.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * floatValue4, 16777215));
         String string45 = !flag42
            ? "Список пуст"
            : (
               this.u == 2
                  ? "Банов нет"
                  : "Ничего не найдено"
            );
         String string31 = !flag42
            ? "Впиши ник в поле ниже и нажми Добавить"
            : (
               this.u == 2
                  ? "Отметить бан можно в карточке аккаунта"
                  : "Попробуй другой ник или сбрось фильтр"
            );
         dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, string45, 15.0F, this.af + this.ag * 0.5F, floatValue38 + dev.leg1tka.gui.menu.MenuLayout.a(2.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.8F * floatValue4, 16777215));
         dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, string31, 12.0F, this.af + this.ag * 0.5F, floatValue38 + dev.leg1tka.gui.menu.MenuLayout.a(22.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue4, 16777215));
      }

      if (!this.E.isEmpty() && System.currentTimeMillis() - this.F < 2600L) {
         float floatValue39 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((float)(2600L - (System.currentTimeMillis() - this.F)) / 400.0F);
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, this.E, 11.5F, this.af + dev.leg1tka.gui.menu.MenuLayout.a(4.0F), this.ao - dev.leg1tka.gui.menu.MenuLayout.a(11.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue39 * floatValue4, 16734826));
      }

      float floatValue40 = dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
      float floatValue43 = dev.leg1tka.gui.menu.MenuLayout.a(118.0F);
      float floatValue46 = dev.leg1tka.gui.menu.MenuLayout.a(132.0F);
      float floatValue47 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
      float floatValue32 = this.ag - floatValue43 - floatValue46 - floatValue47 * 2.0F;
      this.n.a(matrixStack1, this.af, this.ao, floatValue32, floatValue40, intValue2, intValue3, floatValue4);
      this.a(matrixStack1, "add", this.af + floatValue32 + floatValue47, this.ao, floatValue43, floatValue40, "Добавить", "v", intValue2, intValue3, floatValue4, floatValue5);
      this.a(
         matrixStack1,
         "rnd",
         this.af + floatValue32 + floatValue47 + floatValue43 + floatValue47,
         this.ao,
         floatValue46,
         floatValue40,
         "Случайный",
         "w",
         0,
         intValue2,
         intValue3,
         floatValue4,
         floatValue5
      );
   }

   private float a(
      MatrixStack matrixStack1,
      String string2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      String string6,
      int intValue7,
      boolean flag8,
      boolean flag9,
      int intValue10,
      int intValue11,
      float floatValue12,
      float floatValue13
   ) {
      String string14 = String.valueOf(intValue7);
      float floatValue15 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      float floatValue16 = floatValue15 + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string6) + dev.leg1tka.gui.menu.MenuLayout.a(6.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string14) + floatValue15;
      this.X.put(string2, new float[]{floatValue3, floatValue4, floatValue16, floatValue5});
      float floatValue17 = this.a(string2, this.a((double)intValue10, (double)intValue11, floatValue3, floatValue4, floatValue16, floatValue5), floatValue13);
      float floatValue18 = floatValue5 * 0.5F;
      if (flag8) {
         int intValue19 = flag9 ? 16734826 : dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
         int intValue20 = flag9 ? 16747099 : dev.leg1tka.gui.menu.MenuBackdropRenderer.d();
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue3, floatValue4, floatValue16, floatValue5, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.85F * floatValue12, intValue19), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.85F * floatValue12, intValue20), true);
      } else {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue16, floatValue5, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.05F + floatValue17 * 0.04F) * floatValue12, 16777215));
         int intValue21 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.09F + floatValue17 * 0.06F) * floatValue12, flag9 && intValue7 > 0 ? 16734826 : 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue16, floatValue5, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue21, intValue21, intValue21, intValue21);
      }

      float floatValue22 = floatValue4 + floatValue5 * 0.5F;
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string6, 12.5F, floatValue3 + floatValue15, floatValue22, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag8 ? 1.0F : 0.7F + floatValue17 * 0.2F) * floatValue12, 16777215));
      dev.leg1tka.gui.components.MenuTextRenderer.a(
         matrixStack1,
         string14,
         12.5F,
         floatValue3 + floatValue15 + dev.leg1tka.gui.components.MenuTextRenderer.a(12.5F, string6) + dev.leg1tka.gui.menu.MenuLayout.a(6.0F),
         floatValue22,
         dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag8 ? 0.8F : 0.45F) * floatValue12, !flag8 && flag9 && intValue7 > 0 ? 16734826 : 16777215)
      );
      return floatValue16;
   }

   private void a(MatrixStack matrixStack1, dev.leg1tka.accounts.AltAccount value2, int intValue3, float floatValue4, float floatValue5, float floatValue6, int intValue7, int intValue8, boolean flag9, float floatValue10, float floatValue11) {
      if (!(floatValue10 < 0.01F)) {
         String string12 = "r" + System.identityHashCode(value2);
         boolean flag13 = flag9 && this.a((double)intValue7, (double)intValue8, floatValue4, floatValue5, floatValue6, this.am);
         float floatValue14 = this.a(string12, flag13, floatValue11);
         boolean flag15 = value2 == this.s;
         boolean flag16 = this.a(value2);
         boolean flag17 = value2.b();
         float floatValue18 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         if (flag15) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue4, floatValue5, floatValue6, this.am, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.075F * floatValue10, 16777215));
            int intValue19 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * floatValue10, 16777215);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue4, floatValue5, floatValue6, this.am, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue19, intValue19, intValue19, intValue19);
         } else if (floatValue14 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue4, floatValue5, floatValue6, this.am, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.045F * floatValue14 * floatValue10, 16777215));
         }

         float floatValue39 = dev.leg1tka.gui.menu.MenuLayout.a(30.0F);
         float floatValue20 = floatValue4 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         float floatValue21 = floatValue5 + (this.am - floatValue39) * 0.5F;
         float floatValue22 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue20, floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(1.5F), floatValue39, floatValue39, floatValue22, dev.leg1tka.gui.menu.MenuLayout.a(6.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue10, 0));
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, dev.leg1tka.accounts.AccountProfileResolver.b(value2.c()).texture(), floatValue20, floatValue21, floatValue39, floatValue22, floatValue10 * (flag17 ? 0.55F : 1.0F));
         if (flag17) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue20, floatValue21, floatValue39, floatValue39, floatValue22, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.28F * floatValue10, 3803664));
            float floatValue23 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
            float floatValue24 = floatValue20 + floatValue39 - floatValue23 * 0.65F;
            float floatValue25 = floatValue21 + floatValue39 - floatValue23 * 0.65F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24, floatValue25, floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 1053206));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue24, floatValue25, floatValue23, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16734826));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon", "K", floatValue23 * 0.55F, floatValue24, floatValue25, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 16777215));
         } else if (flag16) {
            float floatValue40 = dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
            float floatValue42 = floatValue20 + floatValue39 - floatValue40 * 0.6F;
            float floatValue44 = floatValue21 + floatValue39 - floatValue40 * 0.6F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue42, floatValue44, floatValue40 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 1053206));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue42, floatValue44, floatValue40, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue10, 4906624));
         }

         float floatValue41 = floatValue20 + floatValue39 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         float floatValue43 = floatValue5 + this.am * 0.5F;
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, value2.c(), 14.0F, floatValue41, floatValue43 - dev.leg1tka.gui.menu.MenuLayout.a(7.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag17 ? 0.75F : 0.97F) * floatValue10, 16777215));
         if (flag17) {
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, b(value2), 11.5F, floatValue41, floatValue43 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue10, 16734826));
         } else if (flag16) {
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "в сети", 11.5F, floatValue41, floatValue43 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue10, 4906624));
         } else {
            String string45 = value2.i() > 0L
               ? "заходил " + a(value2.i())
               : "добавлен " + a(value2.g());
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string45, 11.5F, floatValue41, floatValue43 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue10, 16777215));
         }

         float floatValue46 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         float floatValue26 = dev.leg1tka.gui.menu.MenuLayout.a(26.0F);
         float floatValue27 = floatValue4 + floatValue6 - dev.leg1tka.gui.menu.MenuLayout.a(9.0F) - floatValue26;
         float floatValue28 = floatValue27 - dev.leg1tka.gui.menu.MenuLayout.a(2.0F) - floatValue26;
         float floatValue29 = floatValue43 - floatValue26 * 0.5F;
         float floatValue30 = dev.leg1tka.gui.menu.MenuLayout.a(66.0F);
         float floatValue31 = floatValue28 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F) - floatValue30;
         boolean flag32 = flag13 && this.a((double)intValue7, (double)intValue8, floatValue27, floatValue29, floatValue26, floatValue26);
         boolean flag33 = flag13 && this.a((double)intValue7, (double)intValue8, floatValue28, floatValue29, floatValue26, floatValue26);
         boolean flag34 = flag13 && !flag16 && this.a((double)intValue7, (double)intValue8, floatValue31, floatValue29, floatValue30, floatValue26);
         float floatValue35 = this.a(string12 + "d", flag32, floatValue11);
         float floatValue36 = this.a(string12 + "p", flag33, floatValue11);
         float floatValue37 = this.a(string12 + "l", flag34, floatValue11);
         if (flag13) {
            this.X.put("del:" + intValue3, new float[]{floatValue27, floatValue29, floatValue26, floatValue26});
            this.X.put("pin:" + intValue3, new float[]{floatValue28, floatValue29, floatValue26, floatValue26});
            if (!flag16) {
               this.X.put("BackTrackOld:" + intValue3, new float[]{floatValue31, floatValue29, floatValue30, floatValue26});
            }
         }

         this.X.put("row:" + intValue3, new float[]{floatValue4, Math.max(floatValue5, this.ModuleRegistry), floatValue6, Math.min(floatValue5 + this.am, this.ModuleRegistry + this.al) - Math.max(floatValue5, this.ModuleRegistry)});
         if (floatValue14 > 0.01F) {
            if (floatValue35 > 0.01F) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue27, floatValue29, floatValue26, floatValue26, floatValue26 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.14F * floatValue35 * floatValue10, 16734826));
            }

            dev.leg1tka.gui.components.MenuTextRenderer.a(
               matrixStack1, "icon", "F", floatValue46 * 0.9F, floatValue27 + floatValue26 * 0.5F, floatValue43, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.35F + floatValue35 * 0.65F) * floatValue14 * floatValue10, floatValue35 > 0.3F ? 16734826 : 16777215)
            );
            if (!flag16) {
               float floatValue38 = floatValue14 * floatValue10;
               dev.leg1tka.render.RenderPrimitives.a(
                  matrixStack1,
                  floatValue31,
                  floatValue29,
                  floatValue30,
                  floatValue26,
                  floatValue26 * 0.5F,
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.75F + floatValue37 * 0.25F) * floatValue38, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.75F + floatValue37 * 0.25F) * floatValue38, dev.leg1tka.gui.menu.MenuBackdropRenderer.d()),
                  true
               );
               dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, "Войти", 12.0F, floatValue31 + floatValue30 * 0.5F, floatValue43, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue38, 16777215));
            }
         }

         float floatValue47 = value2.h() ? 1.0F : floatValue14 * (0.35F + floatValue36 * 0.5F);
         if (floatValue47 > 0.01F) {
            if (floatValue36 > 0.01F) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue28, floatValue29, floatValue26, floatValue26, floatValue26 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.07F * floatValue36 * floatValue10, 16777215));
            }

            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", "p", floatValue46, floatValue28 + floatValue26 * 0.5F, floatValue43, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue47 * floatValue10, value2.h() ? dev.leg1tka.gui.menu.MenuBackdropRenderer.c() : 16777215));
         }
      }
   }

   private void a(DrawContext drawContext1, MatrixStack matrixStack2, int intValue3, int intValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = this.ModuleManager;
      float floatValue9 = this.ab + this.ad - this.ae - floatValue8;
      float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack2, this.ah, floatValue8, this.Storage, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.035F * floatValue6, 16777215));
      int intValue11 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.08F * floatValue6, 16777215);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack2, this.ah, floatValue8, this.Storage, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue11, intValue11, intValue11, intValue11);
      if (floatValue6 < 0.02F) {
         if (this.r != null) {
            this.r.setDimensionsAndPosition(1, 1, -1000, -1000);
         }
      } else {
         String string12 = this.d();
         dev.leg1tka.accounts.AltAccount value13 = this.s;
         boolean flag14 = value13 == null || this.a(value13);
         float floatValue15 = this.ah + this.Storage * 0.5F;
         float floatValue16 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
         float floatValue17 = this.Storage - floatValue16 * 2.0F;
         if (value13 != null) {
            float floatValue18 = dev.leg1tka.gui.menu.MenuLayout.a(32.0F);
            float floatValue19 = this.ah + this.Storage - floatValue16 - floatValue18;
            float floatValue20 = floatValue8 + floatValue16;
            this.X.put("cpin", new float[]{floatValue19, floatValue20, floatValue18, floatValue18});
            float floatValue21 = this.a("cpin", this.a((double)intValue3, (double)intValue4, floatValue19, floatValue20, floatValue18, floatValue18), floatValue7);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue19, floatValue20, floatValue18, floatValue18, floatValue18 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((value13.h() ? 0.16F : 0.05F + floatValue21 * 0.05F) * floatValue6, value13.h() ? dev.leg1tka.gui.menu.MenuBackdropRenderer.c() : 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.a(
               matrixStack2,
               "icon1",
               "p",
               dev.leg1tka.gui.menu.MenuLayout.a(15.0F),
               floatValue19 + floatValue18 * 0.5F,
               floatValue20 + floatValue18 * 0.5F,
               dev.leg1tka.gui.menu.MenuBackdropRenderer.a((value13.h() ? 1.0F : 0.55F + floatValue21 * 0.4F) * floatValue6, value13.h() ? dev.leg1tka.gui.menu.MenuBackdropRenderer.c() : 16777215)
            );
         }

         float floatValue54 = dev.leg1tka.gui.menu.MenuLayout.a(34.0F);
         float floatValue55 = dev.leg1tka.gui.menu.MenuLayout.a(40.0F);
         float floatValue56 = dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         float floatValue57 = floatValue8 + floatValue9 - floatValue16 - floatValue54;
         float floatValue22 = floatValue57 - floatValue56 - floatValue55;
         float floatValue23 = dev.leg1tka.gui.menu.MenuLayout.a(26.0F);
         int intValue24 = value13 != null ? 4 : 1;
         float floatValue25 = (float)intValue24 * floatValue23 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         float floatValue26 = floatValue22 - dev.leg1tka.gui.menu.MenuLayout.a(12.0F) - floatValue25;
         List list27 = value13 != null ? value13.a() : Collections.emptyList();
         int intValue28 = Math.min(2, list27.size());
         float floatValue29 = dev.leg1tka.gui.menu.MenuLayout.a(50.0F);
         float floatValue30 = intValue28 == 0 ? 0.0F : (float)intValue28 * floatValue29 + (float)(intValue28 - 1) * floatValue56 + (list27.size() > 2 ? dev.leg1tka.gui.menu.MenuLayout.a(20.0F) : 0.0F);
         float floatValue31 = floatValue26 - (intValue28 == 0 ? 0.0F : dev.leg1tka.gui.menu.MenuLayout.a(12.0F) + floatValue30);
         float floatValue32 = floatValue31 - dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
         float floatValue33 = floatValue32 - dev.leg1tka.gui.menu.MenuLayout.a(21.0F);
         float floatValue34 = floatValue8 + floatValue16;
         float floatValue35 = floatValue33 - dev.leg1tka.gui.menu.MenuLayout.a(20.0F) - floatValue34;
         boolean flag36 = floatValue35 >= dev.leg1tka.gui.menu.MenuLayout.a(70.0F);
         if (flag36) {
            int intValue37 = dev.leg1tka.gui.menu.MenuBackdropRenderer.c();
            float floatValue38 = floatValue35 * 0.55F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue15 - floatValue38 * 0.5F, floatValue34 + floatValue35 * 0.2F, floatValue38, floatValue35 * 0.6F, floatValue38 * 0.5F, dev.leg1tka.gui.menu.MenuLayout.a(60.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.2F * floatValue6, intValue37));
            dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue15 - dev.leg1tka.gui.menu.MenuLayout.a(30.0F), floatValue34 + floatValue35 - dev.leg1tka.gui.menu.MenuLayout.a(9.0F), dev.leg1tka.gui.menu.MenuLayout.a(60.0F), dev.leg1tka.gui.menu.MenuLayout.a(7.0F), dev.leg1tka.gui.menu.MenuLayout.a(3.5F), dev.leg1tka.gui.menu.MenuLayout.a(10.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.55F * floatValue6, 0));
            if (this.r != null) {
               int intValue39 = Math.max(20, Math.round(Math.min(floatValue35, dev.leg1tka.gui.menu.MenuLayout.a(210.0F)) * 0.95F));
               int intValue40 = Math.max(10, Math.round((float)intValue39 * 0.5F));
               this.r.setDimensionsAndPosition(intValue40, intValue39, Math.round(floatValue15 - (float)intValue40 * 0.5F), Math.round(floatValue34 + floatValue35 - (float)intValue39 - dev.leg1tka.gui.menu.MenuLayout.a(3.0F)));
               if (floatValue6 > 0.5F) {
                  drawContext1.draw();
                  this.r.render(drawContext1, intValue3, intValue4, floatValue5);
                  drawContext1.draw();
                  dev.leg1tka.render.RenderPrimitives.e();
               }
            }

            if (!k) {
               dev.leg1tka.gui.components.MenuTextRenderer.b(
                  matrixStack2,
                  "Потяни мышкой, чтобы повернуть",
                  10.5F,
                  floatValue15,
                  floatValue34 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.32F * floatValue6, 16777215)
               );
            }
         } else if (this.r != null) {
            this.r.setDimensionsAndPosition(1, 1, -1000, -1000);
         }

         if (this.t && value13 != null) {
            float floatValue59 = Math.min(floatValue17, dev.leg1tka.gui.menu.MenuLayout.a(260.0F));
            this.o.a(matrixStack2, floatValue15 - floatValue59 * 0.5F, floatValue33 - dev.leg1tka.gui.menu.MenuLayout.a(18.0F), floatValue59, dev.leg1tka.gui.menu.MenuLayout.a(36.0F), intValue3, intValue4, floatValue6);
         } else {
            dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack2, string12, 20.0F, floatValue15, floatValue33, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6, 16777215));
            boolean flag58 = value13 != null && value13.b();
            String string61 = flag58
               ? "в бане"
               : (
                  flag14
                     ? "в сети"
                     : (
                        value13 != null && value13.i() > 0L
                           ? "заходил " + a(value13.i())
                           : "ещё не заходил"
                     )
               );
            int intValue67 = flag58 ? 16734826 : (flag14 ? 4906624 : 16777215);
            float floatValue70 = dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            float floatValue41 = floatValue70 + dev.leg1tka.gui.menu.MenuLayout.a(7.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(12.0F, string61);
            float floatValue42 = floatValue15 - floatValue41 * 0.5F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue42 + floatValue70 * 0.5F, floatValue32, floatValue70, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((!flag58 && !flag14 ? 0.3F : 1.0F) * floatValue6, intValue67));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, string61, 12.0F, floatValue42 + floatValue70 + dev.leg1tka.gui.menu.MenuLayout.a(7.0F), floatValue32, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((!flag58 && !flag14 ? 0.5F : 0.9F) * floatValue6, intValue67));
         }

         float floatValue60 = floatValue31 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);

         for (int intValue62 = 0; intValue62 < intValue28; intValue62++) {
            dev.leg1tka.accounts.AccountBan value68 = (dev.leg1tka.accounts.AccountBan)list27.get(intValue62);
            float floatValue71 = this.ah + floatValue16;
            float floatValue73 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue71, floatValue60, floatValue17, floatValue29, floatValue73, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.12F * floatValue6, 16734826));
            int intValue77 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.3F * floatValue6, 16734826);
            dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue71, floatValue60, floatValue17, floatValue29, floatValue73, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue77, intValue77, intValue77, intValue77);
            float floatValue43 = dev.leg1tka.gui.menu.MenuLayout.a(30.0F);
            float floatValue44 = floatValue71 + dev.leg1tka.gui.menu.MenuLayout.a(10.0F) + floatValue43 * 0.5F;
            float floatValue45 = floatValue60 + floatValue29 * 0.5F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack2, floatValue44, floatValue45, floatValue43, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * floatValue6, 16734826));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, "icon", "K", dev.leg1tka.gui.menu.MenuLayout.a(14.0F), floatValue44, floatValue45, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6, 16734826));
            float floatValue46 = floatValue44 + floatValue43 * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
            String string47 = dev.leg1tka.accounts.ServerReconnectTracker.a(value68.e());
            String string48 = value68.h() != null && !value68.h().isBlank() ? "  " + value68.h() : "";
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, a(string47 + string48, 13.5F, floatValue17 - (floatValue46 - floatValue71) - dev.leg1tka.gui.menu.MenuLayout.a(84.0F)), 13.5F, floatValue46, floatValue45 - dev.leg1tka.gui.menu.MenuLayout.a(7.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6, 16777215));
            String string49 = value68.a()
               ? "без срока"
               : "осталось " + dev.leg1tka.accounts.ServerReconnectTracker.a(value68.c());
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, string49, 11.5F, floatValue46, floatValue45 + dev.leg1tka.gui.menu.MenuLayout.a(8.5F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.85F * floatValue6, 16734826));
            float floatValue50 = dev.leg1tka.gui.menu.MenuLayout.a(64.0F);
            float floatValue51 = dev.leg1tka.gui.menu.MenuLayout.a(26.0F);
            this.a(
               matrixStack2,
               "unban:" + intValue62,
               floatValue71 + floatValue17 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F) - floatValue50,
               floatValue45 - floatValue51 * 0.5F,
               floatValue50,
               floatValue51,
               "Снять",
               null,
               0,
               intValue3,
               intValue4,
               floatValue6,
               floatValue7
            );
            if (!value68.a()) {
               float floatValue52 = floatValue17 - floatValue73 * 2.0F;
               float floatValue53 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(2.0F));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack2, floatValue71 + floatValue73, floatValue60 + floatValue29 - floatValue53 - dev.leg1tka.gui.menu.MenuLayout.a(4.0F), floatValue52, floatValue53, floatValue53 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.1F * floatValue6, 16777215));
               dev.leg1tka.render.RenderPrimitives.a(
                  matrixStack2,
                  floatValue71 + floatValue73,
                  floatValue60 + floatValue29 - floatValue53 - dev.leg1tka.gui.menu.MenuLayout.a(4.0F),
                  Math.max(floatValue53, floatValue52 * value68.d()),
                  floatValue53,
                  floatValue53 * 0.5F,
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6, 16734826),
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue6, 16747099),
                  true
               );
            }

            floatValue60 += floatValue29 + floatValue56;
         }

         if (list27.size() > 2) {
            dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack2, "и ещё " + (list27.size() - 2), 11.5F, floatValue15, floatValue60 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.6F * floatValue6, 16734826));
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack2, this.ah + floatValue16, floatValue26, floatValue17, floatValue25, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.035F * floatValue6, 16777215));
         float floatValue63 = floatValue26 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F) + floatValue23 * 0.5F;
         float floatValue69 = this.ah + floatValue16 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         float floatValue72 = this.ah + floatValue16 + floatValue17 - dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         if (value13 != null) {
            this.a(matrixStack2, "Добавлен", b(value13.g()), floatValue69, floatValue72, floatValue63, floatValue6, 16777215);
            floatValue63 += floatValue23;
            String string74 = value13.k() != null && !value13.k().isBlank() ? dev.leg1tka.accounts.ServerReconnectTracker.a(value13.k()) : "ещё не было";
            this.a(
               matrixStack2, "Последний сервер", string74, floatValue69, floatValue72, floatValue63, floatValue6, 16777215
            );
            floatValue63 += floatValue23;
            this.a(
               matrixStack2,
               "Заходов на сервера",
               String.valueOf(value13.l()),
               floatValue69,
               floatValue72,
               floatValue63,
               floatValue6,
               16777215
            );
            floatValue63 += floatValue23;
            String string78 = value13.d() != null ? value13.d() : "";
            String string79 = string78.length() > 13 ? string78.substring(0, 8) + "..." + string78.substring(string78.length() - 4) : string78;
            this.X.put("uuid", new float[]{this.ah + floatValue16, floatValue63 - floatValue23 * 0.5F, floatValue17, floatValue23});
            float floatValue80 = this.a("uuid", this.a((double)intValue3, (double)intValue4, this.ah + floatValue16, floatValue63 - floatValue23 * 0.5F, floatValue17, floatValue23), floatValue7);
            float floatValue81 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, "UUID", 12.0F, floatValue69, floatValue63, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue6, 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack2, "icon", "G", floatValue81, floatValue72 - floatValue81 * 0.5F, floatValue63, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.35F + floatValue80 * 0.6F) * floatValue6, floatValue80 > 0.3F ? dev.leg1tka.gui.menu.MenuBackdropRenderer.c() : 16777215));
            dev.leg1tka.gui.components.MenuTextRenderer.c(matrixStack2, string79, 12.0F, floatValue72 - floatValue81 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue63, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.8F + floatValue80 * 0.2F) * floatValue6, 16777215));
         } else {
            this.a(
               matrixStack2,
               "Аккаунт",
               "не сохранён в списке",
               floatValue69,
               floatValue72,
               floatValue63,
               floatValue6,
               16777215
            );
         }

         if (this.t && value13 != null) {
            this.a(matrixStack2, "rsave", this.ah + floatValue16, floatValue22, floatValue17, floatValue55, "Сохранить", "k", intValue3, intValue4, floatValue6, floatValue7);
            this.a(matrixStack2, "rcancel", this.ah + floatValue16, floatValue57, floatValue17, floatValue54, "Отмена", null, 0, intValue3, intValue4, floatValue6, floatValue7);
         } else if (value13 == null) {
            this.a(
               matrixStack2,
               "keep",
               this.ah + floatValue16,
               floatValue22,
               floatValue17,
               floatValue55,
               "Сохранить в список",
               "v",
               intValue3,
               intValue4,
               floatValue6,
               floatValue7
            );
         } else {
            if (flag14) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack2, this.ah + floatValue16, floatValue22, floatValue17, floatValue55, floatValue55 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.1F * floatValue6, 4906624));
               int intValue75 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.35F * floatValue6, 4906624);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack2, this.ah + floatValue16, floatValue22, floatValue17, floatValue55, floatValue55 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue75, intValue75, intValue75, intValue75);
               this.a(
                  matrixStack2,
                  this.ah + floatValue16,
                  floatValue22,
                  floatValue17,
                  floatValue55,
                  "Вы в этом аккаунте",
                  "k",
                  floatValue6,
                  4906624
               );
            } else {
               this.a(matrixStack2, "login", this.ah + floatValue16, floatValue22, floatValue17, floatValue55, "Войти", null, intValue3, intValue4, floatValue6, floatValue7);
            }

            float floatValue76 = (floatValue17 - floatValue56 * 2.0F) / 3.0F;
            this.a(matrixStack2, "rename", this.ah + floatValue16, floatValue57, floatValue76, floatValue54, "Имя", "s", 0, intValue3, intValue4, floatValue6, floatValue7);
            this.a(matrixStack2, "ban", this.ah + floatValue16 + floatValue76 + floatValue56, floatValue57, floatValue76, floatValue54, "Бан", "h", 0, intValue3, intValue4, floatValue6, floatValue7);
            this.a(
               matrixStack2,
               "delete",
               this.ah + floatValue16 + (floatValue76 + floatValue56) * 2.0F,
               floatValue57,
               floatValue76,
               floatValue54,
               "Удалить",
               null,
               1,
               intValue3,
               intValue4,
               floatValue6,
               floatValue7
            );
         }
      }
   }

   private void c(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         this.O = value1;
         this.J = null;
         this.t = false;
         this.Q = false;
         this.R[0] = 7;
         this.R[1] = 0;
         this.R[2] = 0;
         this.S = -1;
         this.T = "";
         this.V = "";
         this.q.b("");
         this.U.clear();
         if (value1.k() != null && !value1.k().isBlank()) {
            this.U.add(value1.k());
         }

         try {
            ServerList serverList2 = new ServerList(this.client);
            serverList2.loadFile();

            for (int intValue3 = 0; intValue3 < serverList2.size() && this.U.size() < 6; intValue3++) {
               ServerInfo serverInfo4 = serverList2.get(intValue3);
               if (serverInfo4.address != null && !serverInfo4.address.isBlank()) {
                  boolean flag5 = false;

                  for (String string7 : this.U) {
                     if (string7.equalsIgnoreCase(serverInfo4.address)) {
                        flag5 = true;
                     }
                  }

                  if (!flag5) {
                     this.U.add(serverInfo4.address);
                  }
               }
            }
         } catch (Throwable throwable8) {
         }

         this.p.b(this.U.isEmpty() ? "" : (String)this.U.get(0));
         this.a(this.p);
         this.p.c();
      }
   }

   private void g() {
      String string1 = this.p.a().trim();
      if (string1.isEmpty()) {
         this.V = "Укажи сервер";
      } else {
         long longValue2 = ((long)this.R[0] * 86400L + (long)this.R[1] * 3600L + (long)this.R[2] * 60L) * 1000L;
         if (this.Q && longValue2 <= 0L) {
            this.V = "Укажи срок или выбери Без срока";
         } else {
            dev.leg1tka.accounts.AltAccount value4 = this.O;
            value4.a().removeIf(value1x -> value1x.e().equalsIgnoreCase(string1));
            value4.a().add(new dev.leg1tka.accounts.AccountBan(string1, this.Q ? System.currentTimeMillis() + longValue2 : 0L, this.q.a().trim()));
            this.b();
            this.O = null;
            this.S = -1;
            this.a((dev.leg1tka.gui.components.MenuIconButton)null);
            this.a(value4.c() + " в бане на " + dev.leg1tka.accounts.ServerReconnectTracker.a(string1), 16734826, null);
         }
      }
   }

   private void c(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5) {
      float floatValue6 = this.ah;
      float floatValue7 = this.ModuleManager + (1.0F - this.P) * dev.leg1tka.gui.menu.MenuLayout.a(24.0F);
      float floatValue8 = this.Storage;
      float floatValue9 = this.ab + this.ad - this.ae - this.ModuleManager;
      float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.a(16.0F);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue6, floatValue7 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue8, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuLayout.a(24.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.4F * floatValue4, 0));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.96F * floatValue4, 1052951));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.1F * floatValue4, 16734826));
      int intValue11 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.22F * floatValue4, 16734826);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6, floatValue7, floatValue8, floatValue9, floatValue10, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue11, intValue11, intValue11, intValue11);
      if (this.O != null) {
         float floatValue12 = dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
         float floatValue13 = floatValue8 - floatValue12 * 2.0F;
         float floatValue14 = floatValue6 + floatValue12;
         float floatValue15 = floatValue7 + floatValue12;
         float floatValue16 = dev.leg1tka.gui.menu.MenuLayout.a(34.0F);
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue14 + floatValue16 * 0.5F, floatValue15 + floatValue16 * 0.5F, floatValue16, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.2F * floatValue4, 16734826));
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon", "K", dev.leg1tka.gui.menu.MenuLayout.a(15.0F), floatValue14 + floatValue16 * 0.5F, floatValue15 + floatValue16 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16734826));
         dev.leg1tka.gui.components.MenuTextRenderer.a(
            matrixStack1,
            "Отметить бан",
            17.0F,
            floatValue14 + floatValue16 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F),
            floatValue15 + floatValue16 * 0.5F - dev.leg1tka.gui.menu.MenuLayout.a(7.0F),
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16777215)
         );
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, this.O.c(), 12.0F, floatValue14 + floatValue16 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F), floatValue15 + floatValue16 * 0.5F + dev.leg1tka.gui.menu.MenuLayout.a(9.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
         float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(28.0F);
         float floatValue18 = floatValue6 + floatValue8 - floatValue12 - floatValue17;
         this.X.put("bn:x", new float[]{floatValue18, floatValue15 + (floatValue16 - floatValue17) * 0.5F, floatValue17, floatValue17});
         float floatValue19 = this.a("bn:x", this.a((double)intValue2, (double)intValue3, floatValue18, floatValue15 + (floatValue16 - floatValue17) * 0.5F, floatValue17, floatValue17), floatValue5);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18, floatValue15 + (floatValue16 - floatValue17) * 0.5F, floatValue17, floatValue17, floatValue17 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.05F + floatValue19 * 0.06F) * floatValue4, 16777215));
         float floatValue20 = dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
         float floatValue21 = Math.max(dev.leg1tka.gui.menu.MenuLayout.c() * 2.0F, dev.leg1tka.gui.menu.MenuLayout.a(1.5F));

         for (int intValue22 = 0; intValue22 < 2; intValue22++) {
            matrixStack1.push();
            matrixStack1.translate(floatValue18 + floatValue17 * 0.5F, floatValue15 + floatValue16 * 0.5F, 0.0F);
            matrixStack1.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(intValue22 == 0 ? 45.0F : -45.0F));
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, -floatValue20 * 0.5F, -floatValue21 * 0.5F, floatValue20, floatValue21, floatValue21 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.6F + floatValue19 * 0.4F) * floatValue4, 16777215));
            matrixStack1.pop();
         }

         floatValue15 += floatValue16 + dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Сервер", 11.5F, floatValue14, floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(5.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
         floatValue15 += dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         float floatValue48 = dev.leg1tka.gui.menu.MenuLayout.a(36.0F);
         this.p.a(matrixStack1, floatValue14, floatValue15, floatValue13, floatValue48, intValue2, intValue3, floatValue4);
         floatValue15 += floatValue48 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         if (!this.U.isEmpty()) {
            float floatValue23 = floatValue14;
            float floatValue24 = dev.leg1tka.gui.menu.MenuLayout.a(24.0F);

            for (int intValue25 = 0; intValue25 < this.U.size(); intValue25++) {
               String string26 = dev.leg1tka.accounts.ServerReconnectTracker.a((String)this.U.get(intValue25));
               float floatValue27 = dev.leg1tka.gui.components.MenuTextRenderer.a(12.0F, string26) + dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
               if (floatValue23 + floatValue27 > floatValue14 + floatValue13) {
                  break;
               }

               String string28 = "bn:sug:" + intValue25;
               this.X.put(string28, new float[]{floatValue23, floatValue15, floatValue27, floatValue24});
               boolean flag29 = ((String)this.U.get(intValue25)).equalsIgnoreCase(this.p.a().trim());
               float floatValue30 = this.a(string28, this.a((double)intValue2, (double)intValue3, floatValue23, floatValue15, floatValue27, floatValue24), floatValue5);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue23, floatValue15, floatValue27, floatValue24, floatValue24 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag29 ? 0.18F : 0.05F + floatValue30 * 0.05F) * floatValue4, flag29 ? 16734826 : 16777215));
               dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, string26, 12.0F, floatValue23 + floatValue27 * 0.5F, floatValue15 + floatValue24 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag29 ? 1.0F : 0.7F + floatValue30 * 0.3F) * floatValue4, 16777215));
               floatValue23 += floatValue27 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            }

            floatValue15 += floatValue24 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         } else {
            floatValue15 += dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
         }

         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Срок", 11.5F, floatValue14, floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(5.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
         floatValue15 += dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         float floatValue49 = dev.leg1tka.gui.menu.MenuLayout.a(34.0F);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue14, floatValue15, floatValue13, floatValue49, floatValue49 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.05F * floatValue4, 16777215));
         float floatValue50 = (floatValue13 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F)) * 0.5F;
         String[] stringArray51 = new String[]{"Без срока", "На время"};

         for (int intValue52 = 0; intValue52 < 2; intValue52++) {
            float floatValue55 = floatValue14 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F) + (float)intValue52 * floatValue50;
            String string58 = intValue52 == 0 ? "bn:perm" : "bn:timed";
            this.X.put(string58, new float[]{floatValue55, floatValue15, floatValue50, floatValue49});
            boolean flag61 = intValue52 == 1 == this.Q;
            float floatValue64 = this.a(string58, this.a((double)intValue2, (double)intValue3, floatValue55, floatValue15, floatValue50, floatValue49), floatValue5);
            float floatValue31 = floatValue49 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            if (flag61) {
               dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue55, floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F), floatValue50, floatValue31, floatValue31 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue4, 16734826), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue4, 16747099), true);
            } else if (floatValue64 > 0.01F) {
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue55, floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(3.0F), floatValue50, floatValue31, floatValue31 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.05F * floatValue64 * floatValue4, 16777215));
            }

            dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, stringArray51[intValue52], 13.0F, floatValue55 + floatValue50 * 0.5F, floatValue15 + floatValue49 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag61 ? 1.0F : 0.6F + floatValue64 * 0.3F) * floatValue4, 16777215));
         }

         floatValue15 += floatValue49 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         if (this.Q) {
            float floatValue53 = dev.leg1tka.gui.menu.MenuLayout.a(58.0F);
            float floatValue56 = dev.leg1tka.gui.menu.MenuLayout.a(46.0F);
            float floatValue59 = dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
            float floatValue62 = dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
            float floatValue65 = floatValue53 * 3.0F + floatValue59 * 3.0F + floatValue62 * 2.0F;
            float floatValue67 = floatValue14 + (floatValue13 - floatValue65) * 0.5F;

            for (int intValue32 = 0; intValue32 < 3; intValue32++) {
               String string33 = "bn:seg:" + intValue32;
               this.X.put(string33, new float[]{floatValue67, floatValue15, floatValue53, floatValue56});
               float floatValue34 = this.a(string33, this.a((double)intValue2, (double)intValue3, floatValue67, floatValue15, floatValue53, floatValue56), floatValue5);
               boolean flag35 = this.S == intValue32;
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue67, floatValue15, floatValue53, floatValue56, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.06F + floatValue34 * 0.03F + (flag35 ? 0.04F : 0.0F)) * floatValue4, 16777215));
               int intValue36 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag35 ? 0.9F : 0.1F + floatValue34 * 0.08F) * floatValue4, flag35 ? 16734826 : 16777215);
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue67, floatValue15, floatValue53, floatValue56, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue36, intValue36, intValue36, intValue36);
               String string37 = flag35 && !this.T.isEmpty() ? this.T : String.format(Locale.ROOT, intValue32 == 0 ? "%d" : "%02d", this.R[intValue32]);
               dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, string37, 22.0F, floatValue67 + floatValue53 * 0.5F, floatValue15 + floatValue56 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16777215));
               dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, j[intValue32], 13.0F, floatValue67 + floatValue53 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue15 + floatValue56 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
               floatValue67 += floatValue53 + floatValue59 + floatValue62;
            }

            floatValue15 += floatValue56 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
            float floatValue70 = dev.leg1tka.gui.menu.MenuLayout.a(24.0F);
            float floatValue71 = 0.0F;

            for (String string38 : g) {
               floatValue71 += dev.leg1tka.gui.components.MenuTextRenderer.a(12.0F, string38) + dev.leg1tka.gui.menu.MenuLayout.a(20.0F) + dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            }

            float floatValue69 = floatValue14 + (floatValue13 - floatValue71 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F)) * 0.5F;

            for (int intValue73 = 0; intValue73 < g.length; intValue73++) {
               float floatValue75 = dev.leg1tka.gui.components.MenuTextRenderer.a(12.0F, g[intValue73]) + dev.leg1tka.gui.menu.MenuLayout.a(20.0F);
               String string77 = "bn:pre:" + intValue73;
               this.X.put(string77, new float[]{floatValue69, floatValue15, floatValue75, floatValue70});
               float floatValue78 = this.a(string77, this.a((double)intValue2, (double)intValue3, floatValue69, floatValue15, floatValue75, floatValue70), floatValue5);
               boolean flag39 = this.R[0] == h[intValue73][0] && this.R[1] == h[intValue73][1] && this.R[2] == h[intValue73][2];
               dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue69, floatValue15, floatValue75, floatValue70, floatValue70 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag39 ? 0.18F : 0.05F + floatValue78 * 0.05F) * floatValue4, flag39 ? 16734826 : 16777215));
               dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, g[intValue73], 12.0F, floatValue69 + floatValue75 * 0.5F, floatValue15 + floatValue70 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((flag39 ? 1.0F : 0.7F + floatValue78 * 0.3F) * floatValue4, 16777215));
               floatValue69 += floatValue75 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
            }

            floatValue15 += floatValue70 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
         }

         float floatValue54 = dev.leg1tka.gui.menu.MenuLayout.a(38.0F);
         float floatValue57 = floatValue7 + floatValue9 - floatValue12 - floatValue54;
         if (floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + floatValue48 < floatValue57 - dev.leg1tka.gui.menu.MenuLayout.a(22.0F)) {
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "Причина", 11.5F, floatValue14, floatValue15 + dev.leg1tka.gui.menu.MenuLayout.a(5.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.5F * floatValue4, 16777215));
            floatValue15 += dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
            this.q.a(matrixStack1, floatValue14, floatValue15, floatValue13, floatValue48, intValue2, intValue3, floatValue4);
         }

         if (!this.V.isEmpty()) {
            dev.leg1tka.gui.components.MenuTextRenderer.b(matrixStack1, this.V, 11.5F, floatValue14 + floatValue13 * 0.5F, floatValue57 - dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16734826));
         }

         float floatValue60 = (floatValue13 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F)) * 0.5F;
         this.a(matrixStack1, "bn:cancel", floatValue14, floatValue57, floatValue60, floatValue54, "Отмена", null, 0, intValue2, intValue3, floatValue4, floatValue5);
         this.X.put("bn:save", new float[]{floatValue14 + floatValue60 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue57, floatValue60, floatValue54});
         float floatValue63 = this.a("bn:save", this.a((double)intValue2, (double)intValue3, floatValue14 + floatValue60 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F), floatValue57, floatValue60, floatValue54), floatValue5);
         float floatValue66 = floatValue63 * dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
         float floatValue68 = floatValue14 + floatValue60 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         dev.leg1tka.render.RenderPrimitives.a(
            matrixStack1,
            floatValue68 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F),
            floatValue57 + dev.leg1tka.gui.menu.MenuLayout.a(4.0F) - floatValue66,
            floatValue60 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F),
            floatValue54,
            floatValue54 * 0.5F,
            dev.leg1tka.gui.menu.MenuLayout.a(14.0F + floatValue63 * 8.0F),
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.35F + floatValue63 * 0.25F) * floatValue4, 16734826)
         );
         dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue68, floatValue57 - floatValue66, floatValue60, floatValue54, floatValue54 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16734826), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue4, 16747099), true);
         this.a(matrixStack1, floatValue68, floatValue57 - floatValue66, floatValue60, floatValue54, "Сохранить бан", null, floatValue4, 16777215);
      }
   }

   private void a(int intValue1, int intValue2) {
      this.R[intValue1] = Math.max(0, Math.min(i[intValue1], this.R[intValue1] + intValue2));
   }

   private void h() {
      if (this.S >= 0 && !this.T.isEmpty()) {
         try {
            this.R[this.S] = Math.max(0, Math.min(i[this.S], Integer.parseInt(this.T)));
         } catch (NumberFormatException numberFormatException2) {
         }
      }

      this.T = "";
   }

   private void a(dev.leg1tka.accounts.AltAccount value1, double doubleValue2, double doubleValue4) {
      this.s = value1;
      this.J = value1;
      this.K = (float)doubleValue2;
      this.L = (float)doubleValue4;
      this.N.clear();
      if (!this.a(value1)) {
         this.N.add(new AltManagerScreen.a("Войти", "icon1", "k", 4906624, () -> this.d(value1)));
      }

      this.N
         .add(
            new AltManagerScreen.a(
               value1.h() ? "Открепить" : "Закрепить",
               "icon1",
               "p",
               16777215,
               () -> this.f(value1)
            )
         );
      this.N.add(new AltManagerScreen.a("Отметить бан", "icon", "K", 16734826, () -> this.c(value1)));
      this.N.add(new AltManagerScreen.a("Переименовать", "icon1", "s", 16777215, () -> {
         this.s = value1;
         this.n();
      }));
      this.N.add(new AltManagerScreen.a("Копировать ник", "icon", "G", 16777215, () -> {
         if (this.client != null) {
            this.client.keyboard.setClipboard(value1.c());
         }

         this.a("Ник скопирован", dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), null);
      }));
      this.N.add(new AltManagerScreen.a("Удалить", "icon", "F", 16734826, () -> this.e(value1)));
   }

   private void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      float floatValue8 = dev.leg1tka.gui.menu.MenuLayout.a(32.0F);
      float floatValue9 = dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
      float floatValue10 = 0.0F;

      for (AltManagerScreen.a value12 : this.N) {
         floatValue10 = Math.max(floatValue10, dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, value12.lb()));
      }

      floatValue10 += dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + dev.leg1tka.gui.menu.MenuLayout.a(16.0F) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F) + dev.leg1tka.gui.menu.MenuLayout.a(18.0F);
      float floatValue24 = (float)this.N.size() * floatValue8 + floatValue9 * 2.0F;
      float floatValue25 = Math.min(this.K, floatValue4 - floatValue10 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F));
      float floatValue13 = Math.min(this.L, floatValue5 - floatValue24 - dev.leg1tka.gui.menu.MenuLayout.a(8.0F));
      this.X.put("cm", new float[]{floatValue25, floatValue13, floatValue10, floatValue24});
      float floatValue14 = (1.0F - this.M) * dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
      dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue25, floatValue13 + dev.leg1tka.gui.menu.MenuLayout.a(6.0F) - floatValue14, floatValue10, floatValue24, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuLayout.a(20.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue6, 0));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue25, floatValue13 - floatValue14, floatValue10, floatValue24, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.97F * floatValue6, 1381917));
      int intValue15 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.1F * floatValue6, 16777215);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue25, floatValue13 - floatValue14, floatValue10, floatValue24, dev.leg1tka.gui.menu.MenuLayout.a(12.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue15, intValue15, intValue15, intValue15);
      float floatValue16 = floatValue13 + floatValue9 - floatValue14;

      for (int intValue17 = 0; intValue17 < this.N.size(); intValue17++) {
         AltManagerScreen.a value18 = (AltManagerScreen.a)this.N.get(intValue17);
         String string19 = "cm:" + intValue17;
         this.X.put(string19, new float[]{floatValue25 + floatValue9, floatValue16, floatValue10 - floatValue9 * 2.0F, floatValue8});
         float floatValue20 = this.a(string19, this.a((double)intValue2, (double)intValue3, floatValue25 + floatValue9, floatValue16, floatValue10 - floatValue9 * 2.0F, floatValue8), floatValue7);
         if (floatValue20 > 0.01F) {
            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue25 + floatValue9, floatValue16, floatValue10 - floatValue9 * 2.0F, floatValue8, dev.leg1tka.gui.menu.MenuLayout.a(8.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.07F * floatValue20 * floatValue6, value18.col() == 16734826 ? 16734826 : 16777215));
         }

         float floatValue21 = floatValue25 + floatValue9 + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
         float floatValue22 = floatValue16 + floatValue8 * 0.5F;
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, value18.fam(), value18.ico(), dev.leg1tka.gui.menu.MenuLayout.a(14.0F), floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(7.0F), floatValue22, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.6F + floatValue20 * 0.4F) * floatValue6, value18.col()));
         dev.leg1tka.gui.components.MenuTextRenderer.a(
            matrixStack1, value18.lb(), 13.0F, floatValue21 + dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F), floatValue22, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.85F + floatValue20 * 0.15F) * floatValue6, value18.col() == 16734826 ? 16734826 : 16777215)
         );
         floatValue16 += floatValue8;
      }
   }

   private static String a(String string0, float floatValue1, float floatValue2) {
      if (!(floatValue2 <= 0.0F) && !(dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue1, string0) <= floatValue2)) {
         String string3 = string0;

         while (string3.length() > 1 && dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue1, string3 + "...") > floatValue2) {
            string3 = string3.substring(0, string3.length() - 1);
         }

         return string3.trim() + "...";
      } else {
         return string0;
      }
   }

   private void a(MatrixStack matrixStack1, String string2, String string3, float floatValue4, float floatValue5, float floatValue6, float floatValue7, int intValue8) {
      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string2, 12.0F, floatValue4, floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.45F * floatValue7, 16777215));
      dev.leg1tka.gui.components.MenuTextRenderer.c(matrixStack1, string3, 12.0F, floatValue5, floatValue6, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.88F * floatValue7, intValue8));
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, float floatValue4, float floatValue5, String string6, String string7, float floatValue8, int intValue9) {
      float floatValue10 = 13.5F;
      float floatValue11 = dev.leg1tka.gui.components.MenuTextRenderer.a(floatValue10, string6);
      float floatValue12 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
      float floatValue13 = string7 != null ? floatValue12 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F) : 0.0F;
      float floatValue14 = floatValue2 + (floatValue4 - floatValue11 - floatValue13) * 0.5F;
      float floatValue15 = floatValue3 + floatValue5 * 0.5F;
      if (string7 != null) {
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, "icon1", string7, floatValue12, floatValue14 + floatValue12 * 0.5F, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, intValue9));
      }

      dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string6, floatValue10, floatValue14 + floatValue13, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue8, intValue9));
   }

   private void a(
      MatrixStack matrixStack1, String string2, float floatValue3, float floatValue4, float floatValue5, float floatValue6, String string7, String string8, int intValue9, int intValue10, float floatValue11, float floatValue12
   ) {
      this.X.put(string2, new float[]{floatValue3, floatValue4, floatValue5, floatValue6});
      float floatValue13 = this.a(string2, this.a((double)intValue9, (double)intValue10, floatValue3, floatValue4, floatValue5, floatValue6), floatValue12);
      float floatValue14 = floatValue13 * dev.leg1tka.gui.menu.MenuLayout.a(1.5F);
      dev.leg1tka.gui.menu.MenuBackdropRenderer.b(matrixStack1, floatValue3, floatValue4 - floatValue14, floatValue5, floatValue6, floatValue11, floatValue13);
      this.a(matrixStack1, floatValue3, floatValue4 - floatValue14, floatValue5, floatValue6, string7, string8, floatValue11, 16777215);
   }

   private void a(
      MatrixStack matrixStack1,
      String string2,
      float floatValue3,
      float floatValue4,
      float floatValue5,
      float floatValue6,
      String string7,
      String string8,
      int intValue9,
      int intValue10,
      int intValue11,
      float floatValue12,
      float floatValue13
   ) {
      this.X.put(string2, new float[]{floatValue3, floatValue4, floatValue5, floatValue6});
      float floatValue14 = this.a(string2, this.a((double)intValue10, (double)intValue11, floatValue3, floatValue4, floatValue5, floatValue6), floatValue13);
      float floatValue15 = floatValue6 * 0.5F;
      if (intValue9 == 1) {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue5, floatValue6, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.03F + floatValue14 * 0.14F) * floatValue12, floatValue14 > 0.02F ? 16734826 : 16777215));
         int intValue16 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.09F + floatValue14 * 0.25F) * floatValue12, floatValue14 > 0.02F ? 16734826 : 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue5, floatValue6, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue16, intValue16, intValue16, intValue16);
      } else {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue5, floatValue6, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.06F + floatValue14 * 0.05F) * floatValue12, 16777215));
         int intValue19 = dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.09F + floatValue14 * 0.1F) * floatValue12, 16777215);
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue5, floatValue6, floatValue15, dev.leg1tka.gui.menu.MenuBackdropRenderer.g(), intValue19, intValue19, intValue19, intValue19);
      }

      if (!string2.equals("back")) {
         this.a(matrixStack1, floatValue3, floatValue4, floatValue5, floatValue6, string7, string8, floatValue12 * (0.85F + floatValue14 * 0.15F), intValue9 == 1 ? 16734826 : 16777215);
      } else {
         float floatValue20 = dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, string7);
         float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(6.0F);
         float floatValue18 = floatValue3 + (floatValue5 - (floatValue17 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + floatValue20)) * 0.5F;
         dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string7, 13.0F, floatValue18 + floatValue17 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F), floatValue4 + floatValue6 * 0.5F, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(0.9F * floatValue12, 16777215));
      }
   }

   private void b(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4, float floatValue5, float floatValue6, float floatValue7) {
      long longValue8 = System.currentTimeMillis();
      this.Y.removeIf(value2x -> longValue8 - value2x.d > value2x.e);
      float floatValue10 = dev.leg1tka.gui.menu.MenuLayout.a(34.0F);
      float floatValue11 = this.ab + this.ad + dev.leg1tka.gui.menu.MenuLayout.a(12.0F);
      if (floatValue11 + floatValue10 > floatValue5 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F)) {
         floatValue11 = floatValue5 - floatValue10 - dev.leg1tka.gui.menu.MenuLayout.a(10.0F);
      }

      for (int intValue12 = this.Y.size() - 1; intValue12 >= 0; intValue12--) {
         AltManagerScreen.b value13 = (AltManagerScreen.b)this.Y.get(intValue12);
         long longValue14 = longValue8 - value13.d;
         float floatValue16 = Math.min(dev.leg1tka.gui.menu.MenuBackdropRenderer.b((float)longValue14 / 260.0F), dev.leg1tka.gui.menu.MenuBackdropRenderer.a((float)(value13.e - longValue14) / 300.0F)) * floatValue6;
         if (!(floatValue16 < 0.01F)) {
            float floatValue17 = dev.leg1tka.gui.menu.MenuLayout.a(14.0F);
            float floatValue18 = dev.leg1tka.gui.menu.MenuLayout.a(7.0F);
            String string19 = "Вернуть";
            float floatValue20 = value13.c != null ? dev.leg1tka.gui.menu.MenuLayout.a(14.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, string19) : 0.0F;
            float floatValue21 = floatValue17 + floatValue18 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F) + dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, value13.a) + floatValue20 + floatValue17;
            float floatValue22 = dev.leg1tka.gui.menu.MenuLayout.b((floatValue4 - floatValue21) * 0.5F);
            float floatValue23 = floatValue11 + (1.0F - floatValue16) * dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
            dev.leg1tka.gui.menu.MenuBackdropRenderer.a(matrixStack1, floatValue22, floatValue23, floatValue21, floatValue10, floatValue10 * 0.5F, floatValue16);
            float floatValue24 = floatValue23 + floatValue10 * 0.5F;
            dev.leg1tka.render.RenderPrimitives.a(matrixStack1, floatValue22 + floatValue17 + floatValue18 * 0.5F, floatValue24, floatValue18, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue16, value13.b));
            dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, value13.a, 13.0F, floatValue22 + floatValue17 + floatValue18 + dev.leg1tka.gui.menu.MenuLayout.a(9.0F), floatValue24, dev.leg1tka.gui.menu.MenuBackdropRenderer.a(floatValue16, 16777215));
            if (value13.c != null) {
               float floatValue25 = floatValue22 + floatValue21 - floatValue17 - dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, string19);
               String string26 = "undo" + intValue12;
               this.X.put(string26, new float[]{floatValue25 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue23, dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, string19) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F), floatValue10});
               float floatValue27 = this.a(string26, this.a((double)intValue2, (double)intValue3, floatValue25 - dev.leg1tka.gui.menu.MenuLayout.a(6.0F), floatValue23, dev.leg1tka.gui.components.MenuTextRenderer.a(13.0F, string19) + dev.leg1tka.gui.menu.MenuLayout.a(12.0F), floatValue10), floatValue7);
               dev.leg1tka.gui.components.MenuTextRenderer.a(matrixStack1, string19, 13.0F, floatValue25, floatValue24, dev.leg1tka.gui.menu.MenuBackdropRenderer.a((0.8F + floatValue27 * 0.2F) * floatValue16, dev.leg1tka.gui.menu.MenuBackdropRenderer.c()));
            }

            floatValue11 += floatValue10 + dev.leg1tka.gui.menu.MenuLayout.a(8.0F);
         }
      }
   }

   private void i() {
      if (this.G != null) {
         float floatValue1 = (float)(System.currentTimeMillis() - this.H) / 420.0F;
         if (floatValue1 >= 1.0F) {
            this.n.b(this.G);
            this.G = null;
         } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            int intValue3 = (int)(floatValue1 * (float)this.G.length());

            for (int intValue4 = 0; intValue4 < this.G.length(); intValue4++) {
               stringBuilder2.append(
                  intValue4 < intValue3
                     ? this.G.charAt(intValue4)
                     : "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_"
                        .charAt(this.I.nextInt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_".length()))
               );
            }

            this.n.b(stringBuilder2.toString());
         }
      }
   }

   private void a(dev.leg1tka.gui.components.MenuIconButton value1) {
      this.m.a(value1 == this.m);
      this.n.a(value1 == this.n);
      this.o.a(value1 == this.o);
      this.p.a(value1 == this.p);
      this.q.a(value1 == this.q);
      if (value1 != null) {
         this.h();
         this.S = -1;
      }
   }

   private dev.leg1tka.gui.components.MenuIconButton j() {
      if (this.O != null) {
         if (this.p.b()) {
            return this.p;
         } else {
            return this.q.b() ? this.q : null;
         }
      } else if (this.o.b() && this.t) {
         return this.o;
      } else if (this.m.b()) {
         return this.m;
      } else {
         return this.n.b() ? this.n : null;
      }
   }

   private dev.leg1tka.accounts.AltAccount k() {
      if (this.G != null) {
         this.n.b(this.G);
         this.G = null;
      }

      String string1 = this.n.a().trim();
      if (string1.isEmpty()) {
         this.a(this.n);
         this.b("Впиши ник");
         return null;
      } else if (string1.length() < 3) {
         this.b(
            "Ник должен быть от 3 до 16 символов"
         );
         return null;
      } else {
         dev.leg1tka.accounts.AltAccount value2 = this.a(string1);
         if (value2 != null) {
            this.s = value2;
            this.b(
               "Такой ник уже есть, выбрал его в списке"
            );
            this.g(value2);
            return value2;
         } else if (AspectDLC.INSTANCE.altStorage == null) {
            return null;
         } else {
            dev.leg1tka.accounts.AltAccount value3 = new dev.leg1tka.accounts.AltAccount(string1, "Offline");
            AspectDLC.INSTANCE.altStorage.a(value3);
            this.s = value3;
            this.n.b("");
            this.u = 0;
            this.Z = this.e();
            this.g(value3);
            this.a("Добавлен " + string1, dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), null);
            return value3;
         }
      }
   }

   private void l() {
      this.G = dev.leg1tka.accounts.AltSessionManager.a();
      this.H = System.currentTimeMillis();
      this.a(this.n);
   }

   private void d(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         if (value1.e() != null && !value1.e().isEmpty() && !"0".equals(value1.e())) {
            dev.leg1tka.accounts.AltSessionManager.a(value1);
         } else {
            dev.leg1tka.accounts.AltSessionManager.a(value1.c());
         }

         value1.b(System.currentTimeMillis());
         this.b();
         this.a("Вы вошли как " + value1.c(), 4906624, null);
      }
   }

   private void e(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         List list2 = this.a();
         int intValue3 = list2.indexOf(value1);
         if (intValue3 >= 0) {
            int intValue4 = this.Z.indexOf(value1);
            list2.remove(intValue3);
            this.b();
            if (this.s == value1) {
               List list5 = this.e();
               this.s = list5.isEmpty() ? null : (dev.leg1tka.accounts.AltAccount)list5.get(Math.max(0, Math.min(intValue4, list5.size() - 1)));
            }

            this.t = false;
            this.a("Удалён " + value1.c(), 16734826, () -> {
               List list3x = this.a();
               if (!list3x.contains(value1)) {
                  list3x.add(Math.min(intValue3, list3x.size()), value1);
                  this.b();
               }

               this.s = value1;
            });
         }
      }
   }

   private void a(dev.leg1tka.accounts.AltAccount value1, dev.leg1tka.accounts.AccountBan value2) {
      if (value1 != null && value2 != null) {
         int intValue3 = value1.a().indexOf(value2);
         value1.a().remove(value2);
         this.b();
         this.a("Бан на " + dev.leg1tka.accounts.ServerReconnectTracker.a(value2.e()) + " снят", 4906624, () -> {
            if (!value1.a().contains(value2)) {
               value1.a().add(Math.max(0, Math.min(intValue3, value1.a().size())), value2);
            }

            this.b();
         });
      }
   }

   private void m() {
      if (System.currentTimeMillis() - this.A >= 3000L) {
         this.A = System.currentTimeMillis();
      } else {
         this.A = 0L;
         List<dev.leg1tka.accounts.AltAccount> list1 = this.a();
         List<dev.leg1tka.accounts.AltAccount> list2 = new ArrayList<>(list1);
         int intValue3 = 0;

         for (dev.leg1tka.accounts.AltAccount value5 : list1) {
            if (!value5.h()) {
               intValue3++;
            }
         }

         if (intValue3 == 0) {
            this.a(
               "Нечего удалять, остались только закреплённые",
               dev.leg1tka.gui.menu.MenuBackdropRenderer.c(),
               null
            );
         } else {
            list1.removeIf(value0 -> !value0.h());
            this.b();
            if (this.s != null && !list1.contains(this.s)) {
               this.s = list1.isEmpty() ? null : (dev.leg1tka.accounts.AltAccount)list1.get(0);
            }

            this.a(
               "Удалено: "
                  + intValue3
                  + ", закреплённые остались",
               16734826,
               () -> {
                  List list2x = this.a();
                  list2x.clear();
                  list2x.addAll(list2);
                  this.b();
               }
            );
         }
      }
   }

   private void f(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         value1.a(!value1.h());
         this.b();
      }
   }

   private void n() {
      if (this.s != null) {
         this.t = true;
         this.o.b(this.s.c());
         this.a(this.o);
         this.o.c();
      }
   }

   private void o() {
      if (this.s == null) {
         this.t = false;
      } else {
         String string1 = this.o.a().trim();
         if (string1.length() < 3) {
            this.b(
               "Ник должен быть от 3 до 16 символов"
            );
         } else {
            dev.leg1tka.accounts.AltAccount value2 = this.a(string1);
            if (value2 != null && value2 != this.s) {
               this.b(
                  "Такой ник уже есть в списке"
               );
            } else {
               String string3 = this.s.c();
               dev.leg1tka.accounts.AccountProfileResolver.d(string3);
               this.s.a(string1);
               if ("Offline".equalsIgnoreCase(this.s.f())) {
                  this.s.b(dev.leg1tka.accounts.AccountProfileResolver.a(string1).toString());
               }

               this.b();
               this.t = false;
               this.a((dev.leg1tka.gui.components.MenuIconButton)null);
               if (!string3.equals(string1)) {
                  this.a("Переименован в " + string1, dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), null);
               }
            }
         }
      }
   }

   private void c(int intValue1) {
      if (!this.Z.isEmpty()) {
         int intValue2 = this.s == null ? -1 : this.Z.indexOf(this.s);
         intValue2 = Math.max(0, Math.min(this.Z.size() - 1, intValue2 + intValue1));
         this.s = (dev.leg1tka.accounts.AltAccount)this.Z.get(intValue2);
         this.t = false;
         this.g(this.s);
      }
   }

   private void g(dev.leg1tka.accounts.AltAccount value1) {
      int intValue2 = this.e().indexOf(value1);
      if (intValue2 >= 0) {
         float floatValue3 = (float)intValue2 * (this.am + this.an);
         if (floatValue3 < this.x) {
            this.x = floatValue3;
         } else if (floatValue3 + this.am > this.x + this.al) {
            this.x = floatValue3 + this.am - this.al;
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      for (int intValue6 = 0; intValue6 < this.Y.size(); intValue6++) {
         if (button == 0 && ((AltManagerScreen.b)this.Y.get(intValue6)).c != null && this.a("undo" + intValue6, mouseX, mouseY)) {
            AltManagerScreen.b value7 = (AltManagerScreen.b)this.Y.remove(intValue6);
            value7.c.run();
            dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
            return true;
         }
      }

      if (this.J != null) {
         for (int intValue11 = 0; intValue11 < this.N.size(); intValue11++) {
            if (button == 0 && this.a("cm:" + intValue11, mouseX, mouseY)) {
               AltManagerScreen.a value17 = (AltManagerScreen.a)this.N.get(intValue11);
               this.J = null;
               value17.act().run();
               dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
               return true;
            }
         }

         this.J = null;
         if (!this.a("cm", mouseX, mouseY)) {
            return true;
         }
      }

      if (this.O != null) {
         if (button != 0) {
            return true;
         } else if (this.p.a(mouseX, mouseY)) {
            this.a(this.p);
            return true;
         } else if (this.q.a(mouseX, mouseY)) {
            this.a(this.q);
            return true;
         } else {
            for (int intValue14 = 0; intValue14 < this.U.size(); intValue14++) {
               if (this.a("bn:sug:" + intValue14, mouseX, mouseY)) {
                  this.p.b((String)this.U.get(intValue14));
                  this.V = "";
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                  return true;
               }
            }

            if (this.a("bn:perm", mouseX, mouseY)) {
               this.Q = false;
               this.V = "";
               dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
               return true;
            } else if (this.a("bn:timed", mouseX, mouseY)) {
               this.Q = true;
               this.V = "";
               dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
               return true;
            } else {
               for (int intValue15 = 0; intValue15 < 3; intValue15++) {
                  if (this.Q && this.a("bn:seg:" + intValue15, mouseX, mouseY)) {
                     this.a((dev.leg1tka.gui.components.MenuIconButton)null);
                     this.h();
                     this.S = intValue15;
                     return true;
                  }
               }

               for (int intValue16 = 0; intValue16 < h.length; intValue16++) {
                  if (this.Q && this.a("bn:pre:" + intValue16, mouseX, mouseY)) {
                     this.h();
                     this.S = -1;
                     System.arraycopy(h[intValue16], 0, this.R, 0, 3);
                     this.V = "";
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                     return true;
                  }
               }

               if (this.a("bn:save", mouseX, mouseY)) {
                  this.h();
                  this.g();
                  dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                  return true;
               } else if (!this.a("bn:cancel", mouseX, mouseY)
                  && !this.a("bn:x", mouseX, mouseY)
                  && this.a(mouseX, mouseY, this.ah, this.ModuleManager, this.Storage, this.ab + this.ad - this.ae - this.ModuleManager)) {
                  this.h();
                  this.S = -1;
                  return true;
               } else {
                  this.O = null;
                  this.S = -1;
                  this.a((dev.leg1tka.gui.components.MenuIconButton)null);
                  if (this.a("bn:cancel", mouseX, mouseY) || this.a("bn:x", mouseX, mouseY)) {
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                  }

                  return true;
               }
            }
         }
      } else if (button == 1) {
         if (this.a(mouseX, mouseY, this.af, this.ModuleRegistry, this.ag, this.al)) {
            for (int intValue13 = 0; intValue13 < this.Z.size(); intValue13++) {
               if (this.a("row:" + intValue13, mouseX, mouseY)) {
                  this.a((dev.leg1tka.accounts.AltAccount)this.Z.get(intValue13), mouseX, mouseY);
                  return true;
               }
            }
         }

         return super.mouseClicked(mouseX, mouseY, button);
      } else if (button != 0) {
         return super.mouseClicked(mouseX, mouseY, button);
      } else if (this.a("back", mouseX, mouseY)) {
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
         this.close();
         return true;
      } else if (this.m.a(mouseX, mouseY)) {
         this.a(this.m);
         return true;
      } else if (this.n.a(mouseX, mouseY)) {
         this.a(this.n);
         return true;
      } else if (this.t && this.o.a(mouseX, mouseY)) {
         this.a(this.o);
         return true;
      } else if (this.a("rsave", mouseX, mouseY)) {
         this.o();
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
         return true;
      } else if (this.a("rcancel", mouseX, mouseY)) {
         this.t = false;
         this.a((dev.leg1tka.gui.components.MenuIconButton)null);
         dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
         return true;
      } else {
         this.a((dev.leg1tka.gui.components.MenuIconButton)null);
         if (this.t) {
            this.t = false;
         }

         String string12 = null;

         for (String string10 : new String[]{
            "cur", "sort", "chip0", "chip1", "chip2", "clear", "add", "rnd", "login", "keep", "rename", "ban", "delete", "cpin", "uuid"
         }) {
            if (this.a(string10, mouseX, mouseY)) {
               string12 = string10;
               break;
            }
         }

         if (string12 != null && string12.equals("clear") && this.a().isEmpty()) {
            string12 = null;
         }

         if (string12 != null) {
            dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
            switch (string12) {
               case "cur":
                  dev.leg1tka.accounts.AltAccount value26 = this.a(this.c());
                  if (value26 != null) {
                     this.u = 0;
                     this.m.b("");
                     this.s = value26;
                     this.Z = this.e();
                     this.g(value26);
                  }
                  break;
               case "sort":
                  this.v = (this.v + 1) % f.length;
                  break;
               case "chip0":
                  this.u = 0;
                  break;
               case "chip1":
                  this.u = 1;
                  break;
               case "chip2":
                  this.u = 2;
                  break;
               case "clear":
                  this.m();
                  break;
               case "add":
                  this.k();
                  break;
               case "rnd":
                  this.l();
                  break;
               case "login":
                  this.d(this.s);
                  break;
               case "keep":
                  String string25 = this.c();
                  if (AspectDLC.INSTANCE.altStorage != null && !string25.isEmpty()) {
                     dev.leg1tka.accounts.AltAccount value27 = new dev.leg1tka.accounts.AltAccount(string25, "Offline");
                     value27.b(System.currentTimeMillis());
                     AspectDLC.INSTANCE.altStorage.a(value27);
                     this.s = value27;
                     this.a("Сохранён " + string25, dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), null);
                  }
                  break;
               case "rename":
                  this.n();
                  break;
               case "ban":
                  this.c(this.s);
                  break;
               case "delete":
                  this.e(this.s);
                  break;
               case "cpin":
                  this.f(this.s);
                  break;
               case "uuid":
                  if (this.s != null && this.client != null) {
                     this.client.keyboard.setClipboard(this.s.d());
                     this.a("UUID скопирован", dev.leg1tka.gui.menu.MenuBackdropRenderer.c(), null);
                  }
            }

            return true;
         } else {
            if (this.s != null) {
               List list19 = this.s.a();

               for (int intValue21 = 0; intValue21 < Math.min(2, list19.size()); intValue21++) {
                  if (this.a("unban:" + intValue21, mouseX, mouseY)) {
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                     this.a(this.s, (dev.leg1tka.accounts.AccountBan)list19.get(intValue21));
                     return true;
                  }
               }
            }

            if (this.a(mouseX, mouseY, this.af, this.ModuleRegistry, this.ag, this.al)) {
               for (int intValue20 = 0; intValue20 < this.Z.size(); intValue20++) {
                  if (this.a("BackTrackOld:" + intValue20, mouseX, mouseY)) {
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                     this.s = (dev.leg1tka.accounts.AltAccount)this.Z.get(intValue20);
                     this.d((dev.leg1tka.accounts.AltAccount)this.Z.get(intValue20));
                     return true;
                  }

                  if (this.a("del:" + intValue20, mouseX, mouseY)) {
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                     this.e((dev.leg1tka.accounts.AltAccount)this.Z.get(intValue20));
                     return true;
                  }

                  if (this.a("pin:" + intValue20, mouseX, mouseY)) {
                     dev.leg1tka.gui.menu.MenuBackdropRenderer.b();
                     this.f((dev.leg1tka.accounts.AltAccount)this.Z.get(intValue20));
                     return true;
                  }

                  if (this.a("row:" + intValue20, mouseX, mouseY)) {
                     dev.leg1tka.accounts.AltAccount value22 = (dev.leg1tka.accounts.AltAccount)this.Z.get(intValue20);
                     long longValue24 = System.currentTimeMillis();
                     if (value22 == this.D && longValue24 - this.B < 320L) {
                        this.d(value22);
                     }

                     this.D = value22;
                     this.B = longValue24;
                     this.s = value22;
                     return true;
                  }
               }

               return true;
            } else {
               return super.mouseClicked(mouseX, mouseY, button);
            }
         }
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.r != null && this.getFocused() == this.r) {
         k = true;
      }

      return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (this.O != null) {
         for (int intValue9 = 0; intValue9 < 3; intValue9++) {
            if (this.Q && this.a("bn:seg:" + intValue9, mouseX, mouseY)) {
               this.h();
               this.a(intValue9, verticalAmount > 0.0 ? 1 : -1);
               return true;
            }
         }

         return true;
      } else if (this.a(mouseX, mouseY, this.af, this.ModuleRegistry, this.ag, this.al)) {
         this.x = this.x - (float)verticalAmount * (this.am + this.an) * 1.5F;
         this.J = null;
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (this.O != null && this.S >= 0) {
         if (chr >= '0' && chr <= '9') {
            if (this.T.length() >= (this.S == 0 ? 3 : 2)) {
               this.T = "";
            }

            this.T = this.T + chr;
         }

         return true;
      } else {
         dev.leg1tka.gui.components.MenuIconButton value3 = this.j();
         if (value3 != null) {
            boolean flag4 = value3.a(chr);
            if (value3 == this.p) {
               this.V = "";
            }

            return flag4;
         } else if (this.O != null) {
            return true;
         } else if (a(chr)) {
            this.a(this.m);
            return this.m.a(chr);
         } else {
            return super.charTyped(chr, modifiers);
         }
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      boolean flag4 = (modifiers & 2) != 0;
      boolean flag5 = (modifiers & 1) != 0;
      boolean flag6 = keyCode == 257 || keyCode == 335;
      dev.leg1tka.gui.components.MenuIconButton value7 = this.j();
      if (this.J != null) {
         if (keyCode == 256) {
            this.J = null;
         }

         return true;
      } else if (this.O != null) {
         if (keyCode == 256) {
            if (this.S >= 0) {
               this.T = "";
               this.S = -1;
            } else {
               this.O = null;
               this.a((dev.leg1tka.gui.components.MenuIconButton)null);
            }

            return true;
         } else if (flag6) {
            this.h();
            this.g();
            return true;
         } else if (this.S >= 0) {
            switch (keyCode) {
               case 258:
               case 262:
                  this.h();
                  this.S = Math.min(2, this.S + 1);
                  break;
               case 259:
                  if (!this.T.isEmpty()) {
                     this.T = this.T.substring(0, this.T.length() - 1);
                  }
               case 260:
               case 261:
               default:
                  break;
               case 263:
                  this.h();
                  this.S = Math.max(0, this.S - 1);
                  break;
               case 264:
                  this.h();
                  this.a(this.S, -1);
                  break;
               case 265:
                  this.h();
                  this.a(this.S, 1);
            }

            return true;
         } else if (keyCode == 258) {
            this.a(value7 == this.p ? this.q : this.p);
            return true;
         } else {
            if (value7 != null) {
               value7.a(keyCode, modifiers);
            }

            return true;
         }
      } else if (flag4 && keyCode == 70) {
         this.a(this.m);
         this.m.c();
         return true;
      } else if (value7 == this.o) {
         if (flag6) {
            this.o();
         } else if (keyCode == 256) {
            this.t = false;
            this.a((dev.leg1tka.gui.components.MenuIconButton)null);
         } else {
            this.o.a(keyCode, modifiers);
         }

         return true;
      } else if (value7 == this.n) {
         if (flag6) {
            dev.leg1tka.accounts.AltAccount value8 = this.k();
            if (flag5 && value8 != null) {
               this.d(value8);
            }
         } else if (keyCode == 256) {
            this.a((dev.leg1tka.gui.components.MenuIconButton)null);
         } else {
            this.n.a(keyCode, modifiers);
         }

         return true;
      } else if (value7 == this.m) {
         if (keyCode == 256) {
            if (!this.m.a().isEmpty()) {
               this.m.b("");
            } else {
               this.a((dev.leg1tka.gui.components.MenuIconButton)null);
            }
         } else if (keyCode == 264) {
            this.c(1);
         } else if (keyCode == 265) {
            this.c(-1);
         } else if (flag6) {
            this.d(this.s);
         } else {
            this.m.a(keyCode, modifiers);
         }

         return true;
      } else {
         switch (keyCode) {
            case 66:
               if (!flag4) {
                  return super.keyPressed(keyCode, scanCode, modifiers);
               }

               this.c(this.s);
               break;
            case 256:
               this.close();
               break;
            case 257:
            case 335:
               this.d(this.s);
               break;
            case 261:
               this.e(this.s);
               break;
            case 264:
               this.c(1);
               break;
            case 265:
               this.c(-1);
               break;
            case 291:
               this.n();
               break;
            default:
               return super.keyPressed(keyCode, scanCode, modifiers);
         }

         return true;
      }
   }

   static record a(String lb, String fam, String ico, int col, Runnable act)  {




}

   static class b {
      final String a;
      final int b;
      final Runnable c;
      final long d = System.currentTimeMillis();
      final long e;

      b(String string1, int intValue2, Runnable runnable3) {
         this.a = string1;
         this.b = intValue2;
         this.c = runnable3;
         this.e = runnable3 != null ? 5200L : 2600L;
      }
   }
}
