package view.previewers.impl;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
	private static String FILE_EXTENSIONS[] = {".jpg", ".png", ".bmp"};
	@Override
	public String[] getSupportedExtensions() {
		return FILE_EXTENSIONS;
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
			BufferedImage src = ImageIO.read(new File(path));
			BufferedImage canvas = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
			float srcratio = ((float)src.getWidth()) / ((float)src.getHeight());
			float outratio = d.width / d.height;
			int width = d.width;
			int height = d.height;
			int xoffset = 0;
			int yoffset = 0;

			if (outratio < srcratio) {
				height = (int)(((float) d.width) / srcratio);
				yoffset = (int)((d.height - height)/2);
			} else if (outratio > srcratio) {
				width = (int)(((float) d.height) * srcratio);
				xoffset = (int)((d.width - width)/2);
			}
			Image image = src.getScaledInstance(
					width, height, Image.SCALE_SMOOTH
			);
			canvas.getGraphics().drawImage(image, xoffset, yoffset, null);
			this.setIcon(new ImageIcon(canvas));
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.addMouseListener(new Listener(path, h, index));
	}
	
	public void addMouseListener(MouseAdapter a) {
		super.addMouseListener(a);
	}
}
