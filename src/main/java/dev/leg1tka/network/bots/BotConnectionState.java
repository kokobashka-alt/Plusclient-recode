package dev.leg1tka.network.bots;

import dev.leg1tka.modules.*;
import dev.leg1tka.modules.combat.*;
import dev.leg1tka.modules.movement.*;
import dev.leg1tka.modules.render.*;
import dev.leg1tka.modules.player.*;
import dev.leg1tka.modules.misc.*;
import dev.leg1tka.settings.*;
import dev.leg1tka.managers.*;
import dev.leg1tka.gui.*;

import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Pair;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.type.MapIdComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.BlockAttachedEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.WardenEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerPosition;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapDecoration;
import net.minecraft.item.map.MapState;
import net.minecraft.item.map.MapState.UpdateData;
import net.minecraft.network.NetworkState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkRenderDistanceCenterS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkSentS2CPacket;
import net.minecraft.network.packet.s2c.play.CommonPlayerSpawnInfo;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardScoreUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SimulationDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.StartChunkSendS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateSelectedSlotS2CPacket;
import net.minecraft.network.packet.s2c.play.UpdateTickRateS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderInitializeS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;

public final class BotConnectionState {
   private static final MinecraftClient a = MinecraftClient.getInstance();
   private final NetworkState b;
   private final List c = new ArrayList();

   private BotConnectionState(NetworkState<?> networkState1) {
      this.b = networkState1;
   }

   public static List<ByteBuf> a(NetworkState<?> networkState0, int intValue1, int intValue2, float floatValue3, float floatValue4) {
      dev.leg1tka.network.bots.BotConnectionState value5 = new dev.leg1tka.network.bots.BotConnectionState(networkState0);
      value5.a(intValue1, intValue2, floatValue3, floatValue4);
      return value5.c;
   }

   private void a(Packet<?> packet1) {
      ByteBuf byteBuf2 = dev.leg1tka.network.ProxySessionBridge.a(this.b, packet1);
      if (byteBuf2 != null) {
         this.c.add(byteBuf2);
      }
   }

