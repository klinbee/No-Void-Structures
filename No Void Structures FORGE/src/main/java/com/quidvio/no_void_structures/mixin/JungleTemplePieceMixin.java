package com.quidvio.no_void_structures.mixin;


import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.structures.JungleTemplePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JungleTemplePiece.class)
public class JungleTemplePieceMixin {

    /**
     * Adjusts the Jungle Pyramid generation to use the updateHeightPositionToLowestGroundHeight() method rather than updateAverageGroundHeight().
     * This doesn't help with void generation, but makes it less awkward for cases where it is on the edge of islands.
     *
     * @param jungleTemplePiece used for the alternate height adjustment
     * @param levelAccessor default usage
     * @param boundingBox unused
     * @param i default usage, represents the delta y
     * @return boolean representing the successful generation or not
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/JungleTemplePiece;postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/structures/JungleTemplePiece;updateAverageGroundHeight(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;I)Z"))
    private boolean no_void_structures_jungleTempleUseMinHeight_JTP(JungleTemplePiece jungleTemplePiece, LevelAccessor levelAccessor, BoundingBox boundingBox, int i) {
        return jungleTemplePiece.updateHeightPositionToLowestGroundHeight(levelAccessor,i);
    }

}
