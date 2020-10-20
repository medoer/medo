package medo.payment.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/** Value Object - Money */
@Data
public class Money implements Serializable {

    public static Money ZERO = new Money(0);

    private BigDecimal amount;

    private Money() {}

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public Money(String s) {
        this.amount = new BigDecimal(s);
    }

    public Money(long i) {
        this.amount = new BigDecimal(i);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Money money = (Money) o;

        return new EqualsBuilder().append(amount, money.amount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(amount).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("amount", amount).toString();
    }

    public Money add(Money delta) {
        return new Money(amount.add(delta.amount));
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return amount.compareTo(other.amount) >= 0;
    }

    public boolean isGreaterThan(Money other) {
        return amount.compareTo(other.amount) > 0;
    }

    public String asString() {
        return amount.toPlainString();
    }

    public Money multiply(int x) {
        return new Money(amount.multiply(new BigDecimal(x)));
    }

    //    // getter setter for mybatis plus map
    //    public BigDecimal getAmount() {
    //        return amount;
    //    }
    //
    //    public void setAmount(BigDecimal amount) {
    //        this.amount = amount;
    //    }
}
