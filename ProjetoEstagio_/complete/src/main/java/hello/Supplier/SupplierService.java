package hello.Supplier;

import hello.Employee.Employee;
import hello.Employee.EmployeeSpecifications;
import hello.SubType.SubTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    @Autowired
    SupplierRepository repository;

    public Page<Supplier> findAllPageable(Pageable pageable, String value) {


        //could receive params to filter de list
        if(value!= null)
            return filterSupplier(pageable, value);

        else
            return repository.findAllByActived(pageable, true);

    }
    public Page<Supplier> filterSupplier(Pageable pageable, String value) {

        Page<Supplier> page = null;

        if(value.isEmpty())
            return repository.findAllByActived(pageable, true);

        Specification<Employee> specFilter;
        specFilter= EmployeeSpecifications.filter(value);

        page = repository.findAll(specFilter, pageable);

        return page;
    }

//    public void addSupplier(Supplier supplier) {
//        repository.save(supplier);
//    }
//
//    public Supplier getSupplier(Long id) {
//        return repository.findById((long)id);
//    }
//
//    public void editSupplier(Supplier editedSupplier){
//
//        Supplier supplier = getSupplier(editedSupplier.getId());
//
//        supplier.setActived(editedSupplier.isActived());
//        supplier.setAdresses(editedSupplier.getAdresses());
//        supplier.setName(editedSupplier.getName());
//        supplier.setEmail(editedSupplier.getEmail());
//        supplier.setNumberPhone(editedSupplier.getNumberPhone());
//        supplier.setAdresses(editedSupplier.getAdresses());
//        supplier.setContacts(editedSupplier.getContacts());
//        repository.save(supplier);
//    }
//
//    public void removeSupplier(Long id) {
//        Supplier supplier = getSupplier(id);
//        supplier.setActived(false);
//        repository.save(supplier);
//    }
}
