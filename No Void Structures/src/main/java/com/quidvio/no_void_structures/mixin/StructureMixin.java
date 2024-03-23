package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Structure.class)
public class StructureMixin {

    /**
     * Stops most Structures from generating in the void.
     *
     * Only most because some legacy structures are weird. See the other Mixins.
     *
     * If the y-level of generation is 16 blocks from the bottom or less. (-48 by default).
     * Stops void generation by returning "false" for the isValidBiome check (this).
     * Otherwise, uses the current return value.
     *
     * @param result default usage, also used to get the gen height.
     * @param context default usage, also used to get the void height.
     * @param cir the current return value for the isValidBiome check.
     */
    @Inject(method = "Lnet/minecraft/world/gen/structure/Structure;isBiomeValid(Lnet/minecraft/world/gen/structure/Structure$StructurePosition;Lnet/minecraft/world/gen/structure/Structure$Context;)Z", at = @At("RETURN"), cancellable = true)
    private static void no_void_structures_stopGenericStructureVoidGen_S(Structure.StructurePosition result, Structure.Context context, CallbackInfoReturnable<Boolean> cir) {
        int yPos = result.position().getY();
        if (yPos <= context.chunkGenerator().getMinimumY() + 16) {
            cir.setReturnValue(false);
        }
        cir.setReturnValue(cir.getReturnValue());
    }

}
