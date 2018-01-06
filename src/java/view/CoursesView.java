/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import business.CoursesLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.Course;

/**
 *
 * @author Shariar Emami
 * @author Stanley Pieda
 */
public class CoursesView extends HttpServlet {

    private ViewCommon viewCommon = new ViewCommon();
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
        String pageHeader = "Courses";
   
        CoursesLogic logic = new CoursesLogic();
        List<Course> courses = logic.getAllCourses();
            
        String htmlBody = "";
        htmlBody += "<table>";
        htmlBody += "<tr>";
        htmlBody += "<td>Code</td>";
        htmlBody += "<td>Name</td>";
        htmlBody += "</tr>";
        for(Course course : courses){
            htmlBody += "<tr><td>" + course.getCode() + "</td><td>" + course.getName() + "</td></tr>";
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
        processRequest(request, response);
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
        processRequest(request, response);
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
