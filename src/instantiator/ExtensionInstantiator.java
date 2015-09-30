package instantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
					Constructor<?> ctor = type.getConstructor();
					boolean ctorAccessible = ctor.isAccessible();
					ctor.setAccessible(true);
					Object o = ctor.newInstance();
					ctor.setAccessible(ctorAccessible);
					for (String key : keyval.keySet()) {
						Field f = type.getDeclaredField(key);
						boolean fieldAccessible = f.isAccessible();
						f.setAccessible(true);
						type.getField(key).set(o, keyval.get(key));
						f.setAccessible(fieldAccessible);
					}
					return o;
				} catch (Exception e) {
					System.out.println("Ocurrio un error al crear la instancia\n\t"+e.getMessage());
				}
			}
		}
		return null;
	}

	@Override
	public <T> T instantiate(Class<T> type, HashMap<String, Object> keyval) {
		try {
			Constructor<T> ctor = type.getConstructor();
			boolean ctorAccessible = ctor.isAccessible();
			ctor.setAccessible(true);
			T o = ctor.newInstance();
			ctor.setAccessible(ctorAccessible);
			for (Field f : type.getDeclaredFields()) {
				boolean fieldAccessible = f.isAccessible();
				f.setAccessible(true);
				f.set(o, keyval.get(f.getName()));
				f.setAccessible(fieldAccessible);
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
