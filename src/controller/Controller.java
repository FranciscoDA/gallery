package controller;

import instantiator.ExtensionInstantiator;
import instantiator.XMLContainer;
import model.Gallery;
import model.GalleryElement;
import model.Image;
import model.Text;

public class Controller {
	public static void main(String[] args) {
		Gallery gallery = new Gallery();

		ExtensionInstantiator instantiator = new ExtensionInstantiator();
		// ARREGLAR PLS
		instantiator.register(".jpg", Image.class);
		instantiator.register(".png", Image.class);
		instantiator.register(".bmp", Image.class);
		instantiator.register(".txt", Text.class);
		instantiator.register(".xml", Text.class);
		
		XMLContainer container = new XMLContainer();
		container.load();

		for (Object o : container.get(model.GalleryElement.class)) {
			if (o instanceof model.GalleryElement) {
				gallery.addElement((GalleryElement)o);
			}
		}
		
		new GalleryController(gallery, container);
	}

}
