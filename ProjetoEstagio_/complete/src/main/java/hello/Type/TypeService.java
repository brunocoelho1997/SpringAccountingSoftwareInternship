package hello.Type;

import hello.Client.ClientSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository repository;

    public void addType(Type type) {
        repository.save(type);
    }

    public Type getType(Long id) {
        return repository.findById((long)id);
    }

    public List<Type> getTypes(){
        return repository.findAll();
    }

    public List<Type> getTypesActived(){ return repository.findAllByActived(true); }

    public Page<Type> findAllPageable(Pageable pageable, String value, String category) {


        //could receive params to filter de list
        if(category != null)
            return filterTypes(pageable, value, category);

        else
            return repository.findAllByActived(pageable, true);

    }

    public Page<Type> filterTypes(Pageable pageable, String value, String category) {

        Page<Type> page = null;

        if(category.isEmpty())
            return repository.findAllByActived(pageable, true);

        Specification<Type> specFilter;
        specFilter= TypeSpecifications.filter(value, category);

        page = repository.findAll(specFilter, pageable);

        return page;
    }

    public void editType(Type editedType){
        Type type = getType(editedType.getId());
        type.setCategory(editedType.getCategory());
        type.setName(editedType.getName());
        type.setActived(editedType.isActived());
        type.setSubTypeList(editedType.getSubTypeList());

        repository.save(type);
    }

    public void removeType(Long id) {
        Type type = repository.getOne(id);
        type.setActived(false);
        repository.save(type);
    }

}
