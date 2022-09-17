package io.github.marinersfan824.racemod.mixin.rng;

import io.github.marinersfan824.racemod.RNGStreamGenerator;
import io.github.marinersfan824.racemod.mixinterface.ILevelProperties;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin {

    @Shadow public abstract int profession();
    @Shadow protected abstract float method_4566(float f);
    private boolean hasTrade;

    // guarantees eye trade
    @Redirect(method = "method_3111", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;method_4566(F)F", ordinal = 57))
    private float guaranteeEye(VillagerEntity villager, float f) {
        if (hasTrade) {
            return method_4566(f);
        }
        return 1.0f;
    }

    // guarantees book trade
    @Redirect(method = "method_3111", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;method_4566(F)F", ordinal = 49))
    private float guaranteeBook1(VillagerEntity villager, float f) {
        if (hasTrade) {
            return method_4566(f);
        }
        return -1.0f;
    }

    // guarantees book trade
    @Redirect(method = "method_3111", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;method_4566(F)F", ordinal = 50))
    private float guaranteeBook2(VillagerEntity villager, float f) {
        if (hasTrade) {
            return method_4566(f);
        }
        return 1.0f;
    }

    // prevents the eye and book trade from being shuffled
    @Redirect(method = "method_3111", at = @At(value = "INVOKE", target = "Ljava/util/Collections;shuffle(Ljava/util/List;)V"))
    private void guaranteeEye(List<Object> list) {
        int profession = this.profession();
        if ((profession == 2 || profession == 1) && !hasTrade) {
            Object obj = list.remove(0);
            Collections.shuffle(list);
            list.add(0, obj);
            hasTrade = true;
            return;
        }
        Collections.shuffle(list);
    }

    // standardizes the price of the eye
    @Inject(method = "method_3112", at = @At("HEAD"), cancellable = true)
    private static void standardizedEyePrice(Item item, Random random, CallbackInfoReturnable<Integer> cir) {
        if (item == Items.EYE_OF_ENDER) {
            World overWorld = MinecraftServer.getServer().getWorld();
            RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) overWorld.getLevelProperties()).getRngStreamGenerator();
            long seedResult = rngStreamGenerator.getAndUpdateSeed("eyeTradeSeed");
            cir.setReturnValue(7 + (int) seedResult % 4);
        }
    }

    // standardizes the book trade
    @Inject(method = "method_3109", at = @At("HEAD"), cancellable = true)
    private static void standardizedBookPrice(Item item, Random random, CallbackInfoReturnable<Integer> cir) {
        if (item == Items.BOOK) {
            World overWorld = MinecraftServer.getServer().getWorld();
            RNGStreamGenerator rngStreamGenerator = ((ILevelProperties) overWorld.getLevelProperties()).getRngStreamGenerator();
            long seedResult = rngStreamGenerator.getAndUpdateSeed("bookTradeSeed");
            cir.setReturnValue(11 + (int) seedResult % 2);
        }
    }

}
