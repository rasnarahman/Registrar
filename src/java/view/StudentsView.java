/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author rasna
 */
import business.CoursesLogic;
import business.StudentsLogic;
import dataaccess.CourseDAO;
import dataaccess.CourseDAOImpl;
//import business.TuitionLogic;
import dataaccess.StudentDAOImpl;
import dataaccess.TuitionDAO;
import dataaccess.TuitionDAOImpl;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.Course;
import transferobjects.CourseRegistration;
import transferobjects.Student;
import transferobjects.Tuition;
//mport transferobjects.Tuition;

/**
 *
 * @author Rasna
 */
public class StudentsView extends HttpServlet {

    private ViewCommon viewCommon = new ViewCommon();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private String getStudentHtmlBody(List<Student> students){
        String htmlBody = "";
        htmlBody += "<table>";
        htmlBody += "<tr>";
        htmlBody += "<td>Student Number</td>";
        htmlBody += "<td>First Name</td>";
        htmlBody += "<td>Last Name</td>";
        htmlBody += "<td>DOB</td>";
        htmlBody += "<td>Enrolment Date</td>";
        htmlBody += "</tr>";
        for(Student student : students){
            htmlBody += 
                    "<tr>" +
                    "<td>" + student.getStudentNum() + "</td>" +
                    "<td>" + student.getFName() + "</td>" +
                    "<td>" + student.getLName() + "</td>" +
                    "<td>" + student.getDateOfBirth() + "</td>" +
                    "<td>" + student.getEnrolled() + "</td>" +
                    "</tr>";
        }
        htmlBody += "</table>";
        return htmlBody;
    }
    
    private String getTuitionHtmlBody(Tuition tuition){
        String htmlBody = "";
        htmlBody += "<table>";
        htmlBody += "<tr>";
        htmlBody += "<td>Tuition Paid</td>";
        htmlBody += "<td>Tuition Remainder</td>";
        htmlBody += "</tr>";
        htmlBody += "<tr>";
        htmlBody += "<td>" + tuition.getPaid() + "</td>";
        htmlBody += "<td>" + tuition.getRemainder() + "</td>" ;
        htmlBody += "</tr>";
        htmlBody += "</table>";
        return htmlBody;
    }
    
    
    private String getCourseRegistrationHtmlBody(List<CourseRegistration> courseRegistrations){
        String htmlBody = "";
        htmlBody += "<table>";
        htmlBody += "<tr>";
        htmlBody += "<td>Course Number</td>";
        htmlBody += "<td>Course Name</td>";
        htmlBody += "<td>Term</td>";
        htmlBody += "<td>Year</td>";
        htmlBody += "</tr>";
        
        for(CourseRegistration courseRegistration: courseRegistrations) {
            htmlBody += "<tr>";
            htmlBody += "<td>" + courseRegistration.getCourse().getCode() + "</td>";
            htmlBody += "<td>" + courseRegistration.getCourse().getName()+ "</td>" ;
            htmlBody += "<td>" + courseRegistration.getTerm()+ "</td>" ;
            htmlBody += "<td>" + courseRegistration.getYear()+ "</td>" ;
            htmlBody += "</tr>";          
        }
        
        htmlBody += "</table>";

        return htmlBody;
    }
    
    private void displayStudents(List<Student> students, HttpServletRequest request, HttpServletResponse response){
        String pageHeader = "Students";
           
        String htmlBody = getStudentHtmlBody(students);
        
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            List<String> htmlLines = viewCommon.getFileContentsFromSamePackageProjectFile("ResourceFiles/CommonTemplate.html");

            for(String htmlLine : htmlLines) {              
                if(htmlLine.contains("${page_header}")) {
                    String newLine = htmlLine.replace("${page_header}", pageHeader);
                    htmlLine = newLine;
                }
                else if(htmlLine.contains("${page_content}")) {
                    String newLine = htmlLine.replace("${page_content}", htmlBody);
                    htmlLine = newLine;
                }
                out.println(htmlLine);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    protected void getAllStudentsView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentsLogic logic = new StudentsLogic();
        List<Student> students = logic.getAllStudents();
        displayStudents(students, request, response);
    }
    
    
    
    protected void createNewStudent(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
            String firstName = request.getParameter("fName");
            String lastName = request.getParameter("lName");
            String DOB = request.getParameter("dob");
            double tuition = Double.parseDouble(request.getParameter("tuition"));
                   
            java.util.Date date = sdf.parse(DOB);
            java.sql.Date sqlDOBDate = new Date(date.getTime());
            
            Date enrolled = new Date(new java.util.Date().getTime());   
            Student student = new Student(studentNumber,firstName,lastName,sqlDOBDate,enrolled);
            
            StudentsLogic logic = new StudentsLogic();
            logic.addStudent(student, tuition);
            
            List<Student> students = logic.getAllStudents();
            displayStudents(students, request, response);
           
        }catch(Exception e){
            System.out.println("exception occured");
        }
    } 
    
