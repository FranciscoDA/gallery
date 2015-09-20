import java.util.ListIterator;

import view.Visor;
import view.VisorHandler;


public class MediaController {
	private static class Handler implements VisorHandler {
		Visor visor;
		ListIterator<String> current;
		public Handler(Visor v, ListIterator<String> pos) {
			visor = v;
			current = pos;
		}
		@Override
		public void itemNext() {
			if (current.hasNext())
				visor.view(current.next());
		}
		@Override
		public void itemPrev() {
			if (current.hasPrevious())
				visor.view(current.previous());
		}
	}
	public static void view(ListIterator<String> pos) {
		Visor visor = null;
		visor = new Visor(new Handler(visor, pos));
	}
}
