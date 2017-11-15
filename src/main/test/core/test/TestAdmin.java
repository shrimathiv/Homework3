package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vincent on 23/2/2017.
 */
public class TestAdmin {

    private IAdmin admin;

    @Before //set up before any of the unit tests 
    public void setup() {
        this.admin = new Admin(); //set up, admin creates a class 
    }
    
    /* --------------TESTS FOR CREATECLASS -------------------*/

    @Test
    public void testMakeClass() { //check if one instructor can be assigned to 2 classes 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15); //one instructor, 2 classes, should work
        assertTrue(this.admin.classExists("Test2", 2017)); //evaluates to true
    }
    
    @Test
    public void testMakeClass1() { //check if one instructor can be assigned to 3+ classes
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test2", 2017, "Instructor", 15); //one instructor, 2 classes, should work
        this.admin.createClass("Test3", 2017, "Instructor", 15); //3 classes, same instructor, should not work
        assertFalse(this.admin.classExists("Test3", 2017)); //evaluates to false, implementation is wrong! 
    }
    
    @Test
    public void testMakeClass2() { // check if 2 classes in the same year can have same name 
        this.admin.createClass("Test", 2017, "Instructor", 15);
        this.admin.createClass("Test", 2017, "Instructor1", 20); 
        assertFalse(this.admin.getClassCapacity("Test", 2017) == 20); //evaluates to true
    }

    @Test 
    public void testMakeClass3() { //check if year can be in the past
        this.admin.createClass("Test", 2016, "Instructor", 15); //create class in the past. should not work.
        assertFalse(this.admin.classExists("Test", 2016)); // evaluates to FALSE. implementation is wrong!
    } 
    
    @Test 
    public void testMakeClass4() { //check if year can be in the future
        this.admin.createClass("Test", 2018, "Instructor", 15); //create class in the future, capacity > 0. should work.
        assertTrue(this.admin.classExists("Test", 2018)); // evaluates to true
    } 
    
    @Test
    public void testMakeClass5() { //check if class name can be empty
    		this.admin.createClass("", 2018, "Instructor", 15); //shouldn't work
    		assertFalse(this.admin.classExists("", 2018)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testMakeClass6() { //check if instructor name can be null
    		this.admin.createClass("Test", 2018, null, 15); //shouldn't work
    		assertFalse(this.admin.classExists("Test", 2018)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testMakeClass8() { //check if capacity can be zero
    		this.admin.createClass("Test", 2018, "Instructor", 0); //shouldn't work
    		assertFalse(this.admin.classExists("Test", 2018)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testMakeClass9() { //check if capacity can be negative
    		this.admin.createClass("Test", 2018, "Instructor", -1); //shouldn't work
    		assertFalse(this.admin.classExists("Test", 2018)); //evaluates to false, implementation is wrong!
    }
    
    @Test
    public void testMakeClass10() { //check for normal capacity
    		this.admin.createClass("Test", 2018, "Instructor", 15); //should work
    		assertTrue(this.admin.classExists("Test", 2018)); //evaluates to true
    }
    
    @Test
    public void testMakeClass11() { //check for normal class name
    		this.admin.createClass("Test", 2018, "Instructor", 15); //should work
    		assertTrue(this.admin.classExists("Test", 2018)); //evaluates to true
    }
    
    @Test
    public void testMakeClass12() { //check for normal instructor name
    		this.admin.createClass("Test", 2018, "Instructor", 15); //should work
    		assertTrue(this.admin.classExists("Test", 2018)); //evaluates to true
    }
    
    @Test
    public void testMakeClass13() { //check if year can be current year
    		this.admin.createClass("Test", 2017, "Instructor", 15); //should work
    		assertTrue(this.admin.classExists("Test", 2017)); //evaluates to true
    }
    
    @Test
    public void testMakeClass14() { //check if 2 classes in different years can have same name
    		this.admin.createClass("Test", 2018, "Instructor", 15); //should work
    		this.admin.createClass("Test", 2019, "Instructor", 15); //should work
    		assertTrue(this.admin.classExists("Test", 2019)); //evaluates to true
    }
    
    @Test
    public void testMakeClass15() { //create a single class twice with different instructors
    		this.admin.createClass("Test", 2018, "Instructor", 15); //should work
    		this.admin.createClass("Test", 2018, "Instructor1", 15); //should work
    		assertTrue(this.admin.getClassInstructor("Test", 2018) == "Instructor1"); //evaluates to true
    }
    
    
    /* --------------TESTS FOR CHANGECAPACITY -------------------*/
    
    
    @Test 
    public void testChangeCapacity() { //check if capacity can be changed to something bigger than current
    		this.admin.createClass("ECS", 2016, "Instructor", 15);
    		this.admin.changeCapacity("ECS", 2016, 20); //want to change from 15 to 20, should work
        assertTrue(this.admin.getClassCapacity("ECS", 2016) == 20); //Evaluates to true 
    }
    
    @Test 
    public void testChangeCapacity2() { //check if capacity can be changed to something less than current
    		this.admin.createClass("ECS1", 2016, "Instructor", 15);
    		this.admin.changeCapacity("ECS1", 2016, 10); //10 is less than current enrolled, should not work
        assertFalse(this.admin.getClassCapacity("ECS1", 2016) == 10); //Evaluates to FALSE. implementation is wrong!
    }
    
    @Test
    public void testChangeCapacity3() { //check if capacity can be changed to a negative number 
		this.admin.createClass("ECS", 2016, "Instructor", 15);
		this.admin.changeCapacity("ECS", 2016, -20); //want to change from 15 to -20, should not work
		assertFalse(this.admin.getClassCapacity("ECS", 2016) == -20); //Evaluates to false, implementation is wrong! 
    }
    
}
