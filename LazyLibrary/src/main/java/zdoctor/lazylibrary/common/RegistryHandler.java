package zdoctor.lazylibrary.common;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zdoctor.lazylibrary.ModMain;
import zdoctor.lazylibrary.common.api.ICraftable;
import zdoctor.lazylibrary.common.library.EasyRegistry;
import zdoctor.lazylibrary.common.library.RecipeBuilder;

public class RegistryHandler extends EasyRegistry {
	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ITEM_REGISTRY.toArray(new Item[] {}));
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(BLOCK_REGISTRY.toArray(new Block[] {}));
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		RECIPE_REGISTRY.forEach(iCraftable -> {
			Loader.instance().setActiveModContainer(
					Loader.instance().getIndexedModList().get(iCraftable.getRegistryName().getResourceDomain()));
			if (iCraftable.ignoreNBT()) {
				event.getRegistry().register(iCraftable.getRecipe());
				IRecipe[] temp;
				if ((temp = iCraftable.getAdditionalRecipes()) != null) {
					event.getRegistry().registerAll(temp);
				}
			} else {
//				System.out.println("Changing: " + iCraftable.getRecipe().getRegistryName());
				event.getRegistry().register(RecipeBuilder.checkNbt(iCraftable.getRecipe()));
				IRecipe[] temp;
				if ((temp = iCraftable.getAdditionalRecipes()) != null) {
					for (IRecipe iRecipe : temp) {
						event.getRegistry().register(RecipeBuilder.checkNbt(iRecipe));
					}
					
				}
			}
		});
		
		SMELT_REGISTRY.forEach(iSmeltable -> {
			GameRegistry.addSmelting(iSmeltable.getInput(), iSmeltable.getOutput(), iSmeltable.expReward());
		});
		Loader.instance().setActiveModContainer(Loader.instance().getIndexedModList().get(ModMain.MODID));
	}
	

}
