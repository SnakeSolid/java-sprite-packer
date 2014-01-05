package ru.snake.spritepacker.writer;

import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ru.snake.spritepacker.core.CoreFactoryWalker;
import ru.snake.spritepacker.core.Texture;
import ru.snake.spritepacker.core.packer.ImageData;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.util.Dialogs;

public class JoinTextWriter implements CoreFactoryWalker {

	private final PackerOutput output;
	private final File destination;

	private final Map<String, String> fields;

	private String prefix;
	private String postfix;
	private String format;

	private int animationIndex;
	private int spriteIndex;

	private Writer fileWriter;
	private Writer bufferedWriter;

	public JoinTextWriter(PackerOutput output, File destination) {
		this.output = output;
		this.destination = destination;

		fields = new HashMap<String, String>();

		prefix = "";
		postfix = "";
		format = "";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	@Override
	public void start() {
		fileWriter = null;
		bufferedWriter = null;

		try {
			fileWriter = new FileWriter(getOutputFile());
		} catch (IOException ex) {
			Dialogs.error(null, ex.getMessage());

			return;
		}

		bufferedWriter = new BufferedWriter(fileWriter);

		fields.clear();

		animationIndex = 0;
	}

	private File getOutputFile() {
		StringBuilder sb = new StringBuilder();

		if (prefix.isEmpty()) {
			sb.append("description");
		} else {
			sb.append(prefix);
		}

		sb.append(postfix);

		return new File(destination, sb.toString());
	}

	@Override
	public void end() {
		if (fileWriter == null || bufferedWriter == null) {
			return;
		}

		try {
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException ex) {
			Dialogs.error(null, ex.getMessage());
		}

		fileWriter = null;
		bufferedWriter = null;
	}

	@Override
	public void startAnimation(String name) {
		if (fileWriter == null || bufferedWriter == null) {
			return;
		}

		fields.put("{animation.id}", String.valueOf(animationIndex));
		fields.put("{animation.name}", name);

		spriteIndex = 0;
	}

	@Override
	public void endAnimation() {
		animationIndex++;
	}

	@Override
	public void startSprite(String name, int offsetX, int offsetY,
			Texture texture) {
		if (fileWriter == null || bufferedWriter == null) {
			return;
		}

		fields.put("{sprite.id}", String.valueOf(spriteIndex));
		fields.put("{sprite.name}", name);
		fields.put("{sprite.offsetx}", String.valueOf(offsetX));
		fields.put("{sprite.offsety}", String.valueOf(offsetY));

		for (ImageData data : output.packedImages) {
			if (data.texture != texture) {
				continue;
			}

			String atlasName = output.getAtlasName(data.atlas);
			Dimension atlasSize = output.atlasSizes.get(data.atlas);

			fields.put("{atlas.id}", String.valueOf(data.atlas));
			fields.put("{atlas.name}", atlasName);
			fields.put("{atlas.width}", String.valueOf(atlasSize.width));
			fields.put("{atlas.height}", String.valueOf(atlasSize.height));

			fields.put("{texture.left}", String.valueOf(data.x));
			fields.put("{texture.top}", String.valueOf(data.y));
			fields.put("{texture.width}", String.valueOf(data.width));
			fields.put("{texture.height}", String.valueOf(data.height));
			fields.put("{texture.right}", String.valueOf(data.x + data.width));
			fields.put("{texture.bottom}", String.valueOf(data.y + data.height));

			fields.put("{sprite.offset.right}",
					String.valueOf(data.width - offsetX));
			fields.put("{sprite.offset.bottom}",
					String.valueOf(data.height - offsetY));

			writeLine();

			break;
		}
	}

	private void writeLine() {
		String line = format;

		for (Entry<String, String> eachEntry : fields.entrySet()) {
			line = line.replace(eachEntry.getKey(), eachEntry.getValue());
		}

		try {
			bufferedWriter.write(line);
			bufferedWriter.write('\n');
		} catch (IOException e) {
			Dialogs.error(null, e.getMessage());
		}
	}

	@Override
	public void endSprite() {
		if (fileWriter == null || bufferedWriter == null) {
			return;
		}

		spriteIndex++;
	}

}
