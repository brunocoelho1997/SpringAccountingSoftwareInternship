package hello.Type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TypeService {

    @Autowired
    TypeRepository typeRepository;

    public void addType(Type type) {
        typeRepository.save(type);
    }

    //method private! If you need a client use method getClient(long id);
    private Type getOne(Long id) {

        try
        {
            if(id == null)
                throw new EntityNotFoundException();

            Type c = typeRepository.getOne(id);

            return c;
        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }

    public Type getType(Long id) {
        try
        {
            return getOne(id);

        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }

    public List<Type> getTypes(){
        return typeRepository.findAll();
    }
}
