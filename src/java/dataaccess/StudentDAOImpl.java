/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transferobjects.Student;

/**
 *
 * @author rasna
 */
public class StudentDAOImpl implements StudentDAO{
    private static final String GET_ALL_STUDENTS = "SELECT student_num, first_name, last_name, date_birth, enrolled FROM Registrar.Students ORDER BY student_num;";
    private static final String INSERT_STUDENTS = "INSERT INTO STUDENTS (student_num,first_name,last_name, date_birth, enrolled) VALUES(?, ?, ?, ?, ?)";
    private static final String SELECT_STUDENTS_BY_ID = "SELECT student_num, first_name, last_name FROM Registrar.Students WHERE student_num=?";
    private static final String UPDATE_STUDENT = "UPDATE Registrar.Students SET first_name = ? WHERE student_num = ?";
    private static final String DELETE_STUDENT = "DELETE FROM Registrar.Students WHERE student_num = ?";
    

    //private static final String DELETE_COURSES = "DELETE FROM Courses WHERE course_num = ?";
    //private static final String UPDATE_COURSES = "UPDATE Courses SET name = ? WHERE course_num = ?";
    //private static final String GET_BY_CODE_COURSES = "SELECT course_num, name FROM Courses WHERE name = ?";
    
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
   
    
   @Override
    public List<Student> getAllStudents() {
        List<Student> students = Collections.EMPTY_LIST;
        Student student;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
         
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-mm-dd");
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            pstmt = con.prepareStatement(GET_ALL_STUDENTS);
            rs = pstmt.executeQuery();
            students = new ArrayList<>(100);
            while( rs.next()){
                student = new Student();
                student.setStudentNumber( rs.getInt("student_num"));
                student.setFName( rs.getString("first_name"));
                student.setLName(rs.getString("last_name"));
                student.setDateOfBirth(rs.getDate("date_birth"));
                student.setEnrolled(rs.getDate("enrolled"));
                students.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                con.close();
                rs.close();
                pstmt.close();
                rs.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return students;
    }
/**
 * 
 * @param course 
 */

  @Override
    public void addStudent(Student student) {
        try( Connection con = new DataSource().createConnection();
                PreparedStatement pstmt = con.prepareStatement(INSERT_STUDENTS);){
                pstmt.setInt(1, student.getStudentNum());
                pstmt.setString(2, student.getFName());
                pstmt.setString(3, student.getLName());
                pstmt.setDate(4, student.getDateOfBirth());
                pstmt.setDate(5, student.getEnrolled());
                int i = pstmt.executeUpdate();
                
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    @Override
    public Student getStudentByStudentNumber(Integer studentnumber) {
     
        Student student = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            
            pstmt = con.prepareStatement(SELECT_STUDENTS_BY_ID );
            pstmt.setInt(1,studentnumber);
            rs = pstmt.executeQuery();
            
            student = new Student();
            rs.next();
            student.setStudentNumber( rs.getInt("student_num"));
            student.setFName( rs.getString("first_name"));
            student.setLName(rs.getString("last_name"));
                
          
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return student;
    }

    @Override
    public void updateStudent(Integer studentnumber, String firstName) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();    
            pstmt = con.prepareStatement(UPDATE_STUDENT);
            pstmt.setString(1,firstName);
            pstmt.setInt(2,studentnumber);
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }   
    }

    @Override
    public void deleteStudent(Integer studentNumber) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();    
            pstmt = con.prepareStatement(DELETE_STUDENT);
            pstmt.setInt(1,studentNumber);
            
            pstmt.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
 
}
