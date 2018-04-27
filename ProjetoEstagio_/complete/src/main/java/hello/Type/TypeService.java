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

    public Type getType(Long id) {
        return typeRepository.findById((long)id);
    }

    public List<Type> getTypes(){
        return typeRepository.findAll();
    }
}
