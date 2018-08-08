package hello.Transaction;

import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubType_;
import hello.Type.Type;
import hello.Type.Type_;

import javax.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;

public class Transaction_ {

    public static volatile SingularAttribute<Transaction, String> name;
    public static volatile SingularAttribute<Transaction, String> description;
    public static volatile SingularAttribute<Transaction, Float> value;
    public static volatile SingularAttribute<Transaction, Frequency> frequency;
    public static volatile SingularAttribute<Transaction, Genre> genre;
    public static volatile SingularAttribute<Transaction, Type> type;
    public static volatile SingularAttribute<Transaction, LocalDate> date;
    public static volatile SingularAttribute<Transaction, Boolean> executed;




}
