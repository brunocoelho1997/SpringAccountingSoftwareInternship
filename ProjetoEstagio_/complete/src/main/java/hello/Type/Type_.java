package hello.Type;

import hello.Enums.Category;
import hello.SubType.SubType;

import javax.persistence.metamodel.SingularAttribute;

public class Type_ {

    public static volatile SingularAttribute<Type, String> name;
    public static volatile SingularAttribute<Type, Category> category;
    public static volatile SingularAttribute<Type, SubType> subType;

}
