package ioc;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.XMLReader;

import ioc.xml.LoaderXML;

public class ContainerXML extends Container{

	@Override
	public boolean loadFrom(String file) {
		if (new File(file).exists()){
			load(file);
			return true;
		} else {
			return false;
		}
	}
	
	private void load(String file){
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader reader = sp.getXMLReader();
			reader.setContentHandler(new LoaderXML(this));
			reader.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
