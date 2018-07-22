package hello.Transaction;

import hello.EntityPackage.Entity_;
import hello.Enums.Frequency;
import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.Type.Type_;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.util.List;

public class TransactionSpecifications {
    public static Specification<Transaction> filterToFinancialProjection(String value, String frequency, String type, List<SubType> subTypeList, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {
        return (root, query, cb) -> {


            Predicate predicateFinal = null;
            Predicate predicateName;
            Predicate predicateFrequency;
            Predicate predicateType;
            Predicate predicateSubType;
            Predicate predicateProject;
            Predicate predicateDateSince;
            Predicate predicateDateUntil;
            Predicate predicateValueSince;
            Predicate predicateValueUntil;
            Predicate predicateGenre;

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

            if(!frequency.isEmpty())
            {
                predicateFrequency = cb.equal(root.get(Transaction_.frequency), Frequency.valueOf(frequency));
                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateFrequency);
                else
                    predicateFinal = predicateFrequency;
            }

            if(type!= null && !type.isEmpty())
            {
                predicateType = cb.equal(root.get(Transaction_.type).get(Type_.name), type);
                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateType);
                else
                    predicateFinal = predicateType;
            }
            if(subTypeList != null)
            {

                String typeName = subTypeList.get(0).getType().getName();

                predicateSubType = cb.equal(root.get(Transaction_.type).get(Type_.name), typeName);
                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateSubType);
                else
                    predicateFinal = predicateSubType;
            }

            if(!dateSince.isEmpty())
            {
                LocalDate localDateSince = LocalDate.parse(dateSince);
                predicateDateSince = cb.greaterThan(root.get(Transaction_.date), localDateSince);

                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateDateSince);
                else
                    predicateFinal = predicateDateSince;
            }

            if(!dateUntil.isEmpty())
            {
                LocalDate localDateUntil = LocalDate.parse(dateUntil);
                predicateDateUntil = cb.lessThan(root.get(Transaction_.date), localDateUntil);

                if(predicateFinal!=null)
                    predicateFinal = cb.and(predicateFinal, predicateDateUntil);
                else
                    predicateFinal = predicateDateUntil;
            }

            if(!valueSince.isEmpty())
            {
                Float valueFloat;

                try{
                    valueFloat = Float.parseFloat(valueSince);

                    predicateValueSince = cb.greaterThan(root.get(Transaction_.value), valueFloat);

                    if(predicateFinal!=null)
                        predicateFinal = cb.and(predicateFinal, predicateValueSince);
                    else
                        predicateFinal = predicateValueSince;

                }catch (Exception ex){
                    ; //ignore
                }
            }

            if(!valueUntil.isEmpty())
            {
                Float valueFloat;

                try{
                    valueFloat = Float.parseFloat(valueUntil);

                    predicateValueUntil = cb.lessThan(root.get(Transaction_.value), valueFloat);

                    if(predicateFinal!=null)
                        predicateFinal = cb.and(predicateFinal, predicateValueUntil);
                    else
                        predicateFinal = predicateValueUntil;

                }catch (Exception ex){
                    ; //ignore
                }
            }

            //define Genre
            predicateGenre = cb.equal(root.get(Transaction_.genre), genre);

            predicateFinal = cb.and(predicateFinal, predicateGenre);
            return predicateFinal;

        };
    }
}
