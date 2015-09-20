package view.viewers.impl;

import java.awt.event.MouseAdapter;

import view.GridHandler;
import view.viewers.Viewer;

public class ImageViewer implements Viewer {
	private static String[] FileExtensions = {".bmp", ".png", ".jpg"};
	@Override
	public String[] getExtensions() {
		return FileExtensions;
	}

	@Override
	public void preview(String path, GridHandler h, int index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMouseListener(MouseAdapter a) {
		// TODO Auto-generated method stub
		
	}

}