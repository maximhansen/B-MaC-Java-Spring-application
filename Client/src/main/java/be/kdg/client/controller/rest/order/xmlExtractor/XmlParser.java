package be.kdg.client.controller.rest.order.xmlExtractor;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

public class XmlParser {
    public static Document parseXml(byte[] fileBytes) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new ByteArrayInputStream(fileBytes));
    }

    public static NodeList getElementsByTagName(Document document, String tagName) {
        return document.getElementsByTagName(tagName);
    }
}
