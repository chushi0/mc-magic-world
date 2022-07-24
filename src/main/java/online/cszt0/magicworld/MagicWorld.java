package online.cszt0.magicworld;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import online.cszt0.magicworld.entities.WanderMagician;
import online.cszt0.magicworld.items.MagicLapisLazuli;

public class MagicWorld implements ModInitializer {

        public static final EntityType<WanderMagician> WANDER_MAGICIAN = Registry.register(
                        Registry.ENTITY_TYPE,
                        new Identifier("magicworld", "wander_magician"),
                        FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WanderMagician::new)
                                        .dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build());
        public static final Item WANDER_MAGICIAN_SPAWN_EGG = new SpawnEggItem(WANDER_MAGICIAN, 12895428, 11382189,
                        new Item.Settings().group(ItemGroup.MISC));
        public static final Item MAGIC_LAPIS_LAZULI = new MagicLapisLazuli(
                        new FabricItemSettings().group(ItemGroup.MISC));
        public static final Block MAGIC_LAPIS_BLOCK = new Block(
                        FabricBlockSettings.of(Material.STONE).requiresTool().strength(50, 1200));

        @Override
        public void onInitialize() {
                FabricDefaultAttributeRegistry.register(WANDER_MAGICIAN, WanderMagician.createMobAttributes());
                Registry.register(Registry.ITEM, new Identifier("magicworld", "wander_magician_spawn_egg"),
                                WANDER_MAGICIAN_SPAWN_EGG);
                Registry.register(Registry.ITEM, new Identifier("magicworld", "magic_lapis_lazuli"),
                                MAGIC_LAPIS_LAZULI);
                Registry.register(Registry.BLOCK, new Identifier("magicworld", "magic_lapis_block"), MAGIC_LAPIS_BLOCK);
                Registry.register(Registry.ITEM, new Identifier("magicworld", "magic_lapis_block"),
                                new BlockItem(MAGIC_LAPIS_BLOCK, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

                WanderMagician.init();

                CustomPortalBuilder.beginPortal()
                                .frameBlock(MAGIC_LAPIS_BLOCK)
                                .destDimID(new Identifier("magicworld", "overworld"))
                                .onlyLightInOverworld()
                                .tintColor(41, 60, 224)
                                .registerPortal();
        }

}
