package zdoctor.lazylibrary.common.api;

import net.minecraft.util.ResourceLocation;

public interface IAutoRegister {
	
	public RegisterType getType();
	
	public int getSubCount();
	
	public static enum RegisterType {
		ITEM,
		BLOCK,
		ORE,
		TILE_ENTITY,
		ENTITY;
	}

	public String getSubName(int meta);
	
	public ResourceLocation getRegistryName();
}
