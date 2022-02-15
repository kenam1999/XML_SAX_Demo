package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import student.StudentDAO;
import student.StudentHandler;
import utils.XMLHelper;

/**
 * @author Namng
 */
public class LoginController extends HttpServlet {

    String INVALID_PAGE = "invalid.html";
    String SEARCH_PAGE = "search.jsp";
    String LOGIN_PAGE = "index.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, XPathExpressionException, ParserConfigurationException, SAXException {
        response.setContentType("text/html;charset=UTF-8");

        String username = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        String url = INVALID_PAGE;

        try {
            //1. Call DAO
            ServletContext context = this.getServletContext();
            StudentDAO dao = new StudentDAO();

            StudentHandler student;
            student = dao.checkSAXLogin(username, password, context);

            if (student.isFound()) {
                url = SEARCH_PAGE;
                HttpSession session = request.getSession();

                session.setAttribute("FULLNAME", student.getFullname());
                session.setAttribute("USERNAME", username);
            } else {
                url = LOGIN_PAGE;
            }


//            Node student = dao.checkLogin(username, password, context);
//
//            if (student == null) {
//                url = LOGIN_PAGE;
//            } else {
//                //2. process
//                url = SEARCH_PAGE;
//
//                String fullName = "";
//
//                //Create xpath exp
//                String xPathExp = "//lastname";
//                XPath xPath = XMLHelper.getXPath();
//                String tmp = XMLHelper.getTextContext(xPathExp, student);
//                fullName = tmp.trim();
//
//                xPathExp = "//middlename";
//                tmp = XMLHelper.getTextContext(xPathExp, student);
//                fullName += " " + tmp.trim();
//
//                xPathExp = "//firstname";
//                tmp = XMLHelper.getTextContext(xPathExp, student);
//                fullName += " " + tmp.trim();
//
//                System.out.println("Full Name: " + fullName);
//                HttpSession session = request.getSession();
//                session.setAttribute("FULLNAME", fullName);
//                session.setAttribute("USERNAME", username);
//            }
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (XPathExpressionException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            processRequest(request, response);
        } catch (XPathExpressionException | ParserConfigurationException | SAXException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
