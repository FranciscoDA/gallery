package view.viewers.impl;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JTextArea;

import view.VisorHandler;
import view.viewers.Viewer;

@SuppressWarnings("serial")
public class TextViewer extends JTextArea implements Viewer {
	private static Class<?>[] MODEL_CLASSES = {model.Text.class};
	@Override
	public Class<?>[] getSupportedClasses() {
		return MODEL_CLASSES;
	}

	public class Listener extends MouseAdapter {
		private VisorHandler handler;
		public Listener(VisorHandler h) {
			handler = h;
		}
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 1 && me.getID() == MouseEvent.MOUSE_CLICKED) {
				if (me.getButton() == MouseEvent.BUTTON1)
					handler.itemPrev();
				if (me.getButton() == MouseEvent.BUTTON3)
					handler.itemNext();
			}
		}
	}
	
	@Override
	public void view(String path, VisorHandler h) {
		try {
			this.setEditable(false);
			this.setText(new String(Files.readAllBytes(Paths.get(path))));
			this.addMouseListener(new Listener(h));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
