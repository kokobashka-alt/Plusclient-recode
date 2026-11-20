package dev.leg1tka.mixin;

import com.mojang.authlib.GameProfile;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.implement.EventCloseInv;
import dev.leg1tka.api.events.implement.EventMove;
import dev.leg1tka.api.events.implement.EventOnTravelPost;
import dev.leg1tka.api.events.implement.EventPlayerSync;
import dev.leg1tka.api.events.implement.EventPlayerSyncEnd;
import dev.leg1tka.api.events.implement.EventPostMotion;
import dev.leg1tka.api.events.implement.EventSlowWalking;
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.api.events.implement.EventUpdatePost;
import dev.leg1tka.rotation.RotationStorage;
import dev.leg1tka.managers.ModuleManager;
import dev.leg1tka.api.ClientAccess;
import dev.leg1tka.network.ProxySessionBridge;
import dev.leg1tka.modules.combat.AttackAura;
import dev.leg1tka.modules.movement.NoSlow;
import dev.leg1tka.modules.player.NoClip;
import dev.leg1tka.modules.misc.NoPush;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ClientPlayerEntity.class})
public abstract class ClientPlayerEntityMixin extends PlayerEntity implements dev.leg1tka.api.ClientAccess {
   @Shadow
   @Final
   public ClientPlayNetworkHandler networkHandler;

   public ClientPlayerEntityMixin(World world1, BlockPos blockPos2, float floatValue3, GameProfile gameProfile4) {
      super(world1, blockPos2, floatValue3, gameProfile4);
   }

   @Shadow
   public abstract void closeScreen();

