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
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class EasyBlock extends Block implements IAutoRegister {
	protected ArrayList<String> subNames = new ArrayList<>();
	protected EasyItemBlock easyItemBlock;

	public EasyBlock(String unlocalizedName) {
		this(unlocalizedName, Material.ROCK);
	}

	public EasyBlock(String unlocalizedName, Material material) {
		super(material);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		subNames.add(unlocalizedName);
		easyItemBlock = new EasyItemBlock(this);
		EasyRegistry.register(this);
	}
	
	public EasyBlock(String unlocalizedName, Material material, MapColor mapColor) {
		super(material, mapColor);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		subNames.add(unlocalizedName);
		easyItemBlock = new EasyItemBlock(this);
		EasyRegistry.register(this);
	}

	public EasyBlock addSubtype(String subName) {
		subNames.add(subName);
		return this;
	}
	
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

	public static class EasyItemBlock extends ItemBlock implements IAutoRegister {
		
		protected ArrayList<String> subNames;
		private int burnTime = -1;

		public EasyItemBlock(Block block) {
			super(block);
			setUnlocalizedName(block.getUnlocalizedName());
			setRegistryName(block.getRegistryName());
			subNames = new ArrayList<>();
			EasyRegistry.register(this);
		}

		public EasyItemBlock(EasyBlock block) {
			super(block);
			setUnlocalizedName(block.getUnlocalizedName());
			setRegistryName(block.getRegistryName());
			subNames = new ArrayList<>(block.subNames);
			setHasSubtypes(subNames.size() > 0);
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

	}
}
