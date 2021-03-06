package zdoctor.defaultp.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zdoctor.defaultp.init.ZBlocks;
import zdoctor.defaultp.init.ZEvents;
import zdoctor.defaultp.init.ZFluids;
import zdoctor.defaultp.init.ZItems;
import zdoctor.lazymodder.easy.registry.EasyRegistry;

public abstract class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
	}
	
	public void init(FMLInitializationEvent e) {
		
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		EasyRegistry.register(ZEvents.class);
	}
	
}