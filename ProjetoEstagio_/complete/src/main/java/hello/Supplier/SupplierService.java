package hello.Supplier;

import hello.Employee.Employee;
import hello.Employee.EmployeeSpecifications;
import hello.SubType.SubTypeRepository;
import hello.SupplierTransaction.SupplierTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository repository;

    public List<Supplier> getSuppliers(){
        return repository.findAll();
    }
    public List<Supplier> getActivedSuppliers(){
        return repository.findAllByActived(true);
    }

    public Page<Supplier> findAllPageable(Pageable pageable, String value, Boolean deletedEntities) {


        //could receive params to filter de list
        if(value!= null || deletedEntities != null)
            return filterEntities(pageable, value, deletedEntities);

        else
            return repository.findAllByActived(pageable, true);

    }
    public Page<Supplier> filterEntities(Pageable pageable, String value, Boolean deletedEntities) {

        Page<Supplier> page = null;

        if(value.isEmpty() && deletedEntities==null)
            return repository.findAllByActived(pageable, true);

        Specification<Supplier> specFilter = null;

        if(!value.isEmpty())
            specFilter= SupplierSpecifications.filter(value);

        //deleted entities
        deletedEntities = (deletedEntities == null ? false : true);

        if(specFilter==null)
            specFilter = SupplierSpecifications.filterDeleletedEntities(deletedEntities);
        else
            specFilter = specFilter.and(SupplierSpecifications.filterDeleletedEntities(deletedEntities));


        page = repository.findAll(specFilter, pageable);

        return page;
    }

    public void addSupplier(Supplier supplier) {
        repository.save(supplier);
    }

    public Supplier getSupplier(Long id) {
        return repository.findById((long)id);
    }

    public void editSupplier(Supplier editedSupplier){

        Supplier supplier = getSupplier(editedSupplier.getId());

        supplier.setActived(editedSupplier.isActived());
        supplier.setName(editedSupplier.getName());
        supplier.setContacts(editedSupplier.getContacts());
        supplier.setAdresses(editedSupplier.getAdresses());
        repository.save(supplier);
    }

    public void removeSupplier(Long id) {
        Supplier supplier = getSupplier(id);
        supplier.setActived(false);
        repository.save(supplier);
    }

    public void recoveryEntity(Long id) {
        Supplier entity = getSupplier(id);
        entity.setActived(true);
        repository.save(entity);
    }
}
