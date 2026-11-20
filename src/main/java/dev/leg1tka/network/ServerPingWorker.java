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

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.network.Address;
import net.minecraft.client.network.AllowedAddressResolver;
import net.minecraft.client.network.ServerAddress;

public class ServerPingWorker {
   private static final Map b = new ConcurrentHashMap();
   public static final ExecutorService a = Executors.newFixedThreadPool(24, runnable0 -> {
      Thread thread1 = new Thread(runnable0, "FastPing-Worker");
      thread1.setDaemon(true);
      return thread1;
   });

   public ServerPingWorker() {
   }

   public static Optional<Address> a(String string0) {
      if (string0 == null) {
         return Optional.empty();
      } else {
         Address address1 = (Address)b.get(string0.toLowerCase());
         return address1 != null ? Optional.of(address1) : Optional.empty();
      }
   }

   public static void a(String string0, Address address1) {
      if (string0 != null && address1 != null) {
         b.put(string0.toLowerCase(), address1);
      }
   }

   public static Optional<Address> a(ServerAddress serverAddress0) {
      if (serverAddress0 == null) {
         return Optional.empty();
      } else {
         String string1 = serverAddress0.getAddress() + ":" + serverAddress0.getPort();
         Address address2 = (Address)b.get(string1.toLowerCase());
         if (address2 != null) {
            return Optional.of(address2);
         } else {
            Optional optional3 = AllowedAddressResolver.DEFAULT.resolve(serverAddress0);
            optional3.ifPresent(object2x -> {
               b.put(string1.toLowerCase(), object2x);
               b.put(serverAddress0.getAddress().toLowerCase(), object2x);
            });
            return optional3;
         }
      }
   }
}
