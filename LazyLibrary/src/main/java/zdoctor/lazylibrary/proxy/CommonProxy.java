package zdoctor.lazylibrary.proxy;

import org.lwjgl.util.glu.Registry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zdoctor.lazylibrary.common.RegistryHandler;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
	}
	
	public void init(FMLInitializationEvent e) {
		RegistryHandler.registerEntities();
	}
	
	public void postInit(FMLPostInitializationEvent e) {
	}
	
}