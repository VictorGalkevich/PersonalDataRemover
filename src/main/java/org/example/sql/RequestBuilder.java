package org.example.sql;

import java.util.List;

public class RequestBuilder {
    public static String generate(List<String> fields, String tableName) {
        StringBuilder builder = new StringBuilder("SELECT ");
        for (String field : fields) {
            builder.append(field).append(", ");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append(" FROM ");
        builder.append(tableName);
        return builder.toString();
    }
}
