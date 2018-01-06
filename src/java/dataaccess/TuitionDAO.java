/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.util.List;
import transferobjects.Tuition;

/**
 *
 * @author rasna
 */
public interface TuitionDAO {
    List<Tuition> getAllTuitions();
    void addTuition(Tuition tuition);
    //Tuition getStudentByStudentNumber(Integer studentnumber);
    void updateTuition(Integer studentnumber, double paid);
    boolean deleteTuition(Integer studentNumber);
  }
