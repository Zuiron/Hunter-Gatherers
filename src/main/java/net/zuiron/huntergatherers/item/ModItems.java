package net.zuiron.huntergatherers.item;

import com.google.common.collect.BiMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
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
import net.zuiron.huntergatherers.mixin.ItemAccessor;

import java.util.Optional;
import java.util.Random;

class PrimitiveToolMaterial implements ToolMaterial {

    public static final PrimitiveToolMaterial INSTANCE = new PrimitiveToolMaterial();

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

class FlintKnifeItem extends AxeItem {

    public FlintKnifeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
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
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_OAK,getRandomNumberUsingNextInt(1,3)));
            } else if(blockStripped == Blocks.BIRCH_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_BIRCH,getRandomNumberUsingNextInt(1,3)));
            } else if(blockStripped == Blocks.ACACIA_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_ACACIA,getRandomNumberUsingNextInt(1,3)));
            } else if(blockStripped == Blocks.DARK_OAK_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_DARK_OAK,getRandomNumberUsingNextInt(1,3)));
            } else if(blockStripped == Blocks.SPRUCE_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_SPRUCE,getRandomNumberUsingNextInt(1,3)));
            } else if(blockStripped == Blocks.JUNGLE_LOG) {
                Block.dropStack(world,blockPos,new ItemStack(ModItems.BARK_JUNGLE,getRandomNumberUsingNextInt(1,3)));
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
    public static final Item LEAF_ACACIA = registerItem("leaf_acacia",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_BIRCH = registerItem("leaf_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_DARK_OAK = registerItem("leaf_dark_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_JUNGLE = registerItem("leaf_jungle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_OAK = registerItem("leaf_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_SPRUCE = registerItem("leaf_spruce",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //Cones
    public static final Item CONE_ACACIA = registerItem("cone_acacia",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_BIRCH = registerItem("cone_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_DARK_OAK = registerItem("cone_dark_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_JUNGLE = registerItem("cone_jungle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_OAK = registerItem("cone_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_SPRUCE = registerItem("cone_spruce",
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

    public static final Item BARK_ACACIA_BUNDLE = registerItem("bark_acacia_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_BIRCH_BUNDLE = registerItem("bark_birch_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_DARK_OAK_BUNDLE = registerItem("bark_dark_oak_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_JUNGLE_BUNDLE = registerItem("bark_jungle_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_OAK_BUNDLE = registerItem("bark_oak_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BARK_SPRUCE_BUNDLE = registerItem("bark_spruce_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    //flint items
    public static final Item SHARP_FLINT_FRAGMENT = registerItem("sharp_flint_fragment",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item FLINT_KNIFE = registerItem("flint_knife",
            new FlintKnifeItem(PrimitiveToolMaterial.INSTANCE,
                    1,
                    1,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item STONE_MALLET = registerItem("stone_mallet",
            new SwordItem(PrimitiveToolMaterial.INSTANCE,
                    3,
                    -2.4f,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    //clay bucket
    /*
    public static final Item CLAY_BUCKET_UNFIRED = registerItem("bucket_clay_unfired",
            new Item(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1)));
    public static final Item CLAY_BUCKET_FIRED = registerItem("bucket_clay_fired",
            new CustomBucketItem(Fluids.EMPTY, new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CLAY_BUCKET_FIRED_WATER = registerItem("bucket_clay_fired_water",
            new CustomBucketItem(Fluids.WATER, new FabricItemSettings()
                    .recipeRemainder(ModItems.CLAY_BUCKET_FIRED)
                    .group(ItemGroup.MISC)
            ));*/

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
    public static final Item GRASS_FIBRE_PIECE = registerItem("grass_fibre_piece",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item GRASS_TWINE_DRY = registerItem("grass_twine_dry",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item GRASS_TWINE = registerItem("grass_twine",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item BRANCH_ACACIA = registerItem("branch_acacia",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BRANCH_BIRCH = registerItem("branch_birch",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BRANCH_DARK_OAK = registerItem("branch_dark_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BRANCH_JUNGLE = registerItem("branch_jungle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BRANCH_OAK = registerItem("branch_oak",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BRANCH_SPRUCE = registerItem("branch_spruce",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));


    public static final Item STURDY_STICK = registerItem("sturdy_stick",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item BRANCH_BUNDLE = registerItem("branch_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item CONE_BUNDLE = registerItem("cone_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item LEAF_BUNDLE = registerItem("leaf_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LARGE_BOUND = registerItem("stone_large_bound",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LARGE_BOUND_DRY = registerItem("stone_large_bound_dry",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LONG_BOUND = registerItem("stone_long_bound",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_LONG_DRY = registerItem("stone_long_bound_dry",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item STONE_SMALL_PILE = registerItem("stone_small_pile",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item FORESTY_BUNDLE = registerItem("foresty_bundle",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item TINY_CHARCOAL = registerItem("tiny_charcoal",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item IRON_SAW = registerItem("iron_saw",
            new FlintKnifeItem(PrimitiveToolMaterial.INSTANCE,
                    1,
                    1,
                    new FabricItemSettings().group(ItemGroup.MISC)));

    public static final Item WOOL_CARDER = registerRemainderItem("wool_carder",
            new Item(new FabricItemSettings().group(ItemGroup.MISC)));








    private static Item registerRemainderItem(String name, Item item) {
        Item remainderItem = registerItem(name, item);
        ((ItemAccessor) remainderItem).setRecipeRemainder(remainderItem);

        return remainderItem;
    }

    //register items.
    private static Item registerItem(String name, Item item){
        HunterGatherers.LOGGER.info("Registering item with name: " + name);
        return Registry.register(Registry.ITEM, new Identifier(HunterGatherers.MOD_ID, name), item);
    }

    public static void registerModItems() {
        System.out.println("Registered Mod Items for " + HunterGatherers.MOD_ID);
    }
}
