package view.previewers.impl;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.GridHandler;
import view.previewers.Previewer;

@SuppressWarnings("serial")
public class ImagePreviewer extends JLabel implements Previewer {
	
	private static String[] FileExtensions = {".bmp", ".png", ".jpg"};
	
	public String[] getExtensions() {
		return FileExtensions;
	}
	
	public class Listener extends MouseAdapter {
		private String path;
		private GridHandler handler;
		private int index;

		public Listener(String path, GridHandler h, int i) {
			super();
			this.path = path;
			this.handler = h;
			this.index = i;
		}
		
		@Override
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 1 && me.getID() == MouseEvent.MOUSE_CLICKED) {
				handler.itemSelected(this.path, index);
			}
			me.consume();
		}
	}

	@Override
	public void preview(String path, GridHandler h, int index) {
		setOpaque(false);
		try {
			Dimension d = this.getPreferredSize();
			Image image = ImageIO.read(new File(path)).getScaledInstance(
					d.width, d.height, Image.SCALE_SMOOTH
			);
			this.setIcon(new ImageIcon(image));
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.addMouseListener(new Listener(path, h, index));
	}
	
	public void addMouseListener(MouseAdapter a) {
		super.addMouseListener(a);
	}
}
