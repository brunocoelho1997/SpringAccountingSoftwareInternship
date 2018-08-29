package hello.Employee;

import hello.EntityPackage.Entity_;
import hello.Person.Person_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class EmployeeSpecifications {

    public static Specification<Employee> filter(String value) {
        return (root, query, cb) -> {


            Predicate predicateFinal = null;
            Predicate predicateName;
            Predicate predicateActived;


            if(!value.isEmpty())
            {
                try{

                    Long id = Long.parseLong(value);
                    predicateFinal = cb.equal(root.get(Entity_.id), id);

                    //just show the persons actived
                    predicateActived = cb.equal(root.get(Entity_.actived), true);
                    predicateFinal = cb.and(predicateFinal, predicateActived);

                    return predicateFinal;

                }catch(NumberFormatException ex){

                    String name = value;
                    predicateName = cb.like(root.get(Person_.name), name);
                    predicateFinal = predicateName;
                }
            }

            //just show the persons actived
            predicateActived = cb.equal(root.get(Entity_.actived), true);
            predicateFinal = cb.and(predicateFinal, predicateActived);


            return predicateFinal;

        };
    }

    public static Specification<Employee> filterDeleletedEntities(Boolean deletedEntities) {
        return (root, query, cb) -> {
            Predicate predicateFinal = null;

            predicateFinal = cb.equal(root.get(Entity_.actived), !(deletedEntities));

            return predicateFinal;

        };
    }
}
