package net.zuiron.huntergatherers.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.zuiron.huntergatherers.HunterGatherers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems {

    //Raw Wool
    public static final Item WOOLRAW = registerItem("wool_raw_white",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //Leaf
    public static final Item LEAF_OAK = registerItem("leaf_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_BIRCH = registerItem("leaf_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //Cones
    public static final Item CONE_OAK = registerItem("cone_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_BIRCH = registerItem("cone_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //Bark
    public static final Item BARK_ACACIA = registerItem("bark_acacia",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_BIRCH = registerItem("bark_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_DARK_OAK = registerItem("bark_dark_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_JUNGLE = registerItem("bark_jungle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_OAK = registerItem("bark_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_SPRUCE = registerItem("bark_spruce",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //register items.
    private static Item registerItem(String name, Item item){
        HunterGatherers.LOGGER.info("Registering item with name: " + name);
        return Registry.register(Registry.ITEM, new Identifier(HunterGatherers.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registered Mod Items for " + HunterGatherers.MOD_ID);
    }
}
