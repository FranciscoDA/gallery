package view.previewers.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import view.GridHandler;
import view.previewers.Previewer;

@SuppressWarnings("serial")
public class ImagePreviewer extends JPanel implements Previewer {
	private static String[] FileExtensions = {".bmp", ".png", ".jpg"}; 
	public String[] getExtensions() {
		return FileExtensions;
	}
	public class Listener extends java.awt.event.MouseAdapter {
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

	private Image image;
	
	@Override
	public void preview(String path, GridHandler h, int index) {
		setOpaque(false);
		try {
			Dimension d = this.getPreferredSize();
			this.image = ImageIO.read(new File(path)).getScaledInstance(
					d.width, d.height, Image.SCALE_SMOOTH
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.addMouseListener(new Listener(path, h, index));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; 
		g2d.drawImage(image, 0, 0, null);
	}

	@Override
	public void addMouseListener(MouseAdapter a) {
		super.addMouseListener(a);
	}
}
