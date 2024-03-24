package com.quidvio.no_void_structures.mixin;


import net.minecraft.structure.MineshaftGenerator;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.MineshaftStructure;
import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MineshaftStructure.class)
public class MineshaftStructureMixin {

    private int height;
    @Shadow @Final
    private
    MineshaftStructure.Type type;

    @Inject(method = "Lnet/minecraft/world/gen/structure/MineshaftStructure;addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At("HEAD"))
    private void no_void_structure_getMineshaftHeight_MS(StructurePiecesCollector collector, Structure.Context context, CallbackInfoReturnable<Integer> cir) {
        ChunkPos chunkPos = context.chunkPos();
        ChunkRandom chunkRandom = context.random();
        MineshaftGenerator.MineshaftRoom mineshaftRoom = new MineshaftGenerator.MineshaftRoom(
                0, chunkRandom, chunkPos.getOffsetX(2), chunkPos.getOffsetZ(2), this.type
        );
        collector.addPiece(mineshaftRoom);
        mineshaftRoom.fillOpenings(mineshaftRoom, collector, chunkRandom);
        if (!collector.isEmpty()) {
            height = context.chunkGenerator().getHeight(collector.getBoundingBox().getCenter().getX(), collector.getBoundingBox().getCenter().getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world(), context.noiseConfig());
        }
    }



    /**
     * Stops Mineshafts from generating as low in the world.
     *
     * The Mineshaft height generation is based on the sea-level... void worlds tend to have atypical sea-levels.
     *
     * Changes it to be 128 blocks above the minimum y (64 by default).
     *
     * @param instance the Chunk generator, used to get the minimum y.
     * @return the new modified y-level basis for Mineshaft generation.
     */
    @Redirect(method = "Lnet/minecraft/world/gen/structure/MineshaftStructure;addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getSeaLevel()I"))
    private int no_void_structure_mineshaftHeightFix_MS(ChunkGenerator instance) {
        return height-32;
    }

    @Redirect(method = "Lnet/minecraft/world/gen/structure/MineshaftStructure;addPieces(Lnet/minecraft/structure/StructurePiecesCollector;Lnet/minecraft/world/gen/structure/Structure$Context;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/chunk/ChunkGenerator;getMinimumY()I"))
    private int no_void_structure_mineshaftHeight_MS(ChunkGenerator instance) {
        return height-32;
    }

}
