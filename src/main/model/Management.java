package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Arrays;

// EFFECTS: It represents the management of an hospital which can perform the actions
public class Management implements Writable {
    private ArrayList<Patient> patients;
    public final Integer numberOfBeds = 20;
    //CONSTANTS
    private Doctor james = new Doctor("James", "Mental Health", "778 909 1234");
    private Doctor lana = new Doctor("Lana", "Digestion", "778 990 9900");
    private Doctor taran = new Doctor("Taran", "Skin", "777 877 8787");
    private Doctor boparai = new Doctor("Boparai", "Heart", "618 618 6188");



    //EFFECTS: makes a new database with no patients
    public Management() {
        patients = new ArrayList<Patient>();

    }


    //MODIFIES: this
    //EFFECTS: adds a new patients to the system.
    public void addPatientToManagement(String name, String condition,
                                       Integer dob, String doctor) {
        Patient newPatient = new Patient(name, condition, dob, doctor);
        patients.add(newPatient);
        EventLog.getInstance().logEvent(new Event("Patient " + name + " added to the system. "));
    }

    //REQUIRES: not an empty string
    //MODIFIES: this
    //EFFECTS: removes the patient with the given name.
    public String removePatient(String nameOfThePatientToBeRemoved) {
        Patient s = searchPatients(nameOfThePatientToBeRemoved);
        if (s != null) {
            patients.remove(s);
            EventLog.getInstance().logEvent(new Event("Patient "
                    + nameOfThePatientToBeRemoved + " removed from the system. "));
            return ("Removed successfully");
        } else {
            return ("Failed to remove as couldn't find the patient.");

        }
    }


    //REQUIRES: not an empty string
    //MODIFIES: this
    //EFFECTS: changes the name of the patient.
    public String renamePatient(String prevName, String newName) {
        Patient s = searchPatients(prevName);
        if (s != null) {
            patients.remove(s);
            s.setName(newName);
            patients.add(s);
            EventLog.getInstance().logEvent(new Event("Patient " + prevName + " renamed to " + " newName"));
            return "Successfully renamed";
        } else {
            return ("Sorry, couldn't find the patient. Check input for error.");
        }

    }

    //REQUIRES: not an empty string
    //EFFECTS: returns the patient with the given name.
    public Patient searchPatients(String name) {
        EventLog.getInstance().logEvent(new Event("searched for " + name));
        for (Patient next : patients) {
            if (name.equals(next.getName())) {
                return next;
            }

        }
        return null;
    }

    //REQUIRES: not an empty string
    //EFFECTS: returns the condition of the patient with the given name.
    public String searchPatientCondition(String name) {
        EventLog.getInstance().logEvent(new Event("search for condition of " + name));
        return searchPatients(name).getCondition();

    }
    //REQUIRES:Input needs to match one of the diseases.
    //EFFECTS: Suggests a doctor to the patients based on the symtoms

    public Doctor findDoctor(String condition) {
        ArrayList<Doctor> doctors = new ArrayList<Doctor>(Arrays.asList(james, lana, taran, boparai));
        for (Doctor i  :doctors) {
            if (i.fieldOfStudy.equalsIgnoreCase(condition)) {
                return i;
            }
        }
        return null;
    }

    public Integer findNumberOfEmptyBed() {
        return numberOfBeds - patients.size();
    }

    @Override
    // EFFECTS: returns the Management as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("patients", patientsToJson());

        return json;
    }
    // EFFECTS: returns the Patient as JSON object

    public JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Patient i: patients) {
            jsonArray.put(i.toJson());
        }
        return jsonArray;
    }

    //GETTERS

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public Doctor getBoparai() {
        return boparai;
    }

    public Doctor getJames() {
        return james;
    }

    public Doctor getLana() {
        return lana;
    }

    public Doctor getTaran() {
        return taran;
    }
}
