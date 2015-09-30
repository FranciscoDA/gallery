package controller;

import instantiator.XMLContainer;
import model.Gallery;
import model.GalleryElement;

public class Controller {
	public static void main(String[] args) {
		Gallery gallery = new Gallery();

		XMLContainer container = new XMLContainer();
		container.load();

		for (Object o : container.getAll(model.GalleryElement.class)) {
			if (o instanceof model.GalleryElement) {
				gallery.addElement((GalleryElement)o);
			}
		}
		
		new GalleryController(gallery, container);
	}

}