   @Inject(
      method = {"tick()V"},
      at = {@At(
         value = "HEAD",
         target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V"
      )}
   )
   private void onTick(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventUpdate.class)) {
         new EventUpdate().call();
      }
   }

   @Inject(
      method = {"tick()V"},
      at = {@At(
         value = "TAIL",
         target = "Lnet/minecraft/client/network/AbstractClientPlayerEntity;tick()V"
      )}
   )
   private void onTickPost(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventUpdatePost.class)) {
         new EventUpdatePost().call();
      }

      if (EventInvoker.hasListeners(EventPostMotion.class)) {
         new EventPostMotion().call();
      }

      if (this.shouldSyncRotation()) {
         this.headYaw = this.getYaw();
         this.prevHeadYaw = this.getYaw();
         this.bodyYaw = this.getYaw();
         this.prevBodyYaw = this.getYaw();
      }
   }

   @Inject(
      method = {"shouldStopSprinting()Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$rcSprintHoldStop(CallbackInfoReturnable<Boolean> callbackInfoReturnable1) {
      if (AttackAura.c.E.e()) {
         callbackInfoReturnable1.setReturnValue(true);
      } else if (NoSlow.c != null && NoSlow.c.q()) {
         callbackInfoReturnable1.setReturnValue(false);
      }
   }

   @Inject(
      method = {"canStartSprinting()Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$rcSprintHoldStart(CallbackInfoReturnable<Boolean> callbackInfoReturnable1) {
      if (AttackAura.c.E.e()) {
         callbackInfoReturnable1.setReturnValue(false);
      }
   }

   @Inject(
      method = {"sendMovementPackets()V"},
      at = {@At("HEAD")}
   )
   private void sendMovementPackets(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventPlayerSync.class)) {
         new EventPlayerSync().call();
      }
   }

   @Inject(
      method = {"tickMovement()V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void aspect$proxyFreeze(CallbackInfo callbackInfo1) {
      if (dev.leg1tka.network.ProxySessionBridge.e()) {
         callbackInfo1.cancel();
      }
   }

   @Inject(
      method = {"sendMovementPackets()V"},
      at = {@At("RETURN")}
   )
   private void sendMovementPacketsEnd(CallbackInfo callbackInfo1) {
      if (EventInvoker.hasListeners(EventPlayerSyncEnd.class)) {
         new EventPlayerSyncEnd().call();
      }
   }

   @Unique
   private boolean shouldSyncRotation() {
      return ModuleManager.i != null && ModuleManager.i.isEnabled() && AttackAura.I != null && AttackAura.I.getValue() && dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.b();
   }

   @Redirect(
      method = {"tickMovement()V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
      ),
      require = 0
   )
   private boolean onSlowDownRedirect(ClientPlayerEntity clientPlayerEntity1) {
      if (clientPlayerEntity1.isUsingItem()) {
         EventSlowWalking eventSlowWalking2 = new EventSlowWalking();
         eventSlowWalking2.call();
         return clientPlayerEntity1.isUsingItem() && clientPlayerEntity1.getVehicle() == null && !eventSlowWalking2.isCancelled();
      } else {
         return clientPlayerEntity1.isUsingItem() && clientPlayerEntity1.getVehicle() == null;
      }
   }

   @Inject(
      method = {"pushOutOfBlocks(DD)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void pushOutOfBlocks(double doubleValue1, double doubleValue3, CallbackInfo callbackInfo5) {
      if (NoPush.c.isEnabled() && NoPush.c.p().a("Блоки") || NoClip.p()) {
         callbackInfo5.cancel();
      }
   }

   @Inject(
      method = {"move(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onMoveHook(MovementType movementType1, Vec3d vec3d2, @NotNull CallbackInfo callbackInfo3) {
      EventMove eventMove4 = new EventMove(vec3d2);
      eventMove4.call();
      if (eventMove4.isCancelled() || !eventMove4.getMovePos().equals(vec3d2)) {
         if (eventMove4.isCancelled()) {
            callbackInfo3.cancel();
         } else {
            double doubleValue5 = this.getX();
            double doubleValue7 = this.getZ();
            super.move(movementType1, eventMove4.getMovePos());
            float floatValue9 = (float)Math.sqrt(Math.pow(this.getX() - doubleValue5, 2.0) + Math.pow(this.getZ() - doubleValue7, 2.0));
            this.updateLimbs(floatValue9);
            callbackInfo3.cancel();
         }
      }
   }

   @Inject(
      method = {"closeHandledScreen()V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onCloseHandledScreen(CallbackInfo callbackInfo1) {
      int intValue2 = this.currentScreenHandler.syncId;
      EventCloseInv eventCloseInv3 = new EventCloseInv(intValue2);
      eventCloseInv3.call();
      if (!eventCloseInv3.isCancelled()) {
         this.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(intValue2));
      }

      this.closeScreen();
      callbackInfo1.cancel();
   }

   @Inject(
      method = {"dropSelectedItem(Z)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onDropSelectedItem(boolean flag1, CallbackInfoReturnable<Boolean> callbackInfoReturnable2) {
      if (dev.leg1tka.network.ProxySessionBridge.d()) {
         callbackInfoReturnable2.setReturnValue(false);
      } else {
         if (ModuleManager.Y != null && ModuleManager.Y.p()) {
            callbackInfoReturnable2.setReturnValue(false);
         }
      }
   }

   @Inject(
      method = {"tick()V"},
      at = {@At("TAIL")}
   )
   private void onTickElytraVelocity(CallbackInfo callbackInfo1) {
      if (this.isGliding()) {
         if (EventInvoker.hasListeners(EventOnTravelPost.class)) {
            Vec3d vec3d2 = this.getVelocity();
            EventOnTravelPost eventOnTravelPost3 = new EventOnTravelPost(vec3d2.multiply(0.99F, 0.98F, 0.99F));
            eventOnTravelPost3.call();
            if (!eventOnTravelPost3.getOldVelocity().equals(vec3d2.multiply(0.99F, 0.98F, 0.99F))) {
               this.setVelocity(eventOnTravelPost3.getOldVelocity());
            }
         }
      }
   }
}
