/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.StudentDAO;
import dataaccess.StudentDAOImpl;
import java.util.List;
import transferobjects.Student;
import transferobjects.Tuition;

/**
 *
 * @author rasna
 */
public class StudentsLogic {
      
    private static final int STUDENT_FIRST_NAME_MAX_LENGTH = 45;
    private static final int STUDENT_LAST_NAME_MAX_LENGTH = 45;

    private StudentDAO studentDAO = null;

    public StudentsLogic() {
        studentDAO = new StudentDAOImpl();
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    /**
     * Handles logic to add a new student. This method inserts information into Student and Tuition tables. 
     * @param student
     * @param tuition
     * @throws ValidationException 
     */
    public void addStudent(Student student, double tuition) throws javax.xml.bind.ValidationException {
        
        
        cleanStudent(student);
        validateStudent(student);
        studentDAO.addStudent(student);
        
         Tuition tt = new Tuition(student.getStudentNum(), 0.0, tuition);
         TuitionLogic tuitionLogic = new TuitionLogic();
         tuitionLogic.addTuitionLogic(tt);
    }

    public Student findStudentById(int studentNum) throws javax.xml.bind.ValidationException {
        return studentDAO.getStudentByStudentNumber(studentNum);
    }
    
    public void updateStudentFirstName(int studentNumber, String firstName){
        studentDAO.updateStudent(studentNumber, firstName);
    }
    
    public void deleteStudentById(int studentNumber){
        studentDAO.deleteStudent(studentNumber);
    }
    
    
    
    private void cleanStudent(Student student) {
        // how to check not empty for integer
        if (student.getStudentNum() != 0) {
            student.setStudentNumber(student.getStudentNum());
        }
        if (student.getFName() != null) {
            student.setFName(student.getFName().trim());
        }
        if (student.getLName() != null) {
            student.setLName(student.getLName().trim());
        }
        if (student.getDateOfBirth() != null) {
            student.setDateOfBirth(student.getDateOfBirth());
        }
    }

    private void validateStudent(Student student) throws javax.xml.bind.ValidationException {
        validateString(student.getFName(), "Student FirstName", STUDENT_FIRST_NAME_MAX_LENGTH, false);
        validateString(student.getLName(), "Student LastName", STUDENT_LAST_NAME_MAX_LENGTH, false);
    }

    private void validateString(String value, String fieldName, int maxLength, boolean isNullAllowed) throws javax.xml.bind.ValidationException {
        if (value == null && isNullAllowed) {
            // null permitted, nothing to validate
        } else if (value == null && !isNullAllowed) {
            throw new javax.xml.bind.ValidationException(String.format("%s cannot be null", fieldName));
        } else if (value.isEmpty()) {
            throw new javax.xml.bind.ValidationException(String.format("%s cannot be empty or only whitespace", fieldName));
        } else if (value.length() > maxLength) {
            throw new javax.xml.bind.ValidationException(String.format("%s cannot exceed %d characters", fieldName, maxLength));
        }
    }
}
