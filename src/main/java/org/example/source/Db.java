package org.example.source;

import java.sql.ResultSet;
import java.util.List;

public class Db extends File {
    private final ResultSet resultSet;
    private final List<String> fields;

    public Db(ResultSet resultSet, List<String> fields) {
        this.resultSet = resultSet;
        this.fields = fields;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public List<String> getFields() {
        return fields;
    }
}
