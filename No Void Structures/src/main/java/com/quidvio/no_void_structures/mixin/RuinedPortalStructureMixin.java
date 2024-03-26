package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.gen.structure.RuinedPortalStructure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RuinedPortalStructure.class)
public class RuinedPortalStructureMixin {

    @ModifyConstant(method = "Lnet/minecraft/world/gen/structure/RuinedPortalStructure;getFloorHeight(Lnet/minecraft/util/math/random/Random;Lnet/minecraft/world/gen/chunk/ChunkGenerator;Lnet/minecraft/structure/RuinedPortalStructurePiece$VerticalPlacement;ZIILnet/minecraft/util/math/BlockBox;Lnet/minecraft/world/HeightLimitView;Lnet/minecraft/world/gen/noise/NoiseConfig;)I", constant = @Constant(intValue = 15))
    private static int getFloorHeight(int constant) {
        return 0;
    }

}
