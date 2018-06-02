package zdoctor.lazylibrary.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import zdoctor.lazylibrary.common.api.IAutoRegisterLivingEntity;

@SideOnly(Side.CLIENT)
public abstract class EasyRenderLiving <T extends EntityLiving> extends RenderLiving<T> implements IAutoRegisterLivingEntity {

	public EasyRenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
		super(rendermanagerIn, modelbaseIn, shadowsizeIn);
	}

}
