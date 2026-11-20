package dev.leg1tka.gui.themes;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.leg1tka.AspectDLC;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class CustomThemeRepository implements dev.leg1tka.api.ClientAccess {
   private float x;
   private float y;
   private float width = 110.0F;
   private float height = 250.0F;
   private boolean open;
   private final dev.leg1tka.api.animation.TimedAnimation openAnim = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 400L);
   private final dev.leg1tka.api.animation.TimedAnimation scrollAnim = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.i, 200L);
   private final dev.leg1tka.api.animation.TimedAnimation pickerAnim = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.n, 250L);
   private final dev.leg1tka.api.animation.TimedAnimation presetScrollAnim = new dev.leg1tka.api.animation.TimedAnimation(dev.leg1tka.api.animation.TimedEasing.i, 180L);
   private float scroll;
   private float maxScroll;
   private float presetScroll;
   private float presetMaxScroll;
   private final ClickGuiScreen parent;
   private static final File THEME_DIR = new File("aspectdlc");
   private static final File THEME_FILE = new File(THEME_DIR, "themes.json");
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   static final String[] COLOR_NAMES = new String[]{
      "Основной",
      "Визуальные модули",
      "Текст",
      "Неактивный текст",
      "Цвет заголовков",
      "Текст заголовков",
      "Слайдер",
      "Круг слайдера",
      "Окно слайдера",
      "Индикатор",
      "Неактивный индикатор",
      "Кнопка",
      "Неактивная кнопка",
      "Разделитель",
      "Поле",
      "Неактивное поле",
      "Текст в подсказках",
      "Фон окон",
      "Цвет иконок",
      "Цвет клиента"
   };
   static final int COLOR_COUNT = COLOR_NAMES.length;
   private static final int[][] PRESETS = new int[][]{
      {-5618433, -15921904},
      {-12276993, -16119272},
      {-12255318, -16115696},
      {-48060, -15071224},
      {-26300, -15069176},
      {-12255250, -16246248},
      {-47958, -15071216},
      {-7846657, -15726054},
      {-8892, -15067128},
      {-12255420, -16246264},
      {-12285697, -16250342}
   };
   private final List<dev.leg1tka.gui.themes.CustomThemeRepository.a> savedThemes = new ArrayList<>();
   private int[] currentColors;
   private int editingSlot = -1;
   private final float[] hsv = new float[3];
   private boolean draggingSV;
   private boolean draggingH;
   private float pickerPopupX;
   private float pickerPopupY;
   private String themeName = "";
   private boolean nameFieldFocused = false;
   private static float HEADER_H;
   private static float ROW_H;
   private static float PRESET_D;
   private static float PRESET_GAP;
   private static float PRESET_ROW_H;
   private static float FIELD_H;
   private static float BTN_H;
   private static float FIXED_BOTTOM_H;
   private static float SV_SIZE;
   private static float HUE_W;
   private static float PICKER_PAD;
   private static float PICKER_W;
   private static float PICKER_H_TOT;

   public CustomThemeRepository(ClickGuiScreen clickGuiScreen1) {
      this.parent = clickGuiScreen1;
      this.currentColors = dev.leg1tka.managers.ThemeStorage.Theme.Custom.a().a();
      this.b();
   }

   static int[] a(int intValue0, int intValue1) {
      int[] intValueArray2 = new int[COLOR_COUNT];
      intValueArray2[0] = intValue1;
      intValueArray2[1] = intValue0;
      intValueArray2[2] = -1513240;
      intValueArray2[3] = -7829351;
      intValueArray2[4] = -16119283;
      intValueArray2[5] = intValue0;
      intValueArray2[6] = intValue0;
      intValueArray2[7] = -1;
      intValueArray2[8] = -14013899;
      intValueArray2[9] = intValue0;
      intValueArray2[10] = -14013899;
      intValueArray2[11] = intValue0;
      intValueArray2[12] = -14013899;
      intValueArray2[13] = -14013899;
      intValueArray2[14] = -15066590;
      intValueArray2[15] = intValue1;
      intValueArray2[16] = -1;
      intValueArray2[17] = intValue1;
      intValueArray2[18] = intValue0;
      intValueArray2[19] = intValue0;
      return intValueArray2;
   }

   private void A() {
      if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
         dev.leg1tka.managers.ThemeStorage.Theme theme1 = AspectDLC.INSTANCE.themeStorage.b();
         if (theme1 != null && theme1 == dev.leg1tka.managers.ThemeStorage.Theme.Custom) {
            dev.leg1tka.gui.themes.ThemePalette value2 = theme1.a();
            int[] intValueArray3 = this.currentColors;
            value2.a(
               intValueArray3[1],
               intValueArray3[0],
               intValueArray3[0],
               intValueArray3[1],
               intValueArray3[2],
               intValueArray3[3],
               intValueArray3[4],
               intValueArray3[5],
               intValueArray3[6],
               intValueArray3[7],
               intValueArray3[8],
               intValueArray3[9],
               intValueArray3[10],
               intValueArray3[11],
               intValueArray3[12],
               intValueArray3[13],
               intValueArray3[14],
               intValueArray3[15],
               intValueArray3[16],
               intValueArray3[17],
               intValueArray3[18],
               intValueArray3[19]
            );
         }
      }
   }

   public void a() {
      try {
         if (!THEME_DIR.exists()) {
            THEME_DIR.mkdirs();
         }

         JsonObject jsonObject1 = new JsonObject();
         JsonArray jsonArray2 = new JsonArray();

         for (int intValue6 : this.currentColors) {
            jsonArray2.add(intValue6);
         }

         jsonObject1.add("colors", jsonArray2);
         JsonArray jsonArray13 = new JsonArray();

         for (dev.leg1tka.gui.themes.CustomThemeRepository.a value15 : this.savedThemes) {
            JsonObject jsonObject16 = new JsonObject();
            jsonObject16.addProperty("name", value15.a);
            JsonArray jsonArray7 = new JsonArray();

            for (int intValue11 : value15.b) {
               jsonArray7.add(intValue11);
            }

            jsonObject16.add("colors", jsonArray7);
            jsonArray13.add(jsonObject16);
         }

         jsonObject1.add("savedThemes", jsonArray13);
         Files.writeString(THEME_FILE.toPath(), GSON.toJson(jsonObject1));
      } catch (IOException iOException12) {
         iOException12.printStackTrace();
      }
   }

   public void b() {
      if (THEME_FILE.exists()) {
         try {
            JsonObject jsonObject1 = JsonParser.parseString(Files.readString(THEME_FILE.toPath())).getAsJsonObject();
            if (jsonObject1.has("savedThemes")) {
               this.savedThemes.clear();

               for (JsonElement jsonElement3 : jsonObject1.getAsJsonArray("savedThemes")) {
                  JsonObject jsonObject4 = jsonElement3.getAsJsonObject();
                  String string5 = jsonObject4.get("name").getAsString();
                  JsonArray jsonArray6 = jsonObject4.getAsJsonArray("colors");
                  int[] intValueArray7 = new int[COLOR_COUNT];

                  for (int intValue8 = 0; intValue8 < Math.min(jsonArray6.size(), COLOR_COUNT); intValue8++) {
                     intValueArray7[intValue8] = jsonArray6.get(intValue8).getAsInt();
                  }

                  this.savedThemes.add(new dev.leg1tka.gui.themes.CustomThemeRepository.a(string5, intValueArray7));
               }
            }
         } catch (Exception exception9) {
            exception9.printStackTrace();
         }
      }
   }

   private float B() {
      return this.height - 20.0F - 58.0F;
   }

   private float C() {
      return (float)COLOR_COUNT * 16.0F + 2.0F;
   }

   private void D() {
      this.maxScroll = Math.max(0.0F, this.C() - this.B());
      if (this.maxScroll > 0.0F) {
         this.scroll = MathHelper.clamp(this.scroll, -this.maxScroll, 0.0F);
      } else {
         this.scroll = 0.0F;
      }
   }

   private int E() {
      return PRESETS.length + this.savedThemes.size();
   }

   private void F() {
      float floatValue1 = this.width - 8.0F;
      float floatValue2 = (float)this.E() * 9.0F + (float)(this.E() - 1) * 1.5F;
      this.presetMaxScroll = Math.max(0.0F, floatValue2 - floatValue1);
      if (this.presetMaxScroll > 0.0F) {
         this.presetScroll = MathHelper.clamp(this.presetScroll, -this.presetMaxScroll, 0.0F);
      } else {
         this.presetScroll = 0.0F;
      }
   }

   public void a(MatrixStack matrixStack1, int intValue2, int intValue3, float floatValue4) {
      this.openAnim.a(this.open);
      if (!(this.openAnim.d() < 0.01F)) {
         float floatValue5 = this.openAnim.d();
         float floatValue6 = this.x - this.width * (1.0F - floatValue5);
         int intValue7 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5);
         dev.leg1tka.render.RenderPrimitives.e(matrixStack1, floatValue6, this.y, this.width, this.height, 8.0F, 20.0F, dev.leg1tka.api.utils.text.ColorUtils.a(75, 75, 75, intValue7));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6, this.y, this.width, this.height, 8.0F, dev.leg1tka.api.utils.text.ColorUtils.d(dev.leg1tka.api.utils.text.ColorUtils.u(), dev.leg1tka.gui.themes.ThemeAccentResolver.a(30.0F * floatValue5)));
         dev.leg1tka.render.RenderPrimitives.c(matrixStack1, floatValue6, this.y, this.width, 20.0F, 8.0F, 8.0F, 0.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(80.0F * floatValue5)));
         dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("sf_regular", 17);
         String string9 = "Theme Editor";
         if (value8 != null) {
            float floatValue10 = value8.a(string9);
            dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value8, string9, floatValue6 + this.width / 2.0F - floatValue10 / 2.0F, this.y + 6.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue7));
         }

         this.D();
         this.F();
         this.scrollAnim.a(this.scroll);
         this.presetScrollAnim.a(this.presetScroll);
         float floatValue30 = this.y + 20.0F;
         float floatValue11 = this.B();
         dev.leg1tka.render.ScissorStack.a();
         dev.leg1tka.render.ScissorStack.a((double)floatValue6, (double)floatValue30, (double)this.width, (double)floatValue11);
         float floatValue12 = this.scrollAnim.d();
         float floatValue13 = floatValue30 + 2.0F + floatValue12;

         for (int intValue14 = 0; intValue14 < COLOR_COUNT; intValue14++) {
            this.a(matrixStack1, intValue14, floatValue6 + 3.5F, floatValue13, this.width - 7.0F, intValue2, intValue3, floatValue5);
            floatValue13 += 16.0F;
         }

         dev.leg1tka.render.ScissorStack.b();
         float floatValue31 = this.y + this.height - 58.0F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6 + 4.0F, floatValue31, this.width - 8.0F, 0.5F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(25.0F * floatValue5)));
         floatValue31 += 3.0F;
         float floatValue15 = floatValue6 + 4.0F;
         float floatValue16 = this.width - 8.0F;
         float floatValue17 = this.presetScrollAnim.d();
         dev.leg1tka.render.ScissorStack.a();
         dev.leg1tka.render.ScissorStack.a((double)floatValue15, (double)floatValue31, (double)floatValue16, 13.0);
         float floatValue18 = floatValue15 + floatValue17;
         float floatValue19 = floatValue31 + 2.0F;

         for (int[] intValueArray23 : PRESETS) {
            boolean flag24 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue18, (double)floatValue19, 9.0, 9.0);
            if (flag24) {
               dev.leg1tka.render.hud.HudVisibilityState.a();
            }

            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18, floatValue19, 9.0F, 9.0F, 4.0F, dev.leg1tka.api.utils.text.ColorUtils.d(intValueArray23[0], intValue7));
            floatValue18 += 10.5F;
         }

         for (dev.leg1tka.gui.themes.CustomThemeRepository.a value38 : this.savedThemes) {
            boolean flag40 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue18, (double)floatValue19, 9.0, 9.0);
            if (flag40) {
               dev.leg1tka.render.hud.HudVisibilityState.a();
            }

            dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue18, floatValue19, 9.0F, 9.0F, 4.0F, dev.leg1tka.api.utils.text.ColorUtils.d(value38.b[1], intValue7));
            floatValue18 += 10.5F;
         }

         dev.leg1tka.render.ScissorStack.b();
         floatValue31 += 15.0F;
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue6 + 4.0F, floatValue31, this.width - 8.0F, 0.5F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(25.0F * floatValue5)));
         floatValue31 += 3.0F;
         float floatValue37 = floatValue6 + 4.0F;
         float floatValue39 = this.width - 8.0F;
         int intValue41 = dev.leg1tka.api.utils.text.ColorUtils.d(this.currentColors[1], dev.leg1tka.gui.themes.ThemeAccentResolver.a(55.0F * floatValue5));
         int intValue42 = dev.leg1tka.api.utils.text.ColorUtils.d(this.currentColors[1], dev.leg1tka.gui.themes.ThemeAccentResolver.a(90.0F * floatValue5));
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue37, floatValue31, floatValue39, 16.0F, 3.0F, intValue41);
         dev.leg1tka.render.font.MsdfTextRenderer value43 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
         String string25 = this.themeName.isEmpty() ? "Название" : this.themeName;
         int intValue26 = this.themeName.isEmpty() ? dev.leg1tka.api.utils.text.ColorUtils.a(200, 200, 200, dev.leg1tka.gui.themes.ThemeAccentResolver.a(160.0F * floatValue5)) : dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5));
         dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value43, string25, floatValue37 + 4.0F, floatValue31 + 4.0F, intValue26);
         floatValue31 += 18.0F;
         boolean flag27 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue2, (double)intValue3, (double)floatValue37, (double)floatValue31, (double)floatValue39, 16.0);
         if (flag27) {
            dev.leg1tka.render.hud.HudVisibilityState.a();
         }

         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue37, floatValue31, floatValue39, 16.0F, 3.0F, flag27 ? intValue42 : intValue41);
         String string28 = "Создать тему";
         if (value43 != null) {
            float floatValue29 = value43.a(string28);
            dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value43, string28, floatValue37 + floatValue39 / 2.0F - floatValue29 / 2.0F, floatValue31 + 4.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue5)));
         }

         this.pickerAnim.a(this.editingSlot >= 0);
         if (this.pickerAnim.d() > 0.01F) {
            this.a(matrixStack1, this.pickerPopupX, this.pickerPopupY, intValue2, intValue3, this.pickerAnim.d() * floatValue5);
         }
      }
   }

   private void a(MatrixStack matrixStack1, int intValue2, float floatValue3, float floatValue4, float floatValue5, int intValue6, int intValue7, float floatValue8) {
      boolean flag9 = dev.leg1tka.api.utils.math.RectangleHitTest.a((double)intValue6, (double)intValue7, (double)floatValue3, (double)floatValue4, (double)floatValue5, 16.0);
      boolean flag10 = this.editingSlot == intValue2;
      if (flag9 || flag10) {
         dev.leg1tka.render.hud.HudVisibilityState.a();
      }

      if (flag10) {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3, floatValue4, floatValue5, 16.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(60, 60, 80, dev.leg1tka.gui.themes.ThemeAccentResolver.a(60.0F * floatValue8)));
      }

      dev.leg1tka.render.font.MsdfTextRenderer value11 = dev.leg1tka.render.font.FontCache.a("sf_regular", 14);
      dev.leg1tka.gui.components.SettingWidget.a(matrixStack1, value11, COLOR_NAMES[intValue2], floatValue3 + 3.0F, floatValue4 + 4.0F, dev.leg1tka.api.utils.text.ColorUtils.a(210, 210, 210, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue8)));
      float floatValue12 = 8.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue3 + floatValue5 - floatValue12 - 3.0F, floatValue4 + 8.0F - floatValue12 / 2.0F, floatValue12, floatValue12, floatValue12 / 2.0F - 1.0F, dev.leg1tka.api.utils.text.ColorUtils.d(this.currentColors[intValue2], dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue8)));
   }

   private void a(MatrixStack matrixStack1, float floatValue2, float floatValue3, int intValue4, int intValue5, float floatValue6) {
      int intValue7 = dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue6);
      float floatValue8 = 4.0F;
      dev.leg1tka.render.RenderPrimitives.e(matrixStack1, floatValue2 - floatValue8, floatValue3 - floatValue8, 70.0F + floatValue8 * 2.0F, 60.0F + floatValue8 * 2.0F, 5.0F, 20.0F, dev.leg1tka.api.utils.text.ColorUtils.a(75, 75, 75, dev.leg1tka.gui.themes.ThemeAccentResolver.a(255.0F * floatValue6)));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2 - floatValue8, floatValue3 - floatValue8, 70.0F + floatValue8 * 2.0F, 60.0F + floatValue8 * 2.0F, 5.0F, dev.leg1tka.api.utils.text.ColorUtils.a(14, 14, 16, dev.leg1tka.gui.themes.ThemeAccentResolver.a(130.0F * floatValue6)));
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.d(Color.HSBtoRGB(this.hsv[0], 1.0F, 1.0F), intValue7);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue7);
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, 0);
      int intValue12 = dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, intValue7);
      int intValue13 = dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, 0);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, 60.0F, 60.0F, 2.0F, intValue9);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, 60.0F, 60.0F, 2.0F, intValue10, intValue11, intValue10, intValue11);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue2, floatValue3, 60.0F, 60.0F, 2.0F, intValue13, intValue13, intValue12, intValue12);
      float floatValue14 = floatValue2 + this.hsv[1] * 60.0F;
      float floatValue15 = floatValue3 + (1.0F - this.hsv[2]) * 60.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue14 - 3.0F, floatValue15 - 3.0F, 6.0F, 6.0F, 3.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue6)));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue14 - 2.0F, floatValue15 - 2.0F, 4.0F, 4.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue7));
      float floatValue16 = floatValue2 + 60.0F + 4.0F;

      for (float floatValue17 = 0.0F; floatValue17 <= 60.0F; floatValue17 += 0.5F) {
         dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue16, floatValue3 + floatValue17, 6.0F, 1.0F, 0.0F, dev.leg1tka.api.utils.text.ColorUtils.d(Color.HSBtoRGB(floatValue17 / 60.0F, 1.0F, 1.0F), intValue7));
      }

      float floatValue18 = floatValue3 + this.hsv[0] * 60.0F;
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue16 - 1.5F, floatValue18 - 2.5F, 9.0F, 5.0F, 2.0F, dev.leg1tka.api.utils.text.ColorUtils.a(0, 0, 0, dev.leg1tka.gui.themes.ThemeAccentResolver.a(180.0F * floatValue6)));
      dev.leg1tka.render.RenderPrimitives.b(matrixStack1, floatValue16 - 0.5F, floatValue18 - 1.5F, 7.0F, 3.0F, 1.0F, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue7));
      if (this.draggingSV) {
         this.hsv[1] = Math.max(0.0F, Math.min(1.0F, ((float)intValue4 - floatValue2) / 60.0F));
         this.hsv[2] = 1.0F - Math.max(0.0F, Math.min(1.0F, ((float)intValue5 - floatValue3) / 60.0F));
         this.G();
      } else if (this.draggingH) {
         this.hsv[0] = Math.max(0.0F, Math.min(1.0F, ((float)intValue5 - floatValue3) / 60.0F));
         this.G();
      }
   }

   private void G() {
      if (this.editingSlot >= 0) {
         this.currentColors[this.editingSlot] = Color.HSBtoRGB(this.hsv[0], this.hsv[1], this.hsv[2]) | 0xFF000000;
         if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
            AspectDLC.INSTANCE.themeStorage.a(dev.leg1tka.managers.ThemeStorage.Theme.Custom);
         }

         this.A();
      }
   }

   private void c(int intValue1) {
      Color color2 = new Color(this.currentColors[intValue1], true);
      Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), this.hsv);
   }

   public void a(double doubleValue1, double doubleValue3, int intValue5) {
      if (this.open && !(this.openAnim.d() < 0.9F)) {
         float floatValue6 = this.openAnim.d();
         float floatValue7 = this.x - this.width * (1.0F - floatValue6);
         if (this.editingSlot >= 0 && this.pickerAnim.d() > 0.5F) {
            float floatValue8 = this.pickerPopupX;
            float floatValue9 = this.pickerPopupY;
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue8, (double)floatValue9, 60.0, 60.0) && intValue5 == 0) {
               this.draggingSV = true;
               return;
            }

            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)(floatValue8 + 60.0F + 4.0F), (double)floatValue9, 10.0, 60.0) && intValue5 == 0) {
               this.draggingH = true;
               return;
            }

            if (!dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)(floatValue8 - 4.0F), (double)(floatValue9 - 4.0F), 78.0, 68.0)) {
               this.editingSlot = -1;
            }
         }

         float floatValue20 = this.y + 20.0F;
         float floatValue21 = this.B();
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue7, (double)floatValue20, (double)this.width, (double)floatValue21)) {
            float floatValue10 = this.scrollAnim.d();
            float floatValue11 = floatValue20 + 2.0F + floatValue10;

            for (int intValue12 = 0; intValue12 < COLOR_COUNT; intValue12++) {
               float floatValue13 = floatValue7 + 3.5F;
               float floatValue14 = this.width - 7.0F;
               if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue13, (double)floatValue11, (double)floatValue14, 16.0) && intValue5 == 0) {
                  this.editingSlot = this.editingSlot == intValue12 ? -1 : intValue12;
                  if (this.editingSlot >= 0) {
                     this.c(intValue12);
                     this.pickerPopupX = floatValue7 + this.width + 4.0F;
                     this.pickerPopupY = floatValue11 + 8.0F - 30.0F;
                  }

                  return;
               }

               floatValue11 += 16.0F;
            }
         }

         float floatValue22 = this.y + this.height - 58.0F + 1.0F + 2.0F;
         float floatValue25 = floatValue7 + 4.0F;
         float floatValue26 = this.width - 8.0F;
         float floatValue27 = this.presetScrollAnim.d();
         float floatValue28 = floatValue25 + floatValue27;
         float floatValue15 = floatValue22 + 2.0F;

         for (int[] intValueArray19 : PRESETS) {
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue28, (double)floatValue15, 9.0, 9.0) && floatValue28 >= floatValue25 && floatValue28 + 9.0F <= floatValue25 + floatValue26 && intValue5 == 0) {
               this.currentColors = a(intValueArray19[0], intValueArray19[1]);
               if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
                  AspectDLC.INSTANCE.themeStorage.a(dev.leg1tka.managers.ThemeStorage.Theme.Custom);
               }

               this.A();
               this.a();
               return;
            }

            floatValue28 += 10.5F;
         }

         for (dev.leg1tka.gui.themes.CustomThemeRepository.a value31 : this.savedThemes) {
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue28, (double)floatValue15, 9.0, 9.0) && floatValue28 >= floatValue25 && floatValue28 + 9.0F <= floatValue25 + floatValue26 && intValue5 == 0) {
               this.currentColors = (int[])value31.b.clone();
               if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.themeStorage != null) {
                  AspectDLC.INSTANCE.themeStorage.a(dev.leg1tka.managers.ThemeStorage.Theme.Custom);
               }

               this.A();
               this.a();
               return;
            }

            floatValue28 += 10.5F;
         }

         floatValue22 += 18.0F;
         float floatValue30 = floatValue7 + 4.0F;
         float floatValue32 = this.width - 8.0F;
         this.nameFieldFocused = dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue30, (double)floatValue22, (double)floatValue32, 16.0) && intValue5 == 0;
         floatValue22 += 18.0F;
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue30, (double)floatValue22, (double)floatValue32, 16.0) && intValue5 == 0) {
            String string33 = this.themeName.trim().isEmpty() ? "Theme " + (this.savedThemes.size() + 1) : this.themeName.trim();
            this.savedThemes.add(new dev.leg1tka.gui.themes.CustomThemeRepository.a(string33, (int[])this.currentColors.clone()));
            this.a();
            this.themeName = "";
            this.nameFieldFocused = false;
         }
      }
   }

   public void b(double doubleValue1, double doubleValue3, int intValue5) {
      if (this.draggingSV || this.draggingH) {
         this.a();
      }

      this.draggingSV = false;
      this.draggingH = false;
   }

   public void a(double doubleValue1, double doubleValue3, double doubleValue5, double doubleValue7) {
      if (this.open) {
         float floatValue9 = this.openAnim.d();
         float floatValue10 = this.x - this.width * (1.0F - floatValue9);
         float floatValue11 = this.y + this.height - 58.0F + 1.0F + 2.0F;
         if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)(floatValue10 + 4.0F), (double)floatValue11, (double)(this.width - 8.0F), 13.0)) {
            this.presetScroll += (float)(doubleValue7 * 12.0);
            this.F();
         } else {
            if (dev.leg1tka.api.utils.math.RectangleHitTest.a(doubleValue1, doubleValue3, (double)floatValue10, (double)(this.y + 20.0F), (double)this.width, (double)this.B())) {
               this.scroll += (float)(doubleValue7 * 20.0);
               this.D();
            }
         }
      }
   }

   public void a(char character1) {
      if (this.nameFieldFocused) {
         if (character1 >= ' ' && this.themeName.length() < 24) {
            this.themeName = this.themeName + character1;
         }
      }
   }

   public void a(int intValue1) {
      if (this.nameFieldFocused) {
         if (intValue1 == 259 && !this.themeName.isEmpty()) {
            this.themeName = this.themeName.substring(0, this.themeName.length() - 1);
         }

         if (intValue1 == 256) {
            this.nameFieldFocused = false;
         }
      }
   }

   @Generated
   public float c() {
      return this.x;
   }

   @Generated
   public float d() {
      return this.y;
   }

   @Generated
   public float e() {
      return this.width;
   }

   @Generated
   public float f() {
      return this.height;
   }

   @Generated
   public boolean g() {
      return this.open;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation h() {
      return this.openAnim;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation i() {
      return this.scrollAnim;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation j() {
      return this.pickerAnim;
   }

   @Generated
   public dev.leg1tka.api.animation.TimedAnimation k() {
      return this.presetScrollAnim;
   }

   @Generated
   public float l() {
      return this.scroll;
   }

   @Generated
   public float m() {
      return this.maxScroll;
   }

   @Generated
   public float n() {
      return this.presetScroll;
   }

   @Generated
   public float o() {
      return this.presetMaxScroll;
   }

   @Generated
   public ClickGuiScreen p() {
      return this.parent;
   }

   @Generated
   public List<dev.leg1tka.gui.themes.CustomThemeRepository.a> q() {
      return this.savedThemes;
   }

   @Generated
   public int[] r() {
      return this.currentColors;
   }

   @Generated
   public int s() {
      return this.editingSlot;
   }

   @Generated
   public float[] t() {
      return this.hsv;
   }

   @Generated
   public boolean u() {
      return this.draggingSV;
   }

   @Generated
   public boolean v() {
      return this.draggingH;
   }

   @Generated
   public float w() {
      return this.pickerPopupX;
   }

   @Generated
   public float x() {
      return this.pickerPopupY;
   }

   @Generated
   public String y() {
      return this.themeName;
   }

   @Generated
   public boolean z() {
      return this.nameFieldFocused;
   }

   @Generated
   public void a(float floatValue1) {
      this.x = floatValue1;
   }

   @Generated
   public void b(float floatValue1) {
      this.y = floatValue1;
   }

   @Generated
   public void c(float floatValue1) {
      this.width = floatValue1;
   }

   @Generated
   public void d(float floatValue1) {
      this.height = floatValue1;
   }

   @Generated
   public void a(boolean flag1) {
      this.open = flag1;
   }

   @Generated
   public void e(float floatValue1) {
      this.scroll = floatValue1;
   }

   @Generated
   public void f(float floatValue1) {
      this.maxScroll = floatValue1;
   }

   @Generated
   public void g(float floatValue1) {
      this.presetScroll = floatValue1;
   }

   @Generated
   public void h(float floatValue1) {
      this.presetMaxScroll = floatValue1;
   }

   @Generated
   public void a(int[] intValueArray1) {
      this.currentColors = intValueArray1;
   }

   @Generated
   public void b(int intValue1) {
      this.editingSlot = intValue1;
   }

   @Generated
   public void b(boolean flag1) {
      this.draggingSV = flag1;
   }

   @Generated
   public void c(boolean flag1) {
      this.draggingH = flag1;
   }

   @Generated
   public void i(float floatValue1) {
      this.pickerPopupX = floatValue1;
   }

   @Generated
   public void j(float floatValue1) {
      this.pickerPopupY = floatValue1;
   }

   @Generated
   public void a(String string1) {
      this.themeName = string1;
   }

   @Generated
   public void d(boolean flag1) {
      this.nameFieldFocused = flag1;
   }

   static class a {
      String a;
      int[] b;

      a(String string1, int[] intValueArray2) {
         this.a = string1;
         this.b = intValueArray2;
      }
   }
}
