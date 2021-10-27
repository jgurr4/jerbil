package com.ple.jerbil.sql.query;


import com.ple.jerbil.sql.Immutable;
import com.ple.jerbil.sql.PotentialQuery;
import com.ple.jerbil.sql.selectExpression.Column;
import com.ple.jerbil.sql.selectExpression.Expression;
import com.ple.jerbil.sql.selectExpression.SelectExpression;
import com.ple.jerbil.sql.selectExpression.booleanExpression.BooleanExpression;
import com.ple.util.IArrayList;
import com.ple.util.IList;
import com.ple.util.IMap;
import org.jetbrains.annotations.Nullable;

@Immutable
public class Query extends PotentialQuery {

    @Nullable public final IList<BooleanExpression> where;
    @Nullable public final FromExpression fromExpression;
    @Nullable public final QueryType queryType;
    @Nullable public final IList<SelectExpression> select;
    @Nullable public final IList<SelectExpression> groupBy;
    @Nullable public final IList<SelectExpression> orderBy;
    @Nullable public final IList<BooleanExpression> having;
    @Nullable public final Limit limit;
    @Nullable public final IMap<Column, Expression> set;
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

    protected Query(@Nullable IList<BooleanExpression> where, @Nullable FromExpression fromExpression, @Nullable QueryType queryType, @Nullable IList<SelectExpression> select, @Nullable IList<SelectExpression> groupBy, @Nullable IList<SelectExpression> orderBy, @Nullable IList<BooleanExpression> having, @Nullable Limit limit, @Nullable IMap<Column, Expression> set, @Nullable boolean mayInsert, @Nullable boolean mayReplace, @Nullable boolean triggerDeleteWhenReplacing, @Nullable boolean mayThrowOnDuplicate) {
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
        return new SelectQuery(where, fromExpression, queryType, IArrayList.make(selectExpressions), groupBy, orderBy, having, limit, set, mayInsert, mayReplace, triggerDeleteWhenReplacing, mayThrowOnDuplicate);
    }
}
