/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listener;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import utils.XMLHelper;

/**
 * Web application lifecycle listener.
 *
 * @author Namng
 */
public class ContextServletListener implements ServletContextListener {

    @Override
    //Khoi tao khi ung dung deploy
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        //Get xml file path
        String xml_file = context.getInitParameter("STUDENT_XML_FILE");
        //Get XML file absolute path
        String realPath = context.getRealPath("//");
        String xmlRealPath = realPath + "\\" + xml_file;
        //Create DOM and store to context
        try {
            XMLHelper.storeDOMToContext(context, xmlRealPath);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(ContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ContextServletListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("DOM_TREE");
    }
}
