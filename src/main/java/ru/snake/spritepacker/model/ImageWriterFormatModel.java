package ru.snake.spritepacker.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import ru.snake.spritepacker.R;

@SuppressWarnings("serial")
public class ImageWriterFormatModel extends DefaultComboBoxModel implements
		ComboBoxModel {

	private final Map<String, String> formatNames;
	private final String[] items;

	private String selectedItem;

	public ImageWriterFormatModel(String defaultFormat) {
		String[] formats = ImageIO.getWriterFormatNames();

		formatNames = new HashMap<String, String>();

		for (String format : formats) {
			String lowersuffix = format.toLowerCase();
			String uppersuffix = format.toUpperCase();
			String name;

			name = String
					.format(R.FILE_FILTER_FORMAT, uppersuffix, lowersuffix);

			formatNames.put(name, lowersuffix);
		}

		Set<String> formatKeys = formatNames.keySet();

		items = formatKeys.toArray(new String[0]);

		if (formatNames.containsValue(defaultFormat)) {
			for (Entry<String, String> formatEntry : formatNames.entrySet()) {
				if (defaultFormat.equals(formatEntry.getValue())) {
					selectedItem = formatEntry.getKey();

					break;
				}
			}
		} else if (items.length > 0) {
			selectedItem = items[0];
		} else {
			selectedItem = null;
		}
	}

	@Override
	public int getSize() {
		return items.length;
	}

	@Override
	public Object getElementAt(int index) {
		return items[index];
	}

	@Override
	public void setSelectedItem(Object anObject) {
		String selectedValue = anObject.toString();

		if (formatNames.containsKey(selectedValue)) {
			selectedItem = anObject.toString();
		} else {
			selectedItem = null;
		}
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	public String getSelectedFormat() {
		return formatNames.get(selectedItem);
	}

}
