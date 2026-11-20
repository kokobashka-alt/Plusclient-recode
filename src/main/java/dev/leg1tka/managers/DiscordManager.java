package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.api.utils.rpc.utils.DiscordEventHandlers;
import dev.leg1tka.api.utils.rpc.utils.DiscordRPC;
import dev.leg1tka.api.utils.rpc.utils.DiscordRichPresence;
import lombok.Generated;

public class DiscordManager implements dev.leg1tka.api.ClientAccess {
   private dev.leg1tka.managers.DiscordManager.DiscordActivity e;
   boolean f;
   public static DiscordRichPresence c = new DiscordRichPresence();
   public static DiscordRPC d = DiscordRPC.a;

   public DiscordManager() {
   }

   private void f() {
      this.e = new dev.leg1tka.managers.DiscordManager.DiscordActivity();
      this.e.setDaemon(true);
      this.f = true;
   }

   public void a() {
      this.f();
      DiscordEventHandlers discordEventHandlers1 = new DiscordEventHandlers.a().a();
      DiscordRPC.a.Discord_Initialize(dev.leg1tka.api.utils.rpc.DiscordApplication.a(), discordEventHandlers1, true, "");
      c.startTimestamp = System.currentTimeMillis() / 1000L;
      this.g();
      d.Discord_UpdatePresence(c);
      Thread thread2 = new Thread(() -> {
         while (this.f) {
            try {
               this.g();
               DiscordRPC.a.Discord_UpdatePresence(c);
               Thread.sleep(2000L);
            } catch (InterruptedException interruptedException2x) {
               Thread.currentThread().interrupt();
               return;
            }
         }
      }, "Discord-RPC-Updater");
      thread2.setDaemon(true);
      thread2.start();
      this.e.start();
   }

   private void g() {
      String string1 = "Singleplayer";
      if (minecraftClient != null && minecraftClient.getCurrentServerEntry() != null) {
         string1 = minecraftClient.getCurrentServerEntry().address;
      }

      c.details = "Играет на: " + string1;
      c.state = "User | Build: Recode";
      c.largeImageKey = "logo";
      c.largeImageText = "AspectDLC";
      c.button_label_2 = "Телеграмм";
      c.button_url_2 = "https://t.me/aspectbeta";
   }

   public dev.leg1tka.managers.DiscordManager b() {
      this.a();
      return this;
   }

   public void c() {
      this.f = false;
      if (this.e != null) {
         this.e.interrupt();
      }

      Thread thread1 = new Thread(() -> {
         try {
            DiscordRPC.a.Discord_Shutdown();
         } catch (Throwable throwable1x) {
         }
      }, "rpc-shut");
      thread1.setDaemon(true);
      thread1.start();
   }

   @Generated
   public dev.leg1tka.managers.DiscordManager.DiscordActivity d() {
      return this.e;
   }

   @Generated
   public boolean e() {
      return this.f;
   }

   class DiscordActivity extends Thread {
      DiscordActivity() {
      }

      @Override
      public void run() {
         this.setName("Discord-RPC");

         try {
            while (dev.leg1tka.managers.DiscordManager.this.f) {
               DiscordRPC.a.Discord_RunCallbacks();
               Thread.sleep(15000L);
            }
         } catch (Exception exception2) {
         }

         super.run();
      }
   }
}
