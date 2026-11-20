package dev.leg1tka.modules;

import dev.leg1tka.settings.*;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.Generated;

public abstract class Module implements dev.leg1tka.api.ClientAccess {
   private String name;
   private String description;
   private int keyBind;
   private Category category;
   private boolean g;
   private boolean enabled;
   private boolean i = false;
   private final List j = new ArrayList();
   private final dev.leg1tka.api.animation.Animation k = new dev.leg1tka.api.animation.Animation(60.0F, 11.0F, dev.leg1tka.api.animation.Easings.f);
   private final dev.leg1tka.api.animation.Animation l = new dev.leg1tka.api.animation.Animation(0.0F, 11.0F, dev.leg1tka.api.animation.Easings.f);

   public Module(String string1, String string2, Category category3) {
      this.name = string1;
      this.description = string2;
      this.category = category3;
      this.keyBind = -1;
   }

   public Module(String string1, Category category2) {
      this.name = string1;
      this.description = "NULLABLE";
      this.category = category2;
      this.keyBind = -1;
   }

   public void onEnable() {
      this.enabled = true;
      EventInvoker.register(this);
      this.k.animateTo(1.0F);
      dev.leg1tka.render.hud.NotificationQueue.a(this.name, this.category.c(), true);
   }

   public void onDisable() {
      this.enabled = false;
      EventInvoker.unregister(this);
      this.k.animateTo(0.0F);
      dev.leg1tka.render.hud.NotificationQueue.a(this.name, this.category.c(), false);
   }

   public void toggle() {
      this.enabled = !this.enabled;
      if (this.enabled) {
         this.onEnable();
      } else {
         this.onDisable();
      }
   }

   public void setEnabled(boolean flag1) {
      boolean flag2 = this.enabled;
      this.enabled = flag1;

      try {
         if (flag1) {
            this.onEnable();
         } else if (flag2) {
            this.onDisable();
         }
      } catch (Exception exception4) {
         this.enabled = false;
         this.onDisable();
      }
   }

   public void addSettings(Setting... settingArray1) {
      if (settingArray1 != null && settingArray1.length != 0) {
         Arrays.stream(settingArray1).filter(Objects::nonNull).forEach(this.j::add);
      }
   }

   public String getDisplayName() {
      return AspectDLC.INSTANCE.localizationStorage == null ? this.name : AspectDLC.INSTANCE.localizationStorage.a(this.name);
   }

   public String getDescription() {
      return AspectDLC.INSTANCE.localizationStorage == null ? this.description : AspectDLC.INSTANCE.localizationStorage.a(this.description);
   }

   @Generated
   public String getName() {
      return this.name;
   }

   @Generated
   public String getRawDescription() {
      return this.description;
   }

   @Generated
   public int getKeyBind() {
      return this.keyBind;
   }

   @Generated
   public Category getCategory() {
      return this.category;
   }

   @Generated
   public boolean j() {
      return this.g;
   }

   @Generated
   public boolean isEnabled() {
      return this.enabled;
   }

   @Generated
   public boolean l() {
      return this.i;
   }

   @Generated
   public List<Setting> getSettings() {
      return this.j;
   }

   @Generated
   public dev.leg1tka.api.animation.Animation getToggleAnimation() {
      return this.k;
   }

   @Generated
   public dev.leg1tka.api.animation.Animation getHoverAnimation() {
      return this.l;
   }

   @Generated
   public void setName(String string1) {
      this.name = string1;
   }

   @Generated
   public void setDescription(String string1) {
      this.description = string1;
   }

   @Generated
   public void setKeyBind(int intValue1) {
      this.keyBind = intValue1;
   }

   @Generated
   public void a(Category category1) {
      this.category = category1;
   }

   @Generated
   public void b(boolean flag1) {
      this.g = flag1;
   }

   @Generated
   public void c(boolean flag1) {
      this.enabled = flag1;
   }

   @Generated
   public void d(boolean flag1) {
      this.i = flag1;
   }
}
