package zdoctor.lazylibrary;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import zdoctor.lazylibrary.proxy.CommonProxy;

@Mod(modid = ModMain.MODID, version = "1.1.0.0")
public class ModMain {
	public static final String MODID = "lazylibrary";

	@Instance
	public static ModMain mod = new ModMain();

	@SidedProxy(clientSide = "zdoctor.lazylibrary.proxy.ClientProxy", serverSide = "zdoctor.lazylibrary.proxy.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

}