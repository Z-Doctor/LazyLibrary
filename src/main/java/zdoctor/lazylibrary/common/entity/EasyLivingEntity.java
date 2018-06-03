package zdoctor.lazylibrary.common.entity;

import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.FMLLog;
import zdoctor.lazylibrary.common.api.IAutoRegisterLivingEntity;
import zdoctor.lazylibrary.common.library.EasyRegistry;

/**
 * This class is used to handle LivingEntity registration. To use the class,
 * extend. The renderer for the entity should be in package
 * xxx.<modid>.client.renderer.entity.Render + [Entity Name]
 * 
 * Renderer must also have a construct with just {@link RenderManager}
 * @author Z_Doctor
 *
 */
public class EasyLivingEntity extends net.minecraftforge.registries.IForgeRegistryEntry.Impl<EasyLivingEntity>
		implements IAutoRegisterLivingEntity {
	private int trackingRange = 48;
	private int updateFrequenc = 3;
	private boolean sendsVelocityUpdates = true;
	private boolean hasEgg = false;
	private int primaryColor = 0;
	private int secondaryColor = 0;
	private Class<? extends Entity> entityClass;
	private String name;

	public EasyLivingEntity(String name, Class<? extends Entity> entityClass) {
		setRegistryName(name);
		this.name = name;
		this.entityClass = entityClass;
		EasyRegistry.register(this);
	}
	
	@Override
	public String getName() {
		return name;
	}

	public EasyLivingEntity setEggColors(int primary, int secondary) {
		setHasEgg(true);
		primaryColor = primary;
		secondaryColor = secondary;
		return this;
	}

	@Override
	public int getTrackingRange() {
		return trackingRange;
	}

	public EasyLivingEntity setTrackingRange(int trackingRange) {
		this.trackingRange = trackingRange;
		return this;
	}

	@Override
	public int getUpdateFrequency() {
		return updateFrequenc;
	}

	public EasyLivingEntity setUpdateFrequenc(int updateFrequenc) {
		this.updateFrequenc = updateFrequenc;
		return this;
	}

	@Override
	public boolean sendsVelocityUpdates() {
		return sendsVelocityUpdates;
	}

	public EasyLivingEntity setSendsVelocityUpdates(boolean sendsVelocityUpdates) {
		this.sendsVelocityUpdates = sendsVelocityUpdates;
		return this;
	}

	@Override
	public boolean hasEgg() {
		return hasEgg;
	}

	public EasyLivingEntity setHasEgg(boolean hasEgg) {
		this.hasEgg = hasEgg;
		return this;
	}

	@Override
	public int getPrimaryColor() {
		return primaryColor;
	}

	public EasyLivingEntity setPrimaryColor(int primaryColor) {
		this.primaryColor = primaryColor;
		return this;
	}

	@Override
	public int getSecondaryColor() {
		return secondaryColor;
	}

	public EasyLivingEntity setSecondaryColor(int secondaryColor) {
		this.secondaryColor = secondaryColor;
		return this;
	}

	@Override
	public Class<? extends Entity> getEntityClass() {
		return entityClass;
	}

	@Override
	public Class<? extends RenderLivingBase> getRendererClass() {
		String[] path = new String[] { getRegistryName().getResourceDomain(), getName()};
		String rendererClassPath = getEntityClass().getPackage().getName().toString().replaceAll(path[0] + "\\..*",
				path[0] + ".client.renderer.entity.Render" + path[1]);
		Class<? extends RenderLivingBase> rendererClass = null;
		try {
			rendererClass = (Class<? extends RenderLivingBase>) Class.forName(rendererClassPath);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			FMLLog.bigWarning("Could not find class '{}' for render. Skipping.", rendererClassPath);
		}
		return rendererClass;
	}

}
