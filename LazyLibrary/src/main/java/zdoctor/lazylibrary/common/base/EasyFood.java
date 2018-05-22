package zdoctor.lazylibrary.common.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import zdoctor.lazylibrary.common.api.IAutoRegister;
import zdoctor.lazylibrary.common.library.EasyRegistry;

/**
 * Some common values are: Food Hunger Points Saturation Apple - 4 - 0.3f
 * <p>
 * Bread - 5 -0.6f
 * <p>
 * Raw Porkchop/Beef - 3 - 0.3f
 * <p>
 * Cooked Porkchop/Beef - 8 - 0.8f
 * <p>
 * Golden Apple - 4 - 1.2f
 * <p>
 */
public class EasyFood extends ItemFood implements IAutoRegister {
	protected ArrayList<PotionEffect> effectsOnEaten = new ArrayList<>();
	protected Map<PotionEffect, Float> chanceOnEaten = new HashMap<>();
	protected ArrayList<String> subNames = new ArrayList<>();

	protected PotionEffect potionId;
	protected float potionEffectProbability;

	public EasyFood(String name, int amount) {
		this(name, amount, 0.6F);
	}

	public EasyFood(String name, int amount, float saturation) {
		this(name, amount, saturation, false);
	}

	public EasyFood(String name, int amount, boolean isWolfFood) {
		this(name, amount, 0.6F, isWolfFood);
	}

	/**
	 * @param name
	 *            The name of the food
	 * @param amount
	 *            How many hunger points are rewarded for eating this item
	 * @param saturation
	 *            How many points go into the saturation bar (a secondary bar that
	 *            destermines how fast hunger points drain)
	 * @param isWolfFood
	 *            Does a wolf it it.
	 */
	public EasyFood(String name, int amount, float saturation, boolean isWolfFood) {
		super(amount, saturation, isWolfFood);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(CreativeTabs.FOOD);
		this.setMaxStackSize(64);
		subNames.add(name);
		EasyRegistry.register(this);
	}

	public EasyFood addSubtype(String subName) {
		setHasSubtypes(true);
		subNames.add(subName);
		return this;
	}

	public EasyFood addPotionEffect(PotionEffect effect) {
		effectsOnEaten.add(effect);
		return this;
	}

	public EasyFood addEffectWithChance(PotionEffect effect, float probability) {
		this.chanceOnEaten.put(effect, probability);
		return this;
	}

	@Override
	public ItemFood setPotionEffect(PotionEffect effect, float probability) {
		this.potionId = effect;
		this.potionEffectProbability = probability;
		return this;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		applyEffect(stack, world, player);
	}

	public void applyEffect(ItemStack stack, World world, EntityLivingBase entityLiving) {
		if (world.isRemote)
			return;

		if (this.potionId != null && world.rand.nextFloat() < this.potionEffectProbability) {
			entityLiving.addPotionEffect(new PotionEffect(this.potionId));
		}
		for (PotionEffect effect : this.effectsOnEaten) {
			entityLiving.addPotionEffect(effect);
		}
		for (PotionEffect effect : chanceOnEaten.keySet()) {
			if (world.rand.nextFloat() < chanceOnEaten.get(effect))
				entityLiving.addPotionEffect(effect);
		}
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab))
			for (int i = 0; i < subNames.size(); i++)
				subItems.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (subNames.size() <= stack.getMetadata())
			return super.getUnlocalizedName(stack);
		return "item." + subNames.get(stack.getMetadata());
	}

	@Override
	public RegisterType getType() {
		return RegisterType.ITEM;
	}

	@Override
	public int getSubCount() {
		return subNames.size();
	}

	@Override
	public String getSubName(int meta) {
		return subNames.get(meta);
	}

	public static class EasyDrink extends EasyFood {
		public EasyDrink(String name, int amount) {
			this(name, amount, 0.6F);
		}

		public EasyDrink(String name, int amount, float saturation) {
			this(name, amount, saturation, false);
		}

		public EasyDrink(String name, int amount, boolean isWolfFood) {
			this(name, amount, 0.6F, isWolfFood);
		}

		public EasyDrink(String name, int amount, float saturation, boolean isWolfFood) {
			super(name, amount, saturation, isWolfFood);
		}

		@Override
		public EnumAction getItemUseAction(ItemStack stack) {
			return EnumAction.DRINK;
		}
	}

}
