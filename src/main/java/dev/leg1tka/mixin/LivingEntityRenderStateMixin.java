package dev.leg1tka.mixin;

import dev.leg1tka.render.InvisibleTargetAccess;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({LivingEntityRenderState.class})
public class LivingEntityRenderStateMixin implements dev.leg1tka.render.InvisibleTargetAccess {
   @Unique
   private boolean AspectDLC$seeInvisiblesTarget;

   public LivingEntityRenderStateMixin() {
   }

   @Override
   public boolean AspectDLC$isSeeInvisiblesTarget() {
      return this.AspectDLC$seeInvisiblesTarget;
   }

   @Override
   public void AspectDLC$setSeeInvisiblesTarget(boolean flag1) {
      this.AspectDLC$seeInvisiblesTarget = flag1;
   }
}
