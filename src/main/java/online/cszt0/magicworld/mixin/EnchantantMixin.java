package online.cszt0.magicworld.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.EnchantmentScreenHandler;
import net.minecraft.util.ActionResult;
import online.cszt0.magicworld.events.EnchantantCallback;

@Mixin(EnchantmentScreenHandler.class)
public class EnchantantMixin {

    @Inject(at = @At(value = "RETURN", target = "Lnet/minecraft/item/ItemStack;addEnchantment(Lnet/minecraft/enchantment/Enchantment;I)V"), method = "onButtonClick", cancellable = true)
    private void onEnchant(PlayerEntity player, int id, final CallbackInfoReturnable<Boolean> info) {
        ActionResult result = EnchantantCallback.EVENT.invoker().interact(player);

        if (result == ActionResult.FAIL) {
            info.cancel();
        }
    }
}
