package ru.snake.spritepacker.writer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.core.packer.ImageData;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.util.Dialogs;

public class AtlasImageWriter implements CoreFactoryWalker {

	private final PackerOutput output;
	private final File destination;
	private final String format;

	private String prefix;
	private String postfix;

	public AtlasImageWriter(PackerOutput output, File destination, String format) {
		this.output = output;
		this.destination = destination;
		this.format = format;

		prefix = "";
		postfix = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	@Override
	public void start() {
		for (Entry<Integer, Dimension> atlasEntry : output.atlasSizes
				.entrySet()) {
			int index = atlasEntry.getKey();
			String name = output.getAtlasName(index);

			File file = getAtlasFile(name);

			try {
				writeAtlasImage(file, index);
			} catch (IOException e) {
				Dialogs.error(null, e.getMessage());

				break;
			}
		}
	}

	private void writeAtlasImage(File file, int index) throws IOException {
		Dimension size = output.atlasSizes.get(index);
		BufferedImage image = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = image.getGraphics();

		for (ImageData eachData : output.packedImages) {
			if (eachData.atlas == index) {
				BufferedImage textureImage = eachData.texture.image;

				g.drawImage(textureImage, eachData.x, eachData.y, null);
			}
		}

		g.dispose();

		ImageIO.write(image, format, file);
	}

	private File getAtlasFile(String name) {
		StringBuilder sb = new StringBuilder();

		sb.append(prefix);
		sb.append(name);
		sb.append(postfix);

		return new File(destination, sb.toString());
	}

	@Override
	public void end() {
	}

	@Override
	public void startAnimation(int index, String name) {
	}

	@Override
	public void endAnimation() {
	}

	@Override
	public void startSprite(int index, String name, int offsetX, int offsetY,
			Texture texture) {
	}

	@Override
	public void endSprite() {
	}

}
