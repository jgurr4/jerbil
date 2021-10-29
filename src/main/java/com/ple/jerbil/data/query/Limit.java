package com.ple.jerbil.data.query;

import com.ple.jerbil.data.Immutable;

public class Limit {

    public int limit;
    public int offset;

    @Immutable
    protected Limit(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

}
