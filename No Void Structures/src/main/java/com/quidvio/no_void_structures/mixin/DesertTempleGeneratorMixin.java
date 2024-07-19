package com.quidvio.no_void_structures.mixin;


import net.minecraft.structure.DesertTempleGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(DesertTempleGenerator.class)
public class DesertTempleGeneratorMixin {


    /**
     * Stops Desert Pyramids from pillaring down.
     *
     * Stops pillaring by altering the getter fields used by a for loop that's used to iterate over the entire bottom surface.
     *
     * This is done because it can pillar down an island or into the void and  it looks weird.
     *
     * @param instance unused
     * @return 0, so the for loop escapes early
     */
    @Redirect(method = "Lnet/minecraft/structure/DesertTempleGenerator;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/structure/DesertTempleGenerator;width:I", ordinal = 3))
    private int no_void_structures_noDesertPyramidPillaringWidth_DTG(DesertTempleGenerator instance) {
        return 0;
    }

    /**
     * Stops Desert Pyramids from pillaring down.
     *
     * Stops pillaring by altering the getter fields used by a for loop that's used to iterate over the entire bottom surface.
     *
     * @param instance unused
     * @return 0, so the for loop escapes early
     */
    @Redirect(method = "Lnet/minecraft/structure/DesertTempleGenerator;generate(Lnet/minecraft/world/StructureWorldAccess;Lnet/minecraft/world/gen/StructureAccessor;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/util/math/random/Random;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/util/math/ChunkPos;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/structure/DesertTempleGenerator;depth:I", ordinal = 3))
    private int no_void_structures_noDesertPyramidPillaringDepth_DTG(DesertTempleGenerator instance) {
        return 0;
    }

}
