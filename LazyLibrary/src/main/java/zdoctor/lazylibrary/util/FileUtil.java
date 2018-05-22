package zdoctor.lazylibrary.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.filefilter.FileFilterUtils;

import it.unimi.dsi.fastutil.Arrays;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;

public class FileUtil {

	public static boolean transferFile(File source, File destination) {
		if (!source.exists() || !destination.exists()) {
			FMLLog.bigWarning("Tried to transfer with invalid sources");
			return false;
		}

		BufferedReader reader;
		BufferedWriter writer;

		try {
			reader = new BufferedReader(new InputStreamReader(getFileInputStream(source)));
			writer = new BufferedWriter(new OutputStreamWriter(getFileOutputStream(destination)));
			String sb = null;

			while ((sb = reader.readLine()) != null) {
				writer.write(sb);
			}

			reader.close();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static FileInputStream getFileInputStream(File source) {
		FileInputStream fileStream = null;
		try {
			fileStream = new FileInputStream(source);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			FMLLog.bigWarning(e.getMessage());
			FMLCommonHandler.instance().exitJava(-1, true);
		}
		return fileStream;
	}

	public static FileOutputStream getFileOutputStream(File destination) {
		FileOutputStream fileStream = null;
		try {
			fileStream = new FileOutputStream(destination);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			FMLLog.bigWarning(e.getMessage());
			FMLCommonHandler.instance().exitJava(-1, true);
		}
		return fileStream;
	}

	public static InputStream getInputStreamFromResource(ResourceLocation location) {
		try {
			return Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<File> getDirList(File source, FileFilter fileFilter) {
		if (!source.exists() || !source.isDirectory())
			return new ArrayList<>();
		List<File> dir = java.util.Arrays.asList((source.listFiles(fileFilter != null ? fileFilter : new FileFilter() {

			@Override
			public boolean accept(File pathname) {
				return true;
			}
		})));
		return dir;
	}
}
