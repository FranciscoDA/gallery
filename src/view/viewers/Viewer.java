package view.viewers;

import view.VisorHandler;

public interface Viewer {
	public void view(String path, VisorHandler h);
	public Class<?>[] getSupportedClasses();
}