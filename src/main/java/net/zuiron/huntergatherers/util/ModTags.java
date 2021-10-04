package net.zuiron.huntergatherers.util;

import net.fabricmc.fabric.api.tag.TagFactory;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.zuiron.huntergatherers.HunterGatherers;

public class ModTags {
    public static class Blocks {
        //public static final Tag<Block> VALUEABLE_BLOCKS = createTag("valueable_blocks");

        private static Tag<Block> createTag(String name) {
            return TagFactory.BLOCK.create(new Identifier(HunterGatherers.MOD_ID, name));
        }
        private static Tag<Block> createCommonTag(String name) {
            return TagFactory.BLOCK.create(new Identifier("c", name));
        }
    }

    public static class Items {
        public static final Tag<Item> CONES = createCommonTag("cones");
        public static final Tag<Item> BRANCHES = createCommonTag("branches");

        private static Tag<Item> createTag(String name) {
            return TagFactory.ITEM.create(new Identifier(HunterGatherers.MOD_ID, name));
        }
        private static Tag<Item> createCommonTag(String name) {
            return TagFactory.ITEM.create(new Identifier("c", name));
        }
    }
}
