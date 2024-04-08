package com.quidvio.no_void_structures_mod.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PoolElementStructurePiece.class)
public class PoolElementStructurePieceMixin {

    /**
     * Stops structure pieces (E.g. village houses) from generating in the void.
     *
     * If the y-level of generation is 8 blocks from the bottom or less. (-56 by default).
     * Stops void generation by not generating it and returning false.
     * Otherwise, generates as normal.
     *
     *
     * @param instance default usage, the Pool Element itself
     * @param structureTemplateManager default usage
     * @param worldGenLevel default usage
     * @param structureManager default usage
     * @param chunkGenerator default usage, also used to get the void height
     * @param blockPos default usage, also used to get the gen height
     * @param pivot default usage
     * @param rotation default usage
     * @param boundingBox default usage
     * @param randomSource default usage
     * @param b default usage
     * @return the result of the generation
     */
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/core/BlockPos;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/pools/StructurePoolElement;place(Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplateManager;Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;Z)Z"))
    private boolean no_void_structures_stopStructurePieceVoidGen_PSP(StructurePoolElement instance, StructureTemplateManager structureTemplateManager, WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos pivot, Rotation rotation, BoundingBox boundingBox, RandomSource randomSource, boolean b) {
        if (blockPos.getY() <= chunkGenerator.getMinY() + 8) {
            return false;
        }

        return instance.place(structureTemplateManager, worldGenLevel, structureManager, chunkGenerator, blockPos, pivot, rotation, boundingBox, randomSource, b);
    }

}
