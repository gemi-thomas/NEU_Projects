/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kenzan.WebProject.Data;

import com.kenzan.WebProject.Pojo.Employee;
import com.kenzan.WebProject.Utils.JSON;
import com.kenzan.WebProject.Utils.WebProjectException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Gemi
 */
public class XMLDataStore extends AbstractDataStorage{
    
    //private static final String DATA_FILE_PATH = "src/main/java/com/kenzan/WebProject/Data/data.xml";
    private static final String DATA_FILE_PATH = "data.xml";
    File datafile = new File(DATA_FILE_PATH);
    Document doc;
    
    /*
    This function retrieves all the employees stored in the xml
    */
    @Override
    public List<Employee> getAllEmployees() throws Exception {
        
        initializeXML();
        
        String expression = "dataset/employees/employee";
        NodeList nodeList = getNodeList(expression);
        List<Employee> employeeList = new ArrayList<Employee>();
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element)nodeList.item(i);
            int id = Integer.parseInt(element.getAttribute("id"));
            String employeeFirstName = element.getAttribute("firstName");
            String employeeMiddleInitial = element.getAttribute("middleInitial");
            String employeeLastName = element.getAttribute("lastName");
            String employeeDateOfBirth = element.getAttribute("dateOfBirth");
            String employeeDateOfEmployment = element.getAttribute("dateOfEmployment");
            String status = element.getAttribute("status");
            
            if(status.equalsIgnoreCase("inactive"))
                continue;

