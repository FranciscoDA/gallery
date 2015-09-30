package controller;

import instantiator.ExtensionInstantiator;
import instantiator.XMLContainer;
import model.Gallery;
import model.GalleryElement;

public class Controller {
	public static void main(String[] args) {
		Gallery gallery = new Gallery();

		ExtensionInstantiator instantiator = new ExtensionInstantiator();
		XMLContainer container = new XMLContainer();
		container.load();

		for (Object o : container.getAll(model.GalleryElement.class)) {
			if (o instanceof model.GalleryElement) {
				gallery.addElement((GalleryElement)o);
			}
		}
		
		new GalleryController(gallery, container, instantiator);
	}

}
