
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class PersistentLoader {
	private class SAXLoader extends DefaultHandler {
		Gallery gallery;
		public SAXLoader(Gallery g) {
			gallery = g;
		}
		public void startElement(String ns, String lname, String sname, Attributes atts) {
			if (sname.equals("gallery")) {
			}
			else if (sname.equals("media")) {
				gallery.addResource(atts.getValue("path"));
			}
		}
	}
	public void load(Gallery g, String path) {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(new SAXLoader(g));
			reader.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
