package dev.leg1tka.accounts;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.client.util.SkinTextures;

public final class AccountProfileResolver {
   private static final HashMap a = new HashMap();
   private static final HashSet b = new HashSet();
   private static final HashSet c = new HashSet();

   private AccountProfileResolver() {
   }

   public static UUID a(String string0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + string0).getBytes(StandardCharsets.UTF_8));
   }

   public static SkinTextures b(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         String string1 = string0.toLowerCase(Locale.ROOT);
         SkinTextures skinTextures2 = (SkinTextures)a.get(string1);
         if (skinTextures2 != null) {
            return skinTextures2;
         } else {
            if (b.add(string1)) {
               a(string0, string1);
            }

            return DefaultSkinHelper.getSkinTextures(a(string0));
         }
      } else {
         return DefaultSkinHelper.getSteve();
      }
   }

   public static Boolean c(String string0) {
      if (string0 == null) {
         return null;
      } else {
         String string1 = string0.toLowerCase(Locale.ROOT);
         return !a.containsKey(string1) ? null : c.contains(string1);
      }
   }

   private static void a(String string0, String string1) {
      MinecraftClient minecraftClient2 = MinecraftClient.getInstance();

      try {
         SkullBlockEntity.fetchProfileByName(string0)
            .thenCompose(
               optional1x -> optional1x.isPresent()
                     ? minecraftClient2.getSkinProvider()
                        .fetchSkinTextures((GameProfile)optional1x.get())
                        .thenApply(optional0xx -> optional0xx.map(skinTextures0xxx -> new Object[]{skinTextures0xxx, Boolean.TRUE}))
                     : CompletableFuture.completedFuture(Optional.empty())
            )
            .whenComplete((optional3, throwable4x) -> minecraftClient2.execute(() -> {
                  if (throwable4x == null && optional3 != null && optional3.isPresent()) {
                     a.put(string1, (SkinTextures)((Object[])optional3.get())[0]);
                     c.add(string1);
                  } else {
                     a.put(string1, DefaultSkinHelper.getSkinTextures(a(string0)));
                  }
               }));
      } catch (Throwable throwable4) {
         a.put(string1, DefaultSkinHelper.getSkinTextures(a(string0)));
      }
   }

   public static void d(String string0) {
      if (string0 != null) {
         String string1 = string0.toLowerCase(Locale.ROOT);
         a.remove(string1);
         b.remove(string1);
         c.remove(string1);
      }
   }
}
