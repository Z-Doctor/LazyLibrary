package zdoctor.lazylibrary.common.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public interface ISmeltable {

	public ItemStack getInput();

	public ItemStack getOutput();

	public float expReward();

}
