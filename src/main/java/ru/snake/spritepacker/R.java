package ru.snake.spritepacker;

public class R {

	public static final String APPLICATION_NAME = "Texture packer";
	public static final String ALL_IMAGE_FORMATS = "All supported image formats";
	public static final String FILE_FILTER_FORMAT = "%s image file format (*.%s)";

	public static final String PROJECT_FILE_FORMAT = "Sprite packer project (*.spz)";
	public static final String PROJECT_FILE_SUFFIX = "spz";
	public static final String PROJECT_FILE_DOT_SUFFIX = "."
			+ PROJECT_FILE_SUFFIX;

	public static final String FILE_EXISTS_OVERWRITE = "File exists, do you want to overwrite it?";
	public static final String SAVE_CURRENT_PROJECT = "Do you want to save current project?";

	public static final String DEFAULT_ANIMATION_NAME_FORMAT = "animation-%d";
	public static final String SELECT_ANIMATION_BEFORE = "You must select some animation before do it.";
	public static final String SURE_DELETE_ANIMATION = "Are you sure to delete animation \"%s\"?";
	public static final String CHOOSE_ANIMATION_NAME = "Choose new animation name:";
	public static final String CANNT_CREATE_ATLAS = "Can not create atlas.";

	public static final String DEFAULT_SPRITE_NAME_FORMAT = "sprite-%d";
	public static final String SELECT_SPRITE_BEFORE = "You must select some sprite before do it.";
	public static final String SURE_DELETE_SPRITE = "Are you sure to delete sprite \"%s\" from animation \"%s\"?";
	public static final String CHOOSE_SPRITE_NAME = "Choose new sprite name:";
	public static final String SELECT_ANIM_TO_ADD_SPRITE = "Select animation for add sprite to.";

	public static final String ANIMATIONS_TEXT = "Animations:";
	public static final String SPRITES_TEXT = "Current animation sprites:";
	public static final String YOU_MUST_CHOOSE_TEXTURE = "You must choose texture for sprite.";
	public static final String TEXTURE_RENDER_TEXT = "<HTML>width=%d<BR>height=%d";
	public static final String SPRITE_RENDER_TEXT = "<HTML>%s<BR>offset: (%d, %d)";

	public static final String ANIMATIONS_TAB_NAME = "Animations";
	public static final String TEXTURES_TAB_NAME = "Textures";

	public static final String SETTINGS_MARGIN = "Margin (indent from image border):";
	public static final String SETTINGS_PADDING = "Padding (indent between images):";
	public static final String SETTINGS_ATLAS_PER_ANIMATION = "Create atlas for each animation";
	public static final String SETTINGS_ATLAS_SIZE = "Maximal atlas size (width and height):";

	public static final String REALLY_CENTER_ANIMATION = "Do you really center animation. All sprites will be centered?";

	public static final String OFFSET_ANIMATION_X = "X offset:";
	public static final String OFFSET_ANIMATION_Y = "Y offset:";

	public static final Object SELECT_EXPORT_FILE_FORMAT = "Select file format for export to:";
	public static final String NO_WRITERS_FOR_THESE_FORMAT = "No writers for these format.";
	public static final String CROP_IMAGES_WHILE_IMPORT = "Crop image transparent pixels while import?";

	public static final String ATLAS_SIZE_FORMAT = "Width = %d, Height = %d";

	public static final String SOME_TEXTURES_WRONG = "Some textures was not packed in atlas. Would you like to continue export?";

	public static final String SELECT_TEXTURE_BEFORE_DELETE = "You must select texture before remove it.";
	public static final String SURE_DELETE_TEXTURE = "All sprites that contains selected texture will be removed. Are you sure to continue?";

	public static final String SURE_ALIGN_ANIMATIONS_BY = "Offset for all animation will be changed. Are you sure to continue?";

}
