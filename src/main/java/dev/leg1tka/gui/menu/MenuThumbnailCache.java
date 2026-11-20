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

import java.io.InputStream;
import java.util.EnumMap;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public final class MenuThumbnailCache {
   private static int a;
   private static final EnumMap<dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme, Identifier> b = new EnumMap<>(dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme.class);
   private static boolean c = false;

   private MenuThumbnailCache() {
   }

   public static void a() {
      if (!c) {
         c = true;
         MinecraftClient minecraftClient0 = MinecraftClient.getInstance();

         for (dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme4 : dev.leg1tka.gui.menu.WallpaperSettings.b) {
            CompletableFuture.runAsync(() -> {
               NativeImage nativeImage2 = null;

               try (InputStream inputStream3 = minecraftClient0.getResourceManager().getResourceOrThrow(backgroundTheme4.c()).getInputStream()) {
                  NativeImage nativeImage4x = NativeImage.read(inputStream3);

                  try {
                     int intValue5 = Math.max(1, Math.round((float)(640 * nativeImage4x.getHeight()) / (float)nativeImage4x.getWidth()));
                     nativeImage2 = new NativeImage(640, intValue5, false);
                     nativeImage4x.resizeSubRectTo(0, 0, nativeImage4x.getWidth(), nativeImage4x.getHeight(), nativeImage2);
                     NativeImage nativeImage6 = nativeImage2;
                     nativeImage2 = null;
                     minecraftClient0.execute(() -> {
                        NativeImageBackedTexture nativeImageBackedTexture3x = new NativeImageBackedTexture(nativeImage6);
                        nativeImageBackedTexture3x.setFilter(true, false);
                        Identifier identifier4xx = Identifier.of("aspectdlc", "menu_thumb/" + backgroundTheme4.name().toLowerCase());
                        minecraftClient0.getTextureManager().registerTexture(identifier4xx, nativeImageBackedTexture3x);
                        b.put(backgroundTheme4, identifier4xx);
                     });
                  } catch (Throwable throwable9) {
                     if (nativeImage4x != null) {
                        try {
                           nativeImage4x.close();
                        } catch (Throwable throwable8) {
                           throwable9.addSuppressed(throwable8);
                        }
                     }

                     throw throwable9;
                  }

                  if (nativeImage4x != null) {
                     nativeImage4x.close();
                  }
               } catch (Throwable throwable11) {
                  if (nativeImage2 != null) {
                     nativeImage2.close();
                  }
               }
            }, Util.getMainWorkerExecutor());
         }
      }
   }

   public static Identifier a(dev.leg1tka.gui.menu.WallpaperSettings.BackgroundTheme backgroundTheme0) {
      return (Identifier)b.get(backgroundTheme0);
   }
}
