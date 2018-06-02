package zdoctor.lazylibrary.common.api;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;

public interface ICraftable {
	public IRecipe getRecipe();
	
	public default IRecipe[] getAdditionalRecipes() {
		return null;
	};
	
	public ResourceLocation getRegistryName();
	
	public default boolean ignoreNBT() {
		return true;
	};
}
