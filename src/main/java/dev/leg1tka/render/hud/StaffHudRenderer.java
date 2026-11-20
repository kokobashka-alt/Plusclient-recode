package dev.leg1tka.render.hud;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.implement.EventRender;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.world.GameMode;

public class StaffHudRenderer extends dev.leg1tka.render.hud.HudRenderer {
   protected static int f;
   protected static int g;
   protected static int h;
   private final MinecraftClient l = MinecraftClient.getInstance();
   protected final Map<String, dev.leg1tka.render.hud.StaffHudRenderer.b> i = new LinkedHashMap<>();
   protected final Map<String, Float> j = new HashMap<>();
   protected final Set<String> k = new HashSet<>();
   private final Pattern m = Pattern.compile("^\\w{3,16}$");
   private final Set<String> n = new HashSet<>();
   private final dev.leg1tka.api.animation.Animation o = new dev.leg1tka.api.animation.Animation(60.0F, 10.5F, dev.leg1tka.api.animation.Easings.h);
   private float p = 18.0F;
   private long q = 0L;
   private final List<String> r = new ArrayList<>();
   private final Set<String> s = new HashSet<>();
   private dev.leg1tka.render.font.MsdfTextRenderer t;
   private dev.leg1tka.render.font.MsdfTextRenderer u;
   private dev.leg1tka.render.font.MsdfTextRenderer v;
   private dev.leg1tka.render.font.MsdfTextRenderer w;
   private dev.leg1tka.render.font.MsdfTextRenderer x;

   public StaffHudRenderer(dev.leg1tka.render.hud.HudElement value1) {
      super(value1);
      this.n
         .addAll(
            Arrays.asList(
               "supp",
               "ꜱupp",
               "mod",
               "der",
               "adm",
               "wne",
               "мод",
               "помо",
               "адм",
               "владе",
               "отри",
               "таф",
               "taf",
               "curat",
               "курато",
               "dev",
               "раз",
               "сапп",
               "yt",
               "ютуб",
               "стажер",
               "сотрудник",
               "owner",
               "curator",
               "helper",
               "moder",
               "admin",
               "хелп"
            )
         );
   }

   protected void e() {
      if (this.u == null) {
         this.t = dev.leg1tka.render.font.FontCache.a("nunito", 10);
         this.u = dev.leg1tka.render.font.FontCache.a("nunito", 12);
         this.v = dev.leg1tka.render.font.FontCache.a("nunito", 14);
         this.w = dev.leg1tka.render.font.FontCache.a("nunito", 16);
         this.x = dev.leg1tka.render.font.FontCache.a("icon", 13);
      }
   }

   @Override
   public void a(EventRender.Default default1) {
      if (this.l.player != null && this.l.world != null) {
         this.e();
         long longValue2 = System.currentTimeMillis();
         if (longValue2 - this.q > 500L) {
            this.f();
            this.q = longValue2;
         }

         this.g();
         this.b(default1);
         super.a(default1);
      }
   }

   private boolean b(String string1) {
      String string2 = string1.toLowerCase(Locale.ROOT);

      for (String string4 : this.n) {
         if (string2.contains(string4)) {
            return true;
         }
      }

      return false;
   }

   private List<dev.leg1tka.render.hud.StaffHudRenderer.a> a(Text text1) {
      if (text1 == null) {
         return new ArrayList<>();
      } else {
         dev.leg1tka.gui.themes.ThemeGroupRegistry.a value2 = dev.leg1tka.gui.themes.ThemeGroupRegistry.c(dev.leg1tka.gui.themes.ThemeGroupRegistry.a(text1));
         if (value2 == null || !value2.isstaff() || !text1.getString().trim().isEmpty() && this.b(text1.getString())) {
            ArrayList arrayList4 = new ArrayList();
            text1.visit((style2x, string3x) -> {
               if (string3x != null && !string3x.isEmpty()) {
                  this.a(arrayList4, string3x, style2x.getColor() != null ? style2x.getColor().getRgb() : 16777215);
                  return Optional.empty();
               } else {
                  return Optional.empty();
               }
            }, Style.EMPTY);
            return arrayList4;
         } else {
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add(new dev.leg1tka.render.hud.StaffHudRenderer.a(value2.text() + " ", value2.col()));
            return arrayList3;
         }
      }
   }

