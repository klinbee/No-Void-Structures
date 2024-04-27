package com.quidvio.no_void_structures_mod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ScatteredFeaturePiece.class)
public class ScatteredFeaturePieceMixin {

    /**
     * Stops Desert Pyramids and Jungle Pyramids from generating in the void.
     *
     * Because it is a legacy structure, it uses some unique placement and requires special treatment.
     * Specifically, finds the minimum height under the generation surface to generate at.
     *
     * If the y-level of one of the sampled surface positions is -56 or less.
     * Stops void generation by changing the sampled surface y-level to a much lower y-level.
     * Otherwise, leaves the sampled surface y-level unchanged.
     *
     * @param instance the BlockPos of the sampled height
     * @return the altered sampled height
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/ScatteredFeaturePiece;updateHeightPositionToLowestGroundHeight(Lnet/minecraft/world/level/LevelAccessor;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;getY()I"))
    protected int no_void_structures_stopPyramidVoidGen_SSP(BlockPos instance) {
        if (instance.getY() <= -56) {
            return -1024;
        }
        return instance.getY();
    }

    /**
     * Stops Witch Huts from generating in the void.
     *
     * Again legacy structure shenanigans. Typically this is used by Jungle Pyramids as well, but the JungleTempleGeneratorMixin changes this.
     * Specifically, this finds the average terrain height under the structure to generate at.
     *
     * If the y-level of one of the sampled surface positions is -56 or less.
     * Stops void generation by changing the sampled surface y-level to a much, much lower y-level. (To offset the average).
     * Otherwise, leaves the sampled surface y-level unchanged.
     *
     * @param instance the BlockPos of the sampled height
     * @return the altered sampled height
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/ScatteredFeaturePiece;updateAverageGroundHeight(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;getY()I"))
    protected int no_void_structures_stopHutVoidGen_SSP(BlockPos instance) {
        if (instance.getY() <= -56) {
            return -16384;
        }
        return instance.getY();
    }


}
