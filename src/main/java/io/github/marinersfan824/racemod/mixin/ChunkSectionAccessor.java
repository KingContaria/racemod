package io.github.marinersfan824.racemod.mixin;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Chunk.class)
public interface ChunkSectionAccessor {
    @Accessor
    ChunkSection[] getChunkSections();
}
