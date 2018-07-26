package hello.Type;

import hello.SubType.SubType;
import hello.SubType.SubTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository repository;

    @Autowired
    SubTypeService subTypeService;


    public void addType(Type type) {

        SubType subType = null;

//        if(type.getSubType()!= null && !type.getSubType().getName().isEmpty())
//            subType = subTypeService.addSubType(type.getSubType());
//
//        type.setSubType(null);
//        Type aux = repository.save(type);
//
//        if(aux!=null){
//
//            aux.setSubType(subType);
//            repository.save(aux);
//        }
    }

    public Type getType(Long id) {
        return repository.findById((long)id);
    }

    public Type getType(Type type) {
        Type aux = null;

        List<Type> types = null;

//        if(type.getSubType()== null || type.getSubType().getName().isEmpty())
//        {
//            types = repository.findByNameAndSubTypeNull(type.getName());
//            if(!types.isEmpty())
//                aux = types.get(0);
//        }
//        else
//        {
//            types = repository.findByNameAndSubTypeName(type.getName(), type.getSubType().getName());
//            if(!types.isEmpty())
//                aux = types.get(0);
//        }


        return aux;
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
    public List<String> getDistinctTypesActived()
    {
        List<String> stringTypes = new ArrayList<>();

        List<Type> types = repository.findAllByActived(true);

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
            return repository.findAllByActivedAndManuallyCreated(pageable, true, true);



//            return repository.findAllByActived(pageable, true);

    }

    public Page<Type> filterTypes(Pageable pageable, String value, String category) {

        Page<Type> page = null;

        if(category.isEmpty())
            return repository.findAllByActivedAndManuallyCreated(pageable, true, true);


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

//        if(editedType.getSubType()!=null)
//        {
//            if(type.getSubType()!=null)
//            {
////                if want delete subtype
//                if(editedType.getSubType().getName().isEmpty())
//                {
//                    subTypeService.removeSubType(type.getSubType());
//                    type.setSubType(null);
//                }
//                else
//                    type.getSubType().setName(editedType.getSubType().getName());
//            }
//            else
//            {
//                SubType subType = subTypeService.addSubType(editedType.getSubType());
//                type.setSubType(subType);
//            }
//        }

        repository.save(type);
    }

    public void removeType(Long id) {
        Type type = repository.getOne(id);
        type.setActived(false);
        repository.save(type);
    }
    public List<Type> getType(String name) {
        return repository.findByName(name);
    }

    //when we select type and need to return the subtypes of this type
    public List<String>getSubTypeList(String type)
    {
        List<Type> typeList = null;

        List<String> subTypeList = new ArrayList<>();

        if(type.equals("")){
            subTypeList = subTypeService.getDistinctSubTypesActived();

        }
        else
        {
            typeList = repository.findByName(type);
            for(Type aux : typeList){

                if(aux.getSubTypeList()!= null)
                {
                    for(SubType subTypeAux : aux.getSubTypeList()){
                        if(subTypeAux.isActived())
                            subTypeList.add(subTypeAux.getName());
                    }
                }
                else
                    subTypeList.add(0,""); //put in first position for frontend
            }
        }

        return subTypeList;
    }

    //when we select a subtype and need return list of types that contains that subtype
    public List<String>getTypeList(String subType)
    {
        List<String> typeList = new ArrayList<>();;

        List<SubType> subTypeList = null;

        if(subType.equals("")){
            typeList = getDistinctTypes();
        }
        else
        {
            subTypeList = subTypeService.findByName(subType);
            for(SubType aux : subTypeList){
                typeList.add(aux.getType().getName());
            }
        }
        return typeList;
    }
}
