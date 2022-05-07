package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

class ManagementTest {
    Management testManagement;

    @BeforeEach
    private void runBeforeEach() {
        testManagement = new Management();
        testManagement.addPatientToManagement("Paris", "Kidney disorder", 20031004, "Dr.Paul");


    }


    //public void ManagementConstructorTest(){
    //   ArrayList<Patient> patients = testManagement.getPatients();
    //   assertEquals(0 , patients.size());

    @Test
    public void addPatientToManagementTest() {

        assertEquals(1, testManagement.getPatients().size());
        assertEquals("Paris", testManagement.getPatients().get(0).getName());
        testManagement.addPatientToManagement("David", "high blood pressure",
                20000108, "Dr.Boparai");
        assertEquals(2, testManagement.getPatients().size());
        assertEquals("David", testManagement.getPatients().get(1).getName());
    }

    @Test
    public void removePatientTestOneSuccess() {
        testManagement.removePatient("Paris");
        assertEquals(0, testManagement.getPatients().size());

    }
    @Test
    public void removePatientTestOneFail() {
        assertEquals("Failed to remove as couldn't find the patient." , testManagement.removePatient("David"));

    }

    @Test
    public void renamePatientTest() {
        testManagement.renamePatient("Paris", "John");
        assertEquals("John", testManagement.searchPatients("John").getName());
        assertEquals("Sorry, couldn't find the patient. Check input for error.",
                testManagement.renamePatient("Gob", "Jack"));
        assertEquals("Successfully renamed",
                testManagement.renamePatient("John", "Jonathan"));

    }

    @Test
    public void searchPatientTest() {
        assertEquals(testManagement.getPatients().get(0), testManagement.searchPatients("Paris"));
        testManagement.addPatientToManagement("David", "high blood pressure",
                20000108, "Dr.Boparai");
        assertEquals(testManagement.getPatients().get(1), testManagement.searchPatients("David"));
        assertEquals(null , testManagement.searchPatients("Gob"));

    }

    @Test
    public void searchPatientConditions() {
        assertEquals("Kidney disorder",
                    testManagement.searchPatientCondition("Paris"));
        testManagement.addPatientToManagement("David", "high blood pressure",
                20000108, "Dr.Boparai");
        assertEquals("high blood pressure",
                testManagement.searchPatientCondition("David"));

    }

    @Test
    public void findDoctorTest(){
        assertEquals( testManagement.getTaran() , testManagement.findDoctor("Skin"));
        assertEquals( testManagement.getJames(), testManagement.findDoctor("Mental Health"));
        assertEquals( testManagement.getLana(), testManagement.findDoctor("Digestion"));
        assertEquals( testManagement.getBoparai(), testManagement.findDoctor("Heart"));
        assertEquals( null , testManagement.findDoctor("Kidney"));
        assertEquals(testManagement.getBoparai().name, testManagement.getBoparai().getName());
        assertEquals(testManagement.getBoparai().fieldOfStudy, testManagement.getBoparai().getFieldOfStudy());
        assertEquals(testManagement.getBoparai().contact, testManagement.getBoparai().getContact());
    }

    @Test
    public void findNumberOfBeds(){
        assertEquals(testManagement.findNumberOfEmptyBed(), testManagement.numberOfBeds -1);
        testManagement.addPatientToManagement("Maharaj", "condition", 10202003, "Dr.Bop");
        assertEquals(testManagement.findNumberOfEmptyBed(), testManagement.numberOfBeds -2 );
        testManagement.addPatientToManagement("Haider", "kidney", 10202002, "Dr.tara");
        assertEquals(testManagement.findNumberOfEmptyBed(), testManagement.numberOfBeds -3 );
        testManagement.addPatientToManagement("iu", "heart", 10202003, "Dr.Bop");
        assertEquals(testManagement.findNumberOfEmptyBed(), testManagement.numberOfBeds -4 );
    }


}