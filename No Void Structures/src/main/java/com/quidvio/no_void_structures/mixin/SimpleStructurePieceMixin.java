package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SimpleStructurePiece.class)
public class SimpleStructurePieceMixin {

    @Redirect(method = "Lnet/minecraft/structure/SimpleStructurePiece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/StructureTemplate;calculateBoundingBox(Lnet/minecraft/structure/StructurePlacementData;Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/util/math/BlockBox;"))
    protected BlockBox genericStructureVoidReMover(StructureTemplate instance, StructurePlacementData placementData, BlockPos pos) {
        BlockPos modifiedBlockPos = new BlockPos(pos.getX(),100,pos.getZ());
        System.out.println("Simple " + pos.getY());
        if (pos.getY() <= -56) {
            return instance.calculateBoundingBox(placementData.setBoundingBox(instance.calculateBoundingBox(placementData, modifiedBlockPos)), modifiedBlockPos);
        }
        return instance.calculateBoundingBox(placementData, pos);
    }

}
