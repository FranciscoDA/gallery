package previewers.impl;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import previewers.Previewer;
import view.Grid;

@SuppressWarnings("serial")
public class ImagePreviewer extends JPanel implements Previewer {
	static {
		Grid.registerPreviewer(".bmp", ImagePreviewer.class);
		Grid.registerPreviewer(".png", ImagePreviewer.class);
		Grid.registerPreviewer(".jpg", ImagePreviewer.class);
	}
	public ImagePreviewer(){}
	public ImagePreviewer(String path, int w, int h) {
		try {
			BufferedImage img = ImageIO.read(new File(path));
			this.add(new JLabel(new ImageIcon(
				img.getScaledInstance(w, h, Image.SCALE_SMOOTH)
			)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
