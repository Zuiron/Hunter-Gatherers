package net.zuiron.huntergatherers.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.zuiron.huntergatherers.HunterGatherers;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

class PotatoToolMaterial implements ToolMaterial {

    public static final PotatoToolMaterial INSTANCE = new PotatoToolMaterial();

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}

class CustomPickaxeItem extends PickaxeItem {
    public CustomPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}

class CustomAxeItem extends AxeItem {
    public CustomAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
}

class CustomBucketItem extends BucketItem {

    public CustomBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }

    //TODO. um looking at other code on github suggests we need to extend Item instead of BucketItem.
}

public class ModItems {

    //Raw Wool
    public static final Item WOOLRAW_BLACK = registerItem("wool_raw_black",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_BROWN = registerItem("wool_raw_brown",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_CYAN = registerItem("wool_raw_cyan",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_BLUE = registerItem("wool_raw_blue",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_DARK_GRAY = registerItem("wool_raw_dark_gray",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_GRAY = registerItem("wool_raw_gray",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_GREEN = registerItem("wool_raw_green",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_LIGHT_BLUE = registerItem("wool_raw_light_blue",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_LIME = registerItem("wool_raw_lime",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_MAGENTA = registerItem("wool_raw_magenta",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_ORANGE = registerItem("wool_raw_orange",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_PINK = registerItem("wool_raw_pink",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_PURPLE = registerItem("wool_raw_purple",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_RED = registerItem("wool_raw_red",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_WHITE = registerItem("wool_raw_white",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item WOOLRAW_YELLOW = registerItem("wool_raw_yellow",
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

    //flint items
    public static final Item SHARP_FLINT_FRAGMENT = registerItem("sharp_flint_fragment",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));


    //public static ToolItem POTATO_SHOVEL = new ShovelItem(PotatoToolMaterial.INSTANCE, 1.5F, -3.0F, new Item.Settings().group(ItemGroup.TOOLS));
    //public static ToolItem POTATO_SWORD = new SwordItem(PotatoToolMaterial.INSTANCE, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));

    //public static ToolItem POTATO_PICKAXE = new CustomPickaxeItem(PotatoToolMaterial.INSTANCE, 1, -2.8F, new Item.Settings().group(ItemGroup.TOOLS));
    //public static ToolItem POTATO_AXE = new CustomAxeItem(PotatoToolMaterial.INSTANCE, 7.0F, -3.2F, new Item.Settings().group(ItemGroup.TOOLS));
    //public static ToolItem POTATO_HOE = new CustomHoeItem(PotatoToolMaterial.INSTANCE, 7, -3.2F, new Item.Settings().group(ItemGroup.TOOLS));

    public static final Item FLINT_KNIFE = registerItem("flint_knife",
            new CustomAxeItem(PotatoToolMaterial.INSTANCE,
                    1,
                    1,
                    new FabricItemSettings().group(ItemGroup.MISC)));


    //clay bucket
    public static final Item CLAY_BUCKET_UNFIRED = registerItem("bucket_clay_unfired",
            new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1)));
    public static final Item CLAY_BUCKET_FIRED = registerItem("bucket_clay_fired",
            new CustomBucketItem(Fluids.EMPTY, new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CLAY_BUCKET_FIRED_WATER = registerItem("bucket_clay_fired_water",
            new CustomBucketItem(Fluids.WATER, new FabricItemSettings()
                    .recipeRemainder(ModItems.CLAY_BUCKET_FIRED)
                    .group(ItemGroup.MISC)
            ));


    //register items.
    private static Item registerItem(String name, Item item){
        HunterGatherers.LOGGER.info("Registering item with name: " + name);
        return Registry.register(Registry.ITEM, new Identifier(HunterGatherers.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registered Mod Items for " + HunterGatherers.MOD_ID);
    }
}
