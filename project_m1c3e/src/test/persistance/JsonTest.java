package persistance;

import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
        protected void checkPatient(String name, String conditions, Integer dob, String doctor, Patient p) {
            assertEquals(name, p.getName());
            assertEquals(conditions, p.getCondition());
            assertEquals(dob, p.getDob());
            assertEquals(doctor, p.getDoctor());
        }

}
