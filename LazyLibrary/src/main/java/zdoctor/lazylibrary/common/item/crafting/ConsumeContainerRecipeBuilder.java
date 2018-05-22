package zdoctor.lazylibrary.common.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;

public class ConsumeContainerRecipeBuilder {

	public static ShapedRecipes create(String group, int width, int height, ItemStack result, Object... items) {
		NonNullList<Ingredient> ingredients = NonNullList.withSize(width * height, Ingredient.EMPTY);
		int slot = 0;
		for (Object item : items) {
			if (item == null || item == Items.AIR) {
			} else if (item.getClass().isArray()) {
				if (Item[].class.isAssignableFrom(item.getClass()))
					ingredients.set(slot, Ingredient.fromItems((Item[]) item));
				else if (ItemStack[].class.isAssignableFrom(item.getClass()))
					ingredients.set(slot, Ingredient.fromStacks((ItemStack[]) item));
				else if (Block[].class.isAssignableFrom(item.getClass())) {
					Item[] itemBlocks = new Item[((Block[]) item).length];
					for (int i = 0; i < itemBlocks.length; i++)
						itemBlocks[i] = Item.getItemFromBlock(((Block[]) item)[i]);
					ingredients.set(slot, Ingredient.fromItems(itemBlocks));
				} else if (Integer[].class.isAssignableFrom(item.getClass())) {
					Item[] itemList = new Item[((int[]) item).length];
					for (int i = 0; i < itemList.length; i++) {
						itemList[i] = Item.getItemById(((int[]) item)[i]);
					}
					ingredients.set(slot, Ingredient.fromItems(itemList));
				} else if (String[].class.isAssignableFrom(item.getClass())) {
					Item[] itemList = new Item[((String[]) item).length];
					for (int i = 0; i < itemList.length; i++) {
						itemList[i] = Item.getByNameOrId(((String[]) item)[i]);
					}
					ingredients.set(slot, Ingredient.fromItems(itemList));
				}
			} else if (Item.class.isAssignableFrom(item.getClass()))
				ingredients.set(slot, Ingredient.fromItem((Item) item));
			else if (Block.class.isAssignableFrom(item.getClass()))
				ingredients.set(slot, Ingredient.fromItem(Item.getItemFromBlock((Block) item)));
			else if (ItemStack.class.isAssignableFrom(item.getClass()))
				ingredients.set(slot, Ingredient.fromStacks((ItemStack) item));
			else if (Integer.class.isAssignableFrom(item.getClass()))
				ingredients.set(slot, Ingredient.fromItem(Item.getItemById((Integer) item)));
			else if (String.class.isAssignableFrom(item.getClass()))
				ingredients.set(slot, Ingredient.fromItem(Item.getByNameOrId((String) item)));
			slot++;
		}
		ShapedRecipes recipe = new ShapedRecipes(group, width, height, ingredients, result) {
			@Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
				NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(),
						ItemStack.EMPTY);
				return nonnulllist;
			}
		};
		recipe.setRegistryName(result.getItem().getRegistryName());
		return recipe;
	}

	public static ShapelessRecipes create(String group, ItemStack result, Object... items) {
		NonNullList<Ingredient> ingredients = NonNullList.create();
		for (Object item : items) {
			if (item == null || item == Items.AIR) {
				ingredients.add(Ingredient.EMPTY);
			} else if (item.getClass().isArray()) {
				if (Item[].class.isAssignableFrom(item.getClass()))
					ingredients.add(Ingredient.fromItems((Item[]) item));
				else if (ItemStack[].class.isAssignableFrom(item.getClass())) {
					ingredients.add(Ingredient.fromStacks((ItemStack[]) item));
				} else if (Block[].class.isAssignableFrom(item.getClass())) {
					Item[] itemBlocks = new Item[((Block[]) item).length];
					for (int i = 0; i < itemBlocks.length; i++)
						itemBlocks[i] = Item.getItemFromBlock(((Block[]) item)[i]);
					ingredients.add(Ingredient.fromItems(itemBlocks));
				} else if (Integer[].class.isAssignableFrom(item.getClass())) {
					Item[] itemList = new Item[((int[]) item).length];
					for (int i = 0; i < itemList.length; i++) {
						itemList[i] = Item.getItemById(((int[]) item)[i]);
					}
					ingredients.add(Ingredient.fromItems(itemList));
				} else if (String[].class.isAssignableFrom(item.getClass())) {
					Item[] itemList = new Item[((String[]) item).length];
					for (int i = 0; i < itemList.length; i++) {
						itemList[i] = Item.getByNameOrId(((String[]) item)[i]);
					}
					ingredients.add(Ingredient.fromItems(itemList));
				}
			} else if (Item.class.isAssignableFrom(item.getClass()))
				ingredients.add(Ingredient.fromItem((Item) item));
			else if (Block.class.isAssignableFrom(item.getClass()))
				ingredients.add(Ingredient.fromItem(Item.getItemFromBlock((Block) item)));
			else if (ItemStack.class.isAssignableFrom(item.getClass()))
				ingredients.add(Ingredient.fromStacks((ItemStack) item));
			else if (Integer.class.isAssignableFrom(item.getClass()))
				ingredients.add(Ingredient.fromItem(Item.getItemById((Integer) item)));
			else if (String.class.isAssignableFrom(item.getClass()))
				ingredients.add(Ingredient.fromItem(Item.getByNameOrId((String) item)));
		}
		ShapelessRecipes recipe = new ShapelessRecipes(group, result, ingredients) {
			@Override
			public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
				NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inv.getSizeInventory(),
						ItemStack.EMPTY);
				return nonnulllist;
			}
		};
		recipe.setRegistryName(result.getItem().getRegistryName());
		return recipe;
	}

}