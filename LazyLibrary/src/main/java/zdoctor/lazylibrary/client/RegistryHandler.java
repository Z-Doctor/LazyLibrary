package zdoctor.lazylibrary.client;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.library.EasyRegistry;

@SideOnly(Side.CLIENT)
public class RegistryHandler extends EasyRegistry {
	@SubscribeEvent
	public void registerModels(ModelRegistryEvent event) {
		registerItemModels();
		registerBlockModels();
	}

	private void registerItemModels() {
		ITEM_REGISTRY.forEach(item -> {
			if (item instanceof IAutoRegister) {
				IAutoRegister autoRegister = (IAutoRegister) item;
				for (int i = 0; i < autoRegister.getSubCount(); i++) {
					ModelLoader.setCustomModelResourceLocation(item, i,
							new ModelResourceLocation(
									item.getRegistryName().getResourceDomain() + ":" + autoRegister.getSubName(i),
									"inventory"));
				}
			} else {
				ModelLoader.setCustomModelResourceLocation(item, 0,
						new ModelResourceLocation(item.getRegistryName(), "inventory"));
			}
		});
	}

	private void registerBlockModels() {
		BLOCK_REGISTRY.forEach(block -> {
			if(block instanceof IAutoRegister) {
				IAutoRegister autoRegister = (IAutoRegister) block;
				for (int i = 0; i < autoRegister.getSubCount(); i++) {
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i,
							new ModelResourceLocation(
									block.getRegistryName().getResourceDomain() + ":" + autoRegister.getSubName(i),
									"inventory"));
				}
			} else {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
			}
		});
	}

}
