package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Immutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
    @Nullable public final Expression generatedFrom;
    @Nullable public final Expression defaultValue;
    public static Column[] emptyArray = new Column[0];

    protected Column(String name, Table table, DataSpec dataSpec, boolean indexed, boolean primary, @Nullable Expression generatedFrom, @Nullable Expression defaultValue) {
        super(name, table, indexed, primary);
        this.dataSpec = dataSpec;
        this.generatedFrom = generatedFrom;
        this.defaultValue = defaultValue;
    }

    public static PartialColumn make(String name, Table table) {
        return PartialColumn.make(name, table);
    }

    public abstract T make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom);

    public T primary() {
        return make(name, dataSpec, indexed, true, generatedFrom);
    }

    public T indexed() {
        return make(name, dataSpec, true, primary, generatedFrom);
    }

    public T generatedFrom(Expression generatedFrom) {
        return make(name, dataSpec, indexed, primary, generatedFrom);
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
          name.equals(column.name) &&
          Objects.equals(indexed, column.indexed) &&
          Objects.equals(primary, column.primary) &&
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
          "name='" + name + '\'' +
          ", dataSpec=" + dataSpec +
          ", indexed=" + indexed +
          ", primary=" + primary +
          ", generatedFrom=" + generatedFrom +
          ", defaultValue=" + defaultValue +
          "}";
    }

}
