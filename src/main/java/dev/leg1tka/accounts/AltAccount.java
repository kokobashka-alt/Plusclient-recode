package dev.leg1tka.accounts;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Generated;

public class AltAccount {
   private String name;
   private String uuid;
   private String token;
   private String type;
   private long date;
   private boolean pin;
   private long last;
   private List bans;
   private String srv;
   private int joins;

   public List<dev.leg1tka.accounts.AccountBan> a() {
      if (this.bans == null) {
         this.bans = new ArrayList();
      }

      return this.bans;
   }

   public boolean b() {
      return this.bans != null && !this.bans.isEmpty();
   }

   public AltAccount(String string1, String string2) {
      this.name = string1;
      this.type = string2;
      this.uuid = UUID.nameUUIDFromBytes(("OfflinePlayer:" + string1).getBytes(StandardCharsets.UTF_8)).toString();
      this.token = "0";
      this.date = System.currentTimeMillis();
   }

   public AltAccount(String string1, String string2, String string3, String string4) {
      this.name = string1;
      this.uuid = string2;
      this.token = string3;
      this.type = string4;
      this.date = System.currentTimeMillis();
   }

   @Generated
   public String c() {
      String string10000 = this.name;
      if (this.name == null) {
         string10000 = "";
      }

      return string10000;
   }

   @Generated
   public String d() {
      return this.uuid;
   }

   @Generated
   public String e() {
      return this.token;
   }

   @Generated
   public String f() {
      return this.type;
   }

   @Generated
   public long g() {
      return this.date;
   }

   @Generated
   public boolean h() {
      return this.pin;
   }

   @Generated
   public long i() {
      return this.last;
   }

   @Generated
   public List<dev.leg1tka.accounts.AccountBan> j() {
      return this.bans;
   }

   @Generated
   public String k() {
      return this.srv;
   }

   @Generated
   public int l() {
      return this.joins;
   }

   @Generated
   public void a(String string1) {
      this.name = string1;
   }

   @Generated
   public void b(String string1) {
      this.uuid = string1;
   }

   @Generated
   public void c(String string1) {
      this.token = string1;
   }

   @Generated
   public void d(String string1) {
      this.type = string1;
   }

   @Generated
   public void a(long longValue1) {
      this.date = longValue1;
   }

   @Generated
   public void a(boolean flag1) {
      this.pin = flag1;
   }

   @Generated
   public void b(long longValue1) {
      this.last = longValue1;
   }

   @Generated
   public void a(List<dev.leg1tka.accounts.AccountBan> list1) {
      this.bans = list1;
   }

   @Generated
   public void e(String string1) {
      this.srv = string1;
   }

   @Generated
   public void a(int intValue1) {
      this.joins = intValue1;
   }
}
