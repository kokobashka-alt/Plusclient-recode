package dev.leg1tka.api.utils.rpc.callbacks;

import com.sun.jna.Callback;

public interface SpectateGameCallback extends Callback {
   void apply(String string1);
}
