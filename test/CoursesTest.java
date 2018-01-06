/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import dataaccess.*;
import java.util.List;
import org.junit.Test;
import transferobjects.*;

/**
 *
 * @author rasna
 */
public class CoursesTest {
    
    public CoursesTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCoursesRetrieved(){
        CourseDAO coursesDao = new CourseDAOImpl();
        List<Course> courses = coursesDao.getAllCourses();
        
        assert courses.size() > 0;
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
