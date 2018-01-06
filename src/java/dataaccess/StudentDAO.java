/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import transferobjects.Student;

/**
 *
 * @author rasna
 */
public interface StudentDAO {
    List<Student> getAllStudents();
    void addStudent(Student student);
    Student getStudentByStudentNumber(Integer studentnumber);
    void updateStudent(Integer studentNumber, String fName);
    void deleteStudent(Integer studentNumber);
}