   private void a(int intValue1, int intValue2, float floatValue3, float floatValue4) {
      ClientPlayerEntity clientPlayerEntity5 = a.player;
      ClientWorld clientWorld6 = a.world;
      ClientPlayNetworkHandler clientPlayNetworkHandler7 = a.getNetworkHandler();
      GameJoinS2CPacket gameJoinS2CPacket8 = dev.leg1tka.network.PacketRegistry.d;
      CommonPlayerSpawnInfo commonPlayerSpawnInfo9 = dev.leg1tka.network.PacketRegistry.e;
      GameMode gameMode10 = a.interactionManager != null ? a.interactionManager.getCurrentGameMode() : GameMode.SURVIVAL;
      CommonPlayerSpawnInfo commonPlayerSpawnInfo11 = new CommonPlayerSpawnInfo(
         clientWorld6.getDimensionEntry(),
         clientWorld6.getRegistryKey(),
         commonPlayerSpawnInfo9 != null ? commonPlayerSpawnInfo9.seed() : 0L,
         GameMode.SPECTATOR,
         gameMode10,
         commonPlayerSpawnInfo9 != null && commonPlayerSpawnInfo9.isDebug(),
         commonPlayerSpawnInfo9 != null && commonPlayerSpawnInfo9.isFlat(),
         clientPlayerEntity5.getLastDeathPos(),
         clientPlayerEntity5.getPortalCooldown(),
         clientWorld6.getSeaLevel()
      );
      int intValue12 = dev.leg1tka.network.PacketRegistry.f;
      int intValue13 = dev.leg1tka.network.PacketRegistry.g;
      this.a(
         new GameJoinS2CPacket(
            clientPlayerEntity5.getId(),
            gameJoinS2CPacket8 != null && gameJoinS2CPacket8.hardcore(),
            gameJoinS2CPacket8 != null ? gameJoinS2CPacket8.dimensionIds() : Set.of(clientWorld6.getRegistryKey()),
            gameJoinS2CPacket8 != null ? gameJoinS2CPacket8.maxPlayers() : 100,
            intValue12,
            intValue13,
            gameJoinS2CPacket8 != null && gameJoinS2CPacket8.reducedDebugInfo(),
            gameJoinS2CPacket8 == null || gameJoinS2CPacket8.showDeathScreen(),
            gameJoinS2CPacket8 != null && gameJoinS2CPacket8.doLimitedCrafting(),
            commonPlayerSpawnInfo11,
            gameJoinS2CPacket8 != null && gameJoinS2CPacket8.enforcesSecureChat()
         )
      );
      this.a(new DifficultyS2CPacket(clientWorld6.getDifficulty(), clientWorld6.getLevelProperties().isDifficultyLocked()));
      this.a(dev.leg1tka.network.ProxySessionBridge.j());
      this.a(new UpdateSelectedSlotS2CPacket(intValue2 >= 0 ? intValue2 : clientPlayerEntity5.getInventory().selectedSlot));
      if (dev.leg1tka.network.PacketRegistry.h != null) {
         this.a(dev.leg1tka.network.PacketRegistry.h);
      }

      this.a(new EntityStatusS2CPacket(clientPlayerEntity5, (byte)(24 + Math.max(0, Math.min(4, clientPlayerEntity5.getPermissionLevel())))));
      if (dev.leg1tka.network.PacketRegistry.i != null) {
         this.a(dev.leg1tka.network.PacketRegistry.i);
      }

      Set set14 = this.a(clientPlayNetworkHandler7, clientPlayerEntity5);

      for (ResourcePackSendS2CPacket resourcePackSendS2CPacket16 : dev.leg1tka.network.PacketRegistry.c()) {
         this.a(resourcePackSendS2CPacket16);
      }

      this.a(new WorldBorderInitializeS2CPacket(clientWorld6.getWorldBorder()));
      WorldTimeUpdateS2CPacket worldTimeUpdateS2CPacket17 = dev.leg1tka.network.PacketRegistry.j;
      this.a(worldTimeUpdateS2CPacket17 != null ? worldTimeUpdateS2CPacket17 : new WorldTimeUpdateS2CPacket(clientWorld6.getTime(), clientWorld6.getTimeOfDay(), true));
      this.a(new PlayerSpawnPositionS2CPacket(clientWorld6.getLevelProperties().getSpawnPos(), clientWorld6.getLevelProperties().getSpawnAngle()));
      if (dev.leg1tka.network.PacketRegistry.k != null) {
         this.a(dev.leg1tka.network.PacketRegistry.k);
      }

      if (dev.leg1tka.network.PacketRegistry.l != null) {
         this.a(dev.leg1tka.network.PacketRegistry.l);
      }

      if (dev.leg1tka.network.PacketRegistry.m != null) {
         this.a(dev.leg1tka.network.PacketRegistry.m);
      }

      this.a(UpdateTickRateS2CPacket.create(clientWorld6.getTickManager()));
      this.a(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.INITIAL_CHUNKS_COMING, 0.0F));
      this.a(new ChunkLoadDistanceS2CPacket(intValue12));
      this.a(new SimulationDistanceS2CPacket(intValue13));
      this.a(new ChunkRenderDistanceCenterS2CPacket(clientPlayerEntity5.getChunkPos().x, clientPlayerEntity5.getChunkPos().z));
      this.a(new HealthUpdateS2CPacket(clientPlayerEntity5.getHealth(), clientPlayerEntity5.getHungerManager().getFoodLevel(), clientPlayerEntity5.getHungerManager().getSaturationLevel()));
      this.a(new ExperienceBarUpdateS2CPacket(clientPlayerEntity5.experienceProgress, clientPlayerEntity5.experienceLevel, clientPlayerEntity5.totalExperience));
      this.a(
         new InventoryS2CPacket(
            clientPlayerEntity5.playerScreenHandler.syncId,
            clientPlayerEntity5.playerScreenHandler.getRevision(),
            clientPlayerEntity5.playerScreenHandler.getStacks(),
            clientPlayerEntity5.playerScreenHandler.getCursorStack()
         )
      );
      this.a((Entity)clientPlayerEntity5);
      this.a((LivingEntity)clientPlayerEntity5);
      this.a(clientPlayerEntity5);
      this.a(new PlayerPositionLookS2CPacket(intValue1, new PlayerPosition(clientPlayerEntity5.getPos(), Vec3d.ZERO, floatValue3, floatValue4), Collections.emptySet()));
      this.a(clientWorld6, clientPlayerEntity5, intValue12);
      List list18 = this.a(clientWorld6, clientPlayerEntity5, set14);
      if (!list18.isEmpty()) {
         this.a(new PlayerRemoveS2CPacket(list18));
      }

