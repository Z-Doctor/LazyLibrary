package zdoctor.lazylibrary.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zdoctor.lazylibrary.ModMain;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.api.ITESR;
import zdoctor.lazylibrary.common.api.ITileEntity;
import zdoctor.lazylibrary.common.library.EasyRegistry;

/**
 * This class handles registering the models of all Items, Blocks and Living
 * Entities
 *
 */
@SideOnly(Side.CLIENT)
public class ModelRegistryHandler extends EasyRegistry {
	/**
	 * Used Internally, do not use
	 * 
	 * @param event
	 *            ModelRegistryEvent
	 */
	@SubscribeEvent
	@Deprecated
	public void registerModels(ModelRegistryEvent event) {
		registerItemModels();
		registerBlockModels();
		registerLivingEntities();
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
			if (block instanceof IAutoRegister) {
				IAutoRegister autoRegister = (IAutoRegister) block;
				for (int i = 0; i < autoRegister.getSubCount(); i++) {
					ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i,
							new ModelResourceLocation(
									block.getRegistryName().getResourceDomain() + ":" + autoRegister.getSubName(i),
									"inventory"));
				}
			} else {
				ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
						new ModelResourceLocation(block.getRegistryName(), "inventory"));
			}
		});

		TILE_ENTITY_BLOCK_REGISTRY.forEach(autoRegister -> {
			Class<? extends TileEntity> tileEntityClass = ((ITileEntity) autoRegister).getEntityClass();
			if (ITESR.class.isAssignableFrom(tileEntityClass)) {
				System.out.println("REG TileEntity Render: " + autoRegister.getRegistryName());
				try {
					Class<? extends TileEntitySpecialRenderer> renderClass = ((ITESR) tileEntityClass.newInstance())
							.getRenderer();
					TileEntitySpecialRenderer renderer = renderClass.newInstance();
					bindTileEntitySpecialRenderer((Block) autoRegister.getObject(), tileEntityClass, renderer);
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
					System.out.println("REG for TileEntity Render failed: " + autoRegister.getRegistryName());
				}
			}
		});
	}

	public static void bindTileEntitySpecialRenderer(Block block, Class<? extends TileEntity> tileEntityClass,
			TileEntitySpecialRenderer renderer) {
		try {
			ClientRegistry.bindTileEntitySpecialRenderer(tileEntityClass, renderer);
		} catch (Exception e) {
			e.printStackTrace();
			FMLLog.log.warn("Unable to register {} because {}", tileEntityClass.getName(), e.getLocalizedMessage());
		}
	}

	private void registerLivingEntities() {
		ENTITY_REGISTRY.forEach(autoRegister -> {
			Loader.instance().setActiveModContainer(
					Loader.instance().getIndexedModList().get(autoRegister.getRegistryName().getResourceDomain()));
			registerEntityRenderingHandler(autoRegister.getEntityClass(), autoRegister.getRendererClass());
		});
		Loader.instance().setActiveModContainer(Loader.instance().getIndexedModList().get(ModMain.MODID));
	}

	/**
	 * A class to register an Entities rendering
	 * 
	 * @param entityClass
	 *            The Class of the Entity
	 * @param renderFactory
	 *            The RenderFactory of the Entity
	 */
	public static void registerEntityRenderingHandler(Class<? extends Entity> entityClass,
			IRenderFactory renderFactory) {
		RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
	}

	/**
	 * A class to register an Entities rendering
	 * 
	 * @param entityClass
	 *            The Class of the Entity
	 * @param entityRender
	 *            The RenderingClass of the Entity
	 */
	public static void registerEntityRenderingHandler(Class<? extends Entity> entityClass,
			Class<? extends RenderLivingBase> entityRender) {
		registerEntityRenderingHandler(entityClass, new IRenderFactory() {

			@Override
			public Render createRenderFor(RenderManager manager) {
				try {
					return entityRender.getConstructor(RenderManager.class).newInstance(manager);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		});
	}

}
