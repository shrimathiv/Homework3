package core.test;

import core.api.IStudent;
import core.api.impl.Student;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestStudent {

    private IStudent student;
    private IInstructor instructor;
    private IAdmin admin;

    @Before //set up before any of the unit tests 
    public void setup() {
        this.admin = new Admin(); 
        this.instructor = new Instructor();
        this.student = new Student();
    }
    
    /* --------------TESTS FOR REGISTERFORCLASS -------------------*/

    @Test
    public void testRegClass() { //normal functionality- register student for a class that exists  
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("student", "Test", 2017); //should work
        assertTrue(this.student.isRegisteredFor("student", "Test", 2017)); //evaluates to true
    }
    
    @Test
    public void testRegClass1() { //register student for a class that's at max capacity   
        this.admin.createClass("Test", 2017, "Instructor", 1);
        this.student.registerForClass("student", "Test", 2017); //should work
        this.student.registerForClass("student2", "Test", 2017); //should not work
        assertFalse(this.student.isRegisteredFor("student2", "Test", 2017)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testRegClass2() { //register student for a class that doesn't exist   
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("student", "Test1", 2017); //should not work
        assertFalse(this.student.isRegisteredFor("student", "Test1", 2017)); //evaluates to true
    }
    
    
    /* --------------TESTS FOR DROPCLASS -------------------*/
    
    @Test
    public void testDropClass() { //normal functionality- drop class for student registered 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("student", "Test1", 2017); 
        this.student.dropClass("student", "Test1", 2017);
        assertFalse(this.student.isRegisteredFor("student", "Test", 2017)); //evaluates to true
    }
    
    @Test
    public void testDropClass1() { //drop class for student not registered
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertFalse(this.student.isRegisteredFor("student", "Test", 2017)); //evaluates to true
    }
 
    
    /* --------------TESTS FOR SUBMITHW -------------------*/
    
    @Test
    public void testSubmitHW() { //normal functionality - submit hw for student reg, same year, hw assigned
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.registerForClass("student", "Test", 2017); 
        this.student.submitHomework("student", "HW1", "answer", "Test", 2017); //should work
        assertTrue(this.student.hasSubmitted("student", "HW1", "Test", 2017)); //evaluates to true
    }
    
    @Test
    public void testSubmitHW1() { //submit hw for student not registered
    		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2017, "HW1");
        this.student.submitHomework("student", "HW1", "answer", "Test", 2017); //should not work
        assertFalse(this.student.hasSubmitted("student", "HW1", "Test", 2017)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testSubmitHW2() { //submit hw for class that doesnt have a hw
    		this.admin.createClass("Test", 2017, "Instructor", 15);
        this.student.registerForClass("student", "Test", 2017); 
        this.student.submitHomework("student", "HW1", "answer", "Test", 2017); //should not work
        assertFalse(this.student.hasSubmitted("student", "HW1", "Test", 2017)); //evaluates to true 
    }
    
    @Test
    public void testSubmitHW3() { //submit hw for class not in this year
    		this.admin.createClass("Test", 2018, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Test", 2018, "HW1");
        this.student.registerForClass("student", "Test", 2018); 
        this.student.submitHomework("student", "HW1", "answer", "Test", 2018); //should not work
        assertFalse(this.student.hasSubmitted("student", "HW1", "Test", 2018)); //evaluates to false, implementation is wrong!
    }
    
}
