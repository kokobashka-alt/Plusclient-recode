package dev.leg1tka.managers;


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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import dev.leg1tka.AspectDLC;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents.EndTick;

public class ConfigStorage {
   private static final String EXT = ".aspect";
   private static final int AUTOSAVE_TICKS = 20;
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
   public String currentConfig = "default";
   private final AtomicBoolean saved = new AtomicBoolean(false);
   private final JsonObject defaults;
   private String lastConfig = "";
   private String lastGlobals = "";
   private int autoTicks;
   private static final Map RENAMED_SETTINGS = Map.of(
      "Через стены GRIM",
      "Бить через стены GRIM"
   );

   public ConfigStorage() {
      this.defaults = this.e(this.currentConfig);

      try {
         this.d();
      } catch (Exception exception3) {
         exception3.printStackTrace(System.err);
      }

      try {
         this.b(this.currentConfig);
      } catch (Exception exception2) {
         exception2.printStackTrace(System.err);
      }

      this.b();
      ClientTickEvents.END_CLIENT_TICK.register((EndTick)minecraftClient1 -> {
         if (++this.autoTicks >= 20) {
            this.autoTicks = 0;
            this.b();
         }
      });
   }

   public void a() {
      if (this.saved.compareAndSet(false, true)) {
         this.b();
      }
   }

   public synchronized void b() {
      try {
         String string1 = GSON.toJson(this.e(this.currentConfig));
         if (!string1.equals(this.lastConfig)) {
            a(this.d(this.currentConfig), string1);
            this.lastConfig = string1;
         }
      } catch (Exception exception3) {
         exception3.printStackTrace(System.err);
      }

      try {
         String string4 = GSON.toJson(this.e());
         if (!string4.equals(this.lastGlobals)) {
            this.c(string4);
         }
      } catch (Exception exception2) {
         exception2.printStackTrace(System.err);
      }
   }

   public synchronized void a(String string1) throws Exception {
      String string2 = GSON.toJson(this.e(string1));
      a(this.d(string1), string2);
      this.currentConfig = string1;
      this.lastConfig = string2;
      this.c();
   }

   public synchronized boolean b(String string1) throws Exception {
      File file2 = this.d(string1);
      if (!file2.exists()) {
         return false;
      } else {
         String string3 = Files.readString(file2.toPath(), StandardCharsets.UTF_8);
         JsonObject jsonObject4 = JsonParser.parseString(string3).getAsJsonObject();
         this.a(jsonObject4);
         this.currentConfig = string1;
         this.lastConfig = string3;
         this.b();
         return true;
      }
   }

   public synchronized void c() throws Exception {
      this.c(GSON.toJson(this.e()));
   }

   private void c(String string1) throws IOException {
      a(new File(AspectDLC.INSTANCE.globalsDir, "globals.aspect"), string1);
      this.lastGlobals = string1;

      try {
         a(new File("game", "globals.aspect"), string1);
      } catch (Exception exception3) {
      }
   }

