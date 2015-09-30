package instantiator;

import java.util.HashMap;

public interface Instantiator {
	public void register(Object criteria, Class<?> type);
	public Object instantiate(HashMap<String, Object> keyval);
}
