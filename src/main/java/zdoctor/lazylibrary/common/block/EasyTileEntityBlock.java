package zdoctor.lazylibrary.common.block;

import org.apache.logging.log4j.Level;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import zdoctor.lazylibrary.common.api.ITESR;
import zdoctor.lazylibrary.common.api.ITileEntity;
import zdoctor.lazylibrary.common.base.EasyBlock;

public class EasyTileEntityBlock extends EasyBlock implements ITileEntity {
	protected Class<? extends TileEntity> tileEntity;

	public EasyTileEntityBlock(String name, Class<? extends TileEntity> tileEntity) {
		this(name, tileEntity, Material.IRON);
	}

	public EasyTileEntityBlock(String name, Class<? extends TileEntity> tileEntity, Material materialIn) {
		super(name, materialIn);
		setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.tileEntity = tileEntity;
		hasTileEntity = true;
	}

	@Override
	public RegisterType getType() {
		return RegisterType.BLOCK_TILE_ENTITY;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		try {
			return this.tileEntity.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Change log
			FMLLog.log.log(Level.TRACE, "Unable to create new instance of {}", this.tileEntity.getName());
		}
		return null;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		if (ITESR.class.isAssignableFrom(getEntityClass()))
			return EnumBlockRenderType.INVISIBLE;
		return super.getRenderType(state);
	}

	@Override
	public Class<? extends TileEntity> getEntityClass() {
		return tileEntity;
	}
	
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isTranslucent(IBlockState state) {
		return false;
	}

	@Override
	public boolean isNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}


}
