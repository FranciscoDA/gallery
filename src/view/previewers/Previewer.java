package view.previewers;

import view.GridHandler;

public interface Previewer {
	public String[] getExtensions();
	public void preview(String path, GridHandler h, int index);
}