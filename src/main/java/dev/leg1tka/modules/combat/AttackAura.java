package dev.leg1tka.modules.combat;


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

import dev.leg1tka.AspectDLC;
import dev.leg1tka.api.events.EventInvoker;
import dev.leg1tka.api.events.EventLink;
import dev.leg1tka.api.events.implement.EventAttackEntity;
import dev.leg1tka.api.events.implement.EventChangeSprint;
import dev.leg1tka.api.events.implement.EventGameUpdate;
import dev.leg1tka.api.events.implement.EventKeyboardInput;
import dev.leg1tka.api.events.implement.EventLook;
import dev.leg1tka.api.events.implement.EventMoveInput;
import dev.leg1tka.api.events.implement.EventPlayerSync;
import dev.leg1tka.api.events.implement.EventUpdate;
import dev.leg1tka.api.events.implement.EventUpdatePost;
import dev.leg1tka.mixin.ILivingEntity;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import lombok.Generated;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.SequencedPacketCreator;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.CodEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket.Mode;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
public class AttackAura extends Module {
   public static BooleanSetting I = new BooleanSetting("Двигать камеру", false);
   public static final AttackAura c = new AttackAura();
   public final BooleanSetting d = new BooleanSetting("Poison", dev.leg1tka.api.utils.security.ClientIntegrityState.a()) {
      {
         this.visibleWhen(() -> false);
      }

      @Override
      public void setValue(boolean flag1) {
         super.setValue(flag1);
         if (flag1 != dev.leg1tka.api.utils.security.ClientIntegrityState.a()) {
            if (flag1) {
               dev.leg1tka.api.utils.security.ClientIntegrityState.f();
            } else {
               dev.leg1tka.api.utils.security.ClientIntegrityState.g();
            }
         }
      }
   };
   public final ModeSetting e = new ModeSetting("Тип", "Matrix/Fast", "Matrix/Fast", "Shard", "SpookyTime", "NoRotate", "WhiteRise", "HolyWorld", "Assist") {
      @Override
      public void a(String string1) {
         if ("SC3".equals(string1) || "SuckyTime".equals(string1)) {
            super.a("Matrix/Fast");
         } else if ("SpookyTime RC".equals(string1)) {
            super.a("SpookyTime");
         } else if ("Shard-ML".equals(string1)) {
            super.a("Shard");
            AttackAura.this.f.a("Snap-1.0");
         } else if ("ShardAC-AI".equals(string1)) {
            super.a("Shard");
            AttackAura.this.f.a("Snap-1.0");
         } else {
            super.a(string1);
         }
      }
   };
   public final ModeSetting f = (new ModeSetting("Модель обхода", "Shard-2.0", "Shard-2.0", "Snap-1.0") {
      @Override
      public void a(String string1) {
         super.a(!"Shard-0.22".equals(string1) && !"Shard-0.17".equals(string1) ? string1 : "Snap-1.0");
      }
   }).a(createSupplier1(this));
   public final NumberSetting g = new NumberSetting("Assist Radius", 0.0F, -0.5F, 1.0F, 0.01F).a(createSupplier2(this));
   public final NumberSetting h = new NumberSetting("Assist Speed Aimed", 0.25F, 0.0F, 5.0F, 0.01F).a(createSupplier3(this));
   public final NumberSetting i = new NumberSetting("Assist Speed Ground", 2.0F, 0.0F, 5.0F, 0.01F).a(createSupplier4(this));
   public final NumberSetting j = new NumberSetting("Assist Speed Air", 2.0F, 0.0F, 5.0F, 0.01F).a(createSupplier5(this));
   public final NumberSetting k = new NumberSetting("Assist Speed Elytra", 2.0F, 0.0F, 10.0F, 0.01F).a(createSupplier6(this));
   public final BooleanSetting l = new BooleanSetting("Нейро: Предохранитель", true)
      .visibleWhen(createSupplier7(this));
   public final NumberSetting m = new NumberSetting(
         "Нейро: Разброс стиля", 1.0F, 0.0F, 1.5F, 0.05F
      )
      .a(createSupplier8(this));
   public final BooleanSetting n = new BooleanSetting(
         "Нейро: Новый стиль на бой", true
      )
      .visibleWhen(createSupplier9(this));
   public final NumberSetting o = new NumberSetting("Assist FOV", 90.0F, 10.0F, 180.0F, 5.0F).a(createSupplier10(this));
   public final NumberSetting p = new NumberSetting("Assist Разгон (мс)", 70.0F, 10.0F, 300.0F, 5.0F).a(createSupplier11(this));
   public final NumberSetting q = new NumberSetting("Assist Торможение", 1.0F, 0.2F, 3.0F, 0.05F).a(createSupplier12(this));
   public final NumberSetting r = new NumberSetting("Assist Прилипание", 0.4F, 0.0F, 0.8F, 0.01F).a(createSupplier13(this));
   public final NumberSetting s = new NumberSetting("Assist Слежение", 0.6F, 0.0F, 1.0F, 0.01F).a(createSupplier14(this));
   private final BooleanSetting aV = new BooleanSetting("Бить булавой", false);
   private int aW = 0;
   private final MultiSetting aX = new MultiSetting(
      "Цели",
      new BooleanSetting("Игроки", true),
      new BooleanSetting("Голые", true),
      new BooleanSetting("Невидимки", true),
      new BooleanSetting("Мирные", false),
      new BooleanSetting("Мобы", true)
   );
   public final NumberSetting t = new NumberSetting("Дистанция атаки", 3.0F, 0.0F, 6.0F, 0.05F);
   public final NumberSetting u = new NumberSetting("Дистанция наводки", 3.0F, 0.0F, 6.0F, 0.05F);
   public final NumberSetting v = new NumberSetting(
      "Дистанция на элитрах", 50.0F, 10.0F, 100.0F, 0.05F
   );
   public final NumberSetting w = new NumberSetting("FOV", 360.0F, 10.0F, 360.0F, 10.0F);
   public final ModeSetting x = new ModeSetting(
         "Кулдаун ударов",
         "Рандомный",
         "Быстрый",
         "Рандомный"
      )
      .a(createSupplier15(this));
   public final BooleanSetting y = new BooleanSetting("Только криты", true);
   public final BooleanSetting z = new BooleanSetting(
      "Криты только с пробелом", false
   );
   public final ModeSetting A = new ModeSetting(
      "Сброс спринта",
      "Легитный",
      "Легитный",
      "Быстрый",
      "СпейсТайм"
   );
   public final BooleanSetting B = new BooleanSetting("Умные криты", false);
   public final ModeSetting C = new ModeSetting(
      "Кулдаун ударов",
      "Быстрый",
      "Быстрый",
      "Рандомный"
   );
   public final ModeSetting D = new ModeSetting(
      "Сброс спринта",
      "Пакетный",
      "Нет",
      "Пакетный",
      "WTap",
      "Легитный",
      "Легитный 2",
      "WellMine"
   );
   private final BooleanSetting aY = new BooleanSetting("Сброс только для крита", true)
      .visibleWhen(createSupplier16(this));
   private final BooleanSetting aZ = new BooleanSetting("Сброс заранее", true).visibleWhen(createSupplier17(this));
   public final dev.leg1tka.rotation.AttackTimingController E = new dev.leg1tka.rotation.AttackTimingController();
   public final dev.leg1tka.rotation.CombatPacketTracker F = new dev.leg1tka.rotation.CombatPacketTracker();
   private final BooleanSetting ba = new BooleanSetting("Проверка на наведение", false);
   public final BooleanSetting G = new BooleanSetting("Расширять хитбокс", false)
      .visibleWhen(createSupplier18(this));
   public final NumberSetting H = new NumberSetting("Размер расширения", 0.05F, 0.0F, 1.0F, 0.05F)
      .a(createSupplier19(this));
   private final BooleanSetting bb = new BooleanSetting("Отжимать щит", false);
   private final BooleanSetting bc = new BooleanSetting("Ломать щит", true);
   private final BooleanSetting bd = new BooleanSetting("Не бить когда ешь", true);
   private final ModeSetting be = new ModeSetting(
      "Коррекция",
      "Свободная",
      "Свободная",
      "Сфокусированная",
      "Таргетированная"
   );
   private final ModeSetting bf = new ModeSetting(
      "Приоритет",
      "Дистанция",
      "Дистанция",
      "Здоровье",
      "Угол",
      "Никакой"
   );
   public final ModeSetting J = new ModeSetting(
         "SC3: Режим скорости",
         "Ускорение",
         "Ускорение",
         "Постоянная",
         "Адаптивная"
      )
      .a(createSupplier20(this));
   public final BooleanSetting K = new BooleanSetting("SC3: Доводка", false).visibleWhen(createSupplier21(this));
   public final NumberSetting L = new NumberSetting("SC3: Скорость Yaw", 45.0F, 5.0F, 180.0F, 5.0F).a(createSupplier22(this));
   public final NumberSetting M = new NumberSetting("SC3: Скорость Pitch", 30.0F, 5.0F, 180.0F, 5.0F).a(createSupplier23(this));
   public final BooleanSetting N = new BooleanSetting(
         "SC3: Застывать на хитбоксе", false
      )
      .visibleWhen(createSupplier24(this));
   public final NumberSetting O = new NumberSetting(
         "SC3: Прирост ускорения", 0.004F, 0.001F, 0.02F, 0.001F
      )
      .a(createSupplier25(this));
   public final NumberSetting P = new NumberSetting("SC3: Спад ускорения", 0.01F, 0.001F, 0.1F, 0.001F)
      .a(createSupplier26(this));
   public final NumberSetting Q = new NumberSetting(
         "SC3: Спад при большом угле", 0.1F, 0.01F, 0.3F, 0.01F
      )
      .a(createSupplier27(this));
   public final NumberSetting R = new NumberSetting("SC3: Макс. ускорение", 0.18F, 0.1F, 0.5F, 0.01F)
      .a(createSupplier28(this));
   public final NumberSetting S = new NumberSetting("SC3: Мин. ускорение", -0.02F, -0.1F, 0.0F, 0.01F)
      .a(createSupplier29(this));
   public final NumberSetting T = new NumberSetting(
         "SC3: Порог большого угла", 80.0F, 30.0F, 180.0F, 5.0F
      )
      .a(createSupplier30(this));
   public final NumberSetting U = new NumberSetting("SC3: Дистанция RayTrace", 999.0F, 100.0F, 2000.0F, 10.0F).a(createSupplier31(this));
   public final NumberSetting V = new NumberSetting(
         "SC3: Расширение хитбокса", -0.2F, -1.0F, 1.0F, 0.1F
      )
      .a(createSupplier32(this));
   public final ModeSetting W = new ModeSetting(
         "SC3: Режим точки",
         "Ближайшая видимая",
         "Ближайшая видимая",
         "Грудь",
         "Голова"
      )
      .a(createSupplier33(this));
   public final BooleanSetting X = new BooleanSetting("SC3: Мультипоинт", true).visibleWhen(createSupplier34(this));
   public final ModeSetting Y = new ModeSetting(
         "SC3: Режим мультипоинта",
         "Мягкий",
         "Мягкий",
         "Агрессивный",
         "Скелетный",
         "Гауссов",
         "Органический",
         "Спиральный",
         "Периметр",
         "Кинетический"
      )
      .a(createSupplier35(this));
   public final NumberSetting Z = new NumberSetting(
         "SC3: Скорость смены мультипоинтов",
         1.0F,
         0.1F,
         3.0F,
         0.1F
      )
      .a(createSupplier36(this));
   public final BooleanSetting aa = new BooleanSetting("SC3: Фиксация точки", true).visibleWhen(createSupplier37(this));
   public final NumberSetting ab = new NumberSetting("SC3: Время фиксации", 3.0F, 1.0F, 10.0F, 1.0F)
      .a(createSupplier38(this));
   public final NumberSetting ac = new NumberSetting(
         "SC3: Множитель плавности Yaw", 1.0F, 0.1F, 2.5F, 0.1F
      )
      .a(createSupplier39(this));
   public final NumberSetting ad = new NumberSetting(
         "SC3: Множитель плавности Pitch", 0.5F, 0.1F, 2.5F, 0.1F
      )
      .a(createSupplier40(this));
   public final NumberSetting ae = new NumberSetting("SC3: Максимально вниз", 100.0F, 0.0F, 100.0F, 1.0F)
      .a(createSupplier41(this));
   public final NumberSetting af = new NumberSetting(
         "SC3: Максимально вверх", 100.0F, 0.0F, 100.0F, 1.0F
      )
      .a(createSupplier42(this));
   public final NumberSetting ag = new NumberSetting(
         "SC3: Замедление на хитбоксе",
         0.26F,
         0.05F,
         1.0F,
         0.01F
      )
      .a(createSupplier43(this));
   public final NumberSetting ah = new NumberSetting("SC3: Возврат скорости", 0.03F, 0.01F, 0.5F, 0.01F)
      .a(createSupplier44(this));
   public final BooleanSetting Storage = new BooleanSetting("SC3: Анти резкий разворот", true)
      .visibleWhen(createSupplier45(this));
   public final NumberSetting ModuleManager = new NumberSetting(
         "SC3: Порог анти разворота", 110.0F, 45.0F, 180.0F, 5.0F
      )
      .a(createSupplier46(this));
   public final NumberSetting ModuleRegistry = new NumberSetting(
         "SC3: Сила анти разворота", 0.45F, 0.05F, 0.95F, 0.05F
      )
      .a(createSupplier47(this));
   public final BooleanSetting al = new BooleanSetting("SC3: Fix Snap", false).visibleWhen(createSupplier48(this));
   public final BooleanSetting am = new BooleanSetting("SC3: Исправление GCD", false).visibleWhen(createSupplier49(this));
   public final BooleanSetting an = new BooleanSetting("SC3: Микро-овершут", false).visibleWhen(createSupplier50(this));
   public final NumberSetting ao = new NumberSetting("SC3: Сила овершута", 0.6F, 0.1F, 2.0F, 0.05F)
      .a(createSupplier51(this));
   public final BooleanSetting ap = new BooleanSetting("SC3: Задержка реакции", false)
      .visibleWhen(createSupplier52(this));
   public final NumberSetting aq = new NumberSetting("SC3: Время реакции (мс)", 80.0F, 20.0F, 200.0F, 5.0F)
      .a(createSupplier53(this));
   public final BooleanSetting ar = new BooleanSetting("SC3: Траектория Безье", false)
      .visibleWhen(createSupplier54(this));
   public final BooleanSetting as = new BooleanSetting("SC3: Shake Yaw", false).visibleWhen(createSupplier55(this));
   public final NumberSetting at = new NumberSetting("SC3: Скорость Shake Yaw", 0.45F, 0.0F, 3.0F, 0.05F).a(createSupplier56(this));
   public final NumberSetting au = new NumberSetting("SC3: Амплитуда Shake Yaw", 1.8F, 0.0F, 10.0F, 0.1F).a(createSupplier57(this));
   public final NumberSetting av = new NumberSetting("SC3: Затухание Shake Yaw", 0.3F, 0.0F, 1.0F, 0.01F).a(createSupplier58(this));
   public final NumberSetting aw = new NumberSetting("SC3: Отскок Shake Yaw", 0.1F, 0.0F, 1.0F, 0.05F).a(createSupplier59(this));
   public final NumberSetting ax = new NumberSetting("SC3: Рандом Shake Yaw", 0.8F, 0.0F, 1.0F, 0.05F).a(createSupplier60(this));
   public final BooleanSetting ay = new BooleanSetting("SC3: Shake Pitch", false).visibleWhen(createSupplier61(this));
   public final NumberSetting az = new NumberSetting("SC3: Скорость Shake Pitch", 0.25F, 0.0F, 3.0F, 0.05F).a(createSupplier62(this));
   public final NumberSetting aA = new NumberSetting("SC3: Амплитуда Shake Pitch", 0.8F, 0.0F, 6.0F, 0.1F).a(createSupplier63(this));
   public final NumberSetting aB = new NumberSetting("SC3: Затухание Shake Pitch", 0.35F, 0.0F, 1.0F, 0.01F).a(createSupplier64(this));
   public final NumberSetting aC = new NumberSetting("SC3: Отскок Shake Pitch", 0.0F, 0.0F, 1.0F, 0.05F).a(createSupplier65(this));
   public final NumberSetting aD = new NumberSetting("SC3: Рандом Shake Pitch", 0.5F, 0.0F, 1.0F, 0.05F).a(createSupplier66(this));
   public final BooleanSetting aE = new BooleanSetting("SC3: Jitter", false).visibleWhen(createSupplier67(this));
   public final NumberSetting aF = new NumberSetting("SC3: Шанс Jitter", 18.0F, 0.0F, 100.0F, 1.0F).a(createSupplier68(this));
   public final NumberSetting aG = new NumberSetting("SC3: Сила Jitter", 0.35F, 0.0F, 2.0F, 0.05F).a(createSupplier69(this));
   public final BooleanSetting aH = new BooleanSetting("SC3: Jitter только на доводке", true)
      .visibleWhen(createSupplier70(this));
   public final BooleanSetting aI = new BooleanSetting("SC3: Jitter только Yaw", true).visibleWhen(createSupplier71(this));
   public final BooleanSetting aJ = new BooleanSetting("SC3: Плавная отводка", true).visibleWhen(createSupplier72(this));
   public final NumberSetting aK = new NumberSetting(
         "SC3: Скорость отводки Yaw", 25.0F, 5.0F, 180.0F, 1.0F
      )
      .a(createSupplier73(this));
   public final NumberSetting aL = new NumberSetting(
         "SC3: Скорость отводки Pitch", 20.0F, 5.0F, 180.0F, 1.0F
      )
      .a(createSupplier74(this));
   private LivingEntity bg;
   private boolean bh = false;
   private boolean bi = false;
   private int bj = 0;
   private int bk = 0;
   private boolean bl = false;
   private boolean bm = false;
   private boolean bn = false;
   private boolean bo = false;
   private int bp = 0;
   private float bq = 0.0F;
   private boolean br = false;
   private int bs = 0;
   public Vec2f aM = new Vec2f(0.0F, 0.0F);
   public Vec2f aN = new Vec2f(0.0F, 0.0F);
   private final dev.leg1tka.api.utils.time.Stopwatch bt = new dev.leg1tka.api.utils.time.Stopwatch();
   private final BooleanSetting bu = new BooleanSetting("Через стены GRIM", false);
   private final BooleanSetting bv = new BooleanSetting("Только целые блоки", true)
      .visibleWhen(createSupplier75(this));
   private final Map bw = new HashMap();
   private ClientWorld bx;
   private int by;
   private long bz = -1L;
   private long bA;
   private long bB;
   private long bC;
   private int bD;
   private long bE = -1L;
   private long bF;
   private final dev.leg1tka.rotation.ShardRotationStrategy bG = new dev.leg1tka.rotation.ShardRotationStrategy();
   final dev.leg1tka.rotation.SmoothRotationStrategy bH = new dev.leg1tka.rotation.SmoothRotationStrategy();
   final dev.leg1tka.rotation.AdaptiveRotationStrategy bI = new dev.leg1tka.rotation.AdaptiveRotationStrategy();
   private final dev.leg1tka.rotation.WhiteRiseRotationStrategy bJ = new dev.leg1tka.rotation.WhiteRiseRotationStrategy(this);
   private final dev.leg1tka.rotation.holyworld.HolyWorldRotationStrategy holyWorld = new dev.leg1tka.rotation.holyworld.HolyWorldRotationStrategy();
   private final dev.leg1tka.api.utils.time.Stopwatch bK = new dev.leg1tka.api.utils.time.Stopwatch();
   private TpsSync bL;
   private long bM = 0L;
   private int bN = 0;
   public int aO = 0;
   private int bO = 0;
   private LivingEntity bP = null;
   private float bQ = 0.0F;
   private float bR = 0.0F;
   private float[] bS = new float[]{0.0F, 0.0F};
   private boolean bT = false;
   private long bU = 0L;
   private long bV = 0L;
   private float bW = 0.0F;
   private float bX = 0.0F;
   private long bY = 0L;
   public static float aP;
   public static float aQ;
   public static float aR;
   public boolean aS;
   private final BooleanSetting bZ = new BooleanSetting("Игнорировать голых", false);
   private final dev.leg1tka.rotation.TargetAimPoint ca = new dev.leg1tka.rotation.TargetAimPoint();
   private final dev.leg1tka.rotation.TargetAimPoint.a cb = new dev.leg1tka.rotation.TargetAimPoint.a();
   private Vec3d cc = Vec3d.ZERO;
   private boolean cd;
   public final dev.leg1tka.rotation.TargetRotationSampler aT = new dev.leg1tka.rotation.TargetRotationSampler();
   private final dev.leg1tka.rotation.TargetRotationSampler.a ce = new dev.leg1tka.rotation.TargetRotationSampler.a();
   private long cf;
   private int cg;
   private float ch;
   private float ci;
   private int cj;
   private final Random ck = new Random();
   public final dev.leg1tka.rotation.NeuralRotationController aU = new dev.leg1tka.rotation.NeuralRotationController(createBooleanSupplier76(this));
   private LivingEntity cl;
   private final dev.leg1tka.rotation.NeuralRotationController.a cm = createValue77(this);
   private static int cn;
   private static float co;
   private static float cp;
   private static String cq;
   private static String cr;
   private LivingEntity cs;
   private int ct;
   private boolean cu;
   private long cv;
   private boolean cw;

