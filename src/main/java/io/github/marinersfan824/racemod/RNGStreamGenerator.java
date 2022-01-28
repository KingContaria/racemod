package io.github.marinersfan824.racemod;

import net.minecraft.world.level.LevelProperties;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RNGStreamGenerator {
    private static HashMap<String, RNGStreamGenerator> instances = new HashMap<>();
    private long enderEyeSeed;
    private long enderPearlSeed;
    private long blazeRodSeed;
    private long featherSeed;

    public static RNGStreamGenerator getInstance(String id) {
        if (!instances.containsKey(id)) {
            instances.put(id, new RNGStreamGenerator());
        }
        return instances.get(id);
    }

    public void initializeEyeSeed(long worldSeed) {
        long seed = worldSeed ^ 0x99A2B75BBL;
        Random stream = new Random(seed);
        for (int i = 0; i < 24; i++) {
            seed = stream.nextLong();
        }
        this.enderEyeSeed = seed;
    }

    public void initializeBlazeRodSeed(long worldSeed) {
        long seed = worldSeed ^ 0x1111101101000010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 43; i++) {
            seed = stream.nextLong();
        }
        this.blazeRodSeed = seed;
    }

    public void initializePearlSeed(long worldSeed) {
        long seed = worldSeed ^ 0xF111B7010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 97; i++) {
            seed = stream.nextLong();
        }
        this.enderPearlSeed = seed;
    }

    public void initializeFeatherSeed(long worldSeed) {
        long seed = worldSeed ^ 0x1111101101000010L;
        Random stream = new Random(seed);
        for (int i = 0; i < 43; i++) {
            seed = stream.nextLong();
        }
        this.featherSeed = seed;
    }

    public long updateAndGetEnderEyeSeed() {
        Random stream = new Random(this.enderEyeSeed);
        long ret = Math.abs((int)this.enderEyeSeed) % (int)Math.pow(16.0D, 4.0D);
        this.enderEyeSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetEnderPearlSeed() {
        Random stream = new Random(this.enderPearlSeed);
        long ret = Math.abs((int)this.enderPearlSeed) % (int)Math.pow(16.0D, 4.0D);
        this.enderPearlSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetBlazeRodSeed() {
        Random stream = new Random(this.blazeRodSeed);
        long ret = Math.abs((int)this.blazeRodSeed) % (int)Math.pow(16.0D, 4.0D);
        this.blazeRodSeed = stream.nextLong();
        return ret;
    }

    public long updateAndGetFeatherSeed() {
        Random stream = new Random(this.featherSeed);
        long ret = Math.abs((int)this.featherSeed) % (int)Math.pow(16.0D, 5.0D);
        this.featherSeed = stream.nextLong();
        return ret;
    }

    public long getEnderEyeSeed() {
        return this.enderEyeSeed;
    }

    public long getEnderPearlSeed() {
        return this.enderPearlSeed;
    }

    public long getBlazeRodSeed() {
        return this.blazeRodSeed;
    }

    public long getFeatherSeed() {
        return this.featherSeed;
    }

    public void setEnderEyeSeed(long seed) {
        this.enderEyeSeed = seed;
    }

    public void setEnderPearlSeed(long seed) {
        this.enderPearlSeed = seed;
    }

    public void setBlazeRodSeed(long seed) {
        this.blazeRodSeed = seed;
    }

    public void setFeatherSeed(long seed) {
        this.featherSeed = seed;
    }
}
