package dev.leg1tka.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestions;
import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.utils.resources.BaritoneAccess;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatInputSuggestor.SuggestionWindow;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.command.CommandSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ChatInputSuggestor.class})
public abstract class ChatInputSuggestorMixin {
   @Final
   @Shadow
   TextFieldWidget textField;
   @Shadow
   boolean completingSuggestions;
   @Shadow
   private ParseResults<CommandSource> parse;
   @Shadow
   private CompletableFuture<Suggestions> pendingSuggestions;
   @Shadow
   private SuggestionWindow window;

   public ChatInputSuggestorMixin() {
   }

   @Shadow
   public abstract void show(boolean flag1);

   @Inject(
      method = {"refresh()V"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/brigadier/StringReader;canRead()Z",
         remap = false
      )},
      cancellable = true
   )
   public void refresh(CallbackInfo callbackInfo1, @Local StringReader stringReader2) {
      String string3 = stringReader2.getString();
      if (string3.startsWith("#")) {
         if (!dev.leg1tka.api.utils.resources.BaritoneAccess.a()) {
            callbackInfo1.cancel();
         }
      } else if (AspectDLC.INSTANCE != null && AspectDLC.INSTANCE.commandStorage != null) {
         String string4 = AspectDLC.INSTANCE.commandStorage.d();
         if (stringReader2.canRead(string4.length()) && stringReader2.getString().startsWith(string4, stringReader2.getCursor())) {
            stringReader2.setCursor(stringReader2.getCursor() + string4.length());
            CommandDispatcher commandDispatcher6 = AspectDLC.INSTANCE.commandStorage.b();
            if (this.parse == null) {
               this.parse = commandDispatcher6.parse(stringReader2, AspectDLC.INSTANCE.commandStorage.a());
            }

            int intValue5;
            if ((intValue5 = this.textField.getCursor()) >= 1 && (this.window == null || !this.completingSuggestions)) {
               this.pendingSuggestions = commandDispatcher6.getCompletionSuggestions(this.parse, intValue5);
               this.pendingSuggestions.thenRun(() -> {
                  if (this.pendingSuggestions.isDone()) {
                     this.show(false);
                  }
               });
            }

            callbackInfo1.cancel();
         }
      }
   }
}
