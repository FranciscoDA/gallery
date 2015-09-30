package instantiator;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtensionInstantiator implements Instantiator {
	private static Pattern ExtensionPattern = Pattern.compile("(\\.[^.]+)$");
	private HashMap<Object, Class<?>> byExtension;
	
	public ExtensionInstantiator() {
		byExtension = new HashMap<>();
	}
	
	@Override
	public void register(Object criteria, Class<?> type) {
		byExtension.put(criteria, type);
	}

	@Override
	public Object instantiate(HashMap<String, Object> keyval) {
		Object path = keyval.get("path");
		if (path instanceof String) {
			Matcher m = ExtensionPattern.matcher((String) path);
			m.find();
			String ext = m.group(1);
			Class<?> type = byExtension.get(ext);
			if (type != null) {
				try {
					return type.getConstructor().newInstance();
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					System.out.println("Ocurrio un error al crear la instancia\n\t"+e.getMessage());
				}
			}
		}
		return null;
	}

}
