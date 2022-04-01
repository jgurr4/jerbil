package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.*;
import com.ple.jerbil.data.reactiveUtils.ReactiveMono;
import com.ple.jerbil.data.sync.ColumnDiff;
import com.ple.util.Immutable;
import com.ple.jerbil.data.query.Table;
import com.ple.jerbil.data.sync.SyncResult;
import com.ple.jerbil.data.translator.LanguageGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Immutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
//    @Nullable public final Expression generatedFrom;
    @Nullable public final Expression defaultValue;
    public static Column[] emptyArray = new Column[0];
    public final ColumnProps props;

    protected Column(String columnName, Table table, DataSpec dataSpec, @Nullable Expression defaultValue, ColumnProps props) {
        super(columnName, table);
        this.dataSpec = dataSpec;
//        this.generatedFrom = generatedFrom;
        this.defaultValue = defaultValue;
        this.props = props;
    }

    public static PartialColumn make(String columnName, Table table) {
        return PartialColumn.make(columnName, table);
    }

    public abstract T make(String columnName, DataSpec dataSpec);

    public String toSql() {
        if (DataGlobal.bridge == null) {
            throw new NullPointerException("Global.sqlGenerator not set.");
        }
        return toSql(DataGlobal.bridge.getGenerator());
    }

    public String toSql(LanguageGenerator generator) {
        return generator.toSql(this);
    }

    public ReactiveMono<SyncResult<ColumnDiff>> sync() {
        return null;
    }

    public ReactiveMono<SyncResult<ColumnDiff>> sync(Column leftColumn, Column rightColumn) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Column)) return false;
        if (!super.equals(o)) return false;
        Column<?> column = (Column<?>) o;
        return dataSpec.equals(column.dataSpec) && Objects.equals(defaultValue,
            column.defaultValue) && props.equals(column.props);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dataSpec, defaultValue, props);
    }

    @Override
    public String toString() {
        return "Column{" +
          "columnName='" + columnName + '\'' +
          ", dataSpec=" + dataSpec +
//          ", generatedFrom=" + generatedFrom +
          ", defaultValue=" + defaultValue +
          "}";
    }
}
