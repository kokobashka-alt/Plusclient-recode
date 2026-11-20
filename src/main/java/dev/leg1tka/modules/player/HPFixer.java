package dev.leg1tka.modules.player;


import dev.leg1tka.modules.Module;
import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;

public class HPFixer extends Module {
   public static final HPFixer c = new HPFixer();
   private static final Pattern e = Pattern.compile("(?<!\\d)(\\d+(?:[.,]\\d+)?)(?!\\d)");
   public static float d;

   public HPFixer() {
      super(
         "HPFixer",
         "Берет HP из скорборда или ника",
         Category.PLAYER
      );
   }

   public static float a(LivingEntity livingEntity0) {
      if (livingEntity0 == null) {
         return 0.0F;
      } else {
         float floatValue1 = Math.max(0.0F, livingEntity0.getHealth() + livingEntity0.getAbsorptionAmount());
         if (livingEntity0 instanceof PlayerEntity playerEntity2 && c.isEnabled()) {
            Float float3 = a(playerEntity2);
            if (float3 != null) {
               return a(float3, livingEntity0);
            }

            Float float4 = b(playerEntity2);
            if (float4 != null) {
               return a(float4, livingEntity0);
            }

            return a(floatValue1, livingEntity0);
         }

         return a(floatValue1, livingEntity0);
      }
   }

   public static float b(LivingEntity livingEntity0) {
      if (livingEntity0 == null) {
         return 0.0F;
      } else {
         return c.isEnabled() ? a(livingEntity0) : livingEntity0.getHealth();
      }
   }

   public static float c(LivingEntity livingEntity0) {
      if (livingEntity0 == null) {
         return 0.0F;
      } else {
         return c.isEnabled() ? a(livingEntity0) : Math.max(0.0F, livingEntity0.getHealth() + livingEntity0.getAbsorptionAmount());
      }
   }

   public static float d(LivingEntity livingEntity0) {
      return livingEntity0 == null ? 0.0F : livingEntity0.getMaxHealth();
   }

   private static Float a(PlayerEntity playerEntity0) {
      try {
         ScoreboardObjective scoreboardObjective1 = playerEntity0.getScoreboard().getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME);
         if (scoreboardObjective1 == null) {
            scoreboardObjective1 = playerEntity0.getScoreboard().getObjectiveForSlot(ScoreboardDisplaySlot.LIST);
         }

         if (scoreboardObjective1 == null) {
            return null;
         } else {
            MutableText mutableText2 = ReadableScoreboardScore.getFormattedScore(
               playerEntity0.getScoreboard().getScore(playerEntity0, scoreboardObjective1), scoreboardObjective1.getNumberFormatOr(StyledNumberFormat.EMPTY)
            );
            return c(mutableText2.getString());
         }
      } catch (Exception exception3) {
         return null;
      }
   }

   private static Float b(PlayerEntity playerEntity0) {
      if (playerEntity0.getDisplayName() != null) {
         Float float1 = c(playerEntity0.getDisplayName().getString());
         if (float1 != null) {
            return float1;
         }
      }

      if (playerEntity0.getScoreboardTeam() != null) {
         if (playerEntity0.getScoreboardTeam().getPrefix() != null) {
            Float float2 = c(playerEntity0.getScoreboardTeam().getPrefix().getString());
            if (float2 != null) {
               return float2;
            }
         }

         if (playerEntity0.getScoreboardTeam().getSuffix() != null) {
            Float float3 = c(playerEntity0.getScoreboardTeam().getSuffix().getString());
            if (float3 != null) {
               return float3;
            }
         }
      }

      return null;
   }

   public static Float c(String string0) {
      if (string0 != null && !string0.isEmpty()) {
         String string1 = d(string0);
         string1 = string1.replace('❤', ' ')
            .replace('♥', ' ')
            .replace('♡', ' ')
            .replace("❤", " ")
            .replace("♥", " ")
            .replace("HP", " ")
            .replace("hp", " ")
            .replace("хп", " ")
            .replace("Хп", " ")
            .trim();
         Matcher matcher2 = e.matcher(string1);
         Float float3 = null;

         while (matcher2.find()) {
            String string4 = matcher2.group(1).replace(',', '.');

            try {
               float3 = Float.parseFloat(string4);
            } catch (NumberFormatException numberFormatException6) {
            }
         }

         return float3;
      } else {
         return null;
      }
   }

   private static String d(String string0) {
      return string0 == null ? "" : string0.replaceAll("§[0-9a-fk-orx]", "").replaceAll("(?i)§x(§[0-9a-f]){6}", "").trim();
   }

   private static float a(float floatValue0, LivingEntity livingEntity1) {
      return Math.max(0.0F, Math.min(floatValue0, Math.max(1.0F, livingEntity1.getMaxHealth() + livingEntity1.getAbsorptionAmount())));
   }
}
