package zdoctor.lazylibrary.common.library;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.api.IAutoRegisterLivingEntity;
import zdoctor.lazylibrary.common.api.ICraftable;
import zdoctor.lazylibrary.common.api.ISmeltable;

public class EasyRegistry {
	public static final Map<Block, Item> BLOCK_TO_ITEM = net.minecraftforge.registries.GameData.getBlockItemMap();
	
	protected static final ArrayList<Item> ITEM_REGISTRY = new ArrayList<>();
	protected static final ArrayList<Block> BLOCK_REGISTRY = new ArrayList<>();
	protected static final ArrayList<ICraftable> RECIPE_REGISTRY = new ArrayList<>();
	protected static final ArrayList<ISmeltable> SMELT_REGISTRY = new ArrayList<>();

	public static final void register(IAutoRegister autoRegister) {
		switch (autoRegister.getType()) {
		case ITEM:
			ITEM_REGISTRY.add((Item) autoRegister);
			if (autoRegister instanceof ItemBlock) {
				ItemBlock itemBlock = (ItemBlock) autoRegister;
				if (!BLOCK_TO_ITEM.containsKey(itemBlock.getBlock()) || BLOCK_TO_ITEM.get(itemBlock.getBlock()) == Items.AIR) {
					BLOCK_TO_ITEM.put(itemBlock.getBlock(), itemBlock);
				}
			}
			break;
		case BLOCK:
			BLOCK_REGISTRY.add((Block) autoRegister);
			break;
		default:
			break;
		}
		if(autoRegister instanceof ICraftable)
			RECIPE_REGISTRY.add((ICraftable) autoRegister);
		if(autoRegister instanceof ISmeltable)
			SMELT_REGISTRY.add((ISmeltable) autoRegister);
			
	}

	@SideOnly(Side.CLIENT)
	public static void register(IAutoRegisterLivingEntity easyRenderLiving) {
		
	}

}