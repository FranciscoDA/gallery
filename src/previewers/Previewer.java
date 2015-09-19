package previewers;

import view.GridHandler;

public interface Previewer {
	public void preview(String path);
	public void setHandler(GridHandler h);
}