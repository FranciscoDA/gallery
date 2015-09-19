package previewers.impl;

import view.Grid;
import view.GridHandler;

import java.io.File;
import java.util.Scanner;

import javax.swing.JTextArea;

import previewers.DefaultMouseListener;
import previewers.Previewer;

@SuppressWarnings("serial")
public class TextPreviewer extends JTextArea implements Previewer {
	static {
		Grid.registerPreviewer(".txt", TextPreviewer.class);
		Grid.registerPreviewer(".xml", TextPreviewer.class);
	}
	
	private static int MAX_LINES = 9;
	private static int MAX_CHARS = 25;

	private GridHandler handler;

	@Override
	public void preview(String path) { 
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
		
		this.addMouseListener(new DefaultMouseListener(path, handler));
	}

	@Override
	public void setHandler(GridHandler h) {
		this.handler = h;
	}
}
