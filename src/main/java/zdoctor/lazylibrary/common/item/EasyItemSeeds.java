package zdoctor.lazylibrary.common.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import zdoctor.lazylibrary.common.api.IEasyItem;
import zdoctor.lazylibrary.common.base.EasyItem;

public class EasyItemSeeds extends ItemSeeds implements IEasyItem {
	protected Block crop;
	protected Block farmland;
	protected EasyItem easyItem;

	public EasyItemSeeds(Block crop, Block farmland) {
		super(crop, farmland);
		this.crop = crop;
		this.farmland = farmland;
		easyItem = new EasyItem(crop.getUnlocalizedName(), this);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return "item.seed." + this.getUnlocalizedName();
	}

	@Override
	public EasyItem addSubtype(String subName) {
		return easyItem.addSubtype(subName);
	}

	@Override
	public EasyItem setContainerItem(Item item) {
		return easyItem.setContainerItem(item);
	}

	@Override
	public void setBurnTime(int burnTime) {
		easyItem.setBurnTime(burnTime);
	}
}
