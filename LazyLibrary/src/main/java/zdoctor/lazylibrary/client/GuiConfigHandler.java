package zdoctor.lazylibrary.client;

import java.lang.reflect.Field;

import com.google.common.collect.BiMap;

import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.common.ModContainer;
import zdoctor.lazylibrary.common.library.EasyRegistry;

public class GuiConfigHandler extends EasyRegistry {
	public static void fmlPostInit() {
		Field guiFactories;
		try {
			guiFactories = FMLClientHandler.class.getDeclaredField("guiFactories");
			guiFactories.setAccessible(true);
			BiMap<ModContainer, IModGuiFactory> guiMap = (BiMap<ModContainer, IModGuiFactory>) guiFactories
					.get(FMLClientHandler.instance());
			GUIMAP.entrySet().forEach(set -> {
				guiMap.forcePut(set.getKey(), set.getValue());
			});
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
