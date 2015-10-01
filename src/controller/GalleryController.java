package controller;
import instantiator.Instantiator;
import instantiator.PersistenceLayer;

import java.util.HashMap;
import java.util.ListIterator;

import model.Gallery;
import model.GalleryElement;
import view.Grid;
import view.GridHandler;

public class GalleryController implements GridHandler {
	private PersistenceLayer persistor;
	private Instantiator instantiator;
	private Gallery gallery;
	private Grid grid;
	
	public GalleryController(Gallery gallery, PersistenceLayer persistor, Instantiator instantiator) {
		this.instantiator = instantiator;
		this.persistor = persistor;
		this.gallery = gallery;
		this.grid = new Grid(this);
		ListIterator<GalleryElement> i = gallery.getElements();
		while(i.hasNext()) {
			grid.addElement(i.next().getPath());
		}
		grid.show();
	}

	@Override
	public void closeWindow() {
		persistor.save();
		System.exit(0);
	}

	@Override
	public void itemSelected(String path, int index) {
		new MediaController(gallery.getElements(index));
	}

	@Override
	public void addItem(String path) {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("path", path);
		GalleryElement ge = instantiator.instantiate(GalleryElement.class, hm);
		gallery.addElement(ge);
		grid.addElement(ge.getPath());
		persistor.add(ge);
		grid.show();
	}
}
