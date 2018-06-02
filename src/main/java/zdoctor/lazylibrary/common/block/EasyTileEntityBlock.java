package zdoctor.lazylibrary.common.block;

import org.apache.logging.log4j.Level;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLLog;
import zdoctor.lazylibrary.common.base.EasyBlock;

public class EasyTileEntityBlock extends EasyBlock implements ITileEntityProvider {
	protected Class<? extends TileEntity> tileEntity;

	public EasyTileEntityBlock(String name, Class<? extends TileEntity> tileEntity) {
		this(name, tileEntity, Material.IRON);
	}

	public EasyTileEntityBlock(String name, Class<? extends TileEntity> tileEntity, Material materialIn) {
		super(name, materialIn);
		this.tileEntity = tileEntity;
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
	public boolean hasTileEntity() {
		return true;
	}

}
