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

    private Page<GeneralTransaction> filterTransactions(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<GeneralTransaction> saleTransactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenreAndActived(pageable, genre, true);

        Specification<GeneralTransaction> specFilter;

        Type type = typeService.getType(typeId);

        SubType subType = null;
        if(subTypeId!=null)
            subType= subTypeService.getSubType(subTypeId);

        specFilter= GeneralTransactionSpecifications.filter(value, frequency, type, subType, dateSince, dateUntil,valueSince, valueUntil, genre);

        saleTransactionsPage = repository.findAll(specFilter, pageable);

        return saleTransactionsPage;
    }

    public Page<GeneralTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeId != null || subTypeId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeId, subTypeId, dateSince, dateUntil, valueSince, valueUntil, genre);

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
        if(editedSaleTransaction.getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedSaleTransaction.getSubType().getId());
            projectTransaction.setSubType(subType);
        }
        projectTransaction.setDate(editedSaleTransaction.getDate());
        projectTransaction.setValue(editedSaleTransaction.getValue());
        projectTransaction.setFrequency(editedSaleTransaction.getFrequency());
        projectTransaction.setDescription(editedSaleTransaction.getDescription());
        projectTransaction.setName(editedSaleTransaction.getName());

        repository.save(projectTransaction);
    }
}