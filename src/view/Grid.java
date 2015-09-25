package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.previewers.Previewer;

public class Grid {
	private static HashMap<String, Class<?>> previewers;

	static {
		previewers = new HashMap<>();
		ServiceLoader<Previewer> previewLoader = ServiceLoader.load(Previewer.class);
		for (Previewer p : previewLoader) {
			for (String ext : p.getExtensions()) {
				previewers.put(ext, p.getClass());
			}
		}
	}
	private static Pattern ExtensionPattern = Pattern.compile("(\\.[^.]+)$");
	private static int GRID_WIDTH = 2;
	private static int GRID_HEIGHT = 3;
	private static int CELL_SIZE = 125;

	private JFrame frame;
	private JPanel panel;
	private LinkedList<Component> components = new LinkedList<>();

	private GridHandler handler;

	public Grid(GridHandler h) {
		handler = h;

		frame = new JFrame("Galeria");
		/* No permite scroll */
		//frame.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
		/* De esta manera, se permite una galeria a pantalla completa */
		panel = new JPanel(new WrapLayout(FlowLayout.LEFT));

	    frame.add(new JScrollPane(panel));
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});

		frame.getContentPane().setBackground(new Color(0, 0, 0));
	}

	public void addElement(String element) {
		Matcher m = ExtensionPattern.matcher(element.toLowerCase());
		if (!m.find())
			return;
		try {
			Component comp = (Component) previewers.get(m.group(1)).getConstructor().newInstance();
			comp.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
			if (comp instanceof Previewer) {
				Previewer p = (Previewer) comp;
				p.preview(element, handler, components.size());
			}
			panel.add(comp);
			components.add(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void show() {
		frame.setSize((GRID_WIDTH + 1) * CELL_SIZE, (GRID_HEIGHT + 1) * CELL_SIZE);
		/* Luego se puede jugar un poco con los maximos y minimos del frame */
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
