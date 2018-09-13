package zdoctor.lazylibrary.common.base;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.api.IEasyBlock;
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class EasyBlock extends Block implements IAutoRegister, IEasyBlock {
	protected ArrayList<String> subNames = new ArrayList<>();
	protected ItemBlock easyItemBlock;
	private Block block;

	public EasyBlock(String unlocalizedName) {
		this(unlocalizedName, Material.ROCK);
	}

	public EasyBlock(String unlocalizedName, Material material) {
		super(material);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		this.block = this;
		easyItemBlock = createItemBlock(block);
		addSubtype(unlocalizedName);
		EasyRegistry.register(this);
	}

	public EasyBlock(String unlocalizedName, Material material, MapColor mapColor) {
		super(material, mapColor);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		this.block = this;
		easyItemBlock = createItemBlock(block);
		addSubtype(unlocalizedName);
		EasyRegistry.register(this);
	}

	public EasyBlock(String unlocalizedName, Block block) {
		super(block.getMaterial(block.getDefaultState()), block.getMapColor(block.getDefaultState(), null, null));
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		this.block = block;
		block.setUnlocalizedName(unlocalizedName);
		block.setRegistryName(unlocalizedName);
		easyItemBlock = createItemBlock(block);
		addSubtype(unlocalizedName);
		EasyRegistry.register(this);
	}
	
	@Override
	public Block setCreativeTab(CreativeTabs tab) {
		if(easyItemBlock != null)
			easyItemBlock.setCreativeTab(tab);
		return super.setCreativeTab(tab);
	}

	/**
	 * Call this if you want the block to create its own item
	 */
	@Override
	public ItemBlock createItemBlock(Block block) {
		return new EasyItemBlock(block);
	}

	@Override
	public EasyBlock addSubtype(String subName) {
		if(easyItemBlock != null && easyItemBlock instanceof EasyItemBlock)
			((EasyItemBlock) easyItemBlock).addSubtype(subName);
			
		subNames.add(subName);
		return this;
	}

	@Override
	public EasyBlock setContainerItem(Item item) {
		easyItemBlock.setContainerItem(item);
		return this;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for (int i = 0; i < subNames.size(); i++)
			items.add(new ItemStack(this, 1, i));
	}

	@Override
	public RegisterType getType() {
		return RegisterType.BLOCK;
	}

	@Override
	public int getSubCount() {
		return subNames.size();
	}

	@Override
	public String getSubName(int meta) {
		return subNames.get(meta);
	}

	@Override
	public Object getObject() {
		return block;
	}

	public static class EasyItemBlock extends ItemBlock implements IAutoRegister {

		protected ArrayList<String> subNames;
		private int burnTime = -1;

		public EasyItemBlock(Block block) {
			super(block);
			setUnlocalizedName(block.getUnlocalizedName());
			setRegistryName(block.getRegistryName());
			setCreativeTab(block.getCreativeTabToDisplayOn());
			subNames = new ArrayList<>();
			setHasSubtypes(subNames.size() > 1);
			EasyRegistry.register(this);
		}

		public EasyItemBlock(EasyBlock block) {
			super(block);
			setUnlocalizedName(block.getUnlocalizedName());
			setRegistryName(block.getRegistryName());
			setCreativeTab(block.getCreativeTabToDisplayOn());
			subNames = new ArrayList<>(block.subNames);
			setHasSubtypes(subNames.size() > 1);
			EasyRegistry.register(this);
		}

		public EasyItemBlock addSubtype(String subName) {
			setHasSubtypes(true);
			subNames.add(subName);
			return this;
		}

		public EasyItemBlock setContainerItem(Item item) {
			setContainerItem(item);
			return this;
		}

		public void setBurnTime(int burnTime) {
			this.burnTime = burnTime;
		}
		
		@Override
		public int getItemBurnTime(ItemStack itemStack) {
			return burnTime;
		}

		@Override
		public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
			if (isInCreativeTab(tab))
				for (int i = 0; i < subNames.size(); i++)
					subItems.add(new ItemStack(this, 1, i));
		}

		@Override
		public Block getBlock() {
			return block;
		}

		@Override
		public String getUnlocalizedName(ItemStack stack) {
			if (subNames.size() <= stack.getMetadata())
				return super.getUnlocalizedName(stack);
			return "tile." + subNames.get(stack.getMetadata());
		}

		@Override
		public String getUnlocalizedName() {
			return "tile." + subNames.get(0);
		}

		@Override
		public RegisterType getType() {
			return RegisterType.ITEM;
		}

		@Override
		public int getSubCount() {
			return subNames.size();
		}

		@Override
		public String getSubName(int meta) {
			return subNames.get(meta);
		}

		@Override
		public Object getObject() {
			return this;
		}
		
		@Override
		public int getMetadata(ItemStack stack) {
			return super.getMetadata(stack);
		}

	}

}
