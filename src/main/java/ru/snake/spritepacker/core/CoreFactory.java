package ru.snake.spritepacker.core;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ListModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

import ru.snake.spritepacker.core.packer.ImageData;
import ru.snake.spritepacker.core.packer.ImagePacker;
import ru.snake.spritepacker.core.packer.PackerOutput;
import ru.snake.spritepacker.model.CoreListModel;

public class CoreFactory {

	private static final int START_ANIMATION_INDEX = 1;
	private static final int START_SPRITE_INDEX = 1;

	private static final String ZIP_ENTRY_NAME = "project.json";
	private static final String ZIP_IMAGE_FORMAT = "%d.png";

	private static final String KEY_MARGIN = "margin";
	private static final String KEY_PADDING = "padding";
	private static final String KEY_ATLAS = "atlasperanim";
	private static final String KEY_MAX_WIDTH = "maxwidth";
	private static final String KEY_MAX_HEIGHT = "maxheight";

	private static final String KEY_PREFERENCES = "preferences";
	private static final String KEY_PREFERENCE_KEY = "key";
	private static final String KEY_PREFERENCE_VALUE = "value";

	private static final String KEY_SPRITE_OFFSETX = "offsetx";
	private static final String KEY_SPRITE_OFFSETY = "offsety";
	private static final String KEY_SPRITE_NAME = "name";
	private static final String KEY_SPRITE_TEXTURE = "texture";

	private static final String KEY_ANIMATIONS = "animations";
	private static final String KEY_ANIMATION_NAME = "name";
	private static final String KEY_ANIMATION_SPRITES = "sprites";

	private final CoreListModel<Texture> textures;
	private final CoreListModel<Sprite> sprites;
	private final CoreListModel<Animation> animations;

	private final Map<String, String> projectPreferences;

	private final List<CoreSubscriber> subscribers;

	private Texture activeTexture;
	private Sprite activeSprite;
	private Animation activeAnimation;

	private boolean modified;
	private File projectfile;

	private boolean atlasPerAnim;
	private int maxWidth;
	private int maxHeight;

	private int margin;
	private int padding;

	public CoreFactory() {
		textures = new CoreListModel<Texture>();
		sprites = new CoreListModel<Sprite>();
		animations = new CoreListModel<Animation>();

		projectPreferences = new HashMap<String, String>();

		subscribers = new LinkedList<CoreSubscriber>();

		resetData();
	}

	public void subscribe(CoreSubscriber subscriber) {
		if (subscribers.contains(subscriber)) {
			return;
		}

		subscribers.add(subscriber);
	}

	public void unsubscribe(CoreSubscriber subscriber) {
		if (!subscribers.contains(subscriber)) {
			return;
		}

		subscribers.remove(subscriber);
	}

	private void notifySubscribers() {
		for (CoreSubscriber each : subscribers) {
			each.notifyChanged();
		}
	}

	public Texture createTexture(BufferedImage image) {
		Texture texture = new Texture(image);

		textures.addItem(texture);

		modified = true;

		return texture;
	}

	public Texture createTexture(File imagefile) {
		BufferedImage image;

		try {
			image = ImageIO.read(imagefile);
		} catch (IOException e) {
			return null;
		}

		return createTexture(image);
	}

	public Texture createTexture(File imagefile, Point point) {
		BufferedImage image;
		BufferedImage subimage;

		try {
			image = ImageIO.read(imagefile);
		} catch (IOException e) {
			return null;
		}

		int height = image.getHeight();
		int width = image.getWidth();

		int top = 0;
		int left = 0;
		int bottom = height - 1;
		int right = width - 1;

		int[] rgb = new int[width * height];

		image.getRGB(0, 0, width, height, rgb, 0, width);

		toploop: while (top < bottom) {
			for (int i = 0; i < width; i++) {
				if ((rgb[i + width * top] & 0xff000000) != 0) {
					break toploop;
				}
			}

			top++;
		}

		bottomloop: while (top < bottom) {
			for (int i = 0; i < width; i++) {
				if ((rgb[i + width * (bottom - 1)] & 0xff000000) != 0) {
					break bottomloop;
				}
			}

			bottom--;
		}

		if (top == bottom) {
			return null;
		}

		leftloop: while (left < right) {
			for (int i = top; i < bottom; i++) {
				if ((rgb[left + width * i] & 0xff000000) != 0) {
					break leftloop;
				}
			}

			left++;
		}

		rightloop: while (left < right) {
			for (int i = top; i < bottom; i++) {
				if ((rgb[right - 1 + width * i] & 0xff000000) != 0) {
					break rightloop;
				}
			}

			right--;
		}

		if (left == right) {
			return null;
		}

		subimage = image.getSubimage(left, top, right - left, bottom - top);

		if (point != null) {
			point.setLocation(left, top);
		}

		return createTexture(subimage);
	}

