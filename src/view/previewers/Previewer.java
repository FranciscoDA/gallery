package view.previewers;

import view.GridHandler;

public interface Previewer {
	public String[] getSupportedExtensions();
	public void preview(String path, GridHandler h, int index);
}