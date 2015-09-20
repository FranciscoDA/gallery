package view;

import java.util.HashMap;
import java.util.ServiceLoader;

import view.viewers.Viewer;

public class Visor {
	private static HashMap<String, Class<?>> viewers;
	static {
		viewers = new HashMap<>();
		ServiceLoader<Viewer> viewLoader = ServiceLoader.load(Viewer.class);
		for (Viewer v : viewLoader) {
			for (String ext : v.getExtensions()) {
				viewers.put(ext, v.getClass());
			}
		}
	}
	
	private VisorHandler handler;
	public Visor(VisorHandler h) {
		handler = h;
	}

	public void view(String path) {
		
	}
}
