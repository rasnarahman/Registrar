/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rasna
 */
public class UIStudentView extends HttpServlet{
    
    private ViewCommon viewCommon = new ViewCommon();
    
    protected void generateNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        List<String> htmlFileLines = viewCommon.getFileContentsFromSamePackageProjectFile("ResourceFiles/AddStudent.html");   
        try (PrintWriter out = response.getWriter()) {
            viewCommon.populateWebPageFromHtml(htmlFileLines, out);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }
    
   
    
    
    protected void generateSearchForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             ClassLoader classLoader = getClass().getClassLoader();
             File file = new File(classLoader.getResource("html/student_search_by_id_form.html").getFile());
            
            try (Scanner scanner = new Scanner(file)) {

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();		
                        out.println(line + "\n");
		}
		scanner.close();
	        } catch (IOException e) {
		e.printStackTrace();
	       }
             
         }catch(Exception e){
             System.out.println("something wrong");
             
         }
    }
    
    protected void generateUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             ClassLoader classLoader = getClass().getClassLoader();
             File file = new File(classLoader.getResource("html/student_update_by_id_form.html").getFile());
            
            try (Scanner scanner = new Scanner(file)) {

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();		
                        out.println(line + "\n");
		}
		scanner.close();
	        } catch (IOException e) {
		e.printStackTrace();
	       }
             
         }catch(Exception e){
             System.out.println("something wrong");
             
         }
    }
    
    protected void generateDeleteForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             ClassLoader classLoader = getClass().getClassLoader();
             File file = new File(classLoader.getResource("html/student_delete_by_id_form.html").getFile());
            
            try (Scanner scanner = new Scanner(file)) {

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();		
                        out.println(line + "\n");
		}
		scanner.close();
	        } catch (IOException e) {
		e.printStackTrace();
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
       String pathInfo = request.getPathInfo();
        if(pathInfo.equals("/new_form") ){
                generateNewForm(request, response);
        }
        if(pathInfo.equals("/serach_form") ){
                generateSearchForm(request, response);
        }
        if(pathInfo.equals("/update_form") ){
                generateUpdateForm(request, response);
        }
        if(pathInfo.equals("/delete_form") ){
                generateDeleteForm(request, response);
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

