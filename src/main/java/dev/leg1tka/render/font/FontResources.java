package dev.leg1tka.render.font;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.awt.Font;
import java.io.InputStream;
import java.util.Optional;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.Resource;
import net.minecraft.util.Identifier;

public class FontResources {
   public FontResources() {
   }

   public static Font a(Identifier identifier0, float floatValue1, int intValue2) {
      try {
         MinecraftClient minecraftClient3 = MinecraftClient.getInstance();
         if (minecraftClient3 == null) {
            return null;
         } else if (minecraftClient3.getResourceManager() == null) {
            return null;
         } else {
            Optional optional4 = minecraftClient3.getResourceManager().getResource(identifier0);
            if (optional4.isPresent()) {
               InputStream inputStream5 = ((Resource)optional4.get()).getInputStream();
               Font font6 = Font.createFont(intValue2, inputStream5);
               font6 = font6.deriveFont(floatValue1);
               inputStream5.close();
               return font6;
            } else {
               return null;
            }
         }
      } catch (Exception exception7) {
         exception7.printStackTrace();
         return null;
      }
   }
}
