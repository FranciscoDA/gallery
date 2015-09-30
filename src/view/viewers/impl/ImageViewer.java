package view.viewers.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import view.VisorHandler;
import view.viewers.Viewer;

@SuppressWarnings("serial")
public class ImageViewer extends JPanel implements Viewer {
	private static Class<?> MODEL_CLASSES[] = {model.Image.class};
	@Override
	public Class<?>[] getSupportedClasses() {
		return MODEL_CLASSES;
	}
	
	public class Listener extends MouseAdapter {
		private VisorHandler handler;
		public Listener(VisorHandler h) {
			handler = h;
		}
		public void mouseClicked(MouseEvent me) {
			if (me.getClickCount() == 1 && me.getID() == MouseEvent.MOUSE_CLICKED) {
				if (me.getButton() == MouseEvent.BUTTON1)
					handler.itemPrev();
				if (me.getButton() == MouseEvent.BUTTON3)
					handler.itemNext();
			}
		}
	}

	private Image image;
	
	@Override
	public void view(String path, VisorHandler h) {
		setOpaque(false);
		try {
			this.image = ImageIO.read(new File(path));
			Dimension d = new Dimension(image.getWidth(null), image.getHeight(null));
			this.setPreferredSize(d);
			this.addMouseListener(new Listener(h));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; 
		g2d.drawImage(image, 0, 0, null);
	}

}