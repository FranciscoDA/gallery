package controller;
import java.util.ListIterator;

import instantiator.PersistentLoader;
import instantiator.PersistentSaver;
import model.Gallery;
import view.Grid;
import view.GridHandler;

public class GalleryController {
	private static Gallery gallery;
	private static PersistentLoader loader = new PersistentLoader();
	private static PersistentSaver saver = new PersistentSaver();
	private static Grid grid;
	private static final String LIST_FILE = "data.xml";

	public static void main(String[] args) {
		gallery = new Gallery();
		loader.load(gallery, LIST_FILE);
		
		grid = new Grid(new GridHandler() {
			public void itemSelected(String path, int index) {
				MediaController.view(gallery.getResources(index));
			}
			public void addItem(String path) {
				GalleryController.addElement(path);
			}
			public void saveList() {
				GalleryController.saveList();
			}
		});
		ListIterator<String> i = gallery.getResources();
		while(i.hasNext()) {
			grid.addElement(i.next());
		}
		grid.show();
	}
	
	public GalleryController(Gallery gallery) {
		
		GalleryController.gallery = gallery;
		
		grid = new Grid(new GridHandler() {
			public void itemSelected(String path, int index) {
				MediaController.view(gallery.getResources(index));
			}
			public void addItem(String path) {
				GalleryController.addElement(path);
			}
			public void saveList() {
				GalleryController.saveList();
			}
		});
		ListIterator<String> i = gallery.getResources();
		while(i.hasNext()) {
			grid.addElement(i.next());
		}
		grid.show();
	}

	public static void addElement(String path) {
		gallery.addResource(path);
		grid.addElement(path);
		grid.show();
	}

	public static void saveList() {
		saver.save(gallery.getResources(), LIST_FILE);
	}
}
