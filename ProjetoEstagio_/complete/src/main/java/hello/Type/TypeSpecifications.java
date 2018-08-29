package hello.Type;

import hello.EntityPackage.Entity_;
import hello.Enums.Genre;
import hello.SubType.SubType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class TypeSpecifications {

//    public static Specification<Type> filterSubType(Predicate predicateFinal, SubType subType) {
//        return (root, query, cb) -> {
//            Predicate aux = cb.equal(root.get(Type_.subType), subType);
//
//            return cb.equal(predicateFinal, aux);
//
//        };
//    }

    public static Specification<Type> filter(String value) {
        return (root, query, cb) -> {

            Predicate predicateFinal = null;
            Predicate predicateCategory;
            Predicate predicateValue;
            Predicate predicateActived;
            Predicate predicateSubType;



            if(!value.isEmpty())
            {
                predicateValue = cb.equal(root.get(Type_.name), value);
                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateValue);
                else
                    predicateFinal = predicateValue;
            }



//            if(subType != null)
//            {
//
//                predicateSubType = cb.equal(root.get(Type_.subType), subType);
//                if(predicateFinal!=null)
//                    predicateFinal = cb.and(predicateFinal, predicateSubType);
//                else
//                    predicateFinal = predicateSubType;
//            }

            //just show the clients actived
            predicateActived = cb.equal(root.get(Entity_.actived), true);
            predicateFinal = cb.and(predicateFinal, predicateActived);

            return predicateFinal;
        };
    }

    public static Specification<Type> filterDeleletedEntities(Boolean deletedEntities) {
        return (root, query, cb) -> {
            Predicate predicateFinal = null;

            predicateFinal = cb.equal(root.get(Entity_.actived), !(deletedEntities));

            return predicateFinal;

        };
    }

    public static Specification<Type> filterManuallyCreated(Boolean manuallyCreated) {
        return (root, query, cb) -> {
            Predicate predicateFinal = null;

            predicateFinal = cb.equal(root.get(Type_.manuallyCreated), manuallyCreated);

            return predicateFinal;

        };
    }
}
