package hello.Currency;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Entity(name="currency")
public class Currency extends hello.EntityPackage.Entity {
    @NotNull
    @Column(nullable = false)
    String name;
    @NotNull
    @Column(nullable = false)
    String symbol;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return ""+symbol;
    }
}
