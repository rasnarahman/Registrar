/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferobjects;

import java.sql.Date;

/**
 *
 * @author rasna
 */
public class Student {
    private int studentNumner;
    private String fName;
    private String lName;
    private Date dateOfBirth;
    private Date enrolled;
    
    
    
    public Student(){
        
    }
    
    public Student(int studentNumner,String fName, String lName,Date dateOfBirth, Date enrolled){
        
        setStudentNumber(studentNumner);
        setFName(fName);
        setLName(lName);
        setDateOfBirth( dateOfBirth);
        setEnrolled( enrolled);
        
    }
    
    public int getStudentNum(){
        return studentNumner;
    }
    
    public void setStudentNumber(int studentNumner){
        this.studentNumner = studentNumner;
    }
    
    public String getFName(){
        return fName;
    }
    
    public void setFName(String fName){
        this.fName = fName;
    }
    
    public String getLName(){
        return lName;
    }
    
    public void setLName(String lName){
        this.lName = lName;
    }
    
    public Date getDateOfBirth(){
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    public Date getEnrolled(){
        return enrolled;
    }
    
    public void setEnrolled(Date enrolled){
        this.enrolled = enrolled;
    }
}
