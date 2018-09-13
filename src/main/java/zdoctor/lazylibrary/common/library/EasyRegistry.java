package zdoctor.lazylibrary.common.library;

import java.util.ArrayList;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.api.IAutoRegisterLivingEntity;
import zdoctor.lazylibrary.common.api.IAutoTracker;
import zdoctor.lazylibrary.common.api.ICraftable;
import zdoctor.lazylibrary.common.api.IEasyWorldGenerator;
import zdoctor.lazylibrary.common.api.ISmeltable;

public class EasyRegistry {
	public static final Map<Block, Item> BLOCK_TO_ITEM = net.minecraftforge.registries.GameData.getBlockItemMap();

	protected static final ArrayList<Item> ITEM_REGISTRY = new ArrayList<>();
	protected static final ArrayList<Block> BLOCK_REGISTRY = new ArrayList<>();
	protected static final ArrayList<IAutoRegister> TILE_ENTITY_BLOCK_REGISTRY = new ArrayList<>();
	protected static final ArrayList<ICraftable> RECIPE_REGISTRY = new ArrayList<>();
	protected static final ArrayList<ISmeltable> SMELT_REGISTRY = new ArrayList<>();
	protected static final ArrayList<IAutoRegisterLivingEntity> ENTITY_REGISTRY = new ArrayList<>();
	protected static final ArrayList<IAutoTracker> ENTITY_TRACKER = new ArrayList<>();
	protected static final ArrayList<IEasyWorldGenerator> WORLD_GEN = new ArrayList<>();

	public static final void register(IAutoRegister autoRegister) {
		System.out.println("Added " + autoRegister.getRegistryName());
		switch (autoRegister.getType()) {
		case ITEM:
			ITEM_REGISTRY.add((Item) autoRegister.getObject());
			if (autoRegister instanceof ItemBlock) {
				ItemBlock itemBlock = (ItemBlock) autoRegister.getObject();
				if (!BLOCK_TO_ITEM.containsKey(itemBlock.getBlock())
						|| BLOCK_TO_ITEM.get(itemBlock.getBlock()) == Items.AIR) {
					BLOCK_TO_ITEM.put(itemBlock.getBlock(), itemBlock);
				}
			}
			break;
		case BLOCK:
			BLOCK_REGISTRY.add((Block) autoRegister.getObject());
			break;
		case ENTITY:
			if (IAutoRegisterLivingEntity.class.isAssignableFrom(autoRegister.getClass()))
				ENTITY_REGISTRY.add((IAutoRegisterLivingEntity) autoRegister);
			break;
		case TRACKING:
			if (autoRegister instanceof IAutoTracker)
				ENTITY_TRACKER.add((IAutoTracker) autoRegister);
			break;
		case BLOCK_TILE_ENTITY:
			BLOCK_REGISTRY.add((Block) autoRegister.getObject());
			TILE_ENTITY_BLOCK_REGISTRY.add(autoRegister);
			break;
		default:
			break;
		}
		if (autoRegister instanceof ICraftable)
			RECIPE_REGISTRY.add((ICraftable) autoRegister);
		if (autoRegister instanceof ISmeltable)
			SMELT_REGISTRY.add((ISmeltable) autoRegister);

	}

	public static ModContainer getActiveMod() {
		return Loader.instance().activeModContainer();
	}

	public static void register(IEasyWorldGenerator worldGen) {
		WORLD_GEN.add(worldGen);
	}

}
