package hello.SubType;


import hello.Type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubTypeService {

    @Autowired
    SubTypeRepository repository;

    public SubType addSubType(SubType subType) {
        return repository.save(subType);
    }

    public SubType getSubType(Long id) {
        return repository.findById((long)id);
    }

    public List<SubType> getSubType(String name) {
        return repository.findByName(name);
    }

    public void editSubType(SubType editedSubType) {

        SubType aux = repository.findById((long)editedSubType.getId());

        aux.setActived(editedSubType.isActived());
        aux.setName(editedSubType.getName());
        repository.save(aux);
    }

    public void removeSubType(SubType subType) {

        /*
        TODO: remove-se mesmo nao?
         */
        subType.setActived(false);
        repository.save(subType);
    }

    public List<String> getDistinctSubTypesActived()
    {
        List<String> stringTypes = new ArrayList<>();

        List<SubType> subTypeList = repository.findAllByActived(true);

        /*
        doing the distinct... TODO: use JPA to do this. HUGO
         */
        for(SubType subType : subTypeList){
            if(!stringTypes.contains(subType.getName()))
                stringTypes.add(subType.getName());
        }

        return stringTypes;
    }

    public List<SubType> findByName(String name){
        return repository.findByName(name);
    }


    public List<SubType> getSubTypes(Type type){
        return repository.findByTypeName(type.getName());
    }

    public Page<SubType> findAllByActived(Pageable pageable, boolean actived){
        return repository.findAllByActived(pageable, actived);
    }
}
