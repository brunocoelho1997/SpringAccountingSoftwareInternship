package hello.FinancialProjection;

import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Transaction.Transaction;
import hello.Transaction.TransactionRepository;
import hello.Transaction.TransactionService;
import hello.Transaction.TransactionSpecifications;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinancialProjectionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;

    public Page<Transaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || subTypeValue != null || dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return transactionRepository.findAllByGenreAndExecuted(pageable, genre, false);
    }

    private Page<Transaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<Transaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return transactionRepository.findAllByGenreAndExecuted(pageable, genre, false);

        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
        }

        Specification<Transaction> specFilter= TransactionSpecifications.filterToFinancialProjection(value, frequency, typeValue, subTypeList, dateSince, dateUntil,valueSince, valueUntil, genre);

        transactionsPage = transactionRepository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public Transaction getTransaction(long id)
    {
        return transactionRepository.findById(id);
    }
}
