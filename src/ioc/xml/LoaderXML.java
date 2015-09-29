package ioc.xml;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import ioc.Container;
import ioc.IBean;

public class LoaderXML extends DefaultHandler{
	
	private IBean<?> currentClass;
	
	private HashMap<IBean<?>, Integer> currentIndex;
	
	private Pattern metadataPattern = Pattern.compile("^(.+)@(\\d+)$");

	private Container container;
	
	public LoaderXML(Container container) {
		this.container = container;
		this.currentIndex = new HashMap<>();
	}
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (qName == "class") {
			currentClass = container.get((attributes.getValue("name")));
			if (currentIndex.get(currentClass) == null) {
				currentIndex.put(currentClass, 0);
			}
		} else if (qName == "instance") {
			Object o = currentClass.getInstance(currentIndex.get(currentClass));
			for (int i = 0; i < attributes.getLength(); i++) {
				String name = attributes.getQName(i);
				String value = attributes.getValue(i);
				deserialize(o, name, value);
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName == "instance") {
			Integer i = currentIndex.get(currentClass);
			currentIndex.put(currentClass, ++i);
		}
	}

	protected void deserialize(Object o, String aname, String aval) {
		Class<?> c = container.getAttributeClass(o, aname);
		if (c == String.class)
			container.setAttribute(o, aname, aval);
		else if (c == int.class || c == Integer.class)
			container.setAttribute(o, aname, Integer.parseInt(aval));
		else if (c == float.class || c == Float.class)
			container.setAttribute(o, aname, Float.parseFloat(aval));
		else {
			Matcher m = metadataPattern.matcher(aval);
			if (!m.find())
				return;
			IBean<?> destreflector = container.get(m.group(1)); 
			Object destInstance = destreflector.getInstance(Integer.parseInt(m.group(2)));
			container.setAttribute(o, aname, destInstance);
		}
	}
}
