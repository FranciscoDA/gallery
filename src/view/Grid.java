package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ServiceLoader;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.GalleryElement;

import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;

import view.previewers.Previewer;

public class Grid {
	private static Pattern ExtensionPattern = Pattern.compile("(\\.[^.]+)$");

	private static HashMap<String, Class<?>> previewers;
	static {
		previewers = new HashMap<>();
		ServiceLoader<Previewer> previewLoader = ServiceLoader.load(Previewer.class);
		for (Previewer p : previewLoader) {
			for (String ext : p.getSupportedExtensions()) {
				previewers.put(ext, p.getClass());
			}
		}
	}
	private static int GRID_WIDTH = 2;
	private static int GRID_HEIGHT = 3;
	private static int CELL_SIZE = 125;

	private JFrame frame;
	private JPanel panel;
	private LinkedList<Component> components = new LinkedList<>();

	private GridHandler handler;

	@SuppressWarnings("serial")
	public Grid(GridHandler h) {
		handler = h;

		frame = new JFrame("Galeria");

		panel = new JPanel(new WrapLayout(FlowLayout.LEFT));

	    frame.add(new JScrollPane(panel));
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				handler.saveList();
				System.exit(0);
			}
		});

		frame.getContentPane().setBackground(new Color(0, 0, 0));

		panel.setDropTarget(new DropTarget() {
			public synchronized void drop(DropTargetDropEvent evt) {
				Transferable tr = evt.getTransferable();
				evt.acceptDrop(DnDConstants.ACTION_COPY);
				try {
					if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
						List<File> files = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
						for (File file: files) {
							handler.addItem(file.getPath());
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	public void addElement(GalleryElement image) {
		Matcher m = ExtensionPattern.matcher(image.getPath());
		if(!m.find())
			return;

		try {
			Component comp = (Component) previewers.get(m.group(1)).getConstructor().newInstance();
			comp.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
			if (comp instanceof Previewer) {
				Previewer p = (Previewer) comp;
				p.preview(image.getPath(), handler, components.size());
			}
			panel.add(comp);
			components.add(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void show() {
		frame.setSize((GRID_WIDTH + 1) * CELL_SIZE, (GRID_HEIGHT + 1) * CELL_SIZE);
		/* Luego se puede jugar un poco con los maximos y minimos del frame */
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
