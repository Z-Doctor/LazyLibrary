package zdoctor.lazylibrary.common.item.crafting;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;

public class NBTIngredient extends Ingredient {
	private ItemStack[] matchingStacks;
	private ItemStack[] matchingStacksExploded;
	private IntList matchingStacksPacked;

	protected NBTIngredient(ItemStack... p_i47503_1_) {
		this.matchingStacks = p_i47503_1_;
		NonNullList<ItemStack> lst = NonNullList.create();
		for (ItemStack s : p_i47503_1_) {
			if (s.isEmpty())
				continue;
			if (s.getMetadata() == net.minecraftforge.oredict.OreDictionary.WILDCARD_VALUE)
				s.getItem().getSubItems(net.minecraft.creativetab.CreativeTabs.SEARCH, lst);
			else
				lst.add(s);
		}
		this.matchingStacksExploded = lst.toArray(new ItemStack[lst.size()]);
	}

	public static Ingredient fromIngredient(Ingredient ingredient) {
		return new NBTIngredient(ingredient.getMatchingStacks());
	}

	@Override
	public IntList getValidItemStacksPacked() {
		if (this.matchingStacksPacked == null) {
			this.matchingStacksPacked = new IntArrayList(this.matchingStacksExploded.length);

			for (ItemStack itemstack : this.matchingStacksExploded) {
				this.matchingStacksPacked.add(RecipeItemHelper.pack(itemstack));
			}

			this.matchingStacksPacked.sort(IntComparators.NATURAL_COMPARATOR);
		}

		return this.matchingStacksPacked;
	}

	@Override
	public boolean apply(ItemStack p_apply_1_) {
		if (p_apply_1_ == null) {
			return false;
		} else {
			for (ItemStack itemstack : getMatchingStacks()) {
				if (ItemStack.areItemStacksEqual(itemstack, p_apply_1_))
					return true;
				else if (ItemPotion.class.isAssignableFrom(p_apply_1_.getItem().getClass())) {
					if (ItemPotion.class.isAssignableFrom(itemstack.getItem().getClass())) {
//						System.out.println("Test Potion 1: " + PotionUtils.getPotionTypeFromNBT(p_apply_1_.getTagCompound()));
//						System.out.println("Test Potion 2: " + PotionUtils.getPotionTypeFromNBT(itemstack.getTagCompound()));
//						System.out.println("Match: " + Boolean.toString(PotionUtils.getPotionTypeFromNBT(p_apply_1_.getTagCompound()) == PotionUtils.getPotionTypeFromNBT(itemstack.getTagCompound())));
						if(PotionUtils.getPotionTypeFromNBT(p_apply_1_.getTagCompound()) == PotionUtils.getPotionTypeFromNBT(itemstack.getTagCompound()))
							return true;
					}
				}
			}

			return false;
		}
	}

	@Override
	public ItemStack[] getMatchingStacks() {
		return matchingStacksExploded;
	}

}