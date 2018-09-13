package zdoctor.lazylibrary.common.world.gen;

import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.IWorldGenerator;

public class EasySingleVeinOreGen extends EasyOreGen {
	public EasySingleVeinOreGen(Block block, IWorldGenerator generator) {
		this(block.getDefaultState(), generator);
	}

	public EasySingleVeinOreGen(IBlockState state, IWorldGenerator generator) {
		super(state, 1, ALL_COMMON);
		this.customWorldGen = generator;
	}

	public EasySingleVeinOreGen(Block block, Predicate<IBlockState>... genPredicate) {
		this(block.getDefaultState(), genPredicate);
	}

	public EasySingleVeinOreGen(IBlockState state, Predicate<IBlockState>... genPredicate) {
		super(state, 1, genPredicate.length == 0 ? ALL_COMMON : combine(genPredicate));
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		IBlockState state = worldIn.getBlockState(position);
		if (state.getBlock().isReplaceableOreGen(state, worldIn, position, t -> this.predicate.test(t))) {
			worldIn.setBlockState(position, this.getOreState(), 2);
		}
		return true;
	}
}
