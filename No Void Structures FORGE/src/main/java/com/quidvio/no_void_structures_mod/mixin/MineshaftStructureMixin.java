package com.quidvio.no_void_structures_mod.mixin;


import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftPieces;
import net.minecraft.world.level.levelgen.structure.structures.MineshaftStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MineshaftStructure.class)
public class MineshaftStructureMixin {


    private int surfaceHeight;

    @Shadow
    @Final
    private
    MineshaftStructure.Type type; // Used to get the mineshaft piece.


    /**
     * Stops Mineshafts from generating as low in the world.
     *
     * The Mineshaft height generation is based on the sea-level... void worlds tend to have atypical sea-levels.
     *
     * Changes it to be from 32 blocks below the surface height and 64 blocks below the surface height.
     *
     * @param instance the Chunk generator, used to get the minimum y
     * @return the new modified y-level basis for Mineshaft generation
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure;generatePiecesAndAdjust(Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePiecesBuilder;Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;getSeaLevel()I"))
    private int no_void_structure_mineshaftHeightFix_MS(ChunkGenerator instance) {
        return surfaceHeight-32;
    }

    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure;generatePiecesAndAdjust(Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePiecesBuilder;Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ChunkGenerator;getMinY()I"))
    private int no_void_structure_mineshaftHeight_MS(ChunkGenerator instance) {
        return surfaceHeight-64;
    }

    /**
     * Gets the surface height at the area where the Mineshaft attempts to generate.
     *
     * Used for the method above to change the generation range.
     *
     * @param structurePiecesBuilder default usage
     * @param generationContext default usage
     * @param cir unused
     */
    @Inject(method = "Lnet/minecraft/world/level/levelgen/structure/structures/MineshaftStructure;generatePiecesAndAdjust(Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePiecesBuilder;Lnet/minecraft/world/level/levelgen/structure/Structure$GenerationContext;)I", at = @At("HEAD"))
    private void no_void_structure_getMineshaftHeight_MS(StructurePiecesBuilder structurePiecesBuilder, Structure.GenerationContext generationContext, CallbackInfoReturnable<Integer> cir) {
        ChunkPos chunkPos = generationContext.chunkPos();
        WorldgenRandom chunkRandom = generationContext.random();
        MineshaftPieces.MineShaftRoom mineshaftRoom = new MineshaftPieces.MineShaftRoom(
                0, chunkRandom, chunkPos.getBlockX(2), chunkPos.getBlockZ(2), this.type
        );
        structurePiecesBuilder.addPiece(mineshaftRoom);
        mineshaftRoom.addChildren(mineshaftRoom, structurePiecesBuilder, chunkRandom);
        if (!structurePiecesBuilder.isEmpty()) {
            surfaceHeight = generationContext.chunkGenerator().getBaseHeight(structurePiecesBuilder.getBoundingBox().getCenter().getX(), structurePiecesBuilder.getBoundingBox().getCenter().getZ(), Heightmap.Types.WORLD_SURFACE_WG, generationContext.heightAccessor(), generationContext.randomState());
        }
    }

}
