package view.viewers;

import view.VisorHandler;

public interface Viewer {
	public String[] getExtensions();
	public void view(String path, VisorHandler h);
}