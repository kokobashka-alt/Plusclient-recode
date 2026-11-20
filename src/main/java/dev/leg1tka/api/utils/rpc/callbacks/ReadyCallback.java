package dev.leg1tka.api.utils.rpc.callbacks;

import com.sun.jna.Callback;
import dev.leg1tka.api.utils.rpc.utils.DiscordUser;

public interface ReadyCallback extends Callback {
   void apply(DiscordUser discordUser1);
}
