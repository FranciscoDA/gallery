package ioc;

import java.util.Collection;
import java.util.Iterator;

public interface IBean<T> {

	Collection<T> listAll();

	boolean saveAll(Iterator<T> i);

	String getName();

	int addInstance(Object i);

	Object getInstance(int id);

}
