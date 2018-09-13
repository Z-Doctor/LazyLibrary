package zdoctor.lazylibrary.common.api;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface IAutoTracker extends IAutoRegister {

	@Override
	public default int getSubCount() {
		return 0;
	}

	@Override
	public default String getSubName(int meta) {
		return null;
	}

	@Override
	public default ResourceLocation getRegistryName() {
		return null;
	}

	String getEntityName();

	Class<? extends Entity> getEntityClass();

	String getModId();

	int getTrackingRange();

	int getUpdateFrequency();

	boolean sendsVelocityUpdates();
	
	@Override
	default Object getObject() {
		return null;
	}
}
