package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.structures.ShipwreckPieces;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ShipwreckPieces.ShipwreckPiece.class)
public class ShipwreckPiecesMixin {

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
     * @param worldGenLevel default usage
     * @param type default usage
     * @param x default usage
     * @param z default usage
     * @return the altered sampled height
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/ShipwreckPieces$ShipwreckPiece;postProcess(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;getHeight(Lnet/minecraft/world/level/levelgen/Heightmap$Types;II)I"))
    private int no_void_structures_stopShipwreckVoidGen_SP$SP(WorldGenLevel worldGenLevel, Heightmap.Types type, int x, int z) {
        int height = worldGenLevel.getHeight(type,x,z);
        return height <= -56 ? -16384 : height;
    }

}
