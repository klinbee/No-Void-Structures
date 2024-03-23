package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.OceanRuinGenerator;
import net.minecraft.structure.OceanRuinGenerator.Piece;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OceanRuinGenerator.Piece.class)
public class OceanRuinGeneratorMixin {

    @Inject(method = "Lnet/minecraft/structure/OceanRuinGenerator$Piece;getGenerationY(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)I", at = @At("RETURN"), cancellable = true)
    private void fixOceanRuin(BlockPos start, BlockView world, BlockPos end, CallbackInfoReturnable<Integer> cir){
        if (cir.getReturnValue() <= -56) {
            cir.setReturnValue(-1024);
        }
    }

}
