package ioc;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class BeanFactory<T extends Object> implements IBean<Object> {

	protected ArrayList<T> instanceList = new ArrayList<>();
	protected HashMap<T, Integer> instanceMap = new HashMap<>();
	protected Class<T> classType = null;
	protected Constructor<T> ctor = null;
	protected LinkedList<Field> references = new LinkedList<>();
	
	public BeanFactory(Class<T> classType) {
		this.classType = classType;
		try {
			this.ctor = classType.getConstructor();
			for (Field f : classType.getDeclaredFields()) {
				Class<?> fc = f.getType();
				if (Container.isReflectable(fc)) {
					references.add(f);
				}
			}
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getName() {
		return classType.getSimpleName();
	}

	@Override
	public Collection listAll() {
		return this.instanceList;
	}

	@Override
	public boolean saveAll(Iterator i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int addInstance(Object i) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getInstance(int id) {
		if (id >= instanceList.size()) {
			instanceList.ensureCapacity(id + 1);
			for (int j = instanceList.size(); j <= id; j++) {
				T instance;
				boolean oldaccessible = ctor.isAccessible();
				ctor.setAccessible(true);
				try {
					instance = this.ctor.newInstance();
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					e.printStackTrace();
					return null;
				}
				ctor.setAccessible(oldaccessible);
				instanceList.add(instance);
				instanceMap.put(instance, j);
			}
		}
		return instanceList.get(id);
	}
		
}
