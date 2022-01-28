package io.github.marinersfan824.racemod.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.LakesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(LakesFeature.class)
public class LakesMixin {
    @Shadow
    private Block field_7529;

    /**
     * @author marinersfan824
     * @reason remove underground lava and water lakes
     */
    @Overwrite
    public boolean generate(World world, Random random, BlockPos blockPos) {
        if (blockPos.getY() <= 60) {
            return false;
        } else {
            for (blockPos = blockPos.add(-8, 0, -8); blockPos.getY() > 5 && world.isAir(blockPos); blockPos = blockPos.down()) {
            }

            if (blockPos.getY() <= 4) {
                return false;
            } else {
                blockPos = blockPos.down(4);
                boolean[] bls = new boolean[2048];
                int i = random.nextInt(4) + 4;

                int ae;
                for (ae = 0; ae < i; ++ae) {
                    double d = random.nextDouble() * 6.0D + 3.0D;
                    double e = random.nextDouble() * 4.0D + 2.0D;
                    double f = random.nextDouble() * 6.0D + 3.0D;
                    double g = random.nextDouble() * (16.0D - d - 2.0D) + 1.0D + d / 2.0D;
                    double h = random.nextDouble() * (8.0D - e - 4.0D) + 2.0D + e / 2.0D;
                    double k = random.nextDouble() * (16.0D - f - 2.0D) + 1.0D + f / 2.0D;

                    for (int l = 1; l < 15; ++l) {
                        for (int m = 1; m < 15; ++m) {
                            for (int n = 1; n < 7; ++n) {
                                double o = ((double) l - g) / (d / 2.0D);
                                double p = ((double) n - h) / (e / 2.0D);
                                double q = ((double) m - k) / (f / 2.0D);
                                double r = o * o + p * p + q * q;
                                if (r < 1.0D) {
                                    bls[(l * 16 + m) * 8 + n] = true;
                                }
                            }
                        }
                    }
                }

                int ad;
                int af;
                boolean bl2;
                for (ae = 0; ae < 16; ++ae) {
                    for (af = 0; af < 16; ++af) {
                        for (ad = 0; ad < 8; ++ad) {
                            bl2 = !bls[(ae * 16 + af) * 8 + ad] && (ae < 15 && bls[((ae + 1) * 16 + af) * 8 + ad] || ae > 0 && bls[((ae - 1) * 16 + af) * 8 + ad] || af < 15 && bls[(ae * 16 + af + 1) * 8 + ad] || af > 0 && bls[(ae * 16 + (af - 1)) * 8 + ad] || ad < 7 && bls[(ae * 16 + af) * 8 + ad + 1] || ad > 0 && bls[(ae * 16 + af) * 8 + (ad - 1)]);
                            if (bl2) {
                                Material material = world.getBlockState(blockPos.add(ae, ad, af)).getBlock().getMaterial();
                                if (ad >= 4 && material.isFluid()) {
                                    return false;
                                }

                                if (ad < 4 && !material.hasCollision() && world.getBlockState(blockPos.add(ae, ad, af)).getBlock() != this.field_7529) {
                                    return false;
                                }
                            }
                        }
                    }
                }

                for (ae = 0; ae < 16; ++ae) {
                    for (af = 0; af < 16; ++af) {
                        for (ad = 0; ad < 8; ++ad) {
                            if (bls[(ae * 16 + af) * 8 + ad]) {
                                world.setBlockState(blockPos.add(ae, ad, af), ad >= 4 ? Blocks.AIR.getDefaultState() : this.field_7529.getDefaultState(), 2);
                            }
                        }
                    }
                }

                for (ae = 0; ae < 16; ++ae) {
                    for (af = 0; af < 16; ++af) {
                        for (ad = 4; ad < 8; ++ad) {
                            if (bls[(ae * 16 + af) * 8 + ad]) {
                                BlockPos blockPos2 = blockPos.add(ae, ad - 1, af);
                                if (world.getBlockState(blockPos2).getBlock() == Blocks.DIRT && world.getLightAtPos(LightType.SKY, blockPos.add(ae, ad, af)) > 0) {
                                    Biome biome = world.getBiome(blockPos2);
                                    if (biome.topBlock.getBlock() == Blocks.MYCELIUM) {
                                        world.setBlockState(blockPos2, Blocks.MYCELIUM.getDefaultState(), 2);
                                    } else {
                                        world.setBlockState(blockPos2, Blocks.GRASS.getDefaultState(), 2);
                                    }
                                }
                            }
                        }
                    }
                }

                if (this.field_7529.getMaterial() == Material.LAVA) {
                    for (ae = 0; ae < 16; ++ae) {
                        for (af = 0; af < 16; ++af) {
                            for (ad = 0; ad < 8; ++ad) {
                                bl2 = !bls[(ae * 16 + af) * 8 + ad] && (ae < 15 && bls[((ae + 1) * 16 + af) * 8 + ad] || ae > 0 && bls[((ae - 1) * 16 + af) * 8 + ad] || af < 15 && bls[(ae * 16 + af + 1) * 8 + ad] || af > 0 && bls[(ae * 16 + (af - 1)) * 8 + ad] || ad < 7 && bls[(ae * 16 + af) * 8 + ad + 1] || ad > 0 && bls[(ae * 16 + af) * 8 + (ad - 1)]);
                                if (bl2 && (ad < 4 || random.nextInt(2) != 0) && world.getBlockState(blockPos.add(ae, ad, af)).getBlock().getMaterial().hasCollision()) {
                                    world.setBlockState(blockPos.add(ae, ad, af), Blocks.STONE.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }

                if (this.field_7529.getMaterial() == Material.WATER) {
                    for (ae = 0; ae < 16; ++ae) {
                        for (af = 0; af < 16; ++af) {
                            int ag = 4;
                            if (world.method_8566(blockPos.add(ae, ag, af))) {
                                world.setBlockState(blockPos.add(ae, ag, af), Blocks.ICE.getDefaultState(), 2);
                            }
                        }
                    }
                }
                return true;

            }
        }
    }

}
