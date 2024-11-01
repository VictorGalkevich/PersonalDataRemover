package org.example.transformer;

import org.example.source.Csv;
import org.example.source.Db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DbToCsvTransformer implements Transformer<Db, Csv> {

    @Override
    public Csv transform(Db from, int size) throws SQLException {
        if (size == 0) {
            return null;
        }

        ResultSet resultSet = from.getResultSet();

        StringBuilder builder = new StringBuilder();

        while (resultSet.next()) {
            for (String name: from.getFields()) {
                builder.append(name).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append("\n");
        }
        return new Csv(builder.toString(), from.getFields());
    }
}
