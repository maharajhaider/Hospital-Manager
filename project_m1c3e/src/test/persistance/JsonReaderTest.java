package persistance;

import model.Management;
import model.Patient;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

        @Test

        void testReaderNonExistentFile() {
            JsonReader reader = new JsonReader("./data/noSuchFile.json");
            try {
                Management mg = reader.read();
                fail("IOException expected");
            } catch (IOException e) {
                //pass
            }
        }

        @Test
        void testReaderEmptyWorkRoom() {
            JsonReader reader = new JsonReader("./data/testReaderEmptyManagement.json");
            try {
                Management mg = reader.read();
                assertEquals(0, mg.getPatients().size());
            } catch (IOException e) {
                fail("Couldn't read from file");
            }
        }

        @Test
        void testReaderGeneralWorkRoom() {
            JsonReader reader = new JsonReader("./data/testReaderUsualManagement.json");
            try {
                Management mg = reader.read();
                List<Patient> patients = mg.getPatients();
                assertEquals(2, patients.size());
                checkPatient("Maharaj", "Heart", 20031004,"Boparai", patients.get(0));
                checkPatient("Haider", "Mental Health", 20020903,"Tarun", patients.get(1));

            } catch (IOException e) {
                fail("Couldn't read from file");
            }
        }


    }