	public Animation createAnimation(String name, List<Sprite> spritelist) {
		Animation animation = new Animation(name, spritelist);

		animations.addItem(animation);

		modified = true;

		return animation;
	}

	public ListModel getTexturesModel() {
		return textures;
	}

	public ListModel getSpritesModel() {
		return sprites;
	}

	public ListModel getAnimationsModel() {
		return animations;
	}

	public PackerOutput createTextureAtlas() {
		PackerOutput output = new PackerOutput();

		if (atlasPerAnim) {
			List<Animation> allAnimations = animations.getList();
			int animationIndex = START_ANIMATION_INDEX;

			for (Animation eachAnimation : allAnimations) {
				Set<Texture> allTextures = new HashSet<Texture>();

				for (Sprite eachSprite : eachAnimation.getSprites()) {
					allTextures.add(eachSprite.texture);
				}

				ImagePacker packer = new ImagePacker(margin, padding);

				for (Texture eachTexture : allTextures) {
					ImageData data = new ImageData(animationIndex, eachTexture);

					packer.addItem(data);
				}

				output.setNameFormat(eachAnimation.name + "-", null);

				packer.setOutput(output);
				packer.process(maxWidth, maxHeight, true);

				animationIndex++;
			}
		} else {
			Set<Texture> allTextures = new HashSet<Texture>();

			for (Animation eachAnimation : animations.getList()) {
				for (Sprite eachSprite : eachAnimation.getSprites()) {
					allTextures.add(eachSprite.texture);
				}
			}

			ImagePacker packer = new ImagePacker(margin, padding);

			for (Texture eachTexture : allTextures) {
				ImageData data = new ImageData(eachTexture);

				packer.addItem(data);
			}

			output.setNameFormat("atlas-", null);

			packer.setOutput(output);
			packer.process(maxWidth, maxHeight, true);
		}

		return output;
	}

	public void mergeTextures() {
		Collection<Animation> allanimations = animations.getList();
		Collection<Texture> uniquetextures = new LinkedList<Texture>();

		for (Animation eachanim : allanimations) {
			for (Sprite eachsprite : eachanim.getSprites()) {
				boolean founded = false;

				for (Texture eachtex : uniquetextures) {
					if (eachtex.equals(eachsprite.texture)) {
						eachsprite.texture = eachtex;
						founded = true;

						break;
					}
				}

				if (!founded) {
					uniquetextures.add(eachsprite.texture);
				}
			}
		}

		textures.setList(uniquetextures);

		modified = true;
	}

