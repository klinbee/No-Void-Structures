package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShipwreckGenerator.Piece.class)
public class ShipwreckGeneratorMixin {

    @Redirect(method = "Lnet/minecraft/structure/ShipwreckGenerator$Piece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getTopY(Lnet/minecraft/world/Heightmap$Type;II)I"))
    private int no_void_structures_stopShipwreckVoidGen_SG(StructureWorldAccess instance, Heightmap.Type type, int x, int z) {
        int height = instance.getTopY(type,x,z);
        return height <= -56 ? -16384 : height;
    }

}
