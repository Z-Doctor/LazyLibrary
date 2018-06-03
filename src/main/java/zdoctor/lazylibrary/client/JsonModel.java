package zdoctor.lazylibrary.client;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.stream.JsonReader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

/**
 * A planned feature
 */
@Deprecated()
public class JsonModel extends ModelBase {
	protected ModelResourceLocation location;
	private int version;
	private String texture;
	private int texWidth;
	private int texHeight;

	public JsonModel(ModelResourceLocation location) {
		this.location = location;
		try {
			createModel(new JsonReader(new InputStreamReader(
					Minecraft.getMinecraft().getResourceManager().getResource(location).getInputStream())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void createModel(JsonReader jsonReader) throws IOException {
		jsonReader.beginObject();
		version = jsonReader.nextInt();
		if (version != 1)
			return;

		texture = jsonReader.nextName();
		System.out.println("Texture: " + texture);
		texWidth = jsonReader.nextInt();
		texHeight = jsonReader.nextInt();
		System.out.println("Texture Offset: " + texWidth + ", " + texHeight);
		jsonReader.endObject();
		jsonReader.close();
	}

	public ModelRenderer createModel(String name) {
		return new ModelRenderer(this, name);
	}

	public ModelRenderer setTextureOffset(ModelRenderer modelRenderer, int x, int y) {
		return modelRenderer.setTextureOffset(x, y);
	}

	public ModelRenderer addChild(ModelRenderer modelRenderer, float offX, float offY, float offZ, int width,
			int height, int depth, boolean mirrored) {
		return modelRenderer.addBox(offX, offY, offZ, width, height, depth, mirrored);
	}

	public ModelRenderer setRotation(ModelRenderer modelRenderer, float rotationPointXIn, float rotationPointYIn,
			float rotationPointZIn) {
		modelRenderer.setRotationPoint(rotationPointXIn, rotationPointYIn, rotationPointZIn);
		return modelRenderer;
	}
}