	public void saveTo(File file) throws IOException {
		OutputStream fos = new FileOutputStream(file);
		OutputStream bos = new BufferedOutputStream(fos);
		ZipOutputStream zos = new ZipOutputStream(bos);
		ZipEntry ze;

		ze = new ZipEntry(ZIP_ENTRY_NAME);
		zos.putNextEntry(ze);

		OutputStreamWriter osw = new OutputStreamWriter(zos);
		JSONWriter out = new JSONWriter(osw);

		out.object();
		out.key(KEY_MARGIN).value(margin);
		out.key(KEY_PADDING).value(padding);
		out.key(KEY_ATLAS).value(atlasPerAnim);
		out.key(KEY_MAX_WIDTH).value(maxWidth);
		out.key(KEY_MAX_HEIGHT).value(maxHeight);
		out.key(KEY_PREFERENCES).array();

		for (Entry<String, String> entry : projectPreferences.entrySet()) {
			out.object();
			out.key(KEY_PREFERENCE_KEY).value(entry.getKey());
			out.key(KEY_PREFERENCE_VALUE).value(entry.getValue());
			out.endObject();
		}

		out.endArray();
		out.key(KEY_ANIMATIONS).array();

		List<Texture> alltextures = textures.getList();
		List<Animation> allanimations = animations.getList();

		for (Animation eachanimation : allanimations) {
			out.object();

			out.key(KEY_ANIMATION_NAME).value(eachanimation.name);
			out.key(KEY_ANIMATION_SPRITES).array();

			for (Sprite eachssprite : eachanimation.getSprites()) {
				int textureid = alltextures.indexOf(eachssprite.texture);

				out.object();
				out.key(KEY_SPRITE_OFFSETX).value(eachssprite.offsetX);
				out.key(KEY_SPRITE_OFFSETY).value(eachssprite.offsetY);
				out.key(KEY_SPRITE_NAME).value(eachssprite.name);
				out.key(KEY_SPRITE_TEXTURE).value(textureid);
				out.endObject();
			}

			out.endArray();
			out.endObject();
		}

		out.endArray();
		out.endObject();

		osw.flush();
		zos.closeEntry();

		for (int i = 0; i < alltextures.size(); i++) {
			Texture texture = alltextures.get(i);

			ze = new ZipEntry(String.format(ZIP_IMAGE_FORMAT, i));
			zos.putNextEntry(ze);
			ImageIO.write(texture.image, "png", zos);
			zos.closeEntry();
		}

		zos.close();
		bos.close();
		fos.close();

		modified = false;

		projectfile = file;
	}

	public void loadFrom(File file) throws IOException {
		InputStream fin = new FileInputStream(file);
		InputStream bin = new BufferedInputStream(fin);
		ZipInputStream zin = new ZipInputStream(bin);

		List<Texture> alltextures = new ArrayList<Texture>();
		List<Animation> allanimations = new ArrayList<Animation>();

		Map<String, Texture> projtextures = new HashMap<String, Texture>();

		JSONObject in = null;
		JSONArray items;

		while (true) {
			ZipEntry ze = zin.getNextEntry();

			if (ze == null) {
				zin.close();
				bin.close();
				fin.close();

				break;
			}

			String name = ze.getName();

			if (name.equals(ZIP_ENTRY_NAME)) {
				JSONTokener tokener = new JSONTokener(zin);

				in = new JSONObject(tokener);

				zin.closeEntry();
			} else {
				BufferedImage image;
				Texture texture;

				image = ImageIO.read(zin);
				zin.closeEntry();

				texture = new Texture(image);

				alltextures.add(texture);
				projtextures.put(name, texture);
			}
		}

		if (in == null) {
			return;
		}

		projectfile = file;

		// ============ loading settings ============

		margin = in.getInt(KEY_MARGIN);
		padding = in.getInt(KEY_PADDING);
		atlasPerAnim = in.getBoolean(KEY_ATLAS);
		maxWidth = in.getInt(KEY_MAX_WIDTH);
		maxHeight = in.getInt(KEY_MAX_HEIGHT);

		// ============ loading preferences ============

		projectPreferences.clear();

		items = in.getJSONArray(KEY_PREFERENCES);

		for (int prefIdx = 0; prefIdx < items.length(); prefIdx++) {
			JSONObject obj = items.getJSONObject(prefIdx);

			String preferenceKey = obj.getString(KEY_PREFERENCE_KEY);
			String preferenceValue = obj.getString(KEY_PREFERENCE_VALUE);

			projectPreferences.put(preferenceKey, preferenceValue);
		}

		// ============ loading animations ============

		items = in.getJSONArray(KEY_ANIMATIONS);

		for (int animidx = 0; animidx < items.length(); animidx++) {
			JSONObject obj = items.getJSONObject(animidx);
			JSONArray sprites = obj.getJSONArray(KEY_ANIMATION_SPRITES);

			String animname = obj.getString(KEY_ANIMATION_NAME);
			List<Sprite> animsprites = new ArrayList<Sprite>();

			for (int spriteidx = 0; spriteidx < sprites.length(); spriteidx++) {
				JSONObject spriteobj = sprites.getJSONObject(spriteidx);

				int offsetx = spriteobj.getInt(KEY_SPRITE_OFFSETX);
				int offsety = spriteobj.getInt(KEY_SPRITE_OFFSETY);
				String sprname = spriteobj.getString(KEY_SPRITE_NAME);
				int textureid = spriteobj.getInt(KEY_SPRITE_TEXTURE);
				String texturename = String.format(ZIP_IMAGE_FORMAT, textureid);

				Sprite sprite = new Sprite(offsetx, offsety, sprname,
						projtextures.get(texturename));

				animsprites.add(sprite);
			}

			Animation animation = new Animation(animname, animsprites);

			allanimations.add(animation);
		}

		// ============ update lists ============

		List<Sprite> allsprites = Collections.emptyList();

		textures.setList(alltextures);
		sprites.setList(allsprites);
		animations.setList(allanimations);

		activeTexture = null;
		activeSprite = null;
		activeAnimation = null;

		modified = false;

		notifySubscribers();
	}

