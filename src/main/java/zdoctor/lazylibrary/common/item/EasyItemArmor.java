package zdoctor.lazylibrary.common.item;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import zdoctor.lazylibrary.common.base.EasyItem;

public class EasyItemArmor extends ItemArmor {

	private EasyItem itemArmor;

	public EasyItemArmor(String unlocalizedName, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		itemArmor = new EasyItem(unlocalizedName, this);
	}

}