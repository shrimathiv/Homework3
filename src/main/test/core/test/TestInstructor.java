package core.test;

import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestInstructor {

    private IInstructor instructor;
    private IAdmin admin;
    private IStudent student;

    @Before //set up before any of the unit tests 
    public void setup() {
        this.instructor = new Instructor();  
        this.admin = new Admin();
        this.student = new Student();
    }
    
    /* --------------TESTS FOR ADDHOMEWORK -------------------*/

    @Test
    public void testAddHW() { //check normal functionality, make a hw for a class with a valid instructor and year 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1"); //should work
        assertTrue(this.instructor.homeworkExists("Test", 2017, "HW1")); //evaluates to true  
    }
    
    @Test
    public void testAddHW1() { //add hw for a classname that appears in 2 different years
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2018, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1"); //should not work
        assertFalse(this.instructor.homeworkExists("Test", 2018, "HW1")); //evaluates to true  
    }
    
    @Test
    public void testAddHW2() { //add hw for a classname that doesnt exist 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test1", 2017, "HW1"); //should not work
        assertFalse(this.instructor.homeworkExists("Test1", 2017, "HW1")); //evaluates to true  
    }
    
    @Test
    public void testAddHW3() { //add hw for a classname with an instructor that doesn't teach this class 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor1", "Test", 2017, "HW1"); //should not work
        assertFalse(this.instructor.homeworkExists("Test", 2017, "HW1")); //evaluates to false, implementation is wrong! 
    }
    
    /* --------------TESTS FOR ASSIGNGRADE -------------------*/
    
    @Test
    public void testAssignGrade() { //normal functionality, add class, add HW, add student, submit hw, assign grade  
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("student", "Test", 2017); 
        this.student.submitHomework("student", "HW1", "answer", "Test", 2017);
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "student", 80);
        assertTrue(this.instructor.getGrade("Test", 2017,"HW1", "student") == 80); //evaluates to true
    }
    
    @Test
    public void testAssignGrade2() { //assign grade by instructor who doesn't teach this class 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("student", "Test", 2017); 
        this.student.submitHomework("student", "HW1", "answer", "Test", 2017);
        this.instructor.assignGrade("Instructor1", "Test", 2017, "HW1", "student", 80); //should not work
        assertFalse(this.instructor.getGrade("Test", 2017,"HW1", "student") == 80); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testAssignGrade4() { //assign grade for student who never submitted hw
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("student", "Test", 2017); 
        this.instructor.assignGrade("Instructor", "Test", 2017, "HW1", "student", 80); //should not work
        assertFalse(this.instructor.getGrade("Test", 2017,"HW1", "student") == 80); //evaluates to false, implementation is wrong!
    }
}
