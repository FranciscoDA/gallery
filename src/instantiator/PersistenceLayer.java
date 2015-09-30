package instantiator;

public interface PersistenceLayer {
	//public <T> void register(Class<T> type);
	
	public void add(Object o);
	public <T> Iterable<T> get(Class<T> type);
	public void delete(Object o);

	public void load();
	public void save();
}
