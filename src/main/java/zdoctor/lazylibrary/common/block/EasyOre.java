package zdoctor.lazylibrary.common.block;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EasyOre extends BlockOre {
	private int maxXp;
	private int xp;
	private int minXP;
	private Item itemDrop;

	public EasyOre(String unlocalizedName) {
		this(unlocalizedName, Material.ROCK.getMaterialMapColor());
	}

	public EasyOre(String unlocalizedName, MapColor color) {
		super(color);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		itemDrop = Item.getItemFromBlock(this);
	}

	public EasyOre setMaxExp(int maxXP) {
		this.maxXp = maxXP;
		this.maxXp = Math.max(0, maxXP);
		return this;
	}

	public EasyOre setMinExp(int minXP) {
		this.minXP = minXP;
		this.minXP = Math.max(0, minXP);
		return this;
	}

	public EasyOre setXP(int xp) {
		this.xp = Math.max(0, xp);
		return this;
	}

	public EasyOre setDrop(Item itemDrop) {
		this.itemDrop = itemDrop;
		return this;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return itemDrop;
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		Random rand = world instanceof World ? ((World) world).rand : new Random();
		if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
			if (maxXp > 0) {
				return MathHelper.getInt(rand, minXP, maxXp);
			} else
				return xp;
		}
		return 0;
	}

}
