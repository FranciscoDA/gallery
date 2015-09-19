package previewers.impl;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import previewers.DefaultMouseListener;
import previewers.Previewer;
import view.Grid;
import view.GridHandler;

@SuppressWarnings("serial")
public class ImagePreviewer extends JPanel implements Previewer {
	static {
		Grid.registerPreviewer(".bmp", ImagePreviewer.class);
		Grid.registerPreviewer(".png", ImagePreviewer.class);
		Grid.registerPreviewer(".jpg", ImagePreviewer.class);
	}
	
	private GridHandler handler;
	private Image image;
	
	@Override
	public void preview(String path) {
		setOpaque(false);
		try {
			
			Dimension d = this.getPreferredSize();
			this.image = ImageIO.read(new File(path)).getScaledInstance(
					d.width, d.height, Image.SCALE_SMOOTH
			);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.addMouseListener(new DefaultMouseListener(path, handler));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g; 
		g2d.drawImage(image, 0, 0, null);
		
	}

	@Override
	public void setHandler(GridHandler h) {
		this.handler = h;
	}
}
