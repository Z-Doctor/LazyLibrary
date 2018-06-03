package zdoctor.lazylibrary.common.api;

import net.minecraft.util.ResourceLocation;

/**
 * An interface to sort out what is being registered and how to register it
 *
 */
public interface IAutoRegister {

	public RegisterType getType();

	public int getSubCount();

	public static enum RegisterType {
		ITEM,
		BLOCK,
		TRACKING,
		ORE,
		BLOCK_TILE_ENTITY,
		ENTITY;
	}

	public String getSubName(int meta);

	public ResourceLocation getRegistryName();
}
