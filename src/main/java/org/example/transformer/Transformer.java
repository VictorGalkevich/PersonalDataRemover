package org.example.transformer;

import java.sql.SQLException;

public interface Transformer<F, T> {
    T transform(F from, int size) throws SQLException;
}
