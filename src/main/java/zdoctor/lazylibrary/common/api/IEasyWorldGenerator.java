package zdoctor.lazylibrary.common.api;

import net.minecraftforge.fml.common.IWorldGenerator;

public interface IEasyWorldGenerator extends IWorldGenerator {

	public default int getGenWeight() {
		return 2;
	};

}
