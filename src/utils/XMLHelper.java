/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Namng
 */
public class XMLHelper implements Serializable {

    public static Document parseDOMFromFile(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFilePath);
        return document;
    }

    public static void storeDOMToContext(ServletContext context, String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
        if (context == null || xmlFilePath == null) {
            return;
        }
        if (xmlFilePath.trim().length() == 0) {
            return;
        }

        Document doc = parseDOMFromFile(xmlFilePath);

        context.setAttribute("DOM_TREE", doc);

    }

    public static XPath getXPath() {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        return xPath;
    }

    public static String getTextContext(String xPathExp, Node student) throws XPathExpressionException {
        if (xPathExp == null || student == null) {
            return null;
        }

        //Create xpath object
        XPath xPath = XMLHelper.getXPath();

        //Evaluate
        String tmp = (String) xPath.evaluate(xPathExp, student, XPathConstants.STRING);
        return tmp;
    }

    public static void parseFileToSAX(String xmlFilePath, DefaultHandler handler)
            throws ParserConfigurationException, SAXException, IOException
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        parser.parse(xmlFilePath, handler);

    }
}
