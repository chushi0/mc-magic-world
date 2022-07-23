package online.cszt0.magicworld.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MagicLapisLazuli extends Item {

    public MagicLapisLazuli(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

}
