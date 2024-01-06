package net.morimori0317.bettertaskbar.neoforge;

import net.morimori0317.bettertaskbar.TaskbarHandler;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TickEvent;

public class RenderHandlerNeoForge {
    @SubscribeEvent
    public static void onTick(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.START)
            TaskbarHandler.tick();
    }
}
