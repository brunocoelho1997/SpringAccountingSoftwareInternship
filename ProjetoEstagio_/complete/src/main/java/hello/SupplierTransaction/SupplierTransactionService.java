package hello.SupplierTransaction;

import hello.Enums.Genre;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import hello.Supplier.Supplier;
import hello.Supplier.SupplierService;
import hello.Type.Type;
import hello.Type.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
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

    public void addTransaction(@Valid SupplierTransaction transaction) {
        Supplier supplier = supplierService.getSupplier(transaction.getSupplier().getId());
        transaction.setSupplier(supplier);
        repository.save(transaction);
    }

    public Page<SupplierTransaction> findAllPageableByGenre(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long supplierId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        //could receive params to filter de list
        if(value!= null || frequency!=null || typeId != null || subTypeId != null|| supplierId != null|| dateSince != null|| dateUntil != null|| valueSince != null|| valueUntil != null)
            return filterTransactions(pageable, value, frequency, typeId, subTypeId, supplierId, dateSince, dateUntil, valueSince, valueUntil, genre);
        else
            return repository.findAllByGenre(pageable, genre);

    }

    private Page<SupplierTransaction> filterTransactions(PageRequest pageable, String value, String frequency, Long typeId, Long subTypeId, Long supplierId, String dateSince, String dateUntil, String valueSince, String valueUntil, Genre genre) {

        Page<SupplierTransaction> supplierTransactions = null;

        if(value.isEmpty() && frequency.isEmpty() && typeId == 0 && supplierId == 0 && dateSince.isEmpty()&& dateUntil.isEmpty()&& valueSince.isEmpty() && valueUntil.isEmpty())
            return repository.findAllByGenre(pageable, genre);

        Specification<SupplierTransaction> specFilter;

        Type type = typeService.getType(typeId);

        SubType subType = null;
        if(subTypeId!=null)
            subType= subTypeService.getSubType(subTypeId);

        Supplier supplier = supplierService.getSupplier(supplierId);



        specFilter= SupplierTransactionSpecifications.filter(value, frequency, type, subType, supplier, dateSince, dateUntil,valueSince, valueUntil, genre);

        supplierTransactions = repository.findAll(specFilter, pageable);

        return supplierTransactions;
    }


    public SupplierTransaction getEmployeeTransaction(long id)
    {
        return repository.findById(id);
    }

    public void editTransaction(@Valid SupplierTransaction editedSupplierTransaction) {
        SupplierTransaction employeeTransaction = getEmployeeTransaction((long)editedSupplierTransaction.getId());

        employeeTransaction.setGenre(editedSupplierTransaction.getGenre());

        Type type = typeService.getType(editedSupplierTransaction.getType().getId());
        employeeTransaction.setType(type);
        if(editedSupplierTransaction.getType().getSubType() !=null)
        {
            SubType subType= subTypeService.getSubType(editedSupplierTransaction.getType().getSubType().getId());
            employeeTransaction.getType().setSubType(subType);
        }
        employeeTransaction.setDate(editedSupplierTransaction.getDate());
        employeeTransaction.setValue(editedSupplierTransaction.getValue());
        employeeTransaction.setFrequency(editedSupplierTransaction.getFrequency());
        employeeTransaction.setDescription(editedSupplierTransaction.getDescription());
        employeeTransaction.setName(editedSupplierTransaction.getName());

        Supplier supplier = supplierService.getSupplier(editedSupplierTransaction.getSupplier().getId());
        employeeTransaction.setSupplier(supplier);

        repository.save(employeeTransaction);
    }

    public void removeSupplierTransaction(Long id) {
        SupplierTransaction supplierTransaction = getEmployeeTransaction((long)id);
        repository.delete(supplierTransaction);
    }
}
