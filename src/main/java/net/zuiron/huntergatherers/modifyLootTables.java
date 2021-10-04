package net.zuiron.huntergatherers;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionManager;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;
import net.zuiron.huntergatherers.item.ModItems;

public class modifyLootTables {

    private static final Identifier OAK_LEAVES_BLOCK_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/oak_leaves");
    private static final Identifier BIRCH_LEAVES_BLOCK_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/birch_leaves");
    //private static final Identifier WHITE_SHEEP_LOOT_TABLE_ID = new Identifier("minecraft", "entities/sheep/white");
    private static final Identifier COMMON_SHEEP_LOOT_TABLE_ID = new Identifier("minecraft", "entities/sheep");
    private static final Identifier GRASS_ID = new Identifier("minecraft", "blocks/grass");
    private static final Identifier GRAVEL_BLOCK_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/gravel");

    public static void registerModifyLootTables() {

        LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {

            //add stones to gravel
            if(GRAVEL_BLOCK_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.STONE_LARGE).weight(250))
                        .with(ItemEntry.builder(ModItems.STONE_LONG).weight(250))
                        .with(ItemEntry.builder(ModItems.STONE_SMALL).weight(500))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                supplier.withPool(poolBuilder.build());
            }

            //grass block, when using flint knife drop grass fibre.
            if(GRASS_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.GRASS_FIBRE_PIECE))
                        .withCondition(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(ModItems.FLINT_KNIFE)).build())
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                supplier.withPool(poolBuilder.build());
            }

            //modify oak leaves.
            if(OAK_LEAVES_BLOCK_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(ModItems.BRANCH_OAK))
                        .with(ItemEntry.builder(ModItems.CONE_OAK))
                        .with(ItemEntry.builder(ModItems.LEAF_OAK))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                supplier.withPool(poolBuilder.build());
            }

            //modify birch leaves
            if(BIRCH_LEAVES_BLOCK_LOOT_TABLE_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .with(ItemEntry.builder(Items.STICK))
                        .with(ItemEntry.builder(ModItems.CONE_BIRCH))
                        .with(ItemEntry.builder(ModItems.LEAF_BIRCH))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
                supplier.withPool(poolBuilder.build());
            }

            //add sheep bone to "all/common" sheep.
			if(COMMON_SHEEP_LOOT_TABLE_ID.equals(id)) {
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.with(ItemEntry.builder(ModItems.SHEEP_BONES))
						.withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)).build());
				supplier.withPool(poolBuilder.build());
			}

        } ));

        //System.out.println("Registered Mod Items for " + HunterGatherers.MOD_ID);
        HunterGatherers.LOGGER.info("modifying loot tables.");
    }
}
