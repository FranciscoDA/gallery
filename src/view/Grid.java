package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import previewers.*;

public class Grid {
	static {
		previewers = new HashMap<>();
		ServiceLoader<Previewer> previewLoader = ServiceLoader.load(Previewer.class);
		for (@SuppressWarnings("unused") Object p : previewLoader) {
		}
	}
	private static Pattern extensionPattern = Pattern.compile("(\\..+)$");

	private static int GRID_WIDTH = 3;
	private static int GRID_HEIGHT = 2;
	private static int CELL_SIZE = 150;
	private static int FRAME_HEIGHT = 25;
	private static int FRAME_WIDTH = 4;
	
	private JFrame frame;
	private Component[] components = new Component[GRID_WIDTH * GRID_HEIGHT];
	
	private GridHandler handler;
	private LinkedList<String> elements = new LinkedList<>();
	private static HashMap<String, Class<? extends Component>> previewers;
	
	public Grid() {
		frame = new JFrame("Galeria");
		frame.setLayout(new GridLayout(GRID_WIDTH, GRID_HEIGHT));
		frame.getContentPane().setBackground(new Color(0,0,0));
		frame.setSize(
			GRID_WIDTH * CELL_SIZE + FRAME_WIDTH,
			GRID_HEIGHT * CELL_SIZE + FRAME_HEIGHT
		);

		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	public static void registerPreviewer(String ext, Class<? extends Component> p) {
		previewers.put(ext, p);
	}

	public void setHandler(GridHandler h) {
		handler = h;
	}

	public void addElement(String element) {
		elements.add(element);
		if (elements.size() <= components.length) {
			Matcher m = extensionPattern.matcher(element);
			if (!m.find())
				return;
			try {
				Component comp = previewers.get(m.group(1)).getConstructor().newInstance();
				components[elements.size()-1] = comp;
				comp.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
				frame.add(comp);
				if (comp instanceof Previewer) {
					Previewer p = (Previewer) comp;
					p.setHandler(handler);
					p.preview(element);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void show() {
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
	}
}
