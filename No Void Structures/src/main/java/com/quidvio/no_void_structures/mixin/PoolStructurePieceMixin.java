package com.quidvio.no_void_structures.mixin;


import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PoolStructurePiece.class)
public class PoolStructurePieceMixin {


    /**
     * Stops structure pieces (E.g. village houses) from generating in the void.
     *
     * If the y-level of generation is 8 blocks from the bottom or less. (-56 by default).
     * Stops void generation by not generating it and returning false.
     * Otherwise, generates as normal.
     *
     *
     * @param instance default usage, the Pool Element itself.
     * @param structureTemplateManager default usage
     * @param structureWorldAccess default usage
     * @param structureAccessor default usage
     * @param chunkGenerator default usage, also used to get the void height.
     * @param blockPos default usage, also used to get the gen height.
     * @param pivot default usage
     * @param blockRotation default usage
     * @param blockBox default usage
     * @param random default usage
     * @param b default usage
     * @return the result of the generation.
     */
    @Redirect(method = "Lnet/minecraft/structure/PoolStructurePiece;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/BlockPos;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/pool/StructurePoolElement;generate(Lnet/minecraft/structure/StructureTemplateManager;Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/random/Random;Z)Z"))
    private boolean no_void_structures_stopStructurePieceVoidGen_PSP(StructurePoolElement instance, StructureTemplateManager structureTemplateManager, StructureWorldAccess structureWorldAccess, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, BlockPos blockPos, BlockPos pivot, BlockRotation blockRotation, BlockBox blockBox, Random random, boolean b) {
        if (blockPos.getY() <= chunkGenerator.getMinimumY() + 8) {
            return false;
        }

        return instance.generate(structureTemplateManager, structureWorldAccess, structureAccessor, chunkGenerator, blockPos, pivot, blockRotation, blockBox, random, b);
    }

}
