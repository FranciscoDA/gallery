package view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.ServiceLoader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import view.viewers.Viewer;

public class Visor {
	private static Pattern ExtensionPattern = Pattern.compile("(\\.[^.]+)$");
	private static HashMap<String, Class<?>> viewers;
	static {
		viewers = new HashMap<>();
		ServiceLoader<Viewer> viewLoader = ServiceLoader.load(Viewer.class);
		for (Viewer v : viewLoader) {
			for (String ext : v.getSupportedExtensions()) {
				viewers.put(ext, v.getClass());
			}
		}
	}
	
	private static int DISPLAY_WIDTH = 800;
	private static int DISPLAY_HEIGHT = 600;
	
	private JFrame frame;
	
	private VisorHandler handler;
	public Visor(VisorHandler h) {
		handler = h;
		frame = new JFrame("Documento");
		frame.setResizable(false);
	}

	public void setElement(String path) {
		Matcher m = ExtensionPattern.matcher(path);
		if (!m.find())
			return;
		frame.setTitle(path);
		frame.getContentPane().removeAll();
		try {
			Component comp = (Component) viewers.get(m.group(1)).getConstructor().newInstance();
			comp.setPreferredSize(new Dimension(DISPLAY_WIDTH, DISPLAY_HEIGHT));
			if (comp instanceof Viewer) {
				Viewer v = (Viewer) comp;
				v.view(path, handler);
			}
			frame.getContentPane().setPreferredSize(comp.getPreferredSize());
			frame.add(comp);
			frame.pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void show() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
