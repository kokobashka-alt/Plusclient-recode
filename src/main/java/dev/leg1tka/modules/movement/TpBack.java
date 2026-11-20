package dev.leg1tka.modules.movement;


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
import dev.leg1tka.api.events.implement.EventUpdate;
import java.util.concurrent.ThreadLocalRandom;

public class TpBack extends Module {
   public static TpBack c = new TpBack();
   private static long h;
   private static long i;
   private boolean j = false;
   private long k = 0L;
   public BooleanSetting d = new BooleanSetting("Рандом название", false);
   public dev.leg1tka.settings.StringSetting e = new dev.leg1tka.settings.StringSetting("Название дома", "home", 32).a(() -> !this.d.getValue());
   public BooleanSetting f = new BooleanSetting("Команда после /home", false);
   public dev.leg1tka.settings.StringSetting g = new dev.leg1tka.settings.StringSetting("Что писать после /home", "rtp far", 64).a(() -> this.f.getValue());

   public TpBack() {
      super(
         "TpBack",
         "Автотелепорт домой после смерти (под ToolRise)",
         Category.MOVEMENT
      );
      this.addSettings(new Setting[]{this.d, this.e, this.f, this.g});
   }

   @Override
   public void onDisable() {
      this.j = false;
      this.k = 0L;
      super.onDisable();
   }

   @EventLink
   public void onEvent(EventUpdate eventUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && minecraftClient.player.networkHandler != null) {
         boolean flag2 = minecraftClient.player.getHealth() <= 0.0F && minecraftClient.player.deathTime > 0;
         if (flag2 && !this.j) {
            this.j = true;
            this.k = System.currentTimeMillis();
            String string3 = this.p();
            this.e("sethome " + string3);
            minecraftClient.player.requestRespawn();
            dev.leg1tka.api.utils.text.ClientChat.a("TpBack: сохранён дом \"" + string3 + "\"");
            this.c(string3);
         }

         if (!flag2 && System.currentTimeMillis() - this.k > 1000L) {
            this.j = false;
         }
      }
   }

   private void c(String string1) {
      Thread thread2 = new Thread(
         () -> {
            try {
               Thread.sleep(100L);
            } catch (InterruptedException interruptedException3) {
               return;
            }

            minecraftClient.execute(
               () -> {
                  if (minecraftClient.player != null) {
                     this.e("home " + string1);
                     dev.leg1tka.api.utils.text.ClientChat.a("TpBack: выполнен /home " + string1);
                     if (this.f.getValue()) {
                        Thread thread2x = new Thread(
                           () -> {
                              try {
                                 Thread.sleep(200L);
                              } catch (InterruptedException interruptedException2xx) {
                                 return;
                              }

                              minecraftClient.execute(
                                 () -> {
                                    if (minecraftClient.player != null) {
                                       String string1xxx = this.d(this.g.e());
                                       if (!string1xxx.isEmpty()) {
                                          this.e(string1xxx);
                                          dev.leg1tka.api.utils.text.ClientChat.a(
                                             "TpBack: отправлена команда \""
                                                + string1xxx
                                                + "\""
                                          );
                                       }
                                    }
                                 }
                              );
                           }
                        );
                        thread2x.setDaemon(true);
                        thread2x.start();
                     }
                  }
               }
            );
         }
      );
      thread2.setDaemon(true);
      thread2.start();
   }

   private String p() {
      if (this.d.getValue()) {
         return this.q();
      } else {
         String string1 = this.e.e().trim();
         return string1.isEmpty() ? "home" : string1;
      }
   }

   private String q() {
      ThreadLocalRandom threadLocalRandom1 = ThreadLocalRandom.current();
      int intValue2 = threadLocalRandom1.nextInt(2, 7);
      char[] characterArray3 = new char[intValue2];

      for (int intValue4 = 0; intValue4 < intValue2; intValue4++) {
         characterArray3[intValue4] = (char)(97 + threadLocalRandom1.nextInt(26));
      }

      return new String(characterArray3);
   }

   private String d(String string1) {
      if (string1 == null) {
         return "";
      } else {
         String string2 = string1.trim();

         while (string2.startsWith("/")) {
            string2 = string2.substring(1).trim();
         }

         return string2;
      }
   }

   private void e(String string1) {
      if (minecraftClient.player != null && minecraftClient.player.networkHandler != null) {
         minecraftClient.player.networkHandler.sendChatCommand(string1);
      }
   }
}
