package view.previewers;

import java.awt.event.MouseAdapter;

import view.GridHandler;

public interface Previewer {
	public String[] getExtensions();
	public void preview(String path, GridHandler h, int index);
	public void addMouseListener(MouseAdapter a);
}