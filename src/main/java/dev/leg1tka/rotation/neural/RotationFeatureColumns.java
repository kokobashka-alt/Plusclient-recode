package dev.leg1tka.rotation.neural;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

public final class RotationFeatureColumns {
   public static int a;
   public static int b;
   public static int c;
   public static int d;
   public static int e;
   public static final String[] f = new String[]{
      "sequence_id",
      "tick_index",
      "dt_ms",
      "target_entity_id",
      "yaw",
      "pitch",
      "x",
      "y",
      "z",
      "on_ground",
      "sprinting",
      "sneaking",
      "active_item",
      "flying",
      "swimming",
      "gliding",
      "in_vehicle",
      "is_climbing",
      "in_water",
      "pose",
      "input_forward",
      "input_backward",
      "input_left",
      "input_right",
      "input_jump",
      "input_shift",
      "input_sprint",
      "game_mode",
      "food_level",
      "ground_friction",
      "fall_distance",
      "movement_speed",
      "jump_amplifier",
      "slow_falling",
      "player_ping",
      "speed_amplifier",
      "slowness_amplifier",
      "haste_amplifier",
      "mining_fatigue_amplifier",
      "entity_interaction_range",
      "scale",
      "gravity",
      "jump_strength",
      "step_height",
      "target_valid",
      "target_distance",
      "target_aim_angle",
      "target_rel_x",
      "target_rel_y",
      "target_rel_z",
      "ticks_to_attack",
      "attack_this_tick",
      "aim_error",
      "same_target_as_prev_attack",
      "targets_in_range_count",
      "is_critical",
      "raytrace_hit",
      "attack_ray_occluded",
      "raytrace_miss_distance",
      "input_valid",
      "hit_point_rel_x",
      "hit_point_rel_y",
      "hit_point_rel_z",
      "ticks_since_attack",
      "swung_this_tick",
      "attack_cooldown_progress",
      "attack_speed",
      "is_sprinting_on_attack",
      "received_velocity_this_tick",
      "velocity_x",
      "velocity_y",
      "velocity_z",
      "ticks_since_velocity",
      "received_explosion_this_tick",
      "ticks_since_explosion",
      "ticks_since_use_item",
      "rotation_present",
      "mode_yaw",
      "mode_pitch",
      "mode_yaw_valid",
      "mode_pitch_valid",
      "delta_dots_yaw",
      "delta_dots_pitch",
      "active_firework_count",
      "stuck_mult_x",
      "stuck_mult_y",
      "stuck_mult_z",
      "on_slime",
      "on_honey",
      "on_soul_sand",
      "on_mud",
      "levitation_amplifier",
      "swim_friction",
      "riptide_active",
      "teleport_tick",
      "exposure_fraction",
      "box_inflation",
      "health",
      "damage_taken_this_tick",
      "ticks_since_damage",
      "window_start",
      "target_type",
      "crystal_spawn_to_attack",
      "use_offhand",
      "nearby_crystal_count",
      "anchor_charge",
      "anchor_use_interval",
      "place_face",
      "place_block_class",
      "place_block_x",
      "place_block_y",
      "place_block_z",
      "place_cursor_x",
      "place_cursor_y",
      "place_cursor_z",
      "place_inside_block",
      "places_this_tick",
      "held_item_class",
      "offhand_item_class",
      "slot_switches_this_tick",
      "explosion_kb_x",
      "explosion_kb_y",
      "explosion_kb_z",
      "hittable_entities_count",
      "aim_error_yaw",
      "aim_error_pitch",
      "raytrace_dirs_hit_count",
      "raytrace_hit_strict"
   };
   public static final int[] g = new int[]{
      0,
      0,
      1,
      0,
      2,
      2,
      3,
      3,
      3,
      4,
      4,
      4,
      0,
      4,
      4,
      4,
      4,
      4,
      4,
      0,
      4,
      4,
      4,
      4,
      4,
      4,
      4,
      0,
      0,
      1,
      1,
      1,
      0,
      4,
      0,
      0,
      0,
      0,
      0,
      1,
      1,
      1,
      1,
      1,
      4,
      1,
      1,
      1,
      1,
      1,
      0,
      4,
      1,
      4,
      0,
      4,
      4,
      4,
      1,
      4,
      1,
      1,
      1,
      0,
      4,
      1,
      1,
      4,
      4,
      1,
      1,
      1,
      0,
      4,
      0,
      0,
      4,
      1,
      1,
      4,
      4,
      1,
      1,
      0,
      1,
      1,
      1,
      4,
      4,
      4,
      4,
      0,
      1,
      4,
      4,
      1,
      1,
      1,
      1,
      0,
      0,
      0,
      0,
      4,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      0,
      1,
      1,
      1,
      4,
      0,
      0,
      0,
      0,
      1,
      1,
      1,
      0,
      1,
      1,
      0,
      4
   };
   public static final int h = f.length;
   public static final String i = String.join(",", f);
   public static int j;
   public static int k;
   public static int l;
   public static int m;
   public static int n;
   public static int o;
   public static int p;
   public static int q;
   public static int r;
   public static int s;
   public static int t;
   public static int u;
   public static int v;
   public static int w;
   public static int x;
   public static int y;
   public static int z;
   public static int A;
   public static int B;
   public static int C;
   public static int D;
   public static int E;
   public static int F;
   public static int G;
   public static int H;
   public static int I;
   public static int J;
   public static int K;
   public static int L;
   public static int M;
   public static int N;
   public static int O;
   public static int P;
   public static int Q;
   public static int R;
   public static int S;
   public static int T;
   public static int U;
   public static int V;
   public static int W;
   public static int X;
   public static int Y;
   public static int Z;
   public static int ModuleStorage;
   public static int ab;
   public static int ac;
   public static int ad;
   public static int ae;
   public static int af;
   public static int ag;
   public static int ah;
   public static int Storage;
   public static int ModuleManager;
   public static int ModuleRegistry;
   public static int al;
   public static int am;
   public static int an;
   public static int ao;
   public static int ap;
   public static int aq;
   public static int ar;
   public static int as;
   public static int at;
   public static int au;
   public static int av;
   public static int aw;
   public static int ax;
   public static int ay;
   public static int az;
   public static int aA;
   public static int aB;
   public static int aC;
   public static int aD;
   public static int aE;
   public static int aF;
   public static int aG;
   public static int aH;
   public static int aI;
   public static int aJ;
   public static int aK;
   public static int aL;
   public static int aM;
   public static int aN;
   public static int aO;
   public static int aP;
   public static int aQ;
   public static int aR;
   public static int aS;
   public static int aT;
   public static int aU;
   public static int aV;
   public static int aW;
   public static int aX;
   public static int aY;
   public static int aZ;
   public static int ba;
   public static int bb;
   public static int bc;
   public static int bd;
   public static int be;
   public static int bf;
   public static int bg;
   public static int bh;
   public static int bi;
   public static int bj;
   public static int bk;
   public static int bl;
   public static int bm;
   public static int bn;
   public static int bo;
   public static int bp;
   public static int bq;
   public static int br;
   public static int bs;
   public static int bt;
   public static int bu;
   public static int bv;
   public static int bw;
   public static int bx;
   public static int by;
   public static int bz;
   public static int bA;
   public static int bB;
   public static int bC;
   public static int bD;
   public static int bE;
   public static int bF;
   public static int bG;
   private static long bI;
   private static long bJ;
   public final double[] bH = new double[h];

