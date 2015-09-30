package controller;
import java.util.ListIterator;

import model.GalleryElement;

import view.Visor;
import view.VisorHandler;


public class MediaController implements VisorHandler {
	private ListIterator<GalleryElement> current;
	private Visor visor;

	public MediaController(ListIterator<GalleryElement> pos) {
		current = pos;
		if (visor == null)
			visor = new Visor(this);
		visor.setElement(pos.next());
		visor.show();
	}

	@Override
	public void itemNext() {
		if(current.hasNext()) {
			visor.setElement(current.next());
			//MediaController.view(current);
		}
	}

	@Override
	public void itemPrev() {
		for (int i=0; i < 2; i++)
			if(current.hasPrevious())
				current.previous();
		visor.setElement(current.next());
	}
}
