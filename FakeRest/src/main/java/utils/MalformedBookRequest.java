package utils;

import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.Map;

public class MalformedBookRequest {
    private Map<String, Object> fields = new HashMap<>();
    
    public MalformedBookRequest() {
        // Initialize with default values
        fields.put("title", "Test Book");
        fields.put("description", "Test Description");
        fields.put("pageCount", 100);
        fields.put("excerpt", "Test Excerpt");
        fields.put("publishDate", LocalDateTime.now().toString());
    }
    
    public MalformedBookRequest setField(String fieldName, Object value) {
        fields.put(fieldName, value);
        return this;
    }
    
    public Map<String, Object> toMap() {
        return fields;
    }
}