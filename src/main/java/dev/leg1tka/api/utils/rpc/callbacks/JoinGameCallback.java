package dev.leg1tka.api.utils.rpc.callbacks;

import com.sun.jna.Callback;

public interface JoinGameCallback extends Callback {
   void apply(String string1);
}
