/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import dataaccess.TuitionDAO;
import dataaccess.TuitionDAOImpl;
import java.util.List;
import transferobjects.Tuition;

/**
 *
 * @author rasna
 */
public class TuitionLogic {
    
    private static final int COURSE_CODE_MAX_LENGTH = 45;
    private static final int COURSE_NAME_MAX_LENGTH = 45;

    private TuitionDAO tuitionDAO = null;

    public TuitionLogic() {
        tuitionDAO = new TuitionDAOImpl();
    }

    public List<Tuition> getAllTuitions() {
        return tuitionDAO.getAllTuitions();
    }
    
     public void addTuitionLogic(Tuition tuition) throws javax.xml.bind.ValidationException {
        tuitionDAO.addTuition(tuition);
    }
     
     public void updateTuitionLogic(int studentNumber, double paid) throws javax.xml.bind.ValidationException {
        tuitionDAO.updateTuition(studentNumber,paid);
    }

    /**
     * 
     * @param tuition
     * @throws ValidationException 
     *  public void addCourse(Tuition tuition) throws ValidationException {
        cleanCourse(tuition);
        //validateCourse(tuition);
        tuitionDAO.addTuition(tuition);
    }
     */
   

    private void cleanCourse(Tuition tuition) {
        if (tuition.getStudentNum() != 0) {
            tuition.setStudentNum(tuition.getStudentNum());
        }
        if (tuition.getPaid() < 0) {
            tuition.setPaid(tuition.getPaid());
        }
        if (tuition.getRemainder() < 0) {
            tuition.setRemainder(tuition.getRemainder());
        }
    }

    private void validateCourse(Tuition tuition) throws ValidationException {
        //validateString(course.getCode(), "Course Code", COURSE_CODE_MAX_LENGTH, false);
        //validateString(course.getName(), "Course Name", COURSE_NAME_MAX_LENGTH, false);
    }

    private void validateString(String value, String fieldName, int maxLength, boolean isNullAllowed) throws ValidationException {
        if (value == null && isNullAllowed) {
            // null permitted, nothing to validate
        } else if (value == null && !isNullAllowed) {
            throw new ValidationException(String.format("%s cannot be null", fieldName));
        } else if (value.isEmpty()) {
            throw new ValidationException(String.format("%s cannot be empty or only whitespace", fieldName));
        } else if (value.length() > maxLength) {
            throw new ValidationException(String.format("%s cannot exceed %d characters", fieldName, maxLength));
        }
    }
    
}