   public AttackAura() {
      super(
         "AttackAura",
         "Автоматически наводиться и бьёт таргета",
         Category.COMBAT
      );
      EventInvoker.register(this.aU);
      EventInvoker.register(this.E);
      EventInvoker.register(this.F);
      this.addSettings(
         new Setting[]{
            this.d,
            this.e,
            this.f,
            this.g,
            this.h,
            this.i,
            this.j,
            this.k,
            this.o,
            this.p,
            this.q,
            this.r,
            this.s,
            this.l,
            this.m,
            this.n,
            this.aX,
            this.w,
            this.t,
            this.u,
            this.v,
            this.x,
            this.y,
            this.z,
            this.A,
            this.aV,
            this.bd,
            this.bu,
            this.bv,
            this.ba,
            this.G,
            this.H,
            this.bb,
            this.bc,
            I,
            this.be,
            this.bf,
            this.J,
            this.K,
            this.L,
            this.M,
            this.N,
            this.O,
            this.P,
            this.Q,
            this.R,
            this.S,
            this.T,
            this.U,
            this.V,
            this.W,
            this.X,
            this.Y,
            this.Z,
            this.aa,
            this.ab,
            this.ac,
            this.ad,
            this.ae,
            this.af,
            this.ag,
            this.ah,
            this.Storage,
            this.ModuleManager,
            this.ModuleRegistry,
            this.al,
            this.am,
            this.an,
            this.ao,
            this.ap,
            this.aq,
            this.ar,
            this.as,
            this.at,
            this.au,
            this.av,
            this.aw,
            this.ax,
            this.ay,
            this.az,
            this.aA,
            this.aB,
            this.aC,
            this.aE,
            this.aF,
            this.aG,
            this.aH,
            this.aI,
            this.aJ,
            this.aK,
            this.aL
         }
      );
   }

   @EventLink
   public void onLook(EventLook eventLook1) {
      if (this.bg != null) {
         this.ca.a(eventLook1, this.bg, this.cb);
      }
   }

