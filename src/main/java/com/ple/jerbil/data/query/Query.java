package com.ple.jerbil.data.query;


import com.ple.jerbil.data.DelayedImmutable;
import com.ple.jerbil.data.PotentialQuery;
import com.ple.jerbil.data.selectExpression.Column;
import com.ple.jerbil.data.selectExpression.Expression;
import com.ple.jerbil.data.selectExpression.SelectExpression;
import com.ple.jerbil.data.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@DelayedImmutable
public class Query extends PotentialQuery {

    @Nullable public final BooleanExpression where;
    @Nullable public final FromExpression fromExpression;
    @Nullable public final QueryType queryType;
    @Nullable public final IList<SelectExpression> select;
    @Nullable public final IList<SelectExpression> groupBy;
    @Nullable public final IList<SelectExpression> orderBy;
    @Nullable public final IList<BooleanExpression> having;
    @Nullable public final Limit limit;
    @Nullable public final IList<IMap<Column, Expression>> set;
    @Nullable public final boolean mayInsert;
    @Nullable public final boolean mayReplace;
    @Nullable public final boolean triggerDeleteWhenReplacing;
    @Nullable public final boolean mayThrowOnDuplicate;
    // Alternative names: mayDeleteInsteadOfUpdate; mayTriggerDelete; mayDeleteOnDuplicate;
    // Insert = mayInsert : true; mayReplace: false; mayThrowOnDuplciate : true;
    // Insert ignore =  mayInsert : true; mayReplace: false; mayThrowOnDuplicate: False;
    // Insert on Duplicate Key Update(upsert) = mayInsert : true; mayReplace : true; mayThrowOnDuplicate : false; triggerDeleteWhenReplacing = true;  //Deletes duplicates and replaces them causing delete trigger to go off.
    // Replace = mayInsert : true; mayReplace : true; mayThrowOnDuplicate : false; triggerDeleteWhenReplacing : false; //Instead of deleting a duplicate it updates it, causing the update trigger to go off.
    // update = mayInsert : false; mayReplace : true; mayThrowOnDuplicate : false; triggerDeleteWhenReplacing : false;

    protected Query(@Nullable BooleanExpression where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IList<IMap<Column, Expression>> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
        this.where = where;
        this.fromExpression = fromExpression;
        this.queryType = queryType;
        this.select = select;
        this.groupBy = groupBy;
        this.orderBy = orderBy;
        this.having = having;
        this.limit = limit;
        this.set = set;
        this.mayInsert = mayInsert;
        this.mayReplace = mayReplace;
        this.triggerDeleteWhenReplacing = triggerDeleteWhenReplacing;
        this.mayThrowOnDuplicate = mayThrowOnDuplicate;
    }

    @Override
    public SelectQuery select(SelectExpression... selectExpressions) {
        return SelectQuery.make(this.where, this.fromExpression, QueryType.select, IArrayList.make(selectExpressions), this.groupBy, this.orderBy, this.having, this.limit, this.set, this.mayInsert, this.mayReplace, this.triggerDeleteWhenReplacing, this.mayThrowOnDuplicate);
    }
}
