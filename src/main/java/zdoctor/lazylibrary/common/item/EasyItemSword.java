package zdoctor.lazylibrary.common.item;

import net.minecraft.item.ItemSword;
import zdoctor.lazylibrary.common.base.EasyItem;

public class EasyItemSword extends ItemSword {

	public EasyItemSword(String unlocalizedName, ToolMaterial material) {
		super(material);
		new EasyItem(unlocalizedName, this);
	}

}