   @EventLink
   public void onPlayerTick(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null) return;
      this.an();
      if (this.bg != null) {
         this.Y();
      }
      if (this.N()) {
         this.Q();
      }
   }

   public boolean p() {
      return true;
   }

   public boolean q() {
      return false;
   }

   private boolean c(String string1) {
      return this.q() && this.D.b(string1);
   }

   private float K() {
      float floatValue1 = this.t.e().floatValue();
      return this.bg != null ? BackTrackOld.c.a(this.bg, floatValue1) : floatValue1;
   }

   private dev.leg1tka.rotation.AttackTimingController.a L() {
      dev.leg1tka.rotation.AttackTimingController.a value1 = this.E.k;
      LivingEntity livingEntity2 = this.bg;
      value1.a = livingEntity2;
      value1.d = this.p() && livingEntity2 != null;
      value1.c = livingEntity2 == null ? null : BackTrackOld.c.a((Entity)livingEntity2);
      value1.b = livingEntity2 == null ? 0.0F : this.K();
      value1.e = this.aO > 0 || MaceHelper.c.p();
      boolean flag3 = minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater() || minecraftClient.player.isSwimming();
      value1.f = this.y.getValue() && !flag3;
      value1.g = this.z.getValue();
      value1.h = this.bb.getValue();
      value1.i = this.bd.getValue();
      value1.j = true;
      value1.k = minecraftClient.player.getMainHandStack().getItem() instanceof MaceItem;
      value1.l = this.A.g();
      value1.m = this.N() ? "Рандомный" : this.x.g();
      return value1;
   }

   private void M() {
      dev.leg1tka.rotation.AttackTimingController.a value1 = this.L();
      this.E.b(value1);
      if (value1.d) {
         this.E.a(value1, createRunnable78(this));
      }
   }

   private boolean N() {
      return this.e.b("Shard") && this.f.b("Shard-2.0");
   }

   private void O() {
            dev.leg1tka.rotation.RotationStorage rotationStorage1 = dev.leg1tka.rotation.RotationStorage.c;
      if (minecraftClient.player != null && rotationStorage1 != null && rotationStorage1.b()) {
         dev.leg1tka.rotation.FreeLookStorage.a(minecraftClient.player.getYaw());
         dev.leg1tka.rotation.FreeLookStorage.b(minecraftClient.player.getPitch());
         rotationStorage1.a();
      }
   }

   private LivingEntity P() {
      if (this.bg != null) {
         return this.cs = this.bg;
      } else {
         LivingEntity livingEntity1 = this.cs;
         if (livingEntity1 != null && (livingEntity1.isRemoved() || !this.a(livingEntity1, 11.0F))) {
            livingEntity1 = null;
         }

         return this.cs = livingEntity1;
      }
   }

   private void Q() {
      if (this.N() && !dev.leg1tka.api.utils.security.ClientIntegrityState.a()) {
         dev.leg1tka.rotation.neural.RotationModelWeights value1 = dev.leg1tka.rotation.neural.RotationModelWeights.a();
         if (value1 == null) {
            long longValue8 = System.currentTimeMillis();
            if (longValue8 - this.cv > 5000L) {
               this.cv = longValue8;
               String string9 = dev.leg1tka.rotation.neural.RotationModelWeights.b();
               dev.leg1tka.api.utils.text.ClientChat.a(createString80(string9 != null ? createString79(string9) : ""));
            }
         } else {
            LivingEntity livingEntity2 = this.P();
            float floatValue3 = minecraftClient.player.getYaw();
            float floatValue4 = minecraftClient.player.getPitch();
            String string5 = livingEntity2 == null ? "no_target" : (minecraftClient.player.getEyePos().distanceTo(livingEntity2.getBoundingBox().getCenter()) > 11.0 ? "far" : null);
            int intValue6 = string5 == null ? this.a(livingEntity2) : 1;
            this.cl = livingEntity2;
            dev.leg1tka.rotation.NeuralRotationController.a value7 = intValue6 > 1 ? this.cm : null;
            if (string5 == null && !this.aU.step3(value1, livingEntity2, 0.3F, floatValue3, floatValue4, intValue6, value7)) {
               string5 = this.aU.b(livingEntity2);
            }

            if (string5 == null) {
               dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(this.aU.e, this.aU.f), 30.0F, 2, 1, I.getValue());
               this.cw = true;
               this.aN = new Vec2f(this.aU.e, this.aU.f);
               this.ct = 0;
               this.aU.a(this.cu);
               this.cu = false;
            } else {
               if (this.cw && (string5 == "no_target" || string5 == "far")) {
                  this.cw = false;
                  this.O();
               }

               this.ct = 0;
               this.aU.a(this.cu);
               this.cu = false;
            }
         }
      } else {
         if (this.cw && this.N()) {
            this.O();
         }

         this.ct = 0;
         this.cs = null;
         this.cw = false;
      }
   }

   private int a(LivingEntity livingEntity1) {
      byte byteValue2 = 8;
      if (byteValue2 > 1 && livingEntity1 != null && this.ah()) {
         if (minecraftClient.player.getAttackCooldownProgress(4.5F) < this.t()) {
            return 1;
         } else {
            Vec3d vec3d3 = minecraftClient.player.getEyePos();
            Box box4 = livingEntity1.getBoundingBox();
            double doubleValue5 = (box4.minX + box4.maxX) * 0.5 - vec3d3.x;
            double doubleValue7 = (box4.minZ + box4.maxZ) * 0.5 - vec3d3.z;
            return doubleValue5 * doubleValue5 + doubleValue7 * doubleValue7 <= 12.25 ? byteValue2 : 1;
         }
      } else {
         return 1;
      }
   }

   private double a(LivingEntity livingEntity1, float floatValue2, float floatValue3) {
      if (livingEntity1 == null) {
         return 0.0;
      } else if (this.b(livingEntity1, floatValue2, floatValue3)) {
         return 0.0;
      } else {
         Vec3d vec3d4 = minecraftClient.player.getEyePos();
         Box box5 = livingEntity1.getBoundingBox();
         double doubleValue6 = (box5.minX + box5.maxX) * 0.5 - vec3d4.x;
         double doubleValue8 = (box5.minY + box5.maxY) * 0.5 - vec3d4.y;
         double doubleValue10 = (box5.minZ + box5.maxZ) * 0.5 - vec3d4.z;
         double doubleValue12 = Math.sqrt(doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8 + doubleValue10 * doubleValue10) + 1.0E-9;
         double doubleValue14 = Math.toRadians((double)floatValue2);
         double doubleValue16 = Math.toRadians((double)floatValue3);
         double doubleValue18 = Math.cos(doubleValue16);
         double doubleValue20 = (-Math.sin(doubleValue14) * doubleValue18 * doubleValue6 - Math.sin(doubleValue16) * doubleValue8 + Math.cos(doubleValue14) * doubleValue18 * doubleValue10) / doubleValue12;
         return Math.acos(MathHelper.clamp(doubleValue20, -1.0, 1.0));
      }
   }

   private boolean b(LivingEntity livingEntity1, float floatValue2, float floatValue3) {
      Vec3d vec3d4 = minecraftClient.player.getEyePos();
      double doubleValue5 = minecraftClient.player.getEntityInteractionRange();
      Vec3d vec3d7 = vec3d4.add(Vec3d.fromPolar(floatValue3, floatValue2).multiply(doubleValue5));
      Box box8 = livingEntity1.getBoundingBox().expand((double)livingEntity1.getTargetingMargin());
      double doubleValue9;
      if (box8.contains(vec3d4)) {
         doubleValue9 = 0.0;
      } else {
         Optional optional11 = box8.raycast(vec3d4, vec3d7);
         if (optional11.isEmpty()) {
            return false;
         }

         doubleValue9 = vec3d4.squaredDistanceTo((Vec3d)optional11.get());
      }

      BlockHitResult blockHitResult12 = minecraftClient.world.raycast(new RaycastContext(vec3d4, vec3d7, ShapeType.OUTLINE, FluidHandling.NONE, minecraftClient.player));
      return blockHitResult12.getType() == Type.MISS || vec3d4.squaredDistanceTo(blockHitResult12.getPos()) >= doubleValue9;
   }

   private void R() {
      if (this.e.b("Нейро") && this.bg != null) {
         dev.leg1tka.rotation.neural.RotationDatasetLoader value1 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.c();
         if (value1 == null) {
            long longValue7 = System.currentTimeMillis();
            if (longValue7 - this.cf > 5000L) {
               this.cf = longValue7;
               String string8 = dev.leg1tka.rotation.neural.RotationDatasetRecorder.g();
               dev.leg1tka.api.utils.text.ClientChat.a(createString82(string8 != null ? createString81(string8) : ""));
            }
         } else {
            this.ce.a = this.l.getValue();
            this.ce.b = this.m.f();
            this.ce.c = this.n.getValue();

            float[] floatValueArray2;
            try {
               floatValueArray2 = this.aT.a(value1, this.bg, minecraftClient.player.getYaw(), minecraftClient.player.getPitch(), this.ce);
            } catch (Exception exception6) {
               this.aT.a();
               long longValue4 = System.currentTimeMillis();
               if (longValue4 - this.cf > 5000L) {
                  this.cf = longValue4;
                  dev.leg1tka.api.utils.text.ClientChat.a(createString83(String.valueOf(exception6)));
               }

               exception6.printStackTrace();
               return;
            }

            if (floatValueArray2 != null) {
               dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValueArray2[0], floatValueArray2[1]), 360.0F, 360.0F, 30.0F, 30.0F, 2, 1, I.getValue());
               this.aN = new Vec2f(floatValueArray2[0], floatValueArray2[1]);
               if (dev.leg1tka.rotation.neural.RotationDatasetRecorder.e) {
                  this.cg++;
                  this.ch = this.ch + this.aT.c;
                  this.ci = this.ci + (float)Math.hypot((double)this.aT.e, (double)this.aT.f) * 20.0F;
                  if (this.aT.c == 0.0F) {
                     this.cj++;
                  }

                  if (this.cg >= 20) {
                     dev.leg1tka.api.utils.text.ClientChat.a(
                        String.format(
                           Locale.ROOT,
                           "[Neuro] на хитбоксе %d%%, вне на %.1f°, скорость %.0f°/с, предохранитель %.0f%%, сеть %.2f мс",
                           this.cj * 100 / this.cg,
                           this.ch / (float)this.cg,
                           this.ci / (float)this.cg,
                           this.aT.d * 100.0F,
                           (double)this.aT.g / 1000000.0
                        )
                     );
                     this.cg = 0;
                     this.ch = 0.0F;
                     this.ci = 0.0F;
                     this.cj = 0;
                  }
               }
            }
         }
      } else {
         this.aT.a();
      }
   }

   private void S() {
      if (!this.e.b("Assist")) {
         this.cd = false;
      } else {
         if (!this.cd) {
            this.cd = true;
            if (dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.b()) {
               dev.leg1tka.rotation.RotationStorage.c.a();
            }

            this.ca.a();
         }

         this.cc = Vec3d.ZERO;
         if (this.bg != null && minecraftClient.player.isGliding() && this.bg.isGliding() && dev.leg1tka.managers.ModuleManager.O != null && dev.leg1tka.managers.ModuleManager.O.isEnabled()) {
            Vec3d vec3d1 = this.bg.getBoundingBox().getCenter();
            Vec3d vec3d2 = this.a(this.bg, vec3d1);
            if (vec3d2 != null) {
               this.cc = vec3d2.subtract(vec3d1);
            }
         }
      }
   }

   @EventLink
   public void onAttackEntity(EventAttackEntity eventAttackEntity1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         if (eventAttackEntity1.getPlayer() == minecraftClient.player) {
            this.aT.c();
            this.cu = true;
            if (eventAttackEntity1.getTarget() instanceof LivingEntity livingEntity3) {
               if (this.f(livingEntity3)) {
                  this.bg = livingEntity3;
               }
            }
         }
      }
   }

   @EventLink
   public void onMoveInput(EventMoveInput eventMoveInput1) {
      if (Velocity.c.p()) {
         this.a(eventMoveInput1);
      } else {
         if (this.bg != null && this.E.k.d && this.E.d(this.E.k)) {
            eventMoveInput1.setForward(0.0F);
            eventMoveInput1.setStrafe(0.0F);
         }

         if (this.ad() && this.bh) {
            eventMoveInput1.setForward(0.0F);
            eventMoveInput1.setStrafe(0.0F);
            this.bh = false;
            this.bi = true;
            this.bj = 0;
         }

         if (this.bk > 0) {
            eventMoveInput1.setForward(0.0F);
            eventMoveInput1.setStrafe(0.0F);
         }

         this.a(eventMoveInput1);
      }
   }

   @EventLink(
      priority = -200
   )
   public void onMoveInputLegit2(EventMoveInput eventMoveInput1) {
      if (this.bl && !Velocity.c.p()) {
         this.bl = false;
         if (eventMoveInput1.getForward() > 0.0F) {
            eventMoveInput1.setForward(0.0F);
         }
      }
   }

   private void a(EventMoveInput eventMoveInput1) {
      if (minecraftClient.player != null && this.bg != null && !this.e.b("NoRotate")) {
         if (this.be.b("Таргетированная")) {
            if (minecraftClient.player.isGliding()) {
               eventMoveInput1.setForward(0.0F);
               eventMoveInput1.setStrafe(0.0F);
            } else if (eventMoveInput1.getForward() != 0.0F || eventMoveInput1.getStrafe() != 0.0F) {
               if (this.T()) {
                  this.a(eventMoveInput1, this.bg);
               } else {
                  this.a(eventMoveInput1, this.V(), this.U());
               }
            }
         } else if (this.be.b("Сфокусированная")) {
            float floatValue2 = this.V();
            this.a(eventMoveInput1, floatValue2, this.U());
         } else if (this.be.b("Свободная")) {
            dev.leg1tka.api.utils.player.MovementGeometry.a(eventMoveInput1);
         }
      }
   }

   private boolean T() {
      return minecraftClient.options.forwardKey.isPressed() && !minecraftClient.options.backKey.isPressed() && !minecraftClient.options.leftKey.isPressed() && !minecraftClient.options.rightKey.isPressed();
   }

   @EventLink
   public void onKeyboardInput(EventKeyboardInput eventKeyboardInput1) {
      if (minecraftClient.player != null && minecraftClient.world != null && this.bg != null && !this.e.b("NoRotate")) {
         if (this.be.b("Сфокусированная")
            || this.be.b("Таргетированная") && !this.T() && !minecraftClient.player.isGliding()) {
            eventKeyboardInput1.setYaw(this.V(), this.U());
         }
      }
   }

   private float U() {
      return this.N() && dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.b() : minecraftClient.player.getYaw();
   }

   private float V() {
      return dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.k() != null ? dev.leg1tka.rotation.RotationStorage.c.k().getYaw() : minecraftClient.player.getYaw();
   }

   private float W() {
      return dev.leg1tka.api.utils.player.RotationRaycast.b(this.bg.getBoundingBox().getCenter()).x;
   }

   private void a(EventMoveInput eventMoveInput1, float floatValue2, float floatValue3) {
      float floatValue4 = eventMoveInput1.getForward();
      float floatValue5 = eventMoveInput1.getStrafe();
      if (floatValue4 != 0.0F || floatValue5 != 0.0F) {
         double doubleValue6 = MathHelper.wrapDegrees(Math.toDegrees(dev.leg1tka.api.utils.player.MovementGeometry.a(floatValue3, floatValue4, floatValue5)));
         float floatValue8 = 0.0F;
         float floatValue9 = 0.0F;
         float floatValue10 = Float.MAX_VALUE;

         for (float floatValue11 = -1.0F; floatValue11 <= 1.0F; floatValue11++) {
            for (float floatValue12 = -1.0F; floatValue12 <= 1.0F; floatValue12++) {
               double doubleValue15;
               if ((floatValue11 != 0.0F || floatValue12 != 0.0F)
                  && (doubleValue15 = Math.abs(doubleValue6 - MathHelper.wrapDegrees(Math.toDegrees(dev.leg1tka.api.utils.player.MovementGeometry.a(floatValue2, floatValue11, floatValue12))))) < (double)floatValue10) {
                  floatValue10 = (float)doubleValue15;
                  floatValue8 = floatValue11;
                  floatValue9 = floatValue12;
               }
            }
         }

         eventMoveInput1.setForward(floatValue8);
         eventMoveInput1.setStrafe(floatValue9);
      }
   }

   private void a(EventMoveInput eventMoveInput1, LivingEntity livingEntity2) {
      if (minecraftClient.player != null && livingEntity2 != null) {
         Vec3d vec3d5 = livingEntity2.getPos().add(0.0, (double)livingEntity2.getHeight() * 0.5, 0.0);
         double doubleValue6 = vec3d5.x - minecraftClient.player.getX();
         double doubleValue3;
         if (!(doubleValue6 * doubleValue6 + (doubleValue3 = vec3d5.z - minecraftClient.player.getZ()) * doubleValue3 < 1.0E-6)) {
            float floatValue8 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(doubleValue3, doubleValue6)) - 90.0);
            float floatValue9 = MathHelper.wrapDegrees(minecraftClient.player.getYaw());
            float floatValue10 = 0.0F;
            float floatValue11 = 0.0F;
            float floatValue12 = Float.MAX_VALUE;

            for (float floatValue13 = -1.0F; floatValue13 <= 1.0F; floatValue13++) {
               for (float floatValue14 = -1.0F; floatValue14 <= 1.0F; floatValue14++) {
                  float floatValue16;
                  if ((floatValue13 != 0.0F || floatValue14 != 0.0F)
                     && (floatValue16 = Math.abs(MathHelper.wrapDegrees(floatValue8 - (float)MathHelper.wrapDegrees(Math.toDegrees(this.a(floatValue9, floatValue13, floatValue14)))))) < floatValue12) {
                     floatValue12 = floatValue16;
                     floatValue10 = floatValue13;
                     floatValue11 = floatValue14;
                  }
               }
            }

            eventMoveInput1.setForward(floatValue10);
            eventMoveInput1.setStrafe(floatValue11);
         }
      }
   }

   private double a(float floatValue1, float floatValue2, float floatValue3) {
      if (floatValue2 < 0.0F) {
         floatValue1 += 180.0F;
      }

      float floatValue4 = 1.0F;
      if (floatValue2 < 0.0F) {
         floatValue4 = -0.5F;
      } else if (floatValue2 > 0.0F) {
         floatValue4 = 0.5F;
      }

      if (floatValue3 > 0.0F) {
         floatValue1 -= 90.0F * floatValue4;
      } else if (floatValue3 < 0.0F) {
         floatValue1 += 90.0F * floatValue4;
      }

      return Math.toRadians((double)floatValue1);
   }

   @EventLink
   private void onGameUpdate(EventGameUpdate eventGameUpdate1) {
      if (minecraftClient.player != null && minecraftClient.world != null && this.bg != null) {
         this.Y();
      }
   }

   @EventLink
   public void onTick(EventUpdate eventUpdate1) {
      if (minecraftClient.player == null || minecraftClient.world == null) return;
      this.X();
   }

   @EventLink
   public void onPost(EventUpdatePost eventUpdatePost1) {
      if (minecraftClient.player != null && minecraftClient.world != null) {
         boolean flag2 = dev.leg1tka.managers.ModuleManager.ah.isEnabled() && minecraftClient.player.hasStatusEffect(StatusEffects.SLOW_FALLING);
         if (flag2 && minecraftClient.player.fallDistance > 0.0F && minecraftClient.player.fallDistance < 1.0F) {
            this.X();
         }
      }
   }

   private void X() {
      if (this.bi) {
         this.bj++;
      }

      if (this.bk > 0) {
         this.bk--;
         if (this.bk == 0 && minecraftClient.player != null) {
            minecraftClient.player.setSprinting(true);
            if (minecraftClient.player.networkHandler != null) {
               minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.START_SPRINTING));
            }
         }
      }

      this.an();
      this.L();
      if (this.bg != null) {
         if (this.p()) {
            this.M();
         } else if (this.c("Легитный 2")) {
            this.ae();
         } else if (this.ax() && this.bM <= System.currentTimeMillis()) {
            if (this.bd.getValue() && this.au()) {
               return;
            }

            if (this.ad()) {
               if (minecraftClient.player.isSprinting() && !this.bi && !this.Z()) {
                  this.bh = true;
                  return;
               }

               if (this.bi && this.bj < 1) {
                  return;
               }
            } else {
               this.bh = false;
               this.bi = false;
               this.bj = 0;
            }

            this.ap();
            this.bi = false;
            this.bj = 0;
            this.bh = false;
            if (this.ab()) {
               this.bk = 2;
               if (minecraftClient.player != null) {
                  minecraftClient.player.setSprinting(false);
                  minecraftClient.options.sprintKey.setPressed(false);
                  if (minecraftClient.player.networkHandler != null) {
                     minecraftClient.player.networkHandler.sendPacket(new ClientCommandC2SPacket(minecraftClient.player, Mode.STOP_SPRINTING));
                  }
               }

               Sprint.b((int)dev.leg1tka.api.utils.math.MathOperations.a(60.0F, 100.0F));
            }
         }
      } else {
         this.E.b(null);
         this.E.b();
         this.bh = false;
         this.bi = false;
         this.bj = 0;
         this.bk = 0;
         this.resetSprintState();
         this.bM = System.currentTimeMillis();
         this.bK.reset();
         aQ = 0.0F;
         aP = 0.0F;
         this.bG.b();
         this.bH.b();
         dev.leg1tka.api.utils.security.ClientIntegrityState.e();
         this.bI.b();
         this.bJ.b();
         this.bP = null;
         this.bN = 0;
         this.bO = 0;
      }
   }

   public void r() {
      this.Y();
   }

   @EventLink
   public void onChangeSprint(EventChangeSprint eventChangeSprint1) {
      if (this.bh || this.bi) {
         eventChangeSprint1.setSprinting(false);
      }
   }

   public void s() {
      this.bO = 4;
   }

   private void Y() {
      if (this.bg != null) {
         Object object1;
         if (this.e.b("Matrix/Fast")) {
            object1 = new dev.leg1tka.rotation.RotationStrategy() {
               @Override
               public void a(LivingEntity livingEntity1) {
                  if (!AttackAura.minecraftClient.player.isGliding()) {
                     Vec3d vec3d2 = livingEntity1.getPos().add(0.0, (double)(livingEntity1.getHeight() * 0.6F), 0.0).subtract(AttackAura.minecraftClient.player.getEyePos());
                     float floatValue3 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d2.z, vec3d2.x)) - 90.0);
                     float floatValue4 = (float)(-Math.toDegrees(Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z))));
                     dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue3, floatValue4), 360.0F, 360.0F, 360.0F, 360.0F, 1, 1, AttackAura.I.getValue());
                  } else {
                     Vec3d vec3d8 = Vec3d.fromPolar(livingEntity1.getLerpTargetPitch(), livingEntity1.getLerpTargetYaw());
                     Vec3d vec3d9 = livingEntity1.getRotationVector();
                     Vec3d vec3d10 = livingEntity1.getPos().add(0.0, (double)(livingEntity1.getHeight() * 0.6F), 0.0).subtract(AttackAura.minecraftClient.player.getEyePos());
                     Vec3d vec3d5 = vec3d8.normalize().lerp(vec3d9, vec3d8.length());
                     if (AttackAura.minecraftClient.player.isGliding() && livingEntity1.isGliding() && dev.leg1tka.managers.ModuleManager.O.isEnabled()) {
                        vec3d10 = vec3d10.add(vec3d5.normalize().multiply((double)dev.leg1tka.managers.ModuleManager.O.e.e().floatValue()));
                     }

                     float floatValue6 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d10.z, vec3d10.x)) - 90.0);
                     float floatValue7 = (float)(-Math.toDegrees(Math.atan2(vec3d10.y, Math.hypot(vec3d10.x, vec3d10.z))));
                     dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue6, floatValue7), 360.0F, 360.0F, 360.0F, 360.0F, 1, 1, AttackAura.I.getValue());
                  }
               }
            };
         } else if (this.e.b("SC3")) {
            object1 = this.bG;
         } else if (this.N()) {
            object1 = this.bI;
         } else if (this.aw()) {
            if (dev.leg1tka.api.utils.security.ClientIntegrityState.a()) {
               object1 = this.bI;
            } else {
               object1 = new dev.leg1tka.rotation.RotationStrategy() {
                  @Override
                  public void a(LivingEntity livingEntity1) {
                     AttackAura.this.bH.a(livingEntity1);
                     AttackAura.this.bI.d(livingEntity1);
                  }
               };
            }
         } else {
            if (this.e.b("SpookyTime")) {
               return;
            }

            if (this.e.b("HolyWorld")) {
               object1 = this.holyWorld;
            } else if (this.e.b("WhiteRise")) {
               object1 = this.bJ;
            } else if (this.e.b("NoRotate")) {
               object1 = new dev.leg1tka.rotation.RotationStrategy() {
                  @Override
                  public void a(LivingEntity livingEntity1) {
                     if (AttackAura.minecraftClient.player.isGliding() && livingEntity1.isGliding()) {
                        Vec3d vec3d2;
                        if (dev.leg1tka.managers.ModuleManager.O != null && dev.leg1tka.managers.ModuleManager.O.isEnabled()) {
                           Vec3d vec3d3 = livingEntity1.getBoundingBox().getCenter();
                           Vec3d vec3d4 = new Vec3d(livingEntity1.getX() - livingEntity1.prevX, livingEntity1.getY() - livingEntity1.prevY, livingEntity1.getZ() - livingEntity1.prevZ);
                           int intValue5 = dev.leg1tka.managers.ModuleManager.O.e.e().intValue();
                           Vec3d vec3d6 = Vec3d.fromPolar(livingEntity1.getLerpTargetPitch(), livingEntity1.getLerpTargetYaw());
                           Vec3d vec3d7 = livingEntity1.getRotationVector();
                           Vec3d vec3d8 = vec3d6.normalize().lerp(vec3d7, 0.5);
                           Vec3d vec3d9 = vec3d8.normalize().multiply((double)intValue5 * 0.6).add(vec3d4.multiply((double)intValue5));
                           vec3d2 = vec3d3.add(vec3d9);
                           Vec3d vec3d10 = AttackAura.minecraftClient.player.getEyePos();
                           double doubleValue11 = vec3d10.distanceTo(vec3d2);
                           double doubleValue13 = Math.min(1.0, doubleValue11 / 10.0);
                           vec3d2 = vec3d3.lerp(vec3d2, doubleValue13);
                           vec3d2 = vec3d2.add(0.0, (double)(livingEntity1.getHeight() * 0.3F), 0.0);
                        } else {
                           vec3d2 = livingEntity1.getPos().add(0.0, (double)(livingEntity1.getHeight() * 0.6F), 0.0);
                        }

                        Vec3d vec3d17 = vec3d2.subtract(AttackAura.minecraftClient.player.getEyePos());
                        float floatValue18 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d17.z, vec3d17.x)) - 90.0);
                        float floatValue19 = (float)(-Math.toDegrees(Math.atan2(vec3d17.y, Math.hypot(vec3d17.x, vec3d17.z))));
                        dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue18, floatValue19), 180.0F, 180.0F, 120.0F, 120.0F, 1, 1, AttackAura.I.getValue());
                     } else {
                        dev.leg1tka.rotation.RotationStorage.a(
                           new dev.leg1tka.rotation.Rotation(dev.leg1tka.rotation.FreeLookStorage.b(), dev.leg1tka.rotation.FreeLookStorage.c()),
                           dev.leg1tka.api.utils.math.MathOperations.a(100.0F, 170.0F),
                           dev.leg1tka.api.utils.math.MathOperations.a(100.0F, 170.0F),
                           dev.leg1tka.api.utils.math.MathOperations.a(100.0F, 170.0F),
                           dev.leg1tka.api.utils.math.MathOperations.a(100.0F, 170.0F),
                           1,
                           6,
                           false
                        );
                     }
                  }
               };
            } else {
               if (this.e.b("Assist")) {
                  return;
               }

               if (this.e.b("Нейро")) {
                  return;
               }

               final Vec2f vec2f2 = dev.leg1tka.api.utils.player.RotationRaycast.b(this.a(this.bg, this.bg.getLeashPos(1.0F)));
               object1 = new dev.leg1tka.rotation.RotationStrategy() {
                  @Override
                  public void a(LivingEntity livingEntity1) {
                     AttackAura.this.aM = new Vec2f(AttackAura.minecraftClient.player.getYaw(), AttackAura.minecraftClient.player.getPitch());
                     dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(vec2f2.x, vec2f2.y), 360.0F, 360.0F, 360.0F, 360.0F, 1, 1, AttackAura.I.getValue());
                  }
               };
            }
         }

         ((dev.leg1tka.rotation.RotationStrategy)object1).a(this.bg);
      }
   }

   private boolean Z() {
      return minecraftClient.player != null && (minecraftClient.player.isTouchingWater() || minecraftClient.player.isSubmergedInWater()) && Sprint.c != null && Sprint.c.r();
   }

   private boolean canPerformCriticalHit() {
      if (minecraftClient.player == null) {
         return false;
      } else {
         float floatValue1 = this.u();
         if (this.B.getValue()) {
            return dev.leg1tka.api.utils.player.PlayerEnvironment.a(this.bg, floatValue1);
         } else {
            boolean flag2 = AirStuck.c != null && AirStuck.c.q();
            return !minecraftClient.player.isOnGround() && (minecraftClient.player.getVelocity().y < 0.0 || flag2) && (minecraftClient.player.fallDistance >= floatValue1 || flag2);
         }
      }
   }

   private boolean ab() {
      return this.c("WTap");
   }

   private boolean ac() {
      return this.c("Пакетный");
   }

   private boolean ad() {
      if (this.c("Легитный")) {
         return true;
      } else {
         return !this.c("WellMine") ? false : minecraftClient.player != null && !minecraftClient.player.isOnGround() && this.canPerformCriticalHit();
      }
   }

   private void ae() {
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
      long longValue2 = System.currentTimeMillis();
      boolean flag4 = AspectDLC.INSTANCE.serverStorage.i();
      if (clientPlayerEntity1.isOnGround()) {
         this.br = false;
         this.bs = 0;
      }

      if (this.bm) {
         this.bm = false;
         this.bs = flag4 ? this.bs + 1 : 0;
         if (clientPlayerEntity1.age - this.bp <= 2 && !flag4 && this.a(longValue2)) {
            this.af();
            return;
         }

         if (this.bn) {
            this.br = true;
         }
      }

      boolean flag5 = flag4 && this.bs < 2;
      if (this.ax() && this.bM <= longValue2) {
         if (!this.bd.getValue() || !this.au()) {
            if (flag5 && this.ag()) {
               this.a(clientPlayerEntity1.getAttackCooldownProgress(1.5F), false);
            } else {
               this.af();
            }
         }
      } else {
         if (this.aZ.getValue() && flag5 && !this.br) {
            float floatValue6 = this.b(longValue2);
            if (floatValue6 >= 0.0F) {
               this.a(floatValue6, true);
            }
         }
      }
   }

   private void a(float floatValue1, boolean flag2) {
      this.bl = true;
      this.bm = true;
      this.bn = flag2;
      this.bo = this.ah();
      this.bp = minecraftClient.player.age;
      this.bq = floatValue1;
   }

   private void af() {
      if (minecraftClient.player.isSprinting() && !AspectDLC.INSTANCE.serverStorage.i()) {
         minecraftClient.player.setSprinting(false);
      }

      this.bh = false;
      this.bi = false;
      this.bj = 0;
      this.br = false;
      this.bs = 0;
      this.ap();
   }

   private boolean a(long longValue1) {
      ClientPlayerEntity clientPlayerEntity3 = minecraftClient.player;
      if (this.bM - longValue1 > 50L) {
         return false;
      } else if (this.bd.getValue() && this.au()) {
         return false;
      } else if (clientPlayerEntity3.getAttackCooldownProgress(1.5F) < this.bq) {
         return false;
      } else {
         return !this.bo ? this.a(false, true) : this.a(false, false) && this.ah() && clientPlayerEntity3.fallDistance > 0.0F;
      }
   }

   private boolean ag() {
      if (!this.aY.getValue()) {
         return true;
      } else {
         ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
         return !dev.leg1tka.managers.ModuleManager.ah.isEnabled() || !clientPlayerEntity1.hasStatusEffect(StatusEffects.SLOW_FALLING) && !dev.leg1tka.api.utils.player.PlayerEnvironment.f() ? this.ah() : true;
      }
   }

   private boolean ah() {
      ClientPlayerEntity clientPlayerEntity1 = minecraftClient.player;
      return !clientPlayerEntity1.isOnGround()
         && !clientPlayerEntity1.isClimbing()
         && !clientPlayerEntity1.isTouchingWater()
         && !clientPlayerEntity1.isInLava()
         && !clientPlayerEntity1.hasVehicle()
         && !clientPlayerEntity1.isGliding()
         && !clientPlayerEntity1.getAbilities().flying
         && !clientPlayerEntity1.hasStatusEffect(StatusEffects.BLINDNESS)
         && !clientPlayerEntity1.hasStatusEffect(StatusEffects.LEVITATION)
         && !clientPlayerEntity1.hasStatusEffect(StatusEffects.SLOW_FALLING);
   }

   private float b(long longValue1) {
      ClientPlayerEntity clientPlayerEntity3 = minecraftClient.player;
      if (this.bM - longValue1 > 45L) {
         return -1.0F;
      } else if (this.bd.getValue() && this.au()) {
         return -1.0F;
      } else if (this.ah() && !dev.leg1tka.api.utils.player.PlayerEnvironment.f()) {
         float floatValue4 = this.t();
         if (clientPlayerEntity3.getAttackCooldownProgress(2.5F) < floatValue4) {
            return -1.0F;
         } else {
            double doubleValue5 = clientPlayerEntity3.getVelocity().y;
            if ((doubleValue5 - clientPlayerEntity3.getFinalGravity()) * 0.98 >= 0.0) {
               return -1.0F;
            } else if (doubleValue5 < 0.0 && !minecraftClient.world.isSpaceEmpty(clientPlayerEntity3, clientPlayerEntity3.getBoundingBox().offset(0.0, doubleValue5, 0.0))) {
               return -1.0F;
            } else if (clientPlayerEntity3.fallDistance + (float)Math.max(0.0, -doubleValue5) < Math.max(this.u(), 1.0E-4F)) {
               return -1.0F;
            } else {
               return this.a(false, false) ? floatValue4 : -1.0F;
            }
         }
      } else {
         return -1.0F;
      }
   }

   private void resetSprintState() {
      this.bl = false;
      this.bm = false;
      this.br = false;
      this.bs = 0;
   }

   private float getAimYaw() {
      return dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.b() : (minecraftClient.player == null ? 0.0F : minecraftClient.player.getYaw());
   }

   private float getAimPitch() {
      return dev.leg1tka.rotation.FreeLookStorage.a() ? dev.leg1tka.rotation.FreeLookStorage.c() : (minecraftClient.player == null ? 0.0F : minecraftClient.player.getPitch());
   }

   private EntityHitResult al() {
      if (minecraftClient.player != null && this.bg != null) {
         float floatValue1;
         float floatValue2;
         if (this.e.b("Assist") || this.e.b("NoRotate")) {
            floatValue2 = this.getAimPitch();
            floatValue1 = this.getAimYaw();
         } else if (dev.leg1tka.rotation.RotationStorage.c != null && dev.leg1tka.rotation.RotationStorage.c.b() && dev.leg1tka.rotation.RotationStorage.c.k() != null) {
            floatValue2 = dev.leg1tka.rotation.RotationStorage.c.k().getPitch();
            floatValue1 = dev.leg1tka.rotation.RotationStorage.c.k().getYaw();
         } else {
            floatValue2 = minecraftClient.player.getPitch();
            floatValue1 = minecraftClient.player.getYaw();
         }

         Vec3d vec3d3 = minecraftClient.player.getEyePos();
         Vec3d vec3d4 = minecraftClient.player.getRotationVector(floatValue2, floatValue1);
         double doubleValue5 = (double)this.K();
         Vec3d vec3d7 = vec3d3.add(vec3d4.multiply(doubleValue5));
         Box box8 = BackTrackOld.c.a((Entity)this.bg);
         if (this.G.getValue()) {
            box8 = box8.expand((double)this.H.e().floatValue());
         }

         return !box8.contains(vec3d3) && !box8.raycast(vec3d3, vec3d7).isPresent() ? null : new EntityHitResult(this.bg);
      } else {
         return null;
      }
   }

   private boolean b(LivingEntity livingEntity1) {
      return livingEntity1 != null && minecraftClient.player != null && minecraftClient.world != null ? !minecraftClient.player.canSee(livingEntity1) : false;
   }

   private Vec3d a(LivingEntity livingEntity1, Vec3d vec3d2) {
      return minecraftClient.player != null && livingEntity1 != null && minecraftClient.player.isGliding() && livingEntity1.isGliding() && dev.leg1tka.managers.ModuleManager.O != null && dev.leg1tka.managers.ModuleManager.O.isEnabled()
         ? dev.leg1tka.api.utils.player.EntityFilters.a(livingEntity1)
         : vec3d2;
   }

   private Vec3d c(LivingEntity livingEntity1) {
      if (this.d(livingEntity1)) {
         return this.a(livingEntity1, this.e(livingEntity1));
      } else {
         Vec3d vec3d2 = dev.leg1tka.rotation.BacktrackAimGeometry.b(livingEntity1);
         if (vec3d2 == null) {
            vec3d2 = dev.leg1tka.api.utils.player.EntityBoxGeometry.a(livingEntity1);
         }

         if (vec3d2 == null) {
            vec3d2 = this.e(livingEntity1);
         }

         return this.a(livingEntity1, vec3d2);
      }
   }

   private boolean d(LivingEntity livingEntity1) {
      if (minecraftClient.player != null && livingEntity1 != null) {
         Vec3d vec3d2 = minecraftClient.player.getEyePos();
         Box box3 = livingEntity1.getBoundingBox();
         if (box3.expand(0.12).contains(vec3d2)) {
            return true;
         } else {
            Vec3d vec3d4 = this.e(livingEntity1);
            return vec3d2.squaredDistanceTo(vec3d4) <= 2.25;
         }
      } else {
         return false;
      }
   }

   private Vec3d e(LivingEntity livingEntity1) {
      Box box2 = livingEntity1.getBoundingBox();
      return new Vec3d(box2.getCenter().x, box2.minY + box2.getLengthY() * 0.72, box2.getCenter().z);
   }

   private LivingEntity am() {
      ArrayList arrayList1 = new ArrayList();

      for (Object object3 : minecraftClient.world.getEntities()) {
         LivingEntity livingEntity4;
         if (object3 instanceof LivingEntity && this.f(livingEntity4 = (LivingEntity)object3)) {
            if (this.w.f() < 360.0F && minecraftClient.player != null) {
               Vec2f vec2f5 = dev.leg1tka.api.utils.player.RotationRaycast.b(livingEntity4.getBoundingBox().getCenter());
               float floatValue6 = Math.abs(MathHelper.wrapDegrees(vec2f5.x - minecraftClient.player.getYaw()));
               if (floatValue6 > this.w.f() / 2.0F) {
                  continue;
               }
            }

            arrayList1.add(livingEntity4);
         }
      }

      if (!arrayList1.isEmpty() && this.isEnabled()) {
         Comparator comparator7 = null;
         String string8 = this.bf.g();
         switch (string8) {
            case "Дистанция":
               comparator7 = Comparator.comparingDouble(createToDoubleFunction84());
               break;
            case "Здоровье":
               comparator7 = Comparator.comparingDouble(createToDoubleFunction85());
               break;
            case "Угол":
               comparator7 = Comparator.comparingDouble(createToDoubleFunction86());
         }

         arrayList1.sort(createComparator87(comparator7));
         return arrayList1.isEmpty() ? null : (LivingEntity)arrayList1.get(0);
      } else {
         return null;
      }
   }

   private void an() {
      if (!this.isEnabled()) {
         this.bg = null;
      } else if (this.e.b("Assist")) {
         ArrayList arrayList1 = new ArrayList();
         double doubleValue2 = (double)this.az() + 3.0;

         for (Entity entity5 : minecraftClient.world.getEntities()) {
            if (entity5 instanceof LivingEntity) {
               LivingEntity livingEntity6 = (LivingEntity)entity5;
               if (livingEntity6.squaredDistanceTo(minecraftClient.player) <= doubleValue2 * doubleValue2 && this.f(livingEntity6)) {
                  arrayList1.add(livingEntity6);
               }
            }
         }

         this.bg = this.ca.a(arrayList1, this.bg, this.o.f());
      } else if (this.bg == null || !this.f(this.bg)) {
         this.bg = this.am();
      }
   }

   private boolean ao() {
      float floatValue1 = minecraftClient.player.getAttackCooldownProgress(1.5F);
      float floatValue2 = Math.max(0.82F, dev.leg1tka.api.utils.player.PlayerEnvironment.a() - 0.08F);
      boolean flag3 = floatValue1 >= floatValue2;
      boolean flag4 = AirStuck.c != null && AirStuck.c.q();
      boolean flag5 = !minecraftClient.player.isOnGround() && (minecraftClient.player.getVelocity().y < 0.0 || flag4) && (minecraftClient.player.fallDistance > 0.0F || flag4);
      return flag3 || flag5;
   }

   private void ap() {
      Hand hand2 = null;
      boolean flag3 = false;
      if (!this.bu.getValue() || this.aq()) {
         if (!this.p() && this.bb.getValue() && (hand2 = this.at()) != null) {
            flag3 = true;
            minecraftClient.interactionManager.stopUsingItem(minecraftClient.player);

            try {
               Thread.sleep(5L);
            } catch (InterruptedException interruptedException12) {
               Thread.currentThread().interrupt();
            }
         }

         if (this.bu.getValue()) {
            this.bz = dev.leg1tka.network.PacketDelayQueue.e();
            if (this.bA != 0L) {
               dev.leg1tka.network.PacketDelayQueue.b();
               this.bA = 0L;
               this.bE = this.bz;
            }
         }

         boolean flag4 = false;
         LivingEntity livingEntity5 = this.bg;
         PlayerEntity playerEntity1;
         if (livingEntity5 instanceof PlayerEntity && (playerEntity1 = (PlayerEntity)livingEntity5).isBlocking() && this.bc.getValue() && this.a(playerEntity1)) {
            try {
               Thread.sleep(50L);
            } catch (InterruptedException interruptedException11) {
               Thread.currentThread().interrupt();
            }
         }

         boolean flag6 = this.q() && AirStuck.c != null && AirStuck.c.q();
         boolean flag7 = minecraftClient.player.isSprinting();
         if (flag6 && flag7) {
            minecraftClient.player.setSprinting(false);
         }

         if (minecraftClient.player.getMainHandStack().getItem() == Items.MACE && this.aV.getValue()) {
            this.aW = 6;
         }

         if (!MaxDamage.c.a(this.bg)) {
            minecraftClient.interactionManager.attackEntity(minecraftClient.player, this.bg);
            minecraftClient.player.swingHand(Hand.MAIN_HAND);
         }

         if (flag6 && flag7) {
            minecraftClient.player.setSprinting(true);
         } else if (this.c("Пакетный") && flag7) {
            minecraftClient.player.setSprinting(true);
         }

         if (flag3 && hand2 != null) {
            try {
               Thread.sleep(5L);
            } catch (InterruptedException interruptedException10) {
               Thread.currentThread().interrupt();
            }

            this.a(hand2);
         }

         if (this.e.b("WhiteRise")) {
            this.bJ.c();
         }

         if (this.e.b("SC3")) {
            this.bG.c();
         }

         if (this.N()) {
            this.bI.c();
         }

         if (this.aw()) {
            if (dev.leg1tka.api.utils.security.ClientIntegrityState.a()) {
               this.bI.c();
            } else {
               this.bH.c();
               this.bI.c();
            }
         }

         long longValue8 = 467L + this.v();
         if (this.bL != null && this.bL.isEnabled()) {
            longValue8 = (long)((float)this.bL.a(longValue8) * 1.1F);
         }

         this.bM = System.currentTimeMillis() + longValue8;
         this.bN = 10;
         this.bt.reset();
      }
   }

   private boolean a(BlockState blockState1, VoxelShape voxelShape2, BlockPos blockPos3) {
      return Block.isShapeFullCube(voxelShape2) && Block.isShapeFullCube(blockState1.getCollisionShape(minecraftClient.world, blockPos3));
   }

   private boolean aq() {
      if (this.bg != null && minecraftClient.player != null && minecraftClient.world != null) {
         dev.leg1tka.managers.ServerStorage serverStorage1 = AspectDLC.INSTANCE.serverStorage;
         if (serverStorage1 == null) {
            return false;
         } else if (dev.leg1tka.network.PacketDelayQueue.f() > 0) {
            return false;
         } else {
            this.a(serverStorage1);
            Box box2 = BackTrackOld.c.a((Entity)this.bg);
            Vec3d vec3d3 = new Vec3d(serverStorage1.e(), serverStorage1.f() + (double)minecraftClient.player.getEyeHeight(minecraftClient.player.getPose()), serverStorage1.g());
            AttackAura.b value4 = this.a(vec3d3, dev.leg1tka.api.utils.player.EntityRaycast.b(serverStorage1.c(), serverStorage1.b()), box2);
            if (value4 == null) {
               AttackAura.b value10 = this.a(vec3d3, box2.getCenter().subtract(vec3d3).normalize(), box2);
               return value10 != null && !value10.blocked() && value10.hits().isEmpty();
            } else if (value4.blocked()) {
               return false;
            } else {
               boolean flag5 = this.bA != 0L && dev.leg1tka.network.PacketDelayQueue.d();
               int intValue6 = serverStorage1.n();

               for (AttackAura.a value8 : value4.hits()) {
                  long[] longValueArray9 = (long[])this.bw.get(value8.pos());
                  if (longValueArray9 == null) {
                     return false;
                  }

                  if ((!flag5 || longValueArray9[3] != this.bA && longValueArray9[2] >= 0L && longValueArray9[2] <= this.bC) && longValueArray9[0] <= (long)intValue6) {
                     return false;
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }

   private boolean ar() {
      if (System.currentTimeMillis() + 100L < this.bM) {
         return false;
      } else if (minecraftClient.player.getAttackCooldownProgress(2.5F) < this.t()) {
         return false;
      } else {
         return !minecraftClient.player.isOnGround() ? minecraftClient.player.getVelocity().y < 0.05 : !minecraftClient.options.jumpKey.isPressed();
      }
   }

   private void as() {
      if (this.bA != 0L) {
         dev.leg1tka.network.PacketDelayQueue.c();
         this.bA = 0L;
         this.bE = dev.leg1tka.network.PacketDelayQueue.e();
      }
   }

   @EventLink(
      priority = -1000
   )
   public void onLocalWallSync(EventPlayerSync eventPlayerSync1) {
      if (!this.bu.getValue() || this.bg == null || minecraftClient.player == null || minecraftClient.world == null) {
         this.as();
      } else if (minecraftClient.interactionManager != null && minecraftClient.player.networkHandler != null) {
         dev.leg1tka.managers.ServerStorage serverStorage2 = AspectDLC.INSTANCE.serverStorage;
         if (serverStorage2 != null) {
            this.a(serverStorage2);
            long longValue3 = dev.leg1tka.network.PacketDelayQueue.e();
            if (dev.leg1tka.network.PacketDelayQueue.g() <= 0 && this.bz != longValue3) {
               if (System.currentTimeMillis() + 150L >= this.bM) {
                  boolean flag5 = this.bA == 0L && longValue3 >= this.bF && this.ar();
                  AttackAura.b value6 = this.a(minecraftClient.player.getEyePos(), dev.leg1tka.api.utils.player.EntityRaycast.b(minecraftClient.player.getPitch(), minecraftClient.player.getYaw()), BackTrackOld.c.a((Entity)this.bg));
                  if (value6 != null && !value6.blocked() && !value6.hits().isEmpty()) {
                     BlockPos blockPos7 = serverStorage2.m();
                     if (blockPos7 != null && serverStorage2.o()) {
                        BlockState blockState8 = minecraftClient.world.getBlockState(blockPos7);
                        if (!blockState8.isAir() && blockState8.calcBlockBreakingDelta(minecraftClient.player, minecraftClient.world, blockPos7) < 1.0F) {
                           return;
                        }
                     }

                     if (flag5) {
                        for (AttackAura.a value9 : value6.hits()) {
                           long[] longValueArray10 = (long[])this.bw.get(value9.pos());
                           if (longValueArray10 != null && longValueArray10[2] >= 0L && !this.a(longValueArray10, longValue3)) {
                              flag5 = false;
                              break;
                           }
                        }

                        if (flag5 && dev.leg1tka.network.PacketDelayQueue.a()) {
                           this.bA = ++this.bB;
                           this.bC = longValue3;
                           this.bD = 0;
                        }
                     }

                     for (AttackAura.a value14 : value6.hits()) {
                        long[] longValueArray15 = (long[])this.bw.get(value14.pos());
                        if ((this.bA == 0L || longValueArray15 == null || longValueArray15[3] != this.bA) && (longValueArray15 == null || this.a(longValueArray15, longValue3))) {
                           if (this.bA != 0L && this.bD >= 4) {
                              return;
                           }

                           if (!value14.pos().equals(serverStorage2.l())) {
                              minecraftClient.player.networkHandler.sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, value14.pos(), Direction.DOWN));
                           }

                           int[] intValueArray11 = new int[1];
                           minecraftClient.interactionManager.sendSequencedPacket(minecraftClient.world, createSequencedPacketCreator88(intValueArray11, value14));
                           this.bw.put(value14.pos(), new long[]{(long)intValueArray11[0], System.currentTimeMillis(), -1L, this.bA});
                           if (this.bA != 0L) {
                              this.bD++;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private boolean a(long[] longValueArray1, long longValue2) {
      return longValueArray1[2] >= 0L && longValue2 - longValueArray1[2] >= 2L && longValue2 > this.bE;
   }

   private void a(dev.leg1tka.managers.ServerStorage serverStorage1) {
      int intValue2 = serverStorage1.n();
      long longValue3 = dev.leg1tka.network.PacketDelayQueue.e();
      if (this.bx != minecraftClient.world || intValue2 < this.by) {
         this.as();
         this.bw.clear();
         this.bx = minecraftClient.world;
      }

      this.by = intValue2;
      if (this.bA != 0L && !dev.leg1tka.network.PacketDelayQueue.d()) {
         this.bA = 0L;
         this.bE = longValue3;
         this.bF = longValue3 + 4L;
      }

      long longValue5 = System.currentTimeMillis();
      long longValue7 = this.bA;
      this.bw.values().removeIf(createPredicate89(intValue2, longValue3, longValue7, longValue5));
   }

   private AttackAura.b a(Vec3d vec3d1, Vec3d vec3d2, Box box3) {
      if (box3.contains(vec3d1)) {
         return new AttackAura.b(List.of(), false);
      } else {
         Optional optional4 = box3.raycast(vec3d1, vec3d1.add(vec3d2.multiply((double)this.K())));
         if (optional4.isEmpty()) {
            return null;
         } else {
            Vec3d vec3d5 = (Vec3d)optional4.get();
            ArrayList arrayList6 = new ArrayList();
            boolean[] flagArray7 = new boolean[]{false};
            boolean flag8 = this.bv.getValue();
            BlockView.raycast(vec3d1, vec3d5, null, createBiFunction90(this, vec3d1, vec3d5, flag8, flagArray7, arrayList6), createFunction91());
            return new AttackAura.b(arrayList6, flagArray7[0]);
         }
      }
   }

   private boolean a(PlayerEntity playerEntity1) {
      dev.leg1tka.api.utils.player.FindItemResult findItemResult2 = dev.leg1tka.api.utils.player.ItemNames.a();
      if (!findItemResult2.h()) {
         return false;
      } else {
         int intValue3 = minecraftClient.player.getInventory().selectedSlot;
         if (findItemResult2.g() == intValue3) {
            try {
               Thread.sleep(8L);
            } catch (InterruptedException interruptedException11) {
               Thread.currentThread().interrupt();
            }

            boolean flag14 = minecraftClient.player.isSprinting();
            if (minecraftClient.player.getMainHandStack().getItem() == Items.MACE && this.aV.getValue()) {
               this.aW = 6;
            }

            minecraftClient.interactionManager.attackEntity(minecraftClient.player, playerEntity1);
            if (this.c("Пакетный") && flag14) {
               minecraftClient.player.setSprinting(true);
            }

            return true;
         } else if (findItemResult2.d()) {
            return this.a(playerEntity1, findItemResult2.g(), intValue3);
         } else if (minecraftClient.player.currentScreenHandler.syncId != 0) {
            return false;
         } else {
            int intValue4 = this.b(intValue3);
            if (intValue4 == -1) {
               return false;
            } else {
               this.a(findItemResult2.g(), intValue4);
               boolean flag5 = false;

               try {
                  try {
                     Thread.sleep(10L);
                  } catch (InterruptedException interruptedException12) {
                     Thread.currentThread().interrupt();
                  }

                  flag5 = this.a(playerEntity1, intValue4, intValue3);
               } finally {
                  this.a(findItemResult2.g(), intValue4);
               }

               return flag5;
            }
         }
      }
   }

   private boolean a(PlayerEntity playerEntity1, int intValue2, int intValue3) {
      if (minecraftClient.player != null && minecraftClient.player.networkHandler != null && minecraftClient.interactionManager != null) {
         minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue2));

         try {
            Thread.sleep(12L);
         } catch (InterruptedException interruptedException16) {
            Thread.currentThread().interrupt();
         }

         boolean flag7;
         try {
            boolean flag5 = minecraftClient.player.isSprinting();
            if (minecraftClient.player.getMainHandStack().getItem() == Items.MACE && this.aV.getValue()) {
               this.aW = 6;
            }

            minecraftClient.interactionManager.attackEntity(minecraftClient.player, playerEntity1);
            if (this.c("Пакетный") && flag5) {
               minecraftClient.player.setSprinting(true);
            }

            boolean flag4 = true;
            boolean flag6 = true;
            flag7 = flag6;
         } finally {
            try {
               Thread.sleep(8L);
            } catch (InterruptedException interruptedException15) {
               Thread.currentThread().interrupt();
            }

            minecraftClient.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(intValue3));
         }

         return flag7;
      } else {
         return false;
      }
   }

   private void a(int intValue1, int intValue2) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null && minecraftClient.player.networkHandler != null) {
         int intValue3 = minecraftClient.player.currentScreenHandler.syncId;
         minecraftClient.interactionManager.clickSlot(intValue3, intValue1, intValue2, SlotActionType.SWAP, minecraftClient.player);
         minecraftClient.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(intValue3));
      }
   }

   private int b(int intValue1) {
      for (int intValue2 = 8; intValue2 >= 0; intValue2--) {
         if (intValue2 != intValue1) {
            return intValue2;
         }
      }

      return intValue1 >= 0 && intValue1 < 9 ? intValue1 : -1;
   }

   private Hand at() {
      if (minecraftClient.player != null && minecraftClient.player.isBlocking()) {
         Hand hand1 = minecraftClient.player.getActiveHand();
         if (hand1 == null) {
            return null;
         } else {
            return this.a(minecraftClient.player.getStackInHand(hand1)) ? hand1 : null;
         }
      } else {
         return null;
      }
   }

   private boolean au() {
      return minecraftClient.player != null && minecraftClient.player.isUsingItem() ? !this.bb.getValue() || this.at() == null : false;
   }

   private void a(Hand hand1) {
      if (minecraftClient.player != null && minecraftClient.interactionManager != null && hand1 != null) {
         if (this.a(minecraftClient.player.getStackInHand(hand1)) && !minecraftClient.player.isUsingItem()) {
            minecraftClient.interactionManager.interactItem(minecraftClient.player, hand1);
         }
      }
   }

   private boolean a(ItemStack itemStack1) {
      return !itemStack1.isEmpty() && itemStack1.getItem() instanceof ShieldItem;
   }

   private boolean av() {
      Item item1 = minecraftClient.player.getMainHandStack().getItem();
      return item1 != Items.AIR
         && (
            item1 instanceof SwordItem
               || item1 instanceof PickaxeItem
               || item1 instanceof AxeItem
               || item1 instanceof HoeItem
               || item1 instanceof ShovelItem
               || item1 instanceof MaceItem
               || item1 == Items.MACE
         );
   }

   private boolean f(LivingEntity livingEntity1) {
      return this.a(livingEntity1, this.az());
   }

   private boolean a(LivingEntity livingEntity1, float floatValue2) {
      if (livingEntity1 != null && livingEntity1 != minecraftClient.player) {
         if (!livingEntity1.isAlive() || livingEntity1.getHealth() <= 0.0F) {
            return false;
         } else if (livingEntity1 instanceof ArmorStandEntity) {
            return false;
         } else if (!(livingEntity1 instanceof IronGolemEntity) && !(livingEntity1 instanceof BatEntity)) {
            if (livingEntity1 instanceof PlayerEntity playerEntity4) {
               if (!this.aX.a("Игроки")) {
                  return false;
               }

               if (this.b(playerEntity4) && !this.aX.a("Голые")) {
                  return false;
               }

               if (playerEntity4.hasStatusEffect(StatusEffects.INVISIBILITY) && !this.aX.a("Невидимки")) {
                  return false;
               }

               if (AspectDLC.INSTANCE.friendStorage.c(livingEntity1.getName().getString())) {
                  return false;
               }
            } else if (!(livingEntity1 instanceof PassiveEntity) && !(livingEntity1 instanceof CodEntity)) {
               if (!(livingEntity1 instanceof HostileEntity)) {
                  return false;
               }

               if (!this.aX.a("Мобы")) {
                  return false;
               }
            } else if (!this.aX.a("Мирные")) {
               return false;
            }

            Vec3d vec3d3;
            if ((vec3d3 = dev.leg1tka.rotation.BacktrackAimGeometry.b(livingEntity1)) == null) {
               vec3d3 = dev.leg1tka.api.utils.player.EntityBoxGeometry.a(livingEntity1);
            }

            return minecraftClient.player.getEyePos().distanceTo(vec3d3) > (double)floatValue2 ? false : this.B() || minecraftClient.player.canSee(livingEntity1);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean b(PlayerEntity playerEntity1) {
      for (ItemStack itemStack3 : playerEntity1.getArmorItems()) {
         if (!itemStack3.isEmpty()) {
            return false;
         }
      }

      return true;
   }

   private boolean aw() {
      return this.e.b("Shard") && this.f.b("Snap-1.0");
   }

   public float t() {
      if (this.aw()) {
         return this.bH.d();
      } else if (!this.q() || this.C.b("Быстрый")) {
         return dev.leg1tka.api.utils.player.PlayerEnvironment.a();
      } else {
         return this.C.b("Рандомный") ? 0.92F + this.ck.nextFloat() * 0.07F : dev.leg1tka.api.utils.player.PlayerEnvironment.a();
      }
   }

   public float u() {
      if (this.aw()) {
         return this.bH.e();
      } else if (!this.q() || this.C.b("Быстрый")) {
         return 0.01F;
      } else {
         return this.C.b("Рандомный") ? 0.03F + this.ck.nextFloat() * 0.18F : 0.01F;
      }
   }

   public long v() {
      if (this.aw()) {
         return this.bH.f();
      } else if (!this.q() || this.C.b("Быстрый")) {
         return 0L;
      } else {
         return this.C.b("Рандомный") ? (long)this.ck.nextInt(35) : 0L;
      }
   }

   private boolean ax() {
      return this.a(true, true);
   }

   private boolean a(boolean flag1, boolean flag2) {
      if (this.aO > 0) {
         return false;
      } else if (MaceHelper.c.p()) {
         return false;
      } else {
         boolean flag4 = this.aV.getValue() && minecraftClient.player.getMainHandStack().getItem() == Items.MACE;
         if (!flag1 || !(minecraftClient.player.getAttackCooldownProgress(1.5F) < this.t()) || flag4 && this.aW <= 0) {
            if (flag4 && this.aW > 0) {
               return false;
            } else {
               EntityHitResult entityHitResult3;
               if (!this.ba.getValue() || minecraftClient.player.isGliding() && this.bg.isGliding() || (entityHitResult3 = this.al()) != null && entityHitResult3.getEntity() == this.bg) {
                  if (!this.ay()) {
                     return false;
                  } else if (flag2 && this.q()) {
                     float floatValue5 = this.u();
                     return dev.leg1tka.api.utils.player.PlayerEnvironment.a(this.bg, floatValue5);
                  } else {
                     return true;
                  }
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      }
   }

   private boolean ay() {
      if (minecraftClient.player.isGliding() && this.bg.isGliding()) {
         Vec3d vec3d12 = this.bg.getPos().add(0.0, (double)this.bg.getHeight() / 2.0, 0.0);
         int intValue13 = 0;
         if (dev.leg1tka.managers.ModuleManager.O != null && dev.leg1tka.managers.ModuleManager.O.isEnabled()) {
            intValue13 = dev.leg1tka.managers.ModuleManager.O.e.e().intValue();
         }

         Vec3d vec3d15 = dev.leg1tka.api.utils.player.EntityFilters.a(this.bg, vec3d12, intValue13);
         if (minecraftClient.player.getEyePos().distanceTo(vec3d15) > 5.0) {
            return false;
         }
      } else {
         if (BackTrackOld.c.p()) {
            Vec3d vec3d11 = minecraftClient.player.getEyePos();
            Box box2 = BackTrackOld.c.a((Entity)this.bg);
            double doubleValue14 = vec3d11.x - MathHelper.clamp(vec3d11.x, box2.minX, box2.maxX);
            double doubleValue16 = vec3d11.y - MathHelper.clamp(vec3d11.y, box2.minY, box2.maxY);
            double doubleValue7 = vec3d11.z - MathHelper.clamp(vec3d11.z, box2.minZ, box2.maxZ);
            double doubleValue9 = (double)this.K();
            return doubleValue14 * doubleValue14 + doubleValue16 * doubleValue16 + doubleValue7 * doubleValue7 <= doubleValue9 * doubleValue9;
         }

         double doubleValue1 = minecraftClient.player.getEyePos().distanceTo(this.bg.getBoundingBox().getCenter());
         Vec3d vec3d3 = doubleValue1 > 3.0 ? dev.leg1tka.rotation.BacktrackAimGeometry.b(this.bg) : this.bg.getBoundingBox().getCenter();
         if (vec3d3 == null) {
            vec3d3 = dev.leg1tka.api.utils.player.EntityBoxGeometry.a(this.bg);
         }

         double doubleValue5 = (double)this.t.e().floatValue();
         if (minecraftClient.player.getEyePos().distanceTo(vec3d3) > doubleValue5) {
            return false;
         }
      }

      return true;
   }

   public boolean w() {
      return minecraftClient.player == null ? false : minecraftClient.player.getAttackCooldownProgress(1.5F) >= this.t();
   }

   public int x() {
      return this.bN;
   }

   public boolean y() {
      BlockPos blockPos1 = BlockPos.ofFloored(minecraftClient.player.getPos().add(0.0, -0.4, 0.0));
      return !minecraftClient.player.isSubmergedInWater() && minecraftClient.world.getBlockState(blockPos1).isOf(Blocks.WATER);
   }

   public float z() {
      return MathHelper.clamp((float)((ILivingEntity)minecraftClient.player).getLastAttackedTicks() / this.C(), 0.0F, 1.0F);
   }

   public boolean A() {
      return this.ba.getValue();
   }

   public boolean B() {
      return this.bu.getValue();
   }

   public float C() {
      return (float)(1.0 / minecraftClient.player.getAttributeValue(EntityAttributes.ATTACK_SPEED) * 20.0);
   }

   public float D() {
      return minecraftClient.player.isGliding() ? this.v.e().floatValue() : this.t.e().floatValue() + this.u.e().floatValue();
   }

   private float az() {
      return !minecraftClient.player.isGliding() && !this.N() && BackTrackOld.c.q() ? BackTrackOld.c.r() : this.D();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.as();
      if (this.cw && this.N() || this.F.a() && this.e.b("SpookyTime")) {
         this.O();
      }

      this.cw = false;
      if (this.bg != null) {
         this.bK.reset();
      }

      this.bg = null;
      this.bG.b();
      this.bH.b();
      dev.leg1tka.api.utils.security.ClientIntegrityState.e();
      this.bI.b();
      this.bJ.b();
      this.bP = null;
      this.bN = 0;
      this.bh = false;
      this.bi = false;
      this.bj = 0;
      this.bk = 0;
      this.resetSprintState();
      this.bT = false;
      this.bU = 0L;
      this.bY = 0L;
      this.bS = new float[]{0.0F, 0.0F};
      this.ca.a();
      this.cd = false;
      this.aT.a();
      this.ct = 0;
      this.cs = null;
      this.E.h();
      this.F.c();
      BackTrackOld.c.s();
      dev.leg1tka.rotation.RotationSensitivity.c.a(false, false);
      dev.leg1tka.rotation.RotationSensitivity.c.e();
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.bG.b();
      this.bI.b();
      this.bJ.b();
      this.bP = null;
      this.bN = 0;
      this.E.h();
      BackTrackOld.c.s();
      if (minecraftClient.player != null) {
         this.aM = new Vec2f(minecraftClient.player.getYaw(), minecraftClient.player.getPitch());
         this.bS = new float[]{minecraftClient.player.getYaw(), minecraftClient.player.getPitch()};
      }

      dev.leg1tka.rotation.RotationSensitivity.c.a(false, false);
      dev.leg1tka.rotation.RotationSensitivity.c.e();
   }

   private void g(LivingEntity livingEntity1) {
      if (minecraftClient.player != null) {
         if (minecraftClient.player.isGliding()) {
            Vec3d vec3d23 = livingEntity1.getPos().add(0.0, (double)(livingEntity1.getHeight() * 0.6F), 0.0).subtract(minecraftClient.player.getEyePos());
            if (livingEntity1.isGliding() && dev.leg1tka.managers.ModuleManager.O.isEnabled()) {
               Vec3d vec3d24 = Vec3d.fromPolar(livingEntity1.getLerpTargetPitch(), livingEntity1.getLerpTargetYaw());
               Vec3d vec3d26 = livingEntity1.getRotationVector();
               Vec3d vec3d28 = vec3d24.normalize().lerp(vec3d26, vec3d24.length());
               vec3d23 = vec3d23.add(vec3d28.normalize().multiply((double)dev.leg1tka.managers.ModuleManager.O.e.e().floatValue()));
            }

            float floatValue25 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d23.z, vec3d23.x)) - 90.0);
            float floatValue27 = (float)(-Math.toDegrees(Math.atan2(vec3d23.y, Math.hypot(vec3d23.x, vec3d23.z))));
            dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue25, floatValue27), 360.0F, 360.0F, 360.0F, 360.0F, 1, 1, I.getValue());
         } else {
            if (this.bS[0] == 0.0F && this.bS[1] == 0.0F) {
               this.bS[0] = minecraftClient.player.getYaw();
               this.bS[1] = minecraftClient.player.getPitch();
            }

            Vec3d vec3d3 = new Vec3d(livingEntity1.getX(), livingEntity1.getY() + (double)(livingEntity1.getHeight() * 0.8F), livingEntity1.getZ());
            Vec3d vec3d4 = minecraftClient.player.getEyePos();
            Vec3d vec3d5 = vec3d3.subtract(vec3d4).normalize();
            float floatValue6 = (float)Math.toDegrees(Math.atan2(-vec3d5.x, vec3d5.z));
            float floatValue7 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d5.y, Math.hypot(vec3d5.x, vec3d5.z))), -89.0, 89.0);
            long longValue8 = System.currentTimeMillis();
            Random random10 = new Random();
            boolean flag11 = minecraftClient.player.getAttackCooldownProgress(1.5F) >= 0.92F;
            if (!flag11) {
               if (!this.bT && longValue8 - this.bU >= 2000L) {
                  this.bT = true;
                  this.bV = longValue8;
                  float floatValue13 = 180.0F + (random10.nextFloat() * 40.0F - 20.0F);
                  this.bW = floatValue6 + floatValue13 * (float)(random10.nextBoolean() ? 1 : -1);
                  float floatValue14 = 45.0F + (random10.nextFloat() * 40.0F - 20.0F);
                  this.bX = MathHelper.clamp(floatValue7 + floatValue14 * (float)(random10.nextBoolean() ? 1 : -1), -89.0F, 89.0F);
               }

               if (this.bT) {
                  long longValue29 = longValue8 - this.bV;
                  if (longValue29 < 80L) {
                     float floatValue15 = (float)longValue29 / 80.0F;
                     float floatValue16 = 1.0F - (float)Math.pow((double)(1.0F - floatValue15), 3.0);
                     floatValue6 += (this.bW - floatValue6) * floatValue16;
                     floatValue7 += (this.bX - floatValue7) * floatValue16;
                  } else if (longValue29 < 160L) {
                     long longValue32 = longValue29 - 80L;
                     float floatValue17 = (float)longValue32 / 80.0F;
                     float floatValue18 = (float)Math.pow((double)floatValue17, 3.0);
                     Vec3d vec3d19 = new Vec3d(livingEntity1.getX(), livingEntity1.getY() + (double)(livingEntity1.getHeight() * 0.8F), livingEntity1.getZ());
                     Vec3d vec3d20 = vec3d19.subtract(vec3d4).normalize();
                     float floatValue21 = (float)Math.toDegrees(Math.atan2(-vec3d20.x, vec3d20.z));
                     float floatValue22 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d20.y, Math.hypot(vec3d20.x, vec3d20.z))), -89.0, 89.0);
                     floatValue6 = this.bW + (floatValue21 - this.bW) * floatValue18;
                     floatValue7 = this.bX + (floatValue22 - this.bX) * floatValue18;
                  } else {
                     this.bT = false;
                     this.bU = longValue8;
                  }
               }
            } else if (this.bT) {
               this.bT = false;
               this.bU = longValue8 - 2000L + 500L;
            }

            float floatValue30 = MathHelper.wrapDegrees(floatValue6 - this.bS[0]);
            float floatValue31 = MathHelper.wrapDegrees(floatValue7 - this.bS[1]);
            float floatValue33 = this.bT ? 3.0F : 1.0F;
            float floatValue34 = ((Double)minecraftClient.options.getMouseSensitivity().getValue()).floatValue() * 0.6F + 0.2F;
            floatValue34 = floatValue34 * floatValue34 * floatValue34 * 1.2F;
            float floatValue36 = 8.5F * floatValue33;
            float floatValue37 = Math.min(Math.abs(floatValue30), floatValue36);
            float floatValue2;
            if (longValue8 - this.bY >= 290L) {
               floatValue2 = Math.min(Math.abs(floatValue31), 8.0F);
               this.bY = longValue8;
            } else {
               floatValue2 = Math.min(Math.abs(floatValue31), 3.0F);
            }

            float floatValue38 = this.bS[0] + (floatValue30 > 0.0F ? floatValue37 : -floatValue37);
            float floatValue40 = this.bS[1] + (floatValue31 > 0.0F ? floatValue2 : -floatValue2);
            floatValue40 = MathHelper.clamp(floatValue40, -89.0F, 89.0F);
            floatValue38 -= (floatValue38 - this.bS[0]) % floatValue34;
            floatValue40 -= (floatValue40 - this.bS[1]) % floatValue34;
            this.bS[0] = floatValue38;
            this.bS[1] = floatValue40;
            float floatValue43 = this.bT ? 8.0F : 3.0F;
            this.aN = new Vec2f(floatValue38, floatValue40);
            this.aM = new Vec2f(minecraftClient.player.getYaw(), minecraftClient.player.getPitch());
            dev.leg1tka.rotation.RotationStorage.a(new dev.leg1tka.rotation.Rotation(floatValue38, floatValue40), floatValue43 * 12.0F, floatValue43 * 10.0F, floatValue43 * 4.0F, floatValue43 * 4.0F, 1, 5, I.getValue());
         }
      }
   }

   @Generated
   public LivingEntity E() {
      return this.bg;
   }

   @Generated
   public Vec2f F() {
      return this.aM;
   }

   @Generated
   public Vec2f G() {
      return this.aN;
   }

   public float H() {
      return this.aN.x;
   }

   public float I() {
      return this.aN.y;
   }

   public dev.leg1tka.api.utils.time.Stopwatch J() {
      return this.bt;
   }



   private static Supplier createSupplier1(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Shard");
   }

   private static Supplier createSupplier2(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier3(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier4(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier5(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier6(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier7(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Нейро");
   }

   private static Supplier createSupplier8(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Нейро");
   }

   private static Supplier createSupplier9(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Нейро");
   }

   private static Supplier createSupplier10(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier11(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier12(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier13(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier14(AttackAura attackAura0) {
      return () -> attackAura0.e.b("Assist");
   }

   private static Supplier createSupplier15(AttackAura attackAura0) {
      return () -> !attackAura0.N();
   }

   private static Supplier createSupplier16(AttackAura attackAura0) {
      return () -> attackAura0.D.b("Легитный 2");
   }

   private static Supplier createSupplier17(AttackAura attackAura0) {
      return () -> attackAura0.D.b("Легитный 2");
   }

   private static Supplier createSupplier18(AttackAura attackAura0) {
      return () -> attackAura0.ba.getValue();
   }

   private static Supplier createSupplier19(AttackAura attackAura0) {
      return () -> attackAura0.ba.getValue() && attackAura0.G.getValue();
   }

   private static Supplier createSupplier20(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier21(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier22(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Постоянная");
   }

   private static Supplier createSupplier23(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Постоянная");
   }

   private static Supplier createSupplier24(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier25(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Ускорение");
   }

   private static Supplier createSupplier26(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Ускорение");
   }

   private static Supplier createSupplier27(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Ускорение");
   }

   private static Supplier createSupplier28(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Ускорение");
   }

   private static Supplier createSupplier29(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.J.b("Ускорение");
   }

   private static Supplier createSupplier30(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier31(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier32(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier33(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier34(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier35(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.X.getValue();
   }

   private static Supplier createSupplier36(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.X.getValue();
   }

   private static Supplier createSupplier37(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier38(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aa.getValue();
   }

   private static Supplier createSupplier39(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier40(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier41(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier42(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier43(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier44(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier45(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier46(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.Storage.getValue();
   }

   private static Supplier createSupplier47(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.Storage.getValue();
   }

   private static Supplier createSupplier48(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier49(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier50(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier51(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.an.getValue();
   }

   private static Supplier createSupplier52(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier53(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ap.getValue();
   }

   private static Supplier createSupplier54(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier55(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier56(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.as.getValue();
   }

   private static Supplier createSupplier57(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.as.getValue();
   }

   private static Supplier createSupplier58(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.as.getValue();
   }

   private static Supplier createSupplier59(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.as.getValue();
   }

   private static Supplier createSupplier60(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.as.getValue();
   }

   private static Supplier createSupplier61(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier62(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ay.getValue();
   }

   private static Supplier createSupplier63(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ay.getValue();
   }

   private static Supplier createSupplier64(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ay.getValue();
   }

   private static Supplier createSupplier65(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ay.getValue();
   }

   private static Supplier createSupplier66(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.ay.getValue();
   }

   private static Supplier createSupplier67(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier68(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aE.getValue();
   }

   private static Supplier createSupplier69(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aE.getValue();
   }

   private static Supplier createSupplier70(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aE.getValue();
   }

   private static Supplier createSupplier71(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aE.getValue();
   }

   private static Supplier createSupplier72(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3");
   }

   private static Supplier createSupplier73(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aJ.getValue();
   }

   private static Supplier createSupplier74(AttackAura attackAura0) {
      return () -> attackAura0.e.b("SC3") && attackAura0.aJ.getValue();
   }

   private static Supplier createSupplier75(AttackAura attackAura0) {
      return () -> attackAura0.bu.getValue();
   }

   private static BooleanSupplier createBooleanSupplier76(AttackAura attackAura0) {
      return attackAura0::N;
   }

   private static dev.leg1tka.rotation.NeuralRotationController.a createValue77(AttackAura attackAura0) {
      return (floatValue1, floatValue2) -> attackAura0.a(attackAura0.cl, floatValue1, floatValue2);
   }

   private static Runnable createRunnable78(AttackAura attackAura0) {
      return attackAura0::ap;
   }

   private static String createString79(String string0) {
      return ": " + string0;
   }

   private static String createString80(String string0) {
      return "[Shard-2.0] Модель не загружена" + string0;
   }

   private static String createString81(String string0) {
      return ": " + string0;
   }

   private static String createString82(String string0) {
      return "[Neuro] Модель не загружена" + string0 + " — .neuro help";
   }

   private static String createString83(String string0) {
      return "[Neuro] Ошибка: " + string0;
   }

   private static ToDoubleFunction<LivingEntity> createToDoubleFunction84() {
      return (LivingEntity livingEntity0) -> livingEntity0.getBoundingBox().getCenter().squaredDistanceTo(minecraftClient.player.getEyePos());
   }

   private static ToDoubleFunction<LivingEntity> createToDoubleFunction85() {
      return (LivingEntity livingEntity0) -> (double)livingEntity0.getHealth();
   }

   private static ToDoubleFunction<LivingEntity> createToDoubleFunction86() {
      return (LivingEntity livingEntity0) -> {
         Vec2f vec2f1 = dev.leg1tka.api.utils.player.RotationRaycast.b(livingEntity0.getBoundingBox().getCenter());
         double doubleValue2 = (double)Math.abs(MathHelper.wrapDegrees(vec2f1.x - minecraftClient.player.getYaw()));
         double doubleValue4 = (double)Math.abs(MathHelper.wrapDegrees(vec2f1.y - minecraftClient.player.getPitch()));
         return doubleValue2 + doubleValue4;
      };
   }

   private static Comparator<LivingEntity> createComparator87(Comparator<LivingEntity> comparator0) {
      return (LivingEntity livingEntity1, LivingEntity livingEntity2) -> {
         if (minecraftClient.player != null && minecraftClient.player.isGliding()) {
            if (livingEntity1.isGliding() && !livingEntity2.isGliding()) {
               return -1;
            }

            if (!livingEntity1.isGliding() && livingEntity2.isGliding()) {
               return 1;
            }
         }

         return comparator0 != null ? comparator0.compare(livingEntity1, livingEntity2) : 0;
      };
   }

   private static SequencedPacketCreator createSequencedPacketCreator88(int[] intValueArray0, AttackAura.a value1) {
      return intValue2 -> {
         intValueArray0[0] = intValue2;
         return new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, value1.pos(), value1.face(), intValue2);
      };
   }

   private static Predicate<long[]> createPredicate89(int intValue0, long longValue1, long longValue3, long longValue5) {
      return (long[] longValueArray7) -> {
         if (longValueArray7[2] < 0L && longValueArray7[0] <= (long)intValue0) {
            longValueArray7[2] = longValue1;
         }

         if (longValueArray7[3] == longValue3 && longValue3 != 0L) {
            return false;
         } else {
            return longValueArray7[2] < 0L ? longValue5 - longValueArray7[1] > 30000L : longValue1 - longValueArray7[2] > 40L;
         }
      };
   }

   private static BiFunction<Object, BlockPos, Object> createBiFunction90(AttackAura attackAura0, Vec3d vec3d1, Vec3d vec3d2, boolean flag3, boolean[] flagArray4, List list5) {
      return (Object object6, BlockPos blockPos7) -> {
         BlockState blockState8 = minecraftClient.world.getBlockState(blockPos7);
         if (blockState8.isAir()) {
            return null;
         } else {
            VoxelShape voxelShape9 = blockState8.getOutlineShape(minecraftClient.world, blockPos7);
            if (voxelShape9.isEmpty()) {
               return null;
            } else {
               BlockHitResult blockHitResult10 = voxelShape9.raycast(vec3d1, vec3d2, blockPos7);
               if (blockHitResult10 == null) {
                  return null;
               } else if (!(blockState8.getHardness(minecraftClient.world, blockPos7) < 0.0F) && (!flag3 || attackAura0.a(blockState8, voxelShape9, blockPos7))) {
                  list5.add(new AttackAura.a(blockPos7.toImmutable(), blockHitResult10.getSide()));
                  return null;
               } else {
                  flagArray4[0] = true;
                  return Boolean.TRUE;
               }
            }
         }
      };
   }

   private static Function createFunction91() {
      return object0 -> Boolean.FALSE;
   }

   static record a(BlockPos pos, Direction face)  {

}

   static record b(List<AttackAura.a> hits, boolean blocked)  {

}
   public static void dev_leg1tka_o_gk_initializer() {}
}
