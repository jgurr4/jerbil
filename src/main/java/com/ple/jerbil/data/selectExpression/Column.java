package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

@DelayedImmutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
    @Nullable public final Expression generatedFrom;

    protected Column(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        super(name, table, indexed, primary);
        this.dataSpec = dataSpec;
        this.generatedFrom = generatedFrom;
    }

    public static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public abstract T make(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom);

    public T primary() {
        return make(name, table, dataSpec, indexed, true, null);
    }

    public T indexed() {
        return make(name, table, dataSpec, true, primary, null);
    }

    public T generatedFrom(Expression generatedFrom) {
        return make(name, table, dataSpec, indexed, primary, generatedFrom);
    }

}
