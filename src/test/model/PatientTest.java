package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientTest {
    Patient testPatient;
    String condition;
    String doctor;



    @BeforeEach
    public void runBefore(){
         condition = "diabetes";
         doctor = "Dr.Boparai";

         testPatient = new Patient("Paris",condition, 20031004, doctor );
    }
    @Test
    public void patientConstructorTest (){
        assertEquals("Paris", testPatient.getName());
        assertEquals(condition, testPatient.getCondition());
        assertEquals(20031004, testPatient.getDob());
        assertEquals(doctor, testPatient.getDoctor());
    }
    @Test
    public void setNameTest() {
        testPatient.setName("David");
        assertEquals("David", testPatient.getName());
    }


}
