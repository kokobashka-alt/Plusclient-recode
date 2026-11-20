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

import java.util.HashMap;
import java.util.Map;

public class LocalizationStorage {
   private final Map a = new HashMap();
   private final Map b = new HashMap();
   private dev.leg1tka.managers.LocalizationStorage.Language c = dev.leg1tka.managers.LocalizationStorage.Language.RUSSIAN;

   public LocalizationStorage() {
      this.a("Combat", "Combat", "Бій");
      this.a("Movement", "Movement", "Рух");
      this.a("Render", "Render", "Візуал");
      this.a("Misc", "Misc", "Різне");
      this.a("Player", "Player", "Гравець");
      this.a("Язык", "Language", "Мова");
      this.a("Русский", "Russian", "Російська");
      this.a("Английский", "English", "Англійська");
      this.a("Украинский", "Ukrainian", "Українська");
      this.a("Sprint", "Sprint", "Спринт");
      this.a("AutoTotem", "Auto Totem", "Авто тотем");
      this.a("FakePlayer", "Fake Player", "Фейковий гравець");
      this.a("PacketMace", "Packet Mace", "Пакетна булава");
      this.a("Interface", "Interface", "Інтерфейс");
      this.a("GuiMove", "Gui Move", "Хода з інвентарем");
      this.a("Aura", "Aura", "Аура");
      this.a("ElytraBoost", "Elytra Boost", "Елітра буст");
      this.a("ElytraTarget", "Elytra Target", "Таргет елітри");
      this.a("FullBright", "Full Bright", "Повна яскравість");
      this.a("Chams", "Chams", "Чамси");
      this.a("ClientSounds", "Client Sounds", "Звуки клієнта");
      this.a("Cosmetics", "Cosmetics", "Косметика");
      this.a("ServerHelper", "Server Helper", "Сервер Хелпер");
      this.a("HPFixer", "HP Fixer", "Фікс HP");
      this.a("NoClip", "No Clip", "Без кліпу");
      this.a("Particles", "Particles", "Частинки");
      this.a("ElytraMotion", "Elytra Motion", "Рух елітри");
      this.a("RPSpoofer", "RP Spoofer", "RP спуфер");
      this.a("Projectile", "Projectile", "Снаряд");
      this.a("PacketCriticals", "Packet Criticals", "Пакетні кріти");
      this.a("EntityESP", "Entity ESP", "ESP сутностей");
      this.a("NoPush", "No Push", "Без штовхання");
      this.a("TPLoot", "TP Loot", "ТП лут");
      this.a("CutDetector", "Cut Detector", "Детектор кату");
      this.a("AirStuck", "Air Stuck", "Зависання в повітрі");
      this.a("NoWeb", "No Web", "Без павутини");
      this.a("NoRender", "No Render", "Без рендера");
      this.a("SwingAnimations", "Swing Animations", "Анімації свінгу");
      this.a("ViewModel", "View Model", "Модель рук");
      this.a("TargetESP", "Target ESP", "ESP цілі");
      this.a("CustomWorld", "Custom World", "Світ");
      this.a("Atmosphere", "Atmosphere", "Атмосфера");
      this.a("BlockOverlay", "Block Overlay", "Оверлей блоку");
      this.a("ShaderHands", "Shader Hands", "Шейдер рук");
      this.a("Режим", "Mode", "Режим");
      this.a("Мод", "Mode", "Режим");
      this.a("Стиль", "Style", "Стиль");
      this.a("Скорость", "Speed", "Швидкість");
      this.a(
         "Скорость анимации",
         "Animation Speed",
         "Швидкість анімації"
      );
      this.a(
         "Скорость вращения",
         "Rotation Speed",
         "Швидкість обертання"
      );
      this.a(
         "Скорость волн",
         "Wave Speed",
         "Швидкість хвиль"
      );
      this.a(
         "Скорость нитей",
         "Thread Speed",
         "Швидкість ниток"
      );
      this.a("Дистанция", "Distance", "Дистанція");
      this.a("Размер", "Size", "Розмір");
      this.a(
         "Прозрачность", "Opacity", "Прозорість"
      );
      this.a("Свечение", "Glow", "Світіння");
      this.a(
         "Сила свечения",
         "Glow Strength",
         "Сила світіння"
      );
      this.a(
         "Сила анимации",
         "Animation Strength",
         "Сила анімації"
      );
      this.a("Плавность", "Smoothness", "Плавність");
      this.a("Анимация", "Animation", "Анімація");
      this.a(
         "Анимация крыльев",
         "Wing Animation",
         "Анімація крил"
      );
      this.a(
         "Анимация свинга",
         "Swing Animation",
         "Анімація свінгу"
      );
      this.a(
         "Плавная анимация",
         "Smooth Animation",
         "Плавна анімація"
      );
      this.a("Тип частиц", "Particle Type", "Тип частинок");
      this.a("Количество", "Count", "Кількість");
      this.a("Приоритет", "Priority", "Пріоритет");
      this.a("Ротация", "Rotation", "Ротація");
      this.a("Обход", "Bypass", "Обхід");
      this.a("Сервер", "Server", "Сервер");
      this.a("После лута", "After Loot", "Після луту");
      this.a("Элементы", "Elements", "Елементи");
      this.a("Ватермарка", "Watermark", "Ватермарка");
      this.a(
         "Аррай лист",
         "Array List",
         "Список модулів"
      );
      this.a(
         "Горячие клавиши",
         "Key Binds",
         "Гарячі клавіші"
      );
      this.a("Зелья", "Potions", "Зілля");
      this.a("Таргет худ", "Target HUD", "Таргет HUD");
      this.a("Таргет худ2", "Target HUD 2", "Таргет HUD 2");
      this.a(
         "Уведомления", "Notifications", "Сповіщення"
      );
      this.a("Стафф", "Staff", "Стаф");
      this.a("Сессия", "Session", "Сесія");
      this.a("КейСтроки", "Key Strokes", "Кейстроки");
      this.a("Информация", "Information", "Інформація");
      this.a("Обычный", "Default", "Звичайний");
      this.a("Красивый", "Fancy", "Гарний");
      this.a("Шейдер", "Shader", "Шейдер");
      this.a("Нитки", "Threads", "Нитки");
      this.a("Разлет", "Scatter", "Розліт");
      this.a("Падение", "Fall", "Падіння");
      this.a(
         "Возвращаться",
         "Return",
         "Повертатися"
      );
      this.a(
         "Тепаться на спавн",
         "Teleport to Spawn",
         "Телепортуватись на спавн"
      );
      this.a("Картинка 1", "Image 1", "Картинка 1");
      this.a("Ромб", "Image 2", "Ромб");
      this.a("Призраки", "Ghosts", "Привиди");
      this.a("Райдер", "Rider", "Райдер");
      this.a("Души", "Souls", "Души");
      this.a("Кристаллы", "Crystals", "Кристали");
      this.a("Коллизия", "Collision", "Колізія");
      this.a("Тест", "Test", "Тест");
      this.a(
         "Дистанция атаки",
         "Attack Range",
         "Дистанція атаки"
      );
      this.a(
         "Только движение",
         "Only Movement",
         "Тільки рух"
      );
      this.a("Только при Aura", "Only with Aura", "Тільки з Aura");
      this.a(
         "Только с аурой",
         "Only with Aura",
         "Тільки з аурою"
      );
      this.a("Правая рука X", "Right Hand X", "Права рука X");
      this.a("Правая рука Y", "Right Hand Y", "Права рука Y");
      this.a("Правая рука Z", "Right Hand Z", "Права рука Z");
      this.a("Левая рука X", "Left Hand X", "Ліва рука X");
      this.a("Левая рука Y", "Left Hand Y", "Ліва рука Y");
      this.a("Левая рука Z", "Left Hand Z", "Ліва рука Z");
      this.a("Авто-взлёт", "Auto Takeoff", "Авто зліт");
      this.a("Обходить Grim", "Bypass Grim", "Обходити Grim");
      this.a("Крылья", "Wings", "Крила");
      this.a("Крылья 2", "Wings 2", "Крила 2");
      this.a("2D предметы", "2D Items", "2D предмети");
      this.a(
         "Размер предметов",
         "Item Size",
         "Розмір предметів"
      );
      this.a("Optimizator", "Optimizator", "Оптимізатор");
      this.a(
         "Быстрая загрузка",
         "Fast Load",
         "Швидке завантаження"
      );
      this.a("ShaderCrystal", "Shader Crystal", "Шейдер кристалів");
      this.a("Эффекты", "Effects", "Ефекти");
      this.a("Виньетка", "Vignette", "Віньєтка");
      this.a(
         "Модель обхода",
         "Bypass Model",
         "Модель обходу"
      );
      this.a(
         "Игроки с броней",
         "Armored Players",
         "Гравці в броні"
      );
   }

