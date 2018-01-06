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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author rasna
 */
public class ViewCommon {
        public List<String> getFileContentsFromSamePackageProjectFile(String relativeFilePath) {
        List<String> fileContents = new ArrayList<String>();
        
        URL url = getClass().getResource(relativeFilePath);
        File file = new File(url.getPath());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                fileContents.add(line);
            }
            scanner.close();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
        return fileContents;
    }
        
        
    public void populateWebPageFromHtml(List<String> htmlLines, PrintWriter printWriter) {
        if(printWriter != null) {
            if(htmlLines.size() > 0) {
                for(String htmlLine: htmlLines){
                    printWriter.println(htmlLine);
                }              
            }    
            else {
                printWriter.print("ERROR: Could not retrieve contents from source html file");
            }
        }
    }
}