	public File getProjectFile() {
		return projectfile;
	}

	public void resetData() {
		List<Texture> alltextures = Collections.emptyList();
		List<Sprite> allsprites = Collections.emptyList();
		List<Animation> allanimations = Collections.emptyList();

		activeTexture = null;
		activeSprite = null;
		activeAnimation = null;

		modified = false;
		projectfile = null;

		atlasPerAnim = false;
		maxWidth = 1024;
		maxHeight = 1024;

		margin = 1;
		padding = 1;

		textures.setList(alltextures);
		sprites.setList(allsprites);
		animations.setList(allanimations);

		projectPreferences.clear();

		notifySubscribers();
	}

	public void removeAnimation(Animation animation) {
		animations.removeItem(animation);
	}

	public void updateAnimations() {
		Animation selected = activeAnimation;
		List<Animation> list = new ArrayList<Animation>();

		list.addAll(animations.getList());

		animations.setList(list);
		activeSprite = null;

		if (!list.contains(selected)) {
			activeAnimation = null;
		}

		updateSprites();

		notifySubscribers();
	}

	public void setActiveSprite(Sprite activeSprite) {
		this.activeSprite = activeSprite;

		notifySubscribers();
	}

	public void setActiveAnimation(Animation animation) {
		activeAnimation = animation;

		doUpdateSprites(animation);

		notifySubscribers();
	}

	private void doUpdateSprites(Animation animation) {
		Sprite selected = activeSprite;
		List<Sprite> allsprites;

		if (animation == null) {
			allsprites = Collections.emptyList();
		} else {
			allsprites = animation.getSprites();
		}

		if (!allsprites.contains(selected)) {
			activeSprite = null;
		}

		sprites.setList(allsprites);

		modified = true;
	}

	public Sprite getActiveSprite() {
		return activeSprite;
	}

	public Animation getActiveAnimation() {
		return activeAnimation;
	}

	public void updateSprites() {
		doUpdateSprites(activeAnimation);

		notifySubscribers();
	}

	public void removeSprite(Animation animation, Sprite sprite) {
		List<Sprite> sprites = animation.getSprites();
		List<Sprite> list = new LinkedList<Sprite>(sprites);

		if (activeSprite == sprite) {
			activeSprite = null;
		}

		list.remove(sprite);

		animation.setSprites(list);

		notifySubscribers();
	}

	public void addSprite(Animation animation, Sprite sprite) {
		List<Sprite> animsprites = animation.getSprites();
		List<Sprite> list = new LinkedList<Sprite>(animsprites);

		list.add(sprite);

		animation.setSprites(list);
		sprites.setList(animsprites);

		activeSprite = null;
		notifySubscribers();
	}

	public int getMargin() {
		return margin;
	}

	public int getPadding() {
		return padding;
	}

	public void setMargin(int margin) {
		this.margin = margin;

		modified = true;
	}

	public void setPadding(int padding) {
		this.padding = padding;

		modified = true;
	}

