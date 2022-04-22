package net.morimori0317.bettertaskbar.forge;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.morimori0317.bettertaskbar.TaskbarHandler;

public class RenderHandler {
    @SubscribeEvent
    public static void onTick(TickEvent.RenderTickEvent e) {
        if (e.phase == TickEvent.Phase.START)
            TaskbarHandler.tick();
    }
}
