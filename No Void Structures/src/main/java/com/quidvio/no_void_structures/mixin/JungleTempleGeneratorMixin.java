package com.quidvio.no_void_structures.mixin;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.structure.JungleTempleGenerator;
import net.minecraft.util.math.BlockBox;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(JungleTempleGenerator.class)
public class JungleTempleGeneratorMixin {

    /**
     * Adjusts the Jungle Pyramid generation to use the adjustToMinHeight method rather than adjustToAverageHeight.
     * This doesn't help with void generation, but makes it less awkward for cases where it is on the edge of islands.
     *
     * @param jungleTempleGenerator used for the alternate height adjustment
     * @param worldAccess default usage
     * @param blockBox unused
     * @param i default usage, represents the delta y
     * @return boolean representing the successful generation or not
     */
    @WrapOperation(method = "generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/structure/JungleTempleGenerator;adjustToAverageHeight(Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockBox;I)Z"))
    private boolean no_void_structures_jungleTempleUseMinHeight_JTG(JungleTempleGenerator jungleTempleGenerator, WorldAccess worldAccess, BlockBox blockBox, int i, Operation<Boolean> original) {
        return jungleTempleGenerator.adjustToMinHeight(worldAccess,i);
    }

}
