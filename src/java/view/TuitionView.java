/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import business.TuitionLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import transferobjects.Tuition;

/**
 *
 * @author rasna
 */
public class TuitionView extends HttpServlet{
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Tuitions</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Tuitions View at " + request.getContextPath() + "</h1>");
            TuitionLogic logic = new TuitionLogic();
            List<Tuition> tuitions = logic.getAllTuitions();
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<td>Student Number</td>");
            out.println("<td>Paid</td>");
            out.println("<td>Remainder</td>");
            out.println("</tr>");
            for(Tuition tuition : tuitions){
                out.printf("<tr><td>%s</td><td>%s</td><td>%s</td></tr>", tuition.getStudentNum(), tuition.getPaid(),tuition.getRemainder());
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void updateTuitionById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
         response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             int studentNumber = Integer.parseInt(request.getParameter("studentNumber"));
             double paid = Double.parseDouble(request.getParameter("paid"));
             TuitionLogic logic = new TuitionLogic();
             logic.updateTuitionLogic(studentNumber,paid);
            
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
        //processRequest(request, response);
        String pathInfo = request.getPathInfo();
        
        
        if(pathInfo.equals("/update") ){
                updateTuitionById(request, response);
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
    }

    
}