   public synchronized void d() throws Exception {
      File file1 = new File(AspectDLC.INSTANCE.globalsDir, "globals.aspect");
      if (file1.exists()) {
         String string2 = Files.readString(file1.toPath(), StandardCharsets.UTF_8);
         JsonObject jsonObject3 = JsonParser.parseString(string2).getAsJsonObject();
         this.lastGlobals = string2;
         if (jsonObject3.has("config")) {
            this.currentConfig = jsonObject3.get("config").getAsString();
         }

         if (jsonObject3.has("draggables")) {
            JsonObject jsonObject4 = jsonObject3.get("draggables").getAsJsonObject();

            for (String string6 : jsonObject4.keySet()) {
               JsonObject jsonObject7 = jsonObject4.get(string6).getAsJsonObject();
               if (jsonObject7.has("y")) {
                  dev.leg1tka.api.utils.security.ClientIntegrityState.a(string6, jsonObject7.get("y").getAsDouble());
               }
            }
         }

         if (jsonObject3.has("friends")) {
            for (JsonElement jsonElement14 : jsonObject3.get("friends").getAsJsonArray()) {
               if (!AspectDLC.INSTANCE.friendStorage.c(jsonElement14.getAsString())) {
                  AspectDLC.INSTANCE.friendStorage.a(jsonElement14.getAsString());
               }
            }
         }

         if (jsonObject3.has("staffs")) {
            for (JsonElement jsonElement15 : jsonObject3.get("staffs").getAsJsonArray()) {
               if (!AspectDLC.INSTANCE.staffStorage.c(jsonElement15.getAsString())) {
                  AspectDLC.INSTANCE.staffStorage.a(jsonElement15.getAsString());
               }
            }
         }

         if (jsonObject3.has("macros")) {
            for (JsonElement jsonElement16 : jsonObject3.get("macros").getAsJsonArray()) {
               try {
                  int intValue8;
                  String string17;
                  String string18;
                  if (jsonElement16.isJsonObject()) {
                     JsonObject jsonObject9 = jsonElement16.getAsJsonObject();
                     string17 = jsonObject9.has("name") ? jsonObject9.get("name").getAsString() : "";
                     string18 = jsonObject9.has("command") ? jsonObject9.get("command").getAsString() : "";
                     intValue8 = jsonObject9.has("key") ? jsonObject9.get("key").getAsInt() : -1;
                  } else {
                     String[] stringArray19 = jsonElement16.getAsString().split(":", 3);
                     if (stringArray19.length < 3) {
                        continue;
                     }

                     string17 = stringArray19[0];
                     string18 = stringArray19[1];
                     intValue8 = Integer.parseInt(stringArray19[2]);
                  }

                  if (!string17.isBlank() && AspectDLC.INSTANCE.macroStorage.a(string17) == null) {
                     AspectDLC.INSTANCE.macroStorage.a(new dev.leg1tka.commands.MacroBinding(string17, string18, new dev.leg1tka.settings.KeybindSetting("bind", intValue8)));
                  }
               } catch (Exception exception10) {
               }
            }
         }
      }
   }

   private File d(String string1) {
      return new File(AspectDLC.INSTANCE.configsDir, string1 + ".aspect");
   }