   private void a(String string1, String string2, String string3) {
      this.a.put(string1, string2);
      this.b.put(string1, string3);
   }

   public dev.leg1tka.managers.LocalizationStorage.Language a() {
      return this.c;
   }

   public void a(dev.leg1tka.managers.LocalizationStorage.Language language1) {
      this.c = language1 == null ? dev.leg1tka.managers.LocalizationStorage.Language.RUSSIAN : language1;
   }

   public void b() {
      this.c = this.c.b();
   }

   public String a(Category category1) {
      return this.a(category1.b());
   }

   public String a(String string1) {
      if (string1 != null && !string1.isEmpty()) {
         return switch (this.c) {
            case RUSSIAN -> string1;
            case ENGLISH -> (String)this.a.getOrDefault(string1, this.b(string1));
            case UKRAINIAN -> (String)this.b.getOrDefault(string1, this.c(string1));
         };
      } else {
         return string1;
      }
   }

   private String b(String string1) {
      return string1.chars().allMatch(intValue0 -> intValue0 < 128) ? this.d(string1) : string1;
   }

   private String c(String string1) {
      if (this.b.containsKey(string1)) {
         return (String)this.b.get(string1);
      } else {
         return string1.chars().allMatch(intValue0 -> intValue0 < 128) ? this.d(string1) : string1;
      }
   }

   private String d(String string1) {
      if (string1.indexOf(32) >= 0) {
         return string1;
      } else {
         String string2 = string1.replaceAll("([a-z])([A-Z])", "$1 $2");
         string2 = string2.replaceAll("([A-Z]+)([A-Z][a-z])", "$1 $2");
         return string2.trim();
      }
   }

   public static enum Language {
      RUSSIAN("Русский"),
      ENGLISH("English"),
      UKRAINIAN("Українська");

      private final String a;

      private Language(String string3) {
         this.a = string3;
      }

      public String a() {
         return this.a;
      }

      public dev.leg1tka.managers.LocalizationStorage.Language b() {
         dev.leg1tka.managers.LocalizationStorage.Language[] languageArray1 = values();
         return languageArray1[(this.ordinal() + 1) % languageArray1.length];
      }
   }
}
