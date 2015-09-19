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
import javax.swing.JLabel;

import previewers.*;

public class Grid {
	static {
		previewers = new HashMap<>();
		ServiceLoader<Previewer> previewLoader = ServiceLoader.load(Previewer.class);
		for (@SuppressWarnings("unused") Object p : previewLoader) {
		}
	}
	private static Pattern extensionPattern = Pattern.compile("(\\..+)$");

	private static int GRID_WIDTH = 2;
	private static int GRID_HEIGHT = 3;
	private static int CELL_SIZE = 125;
	
	private JFrame frame;
	private LinkedList<Component> components = new LinkedList<>();
	
	private GridHandler handler;
	private LinkedList<String> elements = new LinkedList<>();
	private static HashMap<String, Class<? extends Component>> previewers;
	
	public Grid() {
		frame = new JFrame("Galeria");
		frame.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));

		frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent we) {
					System.exit(0);
				}
			});
		
		frame.getContentPane().setBackground(new Color(0,0,0));
	}

	public static void registerPreviewer(String ext, Class<? extends Component> p) {
		previewers.put(ext, p);
	}

	public void setHandler(GridHandler h) {
		handler = h;
	}

	public void addElement(String element) {
		elements.add(element);
		Matcher m = extensionPattern.matcher(element);
		if (!m.find())
			return;
		try {
			Component comp = previewers.get(m.group(1)).getConstructor().newInstance();
			components.add(comp);
			comp.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
			if (comp instanceof Previewer) {
				Previewer p = (Previewer) comp;
				p.setHandler(handler);
				p.preview(element);
			}
			frame.add(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void show() {
		/* rellena la cuadricula con jlabels vacios para que el gridlayout no se deforme */
		if (components.size() < GRID_HEIGHT * GRID_WIDTH) {
			for (int i = 0; i < GRID_HEIGHT * GRID_WIDTH - components.size(); i++) {
				frame.add(new JLabel());
			}
		}
		frame.pack();
		frame.setVisible(true);
		
	}
}
