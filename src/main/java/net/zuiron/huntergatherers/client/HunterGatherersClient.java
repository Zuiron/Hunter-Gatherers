package net.zuiron.huntergatherers.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.render.RenderLayer;
import net.zuiron.huntergatherers.HunterGatherers;
import net.zuiron.huntergatherers.block.ModBlocks;

@SuppressWarnings("unused")
public class HunterGatherersClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //BlockRenderLayerMapImpl.INSTANCE.putBlocks(RenderLayer.getCutout(), ModBlocks.FLORAMELISSIA);
        //BlockRenderLayerMapImpl.INSTANCE.putBlocks(RenderLayer.getTranslucent(), ModBlocks.FLORAMELISSIA);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLORAMELISSIA, RenderLayer.getCutout());
        //BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FLORAMELISSIA, RenderLayer.getTranslucent());
    }
}
