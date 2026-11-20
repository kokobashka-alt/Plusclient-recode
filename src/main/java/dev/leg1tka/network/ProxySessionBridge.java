package dev.leg1tka.network;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.ScheduledFuture;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.ingame.AbstractSignEditScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkState;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.handler.SizePrepender;
import net.minecraft.network.handler.SplitterHandler;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.ClientOptionsC2SPacket;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.CookieResponseC2SPacket;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.config.ReadyC2SPacket;
import net.minecraft.network.packet.c2s.config.SelectKnownPacksC2SPacket;
import net.minecraft.network.packet.c2s.handshake.ConnectionIntent;
import net.minecraft.network.packet.c2s.handshake.HandshakeC2SPacket;
import net.minecraft.network.packet.c2s.login.EnterConfigurationC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginHelloC2SPacket;
import net.minecraft.network.packet.c2s.play.AcknowledgeChunksC2SPacket;
import net.minecraft.network.packet.c2s.play.AcknowledgeReconfigurationC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatCommandSignedC2SPacket;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.CommandExecutionC2SPacket;
import net.minecraft.network.packet.c2s.play.DebugSampleSubscriptionC2SPacket;
import net.minecraft.network.packet.c2s.play.MessageAcknowledgmentC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerLoadedC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerSessionC2SPacket;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSignC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.c2s.query.QueryPingC2SPacket;
import net.minecraft.network.packet.c2s.query.QueryRequestC2SPacket;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.common.ServerLinksS2CPacket;
import net.minecraft.network.packet.s2c.common.SynchronizeTagsS2CPacket;
import net.minecraft.network.packet.s2c.config.DynamicRegistriesS2CPacket;
import net.minecraft.network.packet.s2c.config.FeaturesS2CPacket;
import net.minecraft.network.packet.s2c.config.ReadyS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginDisconnectS2CPacket;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.EnterReconfigurationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry;
import net.minecraft.network.packet.s2c.query.PingResultS2CPacket;
import net.minecraft.network.packet.s2c.query.QueryResponseS2CPacket;
import net.minecraft.network.state.ConfigurationStates;
import net.minecraft.network.state.HandshakeStates;
import net.minecraft.network.state.LoginStates;
import net.minecraft.network.state.PlayStateFactories;
import net.minecraft.network.state.QueryStates;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SerializableRegistries;
import net.minecraft.registry.DynamicRegistryManager.Immutable;
import net.minecraft.registry.tag.TagPacketSerializer;
import net.minecraft.registry.tag.TagPacketSerializer.Serialized;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.server.ServerMetadata;
import net.minecraft.server.ServerLinks.Known;
import net.minecraft.server.ServerLinks.StringifiedEntry;
import net.minecraft.server.ServerMetadata.Players;
import net.minecraft.server.ServerMetadata.Version;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import org.slf4j.Logger;
import sun.misc.Unsafe;

public final class ProxySessionBridge {
   static final MinecraftClient a = MinecraftClient.getInstance();
   private static final Logger j = LogUtils.getLogger();
   private static Unsafe k;
   static int b;
   static int c;
   static int d;
   static int e;
   static int f;
   static int g;
   private static int l;
   private static int m;
   private static long n;
   private static long o;
   private static EventLoopGroup p;
   private static EventLoopGroup q;
   private static Channel r;
   private static ScheduledFuture s;
   private static volatile boolean t;
   private static int u;
   static volatile ClientConnection v;
   static volatile Channel w;
   static volatile dev.leg1tka.network.ProxySessionBridge.b x;
   static volatile long y;
   private static volatile int z;
   private static volatile boolean A;
   private static volatile long B;
   private static volatile int C;
   private static volatile boolean D;
   static volatile boolean E;
   private static volatile boolean F;
   private static int G;
   private static int H;
   private static int I;
   static dev.leg1tka.network.bots.BotTickPackets h;
   static dev.leg1tka.network.bots.BotTickPackets i;
   private static volatile boolean J;
   private static volatile BlockPos K;
   private static volatile Direction L;
   private static volatile int M;
   private static volatile boolean N;
   private static volatile boolean O;
   private static volatile boolean P;
   private static AtomicBoolean Q;
   private static AtomicInteger R;
   private static ThreadLocal S;
   static dev.leg1tka.network.SequenceCallbackQueue T;
   static dev.leg1tka.network.SequenceCallbackQueue U;
   private static volatile DynamicRegistryManager V;
   private static volatile NetworkState W;

   private ProxySessionBridge() {
   }

   public static synchronized boolean a(int intValue0) {
      if (t) {
         if (u == intValue0) {
            a(
               "§eПрокси уже работает на порту "
                  + u
            );
            return true;
         }

         a();
      }

      u = intValue0;

      try {
         p = new NioEventLoopGroup(1, new DefaultThreadFactory("proxy-boss", true));
         q = new NioEventLoopGroup(1, new DefaultThreadFactory("proxy-work", true));
         ServerBootstrap serverBootstrap1 = ((ServerBootstrap)((ServerBootstrap)new ServerBootstrap().group(p, q).channel(NioServerSocketChannel.class))
               .option(ChannelOption.SO_REUSEADDR, true))
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childHandler(new ChannelInitializer<SocketChannel>() {
               @Override
               protected void initChannel(SocketChannel socketChannel1) {
                  socketChannel1.pipeline().addLast("timeout", new ReadTimeoutHandler(120));
                  socketChannel1.pipeline().addLast("splitter", new SplitterHandler(null));
                  socketChannel1.pipeline().addLast("decoder", new dev.leg1tka.network.ClientPacketDecoder(HandshakeStates.C2S));
                  socketChannel1.pipeline().addLast("prepender", new SizePrepender());
                  socketChannel1.pipeline().addLast("encoder", new dev.leg1tka.network.ClientPacketEncoder(LoginStates.S2C));
                  socketChannel1.pipeline().addLast("handler", new dev.leg1tka.network.ProxySessionBridge.b());
               }
            });
         r = serverBootstrap1.bind(u).sync().channel();
         s = q.next().scheduleAtFixedRate(dev.leg1tka.network.ProxySessionBridge::v, 50L, 50L, TimeUnit.MILLISECONDS);
         t = true;
         R.set(0);
         a("§aПрокси поднят на порту " + u);
         return true;
      } catch (Exception exception2) {
         a();
         a("§cОшибка запуска прокси: " + exception2.getMessage());
         return false;
      }
   }

   public static synchronized void a() {
      if (t || r != null || p != null) {
         boolean flag0 = t;
         t = false;
         dev.leg1tka.network.ProxySessionBridge.b value1 = x;
         if (value1 != null) {
            value1.a(Text.literal("Прокси остановлен"));
            a(value1);
         }

         if (s != null) {
            s.cancel(false);
            s = null;
         }

         if (r != null) {
            r.close();
            r = null;
         }

         if (p != null) {
            p.shutdownGracefully(0L, 200L, TimeUnit.MILLISECONDS);
            p = null;
         }

         if (q != null) {
            q.shutdownGracefully(0L, 200L, TimeUnit.MILLISECONDS);
            q = null;
         }

         T.a();
         U.a();
         if (flag0) {
            a("§cПрокси остановлен");
         }
      }
   }

