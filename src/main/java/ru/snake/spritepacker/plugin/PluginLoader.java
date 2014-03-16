package ru.snake.spritepacker.plugin;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

public class PluginLoader {

	private static final String PLUGINS_DIR = "plugins";

	private final List<ExportPlugin> exportPlugins;
	private final List<ImportPlugin> importPlugins;

	public PluginLoader() {
		exportPlugins = new LinkedList<ExportPlugin>();
		importPlugins = new LinkedList<ImportPlugin>();
	}

	public Collection<String> loadPlugins() {
		String programDir = System.getProperty("user.dir");
		File pluginsDir = new File(programDir, PLUGINS_DIR);
		Collection<String> errors;

		if (pluginsDir.exists() && pluginsDir.isDirectory()) {
			errors = doLoadPlugins(pluginsDir);
		} else {
			errors = Collections.emptyList();
		}

		return errors;
	}

	private Collection<String> doLoadPlugins(File dir) {
		FileFilter filter = new JarFileFilter();
		File[] files = dir.listFiles(filter);
		Collection<String> errors = new ArrayList<String>();

		for (File each : files) {
			try {
				loadImportJar(each);
			} catch (MalformedURLException e) {
				errors.add(e.getLocalizedMessage());
			} catch (ServiceConfigurationError e) {
				errors.add(e.getLocalizedMessage());
			}

			try {
				loadExportJar(each);
			} catch (MalformedURLException e) {
				errors.add(e.getLocalizedMessage());
			} catch (ServiceConfigurationError e) {
				errors.add(e.getLocalizedMessage());
			}
		}

		return errors;
	}

	private void loadImportJar(File file) throws MalformedURLException {
		URI uri = file.toURI();
		URL url = uri.toURL();
		URL[] urls = new URL[] { url };

		URLClassLoader classLoader = new URLClassLoader(urls, getClass()
				.getClassLoader());
		ServiceLoader<ImportPlugin> serviceLoader = ServiceLoader.load(
				ImportPlugin.class, classLoader);

		for (ImportPlugin plugin : serviceLoader) {
			importPlugins.add(plugin);
		}
	}

	private void loadExportJar(File file) throws MalformedURLException {
		URI uri = file.toURI();
		URL url = uri.toURL();
		URL[] urls = new URL[] { url };

		URLClassLoader classLoader = new URLClassLoader(urls, getClass()
				.getClassLoader());
		ServiceLoader<ExportPlugin> serviceLoader = ServiceLoader.load(
				ExportPlugin.class, classLoader);

		for (ExportPlugin plugin : serviceLoader) {
			exportPlugins.add(plugin);
		}
	}

	public boolean hasImportPlugins() {
		return !importPlugins.isEmpty();
	}

	public boolean hasExportPlugins() {
		return !exportPlugins.isEmpty();
	}

	public List<ImportPlugin> getImportPlugins() {
		return importPlugins;
	}

	public List<ExportPlugin> getExportPlugins() {
		return exportPlugins;
	}

}