   private void a(List<dev.leg1tka.render.hud.StaffHudRenderer.a> list1, String string2, int intValue3) {
      int intValue4 = intValue3;
      StringBuilder stringBuilder5 = new StringBuilder();
      int intValue6 = intValue3;
      int intValue7 = 0;

      while (intValue7 < string2.length()) {
         int intValue8 = string2.codePointAt(intValue7);
         int intValue9 = Character.charCount(intValue8);
         if (intValue8 == 167 && intValue7 + intValue9 < string2.length()) {
            this.a(list1, stringBuilder5, intValue6);
            char character14 = Character.toLowerCase(string2.charAt(intValue7 + intValue9));
            Integer integer15 = this.a(character14);
            intValue4 = integer15 != null ? integer15 : (character14 == 'r' ? intValue3 : intValue4);
            intValue6 = intValue4;
            intValue7 += intValue9 + 1;
         } else {
            String string10 = dev.leg1tka.gui.themes.ThemeRoleNames.a(intValue8);
            if (string10 == null) {
               if (stringBuilder5.length() > 0 && intValue6 != intValue4) {
                  this.a(list1, stringBuilder5, intValue6);
               }

               intValue6 = intValue4;
               stringBuilder5.appendCodePoint(intValue8);
               intValue7 += intValue9;
            } else {
               this.a(list1, stringBuilder5, intValue6);
               int intValue11 = Math.max(1, string10.length());

               for (int intValue12 = 0; intValue12 < string10.length(); intValue12++) {
                  int intValue13 = dev.leg1tka.gui.themes.ThemeRoleNames.a(intValue8, intValue12, intValue11, 1.0F, intValue4);
                  if (stringBuilder5.length() > 0 && intValue6 != intValue13) {
                     this.a(list1, stringBuilder5, intValue6);
                  }

                  intValue6 = intValue13;
                  stringBuilder5.append(string10.charAt(intValue12));
               }

               intValue7 += intValue9;
            }
         }
      }

      this.a(list1, stringBuilder5, intValue6);
   }

   private void a(List<dev.leg1tka.render.hud.StaffHudRenderer.a> list1, StringBuilder stringBuilder2, int intValue3) {
      if (!stringBuilder2.isEmpty()) {
         String string4 = stringBuilder2.toString();
         dev.leg1tka.render.hud.StaffHudRenderer.a value5 = new dev.leg1tka.render.hud.StaffHudRenderer.a(string4, intValue3);
         if (this.u != null) {
            value5.c = this.u.b(string4);
         }

         if (this.v != null) {
            value5.d = this.v.b(string4);
         }

         list1.add(value5);
         stringBuilder2.setLength(0);
      }
   }

   private Integer a(char character1) {
      return switch (character1) {
         case '0' -> 0;
         case '1' -> 170;
         case '2' -> 43520;
         case '3' -> 43690;
         case '4' -> 11141120;
         case '5' -> 11141290;
         case '6' -> 16755200;
         case '7' -> 11184810;
         case '8' -> 5592405;
         case '9' -> 5592575;
         default -> null;
         case 'a' -> 5635925;
         case 'b' -> 5636095;
         case 'c' -> 16733525;
         case 'd' -> 16733695;
         case 'e' -> 16777045;
         case 'f' -> 16777215;
      };
   }

