package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.thrown.EyeOfEnderEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EyeOfEnderEntity.class)
public abstract class EyeOfEnderMixin extends Entity {
    @Shadow private boolean dropsItem;
    private RNGStreamGenerator rngStreamGenerator = RNGStreamGenerator.getInstance(this.getWorld().getLevelProperties().toString());
    public EyeOfEnderMixin(World world) {
        super(world);
    }

    @Inject(method = "initTargetPos", at = @At("TAIL"))
    public void overrideEyeThrow(BlockPos pos, CallbackInfo ci) {
        int seedResult = Math.abs((int)rngStreamGenerator.updateAndGetEnderEyeSeed());
        dropsItem = seedResult % 5 > 0;
        System.out.println(dropsItem);
    }
}
