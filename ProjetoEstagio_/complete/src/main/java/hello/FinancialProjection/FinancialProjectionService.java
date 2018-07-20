package hello.FinancialProjection;

import hello.Enums.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class FinancialProjectionService {

//    public Page<EmployeeTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long employeeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {
//
//
//        //could receive params to filter de list
//        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || employeeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
//            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, employeeId, dateSince, dateUntil, valueSince, valueUntil, genre);
//        else
//            return repository.findAllByGenre(pageable, genre);
//    }
}
