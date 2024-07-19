package com.quidvio.no_void_structures.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.gen.structure.RuinedPortalStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RuinedPortalStructure.class)
public class RuinedPortalStructureMixin {

    /**
     * Stops Ruined Portals from generating in the void.
     *
     * Due to Ruined Portals having a minimum generation height, the normal method to remove void structures doesn't activate.
     *
     * Changes the constant value offset from the bottom to be 0.
     *
     * Stops void generation by letting Ruined Portals fall in the y-level range that stops void generation.
     *
     * @param constant 15, the y-level offset from the bottom
     * @return 0, so it will spawn on the bottom, and then fail generation
     */
//    @ModifyConstant(method = "getFloorHeight(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/structure/RuinedPortalStructurePiece$VerticalPlacement;ZIILnet/minecraft/util/math/BlockBox;Lnet/minecraft/world/HeightLimitView;Lnet/minecraft/world/gen/noise/NoiseConfig;)I", constant = @Constant(intValue = 15))
//    private static int no_void_structures_stopRuinedPortalVoidGen_RPS(int constant) {
//        return 0;
//    }
    @ModifyExpressionValue(method = "getFloorHeight", at = @At(value = "CONSTANT", args = "intValue=15", ordinal = 0))
    private static int no_void_structures_stopRuinedPortalVoidGen_RPS2(int constant) {
        return 0;
    }
}
