package instantiator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XMLContainer implements PersistenceLayer {
	private static String FILE_PATH = "data.xml"; 

	/**
	 * Iterador que devuelve instancias de T
	 * Pueden ser de la misma clase o una subclase
	 * 
	 * @author francisco
	 *
	 * @param <T> Clase base
	 */
	class ClassBasedIterator<T> implements Iterator<T> {
		private Class<T> baseClass;
		private Iterator<Object> baseIterator;
		// trampa: para no recalcular en hasnext y next, se precalcula el sgte elemento
		T preparedNextElement; 

		@SuppressWarnings("unchecked")
		public ClassBasedIterator(Class<T> baseClass, Iterator<Object> baseIterator) {
			this.baseClass = baseClass;
			this.baseIterator = baseIterator;
			preparedNextElement = null;
			while (baseIterator.hasNext() && preparedNextElement == null) {
				Object o = baseIterator.next();
				if (baseClass.isAssignableFrom(o.getClass())) {
					preparedNextElement = (T)o;
				}
			}
		}

		@Override
		public boolean hasNext() {
			return preparedNextElement != null;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T next() {
			T oldNext = preparedNextElement;
			preparedNextElement = null;
			while (baseIterator.hasNext() && preparedNextElement == null) {
				Object o = baseIterator.next();
				if (baseClass.isAssignableFrom(o.getClass())) {
					preparedNextElement = (T)o;
				}
			}
			return oldNext;
		}

		@Override
		public void remove() {}
	}
	
	class ClassBasedIterable<T> implements Iterable<T> {
		private Class<T> baseClass;
		private Iterable<Object> baseIterable;
		public ClassBasedIterable(Class<T> baseClass, Iterable<Object> baseIterable) {
			this.baseClass = baseClass;
			this.baseIterable = baseIterable;
		}
		@Override
		public Iterator<T> iterator() {
			return new ClassBasedIterator<T>(baseClass, baseIterable.iterator());
		}
		
	}
	
	private LinkedList<Object> instances;
	
	public XMLContainer() {
		instances = new LinkedList<>();
	}

	@Override
	public void add(Object o) {
		instances.add(o);
	}

	@Override
	public <T> Iterable<T> getAll(Class<T> type) {
		return new ClassBasedIterable<T>(type, instances);
	}

	@Override
	public void delete(Object o) {
		instances.remove(o);
	}
	
	private class SAXLoader extends DefaultHandler {
		private XMLContainer container;
		public SAXLoader(XMLContainer container) {
			this.container = container;
		}

		public void startElement(String ns, String lname, String sname, Attributes atts) {
			if (sname.equals("instance")) {
				Class<?> currentClass = null;
				try {
					currentClass = Class.forName(atts.getValue("class"));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				if (currentClass != null) {
					try {
						
						Constructor<?> ctor = currentClass.getConstructor();
						boolean ctorOld = ctor.isAccessible();
						Object o = ctor.newInstance();
						for (Field f : currentClass.getDeclaredFields()) {
							boolean fieldOld = f.isAccessible();
							f.setAccessible(true);
							f.set(o, atts.getValue(f.getName()));
							f.setAccessible(fieldOld);
						}
						ctor.setAccessible(ctorOld);
						container.add(o);
					} catch(NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void load() {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(new SAXLoader(this));
			reader.parse(FILE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "persistent", null);
			document.setXmlVersion("1.0");
			Element root = document.getDocumentElement();
			root.setAttribute("version", "0.1");

			for (Object o : instances) {
				Class<?> oclass = o.getClass();
				Element node = document.createElement("instance");
				for (Field f : oclass.getDeclaredFields()) {
					boolean oldaccessible = f.isAccessible();
					f.setAccessible(true);
					node.setAttribute(f.getName(), f.get(o).toString());
					f.setAccessible(oldaccessible);
				}
				node.setAttribute("class", oclass.getCanonicalName());
				root.appendChild(node);
			}

			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File(FILE_PATH));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.transform(source, result);
			System.out.println("Se ha guardado la lista de archivos en " + FILE_PATH);
		} catch (Exception e) {
			System.out.print("No se ha podido guardar la lista de archivos en " + FILE_PATH);
			e.printStackTrace();
		}
	}

}
