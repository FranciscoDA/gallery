import java.util.ListIterator;

import view.Grid;
import view.GridHandler;

public class GalleryController {
	private static Gallery gallery = new Gallery();
	private static PersistentLoader loader = new PersistentLoader();
	private static Grid grid;

	public static void main(String[] args) {
		loader.load(gallery, "data.xml");
		
		grid = new Grid(new GridHandler() {
			public void itemSelected(String path, int index) {
				MediaController.view(gallery.getResources(index));
			}
			public void addItem(String path) {
				GalleryController.addElement(path);
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
}
