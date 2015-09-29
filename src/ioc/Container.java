package ioc;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class Container {

	static protected HashMap<Class<?>, IBean> mapper = new HashMap<>();
	static protected Class<?>[] nonReflectable = { boolean.class, byte.class, short.class, int.class, long.class,
			Boolean.class, Byte.class, Short.class, Integer.class, Long.class, float.class, double.class, Float.class,
			Double.class, String.class };

	public void register(Class<?> type) {
		mapper.put(type, new BeanFactory<>(type));
	}

	public Iterable<Class<?>> getClasses() {
		return mapper.keySet();
	}

	public IBean get(String name) {
		try {
			return get(Class.forName(name));
		} catch (Exception e) {
			return null;
		}
	}

	public IBean get(Class<?> c) {
		try {
			return mapper.get(c);
		} catch (Exception e) {
			return null;
		}
	}

	public abstract boolean loadFrom(String file);

	public static boolean isReflectable(Class<?> c) {
		for (Class<?> cl : nonReflectable) {
			if (cl == c)
				return false;
		}
		return true;
	}
	
	
	public boolean setAttribute(Object instance, String attrName, Object value) {
		try {
			Field f = instance.getClass().getDeclaredField(attrName);
			boolean oldaccessible = f.isAccessible();
			f.setAccessible(true);
			f.set(instance, value);
			f.setAccessible(oldaccessible);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Object getAttribute(Object instance, String attrName) {
		try {
			Field f = instance.getClass().getDeclaredField(attrName);
			boolean oldaccessible = f.isAccessible();
			f.setAccessible(true);
			Object o = f.get(instance);
			f.setAccessible(oldaccessible);
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Class<?> getAttributeClass(Object o, String attrName) {
		try {
			Field f = o.getClass().getDeclaredField(attrName);
			return f.getType();
		} catch (Exception e) {
			return null;
		}
	}

}
