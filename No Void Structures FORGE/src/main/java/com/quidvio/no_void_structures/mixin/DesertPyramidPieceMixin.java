package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.level.levelgen.structure.structures.DesertPyramidPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DesertPyramidPiece.class)
public class DesertPyramidPieceMixin {

    /**
     * Stops Desert Pyramids from pillaring down.
     *
     * Stops pillaring by altering the getter fields used by a for loop that's used to iterate over the entire bottom surface.
     *
     * This is done because it can pillar down an island or into the void and  it looks weird.
     *
     * @param instance unused
     * @return 0, so the for loop escapes early
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/DesertPyramidPiece;postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/structure/structures/DesertPyramidPiece;width:I", ordinal = 3))
    private int no_void_structures_noDesertPyramidPillaringWidth_DPP(DesertPyramidPiece instance) {
        return 0;
    }

    /**
     * Stops Desert Pyramids from pillaring down.
     *
     * Stops pillaring by altering the getter fields used by a for loop that's used to iterate over the entire bottom surface.
     *
     * This is done because it can pillar down an island or into the void and  it looks weird.
     *
     * @param instance unused
     * @return 0, so the for loop escapes early
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/DesertPyramidPiece;postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/levelgen/structure/structures/DesertPyramidPiece;depth:I", ordinal = 3))
    private int no_void_structures_noDesertPyramidPillaringDepth_DPP(DesertPyramidPiece instance) {
        return 0;
    }

}
