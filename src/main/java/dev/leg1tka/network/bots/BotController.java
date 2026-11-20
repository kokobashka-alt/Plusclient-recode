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

import com.mojang.logging.LogUtils;
import dev.leg1tka.mixin.BossBarHudAccessor;
import dev.leg1tka.mixin.ChatHudAccessor;
import dev.leg1tka.mixin.ClientConnectionAccessor;
import dev.leg1tka.mixin.ClientPlayerInputAccessor;
import dev.leg1tka.mixin.DisconnectedScreenAccessor;
import dev.leg1tka.mixin.GameOptionsAccessor;
import dev.leg1tka.mixin.IMinecraftClientAccessor;
import dev.leg1tka.mixin.PlayerListHudAccessor;
import dev.leg1tka.mixin.ThreadExecutorInvoker;
import io.netty.channel.Channel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.listener.TickablePacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CommonPongC2SPacket;
import net.minecraft.network.packet.c2s.common.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.c2s.play.ClientTickEndC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInputC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerLoadedC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.Full;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket.PositionAndOnGround;
import net.minecraft.network.packet.s2c.common.CommonPingS2CPacket;
import net.minecraft.network.packet.s2c.common.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.common.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackRemoveS2CPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.common.ServerTransferS2CPacket;
import net.minecraft.network.packet.s2c.config.ResetChatS2CPacket;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.network.packet.s2c.play.BundleS2CPacket;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ClearTitleS2CPacket;
import net.minecraft.network.packet.s2c.play.DeathMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.EnterReconfigurationS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.ProfilelessChatMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import org.slf4j.Logger;

public final class BotController {
   private static final Logger a = LogUtils.getLogger();
   private static int b;
   private static int c;
   private static int d;
   private static int e;
   private static long f;
   private static final Pattern g = Pattern.compile("(?i)[§&]#[0-9a-f]{6}|[§&][0-9a-fk-orx]");
   private static final Pattern h = Pattern.compile("(?<![\\p{L}\\p{N}_])/(login|l|register|reg)(?![\\p{L}\\p{N}_])");
   private static final Pattern i = Pattern.compile("\\d+");
   private static final List<dev.leg1tka.network.bots.BotSession> j = new CopyOnWriteArrayList<>();
   private static final Map k = new ConcurrentHashMap();
   private static final Set l = ConcurrentHashMap.newKeySet();
   private static final Map m = new ConcurrentHashMap();
   private static final ConcurrentLinkedQueue n = new ConcurrentLinkedQueue();
   private static volatile dev.leg1tka.network.bots.BotController.c o;
   private static volatile ClientConnection p;
   private static volatile dev.leg1tka.network.bots.BotTask q;
   private static dev.leg1tka.network.bots.BotSession r;
   private static boolean s;
   private static boolean t;
   private static String u;
   private static int v;
   private static ClientPlayerEntity w;
   private static ClientWorld x;
   private static ClientPlayerInteractionManager y;
   private static Entity z;
   private static Session A;
   private static int B;

   private static MinecraftClient k() {
      return MinecraftClient.getInstance();
   }

   private BotController() {
   }

   public static void a(String string0, String string1) {
      a((Runnable)(() -> c(string0, string1)));
   }

   public static boolean a(String string0) {
      if (d(string0) == null) {
         return false;
      } else {
         a((Runnable)(() -> f(string0)));
         return true;
      }
   }

   public static boolean a() {
      if (j.isEmpty()) {
         return false;
      } else {
         dev.leg1tka.network.bots.BotSession value0 = u != null ? d(u) : null;
         if (value0 == null) {
            value0 = (dev.leg1tka.network.bots.BotSession)j.get(j.size() - 1);
         }

         return a(value0.a);
      }
   }

   public static boolean b(String string0) {
      dev.leg1tka.network.bots.BotSession value1 = d(string0);
      if (value1 == null) {
         return false;
      } else {
         a((Runnable)(() -> a(value1, Text.literal("Disconnected"), false)));
         return true;
      }
   }

   public static int b() {
      int intValue0 = j.size();

      for (dev.leg1tka.network.bots.BotSession value2 : j) {
         b(value2.a);
      }

      return intValue0;
   }

   public static boolean b(String string0, String string1) {
      dev.leg1tka.network.bots.BotSession value2 = d(string0);
      if (value2 != null && value2.g() && value2.f() && !s) {
         c(value2);

         try {
            if (string1.startsWith("/")) {
               value2.g.sendChatCommand(string1.substring(1));
            } else {
               value2.g.sendChatMessage(string1);
            }
         } catch (Throwable throwable7) {
            a.warn("[Bot] {} say failed", value2.a, throwable7);
         } finally {
            d(value2);
         }

         return true;
      } else {
         return false;
      }
   }

