package dev.leg1tka.api.utils.rpc.utils;

import com.sun.jna.Structure;
import dev.leg1tka.api.utils.rpc.callbacks.DisconnectedCallback;
import dev.leg1tka.api.utils.rpc.callbacks.ErroredCallback;
import dev.leg1tka.api.utils.rpc.callbacks.JoinGameCallback;
import dev.leg1tka.api.utils.rpc.callbacks.JoinRequestCallback;
import dev.leg1tka.api.utils.rpc.callbacks.ReadyCallback;
import dev.leg1tka.api.utils.rpc.callbacks.SpectateGameCallback;
import java.util.Arrays;
import java.util.List;

public class DiscordEventHandlers extends Structure {
   public DisconnectedCallback disconnected;
   public JoinRequestCallback joinRequest;
   public SpectateGameCallback spectateGame;
   public ReadyCallback ready;
   public ErroredCallback errored;
   public JoinGameCallback joinGame;

   public DiscordEventHandlers() {
   }

   public List<String> getFieldOrder() {
      return Arrays.asList("ready", "disconnected", "errored", "joinGame", "spectateGame", "joinRequest");
   }

   public static class a {
      private final DiscordEventHandlers a = new DiscordEventHandlers();

      public a() {
      }

      public DiscordEventHandlers a() {
         return this.a;
      }

      public DiscordEventHandlers.a a(DisconnectedCallback disconnectedCallback1) {
         this.a.disconnected = disconnectedCallback1;
         return this;
      }

      public DiscordEventHandlers.a a(ErroredCallback erroredCallback1) {
         this.a.errored = erroredCallback1;
         return this;
      }

      public DiscordEventHandlers.a a(ReadyCallback readyCallback1) {
         this.a.ready = readyCallback1;
         return this;
      }

      public DiscordEventHandlers.a a(JoinRequestCallback joinRequestCallback1) {
         this.a.joinRequest = joinRequestCallback1;
         return this;
      }

      public DiscordEventHandlers.a a(JoinGameCallback joinGameCallback1) {
         this.a.joinGame = joinGameCallback1;
         return this;
      }

      public DiscordEventHandlers.a a(SpectateGameCallback spectateGameCallback1) {
         this.a.spectateGame = spectateGameCallback1;
         return this;
      }
   }
}
