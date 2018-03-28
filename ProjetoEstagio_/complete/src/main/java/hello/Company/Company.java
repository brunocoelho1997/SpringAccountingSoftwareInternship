package hello.Company;

import hello.Entity;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@javax.persistence.Entity(name = "company")
public class Company extends Entity {

    @NotNull
    @Column(nullable = false)
    private float balance;

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}