   public RotationFeatureColumns() {
   }

   public void a(int intValue1, boolean flag2) {
      this.bH[intValue1] = flag2 ? 1.0 : 0.0;
   }

   public boolean a(int intValue1) {
      return this.bH[intValue1] != 0.0;
   }

   public void a(StringBuilder stringBuilder1) {
      for (int intValue2 = 0; intValue2 < h; intValue2++) {
         if (intValue2 > 0) {
            stringBuilder1.append(',');
         }

         double doubleValue3 = this.bH[intValue2];
         switch (g[intValue2]) {
            case 0:
               stringBuilder1.append((long)doubleValue3);
               break;
            case 1:
               a(stringBuilder1, (float)doubleValue3);
               break;
            case 2:
               a(stringBuilder1, (double)((float)doubleValue3));
               break;
            case 3:
               a(stringBuilder1, doubleValue3);
               break;
            default:
               stringBuilder1.append((char)(doubleValue3 != 0.0 ? '1' : '0'));
         }
      }
   }

   private static void a(StringBuilder stringBuilder0, float floatValue1) {
      if (!Float.isFinite(floatValue1)) {
         stringBuilder0.append("0.000000");
      } else {
         double doubleValue2 = (double)floatValue1;
         boolean flag4 = doubleValue2 < 0.0;
         if (flag4) {
            doubleValue2 = -doubleValue2;
         }

         long longValue5 = (long)(doubleValue2 * 1000000.0 + 0.5);
         long longValue7 = longValue5 / 1000000L;
         long longValue9 = longValue5 % 1000000L;
         if (flag4 && longValue5 > 0L) {
            stringBuilder0.append('-');
         }

         stringBuilder0.append(longValue7).append('.');
         a(stringBuilder0, longValue9, 6);
      }
   }

   private static void a(StringBuilder stringBuilder0, double doubleValue1) {
      if (!Double.isFinite(doubleValue1)) {
         stringBuilder0.append("0.0000000000");
      } else {
         double doubleValue3 = doubleValue1;
         boolean flag5 = doubleValue1 < 0.0;
         if (flag5) {
            doubleValue3 = -doubleValue1;
         }

         long longValue6 = (long)(doubleValue3 * 1.0E10 + 0.5);
         long longValue8 = longValue6 / 10000000000L;
         long longValue10 = longValue6 % 10000000000L;
         if (flag5 && longValue6 > 0L) {
            stringBuilder0.append('-');
         }

         stringBuilder0.append(longValue8).append('.');
         a(stringBuilder0, longValue10, 10);
      }
   }

   private static void a(StringBuilder stringBuilder0, long longValue1, int intValue3) {
      long longValue4 = 1L;

      for (int intValue6 = 1; intValue6 < intValue3; intValue6++) {
         longValue4 *= 10L;
      }

      for (long longValue8 = longValue4; longValue8 > 1L && longValue1 < longValue8; longValue8 /= 10L) {
         stringBuilder0.append('0');
      }

      stringBuilder0.append(longValue1);
   }

   static {
      if (f.length != 128 || g.length != 128) {
         throw new IllegalStateException("Shard schema must have 128 columns");
      }
   }
}
