package view.viewers;
import java.awt.event.MouseAdapter;
import view.GridHandler;

public interface Viewer {
	public String[] getExtensions();
	public void preview(String path, GridHandler h, int index);
	public void addMouseListener(MouseAdapter a);
}