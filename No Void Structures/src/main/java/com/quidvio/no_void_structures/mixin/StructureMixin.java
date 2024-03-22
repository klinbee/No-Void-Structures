package com.quidvio.no_void_structures.mixin;

import net.minecraft.world.gen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Structure.class)
public class StructureMixin {

    @Inject(method = "Lnet/minecraft/world/gen/structure/Structure;isBiomeValid(Lnet/minecraft/world/gen/structure/Structure$StructurePosition;Lnet/minecraft/world/gen/structure/Structure$Context;)Z", at = @At("RETURN"), cancellable = true)
    private static void no_void_structures_preventVoidGen_S(Structure.StructurePosition result, Structure.Context context, CallbackInfoReturnable<Boolean> cir) {
        int yPos = result.position().getY();
        if (yPos <= context.chunkGenerator().getMinimumY() + 16) {
            cir.setReturnValue(false);
        }
        cir.setReturnValue(cir.getReturnValue());

    }

}
