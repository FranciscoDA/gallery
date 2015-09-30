package view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.ServiceLoader;

import javax.swing.JFrame;

import model.GalleryElement;

import view.viewers.Viewer;

public class Visor {
	private static HashMap<Class<?>, Class<?>> viewers;
	static {
		viewers = new HashMap<>();
		ServiceLoader<Viewer> viewLoader = ServiceLoader.load(Viewer.class);
		for (Viewer v : viewLoader) {
			for (Class<?> c : v.getSupportedClasses()) {
				viewers.put(c, v.getClass());
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
	public void setElement(GalleryElement element) {
		String path = element.getPath();
		frame.setTitle(path);
		frame.getContentPane().removeAll();
		try {
			Component comp = (Component) viewers.get(element.getClass()).getConstructor().newInstance();
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
