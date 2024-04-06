package com.quidvio.no_void_structures.mixin;


import net.minecraft.structure.StructurePiecesList;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.structure.WoodlandMansionStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WoodlandMansionStructure.class)
public class WoodlandMansionStructureMixin {

    private int mansionYLevel;

    /**
     * Makes Mansions only pillar down 5 blocks, instead of until it hits a block.
     *
     * Stops pillaring down far by changing the result of world.getBottomY() to 5 blocks below the Mansion's Y level.
     *
     * This is done because it can pillar down into the void and it looks weird.
     *
     * @param instance unused
     * @return 5 blocks below the mansion's y-level
     */
    @Redirect(method = "Lnet/minecraft/world/gen/structure/WoodlandMansionStructure;postPlace(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/structure/StructurePiecesList;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getBottomY()I"))
    private int no_void_structure_modifyMansionPillaring_WMS(StructureWorldAccess instance) {
        return mansionYLevel-5;
    }

    /**
     * Gets the bottom y-level of the Mansion, used for the method above.
     *
     *  @param world unused
     * @param structureAccessor unused
     * @param chunkGenerator unused
     * @param random unused
     * @param box unused
     * @param chunkPos unused
     * @param pieces used to get the y-level of the bottom of the Mansion
     * @param ci unused
     */
    @Inject(method = "Lnet/minecraft/world/gen/structure/WoodlandMansionStructure;postPlace(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/structure/StructurePiecesList;)V", at = @At("HEAD"))
    private void no_void_structure_getMansionYLevel_WMS(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox box, ChunkPos chunkPos, StructurePiecesList pieces, CallbackInfo ci) {
        this.mansionYLevel = pieces.getBoundingBox().getMinY();
    }

}
