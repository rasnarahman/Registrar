/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferobjects;

/**
 *
 * @author rasna
 */
public class CourseRegistration {
    private Student student; 
    private Course course;
    private String term;
    private int year;
    
    
    public CourseRegistration(){}
    
    public CourseRegistration( Student student, Course course, String term, int year){
        setStudent(student);
        setCourse(course);
        setTerm(term);
        setYear(year);
    }
    
    public Student getStudent(){
        return student;
    }
    
    public Course getCourse(){
        return course;
    }
    
    public String getTerm(){
        String termFull = "";
        
        if(term.equals("W")) 
            termFull = "Winter";
        else if(term.equals("F")) 
            termFull = "Fall";
        else if(term.equals("S")) 
            termFull = "Summar";
        
        return termFull;
    }
    
    public int getYear(){
        return year;
    }
    
    public void setStudent( Student student){
        this.student = student;
    }
    
    public void setCourse( Course course){
        this.course = course;
    }
    
    public void setTerm(String term){
        this.term = term;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
}
