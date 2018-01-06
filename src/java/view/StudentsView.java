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
//import business.TuitionLogic;
import dataaccess.StudentDAOImpl;
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
import transferobjects.Student;
//mport transferobjects.Tuition;

/**
 *
 * @author fatema
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
    
    private void displayStudents(List<Student> students, HttpServletRequest request, HttpServletResponse response){
        String pageHeader = "Students";
           
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
             
            List<Student> students = new ArrayList<Student>();
            students.add(student);
            displayStudents(students, request, response);
        }catch(Exception e){
             System.out.println("something wrong");
             
         }
    }
    
    
    
    protected void updateStudentById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
             String firstName = request.getParameter("fName");
             StudentsLogic logic = new StudentsLogic();
             logic.updateStudentFirstName(studentNumber,firstName);
            
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Student information serach by id</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Students View at " + request.getContextPath() + "</h1>");
            out.print("<h3> student information is updated </h3>");
            out.println("</body>");
            out.println("</html>");
        }catch(Exception e){
             System.out.println("something wrong");
             
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

