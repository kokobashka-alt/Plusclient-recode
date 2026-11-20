package dev.leg1tka.mixin;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({GameOptions.class})
public interface GameOptionsAccessor {
   @Accessor("serverViewDistance")
   int aspect$getServerViewDistance();

   @Accessor("serverViewDistance")
   void aspect$setServerViewDistance(int intValue1);
}
