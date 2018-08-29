package hello.GeneralTransaction;

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
public class GeneralTransactionService {

    @Autowired
    GeneralTransactionRepository repository;
    @Autowired
    TypeService typeService;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    SubTypeService subTypeService;

    private Page<GeneralTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<GeneralTransaction> transactionsPage = null;


        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);


        Specification<GeneralTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = GeneralTransactionSpecifications.filter(value, frequency, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(GeneralTransactionSpecifications.filter(value, frequency, dateSince, dateUntil,valueSince, valueUntil, genre));

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
                            specFilter = GeneralTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(GeneralTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = GeneralTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(GeneralTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = GeneralTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(GeneralTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = GeneralTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(GeneralTransactionSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }

    public Page<GeneralTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);

        else
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);


    }

    public void addTransaction(@Valid GeneralTransaction transaction) {

        Type typeAux = new Type(transaction.getType().getName());
        typeRepository.save(typeAux);

        typeAux.setSubTypeList(new ArrayList<>());
        for(SubType subTypeAux : transaction.getType().getSubTypeList())
            typeAux.getSubTypeList().add(subTypeAux);

        typeRepository.save(typeAux);
        transaction.setType(typeAux);
        transaction.setExecuted(true);


        repository.save(transaction);
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
        GeneralTransaction transaction = getGeneralTransaction((long)editedSaleTransaction.getId());

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
        GeneralTransaction transaction = getGeneralTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }
}