   public static int c(String string0) {
      int intValue1 = 0;

      for (dev.leg1tka.network.bots.BotSession value3 : j) {
         if (b(value3.a, string0)) {
            intValue1++;
         }
      }

      return intValue1;
   }

   public static String c() {
      return k().getSession() == null ? "" : k().getSession().getUsername();
   }

   public static List<String> a(boolean flag0) {
      ArrayList arrayList1 = new ArrayList();
      if (flag0 && k().player != null) {
         arrayList1.add(c());
      }

      for (dev.leg1tka.network.bots.BotSession value3 : j) {
         arrayList1.add(value3.a);
      }

      return arrayList1;
   }

   public static List<dev.leg1tka.network.bots.BotSession> d() {
      return new ArrayList<>(j);
   }

   public static boolean e() {
      return o != null;
   }

   public static boolean f() {
      return s;
   }

   public static boolean g() {
      return s && k().isOnThread();
   }

   public static void a(dev.leg1tka.network.bots.BotTask value0) {
      q = value0;
   }

   public static dev.leg1tka.network.bots.BotSession d(String string0) {
      if (string0 == null) {
         return null;
      } else {
         for (dev.leg1tka.network.bots.BotSession value2 : j) {
            if (value2.a.equalsIgnoreCase(string0)) {
               return value2;
            }
         }

         return null;
      }
   }

   public static boolean h() {
      dev.leg1tka.network.bots.BotSession value0 = r;
      return value0 != null && value0.f.b();
   }

   public static boolean a(ClientConnection clientConnection0) {
      return k.containsKey(clientConnection0);
   }

   public static String a(ClientWorld clientWorld0, ClientPlayerEntity clientPlayerEntity1) {
      if (clientWorld0 == null) {
         return null;
      } else {
         Scoreboard scoreboard2 = clientWorld0.getScoreboard();
         ScoreboardObjective scoreboardObjective3 = null;
         if (clientPlayerEntity1 != null) {
            Team team4 = scoreboard2.getScoreHolderTeam(clientPlayerEntity1.getNameForScoreboard());
            if (team4 != null) {
               ScoreboardDisplaySlot scoreboardDisplaySlot5 = ScoreboardDisplaySlot.fromFormatting(team4.getColor());
               if (scoreboardDisplaySlot5 != null) {
                  scoreboardObjective3 = scoreboard2.getObjectiveForSlot(scoreboardDisplaySlot5);
               }
            }
         }

         if (scoreboardObjective3 == null) {
            scoreboardObjective3 = scoreboard2.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
         }

         if (scoreboardObjective3 == null) {
            return null;
         } else {
            Matcher matcher6 = i.matcher(e(scoreboardObjective3.getDisplayName().getString()));
            return matcher6.find() ? "an" + matcher6.group() : null;
         }
      }
   }

   private static String e(String string0) {
      return g.matcher(string0).replaceAll("");
   }

   private static void a(Runnable runnable0) {
      n.add(runnable0);
      k().send(dev.leg1tka.network.bots.BotController::l);
   }

   private static void l() {
      if (!t) {
         t = true;

         Runnable runnable0;
         try {
            while ((runnable0 = (Runnable)n.poll()) != null) {
               try {
                  runnable0.run();
               } catch (Throwable throwable5) {
                  a.error("[Bot] action failed", throwable5);
                  h("§c[Bot] Ошибка: " + throwable5);
               }
            }
         } finally {
            t = false;
         }
      }
   }

   public static boolean a(ClientConnection clientConnection0, PacketListener packetListener1, Packet<?> packet2) {
      if (o != null && p == null && packetListener1 instanceof ClientLoginNetworkHandler) {
         p = clientConnection0;
      }

      dev.leg1tka.network.bots.BotSession value3 = (dev.leg1tka.network.bots.BotSession)k.get(clientConnection0);
      if (value3 == null) {
         if (packet2 instanceof ResourcePackSendS2CPacket resourcePackSendS2CPacket4 && (clientConnection0 == p || l.contains(clientConnection0))) {
            a(clientConnection0, resourcePackSendS2CPacket4);
            return true;
         }

         return false;
      } else {
         synchronized (value3) {
            if (!value3.o) {
               return false;
            } else {
               if (packet2 instanceof BundleS2CPacket bundleS2CPacket5) {
                  for (Packet packet7 : bundleS2CPacket5.getPackets()) {
                     a(value3, packet7);
                  }
               } else {
                  a(value3, packet2);
               }

               return true;
            }
         }
      }
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0, Packet<?> packet1) {
      if (packet1 instanceof KeepAliveS2CPacket keepAliveS2CPacket2) {
         value0.c.send(new KeepAliveC2SPacket(keepAliveS2CPacket2.getId()));
      } else if (packet1 instanceof CommonPingS2CPacket commonPingS2CPacket3) {
         value0.c.send(new CommonPongC2SPacket(commonPingS2CPacket3.getParameter()));
      } else if (packet1 instanceof ResourcePackSendS2CPacket resourcePackSendS2CPacket4) {
         a(value0.c, resourcePackSendS2CPacket4);
      } else if (packet1 instanceof DisconnectS2CPacket disconnectS2CPacket5) {
         value0.q = disconnectS2CPacket5.reason();
         value0.p = true;
      } else if (packet1 instanceof ServerTransferS2CPacket) {
         value0.q = Text.literal(
            "сервер перенаправил на другой адрес"
         );
         value0.p = true;
      } else if (!(packet1 instanceof PlaySoundS2CPacket)
         && !(packet1 instanceof PlaySoundFromEntityS2CPacket)
         && !(packet1 instanceof StopSoundS2CPacket)
         && !(packet1 instanceof ParticleS2CPacket)
         && !(packet1 instanceof WorldEventS2CPacket)
         && !(packet1 instanceof TitleS2CPacket)
         && !(packet1 instanceof SubtitleS2CPacket)
         && !(packet1 instanceof TitleFadeS2CPacket)
         && !(packet1 instanceof ClearTitleS2CPacket)
         && !(packet1 instanceof OverlayMessageS2CPacket)
         && !(packet1 instanceof ResourcePackRemoveS2CPacket)
         && !(packet1 instanceof ResetChatS2CPacket)) {
         value0.m.addLast(packet1);
      }
   }

