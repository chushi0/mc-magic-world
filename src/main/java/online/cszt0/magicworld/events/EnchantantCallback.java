package online.cszt0.magicworld.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;

public interface EnchantantCallback {
    Event<EnchantantCallback> EVENT = EventFactory.createArrayBacked(EnchantantCallback.class,
            listeners -> player -> {
                for (EnchantantCallback listener : listeners) {
                    ActionResult result = listener.interact(player);

                    if (result != ActionResult.PASS) {
                        return result;
                    }
                }

                return ActionResult.PASS;
            });

    ActionResult interact(PlayerEntity player);
}
