package hello.SupplierTransaction;

import hello.Enums.Category;
import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Supplier.Supplier;
import hello.Supplier.SupplierService;
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
public class SupplierTransactionService {

    @Autowired
    SupplierTransactionRepository repository;
    @Autowired
    SupplierService supplierService;
    @Autowired
    TypeService typeService;
    @Autowired
    SubTypeService subTypeService;
    @Autowired
    TypeRepository typeRepository;

    public void addTransaction(@Valid SupplierTransaction transaction) {
        Supplier supplier = supplierService.getSupplier(transaction.getSupplier().getId());
        transaction.setSupplier(supplier);

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

    public Page<SupplierTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long supplierId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeValue != null || supplierId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null || deletedEntities != null)
            return filterTransactions(pageable, value, frequency, typeValue, subTypeValue, supplierId, dateSince, dateUntil, valueSince, valueUntil, deletedEntities, genre, executed);
        else
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);

    }

    private Page<SupplierTransaction> filterTransactions(PageRequest pageable, String value, String frequency, String typeValue, String subTypeValue, Long supplierId, String dateSince, String dateUntil, String valueSince, String valueUntil, Boolean deletedEntities, Genre genre, boolean executed) {

        Page<SupplierTransaction> transactionsPage = null;

        if(value.isEmpty() && frequency.isEmpty() && typeValue.isEmpty() && supplierId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty() && deletedEntities==null)
            return repository.findAllByGenreAndExecutedAndActivedOrderByDateDesc(pageable, genre, executed, true);

        Supplier supplier = supplierService.getSupplier(supplierId);

        Specification<SupplierTransaction> specFilter = null;



        if(value.isEmpty() && frequency.isEmpty() && supplierId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            ;
        else
        {
            if(specFilter==null)
                specFilter = SupplierTransactionSpecifications.filter(value, frequency, supplier, dateSince, dateUntil,valueSince, valueUntil, genre);
            else
                specFilter.and(SupplierTransactionSpecifications.filter(value, frequency, supplier, dateSince, dateUntil,valueSince, valueUntil, genre));

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
                            specFilter = SupplierTransactionSpecifications.filterByType(type1);
                        else
                            specFilter = specFilter.or(SupplierTransactionSpecifications.filterByType(type1));
                    }
                }
            }
        }
        else
        {
            if(specFilter==null)
                specFilter = SupplierTransactionSpecifications.filterByNameType(typeValue);
            else
                specFilter = specFilter.or(SupplierTransactionSpecifications.filterByNameType(typeValue));
        }


        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = SupplierTransactionSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(SupplierTransactionSpecifications.filterDeleletedEntities(deletedEntities));


        //executed
        if(specFilter==null)
            specFilter = SupplierTransactionSpecifications.filterExecuted(true);
        else
            specFilter = specFilter.and(SupplierTransactionSpecifications.filterExecuted(true));


        transactionsPage = repository.findAll(specFilter, pageable);

        return transactionsPage;
    }


    public SupplierTransaction getTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editTransaction(@Valid SupplierTransaction editedSupplierTransaction) {
        SupplierTransaction transaction = getTransaction((long)editedSupplierTransaction.getId());

        transaction.setGenre(editedSupplierTransaction.getGenre());

        Type type = transaction.getType();
        type.setName(editedSupplierTransaction.getType().getName());
        type.setSubTypeList(editedSupplierTransaction.getType().getSubTypeList());

        transaction.setDate(editedSupplierTransaction.getDate());
        transaction.setValue(editedSupplierTransaction.getValue());
        transaction.setFrequency(editedSupplierTransaction.getFrequency());
        transaction.setDescription(editedSupplierTransaction.getDescription());
        transaction.setName(editedSupplierTransaction.getName());

        Supplier supplier = supplierService.getSupplier(editedSupplierTransaction.getSupplier().getId());
        transaction.setSupplier(supplier);

        repository.save(transaction);
    }
    public void removeTransaction(Long id) {
        SupplierTransaction transaction = getTransaction((long)id);
        transaction.setActived(false);
        repository.save(transaction);
    }
    public void recoveryTransaction(Long id) {
        SupplierTransaction transaction = getTransaction((long)id);
        transaction.setActived(true);
        repository.save(transaction);
    }

}
