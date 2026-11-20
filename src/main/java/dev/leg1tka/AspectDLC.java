package dev.leg1tka;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.managers.ModuleStorage;
import dev.leg1tka.rotation.RotationStorage;
import dev.leg1tka.managers.ServerStorage;
import dev.leg1tka.managers.StaffStorage;
import dev.leg1tka.managers.ThemeStorage;
import dev.leg1tka.managers.WaypointStorage;
import dev.leg1tka.accounts.ServerReconnectTracker;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.network.bots.BotController;
import dev.leg1tka.managers.UserInfo;
import dev.leg1tka.render.hud.HudElement;
import dev.leg1tka.network.ProxySessionBridge;
import dev.leg1tka.managers.DiscordManager;
import dev.leg1tka.managers.TpsCalculator;
import dev.leg1tka.modules.Module;
import dev.leg1tka.api.utils.logging.ClientLogFilter;
import dev.leg1tka.api.utils.logging.AtmosphereBenchmark;
import dev.leg1tka.managers.StorageManager;
import dev.leg1tka.managers.AltStorage;
import dev.leg1tka.managers.CommandStorage;
import dev.leg1tka.managers.ConfigStorage;
import dev.leg1tka.render.hud.HudElementRegistry;
import dev.leg1tka.rotation.FreeLookStorage;
import dev.leg1tka.managers.FriendStorage;
import dev.leg1tka.managers.LocalizationStorage;
import dev.leg1tka.managers.MacroStorage;
import java.io.File;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
import lombok.Generated;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStarted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.ClientStopping;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents.Join;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.Start;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

public enum AspectDLC implements dev.leg1tka.api.ClientAccess, ModInitializer {
   INSTANCE;

   private static final String[] STARTUP_LINKS = new String[0];
   public boolean isServer;
   private static double prevTime = 0.0;
   public static double deltaTime = 0.0;
   public dev.leg1tka.managers.StorageManager initializer;
   public ModuleStorage moduleStorage;
   public dev.leg1tka.managers.ThemeStorage themeStorage;
   public dev.leg1tka.managers.TpsCalculator tpsCalc;
   public dev.leg1tka.managers.ServerStorage serverStorage;
   public dev.leg1tka.rotation.RotationStorage rotationStorage;
   public dev.leg1tka.rotation.FreeLookStorage freeLookStorage;
   public dev.leg1tka.managers.CommandStorage commandStorage;
   public dev.leg1tka.managers.LocalizationStorage localizationStorage;
   public dev.leg1tka.managers.ConfigStorage configStorage;
   public dev.leg1tka.managers.AltStorage altStorage;
   public dev.leg1tka.managers.FriendStorage friendStorage;
   public dev.leg1tka.managers.MacroStorage macroStorage;
   public dev.leg1tka.managers.StaffStorage staffStorage;
   public dev.leg1tka.managers.WaypointStorage waypointStorage;
   public dev.leg1tka.managers.DiscordManager discordManager;
   public dev.leg1tka.managers.UserInfo userInfo;
   public File globalsDir;
   public File configsDir;
   public File abItemsDir;
   private boolean closed;

   private AspectDLC() {
      try {
         this.userInfo = dev.leg1tka.managers.UserInfo.a();
      } catch (Throwable throwable4) {
      }
   }