   protected void f() {
      this.k.clear();
      String string1 = this.l.player.getName().getString();

      for (Team team3 : this.l.world.getScoreboard().getTeams()) {
         Collection collection4 = team3.getPlayerList();
         if (collection4.size() == 1) {
            String string5 = (String)collection4.iterator().next();
            if (this.m.matcher(string5).matches() && !string5.equals(string1)) {
               PlayerListEntry playerListEntry6 = this.l.getNetworkHandler() != null ? this.l.getNetworkHandler().getPlayerListEntry(string5) : null;
               boolean flag7 = playerListEntry6 == null;
               boolean flag8 = playerListEntry6 != null && playerListEntry6.getGameMode() == GameMode.SPECTATOR;
               Text text9 = team3.getPrefix();
               String string10 = text9 != null ? text9.getString() : "";
               boolean flag11 = this.b(string10);
               dev.leg1tka.gui.themes.ThemeGroupRegistry.a value12 = dev.leg1tka.gui.themes.ThemeGroupRegistry.a(team3);
               boolean flag13 = value12 != null && value12.isstaff();
               boolean flag14 = AspectDLC.INSTANCE.staffStorage.c(string5);
               if ((flag11 || flag13 || flag7 || flag8 || flag14) && (flag11 || flag13 || flag14 || this.k.contains(string5) || this.i.containsKey(string5))) {
                  this.k.add(string5);
                  String string15 = flag7 ? "VANISH" : (flag8 ? "GM3" : "ONLINE");
                  dev.leg1tka.render.hud.StaffHudRenderer.b value16 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string5);
                  if (value16 == null) {
                     value16 = new dev.leg1tka.render.hud.StaffHudRenderer.b(string15);
                     this.i.put(string5, value16);
                  }

                  value16.a = string15;
                  if (!flag13 || text9 != null && !text9.getString().trim().isEmpty() && flag11) {
                     value16.b = this.a(text9);
                  } else {
                     value16.b = new ArrayList();
                     value16.b.add(new dev.leg1tka.render.hud.StaffHudRenderer.a(value12.text() + " ", value12.col()));
                  }

                  this.a(value16, string5);
               }
            }
         }
      }

