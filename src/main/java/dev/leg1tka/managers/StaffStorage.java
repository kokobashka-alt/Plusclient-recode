package dev.leg1tka.managers;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class StaffStorage {
   private final List a = new ArrayList();

   public StaffStorage() {
   }

   public void a(String string1) {
      if (!string1.isEmpty()) {
         this.a.add(string1);
      }
   }

   public void b(String string1) {
      this.a.remove(string1);
   }

   public void a() {
      this.a.clear();
   }

   public boolean c(String string1) {
      return this.a.contains(string1);
   }

   public boolean b() {
      return this.a.isEmpty();
   }

   @Generated
   public List<String> c() {
      return this.a;
   }
}
