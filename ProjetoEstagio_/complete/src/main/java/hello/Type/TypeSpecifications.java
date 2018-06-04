package hello.Type;

import hello.EntityPackage.Entity_;
import hello.Enums.Category;
import hello.Enums.Genre;
import hello.SubType.SubType;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class TypeSpecifications {

    public static Specification<Type> filterSubType(Predicate predicateFinal, SubType subType) {
        return (root, query, cb) -> {
            Predicate aux = cb.equal(root.get(Type_.subType), subType);

            return cb.equal(predicateFinal, aux);

        };
    }

    public static Specification<Type> filter(String value, String category) {
        return (root, query, cb) -> {

            Predicate predicateFinal = null;
            Predicate predicateCategory;
            Predicate predicateValue;
            Predicate predicateActived;
            Predicate predicateSubType;



            if(!value.isEmpty())
            {
                try{

                    predicateValue = cb.equal(root.get(Type_.category), Category.valueOf(value));
                    if(predicateFinal!=null)
                        predicateFinal = cb.and(predicateFinal, predicateValue);
                    else
                        predicateFinal = predicateValue;
                    return predicateFinal;

                }catch (IllegalArgumentException ex){
                    if(!category.isEmpty())
                    {
                        predicateCategory = cb.equal(root.get(Type_.category), Category.valueOf(category));
                        if(predicateFinal!=null)
                            predicateFinal = cb.and(predicateFinal, predicateCategory);
                        else
                            predicateFinal = predicateCategory;
                    }
                }
            }

            if (!category.isEmpty())
            {
                predicateCategory = cb.equal(root.get(Type_.category), Category.valueOf(category));
                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateCategory);
                else
                    predicateFinal = predicateCategory;
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
}
