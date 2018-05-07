package hello.ProjectTransaction;

import hello.Client.Client;
import hello.EntityPackage.Entity_;
import hello.Enums.Frequency;
import hello.Project.Project;
import hello.SubType.SubType;
import hello.Transaction.Transaction;
import hello.Transaction.Transaction_;
import hello.Type.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public class ProjectTransactionSpecifications {

    public static Specification<ProjectTransaction> filter(String value, Frequency frequency, Type type, SubType subType, Project project, String dateSince, String dateUntil, String valueSince, String valueUntil) {
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
                    predicateName = cb.like(root.get(Transaction_.name), name);
                    predicateFinal = predicateName;

                }

            }

            return predicateFinal;

        };
    }
}
