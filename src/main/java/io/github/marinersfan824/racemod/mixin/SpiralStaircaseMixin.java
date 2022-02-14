package io.github.marinersfan824.racemod.mixin;

import io.github.marinersfan824.racemod.ISpiralStaircase;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.structure.StrongholdPieces$SpiralStaircase")
public class SpiralStaircaseMixin implements ISpiralStaircase {
    @Shadow private boolean isStructureStart;
    public BlockBox portalRoomBox;

    public void setPortalRoomBox(BlockBox blockBox){
        portalRoomBox = blockBox;
    }

    @Override
    public BlockBox getPortalRoomBox() {
        return portalRoomBox;
    }

    @Inject(method="serialize",at=@At("TAIL"))
    private void serializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.isStructureStart&&this.portalRoomBox !=null){
            Vec3i pos = portalRoomBox.getCenter();
            structureNbt.putIntArray("portalRoomPos",new int[]{pos.getX(), pos.getY(), pos.getZ()});
        }
    }

    @Inject(method="deserialize",at=@At("TAIL"))
    private void deserializePortalRoomPos(CompoundTag structureNbt, CallbackInfo ci){
        if(this.isStructureStart){
            int[] pos = structureNbt.getIntArray("portalRoomPos");
            if(pos.length==3){
                this.portalRoomBox = new BlockBox(pos[0],pos[1],pos[2],pos[0],pos[1],pos[2]);
            }
        }
    }
}
