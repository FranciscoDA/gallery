package previewers;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import view.GridHandler;

public class DefaultMouseListener extends MouseAdapter {
	private String path;
	private GridHandler handler;

	public DefaultMouseListener(String path, GridHandler h) {
		super();
		this.path = path;
		this.handler = h;
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getClickCount() == 1 && me.getID() == MouseEvent.MOUSE_CLICKED) {
			handler.previewSelected(this.path);
		}
		me.consume();
	}
}
