package model;

import org.json.JSONObject;
import persistence.Writable;
//Represents a Patient in the system

public class Patient implements Writable {
    private String name;
    private int dob;
    private String condition;
    private String doctor;

    //REQUIRES: not empty string for name, dob in YYYYMMDD format.
    // EFFECTS: makes a new patient object with the given name DOB conditions, doctors visited
    public Patient(String name, String conditions, Integer dob, String doctor) {
        this.name = name;
        this.condition = conditions;
        this.dob = dob;
        this.doctor = doctor;
    }

    @Override
    // EFFECTS: returns the patient as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("conditions", condition);
        json.put("dob", dob);
        json.put("doctor", doctor);
        return json;


    }

    //GETTERS


    public String getName() {
        return name;
    }

    public String getCondition() {
        return condition;
    }

    public String getDoctor() {
        return doctor;
    }

    public int getDob() {
        return dob;
    }

    //SETTERS


    //   public void setCondition(String condition) {
//        this.condition = condition;
//    }

//    public void setDob(int dob) {
//        this.dob = dob;
//    }

//    public void setDoctor(String doctor) {
    //       this.doctor = doctor;
//    }

    public void setName(String name) {
        this.name = name;
    }


}