            Employee employee = new Employee(id, employeeFirstName, employeeMiddleInitial,
                    employeeLastName, employeeDateOfBirth, employeeDateOfEmployment, status);
            System.out.println("Employee ="+ employee);
            employeeList.add(employee);
        }
        return employeeList;
    }
    
    /*
    This function retrieves employee based on the id.
    Throws exception if the employee is not found or is inactive
    */
    public Employee getEmployee(String id) throws Exception {
        
        initializeXML();
        
        String expression = "dataset/employees/employee[@id=" + "'" + id + "'" + "]";
        NodeList nodeList = getNodeList(expression);
        Employee employee;
        if(nodeList.getLength() == 0) {
            throw new WebProjectException("Employee not found");
        } 
        Element element = (Element)nodeList.item(0);
        int employee_id = Integer.parseInt(element.getAttribute("id"));
        String employeeFirstName = element.getAttribute("firstName");
        String employeeMiddleInitial = element.getAttribute("middleInitial");
        String employeeLastName = element.getAttribute("lastName");
        String employeeDateOfBirth = element.getAttribute("dateOfBirth");
        String employeeDateOfEmployment = element.getAttribute("dateOfEmployment");
        String status = element.getAttribute("status");
        if(status.equalsIgnoreCase("inactive")) {
            throw new WebProjectException("Employee not found");
        }
        employee = new Employee(employee_id, employeeFirstName, employeeMiddleInitial,
                employeeLastName, employeeDateOfBirth, employeeDateOfEmployment, status);
        System.out.println("Employee ="+ employee);
        return employee;
    }
    
    /*
    This function updates employee based on the id.
    Throws exception if the employee is not found/is inactive/ date doesn't match the required pattern
    */
    public String updateEmployee(String id, String firstName, String middleInitial, 
            String lastName, String dateOfBirth,String dateOfEmployment) throws Exception {
        
        initializeXML();
        
        String expression = "dataset/employees/employee[@id=" + "'" + id + "'" + "]";
        NodeList nodeList = getNodeList(expression);
        if(nodeList.getLength() == 0) {
            throw new WebProjectException("Employee not found");
        } 
   
        Element element = (Element)nodeList.item(0);
        String status = element.getAttribute("status");
        if(status.equalsIgnoreCase("inactive")) {
            throw new WebProjectException("Employee not found");
        }

        if(isDate(dateOfBirth) && isDate(dateOfEmployment)) {
            element.setAttribute("dateOfBirth", dateOfBirth);
            element.setAttribute("dateOfEmployment", dateOfEmployment);
        } else
            throw new WebProjectException("Date can be empty (or) in 'mm/dd/yyyy' pattern");
        
        if(!firstName.isEmpty())
            element.setAttribute("firstName", firstName);
        if(!middleInitial.isEmpty())
            element.setAttribute("middleInitial", middleInitial);
        if(!lastName.isEmpty())
            element.setAttribute("lastName", lastName);
                
        updateXML();
        String message = "Employee details updated!";
        return JSON.toJSONString(message);
    }
    /*
    This function deletes employee based on the id.
    Throws exception if the employee is not found or is inactive
    */
    public String deleteEmployee(String id) throws Exception {
        
        initializeXML();

        String expression = "dataset/employees/employee[@id=" + "'" + id + "'" + "]";
        NodeList nodeList = getNodeList(expression);
        Employee employee;
        if(nodeList.getLength() == 0) {
            throw new WebProjectException("Employee not found");
        } 
        Element element = (Element)nodeList.item(0);
        String status = element.getAttribute("status");
        if(status.equalsIgnoreCase("inactive")) {
            throw new WebProjectException("Employee not found");
        }
        element.setAttribute("status", "INACTIVE");
        
        updateXML();
        String message = "Employee deleted!";
        return JSON.toJSONString(message);
    }
    
    /*
    This function creates employee 
    Throws exception if the employee if date input doesn't match the pattern
    */
    public String createEmployee(String firstName,String middleInitial, String lastName, 
                                String dateOfBirth, String dateOfEmployment) throws Exception {
        
        initializeXML();
        
        Element rootTag = doc.getDocumentElement();
        Element employeesTag =  (Element) rootTag.getElementsByTagName("employees").item(0);
        String id_auto_increment_string = employeesTag.getAttributes().getNamedItem("id_auto_increment").getNodeValue();
        int id_auto_increment_int = Integer.parseInt(id_auto_increment_string);
        Element employee = doc.createElement("employee");
        
        if(isDate(dateOfBirth) && isDate(dateOfEmployment)) {
            employee.setAttribute("dateOfBirth", dateOfBirth);
            employee.setAttribute("dateOfEmployment", dateOfEmployment);
        } else
            throw new WebProjectException("Date can be empty (or) in 'mm/dd/yyyy' pattern");
        
        employee.setAttribute("id", id_auto_increment_string);
        employee.setAttribute("firstName", firstName);
        employee.setAttribute("middleInitial", middleInitial);
        employee.setAttribute("lastName", lastName);
        employee.setAttribute("status", "ACTIVE");
        employeesTag.appendChild(employee);
        id_auto_increment_int++;
        employeesTag.setAttribute("id_auto_increment", String.valueOf(id_auto_increment_int)); 
        
        updateXML();
        String message = "New employee created!";
        return JSON.toJSONString(message);
    }
    
    /*
    This function reads the xml and make sure it is ready to be used
    */
    private void initializeXML() throws Exception {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            doc = db.parse(this.datafile);
        } catch(ParserConfigurationException e) {
            throw new Exception("ParserConfigurationException" + e.getMessage());
        } catch(SAXException e) {
            throw new Exception("SAXException" + e.getMessage() );
        } catch(IOException e) {
            throw new Exception("IOException " + e.getMessage());
        }
    }
    /*
    This function updates the xml and removes any white lines
    */
    private void updateXML() throws Exception {
        
        XPath xp = XPathFactory.newInstance().newXPath();
        NodeList nl = (NodeList) xp.evaluate("//text()[normalize-space(.)='']", doc, XPathConstants.NODESET);

        for (int i=0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            node.getParentNode().removeChild(node);
        }
        try {
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(DATA_FILE_PATH));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (TransformerConfigurationException ex) {
            throw new Exception("TransformerConfigurationException");
        }
    }
    /*
    This function returns the xml nodelist based on the input expression
    */
    private NodeList getNodeList(String expression) throws XPathExpressionException {
        XPath xPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        return nodeList;
    }
    /*
    This function checks the date pattern matching
    */
    private boolean isDate(String date) {
        if(date.isEmpty())
            return true;
        String regex = "(0[1-9]|1[012])[- \\/.](0[1-9]|[12][0-9]|3[01])[- \\/.](19|20)\\d\\d";
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
}
