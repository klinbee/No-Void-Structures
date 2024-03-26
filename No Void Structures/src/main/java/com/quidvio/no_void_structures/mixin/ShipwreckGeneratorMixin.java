package com.quidvio.no_void_structures.mixin;

import net.minecraft.structure.ShipwreckGenerator;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShipwreckGenerator.Piece.class)
public class ShipwreckGeneratorMixin {

    /**
     * Stops Shipwrecks from generating in the void.
     *
     * Again legacy structure shenanigans.
     * I don't know exactly what it does, but it's some sort of average under the sampled area.
     *
     * If the y-level of one of the sampled surface positions is -56 or less.
     * Stops void generation by changing the sampled surface y-level to a much, much lower y-level. (To offset the average).
     * Otherwise, leaves the sampled surface y-level unchanged.
     *
     * @param worldAccess default usage
     * @param type default usage
     * @param x default usage
     * @param z default usage
     * @return the altered sampled height
     */
    @Redirect(method = "Lnet/minecraft/structure/ShipwreckGenerator$Piece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getTopY(Lnet/minecraft/world/Heightmap$Type;II)I"))
    private int no_void_structures_stopShipwreckVoidGen_SG(StructureWorldAccess worldAccess, Heightmap.Type type, int x, int z) {
        int height = worldAccess.getTopY(type,x,z);
        return height <= -56 ? -16384 : height;
    }

}
