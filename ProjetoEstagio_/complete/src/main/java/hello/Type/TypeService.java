package hello.Type;

import hello.Client.ClientSpecifications;
import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository repository;

    @Autowired
    SubTypeService subTypeService;

    public void addType(Type type) {
        repository.save(type);
    }

    public Type getType(Long id) {
        return repository.findById((long)id);
    }

    public List<Type> getTypes(){
        return repository.findAll();
    }

    public List<String> getDistinctTypes()
    {
        List<String> stringTypes = new ArrayList<>();

        List<Type> types = repository.findAll();

        /*
        doing the distinct... TODO: use JPA to do this. HUGO
         */
        for(Type type : types){
            if(!stringTypes.contains(type.getName()))
                stringTypes.add(type.getName());
        }

        return stringTypes;
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


//        type.setSubTypeList(new ArrayList<>());
//        for(SubType editedSubType : editedType.getSubTypeList()){
//            type.getSubTypeList().add(editedSubType);
//        }

//        if subtypelist has changed....
//        for(SubType editedSubType : editedType.getSubTypeList()){
//
//            for(SubType subType : type.getSubTypeList()){
//
//
//
//                if(editedSubType.getId().equals(subType.getId()))
//                {
//                    subType.setActived(editedSubType.isActived());
//                    subType.setName(editedSubType.getName());
//
//
//                }
//                else
//                {
//
//                    if(typeContainsSubType(type, editedSubType)){
//
//                    }
//
//                    type.getSubTypeList().add(editedSubType);
//                }
//            }
//        }


        repository.save(type);
    }

    private boolean typeContainsSubType(Type type, SubType subtype){

        return false;
    }

    public void removeType(Long id) {
        Type type = repository.getOne(id);
        type.setActived(false);
        repository.save(type);
    }

    public List<String>getSubTypeList(String type)
    {
        List<Type> typeList = repository.findByName(type);

        List<String> subTypeList = new ArrayList<>();

        for(Type aux : typeList){
            if(aux.getSubType()!= null)
                subTypeList.add(aux.getSubType().getName());
        }

        return subTypeList;
    }

}
