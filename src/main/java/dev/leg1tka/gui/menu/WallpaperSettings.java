package dev.leg1tka.gui.menu;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;

public final class WallpaperSettings {
   public static final dev.leg1tka.gui.menu.WallpaperSettings a = new dev.leg1tka.gui.menu.WallpaperSettings();
   public static final BackgroundTheme[] b = BackgroundTheme.values();
   private static float c;
   private final HashMap d = new HashMap();
   private BackgroundTheme e = BackgroundTheme.ASPECT;
   private final File f;
   private boolean g = false;

   public void a(TextureManager textureManager1) {
      if (!this.g && textureManager1 != null) {
         this.g = true;

         for (BackgroundTheme backgroundTheme5 : b) {
            textureManager1.getTexture(backgroundTheme5.b);
            textureManager1.getTexture(backgroundTheme5.c);
         }
      }
   }

   public float a(Identifier identifier1) {
      Float float2 = (Float)this.d.get(identifier1);
      if (float2 != null) {
         return float2;
      } else {
         float floatValue3 = 1.7916666F;

         try (InputStream inputStream4 = MinecraftClient.getInstance().getResourceManager().open(identifier1)) {
            byte[] byteValueArray5 = inputStream4.readNBytes(24);
            if (byteValueArray5.length == 24) {
               int intValue6 = (byteValueArray5[16] & 255) << 24 | (byteValueArray5[17] & 255) << 16 | (byteValueArray5[18] & 255) << 8 | byteValueArray5[19] & 255;
               int intValue7 = (byteValueArray5[20] & 255) << 24 | (byteValueArray5[21] & 255) << 16 | (byteValueArray5[22] & 255) << 8 | byteValueArray5[23] & 255;
               if (intValue6 > 0 && intValue7 > 0) {
                  floatValue3 = (float)intValue6 / (float)intValue7;
               }
            }
         } catch (Throwable throwable10) {
         }

         this.d.put(identifier1, floatValue3);
         return floatValue3;
      }
   }

   private WallpaperSettings() {
      File file1 = FabricLoader.getInstance().getConfigDir().toFile();
      this.f = new File(file1, "aspect_wallpaper.txt");
      this.b();
   }

   public BackgroundTheme a() {
      return this.e;
   }

   public void a(BackgroundTheme backgroundTheme1) {
      if (backgroundTheme1 != null && backgroundTheme1 != this.e) {
         this.e = backgroundTheme1;
         this.c();
         dev.leg1tka.gui.menu.MenuScreenRouting.c.a();
      }
   }

   private void b() {
      try {
         if (this.f.exists()) {
            String string1 = Files.readString(this.f.toPath(), StandardCharsets.UTF_8).trim();

            for (BackgroundTheme backgroundTheme5 : BackgroundTheme.values()) {
               if (backgroundTheme5.name().equalsIgnoreCase(string1) || backgroundTheme5.b().equalsIgnoreCase(string1)) {
                  this.e = backgroundTheme5;
                  break;
               }
            }
         }
      } catch (Throwable throwable6) {
      }
   }

   private void c() {
      try {
         Files.writeString(this.f.toPath(), this.e.name(), StandardCharsets.UTF_8);
      } catch (Throwable throwable2) {
      }
   }

   public static enum BackgroundEffect {
      SNOW,
      ASH,
      EMBER,
      DUST,
      FOG;

      private BackgroundEffect() {
      }
   }

   public static enum BackgroundTheme {
      ASPECT("Aspect", "textures/gui/bg_main.png", "textures/gui/bg_other.png", -13739009, -16727809, BackgroundEffect.SNOW),
      BRIDES(
         "5 Невест: Все",
         "textures/gui/bg_brides_main.png",
         "textures/gui/bg_brides_other.png",
         -39548,
         -24454,
         BackgroundEffect.DUST
      ),
      ITSUKI(
         "5 Невест: Ицуки",
         "textures/gui/bg_itsuki_main.png",
         "textures/gui/bg_itsuki_other.png",
         -50384,
         -27392,
         BackgroundEffect.DUST
      ),
      KANEKI(
         "Токийский гуль",
         "textures/gui/bg_kaneki_main.png",
         "textures/gui/bg_kaneki_other.png",
         -1514278,
         -9737358,
         BackgroundEffect.ASH
      ),
      SUNSET(
         "Золотой час",
         "textures/gui/bg_mc_sunset_main.png",
         "textures/gui/bg_mc_sunset_main.png",
         -609170,
         -1938084,
         BackgroundEffect.EMBER
      ),
      SNOW(
         "Снежные острова",
         "textures/gui/bg_mc_snow_main.png",
         "textures/gui/bg_mc_snow_main.png",
         -6310944,
         -2892816,
         BackgroundEffect.SNOW
      ),
      FOG(
         "Туманные пики",
         "textures/gui/bg_mc_fog_main.png",
         "textures/gui/bg_mc_fog_main.png",
         -5788746,
         -10591120,
         BackgroundEffect.FOG
      ),
      SYLPHIE_SUNSET(
         "Сильфи: Закат",
         "textures/gui/bg_sylphie_sunset_main.png",
         "textures/gui/bg_sylphie_sunset_other.png",
         -19364,
         -7360438,
         BackgroundEffect.EMBER
      ),
      SYLPHIE_WINTER(
         "Сильфи: Зима",
         "textures/gui/bg_sylphie_winter_main.png",
         "textures/gui/bg_sylphie_winter_other.png",
         -10781728,
         -3614977,
         BackgroundEffect.SNOW
      ),
      ROXY_FIELD(
         "Рокси: Поле",
         "textures/gui/bg_roxy_field_main.png",
         "textures/gui/bg_roxy_field_other.png",
         -7435024,
         -22662,
         BackgroundEffect.DUST
      ),
      ROXY_VILLAGE(
         "Рокси: Деревня",
         "textures/gui/bg_roxy_village_main.png",
         "textures/gui/bg_roxy_village_other.png",
         -12743681,
         -6369025,
         BackgroundEffect.FOG
      ),
      ALYA("Аля", "textures/gui/bg_alya_main.png", "textures/gui/bg_alya_other.png", -7419393, -2868150, BackgroundEffect.DUST);

      private final String a;
      final Identifier b;
      final Identifier c;
      private final int d;
      private final int e;
      private final BackgroundEffect f;

      private BackgroundTheme(String string3, String string4, String string5, int intValue6, int intValue7, BackgroundEffect backgroundEffect8) {
         this.a = string3;
         this.b = Identifier.of("aspectdlc", string4);
         this.c = Identifier.of("aspectdlc", string5);
         this.d = intValue6;
         this.e = intValue7;
         this.f = backgroundEffect8;
      }

      public BackgroundEffect a() {
         return this.f;
      }

      public String b() {
         return this.a;
      }

      public Identifier c() {
         return this.b;
      }

      public Identifier d() {
         return this.c;
      }

      public int e() {
         return this.d;
      }

      public int f() {
         return this.e;
      }
   }
}
