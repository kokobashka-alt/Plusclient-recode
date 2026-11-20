package dev.leg1tka.api.utils.rpc.callbacks;

import com.sun.jna.Callback;

public interface DisconnectedCallback extends Callback {
   void apply(int intValue1, String string2);
}
