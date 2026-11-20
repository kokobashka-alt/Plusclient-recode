package dev.leg1tka.mixin;

import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({ThreadExecutor.class})
public interface ThreadExecutorInvoker {
   @Invoker("runTasks")
   void aspect$runTasks();
}