   public void onInitialize() {
      if (this.userInfo == null) {
         this.userInfo = dev.leg1tka.managers.UserInfo.a();
      }

      if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
         this.startClient();
      } else {
         this.initStorage();
      }
   }

   private void startClient() {
      boolean flag1 = System.getProperty("aspect.atmoBench") != null;
      this.initStorage();
      if (!flag1) {
         this.openStartupLinks();
      }

      if (flag1) {
         dev.leg1tka.api.utils.logging.AtmosphereBenchmark.a();
      }

      ClientLifecycleEvents.CLIENT_STOPPING.register((ClientStopping)minecraftClient1x -> this.closeMinecraft());
      ClientPlayConnectionEvents.JOIN.register((Join)(clientPlayNetworkHandler0, packetSender1x, minecraftClient2) -> minecraftClient2.execute(() -> dev.leg1tka.accounts.ServerReconnectTracker.a(minecraftClient2)));
      WorldRenderEvents.START.register((Start)worldRenderContext0 -> {
         double doubleValue1x = GLFW.glfwGetTime();
         deltaTime = doubleValue1x - prevTime;
         prevTime = doubleValue1x;
         deltaTime = minecraftClient.isPaused() ? 0.0 : Math.min(0.05, deltaTime);
      });
   }

   private void initStorage() {
      this.globalsDir = System.getProperty("aspect.atmoBench") != null
         ? new File(FabricLoader.getInstance().getGameDir().toFile(), "aspectdlc_bench")
         : new File("game", "aspectdlc");
      this.configsDir = new File(this.globalsDir, "configs");
      this.abItemsDir = new File(this.globalsDir, "abitems");
      disableBaritoneChat();
      ClientLifecycleEvents.CLIENT_STARTED.register((ClientStarted)minecraftClient0 -> disableBaritoneChat());
      EventInvoker.register(this);
      this.createDirs(this.globalsDir, this.configsDir, this.abItemsDir);
      this.initializer = new dev.leg1tka.managers.StorageManager();
      this.initializer.a();
      this.discordManager = new dev.leg1tka.managers.DiscordManager().b();
   }

   private void openStartupLinks() {
      CompletableFuture.runAsync(() -> {
         for (String string3 : STARTUP_LINKS) {
            try {
               Util.getOperatingSystem().open(string3);
               Thread.sleep(150L);
            } catch (Exception exception5) {
            }
         }
      });
   }

   private void createDirs(File... fileArray1) {
      for (File file5 : fileArray1) {
         file5.mkdirs();
      }
   }

   public void closeMinecraft() {
      if (!this.closed) {
         this.closed = true;

         try {
            if (this.configStorage != null) {
               this.configStorage.a();
            }
         } catch (Exception exception5) {
         }

         try {
            dev.leg1tka.api.utils.logging.ClientLogFilter.c(minecraftClient.runDirectory);
         } catch (Exception exception4) {
         }

         if (this.discordManager != null) {
            this.discordManager.c();
         }

         try {
            dev.leg1tka.network.ProxySessionBridge.a();
         } catch (Exception exception3) {
         }

         try {
            dev.leg1tka.network.bots.BotController.j();
         } catch (Exception exception2) {
         }
      }
   }

   public static dev.leg1tka.render.hud.HudElement draggable(Module module0, String string1, float floatValue2, float floatValue3) {
      dev.leg1tka.render.hud.HudElementRegistry.a.put(string1, new dev.leg1tka.render.hud.HudElement(module0, string1, floatValue2, floatValue3));
      return (dev.leg1tka.render.hud.HudElement)dev.leg1tka.render.hud.HudElementRegistry.a.get(string1);
   }

   public void setUserInfo(dev.leg1tka.managers.UserInfo userInfo1) {
      this.userInfo = userInfo1 == null ? dev.leg1tka.managers.UserInfo.a() : userInfo1;
   }

   private static void disableBaritoneChat() {
      try {
         Class class0 = Class.forName("baritone.a");
         Object object1 = class0.getMethod("a").invoke(null);
         if (object1 != null) {
            setBaritoneSetting(object1, "chatControl", false);
            setBaritoneSetting(object1, "chatControlAnyway", false);
            setBaritoneSetting(object1, "prefixControl", false);
            setBaritoneSetting(object1, "prefix", "\u0000");
            setBaritoneSetting(object1, "autoTool", false);
            setBaritoneSetting(object1, "allowInventory", false);
         }
      } catch (Throwable throwable9) {
      }

      try {
         Class class11 = Class.forName("baritone.api.BaritoneAPI");
         Object object12 = class11.getMethod("getSettings").invoke(null);
         if (object12 != null) {
            for (String string5 : new String[]{"chatControl", "chatControlAnyway", "prefixControl", "autoTool", "allowInventory"}) {
               try {
                  Object object6 = object12.getClass().getMethod(string5).invoke(object12);
                  object6.getClass().getMethod("value", Object.class).invoke(object6, false);
               } catch (Throwable throwable8) {
               }
            }

            try {
               Object object13 = object12.getClass().getMethod("prefix").invoke(object12);
               object13.getClass().getMethod("value", Object.class).invoke(object13, "\u0000");
            } catch (Throwable throwable7) {
            }
         }
      } catch (Throwable throwable10) {
      }
   }

   private static void setBaritoneSetting(Object object0, String string1, Object object2) {
      try {
         Field field3 = object0.getClass().getField(string1);
         Object object4 = field3.get(object0);
         if (object4 != null) {
            Field field5 = object4.getClass().getField("a");
            field5.set(object4, object2);
         }
      } catch (Throwable throwable6) {
      }
   }

   @Generated
   public dev.leg1tka.managers.UserInfo getUserInfo() {
      return this.userInfo;
   }
}
