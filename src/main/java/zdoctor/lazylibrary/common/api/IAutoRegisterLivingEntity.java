package zdoctor.lazylibrary.common.api;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IAutoRegisterLivingEntity extends IAutoRegister {
	
	@Override
	public default int getSubCount() {
		return -1;
	}
	
	@Override
	public default String getSubName(int meta) {
		return null;
	}
	
	@Override
	public default RegisterType getType() {
		return RegisterType.ENTITY;
	}

	public Class<? extends Entity> getEntityClass();
	
	@SideOnly(Side.CLIENT)
	public Class<? extends RenderLivingBase> getRendererClass();

	public int getSecondaryColor();

	public int getPrimaryColor();

	public boolean hasEgg();

	public boolean sendsVelocityUpdates();

	public int getUpdateFrequency();

	public int getTrackingRange();

	public String getName();
}
