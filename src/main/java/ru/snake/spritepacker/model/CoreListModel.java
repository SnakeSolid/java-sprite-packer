package ru.snake.spritepacker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;

@SuppressWarnings("serial")
public class CoreListModel<T> extends AbstractListModel {

	private final List<T> items;

	public CoreListModel() {
		items = new ArrayList<T>();
	}

	@Override
	public Object getElementAt(int index) {
		return items.get(index);
	}

	@Override
	public int getSize() {
		return items.size();
	}

	public void addItem(T item) {
		int index = items.size();

		items.add(item);

		fireIntervalAdded(this, index, index);
	}

	public void setItem(int index, T item) {
		items.set(index, item);

		fireContentsChanged(this, index, index);
	}

	public void removeItem(T item) {
		int index = items.indexOf(item);

		if (index != -1) {
			items.remove(item);

			fireIntervalRemoved(this, index, index);
		}
	}

	public List<T> getList() {
		return Collections.unmodifiableList(items);
	}

	public void setList(Collection<T> items) {
		int oldsize = this.items.size();
		int newsize = items.size();

		this.items.clear();
		this.items.addAll(items);

		if (newsize == 0 && oldsize == 0) {
		} else if (newsize == 0 && newsize < oldsize) {
			fireIntervalRemoved(this, 0, oldsize - 1);
		} else if (newsize < oldsize) {
			fireContentsChanged(this, 0, newsize - 1);
			fireIntervalRemoved(this, newsize, oldsize - 1);
		} else if (newsize == oldsize) {
			fireContentsChanged(this, 0, oldsize - 1);
		} else if (newsize > oldsize && oldsize == 0) {
			fireIntervalAdded(this, 0, newsize - 1);
		} else if (newsize > oldsize) {
			fireContentsChanged(this, 0, oldsize - 1);
			fireIntervalAdded(this, oldsize, newsize - 1);
		}
	}

}
