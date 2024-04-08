package com.quidvio.no_void_structures_mod.mixin;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.levelgen.structure.structures.OceanRuinPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OceanRuinPieces.OceanRuinPiece.class)
public class OceanRuinPiecesMixin {

    /**
     * Stops Ocean Ruins from generating in the void.
     * Like the legacy pyramid variants, this also uses jank generation and needs special treatment.
     * Otherwise, it will rarely spread into the void.
     *
     * If the y-level of the Ocean Ruins Piece is -56 or less.
     * Stops void generation by setting it to a much lower y-level.
     * Otherwise, does nothing.
     *
     * @param startPos unused
     * @param blockGetter unused
     * @param endPos unused
     * @param cir used to get the returned getGenerationY value
     */
    @Inject(method = "Lnet/minecraft/world/level/levelgen/structure/structures/OceanRuinPieces$OceanRuinPiece;getHeight(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private void no_void_structures_stopOceanRuinVoidGen_ORP$ORP(BlockPos startPos, BlockGetter blockGetter, BlockPos endPos, CallbackInfoReturnable<Integer> cir){
        if (cir.getReturnValue() <= -56) {
            cir.setReturnValue(-1024);
        }
    }

}
