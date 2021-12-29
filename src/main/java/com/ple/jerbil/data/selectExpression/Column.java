package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.Immutable;
import com.ple.jerbil.data.query.Table;

@Immutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;

    protected Column(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary) {
        super(name, table, indexed, primary);
        this.dataSpec = dataSpec;
    }

    public static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public abstract T make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary);

    public T primary() {
        return make(name, table, dataSpec, indexed, true);
    }

    public T indexed() {
        return make(name, table, dataSpec, true, primary);
    }

//    public abstract T generatedFrom(Expression expression);

}
