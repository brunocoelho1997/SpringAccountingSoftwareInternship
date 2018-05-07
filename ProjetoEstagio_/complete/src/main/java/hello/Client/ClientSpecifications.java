package hello.Client;

import hello.EntityPackage.Entity_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class ClientSpecifications {


    public static Specification<Client> filter(String value) {
        return (root, query, cb) -> {


            Predicate predicateFinal = null;
            Predicate predicateName;


            if(!value.isEmpty())
            {
                try{

                    Long id = Long.parseLong(value);
                    predicateFinal = cb.equal(root.get(Entity_.id), id);
                    return predicateFinal;

                }catch(NumberFormatException ex){

                    String name = value;
                    predicateName = cb.like(root.get(Client_.name), name);
                    predicateFinal = predicateName;
                }
            }

            return predicateFinal;

        };
    }
}
