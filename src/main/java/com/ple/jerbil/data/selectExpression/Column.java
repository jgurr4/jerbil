package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.sync.SyncResult;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Immutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
    @Nullable public final Expression generatedFrom;
    @Nullable public final Expression defaultValue;
    public static Column[] emptyArray = new Column[0];

    protected Column(String name, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom, @Nullable Expression defaultValue) {
        super(name, table);
        this.dataSpec = dataSpec;
        this.generatedFrom = generatedFrom;
        this.defaultValue = defaultValue;
    }

    public static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public abstract T make(String name, DataSpec dataSpec, Expression generatedFrom);

    public T generatedFrom(Expression generatedFrom) {
        return make(columnName, dataSpec, generatedFrom);
    }

    public String toSql() {
        if (DataGlobal.bridge == null) {
            throw new NullPointerException("Global.sqlGenerator not set.");
        }
        return toSql(DataGlobal.bridge.getGenerator());
    }

    public String toSql(LanguageGenerator generator) {
        return generator.toSql(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        Column<?> column = (Column<?>) o;
        return dataSpec.equals(column.dataSpec) &&
          columnName.equals(column.columnName) &&
          Objects.equals(generatedFrom, column.generatedFrom) &&
          Objects.equals(defaultValue, column.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSpec, generatedFrom, defaultValue);
    }

    @Override
    public String toString() {
        return "Column{" +
          "name='" + columnName + '\'' +
          ", dataSpec=" + dataSpec +
          ", generatedFrom=" + generatedFrom +
          ", defaultValue=" + defaultValue +
          "}";
    }

    public SyncResult sync() {
        return null;
    }
}
