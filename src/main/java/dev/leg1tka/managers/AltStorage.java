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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.leg1tka.AspectDLC;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;

public class AltStorage {
   private final List<dev.leg1tka.accounts.AltAccount> alts = new ArrayList<>();
   private final Gson gsn = new GsonBuilder().setPrettyPrinting().create();
   private final File fle;

   public AltStorage() {
      File file1 = AspectDLC.INSTANCE.globalsDir != null ? AspectDLC.INSTANCE.globalsDir : new File("game", "aspectdlc");
      if (!file1.exists()) {
         file1.mkdirs();
      }

      this.fle = new File(file1, "alts.json");
      this.b();
   }

   public void a(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         this.alts.removeIf(value1x -> value1x.c().equalsIgnoreCase(value1.c()));
         this.alts.add(0, value1);
         this.a();
      }
   }

   public void b(dev.leg1tka.accounts.AltAccount value1) {
      if (value1 != null) {
         this.alts.removeIf(value1x -> value1x.c().equalsIgnoreCase(value1.c()));
         this.a();
      }
   }

   public void a() {
      try (FileWriter fileWriter1 = new FileWriter(this.fle)) {
         this.gsn.toJson(this.alts, fileWriter1);
      } catch (Exception exception6) {
         exception6.printStackTrace();
      }
   }

   public void b() {
      if (this.fle.exists()) {
         try (FileReader fileReader1 = new FileReader(this.fle)) {
            Type type2 = (new TypeToken<List<dev.leg1tka.accounts.AltAccount>>() {
            }).getType();
            List list3 = (List)this.gsn.fromJson(fileReader1, type2);
            if (list3 != null) {
               this.alts.clear();
               this.alts.addAll(list3);
            }
         } catch (Exception exception6) {
            exception6.printStackTrace();
         }
      }
   }

   @Generated
   public List<dev.leg1tka.accounts.AltAccount> c() {
      return this.alts;
   }

   @Generated
   public Gson d() {
      return this.gsn;
   }

   @Generated
   public File e() {
      return this.fle;
   }
}
