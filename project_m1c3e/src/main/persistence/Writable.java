package persistence;

import org.json.JSONObject;
//Code based on https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo


// this method returns this as JSON object

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
