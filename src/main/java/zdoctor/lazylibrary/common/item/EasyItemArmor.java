package zdoctor.lazylibrary.common.item;

import java.util.ArrayList;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class EasyItemArmor extends ItemArmor implements IAutoRegister {
	protected ArrayList<String> subNames = new ArrayList<>();

	public EasyItemArmor(String unlocalizedName, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		setUnlocalizedName(unlocalizedName);
		setRegistryName(unlocalizedName);
		subNames.add(unlocalizedName);

		EasyRegistry.register(this);
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