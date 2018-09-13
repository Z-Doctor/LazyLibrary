package zdoctor.lazylibrary.common.api;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Will automatically find the {@link TileEntitySpecialRenderer} for this entity
 * and register it. Read getRenderer for proper usuage.
 *
 */
public interface ITESR {
	/**
	 * This method will attempt to find the renderer class for the given
	 * {@link TileEntity}. The follwong assumes that the entity class is in the
	 * package 'xxx.<modid>.entity.tile' and that the Renderer is named 'Render' +
	 * [getName()]
	 * 
	 * @return The {@link TileEntitySpecialRenderer}
	 */
	@SideOnly(Side.CLIENT)
	public default Class<? extends TileEntitySpecialRenderer> getRenderer() {
		String rendererClassPath = getEntityClass().getPackage().getName().toString().replaceAll("entity.tile",
				"client.renderer.entity.tile.Render" + getName());
		Class<? extends TileEntitySpecialRenderer> rendererClass = null;
		try {
			rendererClass = (Class<? extends TileEntitySpecialRenderer>) Class.forName(rendererClassPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			FMLLog.bigWarning("Could not find class '{}' for render. Skipping.", rendererClassPath);
		}
		return rendererClass;
	}

	public default Class<? extends TileEntity> getEntityClass() {
		return (Class<? extends TileEntity>) this.getClass();
	};

	public String getName();

}
