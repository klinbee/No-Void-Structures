package com.quidvio.no_void_structures.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
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
    @Inject(method = "postPlace", at = @At("HEAD"))
    private void no_void_structure_getMansionYLevel_WMS(
            StructureWorldAccess world, StructureAccessor structureAccessor,
            ChunkGenerator chunkGenerator, Random random, BlockBox box,
            ChunkPos chunkPos, StructurePiecesList pieces, CallbackInfo ci,
            @Share("mansionYLevel") LocalIntRef mansionYLevel
    ) {
        mansionYLevel.set(pieces.getBoundingBox().getMinY());
    }
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
    @WrapOperation(method = "postPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getBottomY()I"))
//    @Redirect(method = "postPlace", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/StructureWorldAccess;getBottomY()I"))
    private int no_void_structure_modifyMansionPillaring_WMS(StructureWorldAccess instance, Operation<Integer> original, @Share("mansionYLevel") LocalIntRef mansionYLevel) {
        return mansionYLevel.get() - 5;
    }


}