   private static void a(File file0, String string1) throws IOException {
      File file2 = file0.getParentFile();
      if (file2 != null && !file2.exists()) {
         file2.mkdirs();
      }

      File file3 = new File(file0.getPath() + ".tmp");
      Files.writeString(file3.toPath(), string1, StandardCharsets.UTF_8);

      try {
         Files.move(file3.toPath(), file0.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException5) {
         Files.move(file3.toPath(), file0.toPath(), StandardCopyOption.REPLACE_EXISTING);
      }
   }

   private JsonObject e(String string1) {
      JsonObject jsonObject2 = new JsonObject();
      jsonObject2.add("config", new JsonPrimitive(string1));
      jsonObject2.add("theme", new JsonPrimitive(AspectDLC.INSTANCE.themeStorage.b().name()));
      jsonObject2.add("language", new JsonPrimitive(AspectDLC.INSTANCE.localizationStorage.a().name()));
      jsonObject2.add("customTheme", this.f());
      jsonObject2.add("modules", this.g());
      jsonObject2.add("draggables", this.i());
      jsonObject2.add("hud", this.h());
      return jsonObject2;
   }

   private JsonObject e() {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.add("config", new JsonPrimitive(this.currentConfig));
      JsonObject jsonObject2 = new JsonObject();
      JsonObject jsonObject3 = new JsonObject();
      jsonObject3.add("x", new JsonPrimitive(7.000838));
      jsonObject3.add("y", new JsonPrimitive(dev.leg1tka.api.utils.security.ClientIntegrityState.a() ? 48.500058 : 48.500057));
      jsonObject2.add("ArrayList", jsonObject3);
      jsonObject1.add("draggables", jsonObject2);
      JsonArray jsonArray4 = new JsonArray();
      AspectDLC.INSTANCE.friendStorage.c().forEach(jsonArray4::add);
      jsonObject1.add("friends", jsonArray4);
      JsonArray jsonArray5 = new JsonArray();
      AspectDLC.INSTANCE.staffStorage.c().forEach(jsonArray5::add);
      jsonObject1.add("staffs", jsonArray5);
      JsonArray jsonArray6 = new JsonArray();
      AspectDLC.INSTANCE.macroStorage.c().forEach(value1x -> {
         JsonObject jsonObject2x = new JsonObject();
         jsonObject2x.addProperty("name", value1x.a());
         jsonObject2x.addProperty("command", value1x.b());
         jsonObject2x.addProperty("key", value1x.c().getKey());
         jsonArray6.add(jsonObject2x);
      });
      jsonObject1.add("macros", jsonArray6);
      return jsonObject1;
   }

   private void a(JsonObject jsonObject1) {
      JsonObject jsonObject2 = this.defaults.deepCopy();
      a(jsonObject2, jsonObject1);
      if (jsonObject2.has("customTheme")) {
         this.b(jsonObject2.get("customTheme").getAsJsonObject());
      }

      String string3 = jsonObject2.get("theme").getAsString();

      for (dev.leg1tka.managers.ThemeStorage.Theme theme7 : dev.leg1tka.managers.ThemeStorage.Theme.values()) {
         if (theme7.name().equals(string3)) {
            AspectDLC.INSTANCE.themeStorage.a(theme7);
            break;
         }
      }

      try {
         AspectDLC.INSTANCE.localizationStorage.a(dev.leg1tka.managers.LocalizationStorage.Language.valueOf(jsonObject2.get("language").getAsString()));
      } catch (Exception exception8) {
      }

      this.e(jsonObject2.get("draggables").getAsJsonObject());
      JsonObject jsonObject9 = jsonObject1.has("modules") && jsonObject1.get("modules").isJsonObject() ? jsonObject1.get("modules").getAsJsonObject() : new JsonObject();
      this.b(jsonObject2.get("modules").getAsJsonObject(), jsonObject9);
      this.d(jsonObject2.get("hud").getAsJsonObject());
   }

   private static void a(JsonObject jsonObject0, JsonObject jsonObject1) {
      for (Entry entry3 : jsonObject1.entrySet()) {
         JsonElement jsonElement4 = jsonObject0.get((String)entry3.getKey());
         if (jsonElement4 != null && jsonElement4.isJsonObject() && ((JsonElement)entry3.getValue()).isJsonObject()) {
            a(jsonElement4.getAsJsonObject(), ((JsonElement)entry3.getValue()).getAsJsonObject());
         } else {
            jsonObject0.add((String)entry3.getKey(), ((JsonElement)entry3.getValue()).deepCopy());
         }
      }
   }

   private JsonObject f() {
      dev.leg1tka.gui.themes.ThemePalette value1 = dev.leg1tka.managers.ThemeStorage.Theme.Custom.a();
      JsonObject jsonObject2 = new JsonObject();
      JsonArray jsonArray3 = new JsonArray();
      if (value1.c != null) {
         for (int intValue7 : value1.c) {
            jsonArray3.add(intValue7);
         }
      }

      JsonArray jsonArray9 = new JsonArray();

      for (int intValue8 : value1.a()) {
         jsonArray9.add(intValue8);
      }

      jsonObject2.add("base", jsonArray3);
      jsonObject2.add("colors", jsonArray9);
      return jsonObject2;
   }

   private void b(JsonObject jsonObject1) {
      try {
         dev.leg1tka.gui.themes.ThemePalette value2 = dev.leg1tka.managers.ThemeStorage.Theme.Custom.a();
         JsonArray jsonArray3 = jsonObject1.getAsJsonArray("base");
         if (jsonArray3 != null && !jsonArray3.isEmpty()) {
            int[] intValueArray4 = new int[jsonArray3.size()];

            for (int intValue5 = 0; intValue5 < intValueArray4.length; intValue5++) {
               intValueArray4[intValue5] = jsonArray3.get(intValue5).getAsInt();
            }

            value2.c = intValueArray4;
         }

         JsonArray jsonArray8 = jsonObject1.getAsJsonArray("colors");
         if (jsonArray8 != null) {
            int[] intValueArray9 = value2.a();

            for (int intValue6 = 0; intValue6 < Math.min(intValueArray9.length, jsonArray8.size()); intValue6++) {
               intValueArray9[intValue6] = jsonArray8.get(intValue6).getAsInt();
            }

            value2.a(intValueArray9);
         }
      } catch (Exception exception7) {
      }
   }

   private static List<String> a(Module module0) {
      List<Setting> list1 = module0.getSettings();
      ArrayList arrayList2 = new ArrayList(list1.size());
      HashMap<String, Integer> hashMap3 = new HashMap<>();

      for (Setting setting5 : list1) {
         int intValue6 = hashMap3.merge(setting5.getName(), 1, Integer::sum);
         arrayList2.add(intValue6 == 1 ? setting5.getName() : setting5.getName() + "#" + intValue6);
      }

      return arrayList2;
   }

   private JsonObject g() {
      JsonObject jsonObject1 = new JsonObject();
      ObjectListIterator objectListIterator2 = ModuleManager.a.a().iterator();

      while (objectListIterator2.hasNext()) {
         Module module3 = (Module)objectListIterator2.next();

         try {
            JsonObject jsonObject4 = new JsonObject();
            jsonObject4.add("toggled", new JsonPrimitive(module3.isEnabled()));
            jsonObject4.add("bind", new JsonPrimitive(module3.getKeyBind()));
            JsonObject jsonObject5 = new JsonObject();
            List list6 = module3.getSettings();
            List list7 = a(module3);

            for (int intValue8 = 0; intValue8 < list6.size(); intValue8++) {
               Setting setting9 = (Setting)list6.get(intValue8);
               String string10 = (String)list7.get(intValue8);

               try {
                  if (!"Poison".equalsIgnoreCase(setting9.getName())) {
                     if (setting9 instanceof BooleanSetting booleanSetting11) {
                        if (booleanSetting11.getKeyBind() == -1) {
                           jsonObject5.add(string10, new JsonPrimitive(booleanSetting11.getValue()));
                        } else {
                           JsonObject jsonObject19 = new JsonObject();
                           jsonObject19.add("state", new JsonPrimitive(booleanSetting11.getValue()));
                           jsonObject19.add("key", new JsonPrimitive(booleanSetting11.getKeyBind()));
                           jsonObject5.add(string10, jsonObject19);
                        }
                     } else if (setting9 instanceof NumberSetting numberSetting12) {
                        jsonObject5.add(string10, new JsonPrimitive(numberSetting12.e().floatValue()));
                     } else if (setting9 instanceof ModeSetting modeSetting13) {
                        jsonObject5.add(string10, new JsonPrimitive(modeSetting13.g()));
                     } else if (setting9 instanceof dev.leg1tka.settings.StringSetting value14) {
                        jsonObject5.add(string10, new JsonPrimitive(value14.e()));
                     } else if (setting9 instanceof dev.leg1tka.settings.KeybindSetting value15) {
                        jsonObject5.add(string10, new JsonPrimitive(value15.getKey()));
                     } else if (setting9 instanceof ColorSetting colorSetting16) {
                        jsonObject5.add(string10, new JsonPrimitive(colorSetting16.getColor()));
                     } else if (setting9 instanceof dev.leg1tka.settings.ThemeSetting value17) {
                        if (value17.f() != null) {
                           jsonObject5.add(string10, new JsonPrimitive(value17.f().name()));
                        }
                     } else if (setting9 instanceof MultiSetting multiSetting18) {
                        JsonObject jsonObject24 = new JsonObject();

                        for (BooleanSetting booleanSetting21 : multiSetting18.e()) {
                           jsonObject24.add(booleanSetting21.getName(), new JsonPrimitive(booleanSetting21.getValue()));
                        }

                        jsonObject5.add(string10, jsonObject24);
                     }
                  }
               } catch (Exception exception22) {
               }
            }

            jsonObject4.add("settings", jsonObject5);
            jsonObject1.add(module3.getName(), jsonObject4);
         } catch (Exception exception23) {
         }
      }

      return jsonObject1;
   }

   private void b(JsonObject jsonObject1, JsonObject jsonObject2) {
      LinkedHashMap<Module, Boolean> linkedHashMap3 = new LinkedHashMap<>();
      ObjectListIterator objectListIterator4 = ModuleManager.a.a().iterator();

      while (objectListIterator4.hasNext()) {
         Module module5 = (Module)objectListIterator4.next();

         try {
            JsonObject jsonObject6 = jsonObject1.has(module5.getName()) ? jsonObject1.get(module5.getName()).getAsJsonObject() : null;
            boolean flag7 = jsonObject6 != null && jsonObject6.has("toggled") && jsonObject6.get("toggled").getAsBoolean();
            linkedHashMap3.put(module5, flag7);
            if (module5.isEnabled()) {
               module5.setEnabled(false);
            }
         } catch (Exception exception33) {
            linkedHashMap3.put(module5, false);
         }
      }

      objectListIterator4 = ModuleManager.a.a().iterator();

      while (objectListIterator4.hasNext()) {
         Module module36 = (Module)objectListIterator4.next();

         try {
            if (jsonObject1.has(module36.getName())) {
               JsonObject jsonObject38 = jsonObject1.get(module36.getName()).getAsJsonObject();
               if (jsonObject38.has("bind")) {
                  module36.setKeyBind(jsonObject38.get("bind").getAsInt());
               }

               if (jsonObject38.has("settings")) {
                  JsonObject jsonObject39 = jsonObject38.get("settings").getAsJsonObject();
                  JsonObject jsonObject8 = jsonObject2.has(module36.getName()) && jsonObject2.get(module36.getName()).isJsonObject() && jsonObject2.getAsJsonObject(module36.getName()).has("settings")
                     ? jsonObject2.getAsJsonObject(module36.getName()).getAsJsonObject("settings")
                     : new JsonObject();
                  boolean flag9 = this.c(jsonObject8);
                  List list10 = module36.getSettings();
                  List list11 = a(module36);

                  for (int intValue12 = 0; intValue12 < list10.size(); intValue12++) {
                     Setting setting13 = (Setting)list10.get(intValue12);
                     String string14 = (String)list11.get(intValue12);

                     try {
                        if (!"Poison".equalsIgnoreCase(setting13.getName())) {
                           String string15 = jsonObject39.has(string14) ? string14 : (String)RENAMED_SETTINGS.get(string14);
                           if (string15 != null && jsonObject39.has(string15)) {
                              JsonElement jsonElement16 = jsonObject39.get(string15);
                              if (setting13 instanceof BooleanSetting booleanSetting17) {
                                 if (!jsonElement16.isJsonObject()) {
                                    booleanSetting17.setValue(jsonElement16.getAsBoolean());
                                    booleanSetting17.setKeyBind(-1);
                                 } else {
                                    JsonObject jsonObject41 = jsonElement16.getAsJsonObject();
                                    booleanSetting17.setValue(jsonObject41.has("state") && jsonObject41.get("state").getAsBoolean());
                                    booleanSetting17.setKeyBind(jsonObject41.has("key") ? jsonObject41.get("key").getAsInt() : -1);
                                 }
                              } else if (setting13 instanceof NumberSetting numberSetting18) {
                                 numberSetting18.a(this.a(setting13.getName(), jsonElement16.getAsFloat(), flag9));
                              } else if (setting13 instanceof ModeSetting modeSetting19) {
                                 modeSetting19.a(jsonElement16.getAsString());
                              } else if (setting13 instanceof dev.leg1tka.settings.StringSetting value20) {
                                 value20.a(jsonElement16.getAsString());
                              } else if (setting13 instanceof dev.leg1tka.settings.KeybindSetting value21) {
                                 value21.setKey(jsonElement16.getAsInt());
                              } else if (setting13 instanceof ColorSetting colorSetting22) {
                                 colorSetting22.setColor(jsonElement16.getAsInt());
                              } else if (setting13 instanceof dev.leg1tka.settings.ThemeSetting value23) {
                                 String string40 = jsonElement16.getAsString();

                                 for (dev.leg1tka.managers.ThemeStorage.Theme theme29 : dev.leg1tka.managers.ThemeStorage.Theme.values()) {
                                    if (theme29.name().equalsIgnoreCase(string40)) {
                                       value23.a(theme29);
                                       break;
                                    }
                                 }
                              } else if (setting13 instanceof MultiSetting multiSetting24) {
                                 JsonObject jsonObject25 = jsonElement16.getAsJsonObject();

                                 for (BooleanSetting booleanSetting27 : multiSetting24.e()) {
                                    if (jsonObject25.has(booleanSetting27.getName())) {
                                       booleanSetting27.setValue(jsonObject25.get(booleanSetting27.getName()).getAsBoolean());
                                    }
                                 }
                              }
                           }
                        }
                     } catch (Exception exception31) {
                     }
                  }
               }
            }
         } catch (Exception exception32) {
         }
      }

      for (Entry<Module, Boolean> entry37 : linkedHashMap3.entrySet()) {
         try {
            ((Module)entry37.getKey()).setEnabled((Boolean)entry37.getValue());
         } catch (Exception exception30) {
         }
      }
   }

   private boolean c(JsonObject jsonObject1) {
      return !jsonObject1.has("SCtest: Версия")
         && (
            this.a(jsonObject1, "SCtest: Скорость Yaw")
               || this.a(jsonObject1, "SCtest: Скорость Pitch")
               || this.a(jsonObject1, "SCtest: Разгон Yaw")
               || this.a(jsonObject1, "SCtest: Разгон Pitch")
               || this.a(jsonObject1, "SCtest: Стабилизация")
         );
   }

   private boolean a(JsonObject jsonObject1, String string2) {
      return jsonObject1.has(string2) && jsonObject1.get(string2).isJsonPrimitive() && jsonObject1.get(string2).getAsFloat() > 100.0F;
   }

   private float a(String string1, float floatValue2, boolean flag3) {
      if (!flag3) {
         return floatValue2;
      } else if (string1.equals("SCtest: Скорость Yaw")) {
         return (floatValue2 - 60.0F) / 13.8F;
      } else if (string1.equals("SCtest: Скорость Pitch")) {
         return (floatValue2 - 60.0F) / 8.4F;
      } else if (string1.equals("SCtest: Разгон Yaw")) {
         return (floatValue2 - 100.0F) / 79.0F;
      } else if (string1.equals("SCtest: Разгон Pitch")) {
         return (floatValue2 - 100.0F) / 59.0F;
      } else {
         return string1.equals("SCtest: Стабилизация") ? (floatValue2 - 100.0F) / 99.0F : floatValue2;
      }
   }

   private JsonObject h() {
      JsonObject jsonObject1 = new JsonObject();
      Interface interface2 = ModuleManager.U;
      if (interface2 == null) {
         return jsonObject1;
      } else {
         for (Entry entry4 : interface2.r().entrySet()) {
            dev.leg1tka.render.hud.HudRenderer value5 = (dev.leg1tka.render.hud.HudRenderer)entry4.getValue();
            if (value5 != null) {
               JsonObject jsonObject6 = new JsonObject();
               jsonObject6.add("hudRectStyle", new JsonPrimitive(value5.b().name()));
               if (value5 instanceof dev.leg1tka.render.hud.WatermarkHudRenderer value7) {
                  jsonObject6.add("showFps", new JsonPrimitive(value7.e()));
                  jsonObject6.add("showMs", new JsonPrimitive(value7.f()));
                  jsonObject6.add("showServer", new JsonPrimitive(value7.g()));
                  jsonObject6.add("showTps", new JsonPrimitive(value7.h()));
               } else if (value5 instanceof dev.leg1tka.render.hud.TotemHudRenderer value8) {
                  jsonObject6.add("headParticlesEnabled", new JsonPrimitive(value8.e()));
               }

               jsonObject1.add((String)entry4.getKey(), jsonObject6);
            }
         }

         return jsonObject1;
      }
   }

   private void d(JsonObject jsonObject1) {
      Interface interface2 = ModuleManager.U;
      if (interface2 != null) {
         for (Entry entry4 : interface2.r().entrySet()) {
            if (jsonObject1.has((String)entry4.getKey())) {
               try {
                  JsonObject jsonObject5 = jsonObject1.get((String)entry4.getKey()).getAsJsonObject();
                  dev.leg1tka.render.hud.HudRenderer value6 = (dev.leg1tka.render.hud.HudRenderer)entry4.getValue();
                  if (jsonObject5.has("hudRectStyle")) {
                     try {
                        value6.a(dev.leg1tka.render.RenderExecutionMode.valueOf(jsonObject5.get("hudRectStyle").getAsString()));
                     } catch (IllegalArgumentException illegalArgumentException9) {
                     }
                  } else if (jsonObject5.has("unusualRectType")) {
                     value6.a(dev.leg1tka.render.RenderExecutionMode.a(jsonObject5.get("unusualRectType").getAsBoolean()));
                  }

                  if (value6 instanceof dev.leg1tka.render.hud.WatermarkHudRenderer value7) {
                     if (jsonObject5.has("showFps")) {
                        value7.c(jsonObject5.get("showFps").getAsBoolean());
                     }

                     if (jsonObject5.has("showMs")) {
                        value7.d(jsonObject5.get("showMs").getAsBoolean());
                     }

                     if (jsonObject5.has("showServer")) {
                        value7.e(jsonObject5.get("showServer").getAsBoolean());
                     }

                     if (jsonObject5.has("showTps")) {
                        value7.f(jsonObject5.get("showTps").getAsBoolean());
                     }
                  } else if (value6 instanceof dev.leg1tka.render.hud.TotemHudRenderer value8 && jsonObject5.has("headParticlesEnabled")) {
                     value8.c(jsonObject5.get("headParticlesEnabled").getAsBoolean());
                  }
               } catch (Exception exception10) {
               }
            }
         }
      }
   }

   private JsonObject i() {
      JsonObject jsonObject1 = new JsonObject();

      for (dev.leg1tka.render.hud.HudElement value3 : dev.leg1tka.render.hud.HudElementRegistry.a.values()) {
         JsonObject jsonObject4 = new JsonObject();
         jsonObject4.add("x", new JsonPrimitive(value3.a()));
         jsonObject4.add("y", new JsonPrimitive(value3.b()));
         jsonObject1.add(value3.e(), jsonObject4);
      }

      return jsonObject1;
   }

   private void e(JsonObject jsonObject1) {
      for (String string3 : jsonObject1.keySet()) {
         dev.leg1tka.render.hud.HudElement value4 = (dev.leg1tka.render.hud.HudElement)dev.leg1tka.render.hud.HudElementRegistry.a.get(string3);
         if (value4 != null) {
            try {
               JsonObject jsonObject5 = jsonObject1.get(string3).getAsJsonObject();
               if (jsonObject5.has("x")) {
                  value4.a(jsonObject5.get("x").getAsFloat());
               }

               if (jsonObject5.has("y")) {
                  value4.b(jsonObject5.get("y").getAsFloat());
               }
            } catch (Exception exception6) {
            }
         }
      }
   }
}
