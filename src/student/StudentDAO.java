/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import utils.XMLHelper;

/**
 *
 * @author Namng
 */
public class StudentDAO implements Serializable {

    private List<StudentDTO> students;

    public StudentHandler checkSAXLogin(String username, String password, ServletContext context) throws ParserConfigurationException, SAXException, IOException {

        //1. Get xml file path
        String xml_file = context.getInitParameter("STUDENT_XML_FILE");
        String realPath = context.getRealPath("//");
        String xmlRealPath = realPath + "\\" + xml_file;

        //2. create Default Handler Obj
        StudentHandler student = new StudentHandler(username, password);

        //3. call parser
        XMLHelper.parseFileToSAX(xmlRealPath, student);

        return student;

    }

    public Node checkLogin(String username, String password, ServletContext context) throws XPathExpressionException {

        //1.Get DOM tree
        Document doc = (Document) context.getAttribute("DOM_TREE");

        if (doc == null) {
            //Get xml file path
            String xml_file = context.getInitParameter("STUDENT_XML_FILE");
            //Get XML file absolute path
            String realPath = context.getRealPath("//");
            String xmlRealPath = realPath + "\\" + xml_file;
            try {
                //Create DOM and store to context
                XMLHelper.storeDOMToContext(context, xmlRealPath);
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //2. Create xpath expression
        String xPathExp = "//student[@id='"
                + username
                + "' and normalize-space(password)='"
                + password
                + "' and normalize-space(status)='studying' or normalize-space(status)='dropout']";

        System.out.println("XPATH: " + xPathExp);
        //3. Create xpath Object
        XPath xPath = XMLHelper.getXPath();
        //4. Evaluate Xpath Expression on DOM tree
        Node student = (Node) xPath.evaluate(xPathExp, doc, XPathConstants.NODE);
        //5. process
        return student;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void SearchAddressStudent(String searchAddress, ServletContext context) throws XPathExpressionException {
        Document doc = (Document) context.getAttribute("DOM_TREE");

        String xpathExp = "//student[contains(address,'"
                + searchAddress
                + "')]";
        System.out.println("XPath: " + xpathExp);
        XPath xpath = XMLHelper.getXPath();
        NodeList studentList = (NodeList) xpath.evaluate(xpathExp, doc, XPathConstants.NODESET);

        String tmp;
        for (int i = 0; i < studentList.getLength(); i++) {
            Node student = studentList.item(i);
            xpathExp = "@id";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String id = tmp.trim();

            xpathExp = "@class";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String aClass = tmp.trim();

            xpathExp = "lastname";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String lastName = tmp.trim();

            xpathExp = "middlename";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String middleName = tmp.trim();

            xpathExp = "firstname";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String firstName = tmp.trim();

            xpathExp = "sex";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String sex = tmp.trim();

            xpathExp = "address";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String address = tmp.trim();

            xpathExp = "status";
            tmp = XMLHelper.getTextContext(xpathExp, student);
            String status = tmp.trim();

            StudentDTO dto = new StudentDTO(id, aClass, firstName, middleName, lastName, sex, status, address);

            if (this.students == null) {
                this.students = new ArrayList<>();
            }

            this.students.add(dto);
        }
    }
}
