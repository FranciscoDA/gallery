package view.previewers;

import view.GridHandler;

public interface Previewer {
	public Class<?>[] getSupportedClasses();
	public void preview(String path, GridHandler h, int index);
}