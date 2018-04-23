package hello.SubType;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class SubTypeService {

    @Autowired
    SubTypeRepository subTypeRepository;

    public void addSubType(SubType subType) {
        subTypeRepository.save(subType);
    }

    //method private! If you need a client use method getClient(long id);
    private SubType getOne(Long id) {

        try
        {
            if(id == null)
                throw new EntityNotFoundException();

            SubType c = subTypeRepository.getOne(id);

            return c;
        }catch (EntityNotFoundException ex)
        {
            return null;
        }
    }

    public SubType getSubType(Long id) {
        try
        {
            return getOne(id);

        }catch (EntityNotFoundException ex)
        {
            //if can't getClient
            return null;
        }
    }
}
