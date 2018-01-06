/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rasna
 */

public class UITuitionView extends HttpServlet{
    
    protected void generateUpdateForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
         try (PrintWriter out = response.getWriter()) {
             ClassLoader classLoader = getClass().getClassLoader();
             File file = new File(classLoader.getResource("html/tuition_update_by_id_form.html").getFile());
            
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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      generateUpdateForm(request, response);
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

