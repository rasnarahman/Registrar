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
public class Tuition {
    private int student_num;
    private double paid;
    private double remainder;
    
    public Tuition(){}
    
    public Tuition( int student_num, double paid, double remainder){
        setStudentNum( student_num);
        setPaid( paid);
        setRemainder(remainder);
    }
    
    public int getStudentNum(){
        return student_num;
    }
    
    public double getPaid(){
        return paid;
    }
    public double getRemainder(){
        return remainder;
    }
    
    public void setStudentNum( int student_num){
        this.student_num = student_num;
    }
    
    public void setPaid( double paid){
        this.paid = paid;
    }
    
    public void setRemainder( double remainder){
        this.remainder = remainder;
    }
}
