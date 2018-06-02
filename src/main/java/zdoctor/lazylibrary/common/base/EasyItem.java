package zdoctor.lazylibrary.common.base;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class EasyItem extends Item implements IAutoRegister {
	protected ArrayList<String> subNames = new ArrayList<>();
	private int burnTime = -1;

	public EasyItem(String unlocalizedName) {
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		subNames.add(unlocalizedName);
		EasyRegistry.register(this);
	}

	public EasyItem addSubtype(String subName) {
		setHasSubtypes(true);
		subNames.add(subName);
		return this;
	}

	public EasyItem setContainerItem(Item item) {
		setContainerItem(item);
		return this;
	}
	
	public void setBurnTime(int burnTime) {
		this.burnTime = burnTime;
	}
	
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return burnTime ;
	}


	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab))
			for (int i = 0; i < subNames.size(); i++)
				subItems.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (subNames.size() <= stack.getMetadata())
			return super.getUnlocalizedName(stack);
		return "item." + subNames.get(stack.getMetadata());
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
