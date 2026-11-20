package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.session.Session;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.ClientConnection;
import net.minecraft.text.Text;

public final class BotSession {
   final String a;
   final String b;
   final ClientConnection c;
   final Session d;
   final BossBarHud e;
   final dev.leg1tka.network.bots.BotInput f = new dev.leg1tka.network.bots.BotInput();
   ClientPlayNetworkHandler g;
   ClientWorld h;
   ClientPlayerEntity i;
   ClientPlayerInteractionManager j;
   Text k;
   Text l;
   final ArrayDeque m = new ArrayDeque();
   final ArrayList n = new ArrayList();
   boolean o;
   volatile boolean p;
   volatile Text q;
   int r;
   int s;
   int t;
   int u;
   boolean v;

   BotSession(String string1, String string2, ClientPlayNetworkHandler clientPlayNetworkHandler3, ClientWorld clientWorld4, ClientPlayerEntity clientPlayerEntity5, ClientPlayerInteractionManager clientPlayerInteractionManager6, Session session7) {
      this.a = string1;
      this.b = string2;
      this.g = clientPlayNetworkHandler3;
      this.c = clientPlayNetworkHandler3.getConnection();
      this.h = clientWorld4;
      this.i = clientPlayerEntity5;
      this.j = clientPlayerInteractionManager6;
      this.d = session7;
      this.e = new BossBarHud(MinecraftClient.getInstance());
   }

   public String a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public ClientPlayerEntity c() {
      return this.i;
   }

   public ClientWorld d() {
      return this.h;
   }

   public dev.leg1tka.network.bots.BotInput e() {
      return this.f;
   }

   public boolean f() {
      return this.c.getPacketListener() == this.g && this.i != null && this.h != null && this.j != null;
   }

   boolean g() {
      return !this.p && this.c.isOpen();
   }
}
