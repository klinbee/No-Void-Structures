package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.ShiftableStructurePiece;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShiftableStructurePiece.class)
public class ShiftableStructurePieceMixin {

    /**
     * Stops Desert Pyramids from generating in the void.
     *
     * Because it is a legacy structure, it uses some unique placement and requires special treatment.
     * Specifically, finds the minimum height under the generation surface to generate at.
     *
     * If the y-level of one of the sampled surface positions is -48 or less.
     * Stops void generation by changing the sampled surface y-level to a much lower y-level.
     * Otherwise, leaves the sampled surface y-level unchanged.
     *
     * @param instance the BlockPos of the sampled height.
     * @return the altered sampled height.
     */
    @Redirect(method = "Lnet/minecraft/structure/ShiftableStructurePiece;adjustToMinHeight(Lnet/minecraft/world/WorldAccess;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_stopDesertPyramidVoidGen_SSP(BlockPos instance) {
        if (instance.getY() <= -48) {
            return -1024;
        }
        return instance.getY();
    }

    /**
     * Stops Witch Huts and Jungle Pyramids from generating in the void.
     *
     * Again legacy structure shenanigans.
     * Specifically, this finds the average terrain height under the structure to generate at.
     *
     * If the y-level of one of the sampled surface positions is -48 or less.
     * Stops void generation by changing the sampled surface y-level to a much, much lower y-level. (To offset the average).
     * Otherwise, leaves the sampled surface y-level unchanged.
     *
     * @param instance the BlockPos of the sampled height.
     * @return the altered sampled height.
     */
    @Redirect(method = "Lnet/minecraft/structure/ShiftableStructurePiece;adjustToAverageHeight(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockBox;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_stopOtherPyramidVoidGen_SSP(BlockPos instance) {
        if (instance.getY() <= -48) {
            return -16384;
        }
        return instance.getY();
    }

}
