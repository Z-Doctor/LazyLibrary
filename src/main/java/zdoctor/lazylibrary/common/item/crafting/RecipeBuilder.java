package zdoctor.lazylibrary.common.item.crafting;

import java.util.function.Consumer;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RecipeBuilder {
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
		ShapedRecipes recipe = new ShapedRecipes(group, width, height, ingredients, result);
		recipe.setRegistryName(result.getItem().getUnlocalizedName(result));
		// System.out.println("Registered ShapedRecipe: " + recipe.getRegistryName());
		// recipe.getIngredients().forEach(ingredient ->
		// System.out.println(ingredient.getMatchingStacks()));
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
					// System.out.println("Adding Stacks");
					// for (ItemStack stack : (ItemStack[]) item) {
					// System.out.println("Item: " + stack.getDisplayName());
					// }
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
		ShapelessRecipes recipe = new ShapelessRecipes(group, result, ingredients);
		recipe.setRegistryName(result.getItem().getUnlocalizedName(result));
		// System.out.println("Registered Shapeless: " + recipe.getRegistryName());
		// recipe.getIngredients().forEach(ingredient ->
		// System.out.println(ingredient.getMatchingStacks()));
		return recipe;
	}

	public static IRecipe checkNbt(IRecipe recipe) {
		for (int i = 0; i < recipe.getIngredients().size(); i++) {
			Ingredient modifiedIngredient = NBTIngredient.fromIngredient(recipe.getIngredients().get(i));
			// System.out.println("Modified: " + modifiedIngredient.getMatchingStacks()[0]);
			recipe.getIngredients().set(i, modifiedIngredient);
		}

		if (recipe instanceof ShapelessRecipes) {
			ResourceLocation tempRegistryName = recipe.getRegistryName();
			String group = recipe.getGroup();
			ItemStack recipeResult = recipe.getRecipeOutput();
			NonNullList<Ingredient> ingredientList = recipe.getIngredients();

			recipe = new ShapelessRecipes(group, recipeResult, ingredientList) {
				@Override
				public boolean matches(InventoryCrafting inv, World worldIn) {
					// System.out.println("Checking for match");
					int ingredientCount = 0;
					for (int i = 0; i < inv.getHeight(); ++i) {
						for (int j = 0; j < inv.getWidth(); ++j) {
							ItemStack itemstack = inv.getStackInRowAndColumn(j, i);
							if (!itemstack.isEmpty()) {
								// System.out.println("Item: " + itemstack);
								for (Ingredient ingredient : ingredientList) {
									if (ingredient.apply(itemstack))
										ingredientCount++;
								}
							}
						}
					}
					// System.out.println("Matches Found: " + ingredientCount + " of " +
					// ingredientList.size());
					return ingredientCount == ingredientList.size();
				}
			};
			recipe.setRegistryName(tempRegistryName);
		}
		// System.out.println("Modified Recipe: " + recipe.getRegistryName());
		// recipe.getIngredients().forEach(ingredient ->
		// System.out.println(ingredient.getMatchingStacks()));
		return recipe;
	}

	public static class CustomShapedRecipes extends ShapedRecipes {
		public CustomShapedRecipes(ShapedRecipes recipe) {
			super(recipe.getGroup(), recipe.recipeWidth, recipe.recipeHeight, recipe.getIngredients(),
					recipe.getRecipeOutput());
			setRegistryName(recipe.getRegistryName());
		}
	}

	public static class CustomShapelessRecipes extends ShapelessRecipes {
		public CustomShapelessRecipes(ShapelessRecipes recipe) {
			super(recipe.getGroup(), recipe.getRecipeOutput(), recipe.getIngredients());
			setRegistryName(recipe.getRegistryName());
		}
	}

}
