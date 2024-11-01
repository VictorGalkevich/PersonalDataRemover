package org.example.source;

import java.util.List;

public class Csv extends File {
    private final String body;
    private final List<String> fields;

    public Csv(String body, List<String> fields) {
        this.body = body;
        this.fields = fields;
    }

    public String getBody() {
        return body;
    }

    public List<String> getFields() {
        return fields;
    }
}
