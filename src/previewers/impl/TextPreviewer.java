package previewers.impl;

import view.Grid;
import java.io.File;
import java.util.Scanner;

import javax.swing.JTextArea;

import previewers.Previewer;

@SuppressWarnings("serial")
public class TextPreviewer extends JTextArea implements Previewer {
	static {
		Grid.registerPreviewer(".txt", TextPreviewer.class);
		Grid.registerPreviewer(".xml", TextPreviewer.class);
	}
	
	private static int MAX_LINES = 9;
	private static int MAX_CHARS = 25;

	public TextPreviewer(){}
	public TextPreviewer(String path, int w, int h) {
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
	}
}
