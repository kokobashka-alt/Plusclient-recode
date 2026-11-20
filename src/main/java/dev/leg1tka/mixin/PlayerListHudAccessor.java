package dev.leg1tka.mixin;

import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({PlayerListHud.class})
public interface PlayerListHudAccessor {
   @Accessor("header")
   Text aspect$getHeader();

   @Accessor("footer")
   Text aspect$getFooter();
}
