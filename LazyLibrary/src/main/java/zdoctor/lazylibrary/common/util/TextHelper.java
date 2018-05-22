package zdoctor.lazylibrary.common.util;

import net.minecraft.client.resources.I18n;

public class TextHelper {
	public static String translateToLocal(String text, Object... params) {
		return I18n.format(text, params);
	}
}
