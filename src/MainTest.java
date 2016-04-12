import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MainTest {

	public static void main(String[] args) {
		// URLTest();
		paresXML();
	}

	private static void paresXML() {
		SAXReader reader = new SAXReader();
		FileInputStream input = null;
		InputStreamReader inputRead = null;
		Document document = null;
		Map<String, Element> elementMap = new HashMap<String, Element>();
		try {
			input = new FileInputStream("D:/workspace/TestDemo/src/AndroidManifest.xml");
			inputRead = new InputStreamReader(input, "UTF-8");
			document = reader.read(inputRead);

			Element rootElement = document.getRootElement();
			System.out.println("rootElement is : " + rootElement);
			rootElement = rootElement.element("application");

			List<Element> elements = rootElement.elements("activity");
			for (Element child : elements) {
				System.out.println("child Element : "+child.getQualifiedName());
				
				List<Attribute> attrs = child.attributes();
				for (Attribute attr : attrs) {
					System.out.println("attr is : " + attr.getQualifiedName() + " ,attr value is : " + attr.getValue());
					elementMap.put(attr.getValue(), child);
				}
				Element oldElement = elementMap.get(child);
				List<Element> subElements = oldElement.elements();
				for(Element element : subElements){
					System.out.println("------ : "+element.getQualifiedName());
				}
			}

			for (String name : elementMap.keySet()) {
				Element oldElement = elementMap.get(name);
				System.out.println("elementMap.get(name): " + oldElement);
				List<Attribute> attrs = oldElement.attributes();
				for (Attribute attr : attrs) {
					System.out.println("attr.getQualifiedName() : " + attr.getQualifiedName() + ", attr.getValue() : "
							+ attr.getValue());
				}
				List<Element> subElements = oldElement.elements();
				for (Element element : subElements) {
					System.out.println("element : ");
				}
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected static void URLTest() {
		URL url;
		try {
			url = new URL("https://www.baidu.com");
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.connect();
			OutputStream out = new FileOutputStream(new File("C:/Users/zengy/Desktop/resp.txt"));
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream input = conn.getInputStream();

				int len = 0;
				byte[] buffer = new byte[1024];
				while ((len = input.read(buffer)) != 0) {
					out.write(buffer, 0, len);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
