package medo.framework.saga.orchestration;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class EnlistedAggregate {

    private final Class<?> aggregateClass;
    private final Object aggregateId;

    public EnlistedAggregate(Class<?> aggregateClass, Object aggregateId) {
        this.aggregateClass = aggregateClass;
        this.aggregateId = aggregateId;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Class<?> getAggregateClass() {
        return aggregateClass;
    }

    public Object getAggregateId() {
        return aggregateId;
    }
}