    protected void processStudentByIdRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
             StudentsLogic logic = new StudentsLogic();
             Student student = logic.findStudentById(studentNumber);
             
             TuitionDAO tuitionDao = new TuitionDAOImpl();
             Tuition tuition = tuitionDao.getTuitionByStudentNumber(studentNumber);
          
             
            List<Student> students = new ArrayList<Student>();
            students.add(student);
            String studentHtmlBody = getStudentHtmlBody(students);
            String tuitionHtmlBody = getTuitionHtmlBody(tuition);
            
            CourseDAO courseDao = new CourseDAOImpl();
            List<CourseRegistration> courseRegistrations = courseDao.getCourseRegistrationByStudentNumber(studentNumber);
            String courseRegistrationHtml = getCourseRegistrationHtmlBody(courseRegistrations);
            
            String finalHtmlBody =
                    studentHtmlBody + 
                    "<br/>" + 
                    "<h3>Tuition</h3>" + 
                    tuitionHtmlBody +
                    "<br/>" + 
                    "<h3>Courses</h3>" + 
                    courseRegistrationHtml;
            
            List<String> htmlLines = viewCommon.getFileContentsFromSamePackageProjectFile("ResourceFiles/CommonTemplate.html");

            for(String htmlLine : htmlLines) {              
                if(htmlLine.contains("${page_header}")) {
                    String newLine = htmlLine.replace("${page_header}", "Student");
                    htmlLine = newLine;
                }
                else if(htmlLine.contains("${page_content}")) {
                    String newLine = htmlLine.replace("${page_content}", finalHtmlBody);
                    htmlLine = newLine;
                }
                out.println(htmlLine);
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    protected void updateStudentById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
            int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
            String firstName = request.getParameter("fName");
            String lastName = request.getParameter("lName");
            StudentsLogic logic = new StudentsLogic();
            logic.updateStudentFirstName(studentNumber,firstName, lastName);

            Student student = logic.findStudentById(studentNumber);
             
            List<Student> students = new ArrayList<Student>();
            students.add(student);
            displayStudents(students, request, response);
        }catch(Exception e){
             System.out.println(e.toString());
             
         }
    }
    
    
    protected void deleteStudentById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
             StudentsLogic logic = new StudentsLogic();
             boolean resultSuccessful = logic.deleteStudentById(studentNumber);
             
            List<String> htmlLines = viewCommon.getFileContentsFromSamePackageProjectFile("ResourceFiles/CommonTemplate.html");

            for(String htmlLine : htmlLines) {              
                if(htmlLine.contains("${page_header}")) {
                    String newLine = htmlLine.replace("${page_header}", "Delete Result");
                    htmlLine = newLine;
                }
                else if(htmlLine.contains("${page_content}")) {
                    String newLine = "";
                    if(resultSuccessful) {
                        newLine = htmlLine.replace("${page_content}", "Student has been deleted!");
                    }
                    else {
                        newLine = htmlLine.replace("${page_content}", "Student delete failed!");
                    }
                    htmlLine = newLine;
                }
                out.println(htmlLine);
            }
        }catch(Exception e){
             System.out.println("something wrong");
             
         }
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getAllStudentsView(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //createNewStudent(request, response);
        String pathInfo = request.getPathInfo();
        
        if(pathInfo == null || pathInfo == "//"){
                 createNewStudent(request, response);
        } 
        if(pathInfo.equals("/search") ){
                processStudentByIdRequest(request, response);
        }
        if(pathInfo.equals("/update") ){
                updateStudentById(request, response);
        }
        if(pathInfo.equals("/delete") ){
                deleteStudentById(request, response);
        }
        
        
        else{
             response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()){
             out.print("<h3> invalid url ... </h3");
         }
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

