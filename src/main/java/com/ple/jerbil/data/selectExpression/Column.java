package com.ple.jerbil.data.selectExpression;

import com.ple.jerbil.data.DataSpec;
import com.ple.jerbil.data.DelayedImmutable;
import org.jetbrains.annotations.Nullable;

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

}
