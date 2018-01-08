/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import transferobjects.Course;
import transferobjects.CourseRegistration;

/**
 *
 * @author Shawn
 */
public interface CourseDAO {
	List<Course> getAllCourses();
	void addCourse(Course author);
        void registerCourse(int sudentNumber, String courseNumber, String term, int year);
        List<CourseRegistration> getCourseRegistrationByStudentNumber(int studentNumber);
        Course getCourseByCourseNumber(String courseNumber);
}
