package zdoctor.lazylibrary.common.entity;

import net.minecraft.entity.Entity;
import zdoctor.lazylibrary.common.api.IAutoRegister.RegisterType;
import zdoctor.lazylibrary.common.api.IAutoTracker;
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class EasyEntityTracking implements IAutoTracker {
	private String entityName;
	private Class<? extends Entity> entityClass;
	private String modId;
	private int trackingRange;
	private int updateFrequency;
	private boolean sendsVelocityUpdates;

	public EasyEntityTracking(String entityName, Class<? extends Entity> entityClass, String modId, int trackingRange,
			int updateFrequency, boolean sendsVelocityUpdates) {
		this.entityName = entityName;
		this.entityClass = entityClass;
		this.modId = modId;
		this.trackingRange = trackingRange;
		this.updateFrequency = updateFrequency;
		this.sendsVelocityUpdates = sendsVelocityUpdates;
		
		EasyRegistry.register(this);
	}

	@Override
	public RegisterType getType() {
		return RegisterType.TRACKING;
	}

	@Override
	public String getEntityName() {
		return entityName;
	}

	@Override
	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}

	@Override
	public String getModId() {
		return modId;
	}

	@Override
	public int getTrackingRange() {
		return trackingRange;
	}

	@Override
	public int getUpdateFrequency() {
		return updateFrequency;
	}

	@Override
	public boolean sendsVelocityUpdates() {
		return sendsVelocityUpdates;
	}
	
	

	
}