   public static boolean b() {
      return t;
   }

   public static boolean c() {
      return z != 0 && z != 1;
   }

   public static boolean d() {
      return E;
   }

   public static boolean e() {
      return F;
   }

   public static boolean f() {
      return (Boolean)S.get();
   }

   public static void a(boolean flag0) {
      dev.leg1tka.network.ProxySessionBridge.b value1 = x;
      if (value1 != null && value1.a()) {
         boolean flag2 = flag0 || !ProxySwapper.c.q();
         switch (z) {
            case 0:
               A = flag2;
               B = System.currentTimeMillis() + 5000L;
               z = 1;
               if (!flag2) {
                  a(
                     "§eПередаю управление чистому клиенту..."
                  );
               }
               break;
            case 1:
               z = 0;
               a("§7Передача отменена");
               break;
            case 2:
            default:
               a(
                  "§eСмена управления уже идёт"
               );
               break;
            case 3:
               A = flag2;
               B = System.currentTimeMillis() + 5000L;
               z = 4;
               if (!flag2) {
                  a("§eВозвращаю управление Aspect...");
               }
               break;
            case 4:
               z = 3;
               a("§7Возврат отменён");
         }
      } else {
         a("§cЧистый клиент не в игре");
      }
   }

   public static void g() {
      if (!t) {
         a("§7Прокси: §cвыключен");
      } else {
         dev.leg1tka.network.ProxySessionBridge.b value0 = x;
         String string1 = value0 != null && value0.a()
            ? "§a" + value0.a
            : (value0 != null ? "§eвход..." : "§cожидание");

         String string2 = switch (z) {
            case 2, 5 -> "§eсмена...";
            case 3, 4 -> "§eЧистый клиент";
            default -> "§bAspect";
         };
         int intValue3 = R.get();
         a(
            "§7Прокси: §a127.0.0.1:"
               + u
               + " §7| Клиент: "
               + string1
               + " §7| Управление: "
               + string2
               + (intValue3 > 0 ? " §7| §cошибок кодека: " + intValue3 : "")
         );
      }
   }

   public static void h() {
      dev.leg1tka.network.ProxySessionBridge.b value0 = x;
      if (value0 != null) {
         value0.a(Text.literal("Отключён из Aspect"));
      } else {
         a("§cНет активного клиента");
      }
   }

   public static void a(ClientConnection clientConnection0, Channel channel1, PacketListener packetListener2, Packet<?> packet3) {
      if (packet3 instanceof LoginSuccessS2CPacket) {
         a(clientConnection0, channel1);
         dev.leg1tka.network.PacketRegistry.a();
      } else {
         if (clientConnection0 != v) {
            if (v != null || !(packetListener2 instanceof ClientPlayNetworkHandler clientPlayNetworkHandler4) || a.getNetworkHandler() != clientPlayNetworkHandler4) {
               return;
            }

            a(clientConnection0, channel1);
         }

         long longValue8 = ++y;
         dev.leg1tka.network.PacketRegistry.a(packet3, packetListener2);
         if (packet3 instanceof UpdateSelectedSlotS2CPacket updateSelectedSlotS2CPacket6) {
            h.k = updateSelectedSlotS2CPacket6.slot();
         }

         if (t) {
            dev.leg1tka.network.ProxySessionBridge.b value9 = x;
            if (packet3 instanceof DisconnectS2CPacket disconnectS2CPacket7) {
               if (value9 != null) {
                  value9.a(disconnectS2CPacket7.reason());
               }

               return;
            }

            if (packet3 instanceof EnterReconfigurationS2CPacket) {
               u();
            }

            if (value9 != null) {
               value9.a(packet3, packetListener2, longValue8);
            }
         }
      }
   }

   public static int a(ClientConnection clientConnection0, Packet<?> packet1) {
      if (!t || clientConnection0 != v) {
         return 0;
      } else if (packet1 instanceof CommonPongC2SPacket commonPongC2SPacket3) {
         return T.a(false, commonPongC2SPacket3.getParameter());
      } else if (packet1 instanceof TeleportConfirmC2SPacket teleportConfirmC2SPacket2) {
         return U.a(false, teleportConfirmC2SPacket2.getTeleportId());
      } else if (E) {
         return c(packet1) ? 0 : 1;
      } else {
         h.a(packet1);
         return 0;
      }
   }

   public static void i() {
      if (t) {
         ClientPlayerEntity clientPlayerEntity0 = a.player;
         if (clientPlayerEntity0 != null) {
            N = clientPlayerEntity0.isGliding();
            O = clientPlayerEntity0.hasVehicle();
            P = ((Byte)clientPlayerEntity0.getDataTracker().get(LivingEntity.LIVING_FLAGS) & 1) != 0;
            dev.leg1tka.network.ProxySessionBridge.b value1 = x;
            boolean flag2 = value1 != null && value1.a();
            if (z == 1) {
               if (!flag2) {
                  z = 0;
                  a("§cЧистый клиент не в игре");
               } else if (!A && a(clientPlayerEntity0) != null) {
                  if (System.currentTimeMillis() > B) {
                     z = 0;
                     a(
                        "§cНе передал управление: "
                           + a(clientPlayerEntity0)
                     );
                  }
               } else {
                  a(clientPlayerEntity0, value1);
               }
            }

            if (flag2 && z == 0 && ++I >= 10) {
               I = 0;
               if (i.q && clientPlayerEntity0.squaredDistanceTo(i.a, i.b, i.c) > 36.0) {
                  value1.a(
                     List.of(
                        new PlayerPositionLookS2CPacket(
                           t(),
                           new PlayerPosition(clientPlayerEntity0.getPos(), Vec3d.ZERO, 0.0F, 0.0F),
                           EnumSet.of(PositionFlag.X_ROT, PositionFlag.Y_ROT, PositionFlag.DELTA_X, PositionFlag.DELTA_Y, PositionFlag.DELTA_Z)
                        )
                     )
                  );
               }
            }
         }
      }
   }

   private static String a(ClientPlayerEntity clientPlayerEntity0) {
      if (!clientPlayerEntity0.isOnGround()) {
         return "игрок не на земле";
      } else if (clientPlayerEntity0.isUsingItem()) {
         return "используется предмет";
      } else if (clientPlayerEntity0.isGliding()) {
         return "полёт на элитрах";
      } else if (clientPlayerEntity0.hasVehicle()) {
         return "игрок верхом";
      } else if (a.interactionManager != null && a.interactionManager.isBreakingBlock()) {
         return "ломается блок";
      } else {
         return clientPlayerEntity0.currentScreenHandler != clientPlayerEntity0.playerScreenHandler
            ? "открыт контейнер"
            : null;
      }
   }

   private static String k() {
      if (!h.f) {
         return "игрок не на земле";
      } else if (P) {
         return "используется предмет";
      } else if (N) {
         return "полёт на элитрах";
      } else if (O) {
         return "игрок верхом";
      } else if (J) {
         return "ломается блок";
      } else {
         return M != 0 ? "открыт контейнер" : null;
      }
   }

