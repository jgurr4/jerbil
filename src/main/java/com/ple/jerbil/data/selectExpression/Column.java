package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataGlobal;
import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.LanguageGenerator;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@DelayedImmutable
public abstract class Column <T extends Column> extends PartialColumn{

    public final DataSpec dataSpec;
    @Nullable public final Expression generatedFrom;

    protected Column(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom) {
        super(name, indexed, primary);
        this.dataSpec = dataSpec;
        this.generatedFrom = generatedFrom;
    }

    public static PartialColumn make(String name) {
        return PartialColumn.make(name);
    }

    public abstract T make(String name, DataSpec dataSpec, boolean indexed, boolean primary, Expression generatedFrom);

    public T primary() {
        return make(name, dataSpec, indexed, true, null);
    }

    public T indexed() {
        return make(name, dataSpec, true, primary, null);
    }

    public T generatedFrom(Expression generatedFrom) {
        return make(name, dataSpec, indexed, primary, generatedFrom);
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
          Objects.equals(generatedFrom, column.generatedFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSpec, generatedFrom);
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
    public String toString() {
        return "Column{" +
          "name='" + name + '\'' +
          ", dataSpec=" + dataSpec +
          ", indexed=" + indexed +
          ", primary=" + primary +
          ", generatedFrom=" + generatedFrom +
          "}";
    }

}
