package hello.Project;

import hello.Client.Client;
import hello.Client.ClientService;
import hello.EntityPackage.Entity_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;


public class ProjectSpecifications {


    private ProjectSpecifications() {}

    public static Specification<Project> filter(String value, String initialDate, String finalDate, Client client) {
        return (root, query, cb) -> {


            Predicate predicateFinal = null;
            Predicate predicateName;
            Predicate predicateDateSince;
            Predicate predicateDateUntil;
            Predicate predicateClient;
            Predicate predicateActived;


            if(!value.isEmpty())
            {
                try{

                    Long id = Long.parseLong(value);
                    predicateFinal = cb.equal(root.get(Entity_.id), id);
                    return predicateFinal;

                }catch(NumberFormatException ex){

                    String name = value;
                    predicateName = cb.like(root.get(Project_.name), name);
                    predicateFinal = predicateName;

                }

            }

            if(!initialDate.isEmpty())
            {
                LocalDate localDateSince = LocalDate.parse(initialDate);
                predicateDateSince = cb.greaterThan(root.get(Project_.initialDate), localDateSince);

                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateDateSince);
                else
                    predicateFinal = predicateDateSince;
            }

            if(!finalDate.isEmpty())
            {
                LocalDate localDateUntil = LocalDate.parse(finalDate);
                predicateDateUntil = cb.lessThan(root.get(Project_.finalDate), localDateUntil);

                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateDateUntil);
                else
                    predicateFinal = predicateDateUntil;
            }

            if(!(client == null))
            {
                predicateClient = cb.equal(root.get(Project_.client), client);

                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateClient);
                else
                    predicateFinal = predicateClient;
            }


            //just show the clients actived
            predicateActived = cb.equal(root.get(Entity_.actived), true);
            predicateFinal = cb.and(predicateFinal, predicateActived);

            return predicateFinal;

        };
    }

    public static Specification<Project> filterDeleletedEntities(Boolean deletedEntities) {
        return (root, query, cb) -> {
            Predicate predicateFinal = null;

            predicateFinal = cb.equal(root.get(Entity_.actived), !(deletedEntities));

            return predicateFinal;

        };
    }
}
