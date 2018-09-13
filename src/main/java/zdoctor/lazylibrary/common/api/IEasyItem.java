package zdoctor.lazylibrary.common.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import zdoctor.lazylibrary.common.base.EasyItem;

public interface IEasyItem {

	public EasyItem addSubtype(String subName);

	public EasyItem setContainerItem(Item item);

	public void setBurnTime(int burnTime);
	
}
