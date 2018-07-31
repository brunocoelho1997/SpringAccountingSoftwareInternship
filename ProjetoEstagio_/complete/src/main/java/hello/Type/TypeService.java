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

        List<SubType> subTypeList = type.getSubTypeList();

        type.setSubTypeList(new ArrayList<>());
        type.setManuallyCreated(true);
        type = repository.save(type);

        if(!subTypeList.isEmpty())
        {
            for(SubType subType: subTypeList){
                subType.setType(type);
                subTypeService.save(subType);
            }
        }
    }

    public Type getType(Long id) {
        Type type = repository.findById((long)id);

        List<SubType> subtypes = subTypeService.findByTypeNameAndActived(type.getName(), true);

        type.getSubTypeList().addAll(subtypes);

        return type;
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
    public List<String> getDistinctTypesActivedAndManuallyCreated()
    {
        List<String> stringTypes = new ArrayList<>();

        List<Type> types = repository.findAllByActivedAndManuallyCreated(true, true);

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

        System.out.print("\n------------\n\n edited:" + editedType);

        Type type = getType(editedType.getId());


        System.out.print("\n\n\n type:" + type);


        List<SubType> subTypeList = type.getSubTypeList();

        //a type manually created need to has empty subtype list
        type.setSubTypeList(new ArrayList<>());

        //need to change the name to all types with that name
        List<Type> listAux = repository.findByName(type.getName());
        for(Type typeAux : listAux)
        {
            typeAux.setName(editedType.getName());
            typeAux.setCategory(editedType.getCategory());
            repository.save(typeAux);
        }



        //define all subtypes as deleted...  In future the subtypes presented in editedType will be activated
        for(SubType subType : subTypeList)
        {
            subType.setActived(false);
            subTypeService.save(subType);
        }

        for(SubType editedSubType : editedType.getSubTypeList())
        {
            //if id  = 0 is a new SubType
            if(editedSubType.getId()==null)
            {
                editedSubType.setType(type);
                editedSubType.setActived(true);
                subTypeService.save(editedSubType);
            }
            else
            {
                SubType subTypeAux = subTypeService.getSubType(editedSubType.getId());
                subTypeAux.setActived(true);
                subTypeAux.setName(editedSubType.getName());
                subTypeService.save(subTypeAux);
            }
        }


//        type.setCategory(editedType.getCategory());
//        type.setName(editedType.getName());
//
//        List<SubType> subTypeList = type.getSubTypeList();
//
//        //verify which subtypes was deleted
//        for(SubType subType: subTypeList){
//
//            if(!editedType.getSubTypeList().contains(subType))
//                subType.setActived(false);
//
//            subTypeService.save(subType);
//        }

        //verify new subtypes


//        Type type = getType(editedType.getId());
//        type.setCategory(editedType.getCategory());
//        type.setName(editedType.getName());
//        type.setActived(editedType.isActived());

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
//                SubType subType = subTypeService.save(editedType.getSubType());
//                type.setSubType(subType);
//            }
//        }

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
