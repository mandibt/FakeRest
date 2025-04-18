package utils;

import java.util.HashMap;
import java.util.Map;

public class MalformedAuthorRequest {
    private Map<String, Object> fields = new HashMap<>();

    public MalformedAuthorRequest() {
        // Initialize with default values based on your Author model
        fields.put("id", 0);
        fields.put("idBook", "book-123");
        fields.put("firstName", "Test");
        fields.put("lastName", "Author");
    }

    public MalformedAuthorRequest setField(String fieldName, Object value) {
        fields.put(fieldName, value);
        return this;
    }

    public Map<String, Object> toMap() {
        return fields;
    }
}