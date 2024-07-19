package com.quidvio.no_void_structures.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.structure.ShiftableStructurePiece;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShiftableStructurePiece.class)
public class ShiftableStructurePieceMixin {

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
    @WrapOperation(method = "adjustToMinHeight", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_stopPyramidVoidGen_SSP2(BlockPos instance, Operation<Integer> original) {
        if (instance.getY() <= -56) {
            return -1024;
        }
        return original.call(instance);
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
    @WrapOperation(method = "adjustToAverageHeight", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_stopHutVoidGen_SSP(BlockPos instance, Operation<Integer> original) {
        if (instance.getY() <= -56) {
            return -16384;
        }
        return instance.getY();
    }

}
