package io.github.marinersfan824.racemod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class TempleMobSpawnMixin {
    @Inject(method = "method_3087", at = @At("HEAD"), cancellable = true)
    private void checkIfInTemple(CallbackInfoReturnable<Boolean> cir) {
        Entity thisEntity = (Entity) (Object) this;
        BlockPos blockPos = new BlockPos(thisEntity.x, thisEntity.getBoundingBox().minY, thisEntity.z);
        Block standingBlock = thisEntity.world.getBlockAt(blockPos.down());
        long timeOfDay = thisEntity.world.getTimeOfDay() % 24000;
        if ((standingBlock == Blocks.SANDSTONE || standingBlock == Blocks.WOOL || standingBlock == Blocks.TERRACOTTA) && timeOfDay < 13000) {
            cir.setReturnValue(false);
        }
    }
}