	public void moveAnimationUp(Animation animation) {
		List<Animation> list = animations.getList();
		int index = list.indexOf(animation);

		if (index != -1 && index > 0) {
			List<Animation> ordered = new ArrayList<Animation>(list);

			ordered.set(index, ordered.get(index - 1));
			ordered.set(index - 1, animation);

			animations.setList(ordered);
		}

		modified = true;
	}

	public void moveAnimationDown(Animation animation) {
		List<Animation> list = animations.getList();
		int index = list.indexOf(animation);

		if (index != -1 && index < list.size() - 1) {
			List<Animation> ordered = new ArrayList<Animation>(list);

			ordered.set(index, ordered.get(index + 1));
			ordered.set(index + 1, animation);

			animations.setList(ordered);
		}

		modified = true;
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;

		modified = true;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;

		modified = true;
	}

	public boolean isModified() {
		return modified;
	}

	public boolean isAtlasPerAnim() {
		return atlasPerAnim;
	}

	public void setAtlasPerAnim(boolean atlasPerAnim) {
		this.atlasPerAnim = atlasPerAnim;

		modified = true;
	}

	public String getPreference(String key, String defaultValue) {
		if (projectPreferences.containsKey(key)) {
			return projectPreferences.get(key);
		}

		return defaultValue;
	}

	public void setPreference(String key, String value) {
		projectPreferences.put(key, value);

		modified = true;
	}

	public void traverse(CoreFactoryWalker walker) {
		walker.start();

		int animationIndex = START_ANIMATION_INDEX;

		for (Animation eachAnim : animations.getList()) {
			walker.startAnimation(animationIndex, eachAnim.name);

			int spriteIndex = START_SPRITE_INDEX;

			for (Sprite eachSprite : eachAnim.getSprites()) {
				walker.startSprite(spriteIndex, eachSprite.name,
						eachSprite.offsetX, eachSprite.offsetY,
						eachSprite.texture);
				walker.endSprite();

				spriteIndex++;
			}

			walker.endAnimation();

			animationIndex++;
		}

		walker.end();
	}

	public void setActiveTexture(Texture texture) {
		activeTexture = texture;
	}

	public Texture getActiveTexture() {
		return activeTexture;
	}

	public void removeTexture(Texture texture) {
		boolean removed = false;

		List<Texture> allTextures = textures.getList();

		if (allTextures.contains(texture)) {
			List<Texture> updatedTextures = new LinkedList<Texture>(allTextures);

			updatedTextures.remove(texture);

			textures.setList(updatedTextures);

			removed = true;
		}

		for (Animation eachAnim : animations.getList()) {
			List<Sprite> animSprites = eachAnim.getSprites();
			LinkedList<Sprite> sprites = new LinkedList<Sprite>(animSprites);
			Iterator<Sprite> spriteIt = sprites.iterator();

			boolean updateAnimation = false;

			while (spriteIt.hasNext()) {
				Sprite sprite = spriteIt.next();

				if (sprite.texture == texture) {
					spriteIt.remove();

					removed = true;
					updateAnimation = true;
				}
			}

			if (updateAnimation) {
				eachAnim.setSprites(sprites);
			}
		}

		if (activeTexture == texture) {
			activeTexture = null;
		}

		if (removed) {
			modified = true;

			notifySubscribers();
		}
	}

	public void alignAnimationsBy(Animation animation) {
		boolean changed = false;
		List<Sprite> referenSprites = animation.getSprites();

		for (Animation eachAnim : animations.getList()) {
			spriteLoop: for (Sprite eachSprite : eachAnim.getSprites()) {
				for (Sprite refSprite : referenSprites) {
					Texture texture = eachSprite.texture;
					Texture refTexture = refSprite.texture;

					if (texture.equals(refTexture)) {
						int offsetX = refSprite.offsetX - eachSprite.offsetX;
						int offsetY = refSprite.offsetY - eachSprite.offsetY;

						eachAnim.translate(offsetX, offsetY);

						changed = true;

						break spriteLoop;
					}
				}
			}
		}

		if (changed) {
			modified = true;

			notifySubscribers();
		}
	}
}
