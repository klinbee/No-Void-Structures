package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.math.BlockBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureStart.class)
public class StructureStartMixin {


    @Redirect(method = "Lnet/minecraft/structure/StructureStart;place(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/structure/StructurePiece;boundingBox:Lnet/minecraft/util/math/BlockBox;"))
    protected BlockBox genericStructureVoidReMover(StructurePiece instance) {
        BlockBox box = instance.getBoundingBox();
        int genPos = box.getMinY();
        return genPos <= -56 ? box.move(0,-512,0) : box;
    }

}

