package com.ple.jerbil.data.query;

import com.ple.jerbil.data.GenericInterfaces.Immutable;

@Immutable
public class Limit {
    final public int limit;
    final public int offset;

    protected Limit(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    public static Limit make(int offset, int limit) {
        return new Limit(offset, limit);
    }

    public static Limit make(int limit) {
        return new Limit(0, limit);
    }

}
