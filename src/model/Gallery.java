package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Gallery {
	private LinkedList<String> media;

	private ArrayList<Image> images;

	public Gallery(Collection<Image> c) {
		media = new LinkedList<>();
		this.images = new ArrayList<>();
		if (c != null) {
			this.images.addAll(c);
		}
		/*SOLUCION TEMPORAL PARA NO MODIFICAR TODO*/
		for (Iterator iterator = c.iterator(); iterator.hasNext();) {
			Image image = (Image) iterator.next();
			addResource(image.getPath());
		}
	}

	public Gallery() {
		this(null);
	}

	public void addResource(String path) {
		media.add(path);
	}

	public ListIterator<String> getResources() {
		return media.listIterator();
	}

	public ListIterator<String> getResources(int i) {
		return media.listIterator(i);
	}
	
	public ListIterator<Image> getImages(){
		return images.listIterator();
	}
}
