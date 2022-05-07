package persistance;

import model.Management;
import model.Patient;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Management mg = new Management();
            persistence.JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Management mg = new Management();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyManagement.json");
            writer.open();
            writer.write(mg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyManagement.json");
            mg = reader.read();
            assertEquals(0, mg.getPatients().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Management mg = new Management();
            mg.addPatientToManagement( "Maharaj", "Heart", 20031004,"Boparai");
            mg.addPatientToManagement("Haider", "Mental Health", 20020903,"Tarun");
            JsonWriter writer = new JsonWriter("./data/testWriterUsualManagement.json");
            writer.open();
            writer.write(mg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterUsualManagement.json");
            mg = reader.read();

            List<Patient> patients = mg.getPatients();
            assertEquals(2, patients.size());
            checkPatient("Maharaj", "Heart", 20031004,"Boparai", patients.get(0));
            checkPatient("Haider", "Mental Health", 20020903,"Tarun", patients.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
