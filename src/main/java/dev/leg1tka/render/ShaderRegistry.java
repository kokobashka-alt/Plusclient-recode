package dev.leg1tka.render;

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
import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

public final class ShaderRegistry implements dev.leg1tka.api.ClientAccess {
   public static final ShaderProgramKey c = a("rect", "rounded_rect", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey d = a("rect", "rounded_rect_batch", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey e = a("esp", "shape", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT_NORMAL);
   public static final ShaderProgramKey f = a("esp", "head", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey g = a("esp", "box", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey h = a("rect", "rounded_rect_outline", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey i = a("ring_arc", "ring_arc", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey j = a("texture", "texture_rect", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey k = a("liquidglass", "liquid", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey l = a("kawase_down", "kawase_down", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey m = a("kawase_up", "kawase_up", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey n = a("gradient_rect", "gradient", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey o = a("shadow_rect", "shadow", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey p = a("shadow6", "shadow", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey q = a("shadow_outer", "shadow", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey r = a("fonts", "fonts", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey s = a("face", "face", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey t = a("gradient6", "gradient", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey u = a("sonar", "sonar", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey v = a("sonar", "scan_effect", VertexFormats.POSITION_TEXTURE);
   public static final ShaderProgramKey w = a("blockoverlay", "block_overlay", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey x = a("chams", "chams_fill", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey y = a("hands", "hands_mask_diff", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey z = a("hands", "hands_overlay", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey A = a("hands", "hands_glow", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey B = a("hands", "hands_kawase_down", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey C = a("hands", "hands_kawase_up", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey D = a("shaderesp", "glow", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey E = a("shaderesp", "fill", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey F = a("sky_nebula", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey G = a("sky_caustic", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey H = a("sky_space", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey I = a("sky_summer", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey J = a("sky_plasma", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey K = a("sky_balatro", "data", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey L = a("blur", "blur", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey M = a("main_menu", "liquid_silk", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey N = a("button", "volumetric_button", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey O = a("menu", "dock_button", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey P = a("menu", "eye", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey Q = a("menu", "ring", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey R = a("menu", "fill", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey S = a("menu", "logo", VertexFormats.POSITION_COLOR);
   public static final ShaderProgramKey T = a("atmosphere", "particles", VertexFormats.POSITION_TEXTURE_COLOR);
   public static final ShaderProgramKey U = a("atmosphere", "composite", VertexFormats.POSITION);
   public static final ShaderProgramKey V = a("atmosphere", "vol", VertexFormats.POSITION);
   public static final ShaderProgramKey W = a("atmosphere", "rays", VertexFormats.POSITION);
   public static final ShaderProgramKey X = a("atmosphere", "style", VertexFormats.POSITION);
   public static final ShaderProgramKey Y = a("atmosphere", "bloom", VertexFormats.POSITION);

   private static ShaderProgramKey a(String string0, String string1, VertexFormat vertexFormat2) {
      return new ShaderProgramKey(Identifier.of(dev.leg1tka.api.utils.resources.NamespaceResolver.a(), "core/" + string0 + "/" + string1), vertexFormat2, Defines.EMPTY);
   }

   @Generated
   private ShaderRegistry() {
      throw new UnsupportedOperationException("This ServerHelper a utility class and cannot be instantiated");
   }
}
