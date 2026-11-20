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

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public interface BotTask {
   boolean a(dev.leg1tka.network.bots.BotSession value1, ClientPlayerEntity clientPlayerEntity2, ClientWorld clientWorld3);

   boolean a(dev.leg1tka.network.bots.BotSession value1);

   void b(dev.leg1tka.network.bots.BotSession value1);
}
