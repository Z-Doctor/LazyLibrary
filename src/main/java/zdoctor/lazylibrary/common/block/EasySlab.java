package zdoctor.lazylibrary.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ICustomModelLoader;
import zdoctor.lazylibrary.common.base.EasyBlock;
import zdoctor.lazylibrary.common.item.EasyItemSlab;

public abstract class EasySlab extends BlockSlab {
	protected EasyDoubleSlab blockDouble;
	private EasyBlock easyBlock;

	public EasySlab(String name) {
		this(name, Material.WOOD);
	}

	public EasySlab(String name, Material material) {
		super(material);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		blockDouble = new EasyDoubleSlab(name, this);
		easyBlock = new EasyBlock(name, this) {
			@Override
			public ItemBlock createItemBlock(Block block) {
				return new EasyItemSlab((BlockSlab) block, blockDouble);
			}
		};
	}

	public abstract IBlockState getStateFromMeta(int meta);

	@Override
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
			i |= 8;
		}

		return i;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, HALF, getVariantProperty());
	}

	@Override
	public boolean isDouble() {
		return false;
	}

	@Override
	public String getUnlocalizedName(int meta) {
		return easyBlock.getSubName(meta);
	}

	private static class EasyDoubleSlab extends EasySlab implements ICustomModelLoader {
		private EasySlab singleSlab;

		public EasyDoubleSlab(String name, EasySlab singleSlab) {
			super(name, singleSlab.blockMaterial);
			this.singleSlab = singleSlab;
		}

		@Override
		public boolean isDouble() {
			return true;
		}

		@Override
		public IBlockState getStateFromMeta(int meta) {
			return singleSlab.getStateFromMeta(meta);
		}

		@Override
		public IProperty<?> getVariantProperty() {
			return singleSlab.getVariantProperty();
		}

		@Override
		public Comparable<?> getTypeForItem(ItemStack stack) {
			return singleSlab.getTypeForItem(stack);
		}

	}

}
