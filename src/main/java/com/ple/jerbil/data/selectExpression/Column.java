package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.selectExpression.NumericExpression.NumericColumn;
import com.ple.jerbil.data.sync.SyncResult;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Objects;

@Immutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
    @Nullable public final Expression generatedFrom;
    @Nullable public final Expression defaultValue;
    public static Column[] emptyArray = new Column[0];
    public final BuildingHints hints;

    protected Column(String columnName, Table table, DataSpec dataSpec, @Nullable Expression generatedFrom,
                     @Nullable Expression defaultValue, BuildingHints hints) {
        super(columnName, table);
        this.dataSpec = dataSpec;
        this.generatedFrom = generatedFrom;
        this.defaultValue = defaultValue;
        this.hints = hints;
    }

    public static PartialColumn make(String columnName, Table table) {
        return PartialColumn.make(columnName, table);
    }

    public abstract T make(String columnName, DataSpec dataSpec, Expression generatedFrom);

    public abstract T indexed();

    public abstract T primary();

    public abstract T unique();

    public abstract T invisible();

    public abstract T allowNull();

    public abstract T defaultValue(Expression e);

    public abstract T defaultValue(Enum<?> value);

    public abstract T onUpdate(Expression e);

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
          "columnName='" + columnName + '\'' +
          ", dataSpec=" + dataSpec +
          ", generatedFrom=" + generatedFrom +
          ", defaultValue=" + defaultValue +
          "}";
    }

    public SyncResult sync() {
        return null;
    }

}
