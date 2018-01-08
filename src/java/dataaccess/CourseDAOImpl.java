package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transferobjects.Course;
import transferobjects.CourseRegistration;
import transferobjects.Student;

/**
 *
 * @author Shawn
 */
public class CourseDAOImpl implements CourseDAO{

    private static final String GET_ALL_COURSES = "SELECT course_num, name FROM Courses ORDER BY course_num";
    private static final String INSERT_COURSES = "INSERT INTO Courses (course_num, name) VALUES(?, ?)";
    private static final String DELETE_COURSES = "DELETE FROM Courses WHERE course_num = ?";
    private static final String UPDATE_COURSES = "UPDATE Courses SET name = ? WHERE course_num = ?";
    private static final String GET_BY_CODE_COURSES = "SELECT course_num, name FROM Courses WHERE name = ?";
    private static final String REGISTER_COURSE = "INSERT into registry (student_num, course_num, term, year) values (?, ?, ?, ?)";
    private static final String GET_COURSE_BY_NUMBER = "SELECT course_num, name FROM Courses WHERE course_num = ?";
    private static final String GET_COURSE_REGISTRATION_BY_STUDENT_NUMBER = "SELECT student_num, course_num, term, year FROM registry WHERE student_num = ?";
  
    
    @Override
    public List<Course> getAllCourses() {
        List<Course> courses = Collections.EMPTY_LIST;
        Course course;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            pstmt = con.prepareStatement( GET_ALL_COURSES);
            rs = pstmt.executeQuery();
            courses = new ArrayList<>(100);
            while( rs.next()){
                course = new Course();
                course.setCode( rs.getString("course_num"));
                course.setName( rs.getString("name"));
                courses.add(course);
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
        return courses;
    }

    @Override
    public void addCourse(Course course) {
        try( Connection con = new DataSource().createConnection();
                PreparedStatement pstmt = con.prepareStatement( INSERT_COURSES);){
            pstmt.setString(1, course.getCode());
            pstmt.setString(2, course.getName());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    @Override
    public void registerCourse(int sudentNumber, String courseNumber, String term, int year) {
        try( Connection con = new DataSource().createConnection();
            PreparedStatement pstmt = con.prepareStatement( REGISTER_COURSE);){
            pstmt.setInt(1, sudentNumber);
            pstmt.setString(2, courseNumber);
            pstmt.setString(3, term);
            pstmt.setInt(4, year);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CourseDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    @Override
    public Course getCourseByCourseNumber(String courseNumber){
        Course course = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            
            pstmt = con.prepareStatement(GET_COURSE_BY_NUMBER );
            pstmt.setString(1,courseNumber);
            rs = pstmt.executeQuery();
            
            course = new Course();
            rs.next();
            course.setCode( rs.getString("course_num"));
            course.setName( rs.getString("name"));
          
        } catch (SQLException ex) {
            course = null;
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
        return course;
    }
    
    @Override
    public List<CourseRegistration> getCourseRegistrationByStudentNumber(int studentNumber){
        List<CourseRegistration> courseRegistrations = new ArrayList<CourseRegistration>();
        
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            DataSource ds = new DataSource();
            con = ds.createConnection();
            
            pstmt = con.prepareStatement(GET_COURSE_REGISTRATION_BY_STUDENT_NUMBER );
            pstmt.setInt(1,studentNumber);
            rs = pstmt.executeQuery();
            while( rs.next()){
                CourseRegistration courseRegistration = new CourseRegistration();
                String term = rs.getString("term");
                int year = rs.getInt("year");
                String courseCode = rs.getString("course_num");

                Course course = getCourseByCourseNumber(courseCode);

                StudentDAO studentDao = new StudentDAOImpl();
                Student student = studentDao.getStudentByStudentNumber(studentNumber);

                courseRegistration.setCourse(course);
                courseRegistration.setStudent(student);
                courseRegistration.setTerm(term);
                courseRegistration.setYear(year);
                courseRegistrations.add(courseRegistration);
            }
      
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
        return courseRegistrations;
    }
}
