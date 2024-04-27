package com.quidvio.no_void_structures_mod.mixin;


import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalStructure;
import org.spongepowered.asm.mixin.Mixin;
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
    @ModifyConstant(method = "Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure;findSuitableY(Lnet/minecraft/util/RandomSource;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalPiece$VerticalPlacement;ZIILnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;)I", constant = @Constant(intValue = 15))
    private static int no_void_structures_stopRuinedPortalVoidGen_RPS(int constant) {
        return 0;
    }

}
