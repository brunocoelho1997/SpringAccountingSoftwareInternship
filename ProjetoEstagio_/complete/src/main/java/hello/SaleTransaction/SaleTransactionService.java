package hello.SaleTransaction;

import hello.Enums.Category;
import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Type.Type;
import hello.Type.TypeRepository;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SaleTransactionService {

    @Autowired
    SaleTransactionRepository repository;
    @Autowired
    TypeService typeService;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    SubTypeService subTypeService;

    private Page<SaleTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<SaleTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty()&& subTypeValue.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);


        Specification<SaleTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = SaleTransactionSpecifications.filter(value, frequency, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(SaleTransactionSpecifications.filter(value, frequency, dateSince, dateUntil,valueSince, valueUntil, genre));

        }

        //types and subtypes
        List<SubType> subTypeList = null;
        if(!subTypeValue.isEmpty()){
            subTypeList = subTypeService.getSubType(subTypeValue);
//            System.out.println("\n\n\n\n subTypeList: " + subTypeList);
        }
        if(subTypeValue!= null && !subTypeValue.isEmpty())
        {
            List<Type> types = typeRepository.findByName(typeValue);

            if(!types.isEmpty())
            {
                for(Type type1: types)
                {
                    if(!type1.isManuallyCreated() && !Collections.disjoint(type1.getSubTypeList(), subTypeList))
                    {
//                        System.out.println("\n\n Type: " + type1);

                        if(specFilter==null)
                            specFilter = SaleTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(SaleTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = SaleTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(SaleTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = SaleTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(SaleTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = SaleTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(SaleTransactionSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }
    public List<SaleTransaction> findAllByGenreAndActivedAndExecuted(Genre genre, boolean actived, boolean executed)
    {
        return repository.findAllByGenreAndActivedAndExecuted(genre, actived, executed);
    }

    public Page<SaleTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);


    }

    public void addTransaction(@Valid SaleTransaction transaction) {

        Type typeAux = new Type(transaction.getType().getName());
        typeAux.setCategory(Category.SUPPLIERS);
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : transaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);

        transaction.setType(typeAux);
        transaction.setExecuted(true);

        repository.save(transaction);
    }

    public SaleTransaction getSaleTransaction(long id)
    {
        return repository.findById(id);
    }

    public void removeSaleTransaction(Long id) {
        SaleTransaction saleTransaction = getSaleTransaction((long)id);
        saleTransaction.setActived(false);
        repository.save(saleTransaction);
    }

    public void editSaleTransaction(@Valid SaleTransaction editedSaleTransaction) {
        SaleTransaction transaction = getSaleTransaction((long)editedSaleTransaction.getId());

        transaction.setGenre(editedSaleTransaction.getGenre());

        Type type = transaction.getType();
        type.setName(editedSaleTransaction.getType().getName());
        type.setSubTypeList(editedSaleTransaction.getType().getSubTypeList());

        transaction.setDate(editedSaleTransaction.getDate());
        transaction.setValue(editedSaleTransaction.getValue());
        transaction.setFrequency(editedSaleTransaction.getFrequency());
        transaction.setDescription(editedSaleTransaction.getDescription());
        transaction.setName(editedSaleTransaction.getName());

        repository.save(transaction);
    }
    public void recoveryTransaction(Long id) {
        SaleTransaction transaction = getSaleTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }
}
