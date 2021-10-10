package net.zuiron.huntergatherers.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.zuiron.huntergatherers.HunterGatherers;

public class ModItemGroup {
    public static final ItemGroup HUNTERGATHERERS = FabricItemGroupBuilder.build(new Identifier(HunterGatherers.MOD_ID, "huntergatherers"),
            () -> new ItemStack(ModItems.STONE_MALLET));
}
