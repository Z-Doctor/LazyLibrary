package zdoctor.lazylibrary.common.api;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import zdoctor.lazylibrary.common.base.EasyBlock;

public interface IEasyBlock {

	ItemBlock createItemBlock(Block block);

	EasyBlock addSubtype(String subName);

	EasyBlock setContainerItem(Item item);

}
