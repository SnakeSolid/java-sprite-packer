package ru.snake.spritepacker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionListener;

import ru.snake.spritepacker.actions.AboutAction;
import ru.snake.spritepacker.actions.ApplicationSettingsAction;
import ru.snake.spritepacker.actions.ExitApplicationAction;
import ru.snake.spritepacker.actions.MergeSimilarAction;
import ru.snake.spritepacker.actions.RemoveTextureAction;
import ru.snake.spritepacker.actions.ViewAtlasAction;
import ru.snake.spritepacker.actions.animation.AlignAnimationAction;
import ru.snake.spritepacker.actions.animation.CenterAnimationAction;
import ru.snake.spritepacker.actions.animation.CreateAnimationAction;
import ru.snake.spritepacker.actions.animation.MoveAnimationDownAction;
import ru.snake.spritepacker.actions.animation.MoveAnimationUpAction;
import ru.snake.spritepacker.actions.animation.OffsetAnimationAction;
import ru.snake.spritepacker.actions.animation.RemoveAnimationAction;
import ru.snake.spritepacker.actions.animation.RenameAnimationAction;
import ru.snake.spritepacker.actions.load.ImportAnimationAction;
import ru.snake.spritepacker.actions.load.ImportTextureAction;
import ru.snake.spritepacker.actions.load.ImportTiledTextureAction;
import ru.snake.spritepacker.actions.project.NewProjectAction;
import ru.snake.spritepacker.actions.project.OpenProjectAction;
import ru.snake.spritepacker.actions.project.ProjectSettingsAction;
import ru.snake.spritepacker.actions.project.SaveProjectAction;
import ru.snake.spritepacker.actions.project.SaveProjectAsAction;
import ru.snake.spritepacker.actions.save.ExportAnimationAction;
import ru.snake.spritepacker.actions.save.ExportAtlasAction;
import ru.snake.spritepacker.actions.save.ExportProjectAction;
import ru.snake.spritepacker.actions.sprite.CreateSpriteAction;
import ru.snake.spritepacker.actions.sprite.MoveSpriteDownAction;
import ru.snake.spritepacker.actions.sprite.MoveSpriteUpAction;
import ru.snake.spritepacker.actions.sprite.OffsetSpriteAction;
import ru.snake.spritepacker.actions.sprite.RemoveSpriteAction;
import ru.snake.spritepacker.actions.sprite.RenameSpriteAction;
import ru.snake.spritepacker.actions.sprite.SetSpriteTextureAction;
import ru.snake.spritepacker.component.AnimationView;
import ru.snake.spritepacker.core.CoreFactory;
import ru.snake.spritepacker.listener.AnimationMouseListener;
import ru.snake.spritepacker.listener.AnimationSelectionListener;
import ru.snake.spritepacker.listener.SpriteMouseListener;
import ru.snake.spritepacker.listener.SpriteSelectionListener;
import ru.snake.spritepacker.listener.TextureSelectionListener;
import ru.snake.spritepacker.render.SpriteCellRenderer;
import ru.snake.spritepacker.render.TextureCellRenderer;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {

	private static final String ALIGN_ANIMATIONS = "align-animations"; //$NON-NLS-1$
	private static final KeyStroke ALIGN_ANIMATIONS_KEY = KeyStroke
			.getKeyStroke("control shift A"); //$NON-NLS-1$
	private static final String CENTER_ANIMATION = "center-animation"; //$NON-NLS-1$
	private static final String CREATE_ANIMATION = "create-animation"; //$NON-NLS-1$
	private static final KeyStroke CREATE_ANIMATION_KEY = KeyStroke
			.getKeyStroke("control shift N"); //$NON-NLS-1$
	private static final String CREATE_SPRITE = "create-sprite"; //$NON-NLS-1$
	private static final KeyStroke CREATE_SPRITE_KEY = KeyStroke
			.getKeyStroke("control alt N"); //$NON-NLS-1$
	private static final String EXIT_APPLICATION = "exit-application"; //$NON-NLS-1$
	private static final KeyStroke EXIT_APPLICATION_KEY = KeyStroke
			.getKeyStroke("control Q"); //$NON-NLS-1$
	private static final String EXPORT_ANIMATION = "export-animation"; //$NON-NLS-1$
	private static final String EXPORT_ATLAS = "export-atlas"; //$NON-NLS-1$
	private static final String EXPORT_PROJECT = "export-project"; //$NON-NLS-1$
	private static final String HELP_ABOUT = "help-about"; //$NON-NLS-1$
	private static final KeyStroke HELP_ABOUT_KEY = KeyStroke
			.getKeyStroke("F1"); //$NON-NLS-1$
	private static final String IMPORT_ANIMATION = "import-animation"; //$NON-NLS-1$
	private static final KeyStroke IMPORT_ANIMATION_KEY = KeyStroke
			.getKeyStroke("control I"); //$NON-NLS-1$
	private static final String IMPORT_TEXTURE = "import-texture"; //$NON-NLS-1$
	private static final String IMPORT_TILED_TEXTURE = "import-tiled-texture"; //$NON-NLS-1$
	private static final String MERGE_SIMILAR_TEXTURES = "merge-similar-textures"; //$NON-NLS-1$
	private static final KeyStroke MERGE_SIMILAR_TEXTURES_KEY = KeyStroke
			.getKeyStroke("control M"); //$NON-NLS-1$
	private static final String MOVE_ANIMATION_DOWN = "move-animation-down"; //$NON-NLS-1$
	private static final KeyStroke MOVE_ANIMATION_DOWN_KEY = KeyStroke
			.getKeyStroke("control shift DOWN"); //$NON-NLS-1$
	private static final String MOVE_ANIMATION_UP = "move-animation-up"; //$NON-NLS-1$
	private static final KeyStroke MOVE_ANIMATION_UP_KEY = KeyStroke
			.getKeyStroke("control shift UP"); //$NON-NLS-1$
	private static final String MOVE_SPRITE_DOWN = "move-sprite-down"; //$NON-NLS-1$
	private static final KeyStroke MOVE_SPRITE_DOWN_KEY = KeyStroke
			.getKeyStroke("control alt DOWN"); //$NON-NLS-1$
	private static final String MOVE_SPRITE_UP = "move-sprite-up"; //$NON-NLS-1$
	private static final KeyStroke MOVE_SPRITE_UP_KEY = KeyStroke
			.getKeyStroke("control alt UP"); //$NON-NLS-1$
	private static final String NEW_PROJECT = "new-project"; //$NON-NLS-1$
	private static final KeyStroke NEW_PROJECT_KEY = KeyStroke
			.getKeyStroke("control N"); //$NON-NLS-1$
	private static final String OFFSET_ANIMATION = "offset-animation"; //$NON-NLS-1$
	private static final KeyStroke OFFSET_ANIMATION_KEY = KeyStroke
			.getKeyStroke("control shift O"); //$NON-NLS-1$
	private static final String OFFSET_SPRITE = "offset-sprite"; //$NON-NLS-1$
	private static final KeyStroke OFFSET_SPRITE_KEY = KeyStroke
			.getKeyStroke("control alt O"); //$NON-NLS-1$
	private static final String OPEN_PROJECT = "open-project"; //$NON-NLS-1$
	private static final KeyStroke OPEN_PROJECT_KEY = KeyStroke
			.getKeyStroke("control O"); //$NON-NLS-1$
	private static final String REMOVE_ANIMATION = "remove-animation"; //$NON-NLS-1$
	private static final KeyStroke REMOVE_ANIMATION_KEY = KeyStroke
			.getKeyStroke("control shift DELETE"); //$NON-NLS-1$
	private static final String REMOVE_SPRITE = "remove-sprite"; //$NON-NLS-1$
	private static final KeyStroke REMOVE_SPRITE_KEY = KeyStroke
			.getKeyStroke("control alt DELETE"); //$NON-NLS-1$
	private static final String REMOVE_TEXTURE = "remove-texture"; //$NON-NLS-1$
	private static final String RENAME_ANIMATION = "rename-animation"; //$NON-NLS-1$
	private static final KeyStroke RENAME_ANIMATION_KEY = KeyStroke
			.getKeyStroke("control shift R"); //$NON-NLS-1$
	private static final String RENAME_SPRITE = "rename-sprite"; //$NON-NLS-1$
	private static final KeyStroke RENAME_SPRITE_KEY = KeyStroke
			.getKeyStroke("control alt R"); //$NON-NLS-1$
	private static final String SAVE_PROJECT = "save-project"; //$NON-NLS-1$
	private static final String SAVE_PROJECT_AS = "save-project-as"; //$NON-NLS-1$
	private static final KeyStroke SAVE_PROJECT_AS_KEY = KeyStroke
			.getKeyStroke("control shift S"); //$NON-NLS-1$
	private static final KeyStroke SAVE_PROJECT_KEY = KeyStroke
			.getKeyStroke("control S"); //$NON-NLS-1$
	private static final String SETTINGS_PROJECT = "settings-project"; //$NON-NLS-1$
	private static final KeyStroke SETTINGS_PROJECT_KEY = KeyStroke
			.getKeyStroke("control E"); //$NON-NLS-1$
	private static final String SETTINGS_APPLICATION = "settings-application"; //$NON-NLS-1$
	private static final String VIEW_ATLAS = "view-atlas"; //$NON-NLS-1$
	private static final KeyStroke VIEW_ATLAS_KEY = KeyStroke
			.getKeyStroke("control T"); //$NON-NLS-1$
	private static final KeyStroke SPRITE_TEXTURE_KEY = KeyStroke
			.getKeyStroke("control alt T"); //$NON-NLS-1$
	private static final String SPRITE_TEXTURE = "sprite-texture"; //$NON-NLS-1$

	private ActionMap actionMap;
	private JToolBar animationBar;
	private JList animationsList;
	private CoreFactory factory;
	private InputMap inputMap;
	private JToolBar spriteBar;
	private JList spritesList;

	private JList texturesList;

	public MainFrame() {
		super(Messages.getString("MainFrame.TITLE")); //$NON-NLS-1$

		createComponents();

		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void createActionMaps() {
		inputMap = new InputMap();
		actionMap = new ActionMap();

		inputMap.put(NEW_PROJECT_KEY, NEW_PROJECT);
		inputMap.put(OPEN_PROJECT_KEY, OPEN_PROJECT);
		inputMap.put(SAVE_PROJECT_KEY, SAVE_PROJECT);
		inputMap.put(SAVE_PROJECT_AS_KEY, SAVE_PROJECT_AS);
		inputMap.put(SETTINGS_PROJECT_KEY, SETTINGS_PROJECT);
		inputMap.put(IMPORT_ANIMATION_KEY, IMPORT_ANIMATION);
		inputMap.put(VIEW_ATLAS_KEY, VIEW_ATLAS);
		inputMap.put(EXIT_APPLICATION_KEY, EXIT_APPLICATION);
		inputMap.put(CREATE_ANIMATION_KEY, CREATE_ANIMATION);
		inputMap.put(RENAME_ANIMATION_KEY, RENAME_ANIMATION);
		inputMap.put(REMOVE_ANIMATION_KEY, REMOVE_ANIMATION);
		inputMap.put(MOVE_ANIMATION_UP_KEY, MOVE_ANIMATION_UP);
		inputMap.put(MOVE_ANIMATION_DOWN_KEY, MOVE_ANIMATION_DOWN);
		inputMap.put(ALIGN_ANIMATIONS_KEY, ALIGN_ANIMATIONS);
		inputMap.put(OFFSET_ANIMATION_KEY, OFFSET_ANIMATION);
		inputMap.put(CREATE_SPRITE_KEY, CREATE_SPRITE);
		inputMap.put(RENAME_SPRITE_KEY, RENAME_SPRITE);
		inputMap.put(SPRITE_TEXTURE_KEY, SPRITE_TEXTURE);
		inputMap.put(MOVE_SPRITE_UP_KEY, MOVE_SPRITE_UP);
		inputMap.put(MOVE_SPRITE_DOWN_KEY, MOVE_SPRITE_DOWN);
		inputMap.put(REMOVE_SPRITE_KEY, REMOVE_SPRITE);
		inputMap.put(OFFSET_SPRITE_KEY, OFFSET_SPRITE);
		inputMap.put(MERGE_SIMILAR_TEXTURES_KEY, MERGE_SIMILAR_TEXTURES);
		inputMap.put(HELP_ABOUT_KEY, HELP_ABOUT);

		actionMap.put(NEW_PROJECT, new NewProjectAction(this, factory));
		actionMap.put(OPEN_PROJECT, new OpenProjectAction(this, factory));
		actionMap.put(SAVE_PROJECT, new SaveProjectAction(this, factory));
		actionMap.put(SAVE_PROJECT_AS, new SaveProjectAsAction(this, factory));
		actionMap.put(VIEW_ATLAS, new ViewAtlasAction(this, factory));
		actionMap.put(SETTINGS_PROJECT,
				new ProjectSettingsAction(this, factory));
		actionMap
				.put(SETTINGS_APPLICATION, new ApplicationSettingsAction(this));
		actionMap.put(IMPORT_ANIMATION,
				new ImportAnimationAction(this, factory));
		actionMap.put(IMPORT_TEXTURE, new ImportTextureAction(this, factory));
		actionMap.put(IMPORT_TILED_TEXTURE, new ImportTiledTextureAction(this,
				factory));
		actionMap.put(EXPORT_ATLAS, new ExportAtlasAction(this, factory));
		actionMap.put(EXPORT_PROJECT, new ExportProjectAction(this, factory));
		actionMap.put(EXPORT_ANIMATION,
				new ExportAnimationAction(this, factory));
		actionMap.put(EXIT_APPLICATION, new ExitApplicationAction(this));
		actionMap.put(CREATE_ANIMATION, new CreateAnimationAction(factory));
		actionMap.put(RENAME_ANIMATION,
				new RenameAnimationAction(this, factory));
		actionMap.put(REMOVE_ANIMATION,
				new RemoveAnimationAction(this, factory));
		actionMap.put(MOVE_ANIMATION_UP, new MoveAnimationUpAction(this,
				factory));
		actionMap.put(MOVE_ANIMATION_DOWN, new MoveAnimationDownAction(this,
				factory));
		actionMap
				.put(ALIGN_ANIMATIONS, new AlignAnimationAction(this, factory));
		actionMap.put(OFFSET_ANIMATION,
				new OffsetAnimationAction(this, factory));
		actionMap.put(CENTER_ANIMATION,
				new CenterAnimationAction(this, factory));
		actionMap.put(CREATE_SPRITE, new CreateSpriteAction(this, factory));
		actionMap.put(RENAME_SPRITE, new RenameSpriteAction(this, factory));
		actionMap
				.put(SPRITE_TEXTURE, new SetSpriteTextureAction(this, factory));
		actionMap.put(MOVE_SPRITE_UP, new MoveSpriteUpAction(this, factory));
		actionMap
				.put(MOVE_SPRITE_DOWN, new MoveSpriteDownAction(this, factory));
		actionMap.put(REMOVE_SPRITE, new RemoveSpriteAction(this, factory));
		actionMap.put(OFFSET_SPRITE, new OffsetSpriteAction(this, factory));
		actionMap.put(MERGE_SIMILAR_TEXTURES, new MergeSimilarAction(factory));
		actionMap.put(REMOVE_TEXTURE, new RemoveTextureAction(this, factory));
		actionMap.put(HELP_ABOUT, new AboutAction(this));
	}

	private void createComponents() {
		factory = new CoreFactory();

		createWorkSpace();
		createActionMaps();
		createMainMenu();
		createToolBars();

		pack();
	}

	private void createMainMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu project = new JMenu(Messages.getString("MainFrame.MENU_PROJECT")); //$NON-NLS-1$
		JMenu animation = new JMenu(
				Messages.getString("MainFrame.MENU_ANIMATION")); //$NON-NLS-1$
		JMenu sprite = new JMenu(Messages.getString("MainFrame.MENU_SPRITE")); //$NON-NLS-1$
		JMenu texture = new JMenu(Messages.getString("MainFrame.MENU_TEXTURE")); //$NON-NLS-1$
		JMenu help = new JMenu(Messages.getString("MainFrame.MENU_HELP")); //$NON-NLS-1$

		project.setMnemonic(KeyEvent.VK_P);
		animation.setMnemonic(KeyEvent.VK_A);
		sprite.setMnemonic(KeyEvent.VK_S);
		texture.setMnemonic(KeyEvent.VK_T);
		help.setMnemonic(KeyEvent.VK_H);

		menuBar.add(project);
		menuBar.add(animation);
		menuBar.add(sprite);
		menuBar.add(texture);
		menuBar.add(help);

		JMenu pimport = new JMenu(Messages.getString("MainFrame.MENU_IMPORT")); //$NON-NLS-1$
		JMenu pexport = new JMenu(Messages.getString("MainFrame.MENU_EXPORT")); //$NON-NLS-1$

		pimport.setMnemonic(KeyEvent.VK_I);
		pexport.setMnemonic(KeyEvent.VK_E);

		pexport.add(createMenuItem(EXPORT_ATLAS));
		pexport.add(createMenuItem(EXPORT_PROJECT));
		pexport.add(createMenuItem(EXPORT_ANIMATION));
		pimport.add(createMenuItem(IMPORT_ANIMATION));
		pimport.add(createMenuItem(IMPORT_TEXTURE));
		pimport.add(createMenuItem(IMPORT_TILED_TEXTURE));

		project.add(createMenuItem(NEW_PROJECT));
		project.add(createMenuItem(OPEN_PROJECT));
		project.add(createMenuItem(SAVE_PROJECT));
		project.add(createMenuItem(SAVE_PROJECT_AS));
		project.addSeparator();
		project.add(createMenuItem(VIEW_ATLAS));
		project.add(createMenuItem(SETTINGS_PROJECT));
		project.addSeparator();
		project.add(pexport);
		project.add(pimport);
		project.add(createMenuItem(SETTINGS_APPLICATION));
		project.addSeparator();
		project.add(createMenuItem(EXIT_APPLICATION));

		animation.add(createMenuItem(CREATE_ANIMATION));
		animation.add(createMenuItem(RENAME_ANIMATION));
		animation.add(createMenuItem(REMOVE_ANIMATION));
		animation.addSeparator();
		animation.add(createMenuItem(MOVE_ANIMATION_UP));
		animation.add(createMenuItem(MOVE_ANIMATION_DOWN));
		animation.addSeparator();
		animation.add(createMenuItem(ALIGN_ANIMATIONS));
		animation.add(createMenuItem(OFFSET_ANIMATION));
		animation.add(createMenuItem(CENTER_ANIMATION));

		sprite.add(createMenuItem(CREATE_SPRITE));
		sprite.add(createMenuItem(RENAME_SPRITE));
		sprite.add(createMenuItem(REMOVE_SPRITE));
		sprite.addSeparator();
		sprite.add(createMenuItem(MOVE_SPRITE_UP));
		sprite.add(createMenuItem(MOVE_SPRITE_DOWN));
		sprite.addSeparator();
		sprite.add(createMenuItem(OFFSET_SPRITE));
		sprite.add(createMenuItem(SPRITE_TEXTURE));

		texture.add(createMenuItem(MERGE_SIMILAR_TEXTURES));
		texture.add(createMenuItem(REMOVE_TEXTURE));

		help.add(createMenuItem(HELP_ABOUT));

		menuBar.setInputMap(JComponent.WHEN_FOCUSED, inputMap);
		menuBar.setActionMap(actionMap);

		setJMenuBar(menuBar);
	}

	private JMenuItem createMenuItem(String key) {
		JMenuItem item = new JMenuItem();

		item.setAction(actionMap.get(key));

		for (KeyStroke keyStroke : inputMap.keys()) {
			if (inputMap.get(keyStroke).equals(key)) {
				item.setAccelerator(keyStroke);

				break;
			}
		}

		return item;
	}

	private void createToolBars() {
		animationBar.setFloatable(false);
		animationBar.add(actionMap.get(CREATE_ANIMATION));
		animationBar.add(actionMap.get(RENAME_ANIMATION));
		animationBar.add(actionMap.get(MOVE_ANIMATION_UP));
		animationBar.add(actionMap.get(MOVE_ANIMATION_DOWN));
		animationBar.add(actionMap.get(OFFSET_ANIMATION));
		animationBar.add(actionMap.get(REMOVE_ANIMATION));

		// ================================================

		spriteBar.setFloatable(false);
		spriteBar.add(actionMap.get(CREATE_SPRITE));
		spriteBar.add(actionMap.get(RENAME_SPRITE));
		spriteBar.add(actionMap.get(SPRITE_TEXTURE));
		spriteBar.add(actionMap.get(MOVE_SPRITE_UP));
		spriteBar.add(actionMap.get(MOVE_SPRITE_DOWN));
		spriteBar.add(actionMap.get(OFFSET_SPRITE));
		spriteBar.add(actionMap.get(REMOVE_SPRITE));

		// ================================================

		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);

		toolBar.add(actionMap.get(NEW_PROJECT));
		toolBar.add(actionMap.get(OPEN_PROJECT));
		toolBar.add(actionMap.get(SAVE_PROJECT));
		toolBar.add(actionMap.get(SETTINGS_PROJECT));
		toolBar.addSeparator();
		toolBar.add(actionMap.get(VIEW_ATLAS));
		toolBar.addSeparator();
		toolBar.add(actionMap.get(IMPORT_ANIMATION));
		toolBar.add(actionMap.get(MERGE_SIMILAR_TEXTURES));

		add(toolBar, BorderLayout.PAGE_START);
	}

	private void createWorkSpace() {
		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);

		animationsList = new JList();
		animationsList.setModel(factory.getAnimationsModel());
		JScrollPane animationScroll = new JScrollPane(animationsList);
		animationScroll
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		spritesList = new JList();
		spritesList.setModel(factory.getSpritesModel());
		spritesList.setCellRenderer(new SpriteCellRenderer());

		JScrollPane spritesScroll = new JScrollPane(spritesList);
		spritesScroll
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		// ================================================

		LayoutManager animTablayout = new GridLayout(2, 1, 4, 4);
		JPanel animTabPane = new JPanel(animTablayout);

		// -------------- animations ----------------------

		LayoutManager animlayout = new BorderLayout(2, 2);
		JPanel animationPane = new JPanel(animlayout);
		animationBar = new JToolBar(JToolBar.HORIZONTAL);

		animationPane
				.add(new JLabel(Messages
						.getString("MainFrame.LABEL_ANIMATIONS")), BorderLayout.PAGE_START); //$NON-NLS-1$
		animationPane.add(animationScroll, BorderLayout.CENTER);
		animationPane.add(animationBar, BorderLayout.SOUTH);

		// --------------- sprites ------------------------

		LayoutManager spritelayout = new BorderLayout(2, 2);
		JPanel spritePane = new JPanel(spritelayout);
		spriteBar = new JToolBar(JToolBar.HORIZONTAL);

		spritePane.add(
				new JLabel(Messages.getString("MainFrame.LABEL_SPRITES")), //$NON-NLS-1$
				BorderLayout.PAGE_START);
		spritePane.add(spritesScroll, BorderLayout.CENTER);
		spritePane.add(spriteBar, BorderLayout.PAGE_END);

		// --------------- complete tab ------------------------

		animTabPane.add(animationPane);
		animTabPane.add(spritePane);

		// ================================================

		texturesList = new JList();
		texturesList.setModel(factory.getTexturesModel());
		texturesList.setCellRenderer(new TextureCellRenderer());
		texturesList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		texturesList.setVisibleRowCount(-1);

		JScrollPane texturesScroll = new JScrollPane(texturesList);
		texturesScroll
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		tabbedPane.add(
				Messages.getString("MainFrame.TAB_ANIMATIONS"), animTabPane); //$NON-NLS-1$
		tabbedPane.add(
				Messages.getString("MainFrame.TAB_TEXTURES"), texturesScroll); //$NON-NLS-1$

		Dimension prefsize = tabbedPane.getPreferredSize();
		prefsize.width = 300;
		tabbedPane.setPreferredSize(prefsize);

		getContentPane().add(tabbedPane, BorderLayout.LINE_START);

		// ================================================

		AnimationView view = new AnimationView(factory);

		getContentPane().add(view, BorderLayout.CENTER);

		// ================================================

		ListSelectionModel animselection = animationsList.getSelectionModel();
		ListSelectionListener animlistener = new AnimationSelectionListener(
				animationsList, factory);
		animselection.addListSelectionListener(animlistener);

		ListSelectionModel sprselection = spritesList.getSelectionModel();
		ListSelectionListener sprlistener = new SpriteSelectionListener(
				spritesList, factory);
		sprselection.addListSelectionListener(sprlistener);

		ListSelectionModel texselection = texturesList.getSelectionModel();
		ListSelectionListener texlistener = new TextureSelectionListener(
				texturesList, factory);
		texselection.addListSelectionListener(texlistener);

		// -----------------------------------------------------

		MouseListener aml = new AnimationMouseListener(this, factory);
		animationsList.addMouseListener(aml);

		MouseListener sml = new SpriteMouseListener(this, factory);
		spritesList.addMouseListener(sml);
	}

}
