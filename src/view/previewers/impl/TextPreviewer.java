package view.previewers.impl;

import view.GridHandler;
import view.previewers.Previewer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Scanner;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class TextPreviewer extends JTextArea implements Previewer {
	private static String[] FileExtensions = {".txt", ".xml"}; 
	public String[] getExtensions() {
		return FileExtensions;
	}

	public class Listener extends MouseAdapter {
		private String path;
		private GridHandler handler;
		private int index;

		public Listener(String path, GridHandler h, int i) {
			super();
			this.path = path;
			this.handler = h;
			this.index = i;
		}
		
		@Override
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 1 && me.getID() == MouseEvent.MOUSE_CLICKED) {
				handler.itemSelected(this.path, index);
			}
			me.consume();
		}
	}
	
	private static int MAX_LINES = 9;
	private static int MAX_CHARS = 25;

	@Override
	public void preview(String path, GridHandler h, int index) { 
		File f = new File(path);
		try {
			Scanner sc = new Scanner(f);
			String s = "";
			for(int i = 0; i < MAX_LINES && sc.hasNextLine(); i++) {
				String line = sc.nextLine();
				s += line.substring(0, Math.min(MAX_CHARS, line.length())) + '\n';
			}
			this.setEditable(false);
			this.setText(s);
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.addMouseListener(new Listener(path, h, index));
	}
}
