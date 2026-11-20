package dev.leg1tka.mixin;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.network.DisconnectionInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({DisconnectedScreen.class})
public interface DisconnectedScreenAccessor {
   @Accessor("info")
   DisconnectionInfo aspect$getInfo();
}
