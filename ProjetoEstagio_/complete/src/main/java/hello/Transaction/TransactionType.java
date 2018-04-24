package hello.Transaction;

import hello.SubType.SubType;
import hello.Type.Type;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name="transaction_type")
public class TransactionType extends hello.Entity{

    @NotNull
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TransactionType_Type"), nullable = false)
    /*
    TODO: perguntar ao hugo se é refresh
     */
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private Type type;

    @JoinColumn(foreignKey = @ForeignKey(name = "FK_TransactionType_SubType"))
    /*
    TODO: perguntar ao hugo se é refresh
     */
    @ManyToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private SubType subType;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SubType getSubType() {
        return subType;
    }

    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
                "type=" + type +
                ", subType=" + subType +
                '}';
    }
}
