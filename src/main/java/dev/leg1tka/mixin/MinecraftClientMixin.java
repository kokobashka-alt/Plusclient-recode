package dev.leg1tka.mixin;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventGameUpdate;
import dev.leg1tka.api.events.implement.EventItemUse;
import dev.leg1tka.api.events.implement.EventTickPost;
import dev.leg1tka.api.events.implement.EventTickPre;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.network.bots.BotController;
import dev.leg1tka.network.PacketDelayQueue;
import dev.leg1tka.api.utils.player.BlockBreakingState;
import dev.leg1tka.api.utils.player.RotationMovementSync;
import dev.leg1tka.network.ProxySessionBridge;
import dev.leg1tka.modules.player.OpenWalls;
import dev.leg1tka.modules.render.ShaderCrystal;
import dev.leg1tka.gui.menu.MenuNavigation;
import java.lang.reflect.InvocationTargetException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MinecraftClient.class})
public abstract class MinecraftClientMixin {
   @Shadow
   protected int attackCooldown;
   @Unique
   private long lastHookTime = Util.getMeasuringTimeNano();
   @Unique
   private int accumulatedCalls = 0;

   public MinecraftClientMixin() {
   }

   @Inject(method = "getWindowTitle()Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
   private void aspect$windowTitle(CallbackInfoReturnable<String> callback) {
      callback.setReturnValue("cracked by t.me/readysrs");
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("HEAD")}
   )
   public void tick(CallbackInfo callbackInfo1) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      dev.leg1tka.network.bots.BotController.i();
      dev.leg1tka.network.PacketDelayQueue.h();
      if (EventInvoker.hasListeners(EventTickPre.class)) {
         EventTickPre eventTickPre2 = new EventTickPre();
         EventInvoker.invoke(eventTickPre2);
      }

      dev.leg1tka.api.utils.player.RotationMovementSync.a();
   }

   @Inject(
      method = {"handleBlockBreaking(Z)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void keepModuleBreaking(boolean flag1, CallbackInfo callbackInfo2) {
      if (!flag1 && dev.leg1tka.api.utils.player.BlockBreakingState.b()) {
         this.attackCooldown = 0;
         callbackInfo2.cancel();
      }
   }

   @Inject(
      method = {"doItemUse()V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void openWalls$doItemUse(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventItemUse.class)) {
         EventItemUse eventItemUse2 = new EventItemUse();
         eventItemUse2.call();
         if (eventItemUse2.isCancelled()) {
            ((MinecraftClient)(Object)this).itemUseCooldown = 4;
            callbackInfo1.cancel();
            return;
         }
      }

      OpenWalls openWalls3 = OpenWalls.c;
      if (openWalls3 != null && openWalls3.isEnabled() && openWalls3.p()) {
         callbackInfo1.cancel();
      }
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("RETURN")}
   )
   public void tickEnd(CallbackInfo callbackInfo1) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      dev.leg1tka.network.ProxySessionBridge.i();
      if (EventInvoker.hasListeners(EventTickPost.class)) {
         EventTickPost eventTickPost2 = new EventTickPost();
         EventInvoker.invoke(eventTickPost2);
      }
   }

   @Inject(
      method = {"render(Z)V"},
      at = {@At("HEAD")}
   )
   private void render(boolean flag1, CallbackInfo callbackInfo2) throws InvocationTargetException, IllegalAccessException, InstantiationException {
      if (!EventInvoker.hasListeners(EventGameUpdate.class)) {
         this.lastHookTime = Util.getMeasuringTimeNano();
         this.accumulatedCalls = 0;
      } else {
         long longValue3 = Util.getMeasuringTimeNano();
         long longValue5 = longValue3 - this.lastHookTime;
         this.accumulatedCalls += (int)(longValue5 / 4166666L);
         this.lastHookTime = this.lastHookTime + (long)this.accumulatedCalls * 4166666L;

         for (this.accumulatedCalls = Math.min(this.accumulatedCalls, 240); this.accumulatedCalls > 0; this.accumulatedCalls--) {
            EventInvoker.invoke(new EventGameUpdate());
         }
      }
   }

   @Inject(
      method = {"hasOutline(Lnet/minecraft/entity/Entity;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void AspectDLC$hasOutline(Entity entity1, CallbackInfoReturnable<Boolean> callbackInfoReturnable2) {
      if (ModuleManager.a != null) {
         ShaderCrystal shaderCrystal3 = ModuleManager.aq;
         if (shaderCrystal3 != null && shaderCrystal3.a(entity1)) {
            callbackInfoReturnable2.setReturnValue(true);
         }
      }
   }

   @Inject(
      method = {"setScreen(Lnet/minecraft/client/gui/screen/Screen;)V"},
      at = {@At("HEAD")}
   )
   private void onSetScreen(Screen screen1, CallbackInfo callbackInfo2) {
      dev.leg1tka.gui.menu.MenuNavigation.a(screen1);
   }

   @Inject(
      method = {"stop()V"},
      at = {@At("HEAD")}
   )
   private void onStopHead(CallbackInfo callbackInfo1) {
      try {
         AspectDLC.INSTANCE.closeMinecraft();
      } catch (Throwable throwable3) {
      }
   }
}
