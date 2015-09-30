package model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;

public class Gallery {
	private LinkedList<GalleryElement> media;

	public Gallery(Collection<GalleryElement> c) {
		media = new LinkedList<>();
		if (c != null) {
			this.media.addAll(c);
		}
	}

	public Gallery() {
		media = new LinkedList<>();
	}

	public void addElement(GalleryElement i) {
		media.add(i);
	}

	public ListIterator<GalleryElement> getElements() {
		return media.listIterator();
	}

	public ListIterator<GalleryElement> getElements(int i) {
		return media.listIterator(i);
	}
}