      this.a(clientWorld6, clientPlayerEntity5);
      this.a(clientWorld6);
      this.a();
      this.b(clientWorld6);
   }

   private Set<UUID> a(ClientPlayNetworkHandler clientPlayNetworkHandler1, ClientPlayerEntity clientPlayerEntity2) {
      HashSet hashSet3 = new HashSet();
      ArrayList arrayList4 = new ArrayList();
      HashSet hashSet5 = new HashSet();

      for (PlayerListEntry playerListEntry7 : clientPlayNetworkHandler1.getListedPlayerListEntries()) {
         hashSet5.add(playerListEntry7.getProfile().getId());
      }

      for (PlayerListEntry playerListEntry14 : clientPlayNetworkHandler1.getPlayerList()) {
         GameProfile gameProfile8 = playerListEntry14.getProfile();
         if (gameProfile8 != null) {
            UUID uUID9 = gameProfile8.getId();
            boolean flag10 = uUID9.equals(clientPlayerEntity2.getUuid());
            GameMode gameMode11 = flag10 ? GameMode.SPECTATOR : (playerListEntry14.getGameMode() != null ? playerListEntry14.getGameMode() : GameMode.SURVIVAL);
            arrayList4.add(
               new Entry(uUID9, gameProfile8, hashSet5.contains(uUID9), playerListEntry14.getLatency(), gameMode11, playerListEntry14.getDisplayName(), playerListEntry14.shouldShowHat(), playerListEntry14.getListOrder(), null)
            );
            hashSet3.add(uUID9);
         }
      }

      if (!hashSet3.contains(clientPlayerEntity2.getUuid())) {
         arrayList4.add(new Entry(clientPlayerEntity2.getUuid(), clientPlayerEntity2.getGameProfile(), true, 0, GameMode.SPECTATOR, null, true, 0, null));
         hashSet3.add(clientPlayerEntity2.getUuid());
      }

      Packet packet13 = dev.leg1tka.network.ProxySessionBridge.a(EnumSet.complementOf(EnumSet.of(Action.INITIALIZE_CHAT)), arrayList4);
      if (packet13 != null) {
         this.a(packet13);
      }

      return hashSet3;
   }

   private void a(Entity entity1) {
      net.minecraft.entity.data.DataTracker.Entry[] entryArray2 = entity1.getDataTracker().entries;
      if (entryArray2 != null) {
         ArrayList arrayList3 = new ArrayList(entryArray2.length);

         for (net.minecraft.entity.data.DataTracker.Entry entry7 : entryArray2) {
            if (entry7 != null) {
               arrayList3.add(entry7.toSerialized());
            }
         }

         if (!arrayList3.isEmpty()) {
            this.a(new EntityTrackerUpdateS2CPacket(entity1.getId(), arrayList3));
         }
      }
   }

   private void a(LivingEntity livingEntity1) {
      Collection collection2 = livingEntity1.getAttributes().getAttributesToSend();
      if (!collection2.isEmpty()) {
         this.a(new EntityAttributesS2CPacket(livingEntity1.getId(), collection2));
      }

      for (StatusEffectInstance statusEffectInstance4 : livingEntity1.getStatusEffects()) {
         this.a(new EntityStatusEffectS2CPacket(livingEntity1.getId(), statusEffectInstance4, false));
      }
   }

   private void a(ClientPlayerEntity clientPlayerEntity1) {
      ItemCooldownManager itemCooldownManager2 = clientPlayerEntity1.getItemCooldownManager();

      for (java.util.Map.Entry entry4 : itemCooldownManager2.entries.entrySet()) {
         int intValue5 = ((net.minecraft.entity.player.ItemCooldownManager.Entry)entry4.getValue()).endTick() - itemCooldownManager2.tick;
         if (intValue5 > 0) {
            this.a(new CooldownUpdateS2CPacket((Identifier)entry4.getKey(), intValue5));
         }
      }
   }

   private void a(ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2, int intValue3) {
      LightingProvider lightingProvider4 = clientWorld1.getChunkManager().getLightingProvider();
      int intValue5 = Math.max(2, intValue3) + 3;
      int intValue6 = clientPlayerEntity2.getChunkPos().x;
      int intValue7 = clientPlayerEntity2.getChunkPos().z;
      ArrayList<WorldChunk> arrayList8 = new ArrayList<>();

      for (int intValue9 = -intValue5; intValue9 <= intValue5; intValue9++) {
         for (int intValue10 = -intValue5; intValue10 <= intValue5; intValue10++) {
            WorldChunk worldChunk11 = clientWorld1.getChunkManager().getChunk(intValue6 + intValue9, intValue7 + intValue10, ChunkStatus.FULL, false);
            if (worldChunk11 != null) {
               arrayList8.add(worldChunk11);
            }
         }
      }

      arrayList8.sort((worldChunk2x, worldChunk3x) -> Integer.compare(a(worldChunk2x, intValue6, intValue7), a(worldChunk3x, intValue6, intValue7)));
      this.a(StartChunkSendS2CPacket.INSTANCE);

      for (WorldChunk worldChunk13 : arrayList8) {
         this.a(new ChunkDataS2CPacket(worldChunk13, lightingProvider4, null, null));
      }

      this.a(new ChunkSentS2CPacket(arrayList8.size()));
   }

   private static int a(WorldChunk worldChunk0, int intValue1, int intValue2) {
      int intValue3 = worldChunk0.getPos().x - intValue1;
      int intValue4 = worldChunk0.getPos().z - intValue2;
      return intValue3 * intValue3 + intValue4 * intValue4;
   }

   private List<UUID> a(ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2, Set<UUID> set3) {
      ArrayList arrayList4 = new ArrayList();
      ArrayList arrayList5 = new ArrayList();

      for (Entity entity7 : clientWorld1.getEntities()) {
         if (entity7 instanceof AbstractClientPlayerEntity) {
            AbstractClientPlayerEntity abstractClientPlayerEntity8 = (AbstractClientPlayerEntity)entity7;
            if (abstractClientPlayerEntity8 != clientPlayerEntity2 && entity7.getId() >= 0 && !entity7.isRemoved() && !set3.contains(abstractClientPlayerEntity8.getUuid())) {
               arrayList5.add(new Entry(abstractClientPlayerEntity8.getUuid(), abstractClientPlayerEntity8.getGameProfile(), false, 0, GameMode.SURVIVAL, null, true, 0, null));
               arrayList4.add(abstractClientPlayerEntity8.getUuid());
            }
         }
      }

      if (!arrayList5.isEmpty()) {
         Packet packet15 = dev.leg1tka.network.ProxySessionBridge.a(EnumSet.of(Action.ADD_PLAYER, Action.UPDATE_GAME_MODE), arrayList5);
         if (packet15 != null) {
            this.a(packet15);
         }
      }

      for (Entity entity17 : clientWorld1.getEntities()) {
         if (entity17 != clientPlayerEntity2 && entity17.getId() >= 0 && !entity17.isRemoved()) {
            this.b(entity17);
            this.a(entity17);
            if (entity17 instanceof LivingEntity) {
               LivingEntity livingEntity18 = (LivingEntity)entity17;
               ArrayList arrayList9 = new ArrayList();

               for (EquipmentSlot equipmentSlot13 : EquipmentSlot.values()) {
                  ItemStack itemStack14 = livingEntity18.getEquippedStack(equipmentSlot13);
                  if (!itemStack14.isEmpty()) {
                     arrayList9.add(Pair.of(equipmentSlot13, itemStack14.copy()));
                  }
               }

               if (!arrayList9.isEmpty()) {
                  this.a(new EntityEquipmentUpdateS2CPacket(livingEntity18.getId(), arrayList9));
               }

               this.a(livingEntity18);
            }
         }
      }

      return arrayList4;
   }

   private void b(Entity entity1) {
      if (entity1 instanceof ExperienceOrbEntity experienceOrbEntity10) {
         PacketByteBuf packetByteBuf13 = new PacketByteBuf(Unpooled.buffer());
         Vec3d vec3d14 = experienceOrbEntity10.getTrackedPosition().getPos();
         packetByteBuf13.writeVarInt(experienceOrbEntity10.getId());
         packetByteBuf13.writeDouble(vec3d14.x);
         packetByteBuf13.writeDouble(vec3d14.y);
         packetByteBuf13.writeDouble(vec3d14.z);
         packetByteBuf13.writeShort(experienceOrbEntity10.getExperienceAmount());

         try {
            this.a((Packet<?>)ExperienceOrbSpawnS2CPacket.CODEC.decode(packetByteBuf13));
         } finally {
            packetByteBuf13.release();
         }
      } else if (entity1 instanceof BlockAttachedEntity blockAttachedEntity9) {
         int intValue12 = blockAttachedEntity9 instanceof LeashKnotEntity ? 0 : blockAttachedEntity9.getHorizontalFacing().getId();
         this.a(new EntitySpawnS2CPacket(blockAttachedEntity9, intValue12, blockAttachedEntity9.getAttachedBlockPos()));
      } else {
         int intValue2 = 0;
         if (entity1 instanceof FallingBlockEntity fallingBlockEntity3) {
            intValue2 = Block.getRawIdFromState(fallingBlockEntity3.getBlockState());
         } else if (entity1 instanceof FishingBobberEntity fishingBobberEntity4) {
            intValue2 = fishingBobberEntity4.getOwner() == null ? fishingBobberEntity4.getId() : fishingBobberEntity4.getOwner().getId();
         } else if (entity1 instanceof ProjectileEntity projectileEntity5) {
            intValue2 = projectileEntity5.getOwner() == null ? 0 : projectileEntity5.getOwner().getId();
         } else if (entity1 instanceof WardenEntity wardenEntity6) {
            intValue2 = wardenEntity6.isInPose(EntityPose.EMERGING) ? 1 : 0;
         }

         Vec3d vec3d11 = entity1.getTrackedPosition().getPos();
         this.a(
            new EntitySpawnS2CPacket(
               entity1.getId(),
               entity1.getUuid(),
               vec3d11.x,
               vec3d11.y,
               vec3d11.z,
               entity1.getLerpTargetPitch(),
               entity1.getLerpTargetYaw(),
               entity1.getType(),
               intValue2,
               entity1.getVelocity(),
               (double)entity1.getHeadYaw()
            )
         );
      }
   }

   private void a(ClientWorld clientWorld1, ClientPlayerEntity clientPlayerEntity2) {
      for (Entity entity4 : clientWorld1.getEntities()) {
         if (entity4.getId() >= 0 && !entity4.isRemoved()) {
            if (entity4.hasPassengers()) {
               this.a(new EntityPassengersSetS2CPacket(entity4));
            }

            if (entity4 != clientPlayerEntity2 && entity4 instanceof Leashable) {
               Leashable leashable5 = (Leashable)entity4;
               if (leashable5.getLeashHolder() != null) {
                  this.a(new EntityAttachS2CPacket(entity4, leashable5.getLeashHolder()));
               }
            }
         }
      }
   }

   private void a(ClientWorld clientWorld1) {
      Scoreboard scoreboard2 = clientWorld1.getScoreboard();
      if (scoreboard2 != null) {
         for (Team team4 : scoreboard2.getTeams()) {
            this.a(TeamS2CPacket.updateTeam(team4, true));
         }

         for (ScoreboardObjective scoreboardObjective10 : scoreboard2.getObjectives()) {
            this.a(new ScoreboardObjectiveUpdateS2CPacket(scoreboardObjective10, 0));

            for (ScoreboardEntry scoreboardEntry6 : scoreboard2.getScoreboardEntries(scoreboardObjective10)) {
               this.a(
                  new ScoreboardScoreUpdateS2CPacket(
                     scoreboardEntry6.owner(), scoreboardObjective10.getName(), scoreboardEntry6.value(), Optional.ofNullable(scoreboardEntry6.display()), Optional.ofNullable(scoreboardEntry6.numberFormatOverride())
                  )
               );
            }
         }

         for (ScoreboardDisplaySlot scoreboardDisplaySlot13 : ScoreboardDisplaySlot.values()) {
            ScoreboardObjective scoreboardObjective7 = scoreboard2.getObjectiveForSlot(scoreboardDisplaySlot13);
            if (scoreboardObjective7 != null) {
               this.a(new ScoreboardDisplayS2CPacket(scoreboardDisplaySlot13, scoreboardObjective7));
            }
         }
      }
   }

   private void a() {
      if (a.inGameHud != null) {
         if (a.inGameHud.getBossBarHud() != null) {
            for (ClientBossBar clientBossBar2 : a.inGameHud.getBossBarHud().bossBars.values()) {
               this.a(BossBarS2CPacket.add(clientBossBar2));
            }
         }

         if (a.inGameHud.getPlayerListHud() != null) {
            Text text3 = a.inGameHud.getPlayerListHud().header;
            Text text4 = a.inGameHud.getPlayerListHud().footer;
            if (text3 != null || text4 != null) {
               this.a(new PlayerListHeaderS2CPacket((Text)(text3 != null ? text3 : Text.empty()), (Text)(text4 != null ? text4 : Text.empty())));
            }
         }
      }
   }

   private void b(ClientWorld clientWorld1) {
      for (java.util.Map.Entry entry3 : clientWorld1.getMapStates().entrySet()) {
         MapState mapState4 = (MapState)entry3.getValue();
         ArrayList arrayList5 = new ArrayList();

         for (MapDecoration mapDecoration7 : mapState4.getDecorations()) {
            arrayList5.add(mapDecoration7);
         }

         this.a(
            new MapUpdateS2CPacket((MapIdComponent)entry3.getKey(), mapState4.scale, mapState4.locked, arrayList5, new UpdateData(0, 0, 128, 128, (byte[])mapState4.colors.clone()))
         );
      }
   }
}
