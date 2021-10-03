package net.zuiron.huntergatherers.item;

import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.zuiron.huntergatherers.HunterGatherers;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

class PotatoToolMaterial implements ToolMaterial {

    public static final PotatoToolMaterial INSTANCE = new PotatoToolMaterial();

    @Override
    public int getDurability() {
        return 59;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 2.0F;
    }

    @Override
    public float getAttackDamage() {
        return 0.0F;
    }

    @Override
    public int getMiningLevel() {
        return -1;
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

class CustomAxeItem extends AxeItem {

    public CustomAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity playerEntity = context.getPlayer();
        BlockState blockState = world.getBlockState(blockPos);
        Optional<BlockState> optional = this.getStrippedState(blockState);
        Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(blockState);
        Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap)HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(blockState.getBlock())).map((block) -> {
            return block.getStateWithProperties(blockState);
        });

        ItemStack itemStack = context.getStack();
        Optional<BlockState> optional4 = Optional.empty();
        if (optional.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            optional4 = optional;

            //used on a log.

            //get block which we just stripped
            Block blockStripped = context.getWorld().getBlockState(blockPos).getBlock();

            if(blockStripped == Blocks.OAK_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_OAK,getRandomNumberUsingNextInt(0,2)));
            } else if(blockStripped == Blocks.BIRCH_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_BIRCH,getRandomNumberUsingNextInt(0,2)));
            } else if(blockStripped == Blocks.ACACIA_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_ACACIA,getRandomNumberUsingNextInt(0,2)));
            } else if(blockStripped == Blocks.DARK_OAK_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_DARK_OAK,getRandomNumberUsingNextInt(0,2)));
            } else if(blockStripped == Blocks.SPRUCE_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_SPRUCE,getRandomNumberUsingNextInt(0,2)));
            } else if(blockStripped == Blocks.JUNGLE_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_JUNGLE,getRandomNumberUsingNextInt(0,2)));
            }

        } else if (optional2.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(playerEntity, 3005, blockPos, 0);
            optional4 = optional2;
        } else if (optional3.isPresent()) {
            world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
            world.syncWorldEvent(playerEntity, 3004, blockPos, 0);
            optional4 = optional3;
        }

        if (optional4.isPresent()) {
            if (playerEntity instanceof ServerPlayerEntity) {
                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)playerEntity, blockPos, itemStack);
            }

            world.setBlockState(blockPos, (BlockState)optional4.get(), 11);
            if (playerEntity != null) {
                itemStack.damage(1, playerEntity, (p) -> {
                    p.sendToolBreakStatus(context.getHand());
                });
            }

            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable((Block)STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> {
            return (BlockState)block.getDefaultState().with(PillarBlock.AXIS, (Direction.Axis)state.get(PillarBlock.AXIS));
        });
    }
}

//FIX LATER. um looking at other code on github suggests we need to extend Item instead of BucketItem.
class CustomBucketItem extends BucketItem {
    public CustomBucketItem(Fluid fluid, Settings settings) {
        super(fluid, settings);
    }
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

    public static final Item STONE_MALLET = registerItem("stone_mallet",
            new SwordItem(PotatoToolMaterial.INSTANCE,
                    3,
                    -2.4f,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    //public static ToolItem POTATO_SWORD = new SwordItem(PotatoToolMaterial.INSTANCE, 3, -2.4F, new Item.Settings().group(ItemGroup.COMBAT));


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

    public static final Item BLUEBERRY = registerItem("blueberry",
            new Item(new FabricItemSettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build())
                    .group(ItemGroup.MISC)));

    public static final Item STONE_SMALL = registerItem("stone_small",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LARGE = registerItem("stone_large",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LONG = registerItem("stone_long",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item SHEEP_BONES = registerItem("sheep_bones",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item GRASS_FIBRE = registerItem("grass_fibre",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item GRASS_TWINE_DRY = registerItem("grass_twine_dry",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item GRASS_TWINE = registerItem("grass_twine",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item BRANCH_OAK = registerItem("branch_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STURDY_STICK = registerItem("sturdy_stick",
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
