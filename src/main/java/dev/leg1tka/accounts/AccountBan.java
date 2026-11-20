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

import lombok.Generated;

public class AccountBan {
   private String server;
   private long at;
   private long until;
   private String reason;

   public AccountBan(String string1, long longValue2, String string4) {
      this.server = string1;
      this.at = System.currentTimeMillis();
      this.until = longValue2;
      this.reason = string4;
   }

   public boolean a() {
      return this.until <= 0L;
   }

   public boolean b() {
      return this.until > 0L && System.currentTimeMillis() >= this.until;
   }

   public long c() {
      return this.until <= 0L ? Long.MAX_VALUE : Math.max(0L, this.until - System.currentTimeMillis());
   }

   public float d() {
      return this.until > 0L && this.until > this.at
         ? Math.max(0.0F, Math.min(1.0F, (float)(System.currentTimeMillis() - this.at) / (float)(this.until - this.at)))
         : 0.0F;
   }

   @Generated
   public String e() {
      return this.server;
   }

   @Generated
   public long f() {
      return this.at;
   }

   @Generated
   public long g() {
      return this.until;
   }

   @Generated
   public String h() {
      return this.reason;
   }

   @Generated
   public void a(String string1) {
      this.server = string1;
   }

   @Generated
   public void a(long longValue1) {
      this.at = longValue1;
   }

   @Generated
   public void b(long longValue1) {
      this.until = longValue1;
   }

   @Generated
   public void b(String string1) {
      this.reason = string1;
   }
}
