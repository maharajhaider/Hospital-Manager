package persistence;


import model.Management;
import model.Patient;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

//Represents a reader that reads the management data from the stored data
//Code based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }



    //EFFECTS: reads Json file if valid and returns as Management object.
    public Management read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseManagement(jsonObject);

    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private Management parseManagement(JSONObject jsonObject) {
        Management mg = new Management();
        addPatients(mg, jsonObject);
        return mg;
    }

    // MODIFIES: mg
    // EFFECTS: parses patients from JSON object and adds them to management
    private void addPatients(Management mg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("patients");
        for (Object json : jsonArray) {
            JSONObject nextPatients = (JSONObject) json;
            addPatient(mg, nextPatients);
        }

    }
    // MODIFIES: mg
    // EFFECTS: parses patient from JSON object and adds it to management

    private void addPatient(Management mg, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String condition = jsonObject.getString("conditions");
        Integer dob = jsonObject.getInt("dob");
        String doctor = jsonObject.getString("doctor");
        mg.addPatientToManagement(name,condition,dob,doctor);
    }
}
