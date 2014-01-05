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

	private CoreFactory factory;
	private InputMap inputMap;
	private ActionMap actionMap;

	private JList animationsList;
	private JList spritesList;
	private JList texturesList;

	private JToolBar animationBar;
	private JToolBar spriteBar;

	public MainFrame() {
		super(R.APPLICATION_NAME);

		createComponents();

		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	private void createComponents() {
		factory = new CoreFactory();

		createWorkSpace();
		createActionMaps();
		createMainMenu();
		createToolBars();

		pack();
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

		animationPane.add(new JLabel(R.ANIMATIONS_TEXT),
				BorderLayout.PAGE_START);
		animationPane.add(animationScroll, BorderLayout.CENTER);
		animationPane.add(animationBar, BorderLayout.SOUTH);

		// --------------- sprites ------------------------

		LayoutManager spritelayout = new BorderLayout(2, 2);
		JPanel spritePane = new JPanel(spritelayout);
		spriteBar = new JToolBar(JToolBar.HORIZONTAL);

		spritePane.add(new JLabel(R.SPRITES_TEXT), BorderLayout.PAGE_START);
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

		tabbedPane.add(R.ANIMATIONS_TAB_NAME, animTabPane);
		tabbedPane.add(R.TEXTURES_TAB_NAME, texturesScroll);

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

	private void createToolBars() {
		animationBar.setFloatable(false);
		animationBar.add(actionMap.get("create-animation"));
		animationBar.add(actionMap.get("rename-animation"));
		animationBar.add(actionMap.get("move-animation-up"));
		animationBar.add(actionMap.get("move-animation-down"));
		animationBar.add(actionMap.get("offset-animation"));
		animationBar.add(actionMap.get("remove-animation"));

		// ================================================

		spriteBar.setFloatable(false);
		spriteBar.add(actionMap.get("create-sprite"));
		spriteBar.add(actionMap.get("rename-sprite"));
		spriteBar.add(actionMap.get("move-sprite-up"));
		spriteBar.add(actionMap.get("move-sprite-down"));
		spriteBar.add(actionMap.get("offset-sprite"));
		spriteBar.add(actionMap.get("remove-sprite"));

		// ================================================

		JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
		toolBar.setFloatable(false);

		toolBar.add(actionMap.get("new-project"));
		toolBar.add(actionMap.get("open-project"));
		toolBar.add(actionMap.get("save-project"));
		toolBar.add(actionMap.get("settings-project"));
		toolBar.addSeparator();
		toolBar.add(actionMap.get("view-atlas"));
		toolBar.addSeparator();
		toolBar.add(actionMap.get("import-animation"));
		toolBar.add(actionMap.get("merge-similar-textures"));

		add(toolBar, BorderLayout.PAGE_START);
	}

	private void createMainMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu project = new JMenu("Project");
		JMenu animation = new JMenu("Animation");
		JMenu sprite = new JMenu("Sprite");
		JMenu texture = new JMenu("Texture");
		JMenu help = new JMenu("Help");

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

		JMenu pimport = new JMenu("Import");
		JMenu pexport = new JMenu("Export");

		pimport.setMnemonic(KeyEvent.VK_I);
		pexport.setMnemonic(KeyEvent.VK_E);

		pexport.add(createMenuItem("export-atlas"));
		pexport.add(createMenuItem("export-project"));
		pexport.add(createMenuItem("export-animation"));
		pimport.add(createMenuItem("import-animation"));
		pimport.add(createMenuItem("import-texture"));

		project.add(createMenuItem("new-project"));
		project.add(createMenuItem("open-project"));
		project.add(createMenuItem("save-project"));
		project.add(createMenuItem("save-project-as"));
		project.addSeparator();
		project.add(createMenuItem("view-atlas"));
		project.add(createMenuItem("settings-project"));
		project.addSeparator();
		project.add(pexport);
		project.add(pimport);
		project.addSeparator();
		project.add(createMenuItem("exit-application"));

		animation.add(createMenuItem("create-animation"));
		animation.add(createMenuItem("rename-animation"));
		animation.add(createMenuItem("remove-animation"));
		animation.addSeparator();
		animation.add(createMenuItem("move-animation-up"));
		animation.add(createMenuItem("move-animation-down"));
		animation.addSeparator();
		animation.add(createMenuItem("align-animations"));
		animation.add(createMenuItem("offset-animation"));
		animation.add(createMenuItem("center-animation"));

		sprite.add(createMenuItem("create-sprite"));
		sprite.add(createMenuItem("rename-sprite"));
		sprite.add(createMenuItem("remove-sprite"));
		sprite.addSeparator();
		sprite.add(createMenuItem("move-sprite-up"));
		sprite.add(createMenuItem("move-sprite-down"));
		sprite.addSeparator();
		sprite.add(createMenuItem("offset-sprite"));

		texture.add(createMenuItem("merge-similar-textures"));
		texture.add(createMenuItem("remove-texture"));

		help.add(createMenuItem("help-about"));

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

	private void createActionMaps() {
		inputMap = new InputMap();
		actionMap = new ActionMap();

		inputMap.put(KeyStroke.getKeyStroke("control N"), "new-project");
		inputMap.put(KeyStroke.getKeyStroke("control O"), "open-project");
		inputMap.put(KeyStroke.getKeyStroke("control S"), "save-project");
		inputMap.put(KeyStroke.getKeyStroke("control shift S"),
				"save-project-as");
		inputMap.put(KeyStroke.getKeyStroke("control E"), "settings-project");
		inputMap.put(KeyStroke.getKeyStroke("control I"), "import-animation");
		inputMap.put(KeyStroke.getKeyStroke("control T"), "view-atlas");
		inputMap.put(KeyStroke.getKeyStroke("control Q"), "exit-application");

		inputMap.put(KeyStroke.getKeyStroke("control shift N"),
				"create-animation");
		inputMap.put(KeyStroke.getKeyStroke("control shift R"),
				"rename-animation");
		inputMap.put(KeyStroke.getKeyStroke("control shift DELETE"),
				"remove-animation");
		inputMap.put(KeyStroke.getKeyStroke("control shift UP"),
				"move-animation-up");
		inputMap.put(KeyStroke.getKeyStroke("control shift DOWN"),
				"move-animation-down");
		inputMap.put(KeyStroke.getKeyStroke("control shift A"),
				"align-animations");
		inputMap.put(KeyStroke.getKeyStroke("control shift O"),
				"offset-animation");

		inputMap.put(KeyStroke.getKeyStroke("control alt N"), "create-sprite");
		inputMap.put(KeyStroke.getKeyStroke("control alt R"), "rename-sprite");
		inputMap.put(KeyStroke.getKeyStroke("control alt UP"), "move-sprite-up");
		inputMap.put(KeyStroke.getKeyStroke("control alt DOWN"),
				"move-sprite-down");
		inputMap.put(KeyStroke.getKeyStroke("control alt DELETE"),
				"remove-sprite");
		inputMap.put(KeyStroke.getKeyStroke("control alt O"), "offset-sprite");

		inputMap.put(KeyStroke.getKeyStroke("control M"),
				"merge-similar-textures");

		inputMap.put(KeyStroke.getKeyStroke("F1"), "help-about");

		actionMap.put("new-project", new NewProjectAction(this, factory));
		actionMap.put("open-project", new OpenProjectAction(this, factory));
		actionMap.put("save-project", new SaveProjectAction(this, factory));
		actionMap
				.put("save-project-as", new SaveProjectAsAction(this, factory));

		actionMap.put("view-atlas", new ViewAtlasAction(this, factory));
		actionMap.put("settings-project", new ProjectSettingsAction(this,
				factory));

		actionMap.put("import-animation", new ImportAnimationAction(this,
				factory));
		actionMap.put("import-texture", new ImportTextureAction(this, factory));

		actionMap.put("export-atlas", new ExportAtlasAction(this, factory));
		actionMap.put("export-project", new ExportProjectAction(this, factory));
		actionMap.put("export-animation", new ExportAnimationAction(this,
				factory));

		actionMap.put("exit-application", new ExitApplicationAction(this));

		actionMap.put("create-animation", new CreateAnimationAction(factory));
		actionMap.put("rename-animation", new RenameAnimationAction(this,
				factory));
		actionMap.put("remove-animation", new RemoveAnimationAction(this,
				factory));
		actionMap.put("move-animation-up", new MoveAnimationUpAction(this,
				factory));
		actionMap.put("move-animation-down", new MoveAnimationDownAction(this,
				factory));
		actionMap.put("align-animations", new AlignAnimationAction(this,
				factory));
		actionMap.put("offset-animation", new OffsetAnimationAction(this,
				factory));
		actionMap.put("center-animation", new CenterAnimationAction(this,
				factory));

		actionMap.put("create-sprite", new CreateSpriteAction(this, factory));
		actionMap.put("rename-sprite", new RenameSpriteAction(this, factory));
		actionMap.put("move-sprite-up", new MoveSpriteUpAction(this, factory));
		actionMap.put("move-sprite-down", new MoveSpriteDownAction(this,
				factory));
		actionMap.put("remove-sprite", new RemoveSpriteAction(this, factory));
		actionMap.put("offset-sprite", new OffsetSpriteAction(this, factory));

		actionMap
				.put("merge-similar-textures", new MergeSimilarAction(factory));
		actionMap.put("remove-texture", new RemoveTextureAction(this, factory));

		actionMap.put("help-about", new AboutAction(this));
	}

}
