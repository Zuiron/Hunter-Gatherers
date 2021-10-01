package net.zuiron.huntergatherers;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.zuiron.huntergatherers.item.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HunterGatherers implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LogManager.getLogger("modid");

	public static final String MOD_ID = "huntergatherers";

	private static final Identifier OAK_LEAVES_BLOCK_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/oak_leaves");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		ModItems.registerModItems();
		modifyLootTables();

		LOGGER.info("Hunter Gatherer's mod says HELLO WORLD! -Zuiron");
	}

	//try modify leaves to also drop sticks.
	private void modifyLootTables(){
		LootTableLoadingCallback.EVENT.register(((resourceManager, manager, id, supplier, setter) -> {
			//check for leaves loot table.
			if(OAK_LEAVES_BLOCK_LOOT_TABLE_ID.equals(id)) {
				//add sticks to oak leaves loot table.
				FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.with(ItemEntry.builder(Items.STICK))
						.withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f)).build());
				supplier.withPool(poolBuilder.build());
			}

		} ));
	}
}