   private static void a(ClientConnection clientConnection0, ResourcePackSendS2CPacket resourcePackSendS2CPacket1) {
      UUID uUID2 = resourcePackSendS2CPacket1.id();
      clientConnection0.send(new ResourcePackStatusC2SPacket(uUID2, Status.ACCEPTED));
      clientConnection0.send(new ResourcePackStatusC2SPacket(uUID2, Status.DOWNLOADED));
      clientConnection0.send(new ResourcePackStatusC2SPacket(uUID2, Status.SUCCESSFULLY_LOADED));
   }

   public static void a(ClientPlayNetworkHandler clientPlayNetworkHandler0) {
      if (k().isOnThread()) {
         ClientConnection clientConnection1 = clientPlayNetworkHandler0.getConnection();
         dev.leg1tka.network.bots.BotController.c value2 = o;
         if (!s && value2 != null) {
            l.add(clientConnection1);
            o = null;
            p = null;
            k().send(() -> h("§7[Bot] §fПодключён: §a" + value2.name + " §7(" + value2.address + ")"));
         }

         if (l.contains(clientConnection1)) {
            m.put(clientConnection1, new dev.leg1tka.network.bots.BotController.b(System.currentTimeMillis() + 90000L));
         }
      }
   }

   public static void a(ClientPlayNetworkHandler clientPlayNetworkHandler0, GameMessageS2CPacket gameMessageS2CPacket1) {
      if (!gameMessageS2CPacket1.overlay() && !m.isEmpty()) {
         ClientConnection clientConnection2 = clientPlayNetworkHandler0.getConnection();
         dev.leg1tka.network.bots.BotController.b value3 = (dev.leg1tka.network.bots.BotController.b)m.get(clientConnection2);
         if (value3 != null) {
            if (System.currentTimeMillis() > value3.a) {
               m.remove(clientConnection2);
            } else {
               BotManager botManager4 = BotManager.c;
               String string5 = botManager4.r();
               if (botManager4.isEnabled() && string5 != null) {
                  String string6 = e(gameMessageS2CPacket1.content().getString()).toLowerCase(Locale.ROOT);
                  if (!a(clientPlayNetworkHandler0, string6)) {
                     boolean flag7 = false;
                     boolean flag8 = false;
                     Matcher matcher9 = h.matcher(string6);

                     while (matcher9.find()) {
                        String string10 = matcher9.group(1);
                        if (!string10.equals("l") && !string10.equals("login")) {
                           flag8 = true;
                        } else {
                           flag7 = true;
                        }
                     }

                     try {
                        if (flag7) {
                           if (!value3.b && botManager4.f.getValue()) {
                              value3.b = true;
                              clientPlayNetworkHandler0.sendChatCommand("login " + string5);
                           }
                        } else if (flag8 && !value3.c && botManager4.g.getValue()) {
                           value3.c = true;
                           clientPlayNetworkHandler0.sendChatCommand("register " + string5 + " " + string5);
                        }
                     } catch (Throwable throwable11) {
                        a.warn("[Bot] auto auth failed", throwable11);
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean a(ClientPlayNetworkHandler clientPlayNetworkHandler0, String string1) {
      String string2 = clientPlayNetworkHandler0.getProfile() != null ? clientPlayNetworkHandler0.getProfile().getName() : "";

      for (PlayerListEntry playerListEntry4 : clientPlayNetworkHandler0.getPlayerList()) {
         String string5 = playerListEntry4.getProfile().getName();
         if (string5 != null && string5.length() >= 3 && !string5.equalsIgnoreCase(string2) && string1.contains(string5.toLowerCase(Locale.ROOT))) {
            return true;
         }
      }

      return false;
   }

   public static boolean a(Screen screen0) {
      if (s) {
         return true;
      } else {
         dev.leg1tka.network.bots.BotController.c value1 = o;
         if (value1 != null && (screen0 == value1.parent || screen0 instanceof DisconnectedScreen)) {
            Object object2 = Text.literal("отменено");
            if (screen0 instanceof DisconnectedScreen disconnectedScreen3) {
               try {
                  object2 = ((DisconnectedScreenAccessor)disconnectedScreen3).aspect$getInfo().reason();
               } catch (Throwable throwable5) {
               }
            }

            o = null;
            p = null;
            dev.leg1tka.network.bots.BotController.a value6 = new dev.leg1tka.network.bots.BotController.a(value1, (Text)object2);
            a((Runnable)(() -> a(value6)));
         }

         return false;
      }
   }

   public static void i() {
      for (dev.leg1tka.network.bots.BotSession value1 : j) {
         if (!value1.g()) {
            Object object2 = value1.q != null
               ? value1.q
               : Text.literal("соединение закрыто");
            a(value1, (Text)object2, true);
         } else {
            b(value1);
         }
      }

      if (!m.isEmpty() && ++v >= 100) {
         v = 0;
         long longValue4 = System.currentTimeMillis();
         Iterator iterator5 = m.entrySet().iterator();

         while (iterator5.hasNext()) {
            Entry entry3 = (Entry)iterator5.next();
            if (longValue4 > ((dev.leg1tka.network.bots.BotController.b)entry3.getValue()).a || !((ClientConnection)entry3.getKey()).isOpen()) {
               iterator5.remove();
            }
         }
      }
   }

   public static void j() {
      for (dev.leg1tka.network.bots.BotSession value1 : j) {
         try {
            value1.c.disconnect(Text.literal("Disconnected"));
         } catch (Throwable throwable3) {
         }
      }

      j.clear();
      k.clear();
      u = null;
   }

   private static void c(String string0, String string1) {
      if (string0 != null && !string0.isBlank() && string1 != null && !string1.isBlank()) {
         if (o != null) {
            h("§c[Bot] Уже идёт подключение " + o.name);
         } else if (!ServerAddress.isValid(string1)) {
            h("§c[Bot] Неверный адрес: " + string1);
         } else if (k().player != null && string0.equalsIgnoreCase(c())) {
            h("§c[Bot] Ты уже играешь ником " + string0);
         } else {
            dev.leg1tka.network.bots.BotSession value2 = d(string0);
            if (value2 != null) {
               a(value2, Text.literal("Replaced"), false);
            }

            Session session3 = k().getSession();
            dev.leg1tka.network.bots.BotSession value4 = k().player != null ? m() : null;
            MultiplayerScreen multiplayerScreen5 = new MultiplayerScreen(new TitleScreen());
            o = new dev.leg1tka.network.bots.BotController.c(string0, string1, value4, session3, multiplayerScreen5);
            p = null;
            ((IMinecraftClientAccessor)k())
               .setSession(new Session(string0, Uuids.getOfflinePlayerUuid(string0), "", Optional.empty(), Optional.empty(), AccountType.LEGACY));

            try {
               ConnectScreen.connect(multiplayerScreen5, k(), ServerAddress.parse(string1), new ServerInfo(string1, string1, ServerType.OTHER), false, null);
            } catch (Throwable throwable7) {
               a.error("[Bot] connect failed", throwable7);
               o = null;
               a(new dev.leg1tka.network.bots.BotController.a(new dev.leg1tka.network.bots.BotController.c(string0, string1, value4, session3, multiplayerScreen5), Text.literal(String.valueOf(throwable7.getMessage()))));
            }
         }
      }
   }

   private static void a(dev.leg1tka.network.bots.BotController.a value0) {
      dev.leg1tka.network.bots.BotController.c value1 = value0.pending;
      ((IMinecraftClientAccessor)k()).setSession(value1.previousSession);
      String string2 = value0.reason == null ? "" : value0.reason.getString();
      if (value1.previous != null && value1.previous.g() && value1.previous.f()) {
         a(value1.previous);
         h(
            "§c[Bot] Не удалось подключить §f"
               + value1.name
               + "§c: §7"
               + string2
         );
      } else {
         a.warn("[Bot] connect {} failed: {}", value1.name, string2);
      }
   }

   private static void f(String string0) {
      dev.leg1tka.network.bots.BotSession value1 = d(string0);
      if (value1 == null) {
         h("§c[Bot] Сессия не найдена: " + string0);
      } else if (!value1.g()) {
         a(value1, Text.literal("соединение закрыто"), true);
      } else if (!value1.f()) {
         h(
            "§e[Bot] §f"
               + string0
               + " §eещё заходит на сервер — попробуй через пару секунд"
         );
      } else {
         dev.leg1tka.network.bots.BotSession value2 = k().player != null ? m() : null;

         try {
            a(value1);
            h("§7[Bot] §fУправление: §a" + value1.a);
         } catch (Throwable throwable6) {
            a.error("[Bot] switch to {} failed", string0, throwable6);
            if (value2 != null && value2.g() && value2.f()) {
               try {
                  a(value2);
               } catch (Throwable throwable5) {
                  a.error("[Bot] rollback failed", throwable5);
               }
            }

            h(
               "§c[Bot] Не удалось переключиться на "
                  + string0
                  + ": "
                  + throwable6
            );
         }
      }
   }

   private static dev.leg1tka.network.bots.BotSession m() {
      ClientPlayerEntity clientPlayerEntity0 = k().player;
      ClientPlayNetworkHandler clientPlayNetworkHandler1 = clientPlayerEntity0.networkHandler;
      dev.leg1tka.api.utils.resources.BaritoneAccess.c();
      a(clientPlayerEntity0, clientPlayNetworkHandler1);
      ServerInfo serverInfo2 = clientPlayNetworkHandler1.getServerInfo();
      dev.leg1tka.network.bots.BotSession value3 = new dev.leg1tka.network.bots.BotSession(c(), serverInfo2 != null ? serverInfo2.address : "", clientPlayNetworkHandler1, k().world, clientPlayerEntity0, k().interactionManager, k().getSession());
      synchronized (value3) {
         value3.o = true;
      }

      k.put(value3.c, value3);
      j.add(value3);
      u = value3.a;

      try {
         Channel channel4 = ((ClientConnectionAccessor)value3.c).aspect$getChannel();
         if (channel4 != null && channel4.isOpen()) {
            channel4.eventLoop().submit(() -> {
            }).syncUninterruptibly();
         }
      } catch (Throwable throwable7) {
         a.warn("[Bot] channel barrier failed", throwable7);
      }

      ((ThreadExecutorInvoker)k()).aspect$runTasks();
      BossBarHud bossBarHud9 = k().inGameHud.getBossBarHud();
      a(value3.e).putAll(a(bossBarHud9));
      PlayerListHud playerListHud5 = k().inGameHud.getPlayerListHud();
      value3.k = ((PlayerListHudAccessor)playerListHud5).aspect$getHeader();
      value3.l = ((PlayerListHudAccessor)playerListHud5).aspect$getFooter();
      bossBarHud9.clear();
      k().inGameHud.clearTitle();
      playerListHud5.setHeader(null);
      playerListHud5.setFooter(null);
      ChatHud chatHud6 = k().inGameHud.getChatHud();
      k().getMessageHandler().processAll();
      value3.n.addAll(((ChatHudAccessor)chatHud6).aspect$getMessages());
      chatHud6.clear(false);
      if (dev.leg1tka.rotation.RotationStorage.c != null) {
         dev.leg1tka.rotation.RotationStorage.c.a();
      }

      dev.leg1tka.rotation.FreeLookStorage.a(false);
      k().player = null;
      k().world = null;
      k().interactionManager = null;
      k().cameraEntity = null;
      return value3;
   }

   private static void a(ClientPlayerEntity clientPlayerEntity0, ClientPlayNetworkHandler clientPlayNetworkHandler1) {
      try {
         if (clientPlayerEntity0.currentScreenHandler != clientPlayerEntity0.playerScreenHandler) {
            clientPlayerEntity0.closeHandledScreen();
         }

         if (k().interactionManager != null) {
            k().interactionManager.cancelBlockBreaking();
            if (clientPlayerEntity0.isUsingItem()) {
               k().interactionManager.stopUsingItem(clientPlayerEntity0);
            }
         }

         clientPlayNetworkHandler1.sendPacket(new PlayerInputC2SPacket(PlayerInput.DEFAULT));
         ((ClientPlayerInputAccessor)clientPlayerEntity0).aspect$setLastPlayerInput(PlayerInput.DEFAULT);
      } catch (Throwable throwable3) {
         a.warn("[Bot] release input failed", throwable3);
      }
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0) {
      ArrayList<Packet<?>> arrayList1;
      synchronized (value0) {
         value0.o = false;
         arrayList1 = new ArrayList<>(value0.m);
         value0.m.clear();
      }

      k.remove(value0.c);
      j.remove(value0);
      k().player = value0.i;
      k().world = value0.h;
      k().interactionManager = value0.j;
      k().cameraEntity = value0.i;
      ((IMinecraftClientAccessor)k()).setSession(value0.d);
      k().setWorld(value0.h);
      k().getSoundManager().stopAll();
      BossBarHud bossBarHud2 = k().inGameHud.getBossBarHud();
      bossBarHud2.clear();
      a(bossBarHud2).putAll(a(value0.e));
      k().inGameHud.clearTitle();
      PlayerListHud playerListHud3 = k().inGameHud.getPlayerListHud();
      playerListHud3.setHeader(value0.k);
      playerListHud3.setFooter(value0.l);
      ChatHud chatHud4 = k().inGameHud.getChatHud();
      chatHud4.clear(false);
      ((ChatHudAccessor)chatHud4).aspect$getMessages().addAll(value0.n);
      value0.n.clear();
      chatHud4.reset();
      value0.i.input = new KeyboardInput(k().options);
      ((ClientPlayerInputAccessor)value0.i).aspect$setLastPlayerInput(PlayerInput.DEFAULT);
      k().setScreen(null);

      for (Packet<?> packet6 : arrayList1) {
         try {
            a(packet6, value0.c.getPacketListener());
         } catch (Throwable throwable8) {
            a.warn("[Bot] {} failed to handle {}", new Object[]{value0.a, packet6.getClass().getSimpleName(), throwable8});
         }
      }

      if (dev.leg1tka.rotation.RotationStorage.c != null) {
         dev.leg1tka.rotation.RotationStorage.c.a();
      }

      dev.leg1tka.rotation.FreeLookStorage.a(false);
      dev.leg1tka.api.utils.resources.BaritoneAccess.a(k().world);
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0, Text text1, boolean flag2) {
      j.remove(value0);
      k.remove(value0.c);
      l.remove(value0.c);
      m.remove(value0.c);
      value0.p = true;
      synchronized (value0) {
         value0.o = false;
         value0.m.clear();
      }

      try {
         if (value0.c.isOpen()) {
            value0.c.disconnect(text1);
         }
      } catch (Throwable throwable6) {
      }

      dev.leg1tka.network.bots.BotTask value3 = q;
      if (value3 != null) {
         try {
            value3.b(value0);
         } catch (Throwable throwable5) {
            a.warn("[Bot] {} task close failed", value0.a, throwable5);
         }
      }

      if (flag2) {
         g("§7[Bot] §f" + value0.a + " §cотключён§7: " + text1.getString());
      }
   }

   private static void b(dev.leg1tka.network.bots.BotSession value0) {
      c(value0);

      try {
         int intValue1 = 0;

         while (intValue1++ < 4096) {
            Packet packet2;
            synchronized (value0) {
               packet2 = (Packet)value0.m.pollFirst();
            }

            if (packet2 == null) {
               break;
            }

            b(value0, packet2);
         }

         PacketListener packetListener17 = value0.c.getPacketListener();
         if (packetListener17 instanceof TickablePacketListener tickablePacketListener3) {
            try {
               tickablePacketListener3.tick();
            } catch (Throwable throwable14) {
               a.warn("[Bot] {} listener tick failed", value0.a, throwable14);
            }
         }

         if (packetListener17 != value0.g || k().player == null || k().world == null || k().interactionManager == null) {
            return;
         }

         ClientPlayerEntity clientPlayerEntity18 = k().player;
         ClientWorld clientWorld4 = k().world;
         a(value0, clientPlayerEntity18, clientWorld4);
         boolean flag5 = !b(value0, clientPlayerEntity18, clientWorld4) && a(value0, clientPlayerEntity18);
         boolean flag6 = c(value0, clientPlayerEntity18, clientWorld4);
         a(value0, clientPlayerEntity18, flag6, flag5);

         try {
            clientWorld4.runQueuedChunkUpdates();
            clientWorld4.getChunkManager().getLightingProvider().doLightUpdates();
         } catch (Throwable throwable13) {
            a.warn("[Bot] {} world update failed", value0.a, throwable13);
         }
      } finally {
         d(value0);
      }
   }

   private static void b(dev.leg1tka.network.bots.BotSession value0, Packet<?> packet1) {
      if (packet1 instanceof BossBarS2CPacket bossBarS2CPacket12) {
         value0.e.handlePacket(bossBarS2CPacket12);
      } else if (packet1 instanceof PlayerListHeaderS2CPacket playerListHeaderS2CPacket11) {
         value0.k = playerListHeaderS2CPacket11.header().getString().isEmpty() ? null : playerListHeaderS2CPacket11.header();
         value0.l = playerListHeaderS2CPacket11.footer().getString().isEmpty() ? null : playerListHeaderS2CPacket11.footer();
      } else {
         if (packet1 instanceof OpenScreenS2CPacket openScreenS2CPacket2) {
            dev.leg1tka.network.bots.BotTask value3 = q;
            if (value3 == null || !value3.a(value0)) {
               value0.c.send(new CloseHandledScreenC2SPacket(openScreenS2CPacket2.getSyncId()));
               return;
            }
         }

         if (packet1 instanceof OpenHorseScreenS2CPacket openHorseScreenS2CPacket10) {
            value0.c.send(new CloseHandledScreenC2SPacket(openHorseScreenS2CPacket10.getSyncId()));
            return;
         }

         if (!(packet1 instanceof SignEditorOpenS2CPacket) && !(packet1 instanceof OpenWrittenBookS2CPacket)) {
            if (packet1 instanceof GameMessageS2CPacket gameMessageS2CPacket9) {
               if (gameMessageS2CPacket9.overlay()) {
                  return;
               }

               a(value0.g, gameMessageS2CPacket9);
               a(value0, gameMessageS2CPacket9.content());
               return;
            }

            if (packet1 instanceof ProfilelessChatMessageS2CPacket profilelessChatMessageS2CPacket8) {
               a(value0, profilelessChatMessageS2CPacket8.chatType().applyChatDecoration(profilelessChatMessageS2CPacket8.message()));
               return;
            }

            if (packet1 instanceof ChatMessageS2CPacket chatMessageS2CPacket7) {
               Object object15 = chatMessageS2CPacket7.unsignedContent() != null ? chatMessageS2CPacket7.unsignedContent() : Text.literal(chatMessageS2CPacket7.body().content());
               a(value0, chatMessageS2CPacket7.serializedParameters().applyChatDecoration((Text)object15));
               return;
            }

            boolean flag10000;
            label87: {
               if (packet1 instanceof DeathMessageS2CPacket deathMessageS2CPacket13 && k().player != null && deathMessageS2CPacket13.playerId() == k().player.getId()) {
                  flag10000 = true;
                  break label87;
               }

               flag10000 = false;
            }

            boolean flag6 = flag10000;

            try {
               a(packet1, value0.c.getPacketListener());
            } catch (Throwable throwable5) {
               a.warn("[Bot] {} failed to handle {}", new Object[]{value0.a, packet1.getClass().getSimpleName(), throwable5});
            }

            if (packet1 instanceof EnterReconfigurationS2CPacket) {
               value0.g.clearWorld();
               k().player = null;
               k().world = null;
               k().interactionManager = null;
               k().cameraEntity = null;
               value0.e.clear();
               value0.k = null;
               value0.l = null;
               value0.u = 0;
               g(
                  "§7[Bot] §f"
                     + value0.a
                     + " §7переходит на другой сервер"
               );
            } else {
               if (value0.c.getPacketListener() instanceof ClientPlayNetworkHandler clientPlayNetworkHandler14) {
                  value0.g = clientPlayNetworkHandler14;
                  if (clientPlayNetworkHandler14.getWorld() != null) {
                     k().world = clientPlayNetworkHandler14.getWorld();
                  }
               }

               if (flag6 && k().player != null) {
                  if (BotManager.c.j.getValue() && k().player.showsDeathScreen()) {
                     k().player.requestRespawn();
                  }

                  g("§7[Bot] §f" + value0.a + " §cумер");
               }
            }
         }
      }
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0, ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      if (clientPlayerEntity1.isLoaded()) {
         value0.u = 0;
      } else {
         ChunkPos chunkPos3 = clientPlayerEntity1.getChunkPos();
         if (++value0.u >= 100 || clientWorld2.getChunkManager().isChunkLoaded(chunkPos3.x, chunkPos3.z)) {
            value0.c.send(new PlayerLoadedC2SPacket());
            clientPlayerEntity1.setLoaded(true);
            value0.u = 0;
         }
      }
   }

   private static boolean b(dev.leg1tka.network.bots.BotSession value0, ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      dev.leg1tka.network.bots.BotTask value3 = q;
      if (value3 == null) {
         return false;
      } else {
         try {
            return value3.a(value0, clientPlayerEntity1, clientWorld2);
         } catch (Throwable throwable5) {
            a.warn("[Bot] {} task tick failed", value0.a, throwable5);
            value0.f.a();
            return false;
         }
      }
   }

   private static boolean a(dev.leg1tka.network.bots.BotSession value0, ClientPlayerEntity clientPlayerEntity1) {
      value0.f.a();
      BotManager botManager2 = BotManager.c;
      if (!botManager2.k.getValue()) {
         value0.s = 0;
         value0.t = 0;
         return false;
      } else {
         boolean flag3 = false;
         if (value0.t == 0 && ++value0.s >= Math.max(20, Math.round(botManager2.m.f() * 20.0F))) {
            value0.s = 0;
            value0.t = 10;
            if (botManager2.l.a("Поворот камеры")) {
               ThreadLocalRandom threadLocalRandom4 = ThreadLocalRandom.current();
               float floatValue5 = threadLocalRandom4.nextFloat(25.0F, 60.0F) * (threadLocalRandom4.nextBoolean() ? 1.0F : -1.0F);
               clientPlayerEntity1.setYaw(clientPlayerEntity1.getYaw() + floatValue5);
               clientPlayerEntity1.setPitch(MathHelper.clamp(threadLocalRandom4.nextFloat(-20.0F, 25.0F), -90.0F, 90.0F));
               clientPlayerEntity1.setHeadYaw(clientPlayerEntity1.getYaw());
               flag3 = true;
            }
         }

         if (value0.t > 0) {
            if (value0.t == 10 && botManager2.l.a("Прыжок")) {
               value0.f.c = true;
            }

            if (botManager2.l.a("Ходьба")) {
               if (value0.t > 5) {
                  value0.f.a = true;
               } else {
                  value0.f.b = true;
               }
            }

            value0.t--;
         }

         return flag3;
      }
   }

   private static boolean c(dev.leg1tka.network.bots.BotSession value0, ClientPlayerEntity clientPlayerEntity1, ClientWorld clientWorld2) {
      if (BotManager.c.i.getValue() && !value0.v && !clientPlayerEntity1.hasVehicle() && clientPlayerEntity1.isLoaded()) {
         if (clientPlayerEntity1.input != value0.f) {
            clientPlayerEntity1.input = value0.f;
         }

         try {
            clientWorld2.tickEntity(clientPlayerEntity1);
            return true;
         } catch (Throwable throwable4) {
            value0.v = true;
            a.warn("[Bot] {} physics disabled", value0.a, throwable4);
            return false;
         }
      } else {
         return false;
      }
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0, ClientPlayerEntity clientPlayerEntity1, boolean flag2, boolean flag3) {
      if (!flag2 && !clientPlayerEntity1.hasVehicle()) {
         if (flag3) {
            value0.r = 0;
            value0.c.send(new Full(clientPlayerEntity1.getX(), clientPlayerEntity1.getY(), clientPlayerEntity1.getZ(), clientPlayerEntity1.getYaw(), clientPlayerEntity1.getPitch(), clientPlayerEntity1.isOnGround(), clientPlayerEntity1.horizontalCollision));
         } else if (++value0.r >= 20) {
            value0.r = 0;
            value0.c.send(new PositionAndOnGround(clientPlayerEntity1.getX(), clientPlayerEntity1.getY(), clientPlayerEntity1.getZ(), clientPlayerEntity1.isOnGround(), clientPlayerEntity1.horizontalCollision));
         }
      }

      value0.c.send(ClientTickEndC2SPacket.INSTANCE);
   }

   private static void c(dev.leg1tka.network.bots.BotSession value0) {
      if (s) {
         throw new IllegalStateException("nested bot context");
      } else {
         w = k().player;
         x = k().world;
         y = k().interactionManager;
         z = k().cameraEntity;
         A = k().getSession();
         B = ((GameOptionsAccessor)k().options).aspect$getServerViewDistance();
         k().player = value0.i;
         k().world = value0.h;
         k().interactionManager = value0.j;
         k().cameraEntity = value0.i;
         ((IMinecraftClientAccessor)k()).setSession(value0.d);
         r = value0;
         s = true;
      }
   }

   private static void d(dev.leg1tka.network.bots.BotSession value0) {
      value0.i = k().player;
      value0.h = k().world;
      value0.j = k().interactionManager;
      k().player = w;
      k().world = x;
      k().interactionManager = y;
      k().cameraEntity = z;
      ((IMinecraftClientAccessor)k()).setSession(A);
      ((GameOptionsAccessor)k().options).aspect$setServerViewDistance(B);
      w = null;
      x = null;
      y = null;
      z = null;
      A = null;
      r = null;
      s = false;
   }

   private static void a(Packet<?> packet0, PacketListener packetListener1) {
      ((Packet)packet0).apply(packetListener1);
   }

   private static void a(dev.leg1tka.network.bots.BotSession value0, Text text1) {
      if (text1 != null) {
         value0.n.add(0, new ChatHudLine(k().inGameHud.getTicks(), text1, null, MessageIndicator.system()));
         if (value0.n.size() > 100) {
            value0.n.remove(value0.n.size() - 1);
         }

         if (BotManager.c.q()) {
            k().inGameHud.getChatHud().addMessage(Text.literal("§8[§7" + value0.a + "§8] §r").append(text1));
         }
      }
   }

   private static Map<UUID, ClientBossBar> a(BossBarHud bossBarHud0) {
      return ((BossBarHudAccessor)bossBarHud0).aspect$getBossBars();
   }

   private static void g(String string0) {
      if (BotManager.c.e.getValue()) {
         h(string0);
      }
   }

   private static void h(String string0) {
      if (k().player != null) {
         dev.leg1tka.api.utils.text.ClientChat.a(string0);
      } else {
         a.info("{}", string0);
      }
   }

   static record a(dev.leg1tka.network.bots.BotController.c pending, Text reason)  {

}

   static class b {
      final long a;
      boolean b;
      boolean c;

      b(long longValue1) {
         this.a = longValue1;
      }
   }

   static record c(String name, String address, dev.leg1tka.network.bots.BotSession previous, Session previousSession, Screen parent)  {




}
}
