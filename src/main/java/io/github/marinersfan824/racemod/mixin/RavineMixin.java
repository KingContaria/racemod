package io.github.marinersfan824.racemod.mixin;

import net.minecraft.world.chunk.ChunkBlockStateStorage;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.RavineCarver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RavineCarver.class)
public class RavineMixin extends Carver {
    @Redirect(method = "carve", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/carver/RavineCarver;carveRavine(JIILnet/minecraft/world/chunk/ChunkBlockStateStorage;DDDFFFIID)V"))
    protected void carveOverride(RavineCarver instance, long seed, int mainChunkX, int mainChunkZ, ChunkBlockStateStorage chunkStorage, double x, double y, double z, float baseWidth, float xzAngle, float yAngle, int branch, int branchCount, double heightWidthRatio) {

    }
}
