package zdoctor.lazylibrary.common.item;

import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import zdoctor.lazylibrary.common.api.IEasyItem;
import zdoctor.lazylibrary.common.base.EasyItem;

public class EasyItemSlab extends ItemSlab implements IEasyItem {

	private EasyItem easyItem;

	public EasyItemSlab(BlockSlab singleSlab, BlockSlab doubleSlab) {
		super(singleSlab, singleSlab, doubleSlab);
		easyItem = new EasyItem(singleSlab.getUnlocalizedName(), this);
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
