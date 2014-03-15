package ru.snake.spritepacker;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONWriter;

public class Configuration {

	private static final String FILENAME = "config.json";

	private static final String RED_KEY = "red";
	private static final String GREEN_KEY = "green";
	private static final String BLUE_KEY = "blue";
	private static final String ALPHA_KEY = "alpha";

	private static final String PANEL_GRID = "panel.thumbnail";
	private static final String PANEL_FOREGROUND = "panel.foreground";
	private static final String PANEL_BACKGROUND = "panel.background";

	private static final String LIST_THUMBNAIL = "list.thumbnail";
	private static final String LIST_GRID = "list.grid";
	private static final String LIST_FOREGROUND = "list.foreground";
	private static final String LIST_BACKGROUND = "list.background";

	private static final int DEFAULT_PANEL_GRID = 8;
	private static final Color DEFAULT_FOREGROUND = Color.LIGHT_GRAY;
	private static final Color DEFAULT_BACKGROUND = Color.WHITE;
	private static final int DEFAULT_LIST_THUMBNAIL = 48;
	private static final int DEFAULT_LIST_GRID = 4;

	private static Configuration instance;

	private int panelGridSize;
	private Color panelForeground;
	private Color panelBackground;
	private int listThumbSize;
	private int listGridSize;
	private Color listForeground;
	private Color listBackground;

	private Configuration() {
		panelGridSize = DEFAULT_PANEL_GRID;
		panelForeground = DEFAULT_FOREGROUND;
		panelBackground = DEFAULT_BACKGROUND;
		listThumbSize = DEFAULT_LIST_THUMBNAIL;
		listGridSize = DEFAULT_LIST_GRID;
		listForeground = DEFAULT_FOREGROUND;
		listBackground = DEFAULT_BACKGROUND;

		try {
			load();
		} catch (IOException e) {
		} catch (JSONException e) {
		}
	}

	private void load() throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append(FILENAME);

		InputStream is = new FileInputStream(sb.toString());
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader reader = new BufferedReader(isr);
		JSONTokener tokener = new JSONTokener(reader);
		JSONObject object = new JSONObject(tokener);

		panelGridSize = loadInt(object, PANEL_GRID, DEFAULT_PANEL_GRID);
		panelForeground = loadColor(object, PANEL_FOREGROUND,
				DEFAULT_FOREGROUND);
		panelBackground = loadColor(object, PANEL_BACKGROUND,
				DEFAULT_BACKGROUND);
		listThumbSize = loadInt(object, LIST_THUMBNAIL, DEFAULT_LIST_THUMBNAIL);
		listGridSize = loadInt(object, LIST_GRID, DEFAULT_LIST_GRID);
		listForeground = loadColor(object, LIST_FOREGROUND, DEFAULT_FOREGROUND);
		listBackground = loadColor(object, LIST_BACKGROUND, DEFAULT_BACKGROUND);

		reader.close();
	}

	private int loadInt(JSONObject object, String key, int defaultValue) {
		if (object.has(key)) {
			return object.getInt(key);
		}

		return defaultValue;
	}

	private Color loadColor(JSONObject object, String key, Color defaultValue) {
		if (object.has(key)) {
			JSONObject value = object.getJSONObject(key);

			int red = value.getInt(RED_KEY);
			int green = value.getInt(GREEN_KEY);
			int blue = value.getInt(BLUE_KEY);
			int alpha = value.getInt(ALPHA_KEY);

			return new Color(red, green, blue, alpha);
		}

		return defaultValue;
	}

	public static Configuration getInstance() {
		if (instance == null) {
			instance = new Configuration();
		}

		return instance;
	}

	public void commit() throws IOException {
		StringBuilder sb = new StringBuilder();

		sb.append(System.getProperty("user.dir"));
		sb.append(File.separator);
		sb.append(FILENAME);

		FileWriter fw = new FileWriter(sb.toString());
		BufferedWriter w = new BufferedWriter(fw);

		JSONWriter writer = new JSONWriter(w);

		writer.object();

		saveInt(writer, PANEL_GRID, panelGridSize);
		saveColor(writer, PANEL_FOREGROUND, panelForeground);
		saveColor(writer, PANEL_BACKGROUND, panelBackground);
		saveInt(writer, LIST_THUMBNAIL, listThumbSize);
		saveInt(writer, LIST_GRID, listGridSize);
		saveColor(writer, LIST_FOREGROUND, listForeground);
		saveColor(writer, LIST_BACKGROUND, listBackground);

		writer.endObject();

		w.close();
	}

	private void saveInt(JSONWriter writer, String key, int value) {
		writer.key(key).value(value);
	}

	private void saveColor(JSONWriter writer, String key, Color value) {
		writer.key(key);
		writer.object();
		writer.key(RED_KEY).value(value.getRed());
		writer.key(GREEN_KEY).value(value.getGreen());
		writer.key(BLUE_KEY).value(value.getBlue());
		writer.key(ALPHA_KEY).value(value.getAlpha());
		writer.endObject();
	}

	public int getPanelGridSize() {
		return panelGridSize;
	}

	public Color getPanelForeground() {
		return panelForeground;
	}

	public Color getPanelBackground() {
		return panelBackground;
	}

	public int getListThumbSize() {
		return listThumbSize;
	}

	public int getListGridSize() {
		return listGridSize;
	}

	public Color getListForeground() {
		return listForeground;
	}

	public Color getListBackground() {
		return listBackground;
	}

	public void setPanelGridSize(int panelGridSize) {
		this.panelGridSize = panelGridSize;
	}

	public void setPanelForeground(Color panelForeground) {
		this.panelForeground = panelForeground;
	}

	public void setPanelBackground(Color panelBackground) {
		this.panelBackground = panelBackground;
	}

	public void setListThumbSize(int listThumbSize) {
		this.listThumbSize = listThumbSize;
	}

	public void setListGridSize(int listGridSize) {
		this.listGridSize = listGridSize;
	}

	public void setListForeground(Color listForeground) {
		this.listForeground = listForeground;
	}

	public void setListBackground(Color listBackground) {
		this.listBackground = listBackground;
	}

}