      if (this.l.getNetworkHandler() != null) {
         for (PlayerListEntry playerListEntry19 : this.l.getNetworkHandler().getPlayerList()) {
            if (playerListEntry19.getProfile() != null && playerListEntry19.getProfile().getName() != null) {
               String string21 = playerListEntry19.getProfile().getName();
               if (!string21.equals(string1) && this.m.matcher(string21).matches() && !this.k.contains(string21)) {
                  dev.leg1tka.gui.themes.ThemeGroupRegistry.a value23 = dev.leg1tka.gui.themes.ThemeGroupRegistry.a(playerListEntry19);
                  boolean flag25 = value23 != null && value23.isstaff();
                  boolean flag28 = AspectDLC.INSTANCE.staffStorage.c(string21);
                  boolean flag29 = playerListEntry19.getGameMode() == GameMode.SPECTATOR;
                  if (flag25 || flag28 || flag29) {
                     this.k.add(string21);
                     String string30 = flag29 ? "GM3" : "ONLINE";
                     dev.leg1tka.render.hud.StaffHudRenderer.b value31 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string21);
                     if (value31 == null) {
                        value31 = new dev.leg1tka.render.hud.StaffHudRenderer.b(string30);
                        this.i.put(string21, value31);
                     }

                     value31.a = string30;
                     if (value23 != null) {
                        value31.b = new ArrayList();
                        value31.b.add(new dev.leg1tka.render.hud.StaffHudRenderer.a(value23.text() + " ", value23.col()));
                     } else if (playerListEntry19.getScoreboardTeam() != null) {
                        value31.b = this.a(playerListEntry19.getScoreboardTeam().getPrefix());
                     } else {
                        value31.b = new ArrayList();
                     }

                     this.a(value31, string21);
                  }
               }
            }
         }
      }

      for (String string20 : AspectDLC.INSTANCE.staffStorage.c()) {
         if (!string20.equals(string1) && this.m.matcher(string20).matches() && !this.k.contains(string20)) {
            this.k.add(string20);
            PlayerListEntry playerListEntry22 = this.l.getNetworkHandler() != null ? this.l.getNetworkHandler().getPlayerListEntry(string20) : null;
            String string24 = playerListEntry22 == null ? "VANISH" : (playerListEntry22.getGameMode() == GameMode.SPECTATOR ? "GM3" : "ONLINE");
            dev.leg1tka.render.hud.StaffHudRenderer.b value26 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string20);
            if (value26 == null) {
               value26 = new dev.leg1tka.render.hud.StaffHudRenderer.b(string24);
               value26.b = new ArrayList();
               this.a(value26, string20);
               this.i.put(string20, value26);
            } else {
               value26.a = string24;
            }
         }
      }
   }

   private void a(dev.leg1tka.render.hud.StaffHudRenderer.b value1, String string2) {
      value1.c = 0.0F;
      value1.d = 0.0F;

      for (dev.leg1tka.render.hud.StaffHudRenderer.a value4 : value1.b) {
         value1.c = value1.c + value4.c;
         value1.d = value1.d + value4.d;
      }

      value1.e = this.u != null ? this.u.b(string2) : 0.0F;
      value1.f = this.v != null ? this.v.b(string2 + " >> ") : 0.0F;
   }

   protected void g() {
      float floatValue1 = 0.1F;
      this.s.clear();
      this.s.addAll(this.j.keySet());
      this.s.addAll(this.k);

      for (String string3 : this.s) {
         boolean flag4 = this.k.contains(string3);
         float floatValue5 = flag4 ? 1.0F : 0.0F;
         float floatValue6 = this.j.getOrDefault(string3, 0.0F);
         floatValue6 += (floatValue5 - floatValue6) * floatValue1;
         this.j.put(string3, floatValue6);
      }

      Iterator iterator7 = this.j.entrySet().iterator();

      while (iterator7.hasNext()) {
         Entry entry8 = (Entry)iterator7.next();
         if ((Float)entry8.getValue() < 0.01F && !this.k.contains(entry8.getKey())) {
            iterator7.remove();
            this.i.remove(entry8.getKey());
         }
      }
   }

   protected List<String> h() {
      this.r.clear();

      for (Map.Entry<String, Float> entry2 : this.j.entrySet()) {
         if ((Float)entry2.getValue() > 0.01F) {
            this.r.add((String)entry2.getKey());
         }
      }

      Collections.sort(this.r);
      return this.r;
   }

   protected int a(String string1) {
      return switch (string1) {
         case "VANISH" -> -47526;
         case "GM3" -> -9146;
         default -> -10158216;
      };
   }

   private void b(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      MatrixStack matrixStack4 = default1.getContext().getMatrices();
      int intValue5;
      if (!AspectDLC.INSTANCE.themeStorage.b().a().b().equals("Rainbow")) {
         intValue5 = AspectDLC.INSTANCE.themeStorage.b().a().c[0];
      } else {
         intValue5 = dev.leg1tka.api.utils.text.ColorUtils.a();
      }

      List<String> list6 = this.h();
      float floatValue7 = 60.0F;
      dev.leg1tka.render.font.MsdfTextRenderer value8 = dev.leg1tka.render.font.FontCache.a("iconsminced", 15);
      dev.leg1tka.render.font.MsdfTextRenderer value9 = dev.leg1tka.render.font.FontCache.a("nunito", 13);
      float floatValue10 = value8.b("D");
      float floatValue11 = floatValue2 + 3.5F + (8.5F - floatValue10) / 2.0F;
      float floatValue12 = floatValue2 + 17.5F;
      float floatValue13 = floatValue12 - floatValue2 + value9.b("Staff list") + 6.0F;
      if (floatValue13 > floatValue7) {
         floatValue7 = floatValue13;
      }

      for (String string15 : list6) {
         dev.leg1tka.render.hud.StaffHudRenderer.b value16 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string15);
         if (value16 != null) {
            String string17 = value16.a.equals("VANISH") ? "-" : (value16.a.equals("GM3") ? "±" : "+");
            float floatValue18 = this.u.b(string17);
            float floatValue19 = 17.5F + value16.c + value16.e + 6.0F + floatValue18 + 5.0F;
            if (floatValue19 > floatValue7) {
               floatValue7 = floatValue19;
            }
         }
      }

      this.o.animateTo(floatValue7);
      float floatValue33 = this.o.getValue();
      float floatValue34 = 0.0F;

      for (String string37 : list6) {
         floatValue34 += 11.5F * this.j.getOrDefault(string37, 0.0F);
      }

      float floatValue36 = list6.isEmpty() ? 11.5F : 11.5F + floatValue34;
      this.p = this.p + (floatValue36 - this.p) * 0.12F;
      float floatValue38 = this.p;
      this.a(matrixStack4, floatValue2, floatValue3, floatValue33, floatValue38, intValue5);
      value8.d(matrixStack4, "D", floatValue11, floatValue3 + 4.75F, intValue5);
      dev.leg1tka.render.RenderPrimitives.b(matrixStack4, floatValue2 + 14.0F, floatValue3 + 3.0F, 0.8F, 5.5F, 0.4F, dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, 140));
      value9.d(matrixStack4, "Staff list", floatValue12, floatValue3 + 3.5F, -1);
      float floatValue39 = 11.5F;
      dev.leg1tka.render.ScissorStack.a();
      dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue33, (double)floatValue38);

      for (String string20 : list6) {
         float floatValue21 = this.j.getOrDefault(string20, 0.0F);
         if (!(floatValue21 <= 0.01F)) {
            dev.leg1tka.render.hud.StaffHudRenderer.b value22 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string20);
            if (value22 != null) {
               int intValue23 = (int)(255.0F * floatValue21);
               dev.leg1tka.render.RenderPrimitives.a(matrixStack4, string20, floatValue2 + 3.0F, floatValue3 + floatValue39 + 1.25F, 9.0F, 2.0F, (float)intValue23 / 255.0F, 0.0F);
               float floatValue24 = floatValue2 + 14.0F;
               float floatValue25 = floatValue3 + floatValue39 + 3.0F;
               int intValue26 = dev.leg1tka.api.utils.text.ColorUtils.a(180, 185, 200, (int)(140.0F * floatValue21));
               dev.leg1tka.render.RenderPrimitives.b(matrixStack4, floatValue24, floatValue25, 0.8F, 5.5F, 0.4F, intValue26);
               float floatValue27 = floatValue2 + 17.5F;
               float floatValue28 = floatValue3 + floatValue39 + 3.65F;

               for (int intValue29 = 0; intValue29 < value22.b.size(); intValue29++) {
                  dev.leg1tka.render.hud.StaffHudRenderer.a value30 = (dev.leg1tka.render.hud.StaffHudRenderer.a)value22.b.get(intValue29);
                  int intValue31 = dev.leg1tka.api.utils.text.ColorUtils.d(value30.b, intValue23);
                  this.u.d(matrixStack4, value30.a, floatValue27, floatValue28, intValue31);
                  floatValue27 += value30.c;
               }

               this.u.d(matrixStack4, string20, floatValue27, floatValue28, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue23));
               String string41 = value22.a.equals("VANISH") ? "-" : (value22.a.equals("GM3") ? "±" : "+");
               float floatValue42 = this.u.b(string41);
               float floatValue43 = floatValue2 + floatValue33 - floatValue42 - 5.0F;
               int intValue32 = dev.leg1tka.api.utils.text.ColorUtils.d(this.a(value22.a), intValue23);
               this.u.d(matrixStack4, string41, floatValue43, floatValue28, intValue32);
               floatValue39 += 11.5F * floatValue21;
            }
         }
      }

      dev.leg1tka.render.ScissorStack.b();
      dev.leg1tka.render.ScissorStack.c();
      this.e.c(floatValue33);
      this.e.d(floatValue38);
   }

   private void c(EventRender.Default default1) {
      float floatValue2 = this.e.a();
      float floatValue3 = this.e.b();
      MatrixStack matrixStack4 = default1.getContext().getMatrices();
      int intValue5 = (int)((float)(System.currentTimeMillis() % 2000L) / 2000.0F * 360.0F);
      int intValue6 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5);
      int intValue7 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 30);
      int intValue8 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 90);
      int intValue9 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 120);
      int intValue10 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 180);
      int intValue11 = dev.leg1tka.api.utils.text.ColorUtils.k(intValue5 + 210);
      List<String> list12 = this.h();
      float floatValue13 = 80.0F;
      float floatValue14 = 18.0F;
      float floatValue15 = 10.0F;
      float floatValue16 = 5.0F;

      for (String string18 : list12) {
         dev.leg1tka.render.hud.StaffHudRenderer.b value19 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string18);
         if (value19 != null) {
            String string20 = value19.a.equals("VANISH") ? "-" : (value19.a.equals("GM3") ? "±" : "+");
            float floatValue21 = this.u.b(string20);
            float floatValue22 = floatValue16 + value19.d + value19.f + floatValue21 + floatValue16;
            if (floatValue22 > floatValue13) {
               floatValue13 = floatValue22;
            }
         }
      }

      float floatValue32 = 0.0F;

      for (String string35 : list12) {
         floatValue32 += floatValue15 * this.j.getOrDefault(string35, 0.0F);
      }

      float floatValue34 = list12.isEmpty() ? floatValue14 : floatValue14 + floatValue32 + 4.0F;
      if (list12.isEmpty()) {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack4, floatValue2, floatValue3, floatValue13, 15.0F, 0.0F, 10.0F, 10.0F, intValue6, intValue7, intValue8, intValue9, intValue10, intValue11);
         float floatValue37 = floatValue2 + (floatValue13 - this.w.b("stafflist")) / 2.0F;
         this.w.f(matrixStack4, "stafflist", floatValue37, floatValue3 + 5.0F, -1);
         this.e.c(floatValue13);
         this.e.d(floatValue14);
      } else {
         dev.leg1tka.render.RenderPrimitives.a(matrixStack4, floatValue2, floatValue3, floatValue13, floatValue34, dev.leg1tka.api.utils.text.ColorUtils.a(25, 25, 25, 150), 15.0F, 0.0F, 10.0F, 10.0F, intValue6, intValue7, intValue8, intValue9, intValue10, intValue11);
         float floatValue36 = floatValue2 + (floatValue13 - this.w.b("stafflist")) / 1.9F;
         this.w.f(matrixStack4, "stafflist", floatValue36, floatValue3 + 5.0F, -1);
         float floatValue38 = 20.0F;
         dev.leg1tka.render.ScissorStack.a();
         dev.leg1tka.render.ScissorStack.a((double)floatValue2, (double)floatValue3, (double)floatValue13, (double)floatValue34);

         for (String string23 : list12) {
            float floatValue24 = this.j.getOrDefault(string23, 0.0F);
            if (!(floatValue24 <= 0.01F)) {
               dev.leg1tka.render.hud.StaffHudRenderer.b value25 = (dev.leg1tka.render.hud.StaffHudRenderer.b)this.i.get(string23);
               if (value25 != null) {
                  int intValue26 = (int)(255.0F * floatValue24);
                  float floatValue27 = -5.0F * (1.0F - floatValue24);
                  float floatValue28 = floatValue2 + floatValue16;

                  for (int intValue29 = 0; intValue29 < value25.b.size(); intValue29++) {
                     dev.leg1tka.render.hud.StaffHudRenderer.a value30 = (dev.leg1tka.render.hud.StaffHudRenderer.a)value25.b.get(intValue29);
                     int intValue31 = dev.leg1tka.api.utils.text.ColorUtils.d(value30.b, intValue26);
                     this.v.d(matrixStack4, value30.a, floatValue28, floatValue3 + floatValue38 + 1.5F + floatValue27, intValue31);
                     floatValue28 += value30.d;
                  }

                  this.v.d(matrixStack4, string23, floatValue28, floatValue3 + floatValue38 + 1.5F + floatValue27, dev.leg1tka.api.utils.text.ColorUtils.a(255, 255, 255, intValue26));
                  float floatValue40 = value25.d + this.v.b(string23);
                  String string41 = value25.a.equals("VANISH") ? "-" : (value25.a.equals("GM3") ? "±" : "+");
                  float floatValue42 = floatValue2 + floatValue16 + floatValue40 + 4.0F;
                  this.v.d(matrixStack4, string41, floatValue42, floatValue3 + floatValue38 + 1.5F + floatValue27, dev.leg1tka.api.utils.text.ColorUtils.d(this.a(value25.a), intValue26));
                  floatValue38 += floatValue15 * floatValue24;
               }
            }
         }

         dev.leg1tka.render.ScissorStack.b();
         dev.leg1tka.render.ScissorStack.c();
         this.e.c(floatValue13);
         this.e.d(floatValue34);
      }
   }

   protected static class a {
      public final String a;
      public final int b;
      public float c;
      public float d;

      a(String string1, int intValue2) {
         this.a = string1;
         this.b = intValue2;
      }
   }

   protected static class b {
      public String a;
      public List<dev.leg1tka.render.hud.StaffHudRenderer.a> b;
      public float c;
      public float d;
      public float e;
      public float f;

      b(String string1) {
         this.a = string1;
         this.b = new ArrayList<>();
      }
   }
}
