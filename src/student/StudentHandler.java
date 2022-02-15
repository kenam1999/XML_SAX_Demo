/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Admin
 */
public class StudentHandler extends DefaultHandler {

    private String username;
    private String password;
    private String currentTag;
    private String fullname;

    private boolean matchUsername;
    private boolean matchPassword;
    private boolean found;

    public StudentHandler() {
        this.currentTag = "";
        this.matchUsername = false;
        this.fullname = "";
        this.matchPassword = false;
        this.found = false;
    }

    public StudentHandler(String username, String password) {
        this.username = username;
        this.password = password;
        this.currentTag = "";
        this.matchUsername = false;
        this.fullname = "";
        this.matchPassword = false;
        this.found = false;
    }

    public String getFullname() {
        return fullname;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atrbts) throws SAXException {
//        System.out.println("start Element");
//        System.out.println("uri: " + uri);
//        System.out.println("localName: " + localName);
//        System.out.println("qName: " + qName);
//        System.out.println("attribute: " + atrbts);
        if (!found) {
            this.currentTag = qName;
            if (qName.equals("student")) {
                String id = atrbts.getValue("id");
                if (id != null) {
                    if (id.equals(this.username)) {
                        this.matchUsername = true;
                    }
                }

            }//cursor point
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
//        System.out.println("end Element");
//        System.out.println("uri: " + uri);
//        System.out.println("localName: " + localName);
//        System.out.println("qName: " + qName);
        this.currentTag = "";
    }

    @Override
    public void characters(char[] ch, int start, int lenght) throws SAXException {
//        System.out.println("Character");
//        String tmp = new String(ch, start, lenght);
        if (!found) {
            if (this.matchUsername) {
                String tmp = new String(ch, start, lenght);

                if (this.currentTag.equals("lastname")) {
                    this.fullname = tmp.trim();
                } else if (this.currentTag.equals("middlename")) {
                    this.fullname = this.fullname + " " + tmp.trim();
                } else if (this.currentTag.equals("firstname")) {
                    this.fullname = this.fullname + " " + tmp.trim();
                } else if (this.currentTag.equals("password")) {
                    if (tmp.trim().equals(password)) {
                        this.matchPassword = true;
                    }//password is matched
                }//cursor pointed password
            }//end if username already matched

            if (this.matchPassword) {
                if (this.currentTag.equals("status")) {
                    String tmp = new String(ch, start, lenght);
                    this.matchPassword = false;
                    if (!"dropout".equals(tmp.trim())) {
                        this.found = true;
                    }//end status is diffrent dropout
                }
            }//end if passeord matched
        }

    }

}