   private static void a(ClientPlayerEntity clientPlayerEntity0, dev.leg1tka.network.ProxySessionBridge.b value1) {
      if (A && a.interactionManager != null) {
         if (clientPlayerEntity0.isUsingItem()) {
            a.interactionManager.stopUsingItem(clientPlayerEntity0);
         }

         if (a.interactionManager.isBreakingBlock()) {
            a.interactionManager.cancelBlockBreaking();
         }

         if (clientPlayerEntity0.currentScreenHandler != clientPlayerEntity0.playerScreenHandler) {
            clientPlayerEntity0.closeHandledScreen();
         }
      }

      E = true;
      F = true;
      Vec3d vec3d2 = clientPlayerEntity0.getVelocity();
      h.m = vec3d2.x;
      h.n = vec3d2.y;
      h.o = vec3d2.z;
      int intValue3 = t();
      C = intValue3;
      D = false;
      GameMode gameMode4 = a.interactionManager != null ? a.interactionManager.getCurrentGameMode() : GameMode.SURVIVAL;
      int intValue5 = h.k >= 0 ? h.k : clientPlayerEntity0.getInventory().selectedSlot;
      ArrayList arrayList6 = new ArrayList();
      Packet packet7 = a(gameMode4);
      if (packet7 != null) {
         arrayList6.add(packet7);
      }

      arrayList6.add(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, (float)gameMode4.getId()));
      arrayList6.add(new PlayerAbilitiesS2CPacket(clientPlayerEntity0.getAbilities()));
      arrayList6.add(new UpdateSelectedSlotS2CPacket(intValue5));
      arrayList6.add(new PlayerPositionLookS2CPacket(intValue3, new PlayerPosition(clientPlayerEntity0.getPos(), vec3d2, h.d, h.e), Collections.emptySet()));
      B = System.currentTimeMillis() + 3000L;
      z = 2;
      value1.a(arrayList6);
   }

   private static void l() {
      z = 3;
      D = true;
      T.a(true);
      U.a(true);
      ClientPlayerEntity clientPlayerEntity0 = a.player;
      if (!i.j.equals(h.j)) {
         b(new PlayerInputC2SPacket(i.j));
         h.j = i.j;
      }

      if (clientPlayerEntity0 != null && i.i != h.i) {
         b(new ClientCommandC2SPacket(clientPlayerEntity0, i.i ? Mode.PRESS_SHIFT_KEY : Mode.RELEASE_SHIFT_KEY));
         h.i = i.i;
      }

      if (clientPlayerEntity0 != null && i.h != h.h) {
         b(new ClientCommandC2SPacket(clientPlayerEntity0, i.h ? Mode.START_SPRINTING : Mode.STOP_SPRINTING));
         h.h = i.h;
      }

      a(
         "§aУправление у чистого клиента"
      );
   }

   private static void m() {
      z = 5;
      h.a();
      a(dev.leg1tka.network.ProxySessionBridge::n);
   }

   private static void n() {
      ClientPlayerEntity clientPlayerEntity0 = a.player;
      boolean flag1 = E;
      if (clientPlayerEntity0 != null && flag1 && h.p) {
         clientPlayerEntity0.setPosition(h.a, h.b, h.c);
         clientPlayerEntity0.setYaw(h.d);
         clientPlayerEntity0.setPitch(h.e);
         clientPlayerEntity0.setVelocity(h.m, h.n, h.o);
         clientPlayerEntity0.setOnGround(h.f);
         clientPlayerEntity0.horizontalCollision = h.g;
         clientPlayerEntity0.lastX = h.a;
         clientPlayerEntity0.lastBaseY = h.b;
         clientPlayerEntity0.lastZ = h.c;
         clientPlayerEntity0.lastYaw = h.d;
         clientPlayerEntity0.lastPitch = h.e;
         clientPlayerEntity0.lastOnGround = h.f;
         clientPlayerEntity0.lastHorizontalCollision = h.g;
         clientPlayerEntity0.lastSprinting = h.h;
         clientPlayerEntity0.lastSneaking = h.i;
         clientPlayerEntity0.lastPlayerInput = h.j;
         clientPlayerEntity0.ticksSinceLastPositionPacketSent = h.l;
         clientPlayerEntity0.setSprinting(h.h);
         if (h.k >= 0 && h.k < 9) {
            clientPlayerEntity0.getInventory().selectedSlot = h.k;
            if (a.interactionManager != null) {
               a.interactionManager.lastSelectedSlot = h.k;
            }
         }
      }

      int intValue2 = M;
      boolean flag3 = J;
      boolean flag4 = P;
      M = 0;
      J = false;
      T.a(false);
      U.a(false);
      z = 0;
      A = false;
      F = false;
      E = false;
      ClientPlayNetworkHandler clientPlayNetworkHandler5 = a.getNetworkHandler();
      if (flag1 && clientPlayerEntity0 != null && clientPlayNetworkHandler5 != null) {
         if (intValue2 != 0) {
            clientPlayNetworkHandler5.sendPacket(new CloseHandledScreenC2SPacket(intValue2));
            if (clientPlayerEntity0.currentScreenHandler.syncId == intValue2) {
               clientPlayerEntity0.closeScreen();
            }
         }

         if (flag4) {
            clientPlayNetworkHandler5.sendPacket(new PlayerActionC2SPacket(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, Direction.DOWN));
         }

         if (flag3) {
            clientPlayNetworkHandler5.sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, K, L));
         }
      }

      dev.leg1tka.network.ProxySessionBridge.b value6 = x;
      if (value6 != null && value6.a()) {
         ArrayList arrayList7 = new ArrayList<>(q());
         if (intValue2 != 0) {
            arrayList7.add(new CloseScreenS2CPacket(intValue2));
         }

         value6.a(arrayList7);
      }

      if (flag1) {
         a("§aУправление у Aspect");
      }
   }

   private static void o() {
      if (Q.compareAndSet(false, true)) {
         a(dev.leg1tka.network.ProxySessionBridge::p);
      }
   }

   private static void p() {
      Q.set(false);
      ClientPlayerEntity clientPlayerEntity0 = a.player;
      if (clientPlayerEntity0 != null && F) {
         clientPlayerEntity0.setPosition(h.a, h.b, h.c);
         clientPlayerEntity0.setYaw(h.d);
         clientPlayerEntity0.setPitch(h.e);
         clientPlayerEntity0.setHeadYaw(h.d);
         clientPlayerEntity0.setVelocity(Vec3d.ZERO);
         clientPlayerEntity0.setOnGround(h.f);
         if (h.k >= 0 && h.k < 9) {
            clientPlayerEntity0.getInventory().selectedSlot = h.k;
            if (a.interactionManager != null) {
               a.interactionManager.lastSelectedSlot = h.k;
            }
         }
      }
   }

   static void a(Packet<?> packet0) {
      if (packet0 instanceof CommonPongC2SPacket commonPongC2SPacket10) {
         T.a(true, commonPongC2SPacket10.getParameter());
      } else if (packet0 instanceof TeleportConfirmC2SPacket teleportConfirmC2SPacket9) {
         int intValue14 = teleportConfirmC2SPacket9.getTeleportId();
         if (intValue14 < -2147483647 || intValue14 >= -2146435071) {
            U.a(true, intValue14);
         } else if (z == 2 && intValue14 == C) {
            l();
         }
      } else if (packet0 instanceof ChatMessageC2SPacket chatMessageC2SPacket8) {
         String string13 = chatMessageC2SPacket8.chatMessage();
         a((Runnable)(() -> {
            if (a.getNetworkHandler() != null) {
               a.getNetworkHandler().sendChatMessage(string13);
            }
         }));
      } else if (packet0 instanceof ChatCommandSignedC2SPacket chatCommandSignedC2SPacket7) {
         b(chatCommandSignedC2SPacket7.command());
      } else if (packet0 instanceof CommandExecutionC2SPacket commandExecutionC2SPacket6) {
         b(commandExecutionC2SPacket6.command());
      } else if (packet0 instanceof RequestCommandCompletionsC2SPacket) {
         b(packet0);
      } else {
         i.a(packet0);
         int intValue1 = z;
         if (intValue1 == 3 || intValue1 == 4) {
            if (D) {
               D = false;
               if (packet0 instanceof Full) {
                  return;
               }
            }

            if (packet0 instanceof UpdateSelectedSlotC2SPacket updateSelectedSlotC2SPacket2 && updateSelectedSlotC2SPacket2.getSelectedSlot() == h.k) {
               return;
            }

            if (packet0 instanceof PlayerActionC2SPacket playerActionC2SPacket11) {
               switch (playerActionC2SPacket11.getAction()) {
                  case START_DESTROY_BLOCK:
                     J = true;
                     K = playerActionC2SPacket11.getPos();
                     L = playerActionC2SPacket11.getDirection();
                     break;
                  case ABORT_DESTROY_BLOCK:
                  case STOP_DESTROY_BLOCK:
                     J = false;
               }
            } else if (packet0 instanceof CloseHandledScreenC2SPacket closeHandledScreenC2SPacket3) {
               int intValue5 = closeHandledScreenC2SPacket3.getSyncId();
               M = 0;
               a((Runnable)(() -> {
                  ClientPlayerEntity clientPlayerEntity1x = a.player;
                  if (clientPlayerEntity1x != null && clientPlayerEntity1x.currentScreenHandler.syncId == intValue5 && clientPlayerEntity1x.currentScreenHandler != clientPlayerEntity1x.playerScreenHandler) {
                     clientPlayerEntity1x.closeScreen();
                  }
               }));
            } else if (packet0 instanceof UpdateSignC2SPacket) {
               a((Runnable)(() -> {
                  if (a.currentScreen instanceof AbstractSignEditScreen) {
                     a.setScreen(null);
                  }
               }));
            } else if (packet0 instanceof ClientStatusC2SPacket clientStatusC2SPacket4
               && clientStatusC2SPacket4.getMode() == net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket.Mode.PERFORM_RESPAWN) {
               a((Runnable)(() -> {
                  if (a.currentScreen instanceof DeathScreen) {
                     a.setScreen(null);
                  }
               }));
            }

            b(packet0);
            h.a(packet0);
            if (packet0 instanceof PlayerMoveC2SPacket || packet0 instanceof UpdateSelectedSlotC2SPacket) {
               o();
            }

            if (packet0 instanceof ClientTickEndC2SPacket && z == 4) {
               String string12 = k();
               if (!A && string12 != null) {
                  if (System.currentTimeMillis() > B) {
                     z = 3;
                     a("§cНе вернул управление: " + string12);
                  }
               } else {
                  m();
               }
            }
         }
      }
   }

   private static void b(String string0) {
      a((Runnable)(() -> {
         if (a.getNetworkHandler() != null) {
            a.getNetworkHandler().sendChatCommand(string0);
         }
      }));
   }

   static void a(Packet<?> packet0, NetworkState<?> networkState1, dev.leg1tka.network.ProxySessionBridge.a value2) {
      if (!(packet0 instanceof KeepAliveS2CPacket)) {
         if (packet0 instanceof CustomPayloadS2CPacket customPayloadS2CPacket3 && !"minecraft".equals(customPayloadS2CPacket3.payload().getId().id().getNamespace())) {
            return;
         }

         int intValue8 = z;
         boolean flag4 = intValue8 == 0 || intValue8 == 1;
         if (flag4) {
            if (packet0 instanceof GameStateChangeS2CPacket gameStateChangeS2CPacket7 && gameStateChangeS2CPacket7.getReason() == GameStateChangeS2CPacket.GAME_MODE_CHANGED) {
               return;
            }

            if (packet0 instanceof PlayerAbilitiesS2CPacket) {
               return;
            }

            if (packet0 instanceof OpenScreenS2CPacket
               || packet0 instanceof OpenHorseScreenS2CPacket
               || packet0 instanceof SignEditorOpenS2CPacket
               || packet0 instanceof OpenWrittenBookS2CPacket) {
               return;
            }

            if (packet0 instanceof EntityVelocityUpdateS2CPacket entityVelocityUpdateS2CPacket13 && entityVelocityUpdateS2CPacket13.getEntityId() == r()) {
               return;
            }
         } else if (packet0 instanceof OpenScreenS2CPacket openScreenS2CPacket5) {
            M = openScreenS2CPacket5.getSyncId();
         } else if (packet0 instanceof OpenHorseScreenS2CPacket openHorseScreenS2CPacket6) {
            M = openHorseScreenS2CPacket6.getSyncId();
         } else if (packet0 instanceof CloseScreenS2CPacket) {
            M = 0;
         }

         if (packet0 instanceof CommonPingS2CPacket commonPingS2CPacket9) {
            value2.c.add(T.a(commonPingS2CPacket9.getParameter()));
         } else if (packet0 instanceof PlayerPositionLookS2CPacket playerPositionLookS2CPacket11) {
            value2.d.add(U.a(playerPositionLookS2CPacket11.teleportId()));
         }

         value2.a(a(networkState1, packet0));
         if (flag4 && (packet0 instanceof GameJoinS2CPacket || packet0 instanceof PlayerRespawnS2CPacket || packet0 instanceof PlayerListS2CPacket playerListS2CPacket10 && a(playerListS2CPacket10))) {
            for (Packet packet14 : q()) {
               value2.a(a(networkState1, packet14));
            }
         }
      }
   }

   private static boolean a(PlayerListS2CPacket playerListS2CPacket0) {
      if (!playerListS2CPacket0.actions.contains(net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action.UPDATE_GAME_MODE)
         && !playerListS2CPacket0.actions.contains(net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action.ADD_PLAYER)) {
         return false;
      } else {
         UUID uUID1 = s();
         if (uUID1 == null) {
            return false;
         } else {
            for (Entry entry3 : playerListS2CPacket0.entries) {
               if (uUID1.equals(entry3.profileId())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private static List<Packet<?>> q() {
      ArrayList arrayList0 = new ArrayList(3);
      arrayList0.add(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, (float)GameMode.SPECTATOR.getId()));
      arrayList0.add(j());
      Packet packet1 = a(GameMode.SPECTATOR);
      if (packet1 != null) {
         arrayList0.add(packet1);
      }

      return arrayList0;
   }

   public static PlayerAbilitiesS2CPacket j() {
      PlayerAbilities playerAbilities0 = new PlayerAbilities();
      playerAbilities0.flying = true;
      playerAbilities0.allowFlying = true;
      playerAbilities0.invulnerable = true;
      return new PlayerAbilitiesS2CPacket(playerAbilities0);
   }

   private static Packet<?> a(GameMode gameMode0) {
      UUID uUID1 = s();
      return uUID1 == null
         ? null
         : a(
            EnumSet.of(net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action.UPDATE_GAME_MODE),
            List.of(new Entry(uUID1, null, true, 0, gameMode0, null, true, 0, null))
         );
   }

   public static Packet<?> a(EnumSet<net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action> enumSet0, List<Entry> list1) {
      if (k == null) {
         return null;
      } else {
         try {
            PlayerListS2CPacket playerListS2CPacket2 = (PlayerListS2CPacket)k.allocateInstance(PlayerListS2CPacket.class);
            playerListS2CPacket2.actions = enumSet0;
            playerListS2CPacket2.entries = list1;
            return playerListS2CPacket2;
         } catch (Exception exception3) {
            return null;
         }
      }
   }

   private static int r() {
      ClientPlayerEntity clientPlayerEntity0 = a.player;
      if (clientPlayerEntity0 != null) {
         return clientPlayerEntity0.getId();
      } else {
         GameJoinS2CPacket gameJoinS2CPacket1 = dev.leg1tka.network.PacketRegistry.d;
         return gameJoinS2CPacket1 != null ? gameJoinS2CPacket1.playerEntityId() : Integer.MIN_VALUE;
      }
   }

   private static UUID s() {
      ClientPlayerEntity clientPlayerEntity0 = a.player;
      if (clientPlayerEntity0 != null) {
         return clientPlayerEntity0.getUuid();
      } else {
         ClientPlayNetworkHandler clientPlayNetworkHandler1 = a.getNetworkHandler();
         return clientPlayNetworkHandler1 != null ? clientPlayNetworkHandler1.getProfile().getId() : null;
      }
   }

   static synchronized int t() {
      G = G + 1 & 1048575;
      return -2147483647 + G;
   }

   static void b(Packet<?> packet0) {
      ClientConnection clientConnection1 = v;
      if (clientConnection1 != null && clientConnection1.isOpen()) {
         boolean flag2 = (Boolean)S.get();
         S.set(Boolean.TRUE);

         try {
            clientConnection1.send(packet0);
         } finally {
            S.set(flag2);
         }
      }
   }

   private static boolean c(Packet<?> packet0) {
      return packet0 instanceof KeepAliveC2SPacket
         || packet0 instanceof ClientOptionsC2SPacket
         || packet0 instanceof CustomPayloadC2SPacket
         || packet0 instanceof ResourcePackStatusC2SPacket
         || packet0 instanceof CookieResponseC2SPacket
         || packet0 instanceof AcknowledgeChunksC2SPacket
         || packet0 instanceof PlayerLoadedC2SPacket
         || packet0 instanceof ChatMessageC2SPacket
         || packet0 instanceof ChatCommandSignedC2SPacket
         || packet0 instanceof CommandExecutionC2SPacket
         || packet0 instanceof MessageAcknowledgmentC2SPacket
         || packet0 instanceof PlayerSessionC2SPacket
         || packet0 instanceof AcknowledgeReconfigurationC2SPacket
         || packet0 instanceof ReadyC2SPacket
         || packet0 instanceof SelectKnownPacksC2SPacket
         || packet0 instanceof DebugSampleSubscriptionC2SPacket
         || packet0 instanceof RequestCommandCompletionsC2SPacket;
   }

   private static void a(ClientConnection clientConnection0, Channel channel1) {
      if (v != clientConnection0) {
         dev.leg1tka.network.ProxySessionBridge.b value2 = x;
         if (value2 != null) {
            value2.a(Text.literal("Aspect сменил соединение"));
         }

         v = clientConnection0;
         w = channel1;
         y = 0L;
         T.a();
         U.a();
         z = 0;
         A = false;
         E = false;
         F = false;
         h.p = false;
      }
   }

   private static void u() {
      if (z != 0 || E) {
         z = 0;
         A = false;
         E = false;
         F = false;
         T.a(false);
         U.a(false);
         a(
            "§eСервер перенастраивает соединение — управление у Aspect"
         );
      }
   }

   static void a(dev.leg1tka.network.ProxySessionBridge.b value0) {
      if (x == value0) {
         x = null;
         int intValue1 = z;
         if (intValue1 == 3 || intValue1 == 4) {
            h.a();
         }

         if (E) {
            a(dev.leg1tka.network.ProxySessionBridge::n);
         } else {
            z = 0;
            T.a(false);
            U.a(false);
         }

         if (t) {
            a("§7Чистый клиент отключился");
         }
      }
   }

   private static void v() {
      dev.leg1tka.network.ProxySessionBridge.b value0 = x;
      if (value0 != null) {
         ClientConnection clientConnection1 = v;
         if (clientConnection1 != null && clientConnection1.isOpen()) {
            if (++H >= 20) {
               H = 0;
               value0.b();
            }

            if (z == 2 && System.currentTimeMillis() > B) {
               z = 5;
               a(
                  "§cЧистый клиент не принял управление"
               );
               a(dev.leg1tka.network.ProxySessionBridge::n);
            }
         } else {
            value0.a(
               Text.literal(
                  "Основное соединение разорвано"
               )
            );
         }
      }
   }

   static NetworkState<ClientPlayPacketListener> a(DynamicRegistryManager dynamicRegistryManager0) {
      NetworkState networkState1 = W;
      if (networkState1 == null || V != dynamicRegistryManager0) {
         networkState1 = PlayStateFactories.S2C.bind(RegistryByteBuf.makeFactory(dynamicRegistryManager0));
         W = networkState1;
         V = dynamicRegistryManager0;
      }

      return networkState1;
   }

   public static ByteBuf a(NetworkState<?> networkState0, Packet<?> packet1) {
      ByteBuf byteBuf2 = Unpooled.buffer();

      try {
         ((NetworkState)networkState0).codec().encode(byteBuf2, (Packet)packet1);
         return byteBuf2;
      } catch (Throwable throwable4) {
         byteBuf2.release();
         a("encode " + packet1.getClass().getSimpleName(), throwable4);
         return null;
      }
   }

   static void a(String string0, Throwable throwable1) {
      if (R.incrementAndGet() <= 20) {
         j.warn("[Proxy] {} failed", string0, throwable1);
      }
   }

   static void a(Runnable runnable0) {
      if (a.isOnThread()) {
         runnable0.run();
      } else {
         a.execute(runnable0);
      }
   }

   static void a(String string0) {
      a((Runnable)(() -> {
         if (a.player != null) {
            dev.leg1tka.api.utils.text.ClientChat.a(string0);
         }
      }));
      dev.leg1tka.network.ProxySessionBridge.b value1 = x;
      if (value1 != null) {
         value1.a(List.of(new GameMessageS2CPacket(Text.literal("§7[Proxy] §r" + string0), false)));
      }
   }

   static {
      Unsafe unsafe0 = null;

      try {
         Field field1 = Unsafe.class.getDeclaredField("theUnsafe");
         field1.setAccessible(true);
         unsafe0 = (Unsafe)field1.get(null);
      } catch (Exception exception2) {
      }

      k = unsafe0;
      u = 25565;
      z = 0;
      h = new dev.leg1tka.network.bots.BotTickPackets();
      i = new dev.leg1tka.network.bots.BotTickPackets();
      K = BlockPos.ORIGIN;
      L = Direction.DOWN;
      Q = new AtomicBoolean();
      R = new AtomicInteger();
      S = ThreadLocal.withInitial(() -> Boolean.FALSE);
      T = new dev.leg1tka.network.SequenceCallbackQueue(intValue0x -> b(new CommonPongC2SPacket(intValue0x)));
      U = new dev.leg1tka.network.SequenceCallbackQueue(intValue0x -> b(new TeleportConfirmC2SPacket(intValue0x)));
   }

   static class a {
      final long a;
      final List<ByteBuf> b = new ArrayList<>(2);
      final List<dev.leg1tka.network.SequenceCallbackQueue.a> c = new ArrayList<>(0);
      final List<dev.leg1tka.network.SequenceCallbackQueue.a> d = new ArrayList<>(0);

      a(long longValue1) {
         this.a = longValue1;
      }

      void a(ByteBuf byteBuf1) {
         if (byteBuf1 != null) {
            this.b.add(byteBuf1);
         }
      }

      void a() {
         for (ByteBuf byteBuf2 : this.b) {
            byteBuf2.release();
         }

         this.b.clear();

         for (dev.leg1tka.network.SequenceCallbackQueue.a value5 : this.c) {
            dev.leg1tka.network.ProxySessionBridge.T.a(value5);
         }

         for (dev.leg1tka.network.SequenceCallbackQueue.a value6 : this.d) {
            dev.leg1tka.network.ProxySessionBridge.U.a(value6);
         }
      }
   }

   static class b extends SimpleChannelInboundHandler<Packet<?>> {
      private static int b;
      private static int c;
      private static int d;
      private static int e;
      private static int f;
      private static int g;
      private static int h;
      private static int i;
      private static int j;
      private static int k;
      private volatile int l = 0;
      private volatile Channel m;
      volatile String a = "";
      private NetworkState n;
      private final List<dev.leg1tka.network.ProxySessionBridge.a> o = new ArrayList<>();
      private final List<Packet<?>> p = new ArrayList<>();
      private boolean q;
      private boolean r;
      private DynamicRegistryManager s;

      b() {
      }

      boolean a() {
         return this.l == 5;
      }

      private void a(NetworkState<?> networkState1) {
         ((dev.leg1tka.network.ClientPacketDecoder)this.m.pipeline().get("decoder")).a(networkState1);
      }

      private void b(NetworkState<?> networkState1) {
         ((dev.leg1tka.network.ClientPacketEncoder)this.m.pipeline().get("encoder")).a(networkState1);
      }

      public void channelActive(ChannelHandlerContext channelHandlerContext1) throws Exception {
         super.channelActive(channelHandlerContext1);
         this.m = channelHandlerContext1.channel();
      }

      public void channelInactive(ChannelHandlerContext channelHandlerContext1) throws Exception {
         super.channelInactive(channelHandlerContext1);
         synchronized (this) {
            this.l = 9;

            for (dev.leg1tka.network.ProxySessionBridge.a value4 : this.o) {
               value4.a();
            }

            this.o.clear();
            this.p.clear();
         }

         dev.leg1tka.network.ProxySessionBridge.a(this);
      }

      public void exceptionCaught(ChannelHandlerContext channelHandlerContext1, Throwable throwable2) {
         dev.leg1tka.network.ProxySessionBridge.a("session", throwable2);
         channelHandlerContext1.close();
      }

      @Override
      protected void channelRead0(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2) {
         switch (this.l) {
            case 0:
               this.b(channelHandlerContext1, packet2);
               break;
            case 1:
               this.c(channelHandlerContext1, packet2);
               break;
            case 2:
               this.d(channelHandlerContext1, packet2);
               break;
            case 3:
               if (packet2 instanceof ReadyC2SPacket) {
                  this.c();
               }
            case 4:
            default:
               break;
            case 5:
               if (packet2 instanceof KeepAliveC2SPacket
                  || packet2 instanceof ClientOptionsC2SPacket
                  || packet2 instanceof CustomPayloadC2SPacket
                  || packet2 instanceof ResourcePackStatusC2SPacket
                  || packet2 instanceof CookieResponseC2SPacket
                  || packet2 instanceof AcknowledgeChunksC2SPacket
                  || packet2 instanceof PlayerLoadedC2SPacket
                  || packet2 instanceof PlayerSessionC2SPacket
                  || packet2 instanceof MessageAcknowledgmentC2SPacket
                  || packet2 instanceof DebugSampleSubscriptionC2SPacket
                  || packet2 instanceof AcknowledgeReconfigurationC2SPacket) {
                  return;
               }

               dev.leg1tka.network.ProxySessionBridge.a(packet2);
               break;
            case 6:
               if (packet2 instanceof AcknowledgeReconfigurationC2SPacket) {
                  this.d();
               }
               break;
            case 7:
            case 8:
               if (packet2 instanceof ReadyC2SPacket) {
                  synchronized (this) {
                     this.r = true;
                     if (this.s != null) {
                        this.e();
                     }
                  }
               }
         }
      }

      private void b(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2) {
         if (packet2 instanceof HandshakeC2SPacket handshakeC2SPacket3) {
            if (handshakeC2SPacket3.intendedState() == ConnectionIntent.STATUS) {
               this.a(QueryStates.C2S);
               this.b(QueryStates.S2C);
               this.l = 1;
            } else {
               this.a(LoginStates.C2S);
               this.b(LoginStates.S2C);
               this.l = 2;
               if (handshakeC2SPacket3.protocolVersion() != SharedConstants.getProtocolVersion()) {
                  this.a(channelHandlerContext1, "§cНужна версия " + SharedConstants.getGameVersion().getName());
               }
            }
         } else {
            channelHandlerContext1.close();
         }
      }

      private void c(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2) {
         if (packet2 instanceof QueryRequestC2SPacket) {
            boolean flag4 = ProxySwapper.c.p() && dev.leg1tka.network.ProxySessionBridge.a.getCurrentServerEntry() != null;
            MutableText mutableText5 = flag4
               ? Text.literal("A Minecraft Server\n§0§k_ad:" + dev.leg1tka.network.ProxySessionBridge.a.getCurrentServerEntry().address)
               : Text.literal("§aAspectDLC Proxy");
            dev.leg1tka.network.ProxySessionBridge.b value6 = dev.leg1tka.network.ProxySessionBridge.x;
            ServerMetadata serverMetadata7 = new ServerMetadata(
               mutableText5,
               Optional.of(new Players(1, value6 != null ? 1 : 0, List.of())),
               Optional.of(new Version(SharedConstants.getGameVersion().getName(), SharedConstants.getProtocolVersion())),
               Optional.empty(),
               false
            );
            channelHandlerContext1.writeAndFlush(new QueryResponseS2CPacket(serverMetadata7));
         } else if (packet2 instanceof QueryPingC2SPacket queryPingC2SPacket3) {
            channelHandlerContext1.writeAndFlush(new PingResultS2CPacket(queryPingC2SPacket3.getStartTime())).addListener(ChannelFutureListener.CLOSE);
         }
      }

      private void d(ChannelHandlerContext channelHandlerContext1, Packet<?> packet2) {
         if (packet2 instanceof LoginHelloC2SPacket loginHelloC2SPacket3) {
            dev.leg1tka.network.ProxySessionBridge.b value4 = dev.leg1tka.network.ProxySessionBridge.x;
            if (value4 != null && value4 != this && value4.l != 9) {
               this.a(
                  channelHandlerContext1,
                  "§cУже подключён другой клиент"
               );
               return;
            }

            ClientPlayNetworkHandler clientPlayNetworkHandler5 = dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler();
            if (clientPlayNetworkHandler5 == null || dev.leg1tka.network.ProxySessionBridge.a.world == null || dev.leg1tka.network.ProxySessionBridge.a.player == null || dev.leg1tka.network.ProxySessionBridge.v == null) {
               this.a(channelHandlerContext1, "§cAspect не на сервере");
               return;
            }

            GameProfile gameProfile6 = clientPlayNetworkHandler5.getProfile();
            this.a = loginHelloC2SPacket3.name();
            dev.leg1tka.network.ProxySessionBridge.x = this;
            channelHandlerContext1.writeAndFlush(new LoginSuccessS2CPacket(gameProfile6));
         } else if (packet2 instanceof EnterConfigurationC2SPacket) {
            if (dev.leg1tka.network.ProxySessionBridge.x != this) {
               channelHandlerContext1.close();
               return;
            }

            this.a(ConfigurationStates.C2S);
            this.b(ConfigurationStates.S2C);
            this.l = 3;
            this.a(channelHandlerContext1);
         }
      }

      private void a(ChannelHandlerContext channelHandlerContext1, String string2) {
         channelHandlerContext1.writeAndFlush(new LoginDisconnectS2CPacket(Text.literal(string2))).addListener(ChannelFutureListener.CLOSE);
      }

      private void a(ChannelHandlerContext channelHandlerContext1) {
         FeaturesS2CPacket featuresS2CPacket2 = dev.leg1tka.network.PacketRegistry.a;
         if (featuresS2CPacket2 != null) {
            channelHandlerContext1.write(featuresS2CPacket2);
         } else if (dev.leg1tka.network.ProxySessionBridge.a.world != null) {
            channelHandlerContext1.write(new FeaturesS2CPacket(FeatureFlags.FEATURE_MANAGER.toId(dev.leg1tka.network.ProxySessionBridge.a.world.getEnabledFeatures())));
         } else {
            channelHandlerContext1.write(new FeaturesS2CPacket(FeatureFlags.FEATURE_MANAGER.toId(FeatureFlags.VANILLA_FEATURES)));
         }

         ArrayList<Packet<?>> arrayList3 = new ArrayList<>(dev.leg1tka.network.PacketRegistry.c);
         if (arrayList3.isEmpty() && dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler() != null) {
            try {
               Immutable immutable10 = dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler().getRegistryManager();
               SerializableRegistries.forEachSyncedRegistry(
                  immutable10.getOps(NbtOps.INSTANCE), immutable10, Collections.emptySet(), (registryKey1x, list2x) -> channelHandlerContext1.write(new DynamicRegistriesS2CPacket(registryKey1x, list2x))
               );
            } catch (Exception exception9) {
               dev.leg1tka.network.ProxySessionBridge.a("registries", exception9);
            }
         } else {
            for (Packet<?> packet5 : arrayList3) {
               channelHandlerContext1.write(packet5);
            }
         }

         SynchronizeTagsS2CPacket synchronizeTagsS2CPacket11 = dev.leg1tka.network.PacketRegistry.b;
         if (synchronizeTagsS2CPacket11 != null) {
            channelHandlerContext1.write(synchronizeTagsS2CPacket11);
         } else {
            HashMap hashMap12 = new HashMap();
            if (dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler() != null) {
               dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler().getRegistryManager().streamAllRegistries().forEach(entry1x -> {
                  Serialized serialized2x = TagPacketSerializer.serializeTags(entry1x.value());
                  if (!serialized2x.isEmpty()) {
                     hashMap12.put(entry1x.key(), serialized2x);
                  }
               });
            }

            for (Registry registry7 : Registries.REGISTRIES) {
               Serialized serialized8 = TagPacketSerializer.serializeTags(registry7);
               if (!serialized8.isEmpty()) {
                  hashMap12.put(registry7.getKey(), serialized8);
               }
            }

            channelHandlerContext1.write(new SynchronizeTagsS2CPacket(hashMap12));
         }

         if (ProxySwapper.c.p() && dev.leg1tka.network.ProxySessionBridge.a.getCurrentServerEntry() != null) {
            channelHandlerContext1.write(
               new ServerLinksS2CPacket(List.of(new StringifiedEntry(Either.left(Known.BUG_REPORT), "http://StaffStorage.local/" + dev.leg1tka.network.ProxySessionBridge.a.getCurrentServerEntry().address)))
            );
         }

         channelHandlerContext1.writeAndFlush(ReadyS2CPacket.INSTANCE);
      }

      private void c() {
         ClientPlayNetworkHandler clientPlayNetworkHandler1 = dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler();
         Channel channel2 = dev.leg1tka.network.ProxySessionBridge.w;
         if (clientPlayNetworkHandler1 != null && channel2 != null && dev.leg1tka.network.ProxySessionBridge.a.player != null) {
            Immutable immutable3 = clientPlayNetworkHandler1.getRegistryManager();
            NetworkState networkState4 = dev.leg1tka.network.ProxySessionBridge.a(immutable3);
            this.a(PlayStateFactories.C2S.bind(RegistryByteBuf.makeFactory(immutable3)));
            this.b(networkState4);
            synchronized (this) {
               this.n = networkState4;
               this.l = 4;
            }

            channel2.eventLoop().execute(() -> {
               long longValue1x = dev.leg1tka.network.ProxySessionBridge.y;
               dev.leg1tka.network.ProxySessionBridge.a.execute(() -> this.a(longValue1x));
            });
         } else {
            this.a(Text.literal("Aspect не на сервере"));
         }
      }

      private void a(long longValue1) {
         if (this.l == 4) {
            ClientPlayerEntity clientPlayerEntity3 = dev.leg1tka.network.ProxySessionBridge.a.player;
            if (clientPlayerEntity3 != null && dev.leg1tka.network.ProxySessionBridge.a.world != null && dev.leg1tka.network.ProxySessionBridge.a.getNetworkHandler() != null && dev.leg1tka.network.ProxySessionBridge.a.interactionManager != null) {
               if (!dev.leg1tka.network.ProxySessionBridge.E) {
                  dev.leg1tka.network.ProxySessionBridge.h.a(clientPlayerEntity3, dev.leg1tka.network.ProxySessionBridge.a.interactionManager.lastSelectedSlot);
               }

               List<ByteBuf> list4;
               try {
                  list4 = dev.leg1tka.network.bots.BotConnectionState.a(this.n, dev.leg1tka.network.ProxySessionBridge.t(), dev.leg1tka.network.ProxySessionBridge.h.k, clientPlayerEntity3.getYaw(), clientPlayerEntity3.getPitch());
               } catch (Throwable throwable12) {
                  dev.leg1tka.network.ProxySessionBridge.a("snapshot", throwable12);
                  this.a(Text.literal("Ошибка снимка мира"));
                  return;
               }

               Channel channel5 = this.m;
               synchronized (this) {
                  if (this.l != 4 || channel5 == null) {
                     for (ByteBuf byteBuf17 : list4) {
                        byteBuf17.release();
                     }

                     return;
                  }

                  for (ByteBuf byteBuf8 : list4) {
                     channel5.write(byteBuf8);
                  }

                  for (dev.leg1tka.network.ProxySessionBridge.a value16 : this.o) {
                     if (value16.a > longValue1) {
                        for (ByteBuf byteBuf10 : value16.b) {
                           channel5.write(byteBuf10);
                        }
                     } else {
                        value16.a();
                     }
                  }

                  this.o.clear();
                  this.l = 5;
                  channel5.flush();
               }

               dev.leg1tka.network.ProxySessionBridge.a(
                  "§aЧистый клиент подключился ["
                     + this.a
                     + "] — наблюдатель. §7.proxy swap — передать управление"
               );
            } else {
               this.a(Text.literal("Aspect не на сервере"));
            }
         }
      }

      synchronized void a(Packet<?> packet1, PacketListener packetListener2, long longValue3) {
         int intValue5 = this.l;
         Channel channel6 = this.m;
         if (channel6 != null && intValue5 >= 4 && intValue5 != 9) {
            boolean flag7 = packetListener2 instanceof ClientPlayNetworkHandler;
            if (intValue5 != 4 && intValue5 != 5) {
               if (flag7) {
                  this.s = ((ClientPlayNetworkHandler)packetListener2).getRegistryManager();
                  dev.leg1tka.network.ProxySessionBridge.a value14 = this.a(packet1, dev.leg1tka.network.ProxySessionBridge.a(this.s), longValue3);
                  if (!value14.b.isEmpty()) {
                     this.o.add(value14);
                  }

                  if (this.r && this.l == 8) {
                     this.e();
                  }
               } else if (!(packet1 instanceof KeepAliveS2CPacket) && !(packet1 instanceof CommonPingS2CPacket)) {
                  if (packet1 instanceof CustomPayloadS2CPacket customPayloadS2CPacket12 && !"minecraft".equals(customPayloadS2CPacket12.payload().getId().id().getNamespace())) {
                     return;
                  }

                  if (intValue5 == 6) {
                     this.p.add(packet1);
                     if (packet1 instanceof ReadyS2CPacket) {
                        this.q = true;
                     }
                  } else if (intValue5 == 7) {
                     ByteBuf byteBuf13 = dev.leg1tka.network.ProxySessionBridge.a(ConfigurationStates.S2C, packet1);
                     if (byteBuf13 != null) {
                        channel6.writeAndFlush(byteBuf13);
                     }

                     if (packet1 instanceof ReadyS2CPacket) {
                        this.l = 8;
                     }
                  }
               }
            } else if (flag7) {
               if (!(packet1 instanceof EnterReconfigurationS2CPacket)) {
                  dev.leg1tka.network.ProxySessionBridge.a value11 = this.a(packet1, this.n, longValue3);
                  if (!value11.b.isEmpty()) {
                     if (intValue5 == 4) {
                        this.o.add(value11);
                     } else {
                        for (ByteBuf byteBuf10 : value11.b) {
                           channel6.write(byteBuf10);
                        }

                        channel6.flush();
                     }
                  }
               } else if (intValue5 == 4) {
                  this.a(
                     Text.literal(
                        "Сервер перенастраивает соединение, зайди заново"
                     )
                  );
               } else {
                  ByteBuf byteBuf8 = dev.leg1tka.network.ProxySessionBridge.a(this.n, packet1);
                  if (byteBuf8 != null) {
                     channel6.writeAndFlush(byteBuf8);
                  }

                  channel6.eventLoop().execute(() -> this.b(ConfigurationStates.S2C));
                  this.l = 6;
                  this.p.clear();
                  this.q = false;
                  this.r = false;
                  this.s = null;
               }
            }
         }
      }

      private dev.leg1tka.network.ProxySessionBridge.a a(Packet<?> packet1, NetworkState<?> networkState2, long longValue3) {
         dev.leg1tka.network.ProxySessionBridge.a value5 = new dev.leg1tka.network.ProxySessionBridge.a(longValue3);
         networkState2.bundleHandler().forEachPacket(packet1, packet2x -> dev.leg1tka.network.ProxySessionBridge.a(packet2x, networkState2, value5));
         return value5;
      }

      private void d() {
         this.a(ConfigurationStates.C2S);
         synchronized (this) {
            Channel channel2 = this.m;

            for (Packet<?> packet4 : this.p) {
               ByteBuf byteBuf5 = dev.leg1tka.network.ProxySessionBridge.a(ConfigurationStates.S2C, packet4);
               if (byteBuf5 != null) {
                  channel2.write(byteBuf5);
               }
            }

            this.p.clear();
            channel2.flush();
            this.l = this.q ? 8 : 7;
            if (this.r && this.s != null && this.l == 8) {
               this.e();
            }
         }
      }

      private void e() {
         NetworkState networkState1 = dev.leg1tka.network.ProxySessionBridge.a(this.s);
         this.n = networkState1;
         this.a(PlayStateFactories.C2S.bind(RegistryByteBuf.makeFactory(this.s)));
         this.b(networkState1);
         Channel channel2 = this.m;

         for (dev.leg1tka.network.ProxySessionBridge.a value4 : this.o) {
            for (ByteBuf byteBuf6 : value4.b) {
               channel2.write(byteBuf6);
            }
         }

         this.o.clear();
         this.l = 5;
         channel2.flush();
      }

      void a(List<Packet<?>> list1) {
         Channel channel2 = this.m;
         if (channel2 != null) {
            synchronized (this) {
               if (this.l == 5 && this.n != null) {
                  for (Packet packet5 : list1) {
                     ByteBuf byteBuf6 = dev.leg1tka.network.ProxySessionBridge.a(this.n, packet5);
                     if (byteBuf6 != null) {
                        channel2.write(byteBuf6);
                     }
                  }

                  channel2.flush();
               }
            }
         }
      }

      void b() {
         Channel channel1 = this.m;
         int intValue2 = this.l;
         if (channel1 != null && intValue2 >= 3 && intValue2 != 9 && intValue2 != 6) {
            channel1.writeAndFlush(new KeepAliveS2CPacket(System.currentTimeMillis()));
         }
      }

      void a(Text text1) {
         Channel channel2 = this.m;
         if (channel2 != null) {
            channel2.eventLoop().execute(() -> {
               int intValue3 = this.l;
               if (intValue3 == 2) {
                  channel2.writeAndFlush(new LoginDisconnectS2CPacket(text1)).addListener(ChannelFutureListener.CLOSE);
               } else if (intValue3 >= 3 && intValue3 != 9) {
                  channel2.writeAndFlush(new DisconnectS2CPacket(text1)).addListener(ChannelFutureListener.CLOSE);
               } else {
                  channel2.close();
               }
            });
         }
      }
   }
}
