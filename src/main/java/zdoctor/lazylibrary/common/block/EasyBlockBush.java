package zdoctor.lazylibrary.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zdoctor.lazylibrary.common.api.IEasyBlock;
import zdoctor.lazylibrary.common.base.EasyBlock;

public class EasyBlockBush extends BlockBush implements IEasyBlock {

	private EasyBlock easyBlock;

	public EasyBlockBush(String name, Item crop) {
		this(name, crop, Material.PLANTS);
	}

	public EasyBlockBush(String name, Item crop, Material material) {
		super(material);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		easyBlock = new EasyBlock(name, this) {
			@Override
			public ItemBlock createItemBlock(Block block) {
				return easyBlock.createItemBlock(block);
			}
		};
	}

	@Override
	public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
		if (state.getBlock() == this) {
			IBlockState soil = worldIn.getBlockState(pos.down());
			return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
		}
		return this.canSustainBush(worldIn.getBlockState(pos.down()));
	}

	@Override
	public ItemBlock createItemBlock(Block block) {
		return null;
	}

	@Override
	public EasyBlock addSubtype(String subName) {
		return easyBlock.addSubtype(subName);
	}

	@Override
	public EasyBlock setContainerItem(Item item) {
		return easyBlock.setContainerItem(item);
	}

}
