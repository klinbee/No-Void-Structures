package com.quidvio.no_void_structures.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.structures.WoodlandMansionStructure;
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
    @Redirect(method = "Lnet/minecraft/world/level/levelgen/structure/structures/WoodlandMansionStructure;afterPlace(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;getMinBuildHeight()I"))
    private int no_void_structure_modifyMansionPillaring_WMS(WorldGenLevel instance) {
        return mansionYLevel-5;
    }

    /**
     * Gets the bottom y-level of the Mansion, used for the method above.
     *
     *  @param worldGenLevel unused
     * @param structureManager unused
     * @param chunkGenerator unused
     * @param randomSource unused
     * @param boundingBox unused
     * @param chunkPos unused
     * @param piecesContainer used to get the y-level of the bottom of the Mansion
     * @param ci unused
     */
    @Inject(method = "Lnet/minecraft/world/level/levelgen/structure/structures/WoodlandMansionStructure;afterPlace(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/ChunkPos;Lnet/minecraft/world/level/levelgen/structure/pieces/PiecesContainer;)V", at = @At("HEAD"))
    private void no_void_structure_getMansionYLevel_WMS(WorldGenLevel worldGenLevel, StructureManager structureManager, ChunkGenerator chunkGenerator, RandomSource randomSource, BoundingBox boundingBox, ChunkPos chunkPos, PiecesContainer piecesContainer, CallbackInfo ci) {
        this.mansionYLevel = piecesContainer.calculateBoundingBox().minY();
    }

}
