import java.util.LinkedList;
import java.util.ListIterator;


public class Gallery {
	private LinkedList<String> media = new LinkedList<>();
	
	public void addResource(String path) {
		media.add(path);
	}
	public ListIterator<String> getResources() {
		return media.listIterator();
	}
}
