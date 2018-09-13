package zdoctor.lazylibrary.common;

import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import zdoctor.lazylibrary.ModMain;
import zdoctor.lazylibrary.common.api.ITileEntity;
import zdoctor.lazylibrary.common.item.crafting.RecipeBuilder;
import zdoctor.lazylibrary.common.library.EasyRegistry;

/**
 * This class handles the registration of all items, blocks and entities
 *
 */
public class RegistryHandler extends EasyRegistry {
	private static final HashMap<ModContainer, Integer> ENTITY_MAP = new HashMap<>();

	/**
	 * Used internally
	 */
	@SubscribeEvent
	@Deprecated
	public void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ITEM_REGISTRY.toArray(new Item[] {}));
	}

	/**
	 * Used internally
	 */
	@SubscribeEvent
	@Deprecated
	public void registerBlocks(RegistryEvent.Register<Block> event) {
		BLOCK_REGISTRY.forEach(
				block -> System.out.println("Added:" + block.getUnlocalizedName() + " -> " + block.getRegistryName()));
		event.getRegistry().registerAll(BLOCK_REGISTRY.toArray(new Block[] {}));
	}

	/**
	 * Used internally
	 */
	@SubscribeEvent
	@Deprecated
	public void registerRecipes(RegistryEvent.Register<IRecipe> event) {
		RECIPE_REGISTRY.forEach(iCraftable -> {
			Loader.instance().setActiveModContainer(
					Loader.instance().getIndexedModList().get(iCraftable.getRegistryName().getResourceDomain()));
			if (iCraftable.ignoreNBT()) {
				IRecipe recipe = iCraftable.getRecipe();
				if (recipe != null)
					event.getRegistry().register(recipe);
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

	/**
	 * Used internally
	 */
	@Deprecated
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
				registerEntity(autoRegister.getRegistryName(), autoRegister.getName(), autoRegister.getEntityClass(),
						id, mod.getMod(), autoRegister.getTrackingRange(), autoRegister.getUpdateFrequency(),
						autoRegister.sendsVelocityUpdates(), autoRegister.getPrimaryColor(),
						autoRegister.getSecondaryColor());
			else
				registerEntity(autoRegister.getRegistryName(), autoRegister.getName(), autoRegister.getEntityClass(),
						id, mod.getMod(), autoRegister.getTrackingRange(), autoRegister.getUpdateFrequency(),
						autoRegister.sendsVelocityUpdates());
		});

		ENTITY_TRACKER.forEach(auto -> autoRegisterEntity(auto.getEntityName(), auto.getEntityClass(), auto.getModId(),
				auto.getTrackingRange(), auto.getUpdateFrequency(), auto.sendsVelocityUpdates()));
		Loader.instance().setActiveModContainer(Loader.instance().getIndexedModList().get(ModMain.MODID));

		TILE_ENTITY_BLOCK_REGISTRY.forEach(autoRegister -> {
			if (autoRegister instanceof ITileEntity)
				GameRegistry.registerTileEntity(((ITileEntity) autoRegister).getEntityClass(),
						autoRegister.getRegistryName().toString());
		});
	}
	
	/**
	 * Used internally
	 */
	@Deprecated
	public static void registerWorldGen() {
		WORLD_GEN.forEach(gen -> {
			GameRegistry.registerWorldGenerator(gen, gen.getGenWeight());
		});
	}

	/**
	 * Registers the Mod Entity
	 * 
	 * @param registryName
	 *            The registery name of the entity
	 * @param entityName
	 *            A unique name for the entity
	 * @param entityClass
	 *            The entity class
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
	public static void registerEntity(ResourceLocation registryName, String entityName,
			Class<? extends Entity> entityClass, int id, Object mod, int trackingRange, int updateFrequency,
			boolean sendsVelocityUpdates) {
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, mod, trackingRange, updateFrequency,
				sendsVelocityUpdates);

	}

	/**
	 * Registers the mod entity with a spawn egg.
	 * 
	 * @param registryName
	 *            The registery name of the entity
	 * @param entityName
	 *            A unique name for the entity
	 * @param entityClass
	 *            The entity class
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
	public static void registerEntity(ResourceLocation registryName, String entityName,
			Class<? extends Entity> entityClass, int id, Object mod, int trackingRange, int updateFrequency,
			boolean sendsVelocityUpdates, int primaryEggColor, int secondaryEggColor) {
		System.out.println("Reg: " + registryName + ", " + entityClass + ", " + entityName + ", " + id + ", " + mod
				+ ", " + trackingRange + ", " + updateFrequency + ", " + sendsVelocityUpdates + ", " + primaryEggColor
				+ ", " + secondaryEggColor);
		EntityRegistry.registerModEntity(registryName, entityClass, entityName, id, mod, trackingRange, updateFrequency,
				sendsVelocityUpdates, primaryEggColor, secondaryEggColor);

	}

	/**
	 * Registers a Mod Entity, creating with some generated values
	 * 
	 * @param entityName
	 *            A unique name for the entity
	 * @param entityClass
	 *            The entity class
	 * @param modId
	 *            The ModId of the mod
	 * @param trackingRange
	 *            The range at which MC will send tracking updates
	 * @param updateFrequency
	 *            The frequency of tracking updates
	 * @param sendsVelocityUpdates
	 *            Whether to send velocity information packets as well
	 */
	public static void autoRegisterEntity(String entityName, Class<? extends Entity> entityClass, String modId,
			int trackingRange, int updateFrequency, boolean sendsVelocityUpdates) {
		ModContainer mod = Loader.instance().getIndexedModList().get(modId);
		Loader.instance().setActiveModContainer(mod);
		int id = ENTITY_MAP.getOrDefault(mod, 0);
		ENTITY_MAP.put(mod, id + 1);
		EntityRegistry.registerModEntity(new ResourceLocation(mod.getModId(), entityName), entityClass, entityName, id,
				modId, trackingRange, updateFrequency, sendsVelocityUpdates);

	}

}
