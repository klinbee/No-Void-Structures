package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.OceanRuinGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OceanRuinGenerator.Piece.class)
public class OceanRuinGeneratorMixin {

    /**
     * Stops Ocean Ruins from generating in the void.
     * Like the legacy pyramid variants, this also uses jank generation and needs special treatment.
     * Otherwise, it will rarely spread into the void.
     *
     * If the y-level of the Ocean Ruins Piece is -56 or less.
     * Stops void generation by setting it to a much lower y-level.
     * Otherwise, does nothing.
     *
     * @param start unused
     * @param world unused
     * @param end unused
     * @param cir used to get the returned getGenerationY value.
     */
    @Inject(method = "Lnet/minecraft/structure/OceanRuinGenerator$Piece;getGenerationY(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private void no_void_structures_stopOceanRuinVoidGen_ORG$P(BlockPos start, BlockView world, BlockPos end, CallbackInfoReturnable<Integer> cir){
        if (cir.getReturnValue() <= -56) {
            cir.setReturnValue(-1024);
        }
    }

}
