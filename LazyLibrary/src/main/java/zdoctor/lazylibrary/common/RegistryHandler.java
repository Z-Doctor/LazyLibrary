package zdoctor.lazylibrary.common;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zdoctor.lazylibrary.ModMain;
import zdoctor.lazylibrary.common.entity.EasyLivingEntity;
import zdoctor.lazylibrary.common.library.EasyRegistry;
import zdoctor.lazylibrary.common.library.RecipeBuilder;

public class RegistryHandler extends EasyRegistry {
	private static final HashMap<ModContainer, Integer> ENTITY_MAP = new HashMap<>();

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
			// System.out.println("Changing: " + iCraftable.getRecipe().getRegistryName());
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

	public static void registerEntities() {
		ENTITY_REGISTRY.forEach(autoRegister -> {
			ModContainer mod = Loader.instance().getIndexedModList()
					.get(autoRegister.getRegistryName().getResourceDomain());
			int id = ENTITY_MAP.getOrDefault(mod, 0);
			ENTITY_MAP.put(mod, id + 1);
			System.out.println("Registering: " + autoRegister.getRegistryName());
			System.out.println("ID:" + id);
			Loader.instance().setActiveModContainer(mod);
			if (autoRegister.hasEgg())
				registerEntity(autoRegister.getRegistryName(), autoRegister.getName(), autoRegister.getEntityClass(), id, mod.getMod(),
						autoRegister.getTrackingRange(), autoRegister.getUpdateFrequency(),
						autoRegister.sendsVelocityUpdates(), autoRegister.getPrimaryColor(),
						autoRegister.getSecondaryColor());
			else
				registerEntity(autoRegister.getRegistryName(), autoRegister.getName(), autoRegister.getEntityClass(), id, mod.getMod(),
						autoRegister.getTrackingRange(), autoRegister.getUpdateFrequency(),
						autoRegister.sendsVelocityUpdates());
		});
		Loader.instance().setActiveModContainer(Loader.instance().getIndexedModList().get(ModMain.MODID));
	}

	/**
	 * Register the mod entity type with FML
	 * 
	 * @param entityClass
	 *            The entity class
	 * @param entityName
	 *            A unique name for the entity
	 * @param id
	 *            A mod specific ID for the entity
	 * @param mod
	 *            The mod
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 */
	public static void registerEntity(ResourceLocation registryName, String entityName, Class<? extends Entity> entityClass, int id,
			Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, mod,
				trackingRange, updateFrequency, sendsVelocityUpdates);

	}

	/**
	 * Register the mod entity type with FML This will also register a spawn egg.
	 * 
	 * @param entityClass
	 *            The entity class
	 * @param entityName
	 *            A unique name for the entity
	 * @param id
	 *            A mod specific ID for the entity
	 * @param mod
	 *            The mod
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 * @param eggPrimary
	 *            Primary egg color
	 * @param eggSecondary
	 *            Secondary egg color
	 */
	public static void registerEntity(ResourceLocation registryName, String entityName, Class<? extends Entity> entityClass, int id,
			Object mod, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryEggColor,
			int secondaryEggColor) {
		System.out.println("Reg: " + registryName + ", " + entityClass + ", " + entityName + ", " + id + ", " + mod + ", " +
				trackingRange + ", " + updateFrequency + ", " + sendsVelocityUpdates + ", " + primaryEggColor + ", " + secondaryEggColor);
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, mod,
				trackingRange, updateFrequency, sendsVelocityUpdates, primaryEggColor, secondaryEggColor);

	}

}
