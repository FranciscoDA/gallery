import java.util.ListIterator;

import view.Visor;
import view.VisorHandler;


public class MediaController {
	private static ListIterator<String> current;
	private static Visor visor;
	private static VisorHandler defaultHandler = new VisorHandler() {
		public void itemNext() {
			if(current.hasNext()) {
				MediaController.view(current);
			}
		}
		public void itemPrev() {
			for (int i=0; i < 2; i++)
				if(current.hasPrevious())
					current.previous();
			MediaController.view(current);
		}
	};

	public static void view(ListIterator<String> pos) {
		current = pos;
		if (visor == null)
			visor = new Visor(defaultHandler);
		visor.setElement(pos.next());
		visor.show();
	}
}
