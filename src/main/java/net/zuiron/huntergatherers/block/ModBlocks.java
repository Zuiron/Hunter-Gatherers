package net.zuiron.huntergatherers.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.fabricmc.fabric.mixin.object.builder.AbstractBlockSettingsAccessor;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.zuiron.huntergatherers.HunterGatherers;

public class ModBlocks {

    /*
    public static final Block RUBY_ORE = registerBlock("ruby_ore",
            new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f)
                    .breakByTool(FabricToolTags.PICKAXES, 2).requiresTool()));
     */

    //flowers
    public static final Block FLORAMELISSIA = registerBlock("floramelissia",
            new FlowerBlock(StatusEffect.byRawId(1),1, AbstractBlock.Settings.copy(Blocks.POPPY)));
        //new Block(FabricBlockSettings.of(Material.REPLACEABLE_PLANT).noCollision()));




    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(HunterGatherers.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registry.ITEM, new Identifier(HunterGatherers.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(ItemGroup.MISC)));
    }

    public static void registerModBlocks() {
        System.out.println("Registering ModBlocks for " + HunterGatherers.MOD_ID);
    }
}