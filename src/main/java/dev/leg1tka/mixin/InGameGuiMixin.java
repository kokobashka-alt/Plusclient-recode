package dev.leg1tka.mixin;

import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventRender;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.render.hud.ScoreboardLine;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.render.FoodHudRenderer;
import dev.leg1tka.render.ScreenBlurPass;
import dev.leg1tka.modules.misc.NameProtect;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.number.NumberFormat;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({InGameHud.class})
public class InGameGuiMixin implements dev.leg1tka.api.ClientAccess {
   private static final int DOMAIN_COLOR = 15557921;
   @Shadow
   @Final
   private MinecraftClient client;

   public InGameGuiMixin() {
   }

   @Inject(
      method = {"renderVignetteOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void renderVignetteOverlay(DrawContext drawContext1, Entity entity2, CallbackInfo callbackInfo3) {
      if (ModuleManager.an.c("Виньетка")) {
         callbackInfo3.cancel();
      }
   }

   @Inject(
      method = {"renderFood(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/entity/player/PlayerEntity;II)V"},
      at = {@At("TAIL")}
   )
   private void AspectDLC$foodOverlay(DrawContext drawContext1, PlayerEntity playerEntity2, int intValue3, int intValue4, CallbackInfo callbackInfo5) {
      dev.leg1tka.render.FoodHudRenderer.a(drawContext1, playerEntity2, intValue3, intValue4);
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"},
      at = {@At("HEAD")}
   )
   private void render(DrawContext drawContext1, RenderTickCounter renderTickCounter2, CallbackInfo callbackInfo3) {
      dev.leg1tka.render.ScreenBlurPass.a().b();
      if (EventInvoker.hasListeners(EventRender.Default.class)) {
         new EventRender.Default(drawContext1, renderTickCounter2.getTickDelta(true)).call();
      }
   }

   @Shadow
   private PlayerEntity getCameraPlayer() {
      return null;
   }

   @Inject(
      method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$renderPatchedScoreboard(DrawContext drawContext1, ScoreboardObjective scoreboardObjective2, CallbackInfo callbackInfo3) {
      if (this.AspectDLC$shouldPatchScoreboard()) {
         Scoreboard scoreboard4 = scoreboardObjective2.getScoreboard();
         NumberFormat numberFormat5 = scoreboardObjective2.getNumberFormatOr(StyledNumberFormat.RED);
         List<dev.leg1tka.render.hud.ScoreboardLine> list6 = scoreboard4.getScoreboardEntries(scoreboardObjective2)
            .stream()
            .filter(scoreboardEntry0 -> !scoreboardEntry0.hidden())
            .sorted(Comparator.comparing(ScoreboardEntry::value).reversed().thenComparing(ScoreboardEntry::owner, String.CASE_INSENSITIVE_ORDER))
            .limit(15L)
            .map(scoreboardEntry3x -> {
               Team team4x = scoreboard4.getScoreHolderTeam(scoreboardEntry3x.owner());
               Text text5x = ModuleManager.A.a(this.AspectDLC$patchText(Team.decorateName(team4x, scoreboardEntry3x.name())));
               MutableText mutableText6x = scoreboardEntry3x.formatted(numberFormat5);
               int intValue7x = this.client.textRenderer.getWidth(mutableText6x);
               return new dev.leg1tka.render.hud.ScoreboardLine(text5x, mutableText6x, intValue7x);
            })
            .toList();
         Text text7 = this.AspectDLC$patchText(scoreboardObjective2.getDisplayName());
         int intValue8 = this.client.textRenderer.getWidth(text7);
         int intValue9 = intValue8;
         int intValue10 = this.client.textRenderer.getWidth(": ");

         for (dev.leg1tka.render.hud.ScoreboardLine value12 : list6) {
            intValue9 = Math.max(intValue9, this.client.textRenderer.getWidth(value12.a) + (value12.c > 0 ? intValue10 + value12.c : 0));
         }

         int intValue22 = list6.size();
         int intValue23 = intValue22 * 9;
         int intValue13 = drawContext1.getScaledWindowHeight() / 2 + intValue23 / 3;
         int intValue14 = drawContext1.getScaledWindowWidth() - intValue9 - 3;
         int intValue15 = drawContext1.getScaledWindowWidth() - 1;
         int intValue16 = this.client.options.getTextBackgroundColor(0.3F);
         int intValue17 = this.client.options.getTextBackgroundColor(0.4F);
         int intValue18 = intValue13 - intValue22 * 9;
         drawContext1.fill(intValue14 - 2, intValue18 - 10, intValue15, intValue18 - 1, intValue17);
         drawContext1.fill(intValue14 - 2, intValue18 - 1, intValue15, intValue13, intValue16);
         drawContext1.drawText(this.client.textRenderer, text7, intValue14 + intValue9 / 2 - intValue8 / 2, intValue18 - 9, -1, false);

         for (int intValue19 = 0; intValue19 < intValue22; intValue19++) {
            dev.leg1tka.render.hud.ScoreboardLine value20 = (dev.leg1tka.render.hud.ScoreboardLine)list6.get(intValue19);
            int intValue21 = intValue13 - (intValue22 - intValue19) * 9;
            drawContext1.drawText(this.client.textRenderer, value20.a, intValue14, intValue21, -1, false);
            drawContext1.drawText(this.client.textRenderer, value20.b, intValue15 - value20.c, intValue21, -1, false);
         }

         callbackInfo3.cancel();
      }
   }

   private boolean AspectDLC$shouldPatchScoreboard() {
      return ModuleManager.a != null && ModuleManager.A != null && ModuleManager.A.isEnabled();
   }

   private Text AspectDLC$patchText(Text text1) {
      NameProtect nameProtect2 = ModuleManager.A;
      Text text3 = nameProtect2.b(text1);
      String string4 = text3.getString();
      if (nameProtect2.q()) {
         if (string4.contains("Анархия-")) {
            string4 = string4.replaceAll("Анархия-\\d+", "AspectDLC.fun");
         }

         if (string4.contains("ГРИФ #")) {
            string4 = string4.replaceAll("ГРИФ #\\d+", "AspectDLC.fun");
         }
      }

      return (Text)(string4.equals(text3.getString()) ? text3 : Text.literal(string4).setStyle(text3.getStyle().withColor(TextColor.fromRgb(15557921))));
   }
}
