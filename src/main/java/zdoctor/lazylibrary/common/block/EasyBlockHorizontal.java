package zdoctor.lazylibrary.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.util.EnumFacing;
import zdoctor.lazylibrary.common.base.EasyBlock;

public abstract class EasyBlockHorizontal extends EasyBlock {
	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	protected EasyBlockHorizontal(String name, Material materialIn) {
		super(name, materialIn);
	}

	protected EasyBlockHorizontal(String name, Material materialIn, MapColor colorIn) {
		super(name, materialIn, colorIn);
	}
}