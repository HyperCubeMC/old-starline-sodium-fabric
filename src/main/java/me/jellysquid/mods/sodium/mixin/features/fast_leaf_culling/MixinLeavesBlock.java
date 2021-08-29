package me.jellysquid.mods.sodium.mixin.features.fast_leaf_culling;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.Material;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock extends Block {
    public MixinLeavesBlock() {
        super(Settings.of(Material.AIR));
        throw new AssertionError("Mixin constructor called!");
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            if (MinecraftClient.getInstance().options.graphicsMode == GraphicsMode.FAST) {
                return stateFrom.getBlock() instanceof LeavesBlock || super.isSideInvisible(state, stateFrom, direction);
            }
        }
        return super.isSideInvisible(state, stateFrom, direction);
    }
}
