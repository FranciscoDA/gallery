package controller;
import instantiator.PersistenceLayer;

import java.util.ListIterator;

import model.Gallery;
import model.GalleryElement;
import view.Grid;
import view.GridHandler;

public class GalleryController implements GridHandler {
	private PersistenceLayer persistor;
	private Gallery gallery;
	private Grid grid;
	
	public GalleryController(Gallery gallery, PersistenceLayer persistor) {
		this.persistor = persistor;
		this.gallery = gallery;
		this.grid = new Grid(this);
		ListIterator<GalleryElement> i = gallery.getElements();
		while(i.hasNext()) {
			grid.addElement(i.next());
		}
		grid.show();
	}

	@Override
	public void saveList() {
		persistor.save();
	}

	@Override
	public void itemSelected(String path, int index) {
		new MediaController(gallery.getElements(index));
	}

	@Override
	public void addItem(String path) {
		//TODO: COMO CREO LA INSTANCIA??
		/*gallery.addElement(path);
		grid.addElement(path);
		grid.show();*/
	}
}
