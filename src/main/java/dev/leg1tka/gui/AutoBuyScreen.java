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

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;

public class AutoBuyScreen extends Screen implements dev.leg1tka.api.ClientAccess {
   private final float c = 170.0F;
   private final float d = 240.0F;

   public AutoBuyScreen() {
      super(Text.of("AutoBuy"));
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
   }

   public void render(DrawContext context, int mouseX, int mouseY, float delta) {
      Window window5 = MinecraftClient.getInstance().getWindow();
      if (window5 != null) {
         float floatValue6 = (float)window5.getScaledWidth() / 2.0F - 170.0F;
         float floatValue7 = (float)window5.getScaledHeight() / 2.0F - 240.0F;
         dev.leg1tka.render.RenderPrimitives.a(context.getMatrices(), floatValue6, floatValue7, 170.0F, 240.0F, 5.0F, dev.leg1tka.api.utils.text.ColorUtils.a(), dev.leg1tka.api.utils.text.ColorUtils.b(dev.leg1tka.api.utils.text.ColorUtils.a(), 0.5F), true);
         super.render(context, mouseX, mouseY, delta);
      }
   }
}
