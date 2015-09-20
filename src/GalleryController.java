import java.util.ListIterator;

import view.Grid;
import view.GridHandler;

public class GalleryController {
	private static class Handler implements GridHandler {
		@Override
		public void itemSelected(String path, int index) {
			MediaController.view(gallery.getResources(index));
		}
	}
	private static Gallery gallery = new Gallery();
	private static PersistentLoader loader = new PersistentLoader();

	public static void main(String[] args) {
		loader.load(gallery, "data.xml");
		
		Grid grid = new Grid(new Handler());
		ListIterator<String> i = gallery.getResources();
		while(i.hasNext()) {
			grid.addElement(i.next());
		}
		grid.show();
	}
}
