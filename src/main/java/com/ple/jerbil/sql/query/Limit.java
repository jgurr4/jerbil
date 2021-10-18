package com.ple.jerbil.sql.query;

import com.ple.jerbil.sql.Immutable;

public class Limit {

    public int limit;
    public int offset;

    @Immutable
    protected Limit(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

}
