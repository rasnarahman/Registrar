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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transferobjects.Tuition;

/**
 *
 * @author rasna
 */
public class TuitionDAOImpl implements TuitionDAO{
    private static final String GET_ALL_TUITIONS = "SELECT student_num, paid, remainder FROM Registrar.Tuition ORDER BY student_num;";
    private static final String INSERT_TUITIONS = "INSERT INTO Registrar.Tuition (student_num, paid, remainder) VALUES(?, ?, ?)";
    private static final String DELETE_COURSES = "DELETE FROM Courses WHERE course_num = ?";
    private static final String UPDATE_PAID = "UPDATE Registrar.Tuition SET paid = ? WHERE student_num = ?;";
    private static final String GET_BY_CODE_COURSES = "SELECT course_num, name FROM Courses WHERE name = ?";

    @Override
    public List<Tuition> getAllTuitions() {
        List<Tuition> tuitions = Collections.EMPTY_LIST;
        Tuition tuition;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            pstmt = con.prepareStatement( GET_ALL_TUITIONS);
            rs = pstmt.executeQuery();
            tuitions = new ArrayList<>(100);
            while( rs.next()){
                tuition = new Tuition();
                tuition.setStudentNum(rs.getInt("student_num"));
                tuition.setPaid(rs.getDouble("paid"));
                tuition.setRemainder(rs.getDouble("remainder"));
                tuitions.add(tuition);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
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
        return tuitions;
    }

    /**
     * 
     * @param tuition 
     */
    @Override
    public void addTuition(Tuition tuition) {
        try( Connection con = new DataSource().createConnection();
                PreparedStatement pstmt = con.prepareStatement( INSERT_TUITIONS);){
            pstmt.setInt(1, tuition.getStudentNum());
            pstmt.setDouble(2, tuition.getPaid());
            pstmt.setDouble(3, tuition.getRemainder());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
/**
 * @param studentnumber
 * @param firstName 
 */
    @Override
    public void updateTuition(Integer studentnumber, double paid) {
       Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();    
            pstmt = con.prepareStatement(UPDATE_PAID);
            pstmt.setDouble(1,paid);
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


    
 
    /**
     * 
     * @param studentNumber 
     * @Override
    public void deleteTUition(Integer studentNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     */
    

    }
