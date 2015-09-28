package instantiator;

import java.util.ListIterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.Source;
import javax.xml.transform.Result;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;


public class PersistentSaver {
	public void save(ListIterator<String> list, String path) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			Document document = implementation.createDocument(null, "gallery", null);
			document.setXmlVersion("1.0");
			Element root = document.getDocumentElement();

			while (list.hasNext()) {
				Element node = document.createElement("media");
				Text whitespace = document.createTextNode("\n\t");
				node.setAttribute("path", list.next());
				root.appendChild(whitespace);
				root.appendChild(node);
			}
			root.appendChild(document.createTextNode("\n"));

			Source source = new DOMSource(document);
			Result result = new StreamResult(new java.io.File(path));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
			System.out.print("Se ha guardado la lista de archivos en " );
			System.out.println (path);
		} catch (Exception e) {
			System.out.print("No se ha podido guardar la lista de archivos en ");
			System.out.println(path);
			e.printStackTrace();
		}
	}
}
