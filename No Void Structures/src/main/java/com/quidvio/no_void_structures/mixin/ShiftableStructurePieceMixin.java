package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.ShiftableStructurePiece;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShiftableStructurePiece.class)
public class ShiftableStructurePieceMixin {

    @Redirect(method = "Lnet/minecraft/structure/ShiftableStructurePiece;adjustToAverageHeight(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockBox;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_desertTempleVoidReMover_SSP(BlockPos instance) {
        if (instance.getY() <= -48) {
            return -512;
        }
        return instance.getY();
    }

    @Redirect(method = "Lnet/minecraft/structure/ShiftableStructurePiece;adjustToMinHeight(Lnet/minecraft/world/WorldAccess;I)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;getY()I"))
    protected int no_void_structures_otherTempleVoidReMover_SSP(BlockPos instance) {
        if (instance.getY() <= -48) {
            return -20000;
        }
        return instance.getY();
    }

}
