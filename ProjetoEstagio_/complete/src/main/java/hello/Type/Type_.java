package hello.Type;

import hello.SubType.SubType;
import hello.SubType.SubType_;

import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

public class Type_ {

    public static volatile SingularAttribute<Type, String> name;
    public static volatile SingularAttribute<Type, List<SubType>> subTypeList;

}
