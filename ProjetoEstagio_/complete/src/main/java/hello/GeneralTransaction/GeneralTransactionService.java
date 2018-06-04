package hello.GeneralTransaction;

import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class GeneralTransactionService {

    @Autowired
    GeneralTransactionRepository repository;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;

    private Page<GeneralTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<GeneralTransaction> saleTransactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenreAndActived(pageable, genre, true);

        Specification<GeneralTransaction> specFilter = GeneralTransactionSpecifications.filter(value, frequency, typeValue, subTypeValue, dateSince, dateUntil,valueSince, valueUntil, genre);

        saleTransactionsPage = repository.findAll(specFilter, pageable);

        return saleTransactionsPage;
    }

    public Page<GeneralTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, genre);

        else
            return repository.findAllByGenreAndActived(pageable, genre, true);

    }

    public void addTransaction(@Valid GeneralTransaction projectTransaction) {
        repository.save(projectTransaction);
    }

    public GeneralTransaction getGeneralTransaction(long id)
    {
        return repository.findById(id);
    }

    public void removeTransaction(Long id) {
        GeneralTransaction transaction = getGeneralTransaction((long)id);
        transaction.setActived(false);
        repository.save(transaction);
    }

    public void editTransaction(@Valid GeneralTransaction editedSaleTransaction) {
        GeneralTransaction projectTransaction = getGeneralTransaction((long)editedSaleTransaction.getId());

        projectTransaction.setGenre(editedSaleTransaction.getGenre());

        Type type = typeService.getType(editedSaleTransaction.getType().getId());
        projectTransaction.setType(type);
        if(editedSaleTransaction.getType().getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedSaleTransaction.getType().getSubType().getId());
            projectTransaction.getType().setSubType(subType);
        }
        projectTransaction.setDate(editedSaleTransaction.getDate());
        projectTransaction.setValue(editedSaleTransaction.getValue());
        projectTransaction.setFrequency(editedSaleTransaction.getFrequency());
        projectTransaction.setDescription(editedSaleTransaction.getDescription());
        projectTransaction.setName(editedSaleTransaction.getName());

        repository.save(projectTransaction);
    }
}
