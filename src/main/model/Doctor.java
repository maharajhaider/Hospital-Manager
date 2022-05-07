package model;


// It represents the doctors in the hospital

public class Doctor {
    String name;
    String fieldOfStudy;
    String contact;

//EFFECT: creates a new doctor with the given name, field of study and their contact info
    public Doctor(String name, String fieldOfStudy, String contact) {
        this.name = name;
        this.fieldOfStudy = fieldOfStudy;
        this.contact = contact;

    }

//GETTERS

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }
}
